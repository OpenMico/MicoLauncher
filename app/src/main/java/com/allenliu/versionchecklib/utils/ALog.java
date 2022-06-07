package com.allenliu.versionchecklib.utils;

import android.util.Log;
import com.allenliu.versionchecklib.core.AllenChecker;

/* loaded from: classes.dex */
public class ALog {
    public static void e(String str) {
        if (AllenChecker.isDebug() && str != null && !str.isEmpty()) {
            Log.e("Allen Checker", str);
        }
    }
}
