package com.xiaomi.micolauncher.common.utils;

import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.mipush.sdk.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes3.dex */
public class DateUtil {
    public static String translateDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(11);
        int i2 = instance.get(12);
        return TimeCalculator.completedString(i) + Constants.COLON_SEPARATOR + TimeCalculator.completedString(i2);
    }

    public static Date strToDate(String str) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
