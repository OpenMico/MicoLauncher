package com.xiaomi.micolauncher.api;

import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes3.dex */
public class MinaResponse<T> extends a {
    public T data;

    public static Type wrapperType(final Type type) {
        return new ParameterizedType() { // from class: com.xiaomi.micolauncher.api.MinaResponse.1
            @Override // java.lang.reflect.ParameterizedType
            public Type getRawType() {
                return null;
            }

            @Override // java.lang.reflect.ParameterizedType
            public Type[] getActualTypeArguments() {
                return new Type[]{type};
            }

            @Override // java.lang.reflect.ParameterizedType
            public Type getOwnerType() {
                return ResponseHandler.class;
            }
        };
    }
}
