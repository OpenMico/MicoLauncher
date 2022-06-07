package io.reactivex.subjects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/* loaded from: classes5.dex */
public abstract class Subject<T> extends Observable<T> implements Observer<T> {
    @Nullable
    public abstract Throwable getThrowable();

    public abstract boolean hasComplete();

    public abstract boolean hasObservers();

    public abstract boolean hasThrowable();

    @NonNull
    public final Subject<T> toSerialized() {
        return this instanceof a ? this : new a(this);
    }
}
