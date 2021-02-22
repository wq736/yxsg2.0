package com.yxsg.service.impl;

import java.util.List;
import java.util.Map;

import com.yxsg.service.inter.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.yxsg.bean.OrderItem;
import com.yxsg.bean.PageBean;
import com.yxsg.mapper.OrderItemMapper;
import com.yxsg.mapper.OrderMapper;
import com.yxsg.utils.Utils;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderItemMapper orderItemMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderMapper orderMapper;

	/* 个人订单面板开始 */
	/**
	 * 根据订单号查看订单的详细信息
	 */
	public List<OrderItem> myOrderInfo(String oId) {
		return orderItemMapper.selOrItemById(oId);
	}
	/* 个人订单面板结束 */
	
	/* 店铺订单管理面板开始 */
	/**
	 * 返回店铺订单管理的分页模型 
	 */
	public PageBean<OrderItem> getPage(Integer stoId, Integer pageOn, Boolean needSelCon, Integer pageCount, Map<String, String> data) {
		if(needSelCon) {
			pageCount = orderItemMapper.selOrderCount(stoId, data);	
		}
		return Utils.getPage(pageCount, Utils.TAB_SIZE);
	}
	
	/**
	 * 根据店铺id以及筛选条件查询订单信息
	 * @param data 
	 */
	public List<OrderItem> stoOrderInfo(Integer stoId, Integer pageOn, Map<String, String> data) {
		Integer pageSize = Utils.TAB_SIZE;
		Integer begin = (pageOn - 1) * pageSize;
		return orderItemMapper.selStoOrder(stoId, data, begin, pageSize);
	}
		
	/**
	 * 修改订单详情中每一件商品的状态，返回订单(非订单详情对应的商品)的状态
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String updStaByShop(Map<String, String> data, String status) {
		//根据对订单的操作设置订单的不同状态
		String oId = data.get("oId"); 
		Map<String, Object> count = orderItemMapper.selCountBySta(oId);
		Integer orderNum = Integer.valueOf(count.get("orderNum") + "");
		Integer sendCount = Integer.valueOf(count.get("sendCount") + "");
		Integer putExitCount = Integer.valueOf(count.get("putExitCount") + "");
		String sta = count.get("sta") + "";
		if(orderNum == sendCount && putExitCount == 0 && sta != "已全部发送") {
			sta = "已全部发送";
		} else if(orderNum == putExitCount && sta != "已处理"){
			sta = "已处理";
		} else if(sendCount != 0 && putExitCount != 0 && sta != "已全部发送，未全部处理") {			
			sta = "已全部发送，未全部处理";
		} else if(orderNum > sendCount) {
			if(putExitCount == 0 && sta != "未全部处理") {							
				sta = "未全部处理";
			} else if(sta != "未全部发送，未全部处理"){													
				sta = "未全部发送，未全部处理";
			}
		}
		orderMapper.updStaByOid(oId, sta);
		return sta;
	}
	
	/**
	 * 店铺删除订单时将对应的订单设置成为"隐藏"，不删除数据库记录
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public PageBean<OrderItem> updHide(Map<String, String> data, String isShow) {
		Integer pageCount = Integer.valueOf(data.get("pageCount") + "");
		Integer pageOn = Integer.valueOf(data.get("pageOn") + "");
		Integer pageTotal = Integer.valueOf(data.get("pageTotal") + "");
		
		Map<String, Object> param = Utils.getLimPageParam(pageCount, pageOn, pageTotal, Utils.TAB_SIZE);
		PageBean<OrderItem> page = (PageBean<OrderItem>) param.get("page");
		page = Utils.getPage(page, page.getPageOn());
		
		orderItemMapper.updScan(data, isShow);	
		List<OrderItem> stoOrderList = null;
		if(param.get("begin") != null) {
			Integer stoId = Integer.valueOf(data.get("stoId"));
			Integer begin = Integer.valueOf(param.get("begin") + "");
			Integer size = Integer.valueOf(param.get("size") + "");
			stoOrderList = orderItemMapper.selStoOrder(stoId, null, begin, size);
		}		
		page.setList(stoOrderList);
		return page;
	}

	/**
	 * 根据商品id删除相关的订单
	 * 根据店铺删除时设置参数1为null， 参数2为店铺id + "-"
	 */
	public void delByShopId(String shopId, String stoId) {
		orderItemMapper.delByShopId(shopId, stoId);
	}
	/* 店铺订单管理面板结束 */

	/* 其它开始 */
	/**
	 * 删除商品或店铺时查看该商品对应的订单,
	 * 删除商品时设置参数2为null
	 */
	public List<OrderItem> shopOiList(String shopName, String stoId, Integer delGender) {
		return orderItemMapper.selOiByShopName(shopName, stoId);
	}
	/* 其它结束 */
}
