package cn.liuyiyou.utils.collect;

import org.junit.Test;

import java.util.List;


public class ListUtilsTest {


    @Test
    public void arrayToList() {
        String[] array = new String[]{"a", "b", "c"};
        List<String> list = ListUtils.arrayToList(array);
        System.out.println(list);
    }

    @Test
    public void listStringToInteger() {
        String[] array = new String[]{"a", "b", "c"};
        List<String> list = ListUtils.arrayToList(array);
        ListUtils.listStringToInteger(list);
    }

    @Test
    public void listIntegerToString() {
    }

    @Test
    public void setToList() {
    }
}