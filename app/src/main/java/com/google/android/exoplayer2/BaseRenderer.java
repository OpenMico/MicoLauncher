package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaClock;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class BaseRenderer implements Renderer, RendererCapabilities {
    private final int a;
    @Nullable
    private RendererConfiguration c;
    private int d;
    private int e;
    @Nullable
    private SampleStream f;
    @Nullable
    private Format[] g;
    private long h;
    private long i;
    private boolean k;
    private boolean l;
    private final FormatHolder b = new FormatHolder();
    private long j = Long.MIN_VALUE;

    @Override // com.google.android.exoplayer2.Renderer
    public final RendererCapabilities getCapabilities() {
        return this;
    }

    @Override // com.google.android.exoplayer2.Renderer
    @Nullable
    public MediaClock getMediaClock() {
        return null;
    }

    @Override // com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
    }

    protected void onDisabled() {
    }

    protected void onEnabled(boolean z, boolean z2) throws ExoPlaybackException {
    }

    protected void onPositionReset(long j, boolean z) throws ExoPlaybackException {
    }

    protected void onReset() {
    }

    protected void onStarted() throws ExoPlaybackException {
    }

    protected void onStopped() {
    }

    public void onStreamChanged(Format[] formatArr, long j, long j2) throws ExoPlaybackException {
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException {
        return 0;
    }

    public BaseRenderer(int i) {
        this.a = i;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public final int getTrackType() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void setIndex(int i) {
        this.d = i;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final int getState() {
        return this.e;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void enable(RendererConfiguration rendererConfiguration, Format[] formatArr, SampleStream sampleStream, long j, boolean z, boolean z2, long j2, long j3) throws ExoPlaybackException {
        Assertions.checkState(this.e == 0);
        this.c = rendererConfiguration;
        this.e = 1;
        this.i = j;
        onEnabled(z, z2);
        replaceStream(formatArr, sampleStream, j2, j3);
        onPositionReset(j, z);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void start() throws ExoPlaybackException {
        boolean z = true;
        if (this.e != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.e = 2;
        onStarted();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void replaceStream(Format[] formatArr, SampleStream sampleStream, long j, long j2) throws ExoPlaybackException {
        Assertions.checkState(!this.k);
        this.f = sampleStream;
        if (this.j == Long.MIN_VALUE) {
            this.j = j;
        }
        this.g = formatArr;
        this.h = j2;
        onStreamChanged(formatArr, j, j2);
    }

    @Override // com.google.android.exoplayer2.Renderer
    @Nullable
    public final SampleStream getStream() {
        return this.f;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean hasReadStreamToEnd() {
        return this.j == Long.MIN_VALUE;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final long getReadingPositionUs() {
        return this.j;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void setCurrentStreamFinal() {
        this.k = true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean isCurrentStreamFinal() {
        return this.k;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void maybeThrowStreamError() throws IOException {
        ((SampleStream) Assertions.checkNotNull(this.f)).maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void resetPosition(long j) throws ExoPlaybackException {
        this.k = false;
        this.i = j;
        this.j = j;
        onPositionReset(j, false);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void stop() {
        Assertions.checkState(this.e == 2);
        this.e = 1;
        onStopped();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void disable() {
        boolean z = true;
        if (this.e != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.b.clear();
        this.e = 0;
        this.f = null;
        this.g = null;
        this.k = false;
        onDisabled();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void reset() {
        Assertions.checkState(this.e == 0);
        this.b.clear();
        onReset();
    }

    protected final long getLastResetPositionUs() {
        return this.i;
    }

    protected final FormatHolder getFormatHolder() {
        this.b.clear();
        return this.b;
    }

    protected final Format[] getStreamFormats() {
        return (Format[]) Assertions.checkNotNull(this.g);
    }

    protected final RendererConfiguration getConfiguration() {
        return (RendererConfiguration) Assertions.checkNotNull(this.c);
    }

    protected final int getIndex() {
        return this.d;
    }

    protected final ExoPlaybackException createRendererException(Throwable th, @Nullable Format format, int i) {
        return createRendererException(th, format, false, i);
    }

    protected final ExoPlaybackException createRendererException(Throwable th, @Nullable Format format, boolean z, int i) {
        int i2;
        if (format != null && !this.l) {
            this.l = true;
            try {
                i2 = RendererCapabilities.getFormatSupport(supportsFormat(format));
            } catch (ExoPlaybackException unused) {
            } finally {
                this.l = false;
            }
            return ExoPlaybackException.createForRenderer(th, getName(), getIndex(), format, i2, z, i);
        }
        i2 = 4;
        return ExoPlaybackException.createForRenderer(th, getName(), getIndex(), format, i2, z, i);
    }

    protected final int readSource(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
        int readData = ((SampleStream) Assertions.checkNotNull(this.f)).readData(formatHolder, decoderInputBuffer, i);
        if (readData == -4) {
            if (decoderInputBuffer.isEndOfStream()) {
                this.j = Long.MIN_VALUE;
                return this.k ? -4 : -3;
            }
            decoderInputBuffer.timeUs += this.h;
            this.j = Math.max(this.j, decoderInputBuffer.timeUs);
        } else if (readData == -5) {
            Format format = (Format) Assertions.checkNotNull(formatHolder.format);
            if (format.subsampleOffsetUs != Long.MAX_VALUE) {
                formatHolder.format = format.buildUpon().setSubsampleOffsetUs(format.subsampleOffsetUs + this.h).build();
            }
        }
        return readData;
    }

    protected int skipSource(long j) {
        return ((SampleStream) Assertions.checkNotNull(this.f)).skipData(j - this.h);
    }

    protected final boolean isSourceReady() {
        return hasReadStreamToEnd() ? this.k : ((SampleStream) Assertions.checkNotNull(this.f)).isReady();
    }
}
