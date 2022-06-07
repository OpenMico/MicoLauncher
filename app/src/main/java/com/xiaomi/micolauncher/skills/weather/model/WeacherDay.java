package com.xiaomi.micolauncher.skills.weather.model;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.utils.DateUtil;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes3.dex */
public class WeacherDay {
    public MiWeather.AQI aqi;
    private final Calendar calendar = Calendar.getInstance();
    public boolean care;
    public String date;
    public MiWeather.WTrans dayTemp;
    public Weather weaterFrom;
    public Weather weaterTo;
    public String windDirection;
    public String windSpeed;

    public Weather getDisplayWeather() {
        return this.weaterFrom.priority > this.weaterTo.priority ? this.weaterFrom : this.weaterTo;
    }

    public String getDateMonthDay() {
        Date strToDate;
        if (TextUtils.isEmpty(this.date) || (strToDate = DateUtil.strToDate(this.date)) == null) {
            return "";
        }
        this.calendar.setTime(strToDate);
        return String.format("%s.%s", Integer.valueOf(this.calendar.get(2) + 1), Integer.valueOf(this.calendar.get(5)));
    }
}
