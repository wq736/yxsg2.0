package com.yxsg.service.impl;

import java.util.List;
import java.util.Map;

import com.yxsg.service.inter.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yxsg.bean.Order;
import com.yxsg.bean.PageBean;
import com.yxsg.mapper.OrderMapper;
import com.yxsg.utils.Utils;

@Service
public class OrderServiceImpl implements OrderService {
	
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderMapper orderMapper;

	/*个人订单开始*/
	/**
	 * 返回订单分页模型
	 */
	public PageBean<Order> getPage(Integer pageCount, Integer uId, Boolean needSelCon) {
		if(needSelCon) {
			pageCount = orderMapper.selMyOrdCount(uId);
		}
		return Utils.getPage(pageCount, Utils.TAB_SIZE);
	}
	
	/**
	 * 查看订单
	 */
	public List<Order> selMyOrder(Integer uId, Integer pageOn) {
		Integer pageSize = Utils.TAB_SIZE;
		Integer begin = (pageOn - 1) * pageSize;
		return orderMapper.selOrdByUid(uId, begin, pageSize);
	}
	
	/**
	 *	用户删除订单(删除后返回需要的一条/一页记录和分页条) 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public PageBean<Order> delUserOrder(Map<String, String> data) {
		Integer pageCount = Integer.valueOf(data.get("pageCount"));
		Integer pageOn = Integer.valueOf(data.get("pageOn"));
		Integer pageTotal = Integer.valueOf(data.get("pageTotal"));
		
		Map<String, Object> param = Utils.getLimPageParam(pageCount, pageOn, pageTotal, Utils.TAB_SIZE);
		PageBean<Order> page = (PageBean<Order>) param.get("page");
		page = Utils.getPage(page, page.getPageOn());

		orderMapper.deleteById(null, data.get("oId"));		
		List<Order> myOrderList = null;
		if(param.get("begin") != null) {
			Integer uId = Integer.valueOf(data.get("uId") + "");
			Integer begin = Integer.valueOf(param.get("begin") + "");
			Integer pageSize = Integer.valueOf(param.get("size") + "");
			myOrderList = orderMapper.selOrdByUid(uId, begin, pageSize);
		}
		page.setList(myOrderList);
		return page;
	}
	/*个人订单结束*/	
}
