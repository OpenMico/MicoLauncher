package com.xiaomi.phonenum.bean;

import com.xiaomi.phonenum.http.Response;

/* loaded from: classes4.dex */
public enum HttpError {
    ENCRYPT(460),
    DECRYPT(461),
    DATA_NOT_ENABLED(462),
    NO_CHANGE_NETWORK_STATE_PERMISSION(463),
    CELLULAR_NETWORK_NOT_AVAILABLE(464),
    CELLULAR_NETWORK_IO_EXCEPTION(600);
    
    public final int code;

    HttpError(int i) {
        this.code = i;
    }

    public Response result() {
        return new Response.Builder().code(this.code).body(toString()).build();
    }
}
