package com.xiaomi.miui.pushads.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.push.cf;
import com.xiaomi.push.cg;
import java.util.List;

/* loaded from: classes4.dex */
public class k extends MiPushClient.MiPushClientCallback implements g {
    private static k a;
    private Context b;
    private m c;
    private String d;
    private cg e;
    private Handler f;
    private int g;
    private int h;
    private String i;
    private SharedPreferences j;

    /* loaded from: classes4.dex */
    public enum a {
        NONE,
        Wifi,
        MN2G,
        MN3G,
        MN4G
    }

    public static synchronized k a() {
        k kVar;
        synchronized (k.class) {
            kVar = a;
        }
        return kVar;
    }

    public static void a(String str) {
        Log.d("ads-notify-fd5dfce4", str);
    }

    private void a(String str, int i, String str2) {
        new n(this.b, this.j, str, i, str2, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
    }

    private void a(String str, long j, int i) {
        this.h++;
        j.a("存入cache 的数量: " + this.h);
        this.c.a(str, j, i);
        this.c.a();
    }

    private void a(String str, String str2) {
        a(str, 0, str2);
    }

    private boolean a(cf cfVar) {
        int i;
        int i2;
        String str;
        SharedPreferences sharedPreferences;
        if (cfVar.c <= 0) {
            a("白名单用户");
            return true;
        }
        switch (cfVar.a) {
            case 1:
                i = cfVar.c * 4;
                a("冒泡上限: " + i);
                sharedPreferences = this.j;
                str = "bubblecount";
                i2 = sharedPreferences.getInt(str, 0);
                break;
            case 2:
                i = cfVar.c;
                a("通知上限: " + i);
                sharedPreferences = this.j;
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
        a("广告次数超过上限---已经获得次数： " + i2 + " 上限: " + i);
        return false;
    }

    public synchronized int a(int i) {
        int i2;
        SharedPreferences sharedPreferences;
        String str;
        i2 = 0;
        try {
            if (i == 2) {
                sharedPreferences = this.j;
                str = "notifycount";
            } else if (i == 1) {
                sharedPreferences = this.j;
                str = "bubblecount";
            }
            i2 = sharedPreferences.getInt(str, 0);
        } catch (Throwable th) {
            throw th;
        }
        return i2;
    }

    /* renamed from: a */
    public synchronized void m750a(int i) {
        SharedPreferences.Editor putInt;
        try {
            if (i == 2) {
                putInt = this.j.edit().putInt("notifycount", this.j.getInt("notifycount", 0) + 1);
            } else if (i == 1) {
                putInt = this.j.edit().putInt("bubblecount", this.j.getInt("bubblecount", 0) + 1);
            }
            putInt.commit();
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00cb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // com.xiaomi.miui.pushads.sdk.g
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(int r4, com.xiaomi.push.cf r5, com.xiaomi.miui.pushads.sdk.n r6) {
        /*
            r3 = this;
            if (r5 != 0) goto L_0x0008
            java.lang.String r4 = "返回广告为null"
            a(r4)
            return
        L_0x0008:
            r6 = -1
            if (r4 != r6) goto L_0x0064
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "广告下载失败: "
            r6.append(r0)
            long r0 = r5.f16a
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            a(r6)
            int r6 = r5.d
            int r6 = r6 + 1
            r5.d = r6
            int r6 = r5.d
            r0 = 10
            if (r6 >= r0) goto L_0x0061
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "下载失败写入缓存 "
            r6.append(r0)
            java.lang.String r0 = r5.h
            r6.append(r0)
            java.lang.String r0 = "  "
            r6.append(r0)
            long r0 = r5.f17b
            r6.append(r0)
            java.lang.String r0 = "  "
            r6.append(r0)
            int r0 = r5.d
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            com.xiaomi.miui.pushads.sdk.j.a(r6)
            java.lang.String r6 = r5.h
            long r0 = r5.f17b
            int r2 = r5.d
            r3.a(r6, r0, r2)
            goto L_0x00c7
        L_0x0061:
            java.lang.String r6 = "下载失败次数超过 10 不写入缓存"
            goto L_0x00c4
        L_0x0064:
            if (r4 != 0) goto L_0x00ac
            int r6 = r5.c
            if (r6 <= 0) goto L_0x0079
            int r6 = r3.g
            int r6 = r6 + 1
            r3.g = r6
            com.xiaomi.miui.pushads.sdk.k r6 = a()
            int r0 = r5.a
            r6.m750a(r0)
        L_0x0079:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "广告下载成功: id: "
            r6.append(r0)
            long r0 = r5.f16a
            r6.append(r0)
            java.lang.String r0 = " 类型: "
            r6.append(r0)
            int r0 = r5.a
            r6.append(r0)
            java.lang.String r0 = " 成功次数: "
            r6.append(r0)
            com.xiaomi.miui.pushads.sdk.k r0 = a()
            int r1 = r5.a
            int r0 = r0.a(r1)
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            a(r6)
            goto L_0x00c7
        L_0x00ac:
            java.lang.String r6 = "com.miui.ads"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "广告无效或者超过限制 "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            android.util.Log.w(r6, r0)
            java.lang.String r6 = "广告无效或者超过限制"
        L_0x00c4:
            com.xiaomi.miui.pushads.sdk.j.a(r6)
        L_0x00c7:
            com.xiaomi.push.cg r6 = r3.e
            if (r6 == 0) goto L_0x00e3
            if (r4 != 0) goto L_0x00e3
            boolean r4 = r3.a(r5)
            if (r4 == 0) goto L_0x00de
            java.lang.String r4 = "===========给APP 发送广告信息"
            a(r4)
            com.xiaomi.push.cg r4 = r3.e
            r4.a(r5)
            goto L_0x00e3
        L_0x00de:
            java.lang.String r4 = "广告数量超过限制，不返回给APP"
            a(r4)
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miui.pushads.sdk.k.a(int, com.xiaomi.push.cf, com.xiaomi.miui.pushads.sdk.n):void");
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onCommandResult(String str, long j, String str2, List<String> list) {
        if (j != 0) {
            a("命令失败: " + str + " code: " + j + " reason: " + str2);
            for (int i = 0; i < list.size(); i++) {
                a("param: " + list.get(i));
            }
        }
        if (TextUtils.equals(MiPushClient.COMMAND_SET_ALIAS, str)) {
            boolean z = false;
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (TextUtils.equals(this.d, list.get(i2))) {
                    a("设置别名成功: ");
                    z = true;
                }
            }
            if (!z) {
                a("设置别名失败，重新设置: ");
                this.f.sendEmptyMessage(2);
            }
        }
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onInitializeResult(long j, String str, String str2) {
        if (this.e != null) {
            Message obtainMessage = this.f.obtainMessage();
            obtainMessage.what = 4;
            obtainMessage.arg1 = (int) j;
            obtainMessage.obj = str2;
            this.f.sendMessage(obtainMessage);
        }
        if (0 == j) {
            a("通道进行初始化OK");
            this.f.sendEmptyMessage(3);
            this.f.sendEmptyMessage(5);
            return;
        }
        a("通道初始化失败， 已经通知了app，需要重新 open 通道");
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onReceiveMessage(String str, String str2, String str3, boolean z) {
        a("接受到消息 " + str + "##" + str3 + "##");
        if (f.a(this.d)) {
            a("没有有效alias，忽略消息 " + str + "##" + str3 + "##");
        } else if (f.a(str2) || f.a(this.d) || TextUtils.equals(this.d, str2)) {
            a(str, this.i);
        } else {
            a("接受到不同alias 的消息，注销旧的 " + str + "##" + str3 + "##");
            MiPushClient.unsetAlias(this.b, str2, getCategory());
        }
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onSubscribeResult(long j, String str, String str2) {
    }

    @Override // com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback
    public void onUnsubscribeResult(long j, String str, String str2) {
    }
}
