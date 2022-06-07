package com.xiaomi.micolauncher.skills.weather.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;
import com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherViewV2;
import com.xiaomi.micolauncher.skills.weather.widget.OnedayWeatherView;
import com.xiaomi.micolauncher.skills.weather.widget.TodayWeatherView;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WeatherX04Fragment extends WeatherBaseFragment {
    OnedayWeatherView d;
    TodayWeatherView e;
    MultidaysWeatherViewV2 f;
    View g;
    private final long h = TimeUnit.SECONDS.toMillis(8);
    private final long i = TimeUnit.SECONDS.toMillis(3);
    private final long j = TimeUnit.SECONDS.toMillis(60);
    private a k;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_weather, (ViewGroup) null);
        this.d = (OnedayWeatherView) inflate.findViewById(R.id.oneday_weather_view);
        this.e = (TodayWeatherView) inflate.findViewById(R.id.today_weather_view);
        this.f = (MultidaysWeatherViewV2) inflate.findViewById(R.id.multidays_weather_view);
        this.g = inflate.findViewById(R.id.root_view);
        initWeatherView();
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.skills.weather.fragment.WeatherBaseFragment
    public void initWeatherView() {
        super.initWeatherView();
        a();
        b();
        if (this.a == null) {
            L.base.e("miWeather is null");
            finish();
            return;
        }
        b(this.j);
        this.e.setVisibility(8);
        this.d.setVisibility(8);
        this.f.setVisibility(8);
        if (this.a.isTodayWeather()) {
            WeatherToday currentWeatherInfoV2 = this.a.getCurrentWeatherInfoV2();
            if (currentWeatherInfoV2 != null) {
                this.e.refreshView(currentWeatherInfoV2);
                this.e.setVisibility(0);
                this.g.setBackgroundResource(currentWeatherInfoV2.weather.getWeatherBackgroundRes(getContext()));
            }
        } else if (this.a.isOneDayWeather()) {
            WeatherToday dateWeatherInfoV2 = this.a.getDateWeatherInfoV2(this.a.date.dateStr);
            if (dateWeatherInfoV2 != null) {
                this.d.refreshView(this.a.getDateWeatherInfoV2(this.a.date.dateStr));
                this.d.setVisibility(0);
                this.g.setBackgroundResource(dateWeatherInfoV2.weather.getWeatherBackgroundRes(getContext()));
            }
        } else if (this.a.isDurationWeather()) {
            this.f.setVisibility(0);
            List<WeacherDay> multiDaysWeatherV2 = this.a.getMultiDaysWeatherV2(15);
            if (multiDaysWeatherV2.size() > 0) {
                this.g.setBackgroundResource(multiDaysWeatherV2.get(0).getDisplayWeather().getWeatherBackgroundRes(getContext()));
            }
            this.f.setUI(multiDaysWeatherV2, this.a.getMuitlDaysUpTemp(15), this.a.getMuitlDaysDownTemp(15));
            int i = this.a.duration.startIndexDaily;
            if (this.a.duration.durationLength < 5 && i > 1) {
                i--;
            }
            this.f.setStartDate(i);
            if (this.a.duration.durationLength > 5) {
                this.f.starCarousel(i, (int) (i + this.a.duration.durationLength), new MultidaysWeatherViewV2.CarouselListener() { // from class: com.xiaomi.micolauncher.skills.weather.fragment.WeatherX04Fragment.1
                    @Override // com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherViewV2.CarouselListener
                    public void onCarouselFinish() {
                        WeatherX04Fragment weatherX04Fragment = WeatherX04Fragment.this;
                        weatherX04Fragment.a(weatherX04Fragment.h);
                    }

                    @Override // com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherViewV2.CarouselListener
                    public void onCarouselInterruptStart() {
                        WeatherX04Fragment.this.a();
                    }

                    @Override // com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherViewV2.CarouselListener
                    public void onCarouselInterruptEnd() {
                        WeatherX04Fragment weatherX04Fragment = WeatherX04Fragment.this;
                        weatherX04Fragment.a(weatherX04Fragment.j);
                    }
                });
            }
        } else {
            L.base.e("Weather data is illegal");
            finish();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.weather.fragment.WeatherBaseFragment
    public void onTtsPlayEnd() {
        L.base.d("WeatherSkill onTtsPlayEnd");
        this.b = true;
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            L.base.d("WeatherSkill onTtsEnd");
            if (this.a != null) {
                if (this.a.isTodayWeather()) {
                    this.f.setUI(this.a.getMultiDaysWeatherV2(4), this.a.getMuitlDaysUpTemp(4), this.a.getMuitlDaysDownTemp(4));
                    this.f.setVisibility(0);
                    this.e.setVisibility(8);
                    this.d.setVisibility(8);
                    a(this.h);
                } else if (this.a.isOneDayWeather()) {
                    a(this.i);
                }
            }
        }
    }

    private void b(long j) {
        b();
        this.k = new a(j, 1000L);
        this.k.start();
        L.base.d("weather skill startCountDown %d", Long.valueOf(j));
    }

    private void b() {
        a aVar = this.k;
        if (aVar != null) {
            aVar.cancel();
            this.k = null;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.weather.fragment.WeatherBaseFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.k = null;
        finish();
        L.base.d("weather skill onCountDownFinish ");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends CountDownTimer {
        @Override // android.os.CountDownTimer
        public void onTick(long j) {
        }

        a(long j, long j2) {
            super(j, j2);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            WeatherX04Fragment.this.c();
        }
    }
}
