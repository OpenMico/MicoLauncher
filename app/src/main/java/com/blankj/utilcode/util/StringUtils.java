package com.blankj.utilcode.util;

import android.content.res.Resources;
import androidx.annotation.ArrayRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import java.util.IllegalFormatException;

/* loaded from: classes.dex */
public final class StringUtils {
    public static String null2Length0(String str) {
        return str == null ? "" : str;
    }

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isTrimEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        int length;
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || (length = charSequence.length()) != charSequence2.length()) {
            return false;
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return charSequence.equals(charSequence2);
        }
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equalsIgnoreCase(str2);
    }

    public static int length(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.length();
    }

    public static String upperFirstLetter(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (!Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        return ((char) (str.charAt(0) - ' ')) + str.substring(1);
    }

    public static String lowerFirstLetter(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (!Character.isUpperCase(str.charAt(0))) {
            return str;
        }
        return String.valueOf((char) (str.charAt(0) + ' ')) + str.substring(1);
    }

    public static String reverse(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        if (length <= 1) {
            return str;
        }
        int i = length >> 1;
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < i; i2++) {
            char c = charArray[i2];
            int i3 = (length - i2) - 1;
            charArray[i2] = charArray[i3];
            charArray[i3] = c;
        }
        return new String(charArray);
    }

    public static String toDBC(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            if (charArray[i] == 12288) {
                charArray[i] = ' ';
            } else if (65281 > charArray[i] || charArray[i] > 65374) {
                charArray[i] = charArray[i];
            } else {
                charArray[i] = (char) (charArray[i] - 65248);
            }
        }
        return new String(charArray);
    }

    public static String toSBC(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            if (charArray[i] == ' ') {
                charArray[i] = 12288;
            } else if ('!' > charArray[i] || charArray[i] > '~') {
                charArray[i] = charArray[i];
            } else {
                charArray[i] = (char) (charArray[i] + 65248);
            }
        }
        return new String(charArray);
    }

    public static String getString(@StringRes int i) {
        return getString(i, null);
    }

    public static String getString(@StringRes int i, Object... objArr) {
        try {
            return format(Utils.getApp().getString(i), objArr);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return String.valueOf(i);
        }
    }

    public static String[] getStringArray(@ArrayRes int i) {
        try {
            return Utils.getApp().getResources().getStringArray(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return new String[]{String.valueOf(i)};
        }
    }

    public static String format(@Nullable String str, Object... objArr) {
        if (str == null || objArr == null || objArr.length <= 0) {
            return str;
        }
        try {
            return String.format(str, objArr);
        } catch (IllegalFormatException e) {
            e.printStackTrace();
            return str;
        }
    }
}
