package com.google.android.material.transition.platform;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.transition.platform.MaterialContainerTransform;

/* compiled from: MaskEvaluator.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
class g {
    private final Path a = new Path();
    private final Path b = new Path();
    private final Path c = new Path();
    private final ShapeAppearancePathProvider d = ShapeAppearancePathProvider.getInstance();
    private ShapeAppearanceModel e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f, ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, RectF rectF, RectF rectF2, RectF rectF3, MaterialContainerTransform.ProgressThresholds progressThresholds) {
        this.e = j.a(shapeAppearanceModel, shapeAppearanceModel2, rectF, rectF3, progressThresholds.getStart(), progressThresholds.getEnd(), f);
        this.d.calculatePath(this.e, 1.0f, rectF2, this.b);
        this.d.calculatePath(this.e, 1.0f, rectF3, this.c);
        if (Build.VERSION.SDK_INT >= 23) {
            this.a.op(this.b, this.c, Path.Op.UNION);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 23) {
            canvas.clipPath(this.a);
            return;
        }
        canvas.clipPath(this.b);
        canvas.clipPath(this.c, Region.Op.UNION);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Path a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ShapeAppearanceModel b() {
        return this.e;
    }
}
