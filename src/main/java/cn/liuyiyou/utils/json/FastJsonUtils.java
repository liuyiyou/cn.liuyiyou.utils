package cn.liuyiyou.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public abstract class FastJsonUtils {

    public <T> void object2Json(T object){
        JSONObject.toJSONString(object);
    }



    class User{

    }
}
