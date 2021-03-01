package com.yxsg.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yxsg.bean.OrderItem;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderItemMapper {
    /* 购物车开始 */
    /**
     * 批量插入详细信息
     */
    int insert(@Param(value = "items") List<OrderItem> orderItems, String oiStatus, String oId);
    /* 购物车结束 */

    /* 个人订单面板开始 */
    /**
     * 根据订单的id查看订单每一件商品的详细信息
     */
    @Select("SELECT shop_name, oi_count, oi_price, oi_status, oi_time, about_sto FROM orderitem WHERE o_id = #{oId}")
    List<OrderItem> selOrItemById(String oId);
	
    /**
     * 根据订单id、购买时间、商品名称查询商品id、订单状态
     */
    Map<String, String> selShopIdSta(@Param(value = "data") Map<String, String> data);
    /* 个人订单面板结束 */
	
    /* 店铺订单管理开始 */
    /**
     * 查看订单的记录数
     */
    Integer selOrderCount(Integer stoId, @Param(value = "order") Map<String, String> data);
	
    /**
     * 根据店铺id筛选条件返回一页订单信息
     */
    List<OrderItem> selStoOrder(Integer stoId, @Param(value = "order") Map<String, String> data, Integer begin, Integer pageSize);
	
    /**
     * 根据商品id、订单号、购买时间修改商品的状态
     */
    void updStaByShop(@Param(value = "updSta") Map<String, String> data, String status);
	
    /**
     * 根据店铺id设置店铺的说明
     */
    @Update("UPDATE orderitem SET about_sto = #{aboutSto} WHERE sto_id = #{stoId}")
    void updAboutByStoId(String stoId, String aboutSto);
	
    /**
     * 根据商品名或店铺id查看未处理的订单数
     */
    Integer selNotHandleCount(String shopName, String stoId);
	
    /**
     * 根据订单id查看已发送、已签收的数量
     */
    Map<String, Object> selCountBySta(String oId);
	
    /**
     * 设置该条订单记录不做店铺订单管理面板中显示
     */
    void updScan(@Param(value = "updScan") Map<String, String> data, String isShow);

    /**
     * 根据商品id或店铺id删除相关的订单信息
     * (只能选择1个，例根据商品id查看时将店铺id设为null，商品id的开头为店铺id + "-")
     */
    void delByShopId(String shopId, String stoId);
    /* 店铺订单管理结束 */
	
    /* 其它开始 */
    /**
     * 删除商品或店铺时查看该商品对应的订单,
     * 删除商品时设置参数2为null
     */
    List<OrderItem> selOiByShopName(String shopName, String stoId);
	
    /**
     * 根据店铺id修改订单是否显示
     */
    @Update("UPDATE orderitem SET s_can = #{isShow} WHERE sto_id = #{stoId}")
    void updScanBySto(String stoId, String isShow);
    /* 其它结束 */
}
