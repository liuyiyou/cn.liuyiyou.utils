package cn.liuyiyou.utils.serializer;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


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
