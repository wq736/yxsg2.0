package com.yxsg.service.inter;

import com.yxsg.bean.PageBean;
import com.yxsg.bean.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {
    /* 页面加载开始 */
    /**
     * 根据用户名查看用户的状态、等级
     */
    public Map<String, String> selStaGenBy(String uName);
    /* 页面加载结束 */

    /*导航条相关开始 */
    /**
     * 注册用户
     */
    public Map<String, String> register(Map<String, String> data, String token);
    /**
     * 登录
     */
    public Map<String, String> login(String uName, String uPassword, String code, String token);
    /*导航条相关结束 */

    /* 我的---个人信息开始 */
    /**
     * 返回个人信息
     */
    public User getUser(String uName);
    /**
     * 修改图片路径
     */
    public void saveHeadPic(String pathSrc, Integer uId);
    /**
     * 修改密码
     */
    public void savePass(String uPassword, Integer uId);
    /**
     * 修改个人信息
     */
    public Map<String, String> saveUser(User user);
    /**
     * 删除账户
     */
    public void delUser(Integer uId);
    /* 我的---个人信息结束 */

    /* 后台管理---用户管理开始 */
    /**
     * 返回分页模型
     */
    public PageBean<User> getPage(String uName, Integer pageCount, Boolean needSelCon);
    /**
     * 返回一页数据
     */
    public List<User> selAllPullUser(Integer pageOn, String uName);
    /* 后台管理---用户管理结束 */

    /**
     * 修改用户的状态
     */
    public String updUserSta(Integer uId, Integer staCode);
    /**
     * 充值提现
     */
    public BigDecimal addReduceYue(Map<String, String> data);
}
