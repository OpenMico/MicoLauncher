package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TsBinarySearchSeeker.java */
/* loaded from: classes2.dex */
public final class d extends BinarySearchSeeker {
    public d(TimestampAdjuster timestampAdjuster, long j, long j2, int i, int i2) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new a(i, timestampAdjuster, i2), j, 0L, j + 1, 0L, j2, 188L, 940);
    }

    /* compiled from: TsBinarySearchSeeker.java */
    /* loaded from: classes2.dex */
    private static final class a implements BinarySearchSeeker.TimestampSeeker {
        private final TimestampAdjuster a;
        private final ParsableByteArray b = new ParsableByteArray();
        private final int c;
        private final int d;

        public a(int i, TimestampAdjuster timestampAdjuster, int i2) {
            this.c = i;
            this.a = timestampAdjuster;
            this.d = i2;
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException {
            long position = extractorInput.getPosition();
            int min = (int) Math.min(this.d, extractorInput.getLength() - position);
            this.b.reset(min);
            extractorInput.peekFully(this.b.getData(), 0, min);
            return a(this.b, j, position);
        }

        private BinarySearchSeeker.TimestampSearchResult a(ParsableByteArray parsableByteArray, long j, long j2) {
            int findSyncBytePosition;
            int findSyncBytePosition2;
            int limit = parsableByteArray.limit();
            long j3 = -1;
            long j4 = -1;
            long j5 = -9223372036854775807L;
            while (parsableByteArray.bytesLeft() >= 188 && (findSyncBytePosition2 = (findSyncBytePosition = TsUtil.findSyncBytePosition(parsableByteArray.getData(), parsableByteArray.getPosition(), limit)) + 188) <= limit) {
                long readPcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, findSyncBytePosition, this.c);
                if (readPcrFromPacket != C.TIME_UNSET) {
                    long adjustTsTimestamp = this.a.adjustTsTimestamp(readPcrFromPacket);
                    if (adjustTsTimestamp > j) {
                        if (j5 == C.TIME_UNSET) {
                            return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(adjustTsTimestamp, j2);
                        }
                        return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j2 + j4);
                    } else if (100000 + adjustTsTimestamp > j) {
                        return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j2 + findSyncBytePosition);
                    } else {
                        j4 = findSyncBytePosition;
                        j5 = adjustTsTimestamp;
                    }
                }
                parsableByteArray.setPosition(findSyncBytePosition2);
                j3 = findSyncBytePosition2;
            }
            if (j5 != C.TIME_UNSET) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j5, j2 + j3);
            }
            return BinarySearchSeeker.TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public void onSeekFinished() {
            this.b.reset(Util.EMPTY_BYTE_ARRAY);
        }
    }
}
