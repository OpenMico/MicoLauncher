package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class LongCodec implements ObjectDeserializer, ObjectSerializer {
    public static LongCodec instance = new LongCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        long longValue = ((Long) obj).longValue();
        serializeWriter.writeLong(longValue);
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName) && longValue <= 2147483647L && longValue >= -2147483648L && type != Long.class && type != Long.TYPE) {
            serializeWriter.write(76);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        T t;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        try {
            int i = jSONLexer.token();
            if (i == 2) {
                long longValue = jSONLexer.longValue();
                jSONLexer.nextToken(16);
                t = (T) Long.valueOf(longValue);
            } else if (i == 3) {
                t = (T) Long.valueOf(TypeUtils.longValue(jSONLexer.decimalValue()));
                jSONLexer.nextToken(16);
            } else {
                if (i == 12) {
                    JSONObject jSONObject = new JSONObject(true);
                    defaultJSONParser.parseObject((Map) jSONObject);
                    t = (T) TypeUtils.castToLong(jSONObject);
                } else {
                    t = (T) TypeUtils.castToLong(defaultJSONParser.parse());
                }
                if (t == null) {
                    return null;
                }
            }
            return type == AtomicLong.class ? (T) new AtomicLong(((Long) t).longValue()) : t;
        } catch (Exception e) {
            throw new JSONException("parseLong error, field : " + obj, e);
        }
    }
}
