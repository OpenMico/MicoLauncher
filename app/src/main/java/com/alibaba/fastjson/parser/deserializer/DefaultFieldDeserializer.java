package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes.dex */
public class DefaultFieldDeserializer extends FieldDeserializer {
    protected boolean customDeserilizer;
    protected ObjectDeserializer fieldValueDeserilizer;

    public DefaultFieldDeserializer(ParserConfig parserConfig, Class<?> cls, FieldInfo fieldInfo) {
        super(cls, fieldInfo);
        boolean z = false;
        this.customDeserilizer = false;
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null) {
            Class<?> deserializeUsing = annotation.deserializeUsing();
            if (!(deserializeUsing == null || deserializeUsing == Void.class)) {
                z = true;
            }
            this.customDeserilizer = z;
        }
    }

    public ObjectDeserializer getFieldValueDeserilizer(ParserConfig parserConfig) {
        if (this.fieldValueDeserilizer == null) {
            JSONField annotation = this.fieldInfo.getAnnotation();
            if (annotation == null || annotation.deserializeUsing() == Void.class) {
                this.fieldValueDeserilizer = parserConfig.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
            } else {
                try {
                    this.fieldValueDeserilizer = (ObjectDeserializer) annotation.deserializeUsing().newInstance();
                } catch (Exception e) {
                    throw new JSONException("create deserializeUsing ObjectDeserializer error", e);
                }
            }
        }
        return this.fieldValueDeserilizer;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a9 A[EDGE_INSN: B:49:0x00a9->B:34:0x00a9 ?: BREAK  , SYNTHETIC] */
    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parseField(com.alibaba.fastjson.parser.DefaultJSONParser r8, java.lang.Object r9, java.lang.reflect.Type r10, java.util.Map<java.lang.String, java.lang.Object> r11) {
        /*
            Method dump skipped, instructions count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.Object, java.lang.reflect.Type, java.util.Map):void");
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public int getFastMatchToken() {
        ObjectDeserializer objectDeserializer = this.fieldValueDeserilizer;
        if (objectDeserializer != null) {
            return objectDeserializer.getFastMatchToken();
        }
        return 2;
    }

    public void parseFieldUnwrapped(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map) {
        throw new JSONException("TODO");
    }
}
