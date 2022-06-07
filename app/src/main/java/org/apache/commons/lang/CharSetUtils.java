package org.apache.commons.lang;

import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class CharSetUtils {
    public static CharSet evaluateSet(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        return new CharSet(strArr);
    }

    public static String squeeze(String str, String str2) {
        return (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) ? str : squeeze(str, new String[]{str2});
    }

    public static String squeeze(String str, String[] strArr) {
        if (StringUtils.isEmpty(str) || ArrayUtils.isEmpty(strArr)) {
            return str;
        }
        CharSet instance = CharSet.getInstance(strArr);
        StrBuilder strBuilder = new StrBuilder(str.length());
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        char c = ' ';
        for (int i = 0; i < length; i++) {
            char c2 = charArray[i];
            if (!instance.contains(c2) || c2 != c || i == 0) {
                strBuilder.append(c2);
                c = c2;
            }
        }
        return strBuilder.toString();
    }

    public static int count(String str, String str2) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) {
            return 0;
        }
        return count(str, new String[]{str2});
    }

    public static int count(String str, String[] strArr) {
        if (StringUtils.isEmpty(str) || ArrayUtils.isEmpty(strArr)) {
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

    public static String keep(String str, String str2) {
        if (str == null) {
            return null;
        }
        return (str.length() == 0 || StringUtils.isEmpty(str2)) ? "" : keep(str, new String[]{str2});
    }

    public static String keep(String str, String[] strArr) {
        if (str == null) {
            return null;
        }
        return (str.length() == 0 || ArrayUtils.isEmpty(strArr)) ? "" : a(str, strArr, true);
    }

    public static String delete(String str, String str2) {
        return (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) ? str : delete(str, new String[]{str2});
    }

    public static String delete(String str, String[] strArr) {
        return (StringUtils.isEmpty(str) || ArrayUtils.isEmpty(strArr)) ? str : a(str, strArr, false);
    }

    private static String a(String str, String[] strArr, boolean z) {
        CharSet instance = CharSet.getInstance(strArr);
        StrBuilder strBuilder = new StrBuilder(str.length());
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            if (instance.contains(charArray[i]) == z) {
                strBuilder.append(charArray[i]);
            }
        }
        return strBuilder.toString();
    }

    public static String translate(String str, String str2, String str3) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        StrBuilder strBuilder = new StrBuilder(str.length());
        char[] charArray = str.toCharArray();
        char[] charArray2 = str3.toCharArray();
        int length = charArray.length;
        int length2 = str3.length() - 1;
        for (int i = 0; i < length; i++) {
            int indexOf = str2.indexOf(charArray[i]);
            if (indexOf != -1) {
                if (indexOf > length2) {
                    indexOf = length2;
                }
                strBuilder.append(charArray2[indexOf]);
            } else {
                strBuilder.append(charArray[i]);
            }
        }
        return strBuilder.toString();
    }
}
