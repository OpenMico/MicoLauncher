package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.OpenHashSet;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CompositeDisposable implements Disposable, DisposableContainer {
    OpenHashSet<Disposable> a;
    volatile boolean b;

    public CompositeDisposable() {
    }

    public CompositeDisposable(@NonNull Disposable... disposableArr) {
        Objects.requireNonNull(disposableArr, "disposables is null");
        this.a = new OpenHashSet<>(disposableArr.length + 1);
        for (Disposable disposable : disposableArr) {
            Objects.requireNonNull(disposable, "A Disposable in the disposables array is null");
            this.a.add(disposable);
        }
    }

    public CompositeDisposable(@NonNull Iterable<? extends Disposable> iterable) {
        Objects.requireNonNull(iterable, "disposables is null");
        this.a = new OpenHashSet<>();
        for (Disposable disposable : iterable) {
            Objects.requireNonNull(disposable, "A Disposable item in the disposables sequence is null");
            this.a.add(disposable);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    this.b = true;
                    OpenHashSet<Disposable> openHashSet = this.a;
                    this.a = null;
                    a(openHashSet);
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.b;
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean add(@NonNull Disposable disposable) {
        Objects.requireNonNull(disposable, "disposable is null");
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    OpenHashSet<Disposable> openHashSet = this.a;
                    if (openHashSet == null) {
                        openHashSet = new OpenHashSet<>();
                        this.a = openHashSet;
                    }
                    openHashSet.add(disposable);
                    return true;
                }
            }
        }
        disposable.dispose();
        return false;
    }

    public boolean addAll(@NonNull Disposable... disposableArr) {
        Objects.requireNonNull(disposableArr, "disposables is null");
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    OpenHashSet<Disposable> openHashSet = this.a;
                    if (openHashSet == null) {
                        openHashSet = new OpenHashSet<>(disposableArr.length + 1);
                        this.a = openHashSet;
                    }
                    for (Disposable disposable : disposableArr) {
                        Objects.requireNonNull(disposable, "A Disposable in the disposables array is null");
                        openHashSet.add(disposable);
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
    public boolean remove(@NonNull Disposable disposable) {
        if (!delete(disposable)) {
            return false;
        }
        disposable.dispose();
        return true;
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean delete(@NonNull Disposable disposable) {
        Objects.requireNonNull(disposable, "disposable is null");
        if (this.b) {
            return false;
        }
        synchronized (this) {
            if (this.b) {
                return false;
            }
            OpenHashSet<Disposable> openHashSet = this.a;
            if (openHashSet != null && openHashSet.remove(disposable)) {
                return true;
            }
            return false;
        }
    }

    public void clear() {
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    OpenHashSet<Disposable> openHashSet = this.a;
                    this.a = null;
                    a(openHashSet);
                }
            }
        }
    }

    public int size() {
        int i = 0;
        if (this.b) {
            return 0;
        }
        synchronized (this) {
            if (this.b) {
                return 0;
            }
            OpenHashSet<Disposable> openHashSet = this.a;
            if (openHashSet != null) {
                i = openHashSet.size();
            }
            return i;
        }
    }

    void a(@Nullable OpenHashSet<Disposable> openHashSet) {
        if (openHashSet != null) {
            ArrayList arrayList = null;
            Object[] keys = openHashSet.keys();
            for (Object obj : keys) {
                if (obj instanceof Disposable) {
                    try {
                        ((Disposable) obj).dispose();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(th);
                    }
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
