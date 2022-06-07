package io.realm.internal.android;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* loaded from: classes5.dex */
public class ISO8601Utils {
    private static final TimeZone a = TimeZone.getTimeZone("UTC");
    private static final TimeZone b = a;

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
            int a2 = a(str, index, i7);
            if (a(str, i7, '-')) {
                i7++;
            }
            int i8 = i7 + 2;
            int a3 = a(str, i7, i8);
            if (a(str, i8, '-')) {
                i8++;
            }
            int i9 = i8 + 2;
            int a4 = a(str, i8, i9);
            boolean a5 = a(str, i9, 'T');
            if (a5 || str.length() > i9) {
                if (a5) {
                    int i10 = i9 + 1;
                    int i11 = i10 + 2;
                    i5 = a(str, i10, i11);
                    if (a(str, i11, ':')) {
                        i11++;
                    }
                    i2 = i11 + 2;
                    i4 = a(str, i11, i2);
                    if (a(str, i2, ':')) {
                        i2++;
                    }
                    if (str.length() <= i2 || (charAt = str.charAt(i2)) == 'Z' || charAt == '+' || charAt == '-') {
                        i3 = 0;
                        i = 0;
                    } else {
                        int i12 = i2 + 2;
                        int a6 = a(str, i2, i12);
                        i = 59;
                        if (a6 <= 59 || a6 >= 63) {
                            i = a6;
                        }
                        if (a(str, i12, '.')) {
                            int i13 = i12 + 1;
                            i2 = a(str, i13 + 1);
                            int min = Math.min(i2, i13 + 3);
                            i3 = a(str, i13, min);
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
                        timeZone = b;
                        i6 = i2 + 1;
                    } else {
                        if (!(charAt2 == '+' || charAt2 == '-')) {
                            throw new IndexOutOfBoundsException("Invalid time zone indicator '" + charAt2 + LrcRow.SINGLE_QUOTE);
                        }
                        String substring = str.substring(i2);
                        i6 = i2 + substring.length();
                        if (substring.length() == 3) {
                            substring = substring + "00";
                        }
                        if (!"+0000".equals(substring) && !"+00:00".equals(substring)) {
                            String str3 = "GMT" + substring;
                            TimeZone timeZone2 = TimeZone.getTimeZone(str3);
                            String id = timeZone2.getID();
                            if (!id.equals(str3) && !id.replace(Constants.COLON_SEPARATOR, "").equals(str3)) {
                                throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str3 + " given, resolves to " + timeZone2.getID());
                            }
                            timeZone = timeZone2;
                        }
                        timeZone = b;
                    }
                    GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
                    gregorianCalendar.setLenient(false);
                    gregorianCalendar.set(1, a2);
                    gregorianCalendar.set(2, a3 - 1);
                    gregorianCalendar.set(5, a4);
                    gregorianCalendar.set(11, i5);
                    gregorianCalendar.set(12, i4);
                    gregorianCalendar.set(13, i);
                    gregorianCalendar.set(14, i3);
                    parsePosition.setIndex(i6);
                    return gregorianCalendar.getTime();
                }
                throw new IllegalArgumentException("No time zone indicator");
            }
            GregorianCalendar gregorianCalendar2 = new GregorianCalendar(a2, a3 - 1, a4);
            parsePosition.setIndex(i9);
            return gregorianCalendar2.getTime();
        } catch (IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException e) {
            if (str == null) {
                str2 = null;
            } else {
                str2 = '\"' + str + LrcRow.SINGLE_QUOTE;
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

    private static boolean a(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int a(String str, int i, int i2) throws NumberFormatException {
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

    private static int a(String str, int i) {
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
