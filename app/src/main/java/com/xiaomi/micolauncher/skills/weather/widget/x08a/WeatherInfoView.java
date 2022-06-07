package com.xiaomi.micolauncher.skills.weather.widget.x08a;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.Weather;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes3.dex */
public class WeatherInfoView extends RelativeLayout {
    TextView a;
    TextView b;
    TextView c;
    TextView d;

    public WeatherInfoView(Context context) {
        super(context);
        a(context);
    }

    public WeatherInfoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public WeatherInfoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_weather_location_date_layout, this);
        this.a = (TextView) findViewById(R.id.weather_location);
        this.b = (TextView) findViewById(R.id.weather_date);
        this.c = (TextView) findViewById(R.id.weather_wind_aqi);
        this.d = (TextView) findViewById(R.id.weather_weather);
    }

    public void refreshView(MiWeather miWeather) {
        if (miWeather != null) {
            this.a.setText(a(miWeather.getLocation()));
            if (miWeather.isTodayWeather()) {
                this.b.setVisibility(8);
                WeatherToday currentWeatherInfoV2 = miWeather.getCurrentWeatherInfoV2();
                this.c.setText(String.format("%s %s", getCurrentWindStr(currentWeatherInfoV2), a(currentWeatherInfoV2)));
                this.c.setVisibility(0);
                setWeather(currentWeatherInfoV2.weather);
            } else if (miWeather.isOneDayWeather()) {
                WeatherToday dateWeatherInfoV2 = miWeather.getDateWeatherInfoV2(miWeather.getDate().dateStr);
                this.b.setText(getDateStr(miWeather.getDate().date));
                this.b.setVisibility(0);
                this.c.setText(String.format("%s %s", MiWeather.getWindStr(getContext(), dateWeatherInfoV2.windSpeed), a(dateWeatherInfoV2)));
                this.c.setVisibility(0);
                setWeather(dateWeatherInfoV2.weather);
            } else if (miWeather.isDurationWeather()) {
                this.c.setVisibility(8);
                this.d.setVisibility(8);
                this.b.setVisibility(8);
            }
        }
    }

    private void setWeather(Weather weather) {
        if (weather != null) {
            this.d.setText(weather.strId);
            this.d.setVisibility(0);
        }
    }

    private String a(WeatherToday weatherToday) {
        if (weatherToday.aqi != null) {
            return getResources().getString(weatherToday.aqi.strId);
        }
        return "";
    }

    public String getCurrentWindStr(WeatherToday weatherToday) {
        return MiWeather.getWindStr(getContext(), weatherToday);
    }

    private String a(MiWeather.Location location) {
        return TextUtils.isEmpty(location.city) ? "" : location.city;
    }

    public String getDateStr(Date date) {
        if (date == null) {
            return "";
        }
        try {
            String[] stringArray = getResources().getStringArray(R.array.weekdays);
            String format = new SimpleDateFormat("MM/dd").format(date);
            int dayOfWeek = TimeCalculator.dayOfWeek(date);
            if (dayOfWeek < 0) {
                dayOfWeek = 0;
            }
            return format + "  " + stringArray[dayOfWeek];
        } catch (Resources.NotFoundException e) {
            L.weather.e("getDateStr : resource not found of weekdays", e);
            return "";
        }
    }

    public void onTtsEnd() {
        a();
    }

    private void a() {
        if (this.d.getVisibility() == 0) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.75f, 1.0f, 0.75f, 0.0f, 0.0f);
            scaleAnimation.setDuration(600L);
            scaleAnimation.setFillAfter(true);
            this.d.startAnimation(scaleAnimation);
        }
    }
}
