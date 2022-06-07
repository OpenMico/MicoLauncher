package retrofit2.adapter.rxjava3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import retrofit2.Response;

/* compiled from: BodyObservable.java */
/* loaded from: classes6.dex */
final class a<T> extends Observable<T> {
    private final Observable<Response<T>> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Observable<Response<T>> observable) {
        this.a = observable;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(new C0390a(observer));
    }

    /* compiled from: BodyObservable.java */
    /* renamed from: retrofit2.adapter.rxjava3.a$a  reason: collision with other inner class name */
    /* loaded from: classes6.dex */
    private static class C0390a<R> implements Observer<Response<R>> {
        private final Observer<? super R> a;
        private boolean b;

        C0390a(Observer<? super R> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        /* renamed from: a */
        public void onNext(Response<R> response) {
            if (response.isSuccessful()) {
                this.a.onNext(response.body());
                return;
            }
            this.b = true;
            HttpException httpException = new HttpException(response);
            try {
                this.a.onError(httpException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(httpException, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.b) {
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (!this.b) {
                this.a.onError(th);
                return;
            }
            AssertionError assertionError = new AssertionError("This should never happen! Report as a bug with the full stacktrace.");
            assertionError.initCause(th);
            RxJavaPlugins.onError(assertionError);
        }
    }
}
