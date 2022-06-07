package com.google.android.exoplayer2.video;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;

/* loaded from: classes2.dex */
public final class VideoSize implements Bundleable {
    @IntRange(from = 0)
    public final int height;
    @FloatRange(from = 0.0d, fromInclusive = false)
    public final float pixelWidthHeightRatio;
    @IntRange(from = 0, to = 359)
    public final int unappliedRotationDegrees;
    @IntRange(from = 0)
    public final int width;
    public static final VideoSize UNKNOWN = new VideoSize(0, 0);
    public static final Bundleable.Creator<VideoSize> CREATOR = $$Lambda$VideoSize$p0OtICOH2E1Gxh2yqZ_SRqSOHLw.INSTANCE;

    public VideoSize(@IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        this(i, i2, 0, 1.0f);
    }

    public VideoSize(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @IntRange(from = 0, to = 359) int i3, @FloatRange(from = 0.0d, fromInclusive = false) float f) {
        this.width = i;
        this.height = i2;
        this.unappliedRotationDegrees = i3;
        this.pixelWidthHeightRatio = f;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VideoSize)) {
            return false;
        }
        VideoSize videoSize = (VideoSize) obj;
        return this.width == videoSize.width && this.height == videoSize.height && this.unappliedRotationDegrees == videoSize.unappliedRotationDegrees && this.pixelWidthHeightRatio == videoSize.pixelWidthHeightRatio;
    }

    public int hashCode() {
        return ((((((217 + this.width) * 31) + this.height) * 31) + this.unappliedRotationDegrees) * 31) + Float.floatToRawIntBits(this.pixelWidthHeightRatio);
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), this.width);
        bundle.putInt(a(1), this.height);
        bundle.putInt(a(2), this.unappliedRotationDegrees);
        bundle.putFloat(a(3), this.pixelWidthHeightRatio);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ VideoSize a(Bundle bundle) {
        return new VideoSize(bundle.getInt(a(0), 0), bundle.getInt(a(1), 0), bundle.getInt(a(2), 0), bundle.getFloat(a(3), 1.0f));
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
