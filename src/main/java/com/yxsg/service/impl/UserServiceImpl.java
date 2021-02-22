package com.yxsg.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxsg.bean.PageBean;
import com.yxsg.exception.CodeException;
import com.yxsg.exception.NameException;
import com.yxsg.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.yxsg.bean.PageBean;
import com.yxsg.bean.User;
import com.yxsg.mapper.UserMapper;
import com.yxsg.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private UserMapper userMapper;
	
	/* 页面加载开始 */
	/**
	 * 根据用户名查看用户的状态、等级
	 */
	public Map<String, String> selStaGenBy(String uName) {
		return userMapper.selStaGenByName(uName);
	}
	/* 页面加载结束 */
	
	/*导航条相关开始 */
	/**
	 * 注册用户
	 */
	public Map<String, String> register(Map<String, String> data, String token){
		String code = data.get("code");	
		if(code != null && !code.equalsIgnoreCase(token)) {
			throw new CodeException("用户注册账号失败！验证码输入错误！");
		}
		if(userMapper.selNameByName(data.get("uName"), -1) != null){
			throw new NameException("用户注册账号失败！该用户名已被注册！");
		}
		data.put("uTime", Utils.getTime());
		userMapper.insert(data);
		data.clear();
		data.put("success", "注册成功");
		return data;
	}
	
	/**
	 * 登录
	 */
	public Map<String, String> login(String uName, String uPassword, String code, String token) {
		Map<String, String> result = new HashMap<String, String>();
		if(code != null && code.equalsIgnoreCase(token)) {
			String name = userMapper.selNameByNamePass(uName, uPassword);
			if(name != null){
				result.put("uName", uName);
				result.putAll(userMapper.selStaGenByName(uName));
			} else {
				throw new NameException("用户登录失败！用户名或密码输入错误！");
			}
		} else {
			throw new CodeException("用户登录失败！验证码输入错误！");
		}
		return result;
	}
	/*导航条相关结束 */
	
	/* 我的---个人信息开始 */
	/**
	 * 返回个人信息
	 */
	public User getUser(String uName) {
		User user = userMapper.selUserByName(uName);
		String status = Utils.getDisableTime(user.getuStatus());
		user.setuStatus(status);
		return user;
	}
	
	/**
	 * 修改图片路径
	 */
	public void saveHeadPic(String pathSrc, Integer uId) {
		userMapper.updHeadPic(pathSrc, uId + "");
	}
	
	/**
	 * 修改密码
	 */
	public void savePass(String uPassword, Integer uId) {
		userMapper.updatePassById(uPassword, uId);
	}

	/**
	 * 修改个人信息
	 */
	public Map<String, String> saveUser(User user) {
		if(userMapper.selNameByName(user.getuName(), user.getuId()) != null) {
			throw new NameException("用户注册账号失败！该用户名已被注册！");
		}
		userMapper.updateByid(user);
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "修改成功");
		return result;
	}
	
	/**
	 * 删除账户
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delUser(Integer uId) {
		userMapper.deleteById(uId);
	}
	/* 我的---个人信息结束 */
	
	/* 后台管理---用户管理开始 */
	/**
	 * 返回分页模型
	 */
	public PageBean<User> getPage(String uName, Integer pageCount, Boolean needSelCon) {
		if(needSelCon || uName != "") {
			pageCount = userMapper.selUserCount(uName);
		}
		return Utils.getPage(pageCount, Utils.TAB_SIZE);
	}
	
	/**
	 * 返回一页数据
	 */
	public List<User> selAllPullUser(Integer pageOn, String uName) {
		Integer pageSize = Utils.TAB_SIZE;
		Integer begin = (pageOn - 1) * pageSize;
		return userMapper.selectUserByGender(uName, begin, pageSize);
	}
	/* 后台管理---用户管理结束 */

	/**
	 * 修改用户的状态
	 */
	public String updUserSta(Integer uId, Integer staCode) {
		String status = Utils.getStatus(staCode);
		userMapper.updUserStaById(uId, status);
		return status;
	}

	/**
	 * 充值提现
	 */
	public BigDecimal addReduceYue(Map<String, String> data) {
		BigDecimal yue = new BigDecimal(0);	
		if("addYue".equals(data.get("type"))) {			
			yue = new BigDecimal(data.get("ye"));
		} else {
			yue = new BigDecimal("-" + data.get("ye"));			
		}
		Integer uId = Integer.valueOf(data.get("uId"));
		userMapper.updMoneyById(uId, null, null, yue);
		return yue;
	}
}
