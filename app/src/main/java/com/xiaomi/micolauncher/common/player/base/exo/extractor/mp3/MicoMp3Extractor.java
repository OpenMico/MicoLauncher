package com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3;

import androidx.annotation.Nullable;
import androidx.renderscript.ScriptIntrinsicBLAS;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.Id3Peeker;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.id3.MlltFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker;
import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class MicoMp3Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$MicoMp3Extractor$qUo50tteoG5gjsdmU49BMs5PUmo.INSTANCE;
    private static final Id3Decoder.FramePredicate a = $$Lambda$MicoMp3Extractor$hZDV3caKLWuPtgFoECOw_o6mss.INSTANCE;
    private final int b;
    private final long c;
    private final ParsableByteArray d;
    private final MpegAudioUtil.Header e;
    private final GaplessInfoHolder f;
    private final Id3Peeker g;
    private final TrackOutput h;
    private ExtractorOutput i;
    private TrackOutput j;
    private TrackOutput k;
    private int l;
    @Nullable
    private Metadata m;
    private long n;
    private long o;
    private long p;
    private int q;
    private Seeker r;
    private boolean s;
    private boolean t;
    private long u;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(int i, int i2, int i3, int i4, int i5) {
        return (i2 == 67 && i3 == 79 && i4 == 77 && (i5 == 77 || i == 2)) || (i2 == 77 && i3 == 76 && i4 == 76 && (i5 == 84 || i == 2));
    }

    private static boolean a(int i, long j) {
        return ((long) (i & (-128000))) == (j & (-128000));
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] b() {
        return new Extractor[]{new MicoMp3Extractor()};
    }

    public MicoMp3Extractor() {
        this(0);
    }

    public MicoMp3Extractor(int i) {
        this(i, C.TIME_UNSET);
    }

    public MicoMp3Extractor(int i, long j) {
        this.b = i;
        this.c = j;
        this.d = new ParsableByteArray(10);
        this.e = new MpegAudioUtil.Header();
        this.f = new GaplessInfoHolder();
        this.n = C.TIME_UNSET;
        this.g = new Id3Peeker();
        this.h = new DummyTrackOutput();
        this.k = this.h;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return a(extractorInput, true);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.i = extractorOutput;
        this.j = this.i.track(0, 1);
        this.k = this.j;
        this.i.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        this.l = 0;
        this.n = C.TIME_UNSET;
        this.o = 0L;
        this.q = 0;
        this.u = j2;
        Seeker seeker = this.r;
        if ((seeker instanceof b) && !((b) seeker).a(j2)) {
            this.t = true;
            this.k = this.h;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        a();
        int a2 = a(extractorInput);
        if (a2 == -1 && (this.r instanceof b)) {
            long a3 = a(this.o);
            if (this.r.getDurationUs() != a3) {
                ((b) this.r).b(a3);
                this.i.seekMap(this.r);
            }
        }
        return a2;
    }

    public void disableSeeking() {
        this.s = true;
    }

    private int a(ExtractorInput extractorInput) throws IOException {
        if (this.l == 0) {
            try {
                a(extractorInput, false);
            } catch (EOFException unused) {
                return -1;
            }
        }
        if (this.r == null) {
            this.r = d(extractorInput);
            this.i.seekMap(this.r);
            this.k.format(new Format.Builder().setSampleMimeType(this.e.mimeType).setMaxInputSize(4096).setChannelCount(this.e.channels).setSampleRate(this.e.sampleRate).setEncoderDelay(this.f.encoderDelay).setEncoderPadding(this.f.encoderPadding).setMetadata((this.b & 4) != 0 ? null : this.m).build());
            this.p = extractorInput.getPosition();
        } else if (this.p != 0) {
            long position = extractorInput.getPosition();
            long j = this.p;
            if (position < j) {
                extractorInput.skipFully((int) (j - position));
            }
        }
        return b(extractorInput);
    }

    private int b(ExtractorInput extractorInput) throws IOException {
        if (this.q == 0) {
            extractorInput.resetPeekPosition();
            if (c(extractorInput)) {
                return -1;
            }
            this.d.setPosition(0);
            int readInt = this.d.readInt();
            if (!a(readInt, this.l) || MpegAudioUtil.getFrameSize(readInt) == -1) {
                extractorInput.skipFully(1);
                this.l = 0;
                return 0;
            }
            this.e.setForHeaderData(readInt);
            if (this.n == C.TIME_UNSET) {
                this.n = this.r.getTimeUs(extractorInput.getPosition());
                if (this.c != C.TIME_UNSET) {
                    this.n += this.c - this.r.getTimeUs(0L);
                }
            }
            this.q = this.e.frameSize;
            Seeker seeker = this.r;
            if (seeker instanceof b) {
                b bVar = (b) seeker;
                bVar.a(a(this.o + this.e.samplesPerFrame), extractorInput.getPosition() + this.e.frameSize);
                if (this.t && bVar.a(this.u)) {
                    this.t = false;
                    this.k = this.j;
                }
            }
        }
        int sampleData = this.k.sampleData((DataReader) extractorInput, this.q, true);
        if (sampleData == -1) {
            return -1;
        }
        this.q -= sampleData;
        if (this.q > 0) {
            return 0;
        }
        this.k.sampleMetadata(a(this.o), 1, this.e.frameSize, 0, null);
        this.o += this.e.samplesPerFrame;
        this.q = 0;
        return 0;
    }

    private long a(long j) {
        return this.n + ((j * 1000000) / this.e.sampleRate);
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a1, code lost:
        if (r14 == false) goto L_0x00a8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a3, code lost:
        r13.skipFully(r8 + r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a8, code lost:
        r13.resetPeekPosition();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ab, code lost:
        r12.l = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ad, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(com.google.android.exoplayer2.extractor.ExtractorInput r13, boolean r14) throws java.io.IOException {
        /*
            r12 = this;
            if (r14 == 0) goto L_0x0005
            r0 = 65536(0x10000, float:9.18355E-41)
            goto L_0x0007
        L_0x0005:
            r0 = 131072(0x20000, float:1.83671E-40)
        L_0x0007:
            r13.resetPeekPosition()
            long r1 = r13.getPosition()
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            r2 = 0
            r3 = 4
            r4 = 1
            r5 = 0
            if (r1 != 0) goto L_0x0046
            int r1 = r12.b
            r1 = r1 & r3
            if (r1 != 0) goto L_0x001f
            r1 = r4
            goto L_0x0020
        L_0x001f:
            r1 = r5
        L_0x0020:
            if (r1 == 0) goto L_0x0024
            r1 = r2
            goto L_0x0026
        L_0x0024:
            com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate r1 = com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.MicoMp3Extractor.a
        L_0x0026:
            com.google.android.exoplayer2.extractor.Id3Peeker r6 = r12.g
            com.google.android.exoplayer2.metadata.Metadata r1 = r6.peekId3Data(r13, r1)
            r12.m = r1
            com.google.android.exoplayer2.metadata.Metadata r1 = r12.m
            if (r1 == 0) goto L_0x0037
            com.google.android.exoplayer2.extractor.GaplessInfoHolder r6 = r12.f
            r6.setFromMetadata(r1)
        L_0x0037:
            long r6 = r13.getPeekPosition()
            int r1 = (int) r6
            if (r14 != 0) goto L_0x0041
            r13.skipFully(r1)
        L_0x0041:
            r8 = r1
            r1 = r5
            r6 = r1
            r7 = r6
            goto L_0x004a
        L_0x0046:
            r1 = r5
            r6 = r1
            r7 = r6
            r8 = r7
        L_0x004a:
            boolean r9 = r12.c(r13)
            if (r9 == 0) goto L_0x0059
            if (r6 <= 0) goto L_0x0053
            goto L_0x00a1
        L_0x0053:
            java.io.EOFException r13 = new java.io.EOFException
            r13.<init>()
            throw r13
        L_0x0059:
            com.google.android.exoplayer2.util.ParsableByteArray r9 = r12.d
            r9.setPosition(r5)
            com.google.android.exoplayer2.util.ParsableByteArray r9 = r12.d
            int r9 = r9.readInt()
            if (r1 == 0) goto L_0x006d
            long r10 = (long) r1
            boolean r10 = a(r9, r10)
            if (r10 == 0) goto L_0x0074
        L_0x006d:
            int r10 = com.google.android.exoplayer2.audio.MpegAudioUtil.getFrameSize(r9)
            r11 = -1
            if (r10 != r11) goto L_0x0094
        L_0x0074:
            int r1 = r7 + 1
            if (r7 != r0) goto L_0x0082
            if (r14 == 0) goto L_0x007b
            return r5
        L_0x007b:
            java.lang.String r13 = "Searched too many bytes."
            com.google.android.exoplayer2.ParserException r13 = com.google.android.exoplayer2.ParserException.createForMalformedContainer(r13, r2)
            throw r13
        L_0x0082:
            if (r14 == 0) goto L_0x008d
            r13.resetPeekPosition()
            int r6 = r8 + r1
            r13.advancePeekPosition(r6)
            goto L_0x0090
        L_0x008d:
            r13.skipFully(r4)
        L_0x0090:
            r7 = r1
            r1 = r5
            r6 = r1
            goto L_0x004a
        L_0x0094:
            int r6 = r6 + 1
            if (r6 != r4) goto L_0x009f
            com.google.android.exoplayer2.audio.MpegAudioUtil$Header r1 = r12.e
            r1.setForHeaderData(r9)
            r1 = r9
            goto L_0x00ae
        L_0x009f:
            if (r6 != r3) goto L_0x00ae
        L_0x00a1:
            if (r14 == 0) goto L_0x00a8
            int r8 = r8 + r7
            r13.skipFully(r8)
            goto L_0x00ab
        L_0x00a8:
            r13.resetPeekPosition()
        L_0x00ab:
            r12.l = r1
            return r4
        L_0x00ae:
            int r10 = r10 + (-4)
            r13.advancePeekPosition(r10)
            goto L_0x004a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.MicoMp3Extractor.a(com.google.android.exoplayer2.extractor.ExtractorInput, boolean):boolean");
    }

    private boolean c(ExtractorInput extractorInput) throws IOException {
        Seeker seeker = this.r;
        if (seeker != null) {
            long dataEndPosition = seeker.getDataEndPosition();
            if (dataEndPosition != -1 && extractorInput.getPeekPosition() > dataEndPosition - 4) {
                return true;
            }
        }
        try {
            return !extractorInput.peekFully(this.d.getData(), 0, 4, true);
        } catch (EOFException unused) {
            return true;
        }
    }

    private Seeker d(ExtractorInput extractorInput) throws IOException {
        long j;
        Seeker e = e(extractorInput);
        c a2 = a(this.m, extractorInput.getPosition());
        if (this.s) {
            return new Seeker.UnseekableSeeker();
        }
        e = null;
        if ((this.b & 2) != 0) {
            long j2 = -1;
            if (a2 != null) {
                long durationUs = a2.getDurationUs();
                j2 = a2.getDataEndPosition();
                j = durationUs;
            } else if (e != null) {
                long durationUs2 = e.getDurationUs();
                j2 = e.getDataEndPosition();
                j = durationUs2;
            } else {
                j = a(this.m);
            }
            e = new b(j, extractorInput.getPosition(), j2);
        } else if (a2 != null) {
            e = a2;
        } else if (e == null) {
        }
        return (e == null || (!e.isSeekable() && (this.b & 1) != 0)) ? f(extractorInput) : e;
    }

    @Nullable
    private Seeker e(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(this.e.frameSize);
        extractorInput.peekFully(parsableByteArray.getData(), 0, this.e.frameSize);
        int i = 21;
        if ((this.e.version & 1) != 0) {
            if (this.e.channels != 1) {
                i = 36;
            }
        } else if (this.e.channels == 1) {
            i = 13;
        }
        int a2 = a(parsableByteArray, i);
        if (a2 == 1483304551 || a2 == 1231971951) {
            e a3 = e.a(extractorInput.getLength(), extractorInput.getPosition(), this.e, parsableByteArray);
            if (a3 != null && !this.f.hasGaplessInfo()) {
                extractorInput.resetPeekPosition();
                extractorInput.advancePeekPosition(i + ScriptIntrinsicBLAS.LEFT);
                extractorInput.peekFully(this.d.getData(), 0, 3);
                this.d.setPosition(0);
                this.f.setFromXingHeaderValue(this.d.readUnsignedInt24());
            }
            extractorInput.skipFully(this.e.frameSize);
            return (a3 == null || a3.isSeekable() || a2 != 1231971951) ? a3 : f(extractorInput);
        } else if (a2 == 1447187017) {
            d a4 = d.a(extractorInput.getLength(), extractorInput.getPosition(), this.e, parsableByteArray);
            extractorInput.skipFully(this.e.frameSize);
            return a4;
        } else {
            extractorInput.resetPeekPosition();
            return null;
        }
    }

    private Seeker f(ExtractorInput extractorInput) throws IOException {
        extractorInput.peekFully(this.d.getData(), 0, 4);
        this.d.setPosition(0);
        this.e.setForHeaderData(this.d.readInt());
        return new a(extractorInput.getLength(), extractorInput.getPosition(), this.e);
    }

    private void a() {
        Assertions.checkStateNotNull(this.j);
        Util.castNonNull(this.i);
    }

    private static int a(ParsableByteArray parsableByteArray, int i) {
        if (parsableByteArray.limit() >= i + 4) {
            parsableByteArray.setPosition(i);
            int readInt = parsableByteArray.readInt();
            if (readInt == 1483304551 || readInt == 1231971951) {
                return readInt;
            }
        }
        if (parsableByteArray.limit() < 40) {
            return 0;
        }
        parsableByteArray.setPosition(36);
        return parsableByteArray.readInt() == 1447187017 ? 1447187017 : 0;
    }

    @Nullable
    private static c a(@Nullable Metadata metadata, long j) {
        if (metadata == null) {
            return null;
        }
        int length = metadata.length();
        for (int i = 0; i < length; i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof MlltFrame) {
                return c.a(j, (MlltFrame) entry, a(metadata));
            }
        }
        return null;
    }

    private static long a(@Nullable Metadata metadata) {
        if (metadata == null) {
            return C.TIME_UNSET;
        }
        int length = metadata.length();
        for (int i = 0; i < length; i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof TextInformationFrame) {
                TextInformationFrame textInformationFrame = (TextInformationFrame) entry;
                if (textInformationFrame.id.equals("TLEN")) {
                    return C.msToUs(Long.parseLong(textInformationFrame.value));
                }
            }
        }
        return C.TIME_UNSET;
    }
}
