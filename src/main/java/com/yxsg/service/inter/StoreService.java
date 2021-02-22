package com.yxsg.service.inter;

import com.yxsg.bean.PageBean;
import com.yxsg.bean.Store;
import java.util.List;
import java.util.Map;

public interface StoreService {
    /* 我的---店铺信息开始 */
    /**
     * 根据用户id查询店铺信息
     */
    public Store selStoreUname(String uName);
    /**
     * 创建店铺
     */
    public Map<String, String> intStore(Map<String, String> data, String token);
    /**
     * 根据店铺id修改店铺信息
     */
    public Map<String, String> updStore(Store store);
    /**
     * 删除店铺，参数2,3供aop使用
     */
    public void delMySto(Integer stoId, Integer uId, Integer delGender);
    /* 我的---店铺信息结束 */

    /* 后台管理---店铺管理开始 */
    /**
     * 返回店铺页模型
     */
    public PageBean<Store> getPage(String stoName, Integer pageCount, Boolean needSelCon);
    /**
     * 返回一页店铺信息
     */
    public List<Store> selStoreAll(Integer pageOn, String stoName);
    /**
     * 修改店铺的状态
     */
    public String updStoSta(Integer stoId, Integer staCode);
    /* 后台管理---店铺管理结束 */
}
