package com.google.android.exoplayer2.video;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeUtils;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final int[] b = {1920, 1600, ChildModeUtils.MAX_MINUTE, 1280, 960, 854, 640, 540, CommonUtils.IMAGE_WIDTH_THRESHOLD};
    private static boolean c;
    private static boolean d;
    private long A;
    private long B;
    private long C;
    private int D;
    private int E;
    private int F;
    private int G;
    private float H;
    @Nullable
    private VideoSize I;
    private boolean J;
    private int K;
    @Nullable
    private VideoFrameMetadataListener L;
    @Nullable
    a a;
    private final Context e;
    private final VideoFrameReleaseHelper f;
    private final VideoRendererEventListener.EventDispatcher g;
    private final long h;
    private final int i;
    private final boolean j;
    private CodecMaxValues k;
    private boolean l;
    private boolean m;
    @Nullable
    private Surface n;
    @Nullable
    private DummySurface o;
    private boolean p;
    private int q;
    private boolean r;
    private boolean s;
    private boolean t;
    private long u;
    private long v;
    private long w;
    private int x;
    private int y;
    private int z;

    private static boolean a(long j) {
        return j < -30000;
    }

    private static boolean b(long j) {
        return j < -500000;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return "MediaCodecVideoRenderer";
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0L);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j) {
        this(context, mediaCodecSelector, j, null, null, 0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, j, false, handler, videoRendererEventListener, i);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, j, z, handler, videoRendererEventListener, i);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        super(2, factory, mediaCodecSelector, z, 30.0f);
        this.h = j;
        this.i = i;
        this.e = context.getApplicationContext();
        this.f = new VideoFrameReleaseHelper(this.e);
        this.g = new VideoRendererEventListener.EventDispatcher(handler, videoRendererEventListener);
        this.j = k();
        this.v = C.TIME_UNSET;
        this.E = -1;
        this.F = -1;
        this.H = -1.0f;
        this.q = 1;
        this.K = 0;
        f();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        int i = 0;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return RendererCapabilities.create(0);
        }
        boolean z = format.drmInitData != null;
        List<MediaCodecInfo> a2 = a(mediaCodecSelector, format, z, false);
        if (z && a2.isEmpty()) {
            a2 = a(mediaCodecSelector, format, false, false);
        }
        if (a2.isEmpty()) {
            return RendererCapabilities.create(1);
        }
        if (!supportsFormatDrm(format)) {
            return RendererCapabilities.create(2);
        }
        MediaCodecInfo mediaCodecInfo = a2.get(0);
        boolean isFormatSupported = mediaCodecInfo.isFormatSupported(format);
        int i2 = mediaCodecInfo.isSeamlessAdaptationSupported(format) ? 16 : 8;
        if (isFormatSupported) {
            List<MediaCodecInfo> a3 = a(mediaCodecSelector, format, z, true);
            if (!a3.isEmpty()) {
                MediaCodecInfo mediaCodecInfo2 = a3.get(0);
                if (mediaCodecInfo2.isFormatSupported(format) && mediaCodecInfo2.isSeamlessAdaptationSupported(format)) {
                    i = 32;
                }
            }
        }
        return RendererCapabilities.create(isFormatSupported ? 4 : 3, i2, i);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws MediaCodecUtil.DecoderQueryException {
        return a(mediaCodecSelector, format, z, this.J);
    }

    private static List<MediaCodecInfo> a(MediaCodecSelector mediaCodecSelector, Format format, boolean z, boolean z2) throws MediaCodecUtil.DecoderQueryException {
        Pair<Integer, Integer> codecProfileAndLevel;
        String str = format.sampleMimeType;
        if (str == null) {
            return Collections.emptyList();
        }
        List<MediaCodecInfo> decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, z, z2), format);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(str) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            int intValue = ((Integer) codecProfileAndLevel.first).intValue();
            if (intValue == 16 || intValue == 256) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H265, z, z2));
            } else if (intValue == 512) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H264, z, z2));
            }
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
        super.onEnabled(z, z2);
        boolean z3 = getConfiguration().tunneling;
        Assertions.checkState(!z3 || this.K != 0);
        if (this.J != z3) {
            this.J = z3;
            releaseCodec();
        }
        this.g.enabled(this.decoderCounters);
        this.f.onEnabled();
        this.s = z2;
        this.t = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        super.onPositionReset(j, z);
        d();
        this.f.onPositionReset();
        this.A = C.TIME_UNSET;
        this.u = C.TIME_UNSET;
        this.y = 0;
        if (z) {
            c();
        } else {
            this.v = C.TIME_UNSET;
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        DummySurface dummySurface;
        if (super.isReady() && (this.r || (((dummySurface = this.o) != null && this.n == dummySurface) || getCodec() == null || this.J))) {
            this.v = C.TIME_UNSET;
            return true;
        } else if (this.v == C.TIME_UNSET) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() < this.v) {
                return true;
            }
            this.v = C.TIME_UNSET;
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onStarted() {
        super.onStarted();
        this.x = 0;
        this.w = SystemClock.elapsedRealtime();
        this.B = SystemClock.elapsedRealtime() * 1000;
        this.C = 0L;
        this.D = 0;
        this.f.onStarted();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onStopped() {
        this.v = C.TIME_UNSET;
        i();
        j();
        this.f.onStopped();
        super.onStopped();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onDisabled() {
        f();
        d();
        this.p = false;
        this.f.onDisabled();
        this.a = null;
        try {
            super.onDisabled();
        } finally {
            this.g.disabled(this.decoderCounters);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Finally extract failed */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    @TargetApi(17)
    public void onReset() {
        try {
            super.onReset();
            DummySurface dummySurface = this.o;
            if (dummySurface != null) {
                if (this.n == dummySurface) {
                    this.n = null;
                }
                this.o.release();
                this.o = null;
            }
        } catch (Throwable th) {
            DummySurface dummySurface2 = this.o;
            if (dummySurface2 != null) {
                if (this.n == dummySurface2) {
                    this.n = null;
                }
                this.o.release();
                this.o = null;
            }
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 1) {
            a(obj);
        } else if (i == 4) {
            this.q = ((Integer) obj).intValue();
            MediaCodecAdapter codec = getCodec();
            if (codec != null) {
                codec.setVideoScalingMode(this.q);
            }
        } else if (i == 6) {
            this.L = (VideoFrameMetadataListener) obj;
        } else if (i != 102) {
            super.handleMessage(i, obj);
        } else {
            int intValue = ((Integer) obj).intValue();
            if (this.K != intValue) {
                this.K = intValue;
                if (this.J) {
                    releaseCodec();
                }
            }
        }
    }

    private void a(@Nullable Object obj) throws ExoPlaybackException {
        DummySurface dummySurface = obj instanceof Surface ? (Surface) obj : null;
        if (dummySurface == null) {
            DummySurface dummySurface2 = this.o;
            if (dummySurface2 != null) {
                dummySurface = dummySurface2;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && a(codecInfo)) {
                    this.o = DummySurface.newInstanceV17(this.e, codecInfo.secure);
                    dummySurface = this.o;
                }
            }
        }
        if (this.n != dummySurface) {
            this.n = dummySurface;
            this.f.onSurfaceChanged(dummySurface);
            this.p = false;
            int state = getState();
            MediaCodecAdapter codec = getCodec();
            if (codec != null) {
                if (Util.SDK_INT < 23 || dummySurface == null || this.l) {
                    releaseCodec();
                    maybeInitCodecOrBypass();
                } else {
                    setOutputSurfaceV23(codec, dummySurface);
                }
            }
            if (dummySurface == null || dummySurface == this.o) {
                f();
                d();
                return;
            }
            h();
            d();
            if (state == 2) {
                c();
            }
        } else if (dummySurface != null && dummySurface != this.o) {
            h();
            e();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        return this.n != null || a(mediaCodecInfo);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean getCodecNeedsEosPropagation() {
        return this.J && Util.SDK_INT < 23;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @TargetApi(17)
    protected MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo mediaCodecInfo, Format format, @Nullable MediaCrypto mediaCrypto, float f) {
        DummySurface dummySurface = this.o;
        if (!(dummySurface == null || dummySurface.secure == mediaCodecInfo.secure)) {
            this.o.release();
            this.o = null;
        }
        String str = mediaCodecInfo.codecMimeType;
        this.k = getCodecMaxValues(mediaCodecInfo, format, getStreamFormats());
        MediaFormat mediaFormat = getMediaFormat(format, str, this.k, f, this.j, this.J ? this.K : 0);
        if (this.n == null) {
            if (a(mediaCodecInfo)) {
                if (this.o == null) {
                    this.o = DummySurface.newInstanceV17(this.e, mediaCodecInfo.secure);
                }
                this.n = this.o;
            } else {
                throw new IllegalStateException();
            }
        }
        return new MediaCodecAdapter.Configuration(mediaCodecInfo, mediaFormat, format, this.n, mediaCrypto, 0);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected DecoderReuseEvaluation canReuseCodec(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        DecoderReuseEvaluation canReuseCodec = mediaCodecInfo.canReuseCodec(format, format2);
        int i = canReuseCodec.discardReasons;
        if (format2.width > this.k.width || format2.height > this.k.height) {
            i |= 256;
        }
        int i2 = getMaxInputSize(mediaCodecInfo, format2) > this.k.inputSize ? i | 64 : i;
        return new DecoderReuseEvaluation(mediaCodecInfo.name, format, format2, i2 != 0 ? 0 : canReuseCodec.result, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public void resetCodecStateForFlush() {
        super.resetCodecStateForFlush();
        this.z = 0;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        super.setPlaybackSpeed(f, f2);
        this.f.onPlaybackSpeed(f);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected float getCodecOperatingRateV23(float f, Format format, Format[] formatArr) {
        float f2 = -1.0f;
        for (Format format2 : formatArr) {
            float f3 = format2.frameRate;
            if (f3 != -1.0f) {
                f2 = Math.max(f2, f3);
            }
        }
        if (f2 == -1.0f) {
            return -1.0f;
        }
        return f2 * f;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecInitialized(String str, long j, long j2) {
        this.g.decoderInitialized(str, j, j2);
        this.l = codecNeedsSetOutputSurfaceWorkaround(str);
        this.m = ((MediaCodecInfo) Assertions.checkNotNull(getCodecInfo())).isHdr10PlusOutOfBandMetadataSupported();
        if (Util.SDK_INT >= 23 && this.J) {
            this.a = new a((MediaCodecAdapter) Assertions.checkNotNull(getCodec()));
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecReleased(String str) {
        this.g.decoderReleased(str);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecError(Exception exc) {
        Log.e("MediaCodecVideoRenderer", "Video codec error", exc);
        this.g.videoCodecError(exc);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @Nullable
    public DecoderReuseEvaluation onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation onInputFormatChanged = super.onInputFormatChanged(formatHolder);
        this.g.inputFormatChanged(formatHolder.format, onInputFormatChanged);
        return onInputFormatChanged;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    protected void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        if (!this.J) {
            this.z++;
        }
        if (Util.SDK_INT < 23 && this.J) {
            onProcessedTunneledBuffer(decoderInputBuffer.timeUs);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onOutputFormatChanged(Format format, @Nullable MediaFormat mediaFormat) {
        int i;
        int i2;
        MediaCodecAdapter codec = getCodec();
        if (codec != null) {
            codec.setVideoScalingMode(this.q);
        }
        if (this.J) {
            this.E = format.width;
            this.F = format.height;
        } else {
            Assertions.checkNotNull(mediaFormat);
            boolean z = mediaFormat.containsKey("crop-right") && mediaFormat.containsKey("crop-left") && mediaFormat.containsKey("crop-bottom") && mediaFormat.containsKey("crop-top");
            if (z) {
                i = (mediaFormat.getInteger("crop-right") - mediaFormat.getInteger("crop-left")) + 1;
            } else {
                i = mediaFormat.getInteger("width");
            }
            this.E = i;
            if (z) {
                i2 = (mediaFormat.getInteger("crop-bottom") - mediaFormat.getInteger("crop-top")) + 1;
            } else {
                i2 = mediaFormat.getInteger("height");
            }
            this.F = i2;
        }
        this.H = format.pixelWidthHeightRatio;
        if (Util.SDK_INT < 21) {
            this.G = format.rotationDegrees;
        } else if (format.rotationDegrees == 90 || format.rotationDegrees == 270) {
            int i3 = this.E;
            this.E = this.F;
            this.F = i3;
            this.H = 1.0f / this.H;
        }
        this.f.onFormatChanged(format.frameRate);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @TargetApi(29)
    protected void handleInputBufferSupplementalData(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        if (this.m) {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.supplementalData);
            if (byteBuffer.remaining() >= 7) {
                byte b2 = byteBuffer.get();
                short s = byteBuffer.getShort();
                short s2 = byteBuffer.getShort();
                byte b3 = byteBuffer.get();
                byte b4 = byteBuffer.get();
                byteBuffer.position(0);
                if (b2 == -75 && s == 60 && s2 == 1 && b3 == 4 && b4 == 0) {
                    byte[] bArr = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bArr);
                    byteBuffer.position(0);
                    a(getCodec(), bArr);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean processOutputBuffer(long j, long j2, @Nullable MediaCodecAdapter mediaCodecAdapter, @Nullable ByteBuffer byteBuffer, int i, int i2, int i3, long j3, boolean z, boolean z2, Format format) throws ExoPlaybackException {
        long j4;
        boolean z3;
        long j5;
        Assertions.checkNotNull(mediaCodecAdapter);
        if (this.u == C.TIME_UNSET) {
            this.u = j;
        }
        if (j3 != this.A) {
            this.f.onNextFrame(j3);
            this.A = j3;
        }
        long outputStreamOffsetUs = getOutputStreamOffsetUs();
        long j6 = j3 - outputStreamOffsetUs;
        if (!z || z2) {
            double playbackSpeed = getPlaybackSpeed();
            boolean z4 = getState() == 2;
            long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
            long j7 = (long) ((j3 - j) / playbackSpeed);
            if (z4) {
                j7 -= elapsedRealtime - j2;
            }
            if (this.n != this.o) {
                long j8 = elapsedRealtime - this.B;
                if (!this.t) {
                    if (z4 || this.s) {
                        z3 = true;
                        j4 = j8;
                    } else {
                        j4 = j8;
                        z3 = false;
                    }
                } else if (!this.r) {
                    z3 = true;
                    j4 = j8;
                } else {
                    j4 = j8;
                    z3 = false;
                }
                if (this.v == C.TIME_UNSET && j >= outputStreamOffsetUs && (z3 || (z4 && shouldForceRenderOutputBuffer(j7, j4)))) {
                    long nanoTime = System.nanoTime();
                    a(j6, nanoTime, format);
                    if (Util.SDK_INT >= 21) {
                        renderOutputBufferV21(mediaCodecAdapter, i, j6, nanoTime);
                    } else {
                        renderOutputBuffer(mediaCodecAdapter, i, j6);
                    }
                    updateVideoFrameProcessingOffsetCounters(j7);
                    return true;
                } else if (!z4 || j == this.u) {
                    return false;
                } else {
                    long nanoTime2 = System.nanoTime();
                    long adjustReleaseTime = this.f.adjustReleaseTime((j7 * 1000) + nanoTime2);
                    long j9 = (adjustReleaseTime - nanoTime2) / 1000;
                    boolean z5 = this.v != C.TIME_UNSET;
                    if (shouldDropBuffersToKeyframe(j9, j2, z2) && maybeDropBuffersToKeyframe(j, z5)) {
                        return false;
                    }
                    if (shouldDropOutputBuffer(j9, j2, z2)) {
                        if (z5) {
                            skipOutputBuffer(mediaCodecAdapter, i, j6);
                            j5 = j9;
                        } else {
                            dropOutputBuffer(mediaCodecAdapter, i, j6);
                            j5 = j9;
                        }
                        updateVideoFrameProcessingOffsetCounters(j5);
                        return true;
                    }
                    if (Util.SDK_INT >= 21) {
                        if (j9 < 50000) {
                            a(j6, adjustReleaseTime, format);
                            renderOutputBufferV21(mediaCodecAdapter, i, j6, adjustReleaseTime);
                            updateVideoFrameProcessingOffsetCounters(j9);
                            return true;
                        }
                    } else if (j9 < 30000) {
                        if (j9 > 11000) {
                            try {
                                Thread.sleep((j9 - 10000) / 1000);
                            } catch (InterruptedException unused) {
                                Thread.currentThread().interrupt();
                                return false;
                            }
                        }
                        a(j6, adjustReleaseTime, format);
                        renderOutputBuffer(mediaCodecAdapter, i, j6);
                        updateVideoFrameProcessingOffsetCounters(j9);
                        return true;
                    }
                    return false;
                }
            } else if (!a(j7)) {
                return false;
            } else {
                skipOutputBuffer(mediaCodecAdapter, i, j6);
                updateVideoFrameProcessingOffsetCounters(j7);
                return true;
            }
        } else {
            skipOutputBuffer(mediaCodecAdapter, i, j6);
            return true;
        }
    }

    private void a(long j, long j2, Format format) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.L;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j, j2, format, getCodecOutputMediaFormat());
        }
    }

    protected void onProcessedTunneledBuffer(long j) throws ExoPlaybackException {
        updateOutputFormatForTime(j);
        g();
        this.decoderCounters.renderedOutputBufferCount++;
        a();
        onProcessedOutputBuffer(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        setPendingOutputEndOfStream();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public void onProcessedOutputBuffer(long j) {
        super.onProcessedOutputBuffer(j);
        if (!this.J) {
            this.z--;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public void onProcessedStreamChange() {
        super.onProcessedStreamChange();
        d();
    }

    protected boolean shouldDropOutputBuffer(long j, long j2, boolean z) {
        return a(j) && !z;
    }

    protected boolean shouldDropBuffersToKeyframe(long j, long j2, boolean z) {
        return b(j) && !z;
    }

    protected boolean shouldForceRenderOutputBuffer(long j, long j2) {
        return a(j) && j2 > 100000;
    }

    protected void skipOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i, long j) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        this.decoderCounters.skippedOutputBufferCount++;
    }

    protected void dropOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i, long j) {
        TraceUtil.beginSection("dropVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        updateDroppedBufferCounters(1);
    }

    protected boolean maybeDropBuffersToKeyframe(long j, boolean z) throws ExoPlaybackException {
        int skipSource = skipSource(j);
        if (skipSource == 0) {
            return false;
        }
        this.decoderCounters.droppedToKeyframeCount++;
        int i = this.z + skipSource;
        if (z) {
            this.decoderCounters.skippedOutputBufferCount += i;
        } else {
            updateDroppedBufferCounters(i);
        }
        flushOrReinitializeCodec();
        return true;
    }

    protected void updateDroppedBufferCounters(int i) {
        this.decoderCounters.droppedBufferCount += i;
        this.x += i;
        this.y += i;
        this.decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(this.y, this.decoderCounters.maxConsecutiveDroppedBufferCount);
        int i2 = this.i;
        if (i2 > 0 && this.x >= i2) {
            i();
        }
    }

    protected void updateVideoFrameProcessingOffsetCounters(long j) {
        this.decoderCounters.addVideoFrameProcessingOffset(j);
        this.C += j;
        this.D++;
    }

    protected void renderOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i, long j) {
        g();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, true);
        TraceUtil.endSection();
        this.B = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.y = 0;
        a();
    }

    @RequiresApi(21)
    protected void renderOutputBufferV21(MediaCodecAdapter mediaCodecAdapter, int i, long j, long j2) {
        g();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, j2);
        TraceUtil.endSection();
        this.B = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.y = 0;
        a();
    }

    private boolean a(MediaCodecInfo mediaCodecInfo) {
        return Util.SDK_INT >= 23 && !this.J && !codecNeedsSetOutputSurfaceWorkaround(mediaCodecInfo.name) && (!mediaCodecInfo.secure || DummySurface.isSecureSupported(this.e));
    }

    private void c() {
        this.v = this.h > 0 ? SystemClock.elapsedRealtime() + this.h : C.TIME_UNSET;
    }

    private void d() {
        MediaCodecAdapter codec;
        this.r = false;
        if (Util.SDK_INT >= 23 && this.J && (codec = getCodec()) != null) {
            this.a = new a(codec);
        }
    }

    void a() {
        this.t = true;
        if (!this.r) {
            this.r = true;
            this.g.renderedFirstFrame(this.n);
            this.p = true;
        }
    }

    private void e() {
        if (this.p) {
            this.g.renderedFirstFrame(this.n);
        }
    }

    private void f() {
        this.I = null;
    }

    private void g() {
        if (this.E != -1 || this.F != -1) {
            VideoSize videoSize = this.I;
            if (videoSize == null || videoSize.width != this.E || this.I.height != this.F || this.I.unappliedRotationDegrees != this.G || this.I.pixelWidthHeightRatio != this.H) {
                this.I = new VideoSize(this.E, this.F, this.G, this.H);
                this.g.videoSizeChanged(this.I);
            }
        }
    }

    private void h() {
        VideoSize videoSize = this.I;
        if (videoSize != null) {
            this.g.videoSizeChanged(videoSize);
        }
    }

    private void i() {
        if (this.x > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.g.droppedFrames(this.x, elapsedRealtime - this.w);
            this.x = 0;
            this.w = elapsedRealtime;
        }
    }

    private void j() {
        int i = this.D;
        if (i != 0) {
            this.g.reportVideoFrameProcessingOffset(this.C, i);
            this.C = 0L;
            this.D = 0;
        }
    }

    @RequiresApi(29)
    private static void a(MediaCodecAdapter mediaCodecAdapter, byte[] bArr) {
        Bundle bundle = new Bundle();
        bundle.putByteArray("hdr10-plus-info", bArr);
        mediaCodecAdapter.setParameters(bundle);
    }

    @RequiresApi(23)
    protected void setOutputSurfaceV23(MediaCodecAdapter mediaCodecAdapter, Surface surface) {
        mediaCodecAdapter.setOutputSurface(surface);
    }

    @RequiresApi(21)
    private static void a(MediaFormat mediaFormat, int i) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", i);
    }

    @SuppressLint({"InlinedApi"})
    @TargetApi(21)
    protected MediaFormat getMediaFormat(Format format, String str, CodecMaxValues codecMaxValues, float f, boolean z, int i) {
        Pair<Integer, Integer> codecProfileAndLevel;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", str);
        mediaFormat.setInteger("width", format.width);
        mediaFormat.setInteger("height", format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            MediaFormatUtil.maybeSetInteger(mediaFormat, "profile", ((Integer) codecProfileAndLevel.first).intValue());
        }
        mediaFormat.setInteger("max-width", codecMaxValues.width);
        mediaFormat.setInteger("max-height", codecMaxValues.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger("priority", 0);
            if (f != -1.0f) {
                mediaFormat.setFloat("operating-rate", f);
            }
        }
        if (z) {
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (i != 0) {
            a(mediaFormat, i);
        }
        return mediaFormat;
    }

    protected CodecMaxValues getCodecMaxValues(MediaCodecInfo mediaCodecInfo, Format format, Format[] formatArr) {
        int b2;
        int i = format.width;
        int i2 = format.height;
        int maxInputSize = getMaxInputSize(mediaCodecInfo, format);
        if (formatArr.length == 1) {
            if (!(maxInputSize == -1 || (b2 = b(mediaCodecInfo, format)) == -1)) {
                maxInputSize = Math.min((int) (maxInputSize * 1.5f), b2);
            }
            return new CodecMaxValues(i, i2, maxInputSize);
        }
        int length = formatArr.length;
        int i3 = i2;
        int i4 = maxInputSize;
        boolean z = false;
        int i5 = i;
        for (int i6 = 0; i6 < length; i6++) {
            Format format2 = formatArr[i6];
            if (format.colorInfo != null && format2.colorInfo == null) {
                format2 = format2.buildUpon().setColorInfo(format.colorInfo).build();
            }
            if (mediaCodecInfo.canReuseCodec(format, format2).result != 0) {
                z |= format2.width == -1 || format2.height == -1;
                i5 = Math.max(i5, format2.width);
                i3 = Math.max(i3, format2.height);
                i4 = Math.max(i4, getMaxInputSize(mediaCodecInfo, format2));
            }
        }
        if (z) {
            StringBuilder sb = new StringBuilder(66);
            sb.append("Resolutions unknown. Codec max resolution: ");
            sb.append(i5);
            sb.append("x");
            sb.append(i3);
            Log.w("MediaCodecVideoRenderer", sb.toString());
            Point a2 = a(mediaCodecInfo, format);
            if (a2 != null) {
                i5 = Math.max(i5, a2.x);
                i3 = Math.max(i3, a2.y);
                i4 = Math.max(i4, b(mediaCodecInfo, format.buildUpon().setWidth(i5).setHeight(i3).build()));
                StringBuilder sb2 = new StringBuilder(57);
                sb2.append("Codec max resolution adjusted to: ");
                sb2.append(i5);
                sb2.append("x");
                sb2.append(i3);
                Log.w("MediaCodecVideoRenderer", sb2.toString());
            }
        }
        return new CodecMaxValues(i5, i3, i4);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected MediaCodecDecoderException createDecoderException(Throwable th, @Nullable MediaCodecInfo mediaCodecInfo) {
        return new MediaCodecVideoDecoderException(th, mediaCodecInfo, this.n);
    }

    private static Point a(MediaCodecInfo mediaCodecInfo, Format format) {
        boolean z = format.height > format.width;
        int i = z ? format.height : format.width;
        int i2 = z ? format.width : format.height;
        float f = i2 / i;
        int[] iArr = b;
        for (int i3 : iArr) {
            int i4 = (int) (i3 * f);
            if (i3 <= i || i4 <= i2) {
                return null;
            }
            if (Util.SDK_INT >= 21) {
                int i5 = z ? i4 : i3;
                if (!z) {
                    i3 = i4;
                }
                Point alignVideoSizeV21 = mediaCodecInfo.alignVideoSizeV21(i5, i3);
                if (mediaCodecInfo.isVideoSizeAndRateSupportedV21(alignVideoSizeV21.x, alignVideoSizeV21.y, format.frameRate)) {
                    return alignVideoSizeV21;
                }
            } else {
                try {
                    int ceilDivide = Util.ceilDivide(i3, 16) * 16;
                    int ceilDivide2 = Util.ceilDivide(i4, 16) * 16;
                    if (ceilDivide * ceilDivide2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        int i6 = z ? ceilDivide2 : ceilDivide;
                        if (!z) {
                            ceilDivide = ceilDivide2;
                        }
                        return new Point(i6, ceilDivide);
                    }
                } catch (MediaCodecUtil.DecoderQueryException unused) {
                    return null;
                }
            }
        }
        return null;
    }

    protected static int getMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        if (format.maxInputSize == -1) {
            return b(mediaCodecInfo, format);
        }
        int size = format.initializationData.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += format.initializationData.get(i2).length;
        }
        return format.maxInputSize + i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int b(MediaCodecInfo mediaCodecInfo, Format format) {
        char c2;
        int i;
        int intValue;
        int i2 = format.width;
        int i3 = format.height;
        if (i2 == -1 || i3 == -1) {
            return -1;
        }
        String str = format.sampleMimeType;
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(str)) {
            str = MimeTypes.VIDEO_H265;
            Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
            if (codecProfileAndLevel != null && ((intValue = ((Integer) codecProfileAndLevel.first).intValue()) == 512 || intValue == 1 || intValue == 2)) {
                str = MimeTypes.VIDEO_H264;
            }
        }
        int i4 = 4;
        switch (str.hashCode()) {
            case -1664118616:
                if (str.equals("video/3gpp")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1662541442:
                if (str.equals(MimeTypes.VIDEO_H265)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 1187890754:
                if (str.equals(MimeTypes.VIDEO_MP4V)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 1331836730:
                if (str.equals(MimeTypes.VIDEO_H264)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1599127256:
                if (str.equals(MimeTypes.VIDEO_VP8)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 1599127257:
                if (str.equals(MimeTypes.VIDEO_VP9)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
                i = i2 * i3;
                i4 = 2;
                break;
            case 2:
                if (!"BRAVIA 4K 2015".equals(Util.MODEL) && (!"Amazon".equals(Util.MANUFACTURER) || (!"KFSOWI".equals(Util.MODEL) && (!"AFTS".equals(Util.MODEL) || !mediaCodecInfo.secure)))) {
                    i = Util.ceilDivide(i2, 16) * Util.ceilDivide(i3, 16) * 16 * 16;
                    i4 = 2;
                    break;
                } else {
                    return -1;
                }
                break;
            case 3:
                i = i2 * i3;
                i4 = 2;
                break;
            case 4:
            case 5:
                i = i2 * i3;
                break;
            default:
                return -1;
        }
        return (i * 3) / (i4 * 2);
    }

    private static boolean k() {
        return "NVIDIA".equals(Util.MANUFACTURER);
    }

    protected boolean codecNeedsSetOutputSurfaceWorkaround(String str) {
        if (str.startsWith("OMX.google")) {
            return false;
        }
        synchronized (MediaCodecVideoRenderer.class) {
            if (!c) {
                d = l();
                c = true;
            }
        }
        return d;
    }

    protected Surface getSurface() {
        return this.n;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int i, int i2, int i3) {
            this.width = i;
            this.height = i2;
            this.inputSize = i3;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0344, code lost:
        if (r0.equals("602LV") != false) goto L_0x0707;
     */
    /* JADX WARN: Code restructure failed: missing block: B:475:0x073c, code lost:
        if (r0.equals("JSN-L21") != false) goto L_0x0740;
     */
    /* JADX WARN: Removed duplicated region for block: B:478:0x0744 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean l() {
        /*
            Method dump skipped, instructions count: 2766
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.l():boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(23)
    /* loaded from: classes2.dex */
    public final class a implements Handler.Callback, MediaCodecAdapter.OnFrameRenderedListener {
        private final Handler b = Util.createHandlerForCurrentLooper(this);

        public a(MediaCodecAdapter mediaCodecAdapter) {
            mediaCodecAdapter.setOnFrameRenderedListener(this, this.b);
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.OnFrameRenderedListener
        public void onFrameRendered(MediaCodecAdapter mediaCodecAdapter, long j, long j2) {
            if (Util.SDK_INT < 30) {
                this.b.sendMessageAtFrontOfQueue(Message.obtain(this.b, 0, (int) (j >> 32), (int) j));
                return;
            }
            a(j);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            a(Util.toLong(message.arg1, message.arg2));
            return true;
        }

        private void a(long j) {
            if (this == MediaCodecVideoRenderer.this.a) {
                if (j == Long.MAX_VALUE) {
                    MediaCodecVideoRenderer.this.b();
                    return;
                }
                try {
                    MediaCodecVideoRenderer.this.onProcessedTunneledBuffer(j);
                } catch (ExoPlaybackException e) {
                    MediaCodecVideoRenderer.this.setPendingPlaybackException(e);
                }
            }
        }
    }
}
