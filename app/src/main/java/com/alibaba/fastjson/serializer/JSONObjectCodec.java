package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class JSONObjectCodec implements ObjectSerializer {
    public static final JSONObjectCodec instance = new JSONObjectCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        MapSerializer mapSerializer = MapSerializer.instance;
        try {
            Field declaredField = obj.getClass().getDeclaredField("map");
            if (Modifier.isPrivate(declaredField.getModifiers())) {
                declaredField.setAccessible(true);
            }
            mapSerializer.write(jSONSerializer, declaredField.get(obj), obj2, type, i);
        } catch (Exception unused) {
            serializeWriter.writeNull();
        }
    }
}
