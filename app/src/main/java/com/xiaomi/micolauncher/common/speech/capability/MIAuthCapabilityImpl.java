package com.xiaomi.micolauncher.common.speech.capability;

import com.xiaomi.ai.android.capability.AuthCapability;
import com.xiaomi.micolauncher.api.model.PassportScope;
import com.xiaomi.micolauncher.api.service.ROPCGSServiceHelper;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.utils.OauthUtil;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public final class MIAuthCapabilityImpl extends AuthCapability {
    private volatile Thread b;
    private AtomicInteger c = new AtomicInteger(0);
    private AtomicReference<AuthCapability.AuthorizationTokens> a = new AtomicReference<>();

    @Override // com.xiaomi.ai.android.capability.AuthCapability
    public String onGetOAuthCode() {
        L.speech.d("MIAuthCapabilityImpl(%s).onGetOAuthCode", Integer.valueOf(hashCode()));
        return OauthUtil.getInstance().getOauthCode();
    }

    @Override // com.xiaomi.ai.android.capability.AuthCapability
    public String onGetToken(int i, boolean z) {
        L.speech.d("MIAuthCapabilityImpl(%s).onGetToken", Integer.valueOf(hashCode()));
        return OauthUtil.getInstance().getOauthToken();
    }

    @Override // com.xiaomi.ai.android.capability.AuthCapability
    public AuthCapability.AuthorizationTokens onGetAuthorizationTokens() {
        L.speech.d("MIAuthCapabilityImpl(%s).onGetAuthorizationTokens", Integer.valueOf(hashCode()));
        a();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        a(countDownLatch);
        try {
            countDownLatch.await(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            L.speech.e("MIAuthCapabilityImpl ROPCGSServiceHelper.getTokenResult timeout", e);
        }
        a();
        return this.a.get();
    }

    private void a() {
        Thread thread = this.b;
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    private void a(final CountDownLatch countDownLatch) {
        this.b = new Thread("MIAuthCapabilityImplThread-" + this.c.incrementAndGet()) { // from class: com.xiaomi.micolauncher.common.speech.capability.MIAuthCapabilityImpl.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                MIAuthCapabilityImpl.this.b(countDownLatch);
            }
        };
        this.b.setPriority(5);
        this.b.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(CountDownLatch countDownLatch) {
        L.speech.d("MIAuthCapabilityImpl loadToken");
        PassportScope.IssuedTokenResult tokenResult = ROPCGSServiceHelper.getTokenResult(MicoApplication.getGlobalContext());
        if (tokenResult == null) {
            L.speech.d("MIAuthCapabilityImpl onGetAuthorizationTokens result is empty");
            countDownLatch.countDown();
            return;
        }
        AuthCapability.AuthorizationTokens authorizationTokens = new AuthCapability.AuthorizationTokens();
        authorizationTokens.accessToken = tokenResult.accessToken;
        authorizationTokens.refreshToken = tokenResult.refreshToken;
        authorizationTokens.expireIn = tokenResult.expiresInSec;
        L.speech.d("MIAuthCapabilityImpl expiresIn=%s, accessToken=%s, refreshToken=%s", Long.valueOf(tokenResult.expiresInSec), tokenResult.accessToken, tokenResult.refreshToken);
        this.a.set(authorizationTokens);
        countDownLatch.countDown();
    }
}
