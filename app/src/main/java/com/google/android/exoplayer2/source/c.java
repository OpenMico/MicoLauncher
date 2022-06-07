package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.upstream.Allocation;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SampleDataQueue.java */
/* loaded from: classes2.dex */
public class c {
    private final Allocator a;
    private final int b;
    private final ParsableByteArray c = new ParsableByteArray(32);
    private a d;
    private a e;
    private a f;
    private long g;

    public c(Allocator allocator) {
        this.a = allocator;
        this.b = allocator.getIndividualAllocationLength();
        this.d = new a(0L, this.b);
        a aVar = this.d;
        this.e = aVar;
        this.f = aVar;
    }

    public void a() {
        a(this.d);
        this.d = new a(0L, this.b);
        a aVar = this.d;
        this.e = aVar;
        this.f = aVar;
        this.g = 0L;
        this.a.trim();
    }

    public void a(long j) {
        this.g = j;
        long j2 = this.g;
        if (j2 == 0 || j2 == this.d.a) {
            a(this.d);
            this.d = new a(this.g, this.b);
            a aVar = this.d;
            this.e = aVar;
            this.f = aVar;
            return;
        }
        a aVar2 = this.d;
        while (this.g > aVar2.b) {
            aVar2 = aVar2.e;
        }
        a aVar3 = aVar2.e;
        a(aVar3);
        aVar2.e = new a(aVar2.b, this.b);
        this.f = this.g == aVar2.b ? aVar2.e : aVar2;
        if (this.e == aVar3) {
            this.e = aVar2.e;
        }
    }

    public void b() {
        this.e = this.d;
    }

    public void a(DecoderInputBuffer decoderInputBuffer, SampleQueue.a aVar) {
        this.e = a(this.e, decoderInputBuffer, aVar, this.c);
    }

    public void b(DecoderInputBuffer decoderInputBuffer, SampleQueue.a aVar) {
        a(this.e, decoderInputBuffer, aVar, this.c);
    }

    public void b(long j) {
        if (j != -1) {
            while (j >= this.d.b) {
                this.a.release(this.d.d);
                this.d = this.d.a();
            }
            if (this.e.a < this.d.a) {
                this.e = this.d;
            }
        }
    }

    public long c() {
        return this.g;
    }

    public int a(DataReader dataReader, int i, boolean z) throws IOException {
        int read = dataReader.read(this.f.d.data, this.f.a(this.g), a(i));
        if (read != -1) {
            b(read);
            return read;
        } else if (z) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    public void a(ParsableByteArray parsableByteArray, int i) {
        while (i > 0) {
            int a2 = a(i);
            parsableByteArray.readBytes(this.f.d.data, this.f.a(this.g), a2);
            i -= a2;
            b(a2);
        }
    }

    private void a(a aVar) {
        if (aVar.c) {
            Allocation[] allocationArr = new Allocation[(this.f.c ? 1 : 0) + (((int) (this.f.a - aVar.a)) / this.b)];
            for (int i = 0; i < allocationArr.length; i++) {
                allocationArr[i] = aVar.d;
                aVar = aVar.a();
            }
            this.a.release(allocationArr);
        }
    }

    private int a(int i) {
        if (!this.f.c) {
            this.f.a(this.a.allocate(), new a(this.f.b, this.b));
        }
        return Math.min(i, (int) (this.f.b - this.g));
    }

    private void b(int i) {
        this.g += i;
        if (this.g == this.f.b) {
            this.f = this.f.e;
        }
    }

    private static a a(a aVar, DecoderInputBuffer decoderInputBuffer, SampleQueue.a aVar2, ParsableByteArray parsableByteArray) {
        if (decoderInputBuffer.isEncrypted()) {
            aVar = b(aVar, decoderInputBuffer, aVar2, parsableByteArray);
        }
        if (decoderInputBuffer.hasSupplementalData()) {
            parsableByteArray.reset(4);
            a a2 = a(aVar, aVar2.b, parsableByteArray.getData(), 4);
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            aVar2.b += 4;
            aVar2.a -= 4;
            decoderInputBuffer.ensureSpaceForWrite(readUnsignedIntToInt);
            a a3 = a(a2, aVar2.b, decoderInputBuffer.data, readUnsignedIntToInt);
            aVar2.b += readUnsignedIntToInt;
            aVar2.a -= readUnsignedIntToInt;
            decoderInputBuffer.resetSupplementalData(aVar2.a);
            return a(a3, aVar2.b, decoderInputBuffer.supplementalData, aVar2.a);
        }
        decoderInputBuffer.ensureSpaceForWrite(aVar2.a);
        return a(aVar, aVar2.b, decoderInputBuffer.data, aVar2.a);
    }

    private static a b(a aVar, DecoderInputBuffer decoderInputBuffer, SampleQueue.a aVar2, ParsableByteArray parsableByteArray) {
        int i;
        long j = aVar2.b;
        parsableByteArray.reset(1);
        a a2 = a(aVar, j, parsableByteArray.getData(), 1);
        long j2 = j + 1;
        byte b = parsableByteArray.getData()[0];
        boolean z = (b & 128) != 0;
        int i2 = b & Byte.MAX_VALUE;
        CryptoInfo cryptoInfo = decoderInputBuffer.cryptoInfo;
        if (cryptoInfo.iv == null) {
            cryptoInfo.iv = new byte[16];
        } else {
            Arrays.fill(cryptoInfo.iv, (byte) 0);
        }
        a a3 = a(a2, j2, cryptoInfo.iv, i2);
        long j3 = j2 + i2;
        if (z) {
            parsableByteArray.reset(2);
            a3 = a(a3, j3, parsableByteArray.getData(), 2);
            j3 += 2;
            i = parsableByteArray.readUnsignedShort();
        } else {
            i = 1;
        }
        int[] iArr = cryptoInfo.numBytesOfClearData;
        int[] iArr2 = (iArr == null || iArr.length < i) ? new int[i] : iArr;
        int[] iArr3 = cryptoInfo.numBytesOfEncryptedData;
        int[] iArr4 = (iArr3 == null || iArr3.length < i) ? new int[i] : iArr3;
        if (z) {
            int i3 = i * 6;
            parsableByteArray.reset(i3);
            a3 = a(a3, j3, parsableByteArray.getData(), i3);
            j3 += i3;
            parsableByteArray.setPosition(0);
            for (int i4 = 0; i4 < i; i4++) {
                iArr2[i4] = parsableByteArray.readUnsignedShort();
                iArr4[i4] = parsableByteArray.readUnsignedIntToInt();
            }
        } else {
            iArr2[0] = 0;
            iArr4[0] = aVar2.a - ((int) (j3 - aVar2.b));
        }
        TrackOutput.CryptoData cryptoData = (TrackOutput.CryptoData) Util.castNonNull(aVar2.c);
        cryptoInfo.set(i, iArr2, iArr4, cryptoData.encryptionKey, cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
        int i5 = (int) (j3 - aVar2.b);
        aVar2.b += i5;
        aVar2.a -= i5;
        return a3;
    }

    private static a a(a aVar, long j, ByteBuffer byteBuffer, int i) {
        a a2 = a(aVar, j);
        while (i > 0) {
            int min = Math.min(i, (int) (a2.b - j));
            byteBuffer.put(a2.d.data, a2.a(j), min);
            i -= min;
            j += min;
            if (j == a2.b) {
                a2 = a2.e;
            }
        }
        return a2;
    }

    private static a a(a aVar, long j, byte[] bArr, int i) {
        long j2 = j;
        a a2 = a(aVar, j);
        int i2 = i;
        while (i2 > 0) {
            int min = Math.min(i2, (int) (a2.b - j2));
            System.arraycopy(a2.d.data, a2.a(j2), bArr, i - i2, min);
            i2 -= min;
            j2 += min;
            if (j2 == a2.b) {
                a2 = a2.e;
            }
        }
        return a2;
    }

    private static a a(a aVar, long j) {
        while (j >= aVar.b) {
            aVar = aVar.e;
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SampleDataQueue.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public final long a;
        public final long b;
        public boolean c;
        @Nullable
        public Allocation d;
        @Nullable
        public a e;

        public a(long j, int i) {
            this.a = j;
            this.b = j + i;
        }

        public void a(Allocation allocation, a aVar) {
            this.d = allocation;
            this.e = aVar;
            this.c = true;
        }

        public int a(long j) {
            return ((int) (j - this.a)) + this.d.offset;
        }

        public a a() {
            this.d = null;
            a aVar = this.e;
            this.e = null;
            return aVar;
        }
    }
}
