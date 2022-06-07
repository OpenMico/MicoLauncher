package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public abstract class ContextObjectDeserializer implements ObjectDeserializer {
    public abstract <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i);

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }
}
