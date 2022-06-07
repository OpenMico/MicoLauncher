package com.google.android.material.datepicker;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.icu.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.R;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UtcDates.java */
/* loaded from: classes2.dex */
public class k {
    static AtomicReference<j> a = new AtomicReference<>();

    static j a() {
        j jVar = a.get();
        return jVar == null ? j.a() : jVar;
    }

    private static TimeZone e() {
        return TimeZone.getTimeZone("UTC");
    }

    @TargetApi(24)
    private static android.icu.util.TimeZone f() {
        return android.icu.util.TimeZone.getTimeZone("UTC");
    }

    public static Calendar b() {
        Calendar b = a().b();
        b.set(11, 0);
        b.set(12, 0);
        b.set(13, 0);
        b.set(14, 0);
        b.setTimeZone(e());
        return b;
    }

    public static Calendar c() {
        return a((Calendar) null);
    }

    static Calendar a(@Nullable Calendar calendar) {
        Calendar instance = Calendar.getInstance(e());
        if (calendar == null) {
            instance.clear();
        } else {
            instance.setTimeInMillis(calendar.getTimeInMillis());
        }
        return instance;
    }

    public static Calendar b(Calendar calendar) {
        Calendar a2 = a(calendar);
        Calendar c = c();
        c.set(a2.get(1), a2.get(2), a2.get(5));
        return c;
    }

    public static long a(long j) {
        Calendar c = c();
        c.setTimeInMillis(j);
        return b(c).getTimeInMillis();
    }

    @TargetApi(24)
    private static DateFormat a(String str, Locale locale) {
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(str, locale);
        instanceForSkeleton.setTimeZone(f());
        return instanceForSkeleton;
    }

    private static java.text.DateFormat a(int i, Locale locale) {
        java.text.DateFormat dateInstance = java.text.DateFormat.getDateInstance(i, locale);
        dateInstance.setTimeZone(e());
        return dateInstance;
    }

    public static SimpleDateFormat d() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(((SimpleDateFormat) java.text.DateFormat.getDateInstance(3, Locale.getDefault())).toLocalizedPattern().replaceAll("\\s+", ""), Locale.getDefault());
        simpleDateFormat.setTimeZone(e());
        simpleDateFormat.setLenient(false);
        return simpleDateFormat;
    }

    public static String a(Resources resources, SimpleDateFormat simpleDateFormat) {
        String localizedPattern = simpleDateFormat.toLocalizedPattern();
        return localizedPattern.replaceAll("d", resources.getString(R.string.mtrl_picker_text_input_day_abbr)).replaceAll("M", resources.getString(R.string.mtrl_picker_text_input_month_abbr)).replaceAll("y", resources.getString(R.string.mtrl_picker_text_input_year_abbr));
    }

    @TargetApi(24)
    public static DateFormat a(Locale locale) {
        return a("yMMMd", locale);
    }

    @TargetApi(24)
    public static DateFormat b(Locale locale) {
        return a("MMMd", locale);
    }

    @TargetApi(24)
    public static DateFormat c(Locale locale) {
        return a("MMMEd", locale);
    }

    @TargetApi(24)
    public static DateFormat d(Locale locale) {
        return a("yMMMEd", locale);
    }

    public static java.text.DateFormat e(Locale locale) {
        return a(2, locale);
    }

    public static java.text.DateFormat f(Locale locale) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) e(locale);
        simpleDateFormat.applyPattern(a(simpleDateFormat.toPattern()));
        return simpleDateFormat;
    }

    public static java.text.DateFormat g(Locale locale) {
        return a(0, locale);
    }

    @NonNull
    private static String a(@NonNull String str) {
        int a2 = a(str, "yY", 1, 0);
        if (a2 >= str.length()) {
            return str;
        }
        String str2 = "EMd";
        int a3 = a(str, str2, 1, a2);
        if (a3 < str.length()) {
            str2 = str2 + Constants.ACCEPT_TIME_SEPARATOR_SP;
        }
        return str.replace(str.substring(a(str, str2, -1, a2) + 1, a3), StringUtils.SPACE).trim();
    }

    private static int a(@NonNull String str, @NonNull String str2, int i, int i2) {
        while (i2 >= 0 && i2 < str.length() && str2.indexOf(str.charAt(i2)) == -1) {
            if (str.charAt(i2) == '\'') {
                i2 += i;
                while (i2 >= 0 && i2 < str.length() && str.charAt(i2) != '\'') {
                    i2 += i;
                }
            }
            i2 += i;
        }
        return i2;
    }
}
