package com.umeng.commonsdk.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.ak;
import com.umeng.analytics.pro.al;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.n;
import com.umeng.analytics.pro.z;
import com.umeng.commonsdk.UMConfigureImpl;
import com.umeng.commonsdk.a;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.listener.OnGetOaidListener;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.b;
import com.umeng.commonsdk.statistics.idtracking.i;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.onMessageSendListener;
import java.io.File;
import java.lang.reflect.Method;
import org.json.JSONObject;

/* compiled from: UMInternalDataProtocol.java */
/* loaded from: classes2.dex */
public class c implements UMLogDataProtocol {
    private static int b = 1;
    private static final String c = "info";
    private static final String d = "stat";
    private static Class<?> e = null;
    private static Method f = null;
    private static Method g = null;
    private static Method h = null;
    private static boolean i = false;
    private Context a;

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public void removeCacheData(Object obj) {
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public JSONObject setupReportData(long j) {
        return null;
    }

    static {
        c();
    }

    private static void c() {
        try {
            Class<?> cls = Class.forName("com.umeng.umzid.ZIDManager");
            if (cls != null) {
                e = cls;
                Method declaredMethod = e.getDeclaredMethod("getInstance", new Class[0]);
                if (declaredMethod != null) {
                    f = declaredMethod;
                }
                Method declaredMethod2 = e.getDeclaredMethod("getZID", Context.class);
                if (declaredMethod2 != null) {
                    g = declaredMethod2;
                }
                Method declaredMethod3 = e.getDeclaredMethod("getSDKVersion", new Class[0]);
                if (declaredMethod3 != null) {
                    h = declaredMethod3;
                }
            }
        } catch (Throwable unused) {
        }
    }

    public String a() {
        Method method;
        Class<?> cls = e;
        if (cls == null || (method = f) == null || g == null) {
            return "";
        }
        try {
            Object invoke = method.invoke(cls, new Object[0]);
            return invoke != null ? (String) g.invoke(invoke, this.a) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String b() {
        Method method;
        Class<?> cls = e;
        if (cls == null || (method = f) == null || h == null) {
            return "";
        }
        try {
            Object invoke = method.invoke(cls, new Object[0]);
            if (invoke != null) {
                return (String) h.invoke(invoke, new Object[0]);
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public c(Context context) {
        if (context != null) {
            this.a = context.getApplicationContext();
        }
    }

    private void a(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appkey", UMGlobalContext.getInstance(context).getAppkey());
            jSONObject.put("app_version", UMGlobalContext.getInstance(context).getAppVersion());
            jSONObject.put(ai.x, "Android");
            JSONObject buildZeroEnvelopeWithExtHeader = UMEnvelopeBuild.buildZeroEnvelopeWithExtHeader(context, jSONObject, null, UMServerURL.ZCFG_PATH);
            if (buildZeroEnvelopeWithExtHeader == null || !buildZeroEnvelopeWithExtHeader.has("exception")) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建零号报文 成功!!!");
            } else {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 构建零号报文失败.");
            }
        } catch (Throwable unused) {
        }
    }

    private void d() {
        ak a = ak.a(this.a);
        al a2 = a.a(am.c);
        if (a2 != null) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建成真正信封。");
            try {
                String str = a2.a;
                String str2 = a2.b;
                JSONObject a3 = new b().a(this.a.getApplicationContext(), new JSONObject(a2.c), new JSONObject(a2.d), a2.e, str2, a2.f);
                if (a3 == null || !a3.has("exception")) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建真正信封 成功! 删除二级缓存记录。");
                } else {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存记录构建真正信封 失败。删除二级缓存记录");
                }
                a.a(am.c, str);
                a.b();
            } catch (Throwable unused) {
            }
        }
    }

    private static void b(final Context context) {
        new Thread(new Runnable() { // from class: com.umeng.commonsdk.internal.c.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(i.a, 0);
                    long currentTimeMillis = System.currentTimeMillis();
                    String a = z.a(context);
                    long currentTimeMillis2 = System.currentTimeMillis();
                    if (!TextUtils.isEmpty(a) && sharedPreferences != null) {
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString(i.c, (currentTimeMillis2 - currentTimeMillis) + "");
                        edit.commit();
                    }
                    if (sharedPreferences != null) {
                        SharedPreferences.Editor edit2 = sharedPreferences.edit();
                        edit2.putString(i.b, a);
                        edit2.commit();
                    }
                    if (Build.VERSION.SDK_INT > 28) {
                        UMConfigureImpl.removeInterruptFlag();
                    }
                } catch (Throwable unused) {
                }
            }
        }).start();
    }

    private static void a(Context context, final OnGetOaidListener onGetOaidListener) {
        if (context != null) {
            final Context applicationContext = context.getApplicationContext();
            new Thread(new Runnable() { // from class: com.umeng.commonsdk.internal.c.2
                @Override // java.lang.Runnable
                public void run() {
                    String a = z.a(applicationContext);
                    OnGetOaidListener onGetOaidListener2 = onGetOaidListener;
                    if (onGetOaidListener2 != null) {
                        onGetOaidListener2.onGetOaid(a);
                    }
                }
            }).start();
        }
    }

    private static void c(final Context context) {
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.G) && Build.VERSION.SDK_INT > 28) {
            a(context, new OnGetOaidListener() { // from class: com.umeng.commonsdk.internal.c.3
                @Override // com.umeng.commonsdk.listener.OnGetOaidListener
                public void onGetOaid(String str) {
                    if (!TextUtils.isEmpty(str)) {
                        try {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(i.a, 0);
                            if (sharedPreferences != null && !sharedPreferences.getString(i.b, "").equalsIgnoreCase(str)) {
                                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 更新本地缓存OAID");
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString(i.b, str);
                                edit.commit();
                            }
                        } catch (Throwable unused) {
                        }
                    }
                }
            });
        }
    }

    private void e() {
        if (!i) {
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.G) && Build.VERSION.SDK_INT > 28) {
                i = true;
                a(this.a, new OnGetOaidListener() { // from class: com.umeng.commonsdk.internal.c.4
                    @Override // com.umeng.commonsdk.listener.OnGetOaidListener
                    public void onGetOaid(String str) {
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> OAID云控参数更新(不采集->采集)：采集完成");
                        if (TextUtils.isEmpty(str)) {
                            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> oaid返回null或者空串，不需要 伪冷启动。");
                            return;
                        }
                        try {
                            SharedPreferences sharedPreferences = c.this.a.getSharedPreferences(i.a, 0);
                            if (sharedPreferences != null) {
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString(i.b, str);
                                edit.commit();
                            }
                        } catch (Throwable unused) {
                        }
                        UMWorkDispatch.sendEvent(c.this.a, a.w, b.a(c.this.a).a(), null);
                    }
                });
            }
        } else if (!FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
            i = false;
        }
    }

    private void f() {
        if (FieldManager.allow(com.umeng.commonsdk.utils.b.G) && Build.VERSION.SDK_INT > 28) {
            i = true;
            UMConfigureImpl.registerInterruptFlag();
            UMConfigureImpl.init(this.a);
            b++;
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 要读取 oaid，需等待读取结果.");
            UMConfigureImpl.registerMessageSendListener(new onMessageSendListener() { // from class: com.umeng.commonsdk.internal.c.5
                @Override // com.umeng.commonsdk.utils.onMessageSendListener
                public void onMessageSend() {
                    if (c.this.a != null) {
                        UMWorkDispatch.sendEvent(c.this.a, a.x, b.a(c.this.a).a(), null);
                    }
                    UMConfigureImpl.removeMessageSendListener(this);
                }
            });
            b(this.a);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00d5 A[Catch: ClassNotFoundException | Exception | Throwable -> 0x02cd, TryCatch #0 {ClassNotFoundException | Exception | Throwable -> 0x02cd, blocks: (B:4:0x0013, B:15:0x008f, B:17:0x00a8, B:19:0x00b3, B:22:0x00ba, B:24:0x00c0, B:26:0x00cb, B:31:0x00d5, B:33:0x00db, B:34:0x00e7, B:35:0x00f0, B:58:0x01d7, B:60:0x01df, B:61:0x01fe, B:63:0x0206, B:65:0x023b, B:65:0x023b, B:67:0x0243, B:67:0x0243, B:69:0x027f, B:69:0x027f, B:71:0x0287, B:71:0x0287, B:72:0x02a5, B:73:0x02b0, B:73:0x02b0, B:73:0x02b0, B:75:0x02b8, B:75:0x02b8, B:75:0x02b8), top: B:78:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f0 A[Catch: ClassNotFoundException | Exception | Throwable -> 0x02cd, TRY_LEAVE, TryCatch #0 {ClassNotFoundException | Exception | Throwable -> 0x02cd, blocks: (B:4:0x0013, B:15:0x008f, B:17:0x00a8, B:19:0x00b3, B:22:0x00ba, B:24:0x00c0, B:26:0x00cb, B:31:0x00d5, B:33:0x00db, B:34:0x00e7, B:35:0x00f0, B:58:0x01d7, B:60:0x01df, B:61:0x01fe, B:63:0x0206, B:65:0x023b, B:65:0x023b, B:67:0x0243, B:67:0x0243, B:69:0x027f, B:69:0x027f, B:71:0x0287, B:71:0x0287, B:72:0x02a5, B:73:0x02b0, B:73:0x02b0, B:73:0x02b0, B:75:0x02b8, B:75:0x02b8, B:75:0x02b8), top: B:78:0x000e }] */
    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void workEvent(java.lang.Object r7, int r8) {
        /*
            Method dump skipped, instructions count: 788
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.c.workEvent(java.lang.Object, int):void");
    }

    private static Class<?> a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private void d(Context context) {
        Object invoke;
        Method declaredMethod;
        Context applicationContext = context.getApplicationContext();
        String appkey = UMUtils.getAppkey(context);
        try {
            Class<?> a = a("com.umeng.umzid.ZIDManager");
            Method declaredMethod2 = a.getDeclaredMethod("getInstance", new Class[0]);
            if (declaredMethod2 != null && (invoke = declaredMethod2.invoke(a, new Object[0])) != null && (declaredMethod = a.getDeclaredMethod("init", Context.class, String.class, a("com.umeng.umzid.IZIDCompletionCallback"))) != null) {
                declaredMethod.invoke(invoke, applicationContext, appkey, null);
            }
        } catch (Throwable unused) {
        }
    }

    private void g() {
        if (b <= 0) {
            h();
            d(this.a);
        }
    }

    private static void e(Context context) {
        File filesDir = context.getFilesDir();
        File file = new File(filesDir.getAbsolutePath() + File.separator + am.l);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Throwable unused) {
            }
        }
    }

    private void h() {
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 真实构建条件满足，开始构建业务信封。");
        if (UMUtils.isMainProgress(this.a)) {
            e(this.a);
            a.a(this.a);
            Context context = this.a;
            UMWorkDispatch.sendEvent(context, n.a.x, CoreProtocol.getInstance(context), null);
            Context context2 = this.a;
            UMWorkDispatch.sendEvent(context2, a.t, b.a(context2).a(), null);
        }
    }
}
