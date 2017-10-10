/** 
 * Project Name:cn.liuyiyou.common 
 * File Name:DecimalSerializer.java 
 * Package Name:cn.liuyiyou.common.utils 
 * Date:2017年7月25日下午4:27:14 
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved. 
 * 
 */ 
package cn.liuyiyou.common.utils;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/** 
 * 名称: DecimalSerializer <br/> 
 * @author liuyiyou.cn
 * @date 2017年7月25日
 * @version 6.0.0
 */
public class DecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(value.toPlainString());
    }
}
