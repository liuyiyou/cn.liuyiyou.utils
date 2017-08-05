/** 
 * Project Name:cn.liuyiyou.common 
 * File Name:BooleanDeserializer.java 
 * Package Name:cn.liuyiyou.common.utils 
 * Date:2017年7月25日下午4:28:07 
 * Copyright (c) 2017, www.daojia.com All Rights Reserved. 
 * 
 */ 
package cn.liuyiyou.common.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/** 
 * /**
 * 自定义 jackson 反序列化实现类：将布尔字符串反序列化为 {@link Boolean} 对象。<br>
 * "1", "true", "yes", "on", "T", "Y", "是", "对" 返回 true；<br>
 * "0", "false", "no", "off", "F", "N", "否", "错" 返回 false；<br>
 * 其他情况返回 {@code null}
 * 
 * <pre>
 * 示例代码：
 * <code>
 * public class Example {
 *     <b>&#64;JsonDeserialize(using = DaojiaBooleanDeserializer.class)</b>
 *     private Boolean success;
 *     // get, set
 * }
 * </code>
 * </pre>
 * 名称: BooleanDeserializer <br/> 
 * @author liuyiyou.cn
 * @date 2017年7月25日
 * @version 6.0.0
 */
public class BooleanDeserializer extends JsonDeserializer<Boolean> {
    private static final String[] TRUE_VALUES = new String[] { "1", "true", "yes", "on", "T", "Y", "是", "对" };
    private static final String[] FALSE_VALUES = new String[] { "0", "false", "no", "off", "F", "N", "否", "错" };

    @Override
    public Boolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = StringUtils.trim(jp.getValueAsString());

        for (String s : TRUE_VALUES) {
            if (s.equals(value)) {
                return Boolean.TRUE;
            }
        }

        for (String s : FALSE_VALUES) {
            if (s.equals(value)) {
                return Boolean.FALSE;
            }
        }

        return null;
    }

}
