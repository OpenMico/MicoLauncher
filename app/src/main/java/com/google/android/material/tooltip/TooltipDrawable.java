package com.google.android.material.tooltip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.graphics.ColorUtils;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.OffsetEdgeTreatment;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class TooltipDrawable extends MaterialShapeDrawable implements TextDrawableHelper.TextDrawableDelegate {
    @StyleRes
    private static final int a = R.style.Widget_MaterialComponents_Tooltip;
    @AttrRes
    private static final int b = R.attr.tooltipStyle;
    @Nullable
    private CharSequence c;
    @NonNull
    private final Context d;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    @Nullable
    private final Paint.FontMetrics e = new Paint.FontMetrics();
    @NonNull
    private final TextDrawableHelper f = new TextDrawableHelper(this);
    @NonNull
    private final View.OnLayoutChangeListener g = new View.OnLayoutChangeListener() { // from class: com.google.android.material.tooltip.TooltipDrawable.1
        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            TooltipDrawable.this.a(view);
        }
    };
    @NonNull
    private final Rect h = new Rect();
    private float o = 1.0f;
    private float p = 1.0f;
    private final float q = 0.5f;
    private float r = 0.5f;
    private float s = 1.0f;

    @NonNull
    public static TooltipDrawable createFromAttributes(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        TooltipDrawable tooltipDrawable = new TooltipDrawable(context, attributeSet, i, i2);
        tooltipDrawable.a(attributeSet, i, i2);
        return tooltipDrawable;
    }

    @NonNull
    public static TooltipDrawable createFromAttributes(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        return createFromAttributes(context, attributeSet, b, a);
    }

    @NonNull
    public static TooltipDrawable create(@NonNull Context context) {
        return createFromAttributes(context, null, b, a);
    }

    private TooltipDrawable(@NonNull Context context, AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        super(context, attributeSet, i, i2);
        this.d = context;
        this.f.getTextPaint().density = context.getResources().getDisplayMetrics().density;
        this.f.getTextPaint().setTextAlign(Paint.Align.CENTER);
    }

    private void a(@Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(this.d, attributeSet, R.styleable.Tooltip, i, i2, new int[0]);
        this.m = this.d.getResources().getDimensionPixelSize(R.dimen.mtrl_tooltip_arrowSize);
        setShapeAppearanceModel(getShapeAppearanceModel().toBuilder().setBottomEdge(b()).build());
        setText(obtainStyledAttributes.getText(R.styleable.Tooltip_android_text));
        setTextAppearance(MaterialResources.getTextAppearance(this.d, obtainStyledAttributes, R.styleable.Tooltip_android_textAppearance));
        setFillColor(ColorStateList.valueOf(obtainStyledAttributes.getColor(R.styleable.Tooltip_backgroundTint, MaterialColors.layer(ColorUtils.setAlphaComponent(MaterialColors.getColor(this.d, 16842801, TooltipDrawable.class.getCanonicalName()), 229), ColorUtils.setAlphaComponent(MaterialColors.getColor(this.d, R.attr.colorOnBackground, TooltipDrawable.class.getCanonicalName()), Opcodes.IFEQ)))));
        setStrokeColor(ColorStateList.valueOf(MaterialColors.getColor(this.d, R.attr.colorSurface, TooltipDrawable.class.getCanonicalName())));
        this.i = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Tooltip_android_padding, 0);
        this.j = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Tooltip_android_minWidth, 0);
        this.k = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Tooltip_android_minHeight, 0);
        this.l = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Tooltip_android_layout_margin, 0);
        obtainStyledAttributes.recycle();
    }

    @Nullable
    public CharSequence getText() {
        return this.c;
    }

    public void setTextResource(@StringRes int i) {
        setText(this.d.getResources().getString(i));
    }

    public void setText(@Nullable CharSequence charSequence) {
        if (!TextUtils.equals(this.c, charSequence)) {
            this.c = charSequence;
            this.f.setTextWidthDirty(true);
            invalidateSelf();
        }
    }

    @Nullable
    public TextAppearance getTextAppearance() {
        return this.f.getTextAppearance();
    }

    public void setTextAppearanceResource(@StyleRes int i) {
        setTextAppearance(new TextAppearance(this.d, i));
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance) {
        this.f.setTextAppearance(textAppearance, this.d);
    }

    public int getMinWidth() {
        return this.j;
    }

    public void setMinWidth(@Px int i) {
        this.j = i;
        invalidateSelf();
    }

    public int getMinHeight() {
        return this.k;
    }

    public void setMinHeight(@Px int i) {
        this.k = i;
        invalidateSelf();
    }

    public int getTextPadding() {
        return this.i;
    }

    public void setTextPadding(@Px int i) {
        this.i = i;
        invalidateSelf();
    }

    public int getLayoutMargin() {
        return this.l;
    }

    public void setLayoutMargin(@Px int i) {
        this.l = i;
        invalidateSelf();
    }

    public void setRevealFraction(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.r = 1.2f;
        this.o = f;
        this.p = f;
        this.s = AnimationUtils.lerp(0.0f, 1.0f, 0.19f, 1.0f, f);
        invalidateSelf();
    }

    public void setRelativeToView(@Nullable View view) {
        if (view != null) {
            a(view);
            view.addOnLayoutChangeListener(this.g);
        }
    }

    public void detachView(@Nullable View view) {
        if (view != null) {
            view.removeOnLayoutChangeListener(this.g);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) Math.max((this.i * 2) + c(), this.j);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) Math.max(this.f.getTextPaint().getTextSize(), this.k);
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.scale(this.o, this.p, getBounds().left + (getBounds().width() * 0.5f), getBounds().top + (getBounds().height() * this.r));
        canvas.translate(a(), (float) (-((this.m * Math.sqrt(2.0d)) - this.m)));
        super.draw(canvas);
        a(canvas);
        canvas.restore();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        setShapeAppearanceModel(getShapeAppearanceModel().toBuilder().setBottomEdge(b()).build());
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }

    @Override // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public void onTextSizeChange() {
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        this.n = iArr[0];
        view.getWindowVisibleDisplayFrame(this.h);
    }

    private float a() {
        if (((this.h.right - getBounds().right) - this.n) - this.l < 0) {
            return ((this.h.right - getBounds().right) - this.n) - this.l;
        }
        if (((this.h.left - getBounds().left) - this.n) + this.l > 0) {
            return ((this.h.left - getBounds().left) - this.n) + this.l;
        }
        return 0.0f;
    }

    private EdgeTreatment b() {
        float width = ((float) (getBounds().width() - (this.m * Math.sqrt(2.0d)))) / 2.0f;
        return new OffsetEdgeTreatment(new MarkerEdgeTreatment(this.m), Math.min(Math.max(-a(), -width), width));
    }

    private void a(@NonNull Canvas canvas) {
        if (this.c != null) {
            Rect bounds = getBounds();
            int a2 = (int) a(bounds);
            if (this.f.getTextAppearance() != null) {
                this.f.getTextPaint().drawableState = getState();
                this.f.updateTextPaintDrawState(this.d);
                this.f.getTextPaint().setAlpha((int) (this.s * 255.0f));
            }
            CharSequence charSequence = this.c;
            canvas.drawText(charSequence, 0, charSequence.length(), bounds.centerX(), a2, this.f.getTextPaint());
        }
    }

    private float c() {
        CharSequence charSequence = this.c;
        if (charSequence == null) {
            return 0.0f;
        }
        return this.f.getTextWidth(charSequence.toString());
    }

    private float a(@NonNull Rect rect) {
        return rect.centerY() - d();
    }

    private float d() {
        this.f.getTextPaint().getFontMetrics(this.e);
        return (this.e.descent + this.e.ascent) / 2.0f;
    }
}
