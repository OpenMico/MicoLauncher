package com.xiaomi.micolauncher.module.weather.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.weather.WeatherDetailActivity;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;
import com.xiaomi.micolauncher.skills.weather.widget.TodayWeatherView;

/* loaded from: classes3.dex */
public class TodayWeatherFragment extends BaseFragment {
    TodayWeatherView a;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_today_weather, viewGroup, false);
        this.a = (TodayWeatherView) inflate;
        setupView(a());
        return inflate;
    }

    public void setupView(MiWeather miWeather) {
        if (miWeather != null && miWeather.date != null) {
            WeatherToday currentWeatherInfoV2 = miWeather.getCurrentWeatherInfoV2();
            this.a.setBackgroundResource(currentWeatherInfoV2.weather.getWeatherBackgroundRes(getContext()));
            this.a.refreshView(currentWeatherInfoV2);
        }
    }

    private MiWeather a() {
        try {
            return ((WeatherDetailActivity) getActivity()).miWeather;
        } catch (Exception e) {
            XLog.e(e);
            return null;
        }
    }

    public static TodayWeatherFragment newInstance() {
        return new TodayWeatherFragment();
    }
}
