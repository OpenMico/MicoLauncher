package com.xiaomi.push;

import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class gd extends ge {
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private boolean h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private boolean n;

    public gd() {
        this.b = null;
        this.c = null;
        this.h = false;
        this.j = "";
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = false;
    }

    public gd(Bundle bundle) {
        super(bundle);
        this.b = null;
        this.c = null;
        this.h = false;
        this.j = "";
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = false;
        this.b = bundle.getString("ext_msg_type");
        this.d = bundle.getString("ext_msg_lang");
        this.c = bundle.getString("ext_msg_thread");
        this.e = bundle.getString("ext_msg_sub");
        this.f = bundle.getString("ext_msg_body");
        this.g = bundle.getString("ext_body_encode");
        this.i = bundle.getString("ext_msg_appid");
        this.h = bundle.getBoolean("ext_msg_trans", false);
        this.n = bundle.getBoolean("ext_msg_encrypt", false);
        this.j = bundle.getString("ext_msg_seq");
        this.k = bundle.getString("ext_msg_mseq");
        this.l = bundle.getString("ext_msg_fseq");
        this.m = bundle.getString("ext_msg_status");
    }

    @Override // com.xiaomi.push.ge
    /* renamed from: a */
    public Bundle mo941a() {
        Bundle a = super.mo941a();
        if (!TextUtils.isEmpty(this.b)) {
            a.putString("ext_msg_type", this.b);
        }
        String str = this.d;
        if (str != null) {
            a.putString("ext_msg_lang", str);
        }
        String str2 = this.e;
        if (str2 != null) {
            a.putString("ext_msg_sub", str2);
        }
        String str3 = this.f;
        if (str3 != null) {
            a.putString("ext_msg_body", str3);
        }
        if (!TextUtils.isEmpty(this.g)) {
            a.putString("ext_body_encode", this.g);
        }
        String str4 = this.c;
        if (str4 != null) {
            a.putString("ext_msg_thread", str4);
        }
        String str5 = this.i;
        if (str5 != null) {
            a.putString("ext_msg_appid", str5);
        }
        if (this.h) {
            a.putBoolean("ext_msg_trans", true);
        }
        if (!TextUtils.isEmpty(this.j)) {
            a.putString("ext_msg_seq", this.j);
        }
        if (!TextUtils.isEmpty(this.k)) {
            a.putString("ext_msg_mseq", this.k);
        }
        if (!TextUtils.isEmpty(this.l)) {
            a.putString("ext_msg_fseq", this.l);
        }
        if (this.n) {
            a.putBoolean("ext_msg_encrypt", true);
        }
        if (!TextUtils.isEmpty(this.m)) {
            a.putString("ext_msg_status", this.m);
        }
        return a;
    }

    @Override // com.xiaomi.push.ge
    /* renamed from: a  reason: collision with other method in class */
    public String mo941a() {
        gi a;
        StringBuilder sb = new StringBuilder();
        sb.append("<message");
        if (p() != null) {
            sb.append(" xmlns=\"");
            sb.append(p());
            sb.append("\"");
        }
        if (this.d != null) {
            sb.append(" xml:lang=\"");
            sb.append(h());
            sb.append("\"");
        }
        if (j() != null) {
            sb.append(" id=\"");
            sb.append(j());
            sb.append("\"");
        }
        if (l() != null) {
            sb.append(" to=\"");
            sb.append(gp.a(l()));
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(d())) {
            sb.append(" seq=\"");
            sb.append(d());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(e())) {
            sb.append(" mseq=\"");
            sb.append(e());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(f())) {
            sb.append(" fseq=\"");
            sb.append(f());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(g())) {
            sb.append(" status=\"");
            sb.append(g());
            sb.append("\"");
        }
        if (m() != null) {
            sb.append(" from=\"");
            sb.append(gp.a(m()));
            sb.append("\"");
        }
        if (k() != null) {
            sb.append(" chid=\"");
            sb.append(gp.a(k()));
            sb.append("\"");
        }
        if (this.h) {
            sb.append(" transient=\"true\"");
        }
        if (!TextUtils.isEmpty(this.i)) {
            sb.append(" appid=\"");
            sb.append(c());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(this.b)) {
            sb.append(" type=\"");
            sb.append(this.b);
            sb.append("\"");
        }
        if (this.n) {
            sb.append(" s=\"1\"");
        }
        sb.append(">");
        if (this.e != null) {
            sb.append("<subject>");
            sb.append(gp.a(this.e));
            sb.append("</subject>");
        }
        if (this.f != null) {
            sb.append("<body");
            if (!TextUtils.isEmpty(this.g)) {
                sb.append(" encode=\"");
                sb.append(this.g);
                sb.append("\"");
            }
            sb.append(">");
            sb.append(gp.a(this.f));
            sb.append("</body>");
        }
        if (this.c != null) {
            sb.append("<thread>");
            sb.append(this.c);
            sb.append("</thread>");
        }
        if ("error".equalsIgnoreCase(this.b) && (a = mo941a()) != null) {
            sb.append(a.m942a());
        }
        sb.append(o());
        sb.append("</message>");
        return sb.toString();
    }

    @Override // com.xiaomi.push.ge
    public void a(String str) {
        this.i = str;
    }

    @Override // com.xiaomi.push.ge
    public void a(String str, String str2) {
        this.f = str;
        this.g = str2;
    }

    public void a(boolean z) {
        this.h = z;
    }

    @Override // com.xiaomi.push.ge
    public String b() {
        return this.b;
    }

    public void b(String str) {
        this.j = str;
    }

    public void b(boolean z) {
        this.n = z;
    }

    public String c() {
        return this.i;
    }

    public void c(String str) {
        this.k = str;
    }

    public String d() {
        return this.j;
    }

    public void d(String str) {
        this.l = str;
    }

    public String e() {
        return this.k;
    }

    public void e(String str) {
        this.m = str;
    }

    @Override // com.xiaomi.push.ge
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        gd gdVar = (gd) obj;
        if (!super.equals(gdVar)) {
            return false;
        }
        String str = this.f;
        if (str == null ? gdVar.f != null : !str.equals(gdVar.f)) {
            return false;
        }
        String str2 = this.d;
        if (str2 == null ? gdVar.d != null : !str2.equals(gdVar.d)) {
            return false;
        }
        String str3 = this.e;
        if (str3 == null ? gdVar.e != null : !str3.equals(gdVar.e)) {
            return false;
        }
        String str4 = this.c;
        if (str4 == null ? gdVar.c == null : str4.equals(gdVar.c)) {
            return this.b == gdVar.b;
        }
        return false;
    }

    public String f() {
        return this.l;
    }

    public void f(String str) {
        this.b = str;
    }

    public String g() {
        return this.m;
    }

    public void g(String str) {
        this.e = str;
    }

    public String h() {
        return this.d;
    }

    public void h(String str) {
        this.f = str;
    }

    @Override // com.xiaomi.push.ge
    public int hashCode() {
        String str = this.b;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.f;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.c;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.d;
        int hashCode4 = (hashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.e;
        if (str5 != null) {
            i = str5.hashCode();
        }
        return hashCode4 + i;
    }

    public void i(String str) {
        this.c = str;
    }

    public void j(String str) {
        this.d = str;
    }
}
