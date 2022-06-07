package com.xiaomi.micolauncher.skills.weather.widget.x08a;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.Weather;

/* loaded from: classes3.dex */
public class WeatherView extends RelativeLayout {
    WeatherInfoView a;
    WeatherTemperatureView b;
    MultidaysWeatherView c;
    private MiWeather d;

    public WeatherView(Context context) {
        super(context);
        a(context);
    }

    public WeatherView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public WeatherView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_weather_08a, this);
        this.a = (WeatherInfoView) findViewById(R.id.weather_info);
        this.b = (WeatherTemperatureView) findViewById(R.id.weather_temperature);
        this.c = (MultidaysWeatherView) findViewById(R.id.multi_weather_info);
    }

    public void refreshView(MiWeather miWeather) {
        L.weather.d("%s refreshView ", "WeatherView:");
        if (miWeather != null) {
            this.d = miWeather;
            this.a.refreshView(miWeather);
            this.b.refreshView(miWeather);
            if (miWeather.isTodayWeather() || miWeather.isDurationWeather()) {
                this.c.refreshView(miWeather);
            }
            if (this.c.getVisibility() == 0) {
                this.c.setVisibility(4);
            }
        }
    }

    public void showMultiInfo() {
        MiWeather miWeather = this.d;
        if (miWeather != null && !miWeather.isOneDayWeather()) {
            this.a.onTtsEnd();
            this.b.onTtsEnd();
            refreshMultiLayout();
            a();
        }
    }

    public void refreshMultiLayout() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.c.getLayoutParams();
        layoutParams.width = DisplayUtils.getScreenWidthPixels(getContext());
        layoutParams.height = DisplayUtils.dip2px(getContext(), getContext().getResources().getDimension(R.dimen.weather_multidays_weatherwiew_height));
        this.c.setLayoutParams(layoutParams);
    }

    private void a() {
        float dimension = getContext().getResources().getDimension(R.dimen.weather_multidays_weatherwiew_height);
        this.c.setVisibility(0);
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, dimension, 0.0f);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(600L);
        this.c.startAnimation(animationSet);
    }

    public Weather getWeather() {
        MiWeather miWeather = this.d;
        if (miWeather == null || !miWeather.isDurationWeather()) {
            return null;
        }
        return this.c.getStartWeather();
    }
}
