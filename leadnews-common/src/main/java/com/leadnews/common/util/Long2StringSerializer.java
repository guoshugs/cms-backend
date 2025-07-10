package com.leadnews.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @version 1.0
 * @description 自定义序列化器 将Long类型数据转成String
 * @package com.leadnews.common.util
 */
public class Long2StringSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value!=null){
            gen.writeString(value.toString());
        }
    }
}
