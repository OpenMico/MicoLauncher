package com.google.android.exoplayer2.video;

import android.os.Handler;
import android.os.SystemClock;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.decoder.Decoder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* loaded from: classes2.dex */
public abstract class DecoderVideoRenderer extends BaseRenderer {
    private boolean A;
    @Nullable
    private VideoSize B;
    private long C;
    private int D;
    private int E;
    private int F;
    private long G;
    private long H;
    private final long a;
    private final int b;
    private final VideoRendererEventListener.EventDispatcher c;
    protected DecoderCounters decoderCounters;
    private Format f;
    private Format g;
    @Nullable
    private Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> h;
    private VideoDecoderInputBuffer i;
    private VideoDecoderOutputBuffer j;
    @Nullable
    private Object l;
    @Nullable
    private Surface m;
    @Nullable
    private VideoDecoderOutputBufferRenderer n;
    @Nullable
    private VideoFrameMetadataListener o;
    @Nullable
    private DrmSession p;
    @Nullable
    private DrmSession q;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private long w;
    private boolean y;
    private boolean z;
    private long x = C.TIME_UNSET;
    private final TimedValueQueue<Format> d = new TimedValueQueue<>();
    private final DecoderInputBuffer e = DecoderInputBuffer.newNoDataInstance();
    private int r = 0;
    private int k = -1;

    private static boolean a(long j) {
        return j < -30000;
    }

    private static boolean b(long j) {
        return j < -500000;
    }

    protected abstract Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> createDecoder(Format format, @Nullable ExoMediaCrypto exoMediaCrypto) throws DecoderException;

    protected void onQueueInputBuffer(VideoDecoderInputBuffer videoDecoderInputBuffer) {
    }

    protected abstract void renderOutputBufferToSurface(VideoDecoderOutputBuffer videoDecoderOutputBuffer, Surface surface) throws DecoderException;

    protected abstract void setDecoderOutputMode(int i);

    protected DecoderVideoRenderer(long j, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        super(2);
        this.a = j;
        this.b = i;
        k();
        this.c = new VideoRendererEventListener.EventDispatcher(handler, videoRendererEventListener);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) throws ExoPlaybackException {
        if (!this.A) {
            if (this.f == null) {
                FormatHolder formatHolder = getFormatHolder();
                this.e.clear();
                int readSource = readSource(formatHolder, this.e, 2);
                if (readSource == -5) {
                    onInputFormatChanged(formatHolder);
                } else if (readSource == -4) {
                    Assertions.checkState(this.e.isEndOfStream());
                    this.z = true;
                    this.A = true;
                    return;
                } else {
                    return;
                }
            }
            a();
            if (this.h != null) {
                try {
                    TraceUtil.beginSection("drainAndFeed");
                    while (a(j, j2)) {
                    }
                    while (b()) {
                    }
                    TraceUtil.endSection();
                    this.decoderCounters.ensureUpdated();
                } catch (DecoderException e) {
                    Log.e("DecoderVideoRenderer", "Video codec error", e);
                    this.c.videoCodecError(e);
                    throw createRendererException(e, this.f, 4003);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.A;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        if (this.f != null && ((isSourceReady() || this.j != null) && (this.t || !c()))) {
            this.x = C.TIME_UNSET;
            return true;
        } else if (this.x == C.TIME_UNSET) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() < this.x) {
                return true;
            }
            this.x = C.TIME_UNSET;
            return false;
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 1) {
            setOutput(obj);
        } else if (i == 6) {
            this.o = (VideoFrameMetadataListener) obj;
        } else {
            super.handleMessage(i, obj);
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
        this.decoderCounters = new DecoderCounters();
        this.c.enabled(this.decoderCounters);
        this.u = z2;
        this.v = false;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        this.z = false;
        this.A = false;
        h();
        this.w = C.TIME_UNSET;
        this.E = 0;
        if (this.h != null) {
            flushDecoder();
        }
        if (z) {
            g();
        } else {
            this.x = C.TIME_UNSET;
        }
        this.d.clear();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStarted() {
        this.D = 0;
        this.C = SystemClock.elapsedRealtime();
        this.G = SystemClock.elapsedRealtime() * 1000;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStopped() {
        this.x = C.TIME_UNSET;
        m();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        this.f = null;
        k();
        h();
        try {
            a((DrmSession) null);
            releaseDecoder();
        } finally {
            this.c.disabled(this.decoderCounters);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j, long j2) throws ExoPlaybackException {
        this.H = j2;
        super.onStreamChanged(formatArr, j, j2);
    }

    @CallSuper
    protected void flushDecoder() throws ExoPlaybackException {
        this.F = 0;
        if (this.r != 0) {
            releaseDecoder();
            a();
            return;
        }
        this.i = null;
        VideoDecoderOutputBuffer videoDecoderOutputBuffer = this.j;
        if (videoDecoderOutputBuffer != null) {
            videoDecoderOutputBuffer.release();
            this.j = null;
        }
        this.h.flush();
        this.s = false;
    }

    @CallSuper
    protected void releaseDecoder() {
        this.i = null;
        this.j = null;
        this.r = 0;
        this.s = false;
        this.F = 0;
        if (this.h != null) {
            this.decoderCounters.decoderReleaseCount++;
            this.h.release();
            this.c.decoderReleased(this.h.getName());
            this.h = null;
        }
        b((DrmSession) null);
    }

    @CallSuper
    protected void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluation;
        this.y = true;
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        a(formatHolder.drmSession);
        Format format2 = this.f;
        this.f = format;
        Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.h;
        if (decoder == null) {
            a();
            this.c.inputFormatChanged(this.f, null);
            return;
        }
        if (this.q != this.p) {
            decoderReuseEvaluation = new DecoderReuseEvaluation(decoder.getName(), format2, format, 0, 128);
        } else {
            decoderReuseEvaluation = canReuseDecoder(decoder.getName(), format2, format);
        }
        if (decoderReuseEvaluation.result == 0) {
            if (this.s) {
                this.r = 1;
            } else {
                releaseDecoder();
                a();
            }
        }
        this.c.inputFormatChanged(this.f, decoderReuseEvaluation);
    }

    @CallSuper
    protected void onProcessedOutputBuffer(long j) {
        this.F--;
    }

    protected boolean shouldDropOutputBuffer(long j, long j2) {
        return a(j);
    }

    protected boolean shouldDropBuffersToKeyframe(long j, long j2) {
        return b(j);
    }

    protected boolean shouldForceRenderOutputBuffer(long j, long j2) {
        return a(j) && j2 > 100000;
    }

    protected void skipOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        this.decoderCounters.skippedOutputBufferCount++;
        videoDecoderOutputBuffer.release();
    }

    protected void dropOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        updateDroppedBufferCounters(1);
        videoDecoderOutputBuffer.release();
    }

    protected boolean maybeDropBuffersToKeyframe(long j) throws ExoPlaybackException {
        int skipSource = skipSource(j);
        if (skipSource == 0) {
            return false;
        }
        this.decoderCounters.droppedToKeyframeCount++;
        updateDroppedBufferCounters(this.F + skipSource);
        flushDecoder();
        return true;
    }

    protected void updateDroppedBufferCounters(int i) {
        this.decoderCounters.droppedBufferCount += i;
        this.D += i;
        this.E += i;
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(this.E, decoderCounters.maxConsecutiveDroppedBufferCount);
        int i2 = this.b;
        if (i2 > 0 && this.D >= i2) {
            m();
        }
    }

    protected void renderOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer, long j, Format format) throws DecoderException {
        VideoFrameMetadataListener videoFrameMetadataListener = this.o;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j, System.nanoTime(), format, null);
        }
        this.G = C.msToUs(SystemClock.elapsedRealtime() * 1000);
        int i = videoDecoderOutputBuffer.mode;
        boolean z = i == 1 && this.m != null;
        boolean z2 = i == 0 && this.n != null;
        if (z2 || z) {
            a(videoDecoderOutputBuffer.width, videoDecoderOutputBuffer.height);
            if (z2) {
                this.n.setOutputBuffer(videoDecoderOutputBuffer);
            } else {
                renderOutputBufferToSurface(videoDecoderOutputBuffer, this.m);
            }
            this.E = 0;
            this.decoderCounters.renderedOutputBufferCount++;
            i();
            return;
        }
        dropOutputBuffer(videoDecoderOutputBuffer);
    }

    protected final void setOutput(@Nullable Object obj) {
        if (obj instanceof Surface) {
            this.m = (Surface) obj;
            this.n = null;
            this.k = 1;
        } else if (obj instanceof VideoDecoderOutputBufferRenderer) {
            this.m = null;
            this.n = (VideoDecoderOutputBufferRenderer) obj;
            this.k = 0;
        } else {
            this.m = null;
            this.n = null;
            this.k = -1;
            obj = null;
        }
        if (this.l != obj) {
            this.l = obj;
            if (obj != null) {
                if (this.h != null) {
                    setDecoderOutputMode(this.k);
                }
                d();
                return;
            }
            e();
        } else if (obj != null) {
            f();
        }
    }

    protected DecoderReuseEvaluation canReuseDecoder(String str, Format format, Format format2) {
        return new DecoderReuseEvaluation(str, format, format2, 0, 1);
    }

    private void a(@Nullable DrmSession drmSession) {
        DrmSession.replaceSession(this.q, drmSession);
        this.q = drmSession;
    }

    private void b(@Nullable DrmSession drmSession) {
        DrmSession.replaceSession(this.p, drmSession);
        this.p = drmSession;
    }

    private void a() throws ExoPlaybackException {
        if (this.h == null) {
            b(this.q);
            ExoMediaCrypto exoMediaCrypto = null;
            DrmSession drmSession = this.p;
            if (drmSession == null || (exoMediaCrypto = drmSession.getMediaCrypto()) != null || this.p.getError() != null) {
                try {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    this.h = createDecoder(this.f, exoMediaCrypto);
                    setDecoderOutputMode(this.k);
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    this.c.decoderInitialized(this.h.getName(), elapsedRealtime2, elapsedRealtime2 - elapsedRealtime);
                    this.decoderCounters.decoderInitCount++;
                } catch (DecoderException e) {
                    Log.e("DecoderVideoRenderer", "Video codec error", e);
                    this.c.videoCodecError(e);
                    throw createRendererException(e, this.f, PlaybackException.ERROR_CODE_DECODER_INIT_FAILED);
                } catch (OutOfMemoryError e2) {
                    throw createRendererException(e2, this.f, PlaybackException.ERROR_CODE_DECODER_INIT_FAILED);
                }
            }
        }
    }

    private boolean b() throws DecoderException, ExoPlaybackException {
        Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.h;
        if (decoder == null || this.r == 2 || this.z) {
            return false;
        }
        if (this.i == null) {
            this.i = decoder.dequeueInputBuffer();
            if (this.i == null) {
                return false;
            }
        }
        if (this.r == 1) {
            this.i.setFlags(4);
            this.h.queueInputBuffer(this.i);
            this.i = null;
            this.r = 2;
            return false;
        }
        FormatHolder formatHolder = getFormatHolder();
        switch (readSource(formatHolder, this.i, 0)) {
            case -5:
                onInputFormatChanged(formatHolder);
                return true;
            case -4:
                if (this.i.isEndOfStream()) {
                    this.z = true;
                    this.h.queueInputBuffer(this.i);
                    this.i = null;
                    return false;
                }
                if (this.y) {
                    this.d.add(this.i.timeUs, this.f);
                    this.y = false;
                }
                this.i.flip();
                VideoDecoderInputBuffer videoDecoderInputBuffer = this.i;
                videoDecoderInputBuffer.format = this.f;
                onQueueInputBuffer(videoDecoderInputBuffer);
                this.h.queueInputBuffer(this.i);
                this.F++;
                this.s = true;
                this.decoderCounters.inputBufferCount++;
                this.i = null;
                return true;
            case -3:
                return false;
            default:
                throw new IllegalStateException();
        }
    }

    private boolean a(long j, long j2) throws ExoPlaybackException, DecoderException {
        if (this.j == null) {
            this.j = (VideoDecoderOutputBuffer) this.h.dequeueOutputBuffer();
            if (this.j == null) {
                return false;
            }
            this.decoderCounters.skippedOutputBufferCount += this.j.skippedOutputBufferCount;
            this.F -= this.j.skippedOutputBufferCount;
        }
        if (this.j.isEndOfStream()) {
            if (this.r == 2) {
                releaseDecoder();
                a();
            } else {
                this.j.release();
                this.j = null;
                this.A = true;
            }
            return false;
        }
        boolean b = b(j, j2);
        if (b) {
            onProcessedOutputBuffer(this.j.timeUs);
            this.j = null;
        }
        return b;
    }

    private boolean b(long j, long j2) throws ExoPlaybackException, DecoderException {
        boolean z;
        if (this.w == C.TIME_UNSET) {
            this.w = j;
        }
        long j3 = this.j.timeUs - j;
        if (c()) {
            long j4 = this.j.timeUs - this.H;
            Format pollFloor = this.d.pollFloor(j4);
            if (pollFloor != null) {
                this.g = pollFloor;
            }
            long elapsedRealtime = (SystemClock.elapsedRealtime() * 1000) - this.G;
            boolean z2 = getState() == 2;
            if (!this.v) {
                z = z2 || this.u;
            } else {
                z = !this.t;
            }
            if (z || (z2 && shouldForceRenderOutputBuffer(j3, elapsedRealtime))) {
                renderOutputBuffer(this.j, j4, this.g);
                return true;
            } else if (!z2 || j == this.w) {
                return false;
            } else {
                if (shouldDropBuffersToKeyframe(j3, j2) && maybeDropBuffersToKeyframe(j)) {
                    return false;
                }
                if (shouldDropOutputBuffer(j3, j2)) {
                    dropOutputBuffer(this.j);
                    return true;
                } else if (j3 >= 30000) {
                    return false;
                } else {
                    renderOutputBuffer(this.j, j4, this.g);
                    return true;
                }
            }
        } else if (!a(j3)) {
            return false;
        } else {
            skipOutputBuffer(this.j);
            return true;
        }
    }

    private boolean c() {
        return this.k != -1;
    }

    private void d() {
        l();
        h();
        if (getState() == 2) {
            g();
        }
    }

    private void e() {
        k();
        h();
    }

    private void f() {
        l();
        j();
    }

    private void g() {
        this.x = this.a > 0 ? SystemClock.elapsedRealtime() + this.a : C.TIME_UNSET;
    }

    private void h() {
        this.t = false;
    }

    private void i() {
        this.v = true;
        if (!this.t) {
            this.t = true;
            this.c.renderedFirstFrame(this.l);
        }
    }

    private void j() {
        if (this.t) {
            this.c.renderedFirstFrame(this.l);
        }
    }

    private void k() {
        this.B = null;
    }

    private void a(int i, int i2) {
        VideoSize videoSize = this.B;
        if (videoSize == null || videoSize.width != i || this.B.height != i2) {
            this.B = new VideoSize(i, i2);
            this.c.videoSizeChanged(this.B);
        }
    }

    private void l() {
        VideoSize videoSize = this.B;
        if (videoSize != null) {
            this.c.videoSizeChanged(videoSize);
        }
    }

    private void m() {
        if (this.D > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.c.droppedFrames(this.D, elapsedRealtime - this.C);
            this.D = 0;
            this.C = elapsedRealtime;
        }
    }
}
