package org.apache.commons.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;

    public static String defaultString(String str) {
        return str == null ? "" : str;
    }

    public static String defaultString(String str, String str2) {
        return str == null ? str2 : str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String clean(String str) {
        return str == null ? "" : str.trim();
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

    public static String strip(String str) {
        return strip(str, null);
    }

    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        String strip = strip(str, null);
        if (strip.length() == 0) {
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
        } else if (str2.length() == 0) {
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
        } else if (str2.length() == 0) {
            return str;
        } else {
            while (length != 0 && str2.indexOf(str.charAt(length - 1)) != -1) {
                length--;
            }
        }
        return str.substring(0, length);
    }

    public static String[] stripAll(String[] strArr) {
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

    public static boolean equals(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equalsIgnoreCase(str2);
    }

    public static int indexOf(String str, char c) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(c);
    }

    public static int indexOf(String str, char c, int i) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(c, i);
    }

    public static int indexOf(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.indexOf(str2);
    }

    public static int ordinalIndexOf(String str, String str2, int i) {
        return a(str, str2, i, false);
    }

    private static int a(String str, String str2, int i, boolean z) {
        int i2 = -1;
        if (str == null || str2 == null || i <= 0) {
            return -1;
        }
        int i3 = 0;
        if (str2.length() != 0) {
            if (z) {
                i2 = str.length();
            }
            do {
                if (z) {
                    i2 = str.lastIndexOf(str2, i2 - 1);
                } else {
                    i2 = str.indexOf(str2, i2 + 1);
                }
                if (i2 < 0) {
                    return i2;
                }
                i3++;
            } while (i3 < i);
            return i2;
        } else if (z) {
            return str.length();
        } else {
            return 0;
        }
    }

    public static int indexOf(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (str2.length() != 0 || i < str.length()) {
            return str.indexOf(str2, i);
        }
        return str.length();
    }

    public static int indexOfIgnoreCase(String str, String str2) {
        return indexOfIgnoreCase(str, str2, 0);
    }

    public static int indexOfIgnoreCase(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        int length = (str.length() - str2.length()) + 1;
        if (i > length) {
            return -1;
        }
        if (str2.length() == 0) {
            return i;
        }
        while (i < length) {
            if (str.regionMatches(true, i, str2, 0, str2.length())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(String str, char c) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(c);
    }

    public static int lastIndexOf(String str, char c, int i) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(c, i);
    }

    public static int lastIndexOf(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.lastIndexOf(str2);
    }

    public static int lastOrdinalIndexOf(String str, String str2, int i) {
        return a(str, str2, i, true);
    }

    public static int lastIndexOf(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        return str.lastIndexOf(str2, i);
    }

    public static int lastIndexOfIgnoreCase(String str, String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        return lastIndexOfIgnoreCase(str, str2, str.length());
    }

    public static int lastIndexOfIgnoreCase(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (i > str.length() - str2.length()) {
            i = str.length() - str2.length();
        }
        if (i < 0) {
            return -1;
        }
        if (str2.length() == 0) {
            return i;
        }
        while (i >= 0) {
            if (str.regionMatches(true, i, str2, 0, str2.length())) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public static boolean contains(String str, char c) {
        return !isEmpty(str) && str.indexOf(c) >= 0;
    }

    public static boolean contains(String str, String str2) {
        return (str == null || str2 == null || str.indexOf(str2) < 0) ? false : true;
    }

    public static boolean containsIgnoreCase(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        int length = str2.length();
        int length2 = str.length() - length;
        for (int i = 0; i <= length2; i++) {
            if (str.regionMatches(true, i, str2, 0, length)) {
                return true;
            }
        }
        return false;
    }

    public static int indexOfAny(String str, char[] cArr) {
        if (isEmpty(str) || ArrayUtils.isEmpty(cArr)) {
            return -1;
        }
        int length = str.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        int i3 = 0;
        while (i3 < length) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt && (i3 >= i || i4 >= i2 || !CharUtils.a(charAt) || cArr[i4 + 1] == str.charAt(i3 + 1))) {
                    return i3;
                }
            }
            i3++;
        }
        return -1;
    }

    public static int indexOfAny(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2)) {
            return -1;
        }
        return indexOfAny(str, str2.toCharArray());
    }

    public static boolean containsAny(String str, char[] cArr) {
        if (isEmpty(str) || ArrayUtils.isEmpty(cArr)) {
            return false;
        }
        int length = str.length();
        int length2 = cArr.length;
        int i = length - 1;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt) {
                    if (!CharUtils.a(charAt) || i4 == i2) {
                        return true;
                    }
                    if (i3 < i && cArr[i4 + 1] == str.charAt(i3 + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean containsAny(String str, String str2) {
        if (str2 == null) {
            return false;
        }
        return containsAny(str, str2.toCharArray());
    }

    public static int indexOfAnyBut(String str, char[] cArr) {
        if (isEmpty(str) || ArrayUtils.isEmpty(cArr)) {
            return -1;
        }
        int length = str.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] != charAt || (i3 < i && i4 < i2 && CharUtils.a(charAt) && cArr[i4 + 1] != str.charAt(i3 + 1))) {
                }
            }
            return i3;
        }
        return -1;
    }

    public static int indexOfAnyBut(String str, String str2) {
        if (isEmpty(str) || isEmpty(str2)) {
            return -1;
        }
        int length = str.length();
        int i = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            boolean z = str2.indexOf(charAt) >= 0;
            int i2 = i + 1;
            if (i2 < length && CharUtils.a(charAt)) {
                char charAt2 = str.charAt(i2);
                if (z && str2.indexOf(charAt2) < 0) {
                    return i;
                }
            } else if (!z) {
                return i;
            }
            i = i2;
        }
        return -1;
    }

    public static boolean containsOnly(String str, char[] cArr) {
        if (cArr == null || str == null) {
            return false;
        }
        if (str.length() == 0) {
            return true;
        }
        return cArr.length != 0 && indexOfAnyBut(str, cArr) == -1;
    }

    public static boolean containsOnly(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        return containsOnly(str, str2.toCharArray());
    }

    public static boolean containsNone(String str, char[] cArr) {
        if (str == null || cArr == null) {
            return true;
        }
        int length = str.length();
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = length2 - 1;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            for (int i4 = 0; i4 < length2; i4++) {
                if (cArr[i4] == charAt) {
                    if (!CharUtils.a(charAt) || i4 == i2) {
                        return false;
                    }
                    if (i3 < i && cArr[i4 + 1] == str.charAt(i3 + 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean containsNone(String str, String str2) {
        if (str == null || str2 == null) {
            return true;
        }
        return containsNone(str, str2.toCharArray());
    }

    public static int indexOfAny(String str, String[] strArr) {
        int indexOf;
        if (str == null || strArr == null) {
            return -1;
        }
        int i = Integer.MAX_VALUE;
        for (String str2 : strArr) {
            if (!(str2 == null || (indexOf = str.indexOf(str2)) == -1 || indexOf >= i)) {
                i = indexOf;
            }
        }
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i;
    }

    public static int lastIndexOfAny(String str, String[] strArr) {
        int lastIndexOf;
        int i = -1;
        if (str == null || strArr == null) {
            return -1;
        }
        for (String str2 : strArr) {
            if (str2 != null && (lastIndexOf = str.lastIndexOf(str2)) > i) {
                i = lastIndexOf;
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
        if (str2.length() == 0) {
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

    public static String getNestedString(String str, String str2) {
        return substringBetween(str, str2, str2);
    }

    public static String getNestedString(String str, String str2, String str3) {
        return substringBetween(str, str2, str3);
    }

    public static String[] split(String str) {
        return split(str, null, -1);
    }

    public static String[] split(String str, char c) {
        return a(str, c, false);
    }

    public static String[] split(String str, String str2) {
        return c(str, str2, -1, false);
    }

    public static String[] split(String str, String str2, int i) {
        return c(str, str2, i, false);
    }

    public static String[] splitByWholeSeparator(String str, String str2) {
        return b(str, str2, -1, false);
    }

    public static String[] splitByWholeSeparator(String str, String str2, int i) {
        return b(str, str2, i, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String str2) {
        return b(str, str2, -1, true);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String str2, int i) {
        return b(str, str2, i, true);
    }

    private static String[] b(String str, String str2, int i, boolean z) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (str2 == null || "".equals(str2)) {
            return c(str, null, i, z);
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
        return c(str, null, -1, true);
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
        return c(str, str2, -1, true);
    }

    public static String[] splitPreserveAllTokens(String str, String str2, int i) {
        return c(str, str2, i, true);
    }

    private static String[] c(String str, String str2, int i, boolean z) {
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
        if (str.length() == 0) {
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

    public static String concatenate(Object[] objArr) {
        return join(objArr, (String) null);
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
        StrBuilder strBuilder = new StrBuilder(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + 1));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                strBuilder.append(c);
            }
            if (objArr[i4] != null) {
                strBuilder.append(objArr[i4]);
            }
        }
        return strBuilder.toString();
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
        StrBuilder strBuilder = new StrBuilder(i3 * ((objArr[i] == null ? 16 : objArr[i].toString().length()) + str.length()));
        for (int i4 = i; i4 < i2; i4++) {
            if (i4 > i) {
                strBuilder.append(str);
            }
            if (objArr[i4] != null) {
                strBuilder.append(objArr[i4]);
            }
        }
        return strBuilder.toString();
    }

    public static String join(Iterator it, char c) {
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
        StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (it.hasNext()) {
            strBuilder.append(c);
            Object next2 = it.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }

    public static String join(Iterator it, String str) {
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
        StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (it.hasNext()) {
            if (str != null) {
                strBuilder.append(str);
            }
            Object next2 = it.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }

    public static String join(Collection collection, char c) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), c);
    }

    public static String join(Collection collection, String str) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), str);
    }

    public static String deleteSpaces(String str) {
        if (str == null) {
            return null;
        }
        return CharSetUtils.delete(str, " \t\r\n\b");
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

    public static String replaceOnce(String str, String str2, String str3) {
        return replace(str, str2, str3, 1);
    }

    public static String replace(String str, String str2, String str3) {
        return replace(str, str2, str3, -1);
    }

    public static String replace(String str, String str2, String str3, int i) {
        if (isEmpty(str) || isEmpty(str2) || str3 == null || i == 0) {
            return str;
        }
        int i2 = 0;
        int indexOf = str.indexOf(str2, 0);
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
        StrBuilder strBuilder = new StrBuilder(str.length() + (length2 * i3));
        while (indexOf != -1) {
            strBuilder.append(str.substring(i2, indexOf)).append(str3);
            i2 = indexOf + length;
            i--;
            if (i == 0) {
                break;
            }
            indexOf = str.indexOf(str2, i2);
        }
        strBuilder.append(str.substring(i2));
        return strBuilder.toString();
    }

    public static String replaceEach(String str, String[] strArr, String[] strArr2) {
        return a(str, strArr, strArr2, false, 0);
    }

    public static String replaceEachRepeatedly(String str, String[] strArr, String[] strArr2) {
        return a(str, strArr, strArr2, true, strArr == null ? 0 : strArr.length);
    }

    private static String a(String str, String[] strArr, String[] strArr2, boolean z, int i) {
        int length;
        if (str == null || str.length() == 0 || strArr == null || strArr.length == 0 || strArr2 == null || strArr2.length == 0) {
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
                    if (!(zArr[i4] || strArr[i4] == null || strArr[i4].length() == 0 || strArr2[i4] == null)) {
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
                StrBuilder strBuilder = new StrBuilder(str.length() + Math.min(i5, str.length() / 5));
                int i7 = 0;
                while (i2 != -1) {
                    while (i7 < i2) {
                        strBuilder.append(str.charAt(i7));
                        i7++;
                    }
                    strBuilder.append(strArr2[i3]);
                    i7 = strArr[i3].length() + i2;
                    int i8 = -1;
                    int i9 = -1;
                    for (int i10 = 0; i10 < length2; i10++) {
                        if (!(zArr[i10] || strArr[i10] == null || strArr[i10].length() == 0 || strArr2[i10] == null)) {
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
                    strBuilder.append(str.charAt(i7));
                    i7++;
                }
                String strBuilder2 = strBuilder.toString();
                return !z ? strBuilder2 : a(strBuilder2, strArr, strArr2, z, i - 1);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Search and Replace array lengths don't match: ");
            stringBuffer.append(length2);
            stringBuffer.append(" vs ");
            stringBuffer.append(length3);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("TimeToLive of ");
        stringBuffer2.append(i);
        stringBuffer2.append(" is less than 0: ");
        stringBuffer2.append(str);
        throw new IllegalStateException(stringBuffer2.toString());
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
        StrBuilder strBuilder = new StrBuilder(length2);
        boolean z = false;
        for (int i = 0; i < length2; i++) {
            char charAt = str.charAt(i);
            int indexOf = str2.indexOf(charAt);
            if (indexOf >= 0) {
                if (indexOf < length) {
                    strBuilder.append(str3.charAt(indexOf));
                }
                z = true;
            } else {
                strBuilder.append(charAt);
            }
        }
        return z ? strBuilder.toString() : str;
    }

    public static String overlayString(String str, String str2, int i, int i2) {
        return new StrBuilder((((str2.length() + i) + str.length()) - i2) + 1).append(str.substring(0, i)).append(str2).append(str.substring(i2)).toString();
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
        return new StrBuilder(((length + i) - i2) + str2.length() + 1).append(str.substring(0, i)).append(str2).append(str.substring(i2)).toString();
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

    public static String chomp(String str, String str2) {
        return (isEmpty(str) || str2 == null || !str.endsWith(str2)) ? str : str.substring(0, str.length() - str2.length());
    }

    public static String chompLast(String str) {
        return chompLast(str, "\n");
    }

    public static String chompLast(String str, String str2) {
        return (str.length() != 0 && str2.equals(str.substring(str.length() - str2.length()))) ? str.substring(0, str.length() - str2.length()) : str;
    }

    public static String getChomp(String str, String str2) {
        int lastIndexOf = str.lastIndexOf(str2);
        return lastIndexOf == str.length() - str2.length() ? str2 : lastIndexOf != -1 ? str.substring(lastIndexOf) : "";
    }

    public static String prechomp(String str, String str2) {
        int indexOf = str.indexOf(str2);
        return indexOf == -1 ? str : str.substring(indexOf + str2.length());
    }

    public static String getPrechomp(String str, String str2) {
        int indexOf = str.indexOf(str2);
        return indexOf == -1 ? "" : str.substring(0, indexOf + str2.length());
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

    public static String chopNewline(String str) {
        int length = str.length() - 1;
        if (length <= 0) {
            return "";
        }
        if (str.charAt(length) != '\n') {
            length++;
        } else if (str.charAt(length - 1) == '\r') {
            length--;
        }
        return str.substring(0, length);
    }

    public static String escape(String str) {
        return StringEscapeUtils.escapeJava(str);
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
            return a(i, str.charAt(0));
        }
        int i2 = length * i;
        switch (length) {
            case 1:
                char charAt = str.charAt(0);
                char[] cArr = new char[i2];
                for (int i3 = i - 1; i3 >= 0; i3--) {
                    cArr[i3] = charAt;
                }
                return new String(cArr);
            case 2:
                char charAt2 = str.charAt(0);
                char charAt3 = str.charAt(1);
                char[] cArr2 = new char[i2];
                for (int i4 = (i * 2) - 2; i4 >= 0; i4 = (i4 - 1) - 1) {
                    cArr2[i4] = charAt2;
                    cArr2[i4 + 1] = charAt3;
                }
                return new String(cArr2);
            default:
                StrBuilder strBuilder = new StrBuilder(i2);
                for (int i5 = 0; i5 < i; i5++) {
                    strBuilder.append(str);
                }
                return strBuilder.toString();
        }
    }

    public static String repeat(String str, String str2, int i) {
        if (str == null || str2 == null) {
            return repeat(str, i);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(str2);
        return removeEnd(repeat(stringBuffer.toString(), i), str2);
    }

    private static String a(int i, char c) throws IndexOutOfBoundsException {
        if (i >= 0) {
            char[] cArr = new char[i];
            for (int i2 = 0; i2 < cArr.length; i2++) {
                cArr[i2] = c;
            }
            return new String(cArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot pad a negative amount: ");
        stringBuffer.append(i);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
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
        return str.concat(a(length, c));
    }

    public static String rightPad(String str, int i, String str2) {
        if (str == null) {
            return null;
        }
        if (isEmpty(str2)) {
            str2 = org.apache.commons.lang3.StringUtils.SPACE;
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
        return a(length, c).concat(str);
    }

    public static String leftPad(String str, int i, String str2) {
        if (str == null) {
            return null;
        }
        if (isEmpty(str2)) {
            str2 = org.apache.commons.lang3.StringUtils.SPACE;
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

    public static int length(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
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
            str2 = org.apache.commons.lang3.StringUtils.SPACE;
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
        return (str == null || (length = str.length()) == 0) ? str : new StrBuilder(length).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String capitalise(String str) {
        return capitalize(str);
    }

    public static String uncapitalize(String str) {
        int length;
        return (str == null || (length = str.length()) == 0) ? str : new StrBuilder(length).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String uncapitalise(String str) {
        return uncapitalize(str);
    }

    public static String swapCase(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        StrBuilder strBuilder = new StrBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                charAt = Character.toLowerCase(charAt);
            } else if (Character.isTitleCase(charAt)) {
                charAt = Character.toLowerCase(charAt);
            } else if (Character.isLowerCase(charAt)) {
                charAt = Character.toUpperCase(charAt);
            }
            strBuilder.append(charAt);
        }
        return strBuilder.toString();
    }

    public static String capitaliseAllWords(String str) {
        return WordUtils.capitalize(str);
    }

    public static int countMatches(String str, String str2) {
        int i = 0;
        if (isEmpty(str) || isEmpty(str2)) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            int indexOf = str.indexOf(str2, i);
            if (indexOf == -1) {
                return i2;
            }
            i2++;
            i = indexOf + str2.length();
        }
    }

    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isLetter(str.charAt(i)) || str.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isLetterOrDigit(str.charAt(i)) || str.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAsciiPrintable(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!CharUtils.isAsciiPrintable(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isDigit(str.charAt(i)) || str.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllLowerCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLowerCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllUpperCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isUpperCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String defaultIfBlank(String str, String str2) {
        return isBlank(str) ? str2 : str;
    }

    public static String defaultIfEmpty(String str, String str2) {
        return isEmpty(str) ? str2 : str;
    }

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StrBuilder(str).reverse().toString();
    }

    public static String reverseDelimited(String str, char c) {
        if (str == null) {
            return null;
        }
        String[] split = split(str, c);
        ArrayUtils.reverse(split);
        return join(split, c);
    }

    public static String reverseDelimitedString(String str, String str2) {
        if (str == null) {
            return null;
        }
        String[] split = split(str, str2);
        ArrayUtils.reverse(split);
        if (str2 == null) {
            return join((Object[]) split, ' ');
        }
        return join(split, str2);
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
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str.substring(0, i3));
                stringBuffer.append("...");
                return stringBuffer.toString();
            } else if (i2 < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else if (i + i3 < str.length()) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("...");
                stringBuffer2.append(abbreviate(str.substring(i), i3));
                return stringBuffer2.toString();
            } else {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("...");
                stringBuffer3.append(str.substring(str.length() - i3));
                return stringBuffer3.toString();
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
        StrBuilder strBuilder = new StrBuilder(i);
        strBuilder.append(str.substring(0, i3));
        strBuilder.append(str2);
        strBuilder.append(str.substring(length2));
        return strBuilder.toString();
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

    public static int indexOfDifference(String str, String str2) {
        if (str == str2) {
            return -1;
        }
        int i = 0;
        if (str == null || str2 == null) {
            return 0;
        }
        while (i < str.length() && i < str2.length() && str.charAt(i) == str2.charAt(i)) {
            i++;
        }
        if (i < str2.length() || i < str.length()) {
            return i;
        }
        return -1;
    }

    public static int indexOfDifference(String[] strArr) {
        if (strArr == null || strArr.length <= 1) {
            return -1;
        }
        int length = strArr.length;
        int i = Integer.MAX_VALUE;
        boolean z = true;
        int i2 = 0;
        boolean z2 = false;
        for (int i3 = 0; i3 < length; i3++) {
            if (strArr[i3] == null) {
                z2 = true;
                i = 0;
            } else {
                i = Math.min(strArr[i3].length(), i);
                i2 = Math.max(strArr[i3].length(), i2);
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
            char charAt = strArr[0].charAt(i5);
            int i6 = 1;
            while (true) {
                if (i6 >= length) {
                    break;
                } else if (strArr[i6].charAt(i5) != charAt) {
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

    public static String getCommonPrefix(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        int indexOfDifference = indexOfDifference(strArr);
        return indexOfDifference == -1 ? strArr[0] == null ? "" : strArr[0] : indexOfDifference == 0 ? "" : strArr[0].substring(0, indexOfDifference);
    }

    public static int getLevenshteinDistance(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = str.length();
        int length2 = str2.length();
        if (length == 0) {
            return length2;
        }
        if (length2 == 0) {
            return length;
        }
        if (length > length2) {
            length2 = str.length();
            length = length2;
        } else {
            str2 = str;
            str = str2;
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
            char charAt = str.charAt(i3 - 1);
            iArr3[0] = i3;
            for (int i4 = 1; i4 <= length; i4++) {
                int i5 = i4 - 1;
                iArr3[i4] = Math.min(Math.min(iArr3[i5] + 1, iArr[i4] + 1), iArr[i5] + (str2.charAt(i5) == charAt ? 0 : 1));
            }
            i3++;
            iArr3 = iArr;
            iArr = iArr3;
        }
        return iArr[length];
    }

    public static boolean startsWith(String str, String str2) {
        return a(str, str2, false);
    }

    public static boolean startsWithIgnoreCase(String str, String str2) {
        return a(str, str2, true);
    }

    private static boolean a(String str, String str2, boolean z) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        }
        if (str2.length() > str.length()) {
            return false;
        }
        return str.regionMatches(z, 0, str2, 0, str2.length());
    }

    public static boolean startsWithAny(String str, String[] strArr) {
        if (isEmpty(str) || ArrayUtils.isEmpty(strArr)) {
            return false;
        }
        for (String str2 : strArr) {
            if (startsWith(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean endsWith(String str, String str2) {
        return b(str, str2, false);
    }

    public static boolean endsWithIgnoreCase(String str, String str2) {
        return b(str, str2, true);
    }

    private static boolean b(String str, String str2, boolean z) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        }
        if (str2.length() > str.length()) {
            return false;
        }
        return str.regionMatches(z, str.length() - str2.length(), str2, 0, str2.length());
    }

    public static String normalizeSpace(String str) {
        String strip = strip(str);
        if (strip == null || strip.length() <= 2) {
            return strip;
        }
        StrBuilder strBuilder = new StrBuilder(strip.length());
        for (int i = 0; i < strip.length(); i++) {
            char charAt = strip.charAt(i);
            if (!Character.isWhitespace(charAt)) {
                strBuilder.append(charAt);
            } else if (i > 0 && !Character.isWhitespace(strip.charAt(i - 1))) {
                strBuilder.append(' ');
            }
        }
        return strBuilder.toString();
    }

    public static boolean endsWithAny(String str, String[] strArr) {
        if (isEmpty(str) || ArrayUtils.isEmpty(strArr)) {
            return false;
        }
        for (String str2 : strArr) {
            if (endsWith(str, str2)) {
                return true;
            }
        }
        return false;
    }
}
