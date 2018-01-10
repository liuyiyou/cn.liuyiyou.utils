package cn.liuyiyou.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static cn.liuyiyou.utils.string.StringUtil.isEmptyString;
import static cn.liuyiyou.utils.string.StringUtil.isNotEmptyString;


/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class WebUtil {


    public static Map<String, String> URLRequest(String strUrlParam) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;

        strUrlParam = getUrlParams(strUrlParam);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (isNotEmptyString(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }


    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String getUrlParams(String strURL) {
        if (isEmptyString(strURL)) {
            return strURL;
        }
        String[] arrSplit = strURL.split("[?]");
        if (arrSplit.length > 1) {
            if (arrSplit[1] != null) {
                return arrSplit[1];
            }
        }
        return strURL;
    }
}
