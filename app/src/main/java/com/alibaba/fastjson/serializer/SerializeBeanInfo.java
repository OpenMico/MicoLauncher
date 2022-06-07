package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;

/* loaded from: classes.dex */
public class SerializeBeanInfo {
    protected final Class<?> beanType;
    protected int features;
    protected final FieldInfo[] fields;
    protected final JSONType jsonType;
    protected final FieldInfo[] sortedFields;
    protected final String typeKey;
    protected final String typeName;

    public SerializeBeanInfo(Class<?> cls, JSONType jSONType, String str, String str2, int i, FieldInfo[] fieldInfoArr, FieldInfo[] fieldInfoArr2) {
        this.beanType = cls;
        this.jsonType = jSONType;
        this.typeName = str;
        this.typeKey = str2;
        this.features = i;
        this.fields = fieldInfoArr;
        this.sortedFields = fieldInfoArr2;
    }
}
