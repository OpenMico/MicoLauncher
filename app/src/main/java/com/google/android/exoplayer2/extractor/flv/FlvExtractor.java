package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.IndexSeekMap;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class FlvExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$FlvExtractor$uVKEltrHsG77xUornnvkC7gBMkw.INSTANCE;
    private ExtractorOutput f;
    private boolean h;
    private long i;
    private int j;
    private int k;
    private int l;
    private long m;
    private boolean n;
    private a o;
    private c p;
    private final ParsableByteArray a = new ParsableByteArray(4);
    private final ParsableByteArray b = new ParsableByteArray(9);
    private final ParsableByteArray c = new ParsableByteArray(11);
    private final ParsableByteArray d = new ParsableByteArray();
    private final b e = new b();
    private int g = 1;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] c() {
        return new Extractor[]{new FlvExtractor()};
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        extractorInput.peekFully(this.a.getData(), 0, 3);
        this.a.setPosition(0);
        if (this.a.readUnsignedInt24() != 4607062) {
            return false;
        }
        extractorInput.peekFully(this.a.getData(), 0, 2);
        this.a.setPosition(0);
        if ((this.a.readUnsignedShort() & 250) != 0) {
            return false;
        }
        extractorInput.peekFully(this.a.getData(), 0, 4);
        this.a.setPosition(0);
        int readInt = this.a.readInt();
        extractorInput.resetPeekPosition();
        extractorInput.advancePeekPosition(readInt);
        extractorInput.peekFully(this.a.getData(), 0, 4);
        this.a.setPosition(0);
        return this.a.readInt() == 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.f = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        if (j == 0) {
            this.g = 1;
            this.h = false;
        } else {
            this.g = 3;
        }
        this.j = 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        Assertions.checkStateNotNull(this.f);
        while (true) {
            switch (this.g) {
                case 1:
                    if (a(extractorInput)) {
                        break;
                    } else {
                        return -1;
                    }
                case 2:
                    b(extractorInput);
                    break;
                case 3:
                    if (c(extractorInput)) {
                        break;
                    } else {
                        return -1;
                    }
                case 4:
                    if (!d(extractorInput)) {
                        break;
                    } else {
                        return 0;
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @RequiresNonNull({"extractorOutput"})
    private boolean a(ExtractorInput extractorInput) throws IOException {
        boolean z = false;
        if (!extractorInput.readFully(this.b.getData(), 0, 9, true)) {
            return false;
        }
        this.b.setPosition(0);
        this.b.skipBytes(4);
        int readUnsignedByte = this.b.readUnsignedByte();
        boolean z2 = (readUnsignedByte & 4) != 0;
        if ((readUnsignedByte & 1) != 0) {
            z = true;
        }
        if (z2 && this.o == null) {
            this.o = new a(this.f.track(8, 1));
        }
        if (z && this.p == null) {
            this.p = new c(this.f.track(9, 2));
        }
        this.f.endTracks();
        this.j = (this.b.readInt() - 9) + 4;
        this.g = 2;
        return true;
    }

    private void b(ExtractorInput extractorInput) throws IOException {
        extractorInput.skipFully(this.j);
        this.j = 0;
        this.g = 3;
    }

    private boolean c(ExtractorInput extractorInput) throws IOException {
        if (!extractorInput.readFully(this.c.getData(), 0, 11, true)) {
            return false;
        }
        this.c.setPosition(0);
        this.k = this.c.readUnsignedByte();
        this.l = this.c.readUnsignedInt24();
        this.m = this.c.readUnsignedInt24();
        this.m = ((this.c.readUnsignedByte() << 24) | this.m) * 1000;
        this.c.skipBytes(3);
        this.g = 4;
        return true;
    }

    @RequiresNonNull({"extractorOutput"})
    private boolean d(ExtractorInput extractorInput) throws IOException {
        boolean z;
        long b = b();
        boolean z2 = false;
        if (this.k == 8 && this.o != null) {
            a();
            z2 = this.o.b(e(extractorInput), b);
            z = true;
        } else if (this.k == 9 && this.p != null) {
            a();
            z2 = this.p.b(e(extractorInput), b);
            z = true;
        } else if (this.k != 18 || this.n) {
            extractorInput.skipFully(this.l);
            z = false;
        } else {
            z2 = this.e.b(e(extractorInput), b);
            long a = this.e.a();
            if (a != C.TIME_UNSET) {
                this.f.seekMap(new IndexSeekMap(this.e.c(), this.e.b(), a));
                this.n = true;
            }
            z = true;
        }
        if (!this.h && z2) {
            this.h = true;
            this.i = this.e.a() == C.TIME_UNSET ? -this.m : 0L;
        }
        this.j = 4;
        this.g = 2;
        return z;
    }

    private ParsableByteArray e(ExtractorInput extractorInput) throws IOException {
        if (this.l > this.d.capacity()) {
            ParsableByteArray parsableByteArray = this.d;
            parsableByteArray.reset(new byte[Math.max(parsableByteArray.capacity() * 2, this.l)], 0);
        } else {
            this.d.setPosition(0);
        }
        this.d.setLimit(this.l);
        extractorInput.readFully(this.d.getData(), 0, this.l);
        return this.d;
    }

    @RequiresNonNull({"extractorOutput"})
    private void a() {
        if (!this.n) {
            this.f.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
            this.n = true;
        }
    }

    private long b() {
        if (this.h) {
            return this.i + this.m;
        }
        if (this.e.a() == C.TIME_UNSET) {
            return 0L;
        }
        return this.m;
    }
}
