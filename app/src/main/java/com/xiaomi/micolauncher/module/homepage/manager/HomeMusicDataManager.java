package com.xiaomi.micolauncher.module.homepage.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.core.app.NotificationCompat;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.homepage.event.AuthStatusEvent;
import com.xiaomi.micolauncher.module.homepage.event.AutoPagingFailedEvent;
import com.xiaomi.micolauncher.module.homepage.event.AutoPagingSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadFirstScreenDataFailedEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadFirstScreenDataSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadMusicCacheSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadRecommendDataEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadRecommendDataFailedEvent;
import com.xiaomi.micolauncher.module.homepage.event.MusicItemDataChangeEvent;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class HomeMusicDataManager {
    private static final long a = TimeUnit.HOURS.toMillis(6);
    private static SparseArray<String> c = new SparseArray<>();
    private boolean e;
    private volatile HashMap<String, List<PatchWall.Group>> g;
    private SparseArray<PatchWall.Block> b = new SparseArray<>();
    private CompositeDisposable d = new CompositeDisposable();
    private Handler f = new Handler(Threads.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$DQDzOfOQEiUu5AT90jBCQBGQ8rc
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = HomeMusicDataManager.this.a(message);
            return a2;
        }
    });

    static {
        c.put(0, "/music/like");
        c.put(1, "/music/mv");
        c.put(2, "/music/recommend2");
        c.put(3, "/music/recommend3");
        c.put(4, "/music/recommend4");
        c.put(5, "/music/radio5");
        c.put(6, "/music/radio6");
        c.put(7, "/music/radio7");
        c.put(8, "/music/hot");
        c.put(9, "/music/singer5");
        c.put(10, "/music/singer6");
        c.put(11, "/music/singer7");
        c.put(12, "/music/category5");
        c.put(13, "/music/category6");
        c.put(14, "/music/category7");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static HomeMusicDataManager a = new HomeMusicDataManager();
    }

    public static HomeMusicDataManager getManager() {
        return a.a;
    }

    public void clearCache() {
        this.f.removeMessages(XiaomiOAuthConstants.SCOPE_MI_CLOUD_GALLERY);
        this.f.removeMessages(11001);
        this.f.removeMessages(11002);
        this.b.clear();
        this.d.clear();
        MusicDataManager.getManager().clearCache();
    }

    public SparseArray<PatchWall.Block> getCacheBlocks() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Message message) {
        switch (message.what) {
            case XiaomiOAuthConstants.SCOPE_MI_CLOUD_GALLERY /* 11000 */:
                EventBusRegistry.getEventBus().post(new RecommendEvent.RefreshFirstScreenMusicData());
                break;
            case 11001:
                EventBusRegistry.getEventBus().post(new RecommendEvent.RefreshMusicRecommend());
                break;
            case 11002:
                EventBusRegistry.getEventBus().post(new RecommendEvent.RefreshPageMusicData());
                break;
        }
        this.e = true;
        return false;
    }

    public boolean isDataChanged() {
        return this.e;
    }

    public void resetDataChanged() {
        this.e = false;
    }

    private void a(int i, PatchWall.Block block) {
        PatchWall.Block block2 = this.b.get(i);
        this.b.put(i, block);
        b(i, block);
        if (block2 != null && !block2.equals(block)) {
            a(new MusicItemDataChangeEvent(i, block));
        }
    }

    private void b(final int i, final PatchWall.Block block) {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$_mPv2MLcswkkd4qCNZRTITmMn8M
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HomeMusicDataManager.a(PatchWall.Block.this, i, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(PatchWall.Block block, int i, ObservableEmitter observableEmitter) throws Exception {
        ApiRealmHelper.getInstance().updateAsync(c.get(i), Gsons.getGson().toJson(block));
    }

    private HashMap<String, List<PatchWall.Group>> a() {
        return this.g != null ? this.g : new HashMap<>();
    }

    public void loadGroupFromDb() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$ZYhu7EaaJUqJGXSDzLV_hfPvqzA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HomeMusicDataManager.this.a(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("/music/group");
        if (!TextUtils.isEmpty(find)) {
            this.g = (HashMap) Gsons.getGson().fromJson(find, new TypeToken<HashMap<String, List<PatchWall.Group>>>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.HomeMusicDataManager.1
            }.getType());
        }
    }

    private void a(final HashMap<String, List<PatchWall.Group>> hashMap) {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$AkLEC7H09ep0A-Y22mh_vhY2NPc
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HomeMusicDataManager.a(hashMap, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(HashMap hashMap, ObservableEmitter observableEmitter) throws Exception {
        ApiRealmHelper.getInstance().updateAsync("/music/group", Gsons.getGson().toJson(hashMap));
    }

    private void a(final int i) {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$uiql0FSp2yb5ttW3Y-zu9Cr0DwA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HomeMusicDataManager.this.a(i, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$FgUOzUrPNgnWm2ikQiPvdrZjFpk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.c(i, (PatchWall.Block) obj);
            }
        }, $$Lambda$HomeMusicDataManager$B4N1jV0MACAdc8X1oixU2vyWU4.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find(c.get(i));
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (PatchWall.Block) Gsons.getGson().fromJson(find, new TypeToken<PatchWall.Block>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.HomeMusicDataManager.2
        }.getType()) : null);
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(int i, PatchWall.Block block) throws Exception {
        if (block != null) {
            this.b.put(i, block);
        }
        if (this.b.size() == 15) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < 15; i2++) {
                arrayList.add(this.b.get(i2));
            }
            a(new LoadMusicCacheSuccessEvent(arrayList));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(Throwable th) throws Exception {
        L.homepage.e("no music cache");
    }

    public void loadFirstScreenData() {
        this.f.removeMessages(XiaomiOAuthConstants.SCOPE_MI_CLOUD_GALLERY);
        this.d.add(loadFirstScreenMusicData().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$JOwTSvWRG3AOYgSoAbbcURLVIic
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.b((PatchWall) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$8g0mi2unM2CfSD0ufuZ36VI-9t8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.d((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(PatchWall patchWall) throws Exception {
        a(patchWall, 0, 1, new LoadFirstScreenDataSuccessEvent(patchWall));
        this.f.sendEmptyMessageDelayed(XiaomiOAuthConstants.SCOPE_MI_CLOUD_GALLERY, a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Throwable th) throws Exception {
        a(new LoadFirstScreenDataFailedEvent(th));
        if (!(th instanceof MusicDataManager.QQNotBindedException) && !(th instanceof MusicDataManager.QQExpiredException)) {
            a(0);
            a(1);
        }
    }

    public Observable<PatchWall> loadGuessYouLikeListen() {
        return c();
    }

    public Observable<PatchWall> loadFirstScreenMusicData() {
        return Observable.zip(loadGuessYouLikeListen(), b(), $$Lambda$HomeMusicDataManager$pauG9VCy1NPHyYYhMXilAZ_xoY.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ PatchWall a(PatchWall patchWall, PatchWall patchWall2) throws Exception {
        PatchWall patchWall3 = new PatchWall();
        patchWall3.blocks = new ArrayList();
        patchWall3.blocks.addAll(patchWall.blocks);
        patchWall3.blocks.addAll(patchWall2.blocks);
        return patchWall3;
    }

    private Observable<PatchWall> b() {
        return ApiManager.displayService.getQQPatchWallMv().flatMap($$Lambda$HomeMusicDataManager$bnKJVT8rNQyFzZqin6W9h0YfxM.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(PatchWall.Block block) throws Exception {
        PatchWall patchWall = new PatchWall();
        patchWall.blocks = new ArrayList();
        patchWall.blocks.add(block);
        return Observable.just(patchWall);
    }

    private void a(PatchWall patchWall, int i, int i2, Object obj) {
        if (patchWall != null && ContainerUtil.hasData(patchWall.blocks)) {
            a(obj);
            a(i, patchWall.blocks.get(0));
            if (patchWall.blocks.size() > 1) {
                a(i2, patchWall.blocks.get(1));
            }
        }
    }

    private Observable<PatchWall> c() {
        return ApiManager.minaService.getQQMusicSongs("0", NotificationCompat.CATEGORY_RECOMMENDATION, Hardware.HARDWARE.getName()).flatMap($$Lambda$HomeMusicDataManager$kzAdDHsixp0aqQeYVGqIGmhhAZA.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Music.SongBook songBook) throws Exception {
        PatchWall patchWall = new PatchWall();
        patchWall.blocks = new ArrayList();
        PatchWall.Block block = new PatchWall.Block();
        block.blockUiType = new PatchWall.BlockUIType();
        block.blockUiType.name = PatchWall.BLOCK_RECOMMENDATION;
        block.items = new ArrayList();
        List<Music.Song> list = songBook.songList;
        ArrayList arrayList = new ArrayList();
        for (Music.Song song : list) {
            if (!TextUtils.isEmpty(song.origin) && !TextUtils.isEmpty(song.originSongID) && !song.isNoPlayable()) {
                PatchWall.Item item = new PatchWall.Item();
                item.id = song.originSongID;
                item.target = song.origin;
                item.title = song.name;
                item.images = new PatchWall.Image();
                item.images.poster = new PatchWall.Poster();
                item.images.poster.url = song.coverURL;
                block.items.add(item);
                arrayList.add(song);
            }
        }
        block.songList = arrayList;
        patchWall.blocks.add(block);
        return Observable.just(patchWall);
    }

    private List<PatchWall.Block> a(PatchWall.Block block, int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < 6) {
            PatchWall.Block block2 = new PatchWall.Block();
            block2.blockUiType = block.blockUiType;
            block2.title = block.title;
            int i3 = i2 + 2;
            block2.items = block.items.subList(i2, i3);
            int i4 = i + 1;
            block2.viewType = i;
            block2.id = block.id + i4;
            arrayList.add(block2);
            a(block2.viewType, block2);
            i = i4;
            i2 = i3;
        }
        return arrayList;
    }

    public void loadMusicData(Context context, final int i) {
        this.f.removeMessages(11001);
        this.d.add(MusicDataManager.getManager().loadMusicDataWithCacheFilter(i, context).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$9Tp3ISCR6xEbE0AXM_IsnGe1b_E
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.a(i, (PatchWall) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$DCMT7u0osus3-KppT9STmQrPF30
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.c((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, PatchWall patchWall) throws Exception {
        if (patchWall != null && ContainerUtil.hasData(patchWall.blocks)) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(a(patchWall.blocks.get(0), 2));
            arrayList.addAll(a(patchWall.blocks.get(1), 5));
            patchWall.blocks = arrayList;
            a(new LoadRecommendDataEvent(patchWall));
        }
        Message obtain = Message.obtain();
        obtain.what = 11001;
        obtain.arg1 = i;
        this.f.sendMessageDelayed(obtain, a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Throwable th) throws Exception {
        a(new LoadRecommendDataFailedEvent(th));
        a(2);
        a(3);
        a(4);
        a(5);
        a(6);
        a(7);
    }

    public void autoPaging(Context context, final int i) {
        this.f.removeMessages(11002, Integer.valueOf(i));
        final Context applicationContext = context.getApplicationContext();
        this.d.add(MusicDataManager.getManager().loadMusicDataWithoutCacheAndAuthCheck(i).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$RiO1mwEkJAclgzWTVuuGqyXP2YA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.a(i, applicationContext, (PatchWall) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$dPZ6jWWQBuNaDJ9p8CxCETqfcQs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.a(i, (Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Context context, PatchWall patchWall) throws Exception {
        if (i == 1) {
            a(patchWall, context);
        } else if (i == 2) {
            a(patchWall);
        } else {
            a(new AutoPagingSuccessEvent(patchWall));
        }
        if (patchWall != null) {
            Message obtain = Message.obtain();
            obtain.what = 11002;
            obtain.obj = Integer.valueOf(i);
            this.f.sendMessageDelayed(obtain, a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Throwable th) throws Exception {
        a(new AutoPagingFailedEvent());
        L.homepage.e("auto page load failed : ", th);
        if (i == 1) {
            a(8);
            a(9);
            a(10);
            a(11);
            return;
        }
        a(12);
        a(13);
        a(14);
    }

    public void checkAuthStatus(Context context) {
        final Context applicationContext = context.getApplicationContext();
        MusicDataManager.getManager().checkAuthStatus().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$40UKXQBCkARZn7SyV_nw_uJF_yE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeMusicDataManager.this.a(applicationContext, (MIBrain.CPBindStatus) obj);
            }
        }, $$Lambda$HomeMusicDataManager$xF_ASFuNUduzfZfK9LbIT4p8HVk.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, MIBrain.CPBindStatus cPBindStatus) throws Exception {
        if (cPBindStatus.isExpired() || cPBindStatus.isNotBinded()) {
            a(new AuthStatusEvent());
        }
        PreferenceUtils.setSettingBoolean(context, HomePageUtils.QQ_MUSIC_BIND, !cPBindStatus.isExpired() && !cPBindStatus.isNotBinded());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.homepage.e("check auth status : ", th);
    }

    private void a(Object obj) {
        EventBusRegistry.getEventBus().post(obj);
    }

    private void a(final PatchWall patchWall) {
        for (final PatchWall.Block block : patchWall.blocks) {
            if (PatchWall.BLOCK_GRID_QQ_MUSIC_GROUP.equals(block.blockUiType.name)) {
                final HashMap<String, List<PatchWall.Group>> a2 = a();
                if (!ContainerUtil.isEmpty(a2)) {
                    for (int i = 0; i < block.items.size(); i++) {
                        block.items.get(i).setGroups(a2.get(block.items.get(i).title));
                    }
                    patchWall.blocks = a(patchWall.blocks.get(0), 12);
                    a(new AutoPagingSuccessEvent(12, patchWall));
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (PatchWall.Item item : block.items) {
                    arrayList.add(a(item.title));
                }
                this.d.add(Observable.zipIterable(arrayList, new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$FOxJG0LhkQQZHMM28VgLGta6EJs
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        Object[] a3;
                        a3 = HomeMusicDataManager.a(PatchWall.Block.this, a2, (Object[]) obj);
                        return a3;
                    }
                }, true, 10).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$UdVE9X4vZtTJ4ne-Gyo-kWut-2k
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        HomeMusicDataManager.this.a(patchWall, a2, (Object[]) obj);
                    }
                }, $$Lambda$HomeMusicDataManager$Oq7wVKfWJ4znTI4IZU57JXsntI.INSTANCE));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object[] a(PatchWall.Block block, HashMap hashMap, Object[] objArr) throws Exception {
        if (ContainerUtil.hasData(objArr)) {
            for (int i = 0; i < objArr.length; i++) {
                List<PatchWall.Group> list = (List) objArr[i];
                block.items.get(i).setGroups(list);
                hashMap.put(block.items.get(i).title, list);
            }
        }
        return objArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall patchWall, HashMap hashMap, Object[] objArr) throws Exception {
        patchWall.blocks = a(patchWall.blocks.get(0), 12);
        a(new AutoPagingSuccessEvent(12, patchWall));
        if (!ContainerUtil.isEmpty(hashMap)) {
            getManager().a((HashMap<String, List<PatchWall.Group>>) hashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.homepage.e("get music category groups : ", th);
    }

    private void a(PatchWall patchWall, Context context) {
        ArrayList arrayList = new ArrayList();
        PatchWall.Block block = null;
        PatchWall.Item item = null;
        PatchWall.Item item2 = null;
        for (PatchWall.Block block2 : patchWall.blocks) {
            if (PatchWall.BLOCK_GRID_HAS_DETAILS.equals(block2.blockUiType.name)) {
                for (PatchWall.Item item3 : block2.items) {
                    if (item3.title.equals(context.getString(R.string.newest_music_list))) {
                        item = item3;
                    }
                    if (item3.title.equals(context.getString(R.string.hottest_music_list))) {
                        item2 = item3;
                    }
                }
                block = block2;
            } else {
                arrayList.addAll(a(block2, 9));
            }
        }
        if (!(block == null || item == null || item2 == null)) {
            block.items.clear();
            block.items.add(item2);
            block.items.add(item);
            arrayList.add(0, block);
            patchWall.blocks = arrayList;
            a(8, block);
            a(new AutoPagingSuccessEvent(8, patchWall));
        }
    }

    private Observable<List<PatchWall.Group>> a(String str) {
        return ApiManager.minaService.getMusicGroupList(str);
    }
}
