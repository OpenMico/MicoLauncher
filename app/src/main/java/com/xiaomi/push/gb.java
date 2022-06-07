package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class gb implements gf {
    private String a;
    private String b;
    private String[] c;
    private String[] d;
    private String e;
    private List<gb> f;

    public gb(String str, String str2, String[] strArr, String[] strArr2) {
        this.c = null;
        this.d = null;
        this.f = null;
        this.a = str;
        this.b = str2;
        this.c = strArr;
        this.d = strArr2;
    }

    public gb(String str, String str2, String[] strArr, String[] strArr2, String str3, List<gb> list) {
        this.c = null;
        this.d = null;
        this.f = null;
        this.a = str;
        this.b = str2;
        this.c = strArr;
        this.d = strArr2;
        this.e = str3;
        this.f = list;
    }

    public static gb a(Bundle bundle) {
        ArrayList arrayList;
        String string = bundle.getString("ext_ele_name");
        String string2 = bundle.getString("ext_ns");
        String string3 = bundle.getString("ext_text");
        Bundle bundle2 = bundle.getBundle("attributes");
        Set<String> keySet = bundle2.keySet();
        String[] strArr = new String[keySet.size()];
        String[] strArr2 = new String[keySet.size()];
        int i = 0;
        for (String str : keySet) {
            strArr[i] = str;
            strArr2[i] = bundle2.getString(str);
            i++;
        }
        if (bundle.containsKey("children")) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("children");
            ArrayList arrayList2 = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                arrayList2.add(a((Bundle) parcelable));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return new gb(string, string2, strArr, strArr2, string3, arrayList);
    }

    public static Parcelable[] a(List<gb> list) {
        return a((gb[]) list.toArray(new gb[list.size()]));
    }

    public static Parcelable[] a(gb[] gbVarArr) {
        if (gbVarArr == null) {
            return null;
        }
        Parcelable[] parcelableArr = new Parcelable[gbVarArr.length];
        for (int i = 0; i < gbVarArr.length; i++) {
            parcelableArr[i] = gbVarArr[i].m933a();
        }
        return parcelableArr;
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putString("ext_ele_name", this.a);
        bundle.putString("ext_ns", this.b);
        bundle.putString("ext_text", this.e);
        Bundle bundle2 = new Bundle();
        String[] strArr = this.c;
        if (strArr != null && strArr.length > 0) {
            int i = 0;
            while (true) {
                String[] strArr2 = this.c;
                if (i >= strArr2.length) {
                    break;
                }
                bundle2.putString(strArr2[i], this.d[i]);
                i++;
            }
        }
        bundle.putBundle("attributes", bundle2);
        List<gb> list = this.f;
        if (list != null && list.size() > 0) {
            bundle.putParcelableArray("children", a(this.f));
        }
        return bundle;
    }

    /* renamed from: a */
    public Parcelable m933a() {
        return a();
    }

    /* renamed from: a */
    public String m934a() {
        return this.a;
    }

    public String a(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        } else if (this.c == null) {
            return null;
        } else {
            int i = 0;
            while (true) {
                String[] strArr = this.c;
                if (i >= strArr.length) {
                    return null;
                }
                if (str.equals(strArr[i])) {
                    return this.d[i];
                }
                i++;
            }
        }
    }

    /* renamed from: a */
    public void m935a(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = gp.a(str);
        }
        this.e = str;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return !TextUtils.isEmpty(this.e) ? gp.b(this.e) : this.e;
    }

    @Override // com.xiaomi.push.gf
    public String d() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(this.a);
        if (!TextUtils.isEmpty(this.b)) {
            sb.append(StringUtils.SPACE);
            sb.append("xmlns=");
            sb.append("\"");
            sb.append(this.b);
            sb.append("\"");
        }
        String[] strArr = this.c;
        if (strArr != null && strArr.length > 0) {
            for (int i = 0; i < this.c.length; i++) {
                if (!TextUtils.isEmpty(this.d[i])) {
                    sb.append(StringUtils.SPACE);
                    sb.append(this.c[i]);
                    sb.append("=\"");
                    sb.append(gp.a(this.d[i]));
                    sb.append("\"");
                }
            }
        }
        if (!TextUtils.isEmpty(this.e)) {
            sb.append(">");
            sb.append(this.e);
        } else {
            List<gb> list = this.f;
            if (list == null || list.size() <= 0) {
                str = "/>";
                sb.append(str);
                return sb.toString();
            }
            sb.append(">");
            for (gb gbVar : this.f) {
                sb.append(gbVar.d());
            }
        }
        sb.append("</");
        sb.append(this.a);
        str = ">";
        sb.append(str);
        return sb.toString();
    }

    public String toString() {
        return d();
    }
}
