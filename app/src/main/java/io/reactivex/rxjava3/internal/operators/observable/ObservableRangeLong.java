package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.observers.BasicIntQueueDisposable;

/* loaded from: classes5.dex */
public final class ObservableRangeLong extends Observable<Long> {
    private final long a;
    private final long b;

    public ObservableRangeLong(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super Long> observer) {
        long j = this.a;
        a aVar = new a(observer, j, j + this.b);
        observer.onSubscribe(aVar);
        aVar.a();
    }

    /* loaded from: classes5.dex */
    static final class a extends BasicIntQueueDisposable<Long> {
        private static final long serialVersionUID = 396518478098735504L;
        final Observer<? super Long> downstream;
        final long end;
        boolean fused;
        long index;

        a(Observer<? super Long> observer, long j, long j2) {
            this.downstream = observer;
            this.index = j;
            this.end = j2;
        }

        void a() {
            if (!this.fused) {
                Observer<? super Long> observer = this.downstream;
                long j = this.end;
                for (long j2 = this.index; j2 != j && get() == 0; j2++) {
                    observer.onNext(Long.valueOf(j2));
                }
                if (get() == 0) {
                    lazySet(1);
                    observer.onComplete();
                }
            }
        }

        @Nullable
        /* renamed from: b */
        public Long poll() {
            long j = this.index;
            if (j != this.end) {
                this.index = 1 + j;
                return Long.valueOf(j);
            }
            lazySet(1);
            return null;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.index == this.end;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.index = this.end;
            lazySet(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            set(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() != 0;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.fused = true;
            return 1;
        }
    }
}
