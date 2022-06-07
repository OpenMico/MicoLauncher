package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.common.Optional;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.SetupManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import com.xiaomi.micolauncher.skills.music.vip.OrderType;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AudioItemListController extends BasePlaylistController {
    public static final int DEFAULT_DELTA = 10;
    private AudioPlayer.Play a;
    private Common.AudioType b;
    private String h;
    private String i;
    private boolean l;
    private boolean m;
    private Music.Station n;
    private Disposable o;
    protected String originId;
    private int p;
    private int q;
    private long r;
    private volatile int s;
    private List<AudioPlayer.AudioItemV1> c = new ArrayList();
    private List<Music.AudioInfo> d = new ArrayList();
    private HashMap<String, Music.AudioInfo> e = new HashMap<>();
    private HashMap<String, Template.PlayInfoItem> f = new HashMap<>();
    private HashMap<String, String> g = new HashMap<>();
    private int j = 10;
    private boolean k = false;

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected void clearAudioBookPlayList() {
    }

    public AudioItemListController(Context context, MediaSession mediaSession, com.xiaomi.micolauncher.skills.music.controller.AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        int currentIndex = getCurrentIndex();
        AudioPlayer.AudioItemV1 k = k();
        if (k == null || ContainerUtil.isOutOfBound(currentIndex, this.trackDataList)) {
            return null;
        }
        Remote.Response.PlayingData from = Remote.Response.PlayingData.from((Remote.Response.TrackData) this.trackDataList.get(currentIndex));
        from.screenExtend.playlistId = 0L;
        from.screenExtend.offset = currentIndex;
        from.screenExtend.mediaType = getMediaType();
        from.screenExtend.favoritesId = this.r;
        if (k.getItemId().getAlbumId() != null && k.getItemId().getAlbumId().isPresent()) {
            from.albumGlobalID = k.getItemId().getAlbumId().get();
        }
        return from;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public List<Remote.Response.TrackData> sendPlaylist(boolean z) {
        if (this.c.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(this.c.size());
        for (int i = 0; i < this.c.size() && i < this.f.size(); i++) {
            AudioPlayer.AudioItemV1 audioItemV1 = this.c.get(i);
            String audioId = audioItemV1.getItemId().getAudioId();
            Remote.Response.TrackData from = Remote.Response.TrackData.from(audioItemV1, this.f.get(audioId), arrayList.size(), getAudioType(), this.e.get(audioId));
            from.dialogId = getDialogId(audioId);
            from.originId = this.originId;
            Music.Station station = this.n;
            if (station != null) {
                from.salesPrice = station.salesPrice;
                from.playSequence = this.n.playSequence;
            }
            arrayList.add(from);
        }
        a(arrayList, z);
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getPlaylistCount() {
        return this.c.size();
    }

    public void handlePlayList(String str, AudioPlayer.Play play, Template.PlayInfo playInfo, String str2, boolean z, long j) {
        this.g.clear();
        this.originId = str;
        this.dialogId = str;
        this.l = z;
        L.player.d("AudioItemListController handlePlayList cmdDuration=%s, processLoopType loopType=%s", Long.valueOf(j), str2);
        ThreadUtil.getWorkHandler().removeCallbacks(this.cmdPlayCountDown);
        if (j > 0) {
            ThreadUtil.getWorkHandler().postDelayed(this.cmdPlayCountDown, TimeUnit.SECONDS.toMillis(j));
        }
        if (!TextUtils.isEmpty(str2)) {
            this.q = LoopType.valueOfType(str2).getIndex();
        }
        a(str, play, playInfo, false, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, AudioPlayer.Play play, Template.PlayInfo playInfo, boolean z, boolean z2, boolean z3) {
        a(str, play);
        AudioPlayer.PlayBehavior playBehavior = play.getPlayBehavior();
        boolean z4 = false;
        L.player.d("AudioItemListController handlePlayList playBehavior = %s", playBehavior);
        this.h = play.getLoadmoreToken().isPresent() ? play.getLoadmoreToken().get() : "";
        this.j = play.getLoadmoreDelta().isPresent() ? play.getLoadmoreDelta().get().intValue() : 10;
        if (play.isNeedsLoadmore().isPresent()) {
            z4 = play.isNeedsLoadmore().get().booleanValue();
        }
        this.m = z4;
        this.i = play.getOriginId().isPresent() ? play.getOriginId().get() : "";
        switch (playBehavior) {
            case INSERT_FRONT:
            case APPEND:
                a(play, playInfo, z, z2, z3);
                break;
            default:
                a(play, playInfo, z);
                break;
        }
        if (play.getFavoritesId().isPresent()) {
            this.r = play.getFavoritesId().get().longValue();
        }
    }

    private void a(AudioPlayer.Play play, Template.PlayInfo playInfo, final boolean z) {
        boolean z2 = true;
        L.player.i("AudioItemListController replacePlayList audioItems size=%d", Integer.valueOf(play.getAudioItems().size()));
        if (!play.getAudioItems().isEmpty()) {
            this.a = play;
            this.k = true;
            this.n = null;
            this.e.clear();
            this.f.clear();
            this.trackDataList.clear();
            this.c.clear();
            this.c.addAll(play.getAudioItems());
            this.p = 0;
            a(playInfo);
            final Common.AudioType audioType = getAudioType();
            this.b = audioType;
            if (!a(audioType) || !ContainerUtil.hasData(this.c)) {
                z2 = false;
            } else {
                a(this.o);
                List<AudioPlayer.AudioItemV1> audioItems = play.getAudioItems();
                ArrayList arrayList = new ArrayList(audioItems.size());
                for (AudioPlayer.AudioItemV1 audioItemV1 : audioItems) {
                    arrayList.add(audioItemV1.getItemId().getAudioId());
                }
                this.o = AudioInfoCache.getAudioInfoList(arrayList).subscribeOn(MicoSchedulers.io()).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$UQzy_i5JB3Ga8kRU2SbTfgUPnvI
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        ObservableSource a;
                        a = AudioItemListController.this.a(audioType, (List) obj);
                        return a;
                    }
                }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$1TQcUw-AtlHjf31kXmTYlqIWpRM
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        ObservableSource a;
                        a = AudioItemListController.this.a((Music.Station) obj);
                        return a;
                    }
                }).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$j8loItTVqjNuA3hZrHshSuCvt-4
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        AudioItemListController.this.a(z, (List) obj);
                    }
                }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$93E98MhLrIcKC3d5rtWJlRxKPjs
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        AudioItemListController.this.b(z, (Throwable) obj);
                    }
                });
            }
            if (!z2) {
                sendPlaylist(false);
                a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(Common.AudioType audioType, List list) throws Exception {
        this.d = list;
        Music.AudioInfo audioInfo = (Music.AudioInfo) list.get(0);
        if (Common.AudioType.BOOKS != audioType || audioInfo.albumSaleType <= 0) {
            return Observable.just(new Music.Station());
        }
        return ApiManager.minaService.getStationInfo(audioInfo.cpAlbumId, audioInfo.cpName, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(Music.Station station) throws Exception {
        if (station != null && !TextUtils.isEmpty(station.cp) && !TextUtils.isEmpty(station.cpID)) {
            this.n = station;
        }
        return Observable.just(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z, List list) throws Exception {
        onInitDataLoadSuccess(this.p, this.q);
        for (int i = 0; i < list.size(); i++) {
            Music.AudioInfo audioInfo = (Music.AudioInfo) list.get(i);
            this.e.put(audioInfo.audioID, audioInfo);
        }
        if (ContainerUtil.hasData(this.trackDataList)) {
            a((ArrayList) this.trackDataList);
        } else {
            sendPlaylist(false);
        }
        if (!z) {
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(boolean z, Throwable th) throws Exception {
        L.player.e("AudioItemListController replacePlayList audio Info for pay error", th);
        sendPlaylist(false);
        if (!z) {
            a();
        }
    }

    private void a(final AudioPlayer.Play play, Template.PlayInfo playInfo, final boolean z, final boolean z2, final boolean z3) {
        boolean z4 = false;
        L.player.i("AudioItemListController addPlayList audioItems size=%d", Integer.valueOf(play.getAudioItems().size()));
        if (!play.getAudioItems().isEmpty()) {
            this.a = play;
            Common.AudioType audioType = getAudioType();
            if (this.b != audioType) {
                this.c.clear();
            }
            this.b = audioType;
            final AudioPlayer.PlayBehavior playBehavior = play.getPlayBehavior();
            if (playBehavior == AudioPlayer.PlayBehavior.INSERT_FRONT || z2) {
                this.c.addAll(0, play.getAudioItems());
                a(playInfo);
            } else if (playBehavior == AudioPlayer.PlayBehavior.APPEND) {
                this.c.addAll(play.getAudioItems());
                a(playInfo);
            }
            if (a(audioType)) {
                List<AudioPlayer.AudioItemV1> audioItems = play.getAudioItems();
                ArrayList arrayList = new ArrayList(audioItems.size());
                for (AudioPlayer.AudioItemV1 audioItemV1 : audioItems) {
                    arrayList.add(audioItemV1.getItemId().getAudioId());
                }
                this.o = AudioInfoCache.getAudioInfoList(arrayList).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$QnmCTvaqT6fgVoJ35QQGs9Udl7A
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        AudioItemListController.this.a(playBehavior, z2, play, z3, z, (List) obj);
                    }
                }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$1kUozcLZb9PHqQjbtd60S_GVt8M
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        AudioItemListController.this.a(z, (Throwable) obj);
                    }
                });
                z4 = true;
            }
            if (!z4) {
                sendPlaylist(z3);
                sendMetadata();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AudioPlayer.PlayBehavior playBehavior, boolean z, AudioPlayer.Play play, boolean z2, boolean z3, List list) throws Exception {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Music.AudioInfo audioInfo = (Music.AudioInfo) it.next();
            this.e.put(audioInfo.audioID, audioInfo);
        }
        if (playBehavior == AudioPlayer.PlayBehavior.INSERT_FRONT || z) {
            int currentIndex = getCurrentIndex();
            int size = play.getAudioItems().size() + currentIndex;
            L.player.i("AudioItemListController preLoadMore lastIndex=%s, setCurrentIndex=%s", Integer.valueOf(currentIndex), Integer.valueOf(size));
            setCurrentIndex(size);
        }
        sendPlaylist(z2);
        if (!z3) {
            a();
        } else {
            sendMetadata();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z, Throwable th) throws Exception {
        L.player.e("AudioItemListController addPlayList audio Info for pay error", th);
        sendPlaylist(false);
        if (!z) {
            a();
        }
    }

    private void a(Template.PlayInfo playInfo) {
        for (Template.PlayInfoItem playInfoItem : playInfo.getItems()) {
            this.f.put(playInfoItem.getAudioId(), playInfoItem);
        }
    }

    private void a(String str, AudioPlayer.Play play) {
        for (AudioPlayer.AudioItemV1 audioItemV1 : play.getAudioItems()) {
            this.g.put(audioItemV1.getItemId().getAudioId(), str);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public String getDialogId(String str) {
        String str2 = this.g.get(str);
        return TextUtils.isEmpty(str2) ? this.dialogId : str2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public String getOriginDialogId(String str) {
        String originDialogId = OperationManager.getInstance().getOriginDialogId(str);
        return TextUtils.isEmpty(originDialogId) ? this.dialogId : originDialogId;
    }

    private boolean a(Common.AudioType audioType) {
        return audioType == Common.AudioType.BOOKS || audioType == Common.AudioType.MUSIC;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doPlay(int i) {
        if (i >= 0 && i < this.c.size()) {
            AudioPlayer.AudioItemV1 audioItemV1 = this.c.get(i);
            if (audioItemV1 != null) {
                a(audioItemV1, i);
            } else {
                a(i);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public void resetErrorCount() {
        this.mPlayingErrorCount = 0;
        a(this.loadMoreDisposable);
        a(this.cpResourceDisposable);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        loadMore(getPlaylistCount());
    }

    private void a(AudioPlayer.AudioItemV1 audioItemV1, final int i) {
        final AudioPlayer.ItemId itemId = audioItemV1.getItemId();
        this.cpResourceDisposable = StreamHelper.processAudioUrl(audioItemV1.getStream(), getMediaType() == 6).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$lrcVRgvZszu0xfOykp3RpcGUcww
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean a;
                a = AudioItemListController.this.a((String) obj);
                return a;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$hGGTEBSMNqS-yUrZ0B2G1yAsKmY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioItemListController.this.a(itemId, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$AudioItemListController$WoNAb2fGEi4C6W_abry53bVGi0E
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioItemListController.this.a(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(String str) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AudioPlayer.ItemId itemId, String str) throws Exception {
        if (!TextUtils.isEmpty(str)) {
            a(str, itemId.getAudioId());
        } else {
            next();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Throwable th) throws Exception {
        a(th, i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getMediaType() {
        Common.AudioType audioType = getAudioType();
        if (audioType != null) {
            switch (audioType) {
                case RADIO_STATION:
                    return 6;
                case MUSIC:
                    return 3;
                case BOOKS:
                    return 5;
                case NEWS:
                    return 15;
                default:
                    L.player.w("AudioItemListController getMediaType no match, so the type=%s", audioType);
                    break;
            }
        }
        L.player.w("AudioItemListController getMediaType is empty, return default music type");
        return 3;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected Common.AudioType getAudioType() {
        Optional<Common.AudioType> audioType = this.a.getAudioType();
        if (audioType.isPresent()) {
            return audioType.get();
        }
        return Common.AudioType.MUSIC;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void onResumeBreakpoint() {
        AudioPlayer.Play play;
        if (getCurrentIndex() == 0 && this.k && (play = this.a) != null && play.getAudioItems().size() > 0) {
            seekTo(play.getAudioItems().get(0).getStream().getOffsetInMs());
        }
        this.k = false;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected boolean onListPlayFinish() {
        if (!this.l || this.m) {
            return false;
        }
        f();
        return true;
    }

    private AudioPlayer.AudioItemV1 k() {
        int currentIndex = getCurrentIndex();
        if (this.c.size() <= currentIndex || currentIndex < 0) {
            return null;
        }
        return this.c.get(currentIndex);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected AudioPlayer.AudioItemV1 getAudioItem() {
        Music.AudioInfo audioInfo;
        AudioPlayer.AudioItemV1 k = k();
        if (!(k == null || !ContainerUtil.hasData(this.e) || k.getItemId() == null || (audioInfo = this.e.get(k.getItemId().getAudioId())) == null || getAudioType() != Common.AudioType.BOOKS)) {
            if (k.getItemId().getCp().isPresent()) {
                AudioPlayer.ContentProvider contentProvider = k.getItemId().getCp().get();
                if (!contentProvider.getAlbumId().isPresent()) {
                    contentProvider.setAlbumId(audioInfo.cpAlbumId);
                }
            } else {
                AudioPlayer.ContentProvider contentProvider2 = new AudioPlayer.ContentProvider();
                contentProvider2.setId(audioInfo.cpID);
                contentProvider2.setName(audioInfo.cpName);
                contentProvider2.setAlbumId(audioInfo.cpAlbumId);
                k.getItemId().setCp(contentProvider2);
            }
        }
        return k;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void release() {
        super.release();
        this.c.clear();
        this.d.clear();
        this.e.clear();
        this.f.clear();
        if (SetupManager.getInstance().isAllSetUpDone()) {
            OperationManager.getInstance().clearOriginDialogIdMap();
        }
        ThreadUtil.getWorkHandler().removeCallbacks(this.cmdPlayCountDown);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        OrderType orderType = onPlayPaySuccess.orderType;
        L.player.i("AudioItemListController onPlayPaySuccess orderType=%s", orderType);
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
        a((ArrayList) this.trackDataList);
        sendMetadata();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore(int i) {
        a(i, (MetadataLoadMoreCallback) null);
    }

    private void a(final int i, final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        if (!canLoadMore()) {
            L.player.e("loadMore return!!! mLoadMoreToken=%s,mOriginId=%s", this.h, this.i);
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreComplete(false);
            } else {
                playByIndex(i, false);
            }
        } else {
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreStart();
            }
            this.musicIndexHelper.setLoadingMore(true);
            int playlistCount = getPlaylistCount() > 0 ? getPlaylistCount() : 0;
            Nlp.LoadMore loadMore = new Nlp.LoadMore();
            loadMore.setOriginId(this.i);
            loadMore.setOffset(playlistCount);
            loadMore.setDelta(this.j);
            loadMore.setToken(this.h);
            Event buildEvent = APIUtils.buildEvent(loadMore);
            buildEvent.setContext(SpeechContextHelper.getContexts(false));
            SpeechEventUtil.getInstance().eventRequest(buildEvent, new SpeechEventUtil.EventListener() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.AudioItemListController.1
                @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
                public void onEventResult(List<Instruction> list, Event event) {
                    Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.AudioPlayer.Play);
                    Instruction intention2 = InstructionUtil.getIntention(list, AIApiConstants.Template.PlayInfo);
                    if (intention != null && intention2 != null) {
                        String dialogId = InstructionUtil.getDialogId(intention);
                        AudioPlayer.Play play = (AudioPlayer.Play) intention.getPayload();
                        Template.PlayInfo playInfo = (Template.PlayInfo) intention2.getPayload();
                        if (play.getAudioItems().isEmpty()) {
                            L.player.i("load more audio items is empty");
                            AudioItemListController.this.loadMoreEnd = true;
                        } else {
                            AudioItemListController.this.a(dialogId, play, playInfo, true, false, metadataLoadMoreCallback != null);
                        }
                        int i2 = i;
                        if (i2 >= 0) {
                            AudioItemListController.this.playByIndex(i2, false);
                        }
                    } else if (i >= 0) {
                        L.player.i("load more fail instruction is empty");
                        AudioItemListController.this.playByIndex(i, false);
                    }
                    AudioItemListController.this.musicIndexHelper.setLoadingMore(false);
                    AudioItemListController.this.musicIndexHelper.setLoadingMoreSuccess();
                    MetadataLoadMoreCallback metadataLoadMoreCallback2 = metadataLoadMoreCallback;
                    if (metadataLoadMoreCallback2 != null) {
                        metadataLoadMoreCallback2.onLoadMoreComplete(true);
                    }
                }

                @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
                public void onEventFail(Event event, boolean z) {
                    L.player.i("load more fail");
                    AudioItemListController.this.musicIndexHelper.setLoadingMore(false);
                    MetadataLoadMoreCallback metadataLoadMoreCallback2 = metadataLoadMoreCallback;
                    if (metadataLoadMoreCallback2 != null) {
                        metadataLoadMoreCallback2.onLoadMoreComplete(false);
                        return;
                    }
                    int i2 = i;
                    if (i2 >= 0) {
                        AudioItemListController.this.playByIndex(i2, false);
                    }
                }
            });
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canLoadMore() {
        return this.m && !TextUtils.isEmpty(this.h) && !TextUtils.isEmpty(this.i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean canPreLoadMore() {
        Music.AudioInfo audioInfo;
        return !this.preLoadMoreEnd && canLoadMore() && getAudioType() == Common.AudioType.BOOKS && ContainerUtil.hasData(this.d) && (audioInfo = this.d.get(0)) != null && audioInfo.episodesNum > 0;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doLoadMore(final MetadataLoadMoreCallback metadataLoadMoreCallback) {
        if (!canLoadMore()) {
            L.player.e("loadMore return!!! mLoadMoreToken=%s,mOriginId=%s", this.h, this.i);
            if (metadataLoadMoreCallback != null) {
                metadataLoadMoreCallback.onLoadMoreComplete(false);
                return;
            }
            return;
        }
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreStart();
        }
        this.musicIndexHelper.setLoadingMore(true);
        int playlistCount = getPlaylistCount() > 0 ? getPlaylistCount() : 0;
        Nlp.LoadMore loadMore = new Nlp.LoadMore();
        loadMore.setOriginId(this.i);
        loadMore.setOffset(playlistCount);
        loadMore.setDelta(this.j);
        loadMore.setToken(this.h);
        Event buildEvent = APIUtils.buildEvent(loadMore);
        buildEvent.setContext(SpeechContextHelper.getContexts(false));
        SpeechEventUtil.getInstance().eventRequest(buildEvent, new SpeechEventUtil.EventListener() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.AudioItemListController.2
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventResult(List<Instruction> list, Event event) {
                Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.AudioPlayer.Play);
                Instruction intention2 = InstructionUtil.getIntention(list, AIApiConstants.Template.PlayInfo);
                if (intention == null || intention2 == null) {
                    L.player.i("load more fail instruction is empty");
                } else {
                    String dialogId = InstructionUtil.getDialogId(intention);
                    AudioPlayer.Play play = (AudioPlayer.Play) intention.getPayload();
                    Template.PlayInfo playInfo = (Template.PlayInfo) intention2.getPayload();
                    if (play.getAudioItems().isEmpty()) {
                        L.player.i("load more audio items is empty");
                        AudioItemListController.this.loadMoreEnd = true;
                        return;
                    }
                    AudioItemListController.this.a(dialogId, play, playInfo, true, false, true);
                }
                AudioItemListController.this.musicIndexHelper.setLoadingMore(false);
                AudioItemListController.this.musicIndexHelper.setLoadingMoreSuccess();
                MetadataLoadMoreCallback metadataLoadMoreCallback2 = metadataLoadMoreCallback;
                if (metadataLoadMoreCallback2 != null) {
                    metadataLoadMoreCallback2.onLoadMoreComplete(true);
                }
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventFail(Event event, boolean z) {
                L.player.i("load more fail");
                AudioItemListController.this.musicIndexHelper.setLoadingMore(false);
                MetadataLoadMoreCallback metadataLoadMoreCallback2 = metadataLoadMoreCallback;
                if (metadataLoadMoreCallback2 != null) {
                    metadataLoadMoreCallback2.onLoadMoreComplete(false);
                }
            }
        });
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doPreLoadMore(final MetadataPreLoadMoreCallback metadataPreLoadMoreCallback) {
        if (!canPreLoadMore()) {
            L.player.e("loadMore return!!! mLoadMoreToken=%s,mOriginId=%s", this.h, this.i);
            if (metadataPreLoadMoreCallback != null) {
                metadataPreLoadMoreCallback.onPreLoadMoreComplete(false);
                return;
            }
            return;
        }
        if (metadataPreLoadMoreCallback != null) {
            metadataPreLoadMoreCallback.onPreLoadMoreStart();
        }
        this.musicIndexHelper.setLoadingMore(true);
        int i = this.s - this.j;
        Nlp.LoadMore loadMore = new Nlp.LoadMore();
        loadMore.setOriginId(this.i);
        loadMore.setOffset(i);
        loadMore.setDelta(this.j);
        loadMore.setToken(this.h);
        Event buildEvent = APIUtils.buildEvent(loadMore);
        buildEvent.setContext(SpeechContextHelper.getContexts(false));
        SpeechEventUtil.getInstance().eventRequest(buildEvent, new SpeechEventUtil.EventListener() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.AudioItemListController.3
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventResult(List<Instruction> list, Event event) {
                Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.AudioPlayer.Play);
                Instruction intention2 = InstructionUtil.getIntention(list, AIApiConstants.Template.PlayInfo);
                if (intention == null || intention2 == null) {
                    L.player.i("load more fail instruction is empty");
                } else {
                    String dialogId = InstructionUtil.getDialogId(intention);
                    AudioPlayer.Play play = (AudioPlayer.Play) intention.getPayload();
                    Template.PlayInfo playInfo = (Template.PlayInfo) intention2.getPayload();
                    if (play.getAudioItems().isEmpty()) {
                        L.player.i("load more audio items is empty");
                        AudioItemListController.this.preLoadMoreEnd = true;
                        return;
                    }
                    AudioItemListController.this.s -= AudioItemListController.this.j;
                    AudioItemListController.this.a(dialogId, play, playInfo, true, true, true);
                }
                AudioItemListController.this.musicIndexHelper.setLoadingMore(false);
                AudioItemListController.this.musicIndexHelper.setLoadingMoreSuccess();
                MetadataPreLoadMoreCallback metadataPreLoadMoreCallback2 = metadataPreLoadMoreCallback;
                if (metadataPreLoadMoreCallback2 != null) {
                    metadataPreLoadMoreCallback2.onPreLoadMoreComplete(true);
                }
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventFail(Event event, boolean z) {
                L.player.i("load more fail");
                AudioItemListController.this.musicIndexHelper.setLoadingMore(false);
                MetadataPreLoadMoreCallback metadataPreLoadMoreCallback2 = metadataPreLoadMoreCallback;
                if (metadataPreLoadMoreCallback2 != null) {
                    metadataPreLoadMoreCallback2.onPreLoadMoreComplete(false);
                }
            }
        });
    }
}
