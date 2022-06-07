package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.m;
import com.xiaomi.push.au;

/* loaded from: classes4.dex */
public class at {
    public static AbstractPushManager a(Context context, f fVar) {
        return b(context, fVar);
    }

    private static AbstractPushManager b(Context context, f fVar) {
        m.a a = m.m745a(fVar);
        if (a == null || TextUtils.isEmpty(a.a) || TextUtils.isEmpty(a.b)) {
            return null;
        }
        return (AbstractPushManager) au.a(a.a, a.b, context);
    }
}
