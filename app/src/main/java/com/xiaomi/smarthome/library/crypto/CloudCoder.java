package com.xiaomi.smarthome.library.crypto;

import android.net.Uri;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public class CloudCoder {
    private static Random a = new SecureRandom();

    public static String hash4SHA1(String str) {
        try {
            return String.valueOf(Base64Coder.encode(MessageDigest.getInstance("SHA1").digest(str.getBytes("UTF-8"))));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            throw new IllegalStateException("failed to SHA1");
        }
    }

    public static String generateSignature(String str, String str2, Map<String, String> map, String str3) {
        if (!TextUtils.isEmpty(str3)) {
            ArrayList<String> arrayList = new ArrayList();
            if (str != null) {
                arrayList.add(str.toUpperCase());
            }
            if (str2 != null) {
                arrayList.add(Uri.parse(str2).getEncodedPath());
            }
            boolean z = true;
            if (map != null && !map.isEmpty()) {
                for (Map.Entry entry : new TreeMap(map).entrySet()) {
                    arrayList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
                }
            }
            arrayList.add(str3);
            StringBuilder sb = new StringBuilder();
            for (String str4 : arrayList) {
                if (!z) {
                    sb.append(Typography.amp);
                }
                sb.append(str4);
                z = false;
            }
            return hash4SHA1(sb.toString());
        }
        throw new InvalidParameterException("security is not nullable");
    }

    public static String generateNonce(long j) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeLong(a.nextLong());
            dataOutputStream.writeInt((int) ((System.currentTimeMillis() + j) / 60000));
            dataOutputStream.flush();
        } catch (IOException unused) {
        }
        return String.valueOf(Base64Coder.encode(byteArrayOutputStream.toByteArray()));
    }

    public static String generateNonceTime(long j) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeLong(a.nextLong());
            dataOutputStream.writeInt((int) (j / 60000));
            dataOutputStream.flush();
        } catch (IOException unused) {
        }
        return String.valueOf(Base64Coder.encode(byteArrayOutputStream.toByteArray()));
    }
}
