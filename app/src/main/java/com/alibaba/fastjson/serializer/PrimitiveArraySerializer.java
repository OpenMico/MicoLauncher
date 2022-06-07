package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class PrimitiveArraySerializer implements ObjectSerializer {
    public static PrimitiveArraySerializer instance = new PrimitiveArraySerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        int i2 = 0;
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            serializeWriter.write(91);
            while (i2 < iArr.length) {
                if (i2 != 0) {
                    serializeWriter.write(44);
                }
                serializeWriter.writeInt(iArr[i2]);
                i2++;
            }
            serializeWriter.write(93);
        } else if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            serializeWriter.write(91);
            while (i2 < sArr.length) {
                if (i2 != 0) {
                    serializeWriter.write(44);
                }
                serializeWriter.writeInt(sArr[i2]);
                i2++;
            }
            serializeWriter.write(93);
        } else if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            serializeWriter.write(91);
            while (i2 < jArr.length) {
                if (i2 != 0) {
                    serializeWriter.write(44);
                }
                serializeWriter.writeLong(jArr[i2]);
                i2++;
            }
            serializeWriter.write(93);
        } else if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            serializeWriter.write(91);
            while (i2 < zArr.length) {
                if (i2 != 0) {
                    serializeWriter.write(44);
                }
                serializeWriter.write(zArr[i2]);
                i2++;
            }
            serializeWriter.write(93);
        } else if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            serializeWriter.write(91);
            while (i2 < fArr.length) {
                if (i2 != 0) {
                    serializeWriter.write(44);
                }
                float f = fArr[i2];
                if (Float.isNaN(f)) {
                    serializeWriter.writeNull();
                } else {
                    serializeWriter.append((CharSequence) Float.toString(f));
                }
                i2++;
            }
            serializeWriter.write(93);
        } else if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            serializeWriter.write(91);
            while (i2 < dArr.length) {
                if (i2 != 0) {
                    serializeWriter.write(44);
                }
                double d = dArr[i2];
                if (Double.isNaN(d)) {
                    serializeWriter.writeNull();
                } else {
                    serializeWriter.append((CharSequence) Double.toString(d));
                }
                i2++;
            }
            serializeWriter.write(93);
        } else if (obj instanceof byte[]) {
            serializeWriter.writeByteArray((byte[]) obj);
        } else {
            serializeWriter.writeString((char[]) obj);
        }
    }
}
