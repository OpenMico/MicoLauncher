package retrofit2.adapter.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* compiled from: CallEnqueueObservable.java */
/* loaded from: classes6.dex */
final class b<T> extends Observable<Response<T>> {
    private final Call<T> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Call<T> call) {
        this.a = call;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Response<T>> observer) {
        Call<T> clone = this.a.clone();
        a aVar = new a(clone, observer);
        observer.onSubscribe(aVar);
        if (!aVar.isDisposed()) {
            clone.enqueue(aVar);
        }
    }

    /* compiled from: CallEnqueueObservable.java */
    /* loaded from: classes6.dex */
    private static final class a<T> implements Disposable, Callback<T> {
        boolean a = false;
        private final Call<?> b;
        private final Observer<? super Response<T>> c;
        private volatile boolean d;

        a(Call<?> call, Observer<? super Response<T>> observer) {
            this.b = call;
            this.c = observer;
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<T> call, Response<T> response) {
            if (!this.d) {
                try {
                    this.c.onNext(response);
                    if (!this.d) {
                        this.a = true;
                        this.c.onComplete();
                    }
                } catch (Throwable th) {
                    if (this.a) {
                        RxJavaPlugins.onError(th);
                    } else if (!this.d) {
                        try {
                            this.c.onError(th);
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            RxJavaPlugins.onError(new CompositeException(th, th2));
                        }
                    }
                }
            }
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<T> call, Throwable th) {
            if (!call.isCanceled()) {
                try {
                    this.c.onError(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, th2));
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d = true;
            this.b.cancel();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d;
        }
    }
}
