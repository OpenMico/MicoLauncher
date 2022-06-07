package com.xiaomi.ai.android.capability;

/* loaded from: classes2.dex */
public abstract class AuthCapability implements Capability {

    /* loaded from: classes2.dex */
    public class AuthorizationTokens {
        public String accessToken;
        public long expireIn;
        public String refreshToken;

        public AuthorizationTokens() {
        }
    }

    public String getOaid() {
        throw new IllegalStateException("getOaid not Override");
    }

    public AuthorizationTokens onGetAuthorizationTokens() {
        throw new IllegalStateException("onGetAuthorizationTokens not Override");
    }

    public String onGetOAuthCode() {
        throw new IllegalStateException("onGetOAuthCode not Override");
    }

    public String onGetToken(int i, boolean z) {
        throw new IllegalStateException("onGetToken not Override");
    }
}
