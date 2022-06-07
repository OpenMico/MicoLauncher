package com.xiaomi.miot.host.lan.impl.codec.internal;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotBindStat extends MiotRequest {
    public static final String REQUEST_METHOD = "local.bind";
    private static final String TAG = "MiotBindStat";
    private int stat;

    public MiotBindStat(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (REQUEST_METHOD.equals(optString)) {
            String optString2 = jSONObject.optString("result");
            if (TextUtils.equals(optString2, "ok")) {
                this.stat = 1;
            } else if (TextUtils.equals(optString2, "unbind")) {
                this.stat = 0;
            } else {
                this.stat = -1;
            }
        } else {
            MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
            throw new MiotException(miotError, "method invalid: " + optString);
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.BINDSTAT;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        return new byte[0];
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new byte[0];
    }

    public int getStat() {
        return this.stat;
    }
}
