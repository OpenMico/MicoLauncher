package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class BundledExtractorsAdapter implements ProgressiveMediaExtractor {
    private final ExtractorsFactory a;
    @Nullable
    private Extractor b;
    @Nullable
    private ExtractorInput c;

    public BundledExtractorsAdapter(ExtractorsFactory extractorsFactory) {
        this.a = extractorsFactory;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
        if (r6.getPosition() != r11) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006f, code lost:
        if (r6.getPosition() != r11) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0072, code lost:
        r1 = false;
     */
    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void init(com.google.android.exoplayer2.upstream.DataReader r8, android.net.Uri r9, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r10, long r11, long r13, com.google.android.exoplayer2.extractor.ExtractorOutput r15) throws java.io.IOException {
        /*
            r7 = this;
            com.google.android.exoplayer2.extractor.DefaultExtractorInput r6 = new com.google.android.exoplayer2.extractor.DefaultExtractorInput
            r0 = r6
            r1 = r8
            r2 = r11
            r4 = r13
            r0.<init>(r1, r2, r4)
            r7.c = r6
            com.google.android.exoplayer2.extractor.Extractor r8 = r7.b
            if (r8 == 0) goto L_0x0010
            return
        L_0x0010:
            com.google.android.exoplayer2.extractor.ExtractorsFactory r8 = r7.a
            com.google.android.exoplayer2.extractor.Extractor[] r8 = r8.createExtractors(r9, r10)
            int r10 = r8.length
            r13 = 0
            r14 = 1
            if (r10 != r14) goto L_0x0021
            r8 = r8[r13]
            r7.b = r8
            goto L_0x0082
        L_0x0021:
            int r10 = r8.length
            r0 = r13
        L_0x0023:
            if (r0 >= r10) goto L_0x007e
            r1 = r8[r0]
            boolean r2 = r1.sniff(r6)     // Catch: EOFException -> 0x0065, all -> 0x0050
            if (r2 == 0) goto L_0x0043
            r7.b = r1     // Catch: EOFException -> 0x0065, all -> 0x0050
            com.google.android.exoplayer2.extractor.Extractor r10 = r7.b
            if (r10 != 0) goto L_0x003b
            long r0 = r6.getPosition()
            int r10 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r10 != 0) goto L_0x003c
        L_0x003b:
            r13 = r14
        L_0x003c:
            com.google.android.exoplayer2.util.Assertions.checkState(r13)
            r6.resetPeekPosition()
            goto L_0x007e
        L_0x0043:
            com.google.android.exoplayer2.extractor.Extractor r1 = r7.b
            if (r1 != 0) goto L_0x0074
            long r1 = r6.getPosition()
            int r1 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r1 != 0) goto L_0x0072
            goto L_0x0074
        L_0x0050:
            r8 = move-exception
            com.google.android.exoplayer2.extractor.Extractor r9 = r7.b
            if (r9 != 0) goto L_0x005d
            long r9 = r6.getPosition()
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 != 0) goto L_0x005e
        L_0x005d:
            r13 = r14
        L_0x005e:
            com.google.android.exoplayer2.util.Assertions.checkState(r13)
            r6.resetPeekPosition()
            throw r8
        L_0x0065:
            com.google.android.exoplayer2.extractor.Extractor r1 = r7.b
            if (r1 != 0) goto L_0x0074
            long r1 = r6.getPosition()
            int r1 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r1 != 0) goto L_0x0072
            goto L_0x0074
        L_0x0072:
            r1 = r13
            goto L_0x0075
        L_0x0074:
            r1 = r14
        L_0x0075:
            com.google.android.exoplayer2.util.Assertions.checkState(r1)
            r6.resetPeekPosition()
            int r0 = r0 + 1
            goto L_0x0023
        L_0x007e:
            com.google.android.exoplayer2.extractor.Extractor r10 = r7.b
            if (r10 == 0) goto L_0x0088
        L_0x0082:
            com.google.android.exoplayer2.extractor.Extractor r8 = r7.b
            r8.init(r15)
            return
        L_0x0088:
            com.google.android.exoplayer2.source.UnrecognizedInputFormatException r10 = new com.google.android.exoplayer2.source.UnrecognizedInputFormatException
            java.lang.String r8 = com.google.android.exoplayer2.util.Util.getCommaDelimitedSimpleClassNames(r8)
            java.lang.String r11 = java.lang.String.valueOf(r8)
            int r11 = r11.length()
            int r11 = r11 + 58
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>(r11)
            java.lang.String r11 = "None of the available extractors ("
            r12.append(r11)
            r12.append(r8)
            java.lang.String r8 = ") could read the stream."
            r12.append(r8)
            java.lang.String r8 = r12.toString()
            java.lang.Object r9 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            android.net.Uri r9 = (android.net.Uri) r9
            r10.<init>(r8, r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.BundledExtractorsAdapter.init(com.google.android.exoplayer2.upstream.DataReader, android.net.Uri, java.util.Map, long, long, com.google.android.exoplayer2.extractor.ExtractorOutput):void");
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void release() {
        Extractor extractor = this.b;
        if (extractor != null) {
            extractor.release();
            this.b = null;
        }
        this.c = null;
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void disableSeekingOnMp3Streams() {
        Extractor extractor = this.b;
        if (extractor instanceof Mp3Extractor) {
            ((Mp3Extractor) extractor).disableSeeking();
        }
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public long getCurrentInputPosition() {
        ExtractorInput extractorInput = this.c;
        if (extractorInput != null) {
            return extractorInput.getPosition();
        }
        return -1L;
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void seek(long j, long j2) {
        ((Extractor) Assertions.checkNotNull(this.b)).seek(j, j2);
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public int read(PositionHolder positionHolder) throws IOException {
        return ((Extractor) Assertions.checkNotNull(this.b)).read((ExtractorInput) Assertions.checkNotNull(this.c), positionHolder);
    }
}
