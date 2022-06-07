package com.xiaomi.micolauncher.module.homepage.manager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.MinaResponse;
import com.xiaomi.micolauncher.api.model.Music;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.service.MinaService;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.childsong.ChildSongApiHelper;
import com.xiaomi.micolauncher.module.homepage.event.ChildSongFavUpdateEvent;
import com.xiaomi.micolauncher.module.homepage.event.ChildSongRecentCoverUpdateEvent;
import com.xiaomi.micolauncher.module.homepage.event.HomeChildSongLoadEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadChildSongFirstScreenSuccessEvent;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Response;

/* loaded from: classes3.dex */
public class ChildSongDataManager {
    public static final String ID_CHILD_SONG_RECOMMEND = "341";
    public static final String TYPE_CHILD_SONG_RECOMMEND = "radio";
    private static final long a = TimeUnit.HOURS.toMillis(6);
    private SparseArray<PatchWall.Block> b;
    private CompositeDisposable c;
    private String d;
    private String e;
    private String f;
    private PatchWall.Block g;
    private final Object h;
    private Handler i;

    private ChildSongDataManager() {
        this.b = new SparseArray<>();
        this.c = new CompositeDisposable();
        this.g = new PatchWall.Block();
        this.h = new Object();
        this.i = new Handler(ThreadUtil.getWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$ChildSongDataManager$3z3T8B8P01QfcObR4v7nKPbj8vE
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = ChildSongDataManager.this.a(message);
                return a2;
            }
        });
        EventBusRegistry.getEventBus().register(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static ChildSongDataManager a = new ChildSongDataManager();
    }

    public static ChildSongDataManager getManager() {
        return a.a;
    }

    public void clearCache() {
        this.i.removeCallbacksAndMessages(null);
        this.b.clear();
        synchronized (this.h) {
            if (this.c != null) {
                this.c.dispose();
            }
        }
    }

    public /* synthetic */ boolean a(Message message) {
        loadHomeChildSong();
        return false;
    }

    public void loadHomeChildSong() {
        this.i.removeCallbacksAndMessages(null);
        a();
        b();
        c();
        loadRecentPlayItemCover();
        this.i.sendEmptyMessageDelayed(0, a);
    }

    private void a() {
        if (ApiManager.isInited()) {
            a(ApiManager.minaService.getQQMusicSongs(ID_CHILD_SONG_RECOMMEND, "radio").subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$ChildSongDataManager$7VlkJtLPGVWN7yUnU-3o2SpCK84
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildSongDataManager.this.a((Response) obj);
                }
            }));
        }
    }

    public /* synthetic */ void a(Response response) throws Exception {
        MinaResponse minaResponse = (MinaResponse) response.body();
        if (minaResponse != null) {
            PatchWall.Block block = new PatchWall.Block();
            block.blockUiType = new PatchWall.BlockUIType();
            block.blockUiType.name = PatchWall.BLOCK_RECOMMENDATION;
            block.items = new ArrayList();
            List<Music.Song> list = ((Music.SongBook) minaResponse.data).songList;
            for (Music.Song song : list) {
                if (TextUtils.isEmpty(song.origin) || TextUtils.isEmpty(song.originSongID) || song.isNoPlayable()) {
                    list.remove(song);
                } else {
                    PatchWall.Item item = new PatchWall.Item();
                    item.id = song.originSongID;
                    item.target = song.origin;
                    item.title = song.name;
                    item.images = new PatchWall.Image();
                    item.images.poster = new PatchWall.Poster();
                    item.images.poster.url = song.coverURL;
                    block.items.add(item);
                }
            }
            block.songList = list;
            a(0, block);
        }
    }

    private void b() {
        a(ChildSongApiHelper.getInstance().loadCategoryList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$ChildSongDataManager$WIEZ_fdF4zmRzZ7gsxaIM_vsbsE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongDataManager.this.a((List<Music.Category>) obj);
            }
        }, $$Lambda$ChildSongDataManager$22Xkk0mnJMW3u2Y5k9kxZnfYdk.INSTANCE));
    }

    public static /* synthetic */ void c(Throwable th) throws Exception {
        L.childmode.e("loadChineseEnglishSong#loadCategoryList onFailed: ", th);
    }

    public void a(List<Music.Category> list) {
        if (ContainerUtil.isEmpty(list)) {
            L.childmode.e("ChildSongDataManager#loadChineseEnglishSong categories is empty");
            return;
        }
        int i = 0;
        for (Music.Category category : list) {
            if (category.getCategoryName().equals("中文儿歌")) {
                a(category, 3);
                i++;
            } else if (category.getCategoryName().equals("英文儿歌")) {
                a(category, 6);
                i++;
            }
            if (i >= 2) {
                return;
            }
        }
    }

    private void a(Music.Category category, final int i) {
        final long categoryId = category.getCategoryId();
        final String categoryName = category.getCategoryName();
        MinaService minaService = ApiManager.minaService;
        a(minaService.getMusicCategoryList(categoryId + "", categoryName, 0, 50).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$ChildSongDataManager$7GfBClQcJRiI05ZmwABbXzlOyO0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongDataManager.this.b(i, (PatchWall.Category) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$ChildSongDataManager$VfDhG7d0pOzjxKWsl6AqxzMrohg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongDataManager.a(categoryId, categoryName, (Throwable) obj);
            }
        }));
    }

    public static /* synthetic */ void a(long j, String str, Throwable th) throws Exception {
        Logger logger = L.childmode;
        logger.e("ChildSongDataManager#loadSongList categoryId = " + j + ", categoryName= " + str, th);
    }

    private void c() {
        if (ApiManager.isInited()) {
            a(ApiManager.minaService.getFavoriteSongBookList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$ChildSongDataManager$tekRV8-SIygE2jPab8ctz8vgF8g
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildSongDataManager.this.d((List) obj);
                }
            }, $$Lambda$ChildSongDataManager$5BbGdqv0hrBhy9t4EPv7NOA3qV4.INSTANCE));
        }
    }

    public /* synthetic */ void d(List list) throws Exception {
        if (ContainerUtil.isEmpty(list)) {
            L.childmode.e("ChildSongDataManager#loadBabyLikeListUrl#getFavoriteSongBookList ContainerUtil.isEmpty(songBookList)");
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PatchWall.FavoriteSongBook favoriteSongBook = (PatchWall.FavoriteSongBook) it.next();
            if (favoriteSongBook.isBabyLike()) {
                this.e = favoriteSongBook.cover;
                this.d = new SquareItem(favoriteSongBook).target;
                a(new ChildSongFavUpdateEvent(this.e));
                return;
            }
        }
    }

    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.childmode.e("loadBabyLikeListUrl#getFavoriteSongBookList failed", th);
    }

    public void loadRecentPlayItemCover() {
        if (ApiManager.isInited()) {
            a(ApiManager.minaService.audioHistory().map($$Lambda$ChildSongDataManager$p9yPJIVPvmOqDYG6qhWT5ePGUAI.INSTANCE).flatMap($$Lambda$ChildSongDataManager$GULgOqGewsVeEvURyHn6jy3ccI.INSTANCE).subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$ChildSongDataManager$uWz2EmbuPRAqjSZOCrp9PR1Nz80
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildSongDataManager.this.a((Music.Song) obj);
                }
            }, $$Lambda$ChildSongDataManager$r9bsfUeaFvfw2eLGcWSAdWYEnvs.INSTANCE));
        }
    }

    public static /* synthetic */ Long c(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            return (Long) list.get(0);
        }
        return 0L;
    }

    public static /* synthetic */ ObservableSource a(Long l) throws Exception {
        return ApiManager.minaService.getSongInfo(l.longValue());
    }

    public /* synthetic */ void a(Music.Song song) throws Exception {
        if (song != null) {
            this.f = song.getItemImageUrl();
            a(new ChildSongRecentCoverUpdateEvent(this.f));
        }
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.childmode.e("ChildSongDataManager#loadRecentPlayItemCover failed", th);
    }

    public String getRecentPlayCover() {
        return this.f;
    }

    public String getBabyLikeCover() {
        return this.e;
    }

    public void playRecent(Context context) {
        PlayerApi.playRecently(context);
        MusicStatHelper.recordMusicClick(MusicStatHelper.MusicVal.MUSIC_RECENT);
    }

    public void playBabyLike(Context context) {
        if (ContainerUtil.isEmpty(this.d)) {
            ToastUtil.showToast((int) R.string.collect_some_now);
            return;
        }
        String queryParameter = Uri.parse(this.d).getQueryParameter("id");
        if (!ContainerUtil.isEmpty(queryParameter)) {
            PlayerApi.playSheet(context, queryParameter, LoopType.SHUFFLE);
        }
    }

    public static void playPatchWallItem(Context context, PatchWall.Item item) {
        if (item != null && !ContainerUtil.isEmpty(item.target)) {
            SchemaManager.handleSchema(context, item.target);
        }
    }

    public boolean hasData() {
        return ContainerUtil.hasData(this.b);
    }

    public PatchWall.Block getBlock(int i) {
        if (i == 2) {
            return this.g;
        }
        switch (i) {
            case 4:
                return this.g;
            case 5:
                return this.g;
            default:
                return this.b.get(i);
        }
    }

    private boolean a(int i) {
        return this.b.get(i) == null;
    }

    /* renamed from: a */
    public void b(int i, PatchWall.Category category) {
        if (!ContainerUtil.isEmpty(category.items)) {
            PatchWall.Block block = new PatchWall.Block();
            block.items = b(category.items);
            a(i, block);
        }
    }

    private void a(int i, PatchWall.Block block) {
        if (a(i)) {
            if (i == 0) {
                a(new LoadChildSongFirstScreenSuccessEvent(block));
            }
            a(new HomeChildSongLoadEvent(i, block));
        }
        this.b.put(i, block);
    }

    private void a(Object obj) {
        EventBusRegistry.getEventBus().post(obj);
    }

    private void a(Disposable disposable) {
        synchronized (this.h) {
            L.childmode.e("ChildSongDataManager addDispose invoked111");
            if (this.c == null || this.c.isDisposed()) {
                this.c = new CompositeDisposable();
                L.childmode.e("ChildSongDataManager addDispose invoked222");
            }
            this.c.add(disposable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <E> List b(List<E> list) {
        if (list.size() <= 6) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list);
        ArrayList arrayList2 = new ArrayList(6);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            arrayList2.add(arrayList.remove(random.nextInt(arrayList.size())));
        }
        return arrayList2;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiConnectivityChangedEvent(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected && a(0)) {
            L.childmode.i("ChildSongDataManager reload data after network connected");
            loadHomeChildSong();
        }
    }
}
