package com.xiaomi.micolauncher.module.lockscreen.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import com.bumptech.glide.Glide;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.utils.Util;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WallPaperDataManager {
    public static final String ALARM_AD_URL = "mina/display/mainpage/get_ad/alarm";
    private static final long a = TimeUnit.SECONDS.toMillis(5);
    private static final long b = TimeUnit.SECONDS.toMillis(10);
    private Picture.Pictorial c;
    private Picture.Pictorial d;
    private Handler e;
    private Context f;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static WallPaperDataManager a = new WallPaperDataManager();
    }

    public static WallPaperDataManager getManager() {
        return a.a;
    }

    private WallPaperDataManager() {
        this.e = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.WallPaperDataManager.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    WallPaperDataManager.this.loadAdData();
                }
            }
        };
        this.f = MicoApplication.getGlobalContext();
        this.f.registerReceiver(new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.WallPaperDataManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                L.lockscreen.i(" action date changed");
                WallPaperDataManager.this.sendRefreshDataMsgDelayed(RandomTimeUtils.getRandomTimeInMills(TimeUnit.HOURS.toMillis(5L)));
            }
        }, new IntentFilter("android.intent.action.DATE_CHANGED"));
    }

    public void loadAdData() {
        this.e.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WallPaperDataManager$GjMgCMP1lW3gsValDzs_kItGKls
            @Override // java.lang.Runnable
            public final void run() {
                WallPaperDataManager.this.b();
            }
        }, a);
        this.e.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WallPaperDataManager$KzntfxrucIdiCwhuW4nJOnibNvc
            @Override // java.lang.Runnable
            public final void run() {
                WallPaperDataManager.this.a();
            }
        }, b);
    }

    public Picture.Pictorial getWakeAd() {
        return this.c;
    }

    public Picture.Pictorial getAlarmAd() {
        return this.d;
    }

    public void a() {
        ApiManager.displayService.getAds(Hardware.current(this.f).getName(), "1.117.d.1", c()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WallPaperDataManager$Rp4QpAvHxdIy27kqg9HqO_r3NSo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WallPaperDataManager.this.b((Picture.Advertise) obj);
            }
        }, $$Lambda$WallPaperDataManager$ix_D4tpegdi0uh714EzFb6dKgPM.INSTANCE);
    }

    public /* synthetic */ void b(Picture.Advertise advertise) throws Exception {
        if (advertise == null || !ContainerUtil.hasData(advertise.adList)) {
            this.c = null;
            return;
        }
        ApiRealmHelper.getInstance().updateAsync("1.117.d.1", Gsons.getGson().toJson(advertise.adList.get(0)));
        this.c = advertise.adList.get(0);
        L.homepage.d("load wake ad  : %s", this.c.url);
        Glide.with(this.f).load(this.c.url).preload();
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.homepage.d("load wake ad error : ", th);
    }

    public void b() {
        ApiManager.displayService.getAds(Hardware.current(this.f).getName(), "1.117.c.1", c()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WallPaperDataManager$Vp_AoX3GzrJnbWpwgDYMUeUisRM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WallPaperDataManager.this.a((Picture.Advertise) obj);
            }
        });
    }

    public /* synthetic */ void a(Picture.Advertise advertise) throws Exception {
        if (advertise == null || !ContainerUtil.hasData(advertise.adList)) {
            this.d = null;
            return;
        }
        ApiRealmHelper.getInstance().updateAsync(ALARM_AD_URL, Gsons.getGson().toJson(advertise.adList.get(0)));
        this.d = advertise.adList.get(0);
        Glide.with(this.f).load(this.d.url).preload();
    }

    private String c() {
        HashMap hashMap = new HashMap();
        hashMap.put(OneTrack.Param.IMEI_MD5, Util.getMD5(SystemSetting.getDeviceID()));
        hashMap.put(OneTrack.Param.OAID, Settings.System.getString(this.f.getContentResolver(), "android_id"));
        hashMap.put("model", Build.MODEL);
        hashMap.put("device", Build.DEVICE);
        hashMap.put("androidVersion", Build.VERSION.RELEASE);
        return Gsons.getGson().toJson(hashMap);
    }

    public void sendRefreshDataMsgDelayed(long j) {
        this.e.sendEmptyMessageDelayed(1, j);
    }
}
