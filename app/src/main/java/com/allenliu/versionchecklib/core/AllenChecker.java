package com.allenliu.versionchecklib.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.allenliu.versionchecklib.core.http.AllenHttp;

@Deprecated
/* loaded from: classes.dex */
public class AllenChecker {
    private static boolean a = true;
    private static Context b;
    private static VersionParams c;

    public static void startVersionCheck(Application application, VersionParams versionParams) {
        b = application;
        c = versionParams;
        Intent intent = new Intent(application, versionParams.getService());
        intent.putExtra(AVersionService.VERSION_PARAMS_KEY, versionParams);
        application.stopService(intent);
        application.startService(intent);
    }

    public static void init(boolean z) {
        a = z;
    }

    public static boolean isDebug() {
        return a;
    }

    public static void cancelMission() {
        VersionParams versionParams;
        AllenHttp.getHttpClient().dispatcher().cancelAll();
        Context context = b;
        if (!(context == null || (versionParams = c) == null)) {
            b.stopService(new Intent(context, versionParams.getService()));
        }
        if (VersionDialogActivity.instance != null) {
            VersionDialogActivity.instance.finish();
        }
        b = null;
        c = null;
    }

    public static Context getGlobalContext() {
        return b;
    }
}
