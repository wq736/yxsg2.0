package com.yxsg.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxsg.service.inter.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yxsg.bean.Comment;
import com.yxsg.bean.PageBean;
import com.yxsg.mapper.CommentMapper;
import com.yxsg.mapper.UserMapper;
import com.yxsg.utils.Utils;

@Service
public class CommentServiceImpl implements CommentService {
	
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private UserMapper userMapper;
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private CommentMapper commentMapper;

	/* 商品详情页评论区开始 */
	/**
	 * 返回评论区分页模型 
	 */
	public PageBean<Comment> getPage(String shopId, Integer pageCount, Boolean needSelCon) {
		if(needSelCon) {
			pageCount = commentMapper.selComCountByShop(shopId);	
		}
		return Utils.getPage(pageCount, Utils.Com_Size);
	}

	/**
	 * 获取用户名对应的id
	 */
	public Integer getUserId(String uName) {
		return userMapper.selUidByName(uName);
	}
	
	/**
	 * 添加评论
	 */
	public Map<String, Object> addComment(Map<String, String> data, MultipartFile comPic[], String path) {
		String comPicUrl = path.replace("\\", "/");
		for (int i = 0; i < comPic.length; i++) {
			if(comPic[i].isEmpty()) {break;}
			data.put("comPicName" + (i + 1), comPicUrl + "comPicName" + (i + 1) + Utils.getPicName(comPic[i]));
		}
		commentMapper.insert(data);
		String uHead = userMapper.selHeaderByUname(data.get("uName"));
		Integer pageCount = Integer.valueOf(data.get("pageCount")) + 1;
		PageBean<Comment> page = Utils.getPage(pageCount, Utils.Com_Size);
		page = Utils.getPage(page, 1);
		Comment comment = new Comment(data.get("comTime"), data.get("uName"), Integer.valueOf(data.get("comMark")), data.get("comContent"), 
									  data.get("comPicName1"), data.get("comPicName2"), data.get("comPicName3"), data.get("shopId"), uHead);	
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("comm", comment);
		result.put("page", page);
		return result;
	}
	
	/**
	 * 查看某件商品对应的评论
	 */
	public List<Comment> selComList(String shopId, Integer pageOn) {
		Integer comSize = Utils.Com_Size;
		Integer begin = (pageOn - 1) * comSize;
		List<Comment> comList = commentMapper.selectAllByShop(shopId, begin, comSize);
		return comList;
	}
	
	/* 商品详情页评论区结束 */
}
