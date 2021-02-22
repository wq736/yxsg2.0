package com.yxsg.bean;

import lombok.Data;

import java.math.BigDecimal;

public class Car {
    private String cId;				//购物车id
    private String uName;			//用户名
    private String shopName;		//商品名
    private Integer shopSum;		//商品数量
    private BigDecimal shopPrice;	//商品价格
    private Integer stoId;			//商品所属店铺
    private Integer shopDiscount;	//商品剩余库存
    private String cTime;			//添加时间
    public Car() {}
	public Car(String cId, String uName, String shopName, Integer shopSum, BigDecimal shopPrice, Integer stoId, Integer shopDiscount, String cTime) {
		this.cId = cId;
		this.uName = uName;
		this.shopName = shopName;
		this.shopSum = shopSum;
		this.shopPrice = shopPrice;
		this.stoId = stoId;
		this.shopDiscount = shopDiscount;
		this.cTime = cTime;
	}
	public String getcId() {return cId;}
    public void setcId(String cId) {this.cId = cId;}
    public String getuName() {return uName;}
    public void setuName(String uName) {this.uName = uName;}
    public String getShopName() {return shopName;}
    public void setShopName(String shopName) {this.shopName = shopName;}
    public Integer getShopSum() {return shopSum;}
    public void setShopSum(Integer shopSum) {this.shopSum = shopSum;}
    public BigDecimal getShopPrice() {return shopPrice;}
    public void setShopPrice(BigDecimal shopPrice) {this.shopPrice = shopPrice;}
    public Integer getStoId() {return stoId;}
    public void setStoId(Integer stoId) {this.stoId = stoId;}
	public Integer getShopDiscount() {return shopDiscount;}
	public void setShopDiscount(Integer shopDiscount) {this.shopDiscount = shopDiscount;}
	public String getcTime() {return cTime;}
	public void setcTime(String cTime) {this.cTime = cTime;}
	@Override
	public String toString() {
		return "Car [cId=" + cId + ", uName=" + uName + ", shopName=" + shopName + ", shopSum=" + shopSum
				+ ", shopPrice=" + shopPrice + ", stoId=" + stoId + ", shopDiscount=" + shopDiscount + ", cTime="
				+ cTime + "]";
	}
}