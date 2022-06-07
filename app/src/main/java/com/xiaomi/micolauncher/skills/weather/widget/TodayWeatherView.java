package com.xiaomi.micolauncher.skills.weather.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.weather.WeatherLocationHintActivity;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;

/* loaded from: classes3.dex */
public class TodayWeatherView extends RelativeLayout {
    ImageView a;
    TextView b;
    TextView c;
    TextView d;
    TextView e;
    TextView f;
    TextView g;
    private String h;

    public TodayWeatherView(Context context) {
        super(context);
        a(context);
    }

    public TodayWeatherView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public TodayWeatherView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_weather_today, this);
        this.h = getContext().getString(R.string.weather_temperature_unit);
        this.a = (ImageView) findViewById(R.id.change_weather);
        this.b = (TextView) findViewById(R.id.weather_location_date);
        this.c = (TextView) findViewById(R.id.weather_temperature);
        this.d = (TextView) findViewById(R.id.weather_weather);
        this.e = (TextView) findViewById(R.id.weather_temp);
        this.f = (TextView) findViewById(R.id.weather_wind);
        this.g = (TextView) findViewById(R.id.weather_aqi);
    }

    public void refreshView(WeatherToday weatherToday) {
        if (weatherToday != null) {
            this.b.setText(getLocationAndDateStr(weatherToday));
            if (weatherToday.weather != null) {
                this.d.setText(weatherToday.weather.strId);
            }
            setCurrentTemprature(weatherToday.currentTemp);
            this.e.setText(getTempStr(weatherToday));
            this.f.setText(MiWeather.getWindStr(getContext(), weatherToday));
            if (weatherToday.aqi != null) {
                this.g.setText(weatherToday.aqi.strId);
            }
            this.a.setVisibility(weatherToday.hasMentionedLocation ? 8 : 0);
        }
    }

    private void setCurrentTemprature(String str) {
        if (!TextUtils.isEmpty(str)) {
            TextView textView = this.c;
            textView.setText(str + this.h);
        }
    }

    public String getTempStr(WeatherToday weatherToday) {
        if (weatherToday.dayTemp == null) {
            return "";
        }
        return weatherToday.dayTemp.getSmallValue() + "/" + weatherToday.dayTemp.getBigValue() + this.h;
    }

    public String getLocationAndDateStr(WeatherToday weatherToday) {
        return (TextUtils.isEmpty(weatherToday.city) || "null".equalsIgnoreCase(weatherToday.city)) ? getResources().getString(R.string.weather_day_today) : String.format(getResources().getString(R.string.weather_location_today), weatherToday.city);
    }

    public void onViewClicked() {
        getContext().startActivity(new Intent(getContext(), WeatherLocationHintActivity.class));
    }
}
