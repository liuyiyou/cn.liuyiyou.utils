package cn.liuyiyou.http;

import cn.liuyiyou.utils.http.HttpClientUtil;

public class HttpUtilsTest {


    public static void main(String[] args) {



        String s2 = HttpClientUtil.get("https://liuyiyou.cn",null);
        System.out.println(s2);
    }
}
