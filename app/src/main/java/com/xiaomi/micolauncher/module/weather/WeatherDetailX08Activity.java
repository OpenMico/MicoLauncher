package com.xiaomi.micolauncher.module.weather;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.view.LoadingDialog;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.weather.WeatherDataManager;
import com.xiaomi.micolauncher.module.weather.WeatherDetailX08Activity;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import com.xiaomi.micolauncher.skills.weather.model.Weather;
import com.xiaomi.micolauncher.skills.weather.widget.x08a.WeatherView;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WeatherDetailX08Activity extends BaseActivity {
    WeatherView a;
    ImageView b;
    VideoView c;
    private CustomDialog d;
    private LoadingDialog e;
    private boolean f;
    private WeatherDataManager.MiWeatherListener g = new AnonymousClass1();
    public MiWeather miWeather;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_weather_08a);
        b();
        setDefaultScheduleDuration();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.miWeather == null) {
            j();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        e();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WeatherDataManager.getManager().b(this.g);
        i();
    }

    private void b() {
        this.a = (WeatherView) findViewById(R.id.weather_view);
        this.b = (ImageView) findViewById(R.id.weather_preview_bg);
        this.c = (VideoView) findViewById(R.id.video_view);
        this.e = new LoadingDialog(this, new LoadingDialog.LoadingListener() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailX08Activity$MrPVrYiyYpfSEeREReEMs2jB6j0
            @Override // com.xiaomi.micolauncher.common.view.LoadingDialog.LoadingListener
            public final void onRetry() {
                WeatherDetailX08Activity.this.j();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void j() {
        d();
        WeatherDataManager.getManager().a(this.g);
    }

    private void d() {
        LoadingDialog loadingDialog = this.e;
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        LoadingDialog loadingDialog = this.e;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            this.e.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        LoadingDialog loadingDialog = this.e;
        if (loadingDialog != null) {
            loadingDialog.showError();
        }
    }

    /* renamed from: com.xiaomi.micolauncher.module.weather.WeatherDetailX08Activity$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements WeatherDataManager.MiWeatherListener {
        AnonymousClass1() {
        }

        @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
        public void onPrevWeatherLoaded(MiWeather miWeather) {
            L.weather.i(" on prev weather loaded ");
            if (miWeather != null) {
                WeatherDetailX08Activity weatherDetailX08Activity = WeatherDetailX08Activity.this;
                weatherDetailX08Activity.miWeather = miWeather;
                weatherDetailX08Activity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailX08Activity$1$2Wmm9aILdC2juVVk7N8nxLM83_0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WeatherDetailX08Activity.AnonymousClass1.this.b();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b() {
            if (WeatherDetailX08Activity.this.g()) {
                WeatherDetailX08Activity weatherDetailX08Activity = WeatherDetailX08Activity.this;
                weatherDetailX08Activity.a(weatherDetailX08Activity.miWeather, false);
            }
        }

        @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
        public void onWeatherLoaded(MiWeather miWeather) {
            L.weather.i(" on weather loaded ");
            if (miWeather != null) {
                WeatherDetailX08Activity weatherDetailX08Activity = WeatherDetailX08Activity.this;
                weatherDetailX08Activity.miWeather = miWeather;
                weatherDetailX08Activity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailX08Activity$1$ddHrcL-4l7chVB-IqA4sAt-I1Ac
                    @Override // java.lang.Runnable
                    public final void run() {
                        WeatherDetailX08Activity.AnonymousClass1.this.a();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a() {
            if (WeatherDetailX08Activity.this.g()) {
                WeatherDetailX08Activity.this.e();
                WeatherDetailX08Activity weatherDetailX08Activity = WeatherDetailX08Activity.this;
                weatherDetailX08Activity.a(weatherDetailX08Activity.miWeather, true);
                WeatherDetailX08Activity.this.f = true;
            }
        }

        @Override // com.xiaomi.micolauncher.module.weather.WeatherDataManager.MiWeatherListener
        public void onWeatherLoadedError(final boolean z) {
            L.weather.i(" on weather loaded error");
            WeatherDetailX08Activity.this.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDetailX08Activity$1$0qnkxg2iqaZRsFv7zAY20Q3NB-M
                @Override // java.lang.Runnable
                public final void run() {
                    WeatherDetailX08Activity.AnonymousClass1.this.a(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(boolean z) {
            if (WeatherDetailX08Activity.this.g()) {
                WeatherDetailX08Activity.this.f();
                if (z) {
                    ToastUtil.showToast((int) R.string.weather_detail_other_error);
                } else {
                    ToastUtil.showToast((int) R.string.weather_detail_network_error);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean g() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MiWeather miWeather, boolean z) {
        if (!z || !this.f) {
            WeatherView weatherView = this.a;
            if (weatherView != null && miWeather != null) {
                weatherView.refreshView(miWeather);
                h();
                if (getHandler() != null && z) {
                    getHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$N4RjsrwhZXiNnw8Cdpmmbu3qdf0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WeatherDetailX08Activity.this.a();
                        }
                    }, TimeUnit.SECONDS.toMillis(3L));
                    return;
                }
                return;
            }
            return;
        }
        L.weather.d("showMulti && updatedData");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        MiWeather miWeather;
        L.base.d("showMultiInfo");
        if (!isFinishing() && !isDestroyed() && (miWeather = this.miWeather) != null) {
            if (miWeather.isTodayWeather() || this.miWeather.isDurationWeather()) {
                this.a.showMultiInfo();
            }
        }
    }

    private void h() {
        Weather weather = this.miWeather.getCurrentWeatherInfoV2().weather;
        if (weather != null) {
            this.b.setBackgroundResource(weather.getWeatherBackgroundRes(this));
        }
    }

    private void i() {
        LocationDialogHelper.dismissDialog(this.d);
    }
}
