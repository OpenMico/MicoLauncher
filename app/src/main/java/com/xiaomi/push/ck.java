package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.xiaomi.onetrack.OneTrack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ck implements cl {
    private static ck a;
    private bo b;
    private HashMap<String, bn> c;
    private String d;
    private Context e;
    private String f;
    private String g;
    private int h;
    private int i;
    private int j;
    private int k;

    public static synchronized ck a() {
        ck ckVar;
        synchronized (ck.class) {
            ckVar = a;
        }
        return ckVar;
    }

    private String a(ArrayList<ch> arrayList, String str) {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(this.d)) {
            jSONObject.put(OneTrack.Param.IMEI_MD5, ci.a(this.d));
        }
        jSONObject.put("actionType", str);
        jSONObject.put("actionTime", System.currentTimeMillis());
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            JSONObject jSONObject2 = null;
            if (TextUtils.isEmpty(arrayList.get(i).f19a)) {
                jSONObject2 = new JSONObject();
            } else {
                try {
                    jSONObject2 = new JSONObject(arrayList.get(i).f19a);
                } catch (Exception unused) {
                    Log.e("com.xiaomi.miui.ads.pushsdk", "content 不是json串");
                }
            }
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            jSONObject2.put("adId", arrayList.get(i).f18a);
            arrayList2.add(jSONObject2);
        }
        jSONObject.put("adList", new JSONArray((Collection) arrayList2));
        return Base64.encodeToString(jSONObject.toString().getBytes(), 2);
    }

    private void a(bn bnVar) {
        if (!this.c.containsKey(bnVar.c)) {
            this.i++;
            ci.b("send: " + this.i);
            by byVar = new by(this, this.f, this.g, bnVar);
            this.c.put(bnVar.c, bnVar);
            byVar.execute(new String[0]);
        }
    }

    private void a(ArrayList<ch> arrayList, String str, int i) {
        try {
            String a2 = a(arrayList, str);
            String a3 = ci.a(a2);
            if (b(new bn(i, a2, a3))) {
                a(new bn(i, a2, a3));
            }
        } catch (JSONException unused) {
        }
    }

    private boolean b(bn bnVar) {
        if (bz.a(this.e)) {
            return true;
        }
        c(bnVar);
        return false;
    }

    private void c(bn bnVar) {
        this.k++;
        ci.b("cacheCount: " + this.k);
        this.b.a(bnVar);
        this.b.a();
    }

    public void a(ch chVar) {
        if (chVar.f18a > 0) {
            ArrayList<ch> arrayList = new ArrayList<>();
            arrayList.add(chVar);
            a(arrayList, "click", chVar.a);
        }
    }

    @Override // com.xiaomi.push.cl
    public void a(Integer num, bn bnVar) {
        if (this.c.containsKey(bnVar.c)) {
            if (num.intValue() != 0) {
                this.j++;
                ci.b("faild: " + this.j + StringUtils.SPACE + bnVar.c + "  " + this.c.size());
                c(bnVar);
            } else {
                this.h++;
                ci.b("success: " + this.h);
            }
            this.c.remove(bnVar.c);
        }
    }

    public void b(ch chVar) {
        if (chVar.f18a > 0) {
            ArrayList<ch> arrayList = new ArrayList<>();
            arrayList.add(chVar);
            a(arrayList, "remove", chVar.a);
        }
    }

    public void c(ch chVar) {
        if (chVar.f18a > 0) {
            ArrayList<ch> arrayList = new ArrayList<>();
            arrayList.add(chVar);
            a(arrayList, "received", chVar.a);
        }
    }
}
