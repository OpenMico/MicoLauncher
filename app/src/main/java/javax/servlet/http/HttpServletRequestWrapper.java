package javax.servlet.http;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestWrapper;

/* loaded from: classes5.dex */
public class HttpServletRequestWrapper extends ServletRequestWrapper implements HttpServletRequest {
    public HttpServletRequestWrapper(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    private HttpServletRequest _getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getAuthType() {
        return _getHttpServletRequest().getAuthType();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Cookie[] getCookies() {
        return _getHttpServletRequest().getCookies();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public long getDateHeader(String str) {
        return _getHttpServletRequest().getDateHeader(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getHeader(String str) {
        return _getHttpServletRequest().getHeader(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Enumeration<String> getHeaders(String str) {
        return _getHttpServletRequest().getHeaders(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Enumeration<String> getHeaderNames() {
        return _getHttpServletRequest().getHeaderNames();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public int getIntHeader(String str) {
        return _getHttpServletRequest().getIntHeader(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getMethod() {
        return _getHttpServletRequest().getMethod();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getPathInfo() {
        return _getHttpServletRequest().getPathInfo();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getPathTranslated() {
        return _getHttpServletRequest().getPathTranslated();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getContextPath() {
        return _getHttpServletRequest().getContextPath();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getQueryString() {
        return _getHttpServletRequest().getQueryString();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getRemoteUser() {
        return _getHttpServletRequest().getRemoteUser();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isUserInRole(String str) {
        return _getHttpServletRequest().isUserInRole(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Principal getUserPrincipal() {
        return _getHttpServletRequest().getUserPrincipal();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getRequestedSessionId() {
        return _getHttpServletRequest().getRequestedSessionId();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getRequestURI() {
        return _getHttpServletRequest().getRequestURI();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public StringBuffer getRequestURL() {
        return _getHttpServletRequest().getRequestURL();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getServletPath() {
        return _getHttpServletRequest().getServletPath();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public HttpSession getSession(boolean z) {
        return _getHttpServletRequest().getSession(z);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public HttpSession getSession() {
        return _getHttpServletRequest().getSession();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdValid() {
        return _getHttpServletRequest().isRequestedSessionIdValid();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdFromCookie() {
        return _getHttpServletRequest().isRequestedSessionIdFromCookie();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdFromURL() {
        return _getHttpServletRequest().isRequestedSessionIdFromURL();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdFromUrl() {
        return _getHttpServletRequest().isRequestedSessionIdFromUrl();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return _getHttpServletRequest().authenticate(httpServletResponse);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public void login(String str, String str2) throws ServletException {
        _getHttpServletRequest().login(str, str2);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public void logout() throws ServletException {
        _getHttpServletRequest().logout();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Collection<Part> getParts() throws IOException, ServletException {
        return _getHttpServletRequest().getParts();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Part getPart(String str) throws IOException, ServletException {
        return _getHttpServletRequest().getPart(str);
    }
}
