package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import java.lang.ref.WeakReference;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class TextDrawableHelper {
    private float c;
    @Nullable
    private TextAppearance f;
    private final TextPaint a = new TextPaint(1);
    private final TextAppearanceFontCallback b = new TextAppearanceFontCallback() { // from class: com.google.android.material.internal.TextDrawableHelper.1
        @Override // com.google.android.material.resources.TextAppearanceFontCallback
        public void onFontRetrieved(@NonNull Typeface typeface, boolean z) {
            if (!z) {
                TextDrawableHelper.this.d = true;
                TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) TextDrawableHelper.this.e.get();
                if (textDrawableDelegate != null) {
                    textDrawableDelegate.onTextSizeChange();
                }
            }
        }

        @Override // com.google.android.material.resources.TextAppearanceFontCallback
        public void onFontRetrievalFailed(int i) {
            TextDrawableHelper.this.d = true;
            TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) TextDrawableHelper.this.e.get();
            if (textDrawableDelegate != null) {
                textDrawableDelegate.onTextSizeChange();
            }
        }
    };
    private boolean d = true;
    @Nullable
    private WeakReference<TextDrawableDelegate> e = new WeakReference<>(null);

    /* loaded from: classes2.dex */
    public interface TextDrawableDelegate {
        @NonNull
        int[] getState();

        boolean onStateChange(int[] iArr);

        void onTextSizeChange();
    }

    public TextDrawableHelper(@Nullable TextDrawableDelegate textDrawableDelegate) {
        setDelegate(textDrawableDelegate);
    }

    public void setDelegate(@Nullable TextDrawableDelegate textDrawableDelegate) {
        this.e = new WeakReference<>(textDrawableDelegate);
    }

    @NonNull
    public TextPaint getTextPaint() {
        return this.a;
    }

    public void setTextWidthDirty(boolean z) {
        this.d = z;
    }

    public boolean isTextWidthDirty() {
        return this.d;
    }

    public float getTextWidth(String str) {
        if (!this.d) {
            return this.c;
        }
        this.c = a(str);
        this.d = false;
        return this.c;
    }

    private float a(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            return 0.0f;
        }
        return this.a.measureText(charSequence, 0, charSequence.length());
    }

    @Nullable
    public TextAppearance getTextAppearance() {
        return this.f;
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance, Context context) {
        if (this.f != textAppearance) {
            this.f = textAppearance;
            if (textAppearance != null) {
                textAppearance.updateMeasureState(context, this.a, this.b);
                TextDrawableDelegate textDrawableDelegate = this.e.get();
                if (textDrawableDelegate != null) {
                    this.a.drawableState = textDrawableDelegate.getState();
                }
                textAppearance.updateDrawState(context, this.a, this.b);
                this.d = true;
            }
            TextDrawableDelegate textDrawableDelegate2 = this.e.get();
            if (textDrawableDelegate2 != null) {
                textDrawableDelegate2.onTextSizeChange();
                textDrawableDelegate2.onStateChange(textDrawableDelegate2.getState());
            }
        }
    }

    public void updateTextPaintDrawState(Context context) {
        this.f.updateDrawState(context, this.a, this.b);
    }
}
