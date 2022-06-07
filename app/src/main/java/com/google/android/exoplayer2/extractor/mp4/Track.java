package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class Track {
    public static final int TRANSFORMATION_CEA608_CDAT = 1;
    public static final int TRANSFORMATION_NONE = 0;
    @Nullable
    private final TrackEncryptionBox[] a;
    public final long durationUs;
    @Nullable
    public final long[] editListDurations;
    @Nullable
    public final long[] editListMediaTimes;
    public final Format format;
    public final int id;
    public final long movieTimescale;
    public final int nalUnitLengthFieldLength;
    public final int sampleTransformation;
    public final long timescale;
    public final int type;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Transformation {
    }

    public Track(int i, int i2, long j, long j2, long j3, Format format, int i3, @Nullable TrackEncryptionBox[] trackEncryptionBoxArr, int i4, @Nullable long[] jArr, @Nullable long[] jArr2) {
        this.id = i;
        this.type = i2;
        this.timescale = j;
        this.movieTimescale = j2;
        this.durationUs = j3;
        this.format = format;
        this.sampleTransformation = i3;
        this.a = trackEncryptionBoxArr;
        this.nalUnitLengthFieldLength = i4;
        this.editListDurations = jArr;
        this.editListMediaTimes = jArr2;
    }

    @Nullable
    public TrackEncryptionBox getSampleDescriptionEncryptionBox(int i) {
        TrackEncryptionBox[] trackEncryptionBoxArr = this.a;
        if (trackEncryptionBoxArr == null) {
            return null;
        }
        return trackEncryptionBoxArr[i];
    }

    public Track copyWithFormat(Format format) {
        return new Track(this.id, this.type, this.timescale, this.movieTimescale, this.durationUs, format, this.sampleTransformation, this.a, this.nalUnitLengthFieldLength, this.editListDurations, this.editListMediaTimes);
    }
}
