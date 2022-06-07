package org.repackage.com.miui.deviceid;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes3.dex */
public class IdentifierManager {
    private static Object a;
    private static Class<?> b;
    private static Method c;
    private static Method d;
    private static Method e;
    private static Method f;

    static {
        try {
            b = Class.forName("com.android.id.impl.IdProviderImpl");
            a = b.newInstance();
            c = b.getMethod("getUDID", Context.class);
            d = b.getMethod("getOAID", Context.class);
            e = b.getMethod("getVAID", Context.class);
            f = b.getMethod("getAAID", Context.class);
        } catch (Exception e2) {
            Log.e("IdentifierManager", "reflect exception!", e2);
        }
    }

    public static boolean a() {
        return (b == null || a == null) ? false : true;
    }

    public static String a(Context context) {
        return a(context, c);
    }

    public static String b(Context context) {
        return a(context, d);
    }

    public static String c(Context context) {
        return a(context, e);
    }

    public static String d(Context context) {
        return a(context, f);
    }

    private static String a(Context context, Method method) {
        Object obj = a;
        if (obj == null || method == null) {
            return null;
        }
        try {
            Object invoke = method.invoke(obj, context);
            if (invoke != null) {
                return (String) invoke;
            }
            return null;
        } catch (Exception e2) {
            Log.e("IdentifierManager", "invoke exception!", e2);
            return null;
        }
    }
}
