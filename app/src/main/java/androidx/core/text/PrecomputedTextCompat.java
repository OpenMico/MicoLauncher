package androidx.core.text;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Layout;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.core.os.TraceCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* loaded from: classes.dex */
public class PrecomputedTextCompat implements Spannable {
    private static final Object a = new Object();
    @NonNull
    @GuardedBy("sLock")
    private static Executor b = null;
    @NonNull
    private final Spannable c;
    @NonNull
    private final Params d;
    @NonNull
    private final int[] e;
    @Nullable
    private final PrecomputedText f;

    /* loaded from: classes.dex */
    public static final class Params {
        final PrecomputedText.Params a;
        @NonNull
        private final TextPaint b;
        @Nullable
        private final TextDirectionHeuristic c;
        private final int d;
        private final int e;

        /* loaded from: classes.dex */
        public static class Builder {
            @NonNull
            private final TextPaint a;
            private TextDirectionHeuristic b;
            private int c;
            private int d;

            public Builder(@NonNull TextPaint textPaint) {
                this.a = textPaint;
                if (Build.VERSION.SDK_INT >= 23) {
                    this.c = 1;
                    this.d = 1;
                } else {
                    this.d = 0;
                    this.c = 0;
                }
                if (Build.VERSION.SDK_INT >= 18) {
                    this.b = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                } else {
                    this.b = null;
                }
            }

            @RequiresApi(23)
            public Builder setBreakStrategy(int i) {
                this.c = i;
                return this;
            }

            @RequiresApi(23)
            public Builder setHyphenationFrequency(int i) {
                this.d = i;
                return this;
            }

            @RequiresApi(18)
            public Builder setTextDirection(@NonNull TextDirectionHeuristic textDirectionHeuristic) {
                this.b = textDirectionHeuristic;
                return this;
            }

            @NonNull
            public Params build() {
                return new Params(this.a, this.b, this.c, this.d);
            }
        }

        @SuppressLint({"NewApi"})
        Params(@NonNull TextPaint textPaint, @NonNull TextDirectionHeuristic textDirectionHeuristic, int i, int i2) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.a = new PrecomputedText.Params.Builder(textPaint).setBreakStrategy(i).setHyphenationFrequency(i2).setTextDirection(textDirectionHeuristic).build();
            } else {
                this.a = null;
            }
            this.b = textPaint;
            this.c = textDirectionHeuristic;
            this.d = i;
            this.e = i2;
        }

        @RequiresApi(28)
        public Params(@NonNull PrecomputedText.Params params) {
            this.b = params.getTextPaint();
            this.c = params.getTextDirection();
            this.d = params.getBreakStrategy();
            this.e = params.getHyphenationFrequency();
            this.a = Build.VERSION.SDK_INT < 29 ? null : params;
        }

        @NonNull
        public TextPaint getTextPaint() {
            return this.b;
        }

        @Nullable
        @RequiresApi(18)
        public TextDirectionHeuristic getTextDirection() {
            return this.c;
        }

        @RequiresApi(23)
        public int getBreakStrategy() {
            return this.d;
        }

        @RequiresApi(23)
        public int getHyphenationFrequency() {
            return this.e;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean equalsWithoutTextDirection(@NonNull Params params) {
            if ((Build.VERSION.SDK_INT >= 23 && (this.d != params.getBreakStrategy() || this.e != params.getHyphenationFrequency())) || this.b.getTextSize() != params.getTextPaint().getTextSize() || this.b.getTextScaleX() != params.getTextPaint().getTextScaleX() || this.b.getTextSkewX() != params.getTextPaint().getTextSkewX()) {
                return false;
            }
            if ((Build.VERSION.SDK_INT >= 21 && (this.b.getLetterSpacing() != params.getTextPaint().getLetterSpacing() || !TextUtils.equals(this.b.getFontFeatureSettings(), params.getTextPaint().getFontFeatureSettings()))) || this.b.getFlags() != params.getTextPaint().getFlags()) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                if (!this.b.getTextLocales().equals(params.getTextPaint().getTextLocales())) {
                    return false;
                }
            } else if (Build.VERSION.SDK_INT >= 17 && !this.b.getTextLocale().equals(params.getTextPaint().getTextLocale())) {
                return false;
            }
            return this.b.getTypeface() == null ? params.getTextPaint().getTypeface() == null : this.b.getTypeface().equals(params.getTextPaint().getTypeface());
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Params)) {
                return false;
            }
            Params params = (Params) obj;
            if (!equalsWithoutTextDirection(params)) {
                return false;
            }
            return Build.VERSION.SDK_INT < 18 || this.c == params.getTextDirection();
        }

        public int hashCode() {
            if (Build.VERSION.SDK_INT >= 24) {
                return ObjectsCompat.hash(Float.valueOf(this.b.getTextSize()), Float.valueOf(this.b.getTextScaleX()), Float.valueOf(this.b.getTextSkewX()), Float.valueOf(this.b.getLetterSpacing()), Integer.valueOf(this.b.getFlags()), this.b.getTextLocales(), this.b.getTypeface(), Boolean.valueOf(this.b.isElegantTextHeight()), this.c, Integer.valueOf(this.d), Integer.valueOf(this.e));
            }
            if (Build.VERSION.SDK_INT >= 21) {
                return ObjectsCompat.hash(Float.valueOf(this.b.getTextSize()), Float.valueOf(this.b.getTextScaleX()), Float.valueOf(this.b.getTextSkewX()), Float.valueOf(this.b.getLetterSpacing()), Integer.valueOf(this.b.getFlags()), this.b.getTextLocale(), this.b.getTypeface(), Boolean.valueOf(this.b.isElegantTextHeight()), this.c, Integer.valueOf(this.d), Integer.valueOf(this.e));
            }
            if (Build.VERSION.SDK_INT >= 18) {
                return ObjectsCompat.hash(Float.valueOf(this.b.getTextSize()), Float.valueOf(this.b.getTextScaleX()), Float.valueOf(this.b.getTextSkewX()), Integer.valueOf(this.b.getFlags()), this.b.getTextLocale(), this.b.getTypeface(), this.c, Integer.valueOf(this.d), Integer.valueOf(this.e));
            }
            if (Build.VERSION.SDK_INT >= 17) {
                return ObjectsCompat.hash(Float.valueOf(this.b.getTextSize()), Float.valueOf(this.b.getTextScaleX()), Float.valueOf(this.b.getTextSkewX()), Integer.valueOf(this.b.getFlags()), this.b.getTextLocale(), this.b.getTypeface(), this.c, Integer.valueOf(this.d), Integer.valueOf(this.e));
            }
            return ObjectsCompat.hash(Float.valueOf(this.b.getTextSize()), Float.valueOf(this.b.getTextScaleX()), Float.valueOf(this.b.getTextSkewX()), Integer.valueOf(this.b.getFlags()), this.b.getTypeface(), this.c, Integer.valueOf(this.d), Integer.valueOf(this.e));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append("textSize=" + this.b.getTextSize());
            sb.append(", textScaleX=" + this.b.getTextScaleX());
            sb.append(", textSkewX=" + this.b.getTextSkewX());
            if (Build.VERSION.SDK_INT >= 21) {
                sb.append(", letterSpacing=" + this.b.getLetterSpacing());
                sb.append(", elegantTextHeight=" + this.b.isElegantTextHeight());
            }
            if (Build.VERSION.SDK_INT >= 24) {
                sb.append(", textLocale=" + this.b.getTextLocales());
            } else if (Build.VERSION.SDK_INT >= 17) {
                sb.append(", textLocale=" + this.b.getTextLocale());
            }
            sb.append(", typeface=" + this.b.getTypeface());
            if (Build.VERSION.SDK_INT >= 26) {
                sb.append(", variationSettings=" + this.b.getFontVariationSettings());
            }
            sb.append(", textDir=" + this.c);
            sb.append(", breakStrategy=" + this.d);
            sb.append(", hyphenationFrequency=" + this.e);
            sb.append("}");
            return sb.toString();
        }
    }

    @SuppressLint({"NewApi"})
    public static PrecomputedTextCompat create(@NonNull CharSequence charSequence, @NonNull Params params) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(params);
        try {
            TraceCompat.beginSection("PrecomputedText");
            if (Build.VERSION.SDK_INT >= 29 && params.a != null) {
                return new PrecomputedTextCompat(PrecomputedText.create(charSequence, params.a), params);
            }
            ArrayList arrayList = new ArrayList();
            int length = charSequence.length();
            int i = 0;
            while (i < length) {
                int indexOf = TextUtils.indexOf(charSequence, '\n', i, length);
                i = indexOf < 0 ? length : indexOf + 1;
                arrayList.add(Integer.valueOf(i));
            }
            int[] iArr = new int[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
            }
            if (Build.VERSION.SDK_INT >= 23) {
                StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), params.getTextPaint(), Integer.MAX_VALUE).setBreakStrategy(params.getBreakStrategy()).setHyphenationFrequency(params.getHyphenationFrequency()).setTextDirection(params.getTextDirection()).build();
            } else if (Build.VERSION.SDK_INT >= 21) {
                new StaticLayout(charSequence, params.getTextPaint(), Integer.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            return new PrecomputedTextCompat(charSequence, params, iArr);
        } finally {
            TraceCompat.endSection();
        }
    }

    private PrecomputedTextCompat(@NonNull CharSequence charSequence, @NonNull Params params, @NonNull int[] iArr) {
        this.c = new SpannableString(charSequence);
        this.d = params;
        this.e = iArr;
        this.f = null;
    }

    @RequiresApi(28)
    private PrecomputedTextCompat(@NonNull PrecomputedText precomputedText, @NonNull Params params) {
        this.c = precomputedText;
        this.d = params;
        this.e = null;
        this.f = Build.VERSION.SDK_INT < 29 ? null : precomputedText;
    }

    @Nullable
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PrecomputedText getPrecomputedText() {
        Spannable spannable = this.c;
        if (spannable instanceof PrecomputedText) {
            return (PrecomputedText) spannable;
        }
        return null;
    }

    @NonNull
    public Params getParams() {
        return this.d;
    }

    @IntRange(from = 0)
    @SuppressLint({"NewApi"})
    public int getParagraphCount() {
        if (Build.VERSION.SDK_INT >= 29) {
            return this.f.getParagraphCount();
        }
        return this.e.length;
    }

    @IntRange(from = 0)
    @SuppressLint({"NewApi"})
    public int getParagraphStart(@IntRange(from = 0) int i) {
        Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex");
        if (Build.VERSION.SDK_INT >= 29) {
            return this.f.getParagraphStart(i);
        }
        if (i == 0) {
            return 0;
        }
        return this.e[i - 1];
    }

    @IntRange(from = 0)
    @SuppressLint({"NewApi"})
    public int getParagraphEnd(@IntRange(from = 0) int i) {
        Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex");
        if (Build.VERSION.SDK_INT >= 29) {
            return this.f.getParagraphEnd(i);
        }
        return this.e[i];
    }

    /* loaded from: classes.dex */
    private static class a extends FutureTask<PrecomputedTextCompat> {

        /* renamed from: androidx.core.text.PrecomputedTextCompat$a$a  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        private static class CallableC0011a implements Callable<PrecomputedTextCompat> {
            private Params a;
            private CharSequence b;

            CallableC0011a(@NonNull Params params, @NonNull CharSequence charSequence) {
                this.a = params;
                this.b = charSequence;
            }

            /* renamed from: a */
            public PrecomputedTextCompat call() throws Exception {
                return PrecomputedTextCompat.create(this.b, this.a);
            }
        }

        a(@NonNull Params params, @NonNull CharSequence charSequence) {
            super(new CallableC0011a(params, charSequence));
        }
    }

    @UiThread
    public static Future<PrecomputedTextCompat> getTextFuture(@NonNull CharSequence charSequence, @NonNull Params params, @Nullable Executor executor) {
        a aVar = new a(params, charSequence);
        if (executor == null) {
            synchronized (a) {
                if (b == null) {
                    b = Executors.newFixedThreadPool(1);
                }
                executor = b;
            }
        }
        executor.execute(aVar);
        return aVar;
    }

    @Override // android.text.Spannable
    @SuppressLint({"NewApi"})
    public void setSpan(Object obj, int i, int i2, int i3) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        } else if (Build.VERSION.SDK_INT >= 29) {
            this.f.setSpan(obj, i, i2, i3);
        } else {
            this.c.setSpan(obj, i, i2, i3);
        }
    }

    @Override // android.text.Spannable
    @SuppressLint({"NewApi"})
    public void removeSpan(Object obj) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        } else if (Build.VERSION.SDK_INT >= 29) {
            this.f.removeSpan(obj);
        } else {
            this.c.removeSpan(obj);
        }
    }

    @Override // android.text.Spanned
    @SuppressLint({"NewApi"})
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        if (Build.VERSION.SDK_INT >= 29) {
            return (T[]) this.f.getSpans(i, i2, cls);
        }
        return (T[]) this.c.getSpans(i, i2, cls);
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object obj) {
        return this.c.getSpanStart(obj);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object obj) {
        return this.c.getSpanEnd(obj);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object obj) {
        return this.c.getSpanFlags(obj);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i, int i2, Class cls) {
        return this.c.nextSpanTransition(i, i2, cls);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.c.length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.c.charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return this.c.subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    @NonNull
    public String toString() {
        return this.c.toString();
    }
}
