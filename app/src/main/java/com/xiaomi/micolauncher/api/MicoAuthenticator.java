package com.xiaomi.micolauncher.api;

import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.io.IOException;
import javax.annotation.Nullable;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/* loaded from: classes3.dex */
public class MicoAuthenticator implements Authenticator {
    @Override // okhttp3.Authenticator
    @Nullable
    public Request authenticate(Route route, Response response) throws IOException {
        if (!response.request().url().host().contains(ApiConstants.API_SERVER_BASE_URL)) {
            return null;
        }
        L.api.i("%s need refresh token", response.request().url().toString());
        ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken(Constants.MICO_SID);
        if (blockGetServiceToken == null || blockGetServiceToken.errorCode != ServiceTokenResult.ErrorCode.ERROR_NONE) {
            return null;
        }
        return response.request().newBuilder().build();
    }
}
