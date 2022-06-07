package com.google.android.material.chip;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.AnimatorRes;
import androidx.annotation.AttrRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.XmlRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.text.BidiFormatter;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ChipDrawable extends MaterialShapeDrawable implements Drawable.Callback, TintAwareDrawable, TextDrawableHelper.TextDrawableDelegate {
    private static final int[] a = {16842910};
    private static final ShapeDrawable b = new ShapeDrawable(new OvalShape());
    @Nullable
    private MotionSpec A;
    private float B;
    private float C;
    private float D;
    private float E;
    private float F;
    private float G;
    private float H;
    private float I;
    @NonNull
    private final Context J;
    @ColorInt
    private int R;
    @ColorInt
    private int S;
    @ColorInt
    private int T;
    @ColorInt
    private int U;
    @ColorInt
    private int V;
    @ColorInt
    private int W;
    private boolean X;
    @ColorInt
    private int Y;
    @Nullable
    private ColorFilter aa;
    @Nullable
    private PorterDuffColorFilter ab;
    @Nullable
    private ColorStateList ac;
    private int[] ae;
    private boolean af;
    @Nullable
    private ColorStateList ag;
    private TextUtils.TruncateAt ai;
    private boolean aj;
    private int ak;
    private boolean al;
    @Nullable
    private ColorStateList c;
    @Nullable
    private ColorStateList d;
    private float e;
    @Nullable
    private ColorStateList g;
    private float h;
    @Nullable
    private ColorStateList i;
    private boolean k;
    @Nullable
    private Drawable l;
    @Nullable
    private ColorStateList m;
    private float n;
    private boolean o;
    private boolean p;
    @Nullable
    private Drawable q;
    @Nullable
    private Drawable r;
    @Nullable
    private ColorStateList s;
    private float t;
    @Nullable
    private CharSequence u;
    private boolean v;
    private boolean w;
    @Nullable
    private Drawable x;
    @Nullable
    private ColorStateList y;
    @Nullable
    private MotionSpec z;
    private float f = -1.0f;
    private final Paint K = new Paint(1);
    private final Paint.FontMetrics M = new Paint.FontMetrics();
    private final RectF N = new RectF();
    private final PointF O = new PointF();
    private final Path P = new Path();
    private int Z = 255;
    @Nullable
    private PorterDuff.Mode ad = PorterDuff.Mode.SRC_IN;
    @NonNull
    private WeakReference<Delegate> ah = new WeakReference<>(null);
    @NonNull
    private final TextDrawableHelper Q = new TextDrawableHelper(this);
    @Nullable
    private CharSequence j = "";
    @Nullable
    private final Paint L = null;

    /* loaded from: classes2.dex */
    public interface Delegate {
        void onChipDrawableSizeChange();
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @NonNull
    public static ChipDrawable createFromAttributes(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        ChipDrawable chipDrawable = new ChipDrawable(context, attributeSet, i, i2);
        chipDrawable.a(attributeSet, i, i2);
        return chipDrawable;
    }

    @NonNull
    public static ChipDrawable createFromResource(@NonNull Context context, @XmlRes int i) {
        AttributeSet parseDrawableXml = DrawableUtils.parseDrawableXml(context, i, "chip");
        int styleAttribute = parseDrawableXml.getStyleAttribute();
        if (styleAttribute == 0) {
            styleAttribute = R.style.Widget_MaterialComponents_Chip_Entry;
        }
        return createFromAttributes(context, parseDrawableXml, R.attr.chipStandaloneStyle, styleAttribute);
    }

    private ChipDrawable(@NonNull Context context, AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        super(context, attributeSet, i, i2);
        initializeElevationOverlay(context);
        this.J = context;
        this.Q.getTextPaint().density = context.getResources().getDisplayMetrics().density;
        Paint paint = this.L;
        if (paint != null) {
            paint.setStyle(Paint.Style.STROKE);
        }
        setState(a);
        setCloseIconState(a);
        this.aj = true;
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            b.setTint(-1);
        }
    }

    private void a(@Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(this.J, attributeSet, R.styleable.Chip, i, i2, new int[0]);
        this.al = obtainStyledAttributes.hasValue(R.styleable.Chip_shapeAppearance);
        b(MaterialResources.getColorStateList(this.J, obtainStyledAttributes, R.styleable.Chip_chipSurfaceColor));
        setChipBackgroundColor(MaterialResources.getColorStateList(this.J, obtainStyledAttributes, R.styleable.Chip_chipBackgroundColor));
        setChipMinHeight(obtainStyledAttributes.getDimension(R.styleable.Chip_chipMinHeight, 0.0f));
        if (obtainStyledAttributes.hasValue(R.styleable.Chip_chipCornerRadius)) {
            setChipCornerRadius(obtainStyledAttributes.getDimension(R.styleable.Chip_chipCornerRadius, 0.0f));
        }
        setChipStrokeColor(MaterialResources.getColorStateList(this.J, obtainStyledAttributes, R.styleable.Chip_chipStrokeColor));
        setChipStrokeWidth(obtainStyledAttributes.getDimension(R.styleable.Chip_chipStrokeWidth, 0.0f));
        setRippleColor(MaterialResources.getColorStateList(this.J, obtainStyledAttributes, R.styleable.Chip_rippleColor));
        setText(obtainStyledAttributes.getText(R.styleable.Chip_android_text));
        TextAppearance textAppearance = MaterialResources.getTextAppearance(this.J, obtainStyledAttributes, R.styleable.Chip_android_textAppearance);
        textAppearance.textSize = obtainStyledAttributes.getDimension(R.styleable.Chip_android_textSize, textAppearance.textSize);
        setTextAppearance(textAppearance);
        switch (obtainStyledAttributes.getInt(R.styleable.Chip_android_ellipsize, 0)) {
            case 1:
                setEllipsize(TextUtils.TruncateAt.START);
                break;
            case 2:
                setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            case 3:
                setEllipsize(TextUtils.TruncateAt.END);
                break;
        }
        setChipIconVisible(obtainStyledAttributes.getBoolean(R.styleable.Chip_chipIconVisible, false));
        if (!(attributeSet == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconEnabled") == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconVisible") != null)) {
            setChipIconVisible(obtainStyledAttributes.getBoolean(R.styleable.Chip_chipIconEnabled, false));
        }
        setChipIcon(MaterialResources.getDrawable(this.J, obtainStyledAttributes, R.styleable.Chip_chipIcon));
        if (obtainStyledAttributes.hasValue(R.styleable.Chip_chipIconTint)) {
            setChipIconTint(MaterialResources.getColorStateList(this.J, obtainStyledAttributes, R.styleable.Chip_chipIconTint));
        }
        setChipIconSize(obtainStyledAttributes.getDimension(R.styleable.Chip_chipIconSize, -1.0f));
        setCloseIconVisible(obtainStyledAttributes.getBoolean(R.styleable.Chip_closeIconVisible, false));
        if (!(attributeSet == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconEnabled") == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconVisible") != null)) {
            setCloseIconVisible(obtainStyledAttributes.getBoolean(R.styleable.Chip_closeIconEnabled, false));
        }
        setCloseIcon(MaterialResources.getDrawable(this.J, obtainStyledAttributes, R.styleable.Chip_closeIcon));
        setCloseIconTint(MaterialResources.getColorStateList(this.J, obtainStyledAttributes, R.styleable.Chip_closeIconTint));
        setCloseIconSize(obtainStyledAttributes.getDimension(R.styleable.Chip_closeIconSize, 0.0f));
        setCheckable(obtainStyledAttributes.getBoolean(R.styleable.Chip_android_checkable, false));
        setCheckedIconVisible(obtainStyledAttributes.getBoolean(R.styleable.Chip_checkedIconVisible, false));
        if (!(attributeSet == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconEnabled") == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconVisible") != null)) {
            setCheckedIconVisible(obtainStyledAttributes.getBoolean(R.styleable.Chip_checkedIconEnabled, false));
        }
        setCheckedIcon(MaterialResources.getDrawable(this.J, obtainStyledAttributes, R.styleable.Chip_checkedIcon));
        if (obtainStyledAttributes.hasValue(R.styleable.Chip_checkedIconTint)) {
            setCheckedIconTint(MaterialResources.getColorStateList(this.J, obtainStyledAttributes, R.styleable.Chip_checkedIconTint));
        }
        setShowMotionSpec(MotionSpec.createFromAttribute(this.J, obtainStyledAttributes, R.styleable.Chip_showMotionSpec));
        setHideMotionSpec(MotionSpec.createFromAttribute(this.J, obtainStyledAttributes, R.styleable.Chip_hideMotionSpec));
        setChipStartPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_chipStartPadding, 0.0f));
        setIconStartPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_iconStartPadding, 0.0f));
        setIconEndPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_iconEndPadding, 0.0f));
        setTextStartPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_textStartPadding, 0.0f));
        setTextEndPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_textEndPadding, 0.0f));
        setCloseIconStartPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_closeIconStartPadding, 0.0f));
        setCloseIconEndPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_closeIconEndPadding, 0.0f));
        setChipEndPadding(obtainStyledAttributes.getDimension(R.styleable.Chip_chipEndPadding, 0.0f));
        setMaxWidth(obtainStyledAttributes.getDimensionPixelSize(R.styleable.Chip_android_maxWidth, Integer.MAX_VALUE));
        obtainStyledAttributes.recycle();
    }

    public void setUseCompatRipple(boolean z) {
        if (this.af != z) {
            this.af = z;
            l();
            onStateChange(getState());
        }
    }

    public boolean getUseCompatRipple() {
        return this.af;
    }

    public void setDelegate(@Nullable Delegate delegate) {
        this.ah = new WeakReference<>(delegate);
    }

    protected void onSizeChange() {
        Delegate delegate = this.ah.get();
        if (delegate != null) {
            delegate.onChipDrawableSizeChange();
        }
    }

    public void getChipTouchBounds(@NonNull RectF rectF) {
        d(getBounds(), rectF);
    }

    public void getCloseIconTouchBounds(@NonNull RectF rectF) {
        e(getBounds(), rectF);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return Math.min(Math.round(this.B + a() + this.E + this.Q.getTextWidth(getText().toString()) + this.F + b() + this.I), this.ak);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) this.e;
    }

    private boolean d() {
        return this.k && this.l != null;
    }

    private boolean e() {
        return this.w && this.x != null && this.X;
    }

    private boolean f() {
        return this.p && this.q != null;
    }

    private boolean g() {
        return this.w && this.x != null && this.v;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float a() {
        if (d() || e()) {
            return this.C + h() + this.D;
        }
        return 0.0f;
    }

    private float h() {
        Drawable drawable = this.X ? this.x : this.l;
        if (this.n > 0.0f || drawable == null) {
            return this.n;
        }
        return drawable.getIntrinsicWidth();
    }

    private float i() {
        Drawable drawable = this.X ? this.x : this.l;
        if (this.n > 0.0f || drawable == null) {
            return this.n;
        }
        float ceil = (float) Math.ceil(ViewUtils.dpToPx(this.J, 24));
        return ((float) drawable.getIntrinsicHeight()) <= ceil ? drawable.getIntrinsicHeight() : ceil;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float b() {
        if (f()) {
            return this.G + this.t + this.H;
        }
        return 0.0f;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (!bounds.isEmpty() && getAlpha() != 0) {
            int i = 0;
            if (this.Z < 255) {
                i = CanvasCompat.saveLayerAlpha(canvas, bounds.left, bounds.top, bounds.right, bounds.bottom, this.Z);
            }
            a(canvas, bounds);
            b(canvas, bounds);
            if (this.al) {
                super.draw(canvas);
            }
            c(canvas, bounds);
            d(canvas, bounds);
            e(canvas, bounds);
            f(canvas, bounds);
            if (this.aj) {
                g(canvas, bounds);
            }
            h(canvas, bounds);
            i(canvas, bounds);
            if (this.Z < 255) {
                canvas.restoreToCount(i);
            }
        }
    }

    private void a(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (!this.al) {
            this.K.setColor(this.R);
            this.K.setStyle(Paint.Style.FILL);
            this.N.set(rect);
            canvas.drawRoundRect(this.N, getChipCornerRadius(), getChipCornerRadius(), this.K);
        }
    }

    private void b(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (!this.al) {
            this.K.setColor(this.S);
            this.K.setStyle(Paint.Style.FILL);
            this.K.setColorFilter(k());
            this.N.set(rect);
            canvas.drawRoundRect(this.N, getChipCornerRadius(), getChipCornerRadius(), this.K);
        }
    }

    private void c(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (this.h > 0.0f && !this.al) {
            this.K.setColor(this.U);
            this.K.setStyle(Paint.Style.STROKE);
            if (!this.al) {
                this.K.setColorFilter(k());
            }
            this.N.set(rect.left + (this.h / 2.0f), rect.top + (this.h / 2.0f), rect.right - (this.h / 2.0f), rect.bottom - (this.h / 2.0f));
            float f = this.f - (this.h / 2.0f);
            canvas.drawRoundRect(this.N, f, f, this.K);
        }
    }

    private void d(@NonNull Canvas canvas, @NonNull Rect rect) {
        this.K.setColor(this.V);
        this.K.setStyle(Paint.Style.FILL);
        this.N.set(rect);
        if (!this.al) {
            canvas.drawRoundRect(this.N, getChipCornerRadius(), getChipCornerRadius(), this.K);
            return;
        }
        calculatePathForSize(new RectF(rect), this.P);
        super.drawShape(canvas, this.K, this.P, getBoundsAsRectF());
    }

    private void e(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (d()) {
            a(rect, this.N);
            float f = this.N.left;
            float f2 = this.N.top;
            canvas.translate(f, f2);
            this.l.setBounds(0, 0, (int) this.N.width(), (int) this.N.height());
            this.l.draw(canvas);
            canvas.translate(-f, -f2);
        }
    }

    private void f(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (e()) {
            a(rect, this.N);
            float f = this.N.left;
            float f2 = this.N.top;
            canvas.translate(f, f2);
            this.x.setBounds(0, 0, (int) this.N.width(), (int) this.N.height());
            this.x.draw(canvas);
            canvas.translate(-f, -f2);
        }
    }

    private void g(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (this.j != null) {
            Paint.Align a2 = a(rect, this.O);
            b(rect, this.N);
            if (this.Q.getTextAppearance() != null) {
                this.Q.getTextPaint().drawableState = getState();
                this.Q.updateTextPaintDrawState(this.J);
            }
            this.Q.getTextPaint().setTextAlign(a2);
            int i = 0;
            boolean z = Math.round(this.Q.getTextWidth(getText().toString())) > Math.round(this.N.width());
            if (z) {
                i = canvas.save();
                canvas.clipRect(this.N);
            }
            CharSequence charSequence = this.j;
            CharSequence ellipsize = (!z || this.ai == null) ? charSequence : TextUtils.ellipsize(charSequence, this.Q.getTextPaint(), this.N.width(), this.ai);
            canvas.drawText(ellipsize, 0, ellipsize.length(), this.O.x, this.O.y, this.Q.getTextPaint());
            if (z) {
                canvas.restoreToCount(i);
            }
        }
    }

    private void h(@NonNull Canvas canvas, @NonNull Rect rect) {
        if (f()) {
            c(rect, this.N);
            float f = this.N.left;
            float f2 = this.N.top;
            canvas.translate(f, f2);
            this.q.setBounds(0, 0, (int) this.N.width(), (int) this.N.height());
            if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
                this.r.setBounds(this.q.getBounds());
                this.r.jumpToCurrentState();
                this.r.draw(canvas);
            } else {
                this.q.draw(canvas);
            }
            canvas.translate(-f, -f2);
        }
    }

    private void i(@NonNull Canvas canvas, @NonNull Rect rect) {
        Paint paint = this.L;
        if (paint != null) {
            paint.setColor(ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, 127));
            canvas.drawRect(rect, this.L);
            if (d() || e()) {
                a(rect, this.N);
                canvas.drawRect(this.N, this.L);
            }
            if (this.j != null) {
                canvas.drawLine(rect.left, rect.exactCenterY(), rect.right, rect.exactCenterY(), this.L);
            }
            if (f()) {
                c(rect, this.N);
                canvas.drawRect(this.N, this.L);
            }
            this.L.setColor(ColorUtils.setAlphaComponent(-65536, 127));
            d(rect, this.N);
            canvas.drawRect(this.N, this.L);
            this.L.setColor(ColorUtils.setAlphaComponent(-16711936, 127));
            e(rect, this.N);
            canvas.drawRect(this.N, this.L);
        }
    }

    private void a(@NonNull Rect rect, @NonNull RectF rectF) {
        rectF.setEmpty();
        if (d() || e()) {
            float f = this.B + this.C;
            float h = h();
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                rectF.left = rect.left + f;
                rectF.right = rectF.left + h;
            } else {
                rectF.right = rect.right - f;
                rectF.left = rectF.right - h;
            }
            float i = i();
            rectF.top = rect.exactCenterY() - (i / 2.0f);
            rectF.bottom = rectF.top + i;
        }
    }

    @NonNull
    Paint.Align a(@NonNull Rect rect, @NonNull PointF pointF) {
        pointF.set(0.0f, 0.0f);
        Paint.Align align = Paint.Align.LEFT;
        if (this.j != null) {
            float a2 = this.B + a() + this.E;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                pointF.x = rect.left + a2;
                align = Paint.Align.LEFT;
            } else {
                pointF.x = rect.right - a2;
                align = Paint.Align.RIGHT;
            }
            pointF.y = rect.centerY() - j();
        }
        return align;
    }

    private float j() {
        this.Q.getTextPaint().getFontMetrics(this.M);
        return (this.M.descent + this.M.ascent) / 2.0f;
    }

    private void b(@NonNull Rect rect, @NonNull RectF rectF) {
        rectF.setEmpty();
        if (this.j != null) {
            float a2 = this.B + a() + this.E;
            float b2 = this.I + b() + this.F;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                rectF.left = rect.left + a2;
                rectF.right = rect.right - b2;
            } else {
                rectF.left = rect.left + b2;
                rectF.right = rect.right - a2;
            }
            rectF.top = rect.top;
            rectF.bottom = rect.bottom;
        }
    }

    private void c(@NonNull Rect rect, @NonNull RectF rectF) {
        rectF.setEmpty();
        if (f()) {
            float f = this.I + this.H;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                rectF.right = rect.right - f;
                rectF.left = rectF.right - this.t;
            } else {
                rectF.left = rect.left + f;
                rectF.right = rectF.left + this.t;
            }
            rectF.top = rect.exactCenterY() - (this.t / 2.0f);
            rectF.bottom = rectF.top + this.t;
        }
    }

    private void d(@NonNull Rect rect, @NonNull RectF rectF) {
        rectF.set(rect);
        if (f()) {
            float f = this.I + this.H + this.t + this.G + this.F;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                rectF.right = rect.right - f;
            } else {
                rectF.left = rect.left + f;
            }
        }
    }

    private void e(@NonNull Rect rect, @NonNull RectF rectF) {
        rectF.setEmpty();
        if (f()) {
            float f = this.I + this.H + this.t + this.G + this.F;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                rectF.right = rect.right;
                rectF.left = rectF.right - f;
            } else {
                rectF.left = rect.left;
                rectF.right = rect.left + f;
            }
            rectF.top = rect.top;
            rectF.bottom = rect.bottom;
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return a(this.c) || a(this.d) || a(this.g) || (this.af && a(this.ag)) || a(this.Q.getTextAppearance()) || g() || a(this.l) || a(this.x) || a(this.ac);
    }

    public boolean isCloseIconStateful() {
        return a(this.q);
    }

    public boolean setCloseIconState(@NonNull int[] iArr) {
        if (Arrays.equals(this.ae, iArr)) {
            return false;
        }
        this.ae = iArr;
        if (f()) {
            return a(getState(), iArr);
        }
        return false;
    }

    @NonNull
    public int[] getCloseIconState() {
        return this.ae;
    }

    @Override // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public void onTextSizeChange() {
        onSizeChange();
        invalidateSelf();
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public boolean onStateChange(@NonNull int[] iArr) {
        if (this.al) {
            super.onStateChange(iArr);
        }
        return a(iArr, getCloseIconState());
    }

    private boolean a(@NonNull int[] iArr, @NonNull int[] iArr2) {
        boolean z;
        boolean onStateChange = super.onStateChange(iArr);
        ColorStateList colorStateList = this.c;
        int compositeElevationOverlayIfNeeded = compositeElevationOverlayIfNeeded(colorStateList != null ? colorStateList.getColorForState(iArr, this.R) : 0);
        if (this.R != compositeElevationOverlayIfNeeded) {
            this.R = compositeElevationOverlayIfNeeded;
            onStateChange = true;
        }
        ColorStateList colorStateList2 = this.d;
        int compositeElevationOverlayIfNeeded2 = compositeElevationOverlayIfNeeded(colorStateList2 != null ? colorStateList2.getColorForState(iArr, this.S) : 0);
        if (this.S != compositeElevationOverlayIfNeeded2) {
            this.S = compositeElevationOverlayIfNeeded2;
            onStateChange = true;
        }
        int layer = MaterialColors.layer(compositeElevationOverlayIfNeeded, compositeElevationOverlayIfNeeded2);
        if ((this.T != layer) || (getFillColor() == null)) {
            this.T = layer;
            setFillColor(ColorStateList.valueOf(this.T));
            onStateChange = true;
        }
        ColorStateList colorStateList3 = this.g;
        int colorForState = colorStateList3 != null ? colorStateList3.getColorForState(iArr, this.U) : 0;
        if (this.U != colorForState) {
            this.U = colorForState;
            onStateChange = true;
        }
        int colorForState2 = (this.ag == null || !RippleUtils.shouldDrawRippleCompat(iArr)) ? 0 : this.ag.getColorForState(iArr, this.V);
        if (this.V != colorForState2) {
            this.V = colorForState2;
            if (this.af) {
                onStateChange = true;
            }
        }
        int colorForState3 = (this.Q.getTextAppearance() == null || this.Q.getTextAppearance().textColor == null) ? 0 : this.Q.getTextAppearance().textColor.getColorForState(iArr, this.W);
        if (this.W != colorForState3) {
            this.W = colorForState3;
            onStateChange = true;
        }
        boolean z2 = a(getState(), 16842912) && this.v;
        if (this.X == z2 || this.x == null) {
            z = false;
        } else {
            float a2 = a();
            this.X = z2;
            if (a2 != a()) {
                onStateChange = true;
                z = true;
            } else {
                z = false;
                onStateChange = true;
            }
        }
        ColorStateList colorStateList4 = this.ac;
        int colorForState4 = colorStateList4 != null ? colorStateList4.getColorForState(iArr, this.Y) : 0;
        if (this.Y != colorForState4) {
            this.Y = colorForState4;
            this.ab = DrawableUtils.updateTintFilter(this, this.ac, this.ad);
            onStateChange = true;
        }
        if (a(this.l)) {
            onStateChange |= this.l.setState(iArr);
        }
        if (a(this.x)) {
            onStateChange |= this.x.setState(iArr);
        }
        if (a(this.q)) {
            int[] iArr3 = new int[iArr.length + iArr2.length];
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
            onStateChange |= this.q.setState(iArr3);
        }
        if (RippleUtils.USE_FRAMEWORK_RIPPLE && a(this.r)) {
            onStateChange |= this.r.setState(iArr2);
        }
        if (onStateChange) {
            invalidateSelf();
        }
        if (z) {
            onSizeChange();
        }
        return onStateChange;
    }

    private static boolean a(@Nullable ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    private static boolean a(@Nullable Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    private static boolean a(@Nullable TextAppearance textAppearance) {
        return (textAppearance == null || textAppearance.textColor == null || !textAppearance.textColor.isStateful()) ? false : true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        boolean onLayoutDirectionChanged = super.onLayoutDirectionChanged(i);
        if (d()) {
            onLayoutDirectionChanged |= DrawableCompat.setLayoutDirection(this.l, i);
        }
        if (e()) {
            onLayoutDirectionChanged |= DrawableCompat.setLayoutDirection(this.x, i);
        }
        if (f()) {
            onLayoutDirectionChanged |= DrawableCompat.setLayoutDirection(this.q, i);
        }
        if (!onLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        boolean onLevelChange = super.onLevelChange(i);
        if (d()) {
            onLevelChange |= this.l.setLevel(i);
        }
        if (e()) {
            onLevelChange |= this.x.setLevel(i);
        }
        if (f()) {
            onLevelChange |= this.q.setLevel(i);
        }
        if (onLevelChange) {
            invalidateSelf();
        }
        return onLevelChange;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (d()) {
            visible |= this.l.setVisible(z, z2);
        }
        if (e()) {
            visible |= this.x.setVisible(z, z2);
        }
        if (f()) {
            visible |= this.q.setVisible(z, z2);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.Z != i) {
            this.Z = i;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.Z;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (this.aa != colorFilter) {
            this.aa = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public ColorFilter getColorFilter() {
        return this.aa;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintList(@Nullable ColorStateList colorStateList) {
        if (this.ac != colorStateList) {
            this.ac = colorStateList;
            onStateChange(getState());
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        if (this.ad != mode) {
            this.ad = mode;
            this.ab = DrawableUtils.updateTintFilter(this, this.ac, mode);
            invalidateSelf();
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.al) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (!bounds.isEmpty()) {
            outline.setRoundRect(bounds, this.f);
        } else {
            outline.setRoundRect(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), this.f);
        }
        outline.setAlpha(getAlpha() / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NonNull Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    private void b(@Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    private void c(@Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(this);
            DrawableCompat.setLayoutDirection(drawable, DrawableCompat.getLayoutDirection(this));
            drawable.setLevel(getLevel());
            drawable.setVisible(isVisible(), false);
            if (drawable == this.q) {
                if (drawable.isStateful()) {
                    drawable.setState(getCloseIconState());
                }
                DrawableCompat.setTintList(drawable, this.s);
                return;
            }
            if (drawable.isStateful()) {
                drawable.setState(getState());
            }
            Drawable drawable2 = this.l;
            if (drawable == drawable2 && this.o) {
                DrawableCompat.setTintList(drawable2, this.m);
            }
        }
    }

    @Nullable
    private ColorFilter k() {
        ColorFilter colorFilter = this.aa;
        return colorFilter != null ? colorFilter : this.ab;
    }

    private void l() {
        this.ag = this.af ? RippleUtils.sanitizeRippleDrawableColor(this.i) : null;
    }

    private void b(@Nullable ColorStateList colorStateList) {
        if (this.c != colorStateList) {
            this.c = colorStateList;
            onStateChange(getState());
        }
    }

    private static boolean a(@Nullable int[] iArr, @AttrRes int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public void setTextSize(@Dimension float f) {
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.textSize = f;
            this.Q.getTextPaint().setTextSize(f);
            onTextSizeChange();
        }
    }

    @Nullable
    public ColorStateList getChipBackgroundColor() {
        return this.d;
    }

    public void setChipBackgroundColorResource(@ColorRes int i) {
        setChipBackgroundColor(AppCompatResources.getColorStateList(this.J, i));
    }

    public void setChipBackgroundColor(@Nullable ColorStateList colorStateList) {
        if (this.d != colorStateList) {
            this.d = colorStateList;
            onStateChange(getState());
        }
    }

    public float getChipMinHeight() {
        return this.e;
    }

    public void setChipMinHeightResource(@DimenRes int i) {
        setChipMinHeight(this.J.getResources().getDimension(i));
    }

    public void setChipMinHeight(float f) {
        if (this.e != f) {
            this.e = f;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getChipCornerRadius() {
        return this.al ? getTopLeftCornerResolvedSize() : this.f;
    }

    @Deprecated
    public void setChipCornerRadiusResource(@DimenRes int i) {
        setChipCornerRadius(this.J.getResources().getDimension(i));
    }

    @Deprecated
    public void setChipCornerRadius(float f) {
        if (this.f != f) {
            this.f = f;
            setShapeAppearanceModel(getShapeAppearanceModel().withCornerSize(f));
        }
    }

    @Nullable
    public ColorStateList getChipStrokeColor() {
        return this.g;
    }

    public void setChipStrokeColorResource(@ColorRes int i) {
        setChipStrokeColor(AppCompatResources.getColorStateList(this.J, i));
    }

    public void setChipStrokeColor(@Nullable ColorStateList colorStateList) {
        if (this.g != colorStateList) {
            this.g = colorStateList;
            if (this.al) {
                setStrokeColor(colorStateList);
            }
            onStateChange(getState());
        }
    }

    public float getChipStrokeWidth() {
        return this.h;
    }

    public void setChipStrokeWidthResource(@DimenRes int i) {
        setChipStrokeWidth(this.J.getResources().getDimension(i));
    }

    public void setChipStrokeWidth(float f) {
        if (this.h != f) {
            this.h = f;
            this.K.setStrokeWidth(f);
            if (this.al) {
                super.setStrokeWidth(f);
            }
            invalidateSelf();
        }
    }

    @Nullable
    public ColorStateList getRippleColor() {
        return this.i;
    }

    public void setRippleColorResource(@ColorRes int i) {
        setRippleColor(AppCompatResources.getColorStateList(this.J, i));
    }

    public void setRippleColor(@Nullable ColorStateList colorStateList) {
        if (this.i != colorStateList) {
            this.i = colorStateList;
            l();
            onStateChange(getState());
        }
    }

    @Nullable
    public CharSequence getText() {
        return this.j;
    }

    public void setTextResource(@StringRes int i) {
        setText(this.J.getResources().getString(i));
    }

    public void setText(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "";
        }
        if (!TextUtils.equals(this.j, charSequence)) {
            this.j = charSequence;
            this.Q.setTextWidthDirty(true);
            invalidateSelf();
            onSizeChange();
        }
    }

    @Nullable
    public TextAppearance getTextAppearance() {
        return this.Q.getTextAppearance();
    }

    public void setTextAppearanceResource(@StyleRes int i) {
        setTextAppearance(new TextAppearance(this.J, i));
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance) {
        this.Q.setTextAppearance(textAppearance, this.J);
    }

    public TextUtils.TruncateAt getEllipsize() {
        return this.ai;
    }

    public void setEllipsize(@Nullable TextUtils.TruncateAt truncateAt) {
        this.ai = truncateAt;
    }

    public boolean isChipIconVisible() {
        return this.k;
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public void setChipIconVisible(@BoolRes int i) {
        setChipIconVisible(this.J.getResources().getBoolean(i));
    }

    public void setChipIconVisible(boolean z) {
        if (this.k != z) {
            boolean d = d();
            this.k = z;
            boolean d2 = d();
            if (d != d2) {
                if (d2) {
                    c(this.l);
                } else {
                    b(this.l);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setChipIconEnabledResource(@BoolRes int i) {
        setChipIconVisible(i);
    }

    @Deprecated
    public void setChipIconEnabled(boolean z) {
        setChipIconVisible(z);
    }

    @Nullable
    public Drawable getChipIcon() {
        Drawable drawable = this.l;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    public void setChipIconResource(@DrawableRes int i) {
        setChipIcon(AppCompatResources.getDrawable(this.J, i));
    }

    public void setChipIcon(@Nullable Drawable drawable) {
        Drawable chipIcon = getChipIcon();
        if (chipIcon != drawable) {
            float a2 = a();
            this.l = drawable != null ? DrawableCompat.wrap(drawable).mutate() : null;
            float a3 = a();
            b(chipIcon);
            if (d()) {
                c(this.l);
            }
            invalidateSelf();
            if (a2 != a3) {
                onSizeChange();
            }
        }
    }

    @Nullable
    public ColorStateList getChipIconTint() {
        return this.m;
    }

    public void setChipIconTintResource(@ColorRes int i) {
        setChipIconTint(AppCompatResources.getColorStateList(this.J, i));
    }

    public void setChipIconTint(@Nullable ColorStateList colorStateList) {
        this.o = true;
        if (this.m != colorStateList) {
            this.m = colorStateList;
            if (d()) {
                DrawableCompat.setTintList(this.l, colorStateList);
            }
            onStateChange(getState());
        }
    }

    public float getChipIconSize() {
        return this.n;
    }

    public void setChipIconSizeResource(@DimenRes int i) {
        setChipIconSize(this.J.getResources().getDimension(i));
    }

    public void setChipIconSize(float f) {
        if (this.n != f) {
            float a2 = a();
            this.n = f;
            float a3 = a();
            invalidateSelf();
            if (a2 != a3) {
                onSizeChange();
            }
        }
    }

    public boolean isCloseIconVisible() {
        return this.p;
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public void setCloseIconVisible(@BoolRes int i) {
        setCloseIconVisible(this.J.getResources().getBoolean(i));
    }

    public void setCloseIconVisible(boolean z) {
        if (this.p != z) {
            boolean f = f();
            this.p = z;
            boolean f2 = f();
            if (f != f2) {
                if (f2) {
                    c(this.q);
                } else {
                    b(this.q);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setCloseIconEnabledResource(@BoolRes int i) {
        setCloseIconVisible(i);
    }

    @Deprecated
    public void setCloseIconEnabled(boolean z) {
        setCloseIconVisible(z);
    }

    @Nullable
    public Drawable getCloseIcon() {
        Drawable drawable = this.q;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    public void setCloseIconResource(@DrawableRes int i) {
        setCloseIcon(AppCompatResources.getDrawable(this.J, i));
    }

    public void setCloseIcon(@Nullable Drawable drawable) {
        Drawable closeIcon = getCloseIcon();
        if (closeIcon != drawable) {
            float b2 = b();
            this.q = drawable != null ? DrawableCompat.wrap(drawable).mutate() : null;
            if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
                m();
            }
            float b3 = b();
            b(closeIcon);
            if (f()) {
                c(this.q);
            }
            invalidateSelf();
            if (b2 != b3) {
                onSizeChange();
            }
        }
    }

    @TargetApi(21)
    private void m() {
        this.r = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(getRippleColor()), this.q, b);
    }

    @Nullable
    public ColorStateList getCloseIconTint() {
        return this.s;
    }

    public void setCloseIconTintResource(@ColorRes int i) {
        setCloseIconTint(AppCompatResources.getColorStateList(this.J, i));
    }

    public void setCloseIconTint(@Nullable ColorStateList colorStateList) {
        if (this.s != colorStateList) {
            this.s = colorStateList;
            if (f()) {
                DrawableCompat.setTintList(this.q, colorStateList);
            }
            onStateChange(getState());
        }
    }

    public float getCloseIconSize() {
        return this.t;
    }

    public void setCloseIconSizeResource(@DimenRes int i) {
        setCloseIconSize(this.J.getResources().getDimension(i));
    }

    public void setCloseIconSize(float f) {
        if (this.t != f) {
            this.t = f;
            invalidateSelf();
            if (f()) {
                onSizeChange();
            }
        }
    }

    public void setCloseIconContentDescription(@Nullable CharSequence charSequence) {
        if (this.u != charSequence) {
            this.u = BidiFormatter.getInstance().unicodeWrap(charSequence);
            invalidateSelf();
        }
    }

    @Nullable
    public CharSequence getCloseIconContentDescription() {
        return this.u;
    }

    public boolean isCheckable() {
        return this.v;
    }

    public void setCheckableResource(@BoolRes int i) {
        setCheckable(this.J.getResources().getBoolean(i));
    }

    public void setCheckable(boolean z) {
        if (this.v != z) {
            this.v = z;
            float a2 = a();
            if (!z && this.X) {
                this.X = false;
            }
            float a3 = a();
            invalidateSelf();
            if (a2 != a3) {
                onSizeChange();
            }
        }
    }

    public boolean isCheckedIconVisible() {
        return this.w;
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public void setCheckedIconVisible(@BoolRes int i) {
        setCheckedIconVisible(this.J.getResources().getBoolean(i));
    }

    public void setCheckedIconVisible(boolean z) {
        if (this.w != z) {
            boolean e = e();
            this.w = z;
            boolean e2 = e();
            if (e != e2) {
                if (e2) {
                    c(this.x);
                } else {
                    b(this.x);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setCheckedIconEnabledResource(@BoolRes int i) {
        setCheckedIconVisible(this.J.getResources().getBoolean(i));
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean z) {
        setCheckedIconVisible(z);
    }

    @Nullable
    public Drawable getCheckedIcon() {
        return this.x;
    }

    public void setCheckedIconResource(@DrawableRes int i) {
        setCheckedIcon(AppCompatResources.getDrawable(this.J, i));
    }

    public void setCheckedIcon(@Nullable Drawable drawable) {
        if (this.x != drawable) {
            float a2 = a();
            this.x = drawable;
            float a3 = a();
            b(this.x);
            c(this.x);
            invalidateSelf();
            if (a2 != a3) {
                onSizeChange();
            }
        }
    }

    @Nullable
    public ColorStateList getCheckedIconTint() {
        return this.y;
    }

    public void setCheckedIconTintResource(@ColorRes int i) {
        setCheckedIconTint(AppCompatResources.getColorStateList(this.J, i));
    }

    public void setCheckedIconTint(@Nullable ColorStateList colorStateList) {
        if (this.y != colorStateList) {
            this.y = colorStateList;
            if (g()) {
                DrawableCompat.setTintList(this.x, colorStateList);
            }
            onStateChange(getState());
        }
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        return this.z;
    }

    public void setShowMotionSpecResource(@AnimatorRes int i) {
        setShowMotionSpec(MotionSpec.createFromResource(this.J, i));
    }

    public void setShowMotionSpec(@Nullable MotionSpec motionSpec) {
        this.z = motionSpec;
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        return this.A;
    }

    public void setHideMotionSpecResource(@AnimatorRes int i) {
        setHideMotionSpec(MotionSpec.createFromResource(this.J, i));
    }

    public void setHideMotionSpec(@Nullable MotionSpec motionSpec) {
        this.A = motionSpec;
    }

    public float getChipStartPadding() {
        return this.B;
    }

    public void setChipStartPaddingResource(@DimenRes int i) {
        setChipStartPadding(this.J.getResources().getDimension(i));
    }

    public void setChipStartPadding(float f) {
        if (this.B != f) {
            this.B = f;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getIconStartPadding() {
        return this.C;
    }

    public void setIconStartPaddingResource(@DimenRes int i) {
        setIconStartPadding(this.J.getResources().getDimension(i));
    }

    public void setIconStartPadding(float f) {
        if (this.C != f) {
            float a2 = a();
            this.C = f;
            float a3 = a();
            invalidateSelf();
            if (a2 != a3) {
                onSizeChange();
            }
        }
    }

    public float getIconEndPadding() {
        return this.D;
    }

    public void setIconEndPaddingResource(@DimenRes int i) {
        setIconEndPadding(this.J.getResources().getDimension(i));
    }

    public void setIconEndPadding(float f) {
        if (this.D != f) {
            float a2 = a();
            this.D = f;
            float a3 = a();
            invalidateSelf();
            if (a2 != a3) {
                onSizeChange();
            }
        }
    }

    public float getTextStartPadding() {
        return this.E;
    }

    public void setTextStartPaddingResource(@DimenRes int i) {
        setTextStartPadding(this.J.getResources().getDimension(i));
    }

    public void setTextStartPadding(float f) {
        if (this.E != f) {
            this.E = f;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getTextEndPadding() {
        return this.F;
    }

    public void setTextEndPaddingResource(@DimenRes int i) {
        setTextEndPadding(this.J.getResources().getDimension(i));
    }

    public void setTextEndPadding(float f) {
        if (this.F != f) {
            this.F = f;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getCloseIconStartPadding() {
        return this.G;
    }

    public void setCloseIconStartPaddingResource(@DimenRes int i) {
        setCloseIconStartPadding(this.J.getResources().getDimension(i));
    }

    public void setCloseIconStartPadding(float f) {
        if (this.G != f) {
            this.G = f;
            invalidateSelf();
            if (f()) {
                onSizeChange();
            }
        }
    }

    public float getCloseIconEndPadding() {
        return this.H;
    }

    public void setCloseIconEndPaddingResource(@DimenRes int i) {
        setCloseIconEndPadding(this.J.getResources().getDimension(i));
    }

    public void setCloseIconEndPadding(float f) {
        if (this.H != f) {
            this.H = f;
            invalidateSelf();
            if (f()) {
                onSizeChange();
            }
        }
    }

    public float getChipEndPadding() {
        return this.I;
    }

    public void setChipEndPaddingResource(@DimenRes int i) {
        setChipEndPadding(this.J.getResources().getDimension(i));
    }

    public void setChipEndPadding(float f) {
        if (this.I != f) {
            this.I = f;
            invalidateSelf();
            onSizeChange();
        }
    }

    @Px
    public int getMaxWidth() {
        return this.ak;
    }

    public void setMaxWidth(@Px int i) {
        this.ak = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return this.aj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.aj = z;
    }
}
