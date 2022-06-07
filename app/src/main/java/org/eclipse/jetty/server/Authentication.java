package org.eclipse.jetty.server;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.UserIdentity;

/* loaded from: classes5.dex */
public interface Authentication {
    public static final Authentication UNAUTHENTICATED = new Authentication() { // from class: org.eclipse.jetty.server.Authentication.1
        public String toString() {
            return "UNAUTHENTICATED";
        }
    };
    public static final Authentication NOT_CHECKED = new Authentication() { // from class: org.eclipse.jetty.server.Authentication.2
        public String toString() {
            return "NOT CHECKED";
        }
    };
    public static final Authentication SEND_CONTINUE = new Challenge() { // from class: org.eclipse.jetty.server.Authentication.3
        public String toString() {
            return "CHALLENGE";
        }
    };
    public static final Authentication SEND_FAILURE = new Failure() { // from class: org.eclipse.jetty.server.Authentication.4
        public String toString() {
            return "FAILURE";
        }
    };
    public static final Authentication SEND_SUCCESS = new SendSuccess() { // from class: org.eclipse.jetty.server.Authentication.5
        public String toString() {
            return "SEND_SUCCESS";
        }
    };

    /* loaded from: classes5.dex */
    public interface Challenge extends ResponseSent {
    }

    /* loaded from: classes5.dex */
    public interface Deferred extends Authentication {
        Authentication authenticate(ServletRequest servletRequest);

        Authentication authenticate(ServletRequest servletRequest, ServletResponse servletResponse);

        Authentication login(String str, Object obj, ServletRequest servletRequest);
    }

    /* loaded from: classes5.dex */
    public interface Failure extends ResponseSent {
    }

    /* loaded from: classes5.dex */
    public interface ResponseSent extends Authentication {
    }

    /* loaded from: classes5.dex */
    public interface SendSuccess extends ResponseSent {
    }

    /* loaded from: classes5.dex */
    public interface User extends Authentication {
        String getAuthMethod();

        UserIdentity getUserIdentity();

        boolean isUserInRole(UserIdentity.Scope scope, String str);

        void logout();
    }

    /* loaded from: classes5.dex */
    public interface Wrapped extends Authentication {
        HttpServletRequest getHttpServletRequest();

        HttpServletResponse getHttpServletResponse();
    }
}
