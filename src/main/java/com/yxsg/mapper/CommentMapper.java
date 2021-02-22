package com.yxsg.mapper;

import com.yxsg.bean.Comment;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {
	
    /* 商品详情页开始 */
	/**
	 * 上传评论
	 */
	@Insert("INSERT INTO comment VALUE(#{data.comTime}, #{data.uId}, #{data.comMark}, #{data.comContent}, " +
			   "#{data.comPicName1}, #{data.comPicName2}, #{data.comPicName3}, #{data.shopId})")
	int insert(@Param(value = "data") Map<String, String> data);
	
	/**
	 * 查看评论数
	 */
	@Select("SELECT COUNT(*) FROM comment WHERE shop_id = #{shopId}")
	Integer selComCountByShop(String shopId);	

	/**
	 * 根据商品id或店铺删除对应评论
	 * (只能选择1个，例根据商品id查看时将店铺id设为null，商品id的开头为店铺id + "-")
	 */
	void delComByShop(String shopId, String stoId);

	/**
	 * 查看某一件商品的某一页评论
	 */
	List<Comment> selectAllByShop(String shopId, Integer begin, Integer size);
	/* 商品详情页结束 */

}