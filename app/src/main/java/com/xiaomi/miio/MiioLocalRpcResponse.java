package com.xiaomi.miio;

import android.util.Log;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public interface MiioLocalRpcResponse {
    void onResponse(String str);

    /* loaded from: classes3.dex */
    public static abstract class MiioLocalRpcResponseSimple implements MiioLocalRpcResponse {
        public abstract void onFail(int i, String str, Throwable th);

        public abstract void onSuccess(JSONObject jSONObject);

        @Override // com.xiaomi.miio.MiioLocalRpcResponse
        public void onResponse(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int optInt = jSONObject.optInt("code", -1);
                if (optInt == 0) {
                    onSuccess(jSONObject.optJSONObject("result"));
                } else {
                    onFail(optInt, str, null);
                }
            } catch (Throwable th) {
                Log.e("fatal", "MiioLocalRpcResponseSimple.onResponse", th);
                onFail(Integer.MIN_VALUE, null, th);
            }
        }
    }
}
