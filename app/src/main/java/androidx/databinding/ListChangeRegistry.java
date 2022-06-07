package androidx.databinding;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import androidx.databinding.CallbackRegistry;
import androidx.databinding.ObservableList;

/* loaded from: classes.dex */
public class ListChangeRegistry extends CallbackRegistry<ObservableList.OnListChangedCallback, ObservableList, a> {
    private static final Pools.SynchronizedPool<a> a = new Pools.SynchronizedPool<>(10);
    private static final CallbackRegistry.NotifierCallback<ObservableList.OnListChangedCallback, ObservableList, a> b = new CallbackRegistry.NotifierCallback<ObservableList.OnListChangedCallback, ObservableList, a>() { // from class: androidx.databinding.ListChangeRegistry.1
        /* renamed from: a */
        public void onNotifyCallback(ObservableList.OnListChangedCallback onListChangedCallback, ObservableList observableList, int i, a aVar) {
            switch (i) {
                case 1:
                    onListChangedCallback.onItemRangeChanged(observableList, aVar.a, aVar.b);
                    return;
                case 2:
                    onListChangedCallback.onItemRangeInserted(observableList, aVar.a, aVar.b);
                    return;
                case 3:
                    onListChangedCallback.onItemRangeMoved(observableList, aVar.a, aVar.c, aVar.b);
                    return;
                case 4:
                    onListChangedCallback.onItemRangeRemoved(observableList, aVar.a, aVar.b);
                    return;
                default:
                    onListChangedCallback.onChanged(observableList);
                    return;
            }
        }
    };

    public void notifyChanged(@NonNull ObservableList observableList) {
        notifyCallbacks(observableList, 0, (a) null);
    }

    public void notifyChanged(@NonNull ObservableList observableList, int i, int i2) {
        notifyCallbacks(observableList, 1, a(i, 0, i2));
    }

    public void notifyInserted(@NonNull ObservableList observableList, int i, int i2) {
        notifyCallbacks(observableList, 2, a(i, 0, i2));
    }

    public void notifyMoved(@NonNull ObservableList observableList, int i, int i2, int i3) {
        notifyCallbacks(observableList, 3, a(i, i2, i3));
    }

    public void notifyRemoved(@NonNull ObservableList observableList, int i, int i2) {
        notifyCallbacks(observableList, 4, a(i, 0, i2));
    }

    private static a a(int i, int i2, int i3) {
        a acquire = a.acquire();
        if (acquire == null) {
            acquire = new a();
        }
        acquire.a = i;
        acquire.c = i2;
        acquire.b = i3;
        return acquire;
    }

    public synchronized void notifyCallbacks(@NonNull ObservableList observableList, int i, a aVar) {
        super.notifyCallbacks((ListChangeRegistry) observableList, i, (int) aVar);
        if (aVar != null) {
            a.release(aVar);
        }
    }

    public ListChangeRegistry() {
        super(b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        public int a;
        public int b;
        public int c;

        a() {
        }
    }
}
