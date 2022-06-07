package com.xiaomi.micolauncher.skills.weather.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.weather.WeatherLocationHintActivity;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes3.dex */
public class OnedayWeatherView extends RelativeLayout {
    TextView a;
    View b;
    TextView c;
    TextView d;
    TextView e;
    TextView f;
    TextView g;
    ImageView h;
    private String i;

    public OnedayWeatherView(Context context) {
        super(context);
        a(context);
    }

    public OnedayWeatherView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public OnedayWeatherView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_weather_one_day, this);
        this.a = (TextView) findViewById(R.id.weather_location_date);
        this.b = findViewById(R.id.weather_temperature);
        this.c = (TextView) findViewById(R.id.weather_temperature_down);
        this.d = (TextView) findViewById(R.id.weather_temperature_up);
        this.e = (TextView) findViewById(R.id.weather_weather);
        this.f = (TextView) findViewById(R.id.weather_wind);
        this.g = (TextView) findViewById(R.id.weather_aqi);
        this.h = (ImageView) findViewById(R.id.change_weather);
        this.i = getContext().getString(R.string.weather_temperature_unit);
    }

    public void refreshView(WeatherToday weatherToday) {
        this.a.setText(a(weatherToday));
        if (weatherToday.weather != null) {
            this.e.setText(weatherToday.weather.strId);
        }
        if (weatherToday.dayTemp != null) {
            setDownTemprature(weatherToday.dayTemp.getSmallValue());
            setUpTemprature(weatherToday.dayTemp.getBigValue());
        }
        this.f.setText(MiWeather.getWindStr(getContext(), weatherToday));
        if (weatherToday.aqi != null) {
            this.g.setText(weatherToday.aqi.strId);
        }
        this.h.setVisibility(weatherToday.hasMentionedLocation ? 8 : 0);
    }

    private void setDownTemprature(String str) {
        this.b.setVisibility(0);
        this.c.setText(str);
    }

    private void setUpTemprature(String str) {
        this.b.setVisibility(0);
        TextView textView = this.d;
        textView.setText(str + this.i);
    }

    private String a(WeatherToday weatherToday) {
        return String.format("%s  %s", TextUtils.isEmpty(weatherToday.city) ? "" : weatherToday.city, getDateStr(TextUtils.isEmpty(weatherToday.date) ? "" : weatherToday.date));
    }

    public String getDateStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            String[] stringArray = getResources().getStringArray(R.array.weekdays);
            Date parse = new SimpleDateFormat("yyyyMMdd").parse(str);
            String format = new SimpleDateFormat("MM/dd").format(parse);
            int dayOfWeek = TimeCalculator.dayOfWeek(parse);
            if (dayOfWeek < 0) {
                dayOfWeek = 0;
            }
            return format + "  " + stringArray[dayOfWeek];
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void onViewClicked() {
        getContext().startActivity(new Intent(getContext(), WeatherLocationHintActivity.class));
    }
}
