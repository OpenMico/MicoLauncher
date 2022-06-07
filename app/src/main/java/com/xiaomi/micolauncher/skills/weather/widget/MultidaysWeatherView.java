package com.xiaomi.micolauncher.skills.weather.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import java.util.List;

/* loaded from: classes3.dex */
public class MultidaysWeatherView extends HorizontalScrollView {
    LinearLayout a;
    TemperatureChartView b;
    private int c;
    private boolean d;
    private CarouselListener e;
    private int h;
    private int i;
    private Handler j;
    private final int f = 1;
    private final long g = RtspMediaSource.DEFAULT_TIMEOUT_MS;
    private Handler.Callback k = new Handler.Callback() { // from class: com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherView.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            MultidaysWeatherView.this.a();
            return false;
        }
    };

    /* loaded from: classes3.dex */
    public interface CarouselListener {
        void onCarouselFinish();

        void onCarouselInterruptEnd();

        void onCarouselInterruptStart();
    }

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
        LayoutInflater.from(context).inflate(R.layout.view_weather_multidays, this);
        this.a = (LinearLayout) findViewById(R.id.weather_info);
        this.b = (TemperatureChartView) findViewById(R.id.temperature_chart_view);
        this.j = new WeakRefHandler(this.k);
        this.c = DisplayUtils.getScreenWidthPixels(context) / 5;
    }

    public void setUi(List<WeacherDay> list, List<Integer> list2, List<Integer> list3) {
        for (WeacherDay weacherDay : list) {
            WeatherCell weatherCell = new WeatherCell(getContext());
            this.a.addView(weatherCell);
            weatherCell.refreshView(weacherDay);
            weatherCell.setWidth(this.c);
        }
        this.b.setTempes(list2, list3);
        this.a.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherView.2
            @Override // java.lang.Runnable
            public void run() {
                L.base.i("onGlobalLayout");
                WeatherCell weatherCell2 = (WeatherCell) MultidaysWeatherView.this.a.getChildAt(0);
                if (weatherCell2 != null) {
                    Logger logger = L.base;
                    logger.i("onGlobalLayout = " + weatherCell2.getWeatherStateY());
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) MultidaysWeatherView.this.b.getLayoutParams();
                    layoutParams.setMargins(0, weatherCell2.getWeatherStateY(), 0, 0);
                    MultidaysWeatherView.this.b.setLayoutParams(layoutParams);
                }
            }
        }, 100L);
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 1:
                c();
                break;
            case 2:
                b();
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setStartDate(final int i) {
        if (i > 0) {
            this.j.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherView.3
                @Override // java.lang.Runnable
                public void run() {
                    MultidaysWeatherView multidaysWeatherView = MultidaysWeatherView.this;
                    multidaysWeatherView.smoothScrollBy(i * multidaysWeatherView.c, 0);
                }
            }, 150L);
        }
    }

    public void starCarousel(int i, int i2, CarouselListener carouselListener) {
        this.d = true;
        this.h = i;
        this.i = i2;
        this.e = carouselListener;
        this.j.sendEmptyMessageDelayed(1, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        CarouselListener carouselListener;
        smoothScrollBy(this.c * 5, 0);
        this.h += 5;
        if (this.h + 5 < this.i) {
            this.j.sendEmptyMessageDelayed(1, RtspMediaSource.DEFAULT_TIMEOUT_MS);
        } else if (this.d && (carouselListener = this.e) != null) {
            carouselListener.onCarouselFinish();
            d();
        }
    }

    private void b() {
        CarouselListener carouselListener;
        if (this.d && (carouselListener = this.e) != null) {
            carouselListener.onCarouselInterruptStart();
            d();
        }
    }

    private void c() {
        CarouselListener carouselListener;
        if (this.d && (carouselListener = this.e) != null) {
            carouselListener.onCarouselInterruptEnd();
        }
    }

    private void d() {
        Handler handler = this.j;
        if (handler != null) {
            handler.removeMessages(1);
            this.j.removeCallbacksAndMessages(null);
            this.j = null;
        }
    }
}
