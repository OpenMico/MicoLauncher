package com.xiaomi.micolauncher.module.weather.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.weather.WeatherDetailActivity;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import com.xiaomi.micolauncher.skills.weather.model.WeatherToday;
import com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherViewV2;
import java.util.List;

/* loaded from: classes3.dex */
public class MultidayWeatherFragment extends BaseFragment {
    private MultidaysWeatherViewV2 a;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_multi_day_weather, viewGroup, false);
        this.a = (MultidaysWeatherViewV2) inflate;
        this.a.setOverScrollMode(2);
        setupView(a());
        return inflate;
    }

    public void setupView(MiWeather miWeather) {
        if (miWeather != null) {
            WeatherToday currentWeatherInfoV2 = miWeather.getCurrentWeatherInfoV2();
            if (currentWeatherInfoV2.weather != null) {
                this.a.setBackgroundResource(currentWeatherInfoV2.weather.getWeatherBackgroundRes(getContext()));
            }
            List<WeacherDay> multiDaysWeatherV2 = miWeather.getMultiDaysWeatherV2(15);
            if (!ContainerUtil.isEmpty(multiDaysWeatherV2)) {
                this.a.setUI(multiDaysWeatherV2, miWeather.getMuitlDaysUpTemp(15), miWeather.getMuitlDaysDownTemp(15));
            }
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

    public static MultidayWeatherFragment newInstance() {
        return new MultidayWeatherFragment();
    }
}
