package com.google.android.material.shape;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.util.ObjectsCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.ShapePath;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.BitSet;

/* loaded from: classes2.dex */
public class MaterialShapeDrawable extends Drawable implements TintAwareDrawable, Shapeable {
    public static final int SHADOW_COMPAT_MODE_ALWAYS = 2;
    public static final int SHADOW_COMPAT_MODE_DEFAULT = 0;
    public static final int SHADOW_COMPAT_MODE_NEVER = 1;
    private static final String a = "MaterialShapeDrawable";
    private static final Paint b = new Paint(1);
    private a c;
    private final ShapePath.c[] d;
    private final ShapePath.c[] e;
    private final BitSet f;
    private boolean g;
    private final Matrix h;
    private final Path i;
    private final Path j;
    private final RectF k;
    private final RectF l;
    private final Region m;
    private final Region n;
    private ShapeAppearanceModel o;
    private final Paint p;
    private final Paint q;
    private final ShadowRenderer r;
    @NonNull
    private final ShapeAppearancePathProvider.PathListener s;
    private final ShapeAppearancePathProvider t;
    @Nullable
    private PorterDuffColorFilter u;
    @Nullable
    private PorterDuffColorFilter v;
    @NonNull
    private final RectF w;
    private boolean x;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface CompatibilityShadowMode {
    }

    private static int a(int i, int i2) {
        return (i * (i2 + (i2 >>> 7))) >>> 8;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @NonNull
    public static MaterialShapeDrawable createWithElevationOverlay(Context context) {
        return createWithElevationOverlay(context, 0.0f);
    }

    @NonNull
    public static MaterialShapeDrawable createWithElevationOverlay(Context context, float f) {
        int color = MaterialColors.getColor(context, R.attr.colorSurface, MaterialShapeDrawable.class.getSimpleName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        materialShapeDrawable.initializeElevationOverlay(context);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf(color));
        materialShapeDrawable.setElevation(f);
        return materialShapeDrawable;
    }

    public MaterialShapeDrawable() {
        this(new ShapeAppearanceModel());
    }

    public MaterialShapeDrawable(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        this(ShapeAppearanceModel.builder(context, attributeSet, i, i2).build());
    }

    @Deprecated
    public MaterialShapeDrawable(@NonNull ShapePathModel shapePathModel) {
        this((ShapeAppearanceModel) shapePathModel);
    }

    public MaterialShapeDrawable(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this(new a(shapeAppearanceModel, null));
    }

    private MaterialShapeDrawable(@NonNull a aVar) {
        this.d = new ShapePath.c[4];
        this.e = new ShapePath.c[4];
        this.f = new BitSet(8);
        this.h = new Matrix();
        this.i = new Path();
        this.j = new Path();
        this.k = new RectF();
        this.l = new RectF();
        this.m = new Region();
        this.n = new Region();
        this.p = new Paint(1);
        this.q = new Paint(1);
        this.r = new ShadowRenderer();
        this.t = Looper.getMainLooper().getThread() == Thread.currentThread() ? ShapeAppearancePathProvider.getInstance() : new ShapeAppearancePathProvider();
        this.w = new RectF();
        this.x = true;
        this.c = aVar;
        this.q.setStyle(Paint.Style.STROKE);
        this.p.setStyle(Paint.Style.FILL);
        b.setColor(-1);
        b.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        g();
        a(getState());
        this.s = new ShapeAppearancePathProvider.PathListener() { // from class: com.google.android.material.shape.MaterialShapeDrawable.1
            @Override // com.google.android.material.shape.ShapeAppearancePathProvider.PathListener
            public void onCornerPathCreated(@NonNull ShapePath shapePath, Matrix matrix, int i) {
                MaterialShapeDrawable.this.f.set(i, shapePath.a());
                MaterialShapeDrawable.this.d[i] = shapePath.a(matrix);
            }

            @Override // com.google.android.material.shape.ShapeAppearancePathProvider.PathListener
            public void onEdgePathCreated(@NonNull ShapePath shapePath, Matrix matrix, int i) {
                MaterialShapeDrawable.this.f.set(i + 4, shapePath.a());
                MaterialShapeDrawable.this.e[i] = shapePath.a(matrix);
            }
        };
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public Drawable.ConstantState getConstantState() {
        return this.c;
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    public Drawable mutate() {
        this.c = new a(this.c);
        return this;
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.c.a = shapeAppearanceModel;
        invalidateSelf();
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.c.a;
    }

    @Deprecated
    public void setShapedViewModel(@NonNull ShapePathModel shapePathModel) {
        setShapeAppearanceModel(shapePathModel);
    }

    @Nullable
    @Deprecated
    public ShapePathModel getShapedViewModel() {
        ShapeAppearanceModel shapeAppearanceModel = getShapeAppearanceModel();
        if (shapeAppearanceModel instanceof ShapePathModel) {
            return (ShapePathModel) shapeAppearanceModel;
        }
        return null;
    }

    public void setFillColor(@Nullable ColorStateList colorStateList) {
        if (this.c.d != colorStateList) {
            this.c.d = colorStateList;
            onStateChange(getState());
        }
    }

    @Nullable
    public ColorStateList getFillColor() {
        return this.c.d;
    }

    public void setStrokeColor(@Nullable ColorStateList colorStateList) {
        if (this.c.e != colorStateList) {
            this.c.e = colorStateList;
            onStateChange(getState());
        }
    }

    @Nullable
    public ColorStateList getStrokeColor() {
        return this.c.e;
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.c.h != mode) {
            this.c.h = mode;
            g();
            b();
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintList(@Nullable ColorStateList colorStateList) {
        this.c.g = colorStateList;
        g();
        b();
    }

    @Nullable
    public ColorStateList getTintList() {
        return this.c.g;
    }

    @Nullable
    public ColorStateList getStrokeTintList() {
        return this.c.f;
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTint(@ColorInt int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    public void setStrokeTint(ColorStateList colorStateList) {
        this.c.f = colorStateList;
        g();
        b();
    }

    public void setStrokeTint(@ColorInt int i) {
        setStrokeTint(ColorStateList.valueOf(i));
    }

    public void setStroke(float f, @ColorInt int i) {
        setStrokeWidth(f);
        setStrokeColor(ColorStateList.valueOf(i));
    }

    public void setStroke(float f, @Nullable ColorStateList colorStateList) {
        setStrokeWidth(f);
        setStrokeColor(colorStateList);
    }

    public float getStrokeWidth() {
        return this.c.l;
    }

    public void setStrokeWidth(float f) {
        this.c.l = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        if (this.c.m != i) {
            this.c.m = i;
            b();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.c.c = colorFilter;
        b();
    }

    @Override // android.graphics.drawable.Drawable
    public Region getTransparentRegion() {
        this.m.set(getBounds());
        a(getBoundsAsRectF(), this.i);
        this.n.setPath(this.i, this.m);
        this.m.op(this.n, Region.Op.DIFFERENCE);
        return this.m;
    }

    @NonNull
    protected RectF getBoundsAsRectF() {
        this.k.set(getBounds());
        return this.k;
    }

    public void setCornerSize(float f) {
        setShapeAppearanceModel(this.c.a.withCornerSize(f));
    }

    public void setCornerSize(@NonNull CornerSize cornerSize) {
        setShapeAppearanceModel(this.c.a.withCornerSize(cornerSize));
    }

    public boolean isPointInTransparentRegion(int i, int i2) {
        return getTransparentRegion().contains(i, i2);
    }

    public int getShadowCompatibilityMode() {
        return this.c.q;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        if (this.c.i == null) {
            return super.getPadding(rect);
        }
        rect.set(this.c.i);
        return true;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if (this.c.i == null) {
            this.c.i = new Rect();
        }
        this.c.i.set(i, i2, i3, i4);
        invalidateSelf();
    }

    public void setShadowCompatibilityMode(int i) {
        if (this.c.q != i) {
            this.c.q = i;
            b();
        }
    }

    @Deprecated
    public boolean isShadowEnabled() {
        return this.c.q == 0 || this.c.q == 2;
    }

    @Deprecated
    public void setShadowEnabled(boolean z) {
        setShadowCompatibilityMode(!z ? 1 : 0);
    }

    public boolean isElevationOverlayEnabled() {
        return this.c.b != null && this.c.b.isThemeElevationOverlayEnabled();
    }

    public boolean isElevationOverlayInitialized() {
        return this.c.b != null;
    }

    public void initializeElevationOverlay(Context context) {
        this.c.b = new ElevationOverlayProvider(context);
        a();
    }

    @ColorInt
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected int compositeElevationOverlayIfNeeded(@ColorInt int i) {
        return this.c.b != null ? this.c.b.compositeOverlayIfNeeded(i, getZ() + getParentAbsoluteElevation()) : i;
    }

    public float getInterpolation() {
        return this.c.k;
    }

    public void setInterpolation(float f) {
        if (this.c.k != f) {
            this.c.k = f;
            this.g = true;
            invalidateSelf();
        }
    }

    public float getParentAbsoluteElevation() {
        return this.c.n;
    }

    public void setParentAbsoluteElevation(float f) {
        if (this.c.n != f) {
            this.c.n = f;
            a();
        }
    }

    public float getElevation() {
        return this.c.o;
    }

    public void setElevation(float f) {
        if (this.c.o != f) {
            this.c.o = f;
            a();
        }
    }

    public float getTranslationZ() {
        return this.c.p;
    }

    public void setTranslationZ(float f) {
        if (this.c.p != f) {
            this.c.p = f;
            a();
        }
    }

    public float getZ() {
        return getElevation() + getTranslationZ();
    }

    public void setZ(float f) {
        setTranslationZ(f - getElevation());
    }

    private void a() {
        float z = getZ();
        this.c.r = (int) Math.ceil(0.75f * z);
        this.c.s = (int) Math.ceil(z * 0.25f);
        g();
        b();
    }

    @Deprecated
    public int getShadowElevation() {
        return (int) getElevation();
    }

    @Deprecated
    public void setShadowElevation(int i) {
        setElevation(i);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getShadowVerticalOffset() {
        return this.c.s;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setShadowBitmapDrawingEnable(boolean z) {
        this.x = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setEdgeIntersectionCheckEnable(boolean z) {
        this.t.a(z);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setShadowVerticalOffset(int i) {
        if (this.c.s != i) {
            this.c.s = i;
            b();
        }
    }

    public int getShadowCompatRotation() {
        return this.c.t;
    }

    public void setShadowCompatRotation(int i) {
        if (this.c.t != i) {
            this.c.t = i;
            b();
        }
    }

    public int getShadowRadius() {
        return this.c.r;
    }

    @Deprecated
    public void setShadowRadius(int i) {
        this.c.r = i;
    }

    public boolean requiresCompatShadow() {
        return Build.VERSION.SDK_INT < 21 || (!isRoundRect() && !this.i.isConvex() && Build.VERSION.SDK_INT < 29);
    }

    public float getScale() {
        return this.c.j;
    }

    public void setScale(float f) {
        if (this.c.j != f) {
            this.c.j = f;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        this.g = true;
        super.invalidateSelf();
    }

    private void b() {
        super.invalidateSelf();
    }

    public void setUseTintColorForShadow(boolean z) {
        if (this.c.u != z) {
            this.c.u = z;
            invalidateSelf();
        }
    }

    public void setShadowColor(int i) {
        this.r.setShadowColor(i);
        this.c.u = false;
        b();
    }

    public Paint.Style getPaintStyle() {
        return this.c.v;
    }

    public void setPaintStyle(Paint.Style style) {
        this.c.v = style;
        b();
    }

    private boolean c() {
        return this.c.q != 1 && this.c.r > 0 && (this.c.q == 2 || requiresCompatShadow());
    }

    private boolean d() {
        return this.c.v == Paint.Style.FILL_AND_STROKE || this.c.v == Paint.Style.FILL;
    }

    private boolean e() {
        return (this.c.v == Paint.Style.FILL_AND_STROKE || this.c.v == Paint.Style.STROKE) && this.q.getStrokeWidth() > 0.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.g = true;
        super.onBoundsChange(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        this.p.setColorFilter(this.u);
        int alpha = this.p.getAlpha();
        this.p.setAlpha(a(alpha, this.c.m));
        this.q.setColorFilter(this.v);
        this.q.setStrokeWidth(this.c.l);
        int alpha2 = this.q.getAlpha();
        this.q.setAlpha(a(alpha2, this.c.m));
        if (this.g) {
            f();
            a(getBoundsAsRectF(), this.i);
            this.g = false;
        }
        a(canvas);
        if (d()) {
            b(canvas);
        }
        if (e()) {
            c(canvas);
        }
        this.p.setAlpha(alpha);
        this.q.setAlpha(alpha2);
    }

    private void a(@NonNull Canvas canvas) {
        if (c()) {
            canvas.save();
            d(canvas);
            if (!this.x) {
                e(canvas);
                canvas.restore();
                return;
            }
            int width = (int) (this.w.width() - getBounds().width());
            int height = (int) (this.w.height() - getBounds().height());
            if (width < 0 || height < 0) {
                throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
            }
            Bitmap createBitmap = Bitmap.createBitmap(((int) this.w.width()) + (this.c.r * 2) + width, ((int) this.w.height()) + (this.c.r * 2) + height, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap);
            float f = (getBounds().left - this.c.r) - width;
            float f2 = (getBounds().top - this.c.r) - height;
            canvas2.translate(-f, -f2);
            e(canvas2);
            canvas.drawBitmap(createBitmap, f, f2, (Paint) null);
            createBitmap.recycle();
            canvas.restore();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void drawShape(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull Path path, @NonNull RectF rectF) {
        a(canvas, paint, path, this.c.a, rectF);
    }

    private void a(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull Path path, @NonNull ShapeAppearanceModel shapeAppearanceModel, @NonNull RectF rectF) {
        if (shapeAppearanceModel.isRoundRect(rectF)) {
            float cornerSize = shapeAppearanceModel.getTopRightCornerSize().getCornerSize(rectF) * this.c.k;
            canvas.drawRoundRect(rectF, cornerSize, cornerSize, paint);
            return;
        }
        canvas.drawPath(path, paint);
    }

    private void b(@NonNull Canvas canvas) {
        a(canvas, this.p, this.i, this.c.a, getBoundsAsRectF());
    }

    private void c(@NonNull Canvas canvas) {
        a(canvas, this.q, this.j, this.o, i());
    }

    private void d(@NonNull Canvas canvas) {
        int shadowOffsetX = getShadowOffsetX();
        int shadowOffsetY = getShadowOffsetY();
        if (Build.VERSION.SDK_INT < 21 && this.x) {
            Rect clipBounds = canvas.getClipBounds();
            clipBounds.inset(-this.c.r, -this.c.r);
            clipBounds.offset(shadowOffsetX, shadowOffsetY);
            canvas.clipRect(clipBounds, Region.Op.REPLACE);
        }
        canvas.translate(shadowOffsetX, shadowOffsetY);
    }

    private void e(@NonNull Canvas canvas) {
        if (this.f.cardinality() > 0) {
            Log.w(a, "Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        if (this.c.s != 0) {
            canvas.drawPath(this.i, this.r.getShadowPaint());
        }
        for (int i = 0; i < 4; i++) {
            this.d[i].a(this.r, this.c.r, canvas);
            this.e[i].a(this.r, this.c.r, canvas);
        }
        if (this.x) {
            int shadowOffsetX = getShadowOffsetX();
            int shadowOffsetY = getShadowOffsetY();
            canvas.translate(-shadowOffsetX, -shadowOffsetY);
            canvas.drawPath(this.i, b);
            canvas.translate(shadowOffsetX, shadowOffsetY);
        }
    }

    public int getShadowOffsetX() {
        return (int) (this.c.s * Math.sin(Math.toRadians(this.c.t)));
    }

    public int getShadowOffsetY() {
        return (int) (this.c.s * Math.cos(Math.toRadians(this.c.t)));
    }

    @Deprecated
    public void getPathForSize(int i, int i2, @NonNull Path path) {
        calculatePathForSize(new RectF(0.0f, 0.0f, i, i2), path);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected final void calculatePathForSize(@NonNull RectF rectF, @NonNull Path path) {
        this.t.calculatePath(this.c.a, this.c.k, rectF, this.s, path);
    }

    private void f() {
        final float f = -h();
        this.o = getShapeAppearanceModel().withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() { // from class: com.google.android.material.shape.MaterialShapeDrawable.2
            @Override // com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize) {
                return cornerSize instanceof RelativeCornerSize ? cornerSize : new AdjustedCornerSize(f, cornerSize);
            }
        });
        this.t.calculatePath(this.o, this.c.k, i(), this.j);
    }

    @Override // android.graphics.drawable.Drawable
    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.c.q != 2) {
            if (isRoundRect()) {
                outline.setRoundRect(getBounds(), getTopLeftCornerResolvedSize() * this.c.k);
                return;
            }
            a(getBoundsAsRectF(), this.i);
            if (this.i.isConvex() || Build.VERSION.SDK_INT >= 29) {
                try {
                    outline.setConvexPath(this.i);
                } catch (IllegalArgumentException unused) {
                }
            }
        }
    }

    private void a(@NonNull RectF rectF, @NonNull Path path) {
        calculatePathForSize(rectF, path);
        if (this.c.j != 1.0f) {
            this.h.reset();
            this.h.setScale(this.c.j, this.c.j, rectF.width() / 2.0f, rectF.height() / 2.0f);
            path.transform(this.h);
        }
        path.computeBounds(this.w, true);
    }

    private boolean g() {
        PorterDuffColorFilter porterDuffColorFilter = this.u;
        PorterDuffColorFilter porterDuffColorFilter2 = this.v;
        this.u = a(this.c.g, this.c.h, this.p, true);
        this.v = a(this.c.f, this.c.h, this.q, false);
        if (this.c.u) {
            this.r.setShadowColor(this.c.g.getColorForState(getState(), 0));
        }
        return !ObjectsCompat.equals(porterDuffColorFilter, this.u) || !ObjectsCompat.equals(porterDuffColorFilter2, this.v);
    }

    @NonNull
    private PorterDuffColorFilter a(@Nullable ColorStateList colorStateList, @Nullable PorterDuff.Mode mode, @NonNull Paint paint, boolean z) {
        if (colorStateList == null || mode == null) {
            return a(paint, z);
        }
        return a(colorStateList, mode, z);
    }

    @Nullable
    private PorterDuffColorFilter a(@NonNull Paint paint, boolean z) {
        int color;
        int compositeElevationOverlayIfNeeded;
        if (!z || (compositeElevationOverlayIfNeeded = compositeElevationOverlayIfNeeded((color = paint.getColor()))) == color) {
            return null;
        }
        return new PorterDuffColorFilter(compositeElevationOverlayIfNeeded, PorterDuff.Mode.SRC_IN);
    }

    @NonNull
    private PorterDuffColorFilter a(@NonNull ColorStateList colorStateList, @NonNull PorterDuff.Mode mode, boolean z) {
        int colorForState = colorStateList.getColorForState(getState(), 0);
        if (z) {
            colorForState = compositeElevationOverlayIfNeeded(colorForState);
        }
        return new PorterDuffColorFilter(colorForState, mode);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return super.isStateful() || (this.c.g != null && this.c.g.isStateful()) || ((this.c.f != null && this.c.f.isStateful()) || ((this.c.e != null && this.c.e.isStateful()) || (this.c.d != null && this.c.d.isStateful())));
    }

    @Override // android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public boolean onStateChange(int[] iArr) {
        boolean z = a(iArr) || g();
        if (z) {
            invalidateSelf();
        }
        return z;
    }

    private boolean a(int[] iArr) {
        boolean z;
        int color;
        int colorForState;
        int color2;
        int colorForState2;
        if (this.c.d == null || color2 == (colorForState2 = this.c.d.getColorForState(iArr, (color2 = this.p.getColor())))) {
            z = false;
        } else {
            this.p.setColor(colorForState2);
            z = true;
        }
        if (this.c.e == null || color == (colorForState = this.c.e.getColorForState(iArr, (color = this.q.getColor())))) {
            return z;
        }
        this.q.setColor(colorForState);
        return true;
    }

    private float h() {
        if (e()) {
            return this.q.getStrokeWidth() / 2.0f;
        }
        return 0.0f;
    }

    @NonNull
    private RectF i() {
        this.l.set(getBoundsAsRectF());
        float h = h();
        this.l.inset(h, h);
        return this.l;
    }

    public float getTopLeftCornerResolvedSize() {
        return this.c.a.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getTopRightCornerResolvedSize() {
        return this.c.a.getTopRightCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getBottomLeftCornerResolvedSize() {
        return this.c.a.getBottomLeftCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getBottomRightCornerResolvedSize() {
        return this.c.a.getBottomRightCornerSize().getCornerSize(getBoundsAsRectF());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRoundRect() {
        return this.c.a.isRoundRect(getBoundsAsRectF());
    }

    /* loaded from: classes2.dex */
    public static final class a extends Drawable.ConstantState {
        @NonNull
        public ShapeAppearanceModel a;
        @Nullable
        public ElevationOverlayProvider b;
        @Nullable
        public ColorFilter c;
        @Nullable
        public ColorStateList d;
        @Nullable
        public ColorStateList e;
        @Nullable
        public ColorStateList f;
        @Nullable
        public ColorStateList g;
        @Nullable
        public PorterDuff.Mode h;
        @Nullable
        public Rect i;
        public float j;
        public float k;
        public float l;
        public int m;
        public float n;
        public float o;
        public float p;
        public int q;
        public int r;
        public int s;
        public int t;
        public boolean u;
        public Paint.Style v;

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        public a(ShapeAppearanceModel shapeAppearanceModel, ElevationOverlayProvider elevationOverlayProvider) {
            this.d = null;
            this.e = null;
            this.f = null;
            this.g = null;
            this.h = PorterDuff.Mode.SRC_IN;
            this.i = null;
            this.j = 1.0f;
            this.k = 1.0f;
            this.m = 255;
            this.n = 0.0f;
            this.o = 0.0f;
            this.p = 0.0f;
            this.q = 0;
            this.r = 0;
            this.s = 0;
            this.t = 0;
            this.u = false;
            this.v = Paint.Style.FILL_AND_STROKE;
            this.a = shapeAppearanceModel;
            this.b = elevationOverlayProvider;
        }

        public a(@NonNull a aVar) {
            this.d = null;
            this.e = null;
            this.f = null;
            this.g = null;
            this.h = PorterDuff.Mode.SRC_IN;
            this.i = null;
            this.j = 1.0f;
            this.k = 1.0f;
            this.m = 255;
            this.n = 0.0f;
            this.o = 0.0f;
            this.p = 0.0f;
            this.q = 0;
            this.r = 0;
            this.s = 0;
            this.t = 0;
            this.u = false;
            this.v = Paint.Style.FILL_AND_STROKE;
            this.a = aVar.a;
            this.b = aVar.b;
            this.l = aVar.l;
            this.c = aVar.c;
            this.d = aVar.d;
            this.e = aVar.e;
            this.h = aVar.h;
            this.g = aVar.g;
            this.m = aVar.m;
            this.j = aVar.j;
            this.s = aVar.s;
            this.q = aVar.q;
            this.u = aVar.u;
            this.k = aVar.k;
            this.n = aVar.n;
            this.o = aVar.o;
            this.p = aVar.p;
            this.r = aVar.r;
            this.t = aVar.t;
            this.f = aVar.f;
            this.v = aVar.v;
            Rect rect = aVar.i;
            if (rect != null) {
                this.i = new Rect(rect);
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this);
            materialShapeDrawable.g = true;
            return materialShapeDrawable;
        }
    }
}
