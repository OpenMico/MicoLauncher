package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class TintTypedArray {
    private final Context a;
    private final TypedArray b;
    private TypedValue c;

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, int i, int[] iArr) {
        return new TintTypedArray(context, context.obtainStyledAttributes(i, iArr));
    }

    private TintTypedArray(Context context, TypedArray typedArray) {
        this.a = context;
        this.b = typedArray;
    }

    public TypedArray getWrappedTypeArray() {
        return this.b;
    }

    public Drawable getDrawable(int i) {
        int resourceId;
        if (!this.b.hasValue(i) || (resourceId = this.b.getResourceId(i, 0)) == 0) {
            return this.b.getDrawable(i);
        }
        return AppCompatResources.getDrawable(this.a, resourceId);
    }

    public Drawable getDrawableIfKnown(int i) {
        int resourceId;
        if (!this.b.hasValue(i) || (resourceId = this.b.getResourceId(i, 0)) == 0) {
            return null;
        }
        return AppCompatDrawableManager.get().a(this.a, resourceId, true);
    }

    @Nullable
    public Typeface getFont(@StyleableRes int i, int i2, @Nullable ResourcesCompat.FontCallback fontCallback) {
        int resourceId = this.b.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.c == null) {
            this.c = new TypedValue();
        }
        return ResourcesCompat.getFont(this.a, resourceId, this.c, i2, fontCallback);
    }

    public int length() {
        return this.b.length();
    }

    public int getIndexCount() {
        return this.b.getIndexCount();
    }

    public int getIndex(int i) {
        return this.b.getIndex(i);
    }

    public Resources getResources() {
        return this.b.getResources();
    }

    public CharSequence getText(int i) {
        return this.b.getText(i);
    }

    public String getString(int i) {
        return this.b.getString(i);
    }

    public String getNonResourceString(int i) {
        return this.b.getNonResourceString(i);
    }

    public boolean getBoolean(int i, boolean z) {
        return this.b.getBoolean(i, z);
    }

    public int getInt(int i, int i2) {
        return this.b.getInt(i, i2);
    }

    public float getFloat(int i, float f) {
        return this.b.getFloat(i, f);
    }

    public int getColor(int i, int i2) {
        return this.b.getColor(i, i2);
    }

    public ColorStateList getColorStateList(int i) {
        int resourceId;
        ColorStateList colorStateList;
        return (!this.b.hasValue(i) || (resourceId = this.b.getResourceId(i, 0)) == 0 || (colorStateList = AppCompatResources.getColorStateList(this.a, resourceId)) == null) ? this.b.getColorStateList(i) : colorStateList;
    }

    public int getInteger(int i, int i2) {
        return this.b.getInteger(i, i2);
    }

    public float getDimension(int i, float f) {
        return this.b.getDimension(i, f);
    }

    public int getDimensionPixelOffset(int i, int i2) {
        return this.b.getDimensionPixelOffset(i, i2);
    }

    public int getDimensionPixelSize(int i, int i2) {
        return this.b.getDimensionPixelSize(i, i2);
    }

    public int getLayoutDimension(int i, String str) {
        return this.b.getLayoutDimension(i, str);
    }

    public int getLayoutDimension(int i, int i2) {
        return this.b.getLayoutDimension(i, i2);
    }

    public float getFraction(int i, int i2, int i3, float f) {
        return this.b.getFraction(i, i2, i3, f);
    }

    public int getResourceId(int i, int i2) {
        return this.b.getResourceId(i, i2);
    }

    public CharSequence[] getTextArray(int i) {
        return this.b.getTextArray(i);
    }

    public boolean getValue(int i, TypedValue typedValue) {
        return this.b.getValue(i, typedValue);
    }

    public int getType(int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.b.getType(i);
        }
        if (this.c == null) {
            this.c = new TypedValue();
        }
        this.b.getValue(i, this.c);
        return this.c.type;
    }

    public boolean hasValue(int i) {
        return this.b.hasValue(i);
    }

    public TypedValue peekValue(int i) {
        return this.b.peekValue(i);
    }

    public String getPositionDescription() {
        return this.b.getPositionDescription();
    }

    public void recycle() {
        this.b.recycle();
    }

    @RequiresApi(21)
    public int getChangingConfigurations() {
        return this.b.getChangingConfigurations();
    }
}
