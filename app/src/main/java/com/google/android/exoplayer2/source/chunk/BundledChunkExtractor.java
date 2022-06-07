package com.google.android.exoplayer2.source.chunk;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

/* loaded from: classes2.dex */
public final class BundledChunkExtractor implements ExtractorOutput, ChunkExtractor {
    public static final ChunkExtractor.Factory FACTORY = $$Lambda$BundledChunkExtractor$DFQ97XOGu6Y45l81LijwVTYimZg.INSTANCE;
    private static final PositionHolder a = new PositionHolder();
    private final Extractor b;
    private final int c;
    private final Format d;
    private final SparseArray<a> e = new SparseArray<>();
    private boolean f;
    @Nullable
    private ChunkExtractor.TrackOutputProvider g;
    private long h;
    private SeekMap i;
    private Format[] j;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ChunkExtractor a(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
        Extractor extractor;
        String str = format.containerMimeType;
        if (MimeTypes.isText(str)) {
            if (!MimeTypes.APPLICATION_RAWCC.equals(str)) {
                return null;
            }
            extractor = new RawCcExtractor(format);
        } else if (MimeTypes.isMatroska(str)) {
            extractor = new MatroskaExtractor(1);
        } else {
            int i2 = 0;
            if (z) {
                i2 = 4;
            }
            extractor = new FragmentedMp4Extractor(i2, null, null, list, trackOutput);
        }
        return new BundledChunkExtractor(extractor, i, format);
    }

    public BundledChunkExtractor(Extractor extractor, int i, Format format) {
        this.b = extractor;
        this.c = i;
        this.d = format;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    @Nullable
    public ChunkIndex getChunkIndex() {
        SeekMap seekMap = this.i;
        if (seekMap instanceof ChunkIndex) {
            return (ChunkIndex) seekMap;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    @Nullable
    public Format[] getSampleFormats() {
        return this.j;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void init(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j, long j2) {
        this.g = trackOutputProvider;
        this.h = j2;
        if (!this.f) {
            this.b.init(this);
            if (j != C.TIME_UNSET) {
                this.b.seek(0L, j);
            }
            this.f = true;
            return;
        }
        Extractor extractor = this.b;
        if (j == C.TIME_UNSET) {
            j = 0;
        }
        extractor.seek(0L, j);
        for (int i = 0; i < this.e.size(); i++) {
            this.e.valueAt(i).a(trackOutputProvider, j2);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void release() {
        this.b.release();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public boolean read(ExtractorInput extractorInput) throws IOException {
        int read = this.b.read(extractorInput, a);
        Assertions.checkState(read != 1);
        return read == 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int i, int i2) {
        a aVar = this.e.get(i);
        if (aVar == null) {
            Assertions.checkState(this.j == null);
            aVar = new a(i, i2, i2 == this.c ? this.d : null);
            aVar.a(this.g, this.h);
            this.e.put(i, aVar);
        }
        return aVar;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        Format[] formatArr = new Format[this.e.size()];
        for (int i = 0; i < this.e.size(); i++) {
            formatArr[i] = (Format) Assertions.checkStateNotNull(this.e.valueAt(i).a);
        }
        this.j = formatArr;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(SeekMap seekMap) {
        this.i = seekMap;
    }

    /* loaded from: classes2.dex */
    private static final class a implements TrackOutput {
        public Format a;
        private final int b;
        private final int c;
        @Nullable
        private final Format d;
        private final DummyTrackOutput e = new DummyTrackOutput();
        private TrackOutput f;
        private long g;

        public a(int i, int i2, @Nullable Format format) {
            this.b = i;
            this.c = i2;
            this.d = format;
        }

        public void a(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j) {
            if (trackOutputProvider == null) {
                this.f = this.e;
                return;
            }
            this.g = j;
            this.f = trackOutputProvider.track(this.b, this.c);
            Format format = this.a;
            if (format != null) {
                this.f.format(format);
            }
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void format(Format format) {
            Format format2 = this.d;
            if (format2 != null) {
                format = format.withManifestFormatInfo(format2);
            }
            this.a = format;
            ((TrackOutput) Util.castNonNull(this.f)).format(this.a);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public int sampleData(DataReader dataReader, int i, boolean z, int i2) throws IOException {
            return ((TrackOutput) Util.castNonNull(this.f)).sampleData(dataReader, i, z);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleData(ParsableByteArray parsableByteArray, int i, int i2) {
            ((TrackOutput) Util.castNonNull(this.f)).sampleData(parsableByteArray, i);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long j, int i, int i2, int i3, @Nullable TrackOutput.CryptoData cryptoData) {
            long j2 = this.g;
            if (j2 != C.TIME_UNSET && j >= j2) {
                this.f = this.e;
            }
            ((TrackOutput) Util.castNonNull(this.f)).sampleMetadata(j, i, i2, i3, cryptoData);
        }
    }
}
