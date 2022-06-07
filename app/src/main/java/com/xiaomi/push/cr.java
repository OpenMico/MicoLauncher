package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class cr {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    protected String h;
    private long i;
    private String k;
    private ArrayList<cp> j = new ArrayList<>();
    private double l = 0.1d;
    private String m = "s.mi1.cc";
    private long n = 86400000;

    public cr(String str) {
        this.a = "";
        if (!TextUtils.isEmpty(str)) {
            this.i = System.currentTimeMillis();
            this.j.add(new cp(str, -1));
            this.a = cv.f();
            this.b = str;
            return;
        }
        throw new IllegalArgumentException("the host is empty");
    }

    private synchronized void c(String str) {
        Iterator<cp> it = this.j.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().a, str)) {
                it.remove();
            }
        }
    }

    public synchronized cr a(JSONObject jSONObject) {
        this.a = jSONObject.optString(OneTrack.Param.NET);
        this.n = jSONObject.getLong(RtspHeaders.Values.TTL);
        this.l = jSONObject.getDouble("pct");
        this.i = jSONObject.getLong("ts");
        this.d = jSONObject.optString("city");
        this.c = jSONObject.optString("prv");
        this.g = jSONObject.optString("cty");
        this.e = jSONObject.optString("isp");
        this.f = jSONObject.optString("ip");
        this.b = jSONObject.optString(b.E);
        this.h = jSONObject.optString("xf");
        JSONArray jSONArray = jSONObject.getJSONArray("fbs");
        for (int i = 0; i < jSONArray.length(); i++) {
            a(new cp().a(jSONArray.getJSONObject(i)));
        }
        return this;
    }

    public synchronized String a() {
        if (!TextUtils.isEmpty(this.k)) {
            return this.k;
        } else if (TextUtils.isEmpty(this.e)) {
            return "hardcode_isp";
        } else {
            this.k = az.a(new String[]{this.e, this.c, this.d, this.g, this.f}, "_");
            return this.k;
        }
    }

    /* renamed from: a */
    public synchronized ArrayList<String> m809a() {
        return a(false);
    }

    public ArrayList<String> a(String str) {
        if (!TextUtils.isEmpty(str)) {
            URL url = new URL(str);
            if (TextUtils.equals(url.getHost(), this.b)) {
                ArrayList<String> arrayList = new ArrayList<>();
                Iterator<String> it = a(true).iterator();
                while (it.hasNext()) {
                    ct a = ct.a(it.next(), url.getPort());
                    arrayList.add(new URL(url.getProtocol(), a.m814a(), a.a(), url.getFile()).toString());
                }
                return arrayList;
            }
            throw new IllegalArgumentException("the url is not supported by the fallback");
        }
        throw new IllegalArgumentException("the url is empty.");
    }

    public synchronized ArrayList<String> a(boolean z) {
        ArrayList<String> arrayList;
        String substring;
        cp[] cpVarArr = new cp[this.j.size()];
        this.j.toArray(cpVarArr);
        Arrays.sort(cpVarArr);
        arrayList = new ArrayList<>();
        for (cp cpVar : cpVarArr) {
            if (z) {
                substring = cpVar.a;
            } else {
                int indexOf = cpVar.a.indexOf(Constants.COLON_SEPARATOR);
                substring = indexOf != -1 ? cpVar.a.substring(0, indexOf) : cpVar.a;
            }
            arrayList.add(substring);
        }
        return arrayList;
    }

    /* renamed from: a */
    public synchronized JSONObject m810a() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        jSONObject.put(OneTrack.Param.NET, this.a);
        jSONObject.put(RtspHeaders.Values.TTL, this.n);
        jSONObject.put("pct", this.l);
        jSONObject.put("ts", this.i);
        jSONObject.put("city", this.d);
        jSONObject.put("prv", this.c);
        jSONObject.put("cty", this.g);
        jSONObject.put("isp", this.e);
        jSONObject.put("ip", this.f);
        jSONObject.put(b.E, this.b);
        jSONObject.put("xf", this.h);
        JSONArray jSONArray = new JSONArray();
        Iterator<cp> it = this.j.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().a());
        }
        jSONObject.put("fbs", jSONArray);
        return jSONObject;
    }

    public void a(double d) {
        this.l = d;
    }

    public void a(long j) {
        if (j > 0) {
            this.n = j;
            return;
        }
        throw new IllegalArgumentException("the duration is invalid " + j);
    }

    public synchronized void a(cp cpVar) {
        c(cpVar.a);
        this.j.add(cpVar);
    }

    /* renamed from: a */
    public synchronized void m811a(String str) {
        a(new cp(str));
    }

    public void a(String str, int i, long j, long j2, Exception exc) {
        a(str, new cq(i, j, j2, exc));
    }

    public void a(String str, long j, long j2) {
        try {
            b(new URL(str).getHost(), j, j2);
        } catch (MalformedURLException unused) {
        }
    }

    public void a(String str, long j, long j2, Exception exc) {
        try {
            b(new URL(str).getHost(), j, j2, exc);
        } catch (MalformedURLException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
        r1.a(r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void a(java.lang.String r4, com.xiaomi.push.cq r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.ArrayList<com.xiaomi.push.cp> r0 = r3.j     // Catch: all -> 0x0020
            java.util.Iterator r0 = r0.iterator()     // Catch: all -> 0x0020
        L_0x0007:
            boolean r1 = r0.hasNext()     // Catch: all -> 0x0020
            if (r1 == 0) goto L_0x001e
            java.lang.Object r1 = r0.next()     // Catch: all -> 0x0020
            com.xiaomi.push.cp r1 = (com.xiaomi.push.cp) r1     // Catch: all -> 0x0020
            java.lang.String r2 = r1.a     // Catch: all -> 0x0020
            boolean r2 = android.text.TextUtils.equals(r4, r2)     // Catch: all -> 0x0020
            if (r2 == 0) goto L_0x0007
            r1.a(r5)     // Catch: all -> 0x0020
        L_0x001e:
            monitor-exit(r3)
            return
        L_0x0020:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.cr.a(java.lang.String, com.xiaomi.push.cq):void");
    }

    public synchronized void a(String[] strArr) {
        int i;
        int size = this.j.size() - 1;
        while (true) {
            i = 0;
            if (size < 0) {
                break;
            }
            int length = strArr.length;
            while (true) {
                if (i < length) {
                    if (TextUtils.equals(this.j.get(size).a, strArr[i])) {
                        this.j.remove(size);
                        break;
                    }
                    i++;
                }
            }
            size--;
        }
        Iterator<cp> it = this.j.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            cp next = it.next();
            if (next.b > i2) {
                i2 = next.b;
            }
        }
        while (i < strArr.length) {
            a(new cp(strArr[i], (strArr.length + i2) - i));
            i++;
        }
    }

    /* renamed from: a */
    public boolean m812a() {
        return TextUtils.equals(this.a, cv.f());
    }

    public boolean a(cr crVar) {
        return TextUtils.equals(this.a, crVar.a);
    }

    public void b(String str) {
        this.m = str;
    }

    public void b(String str, long j, long j2) {
        a(str, 0, j, j2, null);
    }

    public void b(String str, long j, long j2, Exception exc) {
        a(str, -1, j, j2, exc);
    }

    public boolean b() {
        return System.currentTimeMillis() - this.i < this.n;
    }

    public boolean c() {
        long j = this.n;
        if (864000000 >= j) {
            j = 864000000;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = this.i;
        return currentTimeMillis - j2 > j || (currentTimeMillis - j2 > this.n && this.a.startsWith("WIFI-"));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("\n");
        sb.append(a());
        Iterator<cp> it = this.j.iterator();
        while (it.hasNext()) {
            sb.append("\n");
            sb.append(it.next().toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
