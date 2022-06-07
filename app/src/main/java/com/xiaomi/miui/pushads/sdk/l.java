package com.xiaomi.miui.pushads.sdk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.push.ce;
import com.xiaomi.push.cf;
import com.xiaomi.push.ch;
import com.xiaomi.push.ck;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class l extends MiPushClient.MiPushClientCallback implements g {
    static final /* synthetic */ boolean a = !l.class.desiredAssertionStatus();
    private static l b;
    private Context c;
    private m d;
    private String e;
    private e f;
    private Handler g;
    private int h;
    private int i;
    private String j;
    private SharedPreferences k;
    private String l;
    private ck m;
    private ce n;

    private PendingIntent a(h hVar, int i) {
        PendingIntent a2;
        Intent intent = new Intent(this.c, MiPushRelayTraceService.class);
        Bundle bundle = new Bundle();
        bundle.putAll(hVar.a());
        bundle.putInt("intenttype", i);
        if (!(i != 2 || this.f == null || (a2 = this.f.a(new h(hVar))) == null)) {
            bundle.putParcelable("pendingintent", a2);
        }
        intent.putExtras(bundle);
        int i2 = (int) hVar.f16a;
        return PendingIntent.getService(this.c, (i2 * i2) + i, intent, 134217728);
    }

    public static synchronized l a() {
        l lVar;
        synchronized (l.class) {
            lVar = b;
        }
        return lVar;
    }

    private void a(h hVar) {
        Bitmap decodeFile;
        a("sdk handle notify");
        int hashCode = hVar.a.hashCode() + hVar.d.hashCode();
        int a2 = this.f.a();
        Notification.Builder builder = new Notification.Builder(this.c);
        if (a2 != 0) {
            builder.setSmallIcon(a2);
        }
        b bVar = new b(this.c);
        bVar.a(hVar.d, hVar.e);
        bVar.a(a2);
        a(hVar, hashCode, bVar);
        builder.setContent(bVar);
        builder.setTicker(hVar.c).setAutoCancel(true);
        builder.setContentIntent(a(hVar, 2));
        builder.setDeleteIntent(a(hVar, 1));
        Notification build = builder.build();
        if (!TextUtils.isEmpty(hVar.a()) && (decodeFile = BitmapFactory.decodeFile(hVar.a())) != null) {
            a("big picture");
            c cVar = new c(this.c);
            cVar.a(hVar.d, hVar.e);
            cVar.a(a2);
            cVar.a(decodeFile);
            a(hVar, hashCode, cVar);
            build.bigContentView = cVar;
        }
        ((NotificationManager) this.c.getSystemService("notification")).notify(hashCode, build);
    }

    private void a(h hVar, int i, b bVar) {
        e eVar;
        PendingIntent b2 = (hVar.g == null || TextUtils.isEmpty(hVar.g.trim()) || (eVar = this.f) == null) ? null : eVar.b(new h(hVar));
        if (b2 != null) {
            Intent intent = new Intent(this.c, MiPushRelayTraceService.class);
            Bundle bundle = new Bundle();
            bundle.putAll(hVar.a());
            bundle.putInt("intenttype", 2);
            bundle.putInt("notifyid", i);
            bundle.putParcelable("pendingintent", b2);
            intent.putExtras(bundle);
            int i2 = (int) hVar.f16a;
            bVar.a(hVar.g, PendingIntent.getService(this.c, (i2 * i2) + 3, intent, 134217728));
        }
    }

    private void a(cf cfVar) {
        if (this.m != null) {
            a(this.j + "--->receivedT " + cfVar.f16a);
            this.m.c(new ch(cfVar));
        }
    }

    public static void a(String str) {
        Log.d("ads-notify-fd5dfce4", str);
    }

    private void a(String str, int i, String str2) {
        new n(this.c, this.k, str, i, str2, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
    }

    private void a(String str, long j, int i) {
        this.i++;
        j.a("存入cache 的数量: " + this.i);
        m mVar = this.d;
        if (mVar != null) {
            mVar.a(str, j, i);
            this.d.a();
        }
    }

    private void a(String str, String str2) {
        a(str, 0, str2);
    }

    private void b(cf cfVar) {
        a(cfVar);
        if (cfVar.a == 1) {
            a aVar = (a) cfVar;
            e eVar = this.f;
            if (eVar != null) {
                eVar.a(aVar);
            }
        } else if (cfVar.a == 2) {
            h hVar = (h) cfVar;
            try {
                a(this.j + "--->get notify");
                if (this.f != null) {
                    if (!this.f.m748a(new h(hVar))) {
                        a(hVar);
                    }
                }
            } catch (Exception unused) {
                Log.e("ads-notify-fd5dfce4", "SDK 发出notification 失败");
            }
        }
    }

    private boolean b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("showType") != 1000) {
                return false;
            }
            if (this.n != null) {
                this.n.a(jSONObject.optString("content"));
                return true;
            }
            Log.e("ads-notify-fd5dfce4", "接受到外部的消息，但是外部的listener");
            return true;
        } catch (JSONException unused) {
            return true;
        }
    }

    private boolean c(cf cfVar) {
        int i;
        int i2;
        String str;
        SharedPreferences sharedPreferences;
        if (cfVar.c <= 0) {
            a("white user");
            return true;
        }
        switch (cfVar.a) {
            case 1:
                i = cfVar.c * 4;
                a("bubble uplimit: " + i);
                sharedPreferences = this.k;
                str = "bubblecount";
                i2 = sharedPreferences.getInt(str, 0);
                break;
            case 2:
                i = cfVar.c;
                a("notify uplimit: " + i);
                sharedPreferences = this.k;
                str = "notifycount";
                i2 = sharedPreferences.getInt(str, 0);
                break;
            default:
                i = 0;
                i2 = 0;
                break;
        }
        if (i2 <= i) {
            return true;
        }
        a("reach up limit---already count： " + i2 + " 上限: " + i);
        return false;
    }

    public synchronized int a(int i) {
        int i2;
        SharedPreferences sharedPreferences;
        String str;
        i2 = 0;
        try {
            if (i == 2) {
                sharedPreferences = this.k;
                str = "notifycount";
            } else if (i == 1) {
                sharedPreferences = this.k;
                str = "bubblecount";
            }
            i2 = sharedPreferences.getInt(str, 0);
        } catch (Throwable th) {
            throw th;
        }
        return i2;
    }

    /* renamed from: a */
    public synchronized void m751a(int i) {
        SharedPreferences.Editor putInt;
        try {
            if (i == 2) {
                putInt = this.k.edit().putInt("notifycount", this.k.getInt("notifycount", 0) + 1);
            } else if (i == 1) {
                putInt = this.k.edit().putInt("bubblecount", this.k.getInt("bubblecount", 0) + 1);
            }
            putInt.commit();
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00e6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // com.xiaomi.miui.pushads.sdk.g
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(int r4, com.xiaomi.push.cf r5, com.xiaomi.miui.pushads.sdk.n r6) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miui.pushads.sdk.l.a(int, com.xiaomi.push.cf, com.xiaomi.miui.pushads.sdk.n):void");
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onCommandResult(String str, long j, String str2, List<String> list) {
        a(this.j + "--->onCommandResult == " + str + " resultCode: " + j + " reason: " + str2);
        for (int i = 0; i < list.size(); i++) {
            a("param: " + list.get(i));
        }
        if (TextUtils.equals(MiPushClient.COMMAND_SET_ALIAS, str)) {
            boolean z = false;
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (TextUtils.equals(this.e, list.get(i2))) {
                    a(this.j + "--->alias ok: ");
                    z = true;
                }
            }
            if (!z) {
                a(this.j + "--->alias failed, retry: ");
            }
        }
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onInitializeResult(long j, String str, String str2) {
        if (this.f != null) {
            Message obtainMessage = this.g.obtainMessage();
            obtainMessage.what = 4;
            obtainMessage.arg1 = (int) j;
            obtainMessage.obj = str2;
            this.g.sendMessage(obtainMessage);
        }
        if (0 == j) {
            a(this.j + "--->cahnel OK");
            this.g.sendEmptyMessage(3);
            if (!f.a(this.l)) {
                this.g.sendEmptyMessage(6);
            }
            this.g.sendEmptyMessage(5);
            return;
        }
        a(this.j + "--->chanle failed， need app reopen");
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onReceiveMessage(String str, String str2, String str3, boolean z) {
        a(this.j + "--->##" + str3);
        if (f.a(this.e) && f.a(this.l)) {
            a(this.j + "--->no alias，ignore the msg " + str + "##" + str3);
        } else if (!f.a(str2) && !f.a(this.e) && !TextUtils.equals(this.e, str2)) {
            a(this.j + "--->get msg for different alias. unset " + str + "##" + str3);
            MiPushClient.unsetAlias(this.c, str2, getCategory());
        } else if (!f.a(str3) && !f.a(this.l) && !TextUtils.equals(this.l, str3)) {
            a(this.j + "--->get msg for old topic, unset " + str + "##" + str3);
            MiPushClient.unsubscribe(this.c, str3, getCategory());
        } else if (!b(str)) {
            a(str, this.j);
        }
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onSubscribeResult(long j, String str, String str2) {
        a(this.j + "--->topic resultCode: " + j + " reason: " + str + " topic: " + str2);
        if (j != 0) {
            this.g.sendEmptyMessageDelayed(6, 3600000L);
        }
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onUnsubscribeResult(long j, String str, String str2) {
        a(this.j + "--->unsuscribe topic resultCode: " + j + " reason: " + str + " topic: " + str2);
    }
}
