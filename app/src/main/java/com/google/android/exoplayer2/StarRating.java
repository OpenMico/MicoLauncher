package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes.dex */
public final class StarRating extends Rating {
    public static final Bundleable.Creator<StarRating> CREATOR = $$Lambda$StarRating$yrmo1BkLEuPEsaLkK9hYpfilbY.INSTANCE;
    @IntRange(from = 1)
    private final int a;
    private final float b;

    public StarRating(@IntRange(from = 1) int i) {
        Assertions.checkArgument(i > 0, "maxStars must be a positive integer");
        this.a = i;
        this.b = -1.0f;
    }

    public StarRating(@IntRange(from = 1) int i, @FloatRange(from = 0.0d) float f) {
        boolean z = true;
        Assertions.checkArgument(i > 0, "maxStars must be a positive integer");
        Assertions.checkArgument((f < 0.0f || f > ((float) i)) ? false : z, "starRating is out of range [0, maxStars]");
        this.a = i;
        this.b = f;
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.b != -1.0f;
    }

    @IntRange(from = 1)
    public int getMaxStars() {
        return this.a;
    }

    public float getStarRating() {
        return this.b;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.a), Float.valueOf(this.b));
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof StarRating)) {
            return false;
        }
        StarRating starRating = (StarRating) obj;
        return this.a == starRating.a && this.b == starRating.b;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), 2);
        bundle.putInt(a(1), this.a);
        bundle.putFloat(a(2), this.b);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static StarRating a(Bundle bundle) {
        boolean z = false;
        if (bundle.getInt(a(0), -1) == 2) {
            z = true;
        }
        Assertions.checkArgument(z);
        int i = bundle.getInt(a(1), 5);
        float f = bundle.getFloat(a(2), -1.0f);
        if (f == -1.0f) {
            return new StarRating(i);
        }
        return new StarRating(i, f);
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
