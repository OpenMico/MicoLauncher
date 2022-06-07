package com.xiaomi.push;

import android.os.Bundle;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class gc extends ge {
    private a b;
    private final Map<String, String> c;

    /* loaded from: classes4.dex */
    public static class a {
        public static final a a = new a(BluetoothConstants.GET);
        public static final a b = new a(BluetoothConstants.SET);
        public static final a c = new a("result");
        public static final a d = new a("error");
        public static final a e = new a("command");
        private String f;

        private a(String str) {
            this.f = str;
        }

        public static a a(String str) {
            if (str == null) {
                return null;
            }
            String lowerCase = str.toLowerCase();
            if (a.toString().equals(lowerCase)) {
                return a;
            }
            if (b.toString().equals(lowerCase)) {
                return b;
            }
            if (d.toString().equals(lowerCase)) {
                return d;
            }
            if (c.toString().equals(lowerCase)) {
                return c;
            }
            if (e.toString().equals(lowerCase)) {
                return e;
            }
            return null;
        }

        public String toString() {
            return this.f;
        }
    }

    public gc() {
        this.b = a.a;
        this.c = new HashMap();
    }

    public gc(Bundle bundle) {
        super(bundle);
        this.b = a.a;
        this.c = new HashMap();
        if (bundle.containsKey("ext_iq_type")) {
            this.b = a.a(bundle.getString("ext_iq_type"));
        }
    }

    @Override // com.xiaomi.push.ge
    /* renamed from: a */
    public Bundle mo941a() {
        Bundle a2 = super.mo941a();
        a aVar = this.b;
        if (aVar != null) {
            a2.putString("ext_iq_type", aVar.toString());
        }
        return a2;
    }

    @Override // com.xiaomi.push.ge
    /* renamed from: a */
    public a mo941a() {
        return this.b;
    }

    @Override // com.xiaomi.push.ge
    /* renamed from: a */
    public String mo941a() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("<iq ");
        if (j() != null) {
            sb.append("id=\"" + j() + "\" ");
        }
        if (l() != null) {
            sb.append("to=\"");
            sb.append(gp.a(l()));
            sb.append("\" ");
        }
        if (m() != null) {
            sb.append("from=\"");
            sb.append(gp.a(m()));
            sb.append("\" ");
        }
        if (k() != null) {
            sb.append("chid=\"");
            sb.append(gp.a(k()));
            sb.append("\" ");
        }
        for (Map.Entry<String, String> entry : this.c.entrySet()) {
            sb.append(gp.a(entry.getKey()));
            sb.append("=\"");
            sb.append(gp.a(entry.getValue()));
            sb.append("\" ");
        }
        if (this.b == null) {
            str = "type=\"get\">";
        } else {
            sb.append("type=\"");
            sb.append(mo941a());
            str = "\">";
        }
        sb.append(str);
        String b = b();
        if (b != null) {
            sb.append(b);
        }
        sb.append(o());
        gi a2 = mo941a();
        if (a2 != null) {
            sb.append(a2.m942a());
        }
        sb.append("</iq>");
        return sb.toString();
    }

    public void a(a aVar) {
        if (aVar == null) {
            aVar = a.a;
        }
        this.b = aVar;
    }

    public synchronized void a(Map<String, String> map) {
        this.c.putAll(map);
    }

    @Override // com.xiaomi.push.ge
    public String b() {
        return null;
    }
}
