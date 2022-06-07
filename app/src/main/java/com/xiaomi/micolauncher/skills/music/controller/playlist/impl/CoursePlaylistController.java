package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PlayV3Pact;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.alarm.model.BabyCourseHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class CoursePlaylistController extends BasePlaylistController {
    private ArrayList<Music.Station> a = new ArrayList<>();
    private String b;
    private Music.BabyCourse c;
    private long d;
    private int e;
    private Music.Station f;
    private boolean g;

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getMediaType() {
        return 4;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public void setLoopType(int i) {
    }

    public CoursePlaylistController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    public void playCourse(String str) {
        Logger logger = L.player;
        logger.d("CoursePlaylistController playCourse=" + str);
        j();
        this.a.clear();
        this.dialogId = null;
        this.g = false;
        this.b = str;
        this.musicIndexHelper.setLoopType(LoopType.LIST_LOOP.ordinal(), false);
        a(str);
    }

    @SuppressLint({"CheckResult"})
    private void a(String str) {
        a(this.loadMoreDisposable);
        this.loadMoreDisposable = BabyCourseHelper.loadBabyCourse(str).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$cnb4Svf5uyaLQdOCKOL2YpLERO8
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = CoursePlaylistController.this.b((Music.BabyCourse) obj);
                return b;
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$rn8MrnwkyZ85Ew0MD7yJOnSfQ04
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = CoursePlaylistController.this.b((Music.Station) obj);
                return b;
            }
        }).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$oQmtgGByfuBZVgsur9AbHfX_zXU
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean h;
                h = CoursePlaylistController.this.h((List) obj);
                return h;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$lkSc0ENlvua-0kLR0-Abn1w40n8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CoursePlaylistController.this.g((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$x3D4ZtnadOvd79HzQ3D4XlQLYc4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CoursePlaylistController.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource b(Music.BabyCourse babyCourse) throws Exception {
        this.c = babyCourse;
        return ApiManager.minaService.getStationInfo(babyCourse.playCPId, babyCourse.playCP, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource b(Music.Station station) throws Exception {
        this.f = station;
        return BabyCourseHelper.loadStationList(this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean h(List list) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g(List list) throws Exception {
        if (!list.isEmpty()) {
            this.e += list.size();
            this.a.addAll(d(list));
            playByIndex(0, false);
            this.trackDataList = sendPlaylist(false);
            k();
            l();
            return;
        }
        loadMore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.player.e("CoursePlaylistController loadCourseList error", th);
        a(this.a.size() > 0, 0, th);
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
            Music.Station station = this.a.get(i);
            Music.Station station2 = this.f;
            arrayList2.add(Remote.Response.TrackData.from(station, i, station2 != null ? station2.cpAlbumId : ""));
        }
        a(arrayList2, z);
        return arrayList2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        if (this.a.size() <= getCurrentIndex()) {
            return null;
        }
        Remote.Response.PlayingData from = Remote.Response.PlayingData.from(this.a.get(getCurrentIndex()));
        from.cover = this.f.cover;
        return from;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doPlay(final int i) {
        a(this.loadMoreDisposable);
        c(i);
        final Music.Station e = e(i);
        if (e == null || TextUtils.isEmpty(e.playUrl)) {
            a(i);
            return;
        }
        StreamInfo streamInfo = new StreamInfo();
        streamInfo.url = e.playUrl;
        streamInfo.authentication = e.authentication;
        this.cpResourceDisposable = StreamHelper.buildUrl(streamInfo).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$bq1bpZcSsJVHX3kX4BPCOfjUyqw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CoursePlaylistController.this.a(e, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$RecoDsa3oOI4kpkj0nIKxLis_pM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CoursePlaylistController.this.b(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.Station station, String str) throws Exception {
        a(str, station.audioID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i, Throwable th) throws Exception {
        a(th, i);
    }

    private void c(int i) {
        Music.BabyCourse babyCourse = this.c;
        if (babyCourse != null) {
            babyCourse.updatePlayIndex(i);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        loadMore(this.a.size());
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore(int i) {
        a(i, (MetadataLoadMoreCallback) null);
    }

    private void a(final int i, final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        if (this.c != null) {
            this.musicIndexHelper.setLoadingMore(true);
            a(this.loadMoreDisposable);
            this.loadMoreDisposable = ApiManager.minaService.getStationSoundList(this.c.playCPId, this.c.playCP, "", this.e, 20, PlayV3Pact.NOT_REVERSE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$RePCPOlLuGal_vKbO62rFLcoktg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CoursePlaylistController.this.a(metadataLoadMoreCallback, i, (Music.StationSoundList) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$NbeKo5wt9lzREinutwX9zIZzu_c
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CoursePlaylistController.this.a(metadataLoadMoreCallback, i, (Throwable) obj);
                }
            });
            return;
        }
        L.player.w("CoursePlaylistController load more current is empty");
        boolean z = false;
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
            return;
        }
        if (this.a.size() > 0) {
            z = true;
        }
        a(z, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Music.StationSoundList stationSoundList) throws Exception {
        this.g = stationSoundList.isEnd;
        if (stationSoundList != null && !ContainerUtil.isEmpty(stationSoundList.soundList)) {
            this.e += stationSoundList.soundList.size();
            this.a.addAll(d(stationSoundList.soundList));
            this.trackDataList = sendPlaylist(metadataLoadMoreCallback != null);
            if (metadataLoadMoreCallback != null) {
                if (this.g) {
                    metadataLoadMoreCallback.onLoadMoreEnd();
                } else {
                    metadataLoadMoreCallback.onLoadMoreComplete(true);
                }
            } else if (i <= 0 || i >= getPlaylistCount()) {
                next();
            } else {
                playByIndex(i, false);
            }
        } else if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreEnd();
        } else {
            d(i);
        }
        this.musicIndexHelper.setLoadingMore(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Throwable th) throws Exception {
        L.player.e("CoursePlaylistController laodMore error", th);
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
        } else {
            a(this.a.size() > 0, i, th);
        }
        this.musicIndexHelper.setLoadingMore(false);
    }

    @SuppressLint({"CheckResult"})
    private void d(final int i) {
        boolean z = true;
        if (System.currentTimeMillis() - this.d < TimeUnit.SECONDS.toMillis(30L)) {
            if (this.a.size() <= 0) {
                z = false;
            }
            a(z, i);
            return;
        }
        this.g = false;
        this.d = System.currentTimeMillis();
        if (this.c != null) {
            a(this.loadMoreDisposable);
            this.loadMoreDisposable = BabyCourseHelper.nextCourse(this.c).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$l1OC9lojKCHCKMP4d-xUcTplpLM
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource a;
                    a = CoursePlaylistController.this.a((Music.BabyCourse) obj);
                    return a;
                }
            }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$bSAP2PaZVbOvjMdqqI1Z8LCAu8Y
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource a;
                    a = CoursePlaylistController.this.a((Music.Station) obj);
                    return a;
                }
            }).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$UMMjIjMgu6_lU-HlIzzYF71CoWQ
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    boolean f;
                    f = CoursePlaylistController.this.f((List) obj);
                    return f;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$vUe6YNvXcSE3kv41WsMoRQ8vpYg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CoursePlaylistController.this.e((List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$CoursePlaylistController$MGogevUL02Oh0Yk0uhtcZGXbuHg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    CoursePlaylistController.this.a(i, (Throwable) obj);
                }
            });
            return;
        }
        L.player.w("CoursePlaylistController load next current is empty");
        if (this.a.size() <= 0) {
            z = false;
        }
        a(z, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(Music.BabyCourse babyCourse) throws Exception {
        this.c = babyCourse;
        return ApiManager.minaService.getStationInfo(babyCourse.playCPId, babyCourse.playCP, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(Music.Station station) throws Exception {
        this.f = station;
        return BabyCourseHelper.loadStationList(this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean f(List list) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(List list) throws Exception {
        if (!list.isEmpty()) {
            this.e = list.size();
            this.a.clear();
            this.a.addAll(d(list));
            playByIndex(0, false);
            this.trackDataList = sendPlaylist(false);
            return;
        }
        loadMore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Throwable th) throws Exception {
        L.player.e("CoursePlaylistController load next current error", th);
        a(this.a.size() > 0, i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void release() {
        super.release();
        i();
        this.c = null;
        this.a.clear();
    }

    private Music.Station e(int i) {
        if (i < 0 || i >= this.a.size()) {
            return null;
        }
        return this.a.get(i);
    }

    private void k() {
        Music.BabyCourse babyCourse = this.c;
        if (babyCourse != null) {
            if (babyCourse.stopByCount > 0) {
                pausePlayerByFinish(this.c.stopByCount);
            } else if (this.c.stopByTime > 0) {
                Remote.Request.PlayerShutdown playerShutdown = new Remote.Request.PlayerShutdown();
                playerShutdown.action = Remote.Request.PlayerShutdown.ACTION_PAUSE_LATER;
                playerShutdown.interval = TimeUnit.MILLISECONDS.toSeconds(this.c.stopByTime);
                PlayerApi.setPlayerShutdownTimer(playerShutdown);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    void g() {
        if (this.c != null) {
            if (isCompletePause()) {
                this.c.updatePlayIndex(getCurrentIndex() + 1);
            }
            this.c.playPosition = h();
            Music.BabyCourse babyCourse = this.c;
            babyCourse.alarmId = this.b;
            babyCourse.uploadPlayState();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void onResumeBreakpoint() {
        Music.BabyCourse babyCourse;
        if (getCurrentIndex() == 0 && (babyCourse = this.c) != null && babyCourse.playPosition > 0) {
            seekTo(this.c.playPosition);
        }
    }

    private void l() {
        PromptSoundPlayer.getInstance().play(this.mContext, R.raw.tip_baby_course);
    }

    private List<Music.Station> d(List<Music.Station> list) {
        if (this.f == null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (Music.Station station : list) {
            station.albumBought = this.f.bought;
            station.albumSaleType = this.f.saleType;
            station.salesPrice = this.f.salesPrice;
            if (station.isLegal()) {
                arrayList.add(station);
            }
        }
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        OrderType orderType = onPlayPaySuccess.orderType;
        L.player.i("CoursePlaylistController onPlayPaySuccess orderType=%s", orderType);
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
    protected AudioPlayer.AudioItemV1 getAudioItem() {
        Music.Station station;
        if (this.a.size() <= getCurrentIndex() || (station = this.a.get(getCurrentIndex())) == null) {
            return null;
        }
        AudioPlayer.AudioItemV1 audioItemV1 = new AudioPlayer.AudioItemV1();
        AudioPlayer.ItemId itemId = new AudioPlayer.ItemId();
        AudioPlayer.ContentProvider contentProvider = new AudioPlayer.ContentProvider();
        contentProvider.setId(station.cpID);
        contentProvider.setName(station.cp);
        Music.Station station2 = this.f;
        if (station2 != null) {
            contentProvider.setAlbumId(station2.cpAlbumId);
        }
        contentProvider.setEpisodeIndex(station.episodesNum);
        itemId.setCp(contentProvider);
        itemId.setAudioId(station.audioID);
        audioItemV1.setItemId(itemId);
        return audioItemV1;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected Common.AudioType getAudioType() {
        return Common.AudioType.BOOKS;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canLoadMore() {
        return !this.musicIndexHelper.isLoadingMore() && !this.g;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canPreLoadMore() {
        Music.Station e;
        return getAudioType() == Common.AudioType.BOOKS && (e = e(0)) != null && e.episodes > 0;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doLoadMore(MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(this.e, metadataLoadMoreCallback);
    }
}
