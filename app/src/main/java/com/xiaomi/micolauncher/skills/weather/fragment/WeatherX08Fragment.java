package com.xiaomi.micolauncher.skills.weather.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.weather.LocationDialogHelper;
import com.xiaomi.micolauncher.skills.weather.model.Weather;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;
import com.xiaomi.micolauncher.skills.weather.widget.x08a.WeatherView;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WeatherX08Fragment extends WeatherBaseFragment {
    WeatherView d;
    ImageView e;
    VideoView f;
    private CustomDialog j;
    private final long g = TimeUnit.SECONDS.toMillis(28);
    private final long h = TimeUnit.SECONDS.toMillis(3);
    private final long i = TimeUnit.SECONDS.toMillis(60);
    private Runnable k = new Runnable() { // from class: com.xiaomi.micolauncher.skills.weather.fragment.-$$Lambda$WSfMSmD3k4qRogkZifzjMbOiq6o
        @Override // java.lang.Runnable
        public final void run() {
            WeatherX08Fragment.this.finish();
        }
    };

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_weather_08a, (ViewGroup) null);
        this.d = (WeatherView) inflate.findViewById(R.id.weather_view);
        this.e = (ImageView) inflate.findViewById(R.id.weather_preview_bg);
        this.f = (VideoView) inflate.findViewById(R.id.video_view);
        initWeatherView();
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.skills.weather.fragment.WeatherBaseFragment
    public void initWeatherView() {
        super.initWeatherView();
        if (this.a == null) {
            L.weather.i("miWeather is null");
            finish();
            return;
        }
        a();
        b(this.i);
        this.d.refreshView(this.a);
        if (this.a.isTodayWeather()) {
            WeatherToday currentWeatherInfoV2 = this.a.getCurrentWeatherInfoV2();
            if (currentWeatherInfoV2 != null) {
                a(currentWeatherInfoV2.weather);
            }
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.weather.fragment.-$$Lambda$TeypiWyz4D1jOnhxR4UX86Gfifo
                @Override // java.lang.Runnable
                public final void run() {
                    WeatherX08Fragment.this.b();
                }
            }, TimeUnit.SECONDS.toMillis(2L));
        } else if (this.a.isOneDayWeather()) {
            WeatherToday dateWeatherInfoV2 = this.a.getDateWeatherInfoV2(this.a.date.dateStr);
            if (dateWeatherInfoV2 != null) {
                a(dateWeatherInfoV2.weather);
            }
        } else if (this.a.isDurationWeather()) {
            a(this.d.getWeather());
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.weather.fragment.-$$Lambda$TeypiWyz4D1jOnhxR4UX86Gfifo
                @Override // java.lang.Runnable
                public final void run() {
                    WeatherX08Fragment.this.b();
                }
            }, TimeUnit.SECONDS.toMillis(2L));
        } else {
            L.weather.i("Weather data is illegal");
            finish();
        }
    }

    private void a(Weather weather) {
        L.weather.d("setWeatherBackgroundResource");
        if (weather != null) {
            this.e.setBackgroundResource(weather.getWeatherBackgroundRes(getContext()));
        }
    }

    @Override // com.xiaomi.micolauncher.skills.weather.fragment.WeatherBaseFragment
    public void onTtsPlayEnd() {
        L.base.d("WeatherSkill onTtsPlayEnd");
        this.b = true;
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            if (this.a != null && this.a.isTodayWeather()) {
                a(this.g);
            } else if (this.a != null && this.a.isOneDayWeather()) {
                a(this.h);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        L.base.d("WeatherSkill showMultiInfo");
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed() && this.a != null) {
            if (this.a.isTodayWeather() || this.a.isDurationWeather()) {
                this.d.showMultiInfo();
            }
        }
    }

    private void b(long j) {
        c();
        ThreadUtil.postDelayedInMainThread(this.k, j);
        L.base.d("weather skill startCountDown %d", Long.valueOf(j));
    }

    private void c() {
        ThreadUtil.removeCallbacksInMainThread(this.k);
    }

    @Override // com.xiaomi.micolauncher.skills.weather.fragment.WeatherBaseFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        c();
        d();
    }

    private void d() {
        LocationDialogHelper.dismissDialog(this.j);
    }
}
