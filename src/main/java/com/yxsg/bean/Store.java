package com.yxsg.bean;

import lombok.Data;

public class Store {
    private Integer stoId;		//店铺id
    private String stoName;		//店铺名称
    private String stoAddress;		//地址
    private String stoMainShop;		//区域
    private String stoTime;		//创建时间
    private String stoStatus;		//店铺的状态
    private Integer uId;		//所属用户id
    public Store() {}
	public Store(Integer stoId, String stoName, String stoAddress, String stoMainShop, String stoTime, String stoStatus,
			Integer uId) {
		this.stoId = stoId;
		this.stoName = stoName;
		this.stoAddress = stoAddress;
		this.stoMainShop = stoMainShop;
		this.stoTime = stoTime;
		this.stoStatus = stoStatus;
		this.uId = uId;
	}

	public Integer getStoId() {
		return stoId;
	}

	public void setStoId(Integer stoId) {
		this.stoId = stoId;
	}

	public String getStoName() {
		return stoName;
	}

	public String getStoStatus() {
		return stoStatus;
	}

	public void setStoStatus(String stoStatus) {
		this.stoStatus = stoStatus;
	}

	@Override
	public String toString() {
		return "Store [stoId=" + stoId + ", stoName=" + stoName + ", stoAddress=" + stoAddress + ", stoArea=" + stoMainShop
				+ ", stoTime=" + stoTime + ", stoStatus=" + stoStatus + ", uId=" + uId + "]";
	}
}
