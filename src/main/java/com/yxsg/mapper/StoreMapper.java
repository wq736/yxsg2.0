package com.yxsg.mapper;

import com.yxsg.bean.Store;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.*;

@Mapper
public interface StoreMapper {
	
	/* 我的---店铺信息开始 */
	 /**
     * 查看当前店铺名是否已经存在(不包括当前店铺名)
	 * stoId < 0 时表示查询全部店铺，即注册时使用
     */
	 @Select("SELECT sto_name FROM store WHERE sto_name = #{stoName} AND sto_id != #{stoId}")
	String selStoName(String stoName, Integer stoId);
	
	 /**
     * 传入店铺，创建店铺
     */
	 @Insert("INSERT INTO store (sto_name, sto_address, sto_mainShop, sto_time, u_id)" +
			    "VALUE (#{store.stoName}, #{store.stoAddress}, #{store.stoMainShop}, " +
			    "#{store.stoTime}, #{store.uId})")
    int insert(@Param(value = "store") Map<String, String> data);

    /**
     * 根据店铺id修改店铺
     */
    @Update("UPDATE store SET sto_name = #{stoName}, sto_address = #{stoAddress}, " +
				 "sto_mainShop = #{stoMainShop} WHERE sto_id = #{stoId}")
    int updateByPrimaryKey(Store store);
    
    /**
     * 根据用户名查询店铺信息
     */
    @Select("SELECT * FROM store WHERE u_id = (" +
				"SELECT u_id FROM `user` WHERE u_name = #{uName}" +
			")")
    Store selectStoreByUname(String uName);
	/* 我的---店铺信息结束 */
	
    /* 后台管理---店铺管理开始 */
    /**
     * 查询店铺的数量
     */
	Integer selStoreCount(String stoName);

	/**
	 * 根据页码查询店铺信息
	 */
	ArrayList<Store> selectStorePage(String stoName, Integer begin, Integer pageSize);
	
	/**
     * 根据店铺的id修改店铺的状态
     */
	@Update("update store set sto_status = #{status} where sto_id = #{stoId}")
	void updStoStaById(Integer stoId, String status);
    /* 后台管理---店铺管理结束 */
    
	/**
	 * 根据店铺id删除店铺
	 */
	@Delete("DELETE FROM store WHERE sto_id = #{stoId}")
    int deleteByPrimaryKey(Integer stoId);
}