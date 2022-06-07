package org.apache.commons.lang3.time;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.api.c;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* loaded from: classes5.dex */
public class DurationFormatUtils {
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'";
    static final Object a = "y";
    static final Object b = "M";
    static final Object c = "d";
    static final Object d = c.b;
    static final Object e = MessageElement.XPATH_PREFIX;
    static final Object f = ai.az;
    static final Object g = ExifInterface.LATITUDE_SOUTH;

    public static String formatDurationHMS(long j) {
        return formatDuration(j, "HH:mm:ss.SSS");
    }

    public static String formatDurationISO(long j) {
        return formatDuration(j, ISO_EXTENDED_FORMAT_PATTERN, false);
    }

    public static String formatDuration(long j, String str) {
        return formatDuration(j, str, true);
    }

    public static String formatDuration(long j, String str, boolean z) {
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        Validate.inclusiveBetween(0L, Long.MAX_VALUE, j, "durationMillis must not be negative");
        a[] a2 = a(str);
        if (a.a(a2, c)) {
            long j8 = j / 86400000;
            j3 = j - (86400000 * j8);
            j2 = j8;
        } else {
            j3 = j;
            j2 = 0;
        }
        if (a.a(a2, d)) {
            long j9 = j3 / 3600000;
            j3 -= 3600000 * j9;
            j4 = j9;
        } else {
            j4 = 0;
        }
        if (a.a(a2, e)) {
            long j10 = j3 / 60000;
            j3 -= 60000 * j10;
            j5 = j10;
        } else {
            j5 = 0;
        }
        if (a.a(a2, f)) {
            long j11 = j3 / 1000;
            j6 = j3 - (1000 * j11);
            j7 = j11;
        } else {
            j7 = 0;
            j6 = j3;
        }
        return a(a2, 0L, 0L, j2, j4, j5, j7, j6, z);
    }

    public static String formatDurationWords(long j, boolean z, boolean z2) {
        String formatDuration = formatDuration(j, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (z) {
            formatDuration = StringUtils.SPACE + formatDuration;
            String replaceOnce = StringUtils.replaceOnce(formatDuration, " 0 days", "");
            if (replaceOnce.length() != formatDuration.length()) {
                String replaceOnce2 = StringUtils.replaceOnce(replaceOnce, " 0 hours", "");
                if (replaceOnce2.length() != replaceOnce.length()) {
                    formatDuration = StringUtils.replaceOnce(replaceOnce2, " 0 minutes", "");
                    if (formatDuration.length() != formatDuration.length()) {
                        formatDuration = StringUtils.replaceOnce(formatDuration, " 0 seconds", "");
                    }
                } else {
                    formatDuration = replaceOnce;
                }
            }
            if (formatDuration.length() != 0) {
                formatDuration = formatDuration.substring(1);
            }
        }
        if (z2) {
            String replaceOnce3 = StringUtils.replaceOnce(formatDuration, " 0 seconds", "");
            if (replaceOnce3.length() != formatDuration.length()) {
                formatDuration = StringUtils.replaceOnce(replaceOnce3, " 0 minutes", "");
                if (formatDuration.length() != replaceOnce3.length()) {
                    String replaceOnce4 = StringUtils.replaceOnce(formatDuration, " 0 hours", "");
                    if (replaceOnce4.length() != formatDuration.length()) {
                        formatDuration = StringUtils.replaceOnce(replaceOnce4, " 0 days", "");
                    }
                } else {
                    formatDuration = replaceOnce3;
                }
            }
        }
        return StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.SPACE + formatDuration, " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }

    public static String formatPeriodISO(long j, long j2) {
        return formatPeriod(j, j2, ISO_EXTENDED_FORMAT_PATTERN, false, TimeZone.getDefault());
    }

    public static String formatPeriod(long j, long j2, String str) {
        return formatPeriod(j, j2, str, true, TimeZone.getDefault());
    }

    public static String formatPeriod(long j, long j2, String str, boolean z, TimeZone timeZone) {
        Validate.isTrue(j <= j2, "startMillis must not be greater than endMillis", new Object[0]);
        a[] a2 = a(str);
        Calendar instance = Calendar.getInstance(timeZone);
        instance.setTime(new Date(j));
        Calendar instance2 = Calendar.getInstance(timeZone);
        instance2.setTime(new Date(j2));
        int i = instance2.get(14) - instance.get(14);
        int i2 = instance2.get(13) - instance.get(13);
        int i3 = instance2.get(12) - instance.get(12);
        int i4 = instance2.get(11) - instance.get(11);
        int i5 = instance2.get(5) - instance.get(5);
        int i6 = instance2.get(2) - instance.get(2);
        int i7 = instance2.get(1) - instance.get(1);
        while (i < 0) {
            i += 1000;
            i2--;
        }
        while (i2 < 0) {
            i2 += 60;
            i3--;
        }
        while (i3 < 0) {
            i3 += 60;
            i4--;
        }
        while (i4 < 0) {
            i4 += 24;
            i5--;
        }
        if (a.a(a2, b)) {
            while (i5 < 0) {
                i5 += instance.getActualMaximum(5);
                i6--;
                instance.add(2, 1);
            }
            while (i6 < 0) {
                i6 += 12;
                i7--;
            }
            if (!a.a(a2, a) && i7 != 0) {
                while (i7 != 0) {
                    i6 += i7 * 12;
                    i7 = 0;
                }
            }
        } else {
            if (!a.a(a2, a)) {
                int i8 = instance2.get(1);
                if (i6 < 0) {
                    i8--;
                }
                while (instance.get(1) != i8) {
                    int actualMaximum = i5 + (instance.getActualMaximum(6) - instance.get(6));
                    if ((instance instanceof GregorianCalendar) && instance.get(2) == 1 && instance.get(5) == 29) {
                        actualMaximum++;
                    }
                    instance.add(1, 1);
                    i5 = actualMaximum + instance.get(6);
                }
                i7 = 0;
            }
            while (instance.get(2) != instance2.get(2)) {
                i5 += instance.getActualMaximum(5);
                instance.add(2, 1);
            }
            i6 = 0;
            while (i5 < 0) {
                i5 += instance.getActualMaximum(5);
                i6--;
                instance.add(2, 1);
            }
        }
        if (!a.a(a2, c)) {
            i4 += i5 * 24;
            i5 = 0;
        }
        if (!a.a(a2, d)) {
            i3 += i4 * 60;
            i4 = 0;
        }
        if (!a.a(a2, e)) {
            i2 += i3 * 60;
            i3 = 0;
        }
        if (!a.a(a2, f)) {
            i += i2 * 1000;
            i2 = 0;
        }
        return a(a2, i7, i6, i5, i4, i3, i2, i, z);
    }

    static String a(a[] aVarArr, long j, long j2, long j3, long j4, long j5, long j6, long j7, boolean z) {
        int i;
        a[] aVarArr2 = aVarArr;
        StringBuilder sb = new StringBuilder();
        int length = aVarArr2.length;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < length) {
            a aVar = aVarArr2[i2];
            Object c2 = aVar.c();
            int b2 = aVar.b();
            if (c2 instanceof StringBuilder) {
                sb.append(c2.toString());
                length = length;
                i = i2;
            } else if (c2.equals(a)) {
                sb.append(a(j, z, b2));
                length = length;
                i = i2;
                z2 = false;
            } else if (c2.equals(b)) {
                i = i2;
                sb.append(a(j2, z, b2));
                length = length;
                z2 = false;
            } else {
                i = i2;
                if (c2.equals(c)) {
                    sb.append(a(j3, z, b2));
                    length = length;
                    z2 = false;
                } else if (c2.equals(d)) {
                    sb.append(a(j4, z, b2));
                    length = length;
                    z2 = false;
                } else if (c2.equals(e)) {
                    sb.append(a(j5, z, b2));
                    length = length;
                    z2 = false;
                } else if (c2.equals(f)) {
                    length = length;
                    sb.append(a(j6, z, b2));
                    z2 = true;
                } else {
                    length = length;
                    if (c2.equals(g)) {
                        if (z2) {
                            int i3 = 3;
                            if (z) {
                                i3 = Math.max(3, b2);
                            }
                            sb.append(a(j7, true, i3));
                        } else {
                            sb.append(a(j7, z, b2));
                        }
                        z2 = false;
                    }
                }
            }
            i2 = i + 1;
            aVarArr2 = aVarArr;
        }
        return sb.toString();
    }

    private static String a(long j, boolean z, int i) {
        String l = Long.toString(j);
        return z ? StringUtils.leftPad(l, i, '0') : l;
    }

    static a[] a(String str) {
        Object obj;
        ArrayList arrayList = new ArrayList(str.length());
        boolean z = false;
        StringBuilder sb = null;
        a aVar = null;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!z || charAt == '\'') {
                if (charAt != '\'') {
                    if (charAt == 'H') {
                        obj = d;
                    } else if (charAt == 'M') {
                        obj = b;
                    } else if (charAt == 'S') {
                        obj = g;
                    } else if (charAt == 'd') {
                        obj = c;
                    } else if (charAt == 'm') {
                        obj = e;
                    } else if (charAt == 's') {
                        obj = f;
                    } else if (charAt != 'y') {
                        if (sb == null) {
                            sb = new StringBuilder();
                            arrayList.add(new a(sb));
                        }
                        sb.append(charAt);
                        obj = null;
                    } else {
                        obj = a;
                    }
                } else if (z) {
                    z = false;
                    sb = null;
                    obj = null;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    arrayList.add(new a(sb2));
                    z = true;
                    obj = null;
                    sb = sb2;
                }
                if (obj != null) {
                    if (aVar == null || !aVar.c().equals(obj)) {
                        aVar = new a(obj);
                        arrayList.add(aVar);
                    } else {
                        aVar.a();
                    }
                    sb = null;
                }
            } else {
                sb.append(charAt);
            }
        }
        if (!z) {
            return (a[]) arrayList.toArray(new a[arrayList.size()]);
        }
        throw new IllegalArgumentException("Unmatched quote in format: " + str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class a {
        private final Object a;
        private int b = 1;

        static boolean a(a[] aVarArr, Object obj) {
            for (a aVar : aVarArr) {
                if (aVar.c() == obj) {
                    return true;
                }
            }
            return false;
        }

        a(Object obj) {
            this.a = obj;
        }

        void a() {
            this.b++;
        }

        int b() {
            return this.b;
        }

        Object c() {
            return this.a;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (this.a.getClass() != aVar.a.getClass() || this.b != aVar.b) {
                return false;
            }
            Object obj2 = this.a;
            if (obj2 instanceof StringBuilder) {
                return obj2.toString().equals(aVar.a.toString());
            }
            if (obj2 instanceof Number) {
                return obj2.equals(aVar.a);
            }
            return obj2 == aVar.a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return StringUtils.repeat(this.a.toString(), this.b);
        }
    }
}
