package com.google.android.exoplayer2.extractor.ogg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.FlacFrameReader;
import com.google.android.exoplayer2.extractor.FlacMetadataReader;
import com.google.android.exoplayer2.extractor.FlacSeekTableSeekMap;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ogg.g;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* compiled from: FlacReader.java */
/* loaded from: classes2.dex */
final class b extends g {
    @Nullable
    private FlacStreamMetadata a;
    @Nullable
    private a b;

    public static boolean a(ParsableByteArray parsableByteArray) {
        return parsableByteArray.bytesLeft() >= 5 && parsableByteArray.readUnsignedByte() == 127 && parsableByteArray.readUnsignedInt() == 1179402563;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.extractor.ogg.g
    public void a(boolean z) {
        super.a(z);
        if (z) {
            this.a = null;
            this.b = null;
        }
    }

    private static boolean a(byte[] bArr) {
        return bArr[0] == -1;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.g
    protected long b(ParsableByteArray parsableByteArray) {
        if (!a(parsableByteArray.getData())) {
            return -1L;
        }
        return c(parsableByteArray);
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.g
    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    protected boolean a(ParsableByteArray parsableByteArray, long j, g.a aVar) {
        byte[] data = parsableByteArray.getData();
        FlacStreamMetadata flacStreamMetadata = this.a;
        if (flacStreamMetadata == null) {
            FlacStreamMetadata flacStreamMetadata2 = new FlacStreamMetadata(data, 17);
            this.a = flacStreamMetadata2;
            aVar.a = flacStreamMetadata2.getFormat(Arrays.copyOfRange(data, 9, parsableByteArray.limit()), null);
            return true;
        } else if ((data[0] & Byte.MAX_VALUE) == 3) {
            FlacStreamMetadata.SeekTable readSeekTableMetadataBlock = FlacMetadataReader.readSeekTableMetadataBlock(parsableByteArray);
            FlacStreamMetadata copyWithSeekTable = flacStreamMetadata.copyWithSeekTable(readSeekTableMetadataBlock);
            this.a = copyWithSeekTable;
            this.b = new a(copyWithSeekTable, readSeekTableMetadataBlock);
            return true;
        } else if (!a(data)) {
            return true;
        } else {
            a aVar2 = this.b;
            if (aVar2 != null) {
                aVar2.b(j);
                aVar.b = this.b;
            }
            Assertions.checkNotNull(aVar.a);
            return false;
        }
    }

    private int c(ParsableByteArray parsableByteArray) {
        int i = (parsableByteArray.getData()[2] & 255) >> 4;
        if (i == 6 || i == 7) {
            parsableByteArray.skipBytes(4);
            parsableByteArray.readUtf8EncodedLong();
        }
        int readFrameBlockSizeSamplesFromKey = FlacFrameReader.readFrameBlockSizeSamplesFromKey(parsableByteArray, i);
        parsableByteArray.setPosition(0);
        return readFrameBlockSizeSamplesFromKey;
    }

    /* compiled from: FlacReader.java */
    /* loaded from: classes2.dex */
    private static final class a implements e {
        private FlacStreamMetadata a;
        private FlacStreamMetadata.SeekTable b;
        private long c = -1;
        private long d = -1;

        public a(FlacStreamMetadata flacStreamMetadata, FlacStreamMetadata.SeekTable seekTable) {
            this.a = flacStreamMetadata;
            this.b = seekTable;
        }

        public void b(long j) {
            this.c = j;
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.e
        public long a(ExtractorInput extractorInput) {
            long j = this.d;
            if (j < 0) {
                return -1L;
            }
            long j2 = -(j + 2);
            this.d = -1L;
            return j2;
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.e
        public void a(long j) {
            long[] jArr = this.b.pointSampleNumbers;
            this.d = jArr[Util.binarySearchFloor(jArr, j, true, true)];
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.e
        public SeekMap b() {
            Assertions.checkState(this.c != -1);
            return new FlacSeekTableSeekMap(this.a, this.c);
        }
    }
}
