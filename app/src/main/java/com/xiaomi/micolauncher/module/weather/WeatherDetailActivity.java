package com.xiaomi.micolauncher.module.weather;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import com.rd.PageIndicatorView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.view.LoadingDialog;
import com.xiaomi.micolauncher.module.weather.WeatherDataManager;
import com.xiaomi.micolauncher.module.weather.WeatherDetailActivity;
import com.xiaomi.micolauncher.module.weather.fragment.MultidayWeatherFragment;
import com.xiaomi.micolauncher.module.weather.fragment.TodayWeatherFragment;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WeatherDetailActivity extends BaseActivity {
    private static int a;
    private PageIndicatorView b;
    private View c;
    private ViewPager d;
    private TodayWeatherFragment e;
    private MultidayWeatherFragment f;
    private LoadingDialog g;
    private WeatherDataManager.MiWeatherListener h = new AnonymousClass2();
    public MiWeather miWeather;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_weather_detail);
        a();
        g();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        d();
        WeatherDataManager.getManager().b(this.h);
    }

    private void a() {
        this.g = new LoadingDialog(this, new LoadingDialog.LoadingListener() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailActivity$3Q6sHZWkgqDnwNWekPVvV_18IbA
            @Override // com.xiaomi.micolauncher.common.view.LoadingDialog.LoadingListener
            public final void onRetry() {
                WeatherDetailActivity.this.g();
            }
        });
        this.b = (PageIndicatorView) findViewById(R.id.indicator);
        this.c = findViewById(R.id.root_view);
        this.d = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) { // from class: com.xiaomi.micolauncher.module.weather.WeatherDetailActivity.1
            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return 2;
            }

            @Override // androidx.fragment.app.FragmentPagerAdapter
            public Fragment getItem(int i) {
                if (i == 0) {
                    WeatherDetailActivity.this.e = TodayWeatherFragment.newInstance();
                    return WeatherDetailActivity.this.e;
                }
                WeatherDetailActivity.this.f = MultidayWeatherFragment.newInstance();
                return WeatherDetailActivity.this.f;
            }
        };
        this.d.setAdapter(fragmentPagerAdapter);
        this.b.setViewPager(this.d);
        this.b.setCount(fragmentPagerAdapter.getCount());
        a = getResources().getIdentifier(String.format("weather_bg_%s", "0"), "drawable", getPackageName());
        this.c.setBackgroundResource(a);
        c();
    }

    /* renamed from: b */
    public void g() {
        WeatherDataManager.getManager().a(this.h);
    }

    private void c() {
        LoadingDialog loadingDialog = this.g;
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    public void d() {
        LoadingDialog loadingDialog = this.g;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public void e() {
        LoadingDialog loadingDialog = this.g;
        if (loadingDialog != null) {
            loadingDialog.showError();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.module.weather.WeatherDetailActivity$2 */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 implements WeatherDataManager.MiWeatherListener {
        AnonymousClass2() {
            WeatherDetailActivity.this = r1;
        }

        @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
        public void onPrevWeatherLoaded(MiWeather miWeather) {
            L.weather.i(" on prev weather loaded ");
            if (miWeather != null) {
                WeatherDetailActivity weatherDetailActivity = WeatherDetailActivity.this;
                weatherDetailActivity.miWeather = miWeather;
                weatherDetailActivity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailActivity$2$0J_u1-JjbsMjZl-Z87SUT8_cOh8
                    @Override // java.lang.Runnable
                    public final void run() {
                        WeatherDetailActivity.AnonymousClass2.this.a();
                    }
                });
            }
        }

        public /* synthetic */ void a() {
            if (WeatherDetailActivity.this.f() && WeatherDetailActivity.this.e != null) {
                WeatherDetailActivity.this.e.setupView(WeatherDetailActivity.this.miWeather);
            }
        }

        @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
        public void onWeatherLoaded(final MiWeather miWeather) {
            L.weather.i(" on weather loaded ");
            if (miWeather != null) {
                WeatherDetailActivity weatherDetailActivity = WeatherDetailActivity.this;
                weatherDetailActivity.miWeather = miWeather;
                weatherDetailActivity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailActivity$2$g2aRCZgUkRWCJWz6zV8k7_IK35Q
                    @Override // java.lang.Runnable
                    public final void run() {
                        WeatherDetailActivity.AnonymousClass2.this.a(miWeather);
                    }
                });
            }
        }

        public /* synthetic */ void a(MiWeather miWeather) {
            if (WeatherDetailActivity.this.f()) {
                WeatherDetailActivity.this.d();
                if (WeatherDetailActivity.this.e != null) {
                    WeatherDetailActivity.this.e.setupView(miWeather);
                }
                if (WeatherDetailActivity.this.f != null) {
                    WeatherDetailActivity.this.f.setupView(miWeather);
                }
            }
        }

        @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
        public void onWeatherLoadedError(final boolean z) {
            L.weather.i(" on weather loaded error");
            WeatherDetailActivity.this.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailActivity$2$ixJvdq8ZX8N4yjGRKiaLQolKzPE
                @Override // java.lang.Runnable
                public final void run() {
                    WeatherDetailActivity.AnonymousClass2.this.a(z);
                }
            });
        }

        public /* synthetic */ void a(boolean z) {
            if (WeatherDetailActivity.this.f()) {
                WeatherDetailActivity.this.e();
                if (z) {
                    ToastUtil.showToast((int) R.string.weather_detail_other_error);
                } else {
                    ToastUtil.showToast((int) R.string.weather_detail_network_error);
                }
            }
        }
    }

    public boolean f() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }
}
