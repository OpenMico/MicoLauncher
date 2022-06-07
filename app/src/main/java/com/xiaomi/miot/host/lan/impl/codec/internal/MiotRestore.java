package com.xiaomi.miot.host.lan.impl.codec.internal;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.codec.MiotMethod;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotRestore extends MiotRequest {
    public static final String REQUEST_METHOD = "miIO.restore";
    private static final String TAG = "MiotRestore";
    private String mOriginRequest;

    public MiotRestore(JSONObject jSONObject) throws MiotException {
        String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
        if (REQUEST_METHOD.equals(optString)) {
            this.mOriginRequest = jSONObject.toString();
            return;
        }
        MiotError miotError = MiotError.INTERNAL_INVALID_ARGS;
        throw new MiotException(miotError, "method invalid: " + optString);
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public MiotMethod getMethod() {
        return MiotMethod.RESTORE;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeRequest() {
        return new byte[0];
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequest
    public byte[] encodeResponse() {
        return new byte[0];
    }

    public String getOriginRequest() {
        return this.mOriginRequest;
    }
}
