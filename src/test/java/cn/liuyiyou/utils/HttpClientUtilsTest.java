package cn.liuyiyou.utils;

import cn.liuyiyou.utils.http.HttpClientUtil;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class HttpClientUtilsTest {

    public static void main(String[] args) {
        String html = HttpClientUtil.get("http://liuyiyou.cn/",null);
        System.out.println(html);
    }
}
