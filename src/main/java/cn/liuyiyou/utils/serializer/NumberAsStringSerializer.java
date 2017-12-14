/** 
 * Project Name:cn.liuyiyou.common 
 * File Name:NumberAsStringSerializer.java 
 * Package Name:cn.liuyiyou.common.utils 
 * Date:2017年7月25日下午4:46:18 
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved. 
 * 
 */ 
package cn.liuyiyou.utils.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/** 
 * 名称: NumberAsStringSerializer <br/> 
 * @author liuyiyou.cn
 * @date 2017年7月25日
 * @version 6.0.0
 */
public class NumberAsStringSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(String.valueOf(value));
    }

}
