package io.realm;

import io.realm.RealmModel;
import io.realm.internal.ObserverPairList;
import io.realm.internal.OsObject;
import io.realm.internal.PendingRow;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.UncheckedRow;
import java.util.List;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public final class ProxyState<E extends RealmModel> implements PendingRow.FrontEnd {
    private static a i = new a();
    private E a;
    private Row c;
    private OsObject d;
    private BaseRealm e;
    private boolean f;
    private List<String> g;
    private boolean b = true;
    private ObserverPairList<OsObject.ObjectObserverPair> h = new ObserverPairList<>();

    /* loaded from: classes5.dex */
    public static class b<T extends RealmModel> implements RealmObjectChangeListener<T> {
        private final RealmChangeListener<T> a;

        public b(RealmChangeListener<T> realmChangeListener) {
            if (realmChangeListener != null) {
                this.a = realmChangeListener;
                return;
            }
            throw new IllegalArgumentException("Listener should not be null");
        }

        @Override // io.realm.RealmObjectChangeListener
        public void onChange(T t, @Nullable ObjectChangeSet objectChangeSet) {
            this.a.onChange(t);
        }

        public boolean equals(Object obj) {
            return (obj instanceof b) && this.a == ((b) obj).a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    /* loaded from: classes5.dex */
    public static class a implements ObserverPairList.Callback<OsObject.ObjectObserverPair> {
        private a() {
        }

        /* renamed from: a */
        public void onCalled(OsObject.ObjectObserverPair objectObserverPair, Object obj) {
            objectObserverPair.onChange((RealmModel) obj, null);
        }
    }

    public ProxyState() {
    }

    public ProxyState(E e) {
        this.a = e;
    }

    public BaseRealm getRealm$realm() {
        return this.e;
    }

    public void setRealm$realm(BaseRealm baseRealm) {
        this.e = baseRealm;
    }

    public Row getRow$realm() {
        return this.c;
    }

    public void setRow$realm(Row row) {
        this.c = row;
    }

    public boolean getAcceptDefaultValue$realm() {
        return this.f;
    }

    public void setAcceptDefaultValue$realm(boolean z) {
        this.f = z;
    }

    public List<String> getExcludeFields$realm() {
        return this.g;
    }

    public void setExcludeFields$realm(List<String> list) {
        this.g = list;
    }

    private void a() {
        this.h.foreach(i);
    }

    public void addChangeListener(RealmObjectChangeListener<E> realmObjectChangeListener) {
        Row row = this.c;
        if (row instanceof PendingRow) {
            this.h.add(new OsObject.ObjectObserverPair(this.a, realmObjectChangeListener));
        } else if (row instanceof UncheckedRow) {
            b();
            OsObject osObject = this.d;
            if (osObject != null) {
                osObject.addListener(this.a, realmObjectChangeListener);
            }
        }
    }

    public void removeChangeListener(RealmObjectChangeListener<E> realmObjectChangeListener) {
        OsObject osObject = this.d;
        if (osObject != null) {
            osObject.removeListener(this.a, realmObjectChangeListener);
        } else {
            this.h.remove(this.a, realmObjectChangeListener);
        }
    }

    public void removeAllChangeListeners() {
        OsObject osObject = this.d;
        if (osObject != null) {
            osObject.removeListener(this.a);
        } else {
            this.h.clear();
        }
    }

    public boolean isUnderConstruction() {
        return this.b;
    }

    public void setConstructionFinished() {
        this.b = false;
        this.g = null;
    }

    private void b() {
        if (this.e.sharedRealm != null && !this.e.sharedRealm.isClosed() && this.c.isValid() && this.d == null) {
            this.d = new OsObject(this.e.sharedRealm, (UncheckedRow) this.c);
            this.d.setObserverPairs(this.h);
            this.h = null;
        }
    }

    public boolean isLoaded() {
        return this.c.isLoaded();
    }

    public void load() {
        Row row = this.c;
        if (row instanceof PendingRow) {
            ((PendingRow) row).executeQuery();
        }
    }

    @Override // io.realm.internal.PendingRow.FrontEnd
    public void onQueryFinished(Row row) {
        this.c = row;
        a();
        if (row.isValid()) {
            b();
        }
    }

    public void checkValidObject(RealmModel realmModel) {
        if (!RealmObject.isValid(realmModel) || !RealmObject.isManaged(realmModel)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        } else if (((RealmObjectProxy) realmModel).realmGet$proxyState().getRealm$realm() != getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
    }
}
