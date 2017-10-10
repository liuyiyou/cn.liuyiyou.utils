/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.utils.jsoup.JsoupParserUtils.java
 * 日期: 2017年10月6日下午3:14:02
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.common.utils.jsoup;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

/**<p>解析url
 * @author liuyiyou.cn
 * @date 2017年10月6日 下午3:14:02
 * @version
 */
public class JsoupParserUtils {

	private String url = "https://www.zhihu.com/explore#monthly-hot";
	
	private  static String  timeoutMillis = "1000";

	public static String parser(String url) throws IOException {
		Document document = Jsoup.connect(url).execute().parse();
		System.out.println(document.html());
		return null;
	}
	
	@Test
	public void test() throws IOException{
		parser(url);
	}

}
