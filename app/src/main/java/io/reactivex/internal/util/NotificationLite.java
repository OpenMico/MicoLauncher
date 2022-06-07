package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.io.Serializable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public enum NotificationLite {
    COMPLETE;

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T getValue(Object obj) {
        return obj;
    }

    public static <T> Object next(T t) {
        return t;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "NotificationLite.Complete";
    }

    /* loaded from: classes4.dex */
    public static final class b implements Serializable {
        private static final long serialVersionUID = -8759979445933046293L;
        final Throwable e;

        b(Throwable th) {
            this.e = th;
        }

        public String toString() {
            return "NotificationLite.Error[" + this.e + "]";
        }

        public int hashCode() {
            return this.e.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof b) {
                return ObjectHelper.equals(this.e, ((b) obj).e);
            }
            return false;
        }
    }

    /* loaded from: classes4.dex */
    public static final class c implements Serializable {
        private static final long serialVersionUID = -1322257508628817540L;
        final Subscription upstream;

        c(Subscription subscription) {
            this.upstream = subscription;
        }

        public String toString() {
            return "NotificationLite.Subscription[" + this.upstream + "]";
        }
    }

    /* loaded from: classes4.dex */
    public static final class a implements Serializable {
        private static final long serialVersionUID = -7482590109178395495L;
        final Disposable upstream;

        a(Disposable disposable) {
            this.upstream = disposable;
        }

        public String toString() {
            return "NotificationLite.Disposable[" + this.upstream + "]";
        }
    }

    public static Object complete() {
        return COMPLETE;
    }

    public static Object error(Throwable th) {
        return new b(th);
    }

    public static Object subscription(Subscription subscription) {
        return new c(subscription);
    }

    public static Object disposable(Disposable disposable) {
        return new a(disposable);
    }

    public static boolean isComplete(Object obj) {
        return obj == COMPLETE;
    }

    public static boolean isError(Object obj) {
        return obj instanceof b;
    }

    public static boolean isSubscription(Object obj) {
        return obj instanceof c;
    }

    public static boolean isDisposable(Object obj) {
        return obj instanceof a;
    }

    public static Throwable getError(Object obj) {
        return ((b) obj).e;
    }

    public static Subscription getSubscription(Object obj) {
        return ((c) obj).upstream;
    }

    public static Disposable getDisposable(Object obj) {
        return ((a) obj).upstream;
    }

    public static <T> boolean accept(Object obj, Subscriber<? super T> subscriber) {
        if (obj == COMPLETE) {
            subscriber.onComplete();
            return true;
        } else if (obj instanceof b) {
            subscriber.onError(((b) obj).e);
            return true;
        } else {
            subscriber.onNext(obj);
            return false;
        }
    }

    public static <T> boolean accept(Object obj, Observer<? super T> observer) {
        if (obj == COMPLETE) {
            observer.onComplete();
            return true;
        } else if (obj instanceof b) {
            observer.onError(((b) obj).e);
            return true;
        } else {
            observer.onNext(obj);
            return false;
        }
    }

    public static <T> boolean acceptFull(Object obj, Subscriber<? super T> subscriber) {
        if (obj == COMPLETE) {
            subscriber.onComplete();
            return true;
        } else if (obj instanceof b) {
            subscriber.onError(((b) obj).e);
            return true;
        } else if (obj instanceof c) {
            subscriber.onSubscribe(((c) obj).upstream);
            return false;
        } else {
            subscriber.onNext(obj);
            return false;
        }
    }

    public static <T> boolean acceptFull(Object obj, Observer<? super T> observer) {
        if (obj == COMPLETE) {
            observer.onComplete();
            return true;
        } else if (obj instanceof b) {
            observer.onError(((b) obj).e);
            return true;
        } else if (obj instanceof a) {
            observer.onSubscribe(((a) obj).upstream);
            return false;
        } else {
            observer.onNext(obj);
            return false;
        }
    }
}
