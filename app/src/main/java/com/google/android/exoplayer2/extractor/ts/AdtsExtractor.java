package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class AdtsExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$AdtsExtractor$jUTvfsHRUIbDKs5fHaexB_do3s.INSTANCE;
    public static final int FLAG_ENABLE_CONSTANT_BITRATE_SEEKING = 1;
    private final int a;
    private final AdtsReader b;
    private final ParsableByteArray c;
    private final ParsableByteArray d;
    private final ParsableBitArray e;
    private ExtractorOutput f;
    private long g;
    private long h;
    private int i;
    private boolean j;
    private boolean k;
    private boolean l;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] a() {
        return new Extractor[]{new AdtsExtractor()};
    }

    public AdtsExtractor() {
        this(0);
    }

    public AdtsExtractor(int i) {
        this.a = i;
        this.b = new AdtsReader(true);
        this.c = new ParsableByteArray(2048);
        this.i = -1;
        this.h = -1L;
        this.d = new ParsableByteArray(10);
        this.e = new ParsableBitArray(this.d.getData());
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        int a = a(extractorInput);
        int i = a;
        int i2 = 0;
        int i3 = 0;
        do {
            extractorInput.peekFully(this.d.getData(), 0, 2);
            this.d.setPosition(0);
            if (!AdtsReader.isAdtsSyncWord(this.d.readUnsignedShort())) {
                i++;
                extractorInput.resetPeekPosition();
                extractorInput.advancePeekPosition(i);
                i2 = 0;
                i3 = 0;
            } else {
                i2++;
                if (i2 >= 4 && i3 > 188) {
                    return true;
                }
                extractorInput.peekFully(this.d.getData(), 0, 4);
                this.e.setPosition(14);
                int readBits = this.e.readBits(13);
                if (readBits <= 6) {
                    i++;
                    extractorInput.resetPeekPosition();
                    extractorInput.advancePeekPosition(i);
                    i2 = 0;
                    i3 = 0;
                } else {
                    extractorInput.advancePeekPosition(readBits - 6);
                    i3 += readBits;
                }
            }
        } while (i - a < 8192);
        return false;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.f = extractorOutput;
        this.b.createTracks(extractorOutput, new TsPayloadReader.TrackIdGenerator(0, 1));
        extractorOutput.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        this.k = false;
        this.b.seek();
        this.g = j2;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        Assertions.checkStateNotNull(this.f);
        long length = extractorInput.getLength();
        boolean z = ((this.a & 1) == 0 || length == -1) ? false : true;
        if (z) {
            b(extractorInput);
        }
        int read = extractorInput.read(this.c.getData(), 0, 2048);
        boolean z2 = read == -1;
        a(length, z, z2);
        if (z2) {
            return -1;
        }
        this.c.setPosition(0);
        this.c.setLimit(read);
        if (!this.k) {
            this.b.packetStarted(this.g, 4);
            this.k = true;
        }
        this.b.consume(this.c);
        return 0;
    }

    private int a(ExtractorInput extractorInput) throws IOException {
        int i = 0;
        while (true) {
            extractorInput.peekFully(this.d.getData(), 0, 10);
            this.d.setPosition(0);
            if (this.d.readUnsignedInt24() != 4801587) {
                break;
            }
            this.d.skipBytes(3);
            int readSynchSafeInt = this.d.readSynchSafeInt();
            i += readSynchSafeInt + 10;
            extractorInput.advancePeekPosition(readSynchSafeInt);
        }
        extractorInput.resetPeekPosition();
        extractorInput.advancePeekPosition(i);
        if (this.h == -1) {
            this.h = i;
        }
        return i;
    }

    @RequiresNonNull({"extractorOutput"})
    private void a(long j, boolean z, boolean z2) {
        if (!this.l) {
            boolean z3 = z && this.i > 0;
            if (!z3 || this.b.getSampleDurationUs() != C.TIME_UNSET || z2) {
                if (!z3 || this.b.getSampleDurationUs() == C.TIME_UNSET) {
                    this.f.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
                } else {
                    this.f.seekMap(a(j));
                }
                this.l = true;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x006d, code lost:
        r9.j = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0076, code lost:
        throw com.google.android.exoplayer2.ParserException.createForMalformedContainer("Malformed ADTS stream", null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(com.google.android.exoplayer2.extractor.ExtractorInput r10) throws java.io.IOException {
        /*
            r9 = this;
            boolean r0 = r9.j
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            r0 = -1
            r9.i = r0
            r10.resetPeekPosition()
            long r1 = r10.getPosition()
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x0018
            r9.a(r10)
        L_0x0018:
            r1 = 0
            r2 = r1
        L_0x001a:
            r5 = 1
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r9.d     // Catch: EOFException -> 0x0077
            byte[] r6 = r6.getData()     // Catch: EOFException -> 0x0077
            r7 = 2
            boolean r6 = r10.peekFully(r6, r1, r7, r5)     // Catch: EOFException -> 0x0077
            if (r6 == 0) goto L_0x0077
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r9.d     // Catch: EOFException -> 0x0077
            r6.setPosition(r1)     // Catch: EOFException -> 0x0077
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r9.d     // Catch: EOFException -> 0x0077
            int r6 = r6.readUnsignedShort()     // Catch: EOFException -> 0x0077
            boolean r6 = com.google.android.exoplayer2.extractor.ts.AdtsReader.isAdtsSyncWord(r6)     // Catch: EOFException -> 0x0077
            if (r6 != 0) goto L_0x003b
            r2 = r1
            goto L_0x0077
        L_0x003b:
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r9.d     // Catch: EOFException -> 0x0077
            byte[] r6 = r6.getData()     // Catch: EOFException -> 0x0077
            r7 = 4
            boolean r6 = r10.peekFully(r6, r1, r7, r5)     // Catch: EOFException -> 0x0077
            if (r6 != 0) goto L_0x0049
            goto L_0x0077
        L_0x0049:
            com.google.android.exoplayer2.util.ParsableBitArray r6 = r9.e     // Catch: EOFException -> 0x0077
            r7 = 14
            r6.setPosition(r7)     // Catch: EOFException -> 0x0077
            com.google.android.exoplayer2.util.ParsableBitArray r6 = r9.e     // Catch: EOFException -> 0x0077
            r7 = 13
            int r6 = r6.readBits(r7)     // Catch: EOFException -> 0x0077
            r7 = 6
            if (r6 <= r7) goto L_0x006d
            long r7 = (long) r6     // Catch: EOFException -> 0x0077
            long r3 = r3 + r7
            int r2 = r2 + 1
            r7 = 1000(0x3e8, float:1.401E-42)
            if (r2 != r7) goto L_0x0064
            goto L_0x0077
        L_0x0064:
            int r6 = r6 + (-6)
            boolean r6 = r10.advancePeekPosition(r6, r5)     // Catch: EOFException -> 0x0077
            if (r6 != 0) goto L_0x001a
            goto L_0x0077
        L_0x006d:
            r9.j = r5     // Catch: EOFException -> 0x0077
            java.lang.String r1 = "Malformed ADTS stream"
            r6 = 0
            com.google.android.exoplayer2.ParserException r1 = com.google.android.exoplayer2.ParserException.createForMalformedContainer(r1, r6)     // Catch: EOFException -> 0x0077
            throw r1     // Catch: EOFException -> 0x0077
        L_0x0077:
            r10.resetPeekPosition()
            if (r2 <= 0) goto L_0x0082
            long r0 = (long) r2
            long r3 = r3 / r0
            int r10 = (int) r3
            r9.i = r10
            goto L_0x0084
        L_0x0082:
            r9.i = r0
        L_0x0084:
            r9.j = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.AdtsExtractor.b(com.google.android.exoplayer2.extractor.ExtractorInput):void");
    }

    private SeekMap a(long j) {
        return new ConstantBitrateSeekMap(j, this.h, a(this.i, this.b.getSampleDurationUs()), this.i);
    }

    private static int a(int i, long j) {
        return (int) (((i * 8) * 1000000) / j);
    }
}
