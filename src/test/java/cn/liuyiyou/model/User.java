/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.utils.User.java
 * 日期: 2017年10月13日下午5:31:17
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.model;

import java.util.Date;
import java.util.List;

/**
 * @author liuyiyou.cn
 * @date  2017年10月13日 下午5:31:17 
 * @version  
 */
public class User {

	private int id;
	private String userName;
	private String mobile;
	private Date createDate;
	private Date updateDate;
	
	private List<Order> orders;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", orders=");
		builder.append(orders);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
