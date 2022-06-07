package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.vip.OrderType;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SheetPlaylistController extends BasePlaylistController {
    private boolean c;
    private int e;
    private int f;
    private int h;
    private int i;
    private boolean j;
    private long a = -1;
    private List<Music.Song> b = new ArrayList();
    private int d = 0;
    private int g = 20;

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getMediaType() {
        return 9;
    }

    public SheetPlaylistController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    public void playSheet(long j, int i, int i2) {
        boolean z = true;
        L.player.d("SheetPlaylistController playSheet sheetId=%d offset=%d loopType=%d", Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2));
        j();
        this.h = i;
        this.i = i2;
        this.loadMoreEnd = false;
        if (i <= 0) {
            z = false;
        }
        this.c = z;
        this.a = j;
        this.b.clear();
        this.d = 0;
        this.e = 0;
        this.dialogId = null;
        loadMore(i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        a(this.e, this.g, -1, null);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore(int i) {
        if (this.loadMoreEnd) {
            playByIndex(i, false);
            return;
        }
        int i2 = this.e;
        if (i2 != 0 || i <= this.g) {
            a(this.e, this.g, i, null);
        } else {
            a(i2, i + 10, i, null);
        }
    }

    private void a(final int i, final int i2, final int i3, final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(this.loadMoreDisposable);
        if (this.a < 0) {
            boolean z = false;
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreComplete(false);
                return;
            }
            if (this.b.size() > 0) {
                z = true;
            }
            a(z, i3);
            return;
        }
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreStart();
        }
        this.musicIndexHelper.setLoadingMore(true);
        this.loadMoreDisposable = LocalPlayerManager.getInstance().loadBlackList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$SheetPlaylistController$XUB_oNjJYsZk4N-550iGL64r0_8
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = SheetPlaylistController.this.a(i, i2, (List) obj);
                return a;
            }
        }).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$SheetPlaylistController$oAqks9U9Tr2jc6je18Y-1j_cL7E
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                Music.Channel c;
                c = SheetPlaylistController.this.c((Music.Channel) obj);
                return c;
            }
        }).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$SheetPlaylistController$bkjcADE4FIw0ZJvmlMREHJqX-rA
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean b;
                b = SheetPlaylistController.this.b((Music.Channel) obj);
                return b;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$SheetPlaylistController$Hi0y8cHx2ZeoPenydi34rjGDAjg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SheetPlaylistController.this.a(i3, metadataLoadMoreCallback, (Music.Channel) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$SheetPlaylistController$0PRjyK1d1Ve0V747s14gTAXSSek
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SheetPlaylistController.this.a(metadataLoadMoreCallback, i3, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(int i, int i2, List list) throws Exception {
        this.blackListKey = list;
        return ApiManager.minaService.getChannelInfo(this.a, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Music.Channel c(Music.Channel channel) throws Exception {
        return a(channel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(Music.Channel channel) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, MetadataLoadMoreCallback metadataLoadMoreCallback, Music.Channel channel) throws Exception {
        int i2;
        if (channel.songList != null && channel.songList.size() > 0) {
            L.player.d("SheetPlaylistController nextStartOffset=%d", Integer.valueOf(channel.nextStartOffset));
            if (channel.nextStartOffset == 0) {
                this.loadMoreEnd = true;
                this.e += channel.songList.size();
            } else if (channel.nextStartOffset > 0) {
                this.e = channel.nextStartOffset;
            }
            this.b.addAll(channel.songList);
            if (!this.j) {
                this.j = true;
                onInitDataLoadSuccess(this.h, this.i);
            }
            if (this.e > i) {
                this.b = a(i, this.b);
                sendPlaylist(metadataLoadMoreCallback != null);
            }
            L.player.d("SheetPlaylistController loadMore response songList=%d, playlist=%d", Integer.valueOf(channel.songList.size()), Integer.valueOf(this.b.size()));
            int currentIndex = this.d > 0 ? getCurrentIndex() : i;
            L.player.i("SheetPlaylistController loadMore success index=%d, playIndex=%d, getPlaylistCount=%d", Integer.valueOf(currentIndex), Integer.valueOf(i), Integer.valueOf(getPlaylistCount()));
            if (currentIndex >= 0) {
                if (currentIndex < getPlaylistCount()) {
                    playByIndex(currentIndex, false);
                } else if (this.g == 1 || this.f > 10) {
                    this.g = 20;
                    this.f = 0;
                    a();
                    return;
                } else {
                    L.player.d("SheetPlaylistController loadMore 递归触发 retryLoadMore=%d", Integer.valueOf(this.f));
                    this.f++;
                    loadMore(i);
                }
            }
        } else if (this.b.size() > 0) {
            int currentIndex2 = this.d > 0 ? getCurrentIndex() : i;
            if (currentIndex2 >= 0) {
                if (currentIndex2 < getPlaylistCount()) {
                    playByIndex(currentIndex2, false);
                } else {
                    int i3 = this.g;
                    if (i3 == 1 || (i2 = this.f) > 10) {
                        this.g = 20;
                        this.f = 0;
                        a();
                        return;
                    }
                    this.f = i2 + 1;
                    this.g = Math.max(1, i3 / 2);
                    loadMore(i);
                }
            }
        } else {
            c();
        }
        this.musicIndexHelper.setLoadingMore(false);
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(true);
            if (ContainerUtil.isEmpty(channel.songList)) {
                metadataLoadMoreCallback.onLoadMoreEnd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Throwable th) throws Exception {
        boolean z = false;
        this.musicIndexHelper.setLoadingMore(false);
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
            return;
        }
        if (this.b.size() > 0) {
            z = true;
        }
        a(z, i, th);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getPlaylistCount() {
        return this.b.size();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public List<Remote.Response.TrackData> sendPlaylist(boolean z) {
        List<Music.Song> list = this.b;
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < this.b.size(); i++) {
            arrayList.add(Remote.Response.TrackData.from(this.b.get(i), i));
        }
        a(arrayList, z);
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        if (this.b.size() <= getCurrentIndex() || getCurrentIndex() < 0) {
            return null;
        }
        Remote.Response.PlayingData from = Remote.Response.PlayingData.from(this.b.get(getCurrentIndex()));
        from.screenExtend.playlistId = this.a;
        from.screenExtend.offset = getCurrentIndex();
        from.albumGlobalID = String.valueOf(this.a);
        return from;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doPlay(final int i) {
        final Music.Song c = c(i);
        if (c == null || TextUtils.isEmpty(c.playUrl)) {
            a(i);
            return;
        }
        a(this.cpResourceDisposable);
        StreamInfo streamInfo = new StreamInfo();
        streamInfo.url = c.playUrl;
        streamInfo.authentication = c.authentication;
        this.cpResourceDisposable = StreamHelper.buildUrl(streamInfo).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$SheetPlaylistController$noI5Gq-PdfinUtAlecePCz4rA1Y
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SheetPlaylistController.this.a(c, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$SheetPlaylistController$R-J84VGhhY4GPipm-DL5KFXkB3M
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SheetPlaylistController.this.a(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.Song song, String str) throws Exception {
        a(str, song.audioID);
        a(song);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Throwable th) throws Exception {
        a(th, i);
    }

    private Music.Song c(int i) {
        if (ContainerUtil.isOutOfBound(i, this.b)) {
            return null;
        }
        return this.b.get(i);
    }

    private List<Music.Song> a(int i, List<Music.Song> list) {
        if (!this.c) {
            return b(list);
        }
        this.d = 0;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            Music.Song song = list.get(i2);
            if (song.isLegal()) {
                arrayList.add(song);
            } else if (i2 < getCurrentIndex()) {
                this.d++;
            }
        }
        L.player.d("SheetPlaylistController illegalSize=%d", Integer.valueOf(this.d));
        int i3 = this.d;
        if (i3 > 0) {
            setCurrentIndex(i - i3);
        }
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void release() {
        super.release();
        this.b.clear();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        OrderType orderType = onPlayPaySuccess.orderType;
        L.player.i("SheetPlaylistController onPlayPaySuccess orderType=%s", orderType);
        if (orderType == OrderType.MUSIC) {
            for (Music.Song song : this.b) {
                song.unplayableCode = 0;
            }
        }
        sendPlaylist(false);
        sendMetadata();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canLoadMore() {
        return !this.loadMoreEnd && !this.musicIndexHelper.isLoadingMore();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doLoadMore(MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(this.e, this.g, -1, metadataLoadMoreCallback);
    }
}
