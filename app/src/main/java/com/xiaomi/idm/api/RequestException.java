package com.xiaomi.idm.api;

import com.xiaomi.idm.api.ResponseCode;

/* loaded from: classes3.dex */
public class RequestException extends RmiException {
    public RequestException(int i) {
        this(i, ResponseCode.RequestCode.getResponseMsg(i));
    }

    public RequestException(ResponseCode.RequestCode requestCode) {
        this(requestCode.getCode());
    }

    public RequestException(int i, String str) {
        super(i, str);
    }
}
