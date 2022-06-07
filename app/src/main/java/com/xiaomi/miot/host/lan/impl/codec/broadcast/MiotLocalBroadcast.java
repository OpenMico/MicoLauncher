package com.xiaomi.miot.host.lan.impl.codec.broadcast;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.module.miot.OtDefs;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotLocalBroadcast extends MiotRequest {
    public static final String REQUEST_METHOD = "local.status";
    private boolean mIsCloudConnected;
    private boolean mIsWifiConnected;

    public MiotLocalBroadcast(JSONObject jSONObject) throws MiotException {
        this.mIsCloudConnected = false;
        this.mIsWifiConnected = false;
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if ("local.status".equals(optString)) {
            String optString2 = jSONObject.optString("params");
            if (TextUtils.isEmpty(optString2)) {
                MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
                throw new MiotException(miotError, "params invalid: " + optString2);
            } else if (!TextUtils.isEmpty(optString2) && optString2.equalsIgnoreCase(OtDefs.OtLocalStatus.STATUS_CLOUD_CONNECTED)) {
                this.mIsCloudConnected = true;
            } else if (!TextUtils.isEmpty(optString2) && optString2.equalsIgnoreCase("wifi_connected")) {
                this.mIsWifiConnected = true;
            }
        } else {
            MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError2, "method invalid: " + optString);
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.BRAODCAST_LOCAL_STATUS;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        return new JSONObject().toString().getBytes();
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new JSONObject().toString().getBytes();
    }

    public boolean isCloudConnected() {
        return this.mIsCloudConnected;
    }

    public boolean isWifiConnected() {
        return this.mIsWifiConnected;
    }
}
