package com.xiaomi.micolauncher.skills.weather.widget.x08a;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import com.xiaomi.micolauncher.skills.weather.model.Weather;
import java.util.List;

/* loaded from: classes3.dex */
public class MultidaysWeatherView extends HorizontalScrollView {
    public static final int MAX_WEATHER_SIZE = 15;
    LinearLayout a;
    TemperatureChartView b;
    private int c;
    private int d;
    private List<WeacherDay> e;

    public MultidaysWeatherView(Context context) {
        super(context);
        a(context);
    }

    public MultidaysWeatherView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public MultidaysWeatherView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        setClipChildren(false);
        LayoutInflater.from(context).inflate(R.layout.view_weather_multidays_08a, this);
        this.a = (LinearLayout) findViewById(R.id.weather_info);
        this.b = (TemperatureChartView) findViewById(R.id.temperature_chart_view);
        this.c = (DisplayUtils.getScreenWidthPixels(context) - DisplayUtils.dip2px(context, getContext().getResources().getDimension(R.dimen.weather_multidays_cell_width))) / 7;
    }

    public void refreshView(MiWeather miWeather) {
        this.e = miWeather.getMultiDaysWeatherV2(15);
        setUI(this.e, miWeather.getMuitlDaysUpTemp(15), miWeather.getMuitlDaysDownTemp(15));
        if (miWeather.isDurationWeather()) {
            int i = miWeather.duration.startIndexDaily;
            if (miWeather.duration.durationLength <= 7 && i > 1) {
                this.d = i - 1;
            }
            setStartDate(this.d);
        }
    }

    public void setUI(List<WeacherDay> list, List<Integer> list2, List<Integer> list3) {
        this.a.removeAllViews();
        for (WeacherDay weacherDay : list) {
            WeatherCell weatherCell = new WeatherCell(getContext());
            this.a.addView(weatherCell);
            weatherCell.refreshView(weacherDay);
            weatherCell.setWidth(this.c);
        }
        this.b.setTempes(list2, list3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i) {
        smoothScrollBy(i * this.c, 0);
    }

    public void setStartDate(final int i) {
        if (i > 0 && i < 15) {
            postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.weather.widget.x08a.-$$Lambda$MultidaysWeatherView$qDyDBXChRh2awTaOLNR0ALdY-II
                @Override // java.lang.Runnable
                public final void run() {
                    MultidaysWeatherView.this.a(i);
                }
            }, 150L);
        }
    }

    public Weather getStartWeather() {
        List<WeacherDay> list = this.e;
        if (list == null || this.d >= list.size()) {
            return null;
        }
        return this.e.get(this.d).getDisplayWeather();
    }
}
