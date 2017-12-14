/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.model.Comment.java
 * 日期: 2017年10月13日下午5:31:38
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.model;

import java.util.Date;

/**
 * @author liuyiyou.cn
 * @date  2017年10月13日 下午5:31:38 
 * @version  
 */
public class Prod {

	private int id;
	private String prodName;
	private double price;
	private boolean status;
	private Date createDate;
	private Date updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
}
