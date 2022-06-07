package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public final class PreFillType {
    @VisibleForTesting
    static final Bitmap.Config a = Bitmap.Config.RGB_565;
    private final int b;
    private final int c;
    private final Bitmap.Config d;
    private final int e;

    PreFillType(int i, int i2, Bitmap.Config config, int i3) {
        this.d = (Bitmap.Config) Preconditions.checkNotNull(config, "Config must not be null");
        this.b = i;
        this.c = i2;
        this.e = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bitmap.Config c() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return this.e;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PreFillType)) {
            return false;
        }
        PreFillType preFillType = (PreFillType) obj;
        return this.c == preFillType.c && this.b == preFillType.b && this.e == preFillType.e && this.d == preFillType.d;
    }

    public int hashCode() {
        return (((((this.b * 31) + this.c) * 31) + this.d.hashCode()) * 31) + this.e;
    }

    public String toString() {
        return "PreFillSize{width=" + this.b + ", height=" + this.c + ", config=" + this.d + ", weight=" + this.e + '}';
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private final int a;
        private final int b;
        private Bitmap.Config c;
        private int d;

        public Builder(int i) {
            this(i, i);
        }

        public Builder(int i, int i2) {
            this.d = 1;
            if (i <= 0) {
                throw new IllegalArgumentException("Width must be > 0");
            } else if (i2 > 0) {
                this.a = i;
                this.b = i2;
            } else {
                throw new IllegalArgumentException("Height must be > 0");
            }
        }

        public Builder setConfig(@Nullable Bitmap.Config config) {
            this.c = config;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Bitmap.Config a() {
            return this.c;
        }

        public Builder setWeight(int i) {
            if (i > 0) {
                this.d = i;
                return this;
            }
            throw new IllegalArgumentException("Weight must be > 0");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public PreFillType b() {
            return new PreFillType(this.a, this.b, this.c, this.d);
        }
    }
}
