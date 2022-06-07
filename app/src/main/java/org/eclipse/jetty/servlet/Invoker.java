package org.eclipse.jetty.servlet;

import com.umeng.analytics.pro.ai;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class Invoker extends HttpServlet {
    private static final Logger LOG = Log.getLogger(Invoker.class);
    private ContextHandler _contextHandler;
    private Map.Entry _invokerEntry;
    private boolean _nonContextServlets;
    private Map _parameters;
    private ServletHandler _servletHandler;
    private boolean _verbose;

    @Override // javax.servlet.GenericServlet
    public void init() {
        this._contextHandler = ((ContextHandler.Context) getServletContext()).getContextHandler();
        Handler handler = this._contextHandler.getHandler();
        while (handler != null && !(handler instanceof ServletHandler) && (handler instanceof HandlerWrapper)) {
            handler = ((HandlerWrapper) handler).getHandler();
        }
        this._servletHandler = (ServletHandler) handler;
        Enumeration<String> initParameterNames = getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String nextElement = initParameterNames.nextElement();
            String initParameter = getInitParameter(nextElement);
            String lowerCase = initParameter.toLowerCase(Locale.ENGLISH);
            boolean z = true;
            if ("nonContextServlets".equals(nextElement)) {
                this._nonContextServlets = initParameter.length() > 0 && lowerCase.startsWith(ai.aF);
            }
            if ("verbose".equals(nextElement)) {
                if (initParameter.length() <= 0 || !lowerCase.startsWith(ai.aF)) {
                    z = false;
                }
                this._verbose = z;
            } else {
                if (this._parameters == null) {
                    this._parameters = new HashMap();
                }
                this._parameters.put(nextElement, initParameter);
            }
        }
    }

    @Override // javax.servlet.http.HttpServlet
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String str;
        boolean z;
        String str2;
        ServletHolder servletHolder;
        String str3 = (String) httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH);
        int i = 1;
        if (str3 == null) {
            str = httpServletRequest.getServletPath();
            z = false;
        } else {
            str = str3;
            z = true;
        }
        String str4 = (String) httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO);
        String pathInfo = str4 == null ? httpServletRequest.getPathInfo() : str4;
        if (pathInfo == null || pathInfo.length() <= 1) {
            httpServletResponse.sendError(404);
            return;
        }
        if (pathInfo.charAt(0) != '/') {
            i = 0;
        }
        int indexOf = pathInfo.indexOf(47, i);
        String substring = indexOf < 0 ? pathInfo.substring(i) : pathInfo.substring(i, indexOf);
        ServletHolder holder = getHolder(this._servletHandler.getServlets(), substring);
        if (holder != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Adding servlet mapping for named servlet:" + substring + Constants.COLON_SEPARATOR + URIUtil.addPaths(str, substring) + "/*", new Object[0]);
            }
            ServletMapping servletMapping = new ServletMapping();
            servletMapping.setServletName(substring);
            servletMapping.setPathSpec(URIUtil.addPaths(str, substring) + "/*");
            ServletHandler servletHandler = this._servletHandler;
            servletHandler.setServletMappings((ServletMapping[]) LazyList.addToArray(servletHandler.getServletMappings(), servletMapping, ServletMapping.class));
            str2 = substring;
            servletHolder = holder;
        } else {
            if (substring.endsWith(".class")) {
                substring = substring.substring(0, substring.length() - 6);
            }
            if (substring == null || substring.length() == 0) {
                httpServletResponse.sendError(404);
                return;
            }
            synchronized (this._servletHandler) {
                this._invokerEntry = this._servletHandler.getHolderEntry(str);
                String addPaths = URIUtil.addPaths(str, substring);
                PathMap.Entry holderEntry = this._servletHandler.getHolderEntry(addPaths);
                if (holderEntry == null || holderEntry.equals(this._invokerEntry)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Making new servlet=" + substring + " with path=" + addPaths + "/*", new Object[0]);
                    }
                    ServletHolder addServletWithMapping = this._servletHandler.addServletWithMapping(substring, addPaths + "/*");
                    if (this._parameters != null) {
                        addServletWithMapping.setInitParameters(this._parameters);
                    }
                    try {
                        addServletWithMapping.start();
                        if (!this._nonContextServlets) {
                            Servlet servlet = addServletWithMapping.getServlet();
                            if (this._contextHandler.getClassLoader() != servlet.getClass().getClassLoader()) {
                                try {
                                    addServletWithMapping.stop();
                                } catch (Exception e) {
                                    LOG.ignore(e);
                                }
                                LOG.warn("Dynamic servlet " + servlet + " not loaded from context " + httpServletRequest.getContextPath(), new Object[0]);
                                throw new UnavailableException("Not in context");
                            }
                        }
                        if (this._verbose && LOG.isDebugEnabled()) {
                            LOG.debug("Dynamic load '" + substring + "' at " + addPaths, new Object[0]);
                        }
                        servletHolder = addServletWithMapping;
                    } catch (Exception e2) {
                        LOG.debug(e2);
                        throw new UnavailableException(e2.toString());
                    }
                } else {
                    servletHolder = (ServletHolder) holderEntry.getValue();
                }
            }
            str2 = substring;
        }
        if (servletHolder != null) {
            servletHolder.handle(httpServletRequest instanceof Request ? (Request) httpServletRequest : AbstractHttpConnection.getCurrentConnection().getRequest(), new InvokedRequest(httpServletRequest, z, str2, str, pathInfo), httpServletResponse);
            return;
        }
        LOG.info("Can't find holder for servlet: " + str2, new Object[0]);
        httpServletResponse.sendError(404);
    }

    /* loaded from: classes5.dex */
    class InvokedRequest extends HttpServletRequestWrapper {
        boolean _included;
        String _pathInfo;
        String _servletPath;

        InvokedRequest(HttpServletRequest httpServletRequest, boolean z, String str, String str2, String str3) {
            super(httpServletRequest);
            this._included = z;
            this._servletPath = URIUtil.addPaths(str2, str);
            this._pathInfo = str3.substring(str.length() + 1);
            if (this._pathInfo.length() == 0) {
                this._pathInfo = null;
            }
        }

        @Override // javax.servlet.http.HttpServletRequestWrapper, javax.servlet.http.HttpServletRequest
        public String getServletPath() {
            if (this._included) {
                return super.getServletPath();
            }
            return this._servletPath;
        }

        @Override // javax.servlet.http.HttpServletRequestWrapper, javax.servlet.http.HttpServletRequest
        public String getPathInfo() {
            if (this._included) {
                return super.getPathInfo();
            }
            return this._pathInfo;
        }

        @Override // javax.servlet.ServletRequestWrapper, javax.servlet.ServletRequest
        public Object getAttribute(String str) {
            if (this._included) {
                if (str.equals(RequestDispatcher.INCLUDE_REQUEST_URI)) {
                    return URIUtil.addPaths(URIUtil.addPaths(getContextPath(), this._servletPath), this._pathInfo);
                }
                if (str.equals(RequestDispatcher.INCLUDE_PATH_INFO)) {
                    return this._pathInfo;
                }
                if (str.equals(RequestDispatcher.INCLUDE_SERVLET_PATH)) {
                    return this._servletPath;
                }
            }
            return super.getAttribute(str);
        }
    }

    private ServletHolder getHolder(ServletHolder[] servletHolderArr, String str) {
        ServletHolder servletHolder = null;
        if (servletHolderArr == null) {
            return null;
        }
        for (int i = 0; servletHolder == null && i < servletHolderArr.length; i++) {
            if (servletHolderArr[i].getName().equals(str)) {
                servletHolder = servletHolderArr[i];
            }
        }
        return servletHolder;
    }
}
