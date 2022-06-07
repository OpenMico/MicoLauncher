package com.google.android.exoplayer2.extractor.mkv;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* compiled from: Sniffer.java */
/* loaded from: classes2.dex */
final class c {
    private final ParsableByteArray a = new ParsableByteArray(8);
    private int b;

    public boolean a(ExtractorInput extractorInput) throws IOException {
        long b;
        int i;
        long length = extractorInput.getLength();
        int i2 = (length > (-1L) ? 1 : (length == (-1L) ? 0 : -1));
        long j = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        if (i2 != 0 && length <= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            j = length;
        }
        int i3 = (int) j;
        extractorInput.peekFully(this.a.getData(), 0, 4);
        long readUnsignedInt = this.a.readUnsignedInt();
        this.b = 4;
        while (readUnsignedInt != 440786851) {
            int i4 = this.b + 1;
            this.b = i4;
            if (i4 == i3) {
                return false;
            }
            extractorInput.peekFully(this.a.getData(), 0, 1);
            readUnsignedInt = (this.a.getData()[0] & 255) | ((readUnsignedInt << 8) & (-256));
        }
        long b2 = b(extractorInput);
        long j2 = this.b;
        if (b2 == Long.MIN_VALUE || (i2 != 0 && j2 + b2 >= length)) {
            return false;
        }
        while (true) {
            int i5 = this.b;
            long j3 = j2 + b2;
            if (i5 >= j3) {
                return ((long) i5) == j3;
            }
            if (b(extractorInput) != Long.MIN_VALUE && (b = b(extractorInput)) >= 0 && b <= 2147483647L) {
                if (i != 0) {
                    int i6 = (int) b;
                    extractorInput.advancePeekPosition(i6);
                    this.b += i6;
                }
            }
        }
    }

    private long b(ExtractorInput extractorInput) throws IOException {
        int i = 0;
        extractorInput.peekFully(this.a.getData(), 0, 1);
        int i2 = this.a.getData()[0] & 255;
        if (i2 == 0) {
            return Long.MIN_VALUE;
        }
        int i3 = 128;
        int i4 = 0;
        while ((i2 & i3) == 0) {
            i3 >>= 1;
            i4++;
        }
        int i5 = i2 & (~i3);
        extractorInput.peekFully(this.a.getData(), 1, i4);
        while (i < i4) {
            i++;
            i5 = (this.a.getData()[i] & 255) + (i5 << 8);
        }
        this.b += i4 + 1;
        return i5;
    }
}
