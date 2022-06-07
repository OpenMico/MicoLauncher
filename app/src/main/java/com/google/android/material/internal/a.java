package com.google.android.material.internal;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;

/* compiled from: StaticLayoutBuilderCompat.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
final class a {
    static final int a;
    private static boolean b;
    @Nullable
    private static Constructor<StaticLayout> c;
    @Nullable
    private static Object d;
    private CharSequence e;
    private final TextPaint f;
    private final int g;
    private int i;
    private boolean p;
    private int h = 0;
    private Layout.Alignment j = Layout.Alignment.ALIGN_NORMAL;
    private int k = Integer.MAX_VALUE;
    private float l = 0.0f;
    private float m = 1.0f;
    private int n = a;
    private boolean o = true;
    @Nullable
    private TextUtils.TruncateAt q = null;

    static {
        a = Build.VERSION.SDK_INT >= 23 ? 1 : 0;
    }

    private a(CharSequence charSequence, TextPaint textPaint, int i) {
        this.e = charSequence;
        this.f = textPaint;
        this.g = i;
        this.i = charSequence.length();
    }

    @NonNull
    public static a a(@NonNull CharSequence charSequence, @NonNull TextPaint textPaint, @IntRange(from = 0) int i) {
        return new a(charSequence, textPaint, i);
    }

    @NonNull
    public a a(@NonNull Layout.Alignment alignment) {
        this.j = alignment;
        return this;
    }

    @NonNull
    public a a(boolean z) {
        this.o = z;
        return this;
    }

    @NonNull
    public a a(@IntRange(from = 0) int i) {
        this.k = i;
        return this;
    }

    @NonNull
    public a a(float f, float f2) {
        this.l = f;
        this.m = f2;
        return this;
    }

    @NonNull
    public a b(int i) {
        this.n = i;
        return this;
    }

    @NonNull
    public a a(@Nullable TextUtils.TruncateAt truncateAt) {
        this.q = truncateAt;
        return this;
    }

    public StaticLayout a() throws C0077a {
        if (this.e == null) {
            this.e = "";
        }
        int max = Math.max(0, this.g);
        CharSequence charSequence = this.e;
        if (this.k == 1) {
            charSequence = TextUtils.ellipsize(charSequence, this.f, max, this.q);
        }
        this.i = Math.min(charSequence.length(), this.i);
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.p && this.k == 1) {
                this.j = Layout.Alignment.ALIGN_OPPOSITE;
            }
            StaticLayout.Builder obtain = StaticLayout.Builder.obtain(charSequence, this.h, this.i, this.f, max);
            obtain.setAlignment(this.j);
            obtain.setIncludePad(this.o);
            obtain.setTextDirection(this.p ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
            TextUtils.TruncateAt truncateAt = this.q;
            if (truncateAt != null) {
                obtain.setEllipsize(truncateAt);
            }
            obtain.setMaxLines(this.k);
            if (!(this.l == 0.0f && this.m == 1.0f)) {
                obtain.setLineSpacing(this.l, this.m);
            }
            if (this.k > 1) {
                obtain.setHyphenationFrequency(this.n);
            }
            return obtain.build();
        }
        b();
        try {
            return (StaticLayout) ((Constructor) Preconditions.checkNotNull(c)).newInstance(charSequence, Integer.valueOf(this.h), Integer.valueOf(this.i), this.f, Integer.valueOf(max), this.j, Preconditions.checkNotNull(d), Float.valueOf(1.0f), Float.valueOf(0.0f), Boolean.valueOf(this.o), null, Integer.valueOf(max), Integer.valueOf(this.k));
        } catch (Exception e) {
            throw new C0077a(e);
        }
    }

    private void b() throws C0077a {
        Class<?> cls;
        if (!b) {
            try {
                boolean z = this.p && Build.VERSION.SDK_INT >= 23;
                if (Build.VERSION.SDK_INT >= 18) {
                    cls = TextDirectionHeuristic.class;
                    d = z ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
                } else {
                    ClassLoader classLoader = a.class.getClassLoader();
                    String str = this.p ? "RTL" : "LTR";
                    Class<?> loadClass = classLoader.loadClass("android.text.TextDirectionHeuristic");
                    Class<?> loadClass2 = classLoader.loadClass("android.text.TextDirectionHeuristics");
                    d = loadClass2.getField(str).get(loadClass2);
                    cls = loadClass;
                }
                c = StaticLayout.class.getDeclaredConstructor(CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Layout.Alignment.class, cls, Float.TYPE, Float.TYPE, Boolean.TYPE, TextUtils.TruncateAt.class, Integer.TYPE, Integer.TYPE);
                c.setAccessible(true);
                b = true;
            } catch (Exception e) {
                throw new C0077a(e);
            }
        }
    }

    public a b(boolean z) {
        this.p = z;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StaticLayoutBuilderCompat.java */
    /* renamed from: com.google.android.material.internal.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static class C0077a extends Exception {
        C0077a(Throwable th) {
            super("Error thrown initializing StaticLayout " + th.getMessage(), th);
        }
    }
}
