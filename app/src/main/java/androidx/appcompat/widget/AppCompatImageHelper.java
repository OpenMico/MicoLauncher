package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class AppCompatImageHelper {
    @NonNull
    private final ImageView a;
    private TintInfo b;
    private TintInfo c;
    private TintInfo d;

    public AppCompatImageHelper(@NonNull ImageView imageView) {
        this.a = imageView;
    }

    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.AppCompatImageView, i, 0);
        ImageView imageView = this.a;
        ViewCompat.saveAttributeDataForStyleable(imageView, imageView.getContext(), R.styleable.AppCompatImageView, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        try {
            Drawable drawable = this.a.getDrawable();
            if (!(drawable != null || (resourceId = obtainStyledAttributes.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1)) == -1 || (drawable = AppCompatResources.getDrawable(this.a.getContext(), resourceId)) == null)) {
                this.a.setImageDrawable(drawable);
            }
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.a, obtainStyledAttributes.getColorStateList(R.styleable.AppCompatImageView_tint));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tintMode)) {
                ImageViewCompat.setImageTintMode(this.a, DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.AppCompatImageView_tintMode, -1), null));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void setImageResource(int i) {
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(this.a.getContext(), i);
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            this.a.setImageDrawable(drawable);
        } else {
            this.a.setImageDrawable(null);
        }
        d();
    }

    public boolean a() {
        return Build.VERSION.SDK_INT < 21 || !(this.a.getBackground() instanceof RippleDrawable);
    }

    public void a(ColorStateList colorStateList) {
        if (this.c == null) {
            this.c = new TintInfo();
        }
        TintInfo tintInfo = this.c;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = true;
        d();
    }

    public ColorStateList b() {
        TintInfo tintInfo = this.c;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    public void a(PorterDuff.Mode mode) {
        if (this.c == null) {
            this.c = new TintInfo();
        }
        TintInfo tintInfo = this.c;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = true;
        d();
    }

    public PorterDuff.Mode c() {
        TintInfo tintInfo = this.c;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    public void d() {
        Drawable drawable = this.a.getDrawable();
        if (drawable != null) {
            DrawableUtils.a(drawable);
        }
        if (drawable == null) {
            return;
        }
        if (!e() || !a(drawable)) {
            TintInfo tintInfo = this.c;
            if (tintInfo != null) {
                AppCompatDrawableManager.a(drawable, tintInfo, this.a.getDrawableState());
                return;
            }
            TintInfo tintInfo2 = this.b;
            if (tintInfo2 != null) {
                AppCompatDrawableManager.a(drawable, tintInfo2, this.a.getDrawableState());
            }
        }
    }

    private boolean e() {
        int i = Build.VERSION.SDK_INT;
        return i > 21 ? this.b != null : i == 21;
    }

    private boolean a(@NonNull Drawable drawable) {
        if (this.d == null) {
            this.d = new TintInfo();
        }
        TintInfo tintInfo = this.d;
        tintInfo.a();
        ColorStateList imageTintList = ImageViewCompat.getImageTintList(this.a);
        if (imageTintList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = imageTintList;
        }
        PorterDuff.Mode imageTintMode = ImageViewCompat.getImageTintMode(this.a);
        if (imageTintMode != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = imageTintMode;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.a(drawable, tintInfo, this.a.getDrawableState());
        return true;
    }
}
