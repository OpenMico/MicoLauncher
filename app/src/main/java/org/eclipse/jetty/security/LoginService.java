package org.eclipse.jetty.security;

import org.eclipse.jetty.server.UserIdentity;

/* loaded from: classes5.dex */
public interface LoginService {
    IdentityService getIdentityService();

    String getName();

    UserIdentity login(String str, Object obj);

    void logout(UserIdentity userIdentity);

    void setIdentityService(IdentityService identityService);

    boolean validate(UserIdentity userIdentity);
}
