package com.yxsg.bean;

/**
 * 订单
 */

import java.math.BigDecimal;

public class Order {
    private String oId;			//订单号
    private String oTime;		//创建时间
    private Integer oNum;		//商品总数
    private BigDecimal oPrice;	//商品总价
    private String oStatus;		//订单状态
    private Integer uId;		//所属用户id
	public Order() {}
	public Order(String oId, String oTime, Integer oNum, BigDecimal oPrice, String oStatus, Integer uId) {
		this.oId = oId;
		this.oTime = oTime;
		this.oNum = oNum;
		this.oPrice = oPrice;
		this.oStatus = oStatus;
		this.uId = uId;
	}
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public String getoTime() {
		return oTime;
	}
	public void setoTime(String oTime) {
		this.oTime = oTime;
	}
	public Integer getoNum() {
		return oNum;
	}
	public void setoNum(Integer oNum) {
		this.oNum = oNum;
	}
	public BigDecimal getoPrice() {
		return oPrice;
	}
	public void setoPrice(BigDecimal oPrice) {
		this.oPrice = oPrice;
	}
	public String getoStatus() {
		return oStatus;
	}
	public void setoStatus(String oStatus) {
		this.oStatus = oStatus;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	@Override
	public String toString() {
		return "Order [oId=" + oId + ", oTime=" + oTime + ", oNum=" + oNum + ", oPrice=" + oPrice + ", oStatus="
				+ oStatus + ", uId=" + uId + "]";
	}
}