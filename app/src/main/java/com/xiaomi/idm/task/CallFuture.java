package com.xiaomi.idm.task;

import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.concurrent.ExecutionException;
import java8.util.concurrent.CompletableFuture;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class CallFuture<T> extends CompletableFuture<T> {
    CallResultCallBack<T> a = null;

    /* loaded from: classes3.dex */
    public interface CallResultCallBack<T> {
        void onFailed(int i, String str);

        void onResponse(T t);
    }

    public void setFailed(int i, String str) {
        LogUtil.e("CallFuture", "future task failed, code: " + i, new Object[0]);
        CallResultCallBack<T> callResultCallBack = this.a;
        if (callResultCallBack != null) {
            callResultCallBack.onFailed(i, str);
        }
        obtrudeException(createException(i, str));
    }

    public void setDone(T t) {
        LogUtil.e("CallFuture", "future task result done" + System.currentTimeMillis(), new Object[0]);
        CallResultCallBack<T> callResultCallBack = this.a;
        if (callResultCallBack != null) {
            callResultCallBack.onResponse(t);
        }
        obtrudeValue(t);
    }

    public void setCallResultCallBack(CallResultCallBack callResultCallBack) {
        this.a = callResultCallBack;
    }

    public a createException(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", i);
            jSONObject.put("msg", str);
        } catch (JSONException e) {
            LogUtil.e("CallFuture", e.getMessage(), e);
        }
        return new a(jSONObject.toString());
    }

    /* loaded from: classes3.dex */
    public static class a extends ExecutionException {
        private a(String str) {
            super(str);
        }

        @Override // java.lang.Throwable
        public String toString() {
            return getMessage();
        }
    }
}
