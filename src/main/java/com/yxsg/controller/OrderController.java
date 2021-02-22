package com.yxsg.controller;
/**
 * 处理订单模块的请求
 */

import java.util.List;
import java.util.Map;

import com.yxsg.service.inter.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yxsg.bean.Order;
import com.yxsg.bean.PageBean;
import com.yxsg.utils.Utils;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	/* 个人订单开始 */
	/**
	 * 查看个人订单
	 */
	@GetMapping("/orderNav")
	public PageBean<Order> myOrder(Integer uId, Integer pageCount, Integer pageOn, Boolean needSelCon){
		List<Order> myOrderList = null;
		PageBean<Order> orderPage = orderService.getPage(pageCount, uId, needSelCon);
		orderPage = Utils.getPage(orderPage, pageOn);
		if(orderPage.getPageCount() != 0) {
			myOrderList = orderService.selMyOrder(uId, pageOn);
			orderPage.setList(myOrderList);
		}
		return orderPage;
	}

	/**
	 * 删除用户订单
	 */
	@DeleteMapping("/delUserOrder")
	public PageBean<Order> delUserOrder(@RequestBody Map<String, String> data) {
		return orderService.delUserOrder(data);
	}
	/* 个人订单结束 */
}
