package org.apache.commons.lang.time;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.api.c;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang3.StringUtils;
import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* loaded from: classes5.dex */
public class DurationFormatUtils {
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";
    static final Object a = "y";
    static final Object b = "M";
    static final Object c = "d";
    static final Object d = c.b;
    static final Object e = MessageElement.XPATH_PREFIX;
    static final Object f = ai.az;
    static final Object g = ExifInterface.LATITUDE_SOUTH;

    public static String formatDurationHMS(long j) {
        return formatDuration(j, "H:mm:ss.SSS");
    }

    public static String formatDurationISO(long j) {
        return formatDuration(j, ISO_EXTENDED_FORMAT_PATTERN, false);
    }

    public static String formatDuration(long j, String str) {
        return formatDuration(j, str, true);
    }

    public static String formatDuration(long j, String str, boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        a[] a2 = a(str);
        if (a.a(a2, c)) {
            int i5 = (int) (j / 86400000);
            j -= i5 * 86400000;
            i = i5;
        } else {
            i = 0;
        }
        if (a.a(a2, d)) {
            int i6 = (int) (j / 3600000);
            j -= i6 * 3600000;
            i2 = i6;
        } else {
            i2 = 0;
        }
        if (a.a(a2, e)) {
            int i7 = (int) (j / 60000);
            j -= i7 * 60000;
            i3 = i7;
        } else {
            i3 = 0;
        }
        if (a.a(a2, f)) {
            int i8 = (int) (j / 1000);
            j -= i8 * 1000;
            i4 = i8;
        } else {
            i4 = 0;
        }
        return a(a2, 0, 0, i, i2, i3, i4, a.a(a2, g) ? (int) j : 0, z);
    }

    public static String formatDurationWords(long j, boolean z, boolean z2) {
        String formatDuration = formatDuration(j, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(StringUtils.SPACE);
            stringBuffer.append(formatDuration);
            formatDuration = stringBuffer.toString();
            String replaceOnce = org.apache.commons.lang.StringUtils.replaceOnce(formatDuration, " 0 days", "");
            if (replaceOnce.length() != formatDuration.length()) {
                String replaceOnce2 = org.apache.commons.lang.StringUtils.replaceOnce(replaceOnce, " 0 hours", "");
                if (replaceOnce2.length() != replaceOnce.length()) {
                    formatDuration = org.apache.commons.lang.StringUtils.replaceOnce(replaceOnce2, " 0 minutes", "");
                    if (formatDuration.length() != formatDuration.length()) {
                        formatDuration = org.apache.commons.lang.StringUtils.replaceOnce(formatDuration, " 0 seconds", "");
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
            String replaceOnce3 = org.apache.commons.lang.StringUtils.replaceOnce(formatDuration, " 0 seconds", "");
            if (replaceOnce3.length() != formatDuration.length()) {
                formatDuration = org.apache.commons.lang.StringUtils.replaceOnce(replaceOnce3, " 0 minutes", "");
                if (formatDuration.length() != replaceOnce3.length()) {
                    String replaceOnce4 = org.apache.commons.lang.StringUtils.replaceOnce(formatDuration, " 0 hours", "");
                    if (replaceOnce4.length() != formatDuration.length()) {
                        formatDuration = org.apache.commons.lang.StringUtils.replaceOnce(replaceOnce4, " 0 days", "");
                    }
                } else {
                    formatDuration = replaceOnce3;
                }
            }
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(StringUtils.SPACE);
        stringBuffer2.append(formatDuration);
        return org.apache.commons.lang.StringUtils.replaceOnce(org.apache.commons.lang.StringUtils.replaceOnce(org.apache.commons.lang.StringUtils.replaceOnce(org.apache.commons.lang.StringUtils.replaceOnce(stringBuffer2.toString(), " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }

    public static String formatPeriodISO(long j, long j2) {
        return formatPeriod(j, j2, ISO_EXTENDED_FORMAT_PATTERN, false, TimeZone.getDefault());
    }

    public static String formatPeriod(long j, long j2, String str) {
        return formatPeriod(j, j2, str, true, TimeZone.getDefault());
    }

    public static String formatPeriod(long j, long j2, String str, boolean z, TimeZone timeZone) {
        int i;
        int i2;
        int i3;
        a[] a2 = a(str);
        Calendar instance = Calendar.getInstance(timeZone);
        instance.setTime(new Date(j));
        Calendar instance2 = Calendar.getInstance(timeZone);
        instance2.setTime(new Date(j2));
        int i4 = instance2.get(14) - instance.get(14);
        int i5 = instance2.get(13) - instance.get(13);
        int i6 = instance2.get(12) - instance.get(12);
        int i7 = instance2.get(11) - instance.get(11);
        int i8 = instance2.get(5) - instance.get(5);
        int i9 = instance2.get(2) - instance.get(2);
        int i10 = instance2.get(1) - instance.get(1);
        while (i4 < 0) {
            i4 += 1000;
            i5--;
        }
        while (i5 < 0) {
            i5 += 60;
            i6--;
        }
        while (i6 < 0) {
            i6 += 60;
            i7--;
        }
        while (i7 < 0) {
            i7 += 24;
            i8--;
        }
        int i11 = 0;
        if (a.a(a2, b)) {
            while (i8 < 0) {
                i8 += instance.getActualMaximum(5);
                i9--;
                instance.add(2, 1);
            }
            while (i9 < 0) {
                i9 += 12;
                i10--;
            }
            if (a.a(a2, a) || i10 == 0) {
                i = i9;
                i10 = i10;
            } else {
                while (i10 != 0) {
                    i9 += i10 * 12;
                    i10 = 0;
                }
                i = i9;
                i10 = i10;
            }
        } else {
            if (!a.a(a2, a)) {
                int i12 = instance2.get(1);
                if (i9 < 0) {
                    i12--;
                }
                while (instance.get(1) != i12) {
                    int actualMaximum = i8 + (instance.getActualMaximum(6) - instance.get(6));
                    if ((instance instanceof GregorianCalendar) && instance.get(2) == 1 && instance.get(5) == 29) {
                        actualMaximum++;
                    }
                    instance.add(1, 1);
                    i8 = actualMaximum + instance.get(6);
                }
                i10 = 0;
            }
            while (instance.get(2) != instance2.get(2)) {
                i8 += instance.getActualMaximum(5);
                instance.add(2, 1);
            }
            i = 0;
            while (i8 < 0) {
                i8 += instance.getActualMaximum(5);
                i--;
                instance.add(2, 1);
            }
        }
        if (!a.a(a2, c)) {
            i7 += i8 * 24;
            i2 = 0;
        } else {
            i2 = i8;
        }
        if (!a.a(a2, d)) {
            i6 += i7 * 60;
            i7 = 0;
        }
        if (!a.a(a2, e)) {
            i5 += i6 * 60;
            i6 = 0;
        }
        if (!a.a(a2, f)) {
            i3 = i4 + (i5 * 1000);
        } else {
            i3 = i4;
            i11 = i5;
        }
        return a(a2, i10, i, i2, i7, i6, i11, i3, z);
    }

    static String a(a[] aVarArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        StrBuilder strBuilder = new StrBuilder();
        int i8 = i7;
        boolean z2 = false;
        for (a aVar : aVarArr) {
            Object c2 = aVar.c();
            int b2 = aVar.b();
            if (c2 instanceof StringBuffer) {
                strBuilder.append(c2.toString());
            } else if (c2 == a) {
                strBuilder.append(z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i), b2, '0') : Integer.toString(i));
                z2 = false;
            } else if (c2 == b) {
                strBuilder.append(z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i2), b2, '0') : Integer.toString(i2));
                z2 = false;
            } else if (c2 == c) {
                strBuilder.append(z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i3), b2, '0') : Integer.toString(i3));
                z2 = false;
            } else if (c2 == d) {
                strBuilder.append(z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i4), b2, '0') : Integer.toString(i4));
                z2 = false;
            } else if (c2 == e) {
                strBuilder.append(z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i5), b2, '0') : Integer.toString(i5));
                z2 = false;
            } else if (c2 == f) {
                strBuilder.append(z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i6), b2, '0') : Integer.toString(i6));
                z2 = true;
            } else if (c2 == g) {
                if (z2) {
                    i8 += 1000;
                    strBuilder.append((z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i8), b2, '0') : Integer.toString(i8)).substring(1));
                } else {
                    strBuilder.append(z ? org.apache.commons.lang.StringUtils.leftPad(Integer.toString(i8), b2, '0') : Integer.toString(i8));
                }
                z2 = false;
            }
        }
        return strBuilder.toString();
    }

    static a[] a(String str) {
        Object obj;
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList(charArray.length);
        boolean z = false;
        StringBuffer stringBuffer = null;
        a aVar = null;
        for (char c2 : charArray) {
            if (!z || c2 == '\'') {
                if (c2 != '\'') {
                    if (c2 == 'H') {
                        obj = d;
                    } else if (c2 == 'M') {
                        obj = b;
                    } else if (c2 == 'S') {
                        obj = g;
                    } else if (c2 == 'd') {
                        obj = c;
                    } else if (c2 == 'm') {
                        obj = e;
                    } else if (c2 == 's') {
                        obj = f;
                    } else if (c2 != 'y') {
                        if (stringBuffer == null) {
                            stringBuffer = new StringBuffer();
                            arrayList.add(new a(stringBuffer));
                        }
                        stringBuffer.append(c2);
                        obj = null;
                    } else {
                        obj = a;
                    }
                } else if (z) {
                    z = false;
                    stringBuffer = null;
                    obj = null;
                } else {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    arrayList.add(new a(stringBuffer2));
                    z = true;
                    obj = null;
                    stringBuffer = stringBuffer2;
                }
                if (obj != null) {
                    if (aVar == null || aVar.c() != obj) {
                        aVar = new a(obj);
                        arrayList.add(aVar);
                    } else {
                        aVar.a();
                    }
                    stringBuffer = null;
                }
            } else {
                stringBuffer.append(c2);
            }
        }
        return (a[]) arrayList.toArray(new a[arrayList.size()]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class a {
        private Object a;
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
            if (obj2 instanceof StringBuffer) {
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
            return org.apache.commons.lang.StringUtils.repeat(this.a.toString(), this.b);
        }
    }
}
