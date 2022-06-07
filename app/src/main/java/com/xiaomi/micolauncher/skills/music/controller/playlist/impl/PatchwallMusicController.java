package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.cache.MusicCache;
import com.xiaomi.micolauncher.skills.music.vip.OrderType;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PatchwallMusicController extends BasePlaylistController {
    private ArrayList<Music.Song> a = new ArrayList<>();
    private String b;
    private String c;
    private int d;

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getMediaType() {
        return 3;
    }

    public PatchwallMusicController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    public void playPatchwall(String str, String str2, int i, int i2) {
        L.player.d("PatchwallMusicController playPatchwall type=%s id=%s loopType=%d index=%s", str, str2, Integer.valueOf(i), Integer.valueOf(i2));
        j();
        this.b = str2;
        this.c = str;
        this.dialogId = null;
        this.a.clear();
        this.d = i2;
        this.loadMoreEnd = false;
        setLoopType(i);
        a(str, str2, i2, null);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        loadMore(this.a.size());
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore(int i) {
        if (!canLoadMore()) {
            a(!this.a.isEmpty(), i);
        } else {
            a(this.c, this.b, i, null);
        }
    }

    private void a(final String str, final String str2, final int i, final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(this.loadMoreDisposable);
        if (!TextUtils.isEmpty(str)) {
            this.musicIndexHelper.setLoadingMore(true);
            this.loadMoreDisposable = LocalPlayerManager.getInstance().loadBlackList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$mANuBV_JiqOBmAF1aHWvUNmiawY
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource a;
                    a = PatchwallMusicController.this.a(str2, str, (List) obj);
                    return a;
                }
            }).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$S2gjwSSp6E590Wl8d77o-0K5KXU
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Music.SongBook c;
                    c = PatchwallMusicController.this.c((Music.SongBook) obj);
                    return c;
                }
            }).flatMap($$Lambda$PatchwallMusicController$KrEqYoeqhB1kpICBQcjJvPbmfc.INSTANCE).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$9BHa6lo--ZW79jL6INSCGvOOqWc
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    boolean d;
                    d = PatchwallMusicController.this.d((List) obj);
                    return d;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$S1OOQUUKN7Pyr0yhIWp1CIAL3RY
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PatchwallMusicController.this.a(i, metadataLoadMoreCallback, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$CEA1YtxxO_ovYxFyuYAQbD_j4Bw
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PatchwallMusicController.this.a(metadataLoadMoreCallback, i, (Throwable) obj);
                }
            });
        } else if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
        } else {
            a(false, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(String str, String str2, List list) throws Exception {
        this.blackListKey = list;
        return ApiManager.minaService.getQQMusicSongs(str, str2, Hardware.current(getContext()).getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource b(Music.SongBook songBook) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (Music.Song song : songBook.songList) {
            if (song.audioID != null && !song.isNoPlayable()) {
                arrayList.add(song.audioID);
            }
        }
        if (ContainerUtil.hasData(arrayList)) {
            return MusicCache.getSongs(arrayList);
        }
        return Observable.just(songBook.songList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean d(List list) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, MetadataLoadMoreCallback metadataLoadMoreCallback, List list) throws Exception {
        if (list != null) {
            a(list, i, metadataLoadMoreCallback == null);
        } else {
            a(!this.a.isEmpty(), i);
        }
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(true);
            if (ContainerUtil.isEmpty(list)) {
                metadataLoadMoreCallback.onLoadMoreEnd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Throwable th) throws Exception {
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
        } else {
            a(!this.a.isEmpty(), i, th);
        }
    }

    private void a(List<Music.Song> list, int i, boolean z) {
        if (!list.isEmpty()) {
            this.a.addAll(b(list));
            if (!this.a.isEmpty()) {
                setCurrentIndex(this.d);
                sendPlaylist(!z);
                if (!z) {
                    return;
                }
                if (i < 0 || i >= getPlaylistCount()) {
                    a();
                } else {
                    playByIndex(i, false);
                }
            } else if (z) {
                a(false, i);
            }
        } else if (z) {
            a(!this.a.isEmpty(), i);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        OrderType orderType = onPlayPaySuccess.orderType;
        L.player.i("PatchwallMusicController onPlayPaySuccess orderType=%s", orderType);
        if (orderType == OrderType.MUSIC) {
            Iterator<Music.Song> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().unplayableCode = 0;
            }
        }
        sendPlaylist(false);
        sendMetadata();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getPlaylistCount() {
        return this.a.size();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public List<Remote.Response.TrackData> sendPlaylist(boolean z) {
        ArrayList<Music.Song> arrayList = this.a;
        if (arrayList == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i = 0; i < this.a.size(); i++) {
            arrayList2.add(Remote.Response.TrackData.from(this.a.get(i), i));
        }
        a(arrayList2, z);
        return arrayList2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        Music.Song c = c(getCurrentIndex());
        if (c == null) {
            return null;
        }
        Remote.Response.PlayingData from = Remote.Response.PlayingData.from(c);
        from.screenExtend.offset = getCurrentIndex();
        return from;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    @SuppressLint({"CheckResult"})
    public void doPlay(final int i) {
        a(this.loadMoreDisposable);
        a(this.cpResourceDisposable);
        final Music.Song c = c(i);
        if (c == null || TextUtils.isEmpty(c.playUrl)) {
            a(i);
            return;
        }
        StreamInfo streamInfo = new StreamInfo();
        streamInfo.url = c.playUrl;
        streamInfo.authentication = c.authentication;
        this.cpResourceDisposable = StreamHelper.buildUrl(streamInfo).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$3iTieQ6LJLA2JyoAE-ZTGqd0O4U
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PatchwallMusicController.this.a(c, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$QHpGIUAizf1_JHbln9jXh8tZfPk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PatchwallMusicController.this.a(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.Song song, String str) throws Exception {
        a(str, song.audioID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Throwable th) throws Exception {
        a(th, i);
    }

    private Music.Song c(int i) {
        if (ContainerUtil.isOutOfBound(i, this.a)) {
            return null;
        }
        return this.a.get(i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void release() {
        super.release();
        this.a.clear();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doLoadMore(MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(this.c, this.b, this.a.size(), metadataLoadMoreCallback);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canLoadMore() {
        String str;
        return !this.loadMoreEnd && (str = this.c) != null && !"songlist".equals(str) && !"artist".equals(this.c) && !NotificationCompat.CATEGORY_RECOMMENDATION.equals(this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public Music.SongBook c(Music.SongBook songBook) {
        this.blackFilterAll = false;
        ArrayList arrayList = new ArrayList();
        List<Music.Song> list = songBook.songList;
        for (int i = 0; i < list.size(); i++) {
            Music.Song song = list.get(i);
            if (!b(song.name, song.artist != null ? song.artist.name : "")) {
                arrayList.add(song);
            }
        }
        if (ContainerUtil.isEmpty(arrayList) && !ContainerUtil.isEmpty(list)) {
            this.blackFilterAll = true;
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$Knx6jFJEREWcnFbo-I6k-JskPvU
                @Override // java.lang.Runnable
                public final void run() {
                    PatchwallMusicController.this.k();
                }
            });
        }
        songBook.songList = arrayList;
        return songBook;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void k() {
        s();
    }
}
