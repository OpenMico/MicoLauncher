package com.xiaomi.micolauncher.common.event;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/* loaded from: classes3.dex */
public class RefreshTimeEvent {
    public String getHourMinutes() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        return String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(gregorianCalendar.get(11)), Integer.valueOf(gregorianCalendar.get(12)));
    }
}
