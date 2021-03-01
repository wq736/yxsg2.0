package com.yxsg.mapper;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.yxsg.bean.Order;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

    /* 个人信息面板开始 */
    /**
     * 根据用户id查看该用户的未处理的订单数
     */
    @Select("SELECT COUNT(*) FROM `order` WHERE u_id = #{uId} AND o_status NOT IN(\"已处理\");")
    Integer selNotHandleCount(Integer uId);
	
    /**
     * 根据id删除订单，根据用户id删除时设置订单id为null
     */
    void deleteById(Integer uId, String oId);
    /* 个人信息面板结束 */
	
    /* 购物车面板开始 */
    /**
     * 购物车结算时添加一条记录
     */
    @Insert("INSERT INTO `order` VALUE (#{oId}, #{oTime}, #{oNum}, #{oPrice}, #{oStatus}, #{uId})")
    void insert(String oId, String oTime, Integer oNum, BigDecimal oPrice, String oStatus, Integer uId);
    /* 购物车面板结束 */

    /* 个人订单面板开始 */
    /**
     * 查看用户的订单数量
     */
    Integer selMyOrdCount(Integer uId);
	
    /**
     * 根据用户id查看一页的订单信息
     */
    List<Order> selOrdByUid(Integer uId, Integer begin, Integer pageSize);
    /* 个人订单面板结束 */

    /* 店铺订单管理开始 */
    /**
     * 修改订单详情的状态时，根据"发送商品数"修改该用户订单的状态
     */
    @Update("UPDATE `order` SET o_status = #{status} WHERE o_id= #{oId}")
    void updStaByOid(String oId, String status);
    /* 店铺订单管理结束 */
}
