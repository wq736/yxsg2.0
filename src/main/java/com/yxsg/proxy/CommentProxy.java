package com.yxsg.proxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.yxsg.utils.Utils;

/**
 * 评论区的aop
 */
@Aspect
@Component
public class CommentProxy {

	/**
	 * 添加评论时上传对应的图片
	 */
	@AfterReturning(value = "execution(* com.yxsg.service.impl.CommentServiceImpl.addComment(..))")
	public void addCommentAfter(JoinPoint point) {
		String path = point.getArgs()[2] + "";
		MultipartFile pic[] = (MultipartFile[]) point.getArgs()[1];
		for (int i = 0; i < pic.length; i++) {
			if(pic[i].isEmpty()) {break;}
			Utils.up("comPicName" + (i + 1), pic[i], path);
		}
	}
}
