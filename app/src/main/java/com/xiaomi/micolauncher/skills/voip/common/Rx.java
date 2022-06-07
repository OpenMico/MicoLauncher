package com.xiaomi.micolauncher.skills.voip.common;

import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.voip.common.Rx;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class Rx {
    private static String a = "Rx";

    /* loaded from: classes3.dex */
    public interface Consumer<T> {
        void accept(T t);
    }

    /* loaded from: classes3.dex */
    public interface OnSubscribe<T> {
        void call(Subscriber<? super T> subscriber);
    }

    /* loaded from: classes3.dex */
    public interface Subscriber<T> {
        void onCompleted();

        void onError(Throwable th);

        void onNext(T t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) {
    }

    /* loaded from: classes3.dex */
    public static class C<T> {
        T a;

        public C(T t) {
            this.a = t;
        }

        public T get() {
            return this.a;
        }
    }

    /* loaded from: classes3.dex */
    public interface Action1NonNull<T> extends io.reactivex.functions.Consumer<T> {
        void call(T t);

        @Override // io.reactivex.functions.Consumer
        default void accept(T t) throws Exception {
            call(t);
        }
    }

    /* loaded from: classes3.dex */
    public interface Action1<T> extends io.reactivex.functions.Consumer<C<T>> {
        void call(T t);

        @Override // io.reactivex.functions.Consumer
        /* bridge */ /* synthetic */ default void accept(Object obj) throws Exception {
            accept((C) ((C) obj));
        }

        default void accept(C<T> c) throws Exception {
            call(c.get());
        }
    }

    /* loaded from: classes3.dex */
    public interface Action0 extends Action {
        void call();

        @Override // io.reactivex.functions.Action
        default void run() throws Exception {
            call();
        }
    }

    /* loaded from: classes3.dex */
    public static class Observable<T> extends io.reactivex.Observable<C<T>> {
        io.reactivex.Observable<C<T>> a;

        public Observable(io.reactivex.Observable<C<T>> observable) {
            this.a = observable;
        }

        @Override // io.reactivex.Observable
        protected void subscribeActual(Observer<? super C<T>> observer) {
            this.a.subscribe(observer);
        }
    }

    /* loaded from: classes3.dex */
    public static class Subscription implements Disposable {
        Disposable a;

        public Subscription(Disposable disposable) {
            this.a = disposable;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.a.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.a.isDisposed();
        }

        public void unsubscribe() {
            this.a.dispose();
        }

        public boolean isUnsubscribed() {
            return this.a.isDisposed();
        }
    }

    public static <T> Observable<T> create(final Callable<T> callable) {
        return new Observable<>(io.reactivex.Observable.fromCallable(new Callable() { // from class: com.xiaomi.micolauncher.skills.voip.common.-$$Lambda$Rx$yKUZklCJ12fIB2m98HKz4A4VE-w
            @Override // java.util.concurrent.Callable
            public final Object call() {
                Rx.C a2;
                a2 = Rx.a(callable);
                return a2;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C a(Callable callable) throws Exception {
        return new C(callable.call());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(OnSubscribe onSubscribe, final ObservableEmitter observableEmitter) throws Exception {
        onSubscribe.call(new Subscriber<T>() { // from class: com.xiaomi.micolauncher.skills.voip.common.Rx.1
            @Override // com.xiaomi.micolauncher.skills.voip.common.Rx.Subscriber
            public void onNext(T t) {
                ObservableEmitter.this.onNext(t);
            }

            @Override // com.xiaomi.micolauncher.skills.voip.common.Rx.Subscriber
            public void onError(Throwable th) {
                ObservableEmitter.this.onError(th);
            }

            @Override // com.xiaomi.micolauncher.skills.voip.common.Rx.Subscriber
            public void onCompleted() {
                ObservableEmitter.this.onComplete();
            }
        });
    }

    public static <T> ObservableOnSubscribe<T> transformNonNull(final OnSubscribe<T> onSubscribe) {
        return new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.voip.common.-$$Lambda$Rx$d4A7l4aVhd_9EA2LiyiC042hTrA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                Rx.b(Rx.OnSubscribe.this, observableEmitter);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(OnSubscribe onSubscribe, final ObservableEmitter observableEmitter) throws Exception {
        onSubscribe.call(new Subscriber<T>() { // from class: com.xiaomi.micolauncher.skills.voip.common.Rx.2
            @Override // com.xiaomi.micolauncher.skills.voip.common.Rx.Subscriber
            public void onNext(T t) {
                ObservableEmitter.this.onNext(new C(t));
            }

            @Override // com.xiaomi.micolauncher.skills.voip.common.Rx.Subscriber
            public void onError(Throwable th) {
                ObservableEmitter.this.onError(th);
            }

            @Override // com.xiaomi.micolauncher.skills.voip.common.Rx.Subscriber
            public void onCompleted() {
                ObservableEmitter.this.onComplete();
            }
        });
    }

    public static <T> ObservableOnSubscribe<C<T>> transform(final OnSubscribe<T> onSubscribe) {
        return new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.voip.common.-$$Lambda$Rx$PEDEGE3B1T8FXyw6sSOM4HOAPCQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                Rx.a(Rx.OnSubscribe.this, observableEmitter);
            }
        };
    }

    public static <T> io.reactivex.Observable<T> createNonNull(OnSubscribe<T> onSubscribe) {
        return io.reactivex.Observable.create(transformNonNull(onSubscribe));
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(io.reactivex.Observable.create(transform(onSubscribe)));
    }

    public static Disposable runOnIO(Runnable runnable) {
        return runOnIO(runnable, 0L);
    }

    public static Disposable runOnIO(final Runnable runnable, long j) {
        return io.reactivex.Observable.timer(j, TimeUnit.MILLISECONDS).observeOn(MicoSchedulers.io()).subscribe(new io.reactivex.functions.Consumer() { // from class: com.xiaomi.micolauncher.skills.voip.common.-$$Lambda$Rx$5Fa2BYFr6imbBrCy9oHcCUN39y4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Long l = (Long) obj;
                runnable.run();
            }
        }, $$Lambda$Rx$g96NRYTT7LSFbwVHs8OIvpXqOI.INSTANCE);
    }

    public static Disposable runOnUI(Runnable runnable) {
        return runOnUI(runnable, 0L);
    }

    public static Disposable runOnUI(final Runnable runnable, long j) {
        return io.reactivex.Observable.timer(j, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.functions.Consumer() { // from class: com.xiaomi.micolauncher.skills.voip.common.-$$Lambda$Rx$JLSjxA8x8gsuPQwdo6rUK5UxjDI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Long l = (Long) obj;
                runnable.run();
            }
        }, $$Lambda$Rx$KKxeuvA6x1KjVz1EoJSrKOrriSw.INSTANCE);
    }

    public static <T> Disposable async(Callable<T> callable, Consumer<T> consumer) {
        return async(callable, consumer, $$Lambda$Rx$I6o_ERYapVtNqOFMghhaAI79Tw.INSTANCE);
    }

    public static <T> Disposable async(Callable<T> callable, final Consumer<T> consumer, final Consumer<Throwable> consumer2) {
        return create(callable).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.functions.Consumer() { // from class: com.xiaomi.micolauncher.skills.voip.common.-$$Lambda$Rx$JkqHMtO93XqZg2N0sLCdSqzkOCU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Rx.a(Rx.Consumer.this, (Rx.C) obj);
            }
        }, new io.reactivex.functions.Consumer() { // from class: com.xiaomi.micolauncher.skills.voip.common.-$$Lambda$Rx$NgLNqWGNf43O9axhcZLTu4XU0h0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Rx.a(Rx.Consumer.this, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, C c) throws Exception {
        consumer.accept(c.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, Throwable th) throws Exception {
        th.printStackTrace();
        if (consumer != null) {
            consumer.accept(th);
        }
    }

    public static <T> T catchAll(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean catchAll(Runnable... runnableArr) {
        boolean z = false;
        for (Runnable runnable : runnableArr) {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
                z = true;
            }
        }
        return z;
    }
}
