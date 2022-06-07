package com.xiaomi.smarthome.library.http.sync;

import okhttp3.Response;

/* loaded from: classes4.dex */
public class TextSyncHandler extends SyncHandler<String> {
    @Override // com.xiaomi.smarthome.library.http.sync.SyncHandler
    public String processResponse(Response response) throws Exception {
        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("failure code:" + response.code());
        }
    }
}
