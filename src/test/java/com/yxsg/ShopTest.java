package com.yxsg;

import com.yxsg.bean.Shop;
import com.yxsg.mapper.ShopMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("商品相关功能测试")
public class ShopTest {

    @Autowired
    private ShopMapper shopMapper;

    @DisplayName("测试首页商品显示")
    @Test
    public void testIndexShop(){
        System.out.println(shopMapper);
    }
}
