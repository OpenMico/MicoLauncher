package com.xiaomi.micolauncher.skills.weather.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import java.util.List;

/* loaded from: classes3.dex */
public class MultidaysWeatherViewV2 extends HorizontalScrollView {
    LinearLayout a;
    TemperatureChartViewV2 b;
    private int c;
    private boolean d;
    private CarouselListener e;
    private int g;
    private int h;
    private Handler i;
    private final int f = 1;
    private Handler.Callback j = new Handler.Callback() { // from class: com.xiaomi.micolauncher.skills.weather.widget.MultidaysWeatherViewV2.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            MultidaysWeatherViewV2.this.b();
            return false;
        }
    };

    /* loaded from: classes3.dex */
    public interface CarouselListener {
        void onCarouselFinish();

        void onCarouselInterruptEnd();

        void onCarouselInterruptStart();
    }

    public MultidaysWeatherViewV2(Context context) {
        super(context);
        a(context);
    }

    public MultidaysWeatherViewV2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public MultidaysWeatherViewV2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        setClipChildren(false);
        LayoutInflater.from(context).inflate(R.layout.view_weather_multidays_v2, this);
        this.a = (LinearLayout) findViewById(R.id.weather_info);
        this.b = (TemperatureChartViewV2) findViewById(R.id.temperature_chart_view);
        this.c = DisplayUtils.getScreenWidthPixels(context) / 4;
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

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 1:
            case 3:
                f();
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case 2:
                e();
                if (a()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private boolean a() {
        return getScrollX() == 0;
    }

    public void setStartDate(final int i) {
        if (i > 0) {
            c();
            this.i.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.weather.widget.-$$Lambda$MultidaysWeatherViewV2$dUOv5NnyVztb843ujTXOnbLp_Z8
                @Override // java.lang.Runnable
                public final void run() {
                    MultidaysWeatherViewV2.this.a(i);
                }
            }, 150L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i) {
        smoothScrollBy(i * this.c, 0);
    }

    public void starCarousel(int i, int i2, CarouselListener carouselListener) {
        this.d = true;
        this.g = i;
        this.h = i2;
        this.e = carouselListener;
        d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        CarouselListener carouselListener;
        smoothScrollBy(this.c * 4, 0);
        this.g += 4;
        if (this.g + 4 < this.h) {
            d();
        } else if (this.d && (carouselListener = this.e) != null) {
            carouselListener.onCarouselFinish();
            g();
        }
    }

    private void c() {
        if (this.i == null) {
            this.i = new WeakRefHandler(this.j);
        }
    }

    private void d() {
        c();
        this.i.sendEmptyMessageDelayed(1, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }

    private void e() {
        CarouselListener carouselListener;
        if (this.d && (carouselListener = this.e) != null) {
            carouselListener.onCarouselInterruptStart();
            g();
        }
    }

    private void f() {
        CarouselListener carouselListener;
        if (this.d && (carouselListener = this.e) != null) {
            carouselListener.onCarouselInterruptEnd();
        }
    }

    private void g() {
        Handler handler = this.i;
        if (handler != null) {
            handler.removeMessages(1);
            this.i.removeCallbacksAndMessages(null);
            this.i = null;
        }
    }
}
