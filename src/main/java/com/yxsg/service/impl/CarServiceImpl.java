package com.yxsg.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxsg.exception.NumberException;
import com.yxsg.service.inter.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxsg.bean.Car;
import com.yxsg.bean.OrderItem;
import com.yxsg.bean.PageBean;
import com.yxsg.mapper.CarMapper;
import com.yxsg.mapper.OrderItemMapper;
import com.yxsg.mapper.OrderMapper;
import com.yxsg.mapper.ShopMapper;
import com.yxsg.mapper.UserMapper;
import com.yxsg.utils.Utils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class CarServiceImpl implements CarService {
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private CarMapper carMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ShopMapper shopMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private UserMapper userMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderMapper orderMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private OrderItemMapper orderItemMapper;

	/* 商品详情页开始 */
	/**
	 * 将商品添加到购物车，添加成功返回true， 否则返回false
	 */
	@Transactional(rollbackFor = Exception.class)
	public String addCar(Map<String, String> carData) {
		Map<String, Integer> result = shopMapper.selShopDiscount(carData.get("shopId"), null);
		Integer newNum = Integer.valueOf(carData.get("shopSum"));

		if(newNum > result.get("shop_discount")) {return null;}

		Integer uId = userMapper.selUidByName(carData.get("uName"));
		Integer oldNum = carMapper.selNumByIdPrice(uId, carData.get("shopId"), new BigDecimal(carData.get("shopPrice")));
		if(oldNum != null) {
			carMapper.updSumById(carData.get("shopId"), oldNum + newNum);
		} else {
			long time = System.currentTimeMillis();
			carData.put("uId", uId + "");
			carData.put("cId", carData.get("uId") + "-" + time);
			carData.put("stoId", result.get("sto_id") + "");
			carData.put("cTime", Utils.getTime());
			carMapper.insert(carData);
		}
		shopMapper.updDiscountByName(carData.get("shopId"), Integer.valueOf(carData.get("shopSum")));
		return "success";
	}
	/* 商品详情页结束 */

	/* 我的---购物车面板开始 */
	/**
	 * 返回购物车分页模型
	 */
	public PageBean<Car> getPage(Integer pageCount, Integer uId, Boolean needSelCon) {
		if(needSelCon) {
			pageCount = carMapper.selCountByUname(uId);
		}
		return Utils.getPage(pageCount, Utils.TAB_SIZE);
	}

	/**
	 * 返回一页对应用户的一页购物车的数据selCarByUname
	 */
	public List<Car> myCar(Integer uId, Integer pageOn) {
		Integer pageSize = Utils.TAB_SIZE;
		Integer begin = (pageOn - 1) * pageSize;
		return carMapper.selCarByUname(uId, begin, pageSize);
	}


	/**
	 * 根据用户id或购物id获取商品id
	 */
	public List<String> selShopIdById(Integer uId, String cId) {
		return carMapper.selShopIdById(uId, cId);
	}


	/**
	 * 返回对应用户购物车中的全部商品数与总金额
	 */
	public Map<String, BigDecimal> getNumPrice(Integer uId) {
		return carMapper.selSunAndPriceByUname(uId);
	}

	/**
	 * 修改购物车中的商品数量
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> updCar(Map<String, String> data) {
		Map<String, Integer> result = shopMapper.selShopDiscount(null ,data.get("cId"));
		if(Integer.valueOf(data.get("newShopNum")) > result.get("shop_discount")) {
			throw new NumberException("修改购物车中商品的数量失败！该数量大于库存数量！");
		}
		carMapper.updSumByCid(data);
		shopMapper.updSumByCid(data);
		Map<String, String> res = new HashMap<>();
		res.put("success", "修改购物车中商品的数量成功！");
		return res;
	}

	/**
	 * 删除一条购物车中的记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public PageBean<Car> delAshop(Integer uId, String cId, Integer pageCount, Integer pageOn, Integer pageTotal) {
		Map<String, Object> param = Utils.getLimPageParam(pageCount, pageOn, pageTotal, Utils.TAB_SIZE);
		PageBean<Car> page = (PageBean<Car>) param.get("page");
		page = Utils.getPage(page, page.getPageOn());

		List<String> shopIdList = new ArrayList<String>();
		String shopId = carMapper.selShopIdById(null, cId).get(0);
		shopIdList.add(shopId);
		shopMapper.updDisByIds(shopIdList, uId);
		carMapper.delCarByUid(null, cId);

		List<Car> carList = null;
		if(param.get("begin") != null) {
			Integer begin = Integer.valueOf(param.get("begin") + "");
			Integer size = Integer.valueOf(param.get("size") + "");
			carList = carMapper.selCarByUname(uId, begin, size);
		}
		page.setList(carList);
		return page;
	}

	/**
	 * 清空指定用户id的购物车
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delMyCar(Integer uId) {
		List<String> shopIdList = carMapper.selShopIdById(uId, null);
		shopMapper.updDisByIds(shopIdList, uId);
		carMapper.delCarByUid(uId, null);
	}

	/**
	 * 结算
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void jsMyCar(Integer uId, Integer oNum, BigDecimal oPrice) {
		String oId = "o-" + System.currentTimeMillis();
		String oTime = Utils.getTime();
		orderMapper.insert(oId, oTime, oNum, oPrice, "未发送", uId);
		List<OrderItem> orderItems = carMapper.selectAll(uId);
		orderItemMapper.insert(orderItems, "未发送", oId);
	}

	/**
	 * 删除已下架的商品
	 */
	public void delNotExist(Integer uId) {
		carMapper.delShopByNotExit(uId);
	}
	/* 我的---购物车面板结束 */
}
