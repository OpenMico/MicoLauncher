package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.DevInfoKeys;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.g;
import com.xiaomi.push.i;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {
    private static volatile d b;
    String a;
    private Context c;
    private a d;
    private Map<String, a> e;

    /* loaded from: classes4.dex */
    public static class a {

        /* renamed from: a */
        public String f0a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        private Context i;

        /* renamed from: a */
        public boolean f1a = true;

        /* renamed from: b */
        public boolean f2b = false;
        public int a = 1;

        public a(Context context) {
            this.i = context;
        }

        public static a a(Context context, String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                a aVar = new a(context);
                aVar.f0a = jSONObject.getString("appId");
                aVar.b = jSONObject.getString("appToken");
                aVar.c = jSONObject.getString("regId");
                aVar.d = jSONObject.getString("regSec");
                aVar.f = jSONObject.getString(DevInfoKeys.DEVICEID);
                aVar.e = jSONObject.getString("vName");
                aVar.f1a = jSONObject.getBoolean("valid");
                aVar.f2b = jSONObject.getBoolean("paused");
                aVar.a = jSONObject.getInt("envType");
                aVar.g = jSONObject.getString("regResource");
                return aVar;
            } catch (Throwable th) {
                b.a(th);
                return null;
            }
        }

        public static String a(a aVar) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("appId", aVar.f0a);
                jSONObject.put("appToken", aVar.b);
                jSONObject.put("regId", aVar.c);
                jSONObject.put("regSec", aVar.d);
                jSONObject.put(DevInfoKeys.DEVICEID, aVar.f);
                jSONObject.put("vName", aVar.e);
                jSONObject.put("valid", aVar.f1a);
                jSONObject.put("paused", aVar.f2b);
                jSONObject.put("envType", aVar.a);
                jSONObject.put("regResource", aVar.g);
                return jSONObject.toString();
            } catch (Throwable th) {
                b.a(th);
                return null;
            }
        }

        private String c() {
            Context context = this.i;
            return g.m929a(context, context.getPackageName());
        }

        public void a() {
            d.a(this.i).edit().clear().commit();
            this.f0a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.f = null;
            this.e = null;
            this.f1a = false;
            this.f2b = false;
            this.h = null;
            this.a = 1;
        }

        public void a(int i) {
            this.a = i;
        }

        public void a(String str, String str2) {
            this.c = str;
            this.d = str2;
            this.f = i.l(this.i);
            this.e = c();
            this.f1a = true;
        }

        public void a(String str, String str2, String str3) {
            this.f0a = str;
            this.b = str2;
            this.g = str3;
            SharedPreferences.Editor edit = d.a(this.i).edit();
            edit.putString("appId", this.f0a);
            edit.putString("appToken", str2);
            edit.putString("regResource", str3);
            edit.commit();
        }

        public void a(boolean z) {
            this.f2b = z;
        }

        /* renamed from: a */
        public boolean m738a() {
            return m739a(this.f0a, this.b);
        }

        /* renamed from: a */
        public boolean m739a(String str, String str2) {
            return TextUtils.equals(this.f0a, str) && TextUtils.equals(this.b, str2) && !TextUtils.isEmpty(this.c) && !TextUtils.isEmpty(this.d) && (TextUtils.equals(this.f, i.l(this.i)) || TextUtils.equals(this.f, i.k(this.i)));
        }

        public void b() {
            this.f1a = false;
            d.a(this.i).edit().putBoolean("valid", this.f1a).commit();
        }

        public void b(String str, String str2, String str3) {
            this.c = str;
            this.d = str2;
            this.f = i.l(this.i);
            this.e = c();
            this.f1a = true;
            this.h = str3;
            SharedPreferences.Editor edit = d.a(this.i).edit();
            edit.putString("regId", str);
            edit.putString("regSec", str2);
            edit.putString(DevInfoKeys.DEVICEID, this.f);
            edit.putString("vName", c());
            edit.putBoolean("valid", true);
            edit.putString("appRegion", str3);
            edit.commit();
        }

        public void c(String str, String str2, String str3) {
            this.f0a = str;
            this.b = str2;
            this.g = str3;
        }
    }

    private d(Context context) {
        this.c = context;
        g();
    }

    public static SharedPreferences a(Context context) {
        return context.getSharedPreferences("mipush", 0);
    }

    /* renamed from: a */
    public static d m727a(Context context) {
        if (b == null) {
            synchronized (d.class) {
                if (b == null) {
                    b = new d(context);
                }
            }
        }
        return b;
    }

    private void g() {
        this.d = new a(this.c);
        this.e = new HashMap();
        SharedPreferences a2 = a(this.c);
        this.d.f0a = a2.getString("appId", null);
        this.d.b = a2.getString("appToken", null);
        this.d.c = a2.getString("regId", null);
        this.d.d = a2.getString("regSec", null);
        this.d.f = a2.getString(DevInfoKeys.DEVICEID, null);
        if (!TextUtils.isEmpty(this.d.f) && this.d.f.startsWith("a-")) {
            this.d.f = i.l(this.c);
            a2.edit().putString(DevInfoKeys.DEVICEID, this.d.f).commit();
        }
        this.d.e = a2.getString("vName", null);
        this.d.f1a = a2.getBoolean("valid", true);
        this.d.f2b = a2.getBoolean("paused", false);
        this.d.a = a2.getInt("envType", 1);
        this.d.g = a2.getString("regResource", null);
        this.d.h = a2.getString("appRegion", null);
    }

    public int a() {
        return this.d.a;
    }

    public a a(String str) {
        if (this.e.containsKey(str)) {
            return this.e.get(str);
        }
        String str2 = "hybrid_app_info_" + str;
        SharedPreferences a2 = a(this.c);
        if (!a2.contains(str2)) {
            return null;
        }
        a a3 = a.a(this.c, a2.getString(str2, ""));
        this.e.put(str2, a3);
        return a3;
    }

    /* renamed from: a */
    public String m728a() {
        return this.d.f0a;
    }

    /* renamed from: a */
    public void m729a() {
        this.d.a();
    }

    public void a(int i) {
        this.d.a(i);
        a(this.c).edit().putInt("envType", i).commit();
    }

    /* renamed from: a */
    public void m730a(String str) {
        SharedPreferences.Editor edit = a(this.c).edit();
        edit.putString("vName", str);
        edit.commit();
        this.d.e = str;
    }

    public void a(String str, a aVar) {
        this.e.put(str, aVar);
        String a2 = a.a(aVar);
        a(this.c).edit().putString("hybrid_app_info_" + str, a2).commit();
    }

    public void a(String str, String str2, String str3) {
        this.d.a(str, str2, str3);
    }

    public void a(boolean z) {
        this.d.a(z);
        a(this.c).edit().putBoolean("paused", z).commit();
    }

    /* renamed from: a */
    public boolean m731a() {
        Context context = this.c;
        return !TextUtils.equals(g.m929a(context, context.getPackageName()), this.d.e);
    }

    public boolean a(String str, String str2) {
        return this.d.m739a(str, str2);
    }

    /* renamed from: a */
    public boolean m732a(String str, String str2, String str3) {
        a a2 = a(str3);
        return a2 != null && TextUtils.equals(str, a2.f0a) && TextUtils.equals(str2, a2.b);
    }

    public String b() {
        return this.d.b;
    }

    /* renamed from: b */
    public void m733b() {
        this.d.b();
    }

    public void b(String str) {
        this.e.remove(str);
        a(this.c).edit().remove("hybrid_app_info_" + str).commit();
    }

    public void b(String str, String str2, String str3) {
        this.d.b(str, str2, str3);
    }

    /* renamed from: b */
    public boolean m734b() {
        if (this.d.m738a()) {
            return true;
        }
        b.m149a("Don't send message before initialization succeeded!");
        return false;
    }

    public String c() {
        return this.d.c;
    }

    /* renamed from: c */
    public boolean m735c() {
        return this.d.m738a();
    }

    public String d() {
        return this.d.d;
    }

    /* renamed from: d */
    public boolean m736d() {
        return this.d.f2b;
    }

    public String e() {
        return this.d.g;
    }

    /* renamed from: e */
    public boolean m737e() {
        return !this.d.f1a;
    }

    public String f() {
        return this.d.h;
    }
}
