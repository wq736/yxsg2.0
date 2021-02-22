package com.yxsg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxsg.exception.CodeException;
import com.yxsg.exception.NameException;
import com.yxsg.service.inter.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxsg.bean.PageBean;
import com.yxsg.bean.Store;
import com.yxsg.mapper.StoreMapper;
import com.yxsg.utils.Utils;

@Service
public class StoreServiceImpl implements StoreService {
	
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private StoreMapper storeMapper;
	
	/* 我的---店铺信息开始 */
	/**
	 * 根据用户id查询店铺信息
	 */
	public Store selStoreUname(String uName) {
		Store store = storeMapper.selectStoreByUname(uName);
		if(store != null) {
			String status = Utils.getDisableTime(store.getStoStatus());		
			store.setStoStatus(status);;
		}
		return store;
	}
	
	/**
	 * 创建店铺
	 */
	public Map<String, String> intStore(Map<String, String> data, String token) {
		String code = data.get("code"); 
		if (code != null && !code.equalsIgnoreCase(token)) {			
			throw new CodeException("用户注册店铺失败！验证码输入错误！");
		}
		if(storeMapper.selStoName(data.get("stoName"), -1) != null) {
			throw new NameException("用户注册店铺失败！该店铺名已被注册！");
		}
		data.put("stoTime", Utils.getTime());
		storeMapper.insert(data);
		data.clear();
		data.put("success", "注册成功");
		return data;
	}
	
	/**
	 * 根据店铺id修改店铺信息
	 */
	public Map<String, String> updStore(Store store) {
		if(storeMapper.selStoName(store.getStoName(), store.getStoId()) != null) {
			throw new NameException("修改店铺信息失败！该店铺名已被注册！");
		}
		storeMapper.updateByPrimaryKey(store);
		Map<String, String> result = new HashMap<>();
		result.put("success", "修改成功");
		return result;
	}
	
	/**
	 * 删除店铺，参数2,3供aop使用
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delMySto(Integer stoId, Integer uId, Integer delGender) {
		storeMapper.deleteByPrimaryKey(stoId);
	}
	/* 我的---店铺信息结束 */
	
	/* 后台管理---店铺管理开始 */	
	/**
	 * 返回店铺页模型
	 */
	public PageBean<Store> getPage(String stoName, Integer pageCount, Boolean needSelCon) {
		if(needSelCon || stoName != null) {
			pageCount = storeMapper.selStoreCount(stoName);
		}
		return Utils.getPage(pageCount, Utils.TAB_SIZE);
	}
	
	/**
	 * 返回一页店铺信息
	 */
	public List<Store> selStoreAll(Integer pageOn, String stoName) {
		Integer pageSize = Utils.TAB_SIZE;
		Integer begin = (pageOn - 1) * pageSize;
		return storeMapper.selectStorePage(stoName, begin, pageSize);
	}
	
	/**
	 * 修改店铺的状态
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String updStoSta(Integer stoId, Integer staCode) {
		String status = Utils.getStatus(staCode);
		storeMapper.updStoStaById(stoId, status);
		return status;
	}
	/* 后台管理---店铺管理结束 */
}
