package com.yxsg.bean;

/**
 * 订单的详细信息
 */

import java.math.BigDecimal;

public class OrderItem {
    private Integer uId;		//用户id
    private Integer stoId;		//店铺id
    private String uName;		//用户名
    private String shopName;		//商品名称
    private String oiTime;		//商品添加至购物车的时间
    private Integer oiCount;		//商品数量
    private BigDecimal oiPrice;		//商品价格
    private String oiStatus;		//商品状态
    private String oId;			//订单号
    private String exitWhy;		//退货原因
    private String sCan;		//是否显示，Y显示、N不显示
    private String aboutSto;		//关于店铺
	public OrderItem() {}
	public OrderItem(Integer uId, Integer stoId, String uName, String oiTime, Integer oiCount, BigDecimal oiPrice, String oiStatus,
			String oId, String shopName, String exitWhy, String sCan, String aboutSto) {
		this.uId = uId;
		this.stoId = stoId;
		this.uName = uName;
		this.oiTime = oiTime;
		this.oiCount = oiCount;
		this.oiPrice = oiPrice;
		this.oiStatus = oiStatus;
		this.oId = oId;
		this.shopName = shopName;
		this.exitWhy = exitWhy;
		this.sCan = sCan;
		this.aboutSto = aboutSto;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public Integer getStoId() {
		return stoId;
	}
	public void setStoId(Integer stoId) {
		this.stoId = stoId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getOiTime() {
		return oiTime;
	}
	public void setOiTime(String oiTime) {
		this.oiTime = oiTime;
	}
	public Integer getOiCount() {
		return oiCount;
	}
	public void setOiCount(Integer oiCount) {
		this.oiCount = oiCount;
	}
	public BigDecimal getOiPrice() {
		return oiPrice;
	}
	public void setOiPrice(BigDecimal oiPrice) {
		this.oiPrice = oiPrice;
	}
	public String getOiStatus() {
		return oiStatus;
	}
	public void setOiStatus(String oiStatus) {
		this.oiStatus = oiStatus;
	}
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getExitWhy() {
		return exitWhy;
	}
	public void setExitWhy(String exitWhy) {
		this.exitWhy = exitWhy;
	}
	public String getsCan() {
		return sCan;
	}
	public void setsCan(String sCan) {
		this.sCan = sCan;
	}
	public String getAboutSto() {
		return aboutSto;
	}
	public void setAboutSto(String aboutSto) {
		this.aboutSto = aboutSto;
	}
	@Override
	public String toString() {
		return "OrderItem [uId=" + uId + ", stoId=" + stoId + ", uName=" + uName + ", shopName=" + shopName
				+ ", oiTime=" + oiTime + ", oiCount=" + oiCount + ", oiPrice=" + oiPrice + ", oiStatus=" + oiStatus
				+ ", oId=" + oId + ", exitWhy=" + exitWhy + ", sCan=" + sCan + ", aboutSto=" + aboutSto + "]";
	}
}
