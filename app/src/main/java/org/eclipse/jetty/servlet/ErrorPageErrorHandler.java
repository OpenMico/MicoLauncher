package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class ErrorPageErrorHandler extends ErrorHandler {
    public static final String ERROR_PAGE = "org.eclipse.jetty.server.error_page";
    public static final String GLOBAL_ERROR_PAGE = "org.eclipse.jetty.server.error_page.global";
    private static final Logger LOG = Log.getLogger(ErrorPageErrorHandler.class);
    protected ServletContext _servletContext;
    private final Map<String, String> _errorPages = new HashMap();
    private final List<ErrorCodeRange> _errorPageList = new ArrayList();

    @Override // org.eclipse.jetty.server.handler.ErrorHandler, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String str2;
        Integer num;
        String method = httpServletRequest.getMethod();
        if (method.equals("GET") || method.equals("POST") || method.equals("HEAD")) {
            if (this._errorPages != null) {
                String str3 = null;
                Class<?> cls = (Class) httpServletRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
                if (ServletException.class.equals(cls) && (str3 = this._errorPages.get(cls.getName())) == null) {
                    Throwable th = (Throwable) httpServletRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                    while (th instanceof ServletException) {
                        th = ((ServletException) th).getRootCause();
                    }
                    if (th != null) {
                        cls = th.getClass();
                    }
                }
                while (str3 == null && cls != null) {
                    str3 = this._errorPages.get(cls.getName());
                    cls = cls.getSuperclass();
                }
                if (str3 == null && (num = (Integer) httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)) != null && (str3 = this._errorPages.get(Integer.toString(num.intValue()))) == null && this._errorPageList != null) {
                    int i = 0;
                    while (true) {
                        if (i >= this._errorPageList.size()) {
                            break;
                        }
                        ErrorCodeRange errorCodeRange = this._errorPageList.get(i);
                        if (errorCodeRange.isInRange(num.intValue())) {
                            str3 = errorCodeRange.getUri();
                            break;
                        }
                        i++;
                    }
                }
                if (str3 == null) {
                    str3 = this._errorPages.get(GLOBAL_ERROR_PAGE);
                }
                if (str3 != null && ((str2 = (String) httpServletRequest.getAttribute(ERROR_PAGE)) == null || !str2.equals(str3))) {
                    httpServletRequest.setAttribute(ERROR_PAGE, str3);
                    Dispatcher dispatcher = (Dispatcher) this._servletContext.getRequestDispatcher(str3);
                    try {
                        if (dispatcher != null) {
                            dispatcher.error(httpServletRequest, httpServletResponse);
                            return;
                        }
                        LOG.warn("No error page " + str3, new Object[0]);
                    } catch (ServletException e) {
                        LOG.warn(Log.EXCEPTION, e);
                        return;
                    }
                }
            }
            super.handle(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        AbstractHttpConnection.getCurrentConnection().getRequest().setHandled(true);
    }

    public Map<String, String> getErrorPages() {
        return this._errorPages;
    }

    public void setErrorPages(Map<String, String> map) {
        this._errorPages.clear();
        if (map != null) {
            this._errorPages.putAll(map);
        }
    }

    public void addErrorPage(Class<? extends Throwable> cls, String str) {
        this._errorPages.put(cls.getName(), str);
    }

    public void addErrorPage(String str, String str2) {
        this._errorPages.put(str, str2);
    }

    public void addErrorPage(int i, String str) {
        this._errorPages.put(Integer.toString(i), str);
    }

    public void addErrorPage(int i, int i2, String str) {
        this._errorPageList.add(new ErrorCodeRange(i, i2, str));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        this._servletContext = ContextHandler.getCurrentContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
    }

    /* loaded from: classes5.dex */
    private class ErrorCodeRange {
        private int _from;
        private int _to;
        private String _uri;

        ErrorCodeRange(int i, int i2, String str) throws IllegalArgumentException {
            if (i <= i2) {
                this._from = i;
                this._to = i2;
                this._uri = str;
                return;
            }
            throw new IllegalArgumentException("from>to");
        }

        boolean isInRange(int i) {
            return i >= this._from && i <= this._to;
        }

        String getUri() {
            return this._uri;
        }

        public String toString() {
            return "from: " + this._from + ",to: " + this._to + ",uri: " + this._uri;
        }
    }
}
