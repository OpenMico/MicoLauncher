package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;

/* loaded from: classes2.dex */
public class ShapeAppearancePathProvider {
    private final ShapePath[] a = new ShapePath[4];
    private final Matrix[] b = new Matrix[4];
    private final Matrix[] c = new Matrix[4];
    private final PointF d = new PointF();
    private final Path e = new Path();
    private final Path f = new Path();
    private final ShapePath g = new ShapePath();
    private final float[] h = new float[2];
    private final float[] i = new float[2];
    private final Path j = new Path();
    private final Path k = new Path();
    private boolean l = true;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public interface PathListener {
        void onCornerPathCreated(ShapePath shapePath, Matrix matrix, int i);

        void onEdgePathCreated(ShapePath shapePath, Matrix matrix, int i);
    }

    /* loaded from: classes2.dex */
    private static class a {
        static final ShapeAppearancePathProvider a = new ShapeAppearancePathProvider();
    }

    private float b(int i) {
        return (i + 1) * 90;
    }

    public ShapeAppearancePathProvider() {
        for (int i = 0; i < 4; i++) {
            this.a[i] = new ShapePath();
            this.b[i] = new Matrix();
            this.c[i] = new Matrix();
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @UiThread
    public static ShapeAppearancePathProvider getInstance() {
        return a.a;
    }

    public void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, @NonNull Path path) {
        calculatePath(shapeAppearanceModel, f, rectF, null, path);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, PathListener pathListener, @NonNull Path path) {
        path.rewind();
        this.e.rewind();
        this.f.rewind();
        this.f.addRect(rectF, Path.Direction.CW);
        b bVar = new b(shapeAppearanceModel, f, rectF, pathListener, path);
        for (int i = 0; i < 4; i++) {
            a(bVar, i);
            a(i);
        }
        for (int i2 = 0; i2 < 4; i2++) {
            b(bVar, i2);
            c(bVar, i2);
        }
        path.close();
        this.e.close();
        if (Build.VERSION.SDK_INT >= 19 && !this.e.isEmpty()) {
            path.op(this.e, Path.Op.UNION);
        }
    }

    private void a(@NonNull b bVar, int i) {
        a(i, bVar.a).getCornerPath(this.a[i], 90.0f, bVar.e, bVar.c, b(i, bVar.a));
        float b2 = b(i);
        this.b[i].reset();
        a(i, bVar.c, this.d);
        this.b[i].setTranslate(this.d.x, this.d.y);
        this.b[i].preRotate(b2);
    }

    private void a(int i) {
        this.h[0] = this.a[i].d();
        this.h[1] = this.a[i].e();
        this.b[i].mapPoints(this.h);
        float b2 = b(i);
        this.c[i].reset();
        Matrix matrix = this.c[i];
        float[] fArr = this.h;
        matrix.setTranslate(fArr[0], fArr[1]);
        this.c[i].preRotate(b2);
    }

    private void b(@NonNull b bVar, int i) {
        this.h[0] = this.a[i].b();
        this.h[1] = this.a[i].c();
        this.b[i].mapPoints(this.h);
        if (i == 0) {
            Path path = bVar.b;
            float[] fArr = this.h;
            path.moveTo(fArr[0], fArr[1]);
        } else {
            Path path2 = bVar.b;
            float[] fArr2 = this.h;
            path2.lineTo(fArr2[0], fArr2[1]);
        }
        this.a[i].applyToPath(this.b[i], bVar.b);
        if (bVar.d != null) {
            bVar.d.onCornerPathCreated(this.a[i], this.b[i], i);
        }
    }

    private void c(@NonNull b bVar, int i) {
        int i2 = (i + 1) % 4;
        this.h[0] = this.a[i].d();
        this.h[1] = this.a[i].e();
        this.b[i].mapPoints(this.h);
        this.i[0] = this.a[i2].b();
        this.i[1] = this.a[i2].c();
        this.b[i2].mapPoints(this.i);
        float[] fArr = this.h;
        float f = fArr[0];
        float[] fArr2 = this.i;
        float max = Math.max(((float) Math.hypot(f - fArr2[0], fArr[1] - fArr2[1])) - 0.001f, 0.0f);
        float a2 = a(bVar.c, i);
        this.g.reset(0.0f, 0.0f);
        EdgeTreatment c = c(i, bVar.a);
        c.getEdgePath(max, a2, bVar.e, this.g);
        this.j.reset();
        this.g.applyToPath(this.c[i], this.j);
        if (!this.l || Build.VERSION.SDK_INT < 19 || (!c.d() && !a(this.j, i) && !a(this.j, i2))) {
            this.g.applyToPath(this.c[i], bVar.b);
        } else {
            Path path = this.j;
            path.op(path, this.f, Path.Op.DIFFERENCE);
            this.h[0] = this.g.b();
            this.h[1] = this.g.c();
            this.c[i].mapPoints(this.h);
            Path path2 = this.e;
            float[] fArr3 = this.h;
            path2.moveTo(fArr3[0], fArr3[1]);
            this.g.applyToPath(this.c[i], this.e);
        }
        if (bVar.d != null) {
            bVar.d.onEdgePathCreated(this.g, this.c[i], i);
        }
    }

    @RequiresApi(19)
    private boolean a(Path path, int i) {
        this.k.reset();
        this.a[i].applyToPath(this.b[i], this.k);
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        this.k.computeBounds(rectF, true);
        path.op(this.k, Path.Op.INTERSECT);
        path.computeBounds(rectF, true);
        if (rectF.isEmpty()) {
            return rectF.width() > 1.0f && rectF.height() > 1.0f;
        }
        return true;
    }

    private float a(@NonNull RectF rectF, int i) {
        this.h[0] = this.a[i].endX;
        this.h[1] = this.a[i].endY;
        this.b[i].mapPoints(this.h);
        if (i == 1 || i == 3) {
            return Math.abs(rectF.centerX() - this.h[0]);
        }
        return Math.abs(rectF.centerY() - this.h[1]);
    }

    private CornerTreatment a(int i, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        switch (i) {
            case 1:
                return shapeAppearanceModel.getBottomRightCorner();
            case 2:
                return shapeAppearanceModel.getBottomLeftCorner();
            case 3:
                return shapeAppearanceModel.getTopLeftCorner();
            default:
                return shapeAppearanceModel.getTopRightCorner();
        }
    }

    private CornerSize b(int i, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        switch (i) {
            case 1:
                return shapeAppearanceModel.getBottomRightCornerSize();
            case 2:
                return shapeAppearanceModel.getBottomLeftCornerSize();
            case 3:
                return shapeAppearanceModel.getTopLeftCornerSize();
            default:
                return shapeAppearanceModel.getTopRightCornerSize();
        }
    }

    private EdgeTreatment c(int i, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        switch (i) {
            case 1:
                return shapeAppearanceModel.getBottomEdge();
            case 2:
                return shapeAppearanceModel.getLeftEdge();
            case 3:
                return shapeAppearanceModel.getTopEdge();
            default:
                return shapeAppearanceModel.getRightEdge();
        }
    }

    private void a(int i, @NonNull RectF rectF, @NonNull PointF pointF) {
        switch (i) {
            case 1:
                pointF.set(rectF.right, rectF.bottom);
                return;
            case 2:
                pointF.set(rectF.left, rectF.bottom);
                return;
            case 3:
                pointF.set(rectF.left, rectF.top);
                return;
            default:
                pointF.set(rectF.right, rectF.top);
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.l = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class b {
        @NonNull
        public final ShapeAppearanceModel a;
        @NonNull
        public final Path b;
        @NonNull
        public final RectF c;
        @Nullable
        public final PathListener d;
        public final float e;

        b(@NonNull ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, @Nullable PathListener pathListener, Path path) {
            this.d = pathListener;
            this.a = shapeAppearanceModel;
            this.e = f;
            this.c = rectF;
            this.b = path;
        }
    }
}
