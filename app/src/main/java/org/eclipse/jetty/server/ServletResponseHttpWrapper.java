package org.eclipse.jetty.server;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/* loaded from: classes5.dex */
public class ServletResponseHttpWrapper extends ServletResponseWrapper implements HttpServletResponse {
    @Override // javax.servlet.http.HttpServletResponse
    public void addCookie(Cookie cookie) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void addDateHeader(String str, long j) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void addHeader(String str, String str2) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void addIntHeader(String str, int i) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public boolean containsHeader(String str) {
        return false;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeRedirectURL(String str) {
        return null;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeRedirectUrl(String str) {
        return null;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeURL(String str) {
        return null;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeUrl(String str) {
        return null;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String getHeader(String str) {
        return null;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public Collection<String> getHeaders(String str) {
        return null;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public int getStatus() {
        return 0;
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void sendError(int i) throws IOException {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void sendError(int i, String str) throws IOException {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void sendRedirect(String str) throws IOException {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setDateHeader(String str, long j) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setHeader(String str, String str2) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setIntHeader(String str, int i) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setStatus(int i) {
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setStatus(int i, String str) {
    }

    public ServletResponseHttpWrapper(ServletResponse servletResponse) {
        super(servletResponse);
    }
}
