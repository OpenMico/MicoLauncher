package com.xiaomi.micolauncher.skills.video.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.ChildVideoPlayList;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.impl.video.LaunchPlayAppOperation;
import com.xiaomi.micolauncher.instruciton.impl.video.PlayListOperation;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.video.db.VideoRealmHelper;
import com.xiaomi.micolauncher.skills.common.VideoPlayerEvent;
import com.xiaomi.micolauncher.skills.video.controller.VideoSessionController;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoFastForwardEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPauseEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPlayEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPlayNextEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoRestartEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoRewindEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoSeekEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoStopEvent;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.player.VideoPlayerPresenter;
import com.xiaomi.micolauncher.skills.video.player.model.OnlineUri;
import com.xiaomi.micolauncher.skills.video.player.utils.MiTvResourceMapper;
import com.xiaomi.micolauncher.skills.video.player.utils.MvResourceMapper;
import com.xiaomi.micolauncher.skills.video.player.utils.VideoResourceMapper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class VideoPlayerPresenter {
    VideoItem a;
    private FakePlayer b;
    protected Context context;
    private boolean d;
    private boolean e;
    private volatile boolean g;
    private boolean h;
    private int i;
    private VideoTrackHelper j;
    protected WeakReference<IVideoPlayerView> videoPlayerViewReference;
    private CompositeDisposable f = new CompositeDisposable();
    private OnlineUri c = new OnlineUri();
    protected VideoMode videoMode = VideoModel.getInstance().getMode();

    public void handleActivityResult(int i, int i2, Intent intent) {
    }

    public VideoPlayerPresenter(Context context, IVideoPlayerView iVideoPlayerView) {
        this.context = context.getApplicationContext();
        this.videoPlayerViewReference = new WeakReference<>(iVideoPlayerView);
        L.video.d("VideoPlayerPresenter Video Mode=%s", this.videoMode.name());
        a(iVideoPlayerView);
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        initTrackHelper();
    }

    private void a(IVideoPlayerView iVideoPlayerView) {
        AudioSource audioSource;
        if (this.videoMode == VideoMode.RECOMMEND || this.videoMode == VideoMode.VIDEO_PLAYER_PLAY || this.videoMode == VideoMode.VIDEO_PLAYER_PLAY_LIST || this.videoMode == VideoMode.MV || this.videoMode == VideoMode.VIDEO_PLAYER_LAUNCH_PLAY_APP || this.videoMode == VideoMode.SHORT_VIDEO_RECOMMEND) {
            audioSource = AudioSource.AUDIO_SOURCE_SHORT_VIDEO;
        } else {
            audioSource = AudioSource.AUDIO_SOURCE_LONG_VIDEO;
        }
        this.b = new a(this, audioSource);
        this.b.start();
    }

    protected IVideoPlayerView getVideoPlayerView() {
        WeakReference<IVideoPlayerView> weakReference = this.videoPlayerViewReference;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public void f() {
        if (!i()) {
            playByIndex(VideoModel.getInstance().prevPlayIndex());
        }
    }

    public void playNext() {
        if (!i()) {
            playByIndex(VideoModel.getInstance().nextPlayIndex());
        }
    }

    public boolean hasNextResource() {
        int nextPlayIndex;
        return (VideoModel.getInstance().playListSize() == 1 || (nextPlayIndex = VideoModel.getInstance().nextPlayIndex()) == 0 || VideoModel.getInstance().getVideoItem(nextPlayIndex) == null) ? false : true;
    }

    @SuppressLint({"CheckResult"})
    public void playByIndex(int i) {
        L.video.d("playByIndex playIndex=%d", Integer.valueOf(i));
        if (VideoModel.getInstance().hasResource()) {
            VideoItem videoItem = VideoModel.getInstance().getVideoItem(i);
            if (videoItem != null) {
                VideoModel.getInstance().setPlayIndex(i);
                a(videoItem);
                a(i);
                return;
            }
            L.video.w("playByIndex next VideoItem is empty, so loadMore");
            a(true);
        } else if (this.videoMode == VideoMode.MITV_SERIAL) {
            addToDisposeBag(a(VideoModel.getInstance().getSerialId(), VideoModel.getInstance().getPage()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$woVt4Yp7s-4JX9LX07jIxaiXdJU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoPlayerPresenter.this.playByIndex(((Integer) obj).intValue());
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$oMSBviAU-5ArqKHbE3X2-Se3Jtg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoPlayerPresenter.this.e((Throwable) obj);
                }
            }));
        } else {
            e(new IllegalArgumentException("video model is " + this.videoMode + " expect MITV_SERIAL"));
        }
    }

    /* renamed from: a */
    public void e(Throwable th) {
        if (getVideoPlayerView() != null) {
            getVideoPlayerView().handlePlayError(0, -1004, th);
        }
    }

    private void a(VideoItem videoItem) {
        this.a = videoItem;
        this.j.setVideoItem(videoItem);
        L.video.d("videoItem=%s", videoItem.toString());
        videoItem.getSerialId();
        if (VideoModel.getInstance().getCurrentPlayingVideoItem() != null && TextUtils.equals(VideoModel.getInstance().getCurrentPlayingVideoItem().getMediaId(), this.a.getMediaId())) {
            this.a.setAudioId(VideoModel.getInstance().getCurrentPlayingVideoItem().getAudioId());
        }
        if (this.a.isSerial()) {
            this.a.setSerialId(videoItem.getSerialId());
            this.a.saveSerialId();
        }
        g();
        if (getVideoPlayerView() != null) {
            getVideoPlayerView().showLoadingView();
            getVideoPlayerView().updateTitle(this.a.getTitle());
        }
        VideoContentProvider videoContentProvider = videoItem.getVideoContentProvider();
        if (videoContentProvider != null && videoContentProvider.streamInfo != null) {
            a(videoContentProvider.streamInfo);
        } else if (videoItem.isSerial()) {
            j();
        } else if (TextUtils.isEmpty(this.c.uri)) {
            if (this.videoMode == VideoMode.MV) {
                k();
            } else if (this.videoMode == VideoMode.SHORT_VIDEO_RECOMMEND) {
                l();
            } else if (this.videoMode == VideoMode.MITV_SERIAL) {
                m();
            } else {
                j();
            }
        } else if (!TextUtils.isEmpty(videoItem.getVideoUrl())) {
            a(videoItem.getVideoUrl());
        } else {
            playNext();
        }
    }

    private void g() {
        if (this.a.isSerial() && this.a.getSerialId() > 0) {
            OnlineUri onlineUri = this.c;
            onlineUri.title = this.a.getTitle() + "（第" + this.a.getSerialId() + "集）";
        } else if (this.videoMode != VideoMode.MITV_SERIAL) {
            this.c.title = this.a.getTitle();
        } else if (!TextUtils.isEmpty(this.a.getTitle()) || this.a.getCi() <= 0) {
            this.c.title = this.a.getTitle();
        } else {
            this.c.title = this.context.getString(R.string.child_video_ci, Integer.valueOf(this.a.getCi()));
        }
        this.c.uri = this.a.getVideoUrl();
        this.c.source = this.a.getPlayerType();
    }

    public void a(String str) {
        L.video.d("play url=%s", str);
        if (!TextUtils.isEmpty(str)) {
            this.a.setVideoUrl(str);
            g();
            h();
            Logger logger = L.video;
            logger.d("play source:" + this.c.source + ", uri:" + this.c.uri + " title:" + this.c.title);
            if (getVideoPlayerView() != null) {
                getVideoPlayerView().updateUI(this.c);
            }
            this.j.monitorLog("prepare");
            requestStart();
            return;
        }
        L.video.d("play url is empty");
    }

    private void h() {
        if (this.videoMode == VideoMode.MITV_SERIAL) {
            addToDisposeBag(ChildVideoDataManager.getManager().loadVideoItemFromDb(this.a.getMediaId()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$fXeM6Y8GTFMG9QsHl39DYHZ4qs8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoPlayerPresenter.this.b((VideoItem) obj);
                }
            }));
            L.video.d("play video item %s   %d", this.a.getTitle(), Integer.valueOf(this.a.getCi()));
        }
    }

    public /* synthetic */ void b(final VideoItem videoItem) throws Exception {
        videoItem.setCi(this.a.getCi());
        videoItem.setpCode(VideoModel.getInstance().getMiTvType().getpCode());
        ThreadUtil.getWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$A8z2kP4rqgFRO-Gzna39KbuSQwo
            @Override // java.lang.Runnable
            public final void run() {
                VideoPlayerPresenter.c(VideoItem.this);
            }
        });
    }

    public static /* synthetic */ void c(VideoItem videoItem) {
        VideoRealmHelper.getInstance().insert(videoItem);
    }

    public void completionVideo() {
        boolean z;
        EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_COMPLETE));
        r();
        switch (VideoModel.getInstance().getMode()) {
            case MV:
            case VIDEO_PLAYER_PLAY:
                finish();
                z = true;
                break;
            case VIDEO_PLAYER_PLAY_LIST:
            case VIDEO_PLAYER_LAUNCH_PLAY_APP:
            case SERIAL:
            case RECOMMEND:
            case SHORT_VIDEO_RECOMMEND:
            case MITV_SERIAL:
                playNext();
            default:
                z = false;
                break;
        }
        d(z);
    }

    public void a() {
        L.video.d("VideoPlayerPresenter onViewResume autoPlayWhenViewResume=%b", Boolean.valueOf(this.h));
        if (this.h) {
            requestStart();
        } else if (getVideoPlayerView() != null) {
            getVideoPlayerView().showControllerView();
        }
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    public void b() {
        L.video.d("VideoPlayerPresenter onViewPause");
        if (getVideoPlayerView() != null) {
            this.h = getVideoPlayerView().isPlaying();
            if (!this.h) {
                this.h = !isPaused();
            }
        }
        pauseVideo();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    public void c() {
        this.f.dispose();
        this.e = true;
        EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_STOP));
        VideoMediaSession.getInstance().setStopped();
        fakePlayerStop();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
        t();
        WeakReference<IVideoPlayerView> weakReference = this.videoPlayerViewReference;
        if (weakReference != null) {
            weakReference.clear();
        }
        FakePlayer fakePlayer = this.b;
        if (fakePlayer != null) {
            fakePlayer.release();
            this.b = null;
        }
        this.context = null;
    }

    public void fakePlayerStop() {
        FakePlayer fakePlayer = this.b;
        if (fakePlayer != null) {
            fakePlayer.stop();
        }
    }

    public void setPaused(boolean z) {
        L.video.d("setPaused state=%b", Boolean.valueOf(z));
        this.d = z;
    }

    public boolean isPaused() {
        return this.d;
    }

    private boolean i() {
        return this.e;
    }

    protected void setFocusChangeResume(boolean z) {
        this.g = z;
        L.video.d("setFocusChangeResume=%b", Boolean.valueOf(z));
    }

    protected boolean isFocusChangeResume() {
        return this.g;
    }

    public void setAutoPlayWhenViewResume(boolean z) {
        this.h = z;
    }

    @SuppressLint({"CheckResult"})
    private void a(final StreamInfo streamInfo) {
        if (!streamInfo.authentication) {
            a(streamInfo.url);
        } else {
            ApiHelper.getAuthorizationValue().map(new Function() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$S3B9i3NUg5PrhA7Cvrxfw7topzo
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    String streamUrl;
                    streamUrl = StreamInfo.this.getStreamUrl((String) obj);
                    return streamUrl;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new $$Lambda$VideoPlayerPresenter$Q6FO1u7K10IPa8yFEJjUrz02TLM(this), new $$Lambda$d5KVShUUQF_LV1jOrLthtxT2FY(this));
        }
    }

    @SuppressLint({"CheckResult"})
    private void j() {
        addToDisposeBag(ApiHelper.getCpVideoResource(this.a.getCpType(), this.a.getMediaId(), this.a.getSerialId()).map(new VideoResourceMapper()).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$Asd85n0e4IXD6UDxNmW_57w1tsA
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean e;
                e = VideoPlayerPresenter.this.e((String) obj);
                return e;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new $$Lambda$VideoPlayerPresenter$Q6FO1u7K10IPa8yFEJjUrz02TLM(this), new $$Lambda$d5KVShUUQF_LV1jOrLthtxT2FY(this)));
    }

    public /* synthetic */ boolean e(String str) throws Exception {
        return i();
    }

    @SuppressLint({"CheckResult"})
    private void k() {
        addToDisposeBag(ApiHelper.getCpMVResource(this.a.getCpType(), this.a.getMediaId(), "mv").map(new MvResourceMapper()).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$2AcSXV9Ll006Jutsi_knATNf3D8
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean d;
                d = VideoPlayerPresenter.this.d((String) obj);
                return d;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new $$Lambda$VideoPlayerPresenter$Q6FO1u7K10IPa8yFEJjUrz02TLM(this), new $$Lambda$d5KVShUUQF_LV1jOrLthtxT2FY(this)));
    }

    public /* synthetic */ boolean d(String str) throws Exception {
        return i();
    }

    @SuppressLint({"CheckResult"})
    private void l() {
        addToDisposeBag(ApiHelper.getCpResource(this.a.getCpType(), this.a.getMediaId(), "mivideoshort").map(new VideoResourceMapper()).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$XFD9aopfDjd8gVFIN9x_Ihg7xwc
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean c;
                c = VideoPlayerPresenter.this.c((String) obj);
                return c;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new $$Lambda$VideoPlayerPresenter$Q6FO1u7K10IPa8yFEJjUrz02TLM(this), new $$Lambda$d5KVShUUQF_LV1jOrLthtxT2FY(this)));
    }

    public /* synthetic */ boolean c(String str) throws Exception {
        return i();
    }

    @SuppressLint({"CheckResult"})
    private void m() {
        addToDisposeBag(ApiHelper.getMiTvResource(this.a.getMediaId(), this.a.getCi(), VideoModel.getInstance().getMiTvType().getpCode()).map(new MiTvResourceMapper()).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$9Hf47JQ0Nay-qZLlnhpoUI-d_MU
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean b;
                b = VideoPlayerPresenter.this.b((String) obj);
                return b;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new $$Lambda$VideoPlayerPresenter$Q6FO1u7K10IPa8yFEJjUrz02TLM(this), new $$Lambda$d5KVShUUQF_LV1jOrLthtxT2FY(this)));
    }

    public /* synthetic */ boolean b(String str) throws Exception {
        return i();
    }

    public void onLoadResourceError(Throwable th) {
        L.video.e("getVideoResUrl error", th);
        this.i++;
        if (this.i > 10) {
            e(th);
        } else {
            playNext();
        }
    }

    private void a(int i) {
        if (VideoModel.getInstance().isLastResource(i)) {
            L.video.d("do loadMore if current=%s is LastResource", Integer.valueOf(i));
            a(false);
        }
    }

    private void a(boolean z) {
        L.video.d("loadMore VideoMode=%s", this.videoMode);
        if (this.videoMode == VideoMode.RECOMMEND) {
            n();
        } else if (this.videoMode == VideoMode.SHORT_VIDEO_RECOMMEND) {
            o();
        } else if (this.videoMode == VideoMode.MITV_SERIAL) {
            if (VideoModel.getInstance().getMiTvHasNext()) {
                b(z);
            }
        } else if (this.videoMode != VideoMode.VIDEO_PLAYER_PLAY_LIST && this.videoMode != VideoMode.VIDEO_PLAYER_LAUNCH_PLAY_APP) {
        } else {
            if (VideoModel.getInstance().isNeedLoadMore()) {
                c(z);
            } else if (z) {
                finish();
            }
        }
    }

    private void b(final boolean z) {
        int page = VideoModel.getInstance().getPage() + 1;
        VideoModel.getInstance().setPage(page);
        L.video.d("load miTv more video page %d  playIndex %d", Integer.valueOf(page), Integer.valueOf(VideoModel.getInstance().getPlayIndex()));
        addToDisposeBag(a(VideoModel.getInstance().getSerialId(), page).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$357ICq2hvQNaBTWeLQm17fMx6IQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoPlayerPresenter.this.a(z, (Integer) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$DFk-Bwwq1tPNDfrv9fL45MDvaII
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoPlayerPresenter.this.d((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void a(boolean z, Integer num) throws Exception {
        a(z, true, VideoModel.getInstance().getPlayIndex());
    }

    private void c(final boolean z) {
        String dialogId = VideoModel.getInstance().getDialogId();
        Nlp.LoadMore loadMore = new Nlp.LoadMore(dialogId);
        loadMore.setOriginId(dialogId);
        loadMore.setOffset(VideoModel.getInstance().playListSize());
        loadMore.setToken(VideoModel.getInstance().getLoadMoreToken());
        loadMore.setDelta(5);
        Event buildEvent = APIUtils.buildEvent(loadMore);
        buildEvent.setContext(SpeechContextHelper.getContexts(false));
        SpeechEventUtil.getInstance().eventRequest(buildEvent, new SpeechEventUtil.EventListener() { // from class: com.xiaomi.micolauncher.skills.video.player.VideoPlayerPresenter.1
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventResult(List<Instruction> list, Event event) {
                if (VideoPlayerPresenter.this.getVideoPlayerView() != null) {
                    Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.VideoPlayer.LaunchPlayApp);
                    if (intention == null) {
                        intention = InstructionUtil.getIntention(list, AIApiConstants.VideoPlayer.PlayList);
                    }
                    if (intention != null) {
                        VideoPlayerPresenter.this.a(intention, z);
                    }
                }
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventFail(Event event, boolean z2) {
                if (VideoPlayerPresenter.this.getVideoPlayerView() != null) {
                }
            }
        });
    }

    public void a(Instruction instruction, boolean z) {
        boolean z2;
        L.video.d("onLoadMoreFinish");
        String fullName = instruction.getFullName();
        this.j.setDialogId(InstructionUtil.getDialogId(instruction));
        if (AIApiConstants.VideoPlayer.LaunchPlayApp.equals(fullName)) {
            VideoPlayer.LaunchPlayApp launchPlayApp = (VideoPlayer.LaunchPlayApp) instruction.getPayload();
            List<VideoItem> parseVideos = LaunchPlayAppOperation.parseVideos(launchPlayApp.getItems(), null, this.j.originId);
            z2 = ContainerUtil.hasData(parseVideos);
            if (z2) {
                VideoModel.getInstance().addPlayList(parseVideos);
            }
            LaunchPlayAppOperation.loadLoadMoreInfo(launchPlayApp);
        } else if (AIApiConstants.VideoPlayer.PlayList.equals(fullName)) {
            VideoPlayer.PlayList playList = (VideoPlayer.PlayList) instruction.getPayload();
            List<VideoItem> parsePlayList = PlayListOperation.parsePlayList(playList.getItems(), null, this.j.originId);
            z2 = ContainerUtil.hasData(parsePlayList);
            if (z2) {
                VideoModel.getInstance().addPlayList(parsePlayList);
            }
            PlayListOperation.loadLoadMoreInfo(playList);
        } else {
            z2 = false;
        }
        if (z && z2) {
            playNext();
        }
    }

    @SuppressLint({"CheckResult"})
    private void n() {
        addToDisposeBag(ApiManager.displayService.getRecommendNewsList(Hardware.HARDWARE.getName(), true).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$VtYDDuY-DaCpZENs8n4nPC7tStU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoPlayerPresenter.this.b((List) obj);
            }
        }, $$Lambda$VideoPlayerPresenter$V9K6O062UNNrSHwpFLxM0K_jfmw.INSTANCE));
    }

    public /* synthetic */ void b(List list) throws Exception {
        if (this.videoMode != VideoMode.RECOMMEND) {
            L.video.i("videoMode %s is not RECOMMEND", this.videoMode);
            return;
        }
        L.video.i("on recommend news data changed");
        if (!ContainerUtil.isEmpty(list)) {
            ArrayList<VideoItem> arrayList = new ArrayList<>();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MainPage.News news = (MainPage.News) it.next();
                if ("video".equals(news.getType())) {
                    arrayList.add(new VideoItem(news));
                }
            }
            a(arrayList);
            RecommendDataManager.getManager().updateRecommendNewsList(list);
            L.launcher.i("refreshNewsList load news data success");
        }
    }

    public static /* synthetic */ void c(Throwable th) throws Exception {
        L.launcher.e("refreshNewsList : ", th);
    }

    @SuppressLint({"CheckResult"})
    private void o() {
        addToDisposeBag(ApiManager.displayService.recommendShortVideo().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$qkiRWg0D2qN9TUgkZL9LnYB4x70
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoPlayerPresenter.this.a((List) obj);
            }
        }, $$Lambda$VideoPlayerPresenter$Szo9IqJ2LFE870M8rQPopT7_bd4.INSTANCE));
    }

    public /* synthetic */ void a(List list) throws Exception {
        if (this.videoMode != VideoMode.SHORT_VIDEO_RECOMMEND) {
            L.video.i("videoMode %s is not SHORT_VIDEO_RECOMMEND", this.videoMode);
            return;
        }
        L.video.i("on recommend short video data changed");
        if (ContainerUtil.hasData(list)) {
            ArrayList<VideoItem> arrayList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(((Video.ShortVideo) list.get(i)).transform());
            }
            a(arrayList);
            L.launcher.d("RecommendDataManager load short video data success");
        }
        RecommendDataManager.getManager().updateShortVideoData(list);
    }

    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.launcher.e("RecommendDataManager loadShortVideoData : ", th);
    }

    private void a(boolean z, boolean z2, int i) {
        if (!z) {
            return;
        }
        if (!VideoModel.getInstance().hasResource()) {
            L.video.e("video list empty,video player logout");
            finish();
        } else if (z2) {
            playByIndex(i);
        } else {
            playByIndex(0);
        }
    }

    @SuppressLint({"CheckResult"})
    private Observable<Integer> a(final String str, final int i) {
        final String[] strArr = new String[1];
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$LsLl1apSN37rn3rmqPVuC_eFxU0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                VideoPlayerPresenter.a(str, i, strArr, observableEmitter);
            }
        });
    }

    public static /* synthetic */ void a(String str, final int i, final String[] strArr, final ObservableEmitter observableEmitter) throws Exception {
        ChildVideoDataManager.getManager().getMiTvPlayList(str, i, 100).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$on_MobS_QUKY-b2kqY6gU7Dxots
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = VideoPlayerPresenter.a(strArr, (ChildVideoPlayList) obj);
                return a2;
            }
        }).map($$Lambda$Buqtf5wefcWEH9A6pHl9cFgqxyo.INSTANCE).toList().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$XrVUCFl4MbmUj0lAdMDsCzZTBEk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoPlayerPresenter.a(i, strArr, observableEmitter, (List) obj);
            }
        });
    }

    public static /* synthetic */ ObservableSource a(String[] strArr, ChildVideoPlayList childVideoPlayList) throws Exception {
        VideoModel.getInstance().setMiTvHasNext(childVideoPlayList.getData().getPages().isHas_next());
        strArr[0] = childVideoPlayList.getData().getMedianame();
        return Observable.fromIterable(childVideoPlayList.getData().getMediaciinfo());
    }

    public static /* synthetic */ void a(int i, String[] strArr, ObservableEmitter observableEmitter, List list) throws Exception {
        L.video.d("getSerialList  page %d  items size  %d", Integer.valueOf(i), Integer.valueOf(list.size()));
        if (list.size() == 1) {
            ((VideoItem) list.get(0)).setTitle(strArr[0]);
        }
        VideoModel.getInstance().addPlayList(list);
        observableEmitter.onNext(Integer.valueOf(VideoModel.getInstance().getPlayIndex()));
        observableEmitter.onComplete();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoPlayNextEvent(VideoPlayNextEvent videoPlayNextEvent) {
        L.video.i("onVideoPlayNextEvent index=%d", Integer.valueOf(videoPlayNextEvent.index));
        s();
        pauseVideo();
        if (videoPlayNextEvent.index >= 0) {
            playByIndex(videoPlayNextEvent.index);
        } else if (videoPlayNextEvent.index == -1) {
            playNext();
        } else if (videoPlayNextEvent.index == -2) {
            f();
        }
    }

    private void a(ArrayList<VideoItem> arrayList) {
        if (ContainerUtil.isEmpty(arrayList)) {
            L.video.i("video list is empty");
            return;
        }
        VideoItem currentVideoItem = VideoModel.getInstance().getCurrentVideoItem();
        if (currentVideoItem != null) {
            arrayList.add(0, currentVideoItem);
            VideoModel.getInstance().setPlayIndex(0);
            if (arrayList.get(arrayList.size() - 1).getMediaId().equals(currentVideoItem.getMediaId())) {
                arrayList.remove(arrayList.size() - 1);
            } else if (arrayList.size() > 2 && arrayList.get(arrayList.size() - 2).getMediaId().equals(currentVideoItem.getMediaId())) {
                arrayList.remove(arrayList.size() - 2);
            }
        }
        if (getVideoPlayerView() != null) {
            VideoModel.getInstance().setPlayList(arrayList);
            getVideoPlayerView().updateVideoList();
            if (isPaused()) {
                playByIndex(0);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoFastForwardEvent(VideoFastForwardEvent videoFastForwardEvent) {
        L.video.i("videoFastForwardEvent=%s", Integer.valueOf(videoFastForwardEvent.seconds));
        a(TimeUnit.SECONDS.toMillis(videoFastForwardEvent.seconds));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoRewindEvent(VideoRewindEvent videoRewindEvent) {
        L.video.i("videoRewindEvent=%s", Integer.valueOf(videoRewindEvent.seconds));
        b(TimeUnit.SECONDS.toMillis(videoRewindEvent.seconds));
    }

    public void a(long j) {
        if (getVideoPlayerView() != null && !i()) {
            getVideoPlayerView().onFastForwardVideo(j);
        }
    }

    public void b(long j) {
        if (getVideoPlayerView() != null && !i()) {
            getVideoPlayerView().onRewindVideo(j);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoSeekEvent(VideoSeekEvent videoSeekEvent) {
        if (getVideoPlayerView() != null && !i()) {
            if (videoSeekEvent.seconds > 0) {
                getVideoPlayerView().onSeekVideo(videoSeekEvent.seconds);
            } else if (videoSeekEvent.pos >= 0) {
                getVideoPlayerView().onSeekVideoPos(videoSeekEvent.pos, videoSeekEvent.forward);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoRestartEvent(VideoRestartEvent videoRestartEvent) {
        L.video.i("onEvent %s", videoRestartEvent);
        v();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoPauseEvent(VideoPauseEvent videoPauseEvent) {
        L.video.i("onEvent %s", videoPauseEvent);
        setFocusChangeResume(false);
        pauseVideo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoPlayerEvent(VideoPlayEvent videoPlayEvent) {
        L.video.i("onEvent %s", videoPlayEvent);
        requestStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoStopEvent(VideoStopEvent videoStopEvent) {
        L.video.i("onEvent %s", videoStopEvent);
        L.video.d("finish by onVideoStopEvent");
        VideoMediaSession.getInstance().setStopped();
        q();
        finish();
    }

    public void requestStart() {
        L.video.i("requestStart");
        if (this.b != null) {
            setFocusChangeResume(true);
            setPaused(false);
            this.b.start();
        }
    }

    public void pauseVideo() {
        L.video.d("VideoPlayerPresenter pauseVideo");
        if (getVideoPlayerView() != null && !i()) {
            setPaused(true);
            getVideoPlayerView().onPauseVideo();
            VideoMediaSession.getInstance().setPaused();
        }
    }

    public void p() {
        L.video.d("VideoPlayerPresenter startVideo");
        if (getVideoPlayerView() != null && getVideoPlayerView().isResumed() && !i()) {
            setPaused(false);
            setFocusChangeResume(true);
            getVideoPlayerView().onStartVideo();
            VideoMediaSession.getInstance().setPlaying(true);
        }
    }

    public void finish() {
        if (getVideoPlayerView() != null && !i()) {
            getVideoPlayerView().quitPlaying();
            WeakReference<IVideoPlayerView> weakReference = this.videoPlayerViewReference;
            if (weakReference != null) {
                weakReference.clear();
            }
        }
    }

    public void initTrackHelper() {
        this.videoMode = VideoModel.getInstance().getMode();
        VideoTrackHelper videoTrackHelper = this.j;
        if (videoTrackHelper == null) {
            this.j = new VideoTrackHelper(this.videoMode);
        } else {
            videoTrackHelper.setVideoMode(this.videoMode);
        }
        this.j.setOriginId(VideoModel.getInstance().getDialogId());
        this.j.setDialogId(VideoModel.getInstance().getDialogId());
    }

    public void d() {
        this.j.setStartPlayTime();
        this.j.setDuration(getVideoPlayerView() != null ? getVideoPlayerView().getDuration() : 0);
    }

    public void e() {
        this.j.monitorLog("play");
        reportVideoPlayEvent("play");
    }

    private void q() {
        reportVideoPlayEvent("pause");
    }

    private void r() {
        reportVideoPlayEvent("auto_switch");
    }

    private void s() {
        reportVideoPlayEvent("man_switch");
    }

    private void t() {
        reportVideoPlayEvent(VideoTrackHelper.SWITCH_TYPE_LIST_SWITCH);
    }

    public void reportVideoPlayEvent(String str) {
        this.j.setPosition(getVideoPlayerView() != null ? getVideoPlayerView().getCurrentPosition() : 0);
        this.j.postTrack(str);
    }

    private void d(boolean z) {
        if ((this.videoMode == VideoMode.VIDEO_PLAYER_PLAY || this.videoMode == VideoMode.VIDEO_PLAYER_PLAY_LIST || this.videoMode == VideoMode.VIDEO_PLAYER_LAUNCH_PLAY_APP) && VideoModel.getInstance().isDependence()) {
            boolean z2 = true;
            if (VideoModel.getInstance().getPlayIndex() != VideoModel.getInstance().playListSize() - 1) {
                z2 = false;
            }
            if (z2) {
                if (!z) {
                    finish();
                }
                EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_LIST_COMPLETE));
            }
        }
    }

    public void u() {
        VideoSessionController.getInstance().clearStopTime(this.context);
    }

    public void c(long j) {
        VideoSessionController.getInstance().setPlayerShutdownTimer(this.context, j);
    }

    public void v() {
        if (getVideoPlayerView() != null && !i()) {
            getVideoPlayerView().onRestartVideo();
        }
    }

    public void addToDisposeBag(Disposable disposable) {
        this.f.add(disposable);
    }

    /* loaded from: classes3.dex */
    public static class a extends FakePlayer {
        private final WeakReference<VideoPlayerPresenter> a;

        public a(VideoPlayerPresenter videoPlayerPresenter, AudioSource audioSource) {
            super(audioSource);
            this.a = new WeakReference<>(videoPlayerPresenter);
        }

        VideoPlayerPresenter c() {
            WeakReference<VideoPlayerPresenter> weakReference = this.a;
            if (weakReference == null) {
                return null;
            }
            return weakReference.get();
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
        public void postStart() {
            L.video.d("FakePlayer postStart");
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$ifXIJr7zycarUHo96jTWtk6kN_I
                @Override // java.lang.Runnable
                public final void run() {
                    VideoPlayerPresenter.a.this.f();
                }
            });
            VideoMediaSession.getInstance().setPlaying(true);
        }

        public /* synthetic */ void f() {
            VideoPlayerPresenter c = c();
            if (c != null) {
                c.p();
            }
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
        public void postStop(AudioSource audioSource) {
            L.video.d("VideoPlayerPresenter FakePlayer postStop");
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$udXpclZY78t1GqeKWpOmTDhLOrU
                @Override // java.lang.Runnable
                public final void run() {
                    VideoPlayerPresenter.a.this.e();
                }
            });
        }

        public /* synthetic */ void e() {
            VideoPlayerPresenter c = c();
            if (c != null) {
                c.pauseVideo();
            }
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void forceStop(AudioSource audioSource) {
            L.video.d("VideoPlayerPresenter FakePlayer forceStop");
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$d0f4y2EzcrvbTgiXAkF_iFkXe3s
                @Override // java.lang.Runnable
                public final void run() {
                    VideoPlayerPresenter.a.this.d();
                }
            });
        }

        public /* synthetic */ void d() {
            VideoPlayerPresenter c = c();
            if (c != null) {
                c.pauseVideo();
            }
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void suspend(AudioSource audioSource) {
            L.video.d("FakePlayer suspend");
            VideoPlayerPresenter c = c();
            if (c != null) {
                if (!c.isPaused()) {
                    c.setFocusChangeResume(true);
                }
                c.getClass();
                ThreadUtil.postInMainThread(new $$Lambda$g9TZZt7LWkjoXkul2XodbWAT6U(c));
            }
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void resume(AudioSource audioSource) {
            final VideoPlayerPresenter c = c();
            if (c != null) {
                L.video.d("FakePlayer resume focusChangeResume=%b", Boolean.valueOf(c.isFocusChangeResume()));
                if (c.isFocusChangeResume()) {
                    ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$re9Jx_I1orZDisKFqy0-6cGDcEg
                        @Override // java.lang.Runnable
                        public final void run() {
                            VideoPlayerPresenter.a.d(VideoPlayerPresenter.this);
                        }
                    });
                }
            }
        }

        public static /* synthetic */ void d(VideoPlayerPresenter videoPlayerPresenter) {
            if (videoPlayerPresenter.getVideoPlayerView() == null || !videoPlayerPresenter.getVideoPlayerView().isResumed()) {
                videoPlayerPresenter.h = true;
            } else {
                videoPlayerPresenter.p();
            }
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        protected void foreground(AudioSource audioSource) {
            L.video.d("FakePlayer foreground");
            resume(audioSource);
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void background(AudioSource audioSource) {
            L.video.d("FakePlayer background");
            super.background(audioSource);
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        protected void playbackControl(final PlaybackControllerInfo playbackControllerInfo) {
            L.player.i("VideoPlayerPresenter,onPlaybackControl=%s", playbackControllerInfo.playbackControl);
            final VideoPlayerPresenter c = c();
            if (c != null) {
                switch (playbackControllerInfo.playbackControl) {
                    case CONTINUE_PLAYING:
                    case PLAY:
                        c.getClass();
                        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$ozqdaZP-0hQg6wQPQC46xV0HUuk
                            @Override // java.lang.Runnable
                            public final void run() {
                                VideoPlayerPresenter.this.p();
                            }
                        });
                        VideoMediaSession.getInstance().setPlaying(true);
                        return;
                    case PAUSE:
                        c.setFocusChangeResume(false);
                        c.setPaused(true);
                        c.getClass();
                        ThreadUtil.postInMainThread(new $$Lambda$g9TZZt7LWkjoXkul2XodbWAT6U(c));
                        return;
                    case PREV:
                        c.getClass();
                        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$PGNM70ajtPBKaO91okx3E35ybbs
                            @Override // java.lang.Runnable
                            public final void run() {
                                VideoPlayerPresenter.this.f();
                            }
                        });
                        return;
                    case NEXT:
                        c.getClass();
                        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$IYqxESHCR0b52zzLfuFmm87F0eE
                            @Override // java.lang.Runnable
                            public final void run() {
                                VideoPlayerPresenter.this.playNext();
                            }
                        });
                        return;
                    case STOP:
                        c.getClass();
                        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$FfIXu8UisJWa1ib9F5wIrCVGSOc
                            @Override // java.lang.Runnable
                            public final void run() {
                                VideoPlayerPresenter.this.finish();
                            }
                        });
                        return;
                    case SEEK:
                        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$QiOdBq3MvpD9RxTKLhXq7z1BqK4
                            @Override // java.lang.Runnable
                            public final void run() {
                                VideoPlayerPresenter.a.a(PlaybackControllerInfo.this, c);
                            }
                        });
                        return;
                    case START_OVER:
                        c.getClass();
                        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$a23hjWo9UOnpR6NcZfoxwZbFZNE
                            @Override // java.lang.Runnable
                            public final void run() {
                                VideoPlayerPresenter.this.v();
                            }
                        });
                        return;
                    case STOP_AFTER:
                        if (playbackControllerInfo.timeoutInMs > 0) {
                            c.c(playbackControllerInfo.timeoutInMs);
                            return;
                        } else {
                            int i = playbackControllerInfo.countOfEnd;
                            return;
                        }
                    case CANCEL_STOP_AFTER:
                        c.u();
                        return;
                    case SELECTOR:
                        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerPresenter$a$T_b2JUlvKQK8kugzWQfA6C9Po8Y
                            @Override // java.lang.Runnable
                            public final void run() {
                                VideoPlayerPresenter.a.a(VideoPlayerPresenter.this, playbackControllerInfo);
                            }
                        });
                        return;
                    default:
                        return;
                }
            }
        }

        public static /* synthetic */ void a(PlaybackControllerInfo playbackControllerInfo, VideoPlayerPresenter videoPlayerPresenter) {
            if (playbackControllerInfo != null && playbackControllerInfo.referenceDef == PlaybackControllerInfo.ReferenceDef.CURRENT) {
                if (playbackControllerInfo.deltaInMs > 0) {
                    videoPlayerPresenter.a(Math.abs(playbackControllerInfo.deltaInMs));
                } else if (playbackControllerInfo.deltaInMs < 0) {
                    videoPlayerPresenter.b(Math.abs(playbackControllerInfo.deltaInMs));
                }
            }
        }

        public static /* synthetic */ void a(VideoPlayerPresenter videoPlayerPresenter, PlaybackControllerInfo playbackControllerInfo) {
            videoPlayerPresenter.playByIndex(playbackControllerInfo.index - 1);
        }
    }
}
