package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Set;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class dw extends dy {
    public dw(Context context, int i) {
        super(context, i);
    }

    private String c() {
        Bundle extras;
        StringBuilder sb = new StringBuilder();
        try {
            Intent registerReceiver = this.f24a.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (!(registerReceiver == null || (extras = registerReceiver.getExtras()) == null)) {
                Set<String> keySet = extras.keySet();
                JSONObject jSONObject = new JSONObject();
                if (keySet != null && keySet.size() > 0) {
                    for (String str : keySet) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                jSONObject.put(str, String.valueOf(extras.get(str)));
                            } catch (Exception unused) {
                            }
                        }
                    }
                    sb.append(jSONObject);
                }
            }
        } catch (Exception unused2) {
        }
        return sb.toString();
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 20;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a  reason: collision with other method in class */
    public hj mo834a() {
        return hj.Battery;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public String mo834a() {
        return c();
    }
}
