package com.yxsg.service.inter;

import com.yxsg.bean.OrderItem;
import com.yxsg.bean.PageBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OrderItemService {
    /* 个人订单面板开始 */
    /**
     * 根据订单号查看订单的详细信息
     */
    public List<OrderItem> myOrderInfo(String oId);
    /* 个人订单面板结束 */

    /* 店铺订单管理面板开始 */
    /**
     * 返回店铺订单管理的分页模型
     */
    public PageBean<OrderItem> getPage(Integer stoId, Integer pageOn, Boolean needSelCon, Integer pageCount, Map<String, String> data);
    /**
     * 根据店铺id以及筛选条件查询订单信息
     * @param data
     */
    public List<OrderItem> stoOrderInfo(Integer stoId, Integer pageOn, Map<String, String> data);
    /**
     * 修改订单详情中每一件商品的状态，返回订单(非订单详情对应的商品)的状态
     */
    public String updStaByShop(Map<String, String> data, String status);
    /**
     * 店铺删除订单时将对应的订单设置成为"隐藏"，不删除数据库记录
     */
    public PageBean<OrderItem> updHide(Map<String, String> data, String isShow);
    /**
     * 根据商品id删除相关的订单
     * 根据店铺删除时设置参数1为null， 参数2为店铺id + "-"
     */
    public void delByShopId(String shopId, String stoId);
    /* 店铺订单管理面板结束 */

    /* 其它开始 */
    /**
     * 删除商品或店铺时查看该商品对应的订单,
     * 删除商品时设置参数2为null
     */
    public List<OrderItem> shopOiList(String shopName, String stoId, Integer delGender);
    /* 其它结束 */
}
