package com.xiaomi.analytics;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.analytics.a;
import com.xiaomi.analytics.internal.util.ALog;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LoggerFactory.java */
/* loaded from: classes3.dex */
public class b<T extends a> {
    public ConcurrentHashMap<String, T> a = new ConcurrentHashMap<>();

    public T a(Class<T> cls, String str) {
        if (TextUtils.isEmpty(str) || cls == null) {
            throw new IllegalArgumentException("Clazz is null or configKey is empty. configKey:" + str);
        }
        T t = this.a.get(str);
        if (t != null) {
            return t;
        }
        try {
            T newInstance = cls.getDeclaredConstructor(String.class).newInstance(str);
            this.a.put(str, newInstance);
            return newInstance;
        } catch (Exception e) {
            Log.e(ALog.addPrefix("LoggerFactory"), "getLogger e", e);
            throw new IllegalStateException("Can not instantiate logger. configKey:" + str);
        }
    }
}
