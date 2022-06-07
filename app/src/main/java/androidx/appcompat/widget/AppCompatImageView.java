package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.TintableBackgroundView;
import androidx.core.widget.TintableImageSourceView;

/* loaded from: classes.dex */
public class AppCompatImageView extends ImageView implements TintableBackgroundView, TintableImageSourceView {
    private final c mBackgroundTintHelper;
    private final AppCompatImageHelper mImageHelper;

    public AppCompatImageView(@NonNull Context context) {
        this(context, null);
    }

    public AppCompatImageView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppCompatImageView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(TintContextWrapper.wrap(context), attributeSet, i);
        ThemeUtils.checkAppCompatTheme(this, getContext());
        this.mBackgroundTintHelper = new c(this);
        this.mBackgroundTintHelper.a(attributeSet, i);
        this.mImageHelper = new AppCompatImageHelper(this);
        this.mImageHelper.loadFromAttributes(attributeSet, i);
    }

    @Override // android.widget.ImageView
    public void setImageResource(@DrawableRes int i) {
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            appCompatImageHelper.setImageResource(i);
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            appCompatImageHelper.d();
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            appCompatImageHelper.d();
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            appCompatImageHelper.d();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@DrawableRes int i) {
        super.setBackgroundResource(i);
        c cVar = this.mBackgroundTintHelper;
        if (cVar != null) {
            cVar.a(i);
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        c cVar = this.mBackgroundTintHelper;
        if (cVar != null) {
            cVar.a(drawable);
        }
    }

    @Override // androidx.core.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        c cVar = this.mBackgroundTintHelper;
        if (cVar != null) {
            cVar.a(colorStateList);
        }
    }

    @Override // androidx.core.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ColorStateList getSupportBackgroundTintList() {
        c cVar = this.mBackgroundTintHelper;
        if (cVar != null) {
            return cVar.a();
        }
        return null;
    }

    @Override // androidx.core.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        c cVar = this.mBackgroundTintHelper;
        if (cVar != null) {
            cVar.a(mode);
        }
    }

    @Override // androidx.core.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        c cVar = this.mBackgroundTintHelper;
        if (cVar != null) {
            return cVar.b();
        }
        return null;
    }

    @Override // androidx.core.widget.TintableImageSourceView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportImageTintList(@Nullable ColorStateList colorStateList) {
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            appCompatImageHelper.a(colorStateList);
        }
    }

    @Override // androidx.core.widget.TintableImageSourceView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ColorStateList getSupportImageTintList() {
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            return appCompatImageHelper.b();
        }
        return null;
    }

    @Override // androidx.core.widget.TintableImageSourceView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportImageTintMode(@Nullable PorterDuff.Mode mode) {
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            appCompatImageHelper.a(mode);
        }
    }

    @Override // androidx.core.widget.TintableImageSourceView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PorterDuff.Mode getSupportImageTintMode() {
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            return appCompatImageHelper.c();
        }
        return null;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        c cVar = this.mBackgroundTintHelper;
        if (cVar != null) {
            cVar.c();
        }
        AppCompatImageHelper appCompatImageHelper = this.mImageHelper;
        if (appCompatImageHelper != null) {
            appCompatImageHelper.d();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return this.mImageHelper.a() && super.hasOverlappingRendering();
    }
}
