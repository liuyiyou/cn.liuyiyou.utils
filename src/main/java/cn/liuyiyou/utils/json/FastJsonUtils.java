package cn.liuyiyou.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public abstract class FastJsonUtils {

    /**
     * 将Java对象转换为JSON类型
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toJSONString(T t) {
        return JSON.toJSONString(t);
    }


    public static <T> T toJava(String jsonStr, Class<T> tClass) {
        T t = JSONObject.parseObject(jsonStr, tClass);
        return t;
    }
}
