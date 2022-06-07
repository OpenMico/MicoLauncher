package com.umeng.umzid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.onetrack.OneTrack;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ZIDManager {
    public static ZIDManager c;
    public boolean a = false;
    public boolean b = false;

    /* loaded from: classes2.dex */
    public class a implements Runnable {
        public final /* synthetic */ Context a;
        public final /* synthetic */ IZIDCompletionCallback b;

        public a(Context context, IZIDCompletionCallback iZIDCompletionCallback) {
            this.a = context;
            this.b = iZIDCompletionCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            String a = ZIDManager.a(ZIDManager.this, this.a);
            if (TextUtils.isEmpty(a)) {
                IZIDCompletionCallback iZIDCompletionCallback = this.b;
                if (iZIDCompletionCallback != null) {
                    iZIDCompletionCallback.onFailure("1002", "获取zid失败");
                    return;
                }
                return;
            }
            IZIDCompletionCallback iZIDCompletionCallback2 = this.b;
            if (iZIDCompletionCallback2 != null) {
                iZIDCompletionCallback2.onSuccess(a);
            }
        }
    }

    /* loaded from: classes2.dex */
    public class b implements Runnable {
        public final /* synthetic */ Context a;

        public b(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            ZIDManager.b(ZIDManager.this, this.a);
        }
    }

    /* loaded from: classes2.dex */
    public class c implements Runnable {
        public final /* synthetic */ Context a;

        public c(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            ZIDManager.a(ZIDManager.this, this.a);
        }
    }

    /* loaded from: classes2.dex */
    public class d implements Runnable {
        public final /* synthetic */ Context a;

        public d(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            ZIDManager.b(ZIDManager.this, this.a);
        }
    }

    public static /* synthetic */ String a(ZIDManager zIDManager, Context context) {
        String str = null;
        if (!zIDManager.a) {
            zIDManager.a = true;
            JSONObject jSONObject = new JSONObject();
            try {
                String id = Spy.getID();
                jSONObject.put("zdata", id);
                String c2 = c.c(context);
                jSONObject.put(com.xiaomi.onetrack.api.b.B, c2);
                String d2 = c.d(context);
                jSONObject.put(OneTrack.Param.OAID, d2);
                zIDManager.a(context, jSONObject);
                String a2 = a.a("https://aaid.umeng.com/api/postZdata", jSONObject.toString());
                if (!TextUtils.isEmpty(a2)) {
                    JSONObject jSONObject2 = new JSONObject(a2);
                    if (Boolean.valueOf(jSONObject2.optBoolean("suc")).booleanValue()) {
                        c.f(context, id);
                        c.a(context, c2);
                        c.b(context, d2);
                        str = jSONObject2.optString("aaid");
                        if (!TextUtils.isEmpty(str)) {
                            c.e(context, str);
                        }
                        String string = jSONObject2.getString("uabc");
                        if (!TextUtils.isEmpty(string)) {
                            c.d(context, string);
                        }
                        String string2 = jSONObject2.getString("resetToken");
                        if (!TextUtils.isEmpty(string2)) {
                            c.c(context, string2);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
            zIDManager.a = false;
        }
        return str;
    }

    public static /* synthetic */ String b(ZIDManager zIDManager, Context context) {
        SharedPreferences a2;
        SharedPreferences a3;
        SharedPreferences a4;
        SharedPreferences a5;
        String str = null;
        if (!zIDManager.b) {
            zIDManager.b = true;
            JSONObject jSONObject = new JSONObject();
            try {
                Object b2 = c.b(context);
                String id = Spy.getID();
                jSONObject.put("zdata", id);
                jSONObject.put("old_zdata", b2);
                String string = (context == null || (a5 = a.a(context)) == null) ? "" : a5.getString(OneTrack.Param.OAID, "");
                String d2 = c.d(context);
                jSONObject.put("old_oaid", string);
                jSONObject.put(OneTrack.Param.OAID, d2);
                String string2 = (context == null || (a4 = a.a(context)) == null) ? "" : a4.getString(com.xiaomi.onetrack.api.b.B, "");
                String c2 = c.c(context);
                jSONObject.put(com.xiaomi.onetrack.api.b.B, c2);
                jSONObject.put("old_mac", string2);
                zIDManager.a(context, jSONObject);
                jSONObject.put("aaid", c.a(context));
                jSONObject.put("uabc", (context == null || (a3 = a.a(context)) == null) ? "" : a3.getString("uabc", ""));
                String string3 = (context == null || (a2 = a.a(context)) == null) ? "" : a2.getString("resetToken", "");
                if (!TextUtils.isEmpty(string3)) {
                    jSONObject.put("resetToken", string3);
                }
                String a6 = a.a("https://aaid.umeng.com/api/updateZdata", jSONObject.toString());
                if (!TextUtils.isEmpty(a6)) {
                    JSONObject jSONObject2 = new JSONObject(a6);
                    if (Boolean.valueOf(jSONObject2.optBoolean("suc")).booleanValue()) {
                        c.f(context, id);
                        c.a(context, c2);
                        c.b(context, d2);
                        str = jSONObject2.optString("aaid");
                        if (!TextUtils.isEmpty(str)) {
                            c.e(context, str);
                        }
                        String string4 = jSONObject2.getString("uabc");
                        if (!TextUtils.isEmpty(string4)) {
                            c.d(context, string4);
                        }
                        String string5 = jSONObject2.getString("resetToken");
                        if (!TextUtils.isEmpty(string5)) {
                            c.c(context, string5);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
            zIDManager.b = false;
        }
        return str;
    }

    public static synchronized ZIDManager getInstance() {
        ZIDManager zIDManager;
        synchronized (ZIDManager.class) {
            if (c == null) {
                c = new ZIDManager();
            }
            zIDManager = c;
        }
        return zIDManager;
    }

    public static String getSDKVersion() {
        return "1.2.2";
    }

    public synchronized String getZID(Context context) {
        SharedPreferences a2;
        if (context == null) {
            return "";
        }
        Context applicationContext = context.getApplicationContext();
        String a3 = c.a(applicationContext);
        if (TextUtils.isEmpty(a3)) {
            b.a(new c(applicationContext));
            return "";
        }
        if (!((applicationContext == null || (a2 = a.a(applicationContext)) == null) ? "" : a2.getString("zdata", null)).equals(Spy.getID())) {
            b.a(new d(applicationContext));
        }
        return a3;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x006d A[Catch: all -> 0x009a, TryCatch #1 {, blocks: (B:5:0x0005, B:8:0x000e, B:11:0x0016, B:14:0x001f, B:17:0x0027, B:19:0x002d, B:21:0x0033, B:23:0x0039, B:24:0x0042, B:26:0x0048, B:29:0x004f, B:31:0x0059, B:32:0x005d, B:33:0x0065, B:35:0x006d, B:36:0x0075, B:38:0x007b, B:41:0x008b), top: B:49:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007b A[Catch: all -> 0x009a, TRY_LEAVE, TryCatch #1 {, blocks: (B:5:0x0005, B:8:0x000e, B:11:0x0016, B:14:0x001f, B:17:0x0027, B:19:0x002d, B:21:0x0033, B:23:0x0039, B:24:0x0042, B:26:0x0048, B:29:0x004f, B:31:0x0059, B:32:0x005d, B:33:0x0065, B:35:0x006d, B:36:0x0075, B:38:0x007b, B:41:0x008b), top: B:49:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void init(android.content.Context r4, java.lang.String r5, com.umeng.umzid.IZIDCompletionCallback r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 != 0) goto L_0x000e
            if (r6 == 0) goto L_0x000c
            java.lang.String r4 = "1001"
            java.lang.String r5 = "传入参数Context为null"
            r6.onFailure(r4, r5)     // Catch: all -> 0x009a
        L_0x000c:
            monitor-exit(r3)
            return
        L_0x000e:
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch: all -> 0x009a
            if (r0 == 0) goto L_0x001f
            if (r6 == 0) goto L_0x001d
            java.lang.String r4 = "1003"
            java.lang.String r5 = "传入参数appkey为空"
            r6.onFailure(r4, r5)     // Catch: all -> 0x009a
        L_0x001d:
            monitor-exit(r3)
            return
        L_0x001f:
            android.content.Context r0 = r4.getApplicationContext()     // Catch: all -> 0x009a
            if (r0 == 0) goto L_0x0042
            if (r5 == 0) goto L_0x0042
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch: all -> 0x009a
            if (r1 != 0) goto L_0x0042
            android.content.SharedPreferences r1 = com.umeng.umzid.a.a(r0)     // Catch: all -> 0x009a
            if (r1 == 0) goto L_0x0042
            android.content.SharedPreferences$Editor r1 = r1.edit()     // Catch: all -> 0x009a
            if (r1 == 0) goto L_0x0042
            java.lang.String r2 = "appkey"
            android.content.SharedPreferences$Editor r5 = r1.putString(r2, r5)     // Catch: all -> 0x009a
            r5.commit()     // Catch: all -> 0x009a
        L_0x0042:
            java.lang.String r5 = com.umeng.umzid.c.a(r0)     // Catch: all -> 0x009a
            if (r5 == 0) goto L_0x005d
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch: all -> 0x009a
            if (r1 == 0) goto L_0x004f
            goto L_0x005d
        L_0x004f:
            com.umeng.umzid.ZIDManager$b r1 = new com.umeng.umzid.ZIDManager$b     // Catch: all -> 0x009a
            r1.<init>(r0)     // Catch: all -> 0x009a
            com.umeng.umzid.b.a(r1)     // Catch: all -> 0x009a
            if (r6 == 0) goto L_0x0065
            r6.onSuccess(r5)     // Catch: all -> 0x009a
            goto L_0x0065
        L_0x005d:
            com.umeng.umzid.ZIDManager$a r5 = new com.umeng.umzid.ZIDManager$a     // Catch: all -> 0x009a
            r5.<init>(r0, r6)     // Catch: all -> 0x009a
            com.umeng.umzid.b.a(r5)     // Catch: all -> 0x009a
        L_0x0065:
            java.lang.String r5 = ""
            android.content.SharedPreferences r6 = com.umeng.umzid.a.a(r4)     // Catch: all -> 0x009a
            if (r6 == 0) goto L_0x0075
            java.lang.String r5 = "uuid"
            java.lang.String r0 = ""
            java.lang.String r5 = r6.getString(r5, r0)     // Catch: all -> 0x009a
        L_0x0075:
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch: all -> 0x009a
            if (r5 == 0) goto L_0x0098
            java.lang.String r5 = ""
            android.content.SharedPreferences r4 = com.umeng.umzid.a.a(r4)     // Catch: all -> 0x009a
            java.util.UUID r6 = java.util.UUID.randomUUID()     // Catch: all -> 0x009a
            java.lang.String r5 = r6.toString()     // Catch: all -> 0x0089
        L_0x0089:
            if (r4 == 0) goto L_0x0098
            android.content.SharedPreferences$Editor r4 = r4.edit()     // Catch: all -> 0x009a
            java.lang.String r6 = "uuid"
            android.content.SharedPreferences$Editor r4 = r4.putString(r6, r5)     // Catch: all -> 0x009a
            r4.commit()     // Catch: all -> 0x009a
        L_0x0098:
            monitor-exit(r3)
            return
        L_0x009a:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.umzid.ZIDManager.init(android.content.Context, java.lang.String, com.umeng.umzid.IZIDCompletionCallback):void");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(35:2|(3:100|3|(2:8|(2:12|13)))|14|(1:19)(1:18)|(1:23)|24|(5:108|26|(2:98|29)|30|(28:32|34|96|35|(2:40|(2:44|45))|46|47|48|104|49|(3:102|51|(5:92|53|(2:56|54)|110|57))|60|(1:62)(1:63)|64|(1:66)|67|90|68|71|106|72|75|94|76|79|(1:83)|84|85))|33|34|96|35|(4:37|38|40|(3:42|44|45))|46|47|48|104|49|(0)|60|(0)(0)|64|(0)|67|90|68|71|106|72|75|94|76|79|(2:81|83)|84|85) */
    /* JADX WARN: Can't wrap try/catch for region: R(37:2|100|3|(2:8|(2:12|13))|14|(1:19)(1:18)|(1:23)|24|(5:108|26|(2:98|29)|30|(28:32|34|96|35|(2:40|(2:44|45))|46|47|48|104|49|(3:102|51|(5:92|53|(2:56|54)|110|57))|60|(1:62)(1:63)|64|(1:66)|67|90|68|71|106|72|75|94|76|79|(1:83)|84|85))|33|34|96|35|(4:37|38|40|(3:42|44|45))|46|47|48|104|49|(0)|60|(0)(0)|64|(0)|67|90|68|71|106|72|75|94|76|79|(2:81|83)|84|85) */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0144, code lost:
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0190, code lost:
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0191, code lost:
        r2.printStackTrace();
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01b3, code lost:
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01b4, code lost:
        r2.printStackTrace();
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01cc, code lost:
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01cd, code lost:
        r2.printStackTrace();
        r2 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x010c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x016b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.json.JSONObject a(android.content.Context r10, org.json.JSONObject r11) {
        /*
            Method dump skipped, instructions count: 488
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.umzid.ZIDManager.a(android.content.Context, org.json.JSONObject):org.json.JSONObject");
    }
}
