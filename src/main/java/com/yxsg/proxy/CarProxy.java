package com.yxsg.proxy;
/**
 * 购物车相关aop
 */
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.yxsg.exception.ShopException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.yxsg.mapper.CarMapper;
import com.yxsg.mapper.ShopMapper;
import com.yxsg.mapper.UserMapper;

@Aspect
@Component
public class CarProxy {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private CarMapper carMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ShopMapper shopMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private UserMapper userMpper;

	/**
	 * 结算购物车
	 */
	@Transactional(rollbackFor = Exception.class)
	@Around(value = "execution(* com.yxsg.service.impl.CarServiceImpl.jsMyCar(..))")
	public void jsMyCarAround(ProceedingJoinPoint point){
		Integer uId = Integer.valueOf(point.getArgs()[0] + "");
		List<String> shopIdList = carMapper.selShopIdById(uId, null);
		Map<String, String> ids = shopMapper.selIdsByIds(shopIdList);
		for (Entry<String, String> idEntry : ids.entrySet()) {
			if("isNull".equals(idEntry.getValue())) {
				throw new ShopException("结算失败，购物车中存在已下架的商品或商品所在的店铺已注销！");
			}
		}
		userMpper.updMoneyById(uId, null, null, new BigDecimal("-" + point.getArgs()[2]));
		try {
			point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("开始删除购物车信息");
		carMapper.delCarByUid(uId, null);
	}
}
