package com.google.android.exoplayer2;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;

/* loaded from: classes.dex */
public abstract class Rating implements Bundleable {
    public static final Bundleable.Creator<Rating> CREATOR = $$Lambda$Rating$rXL92kR7drKEg_SIImZj0Kint0.INSTANCE;
    public static final float RATING_UNSET = -1.0f;

    public abstract boolean isRated();

    public static Rating a(Bundle bundle) {
        int i = bundle.getInt(a(0), -1);
        switch (i) {
            case 0:
                return HeartRating.CREATOR.fromBundle(bundle);
            case 1:
                return PercentageRating.CREATOR.fromBundle(bundle);
            case 2:
                return StarRating.CREATOR.fromBundle(bundle);
            case 3:
                return ThumbRating.CREATOR.fromBundle(bundle);
            default:
                StringBuilder sb = new StringBuilder(44);
                sb.append("Encountered unknown rating type: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
