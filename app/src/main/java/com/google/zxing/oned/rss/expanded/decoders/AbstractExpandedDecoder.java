package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes2.dex */
public abstract class AbstractExpandedDecoder {
    private final BitArray a;
    private final r b;

    public abstract String parseInformation() throws NotFoundException, FormatException;

    public AbstractExpandedDecoder(BitArray bitArray) {
        this.a = bitArray;
        this.b = new r(bitArray);
    }

    protected final BitArray getInformation() {
        return this.a;
    }

    protected final r getGeneralDecoder() {
        return this.b;
    }

    public static AbstractExpandedDecoder createDecoder(BitArray bitArray) {
        if (bitArray.get(1)) {
            return new g(bitArray);
        }
        if (!bitArray.get(2)) {
            return new j(bitArray);
        }
        switch (r.a(bitArray, 1, 4)) {
            case 4:
                return new a(bitArray);
            case 5:
                return new b(bitArray);
            default:
                switch (r.a(bitArray, 1, 5)) {
                    case 12:
                        return new c(bitArray);
                    case 13:
                        return new d(bitArray);
                    default:
                        switch (r.a(bitArray, 1, 7)) {
                            case 56:
                                return new e(bitArray, "310", "11");
                            case 57:
                                return new e(bitArray, "320", "11");
                            case 58:
                                return new e(bitArray, "310", "13");
                            case 59:
                                return new e(bitArray, "320", "13");
                            case 60:
                                return new e(bitArray, "310", "15");
                            case 61:
                                return new e(bitArray, "320", "15");
                            case 62:
                                return new e(bitArray, "310", "17");
                            case 63:
                                return new e(bitArray, "320", "17");
                            default:
                                throw new IllegalStateException("unknown decoder: " + bitArray);
                        }
                }
        }
    }
}
