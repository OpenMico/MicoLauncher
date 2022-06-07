package com.xiaomi.micolauncher.common.stat;

import android.content.Context;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.StatModel;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MicoStatUtils {
    private static final String LOG_TAG = "[StatUtils]: ";

    public static void postStatLog(String str, String... strArr) {
        StatModel statModel = new StatModel();
        statModel.serialNumber = Constants.getSn();
        statModel.hardware = Hardware.current(MicoApplication.getApp()).getName();
        statModel.source = PlaybackTrackHelper.ROM_TAG;
        statModel.deviceId = SystemSetting.getDeviceID();
        statModel.version = SystemSetting.getRomVersion() + "|" + SystemSetting.getRomChannel();
        statModel.list = new ArrayList();
        if (strArr == null || strArr.length % 2 != 0) {
            L.statistics.w("args format error, key:" + str);
            return;
        }
        StatModel.StatEvent statEvent = new StatModel.StatEvent(str);
        for (int i = 0; i < strArr.length; i += 2) {
            StatModel.StatValue statValue = new StatModel.StatValue();
            statValue.t = strArr[i];
            statValue.v = strArr[i + 1];
            statValue.c = "1";
            statEvent.data.add(statValue);
        }
        statModel.list.add(statEvent);
        String postJson = statModel.toPostJson();
        L.statistics.i("postStatLog,jsonPlayload:%s", postJson);
        ApiManager.minaService.statLog(postJson).subscribeOn(MicoSchedulers.io()).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.common.stat.MicoStatUtils.1
            public void onSuccess(String str2) {
                L.statistics.i("postStatLog success,response:%s", str2);
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                L.statistics.i("postStatLog fail,response:%s", apiError.toString());
            }
        });
    }

    public static void recordEventMultiDimensions(String str, String... strArr) {
        StatModel.StatEvent.recordEventMultiDimensions(str, strArr);
    }

    public static void postEvents(Context context, StatUtils.StatPointsPostListener statPointsPostListener) {
        StatModel.postEvents(context, 10, statPointsPostListener);
    }

    public static void postEventsRightNow(Context context, StatUtils.StatPointsPostListener statPointsPostListener) {
        StatModel.postEvents(context, 0, statPointsPostListener);
    }
}
