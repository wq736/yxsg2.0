package com.yxsg.service.inter;

import com.yxsg.bean.Comment;
import com.yxsg.bean.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /* 商品详情页评论区开始 */
    /**
     * 返回评论区分页模型
     */
    public PageBean<Comment> getPage(String shopId, Integer pageCount, Boolean needSelCon);
    /**
     * 获取用户名对应的id
     */
    public Integer getUserId(String uName);
    /**
     * 添加评论
     */
    public Map<String, Object> addComment(Map<String, String> data, MultipartFile comPic[], String path);
    /**
     * 查看某件商品对应的评论
     */
    public List<Comment> selComList(String shopId, Integer pageOn);
    /* 商品详情页评论区结束 */
}
