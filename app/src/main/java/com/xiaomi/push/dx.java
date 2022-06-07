package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class dx extends dy {
    public static String a = "";
    public static String b = "";

    public dx(Context context, int i) {
        super(context, i);
    }

    private String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        String[] split = str2.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length <= 10) {
            return str2;
        }
        int length = split.length;
        while (true) {
            length--;
            if (length < split.length - 10) {
                return str;
            }
            str = str + split[length];
        }
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 12;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public hj mo834a() {
        return hj.BroadcastAction;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public String mo834a() {
        String str = "";
        if (!TextUtils.isEmpty(a)) {
            str = str + a(dr.f23a, a);
            a = "";
        }
        if (TextUtils.isEmpty(b)) {
            return str;
        }
        String str2 = str + a(dr.b, b);
        b = "";
        return str2;
    }
}
