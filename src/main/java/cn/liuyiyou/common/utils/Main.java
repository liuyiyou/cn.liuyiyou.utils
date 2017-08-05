/** 
 * Project Name:cn.liuyiyou.common 
 * File Name:Main.java 
 * Package Name:cn.liuyiyou.common.utils 
 * Date:2017年7月25日下午4:34:12 
 * Copyright (c) 2017, www.daojia.com All Rights Reserved. 
 * 
 */
package cn.liuyiyou.common.utils;

import java.util.Date;

/**
 * 名称: Main <br/>
 * 
 * @author liuyiyou.cn
 * @date 2017年7月25日
 * @version 6.0.0
 */
public class Main {

	public static void main(String[] args) {
		User user = new User(1, true, "liuyiyiu", new Date());
		System.out.println(user);
		System.out.println(JsonUtil.object2Json(user));
	}
}

class User {
	private int id;
	private boolean man;
	private String name;
	private Date birthday;

	/**
	 * 
	 */
	public User() {
		super();
	}

	/**
	 * @param id
	 * @param man
	 * @param name
	 * @param birthday
	 */
	public User(int id, boolean man, String name, Date birthday) {
		super();
		this.id = id;
		this.man = man;
		this.name = name;
		this.birthday = birthday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMan() {
		return man;
	}

	public void setMan(boolean man) {
		this.man = man;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", man=");
		builder.append(man);
		builder.append(", name=");
		builder.append(name);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append("]");
		return builder.toString();
	}

}
