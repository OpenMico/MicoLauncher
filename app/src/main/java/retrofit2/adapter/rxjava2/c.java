package retrofit2.adapter.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.Response;

/* compiled from: CallExecuteObservable.java */
/* loaded from: classes6.dex */
final class c<T> extends Observable<Response<T>> {
    private final Call<T> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(Call<T> call) {
        this.a = call;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Response<T>> observer) {
        boolean z;
        Throwable th;
        Call<T> clone = this.a.clone();
        a aVar = new a(clone);
        observer.onSubscribe(aVar);
        if (!aVar.isDisposed()) {
            try {
                Response<T> execute = clone.execute();
                if (!aVar.isDisposed()) {
                    observer.onNext(execute);
                }
                if (!aVar.isDisposed()) {
                    try {
                        observer.onComplete();
                    } catch (Throwable th2) {
                        th = th2;
                        z = true;
                        Exceptions.throwIfFatal(th);
                        if (z) {
                            RxJavaPlugins.onError(th);
                        } else if (!aVar.isDisposed()) {
                            try {
                                observer.onError(th);
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                RxJavaPlugins.onError(new CompositeException(th, th3));
                            }
                        }
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                z = false;
            }
        }
    }

    /* compiled from: CallExecuteObservable.java */
    /* loaded from: classes6.dex */
    private static final class a implements Disposable {
        private final Call<?> a;
        private volatile boolean b;

        a(Call<?> call) {
            this.a = call;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b = true;
            this.a.cancel();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b;
        }
    }
}
