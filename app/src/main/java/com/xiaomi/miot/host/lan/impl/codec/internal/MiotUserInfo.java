package com.xiaomi.miot.host.lan.impl.codec.internal;

import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.MiotStore;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.onetrack.OneTrack;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotUserInfo extends MiotRequest {
    public static final String REQUEST_METHOD = "local.ble.config_router";
    private String mAppBindkey;
    private String mCountryDomain;
    private String mPartnerId;
    private String mPartnerToken;
    private String mTimeZone;
    private long mUid;

    public MiotUserInfo(String str, BindParams bindParams) {
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONObject("params");
            if (bindParams != null) {
                this.mAppBindkey = bindParams.getBindKey();
                this.mPartnerId = bindParams.getPartnerId();
                this.mPartnerToken = bindParams.getPartnerToken();
            }
            this.mTimeZone = optJSONObject.optString(ai.M);
            this.mCountryDomain = optJSONObject.optString(MiotStore.COUNTRYDOMAIN);
            String optString = optJSONObject.optString(OneTrack.Param.UID);
            if (!TextUtils.isEmpty(optString)) {
                this.mUid = Long.parseLong(optString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.USERINFO;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            jSONObject.put(SchemaActivity.KEY_METHOD, REQUEST_METHOD);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("bind_key", this.mAppBindkey);
            jSONObject2.put("partner_id", this.mPartnerId);
            jSONObject2.put("partner_token", this.mPartnerToken);
            jSONObject2.put(OneTrack.Param.TZ, TextUtils.isEmpty(this.mTimeZone) ? "Asia/Shanghai" : this.mTimeZone);
            jSONObject2.put(MiotStore.COUNTRYDOMAIN, this.mCountryDomain);
            jSONObject2.put("ssid", "");
            jSONObject2.put("passwd", "");
            jSONObject2.put(OneTrack.Param.UID, this.mUid);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString().getBytes();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new byte[0];
    }
}
