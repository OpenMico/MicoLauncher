package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.push.at;
import com.xiaomi.push.db;
import com.xiaomi.push.ew;
import com.xiaomi.push.hh;
import com.xiaomi.push.hi;
import com.xiaomi.push.hl;
import com.xiaomi.push.hm;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.id;
import com.xiaomi.push.ig;
import com.xiaomi.push.ih;
import com.xiaomi.push.in;
import com.xiaomi.push.ir;
import com.xiaomi.push.is;
import com.xiaomi.push.l;
import com.xiaomi.push.service.ag;
import com.xiaomi.push.service.aj;
import com.xiaomi.push.service.ap;
import com.xiaomi.push.service.as;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ay {
    private static ay b = null;
    private static boolean f = false;
    private static final ArrayList<a> g = new ArrayList<>();
    private boolean a;
    private Context c;
    private Messenger e;
    private Handler h;
    private List<Message> i = new ArrayList();
    private boolean j = false;
    private Intent k = null;
    private Integer l = null;
    private String d = null;

    /* loaded from: classes4.dex */
    public static class a<T extends is<T, ?>> {
        T a;
        hh b;
        boolean c;

        a() {
        }
    }

    private ay(Context context) {
        this.a = false;
        this.h = null;
        this.c = context.getApplicationContext();
        this.a = g();
        f = p();
        this.h = new ah(this, Looper.getMainLooper());
        Intent i = i();
        if (i != null) {
            b(i);
        }
    }

    public static synchronized ay a(Context context) {
        ay ayVar;
        synchronized (ay.class) {
            if (b == null) {
                b = new ay(context);
            }
            ayVar = b;
        }
        return ayVar;
    }

    public void a(String str, bd bdVar, boolean z, HashMap<String, String> hashMap) {
        ig igVar;
        String str2;
        if (d.m727a(this.c).m734b() && at.b(this.c)) {
            ig igVar2 = new ig();
            igVar2.a(true);
            Intent h = h();
            if (TextUtils.isEmpty(str)) {
                str = aj.a();
                igVar2.a(str);
                igVar = z ? new ig(str, true) : null;
                synchronized (ao.class) {
                    ao.a(this.c).m722a(str);
                }
            } else {
                igVar2.a(str);
                igVar = z ? new ig(str, true) : null;
            }
            switch (ak.a[bdVar.ordinal()]) {
                case 1:
                    igVar2.c(hr.DisablePushMessage.f67a);
                    igVar.c(hr.DisablePushMessage.f67a);
                    if (hashMap != null) {
                        igVar2.a(hashMap);
                        igVar.a(hashMap);
                    }
                    str2 = "com.xiaomi.mipush.DISABLE_PUSH_MESSAGE";
                    h.setAction(str2);
                    break;
                case 2:
                    igVar2.c(hr.EnablePushMessage.f67a);
                    igVar.c(hr.EnablePushMessage.f67a);
                    if (hashMap != null) {
                        igVar2.a(hashMap);
                        igVar.a(hashMap);
                    }
                    str2 = "com.xiaomi.mipush.ENABLE_PUSH_MESSAGE";
                    h.setAction(str2);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    igVar2.c(hr.ThirdPartyRegUpdate.f67a);
                    if (hashMap != null) {
                        igVar2.a(hashMap);
                        break;
                    }
                    break;
            }
            igVar2.b(d.m727a(this.c).m728a());
            igVar2.d(this.c.getPackageName());
            a((ay) igVar2, hh.Notification, false, (hu) null);
            if (z) {
                igVar.b(d.m727a(this.c).m728a());
                igVar.d(this.c.getPackageName());
                byte[] a2 = ir.a(ar.a(this.c, igVar, hh.Notification, false, this.c.getPackageName(), d.m727a(this.c).m728a()));
                if (a2 != null) {
                    db.a(this.c.getPackageName(), this.c, igVar, hh.Notification, a2.length);
                    h.putExtra("mipush_payload", a2);
                    h.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
                    h.putExtra("mipush_app_id", d.m727a(this.c).m728a());
                    h.putExtra("mipush_app_token", d.m727a(this.c).b());
                    c(h);
                }
            }
            Message obtain = Message.obtain();
            obtain.what = 19;
            int ordinal = bdVar.ordinal();
            obtain.obj = str;
            obtain.arg1 = ordinal;
            this.h.sendMessageDelayed(obtain, 5000L);
        }
    }

    private void b(Intent intent) {
        try {
            if (l.m1113a() || Build.VERSION.SDK_INT < 26) {
                this.c.startService(intent);
            } else {
                d(intent);
            }
        } catch (Exception e) {
            b.a(e);
        }
    }

    private synchronized void c(int i) {
        this.c.getSharedPreferences("mipush_extra", 0).edit().putInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, i).commit();
    }

    private void c(Intent intent) {
        int a2 = ag.a(this.c).a(hm.ServiceBootMode.a(), hi.START.a());
        int f2 = f();
        boolean z = a2 == hi.BIND.a() && f;
        int a3 = (z ? hi.BIND : hi.START).a();
        if (a3 != f2) {
            m725a(a3);
        }
        if (z) {
            d(intent);
        } else {
            b(intent);
        }
    }

    private synchronized void d(Intent intent) {
        if (this.j) {
            Message e = e(intent);
            if (this.i.size() >= 50) {
                this.i.remove(0);
            }
            this.i.add(e);
            return;
        }
        if (this.e == null) {
            Context context = this.c;
            aj ajVar = new aj(this);
            Context context2 = this.c;
            context.bindService(intent, ajVar, 1);
            this.j = true;
            this.i.clear();
            this.i.add(e(intent));
        } else {
            try {
                this.e.send(e(intent));
            } catch (RemoteException unused) {
                this.e = null;
                this.j = false;
            }
        }
    }

    private Message e(Intent intent) {
        Message obtain = Message.obtain();
        obtain.what = 17;
        obtain.obj = intent;
        return obtain;
    }

    private synchronized int f() {
        return this.c.getSharedPreferences("mipush_extra", 0).getInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, -1);
    }

    private boolean g() {
        try {
            PackageInfo packageInfo = this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 105;
        } catch (Throwable unused) {
            return false;
        }
    }

    private Intent h() {
        return (!m724a() || "com.xiaomi.xmsf".equals(this.c.getPackageName())) ? l() : k();
    }

    private Intent i() {
        if (!"com.xiaomi.xmsf".equals(this.c.getPackageName())) {
            return j();
        }
        b.c("pushChannel xmsf create own channel");
        return l();
    }

    private Intent j() {
        if (m724a()) {
            b.c("pushChannel app start miui china channel");
            return k();
        }
        b.c("pushChannel app start  own channel");
        return l();
    }

    private Intent k() {
        Intent intent = new Intent();
        String packageName = this.c.getPackageName();
        intent.setPackage("com.xiaomi.xmsf");
        intent.setClassName("com.xiaomi.xmsf", m());
        intent.putExtra("mipush_app_package", packageName);
        n();
        return intent;
    }

    private Intent l() {
        Intent intent = new Intent();
        String packageName = this.c.getPackageName();
        o();
        intent.setComponent(new ComponentName(this.c, "com.xiaomi.push.service.XMPushService"));
        intent.putExtra("mipush_app_package", packageName);
        return intent;
    }

    private String m() {
        try {
            return this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 106 ? "com.xiaomi.push.service.XMPushService" : "com.xiaomi.xmsf.push.service.XMPushService";
        } catch (Exception unused) {
            return "com.xiaomi.xmsf.push.service.XMPushService";
        }
    }

    private void n() {
        try {
            PackageManager packageManager = this.c.getPackageManager();
            ComponentName componentName = new ComponentName(this.c, "com.xiaomi.push.service.XMPushService");
            if (packageManager.getComponentEnabledSetting(componentName) != 2) {
                packageManager.setComponentEnabledSetting(componentName, 2, 1);
            }
        } catch (Throwable unused) {
        }
    }

    private void o() {
        try {
            PackageManager packageManager = this.c.getPackageManager();
            ComponentName componentName = new ComponentName(this.c, "com.xiaomi.push.service.XMPushService");
            if (packageManager.getComponentEnabledSetting(componentName) != 1) {
                packageManager.setComponentEnabledSetting(componentName, 1, 1);
            }
        } catch (Throwable unused) {
        }
    }

    private boolean p() {
        if (m724a()) {
            try {
                return this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 108;
            } catch (Exception unused) {
            }
        }
        return true;
    }

    private boolean q() {
        String packageName = this.c.getPackageName();
        return packageName.contains(OneTrack.Param.MIUI) || packageName.contains("xiaomi") || (this.c.getApplicationInfo().flags & 1) != 0;
    }

    public void a() {
        b(h());
    }

    public void a(int i) {
        Intent h = h();
        h.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        h.putExtra(ap.z, this.c.getPackageName());
        h.putExtra(ap.A, i);
        c(h);
    }

    public void a(int i, String str) {
        Intent h = h();
        h.setAction("com.xiaomi.mipush.thirdparty");
        h.putExtra("com.xiaomi.mipush.thirdparty_LEVEL", i);
        h.putExtra("com.xiaomi.mipush.thirdparty_DESC", str);
        b(h);
    }

    public void a(Intent intent) {
        intent.fillIn(h(), 24);
        c(intent);
    }

    public final void a(hl hlVar) {
        Intent h = h();
        byte[] a2 = ir.a(hlVar);
        if (a2 == null) {
            b.m149a("send TinyData failed, because tinyDataBytes is null.");
            return;
        }
        h.setAction("com.xiaomi.mipush.SEND_TINYDATA");
        h.putExtra("mipush_payload", a2);
        b(h);
    }

    public final void a(ih ihVar, boolean z) {
        ew.a(this.c.getApplicationContext()).a(this.c.getPackageName(), "E100003", ihVar.a(), 6001, "construct a register message");
        this.k = null;
        d.m727a(this.c).a = ihVar.a();
        Intent h = h();
        byte[] a2 = ir.a(ar.a(this.c, ihVar, hh.Registration));
        if (a2 == null) {
            b.m149a("register fail, because msgBytes is null.");
            return;
        }
        h.setAction("com.xiaomi.mipush.REGISTER_APP");
        h.putExtra("mipush_app_id", d.m727a(this.c).m728a());
        h.putExtra("mipush_payload", a2);
        h.putExtra("mipush_session", this.d);
        h.putExtra("mipush_env_chanage", z);
        h.putExtra("mipush_env_type", d.m727a(this.c).a());
        if (!at.b(this.c) || !m726b()) {
            this.k = h;
        } else {
            c(h);
        }
    }

    public final void a(in inVar) {
        byte[] a2 = ir.a(ar.a(this.c, inVar, hh.UnRegistration));
        if (a2 == null) {
            b.m149a("unregister fail, because msgBytes is null.");
            return;
        }
        Intent h = h();
        h.setAction("com.xiaomi.mipush.UNREGISTER_APP");
        h.putExtra("mipush_app_id", d.m727a(this.c).m728a());
        h.putExtra("mipush_payload", a2);
        c(h);
    }

    public final <T extends is<T, ?>> void a(T t, hh hhVar, hu huVar) {
        a((ay) t, hhVar, !hhVar.equals(hh.Registration), huVar);
    }

    public <T extends is<T, ?>> void a(T t, hh hhVar, boolean z) {
        a aVar = new a();
        aVar.a = t;
        aVar.b = hhVar;
        aVar.c = z;
        synchronized (g) {
            g.add(aVar);
            if (g.size() > 10) {
                g.remove(0);
            }
        }
    }

    public final <T extends is<T, ?>> void a(T t, hh hhVar, boolean z, hu huVar) {
        a(t, hhVar, z, true, huVar, true);
    }

    public final <T extends is<T, ?>> void a(T t, hh hhVar, boolean z, hu huVar, boolean z2) {
        a(t, hhVar, z, true, huVar, z2);
    }

    public final <T extends is<T, ?>> void a(T t, hh hhVar, boolean z, boolean z2, hu huVar, boolean z3) {
        a(t, hhVar, z, z2, huVar, z3, this.c.getPackageName(), d.m727a(this.c).m728a());
    }

    public final <T extends is<T, ?>> void a(T t, hh hhVar, boolean z, boolean z2, hu huVar, boolean z3, String str, String str2) {
        if (d.m727a(this.c).m735c()) {
            id a2 = ar.a(this.c, t, hhVar, z, str, str2);
            if (huVar != null) {
                a2.a(huVar);
            }
            byte[] a3 = ir.a(a2);
            if (a3 == null) {
                b.m149a("send message fail, because msgBytes is null.");
                return;
            }
            db.a(this.c.getPackageName(), this.c, t, hhVar, a3.length);
            Intent h = h();
            h.setAction("com.xiaomi.mipush.SEND_MESSAGE");
            h.putExtra("mipush_payload", a3);
            h.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", z3);
            c(h);
        } else if (z2) {
            a((ay) t, hhVar, z);
        } else {
            b.m149a("drop the message before initialization.");
        }
    }

    public final void a(String str, bd bdVar, f fVar) {
        ao.a(this.c).a(bdVar, "syncing");
        a(str, bdVar, false, j.a(this.c, fVar));
    }

    public void a(String str, String str2) {
        Intent h = h();
        h.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        h.putExtra(ap.z, this.c.getPackageName());
        h.putExtra(ap.E, str);
        h.putExtra(ap.F, str2);
        c(h);
    }

    public final void a(boolean z) {
        a(z, (String) null);
    }

    public final void a(boolean z, String str) {
        bd bdVar;
        if (z) {
            ao.a(this.c).a(bd.DISABLE_PUSH, "syncing");
            ao.a(this.c).a(bd.ENABLE_PUSH, "");
            bdVar = bd.DISABLE_PUSH;
        } else {
            ao.a(this.c).a(bd.ENABLE_PUSH, "syncing");
            ao.a(this.c).a(bd.DISABLE_PUSH, "");
            bdVar = bd.ENABLE_PUSH;
        }
        a(str, bdVar, true, (HashMap<String, String>) null);
    }

    /* renamed from: a */
    public boolean m724a() {
        return this.a && 1 == d.m727a(this.c).a();
    }

    /* renamed from: a */
    public boolean m725a(int i) {
        if (!d.m727a(this.c).m734b()) {
            return false;
        }
        c(i);
        ig igVar = new ig();
        igVar.a(aj.a());
        igVar.b(d.m727a(this.c).m728a());
        igVar.d(this.c.getPackageName());
        igVar.c(hr.ClientABTest.f67a);
        igVar.f129a = new HashMap();
        Map<String, String> map = igVar.f129a;
        map.put("boot_mode", i + "");
        a(this.c).a((ay) igVar, hh.Notification, false, (hu) null);
        return true;
    }

    public final void b() {
        Intent h = h();
        h.setAction("com.xiaomi.mipush.DISABLE_PUSH");
        c(h);
    }

    public void b(int i) {
        Intent h = h();
        h.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        h.putExtra(ap.z, this.c.getPackageName());
        h.putExtra(ap.B, i);
        String str = ap.D;
        h.putExtra(str, com.xiaomi.push.ay.b(this.c.getPackageName() + i));
        c(h);
    }

    /* renamed from: b */
    public boolean m726b() {
        if (!m724a() || !q()) {
            return true;
        }
        if (this.l == null) {
            this.l = Integer.valueOf(as.a(this.c).a());
            if (this.l.intValue() == 0) {
                this.c.getContentResolver().registerContentObserver(as.a(this.c).m1142a(), false, new ai(this, new Handler(Looper.getMainLooper())));
            }
        }
        return this.l.intValue() != 0;
    }

    public void c() {
        Intent intent = this.k;
        if (intent != null) {
            c(intent);
            this.k = null;
        }
    }

    public void d() {
        synchronized (g) {
            Iterator<a> it = g.iterator();
            while (it.hasNext()) {
                a next = it.next();
                a(next.a, next.b, next.c, false, null, true);
            }
            g.clear();
        }
    }

    public void e() {
        Intent h = h();
        h.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        h.putExtra(ap.z, this.c.getPackageName());
        h.putExtra(ap.D, com.xiaomi.push.ay.b(this.c.getPackageName()));
        c(h);
    }
}
