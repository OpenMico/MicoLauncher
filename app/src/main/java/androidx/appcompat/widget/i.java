package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

/* compiled from: AppCompatSeekBarHelper.java */
/* loaded from: classes.dex */
class i extends g {
    private final SeekBar a;
    private Drawable b;
    private ColorStateList c = null;
    private PorterDuff.Mode d = null;
    private boolean e = false;
    private boolean f = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(SeekBar seekBar) {
        super(seekBar);
        this.a = seekBar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.appcompat.widget.g
    public void a(AttributeSet attributeSet, int i) {
        super.a(attributeSet, i);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.AppCompatSeekBar, i, 0);
        SeekBar seekBar = this.a;
        ViewCompat.saveAttributeDataForStyleable(seekBar, seekBar.getContext(), R.styleable.AppCompatSeekBar, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(R.styleable.AppCompatSeekBar_android_thumb);
        if (drawableIfKnown != null) {
            this.a.setThumb(drawableIfKnown);
        }
        a(obtainStyledAttributes.getDrawable(R.styleable.AppCompatSeekBar_tickMark));
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatSeekBar_tickMarkTintMode)) {
            this.d = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.d);
            this.f = true;
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatSeekBar_tickMarkTint)) {
            this.c = obtainStyledAttributes.getColorStateList(R.styleable.AppCompatSeekBar_tickMarkTint);
            this.e = true;
        }
        obtainStyledAttributes.recycle();
        d();
    }

    void a(@Nullable Drawable drawable) {
        Drawable drawable2 = this.b;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.b = drawable;
        if (drawable != null) {
            drawable.setCallback(this.a);
            DrawableCompat.setLayoutDirection(drawable, ViewCompat.getLayoutDirection(this.a));
            if (drawable.isStateful()) {
                drawable.setState(this.a.getDrawableState());
            }
            d();
        }
        this.a.invalidate();
    }

    private void d() {
        if (this.b == null) {
            return;
        }
        if (this.e || this.f) {
            this.b = DrawableCompat.wrap(this.b.mutate());
            if (this.e) {
                DrawableCompat.setTintList(this.b, this.c);
            }
            if (this.f) {
                DrawableCompat.setTintMode(this.b, this.d);
            }
            if (this.b.isStateful()) {
                this.b.setState(this.a.getDrawableState());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        Drawable drawable = this.b;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        Drawable drawable = this.b;
        if (drawable != null && drawable.isStateful() && drawable.setState(this.a.getDrawableState())) {
            this.a.invalidateDrawable(drawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Canvas canvas) {
        if (this.b != null) {
            int max = this.a.getMax();
            int i = 1;
            if (max > 1) {
                int intrinsicWidth = this.b.getIntrinsicWidth();
                int intrinsicHeight = this.b.getIntrinsicHeight();
                int i2 = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                if (intrinsicHeight >= 0) {
                    i = intrinsicHeight / 2;
                }
                this.b.setBounds(-i2, -i, i2, i);
                float width = ((this.a.getWidth() - this.a.getPaddingLeft()) - this.a.getPaddingRight()) / max;
                int save = canvas.save();
                canvas.translate(this.a.getPaddingLeft(), this.a.getHeight() / 2);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.b.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }
}
