package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PlayV3Pact;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.controller.PlayerHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import com.xiaomi.micolauncher.skills.music.vip.OrderType;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public class MusicPlaylistController extends BasePlaylistController {
    private Remote.Request.Playlist_Message a;
    private PlayV3Pact.V3Payload b;
    private List<PlayV3Pact.AudioItem> c = new ArrayList();
    private List<Music.AudioInfo> d;
    private Disposable e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;

    public MusicPlaylistController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    public void playPlaylist(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.a = (Remote.Request.Playlist_Message) Gsons.getGson().fromJson(str, (Class<Object>) Remote.Request.Playlist_Message.class);
            if (this.a.music == null) {
                L.player.i("mMessage music is empty");
                return;
            }
            if (getPlayer().isPlaying()) {
                getPlayer().pause();
            }
            a(this.a.music);
        }
    }

    private void a(String str) {
        Disposable disposable = this.e;
        if (disposable != null) {
            disposable.dispose();
        }
        this.b = ((PlayV3Pact) Gsons.getGson().fromJson(str, (Class<Object>) PlayV3Pact.class)).payload;
        PlayV3Pact.V3Payload v3Payload = this.b;
        if (v3Payload == null || ContainerUtil.isEmpty(v3Payload.audio_items)) {
            L.player.i("audio_items is empty");
            return;
        }
        j();
        this.c.clear();
        this.d = null;
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = false;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < this.b.audio_items.size(); i2++) {
            PlayV3Pact.AudioItem audioItem = this.b.audio_items.get(i2);
            if (audioItem.item_id.cp != null && !TextUtils.isEmpty(audioItem.item_id.cp.name) && !TextUtils.isEmpty(audioItem.item_id.cp.id)) {
                arrayList.add(audioItem);
                arrayList2.add(audioItem.item_id.audio_id);
            } else if (i2 < this.a.start) {
                i++;
            }
        }
        if (this.a.start > 0 && i > 0) {
            this.a.start -= i;
        }
        if ("MUSIC".equalsIgnoreCase(this.b.audio_type)) {
            a(arrayList2, arrayList);
        } else {
            b(arrayList2, arrayList);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected String buildDialogId() {
        return String.format("%s_%s", "app", UUID.randomUUID().toString());
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public List<Remote.Response.TrackData> sendPlaylist(boolean z) {
        ArrayList<Remote.Response.TrackData> playlist = getPlaylist();
        if (ContainerUtil.isEmpty(playlist)) {
            L.player.d("sendPlaylist fail trackDataList is empty");
            return null;
        }
        a(playlist, z);
        return playlist;
    }

    private void a(final List<String> list, final List<PlayV3Pact.AudioItem> list2) {
        L.player.d("MusicPlaylistController getMusicAudioInfo app play music size = %s", Integer.valueOf(list.size()));
        if (ContainerUtil.isEmpty(list)) {
            L.player.i("app play audio is empty");
        } else {
            this.e = Observable.fromIterable(d(list)).flatMap($$Lambda$H9EkFFMInTtkBum6CLZBbtCQ5C4.INSTANCE).toList().map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$FHc4fuVEeFlTQLSjByJdfy-6wN8
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    List matchAudioInfo;
                    matchAudioInfo = AudioInfoCache.matchAudioInfo((List) obj, list);
                    return matchAudioInfo;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$WF8xRIQiFwTJ5ojWFKeiSXOHxRE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPlaylistController.this.e(list2, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$Bs829Im4MUvRe2P7b3vl_1Ifri4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPlaylistController.this.b(list2, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(List list, List list2) throws Exception {
        if (!isReleased()) {
            this.c = list;
            this.d = list2;
            onInitDataLoadSuccess(this.a.start, LoopType.LIST_LOOP.ordinal());
            this.trackDataList = sendPlaylist(false);
            if (this.trackDataList != null) {
                playByIndex(this.a.start, false);
            } else {
                a(false, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(List list, Throwable th) throws Exception {
        L.player.e("MusicPlaylistController getMusicAudioInfo error", th);
        if (!isReleased()) {
            this.c = list;
            onInitDataLoadSuccess(this.a.start, LoopType.LIST_LOOP.ordinal());
            this.trackDataList = sendPlaylist(false);
            playByIndex(this.a.start, false);
        }
    }

    private void b(final List<String> list, final List<PlayV3Pact.AudioItem> list2) {
        Logger logger = L.player;
        logger.d("app play size = " + list.size());
        if (ContainerUtil.isEmpty(list)) {
            L.player.i("app play audio is empty");
        } else {
            this.e = Observable.fromIterable(d(list)).flatMap($$Lambda$H9EkFFMInTtkBum6CLZBbtCQ5C4.INSTANCE).toList().map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$SgQzVuz9zUwAg-BzKu4VVZ1rjC8
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    List matchAudioInfo;
                    matchAudioInfo = AudioInfoCache.matchAudioInfo((List) obj, list);
                    return matchAudioInfo;
                }
            }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$58pRFcL--6TL_m_6irM7itHlJLs
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    SingleSource e;
                    e = MusicPlaylistController.this.e((List) obj);
                    return e;
                }
            }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$27ghNSH0kIuQyeVb33wmN9Gohbc
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    SingleSource a;
                    a = MusicPlaylistController.this.a((Music.Station) obj);
                    return a;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$sMb030WGPxafbK7a_4p6FacpvlU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPlaylistController.this.c(list2, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$aFa2csHYVdCKGq9yApnU7xbzX08
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPlaylistController.this.a(list2, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ SingleSource e(List list) throws Exception {
        this.d = list;
        if (!"BOOKS".equalsIgnoreCase(this.b.audio_type)) {
            return Single.just(new Music.Station());
        }
        PlayV3Pact.AudioItem audioItem = this.b.audio_items.get(0);
        return ApiManager.minaService.getStationInfoSingle(audioItem.item_id.cp.album_id, audioItem.item_id.cp.name, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ SingleSource a(Music.Station station) throws Exception {
        if (station != null && !TextUtils.isEmpty(station.cp) && !TextUtils.isEmpty(station.cpID)) {
            for (Music.AudioInfo audioInfo : this.d) {
                audioInfo.salesPrice = station.salesPrice;
                audioInfo.playSequence = station.playSequence;
                audioInfo.albumGlobalID = station.albumGlobalID;
            }
        }
        return Single.just(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(List list, List list2) throws Exception {
        this.c = list;
        this.d = list2;
        onInitDataLoadSuccess(this.a.start, LoopType.LIST_LOOP.ordinal());
        this.trackDataList = sendPlaylist(false);
        if (this.trackDataList != null) {
            playByIndex(this.a.start, false);
        } else {
            a(false, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, Throwable th) throws Exception {
        L.player.e("MusicPlaylistController getAudioInfo error", th);
        this.c = list;
        onInitDataLoadSuccess(this.a.start, LoopType.LIST_LOOP.ordinal());
        this.trackDataList = sendPlaylist(false);
        playByIndex(this.a.start, false);
    }

    private List<List<String>> d(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (list.size() > 49) {
            int i = 0;
            while (i < list.size()) {
                int i2 = i + 49;
                arrayList.add(list.subList(i, i2 > list.size() ? list.size() : i2));
                i = i2;
            }
        } else {
            arrayList.add(list);
        }
        return arrayList;
    }

    public ArrayList<Remote.Response.TrackData> getPlaylist() {
        ArrayList<Remote.Response.TrackData> arrayList = new ArrayList<>();
        if (ContainerUtil.hasData(this.d)) {
            for (int i = 0; i < this.d.size(); i++) {
                Music.AudioInfo audioInfo = this.d.get(i);
                if (audioInfo != null) {
                    arrayList.add(Remote.Response.TrackData.from(audioInfo, i));
                }
            }
        }
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getPlaylistCount() {
        List<Music.AudioInfo> list = this.d;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doPlay(int i) {
        if (i < 0 || i >= this.c.size()) {
            L.player.i("play error index out");
            return;
        }
        PlayV3Pact.AudioItem audioItem = this.c.get(i);
        if (audioItem != null) {
            a(audioItem, i);
        } else {
            a(i);
        }
    }

    private void a(PlayV3Pact.AudioItem audioItem, final int i) {
        final String str = audioItem.item_id.audio_id;
        this.cpResourceDisposable = PlayerHelper.processAudioUrl(audioItem, getMediaType() == 6).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$_yEhCiFjQ_qEBjODbVmtKbF18Rg
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean b;
                b = MusicPlaylistController.this.b((String) obj);
                return b;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$r3WpeSZmnmZL4OJ1LGfTdwlLNUE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPlaylistController.this.c(str, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$gTqRy4-hT72GTk5U744-uilg7Og
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPlaylistController.this.a(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(String str) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(String str, String str2) throws Exception {
        if (!TextUtils.isEmpty(str2)) {
            a(str2, str);
            return;
        }
        this.musicIndexHelper.setLoadingMore(false);
        next();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Throwable th) throws Exception {
        a(th, i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        Music.AudioInfo audioInfo;
        int currentIndex = getCurrentIndex();
        List<Music.AudioInfo> list = this.d;
        if (list == null || list.size() <= currentIndex || this.c.size() <= currentIndex || (audioInfo = this.d.get(currentIndex)) == null) {
            return null;
        }
        return Remote.Response.PlayingData.from(audioInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0054 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0057 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0059 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005a A[RETURN] */
    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getMediaType() {
        /*
            r5 = this;
            com.xiaomi.micolauncher.api.model.PlayV3Pact$V3Payload r0 = r5.b
            r1 = 3
            if (r0 == 0) goto L_0x005c
            java.lang.String r0 = r0.audio_type
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x005c
            r2 = -1
            int r3 = r0.hashCode()
            r4 = -1758903120(0xffffffff972944b0, float:-5.469354E-25)
            if (r3 == r4) goto L_0x0045
            r4 = 2392787(0x2482d3, float:3.353009E-39)
            if (r3 == r4) goto L_0x003b
            r4 = 63384202(0x3c72a8a, float:1.1705935E-36)
            if (r3 == r4) goto L_0x0031
            r4 = 73725445(0x464f605, float:2.6914238E-36)
            if (r3 == r4) goto L_0x0027
            goto L_0x004f
        L_0x0027:
            java.lang.String r3 = "MUSIC"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x004f
            r0 = 1
            goto L_0x0050
        L_0x0031:
            java.lang.String r3 = "BOOKS"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x004f
            r0 = 2
            goto L_0x0050
        L_0x003b:
            java.lang.String r3 = "NEWS"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x004f
            r0 = r1
            goto L_0x0050
        L_0x0045:
            java.lang.String r3 = "RADIO_STATION"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x004f
            r0 = 0
            goto L_0x0050
        L_0x004f:
            r0 = r2
        L_0x0050:
            switch(r0) {
                case 0: goto L_0x005a;
                case 1: goto L_0x0059;
                case 2: goto L_0x0057;
                case 3: goto L_0x0054;
                default: goto L_0x0053;
            }
        L_0x0053:
            goto L_0x005c
        L_0x0054:
            r0 = 15
            return r0
        L_0x0057:
            r0 = 4
            return r0
        L_0x0059:
            return r1
        L_0x005a:
            r0 = 6
            return r0
        L_0x005c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.MusicPlaylistController.getMediaType():int");
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected Common.AudioType getAudioType() {
        PlayV3Pact.V3Payload v3Payload = this.b;
        if (v3Payload == null || TextUtils.isEmpty(v3Payload.audio_type)) {
            return Common.AudioType.MUSIC;
        }
        String str = this.b.audio_type;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1758903120) {
            if (hashCode != 2392787) {
                if (hashCode != 63384202) {
                    if (hashCode == 73725445 && str.equals("MUSIC")) {
                        c = 1;
                    }
                } else if (str.equals("BOOKS")) {
                    c = 2;
                }
            } else if (str.equals("NEWS")) {
                c = 3;
            }
        } else if (str.equals(PlayV3Pact.TYPE_RADIO_STATION)) {
            c = 0;
        }
        switch (c) {
            case 0:
                return Common.AudioType.RADIO_STATION;
            case 1:
                return Common.AudioType.MUSIC;
            case 2:
                return Common.AudioType.BOOKS;
            case 3:
                return Common.AudioType.NEWS;
            default:
                return Common.AudioType.MUSIC;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        loadMore(0);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore(int i) {
        a(i, (MetadataLoadMoreCallback) null);
    }

    private void a(int i, MetadataLoadMoreCallback metadataLoadMoreCallback) {
        PlayV3Pact.V3Payload v3Payload = this.b;
        if (v3Payload == null) {
            L.player.i("MusicPlaylistController payload is empty");
            return;
        }
        boolean z = false;
        if (!v3Payload.needs_loadmore) {
            playByIndex(i, false);
            return;
        }
        PlayV3Pact.ListParams listParams = this.b.list_params;
        if (PlayV3Pact.LIST_TYPE_STATION.equals(listParams.type)) {
            if (this.g == 0) {
                this.g = listParams.loadmore_offset;
            }
            a(listParams.listId, this.g, 20, i, TextUtils.isEmpty(listParams.order) ? PlayV3Pact.NOT_REVERSE : listParams.order, metadataLoadMoreCallback);
        } else if (!PlayV3Pact.LIST_TYPE_PLAYLIST.equals(listParams.type)) {
        } else {
            if (this.i) {
                L.player.i("MusicPlaylistController loadMore song musicLoadMoreEnd=true");
                playByIndex(i, false);
                return;
            }
            if (this.f == 0) {
                this.f = listParams.loadmore_offset;
            }
            try {
                a(Long.parseLong(listParams.listId), this.f, 20, i, metadataLoadMoreCallback);
            } catch (NumberFormatException e) {
                Logger logger = L.player;
                logger.e("MusicPlaylistController loadMore song error listId=" + listParams.listId, e);
                if (metadataLoadMoreCallback != null) {
                    metadataLoadMoreCallback.onLoadMoreEnd();
                    return;
                }
                if (this.d.size() > 0) {
                    z = true;
                }
                a(z, i);
            }
        }
    }

    private void a(long j, int i, int i2, final int i3, final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(this.loadMoreDisposable);
        this.musicIndexHelper.setLoadingMore(true);
        this.loadMoreDisposable = ApiManager.minaService.getChannelInfo(j, i, i2).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$stCjBUb8BiuyiWPhkRfwlGWy1TI
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean b;
                b = MusicPlaylistController.this.b((Music.Channel) obj);
                return b;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$Qf7TTdZm0u6hBMLmGIDMrjSNjXo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPlaylistController.this.a(metadataLoadMoreCallback, i3, (Music.Channel) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$UUHY9kGf1kSEOhQ7a30bXSFKkZ0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPlaylistController.this.b(metadataLoadMoreCallback, i3, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(Music.Channel channel) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Music.Channel channel) throws Exception {
        boolean z = true;
        if (channel.songList == null || channel.songList.size() <= 0) {
            this.i = true;
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreEnd();
            } else {
                if (this.d.size() <= 0) {
                    z = false;
                }
                a(z, i);
            }
        } else {
            if (channel.nextStartOffset == 0) {
                this.i = true;
                this.loadMoreEnd = true;
                this.f += channel.songList.size();
            } else if (channel.nextStartOffset > 0) {
                this.f = channel.nextStartOffset;
            }
            for (Music.Song song : channel.songList) {
                if (!TextUtils.isEmpty(song.origin) && !TextUtils.isEmpty(song.originSongID) && !song.isNoPlayable()) {
                    this.d.add(Music.AudioInfo.from(song));
                    this.c.add(PlayV3Pact.AudioItem.buildSong(song));
                }
            }
            this.trackDataList = sendPlaylist(metadataLoadMoreCallback != null);
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreComplete(true);
            } else {
                L.player.d("MusicPlaylistController loadMoreSong End, noMore Data=%s", Boolean.valueOf(this.i));
                playByIndex(i, !this.i);
            }
        }
        this.musicIndexHelper.setLoadingMore(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Throwable th) throws Exception {
        boolean z = false;
        this.musicIndexHelper.setLoadingMore(false);
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
            return;
        }
        if (this.d.size() > 0) {
            z = true;
        }
        a(z, i, th);
    }

    private void a(String str, int i, int i2, final int i3, String str2, final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        if (!this.h || this.d.size() <= 0) {
            a(this.loadMoreDisposable);
            if (ContainerUtil.isEmpty(this.d)) {
                L.player.i("loadMoreStation audioInfoList is empty");
                if (metadataLoadMoreCallback != null) {
                    metadataLoadMoreCallback.onLoadMoreComplete(false);
                    return;
                }
                return;
            }
            this.musicIndexHelper.setLoadingMore(true);
            final Music.AudioInfo audioInfo = this.d.get(0);
            this.loadMoreDisposable = ApiManager.minaService.getStationSoundList(str, audioInfo.cpName, "", i, i2, str2).subscribeOn(MicoSchedulers.io()).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$k7uUolqc-6c7hM_1ckpDMNEMPGc
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    boolean a;
                    a = MusicPlaylistController.this.a((Music.StationSoundList) obj);
                    return a;
                }
            }).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$XOLecR_h7r6n1YKtGaAuM7VvdY8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPlaylistController.this.a(audioInfo, i3, metadataLoadMoreCallback, (Music.StationSoundList) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$MusicPlaylistController$wA16o1cw9uGDCp6Aiyv6CVfMxQM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPlaylistController.this.a(metadataLoadMoreCallback, i3, (Throwable) obj);
                }
            });
        } else if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreEnd();
        } else {
            playByIndex(i3, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Music.StationSoundList stationSoundList) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Music.AudioInfo audioInfo, int i, MetadataLoadMoreCallback metadataLoadMoreCallback, Music.StationSoundList stationSoundList) throws Exception {
        if (stationSoundList != null) {
            a(audioInfo, stationSoundList, i, metadataLoadMoreCallback);
        }
        this.musicIndexHelper.setLoadingMore(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MetadataLoadMoreCallback metadataLoadMoreCallback, int i, Throwable th) throws Exception {
        boolean z = false;
        this.musicIndexHelper.setLoadingMore(false);
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
            return;
        }
        if (this.d.size() > 0) {
            z = true;
        }
        a(z, i, th);
    }

    private void a(Music.AudioInfo audioInfo, Music.StationSoundList stationSoundList, int i, MetadataLoadMoreCallback metadataLoadMoreCallback) {
        this.h = stationSoundList.isEnd;
        boolean z = true;
        if (stationSoundList.soundList != null && stationSoundList.soundList.size() > 0) {
            for (Music.Station station : stationSoundList.soundList) {
                Music.AudioInfo from = Music.AudioInfo.from(station);
                from.bought = audioInfo.bought;
                from.albumSaleType = audioInfo.albumSaleType;
                from.salesPrice = audioInfo.salesPrice;
                this.d.add(from);
                this.c.add(PlayV3Pact.AudioItem.buildStation(station));
            }
            this.g += stationSoundList.soundList.size();
            this.trackDataList = sendPlaylist(metadataLoadMoreCallback != null);
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreComplete(true);
            } else if (i <= 0 || i >= getPlaylistCount()) {
                next();
            } else {
                playByIndex(i, false);
            }
        } else if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreEnd();
        } else {
            if (this.d.size() <= 0) {
                z = false;
            }
            a(z, i);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void release() {
        super.release();
        List<Music.AudioInfo> list = this.d;
        if (list != null) {
            list.clear();
        }
        List<PlayV3Pact.AudioItem> list2 = this.c;
        if (list2 != null) {
            list2.clear();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected AudioPlayer.AudioItemV1 getAudioItem() {
        Music.AudioInfo audioInfo;
        List<Music.AudioInfo> list = this.d;
        if (list == null || list.size() <= getCurrentIndex() || (audioInfo = this.d.get(getCurrentIndex())) == null) {
            return null;
        }
        AudioPlayer.AudioItemV1 audioItemV1 = new AudioPlayer.AudioItemV1();
        AudioPlayer.ItemId itemId = new AudioPlayer.ItemId();
        AudioPlayer.ContentProvider contentProvider = new AudioPlayer.ContentProvider();
        contentProvider.setId(audioInfo.cpID);
        contentProvider.setName(audioInfo.cpName);
        PlayV3Pact.V3Payload v3Payload = this.b;
        if (!(v3Payload == null || v3Payload.list_params == null || this.b.list_params.listId == null)) {
            contentProvider.setAlbumId(this.b.list_params.listId);
        }
        contentProvider.setEpisodeIndex(audioInfo.episodesNum);
        itemId.setCp(contentProvider);
        itemId.setAudioId(audioInfo.audioID);
        audioItemV1.setItemId(itemId);
        return audioItemV1;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        OrderType orderType = onPlayPaySuccess.orderType;
        L.player.i("RecentlyMusicController onPlayPaySuccess orderType=%s", orderType);
        switch (orderType) {
            case MUSIC:
                for (Remote.Response.TrackData trackData : this.trackDataList) {
                    trackData.unplayableCode = 0;
                }
                break;
            case ALBUM:
                for (Remote.Response.TrackData trackData2 : this.trackDataList) {
                    trackData2.albumBought = true;
                }
                break;
            case SINGLE_ALBUM:
                List<Integer> list = onPlayPaySuccess.indexList;
                while (list.size() > 0) {
                    Integer remove = list.remove(0);
                    int i = 0;
                    while (true) {
                        if (i >= this.trackDataList.size()) {
                            break;
                        } else if (i == remove.intValue()) {
                            ((Remote.Response.TrackData) this.trackDataList.get(i)).bought = true;
                        } else {
                            i++;
                        }
                    }
                }
                break;
        }
        a(this.trackDataList);
        sendMetadata();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canLoadMore() {
        PlayV3Pact.V3Payload v3Payload;
        if (this.musicIndexHelper.isLoadingMore()) {
            return false;
        }
        return (!this.h || !ContainerUtil.hasData(this.d)) && !this.i && (v3Payload = this.b) != null && v3Payload.needs_loadmore;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doLoadMore(MetadataLoadMoreCallback metadataLoadMoreCallback) {
        a(-1, metadataLoadMoreCallback);
    }
}
