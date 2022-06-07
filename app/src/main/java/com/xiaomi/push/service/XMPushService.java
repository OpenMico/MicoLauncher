package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.ac;
import com.xiaomi.push.aj;
import com.xiaomi.push.ap;
import com.xiaomi.push.at;
import com.xiaomi.push.ay;
import com.xiaomi.push.bf;
import com.xiaomi.push.cv;
import com.xiaomi.push.de;
import com.xiaomi.push.el;
import com.xiaomi.push.ev;
import com.xiaomi.push.ew;
import com.xiaomi.push.ex;
import com.xiaomi.push.fg;
import com.xiaomi.push.fl;
import com.xiaomi.push.fn;
import com.xiaomi.push.fo;
import com.xiaomi.push.fq;
import com.xiaomi.push.fs;
import com.xiaomi.push.ft;
import com.xiaomi.push.fy;
import com.xiaomi.push.gc;
import com.xiaomi.push.gd;
import com.xiaomi.push.ge;
import com.xiaomi.push.gg;
import com.xiaomi.push.gs;
import com.xiaomi.push.gz;
import com.xiaomi.push.hb;
import com.xiaomi.push.hc;
import com.xiaomi.push.hf;
import com.xiaomi.push.hh;
import com.xiaomi.push.hl;
import com.xiaomi.push.hm;
import com.xiaomi.push.id;
import com.xiaomi.push.ig;
import com.xiaomi.push.ih;
import com.xiaomi.push.ir;
import com.xiaomi.push.ix;
import com.xiaomi.push.service.al;
import com.xiaomi.push.service.g;
import com.xiaomi.push.u;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class XMPushService extends Service implements fq {
    private fo c;
    private t d;
    private String e;
    private e f;
    private fl i;
    private fn j;
    private d k;
    private ContentObserver q;
    private static final int h = Process.myPid();
    public static int a = 1;
    private long g = 0;

    /* renamed from: a */
    protected Class f185a = XMJobService.class;
    private ak l = null;
    private g m = null;
    Messenger b = null;
    private Collection<ae> n = Collections.synchronizedCollection(new ArrayList());
    private ArrayList<l> o = new ArrayList<>();
    private fs p = new ad(this);

    /* loaded from: classes4.dex */
    public class a extends i {
        al.b b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(al.b bVar) {
            super(9);
            XMPushService.this = r1;
            this.b = null;
            this.b = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "bind the client. " + this.b.g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            String str;
            try {
                if (!XMPushService.this.c()) {
                    com.xiaomi.channel.commonutils.logger.b.d("trying bind while the connection is not created, quit!");
                    return;
                }
                al.b a = al.a().a(this.b.g, this.b.b);
                if (a == null) {
                    str = "ignore bind because the channel " + this.b.g + " is removed ";
                } else if (a.j == al.c.unbind) {
                    a.a(al.c.binding, 0, 0, (String) null, (String) null);
                    XMPushService.this.j.a(a);
                    hb.a(XMPushService.this, a);
                    return;
                } else {
                    str = "trying duplicate bind, ingore! " + a.j;
                }
                com.xiaomi.channel.commonutils.logger.b.m149a(str);
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
                XMPushService.this.a(10, e);
            } catch (Throwable unused) {
            }
        }
    }

    /* loaded from: classes4.dex */
    public static class b extends i {
        private final al.b b;

        public b(al.b bVar) {
            super(12);
            this.b = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "bind time out. chid=" + this.b.g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            this.b.a(al.c.unbind, 1, 21, (String) null, (String) null);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            return TextUtils.equals(((b) obj).b.g, this.b.g);
        }

        public int hashCode() {
            return this.b.g.hashCode();
        }
    }

    /* loaded from: classes4.dex */
    class c extends i {
        private fg c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(fg fgVar) {
            super(8);
            XMPushService.this = r1;
            this.c = null;
            this.c = fgVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "receive a message.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            XMPushService.this.l.a(this.c);
        }
    }

    /* loaded from: classes4.dex */
    public class d extends i {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d() {
            super(1);
            XMPushService.this = r1;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "do reconnect..";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            if (XMPushService.this.m1118a()) {
                XMPushService.this.o();
            } else {
                com.xiaomi.channel.commonutils.logger.b.m149a("should not connect. quit the job.");
            }
        }
    }

    /* loaded from: classes4.dex */
    public class e extends BroadcastReceiver {
        e() {
            XMPushService.this = r1;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            XMPushService.this.onStart(intent, XMPushService.a);
        }
    }

    /* loaded from: classes4.dex */
    public class f extends i {
        public Exception a;
        public int b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public f(int i, Exception exc) {
            super(2);
            XMPushService.this = r1;
            this.b = i;
            this.a = exc;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "disconnect the connection.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            XMPushService.this.a(this.b, this.a);
        }
    }

    /* loaded from: classes4.dex */
    class g extends i {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g() {
            super(65535);
            XMPushService.this = r1;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "Init Job";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            XMPushService.this.i();
        }
    }

    /* loaded from: classes4.dex */
    public class h extends i {
        private Intent c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public h(Intent intent) {
            super(15);
            XMPushService.this = r1;
            this.c = null;
            this.c = intent;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "Handle intent action = " + this.c.getAction();
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            XMPushService.this.c(this.c);
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class i extends g.b {
        public i(int i) {
            super(i);
        }

        /* renamed from: a */
        public abstract String mo1167a();

        public abstract void a();

        @Override // java.lang.Runnable
        public void run() {
            if (!(this.a == 4 || this.a == 8)) {
                com.xiaomi.channel.commonutils.logger.b.m149a("JOB: " + mo1167a());
            }
            a();
        }
    }

    /* loaded from: classes4.dex */
    class j extends i {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public j() {
            super(5);
            XMPushService.this = r1;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "ask the job queue to quit";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            XMPushService.this.m.a();
        }
    }

    /* loaded from: classes4.dex */
    class k extends i {
        private ge c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public k(ge geVar) {
            super(8);
            XMPushService.this = r1;
            this.c = null;
            this.c = geVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "receive a message.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            XMPushService.this.l.a(this.c);
        }
    }

    /* loaded from: classes4.dex */
    public interface l {
        void a();
    }

    /* loaded from: classes4.dex */
    public class m extends i {
        boolean b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public m(boolean z) {
            super(4);
            XMPushService.this = r1;
            this.b = z;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "send ping..";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            if (XMPushService.this.c()) {
                try {
                    if (!this.b) {
                        hb.a();
                    }
                    XMPushService.this.j.b(this.b);
                } catch (fy e) {
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    XMPushService.this.a(10, e);
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    public class n extends i {
        al.b b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public n(al.b bVar) {
            super(4);
            XMPushService.this = r1;
            this.b = null;
            this.b = bVar;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "rebind the client. " + this.b.g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            try {
                this.b.a(al.c.unbind, 1, 16, (String) null, (String) null);
                XMPushService.this.j.a(this.b.g, this.b.b);
                this.b.a(al.c.binding, 1, 16, (String) null, (String) null);
                XMPushService.this.j.a(this.b);
            } catch (fy e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
                XMPushService.this.a(10, e);
            }
        }
    }

    /* loaded from: classes4.dex */
    public class o extends i {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        o() {
            super(3);
            XMPushService.this = r1;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "reset the connection.";
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            XMPushService.this.a(11, (Exception) null);
            if (XMPushService.this.m1118a()) {
                XMPushService.this.o();
            }
        }
    }

    /* loaded from: classes4.dex */
    public class p extends i {
        al.b b;
        int c;
        String d;
        String e;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public p(al.b bVar, int i, String str, String str2) {
            super(9);
            XMPushService.this = r1;
            this.b = null;
            this.b = bVar;
            this.c = i;
            this.d = str;
            this.e = str2;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public String mo1167a() {
            return "unbind the channel. " + this.b.g;
        }

        @Override // com.xiaomi.push.service.XMPushService.i
        /* renamed from: a */
        public void mo1167a() {
            if (!(this.b.j == al.c.unbind || XMPushService.this.j == null)) {
                try {
                    XMPushService.this.j.a(this.b.g, this.b.b);
                } catch (fy e) {
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    XMPushService.this.a(10, e);
                }
            }
            this.b.a(al.c.unbind, this.c, 0, this.e, this.d);
        }
    }

    static {
        cv.a("cn.app.chat.xiaomi.net", "cn.app.chat.xiaomi.net");
    }

    @TargetApi(11)
    public static Notification a(Context context) {
        Intent intent = new Intent(context, XMPushService.class);
        if (Build.VERSION.SDK_INT >= 11) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(context.getApplicationInfo().icon);
            builder.setContentTitle("Push Service");
            builder.setContentText("Push Service");
            builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
            return builder.getNotification();
        }
        Notification notification = new Notification();
        try {
            notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class).invoke(notification, context, "Push Service", "Push Service", PendingIntent.getService(context, 0, intent, 0));
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
        return notification;
    }

    private ge a(ge geVar, String str, String str2) {
        StringBuilder sb;
        String str3;
        al a2 = al.a();
        List<String> a3 = a2.m1137a(str);
        if (a3.isEmpty()) {
            sb = new StringBuilder();
            str3 = "open channel should be called first before sending a packet, pkg=";
        } else {
            geVar.o(str);
            str = geVar.k();
            if (TextUtils.isEmpty(str)) {
                str = a3.get(0);
                geVar.l(str);
            }
            al.b a4 = a2.a(str, geVar.m());
            if (!c()) {
                sb = new StringBuilder();
                str3 = "drop a packet as the channel is not connected, chid=";
            } else if (a4 == null || a4.j != al.c.binded) {
                sb = new StringBuilder();
                str3 = "drop a packet as the channel is not opened, chid=";
            } else if (TextUtils.equals(str2, a4.i)) {
                return geVar;
            } else {
                sb = new StringBuilder();
                sb.append("invalid session. ");
                sb.append(str2);
                com.xiaomi.channel.commonutils.logger.b.m149a(sb.toString());
                return null;
            }
        }
        sb.append(str3);
        sb.append(str);
        com.xiaomi.channel.commonutils.logger.b.m149a(sb.toString());
        return null;
    }

    private void a(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
    }

    private void a(Intent intent) {
        String stringExtra = intent.getStringExtra(ap.z);
        String stringExtra2 = intent.getStringExtra(ap.C);
        Bundle bundleExtra = intent.getBundleExtra("ext_packet");
        al a2 = al.a();
        fg fgVar = null;
        if (bundleExtra != null) {
            gd gdVar = (gd) a(new gd(bundleExtra), stringExtra, stringExtra2);
            if (gdVar != null) {
                fgVar = fg.a(gdVar, a2.a(gdVar.k(), gdVar.m()).h);
            } else {
                return;
            }
        } else {
            byte[] byteArrayExtra = intent.getByteArrayExtra("ext_raw_packet");
            if (byteArrayExtra != null) {
                long longExtra = intent.getLongExtra(ap.p, 0L);
                String stringExtra3 = intent.getStringExtra(ap.q);
                String stringExtra4 = intent.getStringExtra("ext_chid");
                al.b a3 = a2.a(stringExtra4, Long.toString(longExtra));
                if (a3 != null) {
                    fg fgVar2 = new fg();
                    try {
                        fgVar2.a(Integer.parseInt(stringExtra4));
                    } catch (NumberFormatException unused) {
                    }
                    fgVar2.a("SECMSG", (String) null);
                    fgVar2.a(longExtra, "xiaomi.com", stringExtra3);
                    fgVar2.a(intent.getStringExtra("ext_pkt_id"));
                    fgVar2.a(byteArrayExtra, a3.h);
                    fgVar = fgVar2;
                }
            }
        }
        if (fgVar != null) {
            c(new u(this, fgVar));
        }
    }

    private void a(Intent intent, int i2) {
        byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
        boolean booleanExtra = intent.getBooleanExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
        ig igVar = new ig();
        try {
            ir.a(igVar, byteArrayExtra);
            aj.a(getApplicationContext()).a((aj.a) new b(igVar, new WeakReference(this), booleanExtra), i2);
        } catch (ix unused) {
            com.xiaomi.channel.commonutils.logger.b.d("aw_ping : send help app ping  error");
        }
    }

    private void a(String str, int i2) {
        Collection<al.b> a2 = al.a().a(str);
        if (a2 != null) {
            for (al.b bVar : a2) {
                if (bVar != null) {
                    a(new p(bVar, i2, null, null));
                }
            }
        }
        al.a().m1139a(str);
    }

    private boolean a(String str, Intent intent) {
        al.b a2 = al.a().a(str, intent.getStringExtra(ap.p));
        boolean z = false;
        if (a2 == null || str == null) {
            return false;
        }
        String stringExtra = intent.getStringExtra(ap.C);
        String stringExtra2 = intent.getStringExtra(ap.v);
        if (!TextUtils.isEmpty(a2.i) && !TextUtils.equals(stringExtra, a2.i)) {
            com.xiaomi.channel.commonutils.logger.b.m149a("session changed. old session=" + a2.i + ", new session=" + stringExtra + " chid = " + str);
            z = true;
        }
        if (stringExtra2.equals(a2.h)) {
            return z;
        }
        com.xiaomi.channel.commonutils.logger.b.m149a("security changed. chid = " + str + " sechash = " + ay.a(stringExtra2));
        return true;
    }

    private al.b b(String str, Intent intent) {
        al.b a2 = al.a().a(str, intent.getStringExtra(ap.p));
        if (a2 == null) {
            a2 = new al.b(this);
        }
        a2.g = intent.getStringExtra(ap.r);
        a2.b = intent.getStringExtra(ap.p);
        a2.c = intent.getStringExtra(ap.t);
        a2.f188a = intent.getStringExtra(ap.z);
        a2.e = intent.getStringExtra(ap.x);
        a2.f = intent.getStringExtra(ap.y);
        a2.f189a = intent.getBooleanExtra(ap.w, false);
        a2.h = intent.getStringExtra(ap.v);
        a2.i = intent.getStringExtra(ap.C);
        a2.d = intent.getStringExtra(ap.u);
        a2.f187a = this.k;
        a2.a((Messenger) intent.getParcelableExtra(ap.G));
        a2.a = getApplicationContext();
        al.a().a(a2);
        return a2;
    }

    private void b(Intent intent) {
        String stringExtra = intent.getStringExtra(ap.z);
        String stringExtra2 = intent.getStringExtra(ap.C);
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("ext_packets");
        gd[] gdVarArr = new gd[parcelableArrayExtra.length];
        intent.getBooleanExtra("ext_encrypt", true);
        for (int i2 = 0; i2 < parcelableArrayExtra.length; i2++) {
            gdVarArr[i2] = new gd((Bundle) parcelableArrayExtra[i2]);
            gdVarArr[i2] = (gd) a(gdVarArr[i2], stringExtra, stringExtra2);
            if (gdVarArr[i2] == null) {
                return;
            }
        }
        al a2 = al.a();
        fg[] fgVarArr = new fg[gdVarArr.length];
        for (int i3 = 0; i3 < gdVarArr.length; i3++) {
            gd gdVar = gdVarArr[i3];
            fgVarArr[i3] = fg.a(gdVar, a2.a(gdVar.k(), gdVar.m()).h);
        }
        c(new bh(this, fgVarArr));
    }

    private void b(boolean z) {
        this.g = System.currentTimeMillis();
        if (c()) {
            if (this.j.m922d() || this.j.e() || at.d(this)) {
                c(new m(z));
                return;
            }
            c(new f(17, null));
        }
        a(true);
    }

    public void c(Intent intent) {
        String str;
        String str2;
        d dVar;
        boolean z;
        int i2;
        i nVar;
        int i3;
        String str3;
        String str4;
        u uVar;
        al a2 = al.a();
        boolean z2 = true;
        if (ap.d.equalsIgnoreCase(intent.getAction()) || ap.j.equalsIgnoreCase(intent.getAction())) {
            String stringExtra = intent.getStringExtra(ap.r);
            if (TextUtils.isEmpty(intent.getStringExtra(ap.v))) {
                str = "security is empty. ignore.";
            } else if (stringExtra != null) {
                boolean a3 = a(stringExtra, intent);
                al.b b2 = b(stringExtra, intent);
                if (!at.b(this)) {
                    dVar = this.k;
                    z = false;
                    i2 = 2;
                } else if (c()) {
                    if (b2.j == al.c.unbind) {
                        nVar = new a(b2);
                    } else if (a3) {
                        nVar = new n(b2);
                    } else if (b2.j == al.c.binding) {
                        str = String.format("the client is binding. %1$s %2$s.", b2.g, al.b.a(b2.b));
                    } else if (b2.j == al.c.binded) {
                        dVar = this.k;
                        z = true;
                        i2 = 0;
                    } else {
                        return;
                    }
                    c(nVar);
                    return;
                } else {
                    a(true);
                    return;
                }
                dVar.a(this, b2, z, i2, null);
                return;
            } else {
                str2 = "channel id is empty, do nothing!";
                com.xiaomi.channel.commonutils.logger.b.d(str2);
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.m149a(str);
        } else if (ap.i.equalsIgnoreCase(intent.getAction())) {
            String stringExtra2 = intent.getStringExtra(ap.z);
            String stringExtra3 = intent.getStringExtra(ap.r);
            String stringExtra4 = intent.getStringExtra(ap.p);
            com.xiaomi.channel.commonutils.logger.b.m149a("Service called close channel chid = " + stringExtra3 + " res = " + al.b.a(stringExtra4));
            if (TextUtils.isEmpty(stringExtra3)) {
                for (String str5 : a2.m1137a(stringExtra2)) {
                    a(str5, 2);
                }
            } else if (TextUtils.isEmpty(stringExtra4)) {
                a(stringExtra3, 2);
            } else {
                a(stringExtra3, stringExtra4, 2, null, null);
            }
        } else if (ap.e.equalsIgnoreCase(intent.getAction())) {
            a(intent);
        } else if (ap.g.equalsIgnoreCase(intent.getAction())) {
            b(intent);
        } else {
            if (ap.f.equalsIgnoreCase(intent.getAction())) {
                ge a4 = a(new gc(intent.getBundleExtra("ext_packet")), intent.getStringExtra(ap.z), intent.getStringExtra(ap.C));
                if (a4 != null) {
                    uVar = new u(this, fg.a(a4, a2.a(a4.k(), a4.m()).h));
                } else {
                    return;
                }
            } else if (ap.h.equalsIgnoreCase(intent.getAction())) {
                ge a5 = a(new gg(intent.getBundleExtra("ext_packet")), intent.getStringExtra(ap.z), intent.getStringExtra(ap.C));
                if (a5 != null) {
                    uVar = new u(this, fg.a(a5, a2.a(a5.k(), a5.m()).h));
                } else {
                    return;
                }
            } else {
                if (ap.k.equals(intent.getAction())) {
                    String stringExtra5 = intent.getStringExtra(ap.r);
                    String stringExtra6 = intent.getStringExtra(ap.p);
                    if (stringExtra5 != null) {
                        com.xiaomi.channel.commonutils.logger.b.m149a("request reset connection from chid = " + stringExtra5);
                        al.b a6 = al.a().a(stringExtra5, stringExtra6);
                        if (a6 != null && a6.h.equals(intent.getStringExtra(ap.v)) && a6.j == al.c.binded) {
                            fn a7 = a();
                            if (a7 == null || !a7.a(System.currentTimeMillis() - 15000)) {
                                nVar = new o();
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    al.b bVar = null;
                    if (ap.l.equals(intent.getAction())) {
                        String stringExtra7 = intent.getStringExtra(ap.z);
                        List<String> a8 = a2.m1137a(stringExtra7);
                        if (a8.isEmpty()) {
                            str4 = "open channel should be called first before update info, pkg=" + stringExtra7;
                        } else {
                            String stringExtra8 = intent.getStringExtra(ap.r);
                            String stringExtra9 = intent.getStringExtra(ap.p);
                            if (TextUtils.isEmpty(stringExtra8)) {
                                stringExtra8 = a8.get(0);
                            }
                            if (TextUtils.isEmpty(stringExtra9)) {
                                Collection<al.b> a9 = a2.a(stringExtra8);
                                if (a9 != null && !a9.isEmpty()) {
                                    bVar = a9.iterator().next();
                                }
                            } else {
                                bVar = a2.a(stringExtra8, stringExtra9);
                            }
                            if (bVar != null) {
                                if (intent.hasExtra(ap.x)) {
                                    bVar.e = intent.getStringExtra(ap.x);
                                }
                                if (intent.hasExtra(ap.y)) {
                                    bVar.f = intent.getStringExtra(ap.y);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    } else if ("com.xiaomi.mipush.REGISTER_APP".equals(intent.getAction())) {
                        if (!as.a(getApplicationContext()).m1143a() || as.a(getApplicationContext()).a() != 0) {
                            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
                            String stringExtra10 = intent.getStringExtra("mipush_app_package");
                            boolean booleanExtra = intent.getBooleanExtra("mipush_env_chanage", false);
                            int intExtra = intent.getIntExtra("mipush_env_type", 1);
                            m.a(this).d(stringExtra10);
                            if (!booleanExtra || "com.xiaomi.xmsf".equals(getPackageName())) {
                                a(byteArrayExtra, stringExtra10);
                                return;
                            }
                            nVar = new bd(this, 14, intExtra, byteArrayExtra, stringExtra10);
                        } else {
                            str4 = "register without being provisioned. " + intent.getStringExtra("mipush_app_package");
                        }
                    } else if ("com.xiaomi.mipush.SEND_MESSAGE".equals(intent.getAction()) || "com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
                        String stringExtra11 = intent.getStringExtra("mipush_app_package");
                        byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                        boolean booleanExtra2 = intent.getBooleanExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
                        if ("com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
                            m.a(this).a(stringExtra11);
                        }
                        a(stringExtra11, byteArrayExtra2, booleanExtra2);
                        return;
                    } else if (at.a.equals(intent.getAction())) {
                        String stringExtra12 = intent.getStringExtra("uninstall_pkg_name");
                        if (stringExtra12 != null && !TextUtils.isEmpty(stringExtra12.trim())) {
                            try {
                                getPackageManager().getPackageInfo(stringExtra12, 0);
                                z2 = false;
                            } catch (PackageManager.NameNotFoundException unused) {
                            }
                            if (!"com.xiaomi.channel".equals(stringExtra12) || al.a().a("1").isEmpty() || !z2) {
                                SharedPreferences sharedPreferences = getSharedPreferences("pref_registered_pkg_names", 0);
                                String string = sharedPreferences.getString(stringExtra12, null);
                                if (!TextUtils.isEmpty(string) && z2) {
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.remove(stringExtra12);
                                    edit.commit();
                                    if (z.d(this, stringExtra12)) {
                                        z.c(this, stringExtra12);
                                    }
                                    z.a((Context) this, stringExtra12);
                                    if (c() && string != null) {
                                        try {
                                            bq.a(this, bq.a(stringExtra12, string));
                                            com.xiaomi.channel.commonutils.logger.b.m149a("uninstall " + stringExtra12 + " msg sent");
                                            return;
                                        } catch (fy e2) {
                                            com.xiaomi.channel.commonutils.logger.b.d("Fail to send Message: " + e2.getMessage());
                                            a(10, e2);
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else {
                                a("1", 0);
                                str4 = "close the miliao channel as the app is uninstalled.";
                            }
                        } else {
                            return;
                        }
                    } else if ("com.xiaomi.mipush.CLEAR_NOTIFICATION".equals(intent.getAction())) {
                        String stringExtra13 = intent.getStringExtra(ap.z);
                        int intExtra2 = intent.getIntExtra(ap.A, -2);
                        if (TextUtils.isEmpty(stringExtra13)) {
                            return;
                        }
                        if (intExtra2 >= -1) {
                            z.a(this, stringExtra13, intExtra2);
                            return;
                        } else {
                            z.a(this, stringExtra13, intent.getStringExtra(ap.E), intent.getStringExtra(ap.F));
                            return;
                        }
                    } else if ("com.xiaomi.mipush.SET_NOTIFICATION_TYPE".equals(intent.getAction())) {
                        String stringExtra14 = intent.getStringExtra(ap.z);
                        String stringExtra15 = intent.getStringExtra(ap.D);
                        if (intent.hasExtra(ap.B)) {
                            i3 = intent.getIntExtra(ap.B, 0);
                            str3 = ay.b(stringExtra14 + i3);
                            z2 = false;
                        } else {
                            str3 = ay.b(stringExtra14);
                            i3 = 0;
                        }
                        if (TextUtils.isEmpty(stringExtra14) || !TextUtils.equals(stringExtra15, str3)) {
                            str2 = "invalid notification for " + stringExtra14;
                            com.xiaomi.channel.commonutils.logger.b.d(str2);
                            return;
                        } else if (z2) {
                            z.c(this, stringExtra14);
                            return;
                        } else {
                            z.b(this, stringExtra14, i3);
                            return;
                        }
                    } else if ("com.xiaomi.mipush.DISABLE_PUSH".equals(intent.getAction())) {
                        String stringExtra16 = intent.getStringExtra("mipush_app_package");
                        if (!TextUtils.isEmpty(stringExtra16)) {
                            m.a(this).b(stringExtra16);
                        }
                        if (!"com.xiaomi.xmsf".equals(getPackageName())) {
                            a(19, (Exception) null);
                            n();
                            stopSelf();
                            return;
                        }
                        return;
                    } else if ("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE".equals(intent.getAction()) || "com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                        String stringExtra17 = intent.getStringExtra("mipush_app_package");
                        byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
                        String stringExtra18 = intent.getStringExtra("mipush_app_id");
                        String stringExtra19 = intent.getStringExtra("mipush_app_token");
                        if ("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                            m.a(this).c(stringExtra17);
                        }
                        if ("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction())) {
                            m.a(this).e(stringExtra17);
                            m.a(this).f(stringExtra17);
                        }
                        if (byteArrayExtra3 == null) {
                            o.a(this, stringExtra17, byteArrayExtra3, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
                            return;
                        }
                        o.b(stringExtra17, byteArrayExtra3);
                        a(new n(this, stringExtra17, stringExtra18, stringExtra19, byteArrayExtra3));
                        if ("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE".equals(intent.getAction()) && this.f == null) {
                            this.f = new e();
                            registerReceiver(this.f, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                            return;
                        }
                        return;
                    } else if ("com.xiaomi.mipush.SEND_TINYDATA".equals(intent.getAction())) {
                        String stringExtra20 = intent.getStringExtra("mipush_app_package");
                        byte[] byteArrayExtra4 = intent.getByteArrayExtra("mipush_payload");
                        hl hlVar = new hl();
                        try {
                            ir.a(hlVar, byteArrayExtra4);
                            hf.a(this).a(hlVar, stringExtra20);
                            return;
                        } catch (ix e3) {
                            com.xiaomi.channel.commonutils.logger.b.a(e3);
                            return;
                        }
                    } else {
                        if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction())) {
                            com.xiaomi.channel.commonutils.logger.b.m149a("Service called on timer");
                            ex.a(false);
                            if (!k()) {
                                return;
                            }
                        } else if ("com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
                            com.xiaomi.channel.commonutils.logger.b.m149a("Service called on check alive.");
                            if (!k()) {
                                return;
                            }
                        } else if ("com.xiaomi.mipush.thirdparty".equals(intent.getAction())) {
                            com.xiaomi.channel.commonutils.logger.b.m149a("on thirdpart push :" + intent.getStringExtra("com.xiaomi.mipush.thirdparty_DESC"));
                            ex.a(this, intent.getIntExtra("com.xiaomi.mipush.thirdparty_LEVEL", 0));
                            return;
                        } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                            j();
                            return;
                        } else if ("action_cr_config".equals(intent.getAction())) {
                            boolean booleanExtra3 = intent.getBooleanExtra("action_cr_event_switch", false);
                            long longExtra = intent.getLongExtra("action_cr_event_frequency", 86400L);
                            boolean booleanExtra4 = intent.getBooleanExtra("action_cr_perf_switch", false);
                            long longExtra2 = intent.getLongExtra("action_cr_perf_frequency", 86400L);
                            boolean booleanExtra5 = intent.getBooleanExtra("action_cr_event_en", true);
                            long longExtra3 = intent.getLongExtra("action_cr_max_file_size", 1048576L);
                            Config build = Config.getBuilder().setEventUploadSwitchOpen(booleanExtra3).setEventUploadFrequency(longExtra).setPerfUploadSwitchOpen(booleanExtra4).setPerfUploadFrequency(longExtra2).setAESKey(bf.a(getApplicationContext())).setEventEncrypted(booleanExtra5).setMaxFileLength(longExtra3).build(getApplicationContext());
                            if (!"com.xiaomi.xmsf".equals(getPackageName()) && longExtra > 0 && longExtra2 > 0 && longExtra3 > 0) {
                                ev.a(getApplicationContext(), build);
                                return;
                            }
                            return;
                        } else if ("action_help_ping".equals(intent.getAction())) {
                            boolean booleanExtra6 = intent.getBooleanExtra("extra_help_ping_switch", false);
                            int intExtra3 = intent.getIntExtra("extra_help_ping_frequency", 0);
                            if (intExtra3 >= 0 && intExtra3 < 30) {
                                com.xiaomi.channel.commonutils.logger.b.c("aw_ping: frquency need > 30s.");
                                intExtra3 = 30;
                            }
                            if (intExtra3 < 0) {
                                booleanExtra6 = false;
                            }
                            com.xiaomi.channel.commonutils.logger.b.m149a("aw_ping: receive a aw_ping message. switch: " + booleanExtra6 + " frequency: " + intExtra3);
                            if (booleanExtra6 && intExtra3 > 0 && !"com.xiaomi.xmsf".equals(getPackageName())) {
                                a(intent, intExtra3);
                                return;
                            }
                            return;
                        } else if ("action_aw_app_logic".equals(intent.getAction())) {
                            d(intent);
                            return;
                        } else {
                            return;
                        }
                        b(false);
                        return;
                    }
                    com.xiaomi.channel.commonutils.logger.b.m149a(str4);
                    return;
                }
                c(nVar);
                return;
            }
            c(uVar);
        }
    }

    private void c(i iVar) {
        this.m.a(iVar);
    }

    private void c(boolean z) {
        try {
            if (!u.m1173a()) {
                return;
            }
            if (z) {
                sendBroadcast(new Intent("miui.intent.action.NETWORK_CONNECTED"));
                for (ae aeVar : (ae[]) this.n.toArray(new ae[0])) {
                    aeVar.a();
                }
                return;
            }
            sendBroadcast(new Intent("miui.intent.action.NETWORK_BLOCKED"));
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private void d(Intent intent) {
        int i2;
        try {
            el.a(getApplicationContext()).a(new ar());
            String stringExtra = intent.getStringExtra("mipush_app_package");
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            if (byteArrayExtra != null) {
                ig igVar = new ig();
                ir.a(igVar, byteArrayExtra);
                String b2 = igVar.b();
                Map<String, String> a2 = igVar.m1036a();
                if (a2 != null) {
                    String str = a2.get("extra_help_aw_info");
                    String str2 = a2.get("extra_aw_app_online_cmd");
                    if (!TextUtils.isEmpty(str2)) {
                        try {
                            i2 = Integer.parseInt(str2);
                        } catch (NumberFormatException unused) {
                            i2 = 0;
                        }
                        if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(b2) && !TextUtils.isEmpty(str)) {
                            el.a(getApplicationContext()).a(this, str, i2, stringExtra, b2);
                        }
                    }
                }
            }
        } catch (ix e2) {
            com.xiaomi.channel.commonutils.logger.b.d("aw_logic: translate fail. " + e2.getMessage());
        }
    }

    private String h() {
        String str;
        ap.a();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Object obj = new Object();
        String str2 = null;
        if ("com.xiaomi.xmsf".equals(getPackageName())) {
            as a2 = as.a(this);
            str = null;
            while (true) {
                if (!TextUtils.isEmpty(str) && a2.a() != 0) {
                    break;
                }
                if (TextUtils.isEmpty(str)) {
                    str = com.xiaomi.push.l.m1112a("ro.miui.region");
                    if (TextUtils.isEmpty(str)) {
                        str = com.xiaomi.push.l.m1112a("ro.product.locale.region");
                    }
                }
                try {
                    synchronized (obj) {
                        obj.wait(100L);
                    }
                } catch (InterruptedException unused) {
                }
            }
        } else {
            str = com.xiaomi.push.l.b();
        }
        if (!TextUtils.isEmpty(str)) {
            a.a(getApplicationContext()).b(str);
            str2 = com.xiaomi.push.l.a(str).name();
        }
        com.xiaomi.channel.commonutils.logger.b.m149a("wait region :" + str2 + " cost = " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        return str2;
    }

    public void i() {
        String str;
        a a2 = a.a(getApplicationContext());
        String a3 = a2.a();
        com.xiaomi.channel.commonutils.logger.b.m149a("region of cache is " + a3);
        if (TextUtils.isEmpty(a3)) {
            a3 = h();
        }
        if (!TextUtils.isEmpty(a3)) {
            this.e = a3;
            a2.a(a3);
            if (com.xiaomi.push.p.Global.name().equals(this.e)) {
                str = "app.chat.global.xiaomi.net";
            } else if (com.xiaomi.push.p.Europe.name().equals(this.e)) {
                str = "fr.app.chat.global.xiaomi.net";
            } else if (com.xiaomi.push.p.Russia.name().equals(this.e)) {
                str = "ru.app.chat.global.xiaomi.net";
            } else if (com.xiaomi.push.p.India.name().equals(this.e)) {
                str = "idmb.app.chat.global.xiaomi.net";
            }
            fo.a(str);
        } else {
            this.e = com.xiaomi.push.p.China.name();
        }
        if (com.xiaomi.push.p.China.name().equals(this.e)) {
            fo.a("cn.app.chat.xiaomi.net");
        }
        if (m()) {
            az azVar = new az(this, 11);
            a(azVar);
            l.a(new bb(this, azVar));
        }
        try {
            if (u.m1173a()) {
                this.k.a(this);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private void j() {
        NetworkInfo networkInfo;
        try {
            networkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            networkInfo = null;
        }
        if (networkInfo != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("network changed,");
            sb.append("[type: " + networkInfo.getTypeName() + "[" + networkInfo.getSubtypeName() + "], state: " + networkInfo.getState() + "/" + networkInfo.getDetailedState());
            com.xiaomi.channel.commonutils.logger.b.m149a(sb.toString());
            NetworkInfo.State state = networkInfo.getState();
            if (state == NetworkInfo.State.SUSPENDED || state == NetworkInfo.State.UNKNOWN) {
                return;
            }
        } else {
            com.xiaomi.channel.commonutils.logger.b.m149a("network changed, no active network");
        }
        if (gz.a() != null) {
            gz.a().a();
        }
        gs.m949a((Context) this);
        this.i.d();
        if (at.b(this)) {
            if (c() && k()) {
                b(false);
            }
            if (!c() && !d()) {
                this.m.a(1);
                a(new d());
            }
            de.a(this).a();
        } else {
            a(new f(2, null));
        }
        n();
    }

    private boolean k() {
        if (System.currentTimeMillis() - this.g < 30000) {
            return false;
        }
        return at.c(this);
    }

    public boolean l() {
        return "com.xiaomi.xmsf".equals(getPackageName()) && Settings.Secure.getInt(getContentResolver(), "EXTREME_POWER_MODE_ENABLE", 0) == 1;
    }

    private boolean m() {
        return "com.xiaomi.xmsf".equals(getPackageName()) || !m.a(this).m1162b(getPackageName());
    }

    public void n() {
        if (!m1118a()) {
            ex.a();
        } else if (!ex.m899a()) {
            ex.a(true);
        }
    }

    public void o() {
        String str;
        fn fnVar = this.j;
        if (fnVar == null || !fnVar.m920b()) {
            fn fnVar2 = this.j;
            if (fnVar2 == null || !fnVar2.m921c()) {
                this.c.b(at.m756a((Context) this));
                p();
                if (this.j == null) {
                    al.a().a(this);
                    c(false);
                    return;
                }
                return;
            }
            str = "try to connect while is connected.";
        } else {
            str = "try to connect while connecting.";
        }
        com.xiaomi.channel.commonutils.logger.b.d(str);
    }

    private void p() {
        try {
            this.i.a(this.p, new am(this));
            this.i.e();
            this.j = this.i;
        } catch (fy e2) {
            com.xiaomi.channel.commonutils.logger.b.a("fail to create Slim connection", e2);
            this.i.b(3, e2);
        }
    }

    private boolean q() {
        if (TextUtils.equals(getPackageName(), "com.xiaomi.xmsf")) {
            return false;
        }
        return ag.a(this).a(hm.ForegroundServiceSwitch.a(), false);
    }

    private void r() {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(h, new Notification());
        } else {
            bindService(new Intent(this, this.f185a), new an(this), 1);
        }
    }

    private void s() {
        synchronized (this.o) {
            this.o.clear();
        }
    }

    public fn a() {
        return this.j;
    }

    /* renamed from: a */
    public d m1117a() {
        return new d();
    }

    public void a(int i2) {
        this.m.a(i2);
    }

    public void a(int i2, Exception exc) {
        StringBuilder sb = new StringBuilder();
        sb.append("disconnect ");
        sb.append(hashCode());
        sb.append(", ");
        fn fnVar = this.j;
        sb.append(fnVar == null ? null : Integer.valueOf(fnVar.hashCode()));
        com.xiaomi.channel.commonutils.logger.b.m149a(sb.toString());
        fn fnVar2 = this.j;
        if (fnVar2 != null) {
            fnVar2.b(i2, exc);
            this.j = null;
        }
        a(7);
        a(4);
        al.a().a(this, i2);
    }

    public void a(fg fgVar) {
        fn fnVar = this.j;
        if (fnVar != null) {
            fnVar.b(fgVar);
            return;
        }
        throw new fy("try send msg while connection is null.");
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar) {
        gz.a().a(fnVar);
        c(true);
        this.d.a();
        Iterator<al.b> it = al.a().m1136a().iterator();
        while (it.hasNext()) {
            a(new a(it.next()));
        }
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar, int i2, Exception exc) {
        gz.a().a(fnVar, i2, exc);
        a(false);
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar, Exception exc) {
        gz.a().a(fnVar, exc);
        c(false);
        a(false);
    }

    public void a(i iVar) {
        a(iVar, 0L);
    }

    public void a(i iVar, long j2) {
        try {
            this.m.a(iVar, j2);
        } catch (IllegalStateException e2) {
            com.xiaomi.channel.commonutils.logger.b.m149a("can't execute job err = " + e2.getMessage());
        }
    }

    public void a(l lVar) {
        synchronized (this.o) {
            this.o.add(lVar);
        }
    }

    public void a(al.b bVar) {
        if (bVar != null) {
            long a2 = bVar.a();
            com.xiaomi.channel.commonutils.logger.b.m149a("schedule rebind job in " + (a2 / 1000));
            a(new a(bVar), a2);
        }
    }

    public void a(String str, String str2, int i2, String str3, String str4) {
        al.b a2 = al.a().a(str, str2);
        if (a2 != null) {
            a(new p(a2, i2, str4, str3));
        }
        al.a().m1140a(str, str2);
    }

    public void a(String str, byte[] bArr, boolean z) {
        Collection<al.b> a2 = al.a().a(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND);
        if (a2.isEmpty()) {
            if (!z) {
                return;
            }
        } else if (a2.iterator().next().j == al.c.binded) {
            a(new bg(this, 4, str, bArr));
            return;
        } else if (!z) {
            return;
        }
        o.b(str, bArr);
    }

    public void a(boolean z) {
        this.d.a(z);
    }

    public void a(byte[] bArr, String str) {
        if (bArr == null) {
            o.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
            com.xiaomi.channel.commonutils.logger.b.m149a("register request without payload");
            return;
        }
        id idVar = new id();
        try {
            ir.a(idVar, bArr);
            if (idVar.a == hh.Registration) {
                ih ihVar = new ih();
                try {
                    ir.a(ihVar, idVar.m1027a());
                    o.a(idVar.b(), bArr);
                    a(new n(this, idVar.b(), ihVar.b(), ihVar.c(), bArr));
                    ew.a(getApplicationContext()).a(idVar.b(), "E100003", ihVar.a(), 6002, "send a register message to server");
                } catch (ix e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    o.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data action error.");
                }
            } else {
                o.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " registration action required.");
                com.xiaomi.channel.commonutils.logger.b.m149a("register request with invalid payload");
            }
        } catch (ix e3) {
            com.xiaomi.channel.commonutils.logger.b.a(e3);
            o.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data container error.");
        }
    }

    public void a(fg[] fgVarArr) {
        fn fnVar = this.j;
        if (fnVar != null) {
            fnVar.a(fgVarArr);
            return;
        }
        throw new fy("try send msg while connection is null.");
    }

    /* renamed from: a */
    public boolean m1118a() {
        return at.b(this) && al.a().m1135a() > 0 && !m1120b() && m() && !l();
    }

    /* renamed from: a */
    public boolean m1119a(int i2) {
        return this.m.m1158a(i2);
    }

    public d b() {
        return this.k;
    }

    @Override // com.xiaomi.push.fq
    public void b(fn fnVar) {
        com.xiaomi.channel.commonutils.logger.b.c("begin to connect...");
        gz.a().b(fnVar);
    }

    public void b(i iVar) {
        this.m.a(iVar.a, iVar);
    }

    /* renamed from: b */
    public boolean m1120b() {
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            Field field = cls.getField("IS_CM_CUSTOMIZATION_TEST");
            Field field2 = cls.getField("IS_CU_CUSTOMIZATION_TEST");
            Field field3 = cls.getField("IS_CT_CUSTOMIZATION_TEST");
            if (!field.getBoolean(null) && !field2.getBoolean(null)) {
                if (!field3.getBoolean(null)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public boolean c() {
        fn fnVar = this.j;
        return fnVar != null && fnVar.m921c();
    }

    public boolean d() {
        fn fnVar = this.j;
        return fnVar != null && fnVar.m920b();
    }

    public void e() {
        if (System.currentTimeMillis() - this.g >= ft.a() && at.c(this)) {
            b(true);
        }
    }

    public void f() {
        Iterator it = new ArrayList(this.o).iterator();
        while (it.hasNext()) {
            ((l) it.next()).a();
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.b.getBinder();
    }

    @Override // android.app.Service
    public void onCreate() {
        Uri uriFor;
        super.onCreate();
        u.m1172a((Context) this);
        k a2 = l.a((Context) this);
        if (a2 != null) {
            ac.a(a2.a);
        }
        this.b = new Messenger(new ao(this));
        aq.a(this);
        this.c = new av(this, null, 5222, "xiaomi.com", null);
        this.c.a(true);
        this.i = new fl(this, this.c);
        this.k = m1117a();
        ex.a(this);
        this.i.a(this);
        this.l = new ak(this);
        this.d = new t(this);
        new e().a();
        gz.m951a().a(this);
        this.m = new g("Connection Controller Thread");
        al a3 = al.a();
        a3.b();
        a3.a(new aw(this));
        if (q()) {
            r();
        }
        hf.a(this).a(new i(this), "UPLOADER_PUSH_CHANNEL");
        a(new hc(this));
        a(new g());
        this.n.add(bc.a(this));
        if (m()) {
            this.f = new e();
            registerReceiver(this.f, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        if ("com.xiaomi.xmsf".equals(getPackageName()) && (uriFor = Settings.Secure.getUriFor("EXTREME_POWER_MODE_ENABLE")) != null) {
            this.q = new ay(this, new Handler(Looper.getMainLooper()));
            try {
                getContentResolver().registerContentObserver(uriFor, false, this.q);
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.m149a("register observer err:" + th.getMessage());
            }
        }
        com.xiaomi.channel.commonutils.logger.b.m149a("XMPushService created pid = " + h);
    }

    @Override // android.app.Service
    public void onDestroy() {
        e eVar = this.f;
        if (eVar != null) {
            a(eVar);
            this.f = null;
        }
        if ("com.xiaomi.xmsf".equals(getPackageName()) && this.q != null) {
            try {
                getContentResolver().unregisterContentObserver(this.q);
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.m149a("unregister observer err:" + th.getMessage());
            }
        }
        this.n.clear();
        this.m.b();
        a(new ai(this, 2));
        a(new j());
        al.a().b();
        al.a().a(this, 15);
        al.a().m1138a();
        this.i.b(this);
        ba.a().b();
        ex.a();
        s();
        super.onDestroy();
        com.xiaomi.channel.commonutils.logger.b.m149a("Service destroyed");
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i2) {
        h hVar;
        long currentTimeMillis = System.currentTimeMillis();
        if (intent == null) {
            com.xiaomi.channel.commonutils.logger.b.d("onStart() with intent NULL");
        } else {
            com.xiaomi.channel.commonutils.logger.b.c(String.format("onStart() with intent.Action = %s, chid = %s, pkg = %s|%s, from-bridge = %s", intent.getAction(), intent.getStringExtra(ap.r), intent.getStringExtra(ap.z), intent.getStringExtra("mipush_app_package"), intent.getStringExtra("ext_is_xmpushservice_bridge")));
        }
        if (!(intent == null || intent.getAction() == null)) {
            if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction()) || "com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
                if (this.m.m1157a()) {
                    com.xiaomi.channel.commonutils.logger.b.d("ERROR, the job controller is blocked.");
                    al.a().a(this, 14);
                    stopSelf();
                } else {
                    hVar = new h(intent);
                    a(hVar);
                }
            } else if (!"com.xiaomi.push.network_status_changed".equalsIgnoreCase(intent.getAction())) {
                hVar = new h(intent);
                a(hVar);
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (currentTimeMillis2 > 50) {
            com.xiaomi.channel.commonutils.logger.b.c("[Prefs] spend " + currentTimeMillis2 + " ms, too more times.");
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        onStart(intent, i3);
        return a;
    }
}
