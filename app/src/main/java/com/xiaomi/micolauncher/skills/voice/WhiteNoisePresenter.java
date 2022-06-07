package com.xiaomi.micolauncher.skills.voice;

import android.os.CountDownTimer;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Template;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AudioPlayerStateChangeEvent;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class WhiteNoisePresenter {
    public static final int UNLIMITED = -1;
    private long a;
    private IWhiteNoiseView b;
    private a c;
    private String d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void f() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WhiteNoisePresenter(IWhiteNoiseView iWhiteNoiseView) {
        this.b = iWhiteNoiseView;
    }

    public void setDialogId(String str) {
        this.d = str;
        L.base.i("setDialogId=%s", str);
    }

    private void a(AudioPlayer.AudioItemV1 audioItemV1) {
        if (audioItemV1 == null) {
            L.base.i("resetView audioItem is null");
            return;
        }
        String str = "";
        String str2 = null;
        if (audioItemV1.getText().isPresent()) {
            str = audioItemV1.getText().get();
        }
        if (audioItemV1.getBackgroundImage().isPresent()) {
            List<Template.ImageSource> sources = audioItemV1.getBackgroundImage().get().getSources();
            if (!ContainerUtil.isEmpty(sources)) {
                str2 = sources.get(0).getUrl();
            }
        }
        if (this.b != null) {
            if (!TextUtils.isEmpty(str2)) {
                this.b.updateImage(str2);
            } else {
                this.b.updateImageLocal(g());
            }
            L.base.i("title=%s", str);
            this.b.updateTitle(str);
        }
    }

    private String g() {
        return "bg_white_noise_" + (new Random().nextInt(100) % 6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        IWhiteNoiseView iWhiteNoiseView = this.b;
        if (iWhiteNoiseView != null) {
            iWhiteNoiseView.finish(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return MicoMultiAudioPlayer.getInstance().hasMoreResource();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        L.base.i("nextVoice");
        if (MicoMultiAudioPlayer.getInstance() != null) {
            MicoMultiAudioPlayer.getInstance().playNext();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        L.base.i("prevVoice");
        if (MicoMultiAudioPlayer.getInstance() != null) {
            MicoMultiAudioPlayer.getInstance().playPrev();
        }
    }

    public void play() {
        if (MicoMultiAudioPlayer.getInstance() != null) {
            MicoMultiAudioPlayer.getInstance().startPlayer();
        }
        onResumeCutDown();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d() {
        if (MicoMultiAudioPlayer.getInstance() != null) {
            MicoMultiAudioPlayer.getInstance().pausePlayer();
        }
        stopCutDownTime();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e() {
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        if (MicoMultiAudioPlayer.getInstance() != null) {
            a(MicoMultiAudioPlayer.getInstance().getPlayingAudioItem());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(long j) {
        this.a = j;
        stopCutDownTime();
        if (-1 == j) {
            b("");
            return;
        }
        this.c = new a(j, TimeUnit.SECONDS.toMillis(1L));
        this.c.start();
    }

    public void stopCutDownTime() {
        a aVar = this.c;
        if (aVar != null) {
            aVar.cancel();
            this.c = null;
            b("");
        }
    }

    public void onResumeCutDown() {
        long j = this.a;
        if (j > 0) {
            a(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(long j) {
        long j2 = j / 1000;
        return String.format("%02d:%02d", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60));
    }

    public void onDestroy() {
        this.b = null;
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
        h();
    }

    private void a(MicoMultiAudioPlayer.AudioState audioState, String str) {
        String str2;
        if (this.b != null && (str2 = this.d) != null && str2.equals(str)) {
            this.b.onPlayStateChange(audioState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        IWhiteNoiseView iWhiteNoiseView = this.b;
        if (iWhiteNoiseView != null) {
            iWhiteNoiseView.updateTime(str);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPlayerStateChangeEvent(AudioPlayerStateChangeEvent audioPlayerStateChangeEvent) {
        L.base.i("event.audioState=%s", audioPlayerStateChangeEvent);
        if (audioPlayerStateChangeEvent.dialogId == null || !audioPlayerStateChangeEvent.dialogId.equalsIgnoreCase(this.d)) {
            L.base.i("not equals dialog id");
            Logger logger = L.base;
            logger.i("dialogId=" + this.d);
            Logger logger2 = L.base;
            logger2.i("event.dialogId=" + audioPlayerStateChangeEvent.dialogId);
            return;
        }
        MicoMultiAudioPlayer.AudioState audioState = audioPlayerStateChangeEvent.audioState;
        if (audioState == MicoMultiAudioPlayer.AudioState.NEXT || audioState == MicoMultiAudioPlayer.AudioState.PREVIOUS) {
            if (MicoMultiAudioPlayer.getInstance() != null) {
                a(MicoMultiAudioPlayer.getInstance().getItemByIndex(audioPlayerStateChangeEvent.index));
                return;
            }
            L.base.i("MicoMultiAudioPlayer is null");
            h();
        } else if (audioState == MicoMultiAudioPlayer.AudioState.STARTED || audioState == MicoMultiAudioPlayer.AudioState.RESUME) {
            a(audioPlayerStateChangeEvent.audioState, audioPlayerStateChangeEvent.dialogId);
        } else if (audioState == MicoMultiAudioPlayer.AudioState.PAUSED || audioState == MicoMultiAudioPlayer.AudioState.SUSPEND) {
            a(audioPlayerStateChangeEvent.audioState, audioPlayerStateChangeEvent.dialogId);
        } else if (audioState == MicoMultiAudioPlayer.AudioState.ERROR) {
            if (!a()) {
                h();
            }
        } else if (audioState == MicoMultiAudioPlayer.AudioState.LIST_FINISH) {
            h();
        } else if (audioState == MicoMultiAudioPlayer.AudioState.STOPPED) {
            stopCutDownTime();
            a("release");
        } else if (audioState == MicoMultiAudioPlayer.AudioState.STOP_AFTER) {
            if (audioPlayerStateChangeEvent.timeoutInMs > 0) {
                a(audioPlayerStateChangeEvent.timeoutInMs);
            }
        } else if (audioState == MicoMultiAudioPlayer.AudioState.CANCEL_STOP_AFTER) {
            stopCutDownTime();
        }
    }

    private void h() {
        if (MicoMultiAudioPlayer.getInstance() != null) {
            MicoMultiAudioPlayer.getInstance().release(this.d);
        }
        stopCutDownTime();
        a("release");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends CountDownTimer {
        a(long j, long j2) {
            super(j, j2);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            WhiteNoisePresenter.this.a = j;
            WhiteNoisePresenter whiteNoisePresenter = WhiteNoisePresenter.this;
            whiteNoisePresenter.b(whiteNoisePresenter.b(j));
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            WhiteNoisePresenter.this.a("count down");
        }
    }
}
