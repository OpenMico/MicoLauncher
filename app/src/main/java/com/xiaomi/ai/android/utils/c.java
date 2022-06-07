package com.xiaomi.ai.android.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import com.xiaomi.ai.log.Logger;

/* loaded from: classes2.dex */
public final class c {
    public static byte[] a(Context context, String str) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(str, 64).signatures;
            if (signatureArr.length == 0) {
                return null;
            }
            return signatureArr[0].toByteArray();
        } catch (PackageManager.NameNotFoundException e) {
            Logger.d("PackageUtils", Log.getStackTraceString(e));
            return null;
        }
    }
}
