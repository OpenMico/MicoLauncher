package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public final class ListSerializer implements ObjectSerializer {
    public static final ListSerializer instance = new ListSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        char c;
        int i2;
        Object obj3;
        boolean z = jSONSerializer.out.isEnabled(SerializerFeature.WriteClassName) || SerializerFeature.isEnabled(i, SerializerFeature.WriteClassName);
        SerializeWriter serializeWriter = jSONSerializer.out;
        Type type2 = null;
        if (z) {
            type2 = TypeUtils.getCollectionItemType(type);
        }
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        List list = (List) obj;
        if (list.size() == 0) {
            serializeWriter.append((CharSequence) HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        SerialContext serialContext = jSONSerializer.context;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        try {
            boolean isEnabled = serializeWriter.isEnabled(SerializerFeature.PrettyFormat);
            char c2 = StringUtil.COMMA;
            char c3 = ']';
            if (isEnabled) {
                serializeWriter.append('[');
                jSONSerializer.incrementIndent();
                int i3 = 0;
                for (Object obj4 : list) {
                    if (i3 != 0) {
                        serializeWriter.append(c2);
                    }
                    jSONSerializer.println();
                    if (obj4 == null) {
                        c3 = c3;
                        jSONSerializer.out.writeNull();
                    } else if (jSONSerializer.containsReference(obj4)) {
                        jSONSerializer.writeReference(obj4);
                        c3 = c3;
                    } else {
                        ObjectSerializer objectWriter = jSONSerializer.getObjectWriter(obj4.getClass());
                        c3 = c3;
                        jSONSerializer.context = new SerialContext(serialContext, obj, obj2, 0, 0);
                        objectWriter.write(jSONSerializer, obj4, Integer.valueOf(i3), type2, i);
                    }
                    i3++;
                    c2 = StringUtil.COMMA;
                }
                jSONSerializer.decrementIdent();
                jSONSerializer.println();
                serializeWriter.append(c3);
                return;
            }
            char c4 = ']';
            serializeWriter.append('[');
            int size = list.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj5 = list.get(i4);
                if (i4 != 0) {
                    c = StringUtil.COMMA;
                    serializeWriter.append(StringUtil.COMMA);
                } else {
                    c = StringUtil.COMMA;
                }
                if (obj5 == null) {
                    serializeWriter.append((CharSequence) "null");
                    i2 = i4;
                    z = z;
                } else {
                    Class<?> cls = obj5.getClass();
                    if (cls == Integer.class) {
                        serializeWriter.writeInt(((Integer) obj5).intValue());
                        i2 = i4;
                        z = z;
                    } else if (cls == Long.class) {
                        long longValue = ((Long) obj5).longValue();
                        if (z) {
                            serializeWriter.writeLong(longValue);
                            serializeWriter.write(76);
                        } else {
                            serializeWriter.writeLong(longValue);
                        }
                        i2 = i4;
                        z = z;
                    } else if ((SerializerFeature.DisableCircularReferenceDetect.mask & i) != 0) {
                        i2 = i4;
                        jSONSerializer.getObjectWriter(obj5.getClass()).write(jSONSerializer, obj5, Integer.valueOf(i4), type2, i);
                        z = z;
                    } else {
                        i2 = i4;
                        if (!serializeWriter.disableCircularReferenceDetect) {
                            obj3 = obj5;
                            z = z;
                            jSONSerializer.context = new SerialContext(serialContext, obj, obj2, 0, 0);
                        } else {
                            obj3 = obj5;
                            z = z;
                        }
                        if (jSONSerializer.containsReference(obj3)) {
                            jSONSerializer.writeReference(obj3);
                        } else {
                            ObjectSerializer objectWriter2 = jSONSerializer.getObjectWriter(obj3.getClass());
                            if ((SerializerFeature.WriteClassName.mask & i) == 0 || !(objectWriter2 instanceof JavaBeanSerializer)) {
                                objectWriter2.write(jSONSerializer, obj3, Integer.valueOf(i2), type2, i);
                            } else {
                                ((JavaBeanSerializer) objectWriter2).writeNoneASM(jSONSerializer, obj3, Integer.valueOf(i2), type2, i);
                            }
                        }
                    }
                }
                i4 = i2 + 1;
                c4 = ']';
            }
            serializeWriter.append(c4);
        } finally {
            jSONSerializer.context = serialContext;
        }
    }
}
