package com.xiaomi.micolauncher.skills.alarm.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.skills.alarm.HourlyChimeService;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class HourlyChimeUtils {
    public static Observable<List<HourlyChimeObject>> loadHourlyChime() {
        return Observable.create($$Lambda$HourlyChimeUtils$k6ssuwt7au_XrNF3YdIL_sbiIzU.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        List<HourlyChimeObject> list;
        if (SystemSetting.getHourlyChimeEnable()) {
            HourlyChimeRealmHelper hourlyChimeRealmHelper = new HourlyChimeRealmHelper();
            list = hourlyChimeRealmHelper.querySetHourlyChime(true);
            hourlyChimeRealmHelper.close();
        } else {
            list = new ArrayList<>();
        }
        observableEmitter.onNext(list);
        observableEmitter.onComplete();
    }

    public static void startAlarmManager(HourlyChimeObject hourlyChimeObject, long j, int i) {
        L.hourlychime.d("startAlarmManager millis:%s, type: %d", Long.valueOf(j), Integer.valueOf(i));
        Intent intent = new Intent();
        intent.setClass(MicoApplication.getGlobalContext(), HourlyChimeService.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", i);
        bundle.putSerializable("data", hourlyChimeObject);
        intent.putExtra(HourlyChimeService.BUNDLE, bundle);
        ((AlarmManager) MicoApplication.getGlobalContext().getSystemService("alarm")).setExact(0, j, PendingIntent.getService(MicoApplication.getGlobalContext(), 0, intent, 134217728));
    }

    public static void cancelAlarmManager() {
        L.hourlychime.d("cancelAlarmManager");
        Intent intent = new Intent();
        intent.setClass(MicoApplication.getGlobalContext(), HourlyChimeService.class);
        ((AlarmManager) MicoApplication.getGlobalContext().getSystemService("alarm")).cancel(PendingIntent.getService(MicoApplication.getGlobalContext(), 0, intent, 134217728));
    }

    private HourlyChimeUtils() {
    }
}
