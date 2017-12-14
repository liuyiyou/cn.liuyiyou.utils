/** 
 * Project Name:cn.liuyiyou.common 
 * File Name:EnumSerializer.java 
 * Package Name:cn.liuyiyou.common.utils 
 * Date:2017年7月25日下午4:30:41 
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved. 
 * 
 */ 
package cn.liuyiyou.utils.serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/** 
 * 名称: EnumSerializer <br/> 
 * @author liuyiyou.cn
 * @date 2017年7月25日
 * @version 6.0.0
 */
@SuppressWarnings("rawtypes")
public class EnumSerializer extends JsonSerializer<Enum> {

  

    @Override
    public void serialize(Enum value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        Map<String, Object> props = null;
        try {
            props = PropertyUtils.describe(value);
            props.remove("class");
            props.remove("declaringClass");
        } catch (Exception e) {
            props = new HashMap<String, Object>();
        }
        props.put("name", value.name());
        jgen.writeObject(props);
    }
}

