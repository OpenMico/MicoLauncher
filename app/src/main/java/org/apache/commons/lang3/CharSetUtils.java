package org.apache.commons.lang3;

/* loaded from: classes5.dex */
public class CharSetUtils {
    public static String squeeze(String str, String... strArr) {
        if (StringUtils.isEmpty(str) || a(strArr)) {
            return str;
        }
        CharSet instance = CharSet.getInstance(strArr);
        StringBuilder sb = new StringBuilder(str.length());
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        char c = charArray[0];
        sb.append(c);
        Character ch = null;
        Character ch2 = null;
        for (int i = 1; i < length; i++) {
            char c2 = charArray[i];
            if (c2 == c) {
                if (ch == null || c2 != ch.charValue()) {
                    if (ch2 == null || c2 != ch2.charValue()) {
                        if (instance.contains(c2)) {
                            ch = Character.valueOf(c2);
                        } else {
                            ch2 = Character.valueOf(c2);
                        }
                    }
                }
            }
            sb.append(c2);
            c = c2;
        }
        return sb.toString();
    }

    public static boolean containsAny(String str, String... strArr) {
        if (StringUtils.isEmpty(str) || a(strArr)) {
            return false;
        }
        CharSet instance = CharSet.getInstance(strArr);
        for (char c : str.toCharArray()) {
            if (instance.contains(c)) {
                return true;
            }
        }
        return false;
    }

    public static int count(String str, String... strArr) {
        if (StringUtils.isEmpty(str) || a(strArr)) {
            return 0;
        }
        CharSet instance = CharSet.getInstance(strArr);
        int i = 0;
        for (char c : str.toCharArray()) {
            if (instance.contains(c)) {
                i++;
            }
        }
        return i;
    }

    public static String keep(String str, String... strArr) {
        if (str == null) {
            return null;
        }
        return (str.isEmpty() || a(strArr)) ? "" : a(str, strArr, true);
    }

    public static String delete(String str, String... strArr) {
        return (StringUtils.isEmpty(str) || a(strArr)) ? str : a(str, strArr, false);
    }

    private static String a(String str, String[] strArr, boolean z) {
        CharSet instance = CharSet.getInstance(strArr);
        StringBuilder sb = new StringBuilder(str.length());
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            if (instance.contains(charArray[i]) == z) {
                sb.append(charArray[i]);
            }
        }
        return sb.toString();
    }

    private static boolean a(String[] strArr) {
        if (strArr == null) {
            return true;
        }
        for (String str : strArr) {
            if (StringUtils.isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }
}
