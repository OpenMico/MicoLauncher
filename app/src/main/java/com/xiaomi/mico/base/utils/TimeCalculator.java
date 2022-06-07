package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.text.format.DateUtils;
import com.xiaomi.mico.base.R;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes3.dex */
public class TimeCalculator {
    private static final SimpleDateFormat a = new SimpleDateFormat("MM/dd HH:mm");

    public static String getCommonListFormatTimeWithDetail(Context context, long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        int i = instance.get(1);
        int i2 = instance.get(6);
        instance.setTimeInMillis(j);
        int i3 = instance.get(1);
        int i4 = instance.get(2) + 1;
        int i5 = instance.get(6);
        int i6 = instance.get(5);
        int i7 = instance.get(11);
        int i8 = instance.get(12);
        return i3 != i ? context.getString(R.string.file_date_full_format_with_detail, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8)) : i5 == i2 ? context.getString(R.string.file_date_today_format_with_detail, Integer.valueOf(i7), Integer.valueOf(i8)) : i5 == i2 - 1 ? context.getString(R.string.file_date_yestoday_format_with_detail, Integer.valueOf(i7), Integer.valueOf(i8)) : context.getString(R.string.file_date_short_format_with_detail, Integer.valueOf(i4), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8));
    }

    public static String timeStamp2DateSlash(long j) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(j));
    }

    public static String formatSeconds2(long j) {
        long j2 = j % 60;
        long j3 = j / 60;
        if (j3 > 60) {
            j3 %= 60;
        }
        return completedString(j3) + LrcRow.SINGLE_QUOTE + completedString(j2) + "''";
    }

    public static String formatSeconds(long j) {
        long j2;
        long j3;
        long j4 = j % 60;
        long j5 = j / 60;
        if (j5 > 60) {
            j3 = j5 % 60;
            j2 = j5 / 60;
            if (j2 > 24) {
                j2 %= 24;
            }
        } else {
            j2 = 0;
            j3 = j5;
        }
        return completedString(j2) + Constants.COLON_SEPARATOR + completedString(j3) + Constants.COLON_SEPARATOR + completedString(j4);
    }

    public static int formatSeconds(String str) {
        String[] split = str.split(Constants.COLON_SEPARATOR);
        int i = 1;
        int i2 = 0;
        for (int length = split.length - 1; length >= 0; length--) {
            i2 += Integer.valueOf(split[length]).intValue() * i;
            i *= 60;
        }
        return i2;
    }

    public static String completedString(long j) {
        if (j < 10) {
            return "0" + j;
        }
        return "" + j;
    }

    public static long getMillisSecondOfDay(Date date) {
        Date date2 = (Date) date.clone();
        long time = date2.getTime();
        date2.setHours(0);
        date2.setMinutes(0);
        date2.setSeconds(0);
        return time - date2.getTime();
    }

    public static long getMillisSecondOfDay(long j) {
        Date date = new Date(j);
        long time = date.getTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return time - date.getTime();
    }

    public static boolean isToday(SimpleDateFormat simpleDateFormat, String str) {
        if (str == null) {
            return false;
        }
        try {
            return DateUtils.isToday(simpleDateFormat.parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isToday(String str, String str2) {
        if (str2 == null) {
            return false;
        }
        try {
            return DateUtils.isToday(new SimpleDateFormat(str).parse(str2).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isToday(Date date) {
        return DateUtils.isToday(date.getTime());
    }

    public static boolean isTomorrow(String str, String str2) {
        if (str2 != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
            try {
                Calendar instance = Calendar.getInstance();
                instance.setTime(new Date(System.currentTimeMillis()));
                Calendar instance2 = Calendar.getInstance();
                instance2.setTime(simpleDateFormat.parse(str2));
                int i = instance2.get(1);
                int i2 = instance.get(1);
                if (i == i2) {
                    if (instance2.get(6) - instance.get(6) == 1) {
                        return true;
                    }
                } else if (i == i2 + 1 && instance2.get(6) == 1) {
                    int i3 = instance.get(6);
                    instance.set(i2, 11, 31);
                    return instance2.get(6) == i3;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isTheDayAfterTomorrow(String str, String str2) {
        if (str2 != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
            try {
                Calendar instance = Calendar.getInstance();
                instance.setTime(new Date(System.currentTimeMillis()));
                Calendar instance2 = Calendar.getInstance();
                instance2.setTime(simpleDateFormat.parse(str2));
                int i = instance2.get(1);
                int i2 = instance.get(1);
                if (i == i2) {
                    if (instance2.get(6) - instance.get(6) == 2) {
                        return true;
                    }
                } else if (i == i2 + 1) {
                    int i3 = instance2.get(6);
                    if (i3 == 1) {
                        int i4 = instance.get(6);
                        instance.set(i2, 11, 31);
                        return instance2.get(6) == i4 + 1;
                    } else if (i3 == 2) {
                        int i5 = instance.get(6);
                        instance.set(i2, 11, 31);
                        return instance2.get(6) == i5;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isYesterday(String str, String str2) {
        if (str2 != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
            try {
                Calendar instance = Calendar.getInstance();
                instance.setTime(new Date(System.currentTimeMillis()));
                Calendar instance2 = Calendar.getInstance();
                instance2.setTime(simpleDateFormat.parse(str2));
                int i = instance2.get(1);
                int i2 = instance.get(1);
                if (i == i2) {
                    if (instance.get(6) - instance2.get(6) == 1) {
                        return true;
                    }
                } else if (i == i2 + 1 && instance.get(6) == 1) {
                    int i3 = instance2.get(6);
                    instance2.set(i2, 11, 31);
                    return instance2.get(6) == i3;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int dayOfWeek(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return (instance.get(7) + 5) % 7;
    }

    public static Date date(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(str).parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSameDate(Date date, Date date2) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return instance.get(1) == instance2.get(1) && instance.get(2) == instance2.get(2) && instance.get(5) == instance2.get(5);
    }
}
