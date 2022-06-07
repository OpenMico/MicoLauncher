package com.google.android.exoplayer2.mediacodec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class MediaCodecRenderer extends BaseRenderer {
    protected static final float CODEC_OPERATING_RATE_UNSET = -1.0f;
    private static final byte[] a = {0, 0, 1, 103, 66, -64, 11, -38, 37, -112, 0, 0, 1, 104, -50, 15, 19, 32, 0, 0, 1, 101, -120, -124, 13, -50, 113, 24, -96, 0, 47, ByteSourceJsonBootstrapper.UTF8_BOM_3, 28, 49, -61, 39, 93, 120};
    @Nullable
    private MediaFormat A;
    private boolean B;
    @Nullable
    private ArrayDeque<MediaCodecInfo> D;
    @Nullable
    private DecoderInitializationException E;
    @Nullable
    private MediaCodecInfo F;
    private boolean H;
    private boolean I;
    private boolean J;
    private boolean K;
    private boolean L;
    private boolean M;
    private boolean N;
    private boolean O;
    private boolean P;
    private boolean Q;
    @Nullable
    private d R;
    @Nullable
    private ByteBuffer V;
    private boolean W;
    private boolean X;
    private boolean Y;
    private boolean Z;
    private boolean aa;
    private boolean ab;
    private boolean af;
    private boolean ag;
    private boolean ah;
    private boolean ak;
    private boolean al;
    private boolean am;
    private boolean an;
    private boolean ao;
    private boolean ap;
    private boolean aq;
    @Nullable
    private ExoPlaybackException ar;
    private int au;
    private final MediaCodecAdapter.Factory b;
    private final MediaCodecSelector c;
    private final boolean d;
    protected DecoderCounters decoderCounters;
    private final float e;
    @Nullable
    private Format p;
    @Nullable
    private Format q;
    @Nullable
    private DrmSession r;
    @Nullable
    private DrmSession s;
    @Nullable
    private MediaCrypto t;
    private boolean u;
    @Nullable
    private MediaCodecAdapter y;
    @Nullable
    private Format z;
    private final DecoderInputBuffer f = DecoderInputBuffer.newNoDataInstance();
    private final DecoderInputBuffer g = new DecoderInputBuffer(0);
    private final DecoderInputBuffer h = new DecoderInputBuffer(2);
    private final c i = new c();
    private final TimedValueQueue<Format> j = new TimedValueQueue<>();
    private final ArrayList<Long> k = new ArrayList<>();
    private final MediaCodec.BufferInfo l = new MediaCodec.BufferInfo();
    private float w = 1.0f;
    private float x = 1.0f;
    private long v = C.TIME_UNSET;
    private final long[] m = new long[10];
    private final long[] n = new long[10];
    private final long[] o = new long[10];
    private long as = C.TIME_UNSET;
    private long at = C.TIME_UNSET;
    private float C = -1.0f;
    private int G = 0;
    private int ac = 0;
    private int T = -1;
    private int U = -1;
    private long S = C.TIME_UNSET;
    private long ai = C.TIME_UNSET;
    private long aj = C.TIME_UNSET;
    private int ad = 0;
    private int ae = 0;

    protected boolean getCodecNeedsEosPropagation() {
        return false;
    }

    protected float getCodecOperatingRateV23(float f, Format format, Format[] formatArr) {
        return -1.0f;
    }

    protected abstract List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws MediaCodecUtil.DecoderQueryException;

    @Nullable
    protected abstract MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo mediaCodecInfo, Format format, @Nullable MediaCrypto mediaCrypto, float f);

    protected void handleInputBufferSupplementalData(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
    }

    protected void onCodecError(Exception exc) {
    }

    protected void onCodecInitialized(String str, long j, long j2) {
    }

    protected void onCodecReleased(String str) {
    }

    protected void onOutputFormatChanged(Format format, @Nullable MediaFormat mediaFormat) throws ExoPlaybackException {
    }

    public void onProcessedStreamChange() {
    }

    protected void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStarted() {
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStopped() {
    }

    protected abstract boolean processOutputBuffer(long j, long j2, @Nullable MediaCodecAdapter mediaCodecAdapter, @Nullable ByteBuffer byteBuffer, int i, int i2, int i3, long j3, boolean z, boolean z2, Format format) throws ExoPlaybackException;

    protected void renderToEndOfStream() throws ExoPlaybackException {
    }

    protected boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        return true;
    }

    protected boolean shouldUseBypass(Format format) {
        return false;
    }

    protected abstract int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException;

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.RendererCapabilities
    public final int supportsMixedMimeTypeAdaptation() {
        return 8;
    }

    /* loaded from: classes2.dex */
    public static class DecoderInitializationException extends Exception {
        @Nullable
        public final MediaCodecInfo codecInfo;
        @Nullable
        public final String diagnosticInfo;
        @Nullable
        public final DecoderInitializationException fallbackDecoderInitializationException;
        public final String mimeType;
        public final boolean secureDecoderRequired;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public DecoderInitializationException(com.google.android.exoplayer2.Format r12, @androidx.annotation.Nullable java.lang.Throwable r13, boolean r14, int r15) {
            /*
                r11 = this;
                java.lang.String r0 = java.lang.String.valueOf(r12)
                java.lang.String r1 = java.lang.String.valueOf(r0)
                int r1 = r1.length()
                int r1 = r1 + 36
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Decoder init failed: ["
                r2.append(r1)
                r2.append(r15)
                java.lang.String r1 = "], "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r4 = r2.toString()
                java.lang.String r6 = r12.sampleMimeType
                java.lang.String r9 = a(r15)
                r8 = 0
                r10 = 0
                r3 = r11
                r5 = r13
                r7 = r14
                r3.<init>(r4, r5, r6, r7, r8, r9, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException.<init>(com.google.android.exoplayer2.Format, java.lang.Throwable, boolean, int):void");
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public DecoderInitializationException(com.google.android.exoplayer2.Format r9, @androidx.annotation.Nullable java.lang.Throwable r10, boolean r11, com.google.android.exoplayer2.mediacodec.MediaCodecInfo r12) {
            /*
                r8 = this;
                java.lang.String r0 = r12.name
                java.lang.String r1 = java.lang.String.valueOf(r9)
                java.lang.String r2 = java.lang.String.valueOf(r0)
                int r2 = r2.length()
                int r2 = r2 + 23
                java.lang.String r3 = java.lang.String.valueOf(r1)
                int r3 = r3.length()
                int r2 = r2 + r3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>(r2)
                java.lang.String r2 = "Decoder init failed: "
                r3.append(r2)
                r3.append(r0)
                java.lang.String r0 = ", "
                r3.append(r0)
                r3.append(r1)
                java.lang.String r1 = r3.toString()
                java.lang.String r3 = r9.sampleMimeType
                int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
                r2 = 21
                if (r0 < r2) goto L_0x003f
                java.lang.String r0 = a(r10)
                goto L_0x0040
            L_0x003f:
                r0 = 0
            L_0x0040:
                r6 = r0
                r7 = 0
                r0 = r8
                r2 = r10
                r4 = r11
                r5 = r12
                r0.<init>(r1, r2, r3, r4, r5, r6, r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException.<init>(com.google.android.exoplayer2.Format, java.lang.Throwable, boolean, com.google.android.exoplayer2.mediacodec.MediaCodecInfo):void");
        }

        private DecoderInitializationException(String str, @Nullable Throwable th, String str2, boolean z, @Nullable MediaCodecInfo mediaCodecInfo, @Nullable String str3, @Nullable DecoderInitializationException decoderInitializationException) {
            super(str, th);
            this.mimeType = str2;
            this.secureDecoderRequired = z;
            this.codecInfo = mediaCodecInfo;
            this.diagnosticInfo = str3;
            this.fallbackDecoderInitializationException = decoderInitializationException;
        }

        @CheckResult
        public DecoderInitializationException a(DecoderInitializationException decoderInitializationException) {
            return new DecoderInitializationException(getMessage(), getCause(), this.mimeType, this.secureDecoderRequired, this.codecInfo, this.diagnosticInfo, decoderInitializationException);
        }

        @Nullable
        @RequiresApi(21)
        private static String a(@Nullable Throwable th) {
            if (th instanceof MediaCodec.CodecException) {
                return ((MediaCodec.CodecException) th).getDiagnosticInfo();
            }
            return null;
        }

        private static String a(int i) {
            String str = i < 0 ? "neg_" : "";
            int abs = Math.abs(i);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 71);
            sb.append("com.google.android.exoplayer2.mediacodec.MediaCodecRenderer_");
            sb.append(str);
            sb.append(abs);
            return sb.toString();
        }
    }

    public MediaCodecRenderer(int i, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, boolean z, float f) {
        super(i);
        this.b = factory;
        this.c = (MediaCodecSelector) Assertions.checkNotNull(mediaCodecSelector);
        this.d = z;
        this.e = f;
        this.i.ensureSpaceForWrite(0);
        this.i.data.order(ByteOrder.nativeOrder());
    }

    public void setRenderTimeLimitMs(long j) {
        this.v = j;
    }

    public void experimentalSetAsynchronousBufferQueueingEnabled(boolean z) {
        this.ao = z;
    }

    public void experimentalSetForceAsyncQueueingSynchronizationWorkaround(boolean z) {
        this.ap = z;
    }

    public void experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z) {
        this.aq = z;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public final int supportsFormat(Format format) throws ExoPlaybackException {
        try {
            return supportsFormat(this.c, format);
        } catch (MediaCodecUtil.DecoderQueryException e) {
            throw createRendererException(e, format, 4002);
        }
    }

    protected final void maybeInitCodecOrBypass() throws ExoPlaybackException {
        Format format;
        if (this.y == null && !this.Y && (format = this.p) != null) {
            if (this.s != null || !shouldUseBypass(format)) {
                b(this.s);
                String str = this.p.sampleMimeType;
                DrmSession drmSession = this.r;
                if (drmSession != null) {
                    if (this.t == null) {
                        FrameworkMediaCrypto c = c(drmSession);
                        if (c != null) {
                            try {
                                this.t = new MediaCrypto(c.uuid, c.sessionId);
                                this.u = !c.forceAllowInsecureDecoderComponents && this.t.requiresSecureDecoderComponent(str);
                            } catch (MediaCryptoException e) {
                                throw createRendererException(e, this.p, PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR);
                            }
                        } else if (this.r.getError() == null) {
                            return;
                        }
                    }
                    if (FrameworkMediaCrypto.WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC) {
                        int state = this.r.getState();
                        if (state == 1) {
                            DrmSession.DrmSessionException drmSessionException = (DrmSession.DrmSessionException) Assertions.checkNotNull(this.r.getError());
                            throw createRendererException(drmSessionException, this.p, drmSessionException.errorCode);
                        } else if (state != 4) {
                            return;
                        }
                    }
                }
                try {
                    a(this.t, this.u);
                } catch (DecoderInitializationException e2) {
                    throw createRendererException(e2, this.p, PlaybackException.ERROR_CODE_DECODER_INIT_FAILED);
                }
            } else {
                a(this.p);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setPendingPlaybackException(ExoPlaybackException exoPlaybackException) {
        this.ar = exoPlaybackException;
    }

    protected final void updateOutputFormatForTime(long j) throws ExoPlaybackException {
        boolean z;
        Format pollFloor = this.j.pollFloor(j);
        if (pollFloor == null && this.B) {
            pollFloor = this.j.pollFirst();
        }
        if (pollFloor != null) {
            this.q = pollFloor;
            z = true;
        } else {
            z = false;
        }
        if (z || (this.B && this.q != null)) {
            onOutputFormatChanged(this.q, this.A);
            this.B = false;
        }
    }

    @Nullable
    protected final MediaCodecAdapter getCodec() {
        return this.y;
    }

    @Nullable
    protected final MediaFormat getCodecOutputMediaFormat() {
        return this.A;
    }

    @Nullable
    protected final MediaCodecInfo getCodecInfo() {
        return this.F;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
        this.decoderCounters = new DecoderCounters();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j, long j2) throws ExoPlaybackException {
        boolean z = true;
        if (this.at == C.TIME_UNSET) {
            if (this.as != C.TIME_UNSET) {
                z = false;
            }
            Assertions.checkState(z);
            this.as = j;
            this.at = j2;
            return;
        }
        int i = this.au;
        long[] jArr = this.n;
        if (i == jArr.length) {
            long j3 = jArr[i - 1];
            StringBuilder sb = new StringBuilder(65);
            sb.append("Too many stream changes, so dropping offset: ");
            sb.append(j3);
            Log.w("MediaCodecRenderer", sb.toString());
        } else {
            this.au = i + 1;
        }
        long[] jArr2 = this.m;
        int i2 = this.au;
        jArr2[i2 - 1] = j;
        this.n[i2 - 1] = j2;
        this.o[i2 - 1] = this.ai;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        this.ak = false;
        this.al = false;
        this.an = false;
        if (this.Y) {
            this.i.clear();
            this.h.clear();
            this.Z = false;
        } else {
            flushOrReinitializeCodec();
        }
        if (this.j.size() > 0) {
            this.am = true;
        }
        this.j.clear();
        int i = this.au;
        if (i != 0) {
            this.at = this.n[i - 1];
            this.as = this.m[i - 1];
            this.au = 0;
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        this.w = f;
        this.x = f2;
        b(this.z);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onDisabled() {
        this.p = null;
        this.as = C.TIME_UNSET;
        this.at = C.TIME_UNSET;
        this.au = 0;
        flushOrReleaseCodec();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onReset() {
        try {
            a();
            releaseCodec();
        } finally {
            a((DrmSession) null);
        }
    }

    private void a() {
        this.aa = false;
        this.i.clear();
        this.h.clear();
        this.Z = false;
        this.Y = false;
    }

    protected void releaseCodec() {
        try {
            if (this.y != null) {
                this.y.release();
                this.decoderCounters.decoderReleaseCount++;
                onCodecReleased(this.F.name);
            }
            this.y = null;
            try {
                if (this.t != null) {
                    this.t.release();
                }
                this.t = null;
                b((DrmSession) null);
                resetCodecStateForRelease();
            } catch (Throwable th) {
                this.t = null;
                b((DrmSession) null);
                resetCodecStateForRelease();
                throw th;
            }
        } catch (Throwable th2) {
            this.y = null;
            try {
                if (this.t != null) {
                    this.t.release();
                }
                this.t = null;
                b((DrmSession) null);
                resetCodecStateForRelease();
                throw th2;
            } catch (Throwable th3) {
                this.t = null;
                b((DrmSession) null);
                resetCodecStateForRelease();
                throw th3;
            }
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) throws ExoPlaybackException {
        if (this.an) {
            this.an = false;
            k();
        }
        ExoPlaybackException exoPlaybackException = this.ar;
        if (exoPlaybackException == null) {
            boolean z = true;
            try {
                if (this.al) {
                    renderToEndOfStream();
                } else if (this.p != null || a(2)) {
                    maybeInitCodecOrBypass();
                    if (this.Y) {
                        TraceUtil.beginSection("bypassRender");
                        while (b(j, j2)) {
                        }
                        TraceUtil.endSection();
                    } else if (this.y != null) {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        TraceUtil.beginSection("drainAndFeed");
                        while (a(j, j2) && a(elapsedRealtime)) {
                        }
                        while (f() && a(elapsedRealtime)) {
                        }
                        TraceUtil.endSection();
                    } else {
                        this.decoderCounters.skippedInputBufferCount += skipSource(j);
                        a(1);
                    }
                    this.decoderCounters.ensureUpdated();
                }
            } catch (IllegalStateException e) {
                if (a(e)) {
                    onCodecError(e);
                    if (Util.SDK_INT < 21 || !c(e)) {
                        z = false;
                    }
                    if (z) {
                        releaseCodec();
                    }
                    throw createRendererException(createDecoderException(e, getCodecInfo()), this.p, z, 4003);
                }
                throw e;
            }
        } else {
            this.ar = null;
            throw exoPlaybackException;
        }
    }

    protected final boolean flushOrReinitializeCodec() throws ExoPlaybackException {
        boolean flushOrReleaseCodec = flushOrReleaseCodec();
        if (flushOrReleaseCodec) {
            maybeInitCodecOrBypass();
        }
        return flushOrReleaseCodec;
    }

    protected boolean flushOrReleaseCodec() {
        if (this.y == null) {
            return false;
        }
        if (this.ae == 3 || this.I || ((this.J && !this.ah) || (this.K && this.ag))) {
            releaseCodec();
            return true;
        }
        b();
        return false;
    }

    private void b() {
        try {
            this.y.flush();
        } finally {
            resetCodecStateForFlush();
        }
    }

    @CallSuper
    public void resetCodecStateForFlush() {
        d();
        e();
        this.S = C.TIME_UNSET;
        this.ag = false;
        this.af = false;
        this.O = false;
        this.P = false;
        this.W = false;
        this.X = false;
        this.k.clear();
        this.ai = C.TIME_UNSET;
        this.aj = C.TIME_UNSET;
        d dVar = this.R;
        if (dVar != null) {
            dVar.a();
        }
        this.ad = 0;
        this.ae = 0;
        this.ac = this.ab ? 1 : 0;
    }

    @CallSuper
    protected void resetCodecStateForRelease() {
        resetCodecStateForFlush();
        this.ar = null;
        this.R = null;
        this.D = null;
        this.F = null;
        this.z = null;
        this.A = null;
        this.B = false;
        this.ah = false;
        this.C = -1.0f;
        this.G = 0;
        this.H = false;
        this.I = false;
        this.J = false;
        this.K = false;
        this.L = false;
        this.M = false;
        this.N = false;
        this.Q = false;
        this.ab = false;
        this.ac = 0;
        this.u = false;
    }

    protected MediaCodecDecoderException createDecoderException(Throwable th, @Nullable MediaCodecInfo mediaCodecInfo) {
        return new MediaCodecDecoderException(th, mediaCodecInfo);
    }

    private boolean a(int i) throws ExoPlaybackException {
        FormatHolder formatHolder = getFormatHolder();
        this.f.clear();
        int readSource = readSource(formatHolder, this.f, i | 4);
        if (readSource == -5) {
            onInputFormatChanged(formatHolder);
            return true;
        } else if (readSource != -4 || !this.f.isEndOfStream()) {
            return false;
        } else {
            this.ak = true;
            k();
            return false;
        }
    }

    private void a(MediaCrypto mediaCrypto, boolean z) throws DecoderInitializationException {
        if (this.D == null) {
            try {
                List<MediaCodecInfo> a2 = a(z);
                this.D = new ArrayDeque<>();
                if (this.d) {
                    this.D.addAll(a2);
                } else if (!a2.isEmpty()) {
                    this.D.add(a2.get(0));
                }
                this.E = null;
            } catch (MediaCodecUtil.DecoderQueryException e) {
                throw new DecoderInitializationException(this.p, e, z, -49998);
            }
        }
        if (!this.D.isEmpty()) {
            while (this.y == null) {
                MediaCodecInfo peekFirst = this.D.peekFirst();
                if (shouldInitCodec(peekFirst)) {
                    try {
                        a(peekFirst, mediaCrypto);
                    } catch (Exception e2) {
                        String valueOf = String.valueOf(peekFirst);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30);
                        sb.append("Failed to initialize decoder: ");
                        sb.append(valueOf);
                        Log.w("MediaCodecRenderer", sb.toString(), e2);
                        this.D.removeFirst();
                        DecoderInitializationException decoderInitializationException = new DecoderInitializationException(this.p, e2, z, peekFirst);
                        onCodecError(decoderInitializationException);
                        DecoderInitializationException decoderInitializationException2 = this.E;
                        if (decoderInitializationException2 == null) {
                            this.E = decoderInitializationException;
                        } else {
                            this.E = decoderInitializationException2.a(decoderInitializationException);
                        }
                        if (this.D.isEmpty()) {
                            throw this.E;
                        }
                    }
                } else {
                    return;
                }
            }
            this.D = null;
            return;
        }
        throw new DecoderInitializationException(this.p, (Throwable) null, z, -49999);
    }

    private List<MediaCodecInfo> a(boolean z) throws MediaCodecUtil.DecoderQueryException {
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(this.c, this.p, z);
        if (decoderInfos.isEmpty() && z) {
            decoderInfos = getDecoderInfos(this.c, this.p, false);
            if (!decoderInfos.isEmpty()) {
                String str = this.p.sampleMimeType;
                String valueOf = String.valueOf(decoderInfos);
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 99 + String.valueOf(valueOf).length());
                sb.append("Drm session requires secure decoder for ");
                sb.append(str);
                sb.append(", but no secure decoder available. Trying to proceed with ");
                sb.append(valueOf);
                sb.append(".");
                Log.w("MediaCodecRenderer", sb.toString());
            }
        }
        return decoderInfos;
    }

    private void a(Format format) {
        a();
        String str = format.sampleMimeType;
        if (MimeTypes.AUDIO_AAC.equals(str) || "audio/mpeg".equals(str) || MimeTypes.AUDIO_OPUS.equals(str)) {
            this.i.a(32);
        } else {
            this.i.a(1);
        }
        this.Y = true;
    }

    private void a(MediaCodecInfo mediaCodecInfo, MediaCrypto mediaCrypto) throws Exception {
        MediaCodecAdapter mediaCodecAdapter;
        String str = mediaCodecInfo.name;
        float codecOperatingRateV23 = Util.SDK_INT < 23 ? -1.0f : getCodecOperatingRateV23(this.x, this.p, getStreamFormats());
        if (codecOperatingRateV23 <= this.e) {
            codecOperatingRateV23 = -1.0f;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        String valueOf = String.valueOf(str);
        TraceUtil.beginSection(valueOf.length() != 0 ? "createCodec:".concat(valueOf) : new String("createCodec:"));
        MediaCodecAdapter.Configuration mediaCodecConfiguration = getMediaCodecConfiguration(mediaCodecInfo, this.p, mediaCrypto, codecOperatingRateV23);
        if (!this.ao || Util.SDK_INT < 23) {
            mediaCodecAdapter = this.b.createAdapter(mediaCodecConfiguration);
        } else {
            mediaCodecAdapter = new AsynchronousMediaCodecAdapter.Factory(getTrackType(), this.ap, this.aq).createAdapter(mediaCodecConfiguration);
        }
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        this.y = mediaCodecAdapter;
        this.F = mediaCodecInfo;
        this.C = codecOperatingRateV23;
        this.z = this.p;
        this.G = b(str);
        this.H = a(str, this.z);
        this.I = a(str);
        this.J = c(str);
        this.K = d(str);
        this.L = f(str);
        this.M = e(str);
        this.N = b(str, this.z);
        boolean z = false;
        this.Q = a(mediaCodecInfo) || getCodecNeedsEosPropagation();
        if (mediaCodecAdapter.needsReconfiguration()) {
            this.ab = true;
            this.ac = 1;
            if (this.G != 0) {
                z = true;
            }
            this.O = z;
        }
        if ("c2.android.mp3.decoder".equals(mediaCodecInfo.name)) {
            this.R = new d();
        }
        if (getState() == 2) {
            this.S = SystemClock.elapsedRealtime() + 1000;
        }
        this.decoderCounters.decoderInitCount++;
        onCodecInitialized(str, elapsedRealtime2, elapsedRealtime2 - elapsedRealtime);
    }

    private boolean a(long j) {
        return this.v == C.TIME_UNSET || SystemClock.elapsedRealtime() - j < this.v;
    }

    private boolean c() {
        return this.U >= 0;
    }

    private void d() {
        this.T = -1;
        this.g.data = null;
    }

    private void e() {
        this.U = -1;
        this.V = null;
    }

    private void a(@Nullable DrmSession drmSession) {
        DrmSession.replaceSession(this.s, drmSession);
        this.s = drmSession;
    }

    private void b(@Nullable DrmSession drmSession) {
        DrmSession.replaceSession(this.r, drmSession);
        this.r = drmSession;
    }

    private boolean f() throws ExoPlaybackException {
        MediaCodecAdapter mediaCodecAdapter = this.y;
        if (mediaCodecAdapter == null || this.ad == 2 || this.ak) {
            return false;
        }
        if (this.T < 0) {
            this.T = mediaCodecAdapter.dequeueInputBufferIndex();
            int i = this.T;
            if (i < 0) {
                return false;
            }
            this.g.data = this.y.getInputBuffer(i);
            this.g.clear();
        }
        if (this.ad == 1) {
            if (!this.Q) {
                this.ag = true;
                this.y.queueInputBuffer(this.T, 0, 0, 0L, 4);
                d();
            }
            this.ad = 2;
            return false;
        } else if (this.O) {
            this.O = false;
            this.g.data.put(a);
            this.y.queueInputBuffer(this.T, 0, a.length, 0L, 0);
            d();
            this.af = true;
            return true;
        } else {
            if (this.ac == 1) {
                for (int i2 = 0; i2 < this.z.initializationData.size(); i2++) {
                    this.g.data.put(this.z.initializationData.get(i2));
                }
                this.ac = 2;
            }
            int position = this.g.data.position();
            FormatHolder formatHolder = getFormatHolder();
            try {
                int readSource = readSource(formatHolder, this.g, 0);
                if (hasReadStreamToEnd()) {
                    this.aj = this.ai;
                }
                if (readSource == -3) {
                    return false;
                }
                if (readSource == -5) {
                    if (this.ac == 2) {
                        this.g.clear();
                        this.ac = 1;
                    }
                    onInputFormatChanged(formatHolder);
                    return true;
                } else if (this.g.isEndOfStream()) {
                    if (this.ac == 2) {
                        this.g.clear();
                        this.ac = 1;
                    }
                    this.ak = true;
                    if (!this.af) {
                        k();
                        return false;
                    }
                    try {
                        if (!this.Q) {
                            this.ag = true;
                            this.y.queueInputBuffer(this.T, 0, 0, 0L, 4);
                            d();
                        }
                        return false;
                    } catch (MediaCodec.CryptoException e) {
                        throw createRendererException(e, this.p, C.getErrorCodeForMediaDrmErrorCode(e.getErrorCode()));
                    }
                } else if (this.af || this.g.isKeyFrame()) {
                    boolean isEncrypted = this.g.isEncrypted();
                    if (isEncrypted) {
                        this.g.cryptoInfo.increaseClearDataFirstSubSampleBy(position);
                    }
                    if (this.H && !isEncrypted) {
                        NalUnitUtil.discardToSps(this.g.data);
                        if (this.g.data.position() == 0) {
                            return true;
                        }
                        this.H = false;
                    }
                    long j = this.g.timeUs;
                    d dVar = this.R;
                    if (dVar != null) {
                        long a2 = dVar.a(this.p, this.g);
                        this.ai = Math.max(this.ai, this.R.a(this.p));
                        j = a2;
                    }
                    if (this.g.isDecodeOnly()) {
                        this.k.add(Long.valueOf(j));
                    }
                    if (this.am) {
                        this.j.add(j, this.p);
                        this.am = false;
                    }
                    this.ai = Math.max(this.ai, j);
                    this.g.flip();
                    if (this.g.hasSupplementalData()) {
                        handleInputBufferSupplementalData(this.g);
                    }
                    onQueueInputBuffer(this.g);
                    try {
                        if (isEncrypted) {
                            this.y.queueSecureInputBuffer(this.T, 0, this.g.cryptoInfo, j, 0);
                        } else {
                            this.y.queueInputBuffer(this.T, 0, this.g.data.limit(), j, 0);
                        }
                        d();
                        this.af = true;
                        this.ac = 0;
                        this.decoderCounters.inputBufferCount++;
                        return true;
                    } catch (MediaCodec.CryptoException e2) {
                        throw createRendererException(e2, this.p, C.getErrorCodeForMediaDrmErrorCode(e2.getErrorCode()));
                    }
                } else {
                    this.g.clear();
                    if (this.ac == 2) {
                        this.ac = 1;
                    }
                    return true;
                }
            } catch (DecoderInputBuffer.InsufficientCapacityException e3) {
                onCodecError(e3);
                a(0);
                b();
                return true;
            }
        }
    }

    @Nullable
    @CallSuper
    public DecoderReuseEvaluation onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        int i;
        boolean z = true;
        this.am = true;
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        if (format.sampleMimeType != null) {
            a(formatHolder.drmSession);
            this.p = format;
            if (this.Y) {
                this.aa = true;
                return null;
            }
            MediaCodecAdapter mediaCodecAdapter = this.y;
            if (mediaCodecAdapter == null) {
                this.D = null;
                maybeInitCodecOrBypass();
                return null;
            }
            MediaCodecInfo mediaCodecInfo = this.F;
            Format format2 = this.z;
            if (a(mediaCodecInfo, format, this.r, this.s)) {
                i();
                return new DecoderReuseEvaluation(mediaCodecInfo.name, format2, format, 0, 128);
            }
            boolean z2 = this.s != this.r;
            Assertions.checkState(!z2 || Util.SDK_INT >= 23);
            DecoderReuseEvaluation canReuseCodec = canReuseCodec(mediaCodecInfo, format2, format);
            switch (canReuseCodec.result) {
                case 0:
                    i();
                    i = 0;
                    break;
                case 1:
                    if (!b(format)) {
                        i = 16;
                        break;
                    } else {
                        this.z = format;
                        if (z2) {
                            if (!h()) {
                                i = 2;
                                break;
                            }
                            i = 0;
                            break;
                        } else {
                            if (!g()) {
                                i = 2;
                                break;
                            }
                            i = 0;
                        }
                    }
                case 2:
                    if (b(format)) {
                        this.ab = true;
                        this.ac = 1;
                        int i2 = this.G;
                        if (!(i2 == 2 || (i2 == 1 && format.width == format2.width && format.height == format2.height))) {
                            z = false;
                        }
                        this.O = z;
                        this.z = format;
                        if (z2 && !h()) {
                            i = 2;
                            break;
                        }
                        i = 0;
                        break;
                    } else {
                        i = 16;
                        break;
                    }
                    break;
                case 3:
                    if (b(format)) {
                        this.z = format;
                        if (z2 && !h()) {
                            i = 2;
                            break;
                        }
                        i = 0;
                        break;
                    } else {
                        i = 16;
                        break;
                    }
                default:
                    throw new IllegalStateException();
            }
            return (canReuseCodec.result == 0 || (this.y == mediaCodecAdapter && this.ae != 3)) ? canReuseCodec : new DecoderReuseEvaluation(mediaCodecInfo.name, format2, format, 0, i);
        }
        throw createRendererException(new IllegalArgumentException(), format, PlaybackException.ERROR_CODE_DECODING_FORMAT_UNSUPPORTED);
    }

    @CallSuper
    public void onProcessedOutputBuffer(long j) {
        while (true) {
            int i = this.au;
            if (i != 0 && j >= this.o[0]) {
                long[] jArr = this.m;
                this.as = jArr[0];
                this.at = this.n[0];
                this.au = i - 1;
                System.arraycopy(jArr, 1, jArr, 0, this.au);
                long[] jArr2 = this.n;
                System.arraycopy(jArr2, 1, jArr2, 0, this.au);
                long[] jArr3 = this.o;
                System.arraycopy(jArr3, 1, jArr3, 0, this.au);
                onProcessedStreamChange();
            } else {
                return;
            }
        }
    }

    protected DecoderReuseEvaluation canReuseCodec(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        return new DecoderReuseEvaluation(mediaCodecInfo.name, format, format2, 0, 1);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.al;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return this.p != null && (isSourceReady() || c() || (this.S != C.TIME_UNSET && SystemClock.elapsedRealtime() < this.S));
    }

    protected float getPlaybackSpeed() {
        return this.w;
    }

    protected float getCodecOperatingRate() {
        return this.C;
    }

    protected final boolean updateCodecOperatingRate() throws ExoPlaybackException {
        return b(this.z);
    }

    private boolean b(Format format) throws ExoPlaybackException {
        if (Util.SDK_INT < 23 || this.y == null || this.ae == 3 || getState() == 0) {
            return true;
        }
        float codecOperatingRateV23 = getCodecOperatingRateV23(this.x, format, getStreamFormats());
        float f = this.C;
        if (f == codecOperatingRateV23) {
            return true;
        }
        if (codecOperatingRateV23 == -1.0f) {
            i();
            return false;
        } else if (f == -1.0f && codecOperatingRateV23 <= this.e) {
            return true;
        } else {
            Bundle bundle = new Bundle();
            bundle.putFloat("operating-rate", codecOperatingRateV23);
            this.y.setParameters(bundle);
            this.C = codecOperatingRateV23;
            return true;
        }
    }

    private boolean g() {
        if (this.af) {
            this.ad = 1;
            if (this.I || this.K) {
                this.ae = 3;
                return false;
            }
            this.ae = 1;
        }
        return true;
    }

    @TargetApi(23)
    private boolean h() throws ExoPlaybackException {
        if (this.af) {
            this.ad = 1;
            if (this.I || this.K) {
                this.ae = 3;
                return false;
            }
            this.ae = 2;
        } else {
            m();
        }
        return true;
    }

    private void i() throws ExoPlaybackException {
        if (this.af) {
            this.ad = 1;
            this.ae = 3;
            return;
        }
        l();
    }

    private boolean a(long j, long j2) throws ExoPlaybackException {
        boolean z;
        boolean z2;
        int i;
        if (!c()) {
            if (!this.L || !this.ag) {
                i = this.y.dequeueOutputBufferIndex(this.l);
            } else {
                try {
                    i = this.y.dequeueOutputBufferIndex(this.l);
                } catch (IllegalStateException unused) {
                    k();
                    if (this.al) {
                        releaseCodec();
                    }
                    return false;
                }
            }
            if (i < 0) {
                if (i == -2) {
                    j();
                    return true;
                }
                if (this.Q && (this.ak || this.ad == 2)) {
                    k();
                }
                return false;
            } else if (this.P) {
                this.P = false;
                this.y.releaseOutputBuffer(i, false);
                return true;
            } else if (this.l.size != 0 || (this.l.flags & 4) == 0) {
                this.U = i;
                this.V = this.y.getOutputBuffer(i);
                ByteBuffer byteBuffer = this.V;
                if (byteBuffer != null) {
                    byteBuffer.position(this.l.offset);
                    this.V.limit(this.l.offset + this.l.size);
                }
                if (this.M && this.l.presentationTimeUs == 0 && (this.l.flags & 4) != 0) {
                    long j3 = this.ai;
                    if (j3 != C.TIME_UNSET) {
                        this.l.presentationTimeUs = j3;
                    }
                }
                this.W = b(this.l.presentationTimeUs);
                this.X = this.aj == this.l.presentationTimeUs;
                updateOutputFormatForTime(this.l.presentationTimeUs);
            } else {
                k();
                return false;
            }
        }
        if (!this.L || !this.ag) {
            z = false;
            z2 = processOutputBuffer(j, j2, this.y, this.V, this.U, this.l.flags, 1, this.l.presentationTimeUs, this.W, this.X, this.q);
        } else {
            try {
                z = false;
            } catch (IllegalStateException unused2) {
                z = false;
            }
            try {
                z2 = processOutputBuffer(j, j2, this.y, this.V, this.U, this.l.flags, 1, this.l.presentationTimeUs, this.W, this.X, this.q);
            } catch (IllegalStateException unused3) {
                k();
                if (this.al) {
                    releaseCodec();
                }
                return z;
            }
        }
        if (z2) {
            onProcessedOutputBuffer(this.l.presentationTimeUs);
            boolean z3 = (this.l.flags & 4) != 0 ? true : z;
            e();
            if (!z3) {
                return true;
            }
            k();
        }
        return z;
    }

    private void j() {
        this.ah = true;
        MediaFormat outputFormat = this.y.getOutputFormat();
        if (this.G != 0 && outputFormat.getInteger("width") == 32 && outputFormat.getInteger("height") == 32) {
            this.P = true;
            return;
        }
        if (this.N) {
            outputFormat.setInteger("channel-count", 1);
        }
        this.A = outputFormat;
        this.B = true;
    }

    @TargetApi(23)
    private void k() throws ExoPlaybackException {
        switch (this.ae) {
            case 1:
                b();
                return;
            case 2:
                b();
                m();
                return;
            case 3:
                l();
                return;
            default:
                this.al = true;
                renderToEndOfStream();
                return;
        }
    }

    protected final void setPendingOutputEndOfStream() {
        this.an = true;
    }

    protected final long getOutputStreamOffsetUs() {
        return this.at;
    }

    protected static boolean supportsFormatDrm(Format format) {
        return format.exoMediaCryptoType == null || FrameworkMediaCrypto.class.equals(format.exoMediaCryptoType);
    }

    private boolean a(MediaCodecInfo mediaCodecInfo, Format format, @Nullable DrmSession drmSession, @Nullable DrmSession drmSession2) throws ExoPlaybackException {
        FrameworkMediaCrypto c;
        if (drmSession == drmSession2) {
            return false;
        }
        if (drmSession2 == null || drmSession == null || Util.SDK_INT < 23 || C.PLAYREADY_UUID.equals(drmSession.getSchemeUuid()) || C.PLAYREADY_UUID.equals(drmSession2.getSchemeUuid()) || (c = c(drmSession2)) == null) {
            return true;
        }
        return !mediaCodecInfo.secure && a(c, format);
    }

    private boolean a(FrameworkMediaCrypto frameworkMediaCrypto, Format format) {
        if (frameworkMediaCrypto.forceAllowInsecureDecoderComponents) {
            return false;
        }
        try {
            MediaCrypto mediaCrypto = new MediaCrypto(frameworkMediaCrypto.uuid, frameworkMediaCrypto.sessionId);
            try {
                return mediaCrypto.requiresSecureDecoderComponent(format.sampleMimeType);
            } finally {
                mediaCrypto.release();
            }
        } catch (MediaCryptoException unused) {
            return true;
        }
    }

    private void l() throws ExoPlaybackException {
        releaseCodec();
        maybeInitCodecOrBypass();
    }

    private boolean b(long j) {
        int size = this.k.size();
        for (int i = 0; i < size; i++) {
            if (this.k.get(i).longValue() == j) {
                this.k.remove(i);
                return true;
            }
        }
        return false;
    }

    @RequiresApi(23)
    private void m() throws ExoPlaybackException {
        try {
            this.t.setMediaDrmSession(c(this.s).sessionId);
            b(this.s);
            this.ad = 0;
            this.ae = 0;
        } catch (MediaCryptoException e) {
            throw createRendererException(e, this.p, PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR);
        }
    }

    @Nullable
    private FrameworkMediaCrypto c(DrmSession drmSession) throws ExoPlaybackException {
        ExoMediaCrypto mediaCrypto = drmSession.getMediaCrypto();
        if (mediaCrypto == null || (mediaCrypto instanceof FrameworkMediaCrypto)) {
            return (FrameworkMediaCrypto) mediaCrypto;
        }
        String valueOf = String.valueOf(mediaCrypto);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 42);
        sb.append("Expecting FrameworkMediaCrypto but found: ");
        sb.append(valueOf);
        throw createRendererException(new IllegalArgumentException(sb.toString()), this.p, 6001);
    }

    private boolean b(long j, long j2) throws ExoPlaybackException {
        Assertions.checkState(!this.al);
        boolean z = false;
        if (this.i.d()) {
            if (!processOutputBuffer(j, j2, null, this.i.data, this.U, 0, this.i.c(), this.i.a(), this.i.isDecodeOnly(), this.i.isEndOfStream(), this.q)) {
                return false;
            }
            onProcessedOutputBuffer(this.i.b());
            this.i.clear();
            z = false;
        }
        if (this.ak) {
            this.al = true;
            return z;
        }
        if (this.Z) {
            Assertions.checkState(this.i.a(this.h));
            this.Z = z;
        }
        if (this.aa) {
            if (this.i.d()) {
                return true;
            }
            a();
            this.aa = z;
            maybeInitCodecOrBypass();
            if (!this.Y) {
                return z;
            }
        }
        n();
        if (this.i.d()) {
            this.i.flip();
        }
        if (this.i.d() || this.ak || this.aa) {
            return true;
        }
        return z;
    }

    private void n() throws ExoPlaybackException {
        Assertions.checkState(!this.ak);
        FormatHolder formatHolder = getFormatHolder();
        this.h.clear();
        do {
            this.h.clear();
            switch (readSource(formatHolder, this.h, 0)) {
                case -5:
                    onInputFormatChanged(formatHolder);
                    return;
                case -4:
                    if (!this.h.isEndOfStream()) {
                        if (this.am) {
                            this.q = (Format) Assertions.checkNotNull(this.p);
                            onOutputFormatChanged(this.q, null);
                            this.am = false;
                        }
                        this.h.flip();
                        break;
                    } else {
                        this.ak = true;
                        return;
                    }
                case -3:
                    return;
                default:
                    throw new IllegalStateException();
            }
        } while (this.i.a(this.h));
        this.Z = true;
    }

    private static boolean a(IllegalStateException illegalStateException) {
        if (Util.SDK_INT >= 21 && b(illegalStateException)) {
            return true;
        }
        StackTraceElement[] stackTrace = illegalStateException.getStackTrace();
        return stackTrace.length > 0 && stackTrace[0].getClassName().equals("android.media.MediaCodec");
    }

    @RequiresApi(21)
    private static boolean b(IllegalStateException illegalStateException) {
        return illegalStateException instanceof MediaCodec.CodecException;
    }

    @RequiresApi(21)
    private static boolean c(IllegalStateException illegalStateException) {
        if (illegalStateException instanceof MediaCodec.CodecException) {
            return ((MediaCodec.CodecException) illegalStateException).isRecoverable();
        }
        return false;
    }

    private static boolean a(String str) {
        return Util.SDK_INT < 18 || (Util.SDK_INT == 18 && ("OMX.SEC.avc.dec".equals(str) || "OMX.SEC.avc.dec.secure".equals(str))) || (Util.SDK_INT == 19 && Util.MODEL.startsWith("SM-G800") && ("OMX.Exynos.avc.dec".equals(str) || "OMX.Exynos.avc.dec.secure".equals(str)));
    }

    private int b(String str) {
        if (Util.SDK_INT <= 25 && "OMX.Exynos.avc.dec.secure".equals(str) && (Util.MODEL.startsWith("SM-T585") || Util.MODEL.startsWith("SM-A510") || Util.MODEL.startsWith("SM-A520") || Util.MODEL.startsWith("SM-J700"))) {
            return 2;
        }
        if (Util.SDK_INT >= 24) {
            return 0;
        }
        if ("OMX.Nvidia.h264.decode".equals(str) || "OMX.Nvidia.h264.decode.secure".equals(str)) {
            return ("flounder".equals(Util.DEVICE) || "flounder_lte".equals(Util.DEVICE) || "grouper".equals(Util.DEVICE) || "tilapia".equals(Util.DEVICE)) ? 1 : 0;
        }
        return 0;
    }

    private static boolean a(String str, Format format) {
        return Util.SDK_INT < 21 && format.initializationData.isEmpty() && "OMX.MTK.VIDEO.DECODER.AVC".equals(str);
    }

    private static boolean c(String str) {
        return Util.SDK_INT == 29 && "c2.android.aac.decoder".equals(str);
    }

    private static boolean a(MediaCodecInfo mediaCodecInfo) {
        String str = mediaCodecInfo.name;
        return (Util.SDK_INT <= 25 && "OMX.rk.video_decoder.avc".equals(str)) || (Util.SDK_INT <= 17 && "OMX.allwinner.video.decoder.avc".equals(str)) || ((Util.SDK_INT <= 29 && ("OMX.broadcom.video_decoder.tunnel".equals(str) || "OMX.broadcom.video_decoder.tunnel.secure".equals(str))) || ("Amazon".equals(Util.MANUFACTURER) && "AFTS".equals(Util.MODEL) && mediaCodecInfo.secure));
    }

    private static boolean d(String str) {
        return (Util.SDK_INT <= 23 && "OMX.google.vorbis.decoder".equals(str)) || (Util.SDK_INT <= 19 && (("hb2000".equals(Util.DEVICE) || "stvm8".equals(Util.DEVICE)) && ("OMX.amlogic.avc.decoder.awesome".equals(str) || "OMX.amlogic.avc.decoder.awesome.secure".equals(str))));
    }

    private static boolean e(String str) {
        return Util.SDK_INT < 21 && "OMX.SEC.mp3.dec".equals(str) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("baffin") || Util.DEVICE.startsWith("grand") || Util.DEVICE.startsWith("fortuna") || Util.DEVICE.startsWith("gprimelte") || Util.DEVICE.startsWith("j2y18lte") || Util.DEVICE.startsWith("ms01"));
    }

    private static boolean f(String str) {
        return Util.SDK_INT == 21 && "OMX.google.aac.decoder".equals(str);
    }

    private static boolean b(String str, Format format) {
        return Util.SDK_INT <= 18 && format.channelCount == 1 && "OMX.MTK.AUDIO.DECODER.MP3".equals(str);
    }
}
