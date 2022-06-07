package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class MoreObjects {
    public static <T> T firstNonNull(@NullableDecl T t, @NullableDecl T t2) {
        if (t != null) {
            return t;
        }
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException("Both parameters are null");
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj.getClass().getSimpleName());
    }

    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(cls.getSimpleName());
    }

    public static ToStringHelper toStringHelper(String str) {
        return new ToStringHelper(str);
    }

    /* loaded from: classes2.dex */
    public static final class ToStringHelper {
        private final String a;
        private final a b;
        private a c;
        private boolean d;

        private ToStringHelper(String str) {
            this.b = new a();
            this.c = this.b;
            this.d = false;
            this.a = (String) Preconditions.checkNotNull(str);
        }

        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.d = true;
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, @NullableDecl Object obj) {
            return a(str, obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, boolean z) {
            return a(str, String.valueOf(z));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, char c) {
            return a(str, String.valueOf(c));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, double d) {
            return a(str, String.valueOf(d));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, float f) {
            return a(str, String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, int i) {
            return a(str, String.valueOf(i));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, long j) {
            return a(str, String.valueOf(j));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(@NullableDecl Object obj) {
            return a(obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(boolean z) {
            return a(String.valueOf(z));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(char c) {
            return a(String.valueOf(c));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(double d) {
            return a(String.valueOf(d));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(float f) {
            return a(String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(int i) {
            return a(String.valueOf(i));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(long j) {
            return a(String.valueOf(j));
        }

        public String toString() {
            boolean z = this.d;
            String str = "";
            StringBuilder sb = new StringBuilder(32);
            sb.append(this.a);
            sb.append('{');
            for (a aVar = this.b.c; aVar != null; aVar = aVar.c) {
                Object obj = aVar.b;
                if (!z || obj != null) {
                    sb.append(str);
                    str = ", ";
                    if (aVar.a != null) {
                        sb.append(aVar.a);
                        sb.append('=');
                    }
                    if (obj == null || !obj.getClass().isArray()) {
                        sb.append(obj);
                    } else {
                        String deepToString = Arrays.deepToString(new Object[]{obj});
                        sb.append((CharSequence) deepToString, 1, deepToString.length() - 1);
                    }
                }
            }
            sb.append('}');
            return sb.toString();
        }

        private a a() {
            a aVar = new a();
            this.c.c = aVar;
            this.c = aVar;
            return aVar;
        }

        private ToStringHelper a(@NullableDecl Object obj) {
            a().b = obj;
            return this;
        }

        private ToStringHelper a(String str, @NullableDecl Object obj) {
            a a2 = a();
            a2.b = obj;
            a2.a = (String) Preconditions.checkNotNull(str);
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class a {
            @NullableDecl
            String a;
            @NullableDecl
            Object b;
            @NullableDecl
            a c;

            private a() {
            }
        }
    }

    private MoreObjects() {
    }
}
