package com.yxsg.bean;

import java.util.List;

public class PageBean<E> {
	private Integer pageOn;					//当前页码
	private Integer pageTotal;				//总页码
	private Integer pageCount;				//总记录数
	private Integer pageSize;				//一页显示几条数据
	private Integer pageBegin;				//分页条起始页码
	private Integer pageEnd;				//分页条结束页码
	private List<E> list;					//页面内容
	public PageBean() {}
	public PageBean(Integer pageOn, Integer pageTotal, Integer pageCount, Integer pageSize) {
		this.pageOn = pageOn;
		this.pageTotal = pageTotal;
		this.pageCount = pageCount;
		this.pageSize = pageSize;
	}
	public void setPageOn(Integer pageOn) {
		this.pageOn = pageOn;
	}
	public Integer getPageOn() {
		return pageOn;
	}
	public Integer getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageBegin() {
		return pageBegin;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setPageBegin(Integer pageBegin) {
		this.pageBegin = pageBegin;
	}
	public Integer getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageBean [pageOn=" + pageOn + ", pageTotal=" + pageTotal + ", pageCount=" + pageCount + 
				", pageBegin=" + pageBegin + ", pageEnd=" + pageEnd + "]";
	}
}
