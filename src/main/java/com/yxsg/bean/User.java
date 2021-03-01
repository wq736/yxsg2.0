package com.yxsg.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户对象：一条记录对应一个用户对象
 */
public class User {
    
    private Integer uId;		//用户id
    private String uName;		//用户名
    private String uPassword;		//登录密码
    private String uEmail;		//邮箱
    private String uTel;		//联系电话
    private String uTime;		//创建时间
    private String uStatus;		//用户的状态
    private String uGender;		//用户等级，1表示后台管理员，2表示普通用户
    private String uHeader;		//用户头像
    private BigDecimal uMoney;		//用户金额
	
    public User() {}
    public User(Integer uId, String uName, String uPassword, String uEmail, String uTel, String uTime, String uStatus, 
    			String uGender, String uHeader, BigDecimal uMoney) {
		this.uId = uId;
		this.uName = uName;
		this.uPassword = uPassword;
		this.uEmail = uEmail;
		this.uTel = uTel;
		this.uTime = uTime;
		this.uStatus = uStatus;
		this.uGender = uGender;
		this.uHeader = uHeader;
		this.uMoney = uMoney;
    }

	public Integer getuId() { return uId; }
	public void setuId(Integer uId) { this.uId = uId; }
	public String getuName() { return uName; }
	public void setuName(String uName) {this.uName = uName; }
	public String getuPassword() { return uPassword; }
	public void setuPassword(String uPassword) {this.uPassword = uPassword; }
	public String getuEmail() { return uEmail; }
	public void setuEmail(String uEmail) { this.uEmail = uEmail; }
	public String getuTel() { return uTel; }
	public void setuTel(String uTel) { this.uTel = uTel; }
	public String getuTime() { return uTime; }
	public void setuTime(String uTime) { this.uTime = uTime; }
	public String getuStatus() { return uStatus; }
	public void setuStatus(String uStatus) { this.uStatus = uStatus; }
	public String getuGender() { return uGender; }
	public void setuGender(String uGender) { this.uGender = uGender; }
	public String getuHeader() { return uHeader; }
	public void setuHeader(String uHeader) { this.uHeader = uHeader; }
	public BigDecimal getuMoney() { return uMoney; }
	public void setuMoney(BigDecimal uMoney) { this.uMoney = uMoney; }

	@Override
	public String toString() {
		return "User [uId=" + uId + ", uName=" + uName + ", uPassword=" + uPassword + ", uEmail=" + uEmail + ", uTel="
				+ uTel + ", uTime=" + uTime + ", uStatus=" + uStatus + ", uGender=" + uGender + ", uHeader=" + uHeader
				+ ", uMoney=" + uMoney + "]";
	}
}
