package com.xiaomi.micolauncher.module.station.manager;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Station;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class StationDataManager {
    private static final long a = TimeUnit.HOURS.toMillis(8);
    private List<Station.Category> b;
    private Map<Long, Station.Block> c;
    private long d;

    private StationDataManager() {
        this.b = new ArrayList();
        this.c = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static StationDataManager a = new StationDataManager();
    }

    public static StationDataManager getManager() {
        return a.a;
    }

    public Observable<List<Station.Category>> loadCategoryList() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.station.manager.-$$Lambda$StationDataManager$En7FZCDGJB7TdQ-O1cCcgy49M-E
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                StationDataManager.this.a(observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        if (ContainerUtil.isEmpty(this.b) || !a()) {
            a((Emitter) observableEmitter);
            return;
        }
        observableEmitter.onNext(this.b);
        observableEmitter.onComplete();
    }

    private void a(final Emitter emitter) {
        ApiManager.displayService.getStationCategoryList().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.manager.-$$Lambda$StationDataManager$A3m7Ix_EF4YBWs9BVdOW5rdmm5w
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationDataManager.this.a(emitter, (List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.station.manager.-$$Lambda$StationDataManager$j1tGlMn4bJDTYTi2VaSZhW548c4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Emitter.this.onError((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Emitter emitter, List list) throws Exception {
        this.d = System.currentTimeMillis();
        this.b = list;
        emitter.onNext(list);
        emitter.onComplete();
    }

    public Observable<Station.Block> loadCategoryContent(final long j, final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.station.manager.-$$Lambda$StationDataManager$OzbLPeUv4yVRwn1pUwYnDsVTPPQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                StationDataManager.this.a(j, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, String str, ObservableEmitter observableEmitter) throws Exception {
        if (this.c.getOrDefault(Long.valueOf(j), null) != null) {
            observableEmitter.onNext(this.c.get(Long.valueOf(j)));
            observableEmitter.onComplete();
            return;
        }
        a(observableEmitter, j, str);
    }

    private void a(final Emitter emitter, final long j, final String str) {
        ApiManager.displayService.getStationCategoryContent(j, 0, 50).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.manager.-$$Lambda$StationDataManager$S1qaIyOuM2gLoSxfw-NScvdWhNA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationDataManager.this.a(j, str, emitter, (List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.station.manager.-$$Lambda$StationDataManager$j7Tf5caeD-tggevDZSItDHgKs1o
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Emitter.this.onError((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, String str, Emitter emitter, List list) throws Exception {
        Station.Block block = new Station.Block(j, str, list);
        this.c.put(Long.valueOf(j), block);
        emitter.onNext(block);
        emitter.onComplete();
    }

    private boolean a() {
        return Math.abs(System.currentTimeMillis() - this.d) <= a;
    }
}
