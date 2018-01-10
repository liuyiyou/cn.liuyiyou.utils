package cn.liuyiyou.utils;

import cn.liuyiyou.beans.Address;
import cn.liuyiyou.beans.SimilarUser;
import cn.liuyiyou.beans.User;
import cn.liuyiyou.utils.beans.BeanUtils;
import cn.liuyiyou.utils.collect.ListUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class BeanUtilsTest {

    @Test
    public void testCopyProperties() throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        user.setId(1);
        user.setName("lyy");
        user.setHobby(ListUtils.arrayToList("pingpang", "game"));
        Address address = new Address();
        address.setCity("changde");
        address.setProv("hunan");
        user.setAddress(address);
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getHobby());
        SimilarUser similarUser = new SimilarUser();
        BeanUtils.copyProperties(user, similarUser);
        System.out.println(similarUser.getId());
        System.out.println(similarUser.getName());
        System.out.println(similarUser.getHobby());
        System.out.println(similarUser.getAddress());
    }

    @Test
    public void testCopyPropertiy() throws InvocationTargetException, IllegalAccessException {
        SimilarUser similarUser = new SimilarUser();
        BeanUtils.copyProperty(similarUser,"id",1);
        BeanUtils.copyProperty(similarUser,"name","lyy");
        BeanUtils.copyProperty(similarUser,"hobby",ListUtils.arrayToList("pingpang", "game"));
        System.out.println(similarUser.getId());
        System.out.println(similarUser.getName());
        System.out.println(similarUser.getHobby());
    }
}
