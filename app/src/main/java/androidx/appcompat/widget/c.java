package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;

/* compiled from: AppCompatBackgroundHelper.java */
/* loaded from: classes.dex */
public class c {
    @NonNull
    private final View a;
    private TintInfo d;
    private TintInfo e;
    private TintInfo f;
    private int c = -1;
    private final AppCompatDrawableManager b = AppCompatDrawableManager.get();

    public c(@NonNull View view) {
        this.a = view;
    }

    public void a(@Nullable AttributeSet attributeSet, int i) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.ViewBackgroundHelper, i, 0);
        View view = this.a;
        ViewCompat.saveAttributeDataForStyleable(view, view.getContext(), R.styleable.ViewBackgroundHelper, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        try {
            if (obtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                this.c = obtainStyledAttributes.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList a = this.b.a(this.a.getContext(), this.c);
                if (a != null) {
                    b(a);
                }
            }
            if (obtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.a, obtainStyledAttributes.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.a, DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void a(int i) {
        this.c = i;
        AppCompatDrawableManager appCompatDrawableManager = this.b;
        b(appCompatDrawableManager != null ? appCompatDrawableManager.a(this.a.getContext(), i) : null);
        c();
    }

    public void a(Drawable drawable) {
        this.c = -1;
        b((ColorStateList) null);
        c();
    }

    public void a(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new TintInfo();
        }
        TintInfo tintInfo = this.e;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = true;
        c();
    }

    public ColorStateList a() {
        TintInfo tintInfo = this.e;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    public void a(PorterDuff.Mode mode) {
        if (this.e == null) {
            this.e = new TintInfo();
        }
        TintInfo tintInfo = this.e;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = true;
        c();
    }

    public PorterDuff.Mode b() {
        TintInfo tintInfo = this.e;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    public void c() {
        Drawable background = this.a.getBackground();
        if (background == null) {
            return;
        }
        if (!d() || !b(background)) {
            TintInfo tintInfo = this.e;
            if (tintInfo != null) {
                AppCompatDrawableManager.a(background, tintInfo, this.a.getDrawableState());
                return;
            }
            TintInfo tintInfo2 = this.d;
            if (tintInfo2 != null) {
                AppCompatDrawableManager.a(background, tintInfo2, this.a.getDrawableState());
            }
        }
    }

    void b(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.d == null) {
                this.d = new TintInfo();
            }
            TintInfo tintInfo = this.d;
            tintInfo.mTintList = colorStateList;
            tintInfo.mHasTintList = true;
        } else {
            this.d = null;
        }
        c();
    }

    private boolean d() {
        int i = Build.VERSION.SDK_INT;
        return i > 21 ? this.d != null : i == 21;
    }

    private boolean b(@NonNull Drawable drawable) {
        if (this.f == null) {
            this.f = new TintInfo();
        }
        TintInfo tintInfo = this.f;
        tintInfo.a();
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(this.a);
        if (backgroundTintList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = backgroundTintList;
        }
        PorterDuff.Mode backgroundTintMode = ViewCompat.getBackgroundTintMode(this.a);
        if (backgroundTintMode != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = backgroundTintMode;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.a(drawable, tintInfo, this.a.getDrawableState());
        return true;
    }
}
