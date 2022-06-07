package com.google.android.material.shape;

import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import com.google.android.material.internal.ViewUtils;

/* loaded from: classes2.dex */
public class MaterialShapeUtils {
    private MaterialShapeUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static CornerTreatment a(int i) {
        switch (i) {
            case 0:
                return new RoundedCornerTreatment();
            case 1:
                return new CutCornerTreatment();
            default:
                return a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static CornerTreatment a() {
        return new RoundedCornerTreatment();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static EdgeTreatment b() {
        return new EdgeTreatment();
    }

    public static void setElevation(@NonNull View view, float f) {
        Drawable background = view.getBackground();
        if (background instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) background).setElevation(f);
        }
    }

    public static void setParentAbsoluteElevation(@NonNull View view) {
        Drawable background = view.getBackground();
        if (background instanceof MaterialShapeDrawable) {
            setParentAbsoluteElevation(view, (MaterialShapeDrawable) background);
        }
    }

    public static void setParentAbsoluteElevation(@NonNull View view, @NonNull MaterialShapeDrawable materialShapeDrawable) {
        if (materialShapeDrawable.isElevationOverlayEnabled()) {
            materialShapeDrawable.setParentAbsoluteElevation(ViewUtils.getParentAbsoluteElevation(view));
        }
    }
}
