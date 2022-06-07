package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class AppendableSerializer implements ObjectSerializer {
    public static final AppendableSerializer instance = new AppendableSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        if (obj == null) {
            jSONSerializer.out.writeNull(SerializerFeature.WriteNullStringAsEmpty);
        } else {
            jSONSerializer.write(obj.toString());
        }
    }
}
