/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.utils.ListUtils.java
 * 日期: 2017年12月14日下午8:56:30
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.utils.collect;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

/**
 * @author liuyiyou.cn
 * @date 2017年12月14日 下午8:56:30
 * @version
 */
public abstract class ListUtils {

	/**
	 * 将数组转换成列表
	 * 
	 * @param t
	 * @return
	 */
	public static <T> List<T> arrayToList(T... t) {
		return Arrays.asList(t);
	}

	/**
	 * 将List<String>数组转换成List<Intger>数组
	 * 
	 * @param list
	 * @return
	 */
	public static List<Integer> listStringToInteger(List<String> list) {
		return list.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	/**
	 * 将List<Integer> 转换为List<String>
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> listIntegerToString(List<Integer> list) {
		return list.stream().map(Object::toString).collect(Collectors.toList());
	}

	/**
	 * 将Set转换成为List
	 * @param t
	 * @return
	 */
	public static <T> List<T> setToList(Set<T> t) {
		return Lists.newArrayList(t);
	}

}
