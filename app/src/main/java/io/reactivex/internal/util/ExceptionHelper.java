package io.reactivex.internal.util;

import io.reactivex.exceptions.CompositeException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public final class ExceptionHelper {
    public static final Throwable TERMINATED = new a();

    private ExceptionHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static RuntimeException wrapOrThrow(Throwable th) {
        if (th instanceof Error) {
            throw ((Error) th);
        } else if (th instanceof RuntimeException) {
            return (RuntimeException) th;
        } else {
            return new RuntimeException(th);
        }
    }

    public static <T> boolean addThrowable(AtomicReference<Throwable> atomicReference, Throwable th) {
        Throwable th2;
        do {
            th2 = atomicReference.get();
            if (th2 == TERMINATED) {
                return false;
            }
        } while (!atomicReference.compareAndSet(th2, th2 == null ? th : new CompositeException(th2, th)));
        return true;
    }

    public static <T> Throwable terminate(AtomicReference<Throwable> atomicReference) {
        Throwable th = atomicReference.get();
        Throwable th2 = TERMINATED;
        return th != th2 ? atomicReference.getAndSet(th2) : th;
    }

    public static List<Throwable> flatten(Throwable th) {
        ArrayList arrayList = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.offer(th);
        while (!arrayDeque.isEmpty()) {
            Throwable th2 = (Throwable) arrayDeque.removeFirst();
            if (th2 instanceof CompositeException) {
                List<Throwable> exceptions = ((CompositeException) th2).getExceptions();
                for (int size = exceptions.size() - 1; size >= 0; size--) {
                    arrayDeque.offerFirst(exceptions.get(size));
                }
            } else {
                arrayList.add(th2);
            }
        }
        return arrayList;
    }

    public static <E extends Throwable> Exception throwIfThrowable(Throwable th) throws Throwable {
        if (th instanceof Exception) {
            return (Exception) th;
        }
        throw th;
    }

    public static String timeoutMessage(long j, TimeUnit timeUnit) {
        return "The source did not signal an event for " + j + StringUtils.SPACE + timeUnit.toString().toLowerCase() + " and has been terminated.";
    }

    /* loaded from: classes4.dex */
    static final class a extends Throwable {
        private static final long serialVersionUID = -4649703670690200604L;

        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            return this;
        }

        a() {
            super("No further exceptions");
        }
    }
}
