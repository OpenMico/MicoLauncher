package com.xiaomi.micolauncher.common.player;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.TtsPlayer;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.utils.PcmData;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.speech.utils.WavFileWriter;
import com.xiaomi.micolauncher.common.track.TrackStat;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class TtsPcmPlayer extends FakePlayer implements TtsPlayer {
    private String b;
    private Handler c;
    private int e;
    private TtsPlayer.TtsPayload f;
    private long g;
    private long h;
    private AudioTrack p;
    private long s;
    private final String a = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vpm/wav/tts.pcm";
    private boolean k = false;
    private boolean l = false;
    private boolean m = true;
    private int n = 0;
    private int o = 0;
    private String j = null;
    private Handler d = new Handler(ThreadUtil.getWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.player.-$$Lambda$TtsPcmPlayer$UfPSamsOimlK-Dv5nsVDO6B9c3s
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a;
            a = TtsPcmPlayer.this.a(message);
            return a;
        }
    });
    private AtomicBoolean q = new AtomicBoolean(false);
    private WavFileWriter i = null;
    private AtomicBoolean r = new AtomicBoolean(true);

    public TtsPcmPlayer(Handler handler) {
        super(AudioSource.AUDIO_SOURCE_TTS);
        this.c = handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Message message) {
        WavFileWriter wavFileWriter;
        if (message.what == 1) {
            PcmData pcmData = (PcmData) message.obj;
            if (pcmData.getPcm() != null) {
                a(pcmData.getPcm(), pcmData.getSampleRate(), pcmData.getChannels(), pcmData.getBits());
                this.e -= pcmData.getPcm().length;
                if (SpeechConfig.needToSaveTts() && (wavFileWriter = this.i) != null) {
                    wavFileWriter.writePcmPacket(pcmData.getPcm(), 0, pcmData.getPcm().length);
                }
            }
            if (pcmData.isEof()) {
                requestFinish();
            }
        }
        return true;
    }

    private boolean c() {
        return a(16000, 4, 2);
    }

    private boolean a(int i, int i2, int i3) {
        if (this.k) {
            L.player.e("%s prepare: Player already mPrepared!", "[TtsPcmPlayer]:");
            return false;
        }
        if (i2 != 4) {
            if (i2 == 1) {
                i2 = 4;
            } else if (i2 != 2) {
                return false;
            } else {
                i2 = 12;
            }
        }
        if (i3 != 2) {
            if (i3 != 16) {
                return false;
            }
            i3 = 2;
        }
        this.n = AudioTrack.getMinBufferSize(i, i2, i3);
        if (this.n == -2) {
            L.player.e("%s prepare: Invalid parameter!", "[TtsPcmPlayer]:");
            return false;
        }
        L.player.d("%s mMinBufferMs=%dms", "[TtsPcmPlayer]:", Integer.valueOf(this.n >> 5));
        this.p = new AudioTrack(new AudioAttributes.Builder().setContentType(2).setUsage(1).build(), new AudioFormat.Builder().setSampleRate(i).setChannelMask(i2).setEncoding(i3).build(), this.n, 1, 0);
        if (this.p.getState() == 0) {
            L.player.e("%s prepare: AudioTrack initialize fail!", "[TtsPcmPlayer]:");
            return false;
        }
        this.k = true;
        this.o = 0;
        if (this.q.get()) {
            this.p.setVolume(0.0f);
        }
        L.player.i("%s prepare: Start audio player success!", "[TtsPcmPlayer]:");
        return true;
    }

    private void a(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            try {
                int write = this.p.write(bArr, i, bArr.length - i);
                if (write <= 0) {
                    L.player.e("%s doWrite.error=%s!", "[TtsPcmPlayer]:", Integer.valueOf(write));
                    if (!f()) {
                        return;
                    }
                } else {
                    this.o += write;
                    if (!this.l && this.o >= this.n) {
                        this.p.play();
                        L.monitor.i("[TtsPcmPlayer]:TTSPlayer.play.dialog_id=%s", this.j);
                        this.l = true;
                        L.player.d("%s doWrite.start_play_due_to mTotalSize=%dms, mMinBufferSize=%dms", "[TtsPcmPlayer]:", Integer.valueOf(this.o >> 5), Integer.valueOf(this.n >> 5));
                        QueryLatency.getInstance().setTtsPlayStreamStartMs();
                    }
                    i += write;
                }
            } catch (IllegalStateException e) {
                L.player.e("%s addData.write.exception=%s", "[TtsPcmPlayer]:", e);
                return;
            }
        }
    }

    private void a(byte[] bArr, int i, int i2, int i3) {
        if (bArr != null && !this.m) {
            if (!this.k) {
                L.player.e("%s play: Player not started!", "[TtsPcmPlayer]:");
                return;
            }
            if (!(i == 16000 && i2 == 1 && i3 == 16)) {
                L.base.d("%s sr=%d, ch=%d, bits=%d", "[TtsPcmPlayer]:", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
                e();
                if (!a(i, i2, i3)) {
                    return;
                }
            }
            if (bArr.length == 0) {
                requestFinish();
            } else {
                a(bArr);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void play(Object obj) {
        if (this.f == null) {
            L.speech.e("play pcm content is empty");
        } else if (obj instanceof PcmData) {
            PcmData pcmData = (PcmData) obj;
            int length = pcmData.getPcm() == null ? 0 : pcmData.getPcm().length;
            if (this.e > 7680000) {
                L.player.e("%s play.mAudioDataCacheLen=%d", "[TtsPcmPlayer]:", Integer.valueOf(this.e));
                return;
            }
            this.d.obtainMessage(1, obj).sendToTarget();
            this.e += length;
        }
    }

    private void d() {
        if (this.k) {
            this.h = this.g;
            this.d.removeMessages(1);
            this.d.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.-$$Lambda$TtsPcmPlayer$rV92cONiY9XtiGEkJWaBhsXGy48
                @Override // java.lang.Runnable
                public final void run() {
                    TtsPcmPlayer.this.h();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void h() {
        if (this.h != this.g) {
            L.player.w("%s a new tts start playing, mStopTtsId=%s, mPlayingTtsId=%s", "[TtsPcmPlayer]:", Long.valueOf(this.h), Long.valueOf(this.g));
            return;
        }
        if (this.p.getPlayState() == 3) {
            this.p.stop();
        }
        this.l = false;
        this.m = true;
        this.o = 0;
        a(false);
        releaseFocus(500L);
        L.player.i("%s stopPlayer: pause audio player success!", "[TtsPcmPlayer]:");
    }

    private void e() {
        this.d.removeMessages(1);
        if (this.k) {
            try {
                this.p.pause();
                this.p.flush();
            } catch (IllegalStateException e) {
                L.player.e("%s stopPlayer.exception=%s", "[TtsPcmPlayer]:", e);
            }
            this.l = false;
            this.m = true;
            this.o = 0;
            L.player.i("%s stopPlayer: Stop audio player success!", "[TtsPcmPlayer]:");
        }
    }

    private boolean f() {
        this.k = false;
        this.p.release();
        return c();
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void requestPlay(String str, String str2) {
        this.d.removeMessages(1);
        this.e = 0;
        this.b = "";
        this.o = 0;
        if (this.k || c()) {
            if (this.r.compareAndSet(false, true)) {
                postStart();
            } else {
                start();
            }
            this.m = false;
            this.b = str2;
        } else {
            a(true);
        }
        this.g = SystemClock.currentThreadTimeMillis();
        if (SpeechConfig.needToSaveTts()) {
            WavFileWriter wavFileWriter = this.i;
            if (wavFileWriter == null) {
                this.i = new WavFileWriter(1, 16000, this.a);
                this.i.open();
            } else {
                wavFileWriter.close();
            }
        }
        L.player.d("%s requestPlay: content=%s", "[TtsPcmPlayer]:", str2);
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void requestPlay(TtsPlayer.TtsPayload ttsPayload) {
        if (ttsPayload == null) {
            L.player.e("%s requestPlay payload is null", "[TtsPcmPlayer]:");
            return;
        }
        this.s = System.currentTimeMillis();
        this.f = ttsPayload;
        this.j = ttsPayload.id;
        requestPlay(ttsPayload.url, ttsPayload.content);
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void requestFinish() {
        L.player.d("%s requestFinish", "[TtsPcmPlayer]:");
        d();
        a(g(), false);
    }

    private void a(int i) {
        this.c.removeMessages(1001);
        this.c.obtainMessage(1001, i, 0, this.b).sendToTarget();
    }

    private void a(boolean z) {
        WavFileWriter wavFileWriter;
        AudioTrack audioTrack;
        TtsPlayer.TtsPayload ttsPayload = this.f;
        if (ttsPayload != null) {
            ttsPayload.interrupt = z;
        }
        this.c.removeMessages(1002);
        this.c.obtainMessage(1002, this.f).sendToTarget();
        if (this.q.compareAndSet(true, false) && (audioTrack = this.p) != null) {
            try {
                audioTrack.setVolume(1.0f);
            } catch (Exception e) {
                L.base.e(e);
            }
        }
        if (SpeechConfig.needToSaveTts() && (wavFileWriter = this.i) != null) {
            wavFileWriter.close();
            this.i = null;
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public boolean isPlaying() {
        AudioTrack audioTrack = this.p;
        return audioTrack != null && audioTrack.getPlayState() == 3;
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void mute() {
        AudioTrack audioTrack = this.p;
        if (audioTrack != null) {
            try {
                audioTrack.setVolume(0.0f);
            } catch (IllegalStateException e) {
                L.speech.e("%s mute.exception=%s", "[TtsPcmPlayer]:", e);
            }
        }
        this.q.set(true);
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void disableFocus() {
        this.r.set(false);
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void enableFocus() {
        this.r.set(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
    public void postStart() {
        a(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
    public void postStop(AudioSource audioSource) {
        if (isPlaying()) {
            e();
            a(true);
            a(g(), true);
        }
    }

    private long g() {
        return this.o / 32;
    }

    private void a(long j, boolean z) {
        TtsPlayer.TtsPayload ttsPayload = this.f;
        if (ttsPayload != null) {
            TrackStat.postTts(ttsPayload.id, this.s, j, z);
        }
    }
}
