package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* compiled from: SoundexUtils.java */
/* loaded from: classes5.dex */
final class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.isLetter(str.charAt(i2))) {
                i++;
                cArr[i] = str.charAt(i2);
            }
        }
        if (i == length) {
            return str.toUpperCase(Locale.ENGLISH);
        }
        return new String(cArr, 0, i).toUpperCase(Locale.ENGLISH);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(StringEncoder stringEncoder, String str, String str2) throws EncoderException {
        return a(stringEncoder.encode(str), stringEncoder.encode(str2));
    }

    static int a(String str, String str2) {
        if (str == null || str2 == null) {
            return 0;
        }
        int min = Math.min(str.length(), str2.length());
        int i = 0;
        for (int i2 = 0; i2 < min; i2++) {
            if (str.charAt(i2) == str2.charAt(i2)) {
                i++;
            }
        }
        return i;
    }
}
