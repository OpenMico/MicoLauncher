package com.trello.rxlifecycle2;

import io.reactivex.Completable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.concurrent.CancellationException;

/* compiled from: Functions.java */
/* loaded from: classes2.dex */
public final class a {
    static final Function<Throwable, Boolean> a = new Function<Throwable, Boolean>() { // from class: com.trello.rxlifecycle2.a.1
        /* renamed from: a */
        public Boolean apply(Throwable th) throws Exception {
            if (th instanceof OutsideLifecycleException) {
                return true;
            }
            Exceptions.propagate(th);
            return false;
        }
    };
    static final Predicate<Boolean> b = new Predicate<Boolean>() { // from class: com.trello.rxlifecycle2.a.2
        /* renamed from: a */
        public boolean test(Boolean bool) throws Exception {
            return bool.booleanValue();
        }
    };
    static final Function<Object, Completable> c = new Function<Object, Completable>() { // from class: com.trello.rxlifecycle2.a.3
        /* renamed from: a */
        public Completable apply(Object obj) throws Exception {
            return Completable.error(new CancellationException());
        }
    };
}
