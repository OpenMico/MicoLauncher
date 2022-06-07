package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;
import androidx.core.os.BuildCompat;
import androidx.core.util.ObjectsCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class DisplayCutoutCompat {
    private final Object a;

    public DisplayCutoutCompat(Rect rect, List<Rect> list) {
        this(Build.VERSION.SDK_INT >= 28 ? new DisplayCutout(rect, list) : null);
    }

    public DisplayCutoutCompat(@NonNull Insets insets, @Nullable Rect rect, @Nullable Rect rect2, @Nullable Rect rect3, @Nullable Rect rect4, @NonNull Insets insets2) {
        this(a(insets, rect, rect2, rect3, rect4, insets2));
    }

    private static DisplayCutout a(@NonNull Insets insets, @Nullable Rect rect, @Nullable Rect rect2, @Nullable Rect rect3, @Nullable Rect rect4, @NonNull Insets insets2) {
        if (BuildCompat.isAtLeastR()) {
            return new DisplayCutout(insets.toPlatformInsets(), rect, rect2, rect3, rect4, insets2.toPlatformInsets());
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return new DisplayCutout(insets.toPlatformInsets(), rect, rect2, rect3, rect4);
        }
        if (Build.VERSION.SDK_INT < 28) {
            return null;
        }
        Rect rect5 = new Rect(insets.left, insets.top, insets.right, insets.bottom);
        ArrayList arrayList = new ArrayList();
        if (rect != null) {
            arrayList.add(rect);
        }
        if (rect2 != null) {
            arrayList.add(rect2);
        }
        if (rect3 != null) {
            arrayList.add(rect3);
        }
        if (rect4 != null) {
            arrayList.add(rect4);
        }
        return new DisplayCutout(rect5, arrayList);
    }

    private DisplayCutoutCompat(Object obj) {
        this.a = obj;
    }

    public int getSafeInsetTop() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.a).getSafeInsetTop();
        }
        return 0;
    }

    public int getSafeInsetBottom() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.a).getSafeInsetBottom();
        }
        return 0;
    }

    public int getSafeInsetLeft() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.a).getSafeInsetLeft();
        }
        return 0;
    }

    public int getSafeInsetRight() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.a).getSafeInsetRight();
        }
        return 0;
    }

    @NonNull
    public List<Rect> getBoundingRects() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.a).getBoundingRects();
        }
        return Collections.emptyList();
    }

    @NonNull
    public Insets getWaterfallInsets() {
        if (BuildCompat.isAtLeastR()) {
            return Insets.toCompatInsets(((DisplayCutout) this.a).getWaterfallInsets());
        }
        return Insets.NONE;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ObjectsCompat.equals(this.a, ((DisplayCutoutCompat) obj).a);
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public String toString() {
        return "DisplayCutoutCompat{" + this.a + "}";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DisplayCutoutCompat a(Object obj) {
        if (obj == null) {
            return null;
        }
        return new DisplayCutoutCompat(obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(api = 28)
    public DisplayCutout a() {
        return (DisplayCutout) this.a;
    }
}
