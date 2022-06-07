package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.onetrack.api.b;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class cm {
    private String a;
    private final ArrayList<cr> b = new ArrayList<>();

    public cm() {
    }

    public cm(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.a = str;
            return;
        }
        throw new IllegalArgumentException("the host is empty");
    }

    public synchronized cm a(JSONObject jSONObject) {
        this.a = jSONObject.getString(b.E);
        JSONArray jSONArray = jSONObject.getJSONArray("fbs");
        for (int i = 0; i < jSONArray.length(); i++) {
            this.b.add(new cr(this.a).a(jSONArray.getJSONObject(i)));
        }
        return this;
    }

    public synchronized cr a() {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            cr crVar = this.b.get(size);
            if (crVar.m812a()) {
                cv.a().m817a(crVar.a());
                return crVar;
            }
        }
        return null;
    }

    public synchronized void a(cr crVar) {
        int i = 0;
        while (true) {
            if (i >= this.b.size()) {
                break;
            } else if (this.b.get(i).a(crVar)) {
                this.b.set(i, crVar);
                break;
            } else {
                i++;
            }
        }
        if (i >= this.b.size()) {
            this.b.add(crVar);
        }
    }

    public synchronized void a(boolean z) {
        ArrayList<cr> arrayList;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            cr crVar = this.b.get(size);
            if (z) {
                if (crVar.c()) {
                    arrayList = this.b;
                    arrayList.remove(size);
                }
            } else if (!crVar.b()) {
                arrayList = this.b;
                arrayList.remove(size);
            }
        }
    }

    public ArrayList<cr> b() {
        return this.b;
    }

    public String c() {
        return this.a;
    }

    public synchronized JSONObject d() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        jSONObject.put(b.E, this.a);
        JSONArray jSONArray = new JSONArray();
        Iterator<cr> it = this.b.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().m810a());
        }
        jSONObject.put("fbs", jSONArray);
        return jSONObject;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("\n");
        Iterator<cr> it = this.b.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }
}
