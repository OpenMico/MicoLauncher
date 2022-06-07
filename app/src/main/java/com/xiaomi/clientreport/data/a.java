package com.xiaomi.clientreport.data;

import com.umeng.analytics.pro.ai;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.push.bf;
import com.xiaomi.push.l;
import org.hapjs.features.channel.IChannel;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class a {
    private String a = bf.a();
    private String b = l.m1111a();
    private String c;
    public String clientInterfaceId;
    private String d;
    public int production;
    public int reportType;

    public String getPackageName() {
        return this.c;
    }

    public void setAppPackageName(String str) {
        this.c = str;
    }

    public void setSdkVersion(String str) {
        this.d = str;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.VALUE_ENV_PRODUCTION, this.production);
            jSONObject.put("reportType", this.reportType);
            jSONObject.put("clientInterfaceId", this.clientInterfaceId);
            jSONObject.put(ai.x, this.a);
            jSONObject.put("miuiVersion", this.b);
            jSONObject.put(IChannel.EXTRA_OPEN_PKG_NAME, this.c);
            jSONObject.put("sdkVersion", this.d);
            return jSONObject;
        } catch (JSONException e) {
            b.a(e);
            return null;
        }
    }

    public String toJsonString() {
        JSONObject json = toJson();
        return json == null ? "" : json.toString();
    }
}
