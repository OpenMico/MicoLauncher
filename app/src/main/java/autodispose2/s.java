package autodispose2;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

/* compiled from: HalfSerializer.java */
/* loaded from: classes.dex */
final class s {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> boolean a(Subscriber<? super T> subscriber, T t, AtomicInteger atomicInteger, a aVar) {
        if (atomicInteger.get() == 0 && atomicInteger.compareAndSet(0, 1)) {
            subscriber.onNext(t);
            if (atomicInteger.decrementAndGet() != 0) {
                Throwable a = aVar.a();
                if (a != null) {
                    subscriber.onError(a);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
        }
        return false;
    }

    public static void a(Subscriber<?> subscriber, Throwable th, AtomicInteger atomicInteger, a aVar) {
        if (!aVar.a(th)) {
            RxJavaPlugins.onError(th);
        } else if (atomicInteger.getAndIncrement() == 0) {
            subscriber.onError(aVar.a());
        }
    }

    public static void a(Subscriber<?> subscriber, AtomicInteger atomicInteger, a aVar) {
        if (atomicInteger.getAndIncrement() == 0) {
            Throwable a = aVar.a();
            if (a != null) {
                subscriber.onError(a);
            } else {
                subscriber.onComplete();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> boolean a(Observer<? super T> observer, T t, AtomicInteger atomicInteger, a aVar) {
        if (atomicInteger.get() == 0 && atomicInteger.compareAndSet(0, 1)) {
            observer.onNext(t);
            if (atomicInteger.decrementAndGet() != 0) {
                Throwable a = aVar.a();
                if (a != null) {
                    observer.onError(a);
                } else {
                    observer.onComplete();
                }
                return true;
            }
        }
        return false;
    }

    public static void a(Observer<?> observer, Throwable th, AtomicInteger atomicInteger, a aVar) {
        if (!aVar.a(th)) {
            RxJavaPlugins.onError(th);
        } else if (atomicInteger.getAndIncrement() == 0) {
            observer.onError(aVar.a());
        }
    }

    public static void a(Observer<?> observer, AtomicInteger atomicInteger, a aVar) {
        if (atomicInteger.getAndIncrement() == 0) {
            Throwable a = aVar.a();
            if (a != null) {
                observer.onError(a);
            } else {
                observer.onComplete();
            }
        }
    }
}
