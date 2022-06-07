package com.xiaomi.micolauncher.common;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public abstract class AbstractHttpCallback implements Callback {
    @Override // okhttp3.Callback
    public void onFailure(@NotNull Call call, @NotNull IOException iOException) {
    }

    public abstract void processResponseOnce(@NotNull Call call, @NotNull Response response) throws IOException;

    @Override // okhttp3.Callback
    public final void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        try {
            processResponseOnce(call, response);
        } finally {
            response.close();
        }
    }
}
