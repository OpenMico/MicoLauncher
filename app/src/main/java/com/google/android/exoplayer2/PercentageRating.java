package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes.dex */
public final class PercentageRating extends Rating {
    public static final Bundleable.Creator<PercentageRating> CREATOR = $$Lambda$PercentageRating$NVlnCiXjfCEwLBt2QI04WKyEsXc.INSTANCE;
    private final float a;

    public PercentageRating() {
        this.a = -1.0f;
    }

    public PercentageRating(@FloatRange(from = 0.0d, to = 100.0d) float f) {
        Assertions.checkArgument(f >= 0.0f && f <= 100.0f, "percent must be in the range of [0, 100]");
        this.a = f;
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.a != -1.0f;
    }

    public float getPercent() {
        return this.a;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.a));
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PercentageRating) && this.a == ((PercentageRating) obj).a;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), 1);
        bundle.putFloat(a(1), this.a);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PercentageRating a(Bundle bundle) {
        boolean z = false;
        if (bundle.getInt(a(0), -1) == 1) {
            z = true;
        }
        Assertions.checkArgument(z);
        float f = bundle.getFloat(a(1), -1.0f);
        return f == -1.0f ? new PercentageRating() : new PercentageRating(f);
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
