package com.google.android.exoplayer2.audio;

import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.decoder.Decoder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.decoder.SimpleOutputBuffer;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public abstract class DecoderAudioRenderer<T extends Decoder<DecoderInputBuffer, ? extends SimpleOutputBuffer, ? extends DecoderException>> extends BaseRenderer implements MediaClock {
    private final AudioRendererEventListener.EventDispatcher a;
    private final AudioSink b;
    private final DecoderInputBuffer c;
    private DecoderCounters d;
    private Format e;
    private int f;
    private int g;
    private boolean h;
    @Nullable
    private T i;
    @Nullable
    private DecoderInputBuffer j;
    @Nullable
    private SimpleOutputBuffer k;
    @Nullable
    private DrmSession l;
    @Nullable
    private DrmSession m;
    private int n;
    private boolean o;
    private boolean p;
    private long q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;

    protected abstract T createDecoder(Format format, @Nullable ExoMediaCrypto exoMediaCrypto) throws DecoderException;

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    @Nullable
    public MediaClock getMediaClock() {
        return this;
    }

    protected abstract Format getOutputFormat(T t);

    protected abstract int supportsFormatInternal(Format format);

    public DecoderAudioRenderer() {
        this((Handler) null, (AudioRendererEventListener) null, new AudioProcessor[0]);
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, null, audioProcessorArr);
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, @Nullable AudioCapabilities audioCapabilities, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, new DefaultAudioSink(audioCapabilities, audioProcessorArr));
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        super(1);
        this.a = new AudioRendererEventListener.EventDispatcher(handler, audioRendererEventListener);
        this.b = audioSink;
        audioSink.setListener(new a());
        this.c = DecoderInputBuffer.newNoDataInstance();
        this.n = 0;
        this.p = true;
    }

    public void experimentalSetEnableKeepAudioTrackOnSeek(boolean z) {
        this.h = z;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public final int supportsFormat(Format format) {
        int i = 0;
        if (!MimeTypes.isAudio(format.sampleMimeType)) {
            return RendererCapabilities.create(0);
        }
        int supportsFormatInternal = supportsFormatInternal(format);
        if (supportsFormatInternal <= 2) {
            return RendererCapabilities.create(supportsFormatInternal);
        }
        if (Util.SDK_INT >= 21) {
            i = 32;
        }
        return RendererCapabilities.create(supportsFormatInternal, 8, i);
    }

    protected final boolean sinkSupportsFormat(Format format) {
        return this.b.supportsFormat(format);
    }

    protected final int getSinkFormatSupport(Format format) {
        return this.b.getFormatSupport(format);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) throws ExoPlaybackException {
        if (this.u) {
            try {
                this.b.playToEndOfStream();
            } catch (AudioSink.WriteException e) {
                throw createRendererException(e, e.format, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
            }
        } else {
            if (this.e == null) {
                FormatHolder formatHolder = getFormatHolder();
                this.c.clear();
                int readSource = readSource(formatHolder, this.c, 2);
                if (readSource == -5) {
                    a(formatHolder);
                } else if (readSource == -4) {
                    Assertions.checkState(this.c.isEndOfStream());
                    this.t = true;
                    try {
                        c();
                        return;
                    } catch (AudioSink.WriteException e2) {
                        throw createRendererException(e2, null, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
                    }
                } else {
                    return;
                }
            }
            e();
            if (this.i != null) {
                try {
                    TraceUtil.beginSection("drainAndFeed");
                    while (a()) {
                    }
                    while (b()) {
                    }
                    TraceUtil.endSection();
                    this.d.ensureUpdated();
                } catch (AudioSink.ConfigurationException e3) {
                    throw createRendererException(e3, e3.format, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
                } catch (AudioSink.InitializationException e4) {
                    throw createRendererException(e4, e4.format, e4.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED);
                } catch (AudioSink.WriteException e5) {
                    throw createRendererException(e5, e5.format, e5.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
                } catch (DecoderException e6) {
                    Log.e("DecoderAudioRenderer", "Audio codec error", e6);
                    this.a.audioCodecError(e6);
                    throw createRendererException(e6, this.e, 4003);
                }
            }
        }
    }

    @CallSuper
    protected void onPositionDiscontinuity() {
        this.s = true;
    }

    protected DecoderReuseEvaluation canReuseDecoder(String str, Format format, Format format2) {
        return new DecoderReuseEvaluation(str, format, format2, 0, 1);
    }

    private boolean a() throws ExoPlaybackException, DecoderException, AudioSink.ConfigurationException, AudioSink.InitializationException, AudioSink.WriteException {
        if (this.k == null) {
            this.k = (SimpleOutputBuffer) this.i.dequeueOutputBuffer();
            SimpleOutputBuffer simpleOutputBuffer = this.k;
            if (simpleOutputBuffer == null) {
                return false;
            }
            if (simpleOutputBuffer.skippedOutputBufferCount > 0) {
                this.d.skippedOutputBufferCount += this.k.skippedOutputBufferCount;
                this.b.handleDiscontinuity();
            }
        }
        if (this.k.isEndOfStream()) {
            if (this.n == 2) {
                f();
                e();
                this.p = true;
            } else {
                this.k.release();
                this.k = null;
                try {
                    c();
                } catch (AudioSink.WriteException e) {
                    throw createRendererException(e, e.format, e.isRecoverable, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED);
                }
            }
            return false;
        }
        if (this.p) {
            this.b.configure(getOutputFormat(this.i).buildUpon().setEncoderDelay(this.f).setEncoderPadding(this.g).build(), 0, null);
            this.p = false;
        }
        if (!this.b.handleBuffer(this.k.data, this.k.timeUs, 1)) {
            return false;
        }
        this.d.renderedOutputBufferCount++;
        this.k.release();
        this.k = null;
        return true;
    }

    private boolean b() throws DecoderException, ExoPlaybackException {
        T t = this.i;
        if (t == null || this.n == 2 || this.t) {
            return false;
        }
        if (this.j == null) {
            this.j = (DecoderInputBuffer) t.dequeueInputBuffer();
            if (this.j == null) {
                return false;
            }
        }
        if (this.n == 1) {
            this.j.setFlags(4);
            this.i.queueInputBuffer(this.j);
            this.j = null;
            this.n = 2;
            return false;
        }
        FormatHolder formatHolder = getFormatHolder();
        switch (readSource(formatHolder, this.j, 0)) {
            case -5:
                a(formatHolder);
                return true;
            case -4:
                if (this.j.isEndOfStream()) {
                    this.t = true;
                    this.i.queueInputBuffer(this.j);
                    this.j = null;
                    return false;
                }
                this.j.flip();
                onQueueInputBuffer(this.j);
                this.i.queueInputBuffer(this.j);
                this.o = true;
                this.d.inputBufferCount++;
                this.j = null;
                return true;
            case -3:
                return false;
            default:
                throw new IllegalStateException();
        }
    }

    private void c() throws AudioSink.WriteException {
        this.u = true;
        this.b.playToEndOfStream();
    }

    private void d() throws ExoPlaybackException {
        if (this.n != 0) {
            f();
            e();
            return;
        }
        this.j = null;
        SimpleOutputBuffer simpleOutputBuffer = this.k;
        if (simpleOutputBuffer != null) {
            simpleOutputBuffer.release();
            this.k = null;
        }
        this.i.flush();
        this.o = false;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.u && this.b.isEnded();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return this.b.hasPendingData() || (this.e != null && (isSourceReady() || this.k != null));
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        if (getState() == 2) {
            g();
        }
        return this.q;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.b.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return this.b.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
        this.d = new DecoderCounters();
        this.a.enabled(this.d);
        if (getConfiguration().tunneling) {
            this.b.enableTunnelingV21();
        } else {
            this.b.disableTunneling();
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        if (this.h) {
            this.b.experimentalFlushWithoutAudioTrackRelease();
        } else {
            this.b.flush();
        }
        this.q = j;
        this.r = true;
        this.s = true;
        this.t = false;
        this.u = false;
        if (this.i != null) {
            d();
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStarted() {
        this.b.play();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStopped() {
        g();
        this.b.pause();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        this.e = null;
        this.p = true;
        try {
            a((DrmSession) null);
            f();
            this.b.reset();
        } finally {
            this.a.disabled(this.d);
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        switch (i) {
            case 2:
                this.b.setVolume(((Float) obj).floatValue());
                return;
            case 3:
                this.b.setAudioAttributes((AudioAttributes) obj);
                return;
            case 5:
                this.b.setAuxEffectInfo((AuxEffectInfo) obj);
                return;
            case 101:
                this.b.setSkipSilenceEnabled(((Boolean) obj).booleanValue());
                return;
            case 102:
                this.b.setAudioSessionId(((Integer) obj).intValue());
                return;
            default:
                super.handleMessage(i, obj);
                return;
        }
    }

    private void e() throws ExoPlaybackException {
        if (this.i == null) {
            b(this.m);
            ExoMediaCrypto exoMediaCrypto = null;
            DrmSession drmSession = this.l;
            if (drmSession == null || (exoMediaCrypto = drmSession.getMediaCrypto()) != null || this.l.getError() != null) {
                try {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    TraceUtil.beginSection("createAudioDecoder");
                    this.i = createDecoder(this.e, exoMediaCrypto);
                    TraceUtil.endSection();
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    this.a.decoderInitialized(this.i.getName(), elapsedRealtime2, elapsedRealtime2 - elapsedRealtime);
                    this.d.decoderInitCount++;
                } catch (DecoderException e) {
                    Log.e("DecoderAudioRenderer", "Audio codec error", e);
                    this.a.audioCodecError(e);
                    throw createRendererException(e, this.e, PlaybackException.ERROR_CODE_DECODER_INIT_FAILED);
                } catch (OutOfMemoryError e2) {
                    throw createRendererException(e2, this.e, PlaybackException.ERROR_CODE_DECODER_INIT_FAILED);
                }
            }
        }
    }

    private void f() {
        this.j = null;
        this.k = null;
        this.n = 0;
        this.o = false;
        if (this.i != null) {
            this.d.decoderReleaseCount++;
            this.i.release();
            this.a.decoderReleased(this.i.getName());
            this.i = null;
        }
        b(null);
    }

    private void a(@Nullable DrmSession drmSession) {
        DrmSession.replaceSession(this.m, drmSession);
        this.m = drmSession;
    }

    private void b(@Nullable DrmSession drmSession) {
        DrmSession.replaceSession(this.l, drmSession);
        this.l = drmSession;
    }

    private void a(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluation;
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        a(formatHolder.drmSession);
        Format format2 = this.e;
        this.e = format;
        this.f = format.encoderDelay;
        this.g = format.encoderPadding;
        T t = this.i;
        if (t == null) {
            e();
            this.a.inputFormatChanged(this.e, null);
            return;
        }
        if (this.m != this.l) {
            decoderReuseEvaluation = new DecoderReuseEvaluation(t.getName(), format2, format, 0, 128);
        } else {
            decoderReuseEvaluation = canReuseDecoder(t.getName(), format2, format);
        }
        if (decoderReuseEvaluation.result == 0) {
            if (this.o) {
                this.n = 1;
            } else {
                f();
                e();
                this.p = true;
            }
        }
        this.a.inputFormatChanged(this.e, decoderReuseEvaluation);
    }

    protected void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        if (this.r && !decoderInputBuffer.isDecodeOnly()) {
            if (Math.abs(decoderInputBuffer.timeUs - this.q) > 500000) {
                this.q = decoderInputBuffer.timeUs;
            }
            this.r = false;
        }
    }

    private void g() {
        long currentPositionUs = this.b.getCurrentPositionUs(isEnded());
        if (currentPositionUs != Long.MIN_VALUE) {
            if (!this.s) {
                currentPositionUs = Math.max(this.q, currentPositionUs);
            }
            this.q = currentPositionUs;
            this.s = false;
        }
    }

    /* loaded from: classes.dex */
    private final class a implements AudioSink.Listener {
        private a() {
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionDiscontinuity() {
            DecoderAudioRenderer.this.onPositionDiscontinuity();
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onPositionAdvancing(long j) {
            DecoderAudioRenderer.this.a.positionAdvancing(j);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onUnderrun(int i, long j, long j2) {
            DecoderAudioRenderer.this.a.underrun(i, j, j2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onSkipSilenceEnabledChanged(boolean z) {
            DecoderAudioRenderer.this.a.skipSilenceEnabledChanged(z);
        }

        @Override // com.google.android.exoplayer2.audio.AudioSink.Listener
        public void onAudioSinkError(Exception exc) {
            Log.e("DecoderAudioRenderer", "Audio sink error", exc);
            DecoderAudioRenderer.this.a.audioSinkError(exc);
        }
    }
}
