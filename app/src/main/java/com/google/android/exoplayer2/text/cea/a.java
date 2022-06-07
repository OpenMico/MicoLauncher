package com.google.android.exoplayer2.text.cea;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.text.cea.a;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

/* compiled from: CeaDecoder.java */
/* loaded from: classes2.dex */
abstract class a implements SubtitleDecoder {
    private final ArrayDeque<C0070a> a = new ArrayDeque<>();
    private final ArrayDeque<SubtitleOutputBuffer> b;
    private final PriorityQueue<C0070a> c;
    @Nullable
    private C0070a d;
    private long e;
    private long f;

    protected abstract Subtitle createSubtitle();

    protected abstract void decode(SubtitleInputBuffer subtitleInputBuffer);

    @Override // com.google.android.exoplayer2.decoder.Decoder
    public abstract String getName();

    protected abstract boolean isNewSubtitleDataAvailable();

    @Override // com.google.android.exoplayer2.decoder.Decoder
    public void release() {
    }

    public a() {
        for (int i = 0; i < 10; i++) {
            this.a.add(new C0070a());
        }
        this.b = new ArrayDeque<>();
        for (int i2 = 0; i2 < 2; i2++) {
            this.b.add(new b(new OutputBuffer.Owner() { // from class: com.google.android.exoplayer2.text.cea.-$$Lambda$UPiildU-sY_onz8-Bp7FgseinZo
                @Override // com.google.android.exoplayer2.decoder.OutputBuffer.Owner
                public final void releaseOutputBuffer(OutputBuffer outputBuffer) {
                    a.this.releaseOutputBuffer((a.b) outputBuffer);
                }
            }));
        }
        this.c = new PriorityQueue<>();
    }

    @Override // com.google.android.exoplayer2.text.SubtitleDecoder
    public void setPositionUs(long j) {
        this.e = j;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        Assertions.checkState(this.d == null);
        if (this.a.isEmpty()) {
            return null;
        }
        this.d = this.a.pollFirst();
        return this.d;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    public void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        Assertions.checkArgument(subtitleInputBuffer == this.d);
        C0070a aVar = (C0070a) subtitleInputBuffer;
        if (aVar.isDecodeOnly()) {
            a(aVar);
        } else {
            long j = this.f;
            this.f = 1 + j;
            aVar.a = j;
            this.c.add(aVar);
        }
        this.d = null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        if (this.b.isEmpty()) {
            return null;
        }
        while (!this.c.isEmpty() && ((C0070a) Util.castNonNull(this.c.peek())).timeUs <= this.e) {
            C0070a aVar = (C0070a) Util.castNonNull(this.c.poll());
            if (aVar.isEndOfStream()) {
                SubtitleOutputBuffer subtitleOutputBuffer = (SubtitleOutputBuffer) Util.castNonNull(this.b.pollFirst());
                subtitleOutputBuffer.addFlag(4);
                a(aVar);
                return subtitleOutputBuffer;
            }
            decode(aVar);
            if (isNewSubtitleDataAvailable()) {
                Subtitle createSubtitle = createSubtitle();
                SubtitleOutputBuffer subtitleOutputBuffer2 = (SubtitleOutputBuffer) Util.castNonNull(this.b.pollFirst());
                subtitleOutputBuffer2.setContent(aVar.timeUs, createSubtitle, Long.MAX_VALUE);
                a(aVar);
                return subtitleOutputBuffer2;
            }
            a(aVar);
        }
        return null;
    }

    private void a(C0070a aVar) {
        aVar.clear();
        this.a.add(aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void releaseOutputBuffer(SubtitleOutputBuffer subtitleOutputBuffer) {
        subtitleOutputBuffer.clear();
        this.b.add(subtitleOutputBuffer);
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    public void flush() {
        this.f = 0L;
        this.e = 0L;
        while (!this.c.isEmpty()) {
            a((C0070a) Util.castNonNull(this.c.poll()));
        }
        C0070a aVar = this.d;
        if (aVar != null) {
            a(aVar);
            this.d = null;
        }
    }

    @Nullable
    protected final SubtitleOutputBuffer getAvailableOutputBuffer() {
        return this.b.pollFirst();
    }

    protected final long getPositionUs() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CeaDecoder.java */
    /* renamed from: com.google.android.exoplayer2.text.cea.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static final class C0070a extends SubtitleInputBuffer implements Comparable<C0070a> {
        private long a;

        private C0070a() {
        }

        /* renamed from: a */
        public int compareTo(C0070a aVar) {
            if (isEndOfStream() != aVar.isEndOfStream()) {
                return isEndOfStream() ? 1 : -1;
            }
            long j = this.timeUs - aVar.timeUs;
            if (j == 0) {
                j = this.a - aVar.a;
                if (j == 0) {
                    return 0;
                }
            }
            return j > 0 ? 1 : -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CeaDecoder.java */
    /* loaded from: classes2.dex */
    public static final class b extends SubtitleOutputBuffer {
        private OutputBuffer.Owner<b> a;

        public b(OutputBuffer.Owner<b> owner) {
            this.a = owner;
        }

        @Override // com.google.android.exoplayer2.decoder.OutputBuffer
        public final void release() {
            this.a.releaseOutputBuffer(this);
        }
    }
}
