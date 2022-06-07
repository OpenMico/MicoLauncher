package com.xiaomi.miot.lib;

import android.app.Application;
import java.util.Locale;

/* loaded from: classes2.dex */
public interface MiotConfig {
    Application getApplication();

    String getEncryptedUserId();

    Locale getLocale();

    String getMiId();

    String getServerName();

    Long getServerTimeDiff();

    String getServiceSecurity();

    String getServiceToken();

    boolean isDebug();

    boolean isPreview();

    void onAuthorizationError(int i);
}
