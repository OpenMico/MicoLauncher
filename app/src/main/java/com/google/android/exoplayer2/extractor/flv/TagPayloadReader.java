package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes2.dex */
abstract class TagPayloadReader {
    protected final TrackOutput a;

    protected abstract boolean a(ParsableByteArray parsableByteArray) throws ParserException;

    protected abstract boolean a(ParsableByteArray parsableByteArray, long j) throws ParserException;

    /* loaded from: classes2.dex */
    public static final class UnsupportedFormatException extends ParserException {
        public UnsupportedFormatException(String str) {
            super(str, null, false, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TagPayloadReader(TrackOutput trackOutput) {
        this.a = trackOutput;
    }

    public final boolean b(ParsableByteArray parsableByteArray, long j) throws ParserException {
        return a(parsableByteArray) && a(parsableByteArray, j);
    }
}
