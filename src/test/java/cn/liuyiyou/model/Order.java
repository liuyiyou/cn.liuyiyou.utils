/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.utils.Teacher.java
 * 日期: 2017年10月13日下午5:30:51
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.model;

import java.util.Date;
import java.util.List;

/**
 * @author liuyiyou.cn
 * @date  2017年10月13日 下午5:30:51 
 * @version  
 */
public class Order {
	
	private int id;
	private String orderNo;
	private List<Prod> prods;
	private Date createDate;
	private Date updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<Prod> getProds() {
		return prods;
	}
	public void setProds(List<Prod> prods) {
		this.prods = prods;
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
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", prods=");
		builder.append(prods);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
