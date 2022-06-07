package org.hapjs.sdk.platform;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
class d {
    private static final Map<String, String> a = new HashMap();

    public static void a(Context context) {
        a.putAll(a.a);
        Map<String, String> d = d(context);
        if (d != null && !d.isEmpty()) {
            a.putAll(d);
        }
    }

    public static void b(Context context) {
        final Context applicationContext = context.getApplicationContext();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: org.hapjs.sdk.platform.d.1
            @Override // java.lang.Runnable
            public void run() {
                d.e(applicationContext);
            }
        });
    }

    public static Map<String, String> a() {
        return a;
    }

    private static Map<String, String> d(Context context) {
        HashMap hashMap = new HashMap();
        String a2 = f.a(context);
        return !TextUtils.isEmpty(a2) ? e.b(a2) : hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(Context context) {
        e a2;
        String a3 = b.a();
        if (!TextUtils.isEmpty(a3) && (a2 = e.a(a3)) != null && a2.a != null) {
            f.a(context, a2.b);
            a.putAll(a2.a);
        }
    }
}
