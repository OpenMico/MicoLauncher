package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.hh;
import com.xiaomi.push.hl;
import com.xiaomi.push.hu;
import com.xiaomi.push.ig;
import com.xiaomi.push.m;
import com.xiaomi.push.service.be;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class MiTinyDataClient {
    public static final String PENDING_REASON_APPID = "com.xiaomi.xmpushsdk.tinydataPending.appId";
    public static final String PENDING_REASON_CHANNEL = "com.xiaomi.xmpushsdk.tinydataPending.channel";
    public static final String PENDING_REASON_INIT = "com.xiaomi.xmpushsdk.tinydataPending.init";

    /* loaded from: classes4.dex */
    public static class a {
        private static a a;
        private Context b;
        private String c;
        private Boolean d;
        private C0177a e = new C0177a();
        private final ArrayList<hl> f = new ArrayList<>();

        /* renamed from: com.xiaomi.mipush.sdk.MiTinyDataClient$a$a */
        /* loaded from: classes4.dex */
        public class C0177a {
            private ScheduledFuture<?> d;
            private ScheduledThreadPoolExecutor c = new ScheduledThreadPoolExecutor(1, new m("mtdc"));
            public final ArrayList<hl> a = new ArrayList<>();
            private final Runnable e = new ab(this);

            public C0177a() {
                a.this = r3;
            }

            public void a() {
                if (this.d == null) {
                    this.d = this.c.scheduleAtFixedRate(this.e, 1000L, 1000L, TimeUnit.MILLISECONDS);
                }
            }

            public void b() {
                hl remove = this.a.remove(0);
                for (ig igVar : be.a(Arrays.asList(remove), a.this.b.getPackageName(), d.m727a(a.this.b).m728a(), 30720)) {
                    b.c("MiTinyDataClient Send item by PushServiceClient.sendMessage(XmActionNotification)." + remove.d());
                    ay.a(a.this.b).a((ay) igVar, hh.Notification, true, (hu) null);
                }
            }

            public void a(hl hlVar) {
                this.c.execute(new aa(this, hlVar));
            }
        }

        public static a a() {
            if (a == null) {
                synchronized (a.class) {
                    if (a == null) {
                        a = new a();
                    }
                }
            }
            return a;
        }

        private void b(hl hlVar) {
            synchronized (this.f) {
                if (!this.f.contains(hlVar)) {
                    this.f.add(hlVar);
                    if (this.f.size() > 100) {
                        this.f.remove(0);
                    }
                }
            }
        }

        private boolean b(Context context) {
            if (!ay.a(context).m724a()) {
                return true;
            }
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
                if (packageInfo == null) {
                    return false;
                }
                return packageInfo.versionCode >= 108;
            } catch (Exception unused) {
                return false;
            }
        }

        private boolean c(Context context) {
            return d.m727a(context).m728a() == null && !b(this.b);
        }

        private boolean c(hl hlVar) {
            if (be.a(hlVar, false)) {
                return false;
            }
            if (this.d.booleanValue()) {
                b.c("MiTinyDataClient Send item by PushServiceClient.sendTinyData(ClientUploadDataItem)." + hlVar.d());
                ay.a(this.b).a(hlVar);
                return true;
            }
            this.e.a(hlVar);
            return true;
        }

        public void a(Context context) {
            if (context == null) {
                b.m149a("context is null, MiTinyDataClientImp.init() failed.");
                return;
            }
            this.b = context;
            this.d = Boolean.valueOf(b(context));
            b(MiTinyDataClient.PENDING_REASON_INIT);
        }

        public synchronized void a(String str) {
            if (TextUtils.isEmpty(str)) {
                b.m149a("channel is null, MiTinyDataClientImp.setChannel(String) failed.");
                return;
            }
            this.c = str;
            b(MiTinyDataClient.PENDING_REASON_CHANNEL);
        }

        /* renamed from: a */
        public boolean m720a() {
            return this.b != null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:47:0x00a5, code lost:
            r0 = "MiTinyDataClient Pending " + r6.b() + " reason is " + com.xiaomi.mipush.sdk.MiTinyDataClient.PENDING_REASON_CHANNEL;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public synchronized boolean a(com.xiaomi.push.hl r6) {
            /*
                Method dump skipped, instructions count: 276
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.MiTinyDataClient.a.a(com.xiaomi.push.hl):boolean");
        }

        public void b(String str) {
            b.c("MiTinyDataClient.processPendingList(" + str + ")");
            ArrayList arrayList = new ArrayList();
            synchronized (this.f) {
                arrayList.addAll(this.f);
                this.f.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                a((hl) it.next());
            }
        }
    }

    public static void init(Context context, String str) {
        if (context == null) {
            b.m149a("context is null, MiTinyDataClient.init(Context, String) failed.");
            return;
        }
        a.a().a(context);
        if (TextUtils.isEmpty(str)) {
            b.m149a("channel is null or empty, MiTinyDataClient.init(Context, String) failed.");
        } else {
            a.a().a(str);
        }
    }

    public static boolean upload(Context context, hl hlVar) {
        b.c("MiTinyDataClient.upload " + hlVar.d());
        if (!a.a().m720a()) {
            a.a().a(context);
        }
        return a.a().a(hlVar);
    }

    public static boolean upload(Context context, String str, String str2, long j, String str3) {
        hl hlVar = new hl();
        hlVar.d(str);
        hlVar.c(str2);
        hlVar.a(j);
        hlVar.b(str3);
        hlVar.a(true);
        hlVar.a("push_sdk_channel");
        return upload(context, hlVar);
    }

    public static boolean upload(String str, String str2, long j, String str3) {
        hl hlVar = new hl();
        hlVar.d(str);
        hlVar.c(str2);
        hlVar.a(j);
        hlVar.b(str3);
        return a.a().a(hlVar);
    }
}
