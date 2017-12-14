/**
 * 所属项目:cn.liuyiyou.common
 * 文件名称:cn.liuyiyou.common.utils.jsoup.JsoupParserUtils.java
 * 日期: 2017年10月6日下午3:14:02
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved.
 *
 */
package cn.liuyiyou.utils.jsoup;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 解析url
 * 
 * @author liuyiyou.cn
 * @date 2017年10月6日 下午3:14:02
 * @version
 */
public class JsoupParserUtils {

	private static Logger logger = LoggerFactory.getLogger(JsoupParserUtils.class);

	private static String mobileSearch = "http://www.ip138.com:8080/search.asp?action=mobile&mobile=";

	/**
	 * 解析手机号归属地
	 * 
	 * @param mobile
	 * @return
	 * @throws IOException
	 */
	public static String parseMobileOwership(String mobile) {
		String provAndCity = "未知";
		try {
			Document document = Jsoup.connect(mobileSearch + mobile).execute().parse();
			Element tr = document.select("table").last().select("tr").first();
			if (tr.text().contains("++ ip138.com查询结果 ++")) {
				provAndCity = tr.nextElementSibling().nextElementSibling().text().replaceAll(" ", "-")
						.replaceAll("卡号归属地 ", "").trim();
				Element e = new Element(provAndCity);
			} else {
				provAndCity = tr.text();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return provAndCity;
	}

	@Test
	public void test() throws IOException {
		parseMobileOwership(mobileSearch);
	}

}
