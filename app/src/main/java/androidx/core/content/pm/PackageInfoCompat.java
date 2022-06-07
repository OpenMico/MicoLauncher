package androidx.core.content.pm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class PackageInfoCompat {
    public static long getLongVersionCode(@NonNull PackageInfo packageInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return packageInfo.getLongVersionCode();
        }
        return packageInfo.versionCode;
    }

    @NonNull
    public static List<Signature> getSignatures(@NonNull PackageManager packageManager, @NonNull String str) throws PackageManager.NameNotFoundException {
        Signature[] signatureArr;
        if (Build.VERSION.SDK_INT >= 28) {
            SigningInfo signingInfo = packageManager.getPackageInfo(str, 134217728).signingInfo;
            if (a.a(signingInfo)) {
                signatureArr = a.b(signingInfo);
            } else {
                signatureArr = a.c(signingInfo);
            }
        } else {
            signatureArr = packageManager.getPackageInfo(str, 64).signatures;
        }
        if (signatureArr == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(signatureArr);
    }

    public static boolean hasSignatures(@NonNull PackageManager packageManager, @NonNull String str, @NonNull @Size(min = 1) Map<byte[], Integer> map, boolean z) throws PackageManager.NameNotFoundException {
        if (map.isEmpty()) {
            return false;
        }
        Set<byte[]> keySet = map.keySet();
        for (byte[] bArr : keySet) {
            if (bArr != null) {
                Integer num = map.get(bArr);
                if (num != null) {
                    switch (num.intValue()) {
                        case 0:
                        case 1:
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported certificate type " + num + " when verifying " + str);
                    }
                } else {
                    throw new IllegalArgumentException("Type must be specified for cert when verifying " + str);
                }
            } else {
                throw new IllegalArgumentException("Cert byte array cannot be null when verifying " + str);
            }
        }
        List<Signature> signatures = getSignatures(packageManager, str);
        if (!z && Build.VERSION.SDK_INT >= 28) {
            for (byte[] bArr2 : keySet) {
                if (!a.a(packageManager, str, bArr2, map.get(bArr2).intValue())) {
                    return false;
                }
            }
            return true;
        } else if (signatures.size() == 0 || map.size() > signatures.size() || (z && map.size() != signatures.size())) {
            return false;
        } else {
            byte[][] bArr3 = null;
            if (map.containsValue(1)) {
                bArr3 = new byte[signatures.size()];
                for (int i = 0; i < signatures.size(); i++) {
                    bArr3[i] = a(signatures.get(i).toByteArray());
                }
            }
            Iterator<byte[]> it = keySet.iterator();
            if (!it.hasNext()) {
                return false;
            }
            byte[] next = it.next();
            Integer num2 = map.get(next);
            switch (num2.intValue()) {
                case 0:
                    if (!signatures.contains(new Signature(next))) {
                        return false;
                    }
                    break;
                case 1:
                    if (!a(bArr3, next)) {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported certificate type " + num2);
            }
            return true;
        }
    }

    private static boolean a(@NonNull byte[][] bArr, @NonNull byte[] bArr2) {
        for (byte[] bArr3 : bArr) {
            if (Arrays.equals(bArr2, bArr3)) {
                return true;
            }
        }
        return false;
    }

    private static byte[] a(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA256").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Device doesn't support SHA256 cert checking", e);
        }
    }

    private PackageInfoCompat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(28)
    /* loaded from: classes.dex */
    public static class a {
        static boolean a(@NonNull PackageManager packageManager, @NonNull String str, @NonNull byte[] bArr, int i) {
            return packageManager.hasSigningCertificate(str, bArr, i);
        }

        static boolean a(@NonNull SigningInfo signingInfo) {
            return signingInfo.hasMultipleSigners();
        }

        @Nullable
        static Signature[] b(@NonNull SigningInfo signingInfo) {
            return signingInfo.getApkContentsSigners();
        }

        @Nullable
        static Signature[] c(@NonNull SigningInfo signingInfo) {
            return signingInfo.getSigningCertificateHistory();
        }
    }
}
