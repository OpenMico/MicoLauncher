package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import com.xiaomi.micolauncher.skills.music.vip.OrderType;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class RecentlyMusicController extends BasePlaylistController {
    private List<String> a;
    private volatile List<Music.AudioInfo> b;

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canLoadMore() {
        return false;
    }

    public RecentlyMusicController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    public void playRecentlyMusic() {
        L.player.d("RecentlyMusicController playRecentlyMusic");
        this.dialogId = null;
        this.a = null;
        j();
        setLoopType(LoopType.SHUFFLE.ordinal());
        k();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        loadMore(0);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore(int i) {
        if (ContainerUtil.isEmpty(this.b)) {
            k();
        } else {
            playByIndex(i, false);
        }
    }

    private void k() {
        a(this.loadMoreDisposable);
        this.musicIndexHelper.setLoadingMore(true);
        this.loadMoreDisposable = LocalPlayerManager.getInstance().loadBlackList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$CgYyjUhCNx7-avYkZnb0sZVmTtM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource l;
                l = RecentlyMusicController.this.l((List) obj);
                return l;
            }
        }).flatMap($$Lambda$RecentlyMusicController$0pWV7vZW3FpmUCxxZs_xctqcNI.INSTANCE).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$7tHFU5mFEf8mHwz1FUSq2gK84jM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List j;
                j = RecentlyMusicController.this.j((List) obj);
                return j;
            }
        }).flatMap($$Lambda$RecentlyMusicController$QSoAmvOAlpLfSZVnhOzYMfgU2o.INSTANCE).flatMap($$Lambda$RecentlyMusicController$B8qL3ZXk0uw77yxaKo0lt6jMgD0.INSTANCE).toList().map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$PRkGnuZYYOmXSfolQFtCVwDaY3I
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List g;
                g = RecentlyMusicController.this.g((List) obj);
                return g;
            }
        }).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$jvR8NAmBkLnNwkT8MtzsqfmdbMM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List f;
                f = RecentlyMusicController.this.f((List) obj);
                return f;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$heM52u6eZJSAaf5_mAfEDsay0K8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecentlyMusicController.this.e((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$qtvGvbIACOqzHzlqAVhvR1fcdf4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecentlyMusicController.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource l(List list) throws Exception {
        this.blackListKey = list;
        return ApiManager.minaService.audioHistory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource k(List list) throws Exception {
        return ApiManager.minaService.getSongInfo(Gsons.getGson().toJson(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List g(List list) throws Exception {
        return AudioInfoCache.matchAudioInfo(list, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List f(List list) throws Exception {
        return c(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(List list) throws Exception {
        if (!isReleased()) {
            this.b = list;
            if (!list.isEmpty()) {
                sendPlaylist(false);
                a();
                return;
            }
            a(false, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        if (!isReleased()) {
            a(ContainerUtil.hasData(this.b), 0, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public List<List<String>> j(List<Music.Song> list) {
        ArrayList arrayList = new ArrayList();
        for (Music.Song song : list) {
            if (!TextUtils.isEmpty(song.audioID) && !"0".equals(song.audioID)) {
                arrayList.add(song.audioID);
            }
        }
        this.a = arrayList;
        ArrayList arrayList2 = new ArrayList();
        if (this.a.size() > 49) {
            int i = 0;
            while (i < this.a.size()) {
                int i2 = i + 49;
                arrayList2.add(this.a.subList(i, i2 > this.a.size() ? this.a.size() : i2));
                i = i2;
            }
        } else {
            arrayList2.add(this.a);
        }
        return arrayList2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        int currentIndex = getCurrentIndex();
        if (this.b == null || this.b.size() <= currentIndex) {
            return null;
        }
        return Remote.Response.PlayingData.from(this.b.get(currentIndex));
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public List<Remote.Response.TrackData> sendPlaylist(boolean z) {
        if (!ContainerUtil.hasData(this.b)) {
            return null;
        }
        ArrayList arrayList = new ArrayList(this.b.size());
        for (int i = 0; i < this.b.size(); i++) {
            Music.AudioInfo audioInfo = this.b.get(i);
            if (audioInfo != null) {
                arrayList.add(Remote.Response.TrackData.from(audioInfo, i));
            }
        }
        a(arrayList, z);
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getPlaylistCount() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    @SuppressLint({"CheckResult"})
    public void doPlay(final int i) {
        a(this.loadMoreDisposable);
        a(this.cpResourceDisposable);
        final Music.AudioInfo c = c(i);
        if (c == null || TextUtils.isEmpty(c.playUrl)) {
            a(i);
            return;
        }
        StreamInfo streamInfo = new StreamInfo();
        streamInfo.url = c.playUrl;
        streamInfo.authentication = c.authentication;
        this.cpResourceDisposable = StreamHelper.buildUrl(streamInfo).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$x9glfmUrnQ8kE8Mei0fQXPIjjtU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecentlyMusicController.this.a(c, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$ChRyLmVs_Q-LUu0Ccwf54VMpF7w
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecentlyMusicController.this.a(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.AudioInfo audioInfo, String str) throws Exception {
        a(str, audioInfo.audioID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Throwable th) throws Exception {
        a(th, i);
    }

    private Music.AudioInfo c(int i) {
        if (ContainerUtil.isOutOfBound(i, this.b)) {
            return null;
        }
        return this.b.get(i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        OrderType orderType = onPlayPaySuccess.orderType;
        L.player.i("RecentlyMusicController onPlayPaySuccess orderType=%s", orderType);
        if (orderType == OrderType.MUSIC) {
            for (Music.AudioInfo audioInfo : this.b) {
                audioInfo.unplayableCode = 0;
            }
        }
        sendPlaylist(false);
        sendMetadata();
    }
}
