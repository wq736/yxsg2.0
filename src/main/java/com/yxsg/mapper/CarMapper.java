package com.yxsg.mapper;

import com.yxsg.bean.Car;
import com.yxsg.bean.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CarMapper {
	/* 商品详情页开始 */
	/**
     * 将商品添加到购物车
     */
	@Insert("INSERT INTO car VALUE (#{data.cId}, #{data.uId}, #{data.shopId}, " +
			   "#{data.shopSum}, #{data.shopPrice}, #{data.stoId}, #{data.cTime})")
    int insert(@Param(value = "data") Map<String, String> carData);

    /**
     * 根据商品Id修改商品数量
     */
    @Update("UPDATE car SET shop_sum = #{newNum} WHERE shop_id = #{shopId}")
	void updSumById(String shopId, Integer newNum);

	/**
	 * 根据用户id商品名称和商品价格查看该商品在购物车中的数量
	 */
	@Select("SELECT shop_sum FROM car WHERE u_id = #{uId} AND shop_id = #{shopId} AND shop_price = #{shopPrice}")
	Integer selNumByIdPrice(Integer uId, String shopId, BigDecimal shopPrice);
	/* 商品详情页结束 */

    /* 我的---购物车开始 */
    /**
     * 查看对应用户的购物车记录数
     */
    @Select("SELECT COUNT(*) FROM car WHERE u_id=#{uId}")
	Integer selCountByUname(Integer uId);

	/**
	 * 返回一页对应用户的购物车数据
	 */
	List<Car> selCarByUname(Integer uId, Integer begin, Integer pageSize);

	/**
	 * 返回对应用户购物车中的全部商品数与总金额
	 */
	@Select("SELECT SUM(shop_sum) allSum,  SUM(shop_sum * shop_price) allPrice FROM car WHERE u_id=#{uId}")
	Map<String, BigDecimal> selSunAndPriceByUname(Integer uId);

	/**
     * 根据用户(购物)id返回购物车中的商品id
     */
	List<String> selShopIdById(Integer uId, String cId);

	/**
     * 根据购物号修改对应商品的数量
     */
	@Update("UPDATE car SET shop_sum = #{num.newShopNum} WHERE c_id = #{num.cId}")
	void updSumByCid(@Param(value = "num") Map<String, String> data);

	 /**
     * 根据用户id删除购物车信息
     */
	void delCarByUid(Integer uId, String cId);

	/**
	 * 查询购物车中全部的信息并存放到订单的详细信息中
	 */
	List<OrderItem> selectAll(Integer uId);

	/**
	 * 删除用户购物车中已下架的商品
	 */
	void delShopByNotExit(Integer uId);
    /* 我的---购物车结束 */
}