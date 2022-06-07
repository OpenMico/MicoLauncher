package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.annotation.XmlRes;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public class BadgeDrawable extends Drawable implements TextDrawableHelper.TextDrawableDelegate {
    public static final int BOTTOM_END = 8388693;
    public static final int BOTTOM_START = 8388691;
    public static final int TOP_END = 8388661;
    public static final int TOP_START = 8388659;
    @StyleRes
    private static final int a = R.style.Widget_MaterialComponents_Badge;
    @AttrRes
    private static final int b = R.attr.badgeStyle;
    @NonNull
    private final WeakReference<Context> c;
    private final float g;
    private final float h;
    private final float i;
    @NonNull
    private final SavedState j;
    private float k;
    private float l;
    private int m;
    private float n;
    private float o;
    private float p;
    @Nullable
    private WeakReference<View> q;
    @Nullable
    private WeakReference<FrameLayout> r;
    @NonNull
    private final Rect f = new Rect();
    @NonNull
    private final MaterialShapeDrawable d = new MaterialShapeDrawable();
    @NonNull
    private final TextDrawableHelper e = new TextDrawableHelper(this);

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface BadgeGravity {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public static final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.google.android.material.badge.BadgeDrawable.SavedState.1
            @NonNull
            /* renamed from: a */
            public SavedState createFromParcel(@NonNull Parcel parcel) {
                return new SavedState(parcel);
            }

            @NonNull
            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        @ColorInt
        private int a;
        @ColorInt
        private int b;
        private int c;
        private int d;
        private int e;
        @Nullable
        private CharSequence f;
        @PluralsRes
        private int g;
        @StringRes
        private int h;
        private int i;
        private boolean j;
        @Dimension(unit = 1)
        private int k;
        @Dimension(unit = 1)
        private int l;
        @Dimension(unit = 1)
        private int m;
        @Dimension(unit = 1)
        private int n;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SavedState(@NonNull Context context) {
            this.c = 255;
            this.d = -1;
            this.b = new TextAppearance(context, R.style.TextAppearance_MaterialComponents_Badge).textColor.getDefaultColor();
            this.f = context.getString(R.string.mtrl_badge_numberless_content_description);
            this.g = R.plurals.mtrl_badge_content_description;
            this.h = R.string.mtrl_exceed_max_badge_number_content_description;
            this.j = true;
        }

        protected SavedState(@NonNull Parcel parcel) {
            this.c = 255;
            this.d = -1;
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readInt();
            this.f = parcel.readString();
            this.g = parcel.readInt();
            this.i = parcel.readInt();
            this.k = parcel.readInt();
            this.l = parcel.readInt();
            this.m = parcel.readInt();
            this.n = parcel.readInt();
            this.j = parcel.readInt() != 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
            parcel.writeString(this.f.toString());
            parcel.writeInt(this.g);
            parcel.writeInt(this.i);
            parcel.writeInt(this.k);
            parcel.writeInt(this.l);
            parcel.writeInt(this.m);
            parcel.writeInt(this.n);
            parcel.writeInt(this.j ? 1 : 0);
        }
    }

    @NonNull
    public SavedState getSavedState() {
        return this.j;
    }

    @NonNull
    public static BadgeDrawable a(@NonNull Context context, @NonNull SavedState savedState) {
        BadgeDrawable badgeDrawable = new BadgeDrawable(context);
        badgeDrawable.a(savedState);
        return badgeDrawable;
    }

    @NonNull
    public static BadgeDrawable create(@NonNull Context context) {
        return a(context, null, b, a);
    }

    @NonNull
    public static BadgeDrawable createFromResource(@NonNull Context context, @XmlRes int i) {
        AttributeSet parseDrawableXml = DrawableUtils.parseDrawableXml(context, i, "badge");
        int styleAttribute = parseDrawableXml.getStyleAttribute();
        if (styleAttribute == 0) {
            styleAttribute = a;
        }
        return a(context, parseDrawableXml, b, styleAttribute);
    }

    @NonNull
    private static BadgeDrawable a(@NonNull Context context, AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        BadgeDrawable badgeDrawable = new BadgeDrawable(context);
        badgeDrawable.b(context, attributeSet, i, i2);
        return badgeDrawable;
    }

    public void setVisible(boolean z) {
        setVisible(z, false);
        this.j.j = z;
        if (BadgeUtils.USE_COMPAT_PARENT && getCustomBadgeParent() != null && !z) {
            ((ViewGroup) getCustomBadgeParent().getParent()).invalidate();
        }
    }

    private void a(@NonNull SavedState savedState) {
        setMaxCharacterCount(savedState.e);
        if (savedState.d != -1) {
            setNumber(savedState.d);
        }
        setBackgroundColor(savedState.a);
        setBadgeTextColor(savedState.b);
        setBadgeGravity(savedState.i);
        setHorizontalOffset(savedState.k);
        setVerticalOffset(savedState.l);
        a(savedState.m);
        b(savedState.n);
        setVisible(savedState.j);
    }

    private void b(Context context, AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Badge, i, i2, new int[0]);
        setMaxCharacterCount(obtainStyledAttributes.getInt(R.styleable.Badge_maxCharacterCount, 4));
        if (obtainStyledAttributes.hasValue(R.styleable.Badge_number)) {
            setNumber(obtainStyledAttributes.getInt(R.styleable.Badge_number, 0));
        }
        setBackgroundColor(a(context, obtainStyledAttributes, R.styleable.Badge_backgroundColor));
        if (obtainStyledAttributes.hasValue(R.styleable.Badge_badgeTextColor)) {
            setBadgeTextColor(a(context, obtainStyledAttributes, R.styleable.Badge_badgeTextColor));
        }
        setBadgeGravity(obtainStyledAttributes.getInt(R.styleable.Badge_badgeGravity, TOP_END));
        setHorizontalOffset(obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0));
        setVerticalOffset(obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0));
        obtainStyledAttributes.recycle();
    }

    private static int a(Context context, @NonNull TypedArray typedArray, @StyleableRes int i) {
        return MaterialResources.getColorStateList(context, typedArray, i).getDefaultColor();
    }

    private BadgeDrawable(@NonNull Context context) {
        this.c = new WeakReference<>(context);
        ThemeEnforcement.checkMaterialTheme(context);
        Resources resources = context.getResources();
        this.g = resources.getDimensionPixelSize(R.dimen.mtrl_badge_radius);
        this.i = resources.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding);
        this.h = resources.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius);
        this.e.getTextPaint().setTextAlign(Paint.Align.CENTER);
        this.j = new SavedState(context);
        c(R.style.TextAppearance_MaterialComponents_Badge);
    }

    @Deprecated
    public void updateBadgeCoordinates(@NonNull View view, @Nullable ViewGroup viewGroup) {
        if (viewGroup instanceof FrameLayout) {
            updateBadgeCoordinates(view, (FrameLayout) viewGroup);
            return;
        }
        throw new IllegalArgumentException("customBadgeParent must be a FrameLayout");
    }

    public void updateBadgeCoordinates(@NonNull View view) {
        updateBadgeCoordinates(view, (FrameLayout) null);
    }

    public void updateBadgeCoordinates(@NonNull View view, @Nullable FrameLayout frameLayout) {
        this.q = new WeakReference<>(view);
        if (!BadgeUtils.USE_COMPAT_PARENT || frameLayout != null) {
            this.r = new WeakReference<>(frameLayout);
        } else {
            a(view);
        }
        if (!BadgeUtils.USE_COMPAT_PARENT) {
            b(view);
        }
        a();
        invalidateSelf();
    }

    @Nullable
    public FrameLayout getCustomBadgeParent() {
        WeakReference<FrameLayout> weakReference = this.r;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void a(final View view) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup == null || viewGroup.getId() != R.id.mtrl_anchor_parent) {
            WeakReference<FrameLayout> weakReference = this.r;
            if (weakReference == null || weakReference.get() != viewGroup) {
                b(view);
                final FrameLayout frameLayout = new FrameLayout(view.getContext());
                frameLayout.setId(R.id.mtrl_anchor_parent);
                frameLayout.setClipChildren(false);
                frameLayout.setClipToPadding(false);
                frameLayout.setLayoutParams(view.getLayoutParams());
                frameLayout.setMinimumWidth(view.getWidth());
                frameLayout.setMinimumHeight(view.getHeight());
                int indexOfChild = viewGroup.indexOfChild(view);
                viewGroup.removeViewAt(indexOfChild);
                view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                frameLayout.addView(view);
                viewGroup.addView(frameLayout, indexOfChild);
                this.r = new WeakReference<>(frameLayout);
                frameLayout.post(new Runnable() { // from class: com.google.android.material.badge.BadgeDrawable.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BadgeDrawable.this.updateBadgeCoordinates(view, frameLayout);
                    }
                });
            }
        }
    }

    private static void b(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.d.getFillColor().getDefaultColor();
    }

    public void setBackgroundColor(@ColorInt int i) {
        this.j.a = i;
        ColorStateList valueOf = ColorStateList.valueOf(i);
        if (this.d.getFillColor() != valueOf) {
            this.d.setFillColor(valueOf);
            invalidateSelf();
        }
    }

    @ColorInt
    public int getBadgeTextColor() {
        return this.e.getTextPaint().getColor();
    }

    public void setBadgeTextColor(@ColorInt int i) {
        this.j.b = i;
        if (this.e.getTextPaint().getColor() != i) {
            this.e.getTextPaint().setColor(i);
            invalidateSelf();
        }
    }

    public boolean hasNumber() {
        return this.j.d != -1;
    }

    public int getNumber() {
        if (!hasNumber()) {
            return 0;
        }
        return this.j.d;
    }

    public void setNumber(int i) {
        int max = Math.max(0, i);
        if (this.j.d != max) {
            this.j.d = max;
            this.e.setTextWidthDirty(true);
            a();
            invalidateSelf();
        }
    }

    public void clearNumber() {
        this.j.d = -1;
        invalidateSelf();
    }

    public int getMaxCharacterCount() {
        return this.j.e;
    }

    public void setMaxCharacterCount(int i) {
        if (this.j.e != i) {
            this.j.e = i;
            c();
            this.e.setTextWidthDirty(true);
            a();
            invalidateSelf();
        }
    }

    public int getBadgeGravity() {
        return this.j.i;
    }

    public void setBadgeGravity(int i) {
        if (this.j.i != i) {
            this.j.i = i;
            WeakReference<View> weakReference = this.q;
            if (weakReference != null && weakReference.get() != null) {
                View view = this.q.get();
                WeakReference<FrameLayout> weakReference2 = this.r;
                updateBadgeCoordinates(view, weakReference2 != null ? weakReference2.get() : null);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.j.c;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.j.c = i;
        this.e.getTextPaint().setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.f.height();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.f.width();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (!getBounds().isEmpty() && getAlpha() != 0 && isVisible()) {
            this.d.draw(canvas);
            if (hasNumber()) {
                a(canvas);
            }
        }
    }

    @Override // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onTextSizeChange() {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }

    public void setContentDescriptionNumberless(CharSequence charSequence) {
        this.j.f = charSequence;
    }

    public void setContentDescriptionQuantityStringsResource(@PluralsRes int i) {
        this.j.g = i;
    }

    public void setContentDescriptionExceedsMaxBadgeNumberStringResource(@StringRes int i) {
        this.j.h = i;
    }

    @Nullable
    public CharSequence getContentDescription() {
        Context context;
        if (!isVisible()) {
            return null;
        }
        if (!hasNumber()) {
            return this.j.f;
        }
        if (this.j.g <= 0 || (context = this.c.get()) == null) {
            return null;
        }
        if (getNumber() <= this.m) {
            return context.getResources().getQuantityString(this.j.g, getNumber(), Integer.valueOf(getNumber()));
        }
        return context.getString(this.j.h, Integer.valueOf(this.m));
    }

    public void setHorizontalOffset(int i) {
        this.j.k = i;
        a();
    }

    public int getHorizontalOffset() {
        return this.j.k;
    }

    public void a(int i) {
        this.j.m = i;
        a();
    }

    public void setVerticalOffset(int i) {
        this.j.l = i;
        a();
    }

    public int getVerticalOffset() {
        return this.j.l;
    }

    public void b(int i) {
        this.j.n = i;
        a();
    }

    private void c(@StyleRes int i) {
        Context context = this.c.get();
        if (context != null) {
            a(new TextAppearance(context, i));
        }
    }

    private void a(@Nullable TextAppearance textAppearance) {
        Context context;
        if (this.e.getTextAppearance() != textAppearance && (context = this.c.get()) != null) {
            this.e.setTextAppearance(textAppearance, context);
            a();
        }
    }

    private void a() {
        Context context = this.c.get();
        WeakReference<View> weakReference = this.q;
        FrameLayout frameLayout = null;
        View view = weakReference != null ? weakReference.get() : null;
        if (context != null && view != null) {
            Rect rect = new Rect();
            rect.set(this.f);
            Rect rect2 = new Rect();
            view.getDrawingRect(rect2);
            WeakReference<FrameLayout> weakReference2 = this.r;
            if (weakReference2 != null) {
                frameLayout = weakReference2.get();
            }
            if (frameLayout != null || BadgeUtils.USE_COMPAT_PARENT) {
                if (frameLayout == null) {
                    frameLayout = (ViewGroup) view.getParent();
                }
                frameLayout.offsetDescendantRectToMyCoords(view, rect2);
            }
            a(context, rect2, view);
            BadgeUtils.updateBadgeBounds(this.f, this.k, this.l, this.o, this.p);
            this.d.setCornerSize(this.n);
            if (!rect.equals(this.f)) {
                this.d.setBounds(this.f);
            }
        }
    }

    private void a(@NonNull Context context, @NonNull Rect rect, @NonNull View view) {
        int i = this.j.l + this.j.n;
        int i2 = this.j.i;
        if (i2 == 8388691 || i2 == 8388693) {
            this.l = rect.bottom - i;
        } else {
            this.l = rect.top + i;
        }
        if (getNumber() <= 9) {
            this.n = !hasNumber() ? this.g : this.h;
            float f = this.n;
            this.p = f;
            this.o = f;
        } else {
            this.n = this.h;
            this.p = this.n;
            this.o = (this.e.getTextWidth(b()) / 2.0f) + this.i;
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(hasNumber() ? R.dimen.mtrl_badge_text_horizontal_edge_offset : R.dimen.mtrl_badge_horizontal_edge_offset);
        int i3 = this.j.k + this.j.m;
        int i4 = this.j.i;
        if (i4 == 8388659 || i4 == 8388691) {
            this.k = ViewCompat.getLayoutDirection(view) == 0 ? (rect.left - this.o) + dimensionPixelSize + i3 : ((rect.right + this.o) - dimensionPixelSize) - i3;
        } else {
            this.k = ViewCompat.getLayoutDirection(view) == 0 ? ((rect.right + this.o) - dimensionPixelSize) - i3 : (rect.left - this.o) + dimensionPixelSize + i3;
        }
    }

    private void a(Canvas canvas) {
        Rect rect = new Rect();
        String b2 = b();
        this.e.getTextPaint().getTextBounds(b2, 0, b2.length(), rect);
        canvas.drawText(b2, this.k, this.l + (rect.height() / 2), this.e.getTextPaint());
    }

    @NonNull
    private String b() {
        if (getNumber() <= this.m) {
            return NumberFormat.getInstance().format(getNumber());
        }
        Context context = this.c.get();
        return context == null ? "" : context.getString(R.string.mtrl_exceed_max_badge_number_suffix, Integer.valueOf(this.m), Marker.ANY_NON_NULL_MARKER);
    }

    private void c() {
        this.m = ((int) Math.pow(10.0d, getMaxCharacterCount() - 1.0d)) - 1;
    }
}
