package com.xiaomi.micolauncher.skills.music.controller;

import android.text.TextUtils;
import android.util.LruCache;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.MinaResponse;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class CollectionManager {
    private LruCache<String, Music.Favourite> a;
    private CopyOnWriteArrayList<CollectionListener> b;
    private Disposable c;

    /* loaded from: classes3.dex */
    public interface CollectionListener {
        void onCollectionChange(String str, boolean z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final CollectionManager a = new CollectionManager();
    }

    public static CollectionManager getInstance() {
        return a.a;
    }

    private CollectionManager() {
        this.a = new LruCache<>(500);
        this.b = new CopyOnWriteArrayList<>();
    }

    public void a(String str, boolean z) {
        Music.Favourite favourite = new Music.Favourite();
        favourite.favouriteId = str;
        favourite.isFavourite = z;
        a(favourite);
    }

    private void a(Music.Favourite favourite) {
        if (!TextUtils.isEmpty(favourite.favouriteId)) {
            this.a.put(favourite.favouriteId, favourite);
        }
    }

    private Music.Favourite a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.a.get(str);
    }

    public void registerCollectionListener(CollectionListener collectionListener) {
        this.b.add(collectionListener);
    }

    public void unregisterCollectionListener(CollectionListener collectionListener) {
        this.b.remove(collectionListener);
    }

    public static boolean canLovable(int i) {
        return MusicHelper.isPlayingSong(i) || MusicHelper.isPlayingRadioStation(i);
    }

    private int a(int i) {
        if (MusicHelper.isPlayingRadioStation(i)) {
            return 4;
        }
        return MusicHelper.isPlayingSong(i) ? 1 : 1;
    }

    public void loadPlayListLoveStatus(Remote.Response.PlayerStatus playerStatus) {
        if (canLovable(playerStatus.media_type)) {
            int a2 = a(playerStatus.media_type);
            if (4 == a2) {
                Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
                if (playingData != null && !TextUtils.isEmpty(playingData.albumGlobalID) && a(playingData.albumGlobalID) == null) {
                    a(a2, playingData.albumGlobalID, playingData.audioID, Optional.empty());
                    return;
                }
                return;
            }
            a(playerStatus.extra_track_list);
        }
    }

    private void a(List<Remote.Response.TrackData> list) {
        if (list != null && !list.isEmpty()) {
            this.c = b(list).flatMap($$Lambda$CollectionManager$LM14y8aI4rxnltBtRqw5t0mhkYE.INSTANCE).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$3joQ64j0w3LkYpfE_7saIciyPxA
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Boolean c;
                    c = CollectionManager.this.c((List) obj);
                    return c;
                }
            }).subscribeOn(MicoSchedulers.io()).subscribe($$Lambda$CollectionManager$7U00Rlz0dSnXaXm9qISFadKMMiU.INSTANCE, $$Lambda$CollectionManager$7yRI76SpPSWHq02Pe6DgqOHaz0.INSTANCE);
        }
    }

    public static /* synthetic */ ObservableSource d(List list) throws Exception {
        if (list.isEmpty()) {
            return Observable.just(new ArrayList());
        }
        return ApiManager.minaService.getAudioInfoV3(Gsons.getGson().toJson(list)).map($$Lambda$CollectionManager$YRBLG6Y9otiSbXSKPO7LoDJFWJs.INSTANCE);
    }

    public static /* synthetic */ List a(MinaResponse minaResponse) throws Exception {
        return (List) minaResponse.data;
    }

    public /* synthetic */ Boolean c(List list) throws Exception {
        if (list == null || list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Music.AudioInfo audioInfo = (Music.AudioInfo) it.next();
            Music.Favourite favourite = new Music.Favourite();
            favourite.favouriteId = audioInfo.audioID;
            favourite.isFavourite = audioInfo.isCollected();
            a(favourite);
        }
        return true;
    }

    public static /* synthetic */ void a(Boolean bool) throws Exception {
        L.base.d("load song favorite state=%s", bool);
    }

    public static /* synthetic */ void c(Throwable th) throws Exception {
        L.base.d("load song favorite error,%s", th);
    }

    private Observable<List<String>> b(final List<Remote.Response.TrackData> list) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$BCBjSnK-JNS3819xRnOdVayxaBk
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                CollectionManager.a(list, observableEmitter);
            }
        });
    }

    public static /* synthetic */ void a(List list, ObservableEmitter observableEmitter) throws Exception {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Remote.Response.TrackData trackData = (Remote.Response.TrackData) it.next();
            if (!TextUtils.isEmpty(trackData.audioID)) {
                arrayList.add(trackData.audioID);
            }
        }
        observableEmitter.onNext(arrayList);
        observableEmitter.onComplete();
    }

    public Observable<Boolean> loadLoveStatus(String str, final String str2, int i) {
        if (a(i) == 4) {
            return ApiManager.minaService.getStationLikeStatus(str2).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$QtIzDDD-RUtzWgdU1NCET-qjRFo
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Boolean c;
                    c = CollectionManager.this.c(str2, (Boolean) obj);
                    return c;
                }
            });
        }
        return loadLoveStatus(str, Optional.empty(), Optional.empty());
    }

    public /* synthetic */ Boolean c(String str, Boolean bool) throws Exception {
        a(str, bool.booleanValue());
        return bool;
    }

    public Observable<Boolean> loadLoveStatus(final String str, final Optional<Boolean> optional, final Optional<AtomicInteger> optional2) {
        return AudioInfoCache.getAudioInfo(str).flatMap($$Lambda$CollectionManager$NsGaOe5s4hwlvzecRyigRbMI4fk.INSTANCE).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$uFZE_v0i-KJ1EuIzRtCmOJWADOY
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = CollectionManager.this.a(optional, optional2, str, (List) obj);
                return a2;
            }
        });
    }

    public static /* synthetic */ ObservableSource c(Music.AudioInfo audioInfo) throws Exception {
        return ApiManager.minaService.getFavouriteStatus(Remote.Request.buildSongFavouriteMessage(audioInfo.cpID, audioInfo.cpName));
    }

    public /* synthetic */ ObservableSource a(Optional optional, Optional optional2, String str, List list) throws Exception {
        if (!ContainerUtil.hasData(list)) {
            return Observable.just(false);
        }
        Music.Favourite favourite = (Music.Favourite) list.get(0);
        if (optional.isPresent() && optional2.isPresent() && ((AtomicInteger) optional2.get()).intValue() > 0 && ((Boolean) optional.get()).booleanValue() != favourite.isFavourite) {
            return Observable.error(new SyncFailedException(((AtomicInteger) optional2.get()).getAndDecrement()));
        }
        a(str, favourite.isFavourite);
        return Observable.just(Boolean.valueOf(favourite.isFavourite));
    }

    public void refreshLoveStatus(Optional<Boolean> optional) {
        L.player.i("CollectionManager refreshLoveStatus");
        Remote.Response.PlayerStatus wiFiPlayerStatus = PlayerApi.getWiFiPlayerStatus();
        if (wiFiPlayerStatus.play_song_detail != null) {
            Remote.Response.PlayingData playingData = wiFiPlayerStatus.play_song_detail;
            L.player.i("CollectionManager media_type=%s playingData.albumGlobalID=%s playingData.musicID=%s", Integer.valueOf(wiFiPlayerStatus.media_type), playingData.albumGlobalID, playingData.musicID);
            if (canLovable(wiFiPlayerStatus.media_type)) {
                int a2 = a(wiFiPlayerStatus.media_type);
                a(a2, 4 == a2 ? playingData.albumGlobalID : playingData.musicID, playingData.audioID, optional);
            }
        }
    }

    private void a(int i, final String str, final String str2, final Optional<Boolean> optional) {
        L.player.d("CollectionManager doRefreshLoveStatus globalId=%s", str);
        if (MusicHelper.isPlayingRadioStation(i)) {
            this.c = ApiManager.minaService.getStationLikeStatus(str).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$Vt9pRQagGSgHUjd3rCzrPKhyWUk
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Music.Favourite b;
                    b = CollectionManager.this.b(str, (Boolean) obj);
                    return b;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$NtEe9wskHNj6JbJ4znlVxpT6RaI
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CollectionManager.this.a(str, (Music.Favourite) obj);
                }
            }, $$Lambda$CollectionManager$0iA2pQOHzqlqWh3xKnoDo0qnblU.INSTANCE);
        } else {
            this.c = loadLoveStatus(str2, optional, Optional.of(new AtomicInteger(2))).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).retryWhen(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$rNWmedwaFIB0DSij_UHDfRzs3uk
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource a2;
                    a2 = CollectionManager.a(optional, (Observable) obj);
                    return a2;
                }
            }).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$glH03xPZxxV7kJvjhrD5kkvrwKc
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CollectionManager.this.a(str2, (Boolean) obj);
                }
            }, $$Lambda$CollectionManager$jZ85Urvc_36coucWobFO3Brz66g.INSTANCE);
        }
    }

    public /* synthetic */ Music.Favourite b(String str, Boolean bool) throws Exception {
        return b(String.valueOf(str), bool.booleanValue());
    }

    public /* synthetic */ void a(String str, Music.Favourite favourite) throws Exception {
        c(str, favourite.isFavourite);
    }

    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.player.e("doRefreshLoveStatus.onFail: %s", th);
    }

    public static /* synthetic */ ObservableSource a(final Optional optional, Observable observable) throws Exception {
        return observable.flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$SaPmydXW1TzML2CpZIueRcAWyiI
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = CollectionManager.a(optional, (Throwable) obj);
                return a2;
            }
        });
    }

    public static /* synthetic */ ObservableSource a(Optional optional, Throwable th) throws Exception {
        if (!(th instanceof SyncFailedException)) {
            return Observable.just(optional.isPresent() ? (Boolean) optional.get() : false);
        }
        SyncFailedException syncFailedException = (SyncFailedException) th;
        if (syncFailedException.getTimes() <= 0) {
            return Observable.just(optional.get());
        }
        L.player.i("CollectionManager retryWhen %s", syncFailedException.toString());
        return Observable.timer(syncFailedException.getInterval(), syncFailedException.getTimeUnit());
    }

    public /* synthetic */ void a(String str, Boolean bool) throws Exception {
        c(str, bool.booleanValue());
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.player.e("doRefreshLoveStatus.onFail: %s", th.toString());
    }

    /* loaded from: classes3.dex */
    public static class SyncFailedException extends Throwable {
        private int interval;
        private TimeUnit timeUnit;
        private int times;

        public SyncFailedException(String str) {
            super(str);
        }

        public SyncFailedException(int i) {
            super(String.format(Locale.US, "cloud not synchronized yet, retry %d times, in %d %s", Integer.valueOf(i), 3, TimeUnit.SECONDS));
            this.interval = 3;
            this.timeUnit = TimeUnit.SECONDS;
            this.times = i;
        }

        public int getTimes() {
            return this.times;
        }

        public int getInterval() {
            return this.interval;
        }

        public TimeUnit getTimeUnit() {
            return this.timeUnit;
        }
    }

    private Music.Favourite b(String str, boolean z) {
        Music.Favourite favourite = new Music.Favourite();
        favourite.favouriteId = str;
        favourite.isFavourite = z;
        a(favourite);
        return favourite;
    }

    public boolean doSetLovable(boolean z) {
        return a(PlayerApi.getWiFiPlayerStatus(), z);
    }

    private boolean a(Remote.Response.PlayerStatus playerStatus, boolean z) {
        Logger logger = L.player;
        logger.d("doSetLovable = play_song_detail" + playerStatus.play_song_detail);
        if (playerStatus.play_song_detail == null) {
            return false;
        }
        Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
        Logger logger2 = L.player;
        logger2.d("doSetLovable mediaType = " + playerStatus.media_type);
        if (!canLovable(playerStatus.media_type)) {
            return false;
        }
        a(z, playerStatus.media_type, 4 == a(playerStatus.media_type) ? playingData.albumGlobalID : playingData.getSimpleId(), playingData.cpID, playingData.cpOrigin);
        return true;
    }

    private void a(boolean z, int i, final String str, String str2, String str3) {
        L.player.d("doSetLovable:lovable=%b,type=%d,globalId=%s,cpId=%s,cpOrigin=%s", Boolean.valueOf(z), Integer.valueOf(i), str, str2, str3);
        if (z) {
            if (MusicHelper.isPlayingRadioStation(i)) {
                ApiHelper.stationLike(Long.parseLong(str)).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.skills.music.controller.CollectionManager.1
                    /* renamed from: a */
                    public void onSuccess(String str4) {
                        L.player.d("doSetLovable：onSuccess");
                        CollectionManager.this.a(str, true);
                        CollectionManager.this.c(str, true);
                    }
                });
            } else if (MusicHelper.isPlayingSong(i)) {
                AudioInfoCache.getAudioInfo(str).flatMap($$Lambda$CollectionManager$q0oxxn79w65YXiYICu8YhJ7oHfQ.INSTANCE).subscribe(new DefaultObserver<Boolean>() { // from class: com.xiaomi.micolauncher.skills.music.controller.CollectionManager.2
                    /* renamed from: a */
                    public void onSuccess(Boolean bool) {
                        L.player.d("doSetLovable：onSuccess");
                        CollectionManager.this.a(str, true);
                        CollectionManager.this.c(str, true);
                    }
                });
            }
        } else if (MusicHelper.isPlayingRadioStation(i)) {
            ApiHelper.stationUnlike(Long.parseLong(str)).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.skills.music.controller.CollectionManager.3
                /* renamed from: a */
                public void onSuccess(String str4) {
                    L.player.d("doSetLovable：onSuccess");
                    CollectionManager.this.a(str, false);
                    CollectionManager.this.c(str, false);
                }
            });
        } else if (MusicHelper.isPlayingSong(i)) {
            AudioInfoCache.getAudioInfo(str).flatMap($$Lambda$CollectionManager$H51jn2Xj3ql_aRMJ9NAmY_6NtE.INSTANCE).subscribe(new DefaultObserver<Boolean>() { // from class: com.xiaomi.micolauncher.skills.music.controller.CollectionManager.4
                /* renamed from: a */
                public void onSuccess(Boolean bool) {
                    L.player.d("doSetLovable：onSuccess");
                    CollectionManager.this.a(str, false);
                    CollectionManager.this.c(str, false);
                }
            });
        }
    }

    public static /* synthetic */ ObservableSource b(Music.AudioInfo audioInfo) throws Exception {
        return ChannelManager.getInstance().addToFavourite(audioInfo);
    }

    public static /* synthetic */ ObservableSource a(Music.AudioInfo audioInfo) throws Exception {
        return ChannelManager.getInstance().removeFavourite(audioInfo);
    }

    public void c(final String str, final boolean z) {
        CopyOnWriteArrayList<CollectionListener> copyOnWriteArrayList = this.b;
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.forEach(new java.util.function.Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$jthYz5E8DPjx5rKC1lrIcqkdRa8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CollectionManager.CollectionListener) obj).onCollectionChange(str, z);
                }
            });
        }
    }
}
