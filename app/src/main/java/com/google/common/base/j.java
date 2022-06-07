package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: Platform.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class j {
    private static final Logger a = Logger.getLogger(j.class.getName());
    private static final i b = b();

    public static String b(@NullableDecl String str) {
        return str == null ? "" : str;
    }

    private j() {
    }

    public static long a() {
        return System.nanoTime();
    }

    public static CharMatcher a(CharMatcher charMatcher) {
        return charMatcher.a();
    }

    public static <T extends Enum<T>> Optional<T> a(Class<T> cls, String str) {
        WeakReference<? extends Enum<?>> weakReference = Enums.a(cls).get(str);
        return weakReference == null ? Optional.absent() : Optional.of(cls.cast(weakReference.get()));
    }

    public static String a(double d) {
        return String.format(Locale.ROOT, "%.4g", Double.valueOf(d));
    }

    public static boolean a(@NullableDecl String str) {
        return str == null || str.isEmpty();
    }

    public static String c(@NullableDecl String str) {
        if (a(str)) {
            return null;
        }
        return str;
    }

    public static d d(String str) {
        Preconditions.checkNotNull(str);
        return b.a(str);
    }

    private static i b() {
        return new a();
    }

    /* compiled from: Platform.java */
    /* loaded from: classes2.dex */
    public static final class a implements i {
        private a() {
        }

        @Override // com.google.common.base.i
        public d a(String str) {
            return new g(Pattern.compile(str));
        }
    }
}
