package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

/* loaded from: classes.dex */
public class Transformations {
    private Transformations() {
    }

    @NonNull
    @MainThread
    public static <X, Y> LiveData<Y> map(@NonNull LiveData<X> liveData, @NonNull final Function<X, Y> function) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>() { // from class: androidx.lifecycle.Transformations.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable X x) {
                MediatorLiveData.this.setValue(function.apply(x));
            }
        });
        return mediatorLiveData;
    }

    @NonNull
    @MainThread
    public static <X, Y> LiveData<Y> switchMap(@NonNull LiveData<X> liveData, @NonNull final Function<X, LiveData<Y>> function) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>() { // from class: androidx.lifecycle.Transformations.2
            LiveData<Y> mSource;

            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable X x) {
                LiveData<Y> liveData2 = (LiveData) Function.this.apply(x);
                Object obj = this.mSource;
                if (obj != liveData2) {
                    if (obj != null) {
                        mediatorLiveData.removeSource(obj);
                    }
                    this.mSource = liveData2;
                    Object obj2 = this.mSource;
                    if (obj2 != null) {
                        mediatorLiveData.addSource(obj2, new Observer<Y>() { // from class: androidx.lifecycle.Transformations.2.1
                            @Override // androidx.lifecycle.Observer
                            public void onChanged(@Nullable Y y) {
                                mediatorLiveData.setValue(y);
                            }
                        });
                    }
                }
            }
        });
        return mediatorLiveData;
    }

    @NonNull
    @MainThread
    public static <X> LiveData<X> distinctUntilChanged(@NonNull LiveData<X> liveData) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>() { // from class: androidx.lifecycle.Transformations.3
            boolean mFirstTime = true;

            @Override // androidx.lifecycle.Observer
            public void onChanged(X x) {
                T value = MediatorLiveData.this.getValue();
                if (this.mFirstTime || ((value == 0 && x != 0) || (value != 0 && !value.equals(x)))) {
                    this.mFirstTime = false;
                    MediatorLiveData.this.setValue(x);
                }
            }
        });
        return mediatorLiveData;
    }
}
