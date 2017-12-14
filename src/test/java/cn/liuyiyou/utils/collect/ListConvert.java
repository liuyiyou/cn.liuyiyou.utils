/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.utils.ListConvert.java
 * 日期: 2017年12月14日上午9:22:57
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.utils.collect;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;

/**
 * @author liuyiyou.cn
 * @date 2017年12月14日 上午9:22:57
 * @version
 */
public class ListConvert {

	/**
	 * 将List<String> 转换为List<Integer>
	 */
	public List<Integer> listStringToInteger(List<String> list) {
		return list.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	public List<Integer> listStringToInteger2(List<String> list) {
		return Lists.transform(list, item -> Integer.valueOf(item));
	}

	/**
	 * 将List<Integer> 转换为List<String>
	 */
	public List<String> listIntegerToString(List<Integer> list) {
		return Lists.transform(list, Functions.toStringFunction());
	}

	public List<String> listIntegerToString2(List<Integer> list) {
		return list.stream().map(Object::toString).collect(Collectors.toList());
	}

}
