package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class CollectionCodec implements ObjectDeserializer, ObjectSerializer {
    public static final CollectionCodec instance = new CollectionCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Type type2 = null;
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName) || SerializerFeature.isEnabled(i, SerializerFeature.WriteClassName)) {
            type2 = TypeUtils.getCollectionItemType(type);
        }
        Collection collection = (Collection) obj;
        SerialContext serialContext = jSONSerializer.context;
        int i2 = 0;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
            if (HashSet.class == collection.getClass()) {
                serializeWriter.append((CharSequence) "Set");
            } else if (TreeSet.class == collection.getClass()) {
                serializeWriter.append((CharSequence) "TreeSet");
            }
        }
        try {
            serializeWriter.append('[');
            for (Object obj3 : collection) {
                int i3 = i2 + 1;
                if (i2 != 0) {
                    serializeWriter.append(StringUtil.COMMA);
                }
                if (obj3 == null) {
                    serializeWriter.writeNull();
                } else {
                    Class<?> cls = obj3.getClass();
                    if (cls == Integer.class) {
                        serializeWriter.writeInt(((Integer) obj3).intValue());
                    } else if (cls == Long.class) {
                        serializeWriter.writeLong(((Long) obj3).longValue());
                        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
                            serializeWriter.write(76);
                        }
                    } else {
                        ObjectSerializer objectWriter = jSONSerializer.getObjectWriter(cls);
                        if (!SerializerFeature.isEnabled(i, SerializerFeature.WriteClassName) || !(objectWriter instanceof JavaBeanSerializer)) {
                            objectWriter.write(jSONSerializer, obj3, Integer.valueOf(i3 - 1), type2, i);
                        } else {
                            ((JavaBeanSerializer) objectWriter).writeNoneASM(jSONSerializer, obj3, Integer.valueOf(i3 - 1), type2, i);
                        }
                    }
                }
                i2 = i3;
            }
            serializeWriter.append(']');
        } finally {
            jSONSerializer.context = serialContext;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [T, java.util.Collection] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.alibaba.fastjson.JSONArray, T, java.util.Collection] */
    /* JADX WARN: Unknown variable types count: 2 */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r3, java.lang.reflect.Type r4, java.lang.Object r5) {
        /*
            r2 = this;
            com.alibaba.fastjson.parser.JSONLexer r0 = r3.lexer
            int r0 = r0.token()
            r1 = 8
            if (r0 != r1) goto L_0x0013
            com.alibaba.fastjson.parser.JSONLexer r3 = r3.lexer
            r4 = 16
            r3.nextToken(r4)
            r3 = 0
            return r3
        L_0x0013:
            java.lang.Class<com.alibaba.fastjson.JSONArray> r0 = com.alibaba.fastjson.JSONArray.class
            if (r4 != r0) goto L_0x0020
            com.alibaba.fastjson.JSONArray r4 = new com.alibaba.fastjson.JSONArray
            r4.<init>()
            r3.parseArray(r4)
            return r4
        L_0x0020:
            java.util.Collection r0 = com.alibaba.fastjson.util.TypeUtils.createCollection(r4)
            java.lang.reflect.Type r4 = com.alibaba.fastjson.util.TypeUtils.getCollectionItemType(r4)
            r3.parseArray(r4, r0, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.CollectionCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
