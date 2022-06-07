package com.allenliu.versionchecklib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionFileProvider;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import java.io.File;

/* loaded from: classes.dex */
public final class AppUtils {
    private AppUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static void installApk(Context context, File file) {
        Uri uri;
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= 24) {
            uri = VersionFileProvider.getUriForFile(context, context.getPackageName() + ".versionProvider", file);
            intent.addFlags(1);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
        AllenChecker.cancelMission();
        AllenVersionChecker.getInstance().cancelAllMission(context);
    }
}
