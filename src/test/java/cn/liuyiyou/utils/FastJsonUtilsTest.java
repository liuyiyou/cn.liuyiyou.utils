package cn.liuyiyou.utils;

import cn.liuyiyou.beans.Address;
import cn.liuyiyou.beans.User;
import cn.liuyiyou.utils.collect.ListUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

import java.util.Date;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class FastJsonUtilsTest {


    @Test
    public void object2Json(){
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        User user = new User();
        user.setId(1);
        user.setName("lyy");
        user.setBirthDay(new Date());
        user.setHobby(ListUtils.arrayToList("pingpang", "game"));
        Address address = new Address();
        address.setCity("changde");
        address.setProv("hunan");
        user.setAddress(address);
        System.out.println(JSON.toJSON(user));
        System.out.println(JSONObject.toJSON(user));
        System.out.println(JSONObject.toJSONStringWithDateFormat(user,JSON.DEFFAULT_DATE_FORMAT));
    }

    @Test
    public void json2Object(){
        String json = "{\"address\":{\"city\":\"changde\",\"prov\":\"hunan\"},\"birthDay\":1515575432881,\"hobby\":[\"pingpang\",\"game\"],\"id\":1,\"name\":\"lyy\"}";
        String json2="{\"address\":{\"city\":\"changde\",\"prov\":\"hunan\"},\"birthDay\":\"2018-01-10 17:19:18\",\"hobby\":[\"pingpang\",\"game\"],\"id\":1,\"name\":\"lyy\"}";
        User user = JSON.parseObject(json,User.class);
        User user2 = JSON.parseObject(json2,User.class);
        System.out.println(user);
        System.out.println(user2);
    }
}
