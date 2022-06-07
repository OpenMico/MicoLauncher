package com.xiaomi.passport.LocalFeatures;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/* loaded from: classes4.dex */
public interface MiLocalFeaturesManager {
    LocalFeaturesManagerFuture getStsUrl(String str, String str2, String str3, Bundle bundle, Activity activity, LocalFeaturesManagerCallback<Bundle> localFeaturesManagerCallback, Handler handler);

    LocalFeaturesManagerFuture<Bundle> handleLoginQRCodeScanResult(String str, Activity activity, Bundle bundle, LocalFeaturesManagerCallback<Bundle> localFeaturesManagerCallback, Handler handler);
}
