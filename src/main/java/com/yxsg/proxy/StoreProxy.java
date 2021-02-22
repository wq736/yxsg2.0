package com.yxsg.proxy;

import java.io.File;

import com.yxsg.exception.NumberException;
import com.yxsg.mapper.CommentMapper;
import com.yxsg.mapper.OrderItemMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//import com.yxsg.exception.MyException;
//import com.yxsg.mapper.CommentMapper;
//import com.yxsg.mapper.OrderItemMapper;
import com.yxsg.mapper.ShopMapper;
import com.yxsg.utils.Utils;

/**
 * 店铺的aop
 */

@Aspect
@Component
public class StoreProxy {
	
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderItemMapper orderItemMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private CommentMapper commentMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ShopMapper shopMapper;
	
	/**
	 * 删除店铺之前根据删除等级查看是否需要判断订单，1需要，2不需要
	 * 删除店铺之后删除店铺拥有的商品的图片，评论等
	 */
	@Transactional(rollbackFor = Exception.class)
	@Around(value = "execution(* com.yxsg.service.impl.StoreServiceImpl.delMySto(..))")
	public void delMyStoAround(ProceedingJoinPoint point) {
		String stoId = point.getArgs()[0] + "";
		Integer delGender = Integer.valueOf(point.getArgs()[2] + "");
		if(delGender == 1){
			Integer canDel = orderItemMapper.selNotHandleCount(null, stoId);
			if(canDel != 0) {throw new NumberException("删除失败！您的店铺中存在尚未处理的订单！");}
			orderItemMapper.updScanBySto(stoId, "N");
		}
		orderItemMapper.updAboutByStoId(stoId, "店铺已被删除");
		commentMapper.delComByShop(null, stoId + "-%");
		shopMapper.delByStoId(stoId);
		try {
			point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		//删除文件
		String uId = point.getArgs()[1] + "";
		String shopPicUrl = Utils.picPath + uId + "\\store";
		String commPicUrl = Utils.picPath + "comment\\" + stoId;
		Utils.delShopPic(new File(shopPicUrl));
		Utils.delShopPic(new File(commPicUrl));
	}

	/**
	 * 当对店铺进行注销时设置店铺的相关订单
	 */
	@Transactional(rollbackFor = Exception.class)
	@Around(value = "execution(* com.yxsg.service.impl.StoreServiceImpl.updStoSta(..))")
	public String updStoStaAround(ProceedingJoinPoint point) {
		String status = null;
		Integer staCode = Integer.valueOf(point.getArgs()[1] + "");
		if(-2 == staCode) {orderItemMapper.updAboutByStoId(point.getArgs()[0] + "", "店铺已被注销");}
		if(-1 == staCode) {orderItemMapper.updAboutByStoId(point.getArgs()[0] + "", null);}
		try {
			status = point.proceed() + "";
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return status;
	}
}
