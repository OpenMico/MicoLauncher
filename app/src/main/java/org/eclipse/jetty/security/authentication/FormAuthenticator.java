package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class FormAuthenticator extends LoginAuthenticator {
    private static final Logger LOG = Log.getLogger(FormAuthenticator.class);
    public static final String __FORM_DISPATCH = "org.eclipse.jetty.security.dispatch";
    public static final String __FORM_ERROR_PAGE = "org.eclipse.jetty.security.form_error_page";
    public static final String __FORM_LOGIN_PAGE = "org.eclipse.jetty.security.form_login_page";
    public static final String __J_PASSWORD = "j_password";
    public static final String __J_POST = "org.eclipse.jetty.security.form_POST";
    public static final String __J_SECURITY_CHECK = "/j_security_check";
    public static final String __J_URI = "org.eclipse.jetty.security.form_URI";
    public static final String __J_USERNAME = "j_username";
    private boolean _alwaysSaveUri;
    private boolean _dispatch;
    private String _formErrorPage;
    private String _formErrorPath;
    private String _formLoginPage;
    private String _formLoginPath;

    @Override // org.eclipse.jetty.security.Authenticator
    public String getAuthMethod() {
        return "FORM";
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException {
        return true;
    }

    public FormAuthenticator() {
    }

    public FormAuthenticator(String str, String str2, boolean z) {
        this();
        if (str != null) {
            setLoginPage(str);
        }
        if (str2 != null) {
            setErrorPage(str2);
        }
        this._dispatch = z;
    }

    public void setAlwaysSaveUri(boolean z) {
        this._alwaysSaveUri = z;
    }

    public boolean getAlwaysSaveUri() {
        return this._alwaysSaveUri;
    }

    @Override // org.eclipse.jetty.security.authentication.LoginAuthenticator, org.eclipse.jetty.security.Authenticator
    public void setConfiguration(Authenticator.AuthConfiguration authConfiguration) {
        super.setConfiguration(authConfiguration);
        String initParameter = authConfiguration.getInitParameter(__FORM_LOGIN_PAGE);
        if (initParameter != null) {
            setLoginPage(initParameter);
        }
        String initParameter2 = authConfiguration.getInitParameter(__FORM_ERROR_PAGE);
        if (initParameter2 != null) {
            setErrorPage(initParameter2);
        }
        String initParameter3 = authConfiguration.getInitParameter(__FORM_DISPATCH);
        this._dispatch = initParameter3 == null ? this._dispatch : Boolean.valueOf(initParameter3).booleanValue();
    }

    private void setLoginPage(String str) {
        if (!str.startsWith("/")) {
            LOG.warn("form-login-page must start with /", new Object[0]);
            str = "/" + str;
        }
        this._formLoginPage = str;
        this._formLoginPath = str;
        if (this._formLoginPath.indexOf(63) > 0) {
            String str2 = this._formLoginPath;
            this._formLoginPath = str2.substring(0, str2.indexOf(63));
        }
    }

    private void setErrorPage(String str) {
        if (str == null || str.trim().length() == 0) {
            this._formErrorPath = null;
            this._formErrorPage = null;
            return;
        }
        if (!str.startsWith("/")) {
            LOG.warn("form-error-page must start with /", new Object[0]);
            str = "/" + str;
        }
        this._formErrorPage = str;
        this._formErrorPath = str;
        if (this._formErrorPath.indexOf(63) > 0) {
            String str2 = this._formErrorPath;
            this._formErrorPath = str2.substring(0, str2.indexOf(63));
        }
    }

    @Override // org.eclipse.jetty.security.authentication.LoginAuthenticator
    public UserIdentity login(String str, Object obj, ServletRequest servletRequest) {
        UserIdentity login = super.login(str, obj, servletRequest);
        if (login != null) {
            ((HttpServletRequest) servletRequest).getSession(true).setAttribute(SessionAuthentication.__J_AUTHENTICATED, new SessionAuthentication(getAuthMethod(), login, obj));
        }
        return login;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z) throws ServerAuthException {
        String str;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI == null) {
            requestURI = "/";
        }
        if (!z && !isJSecurityCheck(requestURI)) {
            return new DeferredAuthentication(this);
        }
        if (isLoginOrErrorPage(URIUtil.addPaths(httpServletRequest.getServletPath(), httpServletRequest.getPathInfo())) && !DeferredAuthentication.isDeferred(httpServletResponse)) {
            return new DeferredAuthentication(this);
        }
        HttpSession session = httpServletRequest.getSession(true);
        try {
            if (isJSecurityCheck(requestURI)) {
                String parameter = httpServletRequest.getParameter(__J_USERNAME);
                UserIdentity login = login(parameter, httpServletRequest.getParameter(__J_PASSWORD), httpServletRequest);
                HttpSession session2 = httpServletRequest.getSession(true);
                if (login != null) {
                    synchronized (session2) {
                        str = (String) session2.getAttribute(__J_URI);
                        if (str == null || str.length() == 0) {
                            str = httpServletRequest.getContextPath();
                            if (str.length() == 0) {
                                str = "/";
                            }
                        }
                    }
                    httpServletResponse.setContentLength(0);
                    httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(str));
                    return new FormAuthentication(getAuthMethod(), login);
                }
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Form authentication FAILED for " + StringUtil.printable(parameter), new Object[0]);
                }
                if (this._formErrorPage == null) {
                    if (httpServletResponse != null) {
                        httpServletResponse.sendError(403);
                    }
                } else if (this._dispatch) {
                    RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(this._formErrorPage);
                    httpServletResponse.setHeader("Cache-Control", "No-cache");
                    httpServletResponse.setDateHeader("Expires", 1L);
                    requestDispatcher.forward(new FormRequest(httpServletRequest), new FormResponse(httpServletResponse));
                } else {
                    httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(URIUtil.addPaths(httpServletRequest.getContextPath(), this._formErrorPage)));
                }
                return Authentication.SEND_FAILURE;
            }
            Authentication authentication = (Authentication) session.getAttribute(SessionAuthentication.__J_AUTHENTICATED);
            if (authentication != null) {
                if (!(authentication instanceof Authentication.User) || this._loginService == null || this._loginService.validate(((Authentication.User) authentication).getUserIdentity())) {
                    String str2 = (String) session.getAttribute(__J_URI);
                    if (str2 != null) {
                        MultiMap<String> multiMap = (MultiMap) session.getAttribute(__J_POST);
                        if (multiMap != null) {
                            StringBuffer requestURL = httpServletRequest.getRequestURL();
                            if (httpServletRequest.getQueryString() != null) {
                                requestURL.append("?");
                                requestURL.append(httpServletRequest.getQueryString());
                            }
                            if (str2.equals(requestURL.toString())) {
                                session.removeAttribute(__J_POST);
                                Request request = servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest();
                                request.setMethod("POST");
                                request.setParameters(multiMap);
                            }
                        } else {
                            session.removeAttribute(__J_URI);
                        }
                    }
                    return authentication;
                }
                session.removeAttribute(SessionAuthentication.__J_AUTHENTICATED);
            }
            if (DeferredAuthentication.isDeferred(httpServletResponse)) {
                LOG.debug("auth deferred {}", session.getId());
                return Authentication.UNAUTHENTICATED;
            }
            synchronized (session) {
                if (session.getAttribute(__J_URI) == null || this._alwaysSaveUri) {
                    StringBuffer requestURL2 = httpServletRequest.getRequestURL();
                    if (httpServletRequest.getQueryString() != null) {
                        requestURL2.append("?");
                        requestURL2.append(httpServletRequest.getQueryString());
                    }
                    session.setAttribute(__J_URI, requestURL2.toString());
                    if ("application/x-www-form-urlencoded".equalsIgnoreCase(servletRequest.getContentType()) && "POST".equals(httpServletRequest.getMethod())) {
                        Request request2 = servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest();
                        request2.extractParameters();
                        session.setAttribute(__J_POST, new MultiMap((MultiMap) request2.getParameters()));
                    }
                }
            }
            if (this._dispatch) {
                RequestDispatcher requestDispatcher2 = httpServletRequest.getRequestDispatcher(this._formLoginPage);
                httpServletResponse.setHeader("Cache-Control", "No-cache");
                httpServletResponse.setDateHeader("Expires", 1L);
                requestDispatcher2.forward(new FormRequest(httpServletRequest), new FormResponse(httpServletResponse));
            } else {
                httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(URIUtil.addPaths(httpServletRequest.getContextPath(), this._formLoginPage)));
            }
            return Authentication.SEND_CONTINUE;
        } catch (IOException e) {
            throw new ServerAuthException(e);
        } catch (ServletException e2) {
            throw new ServerAuthException(e2);
        }
    }

    public boolean isJSecurityCheck(String str) {
        int indexOf = str.indexOf(__J_SECURITY_CHECK);
        if (indexOf < 0) {
            return false;
        }
        int i = indexOf + 17;
        if (i == str.length()) {
            return true;
        }
        char charAt = str.charAt(i);
        return charAt == ';' || charAt == '#' || charAt == '/' || charAt == '?';
    }

    public boolean isLoginOrErrorPage(String str) {
        return str != null && (str.equals(this._formErrorPath) || str.equals(this._formLoginPath));
    }

    /* loaded from: classes5.dex */
    protected static class FormRequest extends HttpServletRequestWrapper {
        public FormRequest(HttpServletRequest httpServletRequest) {
            super(httpServletRequest);
        }

        @Override // javax.servlet.http.HttpServletRequestWrapper, javax.servlet.http.HttpServletRequest
        public long getDateHeader(String str) {
            if (str.toLowerCase(Locale.ENGLISH).startsWith("if-")) {
                return -1L;
            }
            return super.getDateHeader(str);
        }

        @Override // javax.servlet.http.HttpServletRequestWrapper, javax.servlet.http.HttpServletRequest
        public String getHeader(String str) {
            if (str.toLowerCase(Locale.ENGLISH).startsWith("if-")) {
                return null;
            }
            return super.getHeader(str);
        }

        @Override // javax.servlet.http.HttpServletRequestWrapper, javax.servlet.http.HttpServletRequest
        public Enumeration getHeaderNames() {
            return Collections.enumeration(Collections.list(super.getHeaderNames()));
        }

        @Override // javax.servlet.http.HttpServletRequestWrapper, javax.servlet.http.HttpServletRequest
        public Enumeration getHeaders(String str) {
            if (str.toLowerCase(Locale.ENGLISH).startsWith("if-")) {
                return Collections.enumeration(Collections.EMPTY_LIST);
            }
            return super.getHeaders(str);
        }
    }

    /* loaded from: classes5.dex */
    protected static class FormResponse extends HttpServletResponseWrapper {
        public FormResponse(HttpServletResponse httpServletResponse) {
            super(httpServletResponse);
        }

        @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
        public void addDateHeader(String str, long j) {
            if (notIgnored(str)) {
                super.addDateHeader(str, j);
            }
        }

        @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
        public void addHeader(String str, String str2) {
            if (notIgnored(str)) {
                super.addHeader(str, str2);
            }
        }

        @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
        public void setDateHeader(String str, long j) {
            if (notIgnored(str)) {
                super.setDateHeader(str, j);
            }
        }

        @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
        public void setHeader(String str, String str2) {
            if (notIgnored(str)) {
                super.setHeader(str, str2);
            }
        }

        private boolean notIgnored(String str) {
            return !"Cache-Control".equalsIgnoreCase(str) && !"Pragma".equalsIgnoreCase(str) && !"ETag".equalsIgnoreCase(str) && !"Expires".equalsIgnoreCase(str) && !"Last-Modified".equalsIgnoreCase(str) && !"Age".equalsIgnoreCase(str);
        }
    }

    /* loaded from: classes5.dex */
    public static class FormAuthentication extends UserAuthentication implements Authentication.ResponseSent {
        public FormAuthentication(String str, UserIdentity userIdentity) {
            super(str, userIdentity);
        }

        @Override // org.eclipse.jetty.security.UserAuthentication
        public String toString() {
            return "Form" + super.toString();
        }
    }
}
