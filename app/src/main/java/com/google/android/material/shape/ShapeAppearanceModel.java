package com.google.android.material.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import androidx.annotation.AttrRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import com.google.android.material.R;

/* loaded from: classes2.dex */
public class ShapeAppearanceModel {
    public static final CornerSize PILL = new RelativeCornerSize(0.5f);
    CornerTreatment a;
    CornerTreatment b;
    CornerTreatment c;
    CornerTreatment d;
    CornerSize e;
    CornerSize f;
    CornerSize g;
    CornerSize h;
    EdgeTreatment i;
    EdgeTreatment j;
    EdgeTreatment k;
    EdgeTreatment l;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public interface CornerSizeUnaryOperator {
        @NonNull
        CornerSize apply(@NonNull CornerSize cornerSize);
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        @NonNull
        private CornerTreatment a;
        @NonNull
        private CornerTreatment b;
        @NonNull
        private CornerTreatment c;
        @NonNull
        private CornerTreatment d;
        @NonNull
        private CornerSize e;
        @NonNull
        private CornerSize f;
        @NonNull
        private CornerSize g;
        @NonNull
        private CornerSize h;
        @NonNull
        private EdgeTreatment i;
        @NonNull
        private EdgeTreatment j;
        @NonNull
        private EdgeTreatment k;
        @NonNull
        private EdgeTreatment l;

        public Builder() {
            this.a = MaterialShapeUtils.a();
            this.b = MaterialShapeUtils.a();
            this.c = MaterialShapeUtils.a();
            this.d = MaterialShapeUtils.a();
            this.e = new AbsoluteCornerSize(0.0f);
            this.f = new AbsoluteCornerSize(0.0f);
            this.g = new AbsoluteCornerSize(0.0f);
            this.h = new AbsoluteCornerSize(0.0f);
            this.i = MaterialShapeUtils.b();
            this.j = MaterialShapeUtils.b();
            this.k = MaterialShapeUtils.b();
            this.l = MaterialShapeUtils.b();
        }

        public Builder(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
            this.a = MaterialShapeUtils.a();
            this.b = MaterialShapeUtils.a();
            this.c = MaterialShapeUtils.a();
            this.d = MaterialShapeUtils.a();
            this.e = new AbsoluteCornerSize(0.0f);
            this.f = new AbsoluteCornerSize(0.0f);
            this.g = new AbsoluteCornerSize(0.0f);
            this.h = new AbsoluteCornerSize(0.0f);
            this.i = MaterialShapeUtils.b();
            this.j = MaterialShapeUtils.b();
            this.k = MaterialShapeUtils.b();
            this.l = MaterialShapeUtils.b();
            this.a = shapeAppearanceModel.a;
            this.b = shapeAppearanceModel.b;
            this.c = shapeAppearanceModel.c;
            this.d = shapeAppearanceModel.d;
            this.e = shapeAppearanceModel.e;
            this.f = shapeAppearanceModel.f;
            this.g = shapeAppearanceModel.g;
            this.h = shapeAppearanceModel.h;
            this.i = shapeAppearanceModel.i;
            this.j = shapeAppearanceModel.j;
            this.k = shapeAppearanceModel.k;
            this.l = shapeAppearanceModel.l;
        }

        @NonNull
        public Builder setAllCorners(int i, @Dimension float f) {
            return setAllCorners(MaterialShapeUtils.a(i)).setAllCornerSizes(f);
        }

        @NonNull
        public Builder setAllCorners(@NonNull CornerTreatment cornerTreatment) {
            return setTopLeftCorner(cornerTreatment).setTopRightCorner(cornerTreatment).setBottomRightCorner(cornerTreatment).setBottomLeftCorner(cornerTreatment);
        }

        @NonNull
        public Builder setAllCornerSizes(@NonNull CornerSize cornerSize) {
            return setTopLeftCornerSize(cornerSize).setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setAllCornerSizes(@Dimension float f) {
            return setTopLeftCornerSize(f).setTopRightCornerSize(f).setBottomRightCornerSize(f).setBottomLeftCornerSize(f);
        }

        @NonNull
        public Builder setTopLeftCornerSize(@Dimension float f) {
            this.e = new AbsoluteCornerSize(f);
            return this;
        }

        @NonNull
        public Builder setTopLeftCornerSize(@NonNull CornerSize cornerSize) {
            this.e = cornerSize;
            return this;
        }

        @NonNull
        public Builder setTopRightCornerSize(@Dimension float f) {
            this.f = new AbsoluteCornerSize(f);
            return this;
        }

        @NonNull
        public Builder setTopRightCornerSize(@NonNull CornerSize cornerSize) {
            this.f = cornerSize;
            return this;
        }

        @NonNull
        public Builder setBottomRightCornerSize(@Dimension float f) {
            this.g = new AbsoluteCornerSize(f);
            return this;
        }

        @NonNull
        public Builder setBottomRightCornerSize(@NonNull CornerSize cornerSize) {
            this.g = cornerSize;
            return this;
        }

        @NonNull
        public Builder setBottomLeftCornerSize(@Dimension float f) {
            this.h = new AbsoluteCornerSize(f);
            return this;
        }

        @NonNull
        public Builder setBottomLeftCornerSize(@NonNull CornerSize cornerSize) {
            this.h = cornerSize;
            return this;
        }

        @NonNull
        public Builder setTopLeftCorner(int i, @Dimension float f) {
            return setTopLeftCorner(MaterialShapeUtils.a(i)).setTopLeftCornerSize(f);
        }

        @NonNull
        public Builder setTopLeftCorner(int i, @NonNull CornerSize cornerSize) {
            return setTopLeftCorner(MaterialShapeUtils.a(i)).setTopLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopLeftCorner(@NonNull CornerTreatment cornerTreatment) {
            this.a = cornerTreatment;
            float a = a(cornerTreatment);
            if (a != -1.0f) {
                setTopLeftCornerSize(a);
            }
            return this;
        }

        @NonNull
        public Builder setTopRightCorner(int i, @Dimension float f) {
            return setTopRightCorner(MaterialShapeUtils.a(i)).setTopRightCornerSize(f);
        }

        @NonNull
        public Builder setTopRightCorner(int i, @NonNull CornerSize cornerSize) {
            return setTopRightCorner(MaterialShapeUtils.a(i)).setTopRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopRightCorner(@NonNull CornerTreatment cornerTreatment) {
            this.b = cornerTreatment;
            float a = a(cornerTreatment);
            if (a != -1.0f) {
                setTopRightCornerSize(a);
            }
            return this;
        }

        @NonNull
        public Builder setBottomRightCorner(int i, @Dimension float f) {
            return setBottomRightCorner(MaterialShapeUtils.a(i)).setBottomRightCornerSize(f);
        }

        @NonNull
        public Builder setBottomRightCorner(int i, @NonNull CornerSize cornerSize) {
            return setBottomRightCorner(MaterialShapeUtils.a(i)).setBottomRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomRightCorner(@NonNull CornerTreatment cornerTreatment) {
            this.c = cornerTreatment;
            float a = a(cornerTreatment);
            if (a != -1.0f) {
                setBottomRightCornerSize(a);
            }
            return this;
        }

        @NonNull
        public Builder setBottomLeftCorner(int i, @Dimension float f) {
            return setBottomLeftCorner(MaterialShapeUtils.a(i)).setBottomLeftCornerSize(f);
        }

        @NonNull
        public Builder setBottomLeftCorner(int i, @NonNull CornerSize cornerSize) {
            return setBottomLeftCorner(MaterialShapeUtils.a(i)).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomLeftCorner(@NonNull CornerTreatment cornerTreatment) {
            this.d = cornerTreatment;
            float a = a(cornerTreatment);
            if (a != -1.0f) {
                setBottomLeftCornerSize(a);
            }
            return this;
        }

        @NonNull
        public Builder setAllEdges(@NonNull EdgeTreatment edgeTreatment) {
            return setLeftEdge(edgeTreatment).setTopEdge(edgeTreatment).setRightEdge(edgeTreatment).setBottomEdge(edgeTreatment);
        }

        @NonNull
        public Builder setLeftEdge(@NonNull EdgeTreatment edgeTreatment) {
            this.l = edgeTreatment;
            return this;
        }

        @NonNull
        public Builder setTopEdge(@NonNull EdgeTreatment edgeTreatment) {
            this.i = edgeTreatment;
            return this;
        }

        @NonNull
        public Builder setRightEdge(@NonNull EdgeTreatment edgeTreatment) {
            this.j = edgeTreatment;
            return this;
        }

        @NonNull
        public Builder setBottomEdge(@NonNull EdgeTreatment edgeTreatment) {
            this.k = edgeTreatment;
            return this;
        }

        private static float a(CornerTreatment cornerTreatment) {
            if (cornerTreatment instanceof RoundedCornerTreatment) {
                return ((RoundedCornerTreatment) cornerTreatment).a;
            }
            if (cornerTreatment instanceof CutCornerTreatment) {
                return ((CutCornerTreatment) cornerTreatment).a;
            }
            return -1.0f;
        }

        @NonNull
        public ShapeAppearanceModel build() {
            return new ShapeAppearanceModel(this);
        }
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        return builder(context, attributeSet, i, i2, 0);
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2, int i3) {
        return builder(context, attributeSet, i, i2, new AbsoluteCornerSize(i3));
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2, @NonNull CornerSize cornerSize) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialShape, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.MaterialShape_shapeAppearanceOverlay, 0);
        obtainStyledAttributes.recycle();
        return a(context, resourceId, resourceId2, cornerSize);
    }

    @NonNull
    public static Builder builder(Context context, @StyleRes int i, @StyleRes int i2) {
        return a(context, i, i2, 0);
    }

    @NonNull
    private static Builder a(Context context, @StyleRes int i, @StyleRes int i2, int i3) {
        return a(context, i, i2, new AbsoluteCornerSize(i3));
    }

    @NonNull
    private static Builder a(Context context, @StyleRes int i, @StyleRes int i2, @NonNull CornerSize cornerSize) {
        if (i2 != 0) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
            i = i2;
            context = contextThemeWrapper;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, R.styleable.ShapeAppearance);
        try {
            int i3 = obtainStyledAttributes.getInt(R.styleable.ShapeAppearance_cornerFamily, 0);
            int i4 = obtainStyledAttributes.getInt(R.styleable.ShapeAppearance_cornerFamilyTopLeft, i3);
            int i5 = obtainStyledAttributes.getInt(R.styleable.ShapeAppearance_cornerFamilyTopRight, i3);
            int i6 = obtainStyledAttributes.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomRight, i3);
            int i7 = obtainStyledAttributes.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomLeft, i3);
            CornerSize a = a(obtainStyledAttributes, R.styleable.ShapeAppearance_cornerSize, cornerSize);
            CornerSize a2 = a(obtainStyledAttributes, R.styleable.ShapeAppearance_cornerSizeTopLeft, a);
            CornerSize a3 = a(obtainStyledAttributes, R.styleable.ShapeAppearance_cornerSizeTopRight, a);
            CornerSize a4 = a(obtainStyledAttributes, R.styleable.ShapeAppearance_cornerSizeBottomRight, a);
            return new Builder().setTopLeftCorner(i4, a2).setTopRightCorner(i5, a3).setBottomRightCorner(i6, a4).setBottomLeftCorner(i7, a(obtainStyledAttributes, R.styleable.ShapeAppearance_cornerSizeBottomLeft, a));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @NonNull
    private static CornerSize a(TypedArray typedArray, int i, @NonNull CornerSize cornerSize) {
        TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue == null) {
            return cornerSize;
        }
        if (peekValue.type == 5) {
            return new AbsoluteCornerSize(TypedValue.complexToDimensionPixelSize(peekValue.data, typedArray.getResources().getDisplayMetrics()));
        }
        return peekValue.type == 6 ? new RelativeCornerSize(peekValue.getFraction(1.0f, 1.0f)) : cornerSize;
    }

    private ShapeAppearanceModel(@NonNull Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
    }

    public ShapeAppearanceModel() {
        this.a = MaterialShapeUtils.a();
        this.b = MaterialShapeUtils.a();
        this.c = MaterialShapeUtils.a();
        this.d = MaterialShapeUtils.a();
        this.e = new AbsoluteCornerSize(0.0f);
        this.f = new AbsoluteCornerSize(0.0f);
        this.g = new AbsoluteCornerSize(0.0f);
        this.h = new AbsoluteCornerSize(0.0f);
        this.i = MaterialShapeUtils.b();
        this.j = MaterialShapeUtils.b();
        this.k = MaterialShapeUtils.b();
        this.l = MaterialShapeUtils.b();
    }

    @NonNull
    public CornerTreatment getTopLeftCorner() {
        return this.a;
    }

    @NonNull
    public CornerTreatment getTopRightCorner() {
        return this.b;
    }

    @NonNull
    public CornerTreatment getBottomRightCorner() {
        return this.c;
    }

    @NonNull
    public CornerTreatment getBottomLeftCorner() {
        return this.d;
    }

    @NonNull
    public CornerSize getTopLeftCornerSize() {
        return this.e;
    }

    @NonNull
    public CornerSize getTopRightCornerSize() {
        return this.f;
    }

    @NonNull
    public CornerSize getBottomRightCornerSize() {
        return this.g;
    }

    @NonNull
    public CornerSize getBottomLeftCornerSize() {
        return this.h;
    }

    @NonNull
    public EdgeTreatment getLeftEdge() {
        return this.l;
    }

    @NonNull
    public EdgeTreatment getTopEdge() {
        return this.i;
    }

    @NonNull
    public EdgeTreatment getRightEdge() {
        return this.j;
    }

    @NonNull
    public EdgeTreatment getBottomEdge() {
        return this.k;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @NonNull
    public ShapeAppearanceModel withCornerSize(float f) {
        return toBuilder().setAllCornerSizes(f).build();
    }

    @NonNull
    public ShapeAppearanceModel withCornerSize(@NonNull CornerSize cornerSize) {
        return toBuilder().setAllCornerSizes(cornerSize).build();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ShapeAppearanceModel withTransformedCornerSizes(@NonNull CornerSizeUnaryOperator cornerSizeUnaryOperator) {
        return toBuilder().setTopLeftCornerSize(cornerSizeUnaryOperator.apply(getTopLeftCornerSize())).setTopRightCornerSize(cornerSizeUnaryOperator.apply(getTopRightCornerSize())).setBottomLeftCornerSize(cornerSizeUnaryOperator.apply(getBottomLeftCornerSize())).setBottomRightCornerSize(cornerSizeUnaryOperator.apply(getBottomRightCornerSize())).build();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRoundRect(@NonNull RectF rectF) {
        boolean z = this.l.getClass().equals(EdgeTreatment.class) && this.j.getClass().equals(EdgeTreatment.class) && this.i.getClass().equals(EdgeTreatment.class) && this.k.getClass().equals(EdgeTreatment.class);
        float cornerSize = this.e.getCornerSize(rectF);
        return z && ((this.f.getCornerSize(rectF) > cornerSize ? 1 : (this.f.getCornerSize(rectF) == cornerSize ? 0 : -1)) == 0 && (this.h.getCornerSize(rectF) > cornerSize ? 1 : (this.h.getCornerSize(rectF) == cornerSize ? 0 : -1)) == 0 && (this.g.getCornerSize(rectF) > cornerSize ? 1 : (this.g.getCornerSize(rectF) == cornerSize ? 0 : -1)) == 0) && ((this.b instanceof RoundedCornerTreatment) && (this.a instanceof RoundedCornerTreatment) && (this.c instanceof RoundedCornerTreatment) && (this.d instanceof RoundedCornerTreatment));
    }
}
