package retrofit2.adapter.rxjava3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import retrofit2.Response;

/* compiled from: ResultObservable.java */
/* loaded from: classes6.dex */
final class d<T> extends Observable<Result<T>> {
    private final Observable<Response<T>> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(Observable<Response<T>> observable) {
        this.a = observable;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super Result<T>> observer) {
        this.a.subscribe(new a(observer));
    }

    /* compiled from: ResultObservable.java */
    /* loaded from: classes6.dex */
    private static class a<R> implements Observer<Response<R>> {
        private final Observer<? super Result<R>> a;

        a(Observer<? super Result<R>> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        /* renamed from: a */
        public void onNext(Response<R> response) {
            this.a.onNext(Result.response(response));
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            try {
                this.a.onNext(Result.error(th));
                this.a.onComplete();
            } catch (Throwable th2) {
                try {
                    this.a.onError(th2);
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    RxJavaPlugins.onError(new CompositeException(th2, th3));
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
