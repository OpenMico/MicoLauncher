package org.eclipse.jetty.security;

import java.security.Principal;
import javax.security.auth.Subject;
import org.eclipse.jetty.server.UserIdentity;

/* loaded from: classes5.dex */
public interface IdentityService {
    public static final String[] NO_ROLES = new String[0];

    Object associate(UserIdentity userIdentity);

    void disassociate(Object obj);

    UserIdentity getSystemUserIdentity();

    RunAsToken newRunAsToken(String str);

    UserIdentity newUserIdentity(Subject subject, Principal principal, String[] strArr);

    Object setRunAs(UserIdentity userIdentity, RunAsToken runAsToken);

    void unsetRunAs(Object obj);
}
