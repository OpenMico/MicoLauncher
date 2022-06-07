package com.xiaomi.idm.api;

import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class RmiException extends Exception {
    private int mResponseCode;

    public RmiException(int i, String str) {
        super(str);
        this.mResponseCode = i;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return super.getMessage() + "; response code: " + this.mResponseCode;
    }

    public static RmiException createException(ExecutionException executionException) {
        RmiException rmiException;
        try {
            JSONObject jSONObject = new JSONObject(executionException.getMessage());
            rmiException = new RmiException(jSONObject.getInt("code"), jSONObject.getString("msg"));
        } catch (JSONException e) {
            LogUtil.e("RmiException", e.getMessage(), e);
            rmiException = null;
        }
        return rmiException == null ? new RmiException(ResponseCode.ERR_RMI_UNKNOWN, ResponseCode.ERR_RMI_UNKNOWN_MSG) : rmiException;
    }
}
