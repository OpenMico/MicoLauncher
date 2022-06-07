package com.umeng.analytics.pro;

import java.lang.reflect.InvocationTargetException;

/* compiled from: TEnumHelper.java */
/* loaded from: classes2.dex */
public class av {
    public static au a(Class<? extends au> cls, int i) {
        try {
            return (au) cls.getMethod("findByValue", Integer.TYPE).invoke(null, Integer.valueOf(i));
        } catch (IllegalAccessException unused) {
            return null;
        } catch (NoSuchMethodException unused2) {
            return null;
        } catch (InvocationTargetException unused3) {
            return null;
        }
    }
}
