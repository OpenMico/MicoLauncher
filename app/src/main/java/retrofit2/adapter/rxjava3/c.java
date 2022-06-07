package retrofit2.adapter.rxjava3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
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

    @Override // io.reactivex.rxjava3.core.Observable
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

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.b = true;
            this.a.cancel();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b;
        }
    }
}
