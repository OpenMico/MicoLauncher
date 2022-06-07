package com.xiaomi.micolauncher.module.weather;

import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.common.utils.AsyncTaskUtils;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.module.weather.WeatherDataManager;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class WeatherDataManager {
    private List<MiWeatherListener> a;
    private WeakReference<MiWeatherListener> b;
    private MiWeather c;

    /* loaded from: classes3.dex */
    public interface MiWeatherListener {
        void onPrevWeatherLoaded(MiWeather miWeather);

        void onWeatherLoaded(MiWeather miWeather);

        void onWeatherLoadedError(boolean z);
    }

    public static /* synthetic */ boolean a(MiWeatherListener miWeatherListener, MiWeatherListener miWeatherListener2) {
        return miWeatherListener2 == miWeatherListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static WeatherDataManager a = new WeatherDataManager();
    }

    private WeatherDataManager() {
        this.a = new ArrayList();
        c();
    }

    public static WeatherDataManager getManager() {
        return a.a;
    }

    public void getWeatherInApps(MiWeatherListener miWeatherListener) {
        if (miWeatherListener != null) {
            this.b = new WeakReference<>(miWeatherListener);
            MiWeather miWeather = this.c;
            if (miWeather != null) {
                miWeatherListener.onPrevWeatherLoaded(miWeather);
            }
            a();
        }
    }

    public void a(MiWeatherListener miWeatherListener) {
        if (miWeatherListener != null) {
            MiWeather miWeather = this.c;
            if (miWeather != null) {
                miWeatherListener.onPrevWeatherLoaded(miWeather);
            }
            c(miWeatherListener);
            a();
        }
    }

    private void a() {
        MicoSchedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDataManager$Sjm5_aE5U1oKCNverXOqPXImUVg
            @Override // java.lang.Runnable
            public final void run() {
                WeatherDataManager.this.f();
            }
        });
    }

    public /* synthetic */ void f() {
        SpeechEventUtil.getInstance().nlpRequest("weather_天气", new SpeechEventUtil.NLPListener() { // from class: com.xiaomi.micolauncher.module.weather.WeatherDataManager.1
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.NLPListener
            public void onNlpResult(List<Instruction> list, String str) {
                Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.Template.WeatherV2);
                Instruction intention2 = InstructionUtil.getIntention(list, AIApiConstants.Nlp.AuxiliaryIntention);
                if (intention == null || intention2 == null) {
                    L.weather.w("on nlp result");
                    return;
                }
                MiWeather translateWeather = new MiWeather().translateWeather((Template.WeatherV2) intention.getPayload(), intention2);
                if (translateWeather == null || !translateWeather.isDataValid()) {
                    L.weather.i("Weather data is invalid");
                    return;
                }
                WeatherDataManager.this.c = translateWeather;
                synchronized (WeatherDataManager.class) {
                    if (!(WeatherDataManager.this.b == null || WeatherDataManager.this.b.get() == null)) {
                        ((MiWeatherListener) WeatherDataManager.this.b.get()).onWeatherLoaded(translateWeather);
                    }
                }
                if (WeatherDataManager.this.a != null && WeatherDataManager.this.a.size() > 0) {
                    for (MiWeatherListener miWeatherListener : WeatherDataManager.this.a) {
                        if (miWeatherListener != null) {
                            miWeatherListener.onWeatherLoaded(translateWeather);
                        }
                    }
                }
                WeatherDataManager.this.b();
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.NLPListener
            public void onNlpFail(String str, boolean z) {
                L.weather.i("on nlp fail");
                if (!(WeatherDataManager.this.b == null || WeatherDataManager.this.b.get() == null)) {
                    ((MiWeatherListener) WeatherDataManager.this.b.get()).onWeatherLoadedError(z);
                }
                if (WeatherDataManager.this.a != null && WeatherDataManager.this.a.size() > 0) {
                    for (MiWeatherListener miWeatherListener : WeatherDataManager.this.a) {
                        if (miWeatherListener != null) {
                            miWeatherListener.onWeatherLoadedError(z);
                        }
                    }
                }
            }
        });
    }

    public void b() {
        AsyncTaskUtils.runAsync(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDataManager$rIBY4Z59dKBgdHQssJvekbECuF8
            @Override // java.lang.Runnable
            public final void run() {
                WeatherDataManager.this.e();
            }
        });
    }

    public /* synthetic */ void e() {
        if (this.c != null) {
            ApiRealmHelper.getInstance().updateAsync("mina/weather/nlp", Gsons.getGson().toJson(this.c));
        }
    }

    private void c() {
        AsyncTaskUtils.runAsync(new Runnable() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDataManager$0Pr8o8UNOGxIMi61-wBtP9Ph_xE
            @Override // java.lang.Runnable
            public final void run() {
                WeatherDataManager.this.d();
            }
        });
    }

    public /* synthetic */ void d() {
        String find = ApiRealmHelper.getInstance().find("mina/weather/nlp");
        if (!TextUtils.isEmpty(find)) {
            this.c = (MiWeather) Gsons.getGson().fromJson(find, new TypeToken<MiWeather>() { // from class: com.xiaomi.micolauncher.module.weather.WeatherDataManager.2
            }.getType());
        }
    }

    private void c(MiWeatherListener miWeatherListener) {
        List<MiWeatherListener> list;
        if (miWeatherListener != null && (list = this.a) != null) {
            list.add(miWeatherListener);
        }
    }

    public void b(final MiWeatherListener miWeatherListener) {
        List<MiWeatherListener> list = this.a;
        if (list != null && list.size() > 0) {
            this.a.removeIf(new Predicate() { // from class: com.xiaomi.micolauncher.module.weather.-$$Lambda$WeatherDataManager$B3DVD0WWkQBV6rhK4gsv0uKHLR8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean a2;
                    a2 = WeatherDataManager.a(WeatherDataManager.MiWeatherListener.this, (WeatherDataManager.MiWeatherListener) obj);
                    return a2;
                }
            });
        }
    }
}
