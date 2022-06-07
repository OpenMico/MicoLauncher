package com.xiaomi.micolauncher.application.setup.afterlogin;

import android.content.Context;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.SmartHomeRc4Manager;
import com.xiaomi.micolauncher.api.model.Miot;
import com.xiaomi.micolauncher.application.LoginModel;
import com.xiaomi.micolauncher.application.setup.ISetupRule;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.crash.DeviceInfo;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.QuickSettingHelper;
import com.xiaomi.micolauncher.module.lockscreen.manager.AdvertiserDataManager;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.module.weather.WeatherDataManager;
import com.xiaomi.micolauncher.skills.wakeup.WakeDataHelper;
import com.xiaomi.micolauncher.skills.weather.model.Weather;
import com.xiaomi.miot.support.MiotManager;
import java.util.Optional;

/* loaded from: classes3.dex */
public class AsynclyHomeModelsSetup implements ISetupRule {
    private volatile Context a;

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        this.a = context;
        L.base.json(Gsons.getGson().toJson(DeviceInfo.get(context)));
        L.base.d("recommend data manager load data");
        RecommendDataManager.getManager().loadData(context);
        MiotDeviceManager.MiotAccessToken miotAccessToken = MiotDeviceManager.getInstance().getMiotAccessToken();
        if (miotAccessToken != null && LoginModel.getInstance().hasUid()) {
            ApiHelper.uploadMiotAccessToken(miotAccessToken.getAccessToken(), miotAccessToken.getSessionId());
        }
        L.base.d("AsynclyHomeModelsSetup uploadMiotToken.");
        SpeechManager.getInstance().start();
        SpeechManager.getInstance().forceRefreshToken();
        L.base.d("AsynclyHomeModelsSetup init SpeechManager.");
        MiotProvisionManagerWrapper.getInstance().init(context);
        L.base.d("AsynclyHomeModelsSetup init MiotProvisionManagerWrapper.");
        a();
        QuickSettingHelper.enable(context, true);
        WeatherDataManager.getManager();
        L.base.d("WeatherDataManager init done");
        AdvertiserDataManager.getManager(context);
        L.base.d("AdvertiserDataManager init done");
        Weather.CLEAR.getDrawId();
        SystemSetting.setKeyIsTempChild(false);
        WakeDataHelper.getHelper().getOfflineSuggests();
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        RecommendDataManager.getManager().clear();
        NetworkMonitor.getInstance().destroy(this.a);
    }

    private void a() {
        SmartHomeRc4Manager.getInstance().getRoomInfo().subscribeOn(MicoSchedulers.io()).subscribe(new DefaultObserver<Optional<Miot.Room>>() { // from class: com.xiaomi.micolauncher.application.setup.afterlogin.AsynclyHomeModelsSetup.1
            /* renamed from: a */
            public void onSuccess(Optional<Miot.Room> optional) {
                if (optional.isPresent()) {
                    Miot.Room room = optional.get();
                    MiotManager.onHostDeviceHomeIdUpdated(room.parentid);
                    MiotManager.onHostDeviceRoomIdUpdated(room.id);
                }
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                super.onFail(apiError);
                L.miot.i("load room info failed");
            }
        });
        L.base.d("AsynclyHomeModelsSetup init SmartHomeRc4Manager.");
    }
}
