package com.yxsg.bean;

import lombok.Data;

import java.math.BigDecimal;
/**
 * 商品信息
 */
public class Shop {
    private String shopId;			//商品编号
    private String shopName;			//商品名称
    private String shopClass;			//商品类型
    private BigDecimal shopPrice;		//商品单价
    private String shopUnit;			//价格单位
    private String shopTime;			//更新时间
    private Integer shopStock;			//折扣
    private Integer shopDiscount;		//库存
    private String shopBpicture;		//大图
    private String shopSpicture1;		//小图1
    private String shopSpicture2;		//小图2
    private String shopSpicture3;		//小图3
    private Integer stoId;			//所属店铺
    private BigDecimal shopAllPrice;		//累计销量
    
    public Shop() {}
	public Shop(String shopId, String shopName, String shopClass, BigDecimal shopPrice, String shopUnit, String shopTime,
			Integer shopStock, Integer shopDiscount, String shopBpicture, String shopSpicture1, String shopSpicture2,
			String shopSpicture3, Integer stoId, BigDecimal shopAllPrice) {
		this.shopId = shopId;
		this.shopName = shopName;
		this.shopClass = shopClass;
		this.shopPrice = shopPrice;
		this.shopUnit = shopUnit;
		this.shopTime = shopTime;
		this.shopStock = shopStock;
		this.shopDiscount = shopDiscount;
		this.shopBpicture = shopBpicture;
		this.shopSpicture1 = shopSpicture1;
		this.shopSpicture2 = shopSpicture2;
		this.shopSpicture3 = shopSpicture3;
		this.stoId = stoId;
		this.shopAllPrice = shopAllPrice;
	}

	public Integer getStoId() {
		return stoId;
	}

	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", shopName=" + shopName + ", shopClass=" + shopClass + ", shopPrice="
				+ shopPrice + ", shopUnit=" + shopUnit + ", shopTime=" + shopTime + ", shopStock=" + shopStock
				+ ", shopDiscount=" + shopDiscount + ", shopBpicture=" + shopBpicture + ", shopSpicture1="
				+ shopSpicture1 + ", shopSpicture2=" + shopSpicture2 + ", shopSpicture3=" + shopSpicture3 + ", stoId="
				+ stoId + ", shopAllPrice=" + shopAllPrice + "]";
	}
}
