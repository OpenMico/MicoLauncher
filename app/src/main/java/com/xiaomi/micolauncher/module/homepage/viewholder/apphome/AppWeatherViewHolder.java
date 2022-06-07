package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.RoundImageView;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.module.weather.WeatherDataManager;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AppWeatherViewHolder extends BaseAppHolder implements WeatherDataManager.MiWeatherListener {
    private final ImageView a;
    private final ImageView b;
    private final RoundImageView c;
    private final TextView d;
    private final TextView e;
    private final LinearLayout f;
    private AppInfo g;
    private String h;

    @SuppressLint({"CheckResult"})
    public AppWeatherViewHolder(@NonNull View view) {
        super(view, false);
        this.c = (RoundImageView) view.findViewById(R.id.ivBackground);
        this.a = (ImageView) view.findViewById(R.id.ivWeather);
        this.d = (TextView) view.findViewById(R.id.tvWeatherTemperature);
        this.e = (TextView) view.findViewById(R.id.tvWeatherDesc);
        this.f = (LinearLayout) view.findViewById(R.id.llFutureWeathers);
        this.b = (ImageView) view.findViewById(R.id.ivWeatherUnit);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult"})
    public void initViewInMain() {
        super.initViewInMain();
        AnimLifecycleManager.repeatOnAttach(this.itemView, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.itemView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppWeatherViewHolder$mELok6MahCjmYx9y9e9AicGNKME
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppWeatherViewHolder.this.a(obj);
            }
        });
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        executeAction();
        AppRecordHelper.recordAppClickByMi(this.h, this.g.getAppName());
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void bindAppInfo(AppInfo appInfo, String str) {
        super.bindAppInfo(appInfo, str);
        this.g = appInfo;
        this.h = str;
        WeatherDataManager.getManager().getWeatherInApps(this);
    }

    @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
    public void onPrevWeatherLoaded(MiWeather miWeather) {
        a(miWeather);
    }

    private void a(MiWeather miWeather) {
        List<WeacherDay> list;
        SpanUtils.with(this.e).append(MicoApplication.getApp().getText(miWeather.getCurrentWeatherInfoV2().weather.strId)).append("\n").append(miWeather.getCurrentWeatherInfoV2().dayTemp.from).append("°").append("/").append(miWeather.getCurrentWeatherInfoV2().dayTemp.to).append("°").create();
        if (miWeather.getCurrentWeatherInfoV2().weather != null) {
            this.c.setImageResource(miWeather.getCurrentWeatherInfoV2().weather.getWeatherBackgroundRes(this.context));
        } else {
            this.c.setImageResource(R.drawable.bg_app_card_weather);
        }
        if (!TextUtils.isEmpty(miWeather.getCurrentWeatherInfoV2().currentTemp)) {
            this.b.setVisibility(0);
            this.d.setVisibility(0);
            this.d.setText(miWeather.getCurrentWeatherInfoV2().currentTemp);
        } else {
            this.b.setVisibility(4);
            this.d.setVisibility(4);
        }
        this.a.setImageResource(miWeather.getCurrentWeatherInfoV2().weather.drawId);
        new ArrayList();
        if (Hardware.isX6A()) {
            list = miWeather.getMultiDaysWeatherV2(2);
        } else {
            list = miWeather.getMultiDaysWeatherV2(3);
        }
        if (ContainerUtil.hasData(list)) {
            this.f.removeAllViews();
            for (WeacherDay weacherDay : list) {
                View inflate = LayoutInflater.from(this.context).inflate(R.layout.item_forecast, (ViewGroup) null);
                ((TextView) inflate.findViewById(R.id.tvDate)).setText(weacherDay.getDateMonthDay());
                ((ImageView) inflate.findViewById(R.id.ivWeatherIcon)).setImageResource(weacherDay.getDisplayWeather().drawId);
                ((TextView) inflate.findViewById(R.id.tvTemp)).setText(String.format("%s°/%s°", weacherDay.dayTemp.from, weacherDay.dayTemp.to));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                layoutParams.setMargins(0, SizeUtils.dp2px(this.itemView.getResources().getDimensionPixelSize(R.dimen.app_card_weather_future_item_margin)), 0, SizeUtils.dp2px(this.itemView.getResources().getDimensionPixelSize(R.dimen.app_card_weather_future_item_margin)));
                this.f.addView(inflate, layoutParams);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
    public void onWeatherLoaded(MiWeather miWeather) {
        a(miWeather);
    }

    @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
    public void onWeatherLoadedError(boolean z) {
        L.smarthome.e("onWeatherLoadedError byNewRequest=%s", Boolean.valueOf(z));
    }
}
