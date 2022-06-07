package com.xiaomi.micolauncher.skills.alarm.model;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PlayV3Pact;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class BabyCourseHelper {
    public static final int PAGE_SIZE = 20;

    public static Observable<Music.BabyCourse> loadBabyCourse(String str) {
        return ApiManager.minaService.nextCourse(SystemSetting.getDeviceID(), str, "", "");
    }

    public static Observable<Music.BabyCourse> nextCourse(final Music.BabyCourse babyCourse) {
        return ApiManager.minaService.nextCourse(SystemSetting.getDeviceID(), babyCourse.alarmId, babyCourse.playCPId, babyCourse.playCP).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$BabyCourseHelper$WIxC-7t8Pig1s_LbwKL-ltm7SSs
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = BabyCourseHelper.a(Music.BabyCourse.this, (Music.BabyCourse) obj);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Music.BabyCourse babyCourse, Music.BabyCourse babyCourse2) throws Exception {
        babyCourse2.alarmId = babyCourse.alarmId;
        return Observable.just(babyCourse2);
    }

    public static Observable<List<Music.Station>> loadStationList(Music.BabyCourse babyCourse) {
        return ApiManager.minaService.getStationSoundList(babyCourse.playCPId, babyCourse.playCP, "", babyCourse.playStationIndex(), 20, PlayV3Pact.NOT_REVERSE).flatMap($$Lambda$BabyCourseHelper$ClFVQsnKU153zKmqNDT7K82lrI.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Music.StationSoundList stationSoundList) throws Exception {
        if (stationSoundList == null || ContainerUtil.isEmpty(stationSoundList.soundList)) {
            return Observable.just(new ArrayList());
        }
        return Observable.just(stationSoundList.soundList);
    }
}
