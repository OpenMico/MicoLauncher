package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
final class TtmlStyle {
    @Nullable
    private String a;
    private int b;
    private boolean c;
    private int d;
    private boolean e;
    private float k;
    @Nullable
    private String l;
    @Nullable
    private Layout.Alignment o;
    @Nullable
    private Layout.Alignment p;
    @Nullable
    private TextEmphasis r;
    private int f = -1;
    private int g = -1;
    private int h = -1;
    private int i = -1;
    private int j = -1;
    private int m = -1;
    private int n = -1;
    private int q = -1;
    private float s = Float.MAX_VALUE;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface FontSizeUnit {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface RubyType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface StyleFlags {
    }

    public int a() {
        if (this.h == -1 && this.i == -1) {
            return -1;
        }
        int i = 0;
        int i2 = this.h == 1 ? 1 : 0;
        if (this.i == 1) {
            i = 2;
        }
        return i2 | i;
    }

    public boolean b() {
        return this.f == 1;
    }

    public TtmlStyle a(boolean z) {
        this.f = z ? 1 : 0;
        return this;
    }

    public boolean c() {
        return this.g == 1;
    }

    public TtmlStyle b(boolean z) {
        this.g = z ? 1 : 0;
        return this;
    }

    public TtmlStyle c(boolean z) {
        this.h = z ? 1 : 0;
        return this;
    }

    public TtmlStyle d(boolean z) {
        this.i = z ? 1 : 0;
        return this;
    }

    @Nullable
    public String d() {
        return this.a;
    }

    public TtmlStyle a(@Nullable String str) {
        this.a = str;
        return this;
    }

    public int e() {
        if (this.c) {
            return this.b;
        }
        throw new IllegalStateException("Font color has not been defined.");
    }

    public TtmlStyle a(int i) {
        this.b = i;
        this.c = true;
        return this;
    }

    public boolean f() {
        return this.c;
    }

    public int g() {
        if (this.e) {
            return this.d;
        }
        throw new IllegalStateException("Background color has not been defined.");
    }

    public TtmlStyle b(int i) {
        this.d = i;
        this.e = true;
        return this;
    }

    public boolean h() {
        return this.e;
    }

    public TtmlStyle a(float f) {
        this.s = f;
        return this;
    }

    public float i() {
        return this.s;
    }

    public TtmlStyle a(@Nullable TtmlStyle ttmlStyle) {
        return a(ttmlStyle, true);
    }

    private TtmlStyle a(@Nullable TtmlStyle ttmlStyle, boolean z) {
        int i;
        Layout.Alignment alignment;
        Layout.Alignment alignment2;
        String str;
        if (ttmlStyle != null) {
            if (!this.c && ttmlStyle.c) {
                a(ttmlStyle.b);
            }
            if (this.h == -1) {
                this.h = ttmlStyle.h;
            }
            if (this.i == -1) {
                this.i = ttmlStyle.i;
            }
            if (this.a == null && (str = ttmlStyle.a) != null) {
                this.a = str;
            }
            if (this.f == -1) {
                this.f = ttmlStyle.f;
            }
            if (this.g == -1) {
                this.g = ttmlStyle.g;
            }
            if (this.n == -1) {
                this.n = ttmlStyle.n;
            }
            if (this.o == null && (alignment2 = ttmlStyle.o) != null) {
                this.o = alignment2;
            }
            if (this.p == null && (alignment = ttmlStyle.p) != null) {
                this.p = alignment;
            }
            if (this.q == -1) {
                this.q = ttmlStyle.q;
            }
            if (this.j == -1) {
                this.j = ttmlStyle.j;
                this.k = ttmlStyle.k;
            }
            if (this.r == null) {
                this.r = ttmlStyle.r;
            }
            if (this.s == Float.MAX_VALUE) {
                this.s = ttmlStyle.s;
            }
            if (z && !this.e && ttmlStyle.e) {
                b(ttmlStyle.d);
            }
            if (z && this.m == -1 && (i = ttmlStyle.m) != -1) {
                this.m = i;
            }
        }
        return this;
    }

    public TtmlStyle b(@Nullable String str) {
        this.l = str;
        return this;
    }

    @Nullable
    public String j() {
        return this.l;
    }

    public TtmlStyle c(int i) {
        this.m = i;
        return this;
    }

    public int k() {
        return this.m;
    }

    public TtmlStyle d(int i) {
        this.n = i;
        return this;
    }

    public int l() {
        return this.n;
    }

    @Nullable
    public Layout.Alignment m() {
        return this.o;
    }

    public TtmlStyle a(@Nullable Layout.Alignment alignment) {
        this.o = alignment;
        return this;
    }

    @Nullable
    public Layout.Alignment n() {
        return this.p;
    }

    public TtmlStyle b(@Nullable Layout.Alignment alignment) {
        this.p = alignment;
        return this;
    }

    public boolean o() {
        return this.q == 1;
    }

    public TtmlStyle e(boolean z) {
        this.q = z ? 1 : 0;
        return this;
    }

    @Nullable
    public TextEmphasis p() {
        return this.r;
    }

    public TtmlStyle a(@Nullable TextEmphasis textEmphasis) {
        this.r = textEmphasis;
        return this;
    }

    public TtmlStyle b(float f) {
        this.k = f;
        return this;
    }

    public TtmlStyle e(int i) {
        this.j = i;
        return this;
    }

    public int q() {
        return this.j;
    }

    public float r() {
        return this.k;
    }
}
