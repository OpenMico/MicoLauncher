package com.xiaomi.ai.android.track;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.umeng.analytics.pro.c;
import com.xiaomi.ai.android.utils.d;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;

/* loaded from: classes2.dex */
public class g {
    private String a;
    private final int d;
    private final String f;
    private Context g;
    private Long b = -1L;
    private int c = 0;
    private boolean e = false;

    public g(Context context, int i, String str, String str2) {
        this.a = str;
        this.d = i;
        this.f = str2;
        this.g = context;
    }

    private boolean a(long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        return currentTimeMillis > 0 && currentTimeMillis <= 86400000;
    }

    public synchronized void a() {
        ObjectNode objectNode;
        if (this.d > 0 && !TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.f)) {
            String a = d.a(this.g, this.a, this.f);
            Logger.a("UpdateTimesController", "trackRecord:" + a);
            try {
                if (!TextUtils.isEmpty(a) && (objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(a)) != null) {
                    JsonNode jsonNode = objectNode.get(c.p);
                    JsonNode jsonNode2 = objectNode.get("times");
                    if (jsonNode == null || !jsonNode.isNumber() || jsonNode2 == null || !jsonNode2.isNumber()) {
                        d.b(this.g, this.a, this.f);
                    } else {
                        this.b = Long.valueOf(jsonNode.asLong());
                        this.c = jsonNode2.asInt();
                        Logger.a("UpdateTimesController", "load track times:" + this.c + " at " + this.b);
                        return;
                    }
                }
            } catch (IOException e) {
                Logger.c("UpdateTimesController", Log.getStackTraceString(e), this.e);
            }
            this.b = 0L;
            Logger.a("UpdateTimesController", "no track times recorded ");
            return;
        }
        Logger.c("UpdateTimesController", "illegal parameter", this.e);
    }

    public synchronized void b() {
        if (this.d > 0 && !TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.f)) {
            if (this.b.longValue() < 0) {
                Logger.b("UpdateTimesController", "addTrackTimes,not init  return", this.e);
                return;
            }
            if (a(this.b.longValue())) {
                this.c++;
            } else {
                this.b = Long.valueOf(System.currentTimeMillis());
                this.c = 1;
            }
            ObjectNode createObjectNode = new ObjectMapper().createObjectNode();
            createObjectNode.put(c.p, this.b);
            createObjectNode.put("times", this.c);
            d.a(this.g, this.a, this.f, createObjectNode.toString());
            Logger.a("UpdateTimesController", this.f + " addTrackTimes:" + this.c + " in " + this.b + " max " + this.d, this.e);
            return;
        }
        Logger.c("UpdateTimesController", "illegal parameter", this.e);
    }

    public boolean c() {
        if (this.b.longValue() >= 0) {
            return a(this.b.longValue()) && this.c > this.d;
        }
        Logger.b("UpdateTimesController", "isTimeLimit :not init limit", this.e);
        return true;
    }
}
