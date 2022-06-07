package com.xiaomi.miot.host.lan.impl.codec.operation;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotEventNotify extends MiotRequest {
    private String did;
    private String method;
    private String params;
    private String partnerId;

    public MiotEventNotify(String str, String str2, String str3, String str4) {
        this.method = str;
        this.params = str2;
        this.did = str3;
        this.partnerId = str4;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.NOTIFY_EVENT;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            jSONObject.put("did", this.did);
            if (!TextUtils.isEmpty(this.partnerId)) {
                jSONObject.put("partner_id", this.partnerId);
            }
            jSONObject.put(SchemaActivity.KEY_METHOD, this.method);
            try {
                jSONObject.put("params", new JSONArray(this.params));
            } catch (JSONException unused) {
                Log.i("MiotEventNotify", "params is not JSONArray, try JSONObject.");
                try {
                    jSONObject.put("params", new JSONObject(this.params));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jSONObject.toString().getBytes();
        } catch (NullPointerException | JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new byte[0];
    }
}
