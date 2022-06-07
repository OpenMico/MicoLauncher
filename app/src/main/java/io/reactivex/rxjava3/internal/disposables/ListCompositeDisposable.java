package io.reactivex.rxjava3.internal.disposables;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ListCompositeDisposable implements Disposable, DisposableContainer {
    List<Disposable> a;
    volatile boolean b;

    public ListCompositeDisposable() {
    }

    public ListCompositeDisposable(Disposable... disposableArr) {
        Objects.requireNonNull(disposableArr, "resources is null");
        this.a = new LinkedList();
        for (Disposable disposable : disposableArr) {
            Objects.requireNonNull(disposable, "Disposable item is null");
            this.a.add(disposable);
        }
    }

    public ListCompositeDisposable(Iterable<? extends Disposable> iterable) {
        Objects.requireNonNull(iterable, "resources is null");
        this.a = new LinkedList();
        for (Disposable disposable : iterable) {
            Objects.requireNonNull(disposable, "Disposable item is null");
            this.a.add(disposable);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    this.b = true;
                    List<Disposable> list = this.a;
                    this.a = null;
                    a(list);
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.b;
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean add(Disposable disposable) {
        Objects.requireNonNull(disposable, "d is null");
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    List list = this.a;
                    if (list == null) {
                        list = new LinkedList();
                        this.a = list;
                    }
                    list.add(disposable);
                    return true;
                }
            }
        }
        disposable.dispose();
        return false;
    }

    public boolean addAll(Disposable... disposableArr) {
        Objects.requireNonNull(disposableArr, "ds is null");
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    List list = this.a;
                    if (list == null) {
                        list = new LinkedList();
                        this.a = list;
                    }
                    for (Disposable disposable : disposableArr) {
                        Objects.requireNonNull(disposable, "d is null");
                        list.add(disposable);
                    }
                    return true;
                }
            }
        }
        for (Disposable disposable2 : disposableArr) {
            disposable2.dispose();
        }
        return false;
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean remove(Disposable disposable) {
        if (!delete(disposable)) {
            return false;
        }
        disposable.dispose();
        return true;
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean delete(Disposable disposable) {
        Objects.requireNonNull(disposable, "Disposable item is null");
        if (this.b) {
            return false;
        }
        synchronized (this) {
            if (this.b) {
                return false;
            }
            List<Disposable> list = this.a;
            if (list != null && list.remove(disposable)) {
                return true;
            }
            return false;
        }
    }

    public void clear() {
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    List<Disposable> list = this.a;
                    this.a = null;
                    a(list);
                }
            }
        }
    }

    void a(List<Disposable> list) {
        if (list != null) {
            ArrayList arrayList = null;
            for (Disposable disposable : list) {
                try {
                    disposable.dispose();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(th);
                }
            }
            if (arrayList == null) {
                return;
            }
            if (arrayList.size() == 1) {
                throw ExceptionHelper.wrapOrThrow((Throwable) arrayList.get(0));
            }
            throw new CompositeException(arrayList);
        }
    }
}
