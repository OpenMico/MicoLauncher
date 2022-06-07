package org.apache.commons.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class WordUtils {
    public static String wrap(String str, int i) {
        return wrap(str, i, null, false);
    }

    public static String wrap(String str, int i, String str2, boolean z) {
        return wrap(str, i, str2, z, StringUtils.SPACE);
    }

    public static String wrap(String str, int i, String str2, boolean z, String str3) {
        if (str == null) {
            return null;
        }
        if (str2 == null) {
            str2 = System.lineSeparator();
        }
        if (i < 1) {
            i = 1;
        }
        if (StringUtils.isBlank(str3)) {
            str3 = StringUtils.SPACE;
        }
        Pattern compile = Pattern.compile(str3);
        int length = str.length();
        int i2 = 0;
        StringBuilder sb = new StringBuilder(length + 32);
        while (i2 < length) {
            int i3 = -1;
            int i4 = i2 + i;
            Matcher matcher = compile.matcher(str.substring(i2, Math.min(i4 + 1, length)));
            if (matcher.find()) {
                if (matcher.start() == 0) {
                    i2 += matcher.end();
                } else {
                    i3 = matcher.start() + i2;
                }
            }
            if (length - i2 <= i) {
                break;
            }
            while (matcher.find()) {
                i3 = matcher.start() + i2;
            }
            if (i3 >= i2) {
                sb.append(str.substring(i2, i3));
                sb.append(str2);
                i2 = i3 + 1;
            } else if (z) {
                sb.append(str.substring(i2, i4));
                sb.append(str2);
                i2 = i4;
            } else {
                Matcher matcher2 = compile.matcher(str.substring(i4));
                if (matcher2.find()) {
                    i3 = matcher2.start() + i2 + i;
                }
                if (i3 >= 0) {
                    sb.append(str.substring(i2, i3));
                    sb.append(str2);
                    i2 = i3 + 1;
                } else {
                    sb.append(str.substring(i2));
                    i2 = length;
                }
            }
        }
        sb.append(str.substring(i2));
        return sb.toString();
    }

    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    public static String capitalize(String str, char... cArr) {
        int length = cArr == null ? -1 : cArr.length;
        if (StringUtils.isEmpty(str) || length == 0) {
            return str;
        }
        int length2 = str.length();
        int[] iArr = new int[length2];
        int i = 0;
        int i2 = 0;
        boolean z = true;
        while (i < length2) {
            int codePointAt = str.codePointAt(i);
            if (isDelimiter(codePointAt, cArr)) {
                i2++;
                iArr[i2] = codePointAt;
                i += Character.charCount(codePointAt);
                z = true;
            } else if (z) {
                int titleCase = Character.toTitleCase(codePointAt);
                i2++;
                iArr[i2] = titleCase;
                i += Character.charCount(titleCase);
                z = false;
            } else {
                i2++;
                iArr[i2] = codePointAt;
                i += Character.charCount(codePointAt);
            }
        }
        return new String(iArr, 0, i2);
    }

    public static String capitalizeFully(String str) {
        return capitalizeFully(str, null);
    }

    public static String capitalizeFully(String str, char... cArr) {
        return (StringUtils.isEmpty(str) || (cArr == null ? -1 : cArr.length) == 0) ? str : capitalize(str.toLowerCase(), cArr);
    }

    public static String uncapitalize(String str) {
        return uncapitalize(str, null);
    }

    public static String uncapitalize(String str, char... cArr) {
        int length = cArr == null ? -1 : cArr.length;
        if (StringUtils.isEmpty(str) || length == 0) {
            return str;
        }
        int length2 = str.length();
        int[] iArr = new int[length2];
        int i = 0;
        int i2 = 0;
        boolean z = true;
        while (i < length2) {
            int codePointAt = str.codePointAt(i);
            if (isDelimiter(codePointAt, cArr)) {
                i2++;
                iArr[i2] = codePointAt;
                i += Character.charCount(codePointAt);
                z = true;
            } else if (z) {
                int lowerCase = Character.toLowerCase(codePointAt);
                i2++;
                iArr[i2] = lowerCase;
                i += Character.charCount(lowerCase);
                z = false;
            } else {
                i2++;
                iArr[i2] = codePointAt;
                i += Character.charCount(codePointAt);
            }
        }
        return new String(iArr, 0, i2);
    }

    public static String swapCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        int[] iArr = new int[length];
        boolean z = true;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int codePointAt = str.codePointAt(i);
            if (Character.isUpperCase(codePointAt)) {
                codePointAt = Character.toLowerCase(codePointAt);
                z = false;
            } else if (Character.isTitleCase(codePointAt)) {
                codePointAt = Character.toLowerCase(codePointAt);
                z = false;
            } else if (!Character.isLowerCase(codePointAt)) {
                z = Character.isWhitespace(codePointAt);
            } else if (z) {
                codePointAt = Character.toTitleCase(codePointAt);
                z = false;
            } else {
                codePointAt = Character.toUpperCase(codePointAt);
            }
            i2++;
            iArr[i2] = codePointAt;
            i += Character.charCount(codePointAt);
        }
        return new String(iArr, 0, i2);
    }

    public static String initials(String str) {
        return initials(str, null);
    }

    public static String initials(String str, char... cArr) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (cArr != null && cArr.length == 0) {
            return "";
        }
        int length = str.length();
        char[] cArr2 = new char[(length / 2) + 1];
        boolean z = true;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (isDelimiter(charAt, cArr)) {
                z = true;
            } else if (z) {
                i++;
                cArr2[i] = charAt;
                z = false;
            }
        }
        return new String(cArr2, 0, i);
    }

    public static boolean containsAllWords(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArr)) {
            return false;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (StringUtils.isBlank(charSequence2)) {
                return false;
            }
            if (!Pattern.compile(".*\\b" + ((Object) charSequence2) + "\\b.*").matcher(charSequence).matches()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDelimiter(char c, char[] cArr) {
        if (cArr == null) {
            return Character.isWhitespace(c);
        }
        for (char c2 : cArr) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDelimiter(int i, char[] cArr) {
        if (cArr == null) {
            return Character.isWhitespace(i);
        }
        for (int i2 = 0; i2 < cArr.length; i2++) {
            if (Character.codePointAt(cArr, i2) == i) {
                return true;
            }
        }
        return false;
    }

    public static String abbreviate(String str, int i, int i2, String str2) {
        boolean z = true;
        Validate.isTrue(i2 >= -1, "upper value cannot be less than -1", new Object[0]);
        if (i2 < i && i2 != -1) {
            z = false;
        }
        Validate.isTrue(z, "upper value is less than lower value", new Object[0]);
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (i > str.length()) {
            i = str.length();
        }
        if (i2 == -1 || i2 > str.length()) {
            i2 = str.length();
        }
        StringBuilder sb = new StringBuilder();
        int indexOf = StringUtils.indexOf(str, StringUtils.SPACE, i);
        if (indexOf == -1) {
            sb.append(str.substring(0, i2));
            if (i2 != str.length()) {
                sb.append(StringUtils.defaultString(str2));
            }
        } else if (indexOf > i2) {
            sb.append(str.substring(0, i2));
            sb.append(StringUtils.defaultString(str2));
        } else {
            sb.append(str.substring(0, indexOf));
            sb.append(StringUtils.defaultString(str2));
        }
        return sb.toString();
    }
}
