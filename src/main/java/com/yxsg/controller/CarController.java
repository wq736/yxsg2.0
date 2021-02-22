package com.yxsg.controller;
/**
 * 处理购物车相关
 */

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yxsg.service.inter.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yxsg.bean.Car;
import com.yxsg.bean.PageBean;
import com.yxsg.utils.Utils;

@RestController
public class CarController {

	@Autowired
	private CarService carService;

	/* 商品详情页开始 */
	/**
	 * 添加到购物车
	 */
	@PostMapping("/addCar")
	public String addCar(@RequestBody Map<String, String> carData) {
		return carService.addCar(carData);
	}
	/* 商品详情页结束 */

	/* 我的---购物车开始 */
	/**
	 * 查看购物车的商品数量以及总金额
	 */
	@GetMapping(value = "/countAndPrice")
	public Map<String, BigDecimal> countAndPrice(Integer uId){
		return carService.getNumPrice(uId);
	}

	/**
	 * 返回一页购物车的数据
	 */
	@GetMapping(value = "/carManageNav")
	public PageBean<Car> carPage(Integer pageCount, Integer uId, Integer pageOn, Boolean needSelCon){
		List<Car> car = null;
		PageBean<Car> carPage = carService.getPage(pageCount, uId, needSelCon);
		carPage = Utils.getPage(carPage, pageOn);
		if(carPage.getPageCount() != 0) {
			car = carService.myCar(uId, pageOn);
			carPage.setList(car);
		}
		return carPage;
	}

	/**
	 * 修改购物车中的商品数量
	 */
	@PutMapping(value = "/saveShopNum")
	public Map<String, String> updCar(@RequestBody Map<String, String> data) {
		return carService.updCar(data);
	}
	/**
	 * 删除购物车中的一件商品
	 */
	@DeleteMapping(value = "/delACar")
	public PageBean<Car> delACar(@RequestBody Map<String, String> data){
		Integer uId = Integer.valueOf(data.get("uId"));
		String cId = data.get("cId");
		Integer pageCount = Integer.valueOf(data.get("pageCount"));
		Integer pageOn = Integer.valueOf(data.get("pageOn"));
		Integer pageTotal = Integer.valueOf(data.get("pageTotal"));
		return carService.delAshop(uId, cId, pageCount, pageOn, pageTotal);
	}

	/**
	 * 清空购物车
	 */
	@DeleteMapping(value = "/carEmpty")
	public void carEmpty(@RequestBody Map<String, String> data) {
		Integer uId = Integer.valueOf(data.get("uId"));
		carService.delMyCar(uId);
	}

	/**
	 * 结算
	 */
	@DeleteMapping("/carEnd")
	public void carEnd(@RequestBody Map<String, Object> data) {
		Integer uId = Integer.valueOf(data.get("uId") + "");
		Integer oNum = Integer.valueOf(data.get("oNum") + "");
		BigDecimal oPrice = new BigDecimal(data.get("oPrice") + "");
		carService.jsMyCar(uId, oNum, oPrice);
	}

	/**
	 * 删除以下架的商品
	 */
	@DeleteMapping("/delNotExistShop")
	public void delNotExistShop(Integer uId) {
		carService.delNotExist(uId);
	}
	/* 我的---购物车结束 */
}
