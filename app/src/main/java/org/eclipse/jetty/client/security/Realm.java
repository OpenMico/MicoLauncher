package org.eclipse.jetty.client.security;

/* loaded from: classes5.dex */
public interface Realm {
    String getCredentials();

    String getId();

    String getPrincipal();
}
