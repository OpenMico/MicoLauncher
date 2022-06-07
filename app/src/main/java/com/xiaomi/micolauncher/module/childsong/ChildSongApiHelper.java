package com.xiaomi.micolauncher.module.childsong;

import android.annotation.SuppressLint;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.Music;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.service.MinaService;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildSongApiHelper {
    public static final String TAG = "ChildSongApiHelper";
    private static final long a = TimeUnit.HOURS.toMillis(8);
    private static volatile ChildSongApiHelper d;
    private long g;
    private String h;
    private final Object c = new Object();
    private CompositeDisposable b = new CompositeDisposable();
    private List<Music.Category> e = new ArrayList();
    private Map<Long, PatchWall.Category.Block> f = new HashMap();

    public static ChildSongApiHelper getInstance() {
        if (d == null) {
            synchronized (ChildSongApiHelper.class) {
                if (d == null) {
                    d = new ChildSongApiHelper();
                }
            }
        }
        return d;
    }

    @SuppressLint({"UseSparseArrays"})
    private ChildSongApiHelper() {
    }

    public Observable<List<Music.Category>> loadCategoryList() {
        return MusicDataManager.getManager().loadMusicSource().flatMap($$Lambda$ChildSongApiHelper$HCMWRL_rE9DjAu47M5YbZs6tXhY.INSTANCE).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$bjceKtGZ9z5WWQN9G6WgRIRfcVw
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ChildSongApiHelper.this.a((MIBrain.CPBindStatus) obj);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(String str) throws Exception {
        return MusicDataManager.getManager().a(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(MIBrain.CPBindStatus cPBindStatus) throws Exception {
        if (cPBindStatus.isExpired()) {
            throw new MusicDataManager.QQExpiredException();
        } else if (!cPBindStatus.isNotBinded()) {
            return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$WGUiR_lXq_MdnhF896r_n0srooI
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    ChildSongApiHelper.this.a(observableEmitter);
                }
            });
        } else {
            throw new MusicDataManager.QQNotBindedException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        if (ContainerUtil.isEmpty(this.e) || !a()) {
            a((Emitter) observableEmitter);
            return;
        }
        observableEmitter.onNext(this.e);
        observableEmitter.onComplete();
    }

    private void a(final Emitter emitter) {
        Observable<List<Music.Category>> musicCategoryList = ApiManager.displayService.getMusicCategoryList();
        Consumer<? super List<Music.Category>> consumer = new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$EOYUdy5Bz1q_Uf2Cw6TJYTRvPlo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongApiHelper.this.a(emitter, (List) obj);
            }
        };
        emitter.getClass();
        a(musicCategoryList.subscribe(consumer, new $$Lambda$AvoeS3eMM0SkXooQ0BfFkyndy_Q(emitter)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Emitter emitter, List list) throws Exception {
        this.g = System.currentTimeMillis();
        this.e = list;
        emitter.onNext(list);
        emitter.onComplete();
    }

    public Observable<PatchWall.Category.Block> loadCategoryContent(final long j, final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$oGQUcuXs8nv19r5BbhpgulCJWSo
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ChildSongApiHelper.this.a(j, str, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, String str, ObservableEmitter observableEmitter) throws Exception {
        if (this.f.getOrDefault(Long.valueOf(j), null) != null) {
            observableEmitter.onNext(this.f.get(Long.valueOf(j)));
            observableEmitter.onComplete();
            return;
        }
        a(observableEmitter, j, str);
    }

    private void a(final Emitter emitter, final long j, final String str) {
        MinaService minaService = ApiManager.minaService;
        Observable<PatchWall.Category> musicCategoryList = minaService.getMusicCategoryList(j + "", str, 0, 10);
        Consumer<? super PatchWall.Category> consumer = new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$zs4bjt8DDmlPtUYhfkMG9-D_w4Q
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongApiHelper.this.a(j, str, emitter, (PatchWall.Category) obj);
            }
        };
        emitter.getClass();
        a(musicCategoryList.subscribe(consumer, new $$Lambda$AvoeS3eMM0SkXooQ0BfFkyndy_Q(emitter)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, String str, Emitter emitter, PatchWall.Category category) throws Exception {
        PatchWall.Category.Block block = new PatchWall.Category.Block(j, str, category.items);
        this.f.put(Long.valueOf(j), block);
        emitter.onNext(block);
        emitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Observable<PatchWall.Category> a(final long j, final String str, final int i) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$Q4JKl3NRozqJUe4GrdFPi-SKgyY
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ChildSongApiHelper.this.a(j, str, i, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, String str, int i, final ObservableEmitter observableEmitter) throws Exception {
        MinaService minaService = ApiManager.minaService;
        Observable<PatchWall.Category> subscribeOn = minaService.getMusicCategoryList(j + "", str, i, 10).observeOn(MicoSchedulers.mainThread()).subscribeOn(MicoSchedulers.io());
        Consumer<? super PatchWall.Category> consumer = new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$o23mmJ391Z84_Rw-FhXAFjWpyp8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongApiHelper.a(ObservableEmitter.this, (PatchWall.Category) obj);
            }
        };
        observableEmitter.getClass();
        a(subscribeOn.subscribe(consumer, new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$VmLVXQu1wNscSYZdRZXpqdZ-dNg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ObservableEmitter.this.onError((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObservableEmitter observableEmitter, PatchWall.Category category) throws Exception {
        observableEmitter.onNext(category);
        observableEmitter.onComplete();
    }

    public void loadBabyLikeListUrl() {
        a(ApiManager.minaService.getFavoriteSongBookList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongApiHelper$Z4ViwMf4p8hG1LvJ-sexzSgwUmk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongApiHelper.this.a((List) obj);
            }
        }, $$Lambda$ChildSongApiHelper$AlMTgclvWuJNXCbWAqn7EV4ACHU.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        if (ContainerUtil.isEmpty(list)) {
            L.childmode.e(TAG, "loadBabyLikeListUrl#getFavoriteSongBookList ContainerUtil.isEmpty(songBookList)");
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PatchWall.FavoriteSongBook favoriteSongBook = (PatchWall.FavoriteSongBook) it.next();
            if (favoriteSongBook.isBabyLike()) {
                this.h = new SquareItem(favoriteSongBook).target;
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.childmode.e("loadBabyLikeListUrl#getFavoriteSongBookList failed", th);
    }

    public String getBabyLikeListUrl() {
        return this.h;
    }

    private boolean a() {
        return Math.abs(System.currentTimeMillis() - this.g) <= a;
    }

    void a(Disposable disposable) {
        synchronized (this.c) {
            L.childmode.e("ChildSongApiHelper addDispose invoked111");
            if (this.b == null || this.b.isDisposed()) {
                this.b = new CompositeDisposable();
                L.childmode.e("ChildSongApiHelper addDispose invoked222");
            }
            this.b.add(disposable);
        }
    }

    public void release() {
        synchronized (this.c) {
            if (this.b != null) {
                this.b.dispose();
                L.childmode.d("ChildSongApiHelper compositeDisposable is disposed!");
            }
        }
    }
}
