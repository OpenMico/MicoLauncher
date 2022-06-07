package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* compiled from: OggPageHeader.java */
/* loaded from: classes2.dex */
final class d {
    public int a;
    public int b;
    public long c;
    public long d;
    public long e;
    public long f;
    public int g;
    public int h;
    public int i;
    public final int[] j = new int[255];
    private final ParsableByteArray k = new ParsableByteArray(255);

    public void a() {
        this.a = 0;
        this.b = 0;
        this.c = 0L;
        this.d = 0L;
        this.e = 0L;
        this.f = 0L;
        this.g = 0;
        this.h = 0;
        this.i = 0;
    }

    public boolean a(ExtractorInput extractorInput) throws IOException {
        return a(extractorInput, -1L);
    }

    public boolean a(ExtractorInput extractorInput, long j) throws IOException {
        int i;
        Assertions.checkArgument(extractorInput.getPosition() == extractorInput.getPeekPosition());
        this.k.reset(4);
        while (true) {
            i = (j > (-1L) ? 1 : (j == (-1L) ? 0 : -1));
            if ((i == 0 || extractorInput.getPosition() + 4 < j) && ExtractorUtil.peekFullyQuietly(extractorInput, this.k.getData(), 0, 4, true)) {
                this.k.setPosition(0);
                if (this.k.readUnsignedInt() == 1332176723) {
                    extractorInput.resetPeekPosition();
                    return true;
                }
                extractorInput.skipFully(1);
            }
        }
        do {
            if (i != 0 && extractorInput.getPosition() >= j) {
                break;
            }
        } while (extractorInput.skip(1) != -1);
        return false;
    }

    public boolean a(ExtractorInput extractorInput, boolean z) throws IOException {
        a();
        this.k.reset(27);
        if (!ExtractorUtil.peekFullyQuietly(extractorInput, this.k.getData(), 0, 27, z) || this.k.readUnsignedInt() != 1332176723) {
            return false;
        }
        this.a = this.k.readUnsignedByte();
        if (this.a == 0) {
            this.b = this.k.readUnsignedByte();
            this.c = this.k.readLittleEndianLong();
            this.d = this.k.readLittleEndianUnsignedInt();
            this.e = this.k.readLittleEndianUnsignedInt();
            this.f = this.k.readLittleEndianUnsignedInt();
            this.g = this.k.readUnsignedByte();
            int i = this.g;
            this.h = i + 27;
            this.k.reset(i);
            if (!ExtractorUtil.peekFullyQuietly(extractorInput, this.k.getData(), 0, this.g, z)) {
                return false;
            }
            for (int i2 = 0; i2 < this.g; i2++) {
                this.j[i2] = this.k.readUnsignedByte();
                this.i += this.j[i2];
            }
            return true;
        } else if (z) {
            return false;
        } else {
            throw ParserException.createForUnsupportedContainerFeature("unsupported bit stream revision");
        }
    }
}
