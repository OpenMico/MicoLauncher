package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.Log;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class TextAppearance {
    @FontRes
    private final int a;
    private boolean b = false;
    private Typeface c;
    @Nullable
    public final String fontFamily;
    public final boolean hasLetterSpacing;
    public final float letterSpacing;
    @Nullable
    public final ColorStateList shadowColor;
    public final float shadowDx;
    public final float shadowDy;
    public final float shadowRadius;
    public final boolean textAllCaps;
    @Nullable
    public final ColorStateList textColor;
    @Nullable
    public final ColorStateList textColorHint;
    @Nullable
    public final ColorStateList textColorLink;
    public float textSize;
    public final int textStyle;
    public final int typeface;

    public TextAppearance(@NonNull Context context, @StyleRes int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, R.styleable.TextAppearance);
        this.textSize = obtainStyledAttributes.getDimension(R.styleable.TextAppearance_android_textSize, 0.0f);
        this.textColor = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.TextAppearance_android_textColor);
        this.textColorHint = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.TextAppearance_android_textColorHint);
        this.textColorLink = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.TextAppearance_android_textColorLink);
        this.textStyle = obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_textStyle, 0);
        this.typeface = obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_typeface, 1);
        int a = MaterialResources.a(obtainStyledAttributes, R.styleable.TextAppearance_fontFamily, R.styleable.TextAppearance_android_fontFamily);
        this.a = obtainStyledAttributes.getResourceId(a, 0);
        this.fontFamily = obtainStyledAttributes.getString(a);
        this.textAllCaps = obtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
        this.shadowColor = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.TextAppearance_android_shadowColor);
        this.shadowDx = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.shadowDy = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.shadowRadius = obtainStyledAttributes.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        obtainStyledAttributes.recycle();
        if (Build.VERSION.SDK_INT >= 21) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(i, R.styleable.MaterialTextAppearance);
            this.hasLetterSpacing = obtainStyledAttributes2.hasValue(R.styleable.MaterialTextAppearance_android_letterSpacing);
            this.letterSpacing = obtainStyledAttributes2.getFloat(R.styleable.MaterialTextAppearance_android_letterSpacing, 0.0f);
            obtainStyledAttributes2.recycle();
            return;
        }
        this.hasLetterSpacing = false;
        this.letterSpacing = 0.0f;
    }

    @NonNull
    @VisibleForTesting
    public Typeface getFont(@NonNull Context context) {
        if (this.b) {
            return this.c;
        }
        if (!context.isRestricted()) {
            try {
                this.c = ResourcesCompat.getFont(context, this.a);
                if (this.c != null) {
                    this.c = Typeface.create(this.c, this.textStyle);
                }
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            } catch (Exception e) {
                Log.d("TextAppearance", "Error loading font " + this.fontFamily, e);
            }
        }
        a();
        this.b = true;
        return this.c;
    }

    public void getFontAsync(@NonNull Context context, @NonNull final TextAppearanceFontCallback textAppearanceFontCallback) {
        if (a(context)) {
            getFont(context);
        } else {
            a();
        }
        if (this.a == 0) {
            this.b = true;
        }
        if (this.b) {
            textAppearanceFontCallback.onFontRetrieved(this.c, true);
            return;
        }
        try {
            ResourcesCompat.getFont(context, this.a, new ResourcesCompat.FontCallback() { // from class: com.google.android.material.resources.TextAppearance.1
                @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                public void onFontRetrieved(@NonNull Typeface typeface) {
                    TextAppearance textAppearance = TextAppearance.this;
                    textAppearance.c = Typeface.create(typeface, textAppearance.textStyle);
                    TextAppearance.this.b = true;
                    textAppearanceFontCallback.onFontRetrieved(TextAppearance.this.c, false);
                }

                @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                public void onFontRetrievalFailed(int i) {
                    TextAppearance.this.b = true;
                    textAppearanceFontCallback.onFontRetrievalFailed(i);
                }
            }, null);
        } catch (Resources.NotFoundException unused) {
            this.b = true;
            textAppearanceFontCallback.onFontRetrievalFailed(1);
        } catch (Exception e) {
            Log.d("TextAppearance", "Error loading font " + this.fontFamily, e);
            this.b = true;
            textAppearanceFontCallback.onFontRetrievalFailed(-3);
        }
    }

    public void getFontAsync(@NonNull Context context, @NonNull final TextPaint textPaint, @NonNull final TextAppearanceFontCallback textAppearanceFontCallback) {
        updateTextPaintMeasureState(textPaint, getFallbackFont());
        getFontAsync(context, new TextAppearanceFontCallback() { // from class: com.google.android.material.resources.TextAppearance.2
            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrieved(@NonNull Typeface typeface, boolean z) {
                TextAppearance.this.updateTextPaintMeasureState(textPaint, typeface);
                textAppearanceFontCallback.onFontRetrieved(typeface, z);
            }

            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrievalFailed(int i) {
                textAppearanceFontCallback.onFontRetrievalFailed(i);
            }
        });
    }

    public Typeface getFallbackFont() {
        a();
        return this.c;
    }

    private void a() {
        String str;
        if (this.c == null && (str = this.fontFamily) != null) {
            this.c = Typeface.create(str, this.textStyle);
        }
        if (this.c == null) {
            switch (this.typeface) {
                case 1:
                    this.c = Typeface.SANS_SERIF;
                    break;
                case 2:
                    this.c = Typeface.SERIF;
                    break;
                case 3:
                    this.c = Typeface.MONOSPACE;
                    break;
                default:
                    this.c = Typeface.DEFAULT;
                    break;
            }
            this.c = Typeface.create(this.c, this.textStyle);
        }
    }

    public void updateDrawState(@NonNull Context context, @NonNull TextPaint textPaint, @NonNull TextAppearanceFontCallback textAppearanceFontCallback) {
        updateMeasureState(context, textPaint, textAppearanceFontCallback);
        ColorStateList colorStateList = this.textColor;
        textPaint.setColor(colorStateList != null ? colorStateList.getColorForState(textPaint.drawableState, this.textColor.getDefaultColor()) : ViewCompat.MEASURED_STATE_MASK);
        float f = this.shadowRadius;
        float f2 = this.shadowDx;
        float f3 = this.shadowDy;
        ColorStateList colorStateList2 = this.shadowColor;
        textPaint.setShadowLayer(f, f2, f3, colorStateList2 != null ? colorStateList2.getColorForState(textPaint.drawableState, this.shadowColor.getDefaultColor()) : 0);
    }

    public void updateMeasureState(@NonNull Context context, @NonNull TextPaint textPaint, @NonNull TextAppearanceFontCallback textAppearanceFontCallback) {
        if (a(context)) {
            updateTextPaintMeasureState(textPaint, getFont(context));
        } else {
            getFontAsync(context, textPaint, textAppearanceFontCallback);
        }
    }

    public void updateTextPaintMeasureState(@NonNull TextPaint textPaint, @NonNull Typeface typeface) {
        textPaint.setTypeface(typeface);
        int i = (~typeface.getStyle()) & this.textStyle;
        textPaint.setFakeBoldText((i & 1) != 0);
        textPaint.setTextSkewX((i & 2) != 0 ? -0.25f : 0.0f);
        textPaint.setTextSize(this.textSize);
        if (Build.VERSION.SDK_INT >= 21 && this.hasLetterSpacing) {
            textPaint.setLetterSpacing(this.letterSpacing);
        }
    }

    private boolean a(Context context) {
        if (TextAppearanceConfig.shouldLoadFontSynchronously()) {
            return true;
        }
        int i = this.a;
        return (i != 0 ? ResourcesCompat.getCachedFont(context, i) : null) != null;
    }
}
