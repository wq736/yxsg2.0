package com.yxsg.service.inter;

import com.yxsg.bean.PageBean;
import com.yxsg.bean.Shop;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ShopService {
    /* 首页开始 */
    /**
     * 返回首页显示的商品
     */
    public Map<String, List<Shop>> selIndexShop(String begin, String end);
    /* 首页结束 */

    /* 购物大厅开始 */
    /**
     * 返回购物大厅的分页模型
     */
    public PageBean<Shop> getPage(Integer pageCount, Boolean needSelCon, Map<String, String> data);
    /**
     * 获取购物大厅的一页数据
     */
    public List<Shop> selShopList(Integer pageOn, Map<String, String> data);
    /* 购物大厅结束 */

    /* 我的---商品管理---店铺管理开始 */
    /**
     * 返回我的页面店铺对应的商品分页模型
     */
    public PageBean<Shop> getPage(Integer pageCount, Integer stoId, Boolean needSelCon, Map<String, String> data);
    /**
     * 返回我的页面对应店铺的一页数据
     */
    public List<Shop> myShop(Integer stoId, Integer pageOn, Map<String, String> data);
    /**
     * "我的"页面"商品管理"面板的添加商品
     */
    public PageBean<Shop> addShop(Map<String, String> data, MultipartFile[] pic, String path);
    /**
     * "我的"页面"商品管理"面板的修改商品
     */
    public void updMyShop(Map<String, String> data);
    /**
     * "我的--商品管理"删除一件商品
     */
    public PageBean<Shop> delMyShop(Map<String, String> data);
    /* 我的---商品管理---店铺管理结束 */

    /* 商品详情页开始 */
    /**
     * 查找商品的详细信息
     */
    public Map<String, Object> selShopInfo(String shopId);
    /* 商品详情页结束 */
}
