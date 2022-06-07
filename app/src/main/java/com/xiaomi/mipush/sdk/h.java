package com.xiaomi.mipush.sdk;

import android.text.TextUtils;

/* loaded from: classes4.dex */
class h {
    int a = 0;
    String b = "";

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return !TextUtils.isEmpty(hVar.b) && hVar.b.equals(this.b);
    }
}
