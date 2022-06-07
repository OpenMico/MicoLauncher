package com.xiaomi.smarthome.library.http.sync;

import okhttp3.Response;

/* loaded from: classes4.dex */
public abstract class SyncHandler<T> {
    public abstract T processResponse(Response response) throws Exception;
}
