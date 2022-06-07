package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import android.util.LruCache;
import com.xiaomi.onetrack.util.k;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class l extends LruCache<String, k.a> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public l(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public int sizeOf(String str, k.a aVar) {
        if (aVar == null || TextUtils.isEmpty(aVar.a)) {
            return 0;
        }
        return aVar.a.length();
    }
}
