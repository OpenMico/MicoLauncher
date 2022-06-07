package com.alibaba.fastjson.spi;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;

/* loaded from: classes.dex */
public interface Module {
    ObjectDeserializer createDeserializer(ParserConfig parserConfig, Class cls);

    ObjectSerializer createSerializer(SerializeConfig serializeConfig, Class cls);
}
