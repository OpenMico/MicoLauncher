package com.xiaomi.micolauncher.common.player;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AudioPlayerStateChangeEvent;
import com.xiaomi.micolauncher.common.event.UiEvent;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.impl.TemplatePlayInfoOperation;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class MicoMultiAudioPlayer implements BaseExoPlayer.PlayerListener {
    private static volatile MicoMultiAudioPlayer a;
    private BaseExoPlayer b;
    private List<AudioPlayer.AudioItemV1> c;
    private volatile AudioPlayer.AudioItemV1 d;
    private Instruction e;
    private String g;
    private String h;
    private boolean i;
    private boolean j;
    private Common.AudioType k;
    private int f = -1;
    protected int loopType = LoopType.ORDER.ordinal();
    private AudioSource l = AudioSource.AUDIO_SOURCE_UI;

    /* loaded from: classes3.dex */
    public enum AudioState {
        PREPARED,
        COMPLETE,
        ERROR,
        STARTED,
        PAUSED,
        STOPPED,
        SUSPEND,
        RESUME,
        BACKGROUND,
        FOREGROUND,
        STOP_AFTER,
        CANCEL_STOP_AFTER,
        PREVIOUS,
        NEXT,
        LIST_FINISH,
        LOAD_MORE
    }

    public static MicoMultiAudioPlayer getInstance() {
        if (a == null) {
            synchronized (MicoMultiAudioPlayer.class) {
                if (a == null) {
                    a = new MicoMultiAudioPlayer();
                }
            }
        }
        return a;
    }

    private MicoMultiAudioPlayer() {
    }

    private void a() {
        if (this.b == null) {
            this.b = new BaseExoPlayer(this.l) { // from class: com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer.1
                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                protected void onResume() {
                    MicoMultiAudioPlayer.this.a(AudioState.RESUME);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                protected void onPaused() {
                    MicoMultiAudioPlayer.this.a(AudioState.PAUSED);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                protected void onStarted() {
                    setVolume(1.0f);
                    MicoMultiAudioPlayer.this.a(AudioState.STARTED);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                protected void onStopped() {
                    MicoMultiAudioPlayer.this.a(AudioState.STOPPED);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                protected void onSuspend() {
                    MicoMultiAudioPlayer.this.a(AudioState.SUSPEND);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                public void onBackground() {
                    MicoMultiAudioPlayer.this.a(AudioState.BACKGROUND);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                public void onForeground() {
                    MicoMultiAudioPlayer.this.a(AudioState.FOREGROUND);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                public void onVolumeFadeComplete() {
                    super.onVolumeFadeComplete();
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                public void onPlaybackControl(PlaybackControllerInfo playbackControllerInfo) {
                    L.player.i("%s,onPlaybackControl=%s", "MicoMultiAudioPlayer", playbackControllerInfo.playbackControl);
                    switch (AnonymousClass3.a[playbackControllerInfo.playbackControl.ordinal()]) {
                        case 1:
                            if (playbackControllerInfo.setPropertyName == PlaybackControllerInfo.SetProperty.LOOP_MODE && playbackControllerInfo.loopMode != null) {
                                MicoMultiAudioPlayer.this.b(playbackControllerInfo.loopMode.ordinal());
                                return;
                            }
                            return;
                        case 2:
                        case 3:
                            MicoMultiAudioPlayer.this.startPlayer();
                            return;
                        case 4:
                            MicoMultiAudioPlayer.this.pausePlayer();
                            return;
                        case 5:
                            MicoMultiAudioPlayer.this.playPrev();
                            return;
                        case 6:
                            MicoMultiAudioPlayer.this.playNext();
                            return;
                        case 7:
                            MicoMultiAudioPlayer.this.stopPlayer();
                            return;
                        case 8:
                            if (playbackControllerInfo.timeoutInMs > 0) {
                                MicoMultiAudioPlayer.this.a(AudioState.STOP_AFTER, playbackControllerInfo.timeoutInMs);
                                return;
                            }
                            return;
                        case 9:
                            MicoMultiAudioPlayer.this.a(AudioState.CANCEL_STOP_AFTER);
                            return;
                        default:
                            return;
                    }
                }
            };
            this.b.setListener(this);
            this.j = false;
        }
    }

    /* renamed from: com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer$3 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[PlaybackControl.values().length];

        static {
            try {
                a[PlaybackControl.SET_PROPERTY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[PlaybackControl.CONTINUE_PLAYING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[PlaybackControl.PLAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[PlaybackControl.PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[PlaybackControl.PREV.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[PlaybackControl.NEXT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[PlaybackControl.STOP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[PlaybackControl.STOP_AFTER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[PlaybackControl.CANCEL_STOP_AFTER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onPrepared(Player player) {
        L.player.d("%s onPrepared %s", "MicoMultiAudioPlayer", this.h);
        b("play");
        a(AudioState.PREPARED);
        if (!this.i) {
            L.player.d("%s:onPrepared start", "MicoMultiAudioPlayer");
            c();
            if (player != null) {
                player.start();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onComplete(Player player) {
        b("complete");
        a(AudioState.COMPLETE);
        b();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onError(Player player, int i, Exception exc) {
        L.player.e("%s, onError, error=%s, extra=%s, playAudioUrl=%s", "MicoMultiAudioPlayer", Integer.valueOf(i), exc, this.h);
        b("error");
        a(AudioState.ERROR);
        if (!isPaused()) {
            release();
        }
    }

    public boolean hasCapability(Instruction instruction) {
        if (instruction == null || !(instruction.getPayload() instanceof AudioPlayer.Play)) {
            return false;
        }
        AudioPlayer.Play play = (AudioPlayer.Play) instruction.getPayload();
        if (!play.getAudioType().isPresent()) {
            return ContainerUtil.hasData(play.getAudioItems());
        }
        Common.AudioType audioType = play.getAudioType().get();
        return audioType == Common.AudioType.ANCIENT_POEM || audioType == Common.AudioType.WHITE_NOISE || audioType == Common.AudioType.VOICE || audioType == Common.AudioType.JOKE || audioType == Common.AudioType.TTS || audioType == Common.AudioType.TRANSLATION || audioType == Common.AudioType.ALERT_SOUND;
    }

    public void play(Instruction instruction) {
        if (instruction != null) {
            L.player.i("%s play=%s", "MicoMultiAudioPlayer", instruction.getFullName());
            this.l = AudioSource.AUDIO_SOURCE_UI;
            release(false);
            a();
            a(instruction);
            a(this.k);
            b("enter");
        }
    }

    public void play(String str, AudioSource audioSource) {
        L.player.i("%s playUrl=%s", "MicoMultiAudioPlayer", str);
        this.l = audioSource;
        release(false);
        a();
        this.h = str;
        a(str);
        b("enter");
    }

    private void a(Instruction instruction) {
        if (instruction != null) {
            this.e = instruction;
            AudioPlayer.Play play = (AudioPlayer.Play) instruction.getPayload();
            AudioPlayer.PlayBehavior playBehavior = play.getPlayBehavior();
            if (play.getAudioType().isPresent()) {
                this.k = play.getAudioType().get();
            }
            List<AudioPlayer.AudioItemV1> audioItems = play.getAudioItems();
            if (ContainerUtil.isEmpty(audioItems)) {
                L.player.i("AudioItems is empty");
                j();
            } else if (playBehavior == AudioPlayer.PlayBehavior.APPEND) {
                List<AudioPlayer.AudioItemV1> list = this.c;
                if (list != null) {
                    list.addAll(audioItems);
                } else {
                    this.c = audioItems;
                    this.f = -1;
                }
                playNext();
            } else if (playBehavior == AudioPlayer.PlayBehavior.REPLACE_ALL) {
                this.c = audioItems;
                this.f = -1;
                playNext();
            } else if (playBehavior == AudioPlayer.PlayBehavior.REPLACE_PENDING) {
                if (this.d != null) {
                    audioItems.add(0, this.d);
                }
                this.c = audioItems;
                if (this.d == null) {
                    this.f = -1;
                    playNext();
                    return;
                }
                this.f = 0;
            } else if (playBehavior == AudioPlayer.PlayBehavior.INSERT_FRONT) {
                if (!ContainerUtil.isEmpty(this.c)) {
                    audioItems.addAll(this.c);
                }
                this.c = audioItems;
                this.f = -1;
                playNext();
            } else {
                release();
                j();
            }
        }
    }

    public void setProperty(@NotNull PlaybackControllerInfo playbackControllerInfo) {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.onPlaybackControl(playbackControllerInfo);
        }
    }

    private void a(Common.AudioType audioType) {
        if (audioType == Common.AudioType.WHITE_NOISE) {
            EventBusRegistry.getEventBus().post(new UiEvent.WhiteNoiseEvent(getDialogId()));
        }
    }

    public void playPrev() {
        L.player.i("%s playNext", "MicoMultiAudioPlayer");
        if (this.e == null) {
            j();
        } else if (a(e())) {
            a(AudioState.PREVIOUS);
        } else {
            j();
        }
    }

    private void b() {
        L.player.i("%s playNextOnComplete isSingle=%s", "MicoMultiAudioPlayer", Boolean.valueOf(g()));
        if (g()) {
            seekTo(0L);
        } else {
            playNext();
        }
    }

    public void playNext() {
        L.player.i("%s playNext", "MicoMultiAudioPlayer");
        if (this.e == null) {
            j();
        } else if (a(f())) {
            a(AudioState.NEXT);
        } else {
            j();
        }
    }

    private boolean a(int i) {
        L.player.i("%s playByIndex=%s", "MicoMultiAudioPlayer", Integer.valueOf(i));
        AudioPlayer.AudioItemV1 c = c(i);
        if (c == null) {
            return false;
        }
        a(c);
        return true;
    }

    private void a(AudioPlayer.AudioItemV1 audioItemV1) {
        if (audioItemV1 == null) {
            L.player.i("audioItem is empty for do play");
            return;
        }
        this.d = audioItemV1;
        String str = null;
        if (this.d.getStream() != null) {
            str = this.d.getStream().getUrl();
        }
        if (this.d.getItemId() != null) {
            this.g = this.d.getItemId().getAudioId();
        }
        if (!TextUtils.isEmpty(str)) {
            a(this.d.getStream());
        } else {
            playNext();
        }
        L.player.i("play resource audioId=%s,url=%s", this.g, str);
    }

    @SuppressLint({"CheckResult"})
    private void a(AudioPlayer.Stream stream) {
        StreamHelper.buildUrl(stream).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.player.-$$Lambda$MicoMultiAudioPlayer$dNqen3RWVfR-BWjenHCqxqLhAxw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMultiAudioPlayer.this.a((String) obj);
            }
        }, $$Lambda$MicoMultiAudioPlayer$AIqLL058EUOl3Sl4RvQKhwWbUw.INSTANCE);
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.player.i("doAuth", th);
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str) || this.b == null) {
            L.player.i("url is empty player=%s", this.b);
            if (g()) {
                release();
            } else {
                playNext();
            }
        } else {
            this.h = str;
            L.player.i("%s try to play url: %s", "MicoMultiAudioPlayer", str);
            setPaused(false);
            try {
                b("prepare");
                this.b.setDataSource(str);
            } catch (Exception e) {
                e.printStackTrace();
                release();
            }
        }
    }

    private void c() {
        int offsetInMs;
        if (this.d != null && this.d.getStream() != null && (offsetInMs = this.d.getStream().getOffsetInMs()) > 0) {
            seekTo(offsetInMs);
        }
    }

    public void setPaused(boolean z) {
        this.i = z;
    }

    public boolean isPaused() {
        return this.i;
    }

    public boolean isReleased() {
        return this.j;
    }

    public void seekTo(long j) {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.seekTo((int) j);
        }
    }

    public void release(String str) {
        L.player.i("%s,release dialogId=%s", "MicoMultiAudioPlayer", str);
        if (str != null && str.equals(getDialogId())) {
            release();
        }
    }

    public void release() {
        release(true);
    }

    public void release(boolean z) {
        L.player.i("%s,release", "MicoMultiAudioPlayer");
        b("exit");
        setPaused(true);
        if (z) {
            i();
        }
        d();
        this.e = null;
        this.f = -1;
        this.k = null;
        this.c = null;
        this.d = null;
    }

    private void d() {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.release();
            this.b = null;
            this.j = true;
        }
    }

    public void pausePlayer() {
        try {
            setPaused(true);
            if (this.b != null && this.b.isPlaying()) {
                this.b.pause();
            }
            h();
        } catch (Exception e) {
            L.player.e("pause error ", e);
        }
    }

    public void startPlayer() {
        L.player.i("%s resume()", "MicoMultiAudioPlayer");
        try {
            setPaused(false);
            if (this.b == null || !this.b.isPrepared()) {
                playNext();
                L.player.e("start failed");
            } else {
                this.b.start();
                L.player.d("%s resume(),start", "MicoMultiAudioPlayer");
            }
        } catch (Exception e) {
            stopPlayer();
            L.player.e("play failed", e);
        }
    }

    public void stopPlayer() {
        try {
            L.player.i("%s stopPlayer", "MicoMultiAudioPlayer");
            if (this.b != null) {
                this.b.stop();
                this.b.reset();
            }
            setPaused(true);
            i();
        } catch (Exception e) {
            L.player.e("stopPlayer", e);
        }
    }

    public void tryStopPlayer() {
        try {
            L.player.i("%s tryStopPlayer", "MicoMultiAudioPlayer");
            if (this.b != null) {
                this.b.stop();
            }
            if (this.b.isManualPause()) {
                setPaused(true);
                i();
            }
        } catch (Exception e) {
            L.player.e("tryStopPlayer", e);
        }
    }

    private int e() {
        if (LoopType.isShuffle(this.loopType)) {
            this.f = (int) (Math.random() * this.c.size());
        } else {
            this.f--;
        }
        if (this.f < 0 && (this.k == Common.AudioType.WHITE_NOISE || LoopType.isListLoop(this.loopType))) {
            this.f = this.c.size() - 1;
        }
        return this.f;
    }

    private int f() {
        if (LoopType.isShuffle(this.loopType)) {
            this.f = (int) (Math.random() * this.c.size());
        } else {
            this.f++;
        }
        if (this.f >= this.c.size() && (this.k == Common.AudioType.WHITE_NOISE || LoopType.isListLoop(this.loopType))) {
            this.f = 0;
        }
        return this.f;
    }

    private boolean g() {
        return LoopType.isSingle(this.loopType);
    }

    public void b(int i) {
        L.player.d("%s set loop type of %d", "MicoMultiAudioPlayer", Integer.valueOf(i));
        this.loopType = i;
        if (g()) {
            this.b.setLooping(true);
        } else {
            this.b.setLooping(false);
        }
    }

    private AudioPlayer.AudioItemV1 c(int i) {
        if (ContainerUtil.isEmpty(this.c) || ContainerUtil.isOutOfBound(i, this.c)) {
            return null;
        }
        return this.c.get(i);
    }

    public void a(AudioState audioState) {
        L.player.i("%s onAudioStatusChange=%s", "MicoMultiAudioPlayer", audioState);
        a(audioState, 0);
    }

    public void a(AudioState audioState, int i) {
        AudioPlayerStateChangeEvent audioPlayerStateChangeEvent = new AudioPlayerStateChangeEvent(audioState);
        audioPlayerStateChangeEvent.dialogId = getDialogId();
        audioPlayerStateChangeEvent.audioId = getAudioId();
        audioPlayerStateChangeEvent.index = getPlayingIndex();
        audioPlayerStateChangeEvent.timeoutInMs = i;
        EventBusRegistry.getEventBus().post(audioPlayerStateChangeEvent);
    }

    private void h() {
        a(AudioState.PAUSED);
    }

    private void i() {
        a(AudioState.STOPPED);
    }

    private void j() {
        if (k()) {
            l();
            return;
        }
        a(AudioState.LIST_FINISH);
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.releaseFocus();
        }
    }

    private boolean k() {
        Instruction instruction = this.e;
        if (instruction == null) {
            return false;
        }
        AudioPlayer.Play play = (AudioPlayer.Play) instruction.getPayload();
        if (play.isNeedsLoadmore().isPresent()) {
            return play.isNeedsLoadmore().get().booleanValue();
        }
        return false;
    }

    private void l() {
        AudioPlayer.Play play = (AudioPlayer.Play) this.e.getPayload();
        Nlp.LoadMore loadMore = new Nlp.LoadMore();
        loadMore.setOriginId(getDialogId());
        loadMore.setOffset(play.getAudioItems().size());
        if (play.getLoadmoreDelta().isPresent()) {
            loadMore.setDelta(play.getLoadmoreDelta().get().intValue());
        }
        if (play.getLoadmoreToken().isPresent()) {
            loadMore.setToken(play.getLoadmoreToken().get());
        }
        Event buildEvent = APIUtils.buildEvent(loadMore);
        buildEvent.setContext(SpeechContextHelper.getContexts(false));
        SpeechEventUtil.getInstance().eventRequest(buildEvent, new SpeechEventUtil.EventListener() { // from class: com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer.2
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventResult(List<Instruction> list, Event event) {
                MicoMultiAudioPlayer.this.a(list);
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventFail(Event event, boolean z) {
                MicoMultiAudioPlayer.this.release();
            }
        });
    }

    public void a(List<Instruction> list) {
        Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.Template.PlayInfo);
        if (intention != null) {
            Template.PlayInfo playInfo = (Template.PlayInfo) intention.getPayload();
            if (playInfo.getType().isPresent()) {
                if (playInfo.getType().get() == Template.PlayInfoType.JOKE) {
                    List<Template.PlayInfoItem> items = playInfo.getItems();
                    AudioPlayerStateChangeEvent audioPlayerStateChangeEvent = new AudioPlayerStateChangeEvent(AudioState.LOAD_MORE);
                    audioPlayerStateChangeEvent.dialogId = InstructionUtil.getDialogId(intention);
                    audioPlayerStateChangeEvent.jokeItems = TemplatePlayInfoOperation.parsePlayInfoForJoke(items);
                    EventBusRegistry.getEventBus().post(audioPlayerStateChangeEvent);
                }
            } else {
                return;
            }
        }
        getInstance().play(InstructionUtil.getIntention(list, AIApiConstants.AudioPlayer.Play));
    }

    public String getDialogId() {
        return InstructionUtil.getDialogId(this.e);
    }

    public AudioPlayer.AudioItemV1 getPlayingAudioItem() {
        return this.d;
    }

    public AudioPlayer.AudioItemV1 getItemByIndex(int i) {
        if (ContainerUtil.isEmpty(this.c) || i >= this.c.size()) {
            return null;
        }
        return this.c.get(i);
    }

    public boolean hasMoreResource() {
        return !ContainerUtil.isEmpty(this.c) && getPlayingIndex() < this.c.size();
    }

    public int getPlayingIndex() {
        return this.f;
    }

    public String getAudioId() {
        return this.g;
    }

    private void b(String str) {
        L.monitor.i("dialog_id=%s,player_name=%s,action=%s,audio_id=%s,play_url=%s", getDialogId(), "MicoMultiAudioPlayer", str, this.g, this.h);
    }
}
