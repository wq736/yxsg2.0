package com.yxsg.controller;
/**
 * 处理评论相关的请求
 */

import java.util.List;
import java.util.Map;
import com.yxsg.service.inter.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.yxsg.bean.Comment;
import com.yxsg.bean.PageBean;
import com.yxsg.utils.Utils;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	/* 商品详情页评论区开始 */
	/**
	 * 添加评论
	 */
	@PostMapping("/addCommontBtn")
	public Map<String, Object> addCommont(@RequestParam Map<String, String> data, MultipartFile comPic[]) {
		Integer uId = commentService.getUserId(data.get("uName"));
		String shopId = data.get("shopId");
		String stoId = shopId.substring(0, shopId.indexOf("-"));
		String path = "comment" + "\\" + stoId + "\\" + shopId + "\\" + uId + "\\";
		
		data.put("uId", uId + "");
		String comTime = Utils.getTime();
		data.put("comTime", comTime);
		return commentService.addComment(data, comPic, path);
	}
	
	/**
	 * 返回一页评论
	 */
	@GetMapping("/commPage")
	public PageBean<Comment> comPage(Integer pageOn, Integer pageCount, String shopId, Boolean needSelCon){
		List<Comment> comList = null;
		PageBean<Comment> comPage = commentService.getPage(shopId, pageCount, needSelCon);
		comPage = Utils.getPage(comPage, pageOn);
		if(comPage.getPageCount() != 0) {
			comList = commentService.selComList(shopId, pageOn);
			comPage.setList(comList);
		}
		return comPage;
	}
	/* 商品详情页评论区结束 */
}
