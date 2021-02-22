package com.yxsg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxsg.bean.PageBean;
import com.yxsg.exception.NameException;
import com.yxsg.service.inter.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
//import com.yxsg.bean.PageBean;
import com.yxsg.bean.Shop;
import com.yxsg.mapper.ShopMapper;
import com.yxsg.utils.Utils;

@Service
public class ShopServiceImpl implements ShopService {
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ShopMapper shopMapper;
	
	/* 首页开始 */
	/**
	 * 返回首页显示的商品
	 */
	public Map<String, List<Shop>> selIndexShop(String begin, String end) {
		Map<String, List<Shop>> indexShop = new HashMap<String, List<Shop>>();
		List<Shop> seasonShop = shopMapper.selShopBySeason(begin, end);
		List<Shop> hotShop = shopMapper.selShopByAllPrice();
		List<Shop> stockShop = shopMapper.selShopByStock();
		indexShop.put("seasonShop", seasonShop);
		indexShop.put("hotShop", hotShop);
		indexShop.put("stockShop", stockShop);
		return indexShop;
	}
	/* 首页结束 */
	
	/* 购物大厅开始 */
	/**
	 * 返回购物大厅的分页模型
	 */
	public PageBean<Shop> getPage(Integer pageCount, Boolean needSelCon, Map<String, String> data) {
		if(needSelCon) {
			String nameLike = data.get("nameLike");
			if(nameLike != null) {data.put("nameLike", "%" + nameLike + "%");}
			pageCount = shopMapper.selShopCount(data);
		}
		return Utils.getPage(pageCount, Utils.SHOP_SIZE);
	}

	/**
	 * 获取购物大厅的一页数据
	 */
	public List<Shop> selShopList(Integer pageOn, Map<String, String> data) {
		Integer pageSize = Utils.SHOP_SIZE;
		Integer begin = (pageOn - 1) * pageSize;
		String nameLike = data.get("nameLike");
		if(nameLike != null) {data.put("nameLike", "%" + nameLike + "%");}
		return shopMapper.selShop(data, begin, pageSize);
	}
	/* 购物大厅结束 */

	/* 我的---商品管理---店铺管理开始 */
	/**
	 * 返回我的页面店铺对应的商品分页模型
	 */
	public PageBean<Shop> getPage(Integer pageCount, Integer stoId, Boolean needSelCon, Map<String, String> data) {
		if(needSelCon) {
			String shopName = data.get("shopName");
			if(shopName != null) {data.put("shopName", "%" + shopName + "%");}
			pageCount = shopMapper.selShopCountByStoId(stoId, data);
		}
		Integer pageSize = Utils.TAB_SIZE;
		return Utils.getPage(pageCount, pageSize);
	}

	/**
	 * 返回我的页面对应店铺的一页数据
	 */
	public List<Shop> myShop(Integer stoId, Integer pageOn, Map<String, String> data) {
		Integer pageSize = Utils.TAB_SIZE;
		Integer begin = (pageOn - 1) * pageSize;
		String shopName = data.get("shopName");
		if(shopName != null) {data.put("shopName", "%" + shopName + "%");}
		return shopMapper.selShopByStoId(stoId, data, begin, pageSize);
	}

	/**
	 * "我的"页面"商品管理"面板的添加商品
	 */
	public PageBean<Shop> addShop(Map<String, String> data, MultipartFile[] pic, String path) {
		String picUrl = path.replace("\\", "/");

		data.put("shopBpicture", picUrl + Utils.shopPicBname + Utils.getPicName(pic[0]));
		for (int i = 1; i < pic.length; i++) {
			if(pic[i].isEmpty()) {break;}
			data.put("shopSpicture" + i, picUrl + Utils.shopPicSname + "1" + Utils.getPicName(pic[i]));
		}
		shopMapper.insert(data);
		Integer pageCount = Integer.valueOf(data.get("pageCount"));
		pageCount++;
		PageBean<Shop> shopPage = Utils.getPage(pageCount, Utils.TAB_SIZE);
		shopPage = Utils.getPage(shopPage, 1);
		return shopPage;
	}

	/**
	 * "我的"页面"商品管理"面板的修改商品
	 */
	public void updMyShop(Map<String, String> data) {
		shopMapper.updShop(data);
	}

	/**
	 * "我的--商品管理"删除一件商品
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PageBean<Shop> delMyShop(Map<String, String> data) {
		Integer pageCount = Integer.valueOf(data.get("pageCount") + "");
		Integer pageOn = Integer.valueOf(data.get("pageOn") + "");
		Integer pageTotal = Integer.valueOf(data.get("pageTotal") + "");
		String shopId = data.get("shopId");

		Map<String, Object> params = Utils.getLimPageParam(pageCount, pageOn, pageTotal, Utils.TAB_SIZE);
		PageBean<Shop> page = (PageBean<Shop>) params.get("page");
		page = Utils.getPage(page, page.getPageOn());
		shopMapper.deleteByPrimaryKey(shopId);

		List<Shop> shopList = null;
		if(params.get("begin") != null) {
			String stoId = shopId.substring(0, shopId.indexOf("-")) + "%";
			Integer begin = Integer.valueOf(params.get("begin") + "");
			Integer size = Integer.valueOf(params.get("size") + "");
			shopList = shopMapper.selShopDelAfter(stoId, begin, size);
		}

		page.setList(shopList);
		return page;
	}
	/* 我的---商品管理---店铺管理结束 */

	/* 商品详情页开始 */
	/**
	 * 查找商品的详细信息
	 */
	public Map<String, Object> selShopInfo(String shopId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Shop shop = shopMapper.selShopInfoById(shopId);
		result.put("shop", shop);
		result.putAll(shopMapper.selOthInfo(shop.getStoId()));
		return result;
	}
	/* 商品详情页结束 */
}
