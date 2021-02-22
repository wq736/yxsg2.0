package com.yxsg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.yxsg.bean.PageBean;
import com.yxsg.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;
import com.yxsg.bean.User;
import com.yxsg.utils.Utils;

/**
 * 用于处理用户模块的请求
 */
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/* 页面加载开始 */
	/**
	 * 页面加载时根据用户的名称获取用户的状态、等级
	 */
	@GetMapping("/updUserGenSta")
	public Map<String, String> updUserGenSta(String uName){
		return userService.selStaGenBy(uName);
	}
	/* 页面加载结束 */
	
	/* 导航栏相关开始 */
	/**
	 * 注册用户
	 */
	@PostMapping("/register")
	public Map<String, String> register(@RequestBody Map<String, String> data, HttpServletRequest req) {
		String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);		
		return userService.register(data, token);
	}
	
	/**
	 * 用户登录
	 */
	@GetMapping("/login")
	public Map<String, String> login(String n, String p, String code, HttpServletRequest req){
		String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
		p = new StringBuffer(p).reverse() + "";
		return userService.login(n, p, code, token);
	}
	/* 导航栏相关结束 */
	
	/* 我的---个人信息开始 */
	/**
	 * 我的面板获取个人信息
	 */
	@GetMapping(value = "/my")
	public Map<String, Object> my(String uName){
		Map<String, Object> result = new HashMap<String, Object>();
		long now = System.currentTimeMillis();
		User user = userService.getUser(uName);
		result.put("user", user);
		result.put("now", now);
		return result;
	}
	
	/**
	 * 充值提现
	 */
	@PutMapping(value = "updMoney")
	public String addReduceYue(@RequestBody Map<String, String> data) {
		return userService.addReduceYue(data) + "";
	}
	
	/**
	 * 修改密码
	 */
	@PutMapping(value = "/savePass")
	public void savePass(@RequestBody Map<String, String> data) {
		Integer uId = Integer.valueOf(data.get("u"));
		String uPassword = data.get("p");
		uPassword = new StringBuffer(uPassword).reverse() + "";
		userService.savePass(uPassword, uId);
	}
	
	/**
	 * 修改用户信息
	 */
	@PutMapping("/saveUser")
	public Map<String, String> saveUser(@RequestBody User user){
		return userService.saveUser(user);
	}
	
	/**
	 * 修改头像
	 */
	@PostMapping(value = "/saveHead")
	public String saveHead(Integer uId, MultipartFile updateHead) {
		//上传图片
		String pathSrc = Utils.up( "head", updateHead,   uId + "\\");
		userService.saveHeadPic(pathSrc, uId);
		return pathSrc;
	}
	
	/**
	 * 删除用户
	 */
	@DeleteMapping(value = "/delMy")
	public void delMy(Integer uId) {
		userService.delUser(uId);
	}
	/* 我的---个人信息结束 */
	
	/* 后台管理---用户管理开始 */
	/**
	 * 返回对应页的用户信息
	 */
	@GetMapping("/userManageNav")
	public PageBean<User> userPage(Integer pageOn, Integer pageCount, Boolean needSelCon, String uName){
		List<User> userList =null;
		PageBean<User> userPage = userService.getPage(uName, pageCount, needSelCon);
		userPage = Utils.getPage(userPage, pageOn);
		if(userPage.getPageCount() != 0) {
			userList = userService.selAllPullUser(pageOn, uName);
			userPage.setList(userList);
		}
		return userPage;
	}
	
	/**
	 * 修改用户状态
	 */
	@PutMapping("/updUserSta")
	public List<String> updUserSta(@RequestBody Map<String, Integer> data) {
		String userSta = userService.updUserSta(data.get("id"), data.get("staCode"));
		List<String> result = new ArrayList<String>();
		result.add(userSta);
		return result;
	}
	/* 后台管理---用户管理结束 */
}
