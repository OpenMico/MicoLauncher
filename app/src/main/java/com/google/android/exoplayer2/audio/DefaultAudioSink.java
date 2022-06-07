package com.google.android.exoplayer2.audio;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.PlaybackParams;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.AudioTrackPositionTracker;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class DefaultAudioSink implements AudioSink {
    public static final float DEFAULT_PLAYBACK_SPEED = 1.0f;
    public static final float MAX_PITCH = 8.0f;
    public static final float MAX_PLAYBACK_SPEED = 8.0f;
    public static final float MIN_PITCH = 0.1f;
    public static final float MIN_PLAYBACK_SPEED = 0.1f;
    public static final int OFFLOAD_MODE_DISABLED = 0;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_DISABLED = 3;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_NOT_REQUIRED = 2;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_REQUIRED = 1;
    public static boolean failOnSpuriousAudioTimestamp = false;
    private long A;
    private long B;
    private long C;
    private int D;
    private boolean E;
    private boolean F;
    private long G;
    private float H;
    private AudioProcessor[] I;
    private ByteBuffer[] J;
    @Nullable
    private ByteBuffer K;
    private int L;
    @Nullable
    private ByteBuffer M;
    private byte[] N;
    private int O;
    private int P;
    private boolean Q;
    private boolean R;
    private boolean S;
    private boolean T;
    private int U;
    private AuxEffectInfo V;
    private boolean W;
    private long X;
    private boolean Y;
    private boolean Z;
    @Nullable
    private final AudioCapabilities a;
    private final AudioProcessorChain b;
    private final boolean c;
    private final b d;
    private final f e;
    private final AudioProcessor[] f;
    private final AudioProcessor[] g;
    private final ConditionVariable h;
    private final AudioTrackPositionTracker i;
    private final ArrayDeque<b> j;
    private final boolean k;
    private final int l;
    private e m;
    private final c<AudioSink.InitializationException> n;
    private final c<AudioSink.WriteException> o;
    @Nullable
    private AudioSink.Listener p;
    @Nullable
    private a q;
    private a r;
    @Nullable
    private AudioTrack s;
    private AudioAttributes t;
    @Nullable
    private b u;
    private b v;
    private PlaybackParameters w;
    @Nullable
    private ByteBuffer x;
    private int y;
    private long z;

    /* loaded from: classes.dex */
    public interface AudioProcessorChain {
        PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters);

        boolean applySkipSilenceEnabled(boolean z);

        AudioProcessor[] getAudioProcessors();

        long getMediaDuration(long j);

        long getSkippedOutputFrameCount();
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface OffloadMode {
    }

    /* loaded from: classes.dex */
    public static final class InvalidAudioTrackTimestampException extends RuntimeException {
        private InvalidAudioTrackTimestampException(String str) {
            super(str);
        }
    }

    /* loaded from: classes.dex */
    public static class DefaultAudioProcessorChain implements AudioProcessorChain {
        private final AudioProcessor[] a;
        private final SilenceSkippingAudioProcessor b;
        private final SonicAudioProcessor c;

        public DefaultAudioProcessorChain(AudioProcessor... audioProcessorArr) {
            this(audioProcessorArr, new SilenceSkippingAudioProcessor(), new SonicAudioProcessor());
        }

        public DefaultAudioProcessorChain(AudioProcessor[] audioProcessorArr, SilenceSkippingAudioProcessor silenceSkippingAudioProcessor, SonicAudioProcessor sonicAudioProcessor) {
            this.a = new AudioProcessor[audioProcessorArr.length + 2];
            System.arraycopy(audioProcessorArr, 0, this.a, 0, audioProcessorArr.length);
            this.b = silenceSkippingAudioProcessor;
            this.c = sonicAudioProcessor;
            AudioProcessor[] audioProcessorArr2 = this.a;
            audioProcessorArr2[audioProcessorArr.length] = silenceSkippingAudioProcessor;
            audioProcessorArr2[audioProcessorArr.length + 1] = sonicAudioProcessor;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public AudioProcessor[] getAudioProcessors() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters) {
            this.c.setSpeed(playbackParameters.speed);
            this.c.setPitch(playbackParameters.pitch);
            return playbackParameters;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public boolean applySkipSilenceEnabled(boolean z) {
            this.b.setEnabled(z);
            return z;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public long getMediaDuration(long j) {
            return this.c.getMediaDuration(j);
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public long getSkippedOutputFrameCount() {
            return this.b.getSkippedFrames();
        }
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr) {
        this(audioCapabilities, audioProcessorArr, false);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr, boolean z) {
        this(audioCapabilities, new DefaultAudioProcessorChain(audioProcessorArr), z, false, 0);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessorChain audioProcessorChain, boolean z, boolean z2, int i) {
        this.a = audioCapabilities;
        this.b = (AudioProcessorChain) Assertions.checkNotNull(audioProcessorChain);
        this.c = Util.SDK_INT >= 21 && z;
        this.k = Util.SDK_INT >= 23 && z2;
        this.l = Util.SDK_INT < 29 ? 0 : i;
        this.h = new ConditionVariable(true);
        this.i = new AudioTrackPositionTracker(new d());
        this.d = new b();
        this.e = new f();
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, new d(), this.d, this.e);
        Collections.addAll(arrayList, audioProcessorChain.getAudioProcessors());
        this.f = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[0]);
        this.g = new AudioProcessor[]{new c()};
        this.H = 1.0f;
        this.t = AudioAttributes.DEFAULT;
        this.U = 0;
        this.V = new AuxEffectInfo(0, 0.0f);
        this.v = new b(PlaybackParameters.DEFAULT, false, 0L, 0L);
        this.w = PlaybackParameters.DEFAULT;
        this.P = -1;
        this.I = new AudioProcessor[0];
        this.J = new ByteBuffer[0];
        this.j = new ArrayDeque<>();
        this.n = new c<>(100L);
        this.o = new c<>(100L);
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setListener(AudioSink.Listener listener) {
        this.p = listener;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean supportsFormat(Format format) {
        return getFormatSupport(format) != 0;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public int getFormatSupport(Format format) {
        if (!MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
            return ((this.Y || !a(format, this.t)) && !a(format, this.a)) ? 0 : 2;
        }
        if (Util.isEncodingLinearPcm(format.pcmEncoding)) {
            return (format.pcmEncoding == 2 || (this.c && format.pcmEncoding == 4)) ? 2 : 1;
        }
        int i = format.pcmEncoding;
        StringBuilder sb = new StringBuilder(33);
        sb.append("Invalid PCM encoding: ");
        sb.append(i);
        Log.w("DefaultAudioSink", sb.toString());
        return 0;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public long getCurrentPositionUs(boolean z) {
        if (!l() || this.F) {
            return Long.MIN_VALUE;
        }
        return d(c(Math.min(this.i.a(z), this.r.b(n()))));
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void configure(Format format, int i, @Nullable int[] iArr) throws AudioSink.ConfigurationException {
        AudioProcessor[] audioProcessorArr;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        AudioProcessor[] audioProcessorArr2;
        int[] iArr2;
        if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
            Assertions.checkArgument(Util.isEncodingLinearPcm(format.pcmEncoding));
            i7 = Util.getPcmFrameSize(format.pcmEncoding, format.channelCount);
            if (c(format.pcmEncoding)) {
                audioProcessorArr2 = this.g;
            } else {
                audioProcessorArr2 = this.f;
            }
            this.e.a(format.encoderDelay, format.encoderPadding);
            if (Util.SDK_INT < 21 && format.channelCount == 8 && iArr == null) {
                iArr2 = new int[6];
                for (int i8 = 0; i8 < iArr2.length; i8++) {
                    iArr2[i8] = i8;
                }
            } else {
                iArr2 = iArr;
            }
            this.d.a(iArr2);
            AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(format.sampleRate, format.channelCount, format.pcmEncoding);
            for (AudioProcessor audioProcessor : audioProcessorArr2) {
                try {
                    AudioProcessor.AudioFormat configure = audioProcessor.configure(audioFormat);
                    if (audioProcessor.isActive()) {
                        audioFormat = configure;
                    }
                } catch (AudioProcessor.UnhandledAudioFormatException e2) {
                    throw new AudioSink.ConfigurationException(e2, format);
                }
            }
            int i9 = audioFormat.encoding;
            int i10 = audioFormat.sampleRate;
            i3 = Util.getAudioTrackChannelConfig(audioFormat.channelCount);
            i5 = Util.getPcmFrameSize(i9, audioFormat.channelCount);
            audioProcessorArr = audioProcessorArr2;
            i2 = i9;
            i6 = 0;
            i4 = i10;
        } else {
            AudioProcessor[] audioProcessorArr3 = new AudioProcessor[0];
            int i11 = format.sampleRate;
            if (a(format, this.t)) {
                int encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
                i3 = Util.getAudioTrackChannelConfig(format.channelCount);
                i6 = 1;
                audioProcessorArr = audioProcessorArr3;
                i7 = -1;
                i2 = encoding;
                i5 = -1;
                i4 = i11;
            } else {
                i6 = 2;
                Pair<Integer, Integer> b2 = b(format, this.a);
                if (b2 != null) {
                    i2 = ((Integer) b2.first).intValue();
                    i3 = ((Integer) b2.second).intValue();
                    audioProcessorArr = audioProcessorArr3;
                    i7 = -1;
                    i4 = i11;
                    i5 = -1;
                } else {
                    String valueOf = String.valueOf(format);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37);
                    sb.append("Unable to configure passthrough for: ");
                    sb.append(valueOf);
                    throw new AudioSink.ConfigurationException(sb.toString(), format);
                }
            }
        }
        if (i2 == 0) {
            String valueOf2 = String.valueOf(format);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 48);
            sb2.append("Invalid output encoding (mode=");
            sb2.append(i6);
            sb2.append(") for: ");
            sb2.append(valueOf2);
            throw new AudioSink.ConfigurationException(sb2.toString(), format);
        } else if (i3 != 0) {
            this.Y = false;
            a aVar = new a(format, i7, i6, i5, i4, i3, i2, i, this.k, audioProcessorArr);
            if (l()) {
                this.q = aVar;
            } else {
                this.r = aVar;
            }
        } else {
            String valueOf3 = String.valueOf(format);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 54);
            sb3.append("Invalid output channel config (mode=");
            sb3.append(i6);
            sb3.append(") for: ");
            sb3.append(valueOf3);
            throw new AudioSink.ConfigurationException(sb3.toString(), format);
        }
    }

    private void a() {
        AudioProcessor[] audioProcessorArr = this.r.i;
        ArrayList arrayList = new ArrayList();
        for (AudioProcessor audioProcessor : audioProcessorArr) {
            if (audioProcessor.isActive()) {
                arrayList.add(audioProcessor);
            } else {
                audioProcessor.flush();
            }
        }
        int size = arrayList.size();
        this.I = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[size]);
        this.J = new ByteBuffer[size];
        b();
    }

    private void b() {
        int i = 0;
        while (true) {
            AudioProcessor[] audioProcessorArr = this.I;
            if (i < audioProcessorArr.length) {
                AudioProcessor audioProcessor = audioProcessorArr[i];
                audioProcessor.flush();
                this.J[i] = audioProcessor.getOutput();
                i++;
            } else {
                return;
            }
        }
    }

    private void c() throws AudioSink.InitializationException {
        this.h.block();
        this.s = d();
        if (b(this.s)) {
            a(this.s);
            if (this.l != 3) {
                this.s.setOffloadDelayPadding(this.r.a.encoderDelay, this.r.a.encoderPadding);
            }
        }
        this.U = this.s.getAudioSessionId();
        this.i.a(this.s, this.r.c == 2, this.r.g, this.r.d, this.r.h);
        g();
        if (this.V.effectId != 0) {
            this.s.attachAuxEffect(this.V.effectId);
            this.s.setAuxEffectSendLevel(this.V.sendLevel);
        }
        this.F = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void play() {
        this.S = true;
        if (l()) {
            this.i.a();
            this.s.play();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void handleDiscontinuity() {
        this.E = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean handleBuffer(ByteBuffer byteBuffer, long j, int i) throws AudioSink.InitializationException, AudioSink.WriteException {
        ByteBuffer byteBuffer2 = this.K;
        Assertions.checkArgument(byteBuffer2 == null || byteBuffer == byteBuffer2);
        if (this.q != null) {
            if (!f()) {
                return false;
            }
            if (!this.q.a(this.r)) {
                p();
                if (hasPendingData()) {
                    return false;
                }
                flush();
            } else {
                this.r = this.q;
                this.q = null;
                if (b(this.s) && this.l != 3) {
                    this.s.setOffloadEndOfStream();
                    this.s.setOffloadDelayPadding(this.r.a.encoderDelay, this.r.a.encoderPadding);
                    this.Z = true;
                }
            }
            b(j);
        }
        if (!l()) {
            try {
                c();
            } catch (AudioSink.InitializationException e2) {
                if (!e2.isRecoverable) {
                    this.n.a(e2);
                    return false;
                }
                throw e2;
            }
        }
        this.n.a();
        if (this.F) {
            this.G = Math.max(0L, j);
            this.E = false;
            this.F = false;
            if (this.k && Util.SDK_INT >= 23) {
                a(this.w);
            }
            b(j);
            if (this.S) {
                play();
            }
        }
        if (!this.i.a(n())) {
            return false;
        }
        if (this.K == null) {
            Assertions.checkArgument(byteBuffer.order() == ByteOrder.LITTLE_ENDIAN);
            if (!byteBuffer.hasRemaining()) {
                return true;
            }
            if (this.r.c != 0 && this.D == 0) {
                this.D = a(this.r.g, byteBuffer);
                if (this.D == 0) {
                    return true;
                }
            }
            if (this.u != null) {
                if (!f()) {
                    return false;
                }
                b(j);
                this.u = null;
            }
            long a2 = this.G + this.r.a(m() - this.e.b());
            if (!this.E && Math.abs(a2 - j) > 200000) {
                this.p.onAudioSinkError(new AudioSink.UnexpectedDiscontinuityException(j, a2));
                this.E = true;
            }
            if (this.E) {
                if (!f()) {
                    return false;
                }
                long j2 = j - a2;
                this.G += j2;
                this.E = false;
                b(j);
                AudioSink.Listener listener = this.p;
                if (!(listener == null || j2 == 0)) {
                    listener.onPositionDiscontinuity();
                }
            }
            if (this.r.c == 0) {
                this.z += byteBuffer.remaining();
            } else {
                this.A += this.D * i;
            }
            this.K = byteBuffer;
            this.L = i;
        }
        a(j);
        if (!this.K.hasRemaining()) {
            this.K = null;
            this.L = 0;
            return true;
        } else if (!this.i.d(n())) {
            return false;
        } else {
            Log.w("DefaultAudioSink", "Resetting stalled audio track");
            flush();
            return true;
        }
    }

    private AudioTrack d() throws AudioSink.InitializationException {
        try {
            return ((a) Assertions.checkNotNull(this.r)).a(this.W, this.t, this.U);
        } catch (AudioSink.InitializationException e2) {
            e();
            AudioSink.Listener listener = this.p;
            if (listener != null) {
                listener.onAudioSinkError(e2);
            }
            throw e2;
        }
    }

    @RequiresApi(29)
    private void a(AudioTrack audioTrack) {
        if (this.m == null) {
            this.m = new e();
        }
        this.m.a(audioTrack);
    }

    private void a(long j) throws AudioSink.WriteException {
        ByteBuffer byteBuffer;
        int length = this.I.length;
        int i = length;
        while (i >= 0) {
            if (i > 0) {
                byteBuffer = this.J[i - 1];
            } else {
                byteBuffer = this.K;
                if (byteBuffer == null) {
                    byteBuffer = AudioProcessor.EMPTY_BUFFER;
                }
            }
            if (i == length) {
                a(byteBuffer, j);
            } else {
                AudioProcessor audioProcessor = this.I[i];
                if (i > this.P) {
                    audioProcessor.queueInput(byteBuffer);
                }
                ByteBuffer output = audioProcessor.getOutput();
                this.J[i] = output;
                if (output.hasRemaining()) {
                    i++;
                }
            }
            if (!byteBuffer.hasRemaining()) {
                i--;
            } else {
                return;
            }
        }
    }

    private void a(ByteBuffer byteBuffer, long j) throws AudioSink.WriteException {
        int i;
        if (byteBuffer.hasRemaining()) {
            ByteBuffer byteBuffer2 = this.M;
            boolean z = true;
            if (byteBuffer2 != null) {
                Assertions.checkArgument(byteBuffer2 == byteBuffer);
            } else {
                this.M = byteBuffer;
                if (Util.SDK_INT < 21) {
                    int remaining = byteBuffer.remaining();
                    byte[] bArr = this.N;
                    if (bArr == null || bArr.length < remaining) {
                        this.N = new byte[remaining];
                    }
                    int position = byteBuffer.position();
                    byteBuffer.get(this.N, 0, remaining);
                    byteBuffer.position(position);
                    this.O = 0;
                }
            }
            int remaining2 = byteBuffer.remaining();
            if (Util.SDK_INT < 21) {
                int b2 = this.i.b(this.B);
                if (b2 > 0) {
                    i = this.s.write(this.N, this.O, Math.min(remaining2, b2));
                    if (i > 0) {
                        this.O += i;
                        byteBuffer.position(byteBuffer.position() + i);
                    }
                } else {
                    i = 0;
                }
            } else if (this.W) {
                Assertions.checkState(j != C.TIME_UNSET);
                i = a(this.s, byteBuffer, remaining2, j);
            } else {
                i = a(this.s, byteBuffer, remaining2);
            }
            this.X = SystemClock.elapsedRealtime();
            if (i < 0) {
                boolean b3 = b(i);
                if (b3) {
                    e();
                }
                AudioSink.WriteException writeException = new AudioSink.WriteException(i, this.r.a, b3);
                AudioSink.Listener listener = this.p;
                if (listener != null) {
                    listener.onAudioSinkError(writeException);
                }
                if (!writeException.isRecoverable) {
                    this.o.a(writeException);
                    return;
                }
                throw writeException;
            }
            this.o.a();
            if (b(this.s)) {
                if (this.C > 0) {
                    this.Z = false;
                }
                if (this.S && this.p != null && i < remaining2 && !this.Z) {
                    this.p.onOffloadBufferFull(this.i.c(this.C));
                }
            }
            if (this.r.c == 0) {
                this.B += i;
            }
            if (i == remaining2) {
                if (this.r.c != 0) {
                    if (byteBuffer != this.K) {
                        z = false;
                    }
                    Assertions.checkState(z);
                    this.C += this.D * this.L;
                }
                this.M = null;
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void playToEndOfStream() throws AudioSink.WriteException {
        if (!this.Q && l() && f()) {
            p();
            this.Q = true;
        }
    }

    private void e() {
        if (this.r.a()) {
            this.Y = true;
        }
    }

    private static boolean b(int i) {
        return (Util.SDK_INT >= 24 && i == -6) || i == -32;
    }

    private boolean f() throws AudioSink.WriteException {
        boolean z;
        if (this.P == -1) {
            this.P = 0;
            z = true;
        } else {
            z = false;
        }
        while (true) {
            int i = this.P;
            AudioProcessor[] audioProcessorArr = this.I;
            if (i < audioProcessorArr.length) {
                AudioProcessor audioProcessor = audioProcessorArr[i];
                if (z) {
                    audioProcessor.queueEndOfStream();
                }
                a(C.TIME_UNSET);
                if (!audioProcessor.isEnded()) {
                    return false;
                }
                this.P++;
                z = true;
            } else {
                ByteBuffer byteBuffer = this.M;
                if (byteBuffer != null) {
                    a(byteBuffer, C.TIME_UNSET);
                    if (this.M != null) {
                        return false;
                    }
                }
                this.P = -1;
                return true;
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean isEnded() {
        return !l() || (this.Q && !hasPendingData());
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean hasPendingData() {
        return l() && this.i.f(n());
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        PlaybackParameters playbackParameters2 = new PlaybackParameters(Util.constrainValue(playbackParameters.speed, 0.1f, 8.0f), Util.constrainValue(playbackParameters.pitch, 0.1f, 8.0f));
        if (!this.k || Util.SDK_INT < 23) {
            a(playbackParameters2, getSkipSilenceEnabled());
        } else {
            a(playbackParameters2);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public PlaybackParameters getPlaybackParameters() {
        if (this.k) {
            return this.w;
        }
        return i();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setSkipSilenceEnabled(boolean z) {
        a(i(), z);
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean getSkipSilenceEnabled() {
        return j().b;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAudioAttributes(AudioAttributes audioAttributes) {
        if (!this.t.equals(audioAttributes)) {
            this.t = audioAttributes;
            if (!this.W) {
                flush();
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAudioSessionId(int i) {
        if (this.U != i) {
            this.U = i;
            this.T = i != 0;
            flush();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        if (!this.V.equals(auxEffectInfo)) {
            int i = auxEffectInfo.effectId;
            float f = auxEffectInfo.sendLevel;
            if (this.s != null) {
                if (this.V.effectId != i) {
                    this.s.attachAuxEffect(i);
                }
                if (i != 0) {
                    this.s.setAuxEffectSendLevel(f);
                }
            }
            this.V = auxEffectInfo;
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void enableTunnelingV21() {
        Assertions.checkState(Util.SDK_INT >= 21);
        Assertions.checkState(this.T);
        if (!this.W) {
            this.W = true;
            flush();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void disableTunneling() {
        if (this.W) {
            this.W = false;
            flush();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setVolume(float f) {
        if (this.H != f) {
            this.H = f;
            g();
        }
    }

    private void g() {
        if (l()) {
            if (Util.SDK_INT >= 21) {
                a(this.s, this.H);
            } else {
                b(this.s, this.H);
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void pause() {
        this.S = false;
        if (l() && this.i.c()) {
            this.s.pause();
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.exoplayer2.audio.DefaultAudioSink$1] */
    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void flush() {
        if (l()) {
            h();
            if (this.i.b()) {
                this.s.pause();
            }
            if (b(this.s)) {
                ((e) Assertions.checkNotNull(this.m)).b(this.s);
            }
            final AudioTrack audioTrack = this.s;
            this.s = null;
            if (Util.SDK_INT < 21 && !this.T) {
                this.U = 0;
            }
            a aVar = this.q;
            if (aVar != null) {
                this.r = aVar;
                this.q = null;
            }
            this.i.d();
            this.h.close();
            new Thread("ExoPlayer:AudioTrackReleaseThread") { // from class: com.google.android.exoplayer2.audio.DefaultAudioSink.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        audioTrack.flush();
                        audioTrack.release();
                    } finally {
                        DefaultAudioSink.this.h.open();
                    }
                }
            }.start();
        }
        this.o.a();
        this.n.a();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void experimentalFlushWithoutAudioTrackRelease() {
        if (Util.SDK_INT < 25) {
            flush();
            return;
        }
        this.o.a();
        this.n.a();
        if (l()) {
            h();
            if (this.i.b()) {
                this.s.pause();
            }
            this.s.flush();
            this.i.d();
            this.i.a(this.s, this.r.c == 2, this.r.g, this.r.d, this.r.h);
            this.F = true;
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void reset() {
        flush();
        for (AudioProcessor audioProcessor : this.f) {
            audioProcessor.reset();
        }
        for (AudioProcessor audioProcessor2 : this.g) {
            audioProcessor2.reset();
        }
        this.S = false;
        this.Y = false;
    }

    private void h() {
        this.z = 0L;
        this.A = 0L;
        this.B = 0L;
        this.C = 0L;
        this.Z = false;
        this.D = 0;
        this.v = new b(i(), getSkipSilenceEnabled(), 0L, 0L);
        this.G = 0L;
        this.u = null;
        this.j.clear();
        this.K = null;
        this.L = 0;
        this.M = null;
        this.R = false;
        this.Q = false;
        this.P = -1;
        this.x = null;
        this.y = 0;
        this.e.a();
        b();
    }

    @RequiresApi(23)
    private void a(PlaybackParameters playbackParameters) {
        if (l()) {
            try {
                this.s.setPlaybackParams(new PlaybackParams().allowDefaults().setSpeed(playbackParameters.speed).setPitch(playbackParameters.pitch).setAudioFallbackMode(2));
            } catch (IllegalArgumentException e2) {
                Log.w("DefaultAudioSink", "Failed to set playback params", e2);
            }
            playbackParameters = new PlaybackParameters(this.s.getPlaybackParams().getSpeed(), this.s.getPlaybackParams().getPitch());
            this.i.a(playbackParameters.speed);
        }
        this.w = playbackParameters;
    }

    private void a(PlaybackParameters playbackParameters, boolean z) {
        b j = j();
        if (!playbackParameters.equals(j.a) || z != j.b) {
            b bVar = new b(playbackParameters, z, C.TIME_UNSET, C.TIME_UNSET);
            if (l()) {
                this.u = bVar;
            } else {
                this.v = bVar;
            }
        }
    }

    private PlaybackParameters i() {
        return j().a;
    }

    private b j() {
        b bVar = this.u;
        if (bVar != null) {
            return bVar;
        }
        if (!this.j.isEmpty()) {
            return this.j.getLast();
        }
        return this.v;
    }

    private void b(long j) {
        PlaybackParameters playbackParameters;
        if (k()) {
            playbackParameters = this.b.applyPlaybackParameters(i());
        } else {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        boolean applySkipSilenceEnabled = k() ? this.b.applySkipSilenceEnabled(getSkipSilenceEnabled()) : false;
        this.j.add(new b(playbackParameters, applySkipSilenceEnabled, Math.max(0L, j), this.r.b(n())));
        a();
        AudioSink.Listener listener = this.p;
        if (listener != null) {
            listener.onSkipSilenceEnabledChanged(applySkipSilenceEnabled);
        }
    }

    private boolean k() {
        return !this.W && MimeTypes.AUDIO_RAW.equals(this.r.a.sampleMimeType) && !c(this.r.a.pcmEncoding);
    }

    private boolean c(int i) {
        return this.c && Util.isEncodingHighResolutionPcm(i);
    }

    private long c(long j) {
        while (!this.j.isEmpty() && j >= this.j.getFirst().d) {
            this.v = this.j.remove();
        }
        long j2 = j - this.v.d;
        if (this.v.a.equals(PlaybackParameters.DEFAULT)) {
            return this.v.c + j2;
        }
        if (this.j.isEmpty()) {
            return this.v.c + this.b.getMediaDuration(j2);
        }
        b first = this.j.getFirst();
        return first.c - Util.getMediaDurationForPlayoutDuration(first.d - j, this.v.a.speed);
    }

    private long d(long j) {
        return j + this.r.b(this.b.getSkippedOutputFrameCount());
    }

    private boolean l() {
        return this.s != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long m() {
        if (this.r.c == 0) {
            return this.z / this.r.b;
        }
        return this.A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long n() {
        if (this.r.c == 0) {
            return this.B / this.r.d;
        }
        return this.C;
    }

    private static boolean a(Format format, @Nullable AudioCapabilities audioCapabilities) {
        return b(format, audioCapabilities) != null;
    }

    @Nullable
    private static Pair<Integer, Integer> b(Format format, @Nullable AudioCapabilities audioCapabilities) {
        if (audioCapabilities == null) {
            return null;
        }
        int encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
        int i = 6;
        if (!(encoding == 5 || encoding == 6 || encoding == 18 || encoding == 17 || encoding == 7 || encoding == 8 || encoding == 14)) {
            return null;
        }
        if (encoding == 18 && !audioCapabilities.supportsEncoding(18)) {
            encoding = 6;
        } else if (encoding == 8 && !audioCapabilities.supportsEncoding(8)) {
            encoding = 7;
        }
        if (!audioCapabilities.supportsEncoding(encoding)) {
            return null;
        }
        if (encoding != 18) {
            i = format.channelCount;
            if (i > audioCapabilities.getMaxChannelCount()) {
                return null;
            }
        } else if (Util.SDK_INT >= 29 && (i = a(18, format.sampleRate)) == 0) {
            Log.w("DefaultAudioSink", "E-AC3 JOC encoding supported but no channel count supported");
            return null;
        }
        int d2 = d(i);
        if (d2 == 0) {
            return null;
        }
        return Pair.create(Integer.valueOf(encoding), Integer.valueOf(d2));
    }

    @RequiresApi(29)
    private static int a(int i, int i2) {
        AudioAttributes build = new AudioAttributes.Builder().setUsage(1).setContentType(3).build();
        for (int i3 = 8; i3 > 0; i3--) {
            if (AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setEncoding(i).setSampleRate(i2).setChannelMask(Util.getAudioTrackChannelConfig(i3)).build(), build)) {
                return i3;
            }
        }
        return 0;
    }

    private static int d(int i) {
        if (Util.SDK_INT <= 28) {
            if (i == 7) {
                i = 8;
            } else if (i == 3 || i == 4 || i == 5) {
                i = 6;
            }
        }
        if (Util.SDK_INT <= 26 && "fugu".equals(Util.DEVICE) && i == 1) {
            i = 2;
        }
        return Util.getAudioTrackChannelConfig(i);
    }

    private boolean a(Format format, AudioAttributes audioAttributes) {
        int encoding;
        int audioTrackChannelConfig;
        if (Util.SDK_INT < 29 || this.l == 0 || (encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs)) == 0 || (audioTrackChannelConfig = Util.getAudioTrackChannelConfig(format.channelCount)) == 0 || !AudioManager.isOffloadedPlaybackSupported(b(format.sampleRate, audioTrackChannelConfig, encoding), audioAttributes.getAudioAttributesV21())) {
            return false;
        }
        return !(format.encoderDelay != 0 || format.encoderPadding != 0) || !(this.l == 1) || o();
    }

    private static boolean b(AudioTrack audioTrack) {
        return Util.SDK_INT >= 29 && audioTrack.isOffloadedPlayback();
    }

    private static boolean o() {
        return Util.SDK_INT >= 30 && Util.MODEL.startsWith("Pixel");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int e(int i) {
        switch (i) {
            case 5:
                return 80000;
            case 6:
            case 18:
                return Ac3Util.E_AC3_MAX_RATE_BYTES_PER_SECOND;
            case 7:
                return DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND;
            case 8:
                return DtsUtil.DTS_HD_MAX_RATE_BYTES_PER_SECOND;
            case 9:
                return MpegAudioUtil.MAX_RATE_BYTES_PER_SECOND;
            case 10:
                return 100000;
            case 11:
                return 16000;
            case 12:
                return 7000;
            case 13:
            default:
                throw new IllegalArgumentException();
            case 14:
                return Ac3Util.TRUEHD_MAX_RATE_BYTES_PER_SECOND;
            case 15:
                return 8000;
            case 16:
                return AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
            case 17:
                return Ac4Util.MAX_RATE_BYTES_PER_SECOND;
        }
    }

    private static int a(int i, ByteBuffer byteBuffer) {
        switch (i) {
            case 5:
            case 6:
            case 18:
                return Ac3Util.parseAc3SyncframeAudioSampleCount(byteBuffer);
            case 7:
            case 8:
                return DtsUtil.parseDtsAudioSampleCount(byteBuffer);
            case 9:
                int parseMpegAudioFrameSampleCount = MpegAudioUtil.parseMpegAudioFrameSampleCount(Util.getBigEndianInt(byteBuffer, byteBuffer.position()));
                if (parseMpegAudioFrameSampleCount != -1) {
                    return parseMpegAudioFrameSampleCount;
                }
                throw new IllegalArgumentException();
            case 10:
                return 1024;
            case 11:
            case 12:
                return 2048;
            case 13:
            default:
                StringBuilder sb = new StringBuilder(38);
                sb.append("Unexpected audio encoding: ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
            case 14:
                int findTrueHdSyncframeOffset = Ac3Util.findTrueHdSyncframeOffset(byteBuffer);
                if (findTrueHdSyncframeOffset == -1) {
                    return 0;
                }
                return Ac3Util.parseTrueHdSyncframeAudioSampleCount(byteBuffer, findTrueHdSyncframeOffset) * 16;
            case 15:
                return 512;
            case 16:
                return 1024;
            case 17:
                return Ac4Util.parseAc4SyncframeAudioSampleCount(byteBuffer);
        }
    }

    @RequiresApi(21)
    private static int a(AudioTrack audioTrack, ByteBuffer byteBuffer, int i) {
        return audioTrack.write(byteBuffer, i, 1);
    }

    @RequiresApi(21)
    private int a(AudioTrack audioTrack, ByteBuffer byteBuffer, int i, long j) {
        if (Util.SDK_INT >= 26) {
            return audioTrack.write(byteBuffer, i, 1, j * 1000);
        }
        if (this.x == null) {
            this.x = ByteBuffer.allocate(16);
            this.x.order(ByteOrder.BIG_ENDIAN);
            this.x.putInt(1431633921);
        }
        if (this.y == 0) {
            this.x.putInt(4, i);
            this.x.putLong(8, j * 1000);
            this.x.position(0);
            this.y = i;
        }
        int remaining = this.x.remaining();
        if (remaining > 0) {
            int write = audioTrack.write(this.x, remaining, 1);
            if (write < 0) {
                this.y = 0;
                return write;
            } else if (write < remaining) {
                return 0;
            }
        }
        int a2 = a(audioTrack, byteBuffer, i);
        if (a2 < 0) {
            this.y = 0;
            return a2;
        }
        this.y -= a2;
        return a2;
    }

    @RequiresApi(21)
    private static void a(AudioTrack audioTrack, float f) {
        audioTrack.setVolume(f);
    }

    private static void b(AudioTrack audioTrack, float f) {
        audioTrack.setStereoVolume(f, f);
    }

    private void p() {
        if (!this.R) {
            this.R = true;
            this.i.e(n());
            this.s.stop();
            this.y = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public final class e {
        private final Handler b = new Handler();
        private final AudioTrack.StreamEventCallback c;

        public e() {
            this.c = new AudioTrack.StreamEventCallback() { // from class: com.google.android.exoplayer2.audio.DefaultAudioSink.e.1
                @Override // android.media.AudioTrack.StreamEventCallback
                public void onTearDown(@NonNull AudioTrack audioTrack) {
                    Assertions.checkState(audioTrack == DefaultAudioSink.this.s);
                    if (DefaultAudioSink.this.p != null && DefaultAudioSink.this.S) {
                        DefaultAudioSink.this.p.onOffloadBufferEmptying();
                    }
                }
            };
        }

        public void a(AudioTrack audioTrack) {
            final Handler handler = this.b;
            Objects.requireNonNull(handler);
            audioTrack.registerStreamEventCallback(new Executor() { // from class: com.google.android.exoplayer2.audio.-$$Lambda$LfzJt661qZfn2w-6SYHFbD3aMy0
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    handler.post(runnable);
                }
            }, this.c);
        }

        public void b(AudioTrack audioTrack) {
            audioTrack.unregisterStreamEventCallback(this.c);
            this.b.removeCallbacksAndMessages(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        public final PlaybackParameters a;
        public final boolean b;
        public final long c;
        public final long d;

        private b(PlaybackParameters playbackParameters, boolean z, long j, long j2) {
            this.a = playbackParameters;
            this.b = z;
            this.c = j;
            this.d = j2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    public static AudioFormat b(int i, int i2, int i3) {
        return new AudioFormat.Builder().setSampleRate(i).setChannelMask(i2).setEncoding(i3).build();
    }

    /* loaded from: classes.dex */
    private final class d implements AudioTrackPositionTracker.Listener {
        private d() {
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onPositionFramesMismatch(long j, long j2, long j3, long j4) {
            long m = DefaultAudioSink.this.m();
            long n = DefaultAudioSink.this.n();
            StringBuilder sb = new StringBuilder((int) Opcodes.INVOKEVIRTUAL);
            sb.append("Spurious audio timestamp (frame position mismatch): ");
            sb.append(j);
            sb.append(", ");
            sb.append(j2);
            sb.append(", ");
            sb.append(j3);
            sb.append(", ");
            sb.append(j4);
            sb.append(", ");
            sb.append(m);
            sb.append(", ");
            sb.append(n);
            String sb2 = sb.toString();
            if (!DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                Log.w("DefaultAudioSink", sb2);
                return;
            }
            throw new InvalidAudioTrackTimestampException(sb2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onSystemTimeUsMismatch(long j, long j2, long j3, long j4) {
            long m = DefaultAudioSink.this.m();
            long n = DefaultAudioSink.this.n();
            StringBuilder sb = new StringBuilder((int) Opcodes.GETFIELD);
            sb.append("Spurious audio timestamp (system clock mismatch): ");
            sb.append(j);
            sb.append(", ");
            sb.append(j2);
            sb.append(", ");
            sb.append(j3);
            sb.append(", ");
            sb.append(j4);
            sb.append(", ");
            sb.append(m);
            sb.append(", ");
            sb.append(n);
            String sb2 = sb.toString();
            if (!DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                Log.w("DefaultAudioSink", sb2);
                return;
            }
            throw new InvalidAudioTrackTimestampException(sb2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onInvalidLatency(long j) {
            StringBuilder sb = new StringBuilder(61);
            sb.append("Ignoring impossibly large audio latency: ");
            sb.append(j);
            Log.w("DefaultAudioSink", sb.toString());
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onPositionAdvancing(long j) {
            if (DefaultAudioSink.this.p != null) {
                DefaultAudioSink.this.p.onPositionAdvancing(j);
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onUnderrun(int i, long j) {
            if (DefaultAudioSink.this.p != null) {
                DefaultAudioSink.this.p.onUnderrun(i, j, SystemClock.elapsedRealtime() - DefaultAudioSink.this.X);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        public final Format a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;
        public final int h;
        public final AudioProcessor[] i;

        public a(Format format, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, AudioProcessor[] audioProcessorArr) {
            this.a = format;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
            this.g = i6;
            this.i = audioProcessorArr;
            this.h = a(i7, z);
        }

        public boolean a(a aVar) {
            return aVar.c == this.c && aVar.g == this.g && aVar.e == this.e && aVar.f == this.f && aVar.d == this.d;
        }

        public long a(long j) {
            return (j * 1000000) / this.a.sampleRate;
        }

        public long b(long j) {
            return (j * 1000000) / this.e;
        }

        public long c(long j) {
            return (j * this.e) / 1000000;
        }

        public AudioTrack a(boolean z, AudioAttributes audioAttributes, int i) throws AudioSink.InitializationException {
            try {
                AudioTrack b = b(z, audioAttributes, i);
                int state = b.getState();
                if (state == 1) {
                    return b;
                }
                try {
                    b.release();
                } catch (Exception unused) {
                }
                throw new AudioSink.InitializationException(state, this.e, this.f, this.h, this.a, a(), null);
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                throw new AudioSink.InitializationException(0, this.e, this.f, this.h, this.a, a(), e);
            }
        }

        private AudioTrack b(boolean z, AudioAttributes audioAttributes, int i) {
            if (Util.SDK_INT >= 29) {
                return c(z, audioAttributes, i);
            }
            if (Util.SDK_INT >= 21) {
                return d(z, audioAttributes, i);
            }
            return a(audioAttributes, i);
        }

        @RequiresApi(29)
        private AudioTrack c(boolean z, AudioAttributes audioAttributes, int i) {
            AudioFormat b = DefaultAudioSink.b(this.e, this.f, this.g);
            AudioAttributes a = a(audioAttributes, z);
            boolean z2 = true;
            AudioTrack.Builder sessionId = new AudioTrack.Builder().setAudioAttributes(a).setAudioFormat(b).setTransferMode(1).setBufferSizeInBytes(this.h).setSessionId(i);
            if (this.c != 1) {
                z2 = false;
            }
            return sessionId.setOffloadedPlayback(z2).build();
        }

        @RequiresApi(21)
        private AudioTrack d(boolean z, AudioAttributes audioAttributes, int i) {
            return new AudioTrack(a(audioAttributes, z), DefaultAudioSink.b(this.e, this.f, this.g), this.h, 1, i);
        }

        private AudioTrack a(AudioAttributes audioAttributes, int i) {
            int streamTypeForAudioUsage = Util.getStreamTypeForAudioUsage(audioAttributes.usage);
            if (i == 0) {
                return new AudioTrack(streamTypeForAudioUsage, this.e, this.f, this.g, this.h, 1);
            }
            return new AudioTrack(streamTypeForAudioUsage, this.e, this.f, this.g, this.h, 1, i);
        }

        private int a(int i, boolean z) {
            if (i != 0) {
                return i;
            }
            switch (this.c) {
                case 0:
                    return a(z ? 8.0f : 1.0f);
                case 1:
                    return d(50000000L);
                case 2:
                    return d(250000L);
                default:
                    throw new IllegalStateException();
            }
        }

        private int d(long j) {
            int e = DefaultAudioSink.e(this.g);
            if (this.g == 5) {
                e *= 2;
            }
            return (int) ((j * e) / 1000000);
        }

        private int a(float f) {
            int minBufferSize = AudioTrack.getMinBufferSize(this.e, this.f, this.g);
            Assertions.checkState(minBufferSize != -2);
            int constrainValue = Util.constrainValue(minBufferSize * 4, ((int) c(250000L)) * this.d, Math.max(minBufferSize, ((int) c(750000L)) * this.d));
            return f != 1.0f ? Math.round(constrainValue * f) : constrainValue;
        }

        @RequiresApi(21)
        private static AudioAttributes a(AudioAttributes audioAttributes, boolean z) {
            if (z) {
                return b();
            }
            return audioAttributes.getAudioAttributesV21();
        }

        @RequiresApi(21)
        private static AudioAttributes b() {
            return new AudioAttributes.Builder().setContentType(3).setFlags(16).setUsage(1).build();
        }

        public boolean a() {
            return this.c == 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class c<T extends Exception> {
        private final long a;
        @Nullable
        private T b;
        private long c;

        public c(long j) {
            this.a = j;
        }

        public void a(T t) throws Exception {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.b == null) {
                this.b = t;
                this.c = this.a + elapsedRealtime;
            }
            if (elapsedRealtime >= this.c) {
                T t2 = this.b;
                if (t2 != t) {
                    t2.addSuppressed(t);
                }
                T t3 = this.b;
                a();
                throw t3;
            }
        }

        public void a() {
            this.b = null;
        }
    }
}
