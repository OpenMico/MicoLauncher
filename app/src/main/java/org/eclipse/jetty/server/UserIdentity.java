package org.eclipse.jetty.server;

import java.security.Principal;
import java.util.Map;
import javax.security.auth.Subject;

/* loaded from: classes5.dex */
public interface UserIdentity {
    public static final UserIdentity UNAUTHENTICATED_IDENTITY = new UnauthenticatedUserIdentity() { // from class: org.eclipse.jetty.server.UserIdentity.1
        @Override // org.eclipse.jetty.server.UserIdentity
        public Subject getSubject() {
            return null;
        }

        @Override // org.eclipse.jetty.server.UserIdentity
        public Principal getUserPrincipal() {
            return null;
        }

        @Override // org.eclipse.jetty.server.UserIdentity
        public boolean isUserInRole(String str, Scope scope) {
            return false;
        }

        public String toString() {
            return "UNAUTHENTICATED";
        }
    };

    /* loaded from: classes5.dex */
    public interface Scope {
        String getContextPath();

        String getName();

        Map<String, String> getRoleRefMap();
    }

    /* loaded from: classes5.dex */
    public interface UnauthenticatedUserIdentity extends UserIdentity {
    }

    Subject getSubject();

    Principal getUserPrincipal();

    boolean isUserInRole(String str, Scope scope);
}
