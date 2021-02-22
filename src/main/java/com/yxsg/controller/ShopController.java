package com.yxsg.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yxsg.bean.PageBean;
import com.yxsg.service.inter.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.yxsg.bean.Shop;
import com.yxsg.utils.Utils;

@RestController
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	/**
	 * 首页商品
	 */
	@GetMapping("/indexShop")
	public Map<String, List<Shop>> indexShop(){
		String begin = Utils.getBegin();
		String end = Utils.getEnd();
		return shopService.selIndexShop(begin, end);
	}
	
	/**
	 * 购物大厅商品
	 * 1.选中网站导航条中的购物大厅时返回当前页(第1页)全部的商品以及分页模型
	 * 2.选中商品类型导航条中的商品类型时返回对应的商品列表页以及分页模型
	 * 3.单击分页条中的页码时返回对应的数据
	 */
	@GetMapping(value = "/shopAllNav")
	public PageBean<Shop> shopPage(Integer pageOn, Integer pageCount, Boolean needSelCon, String shopNameLike, String shopClass, String indexMore){
		Map<String, String> data = new HashMap<String, String>();
		data.put("nameLike", shopNameLike);
		data.put("shopClass", shopClass);
		data.put("indexMore", indexMore);
		if("seasonShop".equals(indexMore)) {
			data.put("beginDate", Utils.getBegin());
			data.put("endDate", Utils.getEnd());
		}
		List<Shop> shopList = null;
		PageBean<Shop> page = shopService.getPage(pageCount, needSelCon, data);
		page = Utils.getPage(page, pageOn);
		if(page.getPageCount() != 0) {
			shopList = shopService.selShopList(pageOn, data);
			page.setList(shopList);
		}
		return page;
	}

	/* "我的---我的店铺---商品管理" */
	/**
	 * 根据店铺id查询商品
	 */
	@GetMapping("/shopManageNav")
	public PageBean<Shop> shopManageNav(Integer stoId, Integer pageOn, Integer pageCount, Boolean needSelCon,
											String shopId, String shopName, String shopTime) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("shopId", shopId);
		data.put("shopName", shopName);
		data.put("shopTime", shopTime);

		List<Shop> shopList = null;
		PageBean<Shop> shopPage = shopService.getPage(pageCount, stoId, needSelCon, data);
		shopPage = Utils.getPage(shopPage, pageOn);
		if(shopPage.getPageCount() != 0) {
			shopList = shopService.myShop(stoId, pageOn, data);
			shopPage.setList(shopList);
		}
		return shopPage;
	}

	/**
	 * 添加商品
	 */
	@PostMapping("/saveShop")
	public PageBean<Shop> saveShop(@RequestParam Map<String, String> data, MultipartFile pic[]){
		//设置图片的路径
		long t = System.currentTimeMillis();
		String uId = data.get("uId");
		String path = uId + "\\" + "store" + "\\" + t + "\\";

		String shopId = data.get("stoId") + "-" + t; 	//设置商品id
		String shopTime = Utils.getTime();				//设置添加时间

		data.put("shopId", shopId);
		data.put("shopTime", shopTime);
		data.put("shopAllPrice", "0");
		PageBean<Shop> shopPage = shopService.addShop(data, pic, path);
		return shopPage;
	}

	/**
	 * 修改商品
	 */
	@PutMapping("/updShop")
	public void updShop(@RequestBody Map<String, String> data) {
		for (Map.Entry<String, String> entry : data.entrySet()) {
			System.out.println(entry.getKey() + ":" + "".equals(entry.getValue()));
		}

		shopService.updMyShop(data);
	}

	/**
	 * 删除商品
	 */
	@DeleteMapping("/delShop")
	public PageBean<Shop> delShop(@RequestBody Map<String, String> data, HttpServletRequest request) {
//		String picUrl = request.getServletContext().getRealPath("static") + "\\img\\";
		return shopService.delMyShop(data);

	}
	/* "我的---我的店铺---商品管理" */

	/* 商品详情页开始 */
	/**
	 * 显示商品详细信息
	 */
	@GetMapping("/shopInfo")
	public Map<String, Object> shopInfo(String shopId) {
		return shopService.selShopInfo(shopId);
	}
	/* 商品详情页结束 */
}
