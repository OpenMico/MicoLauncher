package androidx.core.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

/* loaded from: classes.dex */
public class ActivityOptionsCompat {
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";

    @Nullable
    public Rect getLaunchBounds() {
        return null;
    }

    public void requestUsageTimeReport(@NonNull PendingIntent pendingIntent) {
    }

    @NonNull
    public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
        return this;
    }

    @Nullable
    public Bundle toBundle() {
        return null;
    }

    public void update(@NonNull ActivityOptionsCompat activityOptionsCompat) {
    }

    @NonNull
    public static ActivityOptionsCompat makeCustomAnimation(@NonNull Context context, int i, int i2) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new a(ActivityOptions.makeCustomAnimation(context, i, i2));
        }
        return new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeScaleUpAnimation(@NonNull View view, int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new a(ActivityOptions.makeScaleUpAnimation(view, i, i2, i3, i4));
        }
        return new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeClipRevealAnimation(@NonNull View view, int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 23) {
            return new a(ActivityOptions.makeClipRevealAnimation(view, i, i2, i3, i4));
        }
        return new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(@NonNull View view, @NonNull Bitmap bitmap, int i, int i2) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new a(ActivityOptions.makeThumbnailScaleUpAnimation(view, bitmap, i, i2));
        }
        return new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity, @NonNull View view, @NonNull String str) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new a(ActivityOptions.makeSceneTransitionAnimation(activity, view, str));
        }
        return new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity, Pair<View, String>... pairArr) {
        if (Build.VERSION.SDK_INT < 21) {
            return new ActivityOptionsCompat();
        }
        android.util.Pair[] pairArr2 = null;
        if (pairArr != null) {
            pairArr2 = new android.util.Pair[pairArr.length];
            for (int i = 0; i < pairArr.length; i++) {
                pairArr2[i] = android.util.Pair.create(pairArr[i].first, pairArr[i].second);
            }
        }
        return new a(ActivityOptions.makeSceneTransitionAnimation(activity, pairArr2));
    }

    @NonNull
    public static ActivityOptionsCompat makeTaskLaunchBehind() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new a(ActivityOptions.makeTaskLaunchBehind());
        }
        return new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeBasic() {
        if (Build.VERSION.SDK_INT >= 23) {
            return new a(ActivityOptions.makeBasic());
        }
        return new ActivityOptionsCompat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(16)
    /* loaded from: classes.dex */
    public static class a extends ActivityOptionsCompat {
        private final ActivityOptions a;

        a(ActivityOptions activityOptions) {
            this.a = activityOptions;
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public Bundle toBundle() {
            return this.a.toBundle();
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public void update(@NonNull ActivityOptionsCompat activityOptionsCompat) {
            if (activityOptionsCompat instanceof a) {
                this.a.update(((a) activityOptionsCompat).a);
            }
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public void requestUsageTimeReport(@NonNull PendingIntent pendingIntent) {
            if (Build.VERSION.SDK_INT >= 23) {
                this.a.requestUsageTimeReport(pendingIntent);
            }
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        @NonNull
        public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
            return Build.VERSION.SDK_INT < 24 ? this : new a(this.a.setLaunchBounds(rect));
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public Rect getLaunchBounds() {
            if (Build.VERSION.SDK_INT < 24) {
                return null;
            }
            return this.a.getLaunchBounds();
        }
    }

    protected ActivityOptionsCompat() {
    }
}
