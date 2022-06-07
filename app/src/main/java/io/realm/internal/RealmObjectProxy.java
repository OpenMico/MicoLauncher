package io.realm.internal;

import io.realm.ProxyState;
import io.realm.RealmModel;

/* loaded from: classes5.dex */
public interface RealmObjectProxy extends RealmModel {
    void realm$injectObjectContext();

    ProxyState realmGet$proxyState();

    /* loaded from: classes5.dex */
    public static class CacheData<E extends RealmModel> {
        public int minDepth;
        public final E object;

        public CacheData(int i, E e) {
            this.minDepth = i;
            this.object = e;
        }
    }
}
