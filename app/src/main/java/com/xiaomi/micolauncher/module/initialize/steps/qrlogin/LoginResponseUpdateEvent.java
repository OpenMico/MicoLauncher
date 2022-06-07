package com.xiaomi.micolauncher.module.initialize.steps.qrlogin;

import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;

/* loaded from: classes3.dex */
public class LoginResponseUpdateEvent {
    private ThirdPartyResponse.QrLoginResponse a;

    public LoginResponseUpdateEvent(ThirdPartyResponse.QrLoginResponse qrLoginResponse) {
        this.a = qrLoginResponse;
    }

    public ThirdPartyResponse.QrLoginResponse getLoginResponse() {
        return this.a;
    }
}
