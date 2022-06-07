package com.xiaomi.smarthome.core.server.internal.account;

import com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo;

/* loaded from: classes4.dex */
public interface ServerTokenCallBack {
    void onFailure(int i, String str);

    void onSuccess(MiServiceTokenInfo miServiceTokenInfo);
}
