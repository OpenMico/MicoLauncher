package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PlayV3Pact;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.vip.OrderType;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class StationPlaylistController extends BasePlaylistController {
    private ArrayList<Music.Station> a = new ArrayList<>();
    private Music.Station b;
    private boolean c;
    private Disposable d;
    private Music.StationHistory e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private String j;

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canPreLoadMore() {
        return false;
    }

    public StationPlaylistController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    public void playStation(String str, String str2, int i) {
        playStation(str, str2, i, false);
    }

    public void playStation(String str, String str2, int i, boolean z) {
        j();
        this.i = 0;
        this.h = i;
        this.c = false;
        this.a.clear();
        this.b = null;
        this.dialogId = null;
        this.f = z;
        setLoopType(LoopType.LIST_LOOP.ordinal());
        a(this.d);
        this.d = ApiManager.minaService.getStationInfo(str, str2, i).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$KDeqX_ef5b06ETIDlSwZYkioPRo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationPlaylistController.this.c((Music.Station) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$22SAQIhDo7CmeDun9X1PQOdQzKA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationPlaylistController.this.b((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        a(this.a.isEmpty(), 0, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void c(Music.Station station) {
        this.b = station;
        this.j = this.b.cpAlbumId;
        if (k()) {
            b(station);
        } else {
            l();
        }
    }

    private boolean k() {
        return this.h == 2;
    }

    private void b(Music.Station station) {
        if (TextUtils.isEmpty(station.cp) || TextUtils.isEmpty(station.cpID)) {
            e();
            return;
        }
        this.a.add(station);
        this.trackDataList = sendPlaylist(false);
        a();
    }

    private void l() {
        if (this.f) {
            m();
        } else {
            loadMore(0);
        }
    }

    private void m() {
        a(this.d);
        this.d = ApiManager.minaService.stationSoundHistory(Hardware.current(getContext()).getName(), this.b.albumGlobalID).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$K5nqXXlI3rzOmRAtzd70lGva7ao
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationPlaylistController.this.a((Music.StationHistory) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$bnvETnSyXr5LJnRIONRMjFvbbWo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationPlaylistController.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.StationHistory stationHistory) throws Exception {
        if (stationHistory == null || TextUtils.isEmpty(stationHistory.globalId) || stationHistory.position <= 0) {
            loadMore(0);
            return;
        }
        this.e = stationHistory;
        loadMore(stationHistory.episode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.base.e("StationPlaylistController stationHistory error", th);
        loadMore(0);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getPlaylistCount() {
        return this.a.size();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public List<Remote.Response.TrackData> sendPlaylist(boolean z) {
        ArrayList<Music.Station> arrayList = this.a;
        if (arrayList == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i = 0; i < this.a.size(); i++) {
            arrayList2.add(Remote.Response.TrackData.from(this.a.get(i), i, this.j));
        }
        a(arrayList2, z);
        return arrayList2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        Music.Station c = c(getCurrentIndex());
        if (c == null) {
            return null;
        }
        Remote.Response.PlayingData from = Remote.Response.PlayingData.from(c);
        from.cover = this.b.cover;
        from.albumGlobalID = this.b.albumGlobalID;
        return from;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doPlay(final int i) {
        a(this.loadMoreDisposable);
        final Music.Station c = c(i);
        if (c == null || TextUtils.isEmpty(c.playUrl)) {
            a(i);
            return;
        }
        a(this.cpResourceDisposable);
        StreamInfo streamInfo = new StreamInfo();
        streamInfo.url = c.playUrl;
        streamInfo.authentication = c.authentication;
        streamInfo.redirect = true;
        this.cpResourceDisposable = StreamHelper.buildUrl(streamInfo).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$HCRHruHVku875wYTW1t7haCsbbA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationPlaylistController.this.a(c, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$iXHUouKBVY6E7siWwSSzn7yMf_k
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationPlaylistController.this.a(c, i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.Station station, String str) throws Exception {
        a(str, station.audioID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.Station station, int i, Throwable th) throws Exception {
        L.player.w("StationPlaylistController doPlay title=%s, cp=%s, id=%s", station.title, station.cp, station.cpID);
        a(th, i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        a(this.i, 20, -1, null);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore(int i) {
        int i2 = this.i;
        if (i2 != 0 || i <= i2) {
            a(this.i, 20, i, null);
        } else {
            a(i2, i + 10, i, null);
        }
    }

    private void a(int i, int i2, final int i3, final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(this.loadMoreDisposable);
        if (this.b == null) {
            e();
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreComplete(true);
            }
        } else if (k()) {
            n();
        } else if (!this.c || this.a.size() <= 0) {
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreStart();
            }
            this.musicIndexHelper.setLoadingMore(true);
            this.loadMoreDisposable = ApiManager.minaService.getStationSoundList(this.b.cpID, this.b.origin, this.b.category, i, i2, PlayV3Pact.NOT_REVERSE).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$jjDImdebZEo6nGyJ7z77DDPH-ko
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    boolean a;
                    a = StationPlaylistController.this.a((Music.StationSoundList) obj);
                    return a;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$OPYC3mzF9sLEyK5tFcuZgLDhz_w
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StationPlaylistController.this.a(metadataLoadMoreCallback, i3, (Music.StationSoundList) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$StationPlaylistController$y3-8K5BhIB4XcNbXSc3ZSb4fOdU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StationPlaylistController.this.a(metadataLoadMoreCallback, i3, (Throwable) obj);
                }
            });
        } else {
            playByIndex(i3, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Music.StationSoundList stationSoundList) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Music.StationSoundList stationSoundList) throws Exception {
        this.c = stationSoundList.isEnd;
        boolean z = true;
        if (stationSoundList.soundList != null && stationSoundList.soundList.size() > 0) {
            this.i += stationSoundList.soundList.size();
            this.a.addAll(d(stationSoundList.soundList));
            this.trackDataList = sendPlaylist(metadataLoadMoreCallback != null);
            if (metadataLoadMoreCallback != null) {
                if (this.c) {
                    metadataLoadMoreCallback.onLoadMoreEnd();
                } else {
                    metadataLoadMoreCallback.onLoadMoreComplete(true);
                }
            } else if (this.f) {
                this.f = false;
                this.g = o();
                playByIndex(this.g, false);
            } else if (i < 0 || i >= getPlaylistCount()) {
                next();
            } else {
                playByIndex(i, false);
            }
        } else if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreEnd();
        } else {
            if (this.a.size() <= 0) {
                z = false;
            }
            a(z, i);
        }
        this.musicIndexHelper.setLoadingMore(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Throwable th) throws Exception {
        this.musicIndexHelper.setLoadingMore(false);
        boolean z = true;
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(true);
            return;
        }
        if (this.a.size() <= 0) {
            z = false;
        }
        a(z, i, th);
    }

    private void n() {
        if (!ContainerUtil.isEmpty(this.a)) {
            playByIndex(0, false);
        } else {
            e();
        }
    }

    private List<Music.Station> d(List<Music.Station> list) {
        ArrayList arrayList = new ArrayList();
        for (Music.Station station : list) {
            station.albumBought = this.b.bought;
            station.albumSaleType = this.b.saleType;
            station.salesPrice = this.b.salesPrice;
            if (station.isLegal()) {
                arrayList.add(station);
            }
        }
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getMediaType() {
        Music.Station station = this.b;
        if (station == null) {
            return super.getMediaType();
        }
        if ("sound".equals(station.type)) {
            return 4;
        }
        return "radio".equals(this.b.type) ? 6 : 5;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void release() {
        super.release();
        a(this.d);
    }

    private Music.Station c(int i) {
        if (i < 0 || i >= this.a.size()) {
            return null;
        }
        return this.a.get(i);
    }

    private int o() {
        Music.StationHistory stationHistory = this.e;
        if (stationHistory != null) {
            Music.Station c = c(stationHistory.episode - 1);
            if (c != null && this.e.globalId.equals(c.globalID)) {
                return this.e.episode;
            }
            for (int i = 0; i < this.a.size(); i++) {
                if (this.e.globalId.equals(this.a.get(i).globalID)) {
                    return i;
                }
            }
        }
        return 0;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void onResumeBreakpoint() {
        if (this.e != null && getCurrentIndex() == this.g) {
            seekTo(this.e.position);
            this.e = null;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        OrderType orderType = onPlayPaySuccess.orderType;
        L.player.i("StationPlaylistController onPlayPaySuccess orderType=%s", orderType);
        if (orderType == OrderType.ALBUM) {
            Iterator<Music.Station> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().albumBought = true;
            }
        } else if (orderType == OrderType.SINGLE_ALBUM) {
            List<Integer> list = onPlayPaySuccess.indexList;
            while (list.size() > 0) {
                Integer remove = list.remove(0);
                int i = 0;
                while (true) {
                    if (i >= this.a.size()) {
                        break;
                    } else if (i == remove.intValue()) {
                        this.a.get(i).bought = true;
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        this.trackDataList = sendPlaylist(false);
        sendMetadata();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected Common.AudioType getAudioType() {
        return Common.AudioType.BOOKS;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected AudioPlayer.AudioItemV1 getAudioItem() {
        Music.Station c = c(getCurrentIndex());
        if (c == null) {
            return null;
        }
        AudioPlayer.AudioItemV1 audioItemV1 = new AudioPlayer.AudioItemV1();
        AudioPlayer.ItemId itemId = new AudioPlayer.ItemId();
        AudioPlayer.ContentProvider contentProvider = new AudioPlayer.ContentProvider();
        contentProvider.setId(c.cpID);
        contentProvider.setName(c.cp);
        Music.Station station = this.b;
        if (station != null) {
            contentProvider.setAlbumId(station.cpAlbumId);
        }
        contentProvider.setEpisodeIndex(c.episodesNum);
        itemId.setCp(contentProvider);
        itemId.setAudioId(c.audioID);
        audioItemV1.setItemId(itemId);
        return audioItemV1;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canLoadMore() {
        return !k() && !this.c && !this.musicIndexHelper.isLoadingMore();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doLoadMore(MetadataLoadMoreCallback metadataLoadMoreCallback) {
        if (canLoadMore()) {
            a(this.i, 20, -1, metadataLoadMoreCallback);
        } else if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
        }
    }
}
