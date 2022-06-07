package com.xiaomi.micolauncher.module.audiobooks.model;

import android.content.Context;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import io.reactivex.Observable;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioBookRepository {
    private static volatile AudioBookRepository a;

    private AudioBookRepository() {
    }

    public static AudioBookRepository getInstance() {
        if (a == null) {
            synchronized (AudioBookRepository.class) {
                if (a == null) {
                    a = new AudioBookRepository();
                }
            }
        }
        return a;
    }

    public void destroy() {
        a = null;
    }

    public Observable<AudioDiscoveryPage.TabListBean> getTabList() {
        return ApiManager.displayService.getDiscAudioTabList().subscribeOn(MicoSchedulers.io());
    }

    public Observable<AudioDiscoveryPage.Flow> getDiscAudioTabFlow(String str) {
        return ApiManager.displayService.getDiscAudioTabFlow(str).subscribeOn(MicoSchedulers.io());
    }

    public Observable<AudioDiscoveryPage.Flow.BlocksBean> getDiscAudioTabBlock(String str, long j) {
        return ApiManager.displayService.getDiscAudioTabBlock(str, j).subscribeOn(MicoSchedulers.io());
    }

    public Observable<Order> getPaymentAudioBooks() {
        return AudiobookDataManager.getManager().loadPaymentOrdersFor04(0L).subscribeOn(MicoSchedulers.io());
    }

    public Observable<List<Station.Item>> getCollectAudioBooks() {
        return AudiobookDataManager.getManager().loadCollectListFor04().subscribeOn(MicoSchedulers.io());
    }

    public Observable<List<Station.Item>> getHistory(Context context) {
        return AudiobookDataManager.getManager().loadHistoryFor04(context, 50).subscribeOn(MicoSchedulers.io());
    }
}
