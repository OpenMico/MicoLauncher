package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes4.dex */
public abstract class ge {
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private List<gb> k;
    private final Map<String, Object> l;
    private gi m;
    protected static final String a = Locale.getDefault().getLanguage().toLowerCase();
    private static String b = null;

    /* renamed from: a */
    public static final DateFormat f49a = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static String c = gp.a(5) + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
    private static long d = 0;

    static {
        f49a.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public ge() {
        this.e = b;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = new CopyOnWriteArrayList();
        this.l = new HashMap();
        this.m = null;
    }

    public ge(Bundle bundle) {
        this.e = b;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = new CopyOnWriteArrayList();
        this.l = new HashMap();
        this.m = null;
        this.g = bundle.getString("ext_to");
        this.h = bundle.getString("ext_from");
        this.i = bundle.getString("ext_chid");
        this.f = bundle.getString("ext_pkt_id");
        Parcelable[] parcelableArray = bundle.getParcelableArray("ext_exts");
        if (parcelableArray != null) {
            this.k = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                gb a2 = gb.a((Bundle) parcelable);
                if (a2 != null) {
                    this.k.add(a2);
                }
            }
        }
        Bundle bundle2 = bundle.getBundle("ext_ERROR");
        if (bundle2 != null) {
            this.m = new gi(bundle2);
        }
    }

    public static synchronized String i() {
        String sb;
        synchronized (ge.class) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(c);
            long j = d;
            d = 1 + j;
            sb2.append(Long.toString(j));
            sb = sb2.toString();
        }
        return sb;
    }

    public static String q() {
        return a;
    }

    /* renamed from: a */
    public Bundle mo941a() {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(this.e)) {
            bundle.putString("ext_ns", this.e);
        }
        if (!TextUtils.isEmpty(this.h)) {
            bundle.putString("ext_from", this.h);
        }
        if (!TextUtils.isEmpty(this.g)) {
            bundle.putString("ext_to", this.g);
        }
        if (!TextUtils.isEmpty(this.f)) {
            bundle.putString("ext_pkt_id", this.f);
        }
        if (!TextUtils.isEmpty(this.i)) {
            bundle.putString("ext_chid", this.i);
        }
        gi giVar = this.m;
        if (giVar != null) {
            bundle.putBundle("ext_ERROR", giVar.a());
        }
        List<gb> list = this.k;
        if (list != null) {
            Bundle[] bundleArr = new Bundle[list.size()];
            int i = 0;
            for (gb gbVar : this.k) {
                Bundle a2 = gbVar.a();
                if (a2 != null) {
                    i++;
                    bundleArr[i] = a2;
                }
            }
            bundle.putParcelableArray("ext_exts", bundleArr);
        }
        return bundle;
    }

    public gb a(String str) {
        return a(str, null);
    }

    public gb a(String str, String str2) {
        for (gb gbVar : this.k) {
            if (str2 == null || str2.equals(gbVar.b())) {
                if (str.equals(gbVar.m934a())) {
                    return gbVar;
                }
            }
        }
        return null;
    }

    public gi a() {
        return this.m;
    }

    /* renamed from: a */
    public synchronized Object m938a(String str) {
        if (this.l == null) {
            return null;
        }
        return this.l.get(str);
    }

    /* renamed from: a  reason: collision with other method in class */
    public abstract String m939a();

    /* renamed from: a */
    public synchronized Collection<gb> m940a() {
        if (this.k == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(this.k));
    }

    public void a(gb gbVar) {
        this.k.add(gbVar);
    }

    public void a(gi giVar) {
        this.m = giVar;
    }

    public synchronized Collection<String> b() {
        if (this.l == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet(this.l.keySet()));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ge geVar = (ge) obj;
        gi giVar = this.m;
        if (giVar == null ? geVar.m != null : !giVar.equals(geVar.m)) {
            return false;
        }
        String str = this.h;
        if (str == null ? geVar.h != null : !str.equals(geVar.h)) {
            return false;
        }
        if (!this.k.equals(geVar.k)) {
            return false;
        }
        String str2 = this.f;
        if (str2 == null ? geVar.f != null : !str2.equals(geVar.f)) {
            return false;
        }
        String str3 = this.i;
        if (str3 == null ? geVar.i != null : !str3.equals(geVar.i)) {
            return false;
        }
        Map<String, Object> map = this.l;
        if (map == null ? geVar.l != null : !map.equals(geVar.l)) {
            return false;
        }
        String str4 = this.g;
        if (str4 == null ? geVar.g != null : !str4.equals(geVar.g)) {
            return false;
        }
        String str5 = this.e;
        if (str5 != null) {
            if (str5.equals(geVar.e)) {
                return true;
            }
        } else if (geVar.e == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.e;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.f;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.g;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.h;
        int hashCode4 = (hashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.i;
        int hashCode5 = (((((hashCode4 + (str5 != null ? str5.hashCode() : 0)) * 31) + this.k.hashCode()) * 31) + this.l.hashCode()) * 31;
        gi giVar = this.m;
        if (giVar != null) {
            i = giVar.hashCode();
        }
        return hashCode5 + i;
    }

    public String j() {
        if ("ID_NOT_AVAILABLE".equals(this.f)) {
            return null;
        }
        if (this.f == null) {
            this.f = i();
        }
        return this.f;
    }

    public String k() {
        return this.i;
    }

    public void k(String str) {
        this.f = str;
    }

    public String l() {
        return this.g;
    }

    public void l(String str) {
        this.i = str;
    }

    public String m() {
        return this.h;
    }

    public void m(String str) {
        this.g = str;
    }

    public String n() {
        return this.j;
    }

    public void n(String str) {
        this.h = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x0109 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x010f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected synchronized java.lang.String o() {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ge.o():java.lang.String");
    }

    public void o(String str) {
        this.j = str;
    }

    public String p() {
        return this.e;
    }
}
