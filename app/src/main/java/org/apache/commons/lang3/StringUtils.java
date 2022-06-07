package org.apache.commons.lang3;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class StringUtils {
    public static final String CR = "\r";
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    public static final String LF = "\n";
    public static final String SPACE = " ";

    public static String defaultString(String str) {
        return str == null ? "" : str;
    }

    public static String defaultString(String str, String str2) {
        return str == null ? str2 : str;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static boolean isAnyEmpty(CharSequence... charSequenceArr) {
        if (ArrayUtils.isEmpty(charSequenceArr)) {
            return true;
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (isEmpty(charSequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneEmpty(CharSequence... charSequenceArr) {
        return !isAnyEmpty(charSequenceArr);
    }

    public static boolean isBlank(CharSequence charSequence) {
        int length;
        if (charSequence == null || (length = charSequence.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    public static boolean isAnyBlank(CharSequence... charSequenceArr) {
        if (ArrayUtils.isEmpty(charSequenceArr)) {
            return true;
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (isBlank(charSequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneBlank(CharSequence... charSequenceArr) {
        return !isAnyBlank(charSequenceArr);
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static String trimToNull(String str) {
        String trim = trim(str);
        if (isEmpty(trim)) {
            return null;
        }
        return trim;
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static String truncate(String str, int i) {
        return truncate(str, 0, i);
    }

    public static String truncate(String str, int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        } else if (i2 < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        } else if (str == null) {
            return null;
        } else {
            if (i > str.length()) {
                return "";
            }
            if (str.length() <= i2) {
                return str.substring(i);
            }
            int i3 = i2 + i;
            if (i3 > str.length()) {
                i3 = str.length();
            }
            return str.substring(i, i3);
        }
    }

    public static String strip(String str) {
        return strip(str, null);
    }

    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        String strip = strip(str, null);
        if (strip.isEmpty()) {
            return null;
        }
        return strip;
    }

    public static String stripToEmpty(String str) {
        return str == null ? "" : strip(str, null);
    }

    public static String strip(String str, String str2) {
        return isEmpty(str) ? str : stripEnd(stripStart(str, str2), str2);
    }

    public static String stripStart(String str, String str2) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        int i = 0;
        if (str2 == null) {
            while (i != length && Character.isWhitespace(str.charAt(i))) {
                i++;
            }
        } else if (str2.isEmpty()) {
            return str;
        } else {
            while (i != length && str2.indexOf(str.charAt(i)) != -1) {
                i++;
            }
        }
        return str.substring(i);
    }

    public static String stripEnd(String str, String str2) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        if (str2 == null) {
            while (length != 0 && Character.isWhitespace(str.charAt(length - 1))) {
                length--;
            }
        } else if (str2.isEmpty()) {
            return str;
        } else {
            while (length != 0 && str2.indexOf(str.charAt(length - 1)) != -1) {
                length--;
            }
        }
        return str.substring(0, length);
    }

    public static String[] stripAll(String... strArr) {
        return stripAll(strArr, null);
    }

    public static String[] stripAll(String[] strArr, String str) {
        int length;
        if (strArr == null || (length = strArr.length) == 0) {
            return strArr;
        }
        String[] strArr2 = new String[length];
        for (int i = 0; i < length; i++) {
            strArr2[i] = strip(strArr[i], str);
        }
        return strArr2;
    }

    public static String stripAccents(String str) {
        if (str == null) {
            return null;
        }
        Pattern compile = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        StringBuilder sb = new StringBuilder(Normalizer.normalize(str, Normalizer.Form.NFD));
        a(sb);
        return compile.matcher(sb).replaceAll("");
    }

    private static void a(StringBuilder sb) {
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == 321) {
                sb.deleteCharAt(i);
                sb.insert(i, 'L');
            } else if (sb.charAt(i) == 322) {
                sb.deleteCharAt(i);
                sb.insert(i, 'l');
            }
        }
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || charSequence.length() != charSequence2.length()) {
            return false;
        }
        if (!(charSequence instanceof String) || !(charSequence2 instanceof String)) {
            return CharSequenceUtils.a(charSequence, false, 0, charSequence2, 0, charSequence.length());
        }
        return charSequence.equals(charSequence2);
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == charSequence2;
        }
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence.length() != charSequence2.length()) {
            return false;
        }
        return CharSequenceUtils.a(charSequence, true, 0, charSequence2, 0, charSequence.length());
    }

    public static int compare(String str, String str2) {
        return compare(str, str2, true);
    }

    public static int compare(String str, String str2, boolean z) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return z ? -1 : 1;
        }
        if (str2 == null) {
            return z ? 1 : -1;
        }
        return str.compareTo(str2);
    }

    public static int compareIgnoreCase(String str, String str2) {
        return compareIgnoreCase(str, str2, true);
    }

    public static int compareIgnoreCase(String str, String str2, boolean z) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return z ? -1 : 1;
        }
        if (str2 == null) {
            return z ? 1 : -1;
        }
        return str.compareToIgnoreCase(str2);
    }

    public static boolean equalsAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (ArrayUtils.isNotEmpty(charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (equals(charSequence, charSequence2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean equalsAnyIgnoreCase(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (ArrayUtils.isNotEmpty(charSequenceArr)) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (equalsIgnoreCase(charSequence, charSequence2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int indexOf(CharSequence charSequence, int i) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.a(charSequence, i, 0);
    }

    public static int indexOf(CharSequence charSequence, int i, int i2) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.a(charSequence, i, i2);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.a(charSequence, charSequence2, 0);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.a(charSequence, charSequence2, i);
    }

    public static int ordinalIndexOf(CharSequence charSequence, CharSequence charSequence2, int i) {
        return a(charSequence, charSequence2, i, false);
    }

    private static int a(CharSequence charSequence, CharSequence charSequence2, int i, boolean z) {
        int i2 = -1;
        if (charSequence == null || charSequence2 == null || i <= 0) {
            return -1;
        }
        int i3 = 0;
        if (charSequence2.length() != 0) {
            if (z) {
                i2 = charSequence.length();
            }
            do {
                if (z) {
                    i2 = CharSequenceUtils.b(charSequence, charSequence2, i2 - 1);
                } else {
                    i2 = CharSequenceUtils.a(charSequence, charSequence2, i2 + 1);
                }
                if (i2 < 0) {
                    return i2;
                }
                i3++;
            } while (i3 < i);
            return i2;
        } else if (z) {
            return charSequence.length();
        } else {
            return 0;
        }
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return indexOfIgnoreCase(charSequence, charSequence2, 0);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        int length = (charSequence.length() - charSequence2.length()) + 1;
        if (i > length) {
            return -1;
        }
        if (charSequence2.length() == 0) {
            return i;
        }
        while (i < length) {
            if (CharSequenceUtils.a(charSequence, true, i, charSequence2, 0, charSequence2.length())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(CharSequence charSequence, int i) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.b(charSequence, i, charSequence.length());
    }

    public static int lastIndexOf(CharSequence charSequence, int i, int i2) {
        if (isEmpty(charSequence)) {
            return -1;
        }
        return CharSequenceUtils.b(charSequence, i, i2);
    }

    public static int lastIndexOf(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.b(charSequence, charSequence2, charSequence.length());
    }

    public static int lastOrdinalIndexOf(CharSequence charSequence, CharSequence charSequence2, int i) {
        return a(charSequence, charSequence2, i, true);
    }

    public static int lastIndexOf(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return CharSequenceUtils.b(charSequence, charSequence2, i);
    }

    public static int lastIndexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        return lastIndexOfIgnoreCase(charSequence, charSequence2, charSequence.length());
    }

    public static int lastIndexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        if (i > charSequence.length() - charSequence2.length()) {
            i = charSequence.length() - charSequence2.length();
        }
        if (i < 0) {
            return -1;
        }
        if (charSequence2.length() == 0) {
            return i;
        }
        while (i >= 0) {
            if (CharSequenceUtils.a(charSequence, true, i, charSequence2, 0, charSequence2.length())) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(CharSequence charSequence, int i) {
        return !isEmpty(charSequence) && CharSequenceUtils.a(charSequence, i, 0) >= 0;
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return (charSequence == null || charSequence2 == null || CharSequenceUtils.a(charSequence, charSequence2, 0) < 0) ? false : true;
    }

    public static boolean containsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        int length = charSequence2.length();
        int length2 = charSequence.length() - length;
        for (int i = 0; i <= length2; i++) {
            if (CharSequenceUtils.a(charSequence, true, i, charSequence2, 0, length)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsWhitespace(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(charSequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int indexOfAny(CharSequence charSequence, char... cArr) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(cArr)) {
            return -1;
        }
        int length = charSequence.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        int i3 = 0;
        while (i3 < length) {
            char charAt = charSequence.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt && (i3 >= i || i4 >= i2 || !Character.isHighSurrogate(charAt) || cArr[i4 + 1] == charSequence.charAt(i3 + 1))) {
                    return i3;
                }
            }
            i3++;
        }
        return -1;
    }

    public static int indexOfAny(CharSequence charSequence, String str) {
        if (isEmpty(charSequence) || isEmpty(str)) {
            return -1;
        }
        return indexOfAny(charSequence, str.toCharArray());
    }

    public static boolean containsAny(CharSequence charSequence, char... cArr) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(cArr)) {
            return false;
        }
        int length = charSequence.length();
        int length2 = cArr.length;
        int i = length - 1;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = charSequence.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt) {
                    if (!Character.isHighSurrogate(charAt) || i4 == i2) {
                        return true;
                    }
                    if (i3 < i && cArr[i4 + 1] == charSequence.charAt(i3 + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean containsAny(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2 == null) {
            return false;
        }
        return containsAny(charSequence, CharSequenceUtils.a(charSequence2));
    }

    public static boolean containsAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArr)) {
            return false;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (contains(charSequence, charSequence2)) {
                return true;
            }
        }
        return false;
    }

    public static int indexOfAnyBut(CharSequence charSequence, char... cArr) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(cArr)) {
            return -1;
        }
        int length = charSequence.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = charSequence.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] != charAt || (i3 < i && i4 < i2 && Character.isHighSurrogate(charAt) && cArr[i4 + 1] != charSequence.charAt(i3 + 1))) {
                }
            }
            return i3;
        }
        return -1;
    }

    public static int indexOfAnyBut(CharSequence charSequence, CharSequence charSequence2) {
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return -1;
        }
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            boolean z = CharSequenceUtils.a(charSequence2, charAt, 0) >= 0;
            int i2 = i + 1;
            if (i2 < length && Character.isHighSurrogate(charAt)) {
                char charAt2 = charSequence.charAt(i2);
                if (z && CharSequenceUtils.a(charSequence2, charAt2, 0) < 0) {
                    return i;
                }
            } else if (!z) {
                return i;
            }
            i = i2;
        }
        return -1;
    }

    public static boolean containsOnly(CharSequence charSequence, char... cArr) {
        if (cArr == null || charSequence == null) {
            return false;
        }
        if (charSequence.length() == 0) {
            return true;
        }
        return cArr.length != 0 && indexOfAnyBut(charSequence, cArr) == -1;
    }

    public static boolean containsOnly(CharSequence charSequence, String str) {
        if (charSequence == null || str == null) {
            return false;
        }
        return containsOnly(charSequence, str.toCharArray());
    }

    public static boolean containsNone(CharSequence charSequence, char... cArr) {
        if (charSequence == null || cArr == null) {
            return true;
        }
        int length = charSequence.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = charSequence.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt) {
                    if (!Character.isHighSurrogate(charAt) || i4 == i2) {
                        return false;
                    }
                    if (i3 < i && cArr[i4 + 1] == charSequence.charAt(i3 + 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean containsNone(CharSequence charSequence, String str) {
        if (charSequence == null || str == null) {
            return true;
        }
        return containsNone(charSequence, str.toCharArray());
    }

    public static int indexOfAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        int a;
        if (charSequence == null || charSequenceArr == null) {
            return -1;
        }
        int i = Integer.MAX_VALUE;
        for (CharSequence charSequence2 : charSequenceArr) {
            if (!(charSequence2 == null || (a = CharSequenceUtils.a(charSequence, charSequence2, 0)) == -1 || a >= i)) {
                i = a;
            }
        }
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i;
    }

    public static int lastIndexOfAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        int b;
        int i = -1;
        if (charSequence == null || charSequenceArr == null) {
            return -1;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (charSequence2 != null && (b = CharSequenceUtils.b(charSequence, charSequence2, charSequence.length())) > i) {
                i = b;
            }
        }
        return i;
    }

    public static String substring(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i < 0) {
            i += str.length();
        }
        if (i < 0) {
            i = 0;
        }
        return i > str.length() ? "" : str.substring(i);
    }

    public static String substring(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 < 0) {
            i2 += str.length();
        }
        if (i < 0) {
            i += str.length();
        }
        if (i2 > str.length()) {
            i2 = str.length();
        }
        if (i > i2) {
            return "";
        }
        if (i < 0) {
            i = 0;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        return str.substring(i, i2);
    }

    public static String left(String str, int i) {
        if (str == null) {
            return null;
        }
        return i < 0 ? "" : str.length() <= i ? str : str.substring(0, i);
    }

    public static String right(String str, int i) {
        if (str == null) {
            return null;
        }
        return i < 0 ? "" : str.length() <= i ? str : str.substring(str.length() - i);
    }

    public static String mid(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 < 0 || i > str.length()) {
            return "";
        }
        if (i < 0) {
            i = 0;
        }
        int i3 = i2 + i;
        if (str.length() <= i3) {
            return str.substring(i);
        }
        return str.substring(i, i3);
    }

    public static String substringBefore(String str, String str2) {
        if (isEmpty(str) || str2 == null) {
            return str;
        }
        if (str2.isEmpty()) {
            return "";
        }
        int indexOf = str.indexOf(str2);
        return indexOf == -1 ? str : str.substring(0, indexOf);
    }

    public static String substringAfter(String str, String str2) {
        int indexOf;
        return isEmpty(str) ? str : (str2 == null || (indexOf = str.indexOf(str2)) == -1) ? "" : str.substring(indexOf + str2.length());
    }

    public static String substringBeforeLast(String str, String str2) {
        int lastIndexOf;
        return (isEmpty(str) || isEmpty(str2) || (lastIndexOf = str.lastIndexOf(str2)) == -1) ? str : str.substring(0, lastIndexOf);
    }

    public static String substringAfterLast(String str, String str2) {
        int lastIndexOf;
        return isEmpty(str) ? str : (isEmpty(str2) || (lastIndexOf = str.lastIndexOf(str2)) == -1 || lastIndexOf == str.length() - str2.length()) ? "" : str.substring(lastIndexOf + str2.length());
    }

    public static String substringBetween(String str, String str2) {
        return substringBetween(str, str2, str2);
    }

    public static String substringBetween(String str, String str2, String str3) {
        int indexOf;
        int indexOf2;
        if (str == null || str2 == null || str3 == null || (indexOf = str.indexOf(str2)) == -1 || (indexOf2 = str.indexOf(str3, str2.length() + indexOf)) == -1) {
            return null;
        }
        return str.substring(indexOf + str2.length(), indexOf2);
    }

    public static String[] substringsBetween(String str, String str2, String str3) {
        int indexOf;
        int i;
        int indexOf2;
        if (str == null || isEmpty(str2) || isEmpty(str3)) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        int length2 = str3.length();
        int length3 = str2.length();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < length - length2 && (indexOf = str.indexOf(str2, i2)) >= 0 && (indexOf2 = str.indexOf(str3, (i = indexOf + length3))) >= 0) {
            arrayList.add(str.substring(i, indexOf2));
            i2 = indexOf2 + length2;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] split(String str) {
        return split(str, null, -1);
    }

    public static String[] split(String str, char c) {
        return a(str, c, false);
    }

    public static String[] split(String str, String str2) {
        return b(str, str2, -1, false);
    }

    public static String[] split(String str, String str2, int i) {
        return b(str, str2, i, false);
    }

    public static String[] splitByWholeSeparator(String str, String str2) {
        return a(str, str2, -1, false);
    }

    public static String[] splitByWholeSeparator(String str, String str2, int i) {
        return a(str, str2, i, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String str2) {
        return a(str, str2, -1, true);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String str2, int i) {
        return a(str, str2, i, true);
    }

    private static String[] a(String str, String str2, int i, boolean z) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (str2 == null || "".equals(str2)) {
            return b(str, (String) null, i, z);
        }
        int length2 = str2.length();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < length) {
            i2 = str.indexOf(str2, i3);
            if (i2 <= -1) {
                arrayList.add(str.substring(i3));
                i2 = length;
            } else if (i2 > i3) {
                i4++;
                if (i4 == i) {
                    arrayList.add(str.substring(i3));
                    i2 = length;
                } else {
                    arrayList.add(str.substring(i3, i2));
                    i3 = i2 + length2;
                }
            } else {
                if (z) {
                    i4++;
                    if (i4 == i) {
                        arrayList.add(str.substring(i3));
                        i2 = length;
                    } else {
                        arrayList.add("");
                    }
                }
                i3 = i2 + length2;
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitPreserveAllTokens(String str) {
        return b(str, (String) null, -1, true);
    }

    public static String[] splitPreserveAllTokens(String str, char c) {
        return a(str, c, true);
    }

    private static String[] a(String str, char c, boolean z) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        int i2 = 0;
        while (i < length) {
            if (str.charAt(i) == c) {
                if (z2 || z) {
                    arrayList.add(str.substring(i2, i));
                    z3 = true;
                    z2 = false;
                }
                i2 = i + 1;
                i = i2;
            } else {
                i++;
                z2 = true;
                z3 = false;
            }
        }
        if (z2 || (z && z3)) {
            arrayList.add(str.substring(i2, i));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitPreserveAllTokens(String str, String str2) {
        return b(str, str2, -1, true);
    }

    public static String[] splitPreserveAllTokens(String str, String str2, int i) {
        return b(str, str2, i, true);
    }

    private static String[] b(String str, String str2, int i, boolean z) {
        int i2;
        boolean z2;
        boolean z3;
        int i3;
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList arrayList = new ArrayList();
        if (str2 == null) {
            int i4 = 0;
            boolean z4 = false;
            boolean z5 = false;
            int i5 = 0;
            int i6 = 1;
            while (i4 < length) {
                if (Character.isWhitespace(str.charAt(i4))) {
                    if (z4 || z) {
                        i6++;
                        if (i6 == i) {
                            i4 = length;
                            z5 = false;
                        } else {
                            z5 = true;
                        }
                        arrayList.add(str.substring(i5, i4));
                        z4 = false;
                    }
                    i5 = i4 + 1;
                    i4 = i5;
                } else {
                    i4++;
                    z5 = false;
                    z4 = true;
                }
            }
            i2 = i5;
            z2 = z5;
            z3 = z4;
            i3 = i4;
        } else if (str2.length() == 1) {
            char charAt = str2.charAt(0);
            i3 = 0;
            z3 = false;
            z2 = false;
            i2 = 0;
            int i7 = 1;
            while (i3 < length) {
                if (str.charAt(i3) == charAt) {
                    if (z3 || z) {
                        i7++;
                        if (i7 == i) {
                            i3 = length;
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        arrayList.add(str.substring(i2, i3));
                        z3 = false;
                    }
                    i2 = i3 + 1;
                    i3 = i2;
                } else {
                    i3++;
                    z2 = false;
                    z3 = true;
                }
            }
        } else {
            i3 = 0;
            z3 = false;
            z2 = false;
            i2 = 0;
            int i8 = 1;
            while (i3 < length) {
                if (str2.indexOf(str.charAt(i3)) >= 0) {
                    if (z3 || z) {
                        i8++;
                        if (i8 == i) {
                            i3 = length;
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        arrayList.add(str.substring(i2, i3));
                        z3 = false;
                    }
                    i2 = i3 + 1;
                    i3 = i2;
                } else {
                    i3++;
                    z2 = false;
                    z3 = true;
                }
            }
        }
        if (z3 || (z && z2)) {
            arrayList.add(str.substring(i2, i3));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitByCharacterType(String str) {
        return a(str, false);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return a(str, true);
    }

    private static String[] a(String str, boolean z) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        int type = Character.getType(charArray[0]);
        int i = 0;
        for (int i2 = 1; i2 < charArray.length; i2++) {
            int type2 = Character.getType(charArray[i2]);
            if (type2 != type) {
                if (z && type2 == 2 && type == 1) {
                    int i3 = i2 - 1;
                    if (i3 != i) {
                        arrayList.add(new String(charArray, i, i3 - i));
                        i = i3;
                    }
                } else {
                    arrayList.add(new String(charArray, i, i2 - i));
                    i = i2;
                }
                type = type2;
            }
        }
        arrayList.add(new String(charArray, i, charArray.length - i));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static <T> String join(T... tArr) {
        return join(tArr, (String) null);
    }

    public static String join(Object[] objArr, char c) {
        if (objArr == null) {
            return null;
        }
        return join(objArr, c, 0, objArr.length);
    }

    public static String join(long[] jArr, char c) {
        if (jArr == null) {
            return null;
        }
        return join(jArr, c, 0, jArr.length);
    }

    public static String join(int[] iArr, char c) {
        if (iArr == null) {
            return null;
        }
        return join(iArr, c, 0, iArr.length);
    }

    public static String join(short[] sArr, char c) {
        if (sArr == null) {
            return null;
        }
        return join(sArr, c, 0, sArr.length);
    }

    public static String join(byte[] bArr, char c) {
        if (bArr == null) {
            return null;
        }
        return join(bArr, c, 0, bArr.length);
    }

    public static String join(char[] cArr, char c) {
        if (cArr == null) {
            return null;
        }
        return join(cArr, c, 0, cArr.length);
    }

    public static String join(float[] fArr, char c) {
        if (fArr == null) {
            return null;
        }
        return join(fArr, c, 0, fArr.length);
    }

    public static String join(double[] dArr, char c) {
        if (dArr == null) {
            return null;
        }
        return join(dArr, c, 0, dArr.length);
    }

    public static String join(Object[] objArr, char c, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
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

    public static String join(long[] jArr, char c, int i, int i2) {
        if (jArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            sb.append(jArr[i4]);
        }
        return sb.toString();
    }

    public static String join(int[] iArr, char c, int i, int i2) {
        if (iArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            sb.append(iArr[i4]);
        }
        return sb.toString();
    }

    public static String join(byte[] bArr, char c, int i, int i2) {
        if (bArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            sb.append((int) bArr[i4]);
        }
        return sb.toString();
    }

    public static String join(short[] sArr, char c, int i, int i2) {
        if (sArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            sb.append((int) sArr[i4]);
        }
        return sb.toString();
    }

    public static String join(char[] cArr, char c, int i, int i2) {
        if (cArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            sb.append(cArr[i4]);
        }
        return sb.toString();
    }

    public static String join(double[] dArr, char c, int i, int i2) {
        if (dArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            sb.append(dArr[i4]);
        }
        return sb.toString();
    }

    public static String join(float[] fArr, char c, int i, int i2) {
        if (fArr == null) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                sb.append(c);
            }
            sb.append(fArr[i4]);
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
        StringBuilder sb = new StringBuilder(i3 * 16);
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
            return ObjectUtils.toString(next);
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
            return ObjectUtils.toString(next);
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

    public static String join(Iterable<?> iterable, char c) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), c);
    }

    public static String join(Iterable<?> iterable, String str) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), str);
    }

    public static String joinWith(String str, Object... objArr) {
        if (objArr != null) {
            String defaultString = defaultString(str, "");
            StringBuilder sb = new StringBuilder();
            Iterator it = Arrays.asList(objArr).iterator();
            while (it.hasNext()) {
                sb.append(ObjectUtils.toString(it.next()));
                if (it.hasNext()) {
                    sb.append(defaultString);
                }
            }
            return sb.toString();
        }
        throw new IllegalArgumentException("Object varargs must not be null");
    }

    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                i++;
                cArr[i] = str.charAt(i2);
            }
        }
        return i == length ? str : new String(cArr, 0, i);
    }

    public static String removeStart(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !str.startsWith(str2)) ? str : str.substring(str2.length());
    }

    public static String removeStartIgnoreCase(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !startsWithIgnoreCase(str, str2)) ? str : str.substring(str2.length());
    }

    public static String removeEnd(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !str.endsWith(str2)) ? str : str.substring(0, str.length() - str2.length());
    }

    public static String removeEndIgnoreCase(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2) || !endsWithIgnoreCase(str, str2)) ? str : str.substring(0, str.length() - str2.length());
    }

    public static String remove(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2)) ? str : replace(str, str2, "", -1);
    }

    public static String removeIgnoreCase(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2)) ? str : replaceIgnoreCase(str, str2, "", -1);
    }

    public static String remove(String str, char c) {
        if (isEmpty(str) || str.indexOf(c) == -1) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int i = 0;
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] != c) {
                i++;
                charArray[i] = charArray[i2];
            }
        }
        return new String(charArray, 0, i);
    }

    public static String removeAll(String str, String str2) {
        return replaceAll(str, str2, "");
    }

    public static String removeFirst(String str, String str2) {
        return replaceFirst(str, str2, "");
    }

    public static String replaceOnce(String str, String str2, String str3) {
        return replace(str, str2, str3, 1);
    }

    public static String replaceOnceIgnoreCase(String str, String str2, String str3) {
        return replaceIgnoreCase(str, str2, str3, 1);
    }

    public static String replacePattern(String str, String str2, String str3) {
        return (str == null || str2 == null || str3 == null) ? str : Pattern.compile(str2, 32).matcher(str).replaceAll(str3);
    }

    public static String removePattern(String str, String str2) {
        return replacePattern(str, str2, "");
    }

    public static String replaceAll(String str, String str2, String str3) {
        return (str == null || str2 == null || str3 == null) ? str : str.replaceAll(str2, str3);
    }

    public static String replaceFirst(String str, String str2, String str3) {
        return (str == null || str2 == null || str3 == null) ? str : str.replaceFirst(str2, str3);
    }

    public static String replace(String str, String str2, String str3) {
        return replace(str, str2, str3, -1);
    }

    public static String replaceIgnoreCase(String str, String str2, String str3) {
        return replaceIgnoreCase(str, str2, str3, -1);
    }

    public static String replace(String str, String str2, String str3, int i) {
        return a(str, str2, str3, i, false);
    }

    private static String a(String str, String str2, String str3, int i, boolean z) {
        String str4;
        if (isEmpty(str) || isEmpty(str2) || str3 == null || i == 0) {
            return str;
        }
        if (z) {
            str4 = str.toLowerCase();
            str2 = str2.toLowerCase();
        } else {
            str4 = str;
        }
        int i2 = 0;
        int indexOf = str4.indexOf(str2, 0);
        if (indexOf == -1) {
            return str;
        }
        int length = str2.length();
        int length2 = str3.length() - length;
        if (length2 < 0) {
            length2 = 0;
        }
        int i3 = 64;
        if (i < 0) {
            i3 = 16;
        } else if (i <= 64) {
            i3 = i;
        }
        StringBuilder sb = new StringBuilder(str.length() + (length2 * i3));
        while (indexOf != -1) {
            sb.append(str.substring(i2, indexOf));
            sb.append(str3);
            i2 = indexOf + length;
            i--;
            if (i == 0) {
                break;
            }
            indexOf = str4.indexOf(str2, i2);
        }
        sb.append(str.substring(i2));
        return sb.toString();
    }

    public static String replaceIgnoreCase(String str, String str2, String str3, int i) {
        return a(str, str2, str3, i, true);
    }

    public static String replaceEach(String str, String[] strArr, String[] strArr2) {
        return a(str, strArr, strArr2, false, 0);
    }

    public static String replaceEachRepeatedly(String str, String[] strArr, String[] strArr2) {
        return a(str, strArr, strArr2, true, strArr == null ? 0 : strArr.length);
    }

    private static String a(String str, String[] strArr, String[] strArr2, boolean z, int i) {
        int length;
        if (str == null || str.isEmpty() || strArr == null || strArr.length == 0 || strArr2 == null || strArr2.length == 0) {
            return str;
        }
        if (i >= 0) {
            int length2 = strArr.length;
            int length3 = strArr2.length;
            if (length2 == length3) {
                boolean[] zArr = new boolean[length2];
                int i2 = -1;
                int i3 = -1;
                for (int i4 = 0; i4 < length2; i4++) {
                    if (!zArr[i4] && strArr[i4] != null && !strArr[i4].isEmpty() && strArr2[i4] != null) {
                        int indexOf = str.indexOf(strArr[i4]);
                        if (indexOf == -1) {
                            zArr[i4] = true;
                        } else if (i2 == -1 || indexOf < i2) {
                            i3 = i4;
                            i2 = indexOf;
                        }
                    }
                }
                if (i2 == -1) {
                    return str;
                }
                int i5 = 0;
                for (int i6 = 0; i6 < strArr.length; i6++) {
                    if (!(strArr[i6] == null || strArr2[i6] == null || (length = strArr2[i6].length() - strArr[i6].length()) <= 0)) {
                        i5 += length * 3;
                    }
                }
                StringBuilder sb = new StringBuilder(str.length() + Math.min(i5, str.length() / 5));
                int i7 = 0;
                while (i2 != -1) {
                    while (i7 < i2) {
                        sb.append(str.charAt(i7));
                        i7++;
                    }
                    sb.append(strArr2[i3]);
                    i7 = strArr[i3].length() + i2;
                    int i8 = -1;
                    int i9 = -1;
                    for (int i10 = 0; i10 < length2; i10++) {
                        if (!zArr[i10] && strArr[i10] != null && !strArr[i10].isEmpty() && strArr2[i10] != null) {
                            int indexOf2 = str.indexOf(strArr[i10], i7);
                            if (indexOf2 == -1) {
                                zArr[i10] = true;
                            } else if (i8 == -1 || indexOf2 < i8) {
                                i9 = i10;
                                i8 = indexOf2;
                            }
                        }
                    }
                    i2 = i8;
                    i3 = i9;
                }
                int length4 = str.length();
                while (i7 < length4) {
                    sb.append(str.charAt(i7));
                    i7++;
                }
                String sb2 = sb.toString();
                return !z ? sb2 : a(sb2, strArr, strArr2, z, i - 1);
            }
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + length2 + " vs " + length3);
        }
        throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
    }

    public static String replaceChars(String str, char c, char c2) {
        if (str == null) {
            return null;
        }
        return str.replace(c, c2);
    }

    public static String replaceChars(String str, String str2, String str3) {
        if (isEmpty(str) || isEmpty(str2)) {
            return str;
        }
        if (str3 == null) {
            str3 = "";
        }
        int length = str3.length();
        int length2 = str.length();
        StringBuilder sb = new StringBuilder(length2);
        boolean z = false;
        for (int i = 0; i < length2; i++) {
            char charAt = str.charAt(i);
            int indexOf = str2.indexOf(charAt);
            if (indexOf >= 0) {
                if (indexOf < length) {
                    sb.append(str3.charAt(indexOf));
                }
                z = true;
            } else {
                sb.append(charAt);
            }
        }
        return z ? sb.toString() : str;
    }

    public static String overlay(String str, String str2, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (str2 == null) {
            str2 = "";
        }
        int length = str.length();
        if (i < 0) {
            i = 0;
        }
        if (i > length) {
            i = length;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > length) {
            i2 = length;
        }
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        StringBuilder sb = new StringBuilder(((length + i) - i2) + str2.length() + 1);
        sb.append(str.substring(0, i));
        sb.append(str2);
        sb.append(str.substring(i2));
        return sb.toString();
    }

    public static String chomp(String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() == 1) {
            char charAt = str.charAt(0);
            return (charAt == '\r' || charAt == '\n') ? "" : str;
        }
        int length = str.length() - 1;
        char charAt2 = str.charAt(length);
        if (charAt2 == '\n') {
            if (str.charAt(length - 1) == '\r') {
                length--;
            }
        } else if (charAt2 != '\r') {
            length++;
        }
        return str.substring(0, length);
    }

    @Deprecated
    public static String chomp(String str, String str2) {
        return removeEnd(str, str2);
    }

    public static String chop(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length < 2) {
            return "";
        }
        int i = length - 1;
        String substring = str.substring(0, i);
        if (str.charAt(i) == '\n') {
            int i2 = i - 1;
            if (substring.charAt(i2) == '\r') {
                return substring.substring(0, i2);
            }
        }
        return substring;
    }

    public static String repeat(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i <= 0) {
            return "";
        }
        int length = str.length();
        if (i == 1 || length == 0) {
            return str;
        }
        if (length == 1 && i <= 8192) {
            return repeat(str.charAt(0), i);
        }
        int i2 = length * i;
        switch (length) {
            case 1:
                return repeat(str.charAt(0), i);
            case 2:
                char charAt = str.charAt(0);
                char charAt2 = str.charAt(1);
                char[] cArr = new char[i2];
                for (int i3 = (i * 2) - 2; i3 >= 0; i3 = (i3 - 1) - 1) {
                    cArr[i3] = charAt;
                    cArr[i3 + 1] = charAt2;
                }
                return new String(cArr);
            default:
                StringBuilder sb = new StringBuilder(i2);
                for (int i4 = 0; i4 < i; i4++) {
                    sb.append(str);
                }
                return sb.toString();
        }
    }

    public static String repeat(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return repeat(str, i);
        }
        return removeEnd(repeat(str + str2, i), str2);
    }

    public static String repeat(char c, int i) {
        if (i <= 0) {
            return "";
        }
        char[] cArr = new char[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            cArr[i2] = c;
        }
        return new String(cArr);
    }

    public static String rightPad(String str, int i) {
        return rightPad(str, i, ' ');
    }

    public static String rightPad(String str, int i, char c) {
        if (str == null) {
            return null;
        }
        int length = i - str.length();
        if (length <= 0) {
            return str;
        }
        if (length > 8192) {
            return rightPad(str, i, String.valueOf(c));
        }
        return str.concat(repeat(c, length));
    }

    public static String rightPad(String str, int i, String str2) {
        if (str == null) {
            return null;
        }
        if (isEmpty(str2)) {
            str2 = SPACE;
        }
        int length = str2.length();
        int length2 = i - str.length();
        if (length2 <= 0) {
            return str;
        }
        if (length == 1 && length2 <= 8192) {
            return rightPad(str, i, str2.charAt(0));
        }
        if (length2 == length) {
            return str.concat(str2);
        }
        if (length2 < length) {
            return str.concat(str2.substring(0, length2));
        }
        char[] cArr = new char[length2];
        char[] charArray = str2.toCharArray();
        for (int i2 = 0; i2 < length2; i2++) {
            cArr[i2] = charArray[i2 % length];
        }
        return str.concat(new String(cArr));
    }

    public static String leftPad(String str, int i) {
        return leftPad(str, i, ' ');
    }

    public static String leftPad(String str, int i, char c) {
        if (str == null) {
            return null;
        }
        int length = i - str.length();
        if (length <= 0) {
            return str;
        }
        if (length > 8192) {
            return leftPad(str, i, String.valueOf(c));
        }
        return repeat(c, length).concat(str);
    }

    public static String leftPad(String str, int i, String str2) {
        if (str == null) {
            return null;
        }
        if (isEmpty(str2)) {
            str2 = SPACE;
        }
        int length = str2.length();
        int length2 = i - str.length();
        if (length2 <= 0) {
            return str;
        }
        if (length == 1 && length2 <= 8192) {
            return leftPad(str, i, str2.charAt(0));
        }
        if (length2 == length) {
            return str2.concat(str);
        }
        if (length2 < length) {
            return str2.substring(0, length2).concat(str);
        }
        char[] cArr = new char[length2];
        char[] charArray = str2.toCharArray();
        for (int i2 = 0; i2 < length2; i2++) {
            cArr[i2] = charArray[i2 % length];
        }
        return new String(cArr).concat(str);
    }

    public static int length(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.length();
    }

    public static String center(String str, int i) {
        return center(str, i, ' ');
    }

    public static String center(String str, int i, char c) {
        int length;
        int length2;
        return (str == null || i <= 0 || (length2 = i - (length = str.length())) <= 0) ? str : rightPad(leftPad(str, length + (length2 / 2), c), i, c);
    }

    public static String center(String str, int i, String str2) {
        if (str == null || i <= 0) {
            return str;
        }
        if (isEmpty(str2)) {
            str2 = SPACE;
        }
        int length = str.length();
        int i2 = i - length;
        return i2 <= 0 ? str : rightPad(leftPad(str, length + (i2 / 2), str2), i, str2);
    }

    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String upperCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(locale);
    }

    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    public static String lowerCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(locale);
    }

    public static String capitalize(String str) {
        int length;
        char charAt;
        char titleCase;
        if (str == null || (length = str.length()) == 0 || charAt == (titleCase = Character.toTitleCase((charAt = str.charAt(0))))) {
            return str;
        }
        char[] cArr = new char[length];
        cArr[0] = titleCase;
        str.getChars(1, length, cArr, 1);
        return String.valueOf(cArr);
    }

    public static String uncapitalize(String str) {
        int length;
        char charAt;
        char lowerCase;
        if (str == null || (length = str.length()) == 0 || charAt == (lowerCase = Character.toLowerCase((charAt = str.charAt(0))))) {
            return str;
        }
        char[] cArr = new char[length];
        cArr[0] = lowerCase;
        str.getChars(1, length, cArr, 1);
        return String.valueOf(cArr);
    }

    public static String swapCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (Character.isUpperCase(c)) {
                charArray[i] = Character.toLowerCase(c);
            } else if (Character.isTitleCase(c)) {
                charArray[i] = Character.toLowerCase(c);
            } else if (Character.isLowerCase(c)) {
                charArray[i] = Character.toUpperCase(c);
            }
        }
        return new String(charArray);
    }

    public static int countMatches(CharSequence charSequence, CharSequence charSequence2) {
        int i = 0;
        if (isEmpty(charSequence) || isEmpty(charSequence2)) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            int a = CharSequenceUtils.a(charSequence, charSequence2, i);
            if (a == -1) {
                return i2;
            }
            i2++;
            i = a + charSequence2.length();
        }
    }

    public static int countMatches(CharSequence charSequence, char c) {
        if (isEmpty(charSequence)) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (c == charSequence.charAt(i2)) {
                i++;
            }
        }
        return i;
    }

    public static boolean isAlpha(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetter(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaSpace(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isLetter(charSequence.charAt(i)) || charSequence.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumeric(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumericSpace(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isLetterOrDigit(charSequence.charAt(i)) || charSequence.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAsciiPrintable(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!CharUtils.isAsciiPrintable(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumericSpace(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isDigit(charSequence.charAt(i)) || charSequence.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllLowerCase(CharSequence charSequence) {
        if (charSequence == null || isEmpty(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLowerCase(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllUpperCase(CharSequence charSequence) {
        if (charSequence == null || isEmpty(charSequence)) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isUpperCase(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static <T extends CharSequence> T defaultIfBlank(T t, T t2) {
        return isBlank(t) ? t2 : t;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T t, T t2) {
        return isEmpty(t) ? t2 : t;
    }

    public static String rotate(String str, int i) {
        int i2;
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (i == 0 || length == 0 || (i2 = i % length) == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(length);
        int i3 = -i2;
        sb.append(substring(str, i3));
        sb.append(substring(str, 0, i3));
        return sb.toString();
    }

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    public static String reverseDelimited(String str, char c) {
        if (str == null) {
            return null;
        }
        String[] split = split(str, c);
        ArrayUtils.reverse(split);
        return join(split, c);
    }

    public static String abbreviate(String str, int i) {
        return abbreviate(str, 0, i);
    }

    public static String abbreviate(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (i2 < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        } else if (str.length() <= i2) {
            return str;
        } else {
            if (i > str.length()) {
                i = str.length();
            }
            int i3 = i2 - 3;
            if (str.length() - i < i3) {
                i = str.length() - i3;
            }
            if (i <= 4) {
                return str.substring(0, i3) + "...";
            } else if (i2 < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else if ((i2 + i) - 3 < str.length()) {
                return "..." + abbreviate(str.substring(i), i3);
            } else {
                return "..." + str.substring(str.length() - i3);
            }
        }
    }

    public static String abbreviateMiddle(String str, String str2, int i) {
        if (isEmpty(str) || isEmpty(str2) || i >= str.length() || i < str2.length() + 2) {
            return str;
        }
        int length = i - str2.length();
        int i2 = length / 2;
        int i3 = (length % 2) + i2;
        int length2 = str.length() - i2;
        StringBuilder sb = new StringBuilder(i);
        sb.append(str.substring(0, i3));
        sb.append(str2);
        sb.append(str.substring(length2));
        return sb.toString();
    }

    public static String difference(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        int indexOfDifference = indexOfDifference(str, str2);
        return indexOfDifference == -1 ? "" : str2.substring(indexOfDifference);
    }

    public static int indexOfDifference(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return -1;
        }
        int i = 0;
        if (charSequence == null || charSequence2 == null) {
            return 0;
        }
        while (i < charSequence.length() && i < charSequence2.length() && charSequence.charAt(i) == charSequence2.charAt(i)) {
            i++;
        }
        if (i < charSequence2.length() || i < charSequence.length()) {
            return i;
        }
        return -1;
    }

    public static int indexOfDifference(CharSequence... charSequenceArr) {
        if (charSequenceArr == null || charSequenceArr.length <= 1) {
            return -1;
        }
        int length = charSequenceArr.length;
        int i = Integer.MAX_VALUE;
        boolean z = true;
        int i2 = 0;
        boolean z2 = false;
        for (int i3 = 0; i3 < length; i3++) {
            if (charSequenceArr[i3] == null) {
                z2 = true;
                i = 0;
            } else {
                i = Math.min(charSequenceArr[i3].length(), i);
                i2 = Math.max(charSequenceArr[i3].length(), i2);
                z = false;
            }
        }
        if (z || (i2 == 0 && !z2)) {
            return -1;
        }
        if (i == 0) {
            return 0;
        }
        int i4 = -1;
        for (int i5 = 0; i5 < i; i5++) {
            char charAt = charSequenceArr[0].charAt(i5);
            int i6 = 1;
            while (true) {
                if (i6 >= length) {
                    break;
                } else if (charSequenceArr[i6].charAt(i5) != charAt) {
                    i4 = i5;
                    break;
                } else {
                    i6++;
                }
            }
            if (i4 != -1) {
                break;
            }
        }
        return (i4 != -1 || i == i2) ? i4 : i;
    }

    public static String getCommonPrefix(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        int indexOfDifference = indexOfDifference(strArr);
        return indexOfDifference == -1 ? strArr[0] == null ? "" : strArr[0] : indexOfDifference == 0 ? "" : strArr[0].substring(0, indexOfDifference);
    }

    public static int getLevenshteinDistance(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        if (length == 0) {
            return length2;
        }
        if (length2 == 0) {
            return length;
        }
        if (length > length2) {
            length2 = charSequence.length();
            length = length2;
        } else {
            charSequence2 = charSequence;
            charSequence = charSequence2;
        }
        int i = length + 1;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        for (int i2 = 0; i2 <= length; i2++) {
            iArr[i2] = i2;
        }
        int[] iArr3 = iArr2;
        int i3 = 1;
        while (i3 <= length2) {
            char charAt = charSequence.charAt(i3 - 1);
            iArr3[0] = i3;
            for (int i4 = 1; i4 <= length; i4++) {
                int i5 = i4 - 1;
                iArr3[i4] = Math.min(Math.min(iArr3[i5] + 1, iArr[i4] + 1), iArr[i5] + (charSequence2.charAt(i5) == charAt ? 0 : 1));
            }
            i3++;
            iArr3 = iArr;
            iArr = iArr3;
        }
        return iArr[length];
    }

    public static int getLevenshteinDistance(CharSequence charSequence, CharSequence charSequence2, int i) {
        CharSequence charSequence3;
        int i2;
        CharSequence charSequence4;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        } else if (i >= 0) {
            int length = charSequence.length();
            int length2 = charSequence2.length();
            if (length == 0) {
                if (length2 <= i) {
                    return length2;
                }
                return -1;
            } else if (length2 == 0) {
                if (length <= i) {
                    return length;
                }
                return -1;
            } else if (Math.abs(length - length2) > i) {
                return -1;
            } else {
                if (length > length2) {
                    charSequence3 = charSequence2;
                    i2 = charSequence.length();
                    length = length2;
                    charSequence4 = charSequence;
                } else {
                    charSequence3 = charSequence;
                    i2 = length2;
                    charSequence4 = charSequence2;
                }
                int i3 = length + 1;
                int[] iArr = new int[i3];
                int[] iArr2 = new int[i3];
                int min = Math.min(length, i) + 1;
                char c = 0;
                for (int i4 = 0; i4 < min; i4++) {
                    iArr[i4] = i4;
                }
                int i5 = Integer.MAX_VALUE;
                Arrays.fill(iArr, min, iArr.length, Integer.MAX_VALUE);
                Arrays.fill(iArr2, Integer.MAX_VALUE);
                int[] iArr3 = iArr2;
                int i6 = 1;
                while (i6 <= i2) {
                    char charAt = charSequence4.charAt(i6 - 1);
                    iArr3[c] = i6;
                    int max = Math.max(1, i6 - i);
                    int min2 = i6 > i5 - i ? length : Math.min(length, i6 + i);
                    if (max > min2) {
                        return -1;
                    }
                    if (max > 1) {
                        iArr3[max - 1] = i5;
                    }
                    while (max <= min2) {
                        int i7 = max - 1;
                        if (charSequence3.charAt(i7) == charAt) {
                            iArr3[max] = iArr[i7];
                        } else {
                            iArr3[max] = Math.min(Math.min(iArr3[i7], iArr[max]), iArr[i7]) + 1;
                        }
                        max++;
                    }
                    i6++;
                    c = 0;
                    i5 = Integer.MAX_VALUE;
                    iArr3 = iArr;
                    iArr = iArr3;
                }
                if (iArr[length] <= i) {
                    return iArr[length];
                }
                return -1;
            }
        } else {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
    }

    public static double getJaroWinklerDistance(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int[] a = a(charSequence, charSequence2);
        double d = a[0];
        if (d == 0.0d) {
            return 0.0d;
        }
        double length = (((d / charSequence.length()) + (d / charSequence2.length())) + ((d - a[1]) / d)) / 3.0d;
        if (length >= 0.7d) {
            length += Math.min(0.1d, 1.0d / a[3]) * a[2] * (1.0d - length);
        }
        return Math.round(length * 100.0d) / 100.0d;
    }

    private static int[] a(CharSequence charSequence, CharSequence charSequence2) {
        CharSequence charSequence3;
        CharSequence charSequence4;
        if (charSequence.length() > charSequence2.length()) {
            charSequence4 = charSequence;
            charSequence3 = charSequence2;
        } else {
            charSequence3 = charSequence;
            charSequence4 = charSequence2;
        }
        int max = Math.max((charSequence4.length() / 2) - 1, 0);
        int[] iArr = new int[charSequence3.length()];
        Arrays.fill(iArr, -1);
        boolean[] zArr = new boolean[charSequence4.length()];
        int i = 0;
        for (int i2 = 0; i2 < charSequence3.length(); i2++) {
            char charAt = charSequence3.charAt(i2);
            int max2 = Math.max(i2 - max, 0);
            int min = Math.min(i2 + max + 1, charSequence4.length());
            while (true) {
                if (max2 >= min) {
                    break;
                }
                if (!zArr[max2] && charAt == charSequence4.charAt(max2)) {
                    iArr[i2] = max2;
                    zArr[max2] = true;
                    i++;
                    break;
                }
                max2++;
            }
        }
        char[] cArr = new char[i];
        char[] cArr2 = new char[i];
        int i3 = 0;
        for (int i4 = 0; i4 < charSequence3.length(); i4++) {
            if (iArr[i4] != -1) {
                cArr[i3] = charSequence3.charAt(i4);
                i3++;
            }
        }
        int i5 = 0;
        for (int i6 = 0; i6 < charSequence4.length(); i6++) {
            if (zArr[i6]) {
                cArr2[i5] = charSequence4.charAt(i6);
                i5++;
            }
        }
        int i7 = 0;
        for (int i8 = 0; i8 < cArr.length; i8++) {
            if (cArr[i8] != cArr2[i8]) {
                i7++;
            }
        }
        int i9 = 0;
        for (int i10 = 0; i10 < charSequence3.length() && charSequence.charAt(i10) == charSequence2.charAt(i10); i10++) {
            i9++;
        }
        return new int[]{i, i7 / 2, i9, charSequence4.length()};
    }

    public static int getFuzzyDistance(CharSequence charSequence, CharSequence charSequence2, Locale locale) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        } else if (locale != null) {
            String lowerCase = charSequence.toString().toLowerCase(locale);
            String lowerCase2 = charSequence2.toString().toLowerCase(locale);
            int i = Integer.MIN_VALUE;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i2 < lowerCase2.length()) {
                char charAt = lowerCase2.charAt(i2);
                int i5 = i3;
                boolean z = false;
                while (i4 < lowerCase.length() && !z) {
                    if (charAt == lowerCase.charAt(i4)) {
                        i5++;
                        if (i + 1 == i4) {
                            i5 += 2;
                        }
                        z = true;
                        i = i4;
                    }
                    i4++;
                }
                i2++;
                i3 = i5;
                i = i;
            }
            return i3;
        } else {
            throw new IllegalArgumentException("Locale must not be null");
        }
    }

    public static boolean startsWith(CharSequence charSequence, CharSequence charSequence2) {
        return a(charSequence, charSequence2, false);
    }

    public static boolean startsWithIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return a(charSequence, charSequence2, true);
    }

    private static boolean a(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == null && charSequence2 == null;
        }
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        return CharSequenceUtils.a(charSequence, z, 0, charSequence2, 0, charSequence2.length());
    }

    public static boolean startsWithAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArr)) {
            return false;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (startsWith(charSequence, charSequence2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        return b(charSequence, charSequence2, false);
    }

    public static boolean endsWithIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return b(charSequence, charSequence2, true);
    }

    private static boolean b(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == null && charSequence2 == null;
        }
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        return CharSequenceUtils.a(charSequence, z, charSequence.length() - charSequence2.length(), charSequence2, 0, charSequence2.length());
    }

    public static String normalizeSpace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i = 1;
        boolean z = true;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char charAt = str.charAt(i4);
            if (!Character.isWhitespace(charAt)) {
                i2++;
                if (charAt == 160) {
                    charAt = ' ';
                }
                cArr[i2] = charAt;
                i3 = 0;
                z = false;
            } else {
                if (i3 == 0 && !z) {
                    i2++;
                    cArr[i2] = SPACE.charAt(0);
                }
                i3++;
            }
        }
        if (z) {
            return "";
        }
        if (i3 <= 0) {
            i = 0;
        }
        return new String(cArr, 0, i2 - i).trim();
    }

    public static boolean endsWithAny(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArr)) {
            return false;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (endsWith(charSequence, charSequence2)) {
                return true;
            }
        }
        return false;
    }

    private static String a(String str, CharSequence charSequence, boolean z, CharSequence... charSequenceArr) {
        if (str == null || isEmpty(charSequence) || b(str, charSequence, z)) {
            return str;
        }
        if (charSequenceArr != null && charSequenceArr.length > 0) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (b(str, charSequence2, z)) {
                    return str;
                }
            }
        }
        return str + charSequence.toString();
    }

    public static String appendIfMissing(String str, CharSequence charSequence, CharSequence... charSequenceArr) {
        return a(str, charSequence, false, charSequenceArr);
    }

    public static String appendIfMissingIgnoreCase(String str, CharSequence charSequence, CharSequence... charSequenceArr) {
        return a(str, charSequence, true, charSequenceArr);
    }

    private static String b(String str, CharSequence charSequence, boolean z, CharSequence... charSequenceArr) {
        if (str == null || isEmpty(charSequence) || a(str, charSequence, z)) {
            return str;
        }
        if (charSequenceArr != null && charSequenceArr.length > 0) {
            for (CharSequence charSequence2 : charSequenceArr) {
                if (a(str, charSequence2, z)) {
                    return str;
                }
            }
        }
        return charSequence.toString() + str;
    }

    public static String prependIfMissing(String str, CharSequence charSequence, CharSequence... charSequenceArr) {
        return b(str, charSequence, false, charSequenceArr);
    }

    public static String prependIfMissingIgnoreCase(String str, CharSequence charSequence, CharSequence... charSequenceArr) {
        return b(str, charSequence, true, charSequenceArr);
    }

    @Deprecated
    public static String toString(byte[] bArr, String str) throws UnsupportedEncodingException {
        return str != null ? new String(bArr, str) : new String(bArr, Charset.defaultCharset());
    }

    public static String toEncodedString(byte[] bArr, Charset charset) {
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        return new String(bArr, charset);
    }

    public static String wrap(String str, char c) {
        if (isEmpty(str) || c == 0) {
            return str;
        }
        return c + str + c;
    }

    public static String wrap(String str, String str2) {
        return (isEmpty(str) || isEmpty(str2)) ? str : str2.concat(str).concat(str2);
    }

    public static String wrapIfMissing(String str, char c) {
        if (isEmpty(str) || c == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() + 2);
        if (str.charAt(0) != c) {
            sb.append(c);
        }
        sb.append(str);
        if (str.charAt(str.length() - 1) != c) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String wrapIfMissing(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + str2.length());
        if (!str.startsWith(str2)) {
            sb.append(str2);
        }
        sb.append(str);
        if (!str.endsWith(str2)) {
            sb.append(str2);
        }
        return sb.toString();
    }
}
