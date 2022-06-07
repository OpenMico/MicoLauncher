package io.realm.internal;

import io.realm.RealmChangeListener;
import io.realm.internal.ObserverPairList;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

@Keep
/* loaded from: classes5.dex */
public abstract class RealmNotifier implements Closeable {
    private OsSharedRealm sharedRealm;
    private ObserverPairList<a> realmObserverPairs = new ObserverPairList<>();
    private final ObserverPairList.Callback<a> onChangeCallBack = new ObserverPairList.Callback<a>() { // from class: io.realm.internal.RealmNotifier.1
        /* renamed from: a */
        public void onCalled(a aVar, Object obj) {
            if (RealmNotifier.this.sharedRealm != null && !RealmNotifier.this.sharedRealm.isClosed()) {
                aVar.a(obj);
            }
        }
    };
    private List<Runnable> transactionCallbacks = new ArrayList();
    private List<Runnable> startSendingNotificationsCallbacks = new ArrayList();
    private List<Runnable> finishedSendingNotificationsCallbacks = new ArrayList();

    public abstract boolean post(Runnable runnable);

    /* loaded from: classes5.dex */
    public static class a<T> extends ObserverPairList.ObserverPair<T, RealmChangeListener<T>> {
        public a(T t, RealmChangeListener<T> realmChangeListener) {
            super(t, realmChangeListener);
        }

        public void a(T t) {
            if (t != null) {
                ((RealmChangeListener) this.listener).onChange(t);
            }
        }
    }

    public RealmNotifier(@Nullable OsSharedRealm osSharedRealm) {
        this.sharedRealm = osSharedRealm;
    }

    void didChange() {
        this.realmObserverPairs.foreach(this.onChangeCallBack);
        if (!this.transactionCallbacks.isEmpty()) {
            List<Runnable> list = this.transactionCallbacks;
            this.transactionCallbacks = new ArrayList();
            for (Runnable runnable : list) {
                runnable.run();
            }
        }
    }

    void beforeNotify() {
        this.sharedRealm.invalidateIterators();
    }

    void willSendNotifications() {
        for (int i = 0; i < this.startSendingNotificationsCallbacks.size(); i++) {
            this.startSendingNotificationsCallbacks.get(i).run();
        }
    }

    void didSendNotifications() {
        for (int i = 0; i < this.startSendingNotificationsCallbacks.size(); i++) {
            this.finishedSendingNotificationsCallbacks.get(i).run();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        removeAllChangeListeners();
        this.startSendingNotificationsCallbacks.clear();
        this.finishedSendingNotificationsCallbacks.clear();
    }

    public <T> void addChangeListener(T t, RealmChangeListener<T> realmChangeListener) {
        this.realmObserverPairs.add(new a(t, realmChangeListener));
    }

    public <E> void removeChangeListener(E e, RealmChangeListener<E> realmChangeListener) {
        this.realmObserverPairs.remove(e, realmChangeListener);
    }

    public <E> void removeChangeListeners(E e) {
        this.realmObserverPairs.a(e);
    }

    private void removeAllChangeListeners() {
        this.realmObserverPairs.clear();
    }

    public void addTransactionCallback(Runnable runnable) {
        this.transactionCallbacks.add(runnable);
    }

    public int getListenersListSize() {
        return this.realmObserverPairs.size();
    }

    public void addBeginSendingNotificationsCallback(Runnable runnable) {
        this.startSendingNotificationsCallbacks.add(runnable);
    }

    public void addFinishedSendingNotificationsCallback(Runnable runnable) {
        this.finishedSendingNotificationsCallbacks.add(runnable);
    }
}
