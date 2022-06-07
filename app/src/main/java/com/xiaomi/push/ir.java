package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.iz;
import com.xiaomi.push.jj;

/* loaded from: classes4.dex */
public class ir {
    public static short a(Context context, id idVar) {
        int i = 0;
        int a = g.m928a(context, idVar.b).a() + 0 + (ai.b(context) ? 4 : 0);
        if (ai.a(context)) {
            i = 8;
        }
        return (short) (a + i);
    }

    public static <T extends is<T, ?>> void a(T t, byte[] bArr) {
        if (bArr != null) {
            new iw(new jj.a(true, true, bArr.length)).a(t, bArr);
            return;
        }
        throw new ix("the message byte is empty.");
    }

    public static <T extends is<T, ?>> byte[] a(T t) {
        if (t == null) {
            return null;
        }
        try {
            return new iy(new iz.a()).a(t);
        } catch (ix e) {
            b.a("convertThriftObjectToBytes catch TException.", e);
            return null;
        }
    }
}
