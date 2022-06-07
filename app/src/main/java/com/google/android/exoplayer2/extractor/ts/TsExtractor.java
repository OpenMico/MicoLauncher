package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
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
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class TsExtractor implements Extractor {
    public static final int DEFAULT_TIMESTAMP_SEARCH_BYTES = 112800;
    public static final ExtractorsFactory FACTORY = $$Lambda$TsExtractor$_bkn2inBwAzuMt_VaCA5z1PK7aI.INSTANCE;
    public static final int MODE_HLS = 2;
    public static final int MODE_MULTI_PMT = 0;
    public static final int MODE_SINGLE_PMT = 1;
    public static final int TS_PACKET_SIZE = 188;
    public static final int TS_STREAM_TYPE_AAC_ADTS = 15;
    public static final int TS_STREAM_TYPE_AAC_LATM = 17;
    public static final int TS_STREAM_TYPE_AC3 = 129;
    public static final int TS_STREAM_TYPE_AC4 = 172;
    public static final int TS_STREAM_TYPE_AIT = 257;
    public static final int TS_STREAM_TYPE_DTS = 138;
    public static final int TS_STREAM_TYPE_DVBSUBS = 89;
    public static final int TS_STREAM_TYPE_E_AC3 = 135;
    public static final int TS_STREAM_TYPE_H262 = 2;
    public static final int TS_STREAM_TYPE_H263 = 16;
    public static final int TS_STREAM_TYPE_H264 = 27;
    public static final int TS_STREAM_TYPE_H265 = 36;
    public static final int TS_STREAM_TYPE_HDMV_DTS = 130;
    public static final int TS_STREAM_TYPE_ID3 = 21;
    public static final int TS_STREAM_TYPE_MPA = 3;
    public static final int TS_STREAM_TYPE_MPA_LSF = 4;
    public static final int TS_STREAM_TYPE_SPLICE_INFO = 134;
    public static final int TS_SYNC_BYTE = 71;
    private final int a;
    private final int b;
    private final List<TimestampAdjuster> c;
    private final ParsableByteArray d;
    private final SparseIntArray e;
    private final TsPayloadReader.Factory f;
    private final SparseArray<TsPayloadReader> g;
    private final SparseBooleanArray h;
    private final SparseBooleanArray i;
    private final e j;
    private d k;
    private ExtractorOutput l;
    private int m;
    private boolean n;
    private boolean o;
    private boolean p;
    private TsPayloadReader q;
    private int r;
    private int s;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Mode {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    static /* synthetic */ int b(TsExtractor tsExtractor) {
        int i = tsExtractor.m;
        tsExtractor.m = i + 1;
        return i;
    }

    public static /* synthetic */ Extractor[] c() {
        return new Extractor[]{new TsExtractor()};
    }

    public TsExtractor() {
        this(0);
    }

    public TsExtractor(int i) {
        this(1, i, (int) DEFAULT_TIMESTAMP_SEARCH_BYTES);
    }

    public TsExtractor(int i, int i2, int i3) {
        this(i, new TimestampAdjuster(0L), new DefaultTsPayloadReaderFactory(i2), i3);
    }

    public TsExtractor(int i, TimestampAdjuster timestampAdjuster, TsPayloadReader.Factory factory) {
        this(i, timestampAdjuster, factory, DEFAULT_TIMESTAMP_SEARCH_BYTES);
    }

    public TsExtractor(int i, TimestampAdjuster timestampAdjuster, TsPayloadReader.Factory factory, int i2) {
        this.f = (TsPayloadReader.Factory) Assertions.checkNotNull(factory);
        this.b = i2;
        this.a = i;
        if (i == 1 || i == 2) {
            this.c = Collections.singletonList(timestampAdjuster);
        } else {
            this.c = new ArrayList();
            this.c.add(timestampAdjuster);
        }
        this.d = new ParsableByteArray(new byte[9400], 0);
        this.h = new SparseBooleanArray();
        this.i = new SparseBooleanArray();
        this.g = new SparseArray<>();
        this.e = new SparseIntArray();
        this.j = new e(i2);
        this.s = -1;
        b();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        boolean z;
        byte[] data = this.d.getData();
        extractorInput.peekFully(data, 0, 940);
        for (int i = 0; i < 188; i++) {
            int i2 = 0;
            while (true) {
                if (i2 >= 5) {
                    z = true;
                    break;
                } else if (data[(i2 * 188) + i] != 71) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                extractorInput.skipFully(i);
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.l = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        d dVar;
        Assertions.checkState(this.a != 2);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            TimestampAdjuster timestampAdjuster = this.c.get(i);
            boolean z = timestampAdjuster.getTimestampOffsetUs() == C.TIME_UNSET;
            if (!z) {
                long firstSampleTimestampUs = timestampAdjuster.getFirstSampleTimestampUs();
                z = (firstSampleTimestampUs == C.TIME_UNSET || firstSampleTimestampUs == 0 || firstSampleTimestampUs == j2) ? false : true;
            }
            if (z) {
                timestampAdjuster.reset(j2);
            }
        }
        if (!(j2 == 0 || (dVar = this.k) == null)) {
            dVar.setSeekTargetUs(j2);
        }
        this.d.reset(0);
        this.e.clear();
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            this.g.valueAt(i2).seek();
        }
        this.r = 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        long length = extractorInput.getLength();
        if (this.n) {
            if (((length == -1 || this.a == 2) ? false : true) && !this.j.a()) {
                return this.j.a(extractorInput, positionHolder, this.s);
            }
            a(length);
            if (this.p) {
                this.p = false;
                seek(0L, 0L);
                if (extractorInput.getPosition() != 0) {
                    positionHolder.position = 0L;
                    return 1;
                }
            }
            d dVar = this.k;
            if (dVar != null && dVar.isSeeking()) {
                return this.k.handlePendingSeek(extractorInput, positionHolder);
            }
        }
        if (!a(extractorInput)) {
            return -1;
        }
        int a2 = a();
        int limit = this.d.limit();
        if (a2 > limit) {
            return 0;
        }
        int readInt = this.d.readInt();
        if ((8388608 & readInt) != 0) {
            this.d.setPosition(a2);
            return 0;
        }
        int i = ((4194304 & readInt) != 0 ? 1 : 0) | 0;
        int i2 = (2096896 & readInt) >> 8;
        boolean z = (readInt & 32) != 0;
        TsPayloadReader tsPayloadReader = (readInt & 16) != 0 ? this.g.get(i2) : null;
        if (tsPayloadReader == null) {
            this.d.setPosition(a2);
            return 0;
        }
        if (this.a != 2) {
            int i3 = readInt & 15;
            int i4 = this.e.get(i2, i3 - 1);
            this.e.put(i2, i3);
            if (i4 == i3) {
                this.d.setPosition(a2);
                return 0;
            } else if (i3 != ((i4 + 1) & 15)) {
                tsPayloadReader.seek();
            }
        }
        if (z) {
            int readUnsignedByte = this.d.readUnsignedByte();
            i |= (this.d.readUnsignedByte() & 64) != 0 ? 2 : 0;
            this.d.skipBytes(readUnsignedByte - 1);
        }
        boolean z2 = this.n;
        if (a(i2)) {
            this.d.setLimit(a2);
            tsPayloadReader.consume(this.d, i);
            this.d.setLimit(limit);
        }
        if (this.a != 2 && !z2 && this.n && length != -1) {
            this.p = true;
        }
        this.d.setPosition(a2);
        return 0;
    }

    private void a(long j) {
        if (!this.o) {
            this.o = true;
            if (this.j.b() != C.TIME_UNSET) {
                this.k = new d(this.j.c(), this.j.b(), j, this.s, this.b);
                this.l.seekMap(this.k.getSeekMap());
                return;
            }
            this.l.seekMap(new SeekMap.Unseekable(this.j.b()));
        }
    }

    private boolean a(ExtractorInput extractorInput) throws IOException {
        byte[] data = this.d.getData();
        if (9400 - this.d.getPosition() < 188) {
            int bytesLeft = this.d.bytesLeft();
            if (bytesLeft > 0) {
                System.arraycopy(data, this.d.getPosition(), data, 0, bytesLeft);
            }
            this.d.reset(data, bytesLeft);
        }
        while (this.d.bytesLeft() < 188) {
            int limit = this.d.limit();
            int read = extractorInput.read(data, limit, 9400 - limit);
            if (read == -1) {
                return false;
            }
            this.d.setLimit(limit + read);
        }
        return true;
    }

    private int a() throws ParserException {
        int position = this.d.getPosition();
        int limit = this.d.limit();
        int findSyncBytePosition = TsUtil.findSyncBytePosition(this.d.getData(), position, limit);
        this.d.setPosition(findSyncBytePosition);
        int i = findSyncBytePosition + 188;
        if (i > limit) {
            this.r += findSyncBytePosition - position;
            if (this.a == 2 && this.r > 376) {
                throw ParserException.createForMalformedContainer("Cannot find sync byte. Most likely not a Transport Stream.", null);
            }
        } else {
            this.r = 0;
        }
        return i;
    }

    private boolean a(int i) {
        return this.a == 2 || this.n || !this.i.get(i, false);
    }

    private void b() {
        this.h.clear();
        this.g.clear();
        SparseArray<TsPayloadReader> createInitialPayloadReaders = this.f.createInitialPayloadReaders();
        int size = createInitialPayloadReaders.size();
        for (int i = 0; i < size; i++) {
            this.g.put(createInitialPayloadReaders.keyAt(i), createInitialPayloadReaders.valueAt(i));
        }
        this.g.put(0, new SectionReader(new a()));
        this.q = null;
    }

    /* loaded from: classes2.dex */
    public class a implements SectionPayloadReader {
        private final ParsableBitArray b = new ParsableBitArray(new byte[4]);

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        }

        public a() {
            TsExtractor.this = r2;
        }

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void consume(ParsableByteArray parsableByteArray) {
            if (parsableByteArray.readUnsignedByte() == 0 && (parsableByteArray.readUnsignedByte() & 128) != 0) {
                parsableByteArray.skipBytes(6);
                int bytesLeft = parsableByteArray.bytesLeft() / 4;
                for (int i = 0; i < bytesLeft; i++) {
                    parsableByteArray.readBytes(this.b, 4);
                    int readBits = this.b.readBits(16);
                    this.b.skipBits(3);
                    if (readBits == 0) {
                        this.b.skipBits(13);
                    } else {
                        int readBits2 = this.b.readBits(13);
                        if (TsExtractor.this.g.get(readBits2) == null) {
                            TsExtractor.this.g.put(readBits2, new SectionReader(new b(readBits2)));
                            TsExtractor.b(TsExtractor.this);
                        }
                    }
                }
                if (TsExtractor.this.a != 2) {
                    TsExtractor.this.g.remove(0);
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    private class b implements SectionPayloadReader {
        private final ParsableBitArray b = new ParsableBitArray(new byte[5]);
        private final SparseArray<TsPayloadReader> c = new SparseArray<>();
        private final SparseIntArray d = new SparseIntArray();
        private final int e;

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        }

        public b(int i) {
            TsExtractor.this = r2;
            this.e = i;
        }

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void consume(ParsableByteArray parsableByteArray) {
            TimestampAdjuster timestampAdjuster;
            if (parsableByteArray.readUnsignedByte() == 2) {
                if (TsExtractor.this.a == 1 || TsExtractor.this.a == 2 || TsExtractor.this.m == 1) {
                    timestampAdjuster = (TimestampAdjuster) TsExtractor.this.c.get(0);
                } else {
                    timestampAdjuster = new TimestampAdjuster(((TimestampAdjuster) TsExtractor.this.c.get(0)).getFirstSampleTimestampUs());
                    TsExtractor.this.c.add(timestampAdjuster);
                }
                if ((parsableByteArray.readUnsignedByte() & 128) != 0) {
                    parsableByteArray.skipBytes(1);
                    int readUnsignedShort = parsableByteArray.readUnsignedShort();
                    int i = 3;
                    parsableByteArray.skipBytes(3);
                    parsableByteArray.readBytes(this.b, 2);
                    this.b.skipBits(3);
                    int i2 = 13;
                    TsExtractor.this.s = this.b.readBits(13);
                    parsableByteArray.readBytes(this.b, 2);
                    int i3 = 4;
                    this.b.skipBits(4);
                    parsableByteArray.skipBytes(this.b.readBits(12));
                    if (TsExtractor.this.a == 2 && TsExtractor.this.q == null) {
                        TsPayloadReader.EsInfo esInfo = new TsPayloadReader.EsInfo(21, null, null, Util.EMPTY_BYTE_ARRAY);
                        TsExtractor tsExtractor = TsExtractor.this;
                        tsExtractor.q = tsExtractor.f.createPayloadReader(21, esInfo);
                        TsExtractor.this.q.init(timestampAdjuster, TsExtractor.this.l, new TsPayloadReader.TrackIdGenerator(readUnsignedShort, 21, 8192));
                    }
                    this.c.clear();
                    this.d.clear();
                    int bytesLeft = parsableByteArray.bytesLeft();
                    while (bytesLeft > 0) {
                        parsableByteArray.readBytes(this.b, 5);
                        int readBits = this.b.readBits(8);
                        this.b.skipBits(i);
                        int readBits2 = this.b.readBits(i2);
                        this.b.skipBits(i3);
                        int readBits3 = this.b.readBits(12);
                        TsPayloadReader.EsInfo a = a(parsableByteArray, readBits3);
                        if (readBits == 6 || readBits == 5) {
                            readBits = a.streamType;
                        }
                        bytesLeft -= readBits3 + 5;
                        int i4 = TsExtractor.this.a == 2 ? readBits : readBits2;
                        if (!TsExtractor.this.h.get(i4)) {
                            TsPayloadReader createPayloadReader = (TsExtractor.this.a == 2 && readBits == 21) ? TsExtractor.this.q : TsExtractor.this.f.createPayloadReader(readBits, a);
                            if (TsExtractor.this.a != 2 || readBits2 < this.d.get(i4, 8192)) {
                                this.d.put(i4, readBits2);
                                this.c.put(i4, createPayloadReader);
                            }
                        }
                        i = 3;
                        i3 = 4;
                        i2 = 13;
                    }
                    int size = this.d.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        int keyAt = this.d.keyAt(i5);
                        int valueAt = this.d.valueAt(i5);
                        TsExtractor.this.h.put(keyAt, true);
                        TsExtractor.this.i.put(valueAt, true);
                        TsPayloadReader valueAt2 = this.c.valueAt(i5);
                        if (valueAt2 != null) {
                            if (valueAt2 != TsExtractor.this.q) {
                                valueAt2.init(timestampAdjuster, TsExtractor.this.l, new TsPayloadReader.TrackIdGenerator(readUnsignedShort, keyAt, 8192));
                            }
                            TsExtractor.this.g.put(valueAt, valueAt2);
                        }
                    }
                    if (TsExtractor.this.a != 2) {
                        int i6 = 0;
                        TsExtractor.this.g.remove(this.e);
                        TsExtractor tsExtractor2 = TsExtractor.this;
                        if (tsExtractor2.a != 1) {
                            i6 = TsExtractor.this.m - 1;
                        }
                        tsExtractor2.m = i6;
                        if (TsExtractor.this.m == 0) {
                            TsExtractor.this.l.endTracks();
                            TsExtractor.this.n = true;
                        }
                    } else if (!TsExtractor.this.n) {
                        TsExtractor.this.l.endTracks();
                        TsExtractor.this.m = 0;
                        TsExtractor.this.n = true;
                    }
                }
            }
        }

        private TsPayloadReader.EsInfo a(ParsableByteArray parsableByteArray, int i) {
            int position = parsableByteArray.getPosition();
            int i2 = i + position;
            String str = null;
            int i3 = -1;
            ArrayList arrayList = null;
            while (parsableByteArray.getPosition() < i2) {
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                int position2 = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
                if (position2 > i2) {
                    break;
                }
                if (readUnsignedByte == 5) {
                    long readUnsignedInt = parsableByteArray.readUnsignedInt();
                    if (readUnsignedInt == 1094921523) {
                        i3 = 129;
                    } else if (readUnsignedInt == 1161904947) {
                        i3 = 135;
                    } else if (readUnsignedInt == 1094921524) {
                        i3 = 172;
                    } else if (readUnsignedInt == 1212503619) {
                        i3 = 36;
                    }
                } else if (readUnsignedByte == 106) {
                    i3 = 129;
                } else if (readUnsignedByte == 122) {
                    i3 = 135;
                } else if (readUnsignedByte == 127) {
                    if (parsableByteArray.readUnsignedByte() == 21) {
                        i3 = 172;
                    }
                } else if (readUnsignedByte == 123) {
                    i3 = TsExtractor.TS_STREAM_TYPE_DTS;
                } else if (readUnsignedByte == 10) {
                    str = parsableByteArray.readString(3).trim();
                } else if (readUnsignedByte == 89) {
                    ArrayList arrayList2 = new ArrayList();
                    while (parsableByteArray.getPosition() < position2) {
                        String trim = parsableByteArray.readString(3).trim();
                        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                        byte[] bArr = new byte[4];
                        parsableByteArray.readBytes(bArr, 0, 4);
                        arrayList2.add(new TsPayloadReader.DvbSubtitleInfo(trim, readUnsignedByte2, bArr));
                    }
                    arrayList = arrayList2;
                    i3 = 89;
                } else if (readUnsignedByte == 111) {
                    i3 = 257;
                }
                parsableByteArray.skipBytes(position2 - parsableByteArray.getPosition());
            }
            parsableByteArray.setPosition(i2);
            return new TsPayloadReader.EsInfo(i3, str, arrayList, Arrays.copyOfRange(parsableByteArray.getData(), position, i2));
        }
    }
}
