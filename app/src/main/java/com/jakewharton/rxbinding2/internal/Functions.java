package com.jakewharton.rxbinding2.internal;

import androidx.annotation.RestrictTo;
import io.reactivex.functions.Predicate;
import java.util.concurrent.Callable;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public final class Functions {
    public static final Callable<Boolean> CALLABLE_ALWAYS_TRUE;
    public static final Predicate<Object> PREDICATE_ALWAYS_TRUE;
    private static final a a = new a(true);

    static {
        a aVar = a;
        CALLABLE_ALWAYS_TRUE = aVar;
        PREDICATE_ALWAYS_TRUE = aVar;
    }

    /* loaded from: classes2.dex */
    private static final class a implements Predicate<Object>, Callable<Boolean> {
        private final Boolean a;

        a(Boolean bool) {
            this.a = bool;
        }

        /* renamed from: a */
        public Boolean call() {
            return this.a;
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(Object obj) throws Exception {
            return this.a.booleanValue();
        }
    }

    private Functions() {
        throw new AssertionError("No instances.");
    }
}
