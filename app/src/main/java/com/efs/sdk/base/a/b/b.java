package com.efs.sdk.base.a.b;

import androidx.annotation.Nullable;
import com.efs.sdk.base.a.h.d;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class b {
    private ConcurrentHashMap<Byte, e> a = new ConcurrentHashMap<>();

    @Nullable
    public final e a(byte b) {
        if (!this.a.containsKey(Byte.valueOf(b))) {
            switch (b) {
                case 1:
                    this.a.putIfAbsent(Byte.valueOf(b), new g());
                    break;
                case 2:
                    this.a.putIfAbsent(Byte.valueOf(b), new d());
                    break;
                default:
                    d.a("efs.cache", "Cache module not support protocol ".concat(String.valueOf((int) b)), null);
                    break;
            }
        }
        return this.a.get(Byte.valueOf(b));
    }
}
