package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.controllers.AccountController;
import com.xiaomi.micolauncher.application.controllers.StatusBarController;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.control.SchemaUiStarter;
import com.xiaomi.micolauncher.module.appstore.control.AppStoreUiStarter;
import com.xiaomi.micolauncher.module.battery.BatteryUiController;
import com.xiaomi.micolauncher.module.faceDetection.FaceDistanceDetectController;
import com.xiaomi.micolauncher.module.localalbum.AlbumAddedEventReceiver;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmUiStarter;
import com.xiaomi.micolauncher.skills.alarm.view.HourlyChimeUiStarter;
import com.xiaomi.micolauncher.skills.baike.controller.BaikeUiStarter;
import com.xiaomi.micolauncher.skills.miot.control.MiotUiStarter;
import com.xiaomi.micolauncher.skills.soundboxcontrol.view.SoundBoxControlUiStarter;
import com.xiaomi.micolauncher.skills.translation.control.TranslationUiStarter;
import com.xiaomi.micolauncher.skills.video.controller.VideoPlayStarter;
import com.xiaomi.micolauncher.skills.weather.WeatherUiStarter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class EventBusSetup implements ISetupRule {
    private boolean a = false;
    private Map<Class, Object> b = new ConcurrentHashMap();

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        if (!this.a) {
            LiveEventBus.config().lifecycleObserverAlwaysActive(false).autoClear(true).enableLogger(false);
            a(new AlbumAddedEventReceiver(context));
            a(new AlarmUiStarter(context));
            a(new AccountController(context));
            a(new StatusBarController(context));
            a(new SoundBoxControlUiStarter(context));
            a(new VideoPlayStarter(context));
            a(new TranslationUiStarter(context));
            a(new BaikeUiStarter(context));
            a(new WeatherUiStarter(context));
            a(new SchemaUiStarter(context));
            a(new MiotUiStarter(context));
            a(new AppStoreUiStarter(context));
            a(new HourlyChimeUiStarter(context));
            if (Hardware.hasCameraCapability(context)) {
                a(new FaceDistanceDetectController(context));
            }
            a(new BatteryUiController(context));
            this.a = true;
        }
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        List<Object> a = a();
        if (!ContainerUtil.isEmpty(a)) {
            for (Object obj : a) {
                b(obj);
            }
            this.a = false;
        }
    }

    private void a(Object obj) {
        L.eventbus.d("register %s", obj);
        this.b.put(obj.getClass(), obj);
        EventBusRegistry.getEventBus().register(obj);
    }

    private void b(Object obj) {
        L.eventbus.d("unregister %s", obj);
        this.b.remove(obj.getClass());
        EventBusRegistry.getEventBus().unregister(obj);
    }

    public <T> T getRegistered(Class<T> cls) {
        try {
            return (T) this.b.get(cls);
        } catch (ClassCastException unused) {
            L.eventbus.e("EventBusRegistry failed to cast %s to %s", this.b.get(cls), cls);
            return null;
        }
    }

    private List<Object> a() {
        return new ArrayList(this.b.values());
    }
}
