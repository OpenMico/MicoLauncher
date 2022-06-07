package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class GuavaCodec implements ObjectDeserializer, ObjectSerializer {
    public static GuavaCodec instance = new GuavaCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 0;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj instanceof Multimap) {
            jSONSerializer.write(((Multimap) obj).asMap());
        }
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [com.google.common.collect.ArrayListMultimap, T] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r3, java.lang.reflect.Type r4, java.lang.Object r5) {
        /*
            r2 = this;
            boolean r5 = r4 instanceof java.lang.reflect.ParameterizedType
            if (r5 == 0) goto L_0x000a
            java.lang.reflect.ParameterizedType r4 = (java.lang.reflect.ParameterizedType) r4
            java.lang.reflect.Type r4 = r4.getRawType()
        L_0x000a:
            java.lang.Class<com.google.common.collect.ArrayListMultimap> r5 = com.google.common.collect.ArrayListMultimap.class
            if (r4 != r5) goto L_0x0045
            com.google.common.collect.ArrayListMultimap r4 = com.google.common.collect.ArrayListMultimap.create()
            com.alibaba.fastjson.JSONObject r3 = r3.parseObject()
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x001e:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0044
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r0 = r5.getValue()
            boolean r1 = r0 instanceof java.util.Collection
            if (r1 == 0) goto L_0x003c
            java.lang.Object r5 = r5.getKey()
            java.util.List r0 = (java.util.List) r0
            r4.putAll(r5, r0)
            goto L_0x001e
        L_0x003c:
            java.lang.Object r5 = r5.getKey()
            r4.put(r5, r0)
            goto L_0x001e
        L_0x0044:
            return r4
        L_0x0045:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.GuavaCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
