package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class JSONSerializableSerializer implements ObjectSerializer {
    public static JSONSerializableSerializer instance = new JSONSerializableSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        JSONSerializable jSONSerializable = (JSONSerializable) obj;
        if (jSONSerializable == null) {
            jSONSerializer.writeNull();
        } else {
            jSONSerializable.write(jSONSerializer, obj2, type, i);
        }
    }
}
