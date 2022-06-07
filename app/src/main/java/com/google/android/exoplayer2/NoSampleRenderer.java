package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaClock;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class NoSampleRenderer implements Renderer, RendererCapabilities {
    private RendererConfiguration a;
    private int b;
    private int c;
    @Nullable
    private SampleStream d;
    private boolean e;

    @Override // com.google.android.exoplayer2.Renderer
    public final RendererCapabilities getCapabilities() {
        return this;
    }

    @Override // com.google.android.exoplayer2.Renderer
    @Nullable
    public MediaClock getMediaClock() {
        return null;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public long getReadingPositionUs() {
        return Long.MIN_VALUE;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public final int getTrackType() {
        return 7;
    }

    @Override // com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean hasReadStreamToEnd() {
        return true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void maybeThrowStreamError() throws IOException {
    }

    protected void onDisabled() {
    }

    protected void onEnabled(boolean z) throws ExoPlaybackException {
    }

    protected void onPositionReset(long j, boolean z) throws ExoPlaybackException {
    }

    protected void onRendererOffsetChanged(long j) throws ExoPlaybackException {
    }

    protected void onReset() {
    }

    protected void onStarted() throws ExoPlaybackException {
    }

    protected void onStopped() {
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException {
        return 0;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void setIndex(int i) {
        this.b = i;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final int getState() {
        return this.c;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void enable(RendererConfiguration rendererConfiguration, Format[] formatArr, SampleStream sampleStream, long j, boolean z, boolean z2, long j2, long j3) throws ExoPlaybackException {
        Assertions.checkState(this.c == 0);
        this.a = rendererConfiguration;
        this.c = 1;
        onEnabled(z);
        replaceStream(formatArr, sampleStream, j2, j3);
        onPositionReset(j, z);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void start() throws ExoPlaybackException {
        boolean z = true;
        if (this.c != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.c = 2;
        onStarted();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void replaceStream(Format[] formatArr, SampleStream sampleStream, long j, long j2) throws ExoPlaybackException {
        Assertions.checkState(!this.e);
        this.d = sampleStream;
        onRendererOffsetChanged(j2);
    }

    @Override // com.google.android.exoplayer2.Renderer
    @Nullable
    public final SampleStream getStream() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void setCurrentStreamFinal() {
        this.e = true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean isCurrentStreamFinal() {
        return this.e;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void resetPosition(long j) throws ExoPlaybackException {
        this.e = false;
        onPositionReset(j, false);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void stop() {
        Assertions.checkState(this.c == 2);
        this.c = 1;
        onStopped();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void disable() {
        boolean z = true;
        if (this.c != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.c = 0;
        this.d = null;
        this.e = false;
        onDisabled();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final void reset() {
        Assertions.checkState(this.c == 0);
        onReset();
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) throws ExoPlaybackException {
        return RendererCapabilities.create(0);
    }

    @Nullable
    protected final RendererConfiguration getConfiguration() {
        return this.a;
    }

    protected final int getIndex() {
        return this.b;
    }
}
