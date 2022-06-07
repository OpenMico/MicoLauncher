package com.milink.base.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes2.dex */
public final class SignatureUtils {
    private static final String[] a = {"0", "1", "2", "3", Commands.ResolutionValues.BITSTREAM_BLUE_HIGH, Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND, "6", "7", "8", Commands.ResolutionValues.BITSTREAM_4K, "A", "B", HomePageRecordHelper.AREA_C, HomePageRecordHelper.AREA_D, "E", HomePageRecordHelper.AREA_F};

    private SignatureUtils() {
    }

    public static String toHexString(@NonNull byte[] bArr) {
        Objects.requireNonNull(bArr);
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(a(b));
        }
        return sb.toString();
    }

    @Nullable
    public static String getSignature(@NonNull Context context, @NonNull String str) {
        Signature[] signatureArr;
        String str2 = (String) Objects.requireNonNull(str);
        PackageManager packageManager = context.getPackageManager();
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                signatureArr = b(str2, packageManager);
            } else {
                signatureArr = a(str2, packageManager);
            }
            if (signatureArr != null && signatureArr.length >= 1) {
                return a(signatureArr[0].toByteArray());
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:0:?, code lost:
        r3 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(byte r3) {
        /*
            if (r3 >= 0) goto L_0x0004
            int r3 = r3 + 256
        L_0x0004:
            int r0 = r3 / 16
            int r3 = r3 % 16
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String[] r2 = com.milink.base.utils.SignatureUtils.a
            r0 = r2[r0]
            r1.append(r0)
            java.lang.String[] r0 = com.milink.base.utils.SignatureUtils.a
            r3 = r0[r3]
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.milink.base.utils.SignatureUtils.a(byte):java.lang.String");
    }

    private static Signature[] a(String str, PackageManager packageManager) throws PackageManager.NameNotFoundException {
        return packageManager.getPackageInfo(str, 64).signatures;
    }

    @RequiresApi(api = 28)
    private static Signature[] b(String str, PackageManager packageManager) throws PackageManager.NameNotFoundException {
        return packageManager.getPackageInfo(str, 134217728).signingInfo.getApkContentsSigners();
    }

    private static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        try {
            return toHexString(MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1).digest(bArr));
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }
}
