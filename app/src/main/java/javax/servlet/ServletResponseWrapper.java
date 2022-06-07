package javax.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/* loaded from: classes5.dex */
public class ServletResponseWrapper implements ServletResponse {
    private ServletResponse response;

    public ServletResponseWrapper(ServletResponse servletResponse) {
        if (servletResponse != null) {
            this.response = servletResponse;
            return;
        }
        throw new IllegalArgumentException("Response cannot be null");
    }

    public ServletResponse getResponse() {
        return this.response;
    }

    public void setResponse(ServletResponse servletResponse) {
        if (servletResponse != null) {
            this.response = servletResponse;
            return;
        }
        throw new IllegalArgumentException("Response cannot be null");
    }

    @Override // javax.servlet.ServletResponse
    public void setCharacterEncoding(String str) {
        this.response.setCharacterEncoding(str);
    }

    @Override // javax.servlet.ServletResponse
    public String getCharacterEncoding() {
        return this.response.getCharacterEncoding();
    }

    @Override // javax.servlet.ServletResponse
    public ServletOutputStream getOutputStream() throws IOException {
        return this.response.getOutputStream();
    }

    @Override // javax.servlet.ServletResponse
    public PrintWriter getWriter() throws IOException {
        return this.response.getWriter();
    }

    @Override // javax.servlet.ServletResponse
    public void setContentLength(int i) {
        this.response.setContentLength(i);
    }

    @Override // javax.servlet.ServletResponse
    public void setContentType(String str) {
        this.response.setContentType(str);
    }

    @Override // javax.servlet.ServletResponse
    public String getContentType() {
        return this.response.getContentType();
    }

    @Override // javax.servlet.ServletResponse
    public void setBufferSize(int i) {
        this.response.setBufferSize(i);
    }

    @Override // javax.servlet.ServletResponse
    public int getBufferSize() {
        return this.response.getBufferSize();
    }

    @Override // javax.servlet.ServletResponse
    public void flushBuffer() throws IOException {
        this.response.flushBuffer();
    }

    @Override // javax.servlet.ServletResponse
    public boolean isCommitted() {
        return this.response.isCommitted();
    }

    @Override // javax.servlet.ServletResponse
    public void reset() {
        this.response.reset();
    }

    @Override // javax.servlet.ServletResponse
    public void resetBuffer() {
        this.response.resetBuffer();
    }

    @Override // javax.servlet.ServletResponse
    public void setLocale(Locale locale) {
        this.response.setLocale(locale);
    }

    @Override // javax.servlet.ServletResponse
    public Locale getLocale() {
        return this.response.getLocale();
    }

    public boolean isWrapperFor(ServletResponse servletResponse) {
        ServletResponse servletResponse2 = this.response;
        if (servletResponse2 == servletResponse) {
            return true;
        }
        if (servletResponse2 instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) servletResponse2).isWrapperFor(servletResponse);
        }
        return false;
    }

    public boolean isWrapperFor(Class cls) {
        if (!ServletResponse.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Given class " + cls.getName() + " not a subinterface of " + ServletResponse.class.getName());
        } else if (cls.isAssignableFrom(this.response.getClass())) {
            return true;
        } else {
            ServletResponse servletResponse = this.response;
            if (servletResponse instanceof ServletResponseWrapper) {
                return ((ServletResponseWrapper) servletResponse).isWrapperFor(cls);
            }
            return false;
        }
    }
}
