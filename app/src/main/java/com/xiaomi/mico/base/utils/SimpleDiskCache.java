package com.xiaomi.mico.base.utils;

import android.content.Context;
import com.xiaomi.mico.base.utils.Cache;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class SimpleDiskCache<K, V extends Serializable> {
    private Cache.a a;

    public SimpleDiskCache(Context context, File file, int i) {
        try {
            this.a = new Cache.a(file, VersionUtils.getVersionCode(context), i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public V get(K k) {
        try {
            if (this.a == null) {
                return null;
            }
            return (V) ((Serializable) this.a.b(String.valueOf(k)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(K k, V v) {
        try {
            this.a.a(String.valueOf(k), v, -1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        Cache.a aVar = this.a;
        if (aVar != null) {
            aVar.a();
        }
    }
}
