package com.jakewharton.rxbinding4.widget;

import android.database.DataSetObserver;
import android.widget.Adapter;
import androidx.exifinterface.media.ExifInterface;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.jakewharton.rxbinding4.widget.b;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AdapterDataChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u000eB\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u0018\u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\rH\u0014R\u0010\u0010\u0004\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00028\u00008TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000f"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AdapterDataChangeObservable;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/widget/Adapter;", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", "adapter", "(Landroid/widget/Adapter;)V", "Landroid/widget/Adapter;", "initialValue", "getInitialValue", "()Landroid/widget/Adapter;", "subscribeListener", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "ObserverDisposable", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class b<T extends Adapter> extends InitialValueObservable<T> {
    private final T a;

    public b(@NotNull T adapter) {
        Intrinsics.checkParameterIsNotNull(adapter, "adapter");
        this.a = adapter;
    }

    @Override // com.jakewharton.rxbinding4.InitialValueObservable
    protected void subscribeListener(@NotNull Observer<? super T> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(getInitialValue(), observer);
            getInitialValue().registerDataSetObserver(aVar.a);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @NotNull
    /* renamed from: a */
    public T getInitialValue() {
        return this.a;
    }

    /* compiled from: AdapterDataChangeObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u0000*\b\b\u0001\u0010\u0001*\u00020\u00022\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\fH\u0014R\u0010\u0010\u0004\u001a\u00028\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0010\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AdapterDataChangeObservable$ObserverDisposable;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/widget/Adapter;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "adapter", "observer", "Lio/reactivex/rxjava3/core/Observer;", "(Landroid/widget/Adapter;Lio/reactivex/rxjava3/core/Observer;)V", "Landroid/widget/Adapter;", "dataSetObserver", "Landroid/database/DataSetObserver;", "onDispose", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a<T extends Adapter> extends MainThreadDisposable {
        @JvmField
        @NotNull
        public final DataSetObserver a;
        private final T b;

        public a(@NotNull T adapter, @NotNull final Observer<? super T> observer) {
            Intrinsics.checkParameterIsNotNull(adapter, "adapter");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.b = adapter;
            this.a = new DataSetObserver() { // from class: com.jakewharton.rxbinding4.widget.AdapterDataChangeObservable$ObserverDisposable$dataSetObserver$1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    Adapter adapter2;
                    if (!b.a.this.isDisposed()) {
                        Observer observer2 = observer;
                        adapter2 = b.a.this.b;
                        observer2.onNext(adapter2);
                    }
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.b.unregisterDataSetObserver(this.a);
        }
    }
}
