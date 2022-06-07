package org.seamless.util.time;

import java.io.Serializable;
import java.util.Date;
import org.fourthline.cling.model.Constants;

/* loaded from: classes4.dex */
public class DateRange implements Serializable {
    protected Date end;
    protected Date start;

    /* loaded from: classes4.dex */
    public enum Preset {
        ALL(new DateRange(null)),
        YEAR_TO_DATE(new DateRange(new Date(DateRange.getCurrentYear(), 0, 1))),
        MONTH_TO_DATE(new DateRange(new Date(DateRange.getCurrentYear(), DateRange.getCurrentMonth(), 1))),
        LAST_MONTH(DateRange.getMonthOf(new Date(DateRange.getCurrentYear(), DateRange.getCurrentMonth() - 1, 1))),
        LAST_YEAR(new DateRange(new Date(DateRange.getCurrentYear() - 1, 0, 1), new Date(DateRange.getCurrentYear() - 1, 11, 31)));
        
        DateRange dateRange;

        Preset(DateRange dateRange) {
            this.dateRange = dateRange;
        }

        public DateRange getDateRange() {
            return this.dateRange;
        }
    }

    public DateRange() {
    }

    public DateRange(Date date) {
        this.start = date;
    }

    public DateRange(Date date, Date date2) {
        this.start = date;
        this.end = date2;
    }

    public DateRange(String str, String str2) throws NumberFormatException {
        if (str != null) {
            this.start = new Date(Long.valueOf(str).longValue());
        }
        if (str2 != null) {
            this.end = new Date(Long.valueOf(str2).longValue());
        }
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public boolean isStartAfter(Date date) {
        return getStart() != null && getStart().getTime() > date.getTime();
    }

    public Date getOneDayBeforeStart() {
        if (getStart() != null) {
            return new Date(getStart().getTime() - 86400000);
        }
        throw new IllegalStateException("Can't get day before start date because start date is null");
    }

    public static int getCurrentYear() {
        return new Date().getYear();
    }

    public static int getCurrentMonth() {
        return new Date().getMonth();
    }

    public static int getCurrentDayOfMonth() {
        return new Date().getDate();
    }

    public boolean hasStartOrEnd() {
        return (getStart() == null && getEnd() == null) ? false : true;
    }

    public static int getDaysInMonth(Date date) {
        int month = date.getMonth();
        int year = date.getYear() + Constants.UPNP_MULTICAST_PORT;
        boolean z = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
        int[] iArr = new int[12];
        iArr[0] = 31;
        iArr[1] = z ? 29 : 28;
        iArr[2] = 31;
        iArr[3] = 30;
        iArr[4] = 31;
        iArr[5] = 30;
        iArr[6] = 31;
        iArr[7] = 31;
        iArr[8] = 30;
        iArr[9] = 31;
        iArr[10] = 30;
        iArr[11] = 31;
        return iArr[month];
    }

    public static DateRange getMonthOf(Date date) {
        return new DateRange(new Date(date.getYear(), date.getMonth(), 1), new Date(date.getYear(), date.getMonth(), getDaysInMonth(date)));
    }

    public boolean isInRange(Date date) {
        return getStart() != null && getStart().getTime() < date.getTime() && (getEnd() == null || getEnd().getTime() > date.getTime());
    }

    public boolean isValid() {
        return getStart() != null && (getEnd() == null || getStart().getTime() <= getEnd().getTime());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DateRange dateRange = (DateRange) obj;
        Date date = this.end;
        if (date == null ? dateRange.end != null : !date.equals(dateRange.end)) {
            return false;
        }
        Date date2 = this.start;
        return date2 == null ? dateRange.start == null : date2.equals(dateRange.start);
    }

    public int hashCode() {
        Date date = this.start;
        int i = 0;
        int hashCode = (date != null ? date.hashCode() : 0) * 31;
        Date date2 = this.end;
        if (date2 != null) {
            i = date2.hashCode();
        }
        return hashCode + i;
    }

    public static DateRange valueOf(String str) {
        if (!str.contains("dr=")) {
            return null;
        }
        String substring = str.substring(str.indexOf("dr=") + 3);
        String[] split = substring.substring(0, substring.indexOf(";")).split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length != 2) {
            return null;
        }
        try {
            return new DateRange(!split[0].equals("0") ? new Date(Long.valueOf(split[0]).longValue()) : null, !split[1].equals("0") ? new Date(Long.valueOf(split[1]).longValue()) : null);
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("dr=");
        sb.append(getStart() != null ? Long.valueOf(getStart().getTime()) : "0");
        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(getEnd() != null ? Long.valueOf(getEnd().getTime()) : "0");
        sb.append(";");
        return sb.toString();
    }
}
