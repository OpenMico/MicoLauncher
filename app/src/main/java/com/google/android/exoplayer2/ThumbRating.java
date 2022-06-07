package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes.dex */
public final class ThumbRating extends Rating {
    public static final Bundleable.Creator<ThumbRating> CREATOR = $$Lambda$ThumbRating$bZ8YY3_NAYTylU1oy4FI7yhs8A.INSTANCE;
    private final boolean a;
    private final boolean b;

    public ThumbRating() {
        this.a = false;
        this.b = false;
    }

    public ThumbRating(boolean z) {
        this.a = true;
        this.b = z;
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.a;
    }

    public boolean isThumbsUp() {
        return this.b;
    }

    public int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.a), Boolean.valueOf(this.b));
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ThumbRating)) {
            return false;
        }
        ThumbRating thumbRating = (ThumbRating) obj;
        return this.b == thumbRating.b && this.a == thumbRating.a;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), 3);
        bundle.putBoolean(a(1), this.a);
        bundle.putBoolean(a(2), this.b);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ThumbRating a(Bundle bundle) {
        Assertions.checkArgument(bundle.getInt(a(0), -1) == 3);
        if (bundle.getBoolean(a(1), false)) {
            return new ThumbRating(bundle.getBoolean(a(2), false));
        }
        return new ThumbRating();
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
