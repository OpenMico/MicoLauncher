package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

/* loaded from: classes2.dex */
public final class TimeSignalCommand extends SpliceCommand {
    public static final Parcelable.Creator<TimeSignalCommand> CREATOR = new Parcelable.Creator<TimeSignalCommand>() { // from class: com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand.1
        /* renamed from: a */
        public TimeSignalCommand createFromParcel(Parcel parcel) {
            return new TimeSignalCommand(parcel.readLong(), parcel.readLong());
        }

        /* renamed from: a */
        public TimeSignalCommand[] newArray(int i) {
            return new TimeSignalCommand[i];
        }
    };
    public final long playbackPositionUs;
    public final long ptsTime;

    private TimeSignalCommand(long j, long j2) {
        this.ptsTime = j;
        this.playbackPositionUs = j2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TimeSignalCommand a(ParsableByteArray parsableByteArray, long j, TimestampAdjuster timestampAdjuster) {
        long a = a(parsableByteArray, j);
        return new TimeSignalCommand(a, timestampAdjuster.adjustTsTimestamp(a));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(ParsableByteArray parsableByteArray, long j) {
        long readUnsignedByte = parsableByteArray.readUnsignedByte();
        return (128 & readUnsignedByte) != 0 ? 8589934591L & ((((readUnsignedByte & 1) << 32) | parsableByteArray.readUnsignedInt()) + j) : C.TIME_UNSET;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.ptsTime);
        parcel.writeLong(this.playbackPositionUs);
    }
}
