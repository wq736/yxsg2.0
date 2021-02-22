package com.yxsg.proxy;

import java.io.File;

import com.yxsg.exception.NumberException;
import com.yxsg.mapper.CarMapper;
import com.yxsg.mapper.OrderMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//import com.yxsg.exception.MyException;
import com.yxsg.utils.Utils;

/**
 *	用户相关的aop
 */

@Aspect
@Component
public class UserProxy {
	
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderMapper orderMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private CarMapper carMapper;

	/**
	 * 删除用户前删除相关的购物车信息、订单信息
	 * 删除用户后删除用户的头像
	 */
	@Transactional(rollbackFor = Exception.class)
	@Around(value = "execution(* com.yxsg.service.impl.UserServiceImpl.delUser(..))")
	public void delUserAround(ProceedingJoinPoint point) {
		Integer uId = Integer.valueOf(point.getArgs()[0] + "");
		Integer canDel = orderMapper.selNotHandleCount(uId);
		if(canDel != 0) {throw new NumberException("删除失败！您的账户中存在未处理的订单！");}
		carMapper.delCarByUid(uId, null);
		orderMapper.deleteById(uId, null);
		try {
			point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Utils.delShopPic(new File(Utils.picPath + uId));
	}
}
