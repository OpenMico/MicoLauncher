package com.xiaomi.mico.base.utils;

import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.xiaomi.mico.utils.UtilsConfig;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes3.dex */
public class XMStringUtils {
    static final char[] a = "0123456789ABCDEF".toCharArray();

    public static String ellipsize(String str, int i) {
        if (str == null || str.length() < i) {
            return str;
        }
        return str.substring(0, i) + "...";
    }

    public static byte[] getRandomBytes(int i) {
        byte[] bArr = new byte[i];
        Random random = new Random();
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) random.nextInt(256);
        }
        return bArr;
    }

    public static String bytesToHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(String.format("%02X", Byte.valueOf(bArr[i])));
        }
        return sb.toString();
    }

    public static String join(Object[] objArr) {
        return join(objArr, (String) null);
    }

    public static String join(Object[] objArr, char c) {
        if (objArr == null) {
            return null;
        }
        return join(objArr, c, 0, objArr.length);
    }

    public static String join(String str, Object... objArr) {
        return join(objArr, str);
    }

    public static String join(Object[] objArr, char c, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + 1));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                stringBuffer.append(c);
            }
            if (objArr[i4] != null) {
                stringBuffer.append(objArr[i4]);
            }
        }
        return stringBuffer.toString();
    }

    public static String join(Object[] objArr, String str) {
        if (objArr == null) {
            return null;
        }
        return join(objArr, str, 0, objArr.length);
    }

    public static String join(Object[] objArr, String str, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        if (str == null) {
            str = "";
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + str.length()));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                stringBuffer.append(str);
            }
            if (objArr[i4] != null) {
                stringBuffer.append(objArr[i4]);
            }
        }
        return stringBuffer.toString();
    }

    public static String join(Iterator<?> it, char c) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return next.toString();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        if (next != null) {
            stringBuffer.append(next);
        }
        while (it.hasNext()) {
            stringBuffer.append(c);
            Object next2 = it.next();
            if (next2 != null) {
                stringBuffer.append(next2);
            }
        }
        return stringBuffer.toString();
    }

    public static String join(Iterator<?> it, String str) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return next.toString();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        if (next != null) {
            stringBuffer.append(next);
        }
        while (it.hasNext()) {
            if (str != null) {
                stringBuffer.append(str);
            }
            Object next2 = it.next();
            if (next2 != null) {
                stringBuffer.append(next2);
            }
        }
        return stringBuffer.toString();
    }

    public static String join(Collection<?> collection, char c) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), c);
    }

    public static String join(Collection<?> collection, String str) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), str);
    }

    public static String generateRandomString(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    public static int getStringUTF8Length(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return 0;
        }
    }

    public static boolean contains(String str, String str2) {
        int i = 0;
        int i2 = 0;
        while (i < str2.length() && i2 < str.length()) {
            if (str2.charAt(i) == str.charAt(i2)) {
                i++;
                i2++;
            } else {
                i2++;
            }
        }
        return i == str2.length();
    }

    public static String getHexString(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            str = str + Integer.toString((bArr[i] & 255) + 256, 16).substring(1);
        }
        return str;
    }

    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static String getStringNotNull(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    public static int stringToInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static String getMd5Digest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(getBytes(str));
            return String.format("%1$032X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    public static String[] toStrArray(List<String> list) {
        String[] strArr = new String[list.size()];
        list.toArray(strArr);
        return strArr;
    }

    public static long[] toLongArray(List<Long> list) {
        long[] jArr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            jArr[i] = list.get(i).longValue();
        }
        return jArr;
    }

    public static int[] toIntArray(List<Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    public static String getSHA1Digest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            instance.update(getBytes(str));
            return String.format("%1$040X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSHA1DigestInLowerCase(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            instance.update(getBytes(str));
            return String.format("%1$040x", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static void handleTextViewEllipsize(TextView textView, String str, int i) {
        textView.setText(str);
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new a(textView, str, i));
    }

    public static boolean isUrl(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.startsWith("http://") || str.startsWith("https://");
        }
        return false;
    }

    /* loaded from: classes3.dex */
    static class a implements ViewTreeObserver.OnGlobalLayoutListener {
        TextView a;
        String b;
        int c;

        public a(TextView textView, String str, int i) {
            this.a = textView;
            this.b = str;
            this.c = i;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            this.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            if (this.a.getLineCount() > this.c) {
                int lineEnd = this.a.getLayout().getLineEnd(this.c - 1);
                this.a.setText(((Object) this.b.subSequence(0, lineEnd - 3)) + "...");
            }
        }
    }

    public static List<String> split(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            return Arrays.asList(str.split(str2));
        }
        return null;
    }

    public static String concat(String str, String... strArr) {
        StringBuilder sb = new StringBuilder();
        if (strArr != null && strArr.length > 0) {
            if (str == null) {
                str = "";
            }
            for (String str2 : strArr) {
                if (!TextUtils.isEmpty(str2)) {
                    if (sb.length() > 0) {
                        sb.append(str);
                    }
                    sb.append(str2);
                }
            }
        }
        return sb.toString();
    }

    public static String getStringByAsciiLength(String str, int i) {
        if (str == null) {
            return null;
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < str.length(); i4++) {
            int codePointAt = Character.codePointAt(str, i4);
            i2 = (codePointAt < 0 || codePointAt > 255) ? i2 + 2 : i2 + 1;
            i3++;
            if (i2 >= i) {
                break;
            }
        }
        return i3 == str.length() ? str : str.substring(0, i3);
    }

    public static String getSha256(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            instance.update(str.getBytes());
            return bytesToHex(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            UtilsConfig.getLogCallback().e("failed to calculate sha256", e);
            return null;
        }
    }
}
