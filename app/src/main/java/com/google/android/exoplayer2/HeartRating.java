package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes.dex */
public final class HeartRating extends Rating {
    public static final Bundleable.Creator<HeartRating> CREATOR = $$Lambda$HeartRating$xfO2mC3dbIcLm6nMXp6TLzMIoi4.INSTANCE;
    private final boolean a;
    private final boolean b;

    public HeartRating() {
        this.a = false;
        this.b = false;
    }

    public HeartRating(boolean z) {
        this.a = true;
        this.b = z;
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.a;
    }

    public boolean isHeart() {
        return this.b;
    }

    public int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.a), Boolean.valueOf(this.b));
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof HeartRating)) {
            return false;
        }
        HeartRating heartRating = (HeartRating) obj;
        return this.b == heartRating.b && this.a == heartRating.a;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), 0);
        bundle.putBoolean(a(1), this.a);
        bundle.putBoolean(a(2), this.b);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HeartRating a(Bundle bundle) {
        Assertions.checkArgument(bundle.getInt(a(0), -1) == 0);
        if (bundle.getBoolean(a(1), false)) {
            return new HeartRating(bundle.getBoolean(a(2), false));
        }
        return new HeartRating();
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
