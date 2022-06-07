package com.google.gson;

import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public interface InstanceCreator<T> {
    T createInstance(Type type);
}
