package org.eclipse.jetty.security;

import com.xiaomi.mipush.sdk.Constants;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;

/* loaded from: classes5.dex */
public class UserAuthentication implements Authentication.User {
    private final String _method;
    private final UserIdentity _userIdentity;

    public UserAuthentication(String str, UserIdentity userIdentity) {
        this._method = str;
        this._userIdentity = userIdentity;
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public String getAuthMethod() {
        return this._method;
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public UserIdentity getUserIdentity() {
        return this._userIdentity;
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public boolean isUserInRole(UserIdentity.Scope scope, String str) {
        return this._userIdentity.isUserInRole(str, scope);
    }

    public String toString() {
        return "{User," + getAuthMethod() + Constants.ACCEPT_TIME_SEPARATOR_SP + this._userIdentity + "}";
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public void logout() {
        SecurityHandler currentSecurityHandler = SecurityHandler.getCurrentSecurityHandler();
        if (currentSecurityHandler != null) {
            currentSecurityHandler.logout(this);
        }
    }
}
