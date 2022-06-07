package com.xiaomi.micolauncher.skills.weather.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import com.xiaomi.mipush.sdk.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/* loaded from: classes3.dex */
public class WeatherCell extends RelativeLayout {
    TextView a;
    ImageView b;
    private final String c = XMPassport.SIMPLE_DATE_FORMAT;
    private final String d = "yyyyMMdd";
    private String e;

    public WeatherCell(Context context) {
        super(context);
        a(context);
    }

    public WeatherCell(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public WeatherCell(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_weather_cell_v2, this);
        this.a = (TextView) findViewById(R.id.weather_date);
        this.b = (ImageView) findViewById(R.id.weather_state);
    }

    public void refreshView(WeacherDay weacherDay) {
        if (weacherDay != null) {
            this.a.setText(transDateToDay(weacherDay.date));
            if (weacherDay.care) {
                this.a.setTypeface(Typeface.defaultFromStyle(1));
            }
            GlideUtils.bindImageView(getContext(), weacherDay.getDisplayWeather().drawId, this.b);
            if (weacherDay.getDisplayWeather().drawId == 0) {
                L.base.w("weather has no icon, %s", Gsons.getGson().toJson(weacherDay));
            }
        }
    }

    public void setWidth(int i) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = i;
        setLayoutParams(layoutParams);
    }

    public int getWeatherStateY() {
        Rect rect = new Rect();
        this.b.getGlobalVisibleRect(rect);
        return rect.bottom;
    }

    public String transDateToDay(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        this.e = str.contains(Constants.ACCEPT_TIME_SEPARATOR_SERVER) ? XMPassport.SIMPLE_DATE_FORMAT : "yyyyMMdd";
        if (TimeCalculator.isToday(this.e, str)) {
            return getResources().getString(R.string.weather_today);
        }
        if (TimeCalculator.isTomorrow(this.e, str)) {
            return getResources().getString(R.string.weather_tomorrow);
        }
        if (TimeCalculator.isTheDayAfterTomorrow(this.e, str)) {
            return getResources().getString(R.string.weather_after_tomorrow);
        }
        try {
            Date parse = new SimpleDateFormat(this.e).parse(str);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(parse);
            return String.valueOf(gregorianCalendar.get(5));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
