package com.alibaba.fastjson.serializer;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;

/* loaded from: classes.dex */
public class EnumerationSerializer implements ObjectSerializer {
    public static EnumerationSerializer instance = new EnumerationSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Type type2 = null;
        int i2 = 0;
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName) && (type instanceof ParameterizedType)) {
            type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        Enumeration enumeration = (Enumeration) obj;
        SerialContext serialContext = jSONSerializer.context;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        try {
            serializeWriter.append('[');
            while (enumeration.hasMoreElements()) {
                Object nextElement = enumeration.nextElement();
                int i3 = i2 + 1;
                if (i2 != 0) {
                    serializeWriter.append(StringUtil.COMMA);
                }
                if (nextElement == null) {
                    serializeWriter.writeNull();
                } else {
                    jSONSerializer.getObjectWriter(nextElement.getClass()).write(jSONSerializer, nextElement, Integer.valueOf(i3 - 1), type2, 0);
                }
                i2 = i3;
            }
            serializeWriter.append(']');
        } finally {
            jSONSerializer.context = serialContext;
        }
    }
}
