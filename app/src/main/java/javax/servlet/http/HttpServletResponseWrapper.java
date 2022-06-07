package javax.servlet.http;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletResponseWrapper;

/* loaded from: classes5.dex */
public class HttpServletResponseWrapper extends ServletResponseWrapper implements HttpServletResponse {
    public HttpServletResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    private HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void addCookie(Cookie cookie) {
        _getHttpServletResponse().addCookie(cookie);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public boolean containsHeader(String str) {
        return _getHttpServletResponse().containsHeader(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeURL(String str) {
        return _getHttpServletResponse().encodeURL(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeRedirectURL(String str) {
        return _getHttpServletResponse().encodeRedirectURL(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeUrl(String str) {
        return _getHttpServletResponse().encodeUrl(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String encodeRedirectUrl(String str) {
        return _getHttpServletResponse().encodeRedirectUrl(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void sendError(int i, String str) throws IOException {
        _getHttpServletResponse().sendError(i, str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void sendError(int i) throws IOException {
        _getHttpServletResponse().sendError(i);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void sendRedirect(String str) throws IOException {
        _getHttpServletResponse().sendRedirect(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setDateHeader(String str, long j) {
        _getHttpServletResponse().setDateHeader(str, j);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void addDateHeader(String str, long j) {
        _getHttpServletResponse().addDateHeader(str, j);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setHeader(String str, String str2) {
        _getHttpServletResponse().setHeader(str, str2);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void addHeader(String str, String str2) {
        _getHttpServletResponse().addHeader(str, str2);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setIntHeader(String str, int i) {
        _getHttpServletResponse().setIntHeader(str, i);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void addIntHeader(String str, int i) {
        _getHttpServletResponse().addIntHeader(str, i);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setStatus(int i) {
        _getHttpServletResponse().setStatus(i);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public void setStatus(int i, String str) {
        _getHttpServletResponse().setStatus(i, str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public int getStatus() {
        return _getHttpServletResponse().getStatus();
    }

    @Override // javax.servlet.http.HttpServletResponse
    public String getHeader(String str) {
        return _getHttpServletResponse().getHeader(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public Collection<String> getHeaders(String str) {
        return _getHttpServletResponse().getHeaders(str);
    }

    @Override // javax.servlet.http.HttpServletResponse
    public Collection<String> getHeaderNames() {
        return _getHttpServletResponse().getHeaderNames();
    }
}
