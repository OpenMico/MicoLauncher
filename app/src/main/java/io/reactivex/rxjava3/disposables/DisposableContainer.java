package io.reactivex.rxjava3.disposables;

/* loaded from: classes4.dex */
public interface DisposableContainer {
    boolean add(Disposable disposable);

    boolean delete(Disposable disposable);

    boolean remove(Disposable disposable);
}
