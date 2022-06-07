package io.realm.internal;

import io.realm.internal.ObserverPairList.ObserverPair;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes5.dex */
public class ObserverPairList<T extends ObserverPair> {
    private List<T> a = new CopyOnWriteArrayList();
    private boolean b = false;

    /* loaded from: classes5.dex */
    public interface Callback<T extends ObserverPair> {
        void onCalled(T t, Object obj);
    }

    /* loaded from: classes5.dex */
    public static abstract class ObserverPair<T, S> {
        final WeakReference<T> a;
        boolean b = false;
        protected final S listener;

        public ObserverPair(T t, S s) {
            this.listener = s;
            this.a = new WeakReference<>(t);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ObserverPair)) {
                return false;
            }
            ObserverPair observerPair = (ObserverPair) obj;
            return this.listener.equals(observerPair.listener) && this.a.get() == observerPair.a.get();
        }

        public int hashCode() {
            T t = this.a.get();
            int i = 0;
            int hashCode = (527 + (t != null ? t.hashCode() : 0)) * 31;
            S s = this.listener;
            if (s != null) {
                i = s.hashCode();
            }
            return hashCode + i;
        }
    }

    public void foreach(Callback<T> callback) {
        for (T t : this.a) {
            if (!this.b) {
                Object obj = t.a.get();
                if (obj == null) {
                    this.a.remove(t);
                } else if (!t.b) {
                    callback.onCalled(t, obj);
                }
            } else {
                return;
            }
        }
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public void clear() {
        this.b = true;
        this.a.clear();
    }

    public void add(T t) {
        if (!this.a.contains(t)) {
            this.a.add(t);
            t.b = false;
        }
        if (this.b) {
            this.b = false;
        }
    }

    public <S, U> void remove(S s, U u) {
        for (T t : this.a) {
            if (s == t.a.get() && u.equals(t.listener)) {
                t.b = true;
                this.a.remove(t);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Object obj) {
        for (T t : this.a) {
            Object obj2 = t.a.get();
            if (obj2 == null || obj2 == obj) {
                t.b = true;
                this.a.remove(t);
            }
        }
    }

    public int size() {
        return this.a.size();
    }
}
