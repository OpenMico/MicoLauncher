package com.xiaomi.micolauncher.skills.alarm.model;

import androidx.annotation.VisibleForTesting;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

/* loaded from: classes3.dex */
public class MicoDate extends Date {
    @VisibleForTesting
    public static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() { // from class: com.xiaomi.micolauncher.skills.alarm.model.MicoDate.1
        /* renamed from: a */
        public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-M-d", Locale.CHINA);
        }
    };
    private static Hashtable<String, String> a = new Hashtable<>();
    private static Hashtable<String, String> b = new Hashtable<>();

    private static void a(DataInputStream dataInputStream, Hashtable<String, String> hashtable) throws IOException {
        while (true) {
            String readLine = dataInputStream.readLine();
            if (readLine != null && readLine.length() != 0) {
                hashtable.put(readLine, readLine);
            } else {
                return;
            }
        }
    }

    private static void a(String str, Hashtable<String, String> hashtable) {
        String workDayConfig = WorkDaySyncService.getWorkDayConfig(str);
        if (StringUtil.isNullOrEmpty(workDayConfig)) {
            try {
                DataInputStream dataInputStream = new DataInputStream(MicoApplication.getGlobalContext().getAssets().open(str));
                a(dataInputStream, hashtable);
                dataInputStream.close();
            } catch (IOException e) {
                L.alarm.w(e.getMessage());
            }
        } else {
            try {
                DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(workDayConfig.getBytes()));
                a(dataInputStream2, hashtable);
                dataInputStream2.close();
            } catch (Exception e2) {
                L.alarm.w(e2.getMessage());
            }
        }
    }

    public static void updateHolidayAndWorkday() {
        a("unnomal_holiday.cfg", a);
        a("unnomal_workday.cfg", b);
    }

    public MicoDate(long j) {
        super(j);
    }

    public boolean isWeekday() {
        int c = c();
        return (c == 1 || c == 7) ? false : true;
    }

    public boolean isWeekend() {
        return !isWeekday();
    }

    public boolean isWorkday() {
        if (isWeekday()) {
            return !a();
        }
        return b();
    }

    public boolean isHoliday() {
        if (isWeekend()) {
            return !b();
        }
        return a();
    }

    private boolean a() {
        return a.contains(DATE_FORMATTER.get().format((Date) this));
    }

    private boolean b() {
        return b.contains(DATE_FORMATTER.get().format((Date) this));
    }

    private int c() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(this);
        return instance.get(7);
    }
}
