package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class PlaybackParameters implements Bundleable {
    private final int a;
    public final float pitch;
    public final float speed;
    public static final PlaybackParameters DEFAULT = new PlaybackParameters(1.0f);
    public static final Bundleable.Creator<PlaybackParameters> CREATOR = $$Lambda$PlaybackParameters$fMzu6iIdfB_71AIszkrV_c9zrCY.INSTANCE;

    public PlaybackParameters(float f) {
        this(f, 1.0f);
    }

    public PlaybackParameters(float f, float f2) {
        boolean z = true;
        Assertions.checkArgument(f > 0.0f);
        Assertions.checkArgument(f2 <= 0.0f ? false : z);
        this.speed = f;
        this.pitch = f2;
        this.a = Math.round(f * 1000.0f);
    }

    public long getMediaTimeUsForPlayoutTimeMs(long j) {
        return j * this.a;
    }

    @CheckResult
    public PlaybackParameters withSpeed(float f) {
        return new PlaybackParameters(f, this.pitch);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PlaybackParameters playbackParameters = (PlaybackParameters) obj;
        return this.speed == playbackParameters.speed && this.pitch == playbackParameters.pitch;
    }

    public int hashCode() {
        return ((527 + Float.floatToRawIntBits(this.speed)) * 31) + Float.floatToRawIntBits(this.pitch);
    }

    public String toString() {
        return Util.formatInvariant("PlaybackParameters(speed=%.2f, pitch=%.2f)", Float.valueOf(this.speed), Float.valueOf(this.pitch));
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putFloat(a(0), this.speed);
        bundle.putFloat(a(1), this.pitch);
        return bundle;
    }

    public static /* synthetic */ PlaybackParameters a(Bundle bundle) {
        return new PlaybackParameters(bundle.getFloat(a(0), 1.0f), bundle.getFloat(a(1), 1.0f));
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
