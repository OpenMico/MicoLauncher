package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;

/* compiled from: SerializedSubject.java */
/* loaded from: classes5.dex */
final class a<T> extends Subject<T> implements AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
    final Subject<T> a;
    boolean b;
    AppendOnlyLinkedArrayList<Object> c;
    volatile boolean d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Subject<T> subject) {
        this.a = subject;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(observer);
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        boolean z = true;
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    if (this.b) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.c;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.c = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(NotificationLite.disposable(disposable));
                        return;
                    }
                    this.b = true;
                    z = false;
                }
            }
        }
        if (z) {
            disposable.dispose();
            return;
        }
        this.a.onSubscribe(disposable);
        a();
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    if (this.b) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.c;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.c = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(NotificationLite.next(t));
                        return;
                    }
                    this.b = true;
                    this.a.onNext(t);
                    a();
                }
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        boolean z;
        if (this.d) {
            RxJavaPlugins.onError(th);
            return;
        }
        synchronized (this) {
            if (this.d) {
                z = true;
            } else {
                this.d = true;
                if (this.b) {
                    AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.c;
                    if (appendOnlyLinkedArrayList == null) {
                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                        this.c = appendOnlyLinkedArrayList;
                    }
                    appendOnlyLinkedArrayList.setFirst(NotificationLite.error(th));
                    return;
                }
                z = false;
                this.b = true;
            }
            if (z) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    this.d = true;
                    if (this.b) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.c;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.c = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(NotificationLite.complete());
                        return;
                    }
                    this.b = true;
                    this.a.onComplete();
                }
            }
        }
    }

    void a() {
        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
        while (true) {
            synchronized (this) {
                appendOnlyLinkedArrayList = this.c;
                if (appendOnlyLinkedArrayList == null) {
                    this.b = false;
                    return;
                }
                this.c = null;
            }
            appendOnlyLinkedArrayList.forEachWhile(this);
        }
    }

    @Override // io.reactivex.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.functions.Predicate
    public boolean test(Object obj) {
        return NotificationLite.acceptFull(obj, this.a);
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasObservers() {
        return this.a.hasObservers();
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasThrowable() {
        return this.a.hasThrowable();
    }

    @Override // io.reactivex.subjects.Subject
    @Nullable
    public Throwable getThrowable() {
        return this.a.getThrowable();
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasComplete() {
        return this.a.hasComplete();
    }
}
