package com.xiaomi.micolauncher.skills.weather.widget.x08a;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;
import java.util.List;

/* loaded from: classes3.dex */
public class WeatherTemperatureView extends RelativeLayout {
    View a;
    TextView b;
    TextView c;
    TextView d;
    View e;
    View f;
    View g;
    TextView h;
    private String i = "";
    private MiWeather.WeatherType j = MiWeather.WeatherType.TODAY;

    public WeatherTemperatureView(Context context) {
        super(context);
        a(context);
    }

    public WeatherTemperatureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public WeatherTemperatureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_weather_temperature_layout, this);
        this.a = findViewById(R.id.layout_today_temperature);
        this.b = (TextView) findViewById(R.id.weather_current_temperature);
        this.c = (TextView) findViewById(R.id.weather_temperature_up);
        this.d = (TextView) findViewById(R.id.weather_temperature_down);
        this.e = findViewById(R.id.weather_today_temperature_layout);
        this.f = findViewById(R.id.layout_temperature);
        this.g = findViewById(R.id.weather_down_up_temperature);
        this.h = (TextView) findViewById(R.id.weather_up_down_temperature);
        this.i = context.getString(R.string.weather_temperature_unit);
    }

    public void refreshView(MiWeather miWeather) {
        L.weather.d("%s refreshView , miWeather is %s ", "WeatherTemperatureView", Gsons.getGson().toJson(miWeather));
        if (miWeather != null) {
            if (miWeather.isTodayWeather()) {
                L.weather.d("%s refreshView , is isTodayWeather", "WeatherTemperatureView");
                this.j = MiWeather.WeatherType.TODAY;
                WeatherToday currentWeatherInfoV2 = miWeather.getCurrentWeatherInfoV2();
                setCurrentTemperature(currentWeatherInfoV2.currentTemp);
                this.d.setText(a(currentWeatherInfoV2));
                this.c.setText(b(currentWeatherInfoV2));
                setLayout(this.j);
            } else if (miWeather.isOneDayWeather()) {
                L.weather.d("%s refreshView ,  isOneDayWeather ", "WeatherTemperatureView");
                this.j = MiWeather.WeatherType.ONE_DAY;
                WeatherToday dateWeatherInfoV2 = miWeather.getDateWeatherInfoV2(miWeather.getDate().dateStr);
                if (dateWeatherInfoV2.dayTemp != null) {
                    String c = c(dateWeatherInfoV2);
                    L.weather.d("%s refreshView , tempStr is %s", "WeatherTemperatureView", c);
                    this.h.setText(c);
                }
                setLayout(this.j);
            } else if (miWeather.isDurationWeather()) {
                L.weather.d("%s refreshView ,  isDurationWeather", "WeatherTemperatureView");
                this.j = MiWeather.WeatherType.DURATION;
                a(miWeather);
                setLayout(this.j);
            }
        }
    }

    private void a(MiWeather miWeather) {
        a(miWeather.duration.startIndexDaily, miWeather.duration.endIndexDaily, miWeather.getMuitlDaysUpTemp(15), miWeather.getMuitlDaysDownTemp(15));
    }

    private void a(int i, int i2, List<Integer> list, List<Integer> list2) {
        if (i >= 0 && i < 15 && i2 >= 0 && i2 < 15 && i < list.size() && i2 < list.size() && i < list2.size() && i2 < list2.size()) {
            int intValue = list.get(i).intValue();
            int intValue2 = list2.get(i).intValue();
            while (i <= i2) {
                int intValue3 = list.get(i).intValue();
                int intValue4 = list2.get(i).intValue();
                if (intValue3 > intValue) {
                    intValue = intValue3;
                }
                if (intValue4 < intValue2) {
                    intValue2 = intValue4;
                }
                i++;
            }
            a(intValue2, intValue);
        }
    }

    private void a(int i, int i2) {
        this.h.setText(b(i, i2));
    }

    private String b(int i, int i2) {
        return i + "/" + i2 + this.i;
    }

    private void setLayout(MiWeather.WeatherType weatherType) {
        switch (weatherType) {
            case TODAY:
                this.a.setVisibility(0);
                this.f.setVisibility(8);
                return;
            case ONE_DAY:
            case DURATION:
                this.f.setVisibility(0);
                this.a.setVisibility(8);
                return;
            default:
                return;
        }
    }

    private void setCurrentTemperature(String str) {
        if (!TextUtils.isEmpty(str)) {
            TextView textView = this.b;
            textView.setText(str + this.i);
        }
    }

    private String a(WeatherToday weatherToday) {
        return weatherToday.dayTemp == null ? "" : weatherToday.dayTemp.getSmallValue();
    }

    private String b(WeatherToday weatherToday) {
        return weatherToday.dayTemp == null ? "" : weatherToday.dayTemp.getBigValue();
    }

    private String c(WeatherToday weatherToday) {
        return a(weatherToday) + "/" + b(weatherToday) + this.i;
    }

    public void onTtsEnd() {
        if (this.j == MiWeather.WeatherType.TODAY) {
            a();
        } else if (this.j == MiWeather.WeatherType.ONE_DAY || this.j == MiWeather.WeatherType.DURATION) {
            d();
        }
    }

    private void a() {
        if (this.a.getVisibility() == 0) {
            b();
            c();
        }
    }

    private void b() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.b, View.TRANSLATION_X, 0.0f, 70.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.b, View.TRANSLATION_Y, 0.0f, -getContext().getResources().getDimension(R.dimen.weather_multidays_current_temp_translation_x));
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.b, View.SCALE_X, 1.0f, 0.74f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.b, View.SCALE_Y, 1.0f, 0.74f);
        animatorSet.setDuration(600L);
        animatorSet.playTogether(ofFloat2, ofFloat, ofFloat3, ofFloat4);
        animatorSet.start();
    }

    private void c() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.e, View.TRANSLATION_X, 0.0f, 35.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.e, View.TRANSLATION_Y, 0.0f, -getContext().getResources().getDimension(R.dimen.weather_multidays_today_temp_translation_x));
        animatorSet.setDuration(600L);
        animatorSet.playTogether(ofFloat2, ofFloat);
        animatorSet.start();
    }

    private void d() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.g, View.TRANSLATION_X, 0.0f, 100.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.g, View.TRANSLATION_Y, 0.0f, -getContext().getResources().getDimension(R.dimen.weather_multidays_weatherwiew_height));
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.g, View.SCALE_X, 1.0f, 0.74f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.g, View.SCALE_Y, 1.0f, 0.74f);
        animatorSet.setDuration(600L);
        animatorSet.playTogether(ofFloat2, ofFloat, ofFloat3, ofFloat4);
        animatorSet.start();
    }
}
