package autodispose2;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.ProtocolViolationException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* compiled from: AutoDisposeEndConsumerHelper.java */
/* loaded from: classes.dex */
final class e {
    public static boolean a(AtomicReference<Disposable> atomicReference, Disposable disposable, Class<?> cls) {
        k.a(disposable, "next is null");
        if (atomicReference.compareAndSet(null, disposable)) {
            return true;
        }
        disposable.dispose();
        if (atomicReference.get() == b.DISPOSED) {
            return false;
        }
        a(cls);
        return false;
    }

    public static boolean a(AtomicReference<Subscription> atomicReference, Subscription subscription, Class<?> cls) {
        k.a(subscription, "next is null");
        if (atomicReference.compareAndSet(null, subscription)) {
            return true;
        }
        subscription.cancel();
        if (atomicReference.get() == q.CANCELLED) {
            return false;
        }
        a(cls);
        return false;
    }

    public static String a(String str) {
        return "It is not allowed to subscribe with a(n) " + str + " multiple times. Please create a fresh instance of " + str + " and subscribe that to the target source instead.";
    }

    public static void a(Class<?> cls) {
        RxJavaPlugins.onError(new ProtocolViolationException(a(cls.getName())));
    }
}
