package io.realm;

import io.realm.internal.OsList;
import io.realm.internal.OsObjectStore;
import io.realm.internal.RealmObjectProxy;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public final class n<T> extends i<T> {
    @Nullable
    private final String d;

    @Override // io.realm.i
    public boolean a() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(BaseRealm baseRealm, OsList osList, @Nullable Class<T> cls, @Nullable String str) {
        super(baseRealm, osList, cls);
        this.d = str;
    }

    @Override // io.realm.i
    public T b(int i) {
        return (T) this.a.a(this.c, this.d, this.b.getUncheckedRow(i));
    }

    @Override // io.realm.i
    protected void a(@Nullable Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("RealmList does not accept null values.");
        } else if (!(obj instanceof RealmModel)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.String", obj.getClass().getName()));
        }
    }

    private void a(int i) {
        int d = d();
        if (i < 0 || d < i) {
            throw new IndexOutOfBoundsException("Invalid index " + i + ", size is " + this.b.size());
        }
    }

    @Override // io.realm.i
    public void b(Object obj) {
        this.b.addRow(((RealmObjectProxy) a((n<T>) ((RealmModel) obj))).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    @Override // io.realm.i
    protected void c(int i) {
        throw new RuntimeException("Should not reach here.");
    }

    @Override // io.realm.i
    public void a(int i, Object obj) {
        a(i);
        this.b.insertRow(i, ((RealmObjectProxy) a((n<T>) ((RealmModel) obj))).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    @Override // io.realm.i
    protected void d(int i) {
        throw new RuntimeException("Should not reach here.");
    }

    @Override // io.realm.i
    protected void b(int i, Object obj) {
        this.b.setRow(i, ((RealmObjectProxy) a((n<T>) ((RealmModel) obj))).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    private <E extends RealmModel> E a(E e) {
        if (e instanceof RealmObjectProxy) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) e;
            if (realmObjectProxy instanceof DynamicRealmObject) {
                String str = this.d;
                if (realmObjectProxy.realmGet$proxyState().getRealm$realm() == this.a) {
                    String type = ((DynamicRealmObject) e).getType();
                    if (str.equals(type)) {
                        return e;
                    }
                    throw new IllegalArgumentException(String.format(Locale.US, "The object has a different type from list's. Type of the list is '%s', type of object is '%s'.", str, type));
                } else if (this.a.d == realmObjectProxy.realmGet$proxyState().getRealm$realm().d) {
                    throw new IllegalArgumentException("Cannot copy DynamicRealmObject between Realm instances.");
                } else {
                    throw new IllegalStateException("Cannot copy an object to a Realm instance created in another thread.");
                }
            } else if (realmObjectProxy.realmGet$proxyState().getRow$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(this.a.getPath())) {
                if (this.a == realmObjectProxy.realmGet$proxyState().getRealm$realm()) {
                    return e;
                }
                throw new IllegalArgumentException("Cannot copy an object from another Realm instance.");
            }
        }
        Realm realm = (Realm) this.a;
        if (OsObjectStore.getPrimaryKeyForObject(realm.c(), realm.getConfiguration().getSchemaMediator().getSimpleClassName(e.getClass())) != null) {
            return (E) realm.copyToRealmOrUpdate((Realm) e, new ImportFlag[0]);
        }
        return (E) realm.copyToRealm((Realm) e, new ImportFlag[0]);
    }
}
