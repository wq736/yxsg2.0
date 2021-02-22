package com.yxsg.proxy;

import java.math.BigDecimal;
import java.util.Map;

import com.yxsg.exception.NumberException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.yxsg.mapper.OrderItemMapper;
import com.yxsg.mapper.ShopMapper;
import com.yxsg.mapper.UserMapper;

/**
 * 订单详情相关的aop
 */
@Aspect
@Component
public class OrderItemProxy {
	
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderItemMapper orderItemMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ShopMapper shopMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 修改该订单项的状态
	 * 签收后店铺长获取收入、该商品的总销售额增加
	 * 退货后店铺长扣除收入、该商品的总销售额减少
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	@Around(value = "execution(* com.yxsg.service.impl.OrderItemServiceImpl.updStaByShop(..))")
	public String afterUpdSta(ProceedingJoinPoint point) {
		String sta = "";
		Map<String, String> data = (Map<String, String>) point.getArgs()[0];
		Map<String, String> idSta = orderItemMapper.selShopIdSta(data);
		String status = point.getArgs()[1] + "";		
		orderItemMapper.updStaByShop(data, status);
		try {
			sta = point.proceed() + "";
		} catch (Throwable e) {
			e.printStackTrace();
		}
		String allPrice = data.get("allPrice");
		if(!"已退款".equals(status)) {
			String shopId = idSta.get("shopId") + "";
			if("已签收".equals(status)) {
				shopMapper.updAllPrice(shopId, new BigDecimal(allPrice));
				userMapper.updMoneyById(null, shopId.substring(0, shopId.indexOf("-")), null, new BigDecimal(allPrice));
			} else if("已退货".equals(status)) {
				if("申请退货中1".equals(idSta.get("oiStatus"))){
					userMapper.updMoneyById(null, shopId.substring(0, shopId.indexOf("-")), null, new BigDecimal("-" + allPrice));
					shopMapper.updAllPrice(shopId, new BigDecimal("-" + allPrice));
				}
				shopMapper.updDiscountByName(shopId, Integer.valueOf("-" + data.get("number")));
				userMapper.updMoneyById(null, null, data.get("oId"), new BigDecimal(allPrice));
			}	
		} else {
			userMapper.updMoneyById(null, null, data.get("oId"), new BigDecimal(allPrice));				
		}
		return sta;
	}

	/**
	 * 删除商品前查看是否存在未处理的订单
	 * 删除商品前需要查看商品相关的订单信息，供用户下载保存
	 */
	@Before(value="execution(* com.yxsg.service.impl.OrderItemServiceImpl.shopOiList(..))")
	public void delShopAround(JoinPoint point){
		String shopName = null;
		String stoId = null;
		if(Integer.valueOf(point.getArgs()[2] + "") == 1) {
			if(point.getArgs()[0] != null) {shopName = point.getArgs()[0] + "";}
			if(point.getArgs()[1] != null) {stoId = point.getArgs()[1] + "";}
			Integer canDelOrderItem = orderItemMapper.selNotHandleCount(shopName, stoId);
			if(canDelOrderItem != 0) {throw new NumberException("删除失败！该商品存在尚未处理的相关订单！");}
		}
	}
}
