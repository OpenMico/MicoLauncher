package com.xiaomi.miot.host.lan.impl.codec.internal;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotReportOtcInfoResult extends MiotRequest {
    public static final String REQUEST_METHOD = "_internal.report_otcinfo_result";
    private boolean mIsSuccess;

    public MiotReportOtcInfoResult(JSONObject jSONObject) throws MiotException {
        this.mIsSuccess = true;
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (REQUEST_METHOD.equals(optString)) {
            String optString2 = jSONObject.optString("params");
            if (!TextUtils.isEmpty(optString2)) {
                try {
                    JSONObject optJSONObject = new JSONObject(optString2).optJSONObject("error");
                    if (optJSONObject != null && optJSONObject.optInt("code") == -30011) {
                        this.mIsSuccess = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
                throw new MiotException(miotError, "params invalid: " + optString2);
            }
        } else {
            MiotError miotError2 = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError2, "method invalid: " + optString);
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.REPORT_OTCINFO_RESULT;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        return new JSONObject().toString().getBytes();
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new JSONObject().toString().getBytes();
    }

    public boolean isSuccess() {
        return this.mIsSuccess;
    }
}
