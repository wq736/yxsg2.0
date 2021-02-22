package com.yxsg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxsg.service.inter.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yxsg.bean.OrderItem;
import com.yxsg.bean.PageBean;
import com.yxsg.utils.Utils;

/**
 * 处理订单详情相关的请求
 */
@RestController
public class OrderItemController {
	
	@Autowired
	private OrderItemService orderItemService;
	
	/* 店铺信息面板开始 */
	/* 查看店铺对应的订单信息 */
	@GetMapping(value = "/selStoOi")
	public List<OrderItem> selStoOi(String stoId, Integer delGender){
		return orderItemService.shopOiList(null, stoId, delGender);
	}
	/* 店铺信息面板结束 */
	
	/* 个人订单开始 */
	/**
	 * 查看订单的详细信息
	 */
	@GetMapping("/myOrderItem")
	public List<OrderItem> myOrderItem(String oId) {
		return orderItemService.myOrderInfo(oId);
	}
	
	/**
	 * 修改订单信息
	 */
	@PutMapping("/updOiSta")
	public Map<String, String> updOiSta(@RequestBody Map<String, String> data) {
		String newStas[] = {"已签收", "申请退货中1", "申请退货中2", "取消退货", "已退款"};
		String newSta = newStas[Integer.valueOf(data.get("staIndex"))];
				Map<String, String> result = new HashMap<String, String>();
		result.put("itemSta", newSta);
		result.put("orderSta", orderItemService.updStaByShop(data, newSta));
		return result;
	}
	/* 个人订单结束 */
	
	/* 商品管理开始 */
	/**
	 * 删除商品时查询对应商品的相关订单
	 */
	@GetMapping("/selShopOi")
	public List<OrderItem> selShopOi(String shopName, String stoId){
		return orderItemService.shopOiList(shopName, stoId, 1);
	}
	/* 商品管理结束 */
	
	/* 店铺订单管理开始 */
	/**
	 * 查看订单的信息
	 */
	@GetMapping("/orderManageNav")
	public PageBean<OrderItem> stoOrderItem(Integer stoId, Integer pageOn, Integer pageCount, 
			Boolean needSelCon, String uId, String shopName, String oiTime, String oiStatus){
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("uId", uId);
		if(shopName != null) {shopName = "%" + shopName + "%";}
		data.put("shopName", shopName);
		data.put("oiTime", oiTime);
		data.put("oiStatus", oiStatus);
	
		List<OrderItem> stoOrderList = null;
		PageBean<OrderItem> page = orderItemService.getPage(stoId, pageOn, needSelCon, pageCount, data);
		page = Utils.getPage(page, pageOn);
		if(page.getPageCount() != 0) {
			stoOrderList = orderItemService.stoOrderInfo(stoId, pageOn, data);
			page.setList(stoOrderList);
		}
		return page;
	}
	
	/**
	 * 发送商品
	 */
	@PutMapping("/sendBtn")
	public void sendBtn(@RequestBody Map<String, String> data) {
		orderItemService.updStaByShop(data, "已发送");
	}
	
	/**
	 * 退货
	 */
	@PutMapping("/exitBtn")
	public void exitBtn(@RequestBody Map<String, String> data) {
		orderItemService.updStaByShop(data, "已退货");
	}
	
	/**
	 * 删除
	 */
	@PutMapping("/delBtn")
	public PageBean<OrderItem> delBtn(@RequestBody Map<String, String> data) {
		return orderItemService.updHide(data, "N");
	}
	/* 店铺订单管理结束 */
}
