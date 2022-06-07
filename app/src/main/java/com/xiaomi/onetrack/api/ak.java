package com.xiaomi.onetrack.api;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ak {
    public static final String a = "name";
    public static final String b = "gender";
    public static final String c = "birthday";
    public static final String d = "phone";
    public static final String e = "job";
    public static final String f = "hobby";
    public static final String g = "region";
    public static final String h = "province";
    public static final String i = "city";
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;

    private ak() {
    }

    public String a() {
        return this.j;
    }

    public String b() {
        return this.k;
    }

    public String c() {
        return this.l;
    }

    public String d() {
        return this.m;
    }

    public String e() {
        return this.n;
    }

    public String f() {
        return this.o;
    }

    public String g() {
        return this.p;
    }

    public String h() {
        return this.q;
    }

    public String i() {
        return this.r;
    }

    public JSONObject j() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", this.j);
            jSONObject.put("gender", this.k);
            jSONObject.put("birthday", this.l);
            jSONObject.put("phone", this.m);
            jSONObject.put("job", this.n);
            jSONObject.put("hobby", this.o);
            jSONObject.put("region", this.p);
            jSONObject.put("province", this.q);
            jSONObject.put("city", this.r);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public String toString() {
        return j().toString();
    }

    /* loaded from: classes4.dex */
    public static final class a {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;
        private String h;
        private String i;

        public a a(String str) {
            this.a = str;
            return this;
        }

        public a b(String str) {
            this.b = str;
            return this;
        }

        public a c(String str) {
            this.c = str;
            return this;
        }

        public a d(String str) {
            this.d = str;
            return this;
        }

        public a e(String str) {
            this.e = str;
            return this;
        }

        public a f(String str) {
            this.f = str;
            return this;
        }

        public a g(String str) {
            this.g = str;
            return this;
        }

        public a h(String str) {
            this.h = str;
            return this;
        }

        public a i(String str) {
            this.i = str;
            return this;
        }

        public ak a() {
            ak akVar = new ak();
            akVar.o = this.f;
            akVar.n = this.e;
            akVar.r = this.i;
            akVar.m = this.d;
            akVar.q = this.h;
            akVar.l = this.c;
            akVar.j = this.a;
            akVar.p = this.g;
            akVar.k = this.b;
            return akVar;
        }
    }
}
