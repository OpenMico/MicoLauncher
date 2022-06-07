package com.xiaomi.micolauncher.skills.wakeup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.xiaomi.ai.android.helper.WakeUpGuideHelper;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.WakeGuide;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.utils.Util;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.common.LimitedSizeList;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MicoAppCommandProcessor;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WakeDataHelper {
    Observable a;
    private WakeUpGuideHelper b;
    private WakeGuide.Device c;
    private WakeGuide.WakeRequestBody d;
    private WakeGuide.AnswerBean e;
    private List<String> f;
    private WakeGuide.AnswerBean g;
    private PackageInfo h;
    private boolean i;
    private boolean j;
    private QuerySource k;
    private String l;
    private String m;

    /* loaded from: classes3.dex */
    public enum QuerySource {
        OFFLINE,
        ONLINE
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final WakeDataHelper a = new WakeDataHelper();
    }

    public static WakeDataHelper getHelper() {
        return a.a;
    }

    private WakeDataHelper() {
        this.f = new LimitedSizeList(100);
        this.a = Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.wakeup.-$$Lambda$WakeDataHelper$InkYrEMFFiKijP7fXwbBQ4Cg_co
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                WakeDataHelper.this.a(observableEmitter);
            }
        });
        a();
    }

    private void a() {
        this.b = SpeechManager.getInstance().getWakeUpGuideHelper();
        this.c = new WakeGuide.Device();
        this.c.setDevice_id(SystemSetting.getDeviceID());
        this.c.setApp_id(MibrainConfig.getMibrainConfig(MicoApplication.getGlobalContext()).clientId);
        WakeGuide.UserInfo userInfo = new WakeGuide.UserInfo();
        userInfo.setId(LoginManager.get().getUserId());
        this.d = new WakeGuide.WakeRequestBody();
        this.d.setUser_info(userInfo);
    }

    private int a(Context context, String str) {
        try {
            this.h = context.getPackageManager().getPackageInfo(str, 0);
            return this.h.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            L.exception.e("get app version exception : ", e);
            return 1;
        }
    }

    private String b() {
        PackageInfo packageInfo = this.h;
        return packageInfo != null ? packageInfo.packageName : "com.xiaomi.micolauncher";
    }

    private String c() {
        return ActivityLifeCycleManager.getInstance().getTopActivityOrLauncher().getLocalClassName();
    }

    private String d() {
        String str = "";
        for (String str2 : this.f) {
            str = str.concat(str2).concat(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        }
        return str;
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        if (!this.j) {
            L.wakeguide.i("time out ! use memory cache");
            WakeGuide.AnswerBean answerBean = this.g;
            if (answerBean == null) {
                L.wakeguide.i("time out ! memory cache is null");
                String find = ApiRealmHelper.getInstance().find("cache_wake_guide");
                if (!TextUtils.isEmpty(find)) {
                    L.wakeguide.i("time out ! use disk cache");
                    this.g = (WakeGuide.AnswerBean) Gsons.getGson().fromJson(find, (Class<Object>) WakeGuide.AnswerBean.class);
                    WakeGuide.AnswerBean answerBean2 = this.g;
                    if (answerBean2 != null) {
                        observableEmitter.onNext(answerBean2);
                        this.i = true;
                        setQuerySource(QuerySource.OFFLINE);
                        return;
                    }
                    return;
                }
                return;
            }
            observableEmitter.onNext(answerBean);
            this.i = true;
            setQuerySource(QuerySource.OFFLINE);
        }
    }

    @SuppressLint({"CheckResult"})
    public Observable<Object> getSuggest(final Context context) {
        this.i = false;
        this.j = false;
        if (this.b == null) {
            a();
        }
        if (this.b != null) {
            return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.wakeup.-$$Lambda$WakeDataHelper$UddrWMxNZ7nxthBH4_O1z2fsk98
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    WakeDataHelper.this.a(context, observableEmitter);
                }
            }).timeout(600L, TimeUnit.MILLISECONDS, this.a);
        }
        L.wakeguide.d("wakeUpGuideHelper is null use cache");
        return this.a;
    }

    public /* synthetic */ void a(Context context, final ObservableEmitter observableEmitter) throws Exception {
        IAppCommandProcessor currentProcessor = ThirdPartyAppProxy.getInstance().getCurrentProcessor();
        if (currentProcessor == null) {
            currentProcessor = new MicoAppCommandProcessor(false);
        }
        int a2 = a(context, currentProcessor.getPackage());
        this.m = b();
        this.d.setApp_name(this.m);
        this.d.setApp_version(a2);
        this.d.setDevice(this.c);
        this.d.setRequest_origin("mi_sound");
        this.l = Util.getUUID();
        this.d.setTrace_id(this.l);
        this.d.setApp_page(c());
        this.d.setToday_impressions(d());
        WakeGuide.DeviceStatus deviceStatus = new WakeGuide.DeviceStatus();
        deviceStatus.setIs_kid_mode_on(ChildModeManager.getManager().isChildMode());
        this.d.setDevice_status(deviceStatus);
        String json = Gsons.getGson().toJson(this.d);
        L.wakeguide.i("wake guide request json is %s", json);
        this.b.getWakeUpGuideInfo(json, new WakeUpGuideHelper.WakeUpGuideCallback() { // from class: com.xiaomi.micolauncher.skills.wakeup.WakeDataHelper.1
            @Override // com.xiaomi.ai.android.helper.WakeUpGuideHelper.WakeUpGuideCallback
            public void onError(String str) {
                L.wakeguide.e("getWeakUpGuideInfo error %s", str);
            }

            @Override // com.xiaomi.ai.android.helper.WakeUpGuideHelper.WakeUpGuideCallback
            public void onSuccess(String str) {
                if (DebugHelper.isDebugVersion()) {
                    L.wakeguide.i("getWeakUpGuideInfo success %s", str);
                }
                WakeGuide wakeGuide = (WakeGuide) Gsons.getGson().fromJson(str, (Class<Object>) WakeGuide.class);
                if (wakeGuide != null && wakeGuide.getStatus().getCode() == 200) {
                    WakeDataHelper.this.e = wakeGuide.getAnswer();
                    if (WakeDataHelper.this.e != null && ContainerUtil.hasData(WakeDataHelper.this.e.getSuggestions())) {
                        for (WakeGuide.AnswerBean.SuggestionsBean suggestionsBean : WakeDataHelper.this.e.getSuggestions()) {
                            WakeDataHelper.this.f.add(suggestionsBean.getId());
                        }
                        WakeDataHelper wakeDataHelper = WakeDataHelper.this;
                        wakeDataHelper.a(wakeDataHelper.e);
                        if (!WakeDataHelper.this.i) {
                            L.wakeguide.i("use net data");
                            WakeDataHelper.this.j = true;
                            WakeDataHelper.this.setQuerySource(QuerySource.ONLINE);
                            WakeDataHelper wakeDataHelper2 = WakeDataHelper.this;
                            wakeDataHelper2.a(observableEmitter, wakeDataHelper2.e);
                        }
                    }
                }
            }
        });
    }

    public void a(final WakeGuide.AnswerBean answerBean) {
        L.wakeguide.i("handle offline suggest");
        ThreadUtil.getWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.wakeup.-$$Lambda$WakeDataHelper$3B-czFpkPgT8W68U-bX76rbiTE8
            @Override // java.lang.Runnable
            public final void run() {
                WakeDataHelper.this.b(answerBean);
            }
        });
    }

    public /* synthetic */ void b(WakeGuide.AnswerBean answerBean) {
        WakeGuide.AnswerBean.OfflineSuggestion offlineSuggestion;
        if (answerBean != null && answerBean.getOfflineSuggestion() != null && (offlineSuggestion = answerBean.getOfflineSuggestion()) != null && !ContainerUtil.isEmpty(offlineSuggestion.getOfflineSuggestions())) {
            L.wakeguide.i("offline suggest data size %d", Integer.valueOf(ContainerUtil.getSize(offlineSuggestion.getOfflineSuggestions())));
            this.g = new WakeGuide.AnswerBean(answerBean);
            this.g.setSuggestions(offlineSuggestion.getOfflineSuggestions());
            ApiRealmHelper.getInstance().update("cache_wake_guide", Gsons.getGson().toJson(this.g));
        }
    }

    public void a(Emitter<Object> emitter, WakeGuide.AnswerBean answerBean) {
        if (!this.i) {
            if (answerBean == null || !ContainerUtil.hasData(answerBean.getSuggestions())) {
                String find = ApiRealmHelper.getInstance().find("cache_wake_guide");
                if (!TextUtils.isEmpty(find)) {
                    L.wakeguide.i("WakeDataHelper load data from cache");
                    WakeGuide.AnswerBean answerBean2 = (WakeGuide.AnswerBean) Gsons.getGson().fromJson(find, (Class<Object>) WakeGuide.AnswerBean.class);
                    if (answerBean2 != null) {
                        emitter.onNext(answerBean2);
                        return;
                    }
                    return;
                }
                L.wakeguide.i("WakeDataHelper load data from cache is null");
                return;
            }
            L.wakeguide.i("do on next suggest data size %d", Integer.valueOf(ContainerUtil.getSize(answerBean.getSuggestions())));
            emitter.onNext(answerBean);
        }
    }

    public void getOfflineSuggests() {
        L.wakeguide.i("init getOffline suggest");
        ThreadUtil.getIoThreadPool().execute(new Runnable() { // from class: com.xiaomi.micolauncher.skills.wakeup.-$$Lambda$WakeDataHelper$AD5S_9b8MErtM4R8WbDVCZnuCxQ
            @Override // java.lang.Runnable
            public final void run() {
                WakeDataHelper.this.e();
            }
        });
    }

    public /* synthetic */ void e() {
        String find = ApiRealmHelper.getInstance().find("cache_wake_guide");
        if (!TextUtils.isEmpty(find)) {
            L.wakeguide.i("getOffline suggests from db");
            this.g = (WakeGuide.AnswerBean) Gsons.getGson().fromJson(find, (Class<Object>) WakeGuide.AnswerBean.class);
            return;
        }
        L.wakeguide.e("offline cache is null");
    }

    public QuerySource getQuerySource() {
        return this.k;
    }

    public void setQuerySource(QuerySource querySource) {
        this.k = querySource;
    }

    public String getTraceId() {
        return this.l;
    }

    public String getCurrentAppName() {
        return this.m;
    }
}
