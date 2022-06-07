package org.eclipse.jetty.client.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.client.HttpDestination;

/* loaded from: classes5.dex */
public class HashRealmResolver implements RealmResolver {
    private Map<String, Realm> _realmMap;

    public void addSecurityRealm(Realm realm) {
        if (this._realmMap == null) {
            this._realmMap = new HashMap();
        }
        this._realmMap.put(realm.getId(), realm);
    }

    @Override // org.eclipse.jetty.client.security.RealmResolver
    public Realm getRealm(String str, HttpDestination httpDestination, String str2) throws IOException {
        return this._realmMap.get(str);
    }
}
