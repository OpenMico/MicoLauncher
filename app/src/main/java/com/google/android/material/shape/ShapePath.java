package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.shadow.ShadowRenderer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ShapePath {
    protected static final float ANGLE_LEFT = 180.0f;
    private final List<PathOperation> a = new ArrayList();
    private final List<c> b = new ArrayList();
    private boolean c;
    @Deprecated
    public float currentShadowAngle;
    @Deprecated
    public float endShadowAngle;
    @Deprecated
    public float endX;
    @Deprecated
    public float endY;
    @Deprecated
    public float startX;
    @Deprecated
    public float startY;

    /* loaded from: classes2.dex */
    public static abstract class PathOperation {
        protected final Matrix matrix = new Matrix();

        public abstract void applyToPath(Matrix matrix, Path path);
    }

    public ShapePath() {
        reset(0.0f, 0.0f);
    }

    public ShapePath(float f, float f2) {
        reset(f, f2);
    }

    public void reset(float f, float f2) {
        reset(f, f2, 270.0f, 0.0f);
    }

    public void reset(float f, float f2, float f3, float f4) {
        b(f);
        c(f2);
        d(f);
        e(f2);
        f(f3);
        g((f3 + f4) % 360.0f);
        this.a.clear();
        this.b.clear();
        this.c = false;
    }

    public void lineTo(float f, float f2) {
        PathLineOperation pathLineOperation = new PathLineOperation();
        pathLineOperation.a = f;
        pathLineOperation.b = f2;
        this.a.add(pathLineOperation);
        b bVar = new b(pathLineOperation, d(), e());
        a(bVar, bVar.a() + 270.0f, bVar.a() + 270.0f);
        d(f);
        e(f2);
    }

    @RequiresApi(21)
    public void quadToPoint(float f, float f2, float f3, float f4) {
        PathQuadOperation pathQuadOperation = new PathQuadOperation();
        pathQuadOperation.d(f);
        pathQuadOperation.b(f2);
        pathQuadOperation.a(f3);
        pathQuadOperation.c(f4);
        this.a.add(pathQuadOperation);
        this.c = true;
        d(f3);
        e(f4);
    }

    @RequiresApi(21)
    public void cubicToPoint(float f, float f2, float f3, float f4, float f5, float f6) {
        this.a.add(new PathCubicOperation(f, f2, f3, f4, f5, f6));
        this.c = true;
        d(f5);
        e(f6);
    }

    public void addArc(float f, float f2, float f3, float f4, float f5, float f6) {
        PathArcOperation pathArcOperation = new PathArcOperation(f, f2, f3, f4);
        pathArcOperation.e(f5);
        pathArcOperation.f(f6);
        this.a.add(pathArcOperation);
        a aVar = new a(pathArcOperation);
        float f7 = f5 + f6;
        boolean z = f6 < 0.0f;
        if (z) {
            f5 = (f5 + ANGLE_LEFT) % 360.0f;
        }
        a(aVar, f5, z ? (ANGLE_LEFT + f7) % 360.0f : f7);
        double d = f7;
        d(((f + f3) * 0.5f) + (((f3 - f) / 2.0f) * ((float) Math.cos(Math.toRadians(d)))));
        e(((f2 + f4) * 0.5f) + (((f4 - f2) / 2.0f) * ((float) Math.sin(Math.toRadians(d)))));
    }

    public void applyToPath(Matrix matrix, Path path) {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            this.a.get(i).applyToPath(matrix, path);
        }
    }

    @NonNull
    public c a(Matrix matrix) {
        a(g());
        final Matrix matrix2 = new Matrix(matrix);
        final ArrayList arrayList = new ArrayList(this.b);
        return new c() { // from class: com.google.android.material.shape.ShapePath.1
            @Override // com.google.android.material.shape.ShapePath.c
            public void a(Matrix matrix3, ShadowRenderer shadowRenderer, int i, Canvas canvas) {
                for (c cVar : arrayList) {
                    cVar.a(matrix2, shadowRenderer, i, canvas);
                }
            }
        };
    }

    private void a(c cVar, float f, float f2) {
        a(f);
        this.b.add(cVar);
        f(f2);
    }

    public boolean a() {
        return this.c;
    }

    private void a(float f) {
        if (f() != f) {
            float f2 = ((f - f()) + 360.0f) % 360.0f;
            if (f2 <= ANGLE_LEFT) {
                PathArcOperation pathArcOperation = new PathArcOperation(d(), e(), d(), e());
                pathArcOperation.e(f());
                pathArcOperation.f(f2);
                this.b.add(new a(pathArcOperation));
                f(f);
            }
        }
    }

    public float b() {
        return this.startX;
    }

    public float c() {
        return this.startY;
    }

    public float d() {
        return this.endX;
    }

    public float e() {
        return this.endY;
    }

    private float f() {
        return this.currentShadowAngle;
    }

    private float g() {
        return this.endShadowAngle;
    }

    private void b(float f) {
        this.startX = f;
    }

    private void c(float f) {
        this.startY = f;
    }

    private void d(float f) {
        this.endX = f;
    }

    private void e(float f) {
        this.endY = f;
    }

    private void f(float f) {
        this.currentShadowAngle = f;
    }

    private void g(float f) {
        this.endShadowAngle = f;
    }

    /* loaded from: classes2.dex */
    public static abstract class c {
        static final Matrix d = new Matrix();

        public abstract void a(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas);

        c() {
        }

        public final void a(ShadowRenderer shadowRenderer, int i, Canvas canvas) {
            a(d, shadowRenderer, i, canvas);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class b extends c {
        private final PathLineOperation a;
        private final float b;
        private final float c;

        public b(PathLineOperation pathLineOperation, float f, float f2) {
            this.a = pathLineOperation;
            this.b = f;
            this.c = f2;
        }

        @Override // com.google.android.material.shape.ShapePath.c
        public void a(Matrix matrix, @NonNull ShadowRenderer shadowRenderer, int i, @NonNull Canvas canvas) {
            RectF rectF = new RectF(0.0f, 0.0f, (float) Math.hypot(this.a.b - this.c, this.a.a - this.b), 0.0f);
            Matrix matrix2 = new Matrix(matrix);
            matrix2.preTranslate(this.b, this.c);
            matrix2.preRotate(a());
            shadowRenderer.drawEdgeShadow(canvas, matrix2, rectF, i);
        }

        float a() {
            return (float) Math.toDegrees(Math.atan((this.a.b - this.c) / (this.a.a - this.b)));
        }
    }

    /* loaded from: classes2.dex */
    public static class a extends c {
        private final PathArcOperation a;

        public a(PathArcOperation pathArcOperation) {
            this.a = pathArcOperation;
        }

        @Override // com.google.android.material.shape.ShapePath.c
        public void a(Matrix matrix, @NonNull ShadowRenderer shadowRenderer, int i, @NonNull Canvas canvas) {
            shadowRenderer.drawCornerShadow(canvas, matrix, new RectF(this.a.a(), this.a.b(), this.a.c(), this.a.d()), i, this.a.e(), this.a.f());
        }
    }

    /* loaded from: classes2.dex */
    public static class PathLineOperation extends PathOperation {
        private float a;
        private float b;

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(@NonNull Matrix matrix, @NonNull Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.lineTo(this.a, this.b);
            path.transform(matrix);
        }
    }

    /* loaded from: classes2.dex */
    public static class PathQuadOperation extends PathOperation {
        @Deprecated
        public float controlX;
        @Deprecated
        public float controlY;
        @Deprecated
        public float endX;
        @Deprecated
        public float endY;

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(@NonNull Matrix matrix, @NonNull Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.quadTo(d(), b(), a(), c());
            path.transform(matrix);
        }

        private float a() {
            return this.endX;
        }

        public void a(float f) {
            this.endX = f;
        }

        private float b() {
            return this.controlY;
        }

        public void b(float f) {
            this.controlY = f;
        }

        private float c() {
            return this.endY;
        }

        public void c(float f) {
            this.endY = f;
        }

        private float d() {
            return this.controlX;
        }

        public void d(float f) {
            this.controlX = f;
        }
    }

    /* loaded from: classes2.dex */
    public static class PathArcOperation extends PathOperation {
        private static final RectF a = new RectF();
        @Deprecated
        public float bottom;
        @Deprecated
        public float left;
        @Deprecated
        public float right;
        @Deprecated
        public float startAngle;
        @Deprecated
        public float sweepAngle;
        @Deprecated
        public float top;

        public PathArcOperation(float f, float f2, float f3, float f4) {
            a(f);
            b(f2);
            c(f3);
            d(f4);
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(@NonNull Matrix matrix, @NonNull Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            a.set(a(), b(), c(), d());
            path.arcTo(a, e(), f(), false);
            path.transform(matrix);
        }

        public float a() {
            return this.left;
        }

        public float b() {
            return this.top;
        }

        public float c() {
            return this.right;
        }

        public float d() {
            return this.bottom;
        }

        private void a(float f) {
            this.left = f;
        }

        private void b(float f) {
            this.top = f;
        }

        private void c(float f) {
            this.right = f;
        }

        private void d(float f) {
            this.bottom = f;
        }

        public float e() {
            return this.startAngle;
        }

        public float f() {
            return this.sweepAngle;
        }

        public void e(float f) {
            this.startAngle = f;
        }

        public void f(float f) {
            this.sweepAngle = f;
        }
    }

    /* loaded from: classes2.dex */
    public static class PathCubicOperation extends PathOperation {
        private float a;
        private float b;
        private float c;
        private float d;
        private float e;
        private float f;

        public PathCubicOperation(float f, float f2, float f3, float f4, float f5, float f6) {
            a(f);
            b(f2);
            c(f3);
            d(f4);
            e(f5);
            f(f6);
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(@NonNull Matrix matrix, @NonNull Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.cubicTo(this.a, this.b, this.c, this.d, this.e, this.f);
            path.transform(matrix);
        }

        private void a(float f) {
            this.a = f;
        }

        private void b(float f) {
            this.b = f;
        }

        private void c(float f) {
            this.c = f;
        }

        private void d(float f) {
            this.d = f;
        }

        private void e(float f) {
            this.e = f;
        }

        private void f(float f) {
            this.f = f;
        }
    }
}
