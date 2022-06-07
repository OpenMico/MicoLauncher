package autodispose2;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ExceptionHelper.java */
/* loaded from: classes.dex */
final class r {
    private static final Throwable a = new a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(AtomicReference<Throwable> atomicReference, Throwable th) {
        Throwable th2;
        do {
            th2 = atomicReference.get();
            if (th2 == a) {
                return false;
            }
        } while (!atomicReference.compareAndSet(th2, th2 == null ? th : new CompositeException(th2, th)));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static Throwable a(AtomicReference<Throwable> atomicReference) {
        Throwable th = atomicReference.get();
        Throwable th2 = a;
        return th != th2 ? atomicReference.getAndSet(th2) : th;
    }

    /* compiled from: ExceptionHelper.java */
    /* loaded from: classes.dex */
    static final class a extends Throwable {
        a() {
            super("No further exceptions");
        }

        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }
}
