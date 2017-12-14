/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.json.JsonUtilTest.java
 * 日期: 2017年10月13日下午5:37:15
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.utils.json;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.liuyiyou.model.Order;
import cn.liuyiyou.model.User;
import cn.liuyiyou.utils.json.JsonUtil;

/**
 * @author liuyiyou.cn
 * @date 2017年10月13日 下午5:37:15
 * @version
 */
public class JsonUtilTest {

	@Test
	public void test() {
		User user = new User() {
			{
				setCreateDate(new Date());
				setId(1);
				setMobile("18600774073");
				setUserName("刘易友");
				setOrders(Arrays.asList(new Order() {
					{
						setId(1001);
						setOrderNo("order_10001");
						setCreateDate(new Date());
						setUpdateDate(new Date());
					}
				}, new Order() {
					{
						setId(1002);
						setOrderNo("order_10002");
						setCreateDate(new Date());
						setUpdateDate(new Date());
					}
				}));
			}
		};
		
		
		
		/*
		 * {
    "id":1,
    "userName":"刘易友"
    "mobile":"18600774073",
    "createDate":"2017-10-14 00:10:47",
    "orders":[
        {"createDate":"2017-10-14 00:10:47","id":1001,"orderNo":"order_10001","updateDate":"2017-10-14 00:10:47"},
        {"createDate":"2017-10-14 00:10:47","id":1002,"orderNo":"order_10002","updateDate":"2017-10-14 00:10:47"}
    ]
}
----------------
{
    "id":1,
    "userName":"刘易友",
    "mobile":"18600774073",
    "createDate":"2017-10-14 00:10:47",
    "updateDate":null,
    "orders":[
        {"id":1001,"orderNo":"order_10001","prods":null,"createDate":"2017-10-14 00:10:47","updateDate":"2017-10-14 00:10:47"},
        {"id":1002,"orderNo":"order_10002","prods":null,"createDate":"2017-10-14 00:10:47","updateDate":"2017-10-14 00:10:47"}
    ]
}

		 */
		
		
		
		String aliJsonStr = JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss", SerializerFeature.EMPTY);
		String jacksonStr = JsonUtil.object2Json(user);
		System.out.println(aliJsonStr);
		System.out.println("----------------");
		System.out.println(jacksonStr);

		
		
		User user2 = JSON.parseObject(aliJsonStr, User.class);
		User user3 = JSON.toJavaObject(JSONObject.parseObject(aliJsonStr), User.class);
		User user4 = JsonUtil.json2Object(aliJsonStr, User.class);
		System.out.println(user2);
		System.out.println(user3);
		System.out.println(user4);

	}
}
