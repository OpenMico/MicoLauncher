package com.google.android.exoplayer2.source.chunk;

import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public abstract class BaseMediaChunkIterator implements MediaChunkIterator {
    private final long a;
    private final long b;
    private long c;

    public BaseMediaChunkIterator(long j, long j2) {
        this.a = j;
        this.b = j2;
        reset();
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
    public boolean isEnded() {
        return this.c > this.b;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
    public boolean next() {
        this.c++;
        return !isEnded();
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
    public void reset() {
        this.c = this.a - 1;
    }

    protected final void checkInBounds() {
        long j = this.c;
        if (j < this.a || j > this.b) {
            throw new NoSuchElementException();
        }
    }

    protected final long getCurrentIndex() {
        return this.c;
    }
}
