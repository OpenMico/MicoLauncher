package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PsBinarySearchSeeker.java */
/* loaded from: classes2.dex */
public final class b extends BinarySearchSeeker {
    public b(TimestampAdjuster timestampAdjuster, long j, long j2) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new a(timestampAdjuster), j, 0L, j + 1, 0L, j2, 188L, 1000);
    }

    /* compiled from: PsBinarySearchSeeker.java */
    /* loaded from: classes2.dex */
    private static final class a implements BinarySearchSeeker.TimestampSeeker {
        private final TimestampAdjuster a;
        private final ParsableByteArray b;

        private a(TimestampAdjuster timestampAdjuster) {
            this.a = timestampAdjuster;
            this.b = new ParsableByteArray();
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException {
            long position = extractorInput.getPosition();
            int min = (int) Math.min(20000L, extractorInput.getLength() - position);
            this.b.reset(min);
            extractorInput.peekFully(this.b.getData(), 0, min);
            return a(this.b, j, position);
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public void onSeekFinished() {
            this.b.reset(Util.EMPTY_BYTE_ARRAY);
        }

        private BinarySearchSeeker.TimestampSearchResult a(ParsableByteArray parsableByteArray, long j, long j2) {
            int i = -1;
            int i2 = -1;
            long j3 = -9223372036854775807L;
            while (parsableByteArray.bytesLeft() >= 4) {
                if (b.b(parsableByteArray.getData(), parsableByteArray.getPosition()) != 442) {
                    parsableByteArray.skipBytes(1);
                } else {
                    parsableByteArray.skipBytes(4);
                    long a = c.a(parsableByteArray);
                    if (a != C.TIME_UNSET) {
                        long adjustTsTimestamp = this.a.adjustTsTimestamp(a);
                        if (adjustTsTimestamp > j) {
                            if (j3 == C.TIME_UNSET) {
                                return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(adjustTsTimestamp, j2);
                            }
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j2 + i2);
                        } else if (100000 + adjustTsTimestamp > j) {
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j2 + parsableByteArray.getPosition());
                        } else {
                            i2 = parsableByteArray.getPosition();
                            j3 = adjustTsTimestamp;
                        }
                    }
                    a(parsableByteArray);
                    i = parsableByteArray.getPosition();
                }
            }
            if (j3 != C.TIME_UNSET) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j3, j2 + i);
            }
            return BinarySearchSeeker.TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }

        private static void a(ParsableByteArray parsableByteArray) {
            int b;
            int limit = parsableByteArray.limit();
            if (parsableByteArray.bytesLeft() < 10) {
                parsableByteArray.setPosition(limit);
                return;
            }
            parsableByteArray.skipBytes(9);
            int readUnsignedByte = parsableByteArray.readUnsignedByte() & 7;
            if (parsableByteArray.bytesLeft() < readUnsignedByte) {
                parsableByteArray.setPosition(limit);
                return;
            }
            parsableByteArray.skipBytes(readUnsignedByte);
            if (parsableByteArray.bytesLeft() < 4) {
                parsableByteArray.setPosition(limit);
                return;
            }
            if (b.b(parsableByteArray.getData(), parsableByteArray.getPosition()) == 443) {
                parsableByteArray.skipBytes(4);
                int readUnsignedShort = parsableByteArray.readUnsignedShort();
                if (parsableByteArray.bytesLeft() < readUnsignedShort) {
                    parsableByteArray.setPosition(limit);
                    return;
                }
                parsableByteArray.skipBytes(readUnsignedShort);
            }
            while (parsableByteArray.bytesLeft() >= 4 && (b = b.b(parsableByteArray.getData(), parsableByteArray.getPosition())) != 442 && b != 441 && (b >>> 8) == 1) {
                parsableByteArray.skipBytes(4);
                if (parsableByteArray.bytesLeft() < 2) {
                    parsableByteArray.setPosition(limit);
                    return;
                }
                parsableByteArray.setPosition(Math.min(parsableByteArray.limit(), parsableByteArray.getPosition() + parsableByteArray.readUnsignedShort()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }
}
