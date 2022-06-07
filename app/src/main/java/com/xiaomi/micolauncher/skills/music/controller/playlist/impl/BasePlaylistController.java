package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.text.TextUtils;
import androidx.annotation.StringRes;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.music.MusicProfile;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.controller.PlayerControlCallback;
import com.xiaomi.micolauncher.skills.music.controller.RetCode;
import com.xiaomi.micolauncher.skills.music.controller.playlist.MusicIndexHelper;
import com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import com.xiaomi.miplay.mylibrary.DataModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class BasePlaylistController implements PlayerControlCallback, PlaylistController {
    protected static final int maxRetryLoadMore = 10;
    protected static final int preLoadMore = 10;
    private final MediaSession a;
    private final AudioPlayer b;
    protected volatile boolean blackFilterAll;
    protected List<String> blackListKey;
    protected Disposable cpResourceDisposable;
    private boolean d;
    protected volatile String dialogId;
    private Disposable e;
    private boolean f;
    private PlaybackTrackHelper g;
    protected Disposable loadMoreDisposable;
    protected final Context mContext;
    protected int mLastCpResouceIllegalIndex;
    protected int mPlayingErrorCount;
    protected volatile String originDialogId;
    protected int totalErrorCount;
    protected List<Remote.Response.TrackData> trackDataList = new ArrayList();
    protected volatile boolean loadMoreEnd = false;
    protected volatile boolean preLoadMoreEnd = false;
    protected Runnable cmdPlayCountDown = new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$lv9_R1om4GWk0OAQDXN_DDkmHf4
        @Override // java.lang.Runnable
        public final void run() {
            BasePlaylistController.this.t();
        }
    };
    private final PlaybackState.Builder c = new PlaybackState.Builder().setActions(516);
    protected MusicIndexHelper musicIndexHelper = new MusicIndexHelper();

    public boolean canLoadMore() {
        return false;
    }

    public boolean canPreLoadMore() {
        return false;
    }

    public boolean canRegEventBus() {
        return true;
    }

    void g() {
    }

    public int getMediaType() {
        return 3;
    }

    public abstract Remote.Response.PlayingData getPlayingData();

    public void loadMore(int i) {
    }

    protected boolean onListPlayFinish() {
        return false;
    }

    void onPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
    }

    public void onResumeBreakpoint() {
    }

    public /* synthetic */ void t() {
        pause(true);
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayFinish());
    }

    public BasePlaylistController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        this.mContext = context;
        this.a = mediaSession;
        this.b = audioPlayer;
        this.a.setPlaybackState(this.c.build());
        this.b.setListener(new AudioPlayerListener() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController.1
            @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener
            public void onComplete() {
                L.player.d("BasePlaylistController#AudioPlayerListener onComplete");
                BasePlaylistController.this.p();
                if (BasePlaylistController.this.musicIndexHelper.checkPlayCompletePause()) {
                    BasePlaylistController.this.setCompletePause(true);
                    BasePlaylistController.this.pause(false);
                    return;
                }
                BasePlaylistController.this.k();
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener
            public void onError(int i, Exception exc) {
                L.player.w("BasePlaylistController#AudioPlayerListener onError mPlayingErrorCount = %d", Integer.valueOf(BasePlaylistController.this.mPlayingErrorCount));
                BasePlaylistController basePlaylistController = BasePlaylistController.this;
                basePlaylistController.e(basePlaylistController.getCurrentIndex());
                BasePlaylistController.this.f(i);
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener
            public void onPrepared() {
                L.player.d("BasePlaylistController#AudioPlayerListener onPrepared");
                BasePlaylistController.this.onResumeBreakpoint();
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener
            public void onPrepareTimeout() {
                L.player.d("BasePlaylistController#AudioPlayerListener onPrepareTimeout");
                BasePlaylistController basePlaylistController = BasePlaylistController.this;
                basePlaylistController.e(basePlaylistController.getCurrentIndex());
                BasePlaylistController.this.f(1);
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener
            public void onPaused() {
                L.player.d("BasePlaylistController#AudioPlayerListener onPaused");
                BasePlaylistController.this.stopPublishPlayState();
                BasePlaylistController.this.d(2);
                ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO);
                if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
                    ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener
            public void onStarted() {
                L.player.d("BasePlaylistController#AudioPlayerListener onStarted");
                BasePlaylistController.this.d(1);
                BasePlaylistController.this.schedulePublishPlayState();
                BasePlaylistController.this.n();
                ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO);
                if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
                    ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener
            public void onStopped() {
                L.player.d("BasePlaylistController#AudioPlayerListener onStopped");
                BasePlaylistController.this.stopPublishPlayState();
                BasePlaylistController.this.d(0);
                BasePlaylistController.this.o();
                ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO);
                if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
                    ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
                }
            }
        });
        this.b.setPlayerControlCallback(this);
        L.player.i("BasePlaylistController %s onCreate", getClass().getSimpleName());
        MusicProfile.loadCpConfig();
        registerToEventBusIfNot();
        clearAudioBookPlayList();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.PlayerControlCallback
    public RetCode onControlCommand(PlaybackControllerInfo playbackControllerInfo) {
        L.player.d("PlaylistController %s", playbackControllerInfo);
        if (playbackControllerInfo == null) {
            return RetCode.UNKNOWN;
        }
        switch (playbackControllerInfo.playbackControl) {
            case PLAY:
            case CONTINUE_PLAYING:
                play();
                PlayerApi.showSystemBarText();
                return RetCode.SUCCESS;
            case STOP:
            case PAUSE:
                pause(false);
                PlayerApi.clearSystemBarText();
                return RetCode.SUCCESS;
            case PREV:
                prev();
                return RetCode.SUCCESS;
            case NEXT:
                this.musicIndexHelper.setLoadingMore(false);
                this.dialogId = playbackControllerInfo.dialogId;
                next();
                return RetCode.SUCCESS;
            case SEEK:
                if (playbackControllerInfo.referenceDef == PlaybackControllerInfo.ReferenceDef.CURRENT) {
                    seekTo(h() + playbackControllerInfo.deltaInMs);
                }
                return RetCode.SUCCESS;
            case SET_PROPERTY:
                if (playbackControllerInfo.setPropertyName == PlaybackControllerInfo.SetProperty.LOOP_MODE && playbackControllerInfo.loopMode != null) {
                    setLoopType(playbackControllerInfo.loopMode.ordinal());
                }
                return RetCode.SUCCESS;
            case START_OVER:
                if (playbackControllerInfo.deltaInMs >= 0) {
                    seekTo(playbackControllerInfo.deltaInMs);
                }
                return RetCode.SUCCESS;
            case STOP_AFTER:
                if (playbackControllerInfo.timeoutInMs > 0) {
                    pausePlayerByFinish(-2);
                    a(playbackControllerInfo.timeoutInMs);
                } else if (playbackControllerInfo.countOfEnd > 0) {
                    i();
                    pausePlayerByFinish(playbackControllerInfo.countOfEnd);
                }
                return RetCode.SUCCESS;
            case CANCEL_STOP_AFTER:
                i();
                pausePlayerByFinish(0);
                return RetCode.SUCCESS;
            case DELETE_PLAYING_HISTORY:
            default:
                return RetCode.UNSUPPORTED;
        }
    }

    protected void registerToEventBusIfNot() {
        if (!EventBusRegistry.getEventBus().isRegistered(this) && canRegEventBus()) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    protected void unregisterToEventBusIfNeed() {
        if (EventBusRegistry.getEventBus().isRegistered(this) && canRegEventBus()) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public MediaSession getMediaSession() {
        return this.a;
    }

    public int getCurrentIndex() {
        return this.musicIndexHelper.getPlayingIndexInOriginalList();
    }

    public void setCurrentIndex(int i) {
        this.musicIndexHelper.setPlayIndex(i);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean isReleased() {
        return this.f;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void prev() {
        Remote.Response.TrackData trackData;
        if (!isReleased()) {
            L.player.i("BasePlaylistController %s prev", getClass().getSimpleName());
            setCompletePause(false);
            if (getPlaylistCount() > 0) {
                int prevIndex = this.musicIndexHelper.getPrevIndex(getPlaylistCount());
                EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayIndexChange(prevIndex));
                if (ContainerUtil.isOutOfBound(prevIndex, this.trackDataList) || (trackData = this.trackDataList.get(prevIndex)) == null || !trackData.stationIsLocked()) {
                    doPlay(prevIndex);
                    schedulePublishPlayState();
                    q();
                    return;
                }
                setCompletePause(true);
                pause(false);
                return;
            }
            L.player.i("BasePlaylistController prev music list empty");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void next() {
        Remote.Response.TrackData trackData;
        int playlistCount = getPlaylistCount();
        if (!isReleased() && playlistCount >= 1 && !this.musicIndexHelper.isLoadingMore()) {
            L.player.i("BasePlaylistController %s next currentIndex+1=%d, playListCount=%d", getClass().getSimpleName(), Integer.valueOf(getCurrentIndex() + 1), Integer.valueOf(playlistCount));
            setCompletePause(false);
            if (this.musicIndexHelper.isFinishOfPlayList()) {
                onListPlayFinish();
                return;
            }
            int shouldLoadMore = this.musicIndexHelper.shouldLoadMore(playlistCount);
            L.player.d("BasePlaylistController %s next nextPlayIndex=%d", getClass().getSimpleName(), Integer.valueOf(shouldLoadMore));
            if (shouldLoadMore != -1) {
                g(shouldLoadMore);
                return;
            }
            int nextIndex = this.musicIndexHelper.getNextIndex(getPlaylistCount());
            EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayIndexChange(nextIndex));
            if (ContainerUtil.isOutOfBound(nextIndex, this.trackDataList) || (trackData = this.trackDataList.get(nextIndex)) == null || !trackData.stationIsLocked()) {
                doPlay(nextIndex);
                schedulePublishPlayState();
                return;
            }
            setCompletePause(true);
            pause(false);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void play() {
        if (!isReleased()) {
            boolean isPaused = isPaused();
            L.player.i("BasePlaylistController %s play isPaused=%b", getClass().getSimpleName(), Boolean.valueOf(isPaused));
            if (isPaused && !isCompletePause()) {
                this.b.resume();
                schedulePublishPlayState();
                sendMetadata();
            } else if (!this.b.isPlaying()) {
                this.musicIndexHelper.setLoadingMore(false);
                next();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void pause(Boolean bool) {
        if (!isReleased()) {
            L.player.i("BasePlaylistController %s pause", getClass().getSimpleName());
            this.b.pause();
            stopPublishPlayState();
            d(2);
            sendMetadata();
            o();
            if (PlayerApi.pauseByManually) {
                PlayerApi.pauseByManually = false;
                ThreadUtil.getWorkHandler().removeCallbacks(this.cmdPlayCountDown);
            }
            ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO);
            if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
                ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public boolean isPaused() {
        return this.b.isPaused();
    }

    protected boolean isCompletePause() {
        return this.d;
    }

    protected void setCompletePause(boolean z) {
        this.d = z;
    }

    void a() {
        if (isShuffleLoop()) {
            this.musicIndexHelper.setLoadingMore(false);
            next();
            return;
        }
        playByIndex(0, true);
    }

    public void k() {
        if (this.musicIndexHelper.isSingleLoop()) {
            playByIndex(getCurrentIndex(), true);
            return;
        }
        this.musicIndexHelper.setLoadingMore(false);
        next();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void playByIndex(int i, boolean z) {
        if (!isReleased()) {
            int playlistCount = getPlaylistCount();
            L.player.i("BasePlaylistController %s playByIndex=%d, playListCount=%d", getClass().getSimpleName(), Integer.valueOf(i), Integer.valueOf(playlistCount));
            if (z && this.musicIndexHelper.shouldLoadMoreInShuffleLoop(playlistCount)) {
                L.player.d("BasePlaylistController %s playByIndex (randomLoop) wrapLoadMore index=%d", getClass().getSimpleName(), Integer.valueOf(i));
                g(i);
            } else if (!z || playlistCount < 1 || i < playlistCount - 2) {
                if (i >= playlistCount) {
                    if (onListPlayFinish()) {
                        L.player.i("onListPlayFinish");
                        return;
                    }
                    i = 0;
                }
                setCurrentIndex(i);
                doPlay(i);
                schedulePublishPlayState();
            } else {
                L.player.d("BasePlaylistController %s playByIndex wrapLoadMore index=%d", getClass().getSimpleName(), Integer.valueOf(i));
                g(i);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public AudioPlayer getPlayer() {
        return this.b;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void setVolume(float f) {
        AudioPlayer audioPlayer;
        if (!isReleased() && (audioPlayer = this.b) != null) {
            audioPlayer.setVolume(f);
        }
    }

    public void release() {
        if (!isReleased()) {
            stopPublishPlayState();
            L.player.i("BasePlaylistController %s release", getClass().getSimpleName());
            this.f = true;
            this.loadMoreEnd = false;
            this.b.reset();
            this.b.setListener(null);
            this.b.setPlayerControlCallback(null);
            a(this.loadMoreDisposable);
            a(this.cpResourceDisposable);
            unregisterToEventBusIfNeed();
            this.musicIndexHelper.reset();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void updatePlaybackStatus() {
        c(this.b.isPlaying() ? 1 : 2);
    }

    private void c(int i) {
        LocalPlayerManager.getInstance().setPlayerStatus(i);
        if (ThreadUtil.isMainThread()) {
            LocalPlayerManager.getInstance().setPlayerPosition(this.b.getDuration(), this.b.getCurrentPosition());
            return;
        }
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController.2
            @Override // java.lang.Runnable
            public void run() {
                if (BasePlaylistController.this.b != null) {
                    long duration = BasePlaylistController.this.b.getDuration();
                    LocalPlayerManager.getInstance().setPlayerPosition(duration, BasePlaylistController.this.b.getCurrentPosition());
                }
            }
        });
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public MusicIndexHelper getMusicIndexHelper() {
        return this.musicIndexHelper;
    }

    public void d(int i) {
        c(i);
        LocalPlayerManager.getInstance().notifyPlaybackStateChanged(null);
    }

    protected void schedulePublishPlayState() {
        if (!isReleased() && this.e == null) {
            L.player.i("%s schedule to publish play state", getClass().getSimpleName());
            this.e = Observable.interval(1L, TimeUnit.SECONDS, MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$vBbQPnvt5nqmf9ceyv0t-isJTRM
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    boolean b;
                    b = BasePlaylistController.this.b((Long) obj);
                    return b;
                }
            }).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$5KuPOehaDCFzKPDNdg38bfGb5ng
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BasePlaylistController.this.a((Long) obj);
                }
            }, $$Lambda$BasePlaylistController$wEbm3wUSJHpaJH_sQ4VyIlOr_Cc.INSTANCE);
        }
    }

    public /* synthetic */ boolean b(Long l) throws Exception {
        return isReleased();
    }

    public /* synthetic */ void a(Long l) throws Exception {
        if (this.b.isPlaying()) {
            d(1);
        } else {
            d(2);
        }
    }

    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.player.i("schedulePublishPlayState sendPlaybackState error %s ", th.getMessage());
    }

    protected void stopPublishPlayState() {
        Disposable disposable = this.e;
        if (disposable != null) {
            disposable.dispose();
            this.e = null;
            L.player.i("%s stop to publish play state", getClass().getSimpleName());
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    @SuppressLint({"CheckResult"})
    public void sendMetadata() {
        Remote.Response.PlayingData playingData = getPlayingData();
        if (playingData != null) {
            L.player.i("BasePlaylistController %s sendMetadata", getClass().getSimpleName());
            a(playingData);
        }
    }

    private void a(Remote.Response.PlayingData playingData) {
        if (playingData != null) {
            playingData.screenExtend.mediaType = getMediaType();
            playingData.screenExtend.loopMode = this.musicIndexHelper.getLoopType();
            playingData.screenExtend.index = getCurrentIndex();
            playingData.position = this.b.getCurrentPosition();
            playingData.duration = this.b.getDuration();
            LocalPlayerManager.getInstance().setMediaType(getMediaType());
            boolean z = LocalPlayerManager.getInstance().getLoopType() != getLoopType();
            LocalPlayerManager.getInstance().setLoopType(getLoopType());
            LocalPlayerManager.getInstance().setMicoPlayingData(getDialogId(null), playingData, z);
        }
    }

    void a(List<Remote.Response.TrackData> list) {
        a(list, false);
    }

    void a(List<Remote.Response.TrackData> list, boolean z) {
        this.trackDataList = list;
        int currentIndex = getCurrentIndex();
        this.musicIndexHelper.setLoadingMore(false);
        L.player.d("BasePlaylistController %s updatePlayList playListSize=%d, currentIndex=%d", getClass().getSimpleName(), Integer.valueOf(list.size()), Integer.valueOf(currentIndex));
        this.musicIndexHelper.setListCount(list.size());
        LocalPlayerManager.getInstance().setPlayingList(currentIndex, list, z);
    }

    public void setLoopType(int i) {
        setLoopType(i, false);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void setLoopType(int i, boolean z) {
        L.player.d("%s set loop type of %d", getClass().getSimpleName(), Integer.valueOf(i));
        if (this.musicIndexHelper.setLoopType(i, z)) {
            sendMetadata();
        }
    }

    public int getLoopType() {
        return this.musicIndexHelper.getLoopType();
    }

    public boolean isShuffleLoop() {
        return this.musicIndexHelper.isShuffleLoop();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void seekTo(long j) {
        this.b.seekTo(j);
    }

    public void resetErrorCount() {
        this.mPlayingErrorCount = 0;
        a(this.loadMoreDisposable);
        a(this.cpResourceDisposable);
    }

    void a(Throwable th, int i) {
        L.player.e("BasePlaylistController %s getCpResource failed %s", getClass().getSimpleName(), th);
        e(i);
    }

    void b() {
        MusicProfile.checkQQMusicAuthStatus(new MusicProfile.QQMusicAuthStatusListener() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController.3
            @Override // com.xiaomi.micolauncher.skills.music.MusicProfile.QQMusicAuthStatusListener
            public void onNonQQMusicSource(String str) {
                BasePlaylistController.this.e();
            }

            @Override // com.xiaomi.micolauncher.skills.music.MusicProfile.QQMusicAuthStatusListener
            public void onLoadQQMusicAuthStatus(MIBrain.CPBindStatus cPBindStatus) {
                if (cPBindStatus == null || !cPBindStatus.isInvalid()) {
                    BasePlaylistController.this.e();
                } else {
                    BasePlaylistController.this.a(cPBindStatus.isNotBinded());
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.MusicProfile.QQMusicAuthStatusListener
            public void onFail(Throwable th) {
                BasePlaylistController.this.e();
            }
        });
    }

    @SuppressLint({"CheckResult"})
    void c() {
        if (!this.blackFilterAll) {
            MusicProfile.checkQQMusicAuthStatus().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$oE32-L3zU-p9GJVxK1aJ8Irazec
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BasePlaylistController.this.a((MIBrain.CPBindStatus) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$LvGk7q2M_EWKw62pnCxn3J_XlJw
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BasePlaylistController.this.a((Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void a(MIBrain.CPBindStatus cPBindStatus) throws Exception {
        if (cPBindStatus == null || !cPBindStatus.isInvalid()) {
            b(R.string.music_list_empty_toast);
        } else {
            a(cPBindStatus.isNotBinded());
        }
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        if (th instanceof MusicProfile.NotQQSourceException) {
            e();
        }
    }

    /* renamed from: d */
    public void s() {
        b(R.string.music_list_empty_toast_of_black_list);
    }

    protected int getNextIndex() {
        return this.musicIndexHelper.getNextIndex(getPlaylistCount());
    }

    public void onInitDataLoadSuccess(int i, int i2) {
        this.musicIndexHelper.reset();
        setLoopType(i2);
        setCurrentIndex(i);
    }

    void a(boolean z, int i) {
        a(z, i, null);
    }

    void a(boolean z, int i, Throwable th) {
        L.player.w("BasePlaylistController %s onLoadMoreError hasListData=%s", getClass().getSimpleName(), Boolean.valueOf(z));
        this.musicIndexHelper.setLoadingMore(false);
        if (z) {
            playByIndex(i, false);
        } else if (th != null) {
            L.player.e("BasePlaylistController %s onLoadMoreError throwable %s", getClass().getSimpleName(), th);
            e();
        } else {
            b(R.string.music_list_empty_toast);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void e(int r8) {
        /*
            r7 = this;
            r0 = 1
            if (r8 == 0) goto L_0x0016
            int r1 = r7.mLastCpResouceIllegalIndex
            int r2 = r1 + 1
            if (r8 == r2) goto L_0x0016
            if (r8 == r1) goto L_0x0016
            int r2 = r7.getPlaylistCount()
            int r2 = r2 - r0
            if (r1 != r2) goto L_0x0013
            goto L_0x0016
        L_0x0013:
            r7.mPlayingErrorCount = r0
            goto L_0x001b
        L_0x0016:
            int r1 = r7.mPlayingErrorCount
            int r1 = r1 + r0
            r7.mPlayingErrorCount = r1
        L_0x001b:
            int r1 = r7.totalErrorCount
            int r1 = r1 + r0
            r7.totalErrorCount = r1
            com.elvishew.xlog.Logger r1 = com.xiaomi.micolauncher.common.L.player
            java.lang.String r2 = "BasePlaylistController %s totalErrorCount=%d"
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]
            java.lang.Class r5 = r7.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r6 = 0
            r4[r6] = r5
            int r5 = r7.totalErrorCount
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r0] = r5
            r1.w(r2, r4)
            int r1 = r7.totalErrorCount
            r2 = 99
            if (r1 <= r2) goto L_0x0047
            r7.e()
            return
        L_0x0047:
            com.elvishew.xlog.Logger r1 = com.xiaomi.micolauncher.common.L.player
            java.lang.String r2 = "BasePlaylistController %s mPlayingErrorCount=%d"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Class r4 = r7.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3[r6] = r4
            int r4 = r7.mPlayingErrorCount
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r0] = r4
            r1.w(r2, r3)
            int r0 = r7.mPlayingErrorCount
            r1 = 10
            if (r0 >= r1) goto L_0x006c
            r7.k()
            goto L_0x006f
        L_0x006c:
            r7.b()
        L_0x006f:
            r7.mLastCpResouceIllegalIndex = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController.e(int):void");
    }

    void a(int i) {
        L.player.w("BasePlaylistController %s onResourceIllegal", getClass().getSimpleName());
        e(i);
    }

    private void l() {
        L.player.e("BasePlaylistController %s onPlayError", getClass().getSimpleName());
        EventBusRegistry.getEventBus().post(new PlayerEvent.ClosePlayerListActivityV2Event());
        stopPublishPlayState();
        pause(true);
    }

    void e() {
        b(-1);
    }

    void b(@StringRes int i) {
        L.player.w("BasePlaylistController %s onLoadMusicListError", getClass().getSimpleName());
        l();
        MusicHelper.postPlayError(this.mContext, i);
        this.totalErrorCount = 0;
    }

    public void a(boolean z) {
        L.player.d("BasePlaylistController %s onMusicAuthInvalid", getClass().getSimpleName());
        l();
        MusicHelper.postMusicAuthError(this.mContext, z);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void pausePlayerByFinish(int i) {
        this.musicIndexHelper.pausePlayerByFinish(i);
    }

    void a(String str, String str2) {
        if (!ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO)) {
            L.childmode.e("time manage effect can not playResource");
            return;
        }
        if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
            ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
        }
        this.b.play(str);
        sendMetadata();
        d(1);
        m();
    }

    void f() {
        L.player.w("BasePlaylistController %s onPlayFinish", getClass().getSimpleName());
        l();
        MusicHelper.postPlayListFinish();
    }

    List<Music.Song> b(List<Music.Song> list) {
        ArrayList arrayList = new ArrayList();
        for (Music.Song song : list) {
            if (song.isLegal()) {
                arrayList.add(song);
            }
        }
        L.player.d("BasePlaylistController %s filterIllegalMusicResource songsSize=%d legalSongsSize=%d", getClass().getSimpleName(), Integer.valueOf(list.size()), Integer.valueOf(arrayList.size()));
        return arrayList;
    }

    private void m() {
        L.player.i("BasePlaylistController %s recordStartLoadTime", getClass().getSimpleName());
        AudioPlayer.AudioItemV1 audioItem = getAudioItem();
        this.g = new PlaybackTrackHelper(getOriginDialogId(audioItem.getItemId().getAlbumId().isPresent() ? audioItem.getItemId().getAlbumId().get() : ""), getDialogId(audioItem.getItemId().getAudioId()), audioItem, getAudioType().name());
        this.g.setOffset(getCurrentIndex());
        this.g.monitorLog("prepare");
    }

    public void f(int i) {
        PlaybackTrackHelper playbackTrackHelper = this.g;
        if (playbackTrackHelper != null) {
            playbackTrackHelper.monitorLog("error");
        }
    }

    public void n() {
        PlaybackTrackHelper playbackTrackHelper = this.g;
        if (playbackTrackHelper != null) {
            playbackTrackHelper.setStartTs();
            this.g.setDuration(this.b.getDuration());
            this.g.monitorLog("play");
        }
    }

    public void o() {
        c("click", "pause");
        g();
    }

    public void p() {
        c("click", "auto_switch");
        g();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void reportVideoManSwitch() {
        c("click", "man_switch");
        g();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void reportVideoListSwitch() {
        c("click", PlaybackTrackHelper.SWITCH_TYPE_LIST_SWITCH);
        g();
    }

    private void c(String str, String str2) {
        PlaybackTrackHelper playbackTrackHelper = this.g;
        if (playbackTrackHelper != null) {
            playbackTrackHelper.setEndTs();
            this.g.setPositionInMs(h());
            this.g.postPlayEvent(str, str2);
        }
    }

    void a(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void g(int i) {
        L.player.i("BasePlaylistController %s loadMore index=%d", getClass().getSimpleName(), Integer.valueOf(i));
        loadMore(i);
    }

    private void q() {
        if (canLoadMore() && this.musicIndexHelper.needLoadMore()) {
            doLoadMore(new MetadataLoadMoreCallback() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController.4
                @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
                public void onLoadMoreStart() {
                    BasePlaylistController.this.musicIndexHelper.setLoadingMore(true);
                }

                @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
                public void onLoadMoreComplete(boolean z) {
                    BasePlaylistController.this.musicIndexHelper.setLoadingMore(false);
                }

                @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
                public void onLoadMoreEnd() {
                    BasePlaylistController.this.loadMoreEnd = true;
                }
            });
        }
    }

    public void doLoadMore(MetadataLoadMoreCallback metadataLoadMoreCallback) {
        if (metadataLoadMoreCallback != null) {
            metadataLoadMoreCallback.onLoadMoreComplete(false);
        }
    }

    public void doPreLoadMore(MetadataPreLoadMoreCallback metadataPreLoadMoreCallback) {
        if (metadataPreLoadMoreCallback != null) {
            metadataPreLoadMoreCallback.onPreLoadMoreComplete(false);
        }
    }

    @SuppressLint({"CheckResult"})
    void a(final Music.Song song) {
        if (TextUtils.isEmpty(song.coverURL) && !TextUtils.isEmpty(song.audioID)) {
            AudioInfoCache.getAudioInfo(song.audioID).subscribeOn(MicoSchedulers.io()).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$xcyXfVgsrYdtgAXPxRPiWYRgfHA
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    boolean a;
                    a = BasePlaylistController.this.a((Music.AudioInfo) obj);
                    return a;
                }
            }).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$4pIZIKq_lYfB_YtuipVSYi0nzVI
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BasePlaylistController.this.a(song, (Music.AudioInfo) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$PXsHjKzewmKOXGsG9-daIzUhOUA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BasePlaylistController.this.a(song, (Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ boolean a(Music.AudioInfo audioInfo) throws Exception {
        return isReleased();
    }

    public /* synthetic */ void a(Music.Song song, Music.AudioInfo audioInfo) throws Exception {
        if (audioInfo != null) {
            song.coverURL = audioInfo.cover;
            sendMetadata();
        }
    }

    public /* synthetic */ void a(Music.Song song, Throwable th) throws Exception {
        song.coverURL = DataModel.CIRCULATEFAIL_NO_SUPPORT;
        sendMetadata();
    }

    public String getOriginDialogId(String str) {
        if (this.originDialogId == null) {
            this.originDialogId = buildDialogId();
            L.monitor.w("getOriginDialogId is null, build a new one! originDialogId=%s", this.originDialogId);
        }
        return this.originDialogId;
    }

    public String getDialogId(String str) {
        return this.dialogId;
    }

    protected String buildDialogId() {
        return String.format("%s_%s", PlaybackTrackHelper.ROM_TAG, UUID.randomUUID().toString());
    }

    protected AudioPlayer.AudioItemV1 getAudioItem() {
        Remote.Response.PlayingData playingData = LocalPlayerManager.getInstance().getPlayerStatus().play_song_detail;
        if (playingData == null) {
            return null;
        }
        AudioPlayer.AudioItemV1 audioItemV1 = new AudioPlayer.AudioItemV1();
        AudioPlayer.ItemId itemId = new AudioPlayer.ItemId();
        AudioPlayer.ContentProvider contentProvider = new AudioPlayer.ContentProvider();
        contentProvider.setId(playingData.cpID);
        contentProvider.setName(playingData.cpOrigin);
        contentProvider.setAlbumId(playingData.cpAlbumId);
        if (playingData.albumGlobalID != null) {
            itemId.setAlbumId(playingData.albumGlobalID);
        }
        itemId.setCp(contentProvider);
        itemId.setAudioId(playingData.audioID);
        audioItemV1.setItemId(itemId);
        if (playingData.screenExtend.log != null) {
            audioItemV1.setLog(playingData.screenExtend.log);
        }
        return audioItemV1;
    }

    protected Common.AudioType getAudioType() {
        return Common.AudioType.MUSIC;
    }

    int h() {
        com.xiaomi.micolauncher.skills.music.controller.AudioPlayer audioPlayer = this.b;
        if (audioPlayer != null) {
            return audioPlayer.getCurrentPosition();
        }
        return 0;
    }

    void i() {
        Remote.Request.PlayerShutdown playerShutdown = new Remote.Request.PlayerShutdown();
        playerShutdown.action = Remote.Request.PlayerShutdown.ACTION_CANCEL;
        PlayerApi.setPlayerShutdownTimer(playerShutdown);
    }

    private void a(long j) {
        Remote.Request.PlayerShutdown playerShutdown = new Remote.Request.PlayerShutdown();
        playerShutdown.action = Remote.Request.PlayerShutdown.ACTION_PAUSE_LATER;
        playerShutdown.interval = TimeUnit.MILLISECONDS.toSeconds(j);
        PlayerApi.setPlayerShutdownTimer(playerShutdown);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayPaySuccess(PlayerEvent.OnPlayPaySuccess onPlayPaySuccess) {
        onPaySuccess(onPlayPaySuccess);
    }

    void j() {
        if (getPlayer().isPlaying()) {
            getPlayer().pause();
        }
        this.musicIndexHelper.reset();
    }

    protected void clearAudioBookPlayList() {
        LocalPlayerManager.getInstance().setStations(null);
    }

    boolean b(String str, String str2) {
        if (ContainerUtil.isEmpty(this.blackListKey)) {
            return false;
        }
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return false;
        }
        for (int i = 0; i < this.blackListKey.size(); i++) {
            String str3 = this.blackListKey.get(i);
            if (str != null && str.contains(str3)) {
                return true;
            }
            if (str2 != null && str2.contains(str3)) {
                return true;
            }
        }
        return false;
    }

    List<Music.AudioInfo> c(List<Music.AudioInfo> list) {
        this.blackFilterAll = false;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Music.AudioInfo audioInfo = list.get(i);
            if (!b(audioInfo.name, audioInfo.artistName) && !audioInfo.isNoPlayable()) {
                arrayList.add(audioInfo);
            }
        }
        if (ContainerUtil.isEmpty(arrayList) && !ContainerUtil.isEmpty(list)) {
            this.blackFilterAll = true;
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$KN-immEC7ayH-ZRRMhAVMEPmICk
                @Override // java.lang.Runnable
                public final void run() {
                    BasePlaylistController.this.s();
                }
            });
        }
        return arrayList;
    }

    Music.Channel a(Music.Channel channel) {
        this.blackFilterAll = false;
        ArrayList arrayList = new ArrayList();
        List<Music.Song> list = channel.songList;
        for (int i = 0; i < list.size(); i++) {
            Music.Song song = list.get(i);
            if (!b(song.name, song.artist != null ? song.artist.name : "")) {
                arrayList.add(song);
            }
        }
        if (ContainerUtil.isEmpty(arrayList) && !ContainerUtil.isEmpty(list)) {
            this.blackFilterAll = true;
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$BasePlaylistController$NRlXY5s2sV1pSMiJdFZToyfpju4
                @Override // java.lang.Runnable
                public final void run() {
                    BasePlaylistController.this.r();
                }
            });
        }
        channel.songList = arrayList;
        return channel;
    }
}
