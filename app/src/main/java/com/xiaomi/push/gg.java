package com.xiaomi.push;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class gg extends ge {
    private b b;
    private String c;
    private int d;
    private a e;

    /* loaded from: classes4.dex */
    public enum a {
        chat,
        available,
        away,
        xa,
        dnd
    }

    /* loaded from: classes4.dex */
    public enum b {
        available,
        unavailable,
        subscribe,
        subscribed,
        unsubscribe,
        unsubscribed,
        error,
        probe
    }

    public gg(Bundle bundle) {
        super(bundle);
        this.b = b.available;
        this.c = null;
        this.d = Integer.MIN_VALUE;
        this.e = null;
        if (bundle.containsKey("ext_pres_type")) {
            this.b = b.valueOf(bundle.getString("ext_pres_type"));
        }
        if (bundle.containsKey("ext_pres_status")) {
            this.c = bundle.getString("ext_pres_status");
        }
        if (bundle.containsKey("ext_pres_prio")) {
            this.d = bundle.getInt("ext_pres_prio");
        }
        if (bundle.containsKey("ext_pres_mode")) {
            this.e = a.valueOf(bundle.getString("ext_pres_mode"));
        }
    }

    public gg(b bVar) {
        this.b = b.available;
        this.c = null;
        this.d = Integer.MIN_VALUE;
        this.e = null;
        a(bVar);
    }

    @Override // com.xiaomi.push.ge
    /* renamed from: a */
    public Bundle mo941a() {
        Bundle a2 = super.mo941a();
        b bVar = this.b;
        if (bVar != null) {
            a2.putString("ext_pres_type", bVar.toString());
        }
        String str = this.c;
        if (str != null) {
            a2.putString("ext_pres_status", str);
        }
        int i = this.d;
        if (i != Integer.MIN_VALUE) {
            a2.putInt("ext_pres_prio", i);
        }
        a aVar = this.e;
        if (!(aVar == null || aVar == a.available)) {
            a2.putString("ext_pres_mode", this.e.toString());
        }
        return a2;
    }

    @Override // com.xiaomi.push.ge
    /* renamed from: a  reason: collision with other method in class */
    public String mo941a() {
        StringBuilder sb = new StringBuilder();
        sb.append("<presence");
        if (p() != null) {
            sb.append(" xmlns=\"");
            sb.append(p());
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
        if (this.b != null) {
            sb.append(" type=\"");
            sb.append(this.b);
            sb.append("\"");
        }
        sb.append(">");
        if (this.c != null) {
            sb.append("<status>");
            sb.append(gp.a(this.c));
            sb.append("</status>");
        }
        if (this.d != Integer.MIN_VALUE) {
            sb.append("<priority>");
            sb.append(this.d);
            sb.append("</priority>");
        }
        a aVar = this.e;
        if (!(aVar == null || aVar == a.available)) {
            sb.append("<show>");
            sb.append(this.e);
            sb.append("</show>");
        }
        sb.append(o());
        gi a2 = mo941a();
        if (a2 != null) {
            sb.append(a2.m942a());
        }
        sb.append("</presence>");
        return sb.toString();
    }

    public void a(int i) {
        if (i < -128 || i > 128) {
            throw new IllegalArgumentException("Priority value " + i + " is not valid. Valid range is -128 through 128.");
        }
        this.d = i;
    }

    public void a(a aVar) {
        this.e = aVar;
    }

    public void a(b bVar) {
        if (bVar != null) {
            this.b = bVar;
            return;
        }
        throw new NullPointerException("Type cannot be null");
    }

    @Override // com.xiaomi.push.ge
    public void a(String str) {
        this.c = str;
    }
}
