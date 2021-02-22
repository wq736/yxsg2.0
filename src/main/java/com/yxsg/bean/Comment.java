package com.yxsg.bean;

public class Comment {
    private String comTime;
    private String uName;
    private Integer comMark;
    private String comContent;
    private String comPic1;
    private String comPic2;
    private String comPic3;
    private String shopId;
    private String uHeader;
    public Comment() {}
	public Comment(String comTime, String uName, Integer comMark, String comContent, String comPic1, String comPic2,
			String comPic3, String shopId, String uHeader) {
		this.comTime = comTime;
		this.uName = uName;
		this.comMark = comMark;
		this.comContent = comContent;
		this.comPic1 = comPic1;
		this.comPic2 = comPic2;
		this.comPic3 = comPic3;
		this.shopId = shopId;
		this.uHeader = uHeader;
	}
	public String getComTime() {
        return comTime;
    }
    public void setComTime(String comTime) {
        this.comTime = comTime;
    }
    public String getuName() {
        return uName;
    }
    public void setuName(String uName) {
        this.uName = uName;
    }
    public Integer getComMark() {
        return comMark;
    }
    public void setComMark(Integer comMark) {
        this.comMark = comMark;
    }
    public String getComContent() {
        return comContent;
    }
    public void setComContent(String comContent) {
        this.comContent = comContent == null ? null : comContent.trim();
    }
    public String getComPic1() {
        return comPic1;
    }
    public void setComPic1(String comPic1) {
        this.comPic1 = comPic1 == null ? null : comPic1.trim();
    }
    public String getComPic2() {
        return comPic2;
    }
    public void setComPic2(String comPic2) {
        this.comPic2 = comPic2 == null ? null : comPic2.trim();
    }
    public String getComPic3() {
        return comPic3;
    }
    public void setComPic3(String comPic3) {
        this.comPic3 = comPic3 == null ? null : comPic3.trim();
    }
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getuHeader() {
		return uHeader;
	}
	public void setuHeader(String uHeader) {
		this.uHeader = uHeader;
	}
	@Override
	public String toString() {
		return "Comment [comTime=" + comTime + ", uName=" + uName + ", comMark=" + comMark + ", comContent="
				+ comContent + ", comPic1=" + comPic1 + ", comPic2=" + comPic2 + ", comPic3=" + comPic3 + ", shopId="
				+ shopId + ", uHeader=" + uHeader + "]";
	}
}