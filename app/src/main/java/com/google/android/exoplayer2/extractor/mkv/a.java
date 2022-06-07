package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayDeque;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* compiled from: DefaultEbmlReader.java */
/* loaded from: classes2.dex */
final class a implements b {
    private final byte[] a = new byte[8];
    private final ArrayDeque<C0059a> b = new ArrayDeque<>();
    private final d c = new d();
    private EbmlProcessor d;
    private int e;
    private int f;
    private long g;

    @Override // com.google.android.exoplayer2.extractor.mkv.b
    public void a(EbmlProcessor ebmlProcessor) {
        this.d = ebmlProcessor;
    }

    @Override // com.google.android.exoplayer2.extractor.mkv.b
    public void a() {
        this.e = 0;
        this.b.clear();
        this.c.a();
    }

    @Override // com.google.android.exoplayer2.extractor.mkv.b
    public boolean a(ExtractorInput extractorInput) throws IOException {
        Assertions.checkStateNotNull(this.d);
        while (true) {
            C0059a peek = this.b.peek();
            if (peek == null || extractorInput.getPosition() < peek.b) {
                if (this.e == 0) {
                    long a = this.c.a(extractorInput, true, false, 4);
                    if (a == -2) {
                        a = b(extractorInput);
                    }
                    if (a == -1) {
                        return false;
                    }
                    this.f = (int) a;
                    this.e = 1;
                }
                if (this.e == 1) {
                    this.g = this.c.a(extractorInput, false, true, 8);
                    this.e = 2;
                }
                int elementType = this.d.getElementType(this.f);
                switch (elementType) {
                    case 0:
                        extractorInput.skipFully((int) this.g);
                        this.e = 0;
                    case 1:
                        long position = extractorInput.getPosition();
                        this.b.push(new C0059a(this.f, this.g + position));
                        this.d.startMasterElement(this.f, position, this.g);
                        this.e = 0;
                        return true;
                    case 2:
                        long j = this.g;
                        if (j <= 8) {
                            this.d.integerElement(this.f, a(extractorInput, (int) j));
                            this.e = 0;
                            return true;
                        }
                        StringBuilder sb = new StringBuilder(42);
                        sb.append("Invalid integer size: ");
                        sb.append(j);
                        throw ParserException.createForMalformedContainer(sb.toString(), null);
                    case 3:
                        long j2 = this.g;
                        if (j2 <= 2147483647L) {
                            this.d.stringElement(this.f, c(extractorInput, (int) j2));
                            this.e = 0;
                            return true;
                        }
                        StringBuilder sb2 = new StringBuilder(41);
                        sb2.append("String element size: ");
                        sb2.append(j2);
                        throw ParserException.createForMalformedContainer(sb2.toString(), null);
                    case 4:
                        this.d.binaryElement(this.f, (int) this.g, extractorInput);
                        this.e = 0;
                        return true;
                    case 5:
                        long j3 = this.g;
                        if (j3 == 4 || j3 == 8) {
                            this.d.floatElement(this.f, b(extractorInput, (int) this.g));
                            this.e = 0;
                            return true;
                        }
                        StringBuilder sb3 = new StringBuilder(40);
                        sb3.append("Invalid float size: ");
                        sb3.append(j3);
                        throw ParserException.createForMalformedContainer(sb3.toString(), null);
                    default:
                        StringBuilder sb4 = new StringBuilder(32);
                        sb4.append("Invalid element type ");
                        sb4.append(elementType);
                        throw ParserException.createForMalformedContainer(sb4.toString(), null);
                }
            } else {
                this.d.endMasterElement(this.b.pop().a);
                return true;
            }
        }
    }

    @RequiresNonNull({"processor"})
    private long b(ExtractorInput extractorInput) throws IOException {
        extractorInput.resetPeekPosition();
        while (true) {
            extractorInput.peekFully(this.a, 0, 4);
            int a = d.a(this.a[0]);
            if (a != -1 && a <= 4) {
                int a2 = (int) d.a(this.a, a, false);
                if (this.d.isLevel1Element(a2)) {
                    extractorInput.skipFully(a);
                    return a2;
                }
            }
            extractorInput.skipFully(1);
        }
    }

    private long a(ExtractorInput extractorInput, int i) throws IOException {
        extractorInput.readFully(this.a, 0, i);
        long j = 0;
        for (int i2 = 0; i2 < i; i2++) {
            j = (j << 8) | (this.a[i2] & 255);
        }
        return j;
    }

    private double b(ExtractorInput extractorInput, int i) throws IOException {
        long a = a(extractorInput, i);
        if (i == 4) {
            return Float.intBitsToFloat((int) a);
        }
        return Double.longBitsToDouble(a);
    }

    private static String c(ExtractorInput extractorInput, int i) throws IOException {
        if (i == 0) {
            return "";
        }
        byte[] bArr = new byte[i];
        extractorInput.readFully(bArr, 0, i);
        while (i > 0 && bArr[i - 1] == 0) {
            i--;
        }
        return new String(bArr, 0, i);
    }

    /* compiled from: DefaultEbmlReader.java */
    /* renamed from: com.google.android.exoplayer2.extractor.mkv.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static final class C0059a {
        private final int a;
        private final long b;

        private C0059a(int i, long j) {
            this.a = i;
            this.b = j;
        }
    }
}
