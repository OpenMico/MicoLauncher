package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* loaded from: classes.dex */
public class AtomicCodec implements ObjectDeserializer, ObjectSerializer {
    public static final AtomicCodec instance = new AtomicCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj instanceof AtomicInteger) {
            serializeWriter.writeInt(((AtomicInteger) obj).get());
        } else if (obj instanceof AtomicLong) {
            serializeWriter.writeLong(((AtomicLong) obj).get());
        } else if (obj instanceof AtomicBoolean) {
            serializeWriter.append((CharSequence) (((AtomicBoolean) obj).get() ? "true" : "false"));
        } else if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
        } else {
            int i2 = 0;
            if (obj instanceof AtomicIntegerArray) {
                AtomicIntegerArray atomicIntegerArray = (AtomicIntegerArray) obj;
                int length = atomicIntegerArray.length();
                serializeWriter.write(91);
                while (i2 < length) {
                    int i3 = atomicIntegerArray.get(i2);
                    if (i2 != 0) {
                        serializeWriter.write(44);
                    }
                    serializeWriter.writeInt(i3);
                    i2++;
                }
                serializeWriter.write(93);
                return;
            }
            AtomicLongArray atomicLongArray = (AtomicLongArray) obj;
            int length2 = atomicLongArray.length();
            serializeWriter.write(91);
            while (i2 < length2) {
                long j = atomicLongArray.get(i2);
                if (i2 != 0) {
                    serializeWriter.write(44);
                }
                serializeWriter.writeLong(j);
                i2++;
            }
            serializeWriter.write(93);
        }
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [T, java.util.concurrent.atomic.AtomicLongArray] */
    /* JADX WARN: Type inference failed for: r4v3, types: [T, java.util.concurrent.atomic.AtomicIntegerArray] */
    /* JADX WARN: Unknown variable types count: 2 */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r4, java.lang.reflect.Type r5, java.lang.Object r6) {
        /*
            r3 = this;
            com.alibaba.fastjson.parser.JSONLexer r6 = r4.lexer
            int r6 = r6.token()
            r0 = 8
            if (r6 != r0) goto L_0x0013
            com.alibaba.fastjson.parser.JSONLexer r4 = r4.lexer
            r5 = 16
            r4.nextToken(r5)
            r4 = 0
            return r4
        L_0x0013:
            com.alibaba.fastjson.JSONArray r6 = new com.alibaba.fastjson.JSONArray
            r6.<init>()
            r4.parseArray(r6)
            java.lang.Class<java.util.concurrent.atomic.AtomicIntegerArray> r4 = java.util.concurrent.atomic.AtomicIntegerArray.class
            r0 = 0
            if (r5 != r4) goto L_0x003e
            java.util.concurrent.atomic.AtomicIntegerArray r4 = new java.util.concurrent.atomic.AtomicIntegerArray
            int r5 = r6.size()
            r4.<init>(r5)
        L_0x0029:
            int r5 = r6.size()
            if (r0 >= r5) goto L_0x003d
            java.lang.Integer r5 = r6.getInteger(r0)
            int r5 = r5.intValue()
            r4.set(r0, r5)
            int r0 = r0 + 1
            goto L_0x0029
        L_0x003d:
            return r4
        L_0x003e:
            java.util.concurrent.atomic.AtomicLongArray r4 = new java.util.concurrent.atomic.AtomicLongArray
            int r5 = r6.size()
            r4.<init>(r5)
        L_0x0047:
            int r5 = r6.size()
            if (r0 >= r5) goto L_0x005b
            java.lang.Long r5 = r6.getLong(r0)
            long r1 = r5.longValue()
            r4.set(r0, r1)
            int r0 = r0 + 1
            goto L_0x0047
        L_0x005b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.AtomicCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
