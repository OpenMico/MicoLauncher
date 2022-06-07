package com.xiaomi.micolauncher.module.lockscreen.manager;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.ThirdParty;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.CurrentWeatherChangedEvent;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class WeatherManager {
    private static final long a = TimeUnit.MINUTES.toMillis(30);
    private static final long b = TimeUnit.HOURS.toMillis(1);
    private static final long c = TimeUnit.MINUTES.toMillis(1);
    private static final int d = RandomTimeUtils.getRandomGenerator().nextInt(6) + 8;
    private static final long f = TimeUnit.MINUTES.toMillis(10);
    private long e;
    private JobService g;
    private JobParameters h;
    private ThirdParty.WeatherCurrentForecast i;
    private Handler j;
    private Disposable k;
    private long l;
    private long m;
    private boolean n;
    private boolean o;
    private boolean p;

    /* loaded from: classes3.dex */
    private static class a {
        private static WeatherManager a = new WeatherManager();
    }

    public static WeatherManager getManager() {
        return a.a;
    }

    private WeatherManager() {
        this.e = c;
        this.m = a;
        this.j = new Handler(Threads.getHeavyWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WeatherManager$ZvdvtLCuJA34Ru0S8GpbMkZdQpc
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = WeatherManager.this.a(message);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Message message) {
        if (this.n) {
            return true;
        }
        switch (message.what) {
            case 0:
                startLoadWeatherData(false);
                break;
            case 1:
                loadWeatherData(false);
                break;
            default:
                return false;
        }
        return true;
    }

    public void setJobService(JobService jobService, JobParameters jobParameters) {
        this.g = jobService;
        this.h = jobParameters;
    }

    public void startRefresh() {
        L.weather.i("WeatherManager#startRefresh");
        this.n = false;
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        a();
    }

    private void a() {
        this.j.removeMessages(0);
        this.j.sendEmptyMessageDelayed(0, 700L);
    }

    public void release() {
        L.weather.i("WeatherManager#release");
        this.n = true;
        Disposable disposable = this.k;
        if (disposable != null) {
            disposable.dispose();
        }
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    public ThirdParty.WeatherCurrentForecast getWeather() {
        return this.i;
    }

    public void loadWeatherData(boolean z) {
        long millis = z ? TimeUnit.SECONDS.toMillis(30L) + RandomTimeUtils.getRandomTimeInMills(TimeUnit.MINUTES.toMillis(d)) : 0L;
        L.weather.i("start load weather data beforeFlowPeak=%s, delayTime=%s ", Boolean.valueOf(this.p), Long.valueOf(millis));
        this.k = Observable.timer(millis, TimeUnit.MILLISECONDS).flatMap($$Lambda$WeatherManager$WiKBZzzMLDTMNOARbuOKm5HVmc.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WeatherManager$HyyhJ9b17BwRLF-p9_VMKfQEEkE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WeatherManager.this.a((ThirdParty.WeatherCurrentForecast) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WeatherManager$NzHHG-8Fw0Xd4x9zwbBRYwmYouI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WeatherManager.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Long l) throws Exception {
        return ApiManager.minaService.getWeatherCurrentForecast("", false).retry(1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ThirdParty.WeatherCurrentForecast weatherCurrentForecast) throws Exception {
        if (weatherCurrentForecast == null || weatherCurrentForecast.currentWeather == null || weatherCurrentForecast.currentWeather.temperature == null) {
            L.weather.w("WeatherManager#loadWeatherData success, but data is inValid cause of value is null");
            this.m = a(false);
        } else {
            this.i = weatherCurrentForecast;
            this.i.localCache = this.p;
            EventBusRegistry.getEventBus().post(new CurrentWeatherChangedEvent());
            L.weather.i("WeatherManager#loadWeatherData success");
            this.m = a(true);
        }
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.weather.e("WeatherManager#loadWeatherData failed", th);
        this.m = a(false);
        b();
    }

    private void b() {
        JobService jobService;
        JobParameters jobParameters;
        this.l = System.currentTimeMillis();
        this.j.removeMessages(1);
        this.j.sendEmptyMessageDelayed(1, this.m);
        if (this.p && (jobService = this.g) != null && (jobParameters = this.h) != null) {
            this.p = false;
            jobService.jobFinished(jobParameters, false);
        }
    }

    public void startLoadWeatherData(boolean z) {
        this.p = z;
        long currentTimeMillis = System.currentTimeMillis() - this.l;
        if (!this.j.hasMessages(1) || currentTimeMillis >= this.m || this.o) {
            this.j.removeCallbacksAndMessages(null);
            this.j.sendEmptyMessage(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onWifiConnectivityChanged(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected) {
            L.weather.i("WeatherManager onWifiConnectivityChanged wifi connected");
            long currentTimeMillis = System.currentTimeMillis() - this.l;
            if (!this.j.hasMessages(1) || currentTimeMillis >= this.m) {
                this.j.removeCallbacksAndMessages(null);
                this.j.sendEmptyMessage(1);
            }
        }
    }

    private long a(boolean z) {
        this.o = !z;
        boolean inDuringSleepTime = SleepMode.getInstance().inDuringSleepTime();
        if (!this.o || inDuringSleepTime) {
            this.e = c;
            long anchorOfFactorInMillis = RandomTimeUtils.getAnchorOfFactorInMillis(inDuringSleepTime ? b : a, f);
            L.weather.i("WeatherManager refreshDataInterval=%s", Long.valueOf(anchorOfFactorInMillis));
            return anchorOfFactorInMillis;
        }
        long j = this.e;
        if (j < a) {
            this.e = j * 2;
        }
        return this.e;
    }
}
