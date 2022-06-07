package com.xiaomi.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.analytics.internal.SdkManager;
import com.xiaomi.analytics.internal.util.ALog;
import com.xiaomi.analytics.internal.util.AndroidUtils;
import com.xiaomi.analytics.internal.util.Utils;
import com.xiaomi.analytics.internal.v1.AnalyticsInterface;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseLogger.java */
/* loaded from: classes3.dex */
public class a {
    private static volatile AnalyticsInterface a;
    private static String b;
    private static Context c;
    private static ConcurrentLinkedQueue<C0158a> d = new ConcurrentLinkedQueue<>();
    private static SdkManager.OnSdkCorePrepareListener g = new SdkManager.OnSdkCorePrepareListener() { // from class: com.xiaomi.analytics.a.1
        @Override // com.xiaomi.analytics.internal.SdkManager.OnSdkCorePrepareListener
        public void onSdkCorePrepared(AnalyticsInterface analyticsInterface) {
            AnalyticsInterface unused = a.a = analyticsInterface;
            a.b();
        }
    };
    private String e = "";
    private String f;

    public static synchronized void a(Context context) {
        synchronized (a.class) {
            c = AndroidUtils.getApplicationContext(context);
            b = c.getPackageName();
            if (!TextUtils.isEmpty(b)) {
                SdkManager.getInstance(c).setOnSdkPrepareListener(g);
            } else {
                throw new IllegalArgumentException("Context is not a application context.");
            }
        }
    }

    public a(String str) {
        this.f = "";
        if (c != null) {
            this.f = str;
            return;
        }
        throw new IllegalStateException("Do you forget to do Logger.init ?");
    }

    public void startSession() {
        this.e = UUID.randomUUID().toString();
        ALog.d("BaseLogger", "startSession " + this.e);
    }

    public void endSession() {
        this.e = "";
    }

    protected void log(LogEvent logEvent) {
        if (logEvent != null) {
            a = SdkManager.getInstance(c).getAnalytics();
            SdkManager.getInstance(c).pollUpdate();
            if (a != null) {
                a.trackEvent(logEvent.pack(b, this.f, this.e));
            } else {
                d.offer(new C0158a(b, this.f, this.e, logEvent));
            }
        }
    }

    protected void log(String str, LogEvent logEvent) {
        if (logEvent != null && !TextUtils.isEmpty(str)) {
            a = SdkManager.getInstance(c).getAnalytics();
            SdkManager.getInstance(c).pollUpdate();
            if (a != null) {
                a.trackEvent(logEvent.pack(str, this.f, this.e));
            } else {
                d.offer(new C0158a(str, this.f, this.e, logEvent));
            }
        }
    }

    public static void b() {
        if (d.size() > 0 && a != null) {
            ALog.d("BaseLogger", "drainPendingEvents ");
            ArrayList arrayList = new ArrayList();
            while (d.size() > 0) {
                C0158a poll = d.poll();
                arrayList.add(poll.d.pack(poll.a, poll.b, poll.c));
            }
            int i = 0;
            while (i < arrayList.size()) {
                ArrayList arrayList2 = new ArrayList();
                while (arrayList2.size() < 100 && i < arrayList.size()) {
                    arrayList2.add((String) arrayList.get(i));
                    i++;
                }
                ALog.d("BaseLogger", "trackEvents " + arrayList2.size());
                a.trackEvents((String[]) Utils.list2Array(arrayList2, String.class));
            }
        }
    }

    /* compiled from: BaseLogger.java */
    /* renamed from: com.xiaomi.analytics.a$a */
    /* loaded from: classes3.dex */
    public static class C0158a {
        String a;
        String b;
        String c;
        LogEvent d;

        public C0158a(String str, String str2, String str3, LogEvent logEvent) {
            this.b = str2;
            this.c = str3;
            this.d = logEvent;
            this.a = str;
        }
    }
}
