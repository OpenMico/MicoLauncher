package org.hapjs.sdk.platform;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes5.dex */
class c {
    public static String a(PackageManager packageManager, String str) {
        PackageInfo b;
        try {
            b = b(packageManager, str);
        } catch (Exception e) {
            Log.w("PackageUtil", "getNativeAppSignMd5 error", e);
        }
        if (b == null) {
            return null;
        }
        Signature[] signatureArr = b.signatures;
        if (signatureArr.length > 0) {
            return a(signatureArr[0].toByteArray());
        }
        return null;
    }

    public static PackageInfo b(PackageManager packageManager, String str) {
        try {
            return packageManager.getPackageInfo(str, 64);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            instance.update(bArr);
            return String.format("%1$032X", new BigInteger(1, instance.digest())).toLowerCase();
        } catch (Exception e) {
            Log.w("PackageUtil", "getSha256 error", e);
            return null;
        }
    }
}
