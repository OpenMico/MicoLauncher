package com.google.android.exoplayer2.decoder;

/* loaded from: classes.dex */
public final class DecoderCounters {
    public int decoderInitCount;
    public int decoderReleaseCount;
    public int droppedBufferCount;
    public int droppedToKeyframeCount;
    public int inputBufferCount;
    public int maxConsecutiveDroppedBufferCount;
    public int renderedOutputBufferCount;
    public int skippedInputBufferCount;
    public int skippedOutputBufferCount;
    public long totalVideoFrameProcessingOffsetUs;
    public int videoFrameProcessingOffsetCount;

    public synchronized void ensureUpdated() {
    }

    public void merge(DecoderCounters decoderCounters) {
        this.decoderInitCount += decoderCounters.decoderInitCount;
        this.decoderReleaseCount += decoderCounters.decoderReleaseCount;
        this.inputBufferCount += decoderCounters.inputBufferCount;
        this.skippedInputBufferCount += decoderCounters.skippedInputBufferCount;
        this.renderedOutputBufferCount += decoderCounters.renderedOutputBufferCount;
        this.skippedOutputBufferCount += decoderCounters.skippedOutputBufferCount;
        this.droppedBufferCount += decoderCounters.droppedBufferCount;
        this.maxConsecutiveDroppedBufferCount = Math.max(this.maxConsecutiveDroppedBufferCount, decoderCounters.maxConsecutiveDroppedBufferCount);
        this.droppedToKeyframeCount += decoderCounters.droppedToKeyframeCount;
        a(decoderCounters.totalVideoFrameProcessingOffsetUs, decoderCounters.videoFrameProcessingOffsetCount);
    }

    public void addVideoFrameProcessingOffset(long j) {
        a(j, 1);
    }

    private void a(long j, int i) {
        this.totalVideoFrameProcessingOffsetUs += j;
        this.videoFrameProcessingOffsetCount += i;
    }
}
