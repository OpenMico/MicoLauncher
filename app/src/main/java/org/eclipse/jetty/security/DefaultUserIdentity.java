package org.eclipse.jetty.security;

import java.security.Principal;
import javax.security.auth.Subject;
import org.eclipse.jetty.server.UserIdentity;

/* loaded from: classes5.dex */
public class DefaultUserIdentity implements UserIdentity {
    private final String[] _roles;
    private final Subject _subject;
    private final Principal _userPrincipal;

    public DefaultUserIdentity(Subject subject, Principal principal, String[] strArr) {
        this._subject = subject;
        this._userPrincipal = principal;
        this._roles = strArr;
    }

    @Override // org.eclipse.jetty.server.UserIdentity
    public Subject getSubject() {
        return this._subject;
    }

    @Override // org.eclipse.jetty.server.UserIdentity
    public Principal getUserPrincipal() {
        return this._userPrincipal;
    }

    @Override // org.eclipse.jetty.server.UserIdentity
    public boolean isUserInRole(String str, UserIdentity.Scope scope) {
        if (!(scope == null || scope.getRoleRefMap() == null)) {
            str = scope.getRoleRefMap().get(str);
        }
        for (String str2 : this._roles) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return DefaultUserIdentity.class.getSimpleName() + "('" + this._userPrincipal + "')";
    }
}
