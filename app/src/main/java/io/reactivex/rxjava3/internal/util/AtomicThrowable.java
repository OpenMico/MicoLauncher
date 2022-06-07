package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class AtomicThrowable extends AtomicReference<Throwable> {
    private static final long serialVersionUID = 3949248817947090603L;

    public boolean tryAddThrowable(Throwable th) {
        return ExceptionHelper.addThrowable(this, th);
    }

    public boolean tryAddThrowableOrReport(Throwable th) {
        if (tryAddThrowable(th)) {
            return true;
        }
        RxJavaPlugins.onError(th);
        return false;
    }

    public Throwable terminate() {
        return ExceptionHelper.terminate(this);
    }

    public boolean isTerminated() {
        return get() == ExceptionHelper.TERMINATED;
    }

    public void tryTerminateAndReport() {
        Throwable terminate = terminate();
        if (terminate != null && terminate != ExceptionHelper.TERMINATED) {
            RxJavaPlugins.onError(terminate);
        }
    }

    public void tryTerminateConsumer(Subscriber<?> subscriber) {
        Throwable terminate = terminate();
        if (terminate == null) {
            subscriber.onComplete();
        } else if (terminate != ExceptionHelper.TERMINATED) {
            subscriber.onError(terminate);
        }
    }

    public void tryTerminateConsumer(Observer<?> observer) {
        Throwable terminate = terminate();
        if (terminate == null) {
            observer.onComplete();
        } else if (terminate != ExceptionHelper.TERMINATED) {
            observer.onError(terminate);
        }
    }

    public void tryTerminateConsumer(MaybeObserver<?> maybeObserver) {
        Throwable terminate = terminate();
        if (terminate == null) {
            maybeObserver.onComplete();
        } else if (terminate != ExceptionHelper.TERMINATED) {
            maybeObserver.onError(terminate);
        }
    }

    public void tryTerminateConsumer(SingleObserver<?> singleObserver) {
        Throwable terminate = terminate();
        if (terminate != null && terminate != ExceptionHelper.TERMINATED) {
            singleObserver.onError(terminate);
        }
    }

    public void tryTerminateConsumer(CompletableObserver completableObserver) {
        Throwable terminate = terminate();
        if (terminate == null) {
            completableObserver.onComplete();
        } else if (terminate != ExceptionHelper.TERMINATED) {
            completableObserver.onError(terminate);
        }
    }

    public void tryTerminateConsumer(Emitter<?> emitter) {
        Throwable terminate = terminate();
        if (terminate == null) {
            emitter.onComplete();
        } else if (terminate != ExceptionHelper.TERMINATED) {
            emitter.onError(terminate);
        }
    }
}
