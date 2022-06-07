package com.google.android.exoplayer2.metadata.scte35;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class SpliceInfoDecoder extends SimpleMetadataDecoder {
    private final ParsableByteArray a = new ParsableByteArray();
    private final ParsableBitArray b = new ParsableBitArray();
    private TimestampAdjuster c;

    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    protected Metadata decode(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer) {
        if (this.c == null || metadataInputBuffer.subsampleOffsetUs != this.c.getTimestampOffsetUs()) {
            this.c = new TimestampAdjuster(metadataInputBuffer.timeUs);
            this.c.adjustSampleTimestamp(metadataInputBuffer.timeUs - metadataInputBuffer.subsampleOffsetUs);
        }
        byte[] array = byteBuffer.array();
        int limit = byteBuffer.limit();
        this.a.reset(array, limit);
        this.b.reset(array, limit);
        this.b.skipBits(39);
        long readBits = (this.b.readBits(1) << 32) | this.b.readBits(32);
        this.b.skipBits(20);
        int readBits2 = this.b.readBits(12);
        int readBits3 = this.b.readBits(8);
        Metadata.Entry entry = null;
        this.a.skipBytes(14);
        if (readBits3 == 0) {
            entry = new SpliceNullCommand();
        } else if (readBits3 != 255) {
            switch (readBits3) {
                case 4:
                    entry = SpliceScheduleCommand.a(this.a);
                    break;
                case 5:
                    entry = SpliceInsertCommand.a(this.a, readBits, this.c);
                    break;
                case 6:
                    entry = TimeSignalCommand.a(this.a, readBits, this.c);
                    break;
            }
        } else {
            entry = PrivateCommand.a(this.a, readBits2, readBits);
        }
        return entry == null ? new Metadata(new Metadata.Entry[0]) : new Metadata(entry);
    }
}
