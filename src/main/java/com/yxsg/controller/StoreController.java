package com.yxsg.controller;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yxsg.service.inter.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yxsg.bean.PageBean;
import com.yxsg.bean.Store;
import com.yxsg.utils.Utils;

@RestController
public class StoreController {
	
	@Autowired
	private StoreService storeService;
	
	/* 我的---店铺信息开始 */
	/**
	 * 查看店铺信息
	 */
	@GetMapping(value = "/sto")
	public Map<String, Object> myStoInfo(String uName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Store store = storeService.selStoreUname(uName);
		long now = System.currentTimeMillis();
		result.put("store", store);
		result.put("now", now);
		return result;
	}
	
	/**
	 * 创建店铺
	 */
	@PostMapping(value = "/regStore")
	public Map<String, String> storeInfo(@RequestBody Map<String, String> data, HttpServletRequest req) {
		String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
		return storeService.intStore(data, token);
	}
	
	/**
	 * 修改店铺信息
	 */
	@PutMapping(value = "/saveStore")
	public Map<String, String> saveStore(@RequestBody Store store) {
		return storeService.updStore(store);
	}
	
	/**
	 * 删除店铺
	 */
	@DeleteMapping(value = "/delMySto")
	public String delMySto(Integer stoId, Integer uId, Integer delGender) {
		storeService.delMySto(stoId, uId, delGender);
		return "success";
	}
	/* 我的---店铺信息结束 */
	
	/* 后台管理---店铺管理开始 */
	/**
	 * 返回当前页的店铺信息
	 */
	@GetMapping("/storeManageNav")
	public PageBean<Store> userPage(Integer pageOn, String stoName, Integer pageCount, Boolean needSelCon){
		List<Store> storeList = null;
		PageBean<Store> storePage = storeService.getPage(stoName, pageCount, needSelCon);
		storePage = Utils.getPage(storePage, pageOn);
		if(storePage.getPageCount() != 0) {
			storeList = storeService.selStoreAll(pageOn, stoName);
			storePage.setList(storeList);
		}
		return storePage;
	}
	
	/**
	 * 修改店铺状态
	 */
	@PutMapping(value = "/updStoSta")
	public List<String> updUserSta(@RequestBody Map<String, Integer> data) {
		String storeSta = storeService.updStoSta(data.get("id"), data.get("staCode"));
		List<String> result = new ArrayList<String>();
		result.add(storeSta);
		return result;
	}
	/* 后台管理---店铺管理结束 */
}
