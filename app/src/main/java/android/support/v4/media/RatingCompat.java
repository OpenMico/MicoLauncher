package android.support.v4.media;

import android.annotation.SuppressLint;
import android.media.Rating;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class RatingCompat implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator<RatingCompat>() { // from class: android.support.v4.media.RatingCompat.1
        /* renamed from: a */
        public RatingCompat createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        /* renamed from: a */
        public RatingCompat[] newArray(int i) {
            return new RatingCompat[i];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private final int a;
    private final float b;
    private Object c;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface StarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface Style {
    }

    RatingCompat(int i, float f) {
        this.a = i;
        this.b = f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rating:style=");
        sb.append(this.a);
        sb.append(" rating=");
        float f = this.b;
        sb.append(f < 0.0f ? "unrated" : String.valueOf(f));
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.a;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeFloat(this.b);
    }

    public static RatingCompat newUnratedRating(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new RatingCompat(i, -1.0f);
            default:
                return null;
        }
    }

    public static RatingCompat newHeartRating(boolean z) {
        return new RatingCompat(1, z ? 1.0f : 0.0f);
    }

    public static RatingCompat newThumbRating(boolean z) {
        return new RatingCompat(2, z ? 1.0f : 0.0f);
    }

    public static RatingCompat newStarRating(int i, float f) {
        float f2;
        switch (i) {
            case 3:
                f2 = 3.0f;
                break;
            case 4:
                f2 = 4.0f;
                break;
            case 5:
                f2 = 5.0f;
                break;
            default:
                Log.e("Rating", "Invalid rating style (" + i + ") for a star rating");
                return null;
        }
        if (f >= 0.0f && f <= f2) {
            return new RatingCompat(i, f);
        }
        Log.e("Rating", "Trying to set out of range star-based rating");
        return null;
    }

    public static RatingCompat newPercentageRating(float f) {
        if (f >= 0.0f && f <= 100.0f) {
            return new RatingCompat(6, f);
        }
        Log.e("Rating", "Invalid percentage-based rating value");
        return null;
    }

    public boolean isRated() {
        return this.b >= 0.0f;
    }

    public int getRatingStyle() {
        return this.a;
    }

    public boolean hasHeart() {
        return this.a == 1 && this.b == 1.0f;
    }

    public boolean isThumbUp() {
        return this.a == 2 && this.b == 1.0f;
    }

    public float getStarRating() {
        switch (this.a) {
            case 3:
            case 4:
            case 5:
                if (isRated()) {
                    return this.b;
                }
                return -1.0f;
            default:
                return -1.0f;
        }
    }

    public float getPercentRating() {
        if (this.a != 6 || !isRated()) {
            return -1.0f;
        }
        return this.b;
    }

    public static RatingCompat fromRating(Object obj) {
        RatingCompat ratingCompat;
        if (obj == null || Build.VERSION.SDK_INT < 19) {
            return null;
        }
        Rating rating = (Rating) obj;
        int a2 = a.a(rating);
        if (a.b(rating)) {
            switch (a2) {
                case 1:
                    ratingCompat = newHeartRating(a.c(rating));
                    break;
                case 2:
                    ratingCompat = newThumbRating(a.d(rating));
                    break;
                case 3:
                case 4:
                case 5:
                    ratingCompat = newStarRating(a2, a.e(rating));
                    break;
                case 6:
                    ratingCompat = newPercentageRating(a.f(rating));
                    break;
                default:
                    return null;
            }
        } else {
            ratingCompat = newUnratedRating(a2);
        }
        ratingCompat.c = obj;
        return ratingCompat;
    }

    public Object getRating() {
        if (this.c == null && Build.VERSION.SDK_INT >= 19) {
            if (isRated()) {
                int i = this.a;
                switch (i) {
                    case 1:
                        this.c = a.a(hasHeart());
                        break;
                    case 2:
                        this.c = a.b(isThumbUp());
                        break;
                    case 3:
                    case 4:
                    case 5:
                        this.c = a.a(i, getStarRating());
                        break;
                    case 6:
                        this.c = a.a(getPercentRating());
                        break;
                    default:
                        return null;
                }
            } else {
                this.c = a.a(this.a);
            }
        }
        return this.c;
    }

    @RequiresApi(19)
    /* loaded from: classes.dex */
    private static class a {
        @DoNotInline
        static int a(Rating rating) {
            return rating.getRatingStyle();
        }

        @DoNotInline
        static boolean b(Rating rating) {
            return rating.isRated();
        }

        @DoNotInline
        static boolean c(Rating rating) {
            return rating.hasHeart();
        }

        @DoNotInline
        static boolean d(Rating rating) {
            return rating.isThumbUp();
        }

        @DoNotInline
        static float e(Rating rating) {
            return rating.getStarRating();
        }

        @DoNotInline
        static float f(Rating rating) {
            return rating.getPercentRating();
        }

        @DoNotInline
        static Rating a(boolean z) {
            return Rating.newHeartRating(z);
        }

        @DoNotInline
        static Rating b(boolean z) {
            return Rating.newThumbRating(z);
        }

        @DoNotInline
        static Rating a(int i, float f) {
            return Rating.newStarRating(i, f);
        }

        @DoNotInline
        static Rating a(float f) {
            return Rating.newPercentageRating(f);
        }

        @DoNotInline
        static Rating a(int i) {
            return Rating.newUnratedRating(i);
        }
    }
}
