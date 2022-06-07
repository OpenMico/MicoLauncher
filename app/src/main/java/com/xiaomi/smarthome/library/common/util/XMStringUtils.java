package com.xiaomi.smarthome.library.common.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* loaded from: classes4.dex */
public class XMStringUtils {
    public static String getNonNullString(String str) {
        return str != null ? str : "";
    }

    public static boolean isAscii(int i) {
        return i >= 0 && i <= 255;
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

    public static String join(Object[] objArr, char c, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + 1));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            if (objArr[i4] != null) {
                sb.append(objArr[i4]);
            }
        }
        return sb.toString();
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
        StringBuilder sb = new StringBuilder(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + str.length()));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(str);
            }
            if (objArr[i4] != null) {
                sb.append(objArr[i4]);
            }
        }
        return sb.toString();
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
        StringBuilder sb = new StringBuilder(256);
        if (next != null) {
            sb.append(next);
        }
        while (it.hasNext()) {
            sb.append(c);
            Object next2 = it.next();
            if (next2 != null) {
                sb.append(next2);
            }
        }
        return sb.toString();
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
        StringBuilder sb = new StringBuilder(256);
        if (next != null) {
            sb.append(next);
        }
        while (it.hasNext()) {
            if (str != null) {
                sb.append(str);
            }
            Object next2 = it.next();
            if (next2 != null) {
                sb.append(next2);
            }
        }
        return sb.toString();
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
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(62)));
        }
        return sb.toString();
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
        for (byte b : bArr) {
            str = str + Integer.toString((b & 255) + 256, 16).substring(1);
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

    public static int getWordCount(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            i = isAscii(str, i2) ? i + 1 : i + 2;
        }
        return i;
    }

    public static String getSubWord(String str, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            i2 = isAscii(str, i3) ? i2 + 1 : i2 + 2;
            if (i2 == i) {
                return str.substring(0, i3 + 1);
            }
            if (i2 > i) {
                return str.substring(0, i3);
            }
        }
        return str;
    }

    public static boolean isAscii(String str, int i) {
        return isAscii(Character.codePointAt(str, i));
    }

    public static String getString(Context context, int i) {
        return context.getString(i);
    }

    public static String getQuantityString(Context context, int i, int i2, Object obj) {
        return context.getResources().getQuantityString(i, i2, obj);
    }

    public static String[] getStringArray(Context context, int i) {
        return context.getResources().getStringArray(i);
    }

    /* loaded from: classes4.dex */
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
}
