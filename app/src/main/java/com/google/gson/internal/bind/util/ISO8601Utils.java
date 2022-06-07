package com.google.gson.internal.bind.util;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        char c = '-';
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / 60000;
            int abs = Math.abs(i / 60);
            int abs2 = Math.abs(i % 60);
            if (offset >= 0) {
                c = '+';
            }
            sb.append(c);
            padInt(sb, abs, 2);
            sb.append(':');
            padInt(sb, abs2, 2);
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }

    public static Date parse(String str, ParsePosition parsePosition) throws ParseException {
        String str2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        TimeZone timeZone;
        char charAt;
        try {
            int index = parsePosition.getIndex();
            int i7 = index + 4;
            int parseInt = parseInt(str, index, i7);
            if (checkOffset(str, i7, '-')) {
                i7++;
            }
            int i8 = i7 + 2;
            int parseInt2 = parseInt(str, i7, i8);
            if (checkOffset(str, i8, '-')) {
                i8++;
            }
            int i9 = i8 + 2;
            int parseInt3 = parseInt(str, i8, i9);
            boolean checkOffset = checkOffset(str, i9, 'T');
            if (checkOffset || str.length() > i9) {
                if (checkOffset) {
                    int i10 = i9 + 1;
                    int i11 = i10 + 2;
                    i5 = parseInt(str, i10, i11);
                    if (checkOffset(str, i11, ':')) {
                        i11++;
                    }
                    i2 = i11 + 2;
                    i4 = parseInt(str, i11, i2);
                    if (checkOffset(str, i2, ':')) {
                        i2++;
                    }
                    if (str.length() <= i2 || (charAt = str.charAt(i2)) == 'Z' || charAt == '+' || charAt == '-') {
                        i3 = 0;
                        i = 0;
                    } else {
                        int i12 = i2 + 2;
                        int parseInt4 = parseInt(str, i2, i12);
                        i = 59;
                        if (parseInt4 <= 59 || parseInt4 >= 63) {
                            i = parseInt4;
                        }
                        if (checkOffset(str, i12, '.')) {
                            int i13 = i12 + 1;
                            i2 = indexOfNonDigit(str, i13 + 1);
                            int min = Math.min(i2, i13 + 3);
                            i3 = parseInt(str, i13, min);
                            switch (min - i13) {
                                case 1:
                                    i3 *= 100;
                                    break;
                                case 2:
                                    i3 *= 10;
                                    break;
                            }
                        } else {
                            i2 = i12;
                            i3 = 0;
                        }
                    }
                } else {
                    i2 = i9;
                    i5 = 0;
                    i4 = 0;
                    i3 = 0;
                    i = 0;
                }
                if (str.length() > i2) {
                    char charAt2 = str.charAt(i2);
                    if (charAt2 == 'Z') {
                        timeZone = TIMEZONE_UTC;
                        i6 = i2 + 1;
                    } else {
                        if (!(charAt2 == '+' || charAt2 == '-')) {
                            throw new IndexOutOfBoundsException("Invalid time zone indicator '" + charAt2 + LrcRow.SINGLE_QUOTE);
                        }
                        String substring = str.substring(i2);
                        if (substring.length() < 5) {
                            substring = substring + "00";
                        }
                        i6 = i2 + substring.length();
                        if (!"+0000".equals(substring) && !"+00:00".equals(substring)) {
                            String str3 = "GMT" + substring;
                            TimeZone timeZone2 = TimeZone.getTimeZone(str3);
                            String id = timeZone2.getID();
                            if (!id.equals(str3) && !id.replace(Constants.COLON_SEPARATOR, "").equals(str3)) {
                                throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str3 + " given, resolves to " + timeZone2.getID());
                            }
                            timeZone = timeZone2;
                        }
                        timeZone = TIMEZONE_UTC;
                    }
                    GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
                    gregorianCalendar.setLenient(false);
                    gregorianCalendar.set(1, parseInt);
                    gregorianCalendar.set(2, parseInt2 - 1);
                    gregorianCalendar.set(5, parseInt3);
                    gregorianCalendar.set(11, i5);
                    gregorianCalendar.set(12, i4);
                    gregorianCalendar.set(13, i);
                    gregorianCalendar.set(14, i3);
                    parsePosition.setIndex(i6);
                    return gregorianCalendar.getTime();
                }
                throw new IllegalArgumentException("No time zone indicator");
            }
            GregorianCalendar gregorianCalendar2 = new GregorianCalendar(parseInt, parseInt2 - 1, parseInt3);
            parsePosition.setIndex(i9);
            return gregorianCalendar2.getTime();
        } catch (IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException e) {
            if (str == null) {
                str2 = null;
            } else {
                str2 = '\"' + str + '\"';
            }
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                message = "(" + e.getClass().getName() + ")";
            }
            ParseException parseException = new ParseException("Failed to parse date [" + str2 + "]: " + message, parsePosition.getIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        int i4 = 0;
        if (i < i2) {
            i3 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit >= 0) {
                i4 = -digit;
            } else {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
        } else {
            i3 = i;
        }
        while (i3 < i2) {
            i3++;
            int digit2 = Character.digit(str.charAt(i3), 10);
            if (digit2 >= 0) {
                i4 = (i4 * 10) - digit2;
            } else {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
        }
        return -i4;
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String num = Integer.toString(i);
        for (int length = i2 - num.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(num);
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}
