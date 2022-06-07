package org.eclipse.jetty.security;

import java.security.Principal;
import javax.security.auth.Subject;
import org.eclipse.jetty.server.UserIdentity;

/* loaded from: classes5.dex */
public class DefaultIdentityService implements IdentityService {
    @Override // org.eclipse.jetty.security.IdentityService
    public Object associate(UserIdentity userIdentity) {
        return null;
    }

    @Override // org.eclipse.jetty.security.IdentityService
    public void disassociate(Object obj) {
    }

    @Override // org.eclipse.jetty.security.IdentityService
    public UserIdentity getSystemUserIdentity() {
        return null;
    }

    @Override // org.eclipse.jetty.security.IdentityService
    public Object setRunAs(UserIdentity userIdentity, RunAsToken runAsToken) {
        return runAsToken;
    }

    @Override // org.eclipse.jetty.security.IdentityService
    public void unsetRunAs(Object obj) {
    }

    @Override // org.eclipse.jetty.security.IdentityService
    public RunAsToken newRunAsToken(String str) {
        return new RoleRunAsToken(str);
    }

    @Override // org.eclipse.jetty.security.IdentityService
    public UserIdentity newUserIdentity(Subject subject, Principal principal, String[] strArr) {
        return new DefaultUserIdentity(subject, principal, strArr);
    }
}
