package org.eclipse.jetty.client.security;

import java.io.IOException;
import org.eclipse.jetty.client.HttpDestination;

/* loaded from: classes5.dex */
public class SimpleRealmResolver implements RealmResolver {
    private Realm _realm;

    public SimpleRealmResolver(Realm realm) {
        this._realm = realm;
    }

    @Override // org.eclipse.jetty.client.security.RealmResolver
    public Realm getRealm(String str, HttpDestination httpDestination, String str2) throws IOException {
        return this._realm;
    }
}
