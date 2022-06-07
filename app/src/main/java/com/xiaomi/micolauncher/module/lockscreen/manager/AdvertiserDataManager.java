package com.xiaomi.micolauncher.module.lockscreen.manager;

import android.content.Context;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;

/* loaded from: classes3.dex */
public class AdvertiserDataManager {
    private static volatile AdvertiserDataManager a;

    public static AdvertiserDataManager getManager(Context context) {
        if (a == null) {
            synchronized (AdvertiserDataManager.class) {
                if (a == null) {
                    a = new AdvertiserDataManager(context);
                }
            }
        }
        return a;
    }

    private AdvertiserDataManager(Context context) {
        a(context);
    }

    private void a(Context context) {
        ApiManager.displayService.getAdvertiserCode(Constants.getSn(), Hardware.current(context).getName()).subscribeOn(MicoSchedulers.io()).subscribe($$Lambda$AdvertiserDataManager$h9W8GtngFJTvlq2skOYTtOk4e4.INSTANCE, $$Lambda$AdvertiserDataManager$BaZQGZDvs7EQ_ZCE_0gj6iYHQY.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Long l) throws Exception {
        SystemSetting.setLockScreenAdvertiser(l.longValue());
    }
}
