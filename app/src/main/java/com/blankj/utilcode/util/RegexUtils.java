package com.blankj.utilcode.util;

import androidx.collection.SimpleArrayMap;
import androidx.room.RoomMasterTable;
import com.blankj.utilcode.constant.RegexConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class RegexUtils {
    private static final SimpleArrayMap<String, String> a = new SimpleArrayMap<>();

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isMobileSimple(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, charSequence);
    }

    public static boolean isMobileExact(CharSequence charSequence) {
        return isMobileExact(charSequence, null);
    }

    public static boolean isMobileExact(CharSequence charSequence, List<String> list) {
        if (isMatch(RegexConstants.REGEX_MOBILE_EXACT, charSequence)) {
            return true;
        }
        if (list == null || charSequence == null || charSequence.length() != 11) {
            return false;
        }
        String charSequence2 = charSequence.toString();
        for (char c : charSequence2.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        for (String str : list) {
            if (charSequence2.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTel(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_TEL, charSequence);
    }

    public static boolean isIDCard15(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_ID_CARD15, charSequence);
    }

    public static boolean isIDCard18(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_ID_CARD18, charSequence);
    }

    public static boolean isIDCard18Exact(CharSequence charSequence) {
        if (isIDCard18(charSequence)) {
            int[] iArr = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            char[] cArr = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
            if (a.isEmpty()) {
                a.put("11", "??????");
                a.put("12", "??????");
                a.put("13", "??????");
                a.put("14", "??????");
                a.put("15", "?????????");
                a.put("21", "??????");
                a.put("22", "??????");
                a.put("23", "?????????");
                a.put("31", "??????");
                a.put("32", "??????");
                a.put("33", "??????");
                a.put("34", "??????");
                a.put("35", "??????");
                a.put("36", "??????");
                a.put("37", "??????");
                a.put("41", "??????");
                a.put(RoomMasterTable.DEFAULT_ID, "??????");
                a.put("43", "??????");
                a.put("44", "??????");
                a.put("45", "??????");
                a.put("46", "??????");
                a.put("50", "??????");
                a.put("51", "??????");
                a.put("52", "??????");
                a.put("53", "??????");
                a.put("54", "??????");
                a.put("61", "??????");
                a.put("62", "??????");
                a.put("63", "??????");
                a.put("64", "??????");
                a.put("65", "??????");
                a.put("71", "?????????");
                a.put("81", "??????");
                a.put("82", "??????");
                a.put("83", "?????????");
                a.put("91", "??????");
            }
            if (a.get(charSequence.subSequence(0, 2).toString()) != null) {
                int i = 0;
                for (int i2 = 0; i2 < 17; i2++) {
                    i += (charSequence.charAt(i2) - '0') * iArr[i2];
                }
                return charSequence.charAt(17) == cArr[i % 11];
            }
        }
        return false;
    }

    public static boolean isEmail(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_EMAIL, charSequence);
    }

    public static boolean isURL(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_URL, charSequence);
    }

    public static boolean isZh(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_ZH, charSequence);
    }

    public static boolean isUsername(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_USERNAME, charSequence);
    }

    public static boolean isDate(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_DATE, charSequence);
    }

    public static boolean isIP(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_IP, charSequence);
    }

    public static boolean isMatch(String str, CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0 && Pattern.matches(str, charSequence);
    }

    public static List<String> getMatches(String str, CharSequence charSequence) {
        if (charSequence == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile(str).matcher(charSequence);
        while (matcher.find()) {
            arrayList.add(matcher.group());
        }
        return arrayList;
    }

    public static String[] getSplits(String str, String str2) {
        if (str == null) {
            return new String[0];
        }
        return str.split(str2);
    }

    public static String getReplaceFirst(String str, String str2, String str3) {
        return str == null ? "" : Pattern.compile(str2).matcher(str).replaceFirst(str3);
    }

    public static String getReplaceAll(String str, String str2, String str3) {
        return str == null ? "" : Pattern.compile(str2).matcher(str).replaceAll(str3);
    }
}
