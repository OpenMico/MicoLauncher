package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* compiled from: AppCompatTextHelper.java */
/* loaded from: classes.dex */
public class k {
    @NonNull
    private final TextView a;
    private TintInfo b;
    private TintInfo c;
    private TintInfo d;
    private TintInfo e;
    private TintInfo f;
    private TintInfo g;
    private TintInfo h;
    @NonNull
    private final l i;
    private int j = 0;
    private int k = -1;
    private Typeface l;
    private boolean m;

    public k(@NonNull TextView textView) {
        this.a = textView;
        this.i = new l(this.a);
    }

    @SuppressLint({"NewApi"})
    public void a(@Nullable AttributeSet attributeSet, int i) {
        ColorStateList colorStateList;
        String str;
        ColorStateList colorStateList2;
        boolean z;
        String str2;
        ColorStateList colorStateList3;
        boolean z2;
        int i2;
        Context context = this.a.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextHelper, i, 0);
        TextView textView = this.a;
        ViewCompat.saveAttributeDataForStyleable(textView, textView.getContext(), R.styleable.AppCompatTextHelper, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.b = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.c = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.d = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.e = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
                this.f = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
                this.g = a(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
            }
        }
        obtainStyledAttributes.recycle();
        boolean z3 = this.a.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (resourceId != -1) {
            TintTypedArray obtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId, R.styleable.TextAppearance);
            if (z3 || !obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                z2 = false;
                z = false;
            } else {
                z = obtainStyledAttributes2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                z2 = true;
            }
            a(context, obtainStyledAttributes2);
            if (Build.VERSION.SDK_INT < 23) {
                ColorStateList colorStateList4 = obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColor) ? obtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColor) : null;
                colorStateList3 = obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorHint) ? obtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null;
                if (obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                    colorStateList = obtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                    colorStateList2 = colorStateList4;
                } else {
                    colorStateList2 = colorStateList4;
                    colorStateList = null;
                }
            } else {
                colorStateList3 = null;
                colorStateList2 = null;
                colorStateList = null;
            }
            str = obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textLocale) ? obtainStyledAttributes2.getString(R.styleable.TextAppearance_textLocale) : null;
            str2 = (Build.VERSION.SDK_INT < 26 || !obtainStyledAttributes2.hasValue(R.styleable.TextAppearance_fontVariationSettings)) ? null : obtainStyledAttributes2.getString(R.styleable.TextAppearance_fontVariationSettings);
            obtainStyledAttributes2.recycle();
        } else {
            z2 = false;
            z = false;
            colorStateList3 = null;
            str2 = null;
            colorStateList2 = null;
            str = null;
            colorStateList = null;
        }
        TintTypedArray obtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextAppearance, i, 0);
        if (!z3 && obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            z = obtainStyledAttributes3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            z2 = true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColor)) {
                colorStateList2 = obtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColor);
            }
            if (obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                colorStateList3 = obtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            }
            if (obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList = obtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
            }
        }
        if (obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textLocale)) {
            str = obtainStyledAttributes3.getString(R.styleable.TextAppearance_textLocale);
        }
        if (Build.VERSION.SDK_INT >= 26 && obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
            str2 = obtainStyledAttributes3.getString(R.styleable.TextAppearance_fontVariationSettings);
        }
        if (Build.VERSION.SDK_INT >= 28 && obtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textSize) && obtainStyledAttributes3.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.a.setTextSize(0, 0.0f);
        }
        a(context, obtainStyledAttributes3);
        obtainStyledAttributes3.recycle();
        if (colorStateList2 != null) {
            this.a.setTextColor(colorStateList2);
        }
        if (colorStateList3 != null) {
            this.a.setHintTextColor(colorStateList3);
        }
        if (colorStateList != null) {
            this.a.setLinkTextColor(colorStateList);
        }
        if (!z3 && z2) {
            a(z);
        }
        Typeface typeface = this.l;
        if (typeface != null) {
            if (this.k == -1) {
                this.a.setTypeface(typeface, this.j);
            } else {
                this.a.setTypeface(typeface);
            }
        }
        if (str2 != null) {
            this.a.setFontVariationSettings(str2);
        }
        if (str != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                this.a.setTextLocales(LocaleList.forLanguageTags(str));
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.a.setTextLocale(Locale.forLanguageTag(str.substring(0, str.indexOf(44))));
            }
        }
        this.i.a(attributeSet, i);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.i.a() != 0) {
            int[] e = this.i.e();
            if (e.length > 0) {
                if (this.a.getAutoSizeStepGranularity() != -1.0f) {
                    this.a.setAutoSizeTextTypeUniformWithConfiguration(this.i.c(), this.i.d(), this.i.b(), 0);
                } else {
                    this.a.setAutoSizeTextTypeUniformWithPresetSizes(e, 0);
                }
            }
        }
        TintTypedArray obtainStyledAttributes4 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextView);
        int resourceId2 = obtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableLeftCompat, -1);
        Drawable drawable = resourceId2 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId2) : null;
        int resourceId3 = obtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableTopCompat, -1);
        Drawable drawable2 = resourceId3 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId3) : null;
        int resourceId4 = obtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableRightCompat, -1);
        Drawable drawable3 = resourceId4 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId4) : null;
        int resourceId5 = obtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableBottomCompat, -1);
        Drawable drawable4 = resourceId5 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId5) : null;
        int resourceId6 = obtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableStartCompat, -1);
        Drawable drawable5 = resourceId6 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId6) : null;
        int resourceId7 = obtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableEndCompat, -1);
        a(drawable, drawable2, drawable3, drawable4, drawable5, resourceId7 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId7) : null);
        if (obtainStyledAttributes4.hasValue(R.styleable.AppCompatTextView_drawableTint)) {
            TextViewCompat.setCompoundDrawableTintList(this.a, obtainStyledAttributes4.getColorStateList(R.styleable.AppCompatTextView_drawableTint));
        }
        if (obtainStyledAttributes4.hasValue(R.styleable.AppCompatTextView_drawableTintMode)) {
            i2 = -1;
            TextViewCompat.setCompoundDrawableTintMode(this.a, DrawableUtils.parseTintMode(obtainStyledAttributes4.getInt(R.styleable.AppCompatTextView_drawableTintMode, -1), null));
        } else {
            i2 = -1;
        }
        int dimensionPixelSize = obtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_firstBaselineToTopHeight, i2);
        int dimensionPixelSize2 = obtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lastBaselineToBottomHeight, i2);
        int dimensionPixelSize3 = obtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lineHeight, i2);
        obtainStyledAttributes4.recycle();
        if (dimensionPixelSize != i2) {
            TextViewCompat.setFirstBaselineToTopHeight(this.a, dimensionPixelSize);
        }
        if (dimensionPixelSize2 != i2) {
            TextViewCompat.setLastBaselineToBottomHeight(this.a, dimensionPixelSize2);
        }
        if (dimensionPixelSize3 != i2) {
            TextViewCompat.setLineHeight(this.a, dimensionPixelSize3);
        }
    }

    private void a(Context context, TintTypedArray tintTypedArray) {
        int i;
        String string;
        this.j = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.j);
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 28) {
            this.k = tintTypedArray.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
            if (this.k != -1) {
                this.j = (this.j & 2) | 0;
            }
        }
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) || tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.l = null;
            if (tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
                i = R.styleable.TextAppearance_fontFamily;
            } else {
                i = R.styleable.TextAppearance_android_fontFamily;
            }
            final int i2 = this.k;
            final int i3 = this.j;
            if (!context.isRestricted()) {
                final WeakReference weakReference = new WeakReference(this.a);
                try {
                    Typeface font = tintTypedArray.getFont(i, this.j, new ResourcesCompat.FontCallback() { // from class: androidx.appcompat.widget.k.1
                        @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                        public void onFontRetrievalFailed(int i4) {
                        }

                        @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                        public void onFontRetrieved(@NonNull Typeface typeface) {
                            int i4;
                            if (Build.VERSION.SDK_INT >= 28 && (i4 = i2) != -1) {
                                typeface = Typeface.create(typeface, i4, (i3 & 2) != 0);
                            }
                            k.this.a(weakReference, typeface);
                        }
                    });
                    if (font != null) {
                        if (Build.VERSION.SDK_INT < 28 || this.k == -1) {
                            this.l = font;
                        } else {
                            this.l = Typeface.create(Typeface.create(font, 0), this.k, (this.j & 2) != 0);
                        }
                    }
                    this.m = this.l == null;
                } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.l == null && (string = tintTypedArray.getString(i)) != null) {
                if (Build.VERSION.SDK_INT < 28 || this.k == -1) {
                    this.l = Typeface.create(string, this.j);
                    return;
                }
                Typeface create = Typeface.create(string, 0);
                int i4 = this.k;
                if ((this.j & 2) != 0) {
                    z = true;
                }
                this.l = Typeface.create(create, i4, z);
            }
        } else if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
            this.m = false;
            switch (tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1)) {
                case 1:
                    this.l = Typeface.SANS_SERIF;
                    return;
                case 2:
                    this.l = Typeface.SERIF;
                    return;
                case 3:
                    this.l = Typeface.MONOSPACE;
                    return;
                default:
                    return;
            }
        }
    }

    void a(WeakReference<TextView> weakReference, final Typeface typeface) {
        if (this.m) {
            this.l = typeface;
            final TextView textView = weakReference.get();
            if (textView == null) {
                return;
            }
            if (ViewCompat.isAttachedToWindow(textView)) {
                final int i = this.j;
                textView.post(new Runnable() { // from class: androidx.appcompat.widget.k.2
                    @Override // java.lang.Runnable
                    public void run() {
                        textView.setTypeface(typeface, i);
                    }
                });
                return;
            }
            textView.setTypeface(typeface, this.j);
        }
    }

    public void a(Context context, int i) {
        String string;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            a(obtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList3 = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
                this.a.setTextColor(colorStateList3);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColorLink) && (colorStateList2 = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColorLink)) != null) {
                this.a.setLinkTextColor(colorStateList2);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColorHint) && (colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColorHint)) != null) {
                this.a.setHintTextColor(colorStateList);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize) && obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.a.setTextSize(0, 0.0f);
        }
        a(context, obtainStyledAttributes);
        if (Build.VERSION.SDK_INT >= 26 && obtainStyledAttributes.hasValue(R.styleable.TextAppearance_fontVariationSettings) && (string = obtainStyledAttributes.getString(R.styleable.TextAppearance_fontVariationSettings)) != null) {
            this.a.setFontVariationSettings(string);
        }
        obtainStyledAttributes.recycle();
        Typeface typeface = this.l;
        if (typeface != null) {
            this.a.setTypeface(typeface, this.j);
        }
    }

    public void a(boolean z) {
        this.a.setAllCaps(z);
    }

    public void a() {
        b();
    }

    public void b() {
        if (!(this.b == null && this.c == null && this.d == null && this.e == null)) {
            Drawable[] compoundDrawables = this.a.getCompoundDrawables();
            a(compoundDrawables[0], this.b);
            a(compoundDrawables[1], this.c);
            a(compoundDrawables[2], this.d);
            a(compoundDrawables[3], this.e);
        }
        if (Build.VERSION.SDK_INT < 17) {
            return;
        }
        if (this.f != null || this.g != null) {
            Drawable[] compoundDrawablesRelative = this.a.getCompoundDrawablesRelative();
            a(compoundDrawablesRelative[0], this.f);
            a(compoundDrawablesRelative[2], this.g);
        }
    }

    private void a(Drawable drawable, TintInfo tintInfo) {
        if (drawable != null && tintInfo != null) {
            AppCompatDrawableManager.a(drawable, tintInfo, this.a.getDrawableState());
        }
    }

    private static TintInfo a(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList a = appCompatDrawableManager.a(context, i);
        if (a == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = a;
        return tintInfo;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void a(boolean z, int i, int i2, int i3, int i4) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            c();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void a(int i, float f) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !d()) {
            b(i, f);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void c() {
        this.i.f();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean d() {
        return this.i.g();
    }

    private void b(int i, float f) {
        this.i.a(i, f);
    }

    public void a(int i) {
        this.i.a(i);
    }

    public void a(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        this.i.a(i, i2, i3, i4);
    }

    public void a(@NonNull int[] iArr, int i) throws IllegalArgumentException {
        this.i.a(iArr, i);
    }

    public int e() {
        return this.i.a();
    }

    public int f() {
        return this.i.b();
    }

    public int g() {
        return this.i.c();
    }

    public int h() {
        return this.i.d();
    }

    public int[] i() {
        return this.i.e();
    }

    @Nullable
    public ColorStateList j() {
        TintInfo tintInfo = this.h;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    public void a(@Nullable ColorStateList colorStateList) {
        if (this.h == null) {
            this.h = new TintInfo();
        }
        TintInfo tintInfo = this.h;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = colorStateList != null;
        l();
    }

    @Nullable
    public PorterDuff.Mode k() {
        TintInfo tintInfo = this.h;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    public void a(@Nullable PorterDuff.Mode mode) {
        if (this.h == null) {
            this.h = new TintInfo();
        }
        TintInfo tintInfo = this.h;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = mode != null;
        l();
    }

    private void l() {
        TintInfo tintInfo = this.h;
        this.b = tintInfo;
        this.c = tintInfo;
        this.d = tintInfo;
        this.e = tintInfo;
        this.f = tintInfo;
        this.g = tintInfo;
    }

    private void a(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (Build.VERSION.SDK_INT >= 17 && (drawable5 != null || drawable6 != null)) {
            Drawable[] compoundDrawablesRelative = this.a.getCompoundDrawablesRelative();
            TextView textView = this.a;
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
        } else if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                Drawable[] compoundDrawablesRelative2 = this.a.getCompoundDrawablesRelative();
                if (!(compoundDrawablesRelative2[0] == null && compoundDrawablesRelative2[2] == null)) {
                    TextView textView2 = this.a;
                    Drawable drawable7 = compoundDrawablesRelative2[0];
                    if (drawable2 == null) {
                        drawable2 = compoundDrawablesRelative2[1];
                    }
                    Drawable drawable8 = compoundDrawablesRelative2[2];
                    if (drawable4 == null) {
                        drawable4 = compoundDrawablesRelative2[3];
                    }
                    textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, drawable8, drawable4);
                    return;
                }
            }
            Drawable[] compoundDrawables = this.a.getCompoundDrawables();
            TextView textView3 = this.a;
            if (drawable == null) {
                drawable = compoundDrawables[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawables[1];
            }
            if (drawable3 == null) {
                drawable3 = compoundDrawables[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawables[3];
            }
            textView3.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }

    public void a(@NonNull TextView textView, @Nullable InputConnection inputConnection, @NonNull EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT < 30 && inputConnection != null) {
            EditorInfoCompat.setInitialSurroundingText(editorInfo, textView.getText());
        }
    }
}
