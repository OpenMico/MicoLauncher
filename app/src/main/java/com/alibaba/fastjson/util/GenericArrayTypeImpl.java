package com.alibaba.fastjson.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class GenericArrayTypeImpl implements GenericArrayType {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Type genericComponentType;

    public GenericArrayTypeImpl(Type type) {
        this.genericComponentType = type;
    }

    @Override // java.lang.reflect.GenericArrayType
    public Type getGenericComponentType() {
        return this.genericComponentType;
    }

    public String toString() {
        Type genericComponentType = getGenericComponentType();
        StringBuilder sb = new StringBuilder();
        if (genericComponentType instanceof Class) {
            sb.append(((Class) genericComponentType).getName());
        } else {
            sb.append(genericComponentType.toString());
        }
        sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof GenericArrayType) {
            return this.genericComponentType.equals(((GenericArrayType) obj).getGenericComponentType());
        }
        return false;
    }

    public int hashCode() {
        return this.genericComponentType.hashCode();
    }
}
