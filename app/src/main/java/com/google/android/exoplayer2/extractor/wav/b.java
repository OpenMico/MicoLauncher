package com.google.android.exoplayer2.extractor.wav;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* compiled from: WavHeaderReader.java */
/* loaded from: classes2.dex */
final class b {
    @Nullable
    public static a a(ExtractorInput extractorInput) throws IOException {
        byte[] bArr;
        Assertions.checkNotNull(extractorInput);
        ParsableByteArray parsableByteArray = new ParsableByteArray(16);
        if (a.a(extractorInput, parsableByteArray).a != 1380533830) {
            return null;
        }
        extractorInput.peekFully(parsableByteArray.getData(), 0, 4);
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        if (readInt != 1463899717) {
            StringBuilder sb = new StringBuilder(36);
            sb.append("Unsupported RIFF format: ");
            sb.append(readInt);
            Log.e("WavHeaderReader", sb.toString());
            return null;
        }
        a a2 = a.a(extractorInput, parsableByteArray);
        while (a2.a != 1718449184) {
            extractorInput.advancePeekPosition((int) a2.b);
            a2 = a.a(extractorInput, parsableByteArray);
        }
        Assertions.checkState(a2.b >= 16);
        extractorInput.peekFully(parsableByteArray.getData(), 0, 16);
        parsableByteArray.setPosition(0);
        int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort2 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedShort3 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort4 = parsableByteArray.readLittleEndianUnsignedShort();
        int i = ((int) a2.b) - 16;
        if (i > 0) {
            byte[] bArr2 = new byte[i];
            extractorInput.peekFully(bArr2, 0, i);
            bArr = bArr2;
        } else {
            bArr = Util.EMPTY_BYTE_ARRAY;
        }
        return new a(readLittleEndianUnsignedShort, readLittleEndianUnsignedShort2, readLittleEndianUnsignedIntToInt, readLittleEndianUnsignedIntToInt2, readLittleEndianUnsignedShort3, readLittleEndianUnsignedShort4, bArr);
    }

    public static Pair<Long, Long> b(ExtractorInput extractorInput) throws IOException {
        Assertions.checkNotNull(extractorInput);
        extractorInput.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        a a2 = a.a(extractorInput, parsableByteArray);
        while (a2.a != 1684108385) {
            if (!(a2.a == 1380533830 || a2.a == 1718449184)) {
                int i = a2.a;
                StringBuilder sb = new StringBuilder(39);
                sb.append("Ignoring unknown WAV chunk: ");
                sb.append(i);
                Log.w("WavHeaderReader", sb.toString());
            }
            long j = a2.b + 8;
            if (a2.a == 1380533830) {
                j = 12;
            }
            if (j <= 2147483647L) {
                extractorInput.skipFully((int) j);
                a2 = a.a(extractorInput, parsableByteArray);
            } else {
                int i2 = a2.a;
                StringBuilder sb2 = new StringBuilder(51);
                sb2.append("Chunk is too large (~2GB+) to skip; id: ");
                sb2.append(i2);
                throw ParserException.createForUnsupportedContainerFeature(sb2.toString());
            }
        }
        extractorInput.skipFully(8);
        long position = extractorInput.getPosition();
        long j2 = a2.b + position;
        long length = extractorInput.getLength();
        if (length != -1 && j2 > length) {
            StringBuilder sb3 = new StringBuilder(69);
            sb3.append("Data exceeds input length: ");
            sb3.append(j2);
            sb3.append(", ");
            sb3.append(length);
            Log.w("WavHeaderReader", sb3.toString());
            j2 = length;
        }
        return Pair.create(Long.valueOf(position), Long.valueOf(j2));
    }

    /* compiled from: WavHeaderReader.java */
    /* loaded from: classes2.dex */
    private static final class a {
        public final int a;
        public final long b;

        private a(int i, long j) {
            this.a = i;
            this.b = j;
        }

        public static a a(ExtractorInput extractorInput, ParsableByteArray parsableByteArray) throws IOException {
            extractorInput.peekFully(parsableByteArray.getData(), 0, 8);
            parsableByteArray.setPosition(0);
            return new a(parsableByteArray.readInt(), parsableByteArray.readLittleEndianUnsignedInt());
        }
    }
}
