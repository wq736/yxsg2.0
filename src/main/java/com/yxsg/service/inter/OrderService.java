package com.yxsg.service.inter;

import com.yxsg.bean.Order;
import com.yxsg.bean.PageBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /*个人订单开始*/
    /**
     * 返回订单分页模型
     */
    public PageBean<Order> getPage(Integer pageCount, Integer uId, Boolean needSelCon);
    /**
     * 查看订单
     */
    public List<Order> selMyOrder(Integer uId, Integer pageOn);
    /**
     *	用户删除订单(删除后返回需要的一条/一页记录和分页条)
     */
    public PageBean<Order> delUserOrder(Map<String, String> data);
    /*个人订单结束*/
}
