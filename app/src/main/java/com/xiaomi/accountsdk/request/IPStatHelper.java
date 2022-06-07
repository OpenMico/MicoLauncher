package com.xiaomi.accountsdk.request;

import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.utils.VersionUtils;

/* loaded from: classes2.dex */
public class IPStatHelper {
    protected AccountStatInterface getStatInterface() {
        return AccountStatInterface.getInstance();
    }

    protected void statCountEvent(String str, String str2) {
        getStatInterface().statCountEvent(str, str2);
    }

    protected void statDummyUrl(String str) {
        if (!VersionUtils.isMiuiStableVersion()) {
            getStatInterface().statHttpEvent(str, 0L);
        }
    }

    protected String getNetworkName() {
        return NetworkUtils.getNetworkNameForMiUrlStat(XMPassportSettings.getApplicationContext());
    }
}
