package com.google.android.exoplayer2.text;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes2.dex */
public final class Cue implements Bundleable {
    public static final int ANCHOR_TYPE_END = 2;
    public static final int ANCHOR_TYPE_MIDDLE = 1;
    public static final int ANCHOR_TYPE_START = 0;
    public static final float DIMEN_UNSET = -3.4028235E38f;
    public static final int LINE_TYPE_FRACTION = 0;
    public static final int LINE_TYPE_NUMBER = 1;
    public static final int TEXT_SIZE_TYPE_ABSOLUTE = 2;
    public static final int TEXT_SIZE_TYPE_FRACTIONAL = 0;
    public static final int TEXT_SIZE_TYPE_FRACTIONAL_IGNORE_PADDING = 1;
    public static final int TYPE_UNSET = Integer.MIN_VALUE;
    public static final int VERTICAL_TYPE_LR = 2;
    public static final int VERTICAL_TYPE_RL = 1;
    @Nullable
    public final Bitmap bitmap;
    public final float bitmapHeight;
    public final float line;
    public final int lineAnchor;
    public final int lineType;
    @Nullable
    public final Layout.Alignment multiRowAlignment;
    public final float position;
    public final int positionAnchor;
    public final float shearDegrees;
    public final float size;
    @Nullable
    public final CharSequence text;
    @Nullable
    public final Layout.Alignment textAlignment;
    public final float textSize;
    public final int textSizeType;
    public final int verticalType;
    public final int windowColor;
    public final boolean windowColorSet;
    public static final Cue EMPTY = new Builder().setText("").build();
    public static final Bundleable.Creator<Cue> CREATOR = $$Lambda$Cue$brMSGDCqVf6SvEovQ9MlOoo9Uiw.INSTANCE;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface AnchorType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface LineType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface TextSizeType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface VerticalType {
    }

    @Deprecated
    public Cue(CharSequence charSequence) {
        this(charSequence, null, -3.4028235E38f, Integer.MIN_VALUE, Integer.MIN_VALUE, -3.4028235E38f, Integer.MIN_VALUE, -3.4028235E38f);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f, int i, int i2, float f2, int i3, float f3) {
        this(charSequence, alignment, f, i, i2, f2, i3, f3, false, (int) ViewCompat.MEASURED_STATE_MASK);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f, int i, int i2, float f2, int i3, float f3, int i4, float f4) {
        this(charSequence, alignment, null, null, f, i, i2, f2, i3, i4, f4, f3, -3.4028235E38f, false, ViewCompat.MEASURED_STATE_MASK, Integer.MIN_VALUE, 0.0f);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f, int i, int i2, float f2, int i3, float f3, boolean z, int i4) {
        this(charSequence, alignment, null, null, f, i, i2, f2, i3, Integer.MIN_VALUE, -3.4028235E38f, f3, -3.4028235E38f, z, i4, Integer.MIN_VALUE, 0.0f);
    }

    private Cue(@Nullable CharSequence charSequence, @Nullable Layout.Alignment alignment, @Nullable Layout.Alignment alignment2, @Nullable Bitmap bitmap, float f, int i, int i2, float f2, int i3, int i4, float f3, float f4, float f5, boolean z, int i5, int i6, float f6) {
        Layout.Alignment alignment3;
        if (charSequence == null) {
            Assertions.checkNotNull(bitmap);
        } else {
            Assertions.checkArgument(bitmap == null);
        }
        if (charSequence instanceof Spanned) {
            this.text = SpannedString.valueOf(charSequence);
            alignment3 = alignment;
        } else if (charSequence != null) {
            this.text = charSequence.toString();
            alignment3 = alignment;
        } else {
            this.text = null;
            alignment3 = alignment;
        }
        this.textAlignment = alignment3;
        this.multiRowAlignment = alignment2;
        this.bitmap = bitmap;
        this.line = f;
        this.lineType = i;
        this.lineAnchor = i2;
        this.position = f2;
        this.positionAnchor = i3;
        this.size = f4;
        this.bitmapHeight = f5;
        this.windowColorSet = z;
        this.windowColor = i5;
        this.textSizeType = i4;
        this.textSize = f3;
        this.verticalType = i6;
        this.shearDegrees = f6;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(@Nullable Object obj) {
        Bitmap bitmap;
        Bitmap bitmap2;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cue cue = (Cue) obj;
        return TextUtils.equals(this.text, cue.text) && this.textAlignment == cue.textAlignment && this.multiRowAlignment == cue.multiRowAlignment && ((bitmap = this.bitmap) != null ? !((bitmap2 = cue.bitmap) == null || !bitmap.sameAs(bitmap2)) : cue.bitmap == null) && this.line == cue.line && this.lineType == cue.lineType && this.lineAnchor == cue.lineAnchor && this.position == cue.position && this.positionAnchor == cue.positionAnchor && this.size == cue.size && this.bitmapHeight == cue.bitmapHeight && this.windowColorSet == cue.windowColorSet && this.windowColor == cue.windowColor && this.textSizeType == cue.textSizeType && this.textSize == cue.textSize && this.verticalType == cue.verticalType && this.shearDegrees == cue.shearDegrees;
    }

    public int hashCode() {
        return Objects.hashCode(this.text, this.textAlignment, this.multiRowAlignment, this.bitmap, Float.valueOf(this.line), Integer.valueOf(this.lineType), Integer.valueOf(this.lineAnchor), Float.valueOf(this.position), Integer.valueOf(this.positionAnchor), Float.valueOf(this.size), Float.valueOf(this.bitmapHeight), Boolean.valueOf(this.windowColorSet), Integer.valueOf(this.windowColor), Integer.valueOf(this.textSizeType), Float.valueOf(this.textSize), Integer.valueOf(this.verticalType), Float.valueOf(this.shearDegrees));
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        @Nullable
        private CharSequence a;
        @Nullable
        private Bitmap b;
        @Nullable
        private Layout.Alignment c;
        @Nullable
        private Layout.Alignment d;
        private float e;
        private int f;
        private int g;
        private float h;
        private int i;
        private int j;
        private float k;
        private float l;
        private float m;
        private boolean n;
        @ColorInt
        private int o;
        private int p;
        private float q;

        public Builder() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = -3.4028235E38f;
            this.f = Integer.MIN_VALUE;
            this.g = Integer.MIN_VALUE;
            this.h = -3.4028235E38f;
            this.i = Integer.MIN_VALUE;
            this.j = Integer.MIN_VALUE;
            this.k = -3.4028235E38f;
            this.l = -3.4028235E38f;
            this.m = -3.4028235E38f;
            this.n = false;
            this.o = ViewCompat.MEASURED_STATE_MASK;
            this.p = Integer.MIN_VALUE;
        }

        private Builder(Cue cue) {
            this.a = cue.text;
            this.b = cue.bitmap;
            this.c = cue.textAlignment;
            this.d = cue.multiRowAlignment;
            this.e = cue.line;
            this.f = cue.lineType;
            this.g = cue.lineAnchor;
            this.h = cue.position;
            this.i = cue.positionAnchor;
            this.j = cue.textSizeType;
            this.k = cue.textSize;
            this.l = cue.size;
            this.m = cue.bitmapHeight;
            this.n = cue.windowColorSet;
            this.o = cue.windowColor;
            this.p = cue.verticalType;
            this.q = cue.shearDegrees;
        }

        public Builder setText(CharSequence charSequence) {
            this.a = charSequence;
            return this;
        }

        @Nullable
        @Pure
        public CharSequence getText() {
            return this.a;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.b = bitmap;
            return this;
        }

        @Nullable
        @Pure
        public Bitmap getBitmap() {
            return this.b;
        }

        public Builder setTextAlignment(@Nullable Layout.Alignment alignment) {
            this.c = alignment;
            return this;
        }

        @Nullable
        @Pure
        public Layout.Alignment getTextAlignment() {
            return this.c;
        }

        public Builder setMultiRowAlignment(@Nullable Layout.Alignment alignment) {
            this.d = alignment;
            return this;
        }

        public Builder setLine(float f, int i) {
            this.e = f;
            this.f = i;
            return this;
        }

        @Pure
        public float getLine() {
            return this.e;
        }

        @Pure
        public int getLineType() {
            return this.f;
        }

        public Builder setLineAnchor(int i) {
            this.g = i;
            return this;
        }

        @Pure
        public int getLineAnchor() {
            return this.g;
        }

        public Builder setPosition(float f) {
            this.h = f;
            return this;
        }

        @Pure
        public float getPosition() {
            return this.h;
        }

        public Builder setPositionAnchor(int i) {
            this.i = i;
            return this;
        }

        @Pure
        public int getPositionAnchor() {
            return this.i;
        }

        public Builder setTextSize(float f, int i) {
            this.k = f;
            this.j = i;
            return this;
        }

        @Pure
        public int getTextSizeType() {
            return this.j;
        }

        @Pure
        public float getTextSize() {
            return this.k;
        }

        public Builder setSize(float f) {
            this.l = f;
            return this;
        }

        @Pure
        public float getSize() {
            return this.l;
        }

        public Builder setBitmapHeight(float f) {
            this.m = f;
            return this;
        }

        @Pure
        public float getBitmapHeight() {
            return this.m;
        }

        public Builder setWindowColor(@ColorInt int i) {
            this.o = i;
            this.n = true;
            return this;
        }

        public Builder clearWindowColor() {
            this.n = false;
            return this;
        }

        public boolean isWindowColorSet() {
            return this.n;
        }

        @ColorInt
        @Pure
        public int getWindowColor() {
            return this.o;
        }

        public Builder setVerticalType(int i) {
            this.p = i;
            return this;
        }

        public Builder setShearDegrees(float f) {
            this.q = f;
            return this;
        }

        @Pure
        public int getVerticalType() {
            return this.p;
        }

        public Cue build() {
            return new Cue(this.a, this.c, this.d, this.b, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q);
        }
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(a(0), this.text);
        bundle.putSerializable(a(1), this.textAlignment);
        bundle.putSerializable(a(2), this.multiRowAlignment);
        bundle.putParcelable(a(3), this.bitmap);
        bundle.putFloat(a(4), this.line);
        bundle.putInt(a(5), this.lineType);
        bundle.putInt(a(6), this.lineAnchor);
        bundle.putFloat(a(7), this.position);
        bundle.putInt(a(8), this.positionAnchor);
        bundle.putInt(a(9), this.textSizeType);
        bundle.putFloat(a(10), this.textSize);
        bundle.putFloat(a(11), this.size);
        bundle.putFloat(a(12), this.bitmapHeight);
        bundle.putBoolean(a(14), this.windowColorSet);
        bundle.putInt(a(13), this.windowColor);
        bundle.putInt(a(15), this.verticalType);
        bundle.putFloat(a(16), this.shearDegrees);
        return bundle;
    }

    public static final Cue a(Bundle bundle) {
        Builder builder = new Builder();
        CharSequence charSequence = bundle.getCharSequence(a(0));
        if (charSequence != null) {
            builder.setText(charSequence);
        }
        Layout.Alignment alignment = (Layout.Alignment) bundle.getSerializable(a(1));
        if (alignment != null) {
            builder.setTextAlignment(alignment);
        }
        Layout.Alignment alignment2 = (Layout.Alignment) bundle.getSerializable(a(2));
        if (alignment2 != null) {
            builder.setMultiRowAlignment(alignment2);
        }
        Bitmap bitmap = (Bitmap) bundle.getParcelable(a(3));
        if (bitmap != null) {
            builder.setBitmap(bitmap);
        }
        if (bundle.containsKey(a(4)) && bundle.containsKey(a(5))) {
            builder.setLine(bundle.getFloat(a(4)), bundle.getInt(a(5)));
        }
        if (bundle.containsKey(a(6))) {
            builder.setLineAnchor(bundle.getInt(a(6)));
        }
        if (bundle.containsKey(a(7))) {
            builder.setPosition(bundle.getFloat(a(7)));
        }
        if (bundle.containsKey(a(8))) {
            builder.setPositionAnchor(bundle.getInt(a(8)));
        }
        if (bundle.containsKey(a(10)) && bundle.containsKey(a(9))) {
            builder.setTextSize(bundle.getFloat(a(10)), bundle.getInt(a(9)));
        }
        if (bundle.containsKey(a(11))) {
            builder.setSize(bundle.getFloat(a(11)));
        }
        if (bundle.containsKey(a(12))) {
            builder.setBitmapHeight(bundle.getFloat(a(12)));
        }
        if (bundle.containsKey(a(13))) {
            builder.setWindowColor(bundle.getInt(a(13)));
        }
        if (!bundle.getBoolean(a(14), false)) {
            builder.clearWindowColor();
        }
        if (bundle.containsKey(a(15))) {
            builder.setVerticalType(bundle.getInt(a(15)));
        }
        if (bundle.containsKey(a(16))) {
            builder.setShearDegrees(bundle.getFloat(a(16)));
        }
        return builder.build();
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
