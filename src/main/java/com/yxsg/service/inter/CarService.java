package com.yxsg.service.inter;

import com.yxsg.bean.Car;
import com.yxsg.bean.PageBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CarService {
    /* 商品详情页开始 */
    /**
     * 将商品添加到购物车，添加成功返回true， 否则返回false
     */
    public String addCar(Map<String, String> carData);
    /* 商品详情页结束 */

    /* 我的---购物车面板开始 */
    /**
     * 返回购物车分页模型
     */
    public PageBean<Car> getPage(Integer pageCount, Integer uId, Boolean needSelCon);
    /**
     * 返回一页对应用户的一页购物车的数据selCarByUname
     */
    public List<Car> myCar(Integer uId, Integer pageOn);
    /**
     * 根据用户id或购物id获取商品id
     */
    public List<String> selShopIdById(Integer uId, String cId);
    /**
     * 返回对应用户购物车中的全部商品数与总金额
     */
    public Map<String, BigDecimal> getNumPrice(Integer uId);
    /**
     * 修改购物车中的商品数量
     */
    public Map<String, String> updCar(Map<String, String> data);
    /**
     * 删除一条购物车中的记录
     * @return
     */
    public PageBean<Car> delAshop(Integer uId, String cId, Integer pageCount, Integer pageOn, Integer pageTotal);
    /**
     * 清空指定用户id的购物车
     */
    public void delMyCar(Integer uId);
    /**
     * 结算
     */
    public void jsMyCar(Integer uId, Integer oNum, BigDecimal oPrice);
    /**
     * 删除已下架的商品
     */
    public void delNotExist(Integer uId);
    /* 我的---购物车面板结束 */
}
