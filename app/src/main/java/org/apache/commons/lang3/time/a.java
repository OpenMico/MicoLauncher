package org.apache.commons.lang3.time;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.exception.ExceptionUtils;

/* compiled from: CalendarReflection.java */
/* loaded from: classes5.dex */
class a {
    private static final Method a = a("isWeekDateSupported", new Class[0]);
    private static final Method b = a("getWeekYear", new Class[0]);

    private static Method a(String str, Class<?>... clsArr) {
        try {
            return Calendar.class.getMethod(str, clsArr);
        } catch (Exception unused) {
            return null;
        }
    }

    static boolean a(Calendar calendar) {
        try {
            if (a != null) {
                return ((Boolean) a.invoke(calendar, new Object[0])).booleanValue();
            }
            return false;
        } catch (Exception e) {
            return ((Boolean) ExceptionUtils.rethrow(e)).booleanValue();
        }
    }

    public static int b(Calendar calendar) {
        try {
            if (a(calendar)) {
                return ((Integer) b.invoke(calendar, new Object[0])).intValue();
            }
            int i = calendar.get(1);
            if (a != null || !(calendar instanceof GregorianCalendar)) {
                return i;
            }
            int i2 = calendar.get(2);
            return i2 != 0 ? (i2 == 11 && calendar.get(3) == 1) ? i + 1 : i : calendar.get(3) >= 52 ? i - 1 : i;
        } catch (Exception e) {
            return ((Integer) ExceptionUtils.rethrow(e)).intValue();
        }
    }
}
