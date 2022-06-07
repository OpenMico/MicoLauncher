package com.xiaomi.accountsdk.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.account.exception.CryptoException;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class IpFilterHelper {
    private static final String SIMPLE_PATTERN_IP_FILTER = "\\/\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    private static final String TAG = "IpFilterHelper";
    private static volatile PublicKey sRSAPublicKey;

    IpFilterHelper() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String envIPAddressIfHas(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = Pattern.compile(SIMPLE_PATTERN_IP_FILTER).matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            str = str.replace(group, "(" + rsaEnv(group) + ")");
        }
        return str;
    }

    private static String rsaEnv(String str) {
        try {
            if (sRSAPublicKey == null) {
                sRSAPublicKey = RSACoder.getCertificatePublicKey(RSACoder.SPECIFIED_RSA_PUBLIC_KEY);
            }
            return Base64.encodeToString(RSACoder.encrypt(str.getBytes("UTF-8"), sRSAPublicKey), 0);
        } catch (CryptoException e) {
            AccountLog.w(TAG, e);
            return null;
        } catch (UnsupportedEncodingException e2) {
            AccountLog.w(TAG, e2);
            return null;
        }
    }
}
