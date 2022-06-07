package com.xiaomi.micolauncher.skills.alarm.model.bean;

import android.text.TextUtils;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.ubus.handlers.AlarmHandler;
import com.xiaomi.micolauncher.common.utils.DateUtil;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmEntity;
import com.xiaomi.micolauncher.skills.alarm.model.MicoDate;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.util.internal.StringUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class AlarmRealmObjectBean {
    private static final long a = TimeUnit.MINUTES.toMillis(2);
    private int b;
    private String c;
    private String d;
    private long e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private int n;
    private String o;
    private String p;
    private String q;
    private String r;
    private long s;
    private String t;
    private long u;
    private long v;
    private boolean w;

    public static String getCircleString(int i) {
        switch (i) {
            case 0:
                return Circle.ONCE;
            case 1:
                return Circle.EVERYDAY;
            case 2:
                return Circle.WORKDAY;
            case 3:
                return Circle.MONTOFRI;
            case 4:
                return Circle.HOLIDAY;
            case 5:
                return Circle.WEEKEND;
            case 6:
                return Circle.EVERYWEEK;
            default:
                return Circle.ONCE;
        }
    }

    public AlarmRealmObjectBean() {
    }

    public AlarmRealmObjectBean(AlarmRealmObject alarmRealmObject) {
        this(alarmRealmObject.getId(), alarmRealmObject.getIdStr(), alarmRealmObject.getAlarmType(), alarmRealmObject.getTimestamp(), alarmRealmObject.getReminder(), alarmRealmObject.getPosition(), alarmRealmObject.getCircle(), alarmRealmObject.getCircleExtra(), alarmRealmObject.getStatus(), alarmRealmObject.getEvent(), alarmRealmObject.getRingtone(), alarmRealmObject.getTimeReminder(), alarmRealmObject.getVolume());
    }

    public AlarmRealmObjectBean(int i, String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2) {
        this(i, str, str2, j, str3, str4, str5, str6, str7, str8, str9, str10, i2, "", "", "", "");
    }

    public AlarmRealmObjectBean(int i, String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2, String str11, String str12, String str13, String str14) {
        this.b = i;
        this.c = str;
        this.d = str2;
        this.e = j;
        this.j = str3;
        this.k = str4;
        this.f = str5;
        this.g = str6;
        this.h = str7;
        this.i = str8;
        this.l = str9;
        this.m = str10;
        this.n = i2;
        this.o = str11;
        this.p = str12;
        this.q = str13;
        this.r = str14;
        this.s = System.currentTimeMillis();
        resetLocalTimeStamp();
        this.w = false;
    }

    public AlarmRealmObjectBean(AlarmEntity alarmEntity) {
        this.b = alarmEntity.idInt;
        this.c = alarmEntity.getIdentify();
        this.d = alarmEntity.type;
        if (alarmEntity.timestamp == 0) {
            this.e = alarmEntity.timestampMillis;
        } else {
            this.e = alarmEntity.getTimestampMillisTimeZone();
        }
        this.j = alarmEntity.reminder;
        this.k = alarmEntity.position;
        this.f = alarmEntity.getCircleString();
        this.g = alarmEntity.getCircleExtra();
        this.h = alarmEntity.getStatus();
        this.i = alarmEntity.event;
        this.l = alarmEntity.ringtoneQuery;
        this.m = alarmEntity.timeReminder;
        this.n = alarmEntity.volume;
        this.o = alarmEntity.ringtoneType;
        this.p = alarmEntity.displayTxt;
        this.q = alarmEntity.ringtoneVideo;
        this.r = alarmEntity.ringtoneVideoImage;
        if (!TextUtils.isEmpty(alarmEntity.dateTime)) {
            this.t = alarmEntity.dateTime;
        } else if (!TextUtils.isEmpty(alarmEntity.offset)) {
            this.t = alarmEntity.offset;
        }
        this.s = System.currentTimeMillis();
        resetLocalTimeStamp();
        this.w = false;
    }

    public int getId() {
        return this.b;
    }

    public void setId(int i) {
        this.b = i;
    }

    public String getIdStr() {
        return this.c;
    }

    public String getAlarmType() {
        return this.d;
    }

    public void setAlarmType(String str) {
        this.d = str;
    }

    public long getTimestamp() {
        return this.e;
    }

    public String getTimeStr() {
        return DateUtil.translateDate(new Date(getAlarmTimestampWithTimeZone()));
    }

    public long getAlarmTimestampWithTimeZone() {
        return this.e + TimeUtils.getTimeZoneOffsetMillis();
    }

    public void setTimestamp(long j) {
        this.e = j;
    }

    public long getLocalTimestamp() {
        return this.u;
    }

    public Date getLocalDate() {
        Date date = new Date();
        date.setTime(this.u);
        return date;
    }

    public String getReminder() {
        return this.j;
    }

    public void setReminder(String str) {
        this.j = str;
    }

    public String getPosition() {
        return this.k;
    }

    public String getCircle() {
        return this.f;
    }

    public void setCircle(String str) {
        this.f = str;
    }

    public void setCircleExtra(String str) {
        this.g = str;
    }

    public String getCircleExtra() {
        return this.g;
    }

    public String getStatus() {
        return this.h;
    }

    public void setStatus(String str) {
        this.h = str;
    }

    public String getEvent() {
        return this.i;
    }

    public void setEvent(String str) {
        this.i = str;
    }

    public String getRingtone() {
        return this.l;
    }

    public void setRingtone(String str) {
        this.l = str;
    }

    public String getRingtoneType() {
        return this.o;
    }

    public void setRingtoneType(String str) {
        this.o = str;
    }

    public String getDisplayTxt() {
        return this.p;
    }

    public void setDisplayTxt(String str) {
        this.p = str;
    }

    public String getRingtoneVideo() {
        return this.q;
    }

    public void setRingtoneVideo(String str) {
        this.q = str;
    }

    public String getRingtoneVideoImage() {
        return this.r;
    }

    public void setRingtoneVideoImage(String str) {
        this.r = str;
    }

    public String getTimeReminder() {
        return this.m;
    }

    public void setTimeReminder(String str) {
        this.m = str;
    }

    public long getBoomMilliSecondsFromMidnight() {
        return this.v;
    }

    public boolean expireInDay() {
        return this.v < TimeUtils.getMilliSecondsFromMidnight();
    }

    public String getDateTime() {
        return this.t;
    }

    public void resetLocalTimeStamp() {
        if ("TIMER".equalsIgnoreCase(this.d)) {
            this.u = this.e;
        } else {
            this.u = this.e + TimeUtils.getTimeZoneOffsetMillis();
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(this.u);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        this.v = this.u - instance.getTimeInMillis();
    }

    public boolean isBoom() {
        return this.w;
    }

    public void setBoom(boolean z) {
        this.w = z;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean isInDay(long j) {
        char c;
        MicoDate micoDate = new MicoDate(j);
        int year = micoDate.getYear();
        int month = micoDate.getMonth();
        int date = micoDate.getDate();
        int day = micoDate.getDay();
        MicoDate micoDate2 = new MicoDate(this.u);
        int year2 = micoDate2.getYear();
        int month2 = micoDate2.getMonth();
        int date2 = micoDate2.getDate();
        String str = this.f;
        boolean z = false;
        switch (str.hashCode()) {
            case -734561654:
                if (str.equals(Circle.YEARLY)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -318797514:
                if (str.equals(Circle.MONTOFRI)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 3415681:
                if (str.equals(Circle.ONCE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 151588239:
                if (str.equals(Circle.EVERYWEEK)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 281966241:
                if (str.equals(Circle.EVERYDAY)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1091905624:
                if (str.equals(Circle.HOLIDAY)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1226863719:
                if (str.equals(Circle.WEEKEND)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1236635661:
                if (str.equals(Circle.MONTHLY)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1525159659:
                if (str.equals(Circle.WORKDAY)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (year == year2 && month == month2 && date == date2) {
                    return true;
                }
                break;
            case 1:
                return true;
            case 2:
                HashMap a2 = a();
                if (a2 != null) {
                    if (day == 0) {
                        day = 7;
                    }
                    if (a2.containsKey(String.valueOf(day))) {
                        z = true;
                    }
                }
                return z;
            case 3:
                if (date == date2) {
                    return true;
                }
                break;
            case 4:
                if (month == month2 && date == date2) {
                    return true;
                }
                break;
            case 5:
                if (micoDate.isWorkday()) {
                    return true;
                }
                break;
            case 6:
                if (micoDate.isHoliday()) {
                    return true;
                }
                break;
            case 7:
                if (micoDate.isWeekday()) {
                    return true;
                }
                break;
            case '\b':
                if (micoDate.isWeekend()) {
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean checkBoom(long j) {
        if (isBoom()) {
            return false;
        }
        String str = this.h;
        if ((str == null || str.equals("on")) && isInDay(System.currentTimeMillis())) {
            long boomMilliSecondsFromMidnight = getBoomMilliSecondsFromMidnight();
            long j2 = j - boomMilliSecondsFromMidnight;
            if (j > boomMilliSecondsFromMidnight && j2 < a) {
                L.alarm.d("checkBoom alarmId=%d,isBoom=%b,interval=%d", Integer.valueOf(this.b), Boolean.valueOf(this.w), Long.valueOf(j2));
                return true;
            }
        }
        return false;
    }

    private HashMap a() {
        String str = this.g;
        if (str != null) {
            String[] split = str.split(StringUtils.SPACE);
            if (split.length > 5) {
                HashMap hashMap = new HashMap(12);
                String[] split2 = split[5].split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                if (split2.length <= 0) {
                    return hashMap;
                }
                for (String str2 : split2) {
                    hashMap.put(str2, str2);
                }
                return hashMap;
            }
        }
        return null;
    }

    public int getVolume() {
        return this.n;
    }

    public void setVolume(int i) {
        this.n = i;
    }

    public static int getCircleValue(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            return 0;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -734561654:
                if (str.equals(Circle.YEARLY)) {
                    c = '\b';
                    break;
                }
                break;
            case -318797514:
                if (str.equals(Circle.MONTOFRI)) {
                    c = 3;
                    break;
                }
                break;
            case 3415681:
                if (str.equals(Circle.ONCE)) {
                    c = 0;
                    break;
                }
                break;
            case 151588239:
                if (str.equals(Circle.EVERYWEEK)) {
                    c = 6;
                    break;
                }
                break;
            case 281966241:
                if (str.equals(Circle.EVERYDAY)) {
                    c = 1;
                    break;
                }
                break;
            case 1091905624:
                if (str.equals(Circle.HOLIDAY)) {
                    c = 4;
                    break;
                }
                break;
            case 1226863719:
                if (str.equals(Circle.WEEKEND)) {
                    c = 5;
                    break;
                }
                break;
            case 1236635661:
                if (str.equals(Circle.MONTHLY)) {
                    c = 7;
                    break;
                }
                break;
            case 1525159659:
                if (str.equals(Circle.WORKDAY)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case '\b':
                return 8;
            default:
                return 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getCircleCn(String str) {
        char c;
        switch (str.hashCode()) {
            case -734561654:
                if (str.equals(Circle.YEARLY)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -318797514:
                if (str.equals(Circle.MONTOFRI)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3415681:
                if (str.equals(Circle.ONCE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 151588239:
                if (str.equals(Circle.EVERYWEEK)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 281966241:
                if (str.equals(Circle.EVERYDAY)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1091905624:
                if (str.equals(Circle.HOLIDAY)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1226863719:
                if (str.equals(Circle.WEEKEND)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1236635661:
                if (str.equals(Circle.MONTHLY)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1525159659:
                if (str.equals(Circle.WORKDAY)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "只响一次";
            case 1:
                return "每天";
            case 2:
                return "工作日";
            case 3:
                return "周一到周五";
            case 4:
                return "节假日";
            case 5:
                return "周末";
            case 6:
                return "每周";
            case 7:
                return "每月";
            case '\b':
                return "每年";
            default:
                return str;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getCircleExtraCn(String str) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            String[] split = str.split(StringUtils.SPACE);
            if (split.length > 5) {
                String[] split2 = split[5].split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                if (split2.length > 0) {
                    for (String str2 : split2) {
                        String str3 = "";
                        char c = 65535;
                        switch (str2.hashCode()) {
                            case 49:
                                if (str2.equals("1")) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case 50:
                                if (str2.equals("2")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 51:
                                if (str2.equals("3")) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case 52:
                                if (str2.equals(Commands.ResolutionValues.BITSTREAM_BLUE_HIGH)) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case 53:
                                if (str2.equals(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND)) {
                                    c = 4;
                                    break;
                                }
                                break;
                            case 54:
                                if (str2.equals("6")) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case 55:
                                if (str2.equals("7")) {
                                    c = 6;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                str3 = "周一";
                                break;
                            case 1:
                                str3 = "周二";
                                break;
                            case 2:
                                str3 = "周三";
                                break;
                            case 3:
                                str3 = "周四";
                                break;
                            case 4:
                                str3 = "周五";
                                break;
                            case 5:
                                str3 = "周六";
                                break;
                            case 6:
                                str3 = "周日";
                                break;
                        }
                        if (sb.length() == 0) {
                            sb.append(str3);
                        } else {
                            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                            sb.append(str3);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String convertCircleExtraFromBrainToApp(String str) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            String[] split = str.split(StringUtils.SPACE);
            if (split.length > 5) {
                String[] split2 = split[5].split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                if (split2.length > 0) {
                    for (String str2 : split2) {
                        String str3 = "";
                        char c = 65535;
                        switch (str2.hashCode()) {
                            case 49:
                                if (str2.equals("1")) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case 50:
                                if (str2.equals("2")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 51:
                                if (str2.equals("3")) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case 52:
                                if (str2.equals(Commands.ResolutionValues.BITSTREAM_BLUE_HIGH)) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case 53:
                                if (str2.equals(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND)) {
                                    c = 4;
                                    break;
                                }
                                break;
                            case 54:
                                if (str2.equals("6")) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case 55:
                                if (str2.equals("7")) {
                                    c = 6;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                str3 = "mon";
                                break;
                            case 1:
                                str3 = "tue";
                                break;
                            case 2:
                                str3 = "wed";
                                break;
                            case 3:
                                str3 = "thu";
                                break;
                            case 4:
                                str3 = "fri";
                                break;
                            case 5:
                                str3 = "sat";
                                break;
                            case 6:
                                str3 = "sun";
                                break;
                        }
                        if (sb.length() == 0) {
                            sb.append(str3);
                        } else {
                            sb.append(StringUtils.SPACE);
                            sb.append(str3);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String convertCircleExtraFromAppToBrain(String str) {
        int i;
        StringBuilder sb = new StringBuilder();
        if (str != null && str.length() > 0) {
            String[] split = str.split(StringUtils.SPACE);
            if (split.length > 0) {
                sb.append("* * * * * ");
                StringBuilder sb2 = new StringBuilder();
                for (String str2 : split) {
                    char c = 65535;
                    switch (str2.hashCode()) {
                        case 101661:
                            if (str2.equals("fri")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 108300:
                            if (str2.equals("mon")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 113638:
                            if (str2.equals("sat")) {
                                c = 5;
                                break;
                            }
                            break;
                        case 114252:
                            if (str2.equals("sun")) {
                                c = 6;
                                break;
                            }
                            break;
                        case 114817:
                            if (str2.equals("thu")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 115204:
                            if (str2.equals("tue")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 117590:
                            if (str2.equals("wed")) {
                                c = 2;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            i = 1;
                            break;
                        case 1:
                            i = 2;
                            break;
                        case 2:
                            i = 3;
                            break;
                        case 3:
                            i = 4;
                            break;
                        case 4:
                            i = 5;
                            break;
                        case 5:
                            i = 6;
                            break;
                        case 6:
                            i = 7;
                            break;
                        default:
                            i = 0;
                            break;
                    }
                    if (i != 0) {
                        if (sb2.length() == 0) {
                            sb2.append(i);
                        } else {
                            sb2.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                            sb2.append(i);
                        }
                    }
                }
                sb.append((CharSequence) sb2);
                sb.append(" *");
            }
        }
        return sb.toString();
    }

    public boolean isBabyCourse() {
        return AlarmHandler.TYPE_BABY_COURSE.equals(this.d);
    }

    public long getUpdateTimestamp() {
        long j = this.s;
        return j > 0 ? j : this.e;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AlarmRealmObjectBean alarmRealmObjectBean = (AlarmRealmObjectBean) obj;
        return this.e == alarmRealmObjectBean.e && this.n == alarmRealmObjectBean.n && Objects.equals(this.d, alarmRealmObjectBean.d) && Objects.equals(this.f, alarmRealmObjectBean.f) && Objects.equals(this.g, alarmRealmObjectBean.g) && Objects.equals(this.h, alarmRealmObjectBean.h) && Objects.equals(this.i, alarmRealmObjectBean.i) && Objects.equals(this.j, alarmRealmObjectBean.j) && Objects.equals(this.k, alarmRealmObjectBean.k) && Objects.equals(this.l, alarmRealmObjectBean.l) && Objects.equals(this.m, alarmRealmObjectBean.m) && Objects.equals(this.o, alarmRealmObjectBean.o) && Objects.equals(this.p, alarmRealmObjectBean.p) && Objects.equals(this.q, alarmRealmObjectBean.q) && Objects.equals(this.r, alarmRealmObjectBean.r);
    }

    public String toString() {
        return "id:" + this.b + " alarmType:" + this.d + " timestamp:" + this.e + " localTimestamp:" + this.u + " circle:" + this.f + " circleExtra:" + this.g + " status:" + this.h + " event:" + this.i + " reminder:" + this.j + " timeReminder:" + this.m + " position:" + this.k + " ringtoneType:" + this.o + " boom:" + this.w + " ringtone:" + this.l;
    }
}
