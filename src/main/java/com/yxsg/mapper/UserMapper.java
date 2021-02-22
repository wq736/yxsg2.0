package com.yxsg.mapper;

import com.yxsg.bean.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
	/* 导航条开始 */
	 /**
     * 根据传入的用户添加一条记录，返回影响的行数
     */
	 @Insert("INSERT INTO user (u_name, u_password,  u_email, u_tel, u_time) " +
                "VALUE (#{user.uName}, #{user.uPassword}, #{user.uEmail}, #{user.uTel}, #{user.uTime})")
    int insert(@Param(value = "user") Map<String, String> data);
    
	/**
     * 根据用户名和密码查找用户
     */
	@Select("SELECT u_name FROM user WHERE u_name = #{uName} AND u_password = #{uPassword}")
    String selNameByNamePass(String uName, String uPassword);
    
    /**
     * 根据用户名查看用户的状态
     */
	Map<String, String> selStaGenByName(String uName);
	
	/**
     * 查看用户名是否存在(不包含当前的id)，不存在返回null
	 * uId < 0 时查找全部用户，即注册时使用，修改个人信息时传入当前用户的id
     */
	@Select("SELECT u_name FROM user WHERE u_name = #{uName} AND u_id != #{uId}")
	String selNameByName(String uName, Integer uId);
	/* 导航条结束 */
	
    /* 我的--个人信息开始 */
	/**
     * 根据用户名查询用户信息
     */
	@Select("SELECT * FROM user WHERE u_name = #{uName}")
    User selUserByName(String uName);
    
    /**
     * 根据用户id、店铺id、订单id设置用户的金额
     * 只能选择一个，根据用户id设置时将店铺id设置成null
     */
	void updMoneyById(Integer uId, String stoId, String oId, BigDecimal yue);
    
    /**
     * 修改图片路径
     */
    @Update("UPDATE user SET u_header = #{pathSrc} WHERE u_id = #{uId}")
	void updHeadPic(String pathSrc, String uId);
    
    /**
     * 根据id修改密码
     */
    @Update("UPDATE user SET u_password=#{uPassword} WHERE u_id=#{uId}")
    int updatePassById(String uPassword, Integer uId);
    
    /**
     * 传入user，根据用户的id修改该条记录
     */
    @Update("UPDATE `user` SET u_name = #{uName},u_email = #{uEmail}, " +
                 "u_tel = #{uTel} WHERE u_id = #{uId}")
    int updateByid(User user);
    
    /**
   	 * 根据用户的id删除用户,返回影响的行数
   	 */
    @Delete("DELETE FROM user where u_id = #{uId}")
    int deleteById(Integer uId);
    /* 我的--个人信息结束 */
	
    /* 后台管理---用户管理开始 */
    /**
     * 查询普通用户数
     */
    Integer selUserCount(String uName);
    
    /**
     * 查询全部普通的用户(可以添加用户名作为条件)
     */
    ArrayList<User> selectUserByGender(String uName, Integer begin, Integer size);
    
    /**
     * 修改根据用户的状态
     */
    @Update("UPDATE user SET u_status = #{status} WHERE u_id = #{uId}")
	void updUserStaById(Integer uId, String status);
    /* 后台管理---用户管理结束 */
   
    /* 商品详情页开始 */
    /**
     * 根据用户名查看用户的头像路径
     */
    @Select("SELECT u_header FROM user WHERE u_name=#{uName}")
    String selHeaderByUname(String uName);
    /**
     * 根据用户名查看用户的id
     */
    @Select("SELECT u_id FROM user WHERE u_name=#{uName}")
    Integer selUidByName(String uName);
    /* 商品详情页结束 */
}