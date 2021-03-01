package com.yxsg.mapper;

import com.yxsg.bean.Shop;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

@Mapper
public interface ShopMapper {	
    /* 首页相关开始 */
    /**
     * 根据季节查询商品
     */
    List<Shop> selShopBySeason(String begin, String end);
	
    /**
     * 根据商品的总销售额返回前10件产品
     */
    List<Shop> selShopByAllPrice();

    /**
     * 根据时间查询有折扣的前10件商品
     */
    List<Shop> selShopByStock();
    /* "首页"相关结束 */
	
    /* "购物大厅"相关开始 */
    /**
     * 根据商品名称或商品类型返回商品数量，都为空表示返回全部的商品数量
     */
    Integer selShopCount(@Param(value = "pageNeed") Map<String, String> data);

    /**
     * 根据商品名称或商品类型返回一页数据
     */
    List<Shop> selShop(@Param(value = "pageNeed") Map<String, String>data, Integer begin, Integer pageSize);
    /* "购物大厅"相关结束 */
	
    /* 我的---个人信息开始 */
    /**
     * 根据商品id删除商品
     */
    @Delete("DELETE FROM shop WHERE shop_id = #{shopId}")
    int deleteByPrimaryKey(String shopId);
    /* 我的---个人信息结束 */
	
    /* "我的"页面---"我的店铺"---"商品管理"开始 */
    /**
     * 查看当前店铺中是否存在当前要添加的商品名称
     */
    @Select("SELECT shop_name FROM shop WHERE sto_id = #{stoId} AND shop_name = #{shopName}")
    String selNameByName(Integer stoId, String shopName);
    
    /**
     * 添加商品
     */
     @Insert("INSERT INTO shop VALUE(#{data.shopId}, #{data.shopName}, #{data.shopClass}, #{data.shopPrice}, " +
	     "#{data.shopUnit}, #{data.shopTime}, #{data.shopStock}, #{data.shopDiscount}, #{data.shopBpicture}, " +
             "#{data.shopSpicture1}, #{data.shopSpicture2}, #{data.shopSpicture3}, #{data.shopAllPrice}, #{data.stoId})")
    int insert(@Param(value = "data") Map<String, String> data);
	
    /**
     * 查询店铺对应的商品数量
     */
    Integer selShopCountByStoId(Integer stoId, @Param(value = "stoShop") Map<String, String> data);
   
    /**
     * 根据页码查询对应店铺id的商品
     */
    List<Shop> selShopByStoId(Integer stoId, @Param(value = "stoShop") Map<String, String> data, Integer begin, Integer size);
	
    /**
     * 修改商品信息
     */
    void updShop(@Param(value = "data") Map<String, String> data);
    
    /**
     * 返回店铺删除的商品后返回的商品
     */
    List<Shop> selShopDelAfter(String stoId, Integer begin, Integer size);
	
    /**
     * 根据店铺id删除商品
     */
    @Delete("DELETE FROM shop WHERE sto_id = #{stoId}")
    void delByStoId(String stoId);
    /* "我的"页面---"我的店铺"---"商品管理"结束 */
    
    /* 商品列表页开始 */
    /**
     * 根据商品id查询商品的详细信息、发送地址以及联系电话
     */
    @Select("SELECT * FROM shop WHERE shop_id = #{shopId}")
    Shop selShopInfoById(String shopId);
    /* 商品列表页结束 */
	
    /* 商品详情页开始 */
    /**
     * 根据商品所在的店铺id查询店铺名称，地址，联系方式
     */
    @Select("SELECT s.sto_name stoName, s.sto_address stoAddress, u.u_tel uTel " +
            "FROM store s, `user` u WHERE s.u_id = u.u_id AND s.sto_id = #{stoId}")
    Map<String, String> selOthInfo(Integer stoId);
	
    /**
     * 根据商品id查询当前商品的库存和所属店铺，没有商品id时根据购物号查看商品名称
     */
    Map<String, Integer> selShopDiscount(String shopId, String cId);
	
    /**
     * 根据商品名称减少该商品的数量
     */
    @Update("UPDATE shop SET shop_discount = shop_discount - #{shopDiscount} WHERE shop_id = #{shopId}")
    void updDiscountByName(String shopId, Integer shopDiscount);
    /* 商品详情页结束 */

    /* 购物车开始 */
    /**
     * 查看商品id是否存在，返回一个map集合,key为商品id,
     * 商品id存在时value也为商品id,否则value为"isNull"
     */
    Map<String, String> selIdsByIds(@Param("ids") List<String> ids);
	
    /**
     * 购物车中的商品数量完后修改对应商品的库存数量
     */
    void updSumByCid(@Param("dis") Map<String, String> data);
	
    /**
     * 清空购物车后增加对应商品的库存
     */
    void updDisByIds(@Param("ids") List<String> shopIdList, Integer uId);
    /* 购物车结束 */

    /* 订单项开始 */
    /**
     * 修改对应商品id的销量
     */
    @Update("UPDATE shop SET shop_allprice = shop_allprice + #{addPrice} WHERE shop_id = #{shopId}")
    void updAllPrice(String shopId, BigDecimal addPrice);
    /* 订单项结束 */
}
