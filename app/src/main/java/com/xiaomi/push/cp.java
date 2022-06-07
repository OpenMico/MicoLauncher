package com.xiaomi.push;

import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.b;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class cp implements Comparable<cp> {
    String a;
    protected int b;
    private final LinkedList<cq> c;
    private long d;

    public cp() {
        this(null, 0);
    }

    public cp(String str) {
        this(str, 0);
    }

    public cp(String str, int i) {
        this.c = new LinkedList<>();
        this.d = 0L;
        this.a = str;
        this.b = i;
    }

    /* renamed from: a */
    public int compareTo(cp cpVar) {
        if (cpVar == null) {
            return 1;
        }
        return cpVar.b - this.b;
    }

    public synchronized cp a(JSONObject jSONObject) {
        this.d = jSONObject.getLong("tt");
        this.b = jSONObject.getInt("wt");
        this.a = jSONObject.getString(b.E);
        JSONArray jSONArray = jSONObject.getJSONArray("ah");
        for (int i = 0; i < jSONArray.length(); i++) {
            this.c.add(new cq().a(jSONArray.getJSONObject(i)));
        }
        return this;
    }

    public synchronized JSONObject a() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        jSONObject.put("tt", this.d);
        jSONObject.put("wt", this.b);
        jSONObject.put(b.E, this.a);
        JSONArray jSONArray = new JSONArray();
        Iterator<cq> it = this.c.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().m808a());
        }
        jSONObject.put("ah", jSONArray);
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void a(cq cqVar) {
        if (cqVar != null) {
            this.c.add(cqVar);
            int a = cqVar.a();
            if (a > 0) {
                this.b += cqVar.a();
            } else {
                int i = 0;
                for (int size = this.c.size() - 1; size >= 0 && this.c.get(size).a() < 0; size--) {
                    i++;
                }
                this.b += a * i;
            }
            if (this.c.size() > 30) {
                this.b -= this.c.remove().a();
            }
        }
    }

    public String toString() {
        return this.a + Constants.COLON_SEPARATOR + this.b;
    }
}
