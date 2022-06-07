package org.eclipse.jetty.servlet;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.ContinuationThrowable;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.RuntimeIOException;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServletRequestHttpWrapper;
import org.eclipse.jetty.server.ServletResponseHttpWrapper;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class ServletHandler extends ScopedHandler {
    private static final Logger LOG = Log.getLogger(ServletHandler.class);
    public static final String __DEFAULT_SERVLET = "default";
    private ServletContextHandler _contextHandler;
    private FilterMapping[] _filterMappings;
    private MultiMap<String> _filterNameMappings;
    private List<FilterMapping> _filterPathMappings;
    private IdentityService _identityService;
    private ContextHandler.Context _servletContext;
    private ServletMapping[] _servletMappings;
    private PathMap _servletPathMap;
    private FilterHolder[] _filters = new FilterHolder[0];
    private boolean _filterChainsCached = true;
    private int _maxFilterChainsCacheSize = 512;
    private boolean _startWithUnavailable = true;
    private ServletHolder[] _servlets = new ServletHolder[0];
    private final Map<String, FilterHolder> _filterNameMap = new HashMap();
    private final Map<String, ServletHolder> _servletNameMap = new HashMap();
    protected final ConcurrentMap<String, FilterChain>[] _chainCache = new ConcurrentMap[31];
    protected final Queue<String>[] _chainLRU = new Queue[31];

    public Object getContextLog() {
        return null;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        Server server2 = getServer();
        if (!(server2 == null || server2 == server)) {
            getServer().getContainer().update((Object) this, (Object[]) this._filters, (Object[]) null, ChildVideo.ItemsBean.TYPE_FILTER, true);
            getServer().getContainer().update((Object) this, (Object[]) this._filterMappings, (Object[]) null, "filterMapping", true);
            getServer().getContainer().update((Object) this, (Object[]) this._servlets, (Object[]) null, "servlet", true);
            getServer().getContainer().update((Object) this, (Object[]) this._servletMappings, (Object[]) null, "servletMapping", true);
        }
        super.setServer(server);
        if (server != null && server2 != server) {
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._filters, ChildVideo.ItemsBean.TYPE_FILTER, true);
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._filterMappings, "filterMapping", true);
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._servlets, "servlet", true);
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._servletMappings, "servletMapping", true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.ScopedHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public synchronized void doStart() throws Exception {
        SecurityHandler securityHandler;
        this._servletContext = ContextHandler.getCurrentContext();
        this._contextHandler = (ServletContextHandler) (this._servletContext == null ? null : this._servletContext.getContextHandler());
        if (!(this._contextHandler == null || (securityHandler = (SecurityHandler) this._contextHandler.getChildHandlerByClass(SecurityHandler.class)) == null)) {
            this._identityService = securityHandler.getIdentityService();
        }
        updateNameMappings();
        updateMappings();
        if (this._filterChainsCached) {
            this._chainCache[1] = new ConcurrentHashMap();
            this._chainCache[2] = new ConcurrentHashMap();
            this._chainCache[4] = new ConcurrentHashMap();
            this._chainCache[8] = new ConcurrentHashMap();
            this._chainCache[16] = new ConcurrentHashMap();
            this._chainLRU[1] = new ConcurrentLinkedQueue();
            this._chainLRU[2] = new ConcurrentLinkedQueue();
            this._chainLRU[4] = new ConcurrentLinkedQueue();
            this._chainLRU[8] = new ConcurrentLinkedQueue();
            this._chainLRU[16] = new ConcurrentLinkedQueue();
        }
        super.doStart();
        if (this._contextHandler == null || !(this._contextHandler instanceof ServletContextHandler)) {
            initialize();
        }
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public synchronized void doStop() throws Exception {
        super.doStop();
        if (this._filters != null) {
            int length = this._filters.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                try {
                    this._filters[i].stop();
                } catch (Exception e) {
                    LOG.warn(Log.EXCEPTION, e);
                }
                length = i;
            }
        }
        if (this._servlets != null) {
            int length2 = this._servlets.length;
            while (true) {
                int i2 = length2 - 1;
                if (length2 <= 0) {
                    break;
                }
                try {
                    this._servlets[i2].stop();
                } catch (Exception e2) {
                    LOG.warn(Log.EXCEPTION, e2);
                }
                length2 = i2;
            }
        }
        this._filterPathMappings = null;
        this._filterNameMappings = null;
        this._servletPathMap = null;
    }

    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public FilterMapping[] getFilterMappings() {
        return this._filterMappings;
    }

    public FilterHolder[] getFilters() {
        return this._filters;
    }

    public PathMap.Entry getHolderEntry(String str) {
        PathMap pathMap = this._servletPathMap;
        if (pathMap == null) {
            return null;
        }
        return pathMap.getMatch(str);
    }

    public ServletContext getServletContext() {
        return this._servletContext;
    }

    public ServletMapping[] getServletMappings() {
        return this._servletMappings;
    }

    public ServletMapping getServletMapping(String str) {
        ServletMapping[] servletMappingArr = this._servletMappings;
        if (servletMappingArr == null) {
            return null;
        }
        ServletMapping servletMapping = null;
        for (ServletMapping servletMapping2 : servletMappingArr) {
            String[] pathSpecs = servletMapping2.getPathSpecs();
            if (pathSpecs != null) {
                ServletMapping servletMapping3 = servletMapping;
                for (String str2 : pathSpecs) {
                    if (str.equals(str2)) {
                        servletMapping3 = servletMapping2;
                    }
                }
                servletMapping = servletMapping3;
            }
        }
        return servletMapping;
    }

    public ServletHolder[] getServlets() {
        return this._servlets;
    }

    public ServletHolder getServlet(String str) {
        return this._servletNameMap.get(str);
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        UserIdentity.Scope scope;
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        DispatcherType dispatcherType = request.getDispatcherType();
        UserIdentity.Scope scope2 = null;
        if (str.startsWith("/")) {
            PathMap.Entry holderEntry = getHolderEntry(str);
            if (holderEntry != null) {
                scope = (ServletHolder) holderEntry.getValue();
                String str2 = (String) holderEntry.getKey();
                String mapped = holderEntry.getMapped() != null ? holderEntry.getMapped() : PathMap.pathMatch(str2, str);
                String pathInfo2 = PathMap.pathInfo(str2, str);
                if (DispatcherType.INCLUDE.equals(dispatcherType)) {
                    request.setAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH, mapped);
                    request.setAttribute(RequestDispatcher.INCLUDE_PATH_INFO, pathInfo2);
                } else {
                    request.setServletPath(mapped);
                    request.setPathInfo(pathInfo2);
                }
            } else {
                scope = scope2;
            }
        } else {
            scope = (ServletHolder) this._servletNameMap.get(str);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("servlet {}|{}|{} -> {}", request.getContextPath(), request.getServletPath(), request.getPathInfo(), scope);
        }
        try {
            scope2 = request.getUserIdentityScope();
            request.setUserIdentityScope(scope);
            if (never()) {
                nextScope(str, request, httpServletRequest, httpServletResponse);
            } else if (this._nextScope != null) {
                this._nextScope.doScope(str, request, httpServletRequest, httpServletResponse);
            } else if (this._outerScope != null) {
                this._outerScope.doHandle(str, request, httpServletRequest, httpServletResponse);
            } else {
                doHandle(str, request, httpServletRequest, httpServletResponse);
            }
        } finally {
            if (scope2 != null) {
                request.setUserIdentityScope(scope2);
            }
            if (!DispatcherType.INCLUDE.equals(dispatcherType)) {
                request.setServletPath(servletPath);
                request.setPathInfo(pathInfo);
            }
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        FilterMapping[] filterMappingArr;
        FilterMapping[] filterMappingArr2;
        DispatcherType dispatcherType = request.getDispatcherType();
        ServletHolder servletHolder = (ServletHolder) request.getUserIdentityScope();
        FilterChain filterChain = null;
        if (str.startsWith("/")) {
            if (!(servletHolder == null || (filterMappingArr2 = this._filterMappings) == null || filterMappingArr2.length <= 0)) {
                filterChain = getFilterChain(request, str, servletHolder);
            }
        } else if (!(servletHolder == null || (filterMappingArr = this._filterMappings) == null || filterMappingArr.length <= 0)) {
            filterChain = getFilterChain(request, null, servletHolder);
        }
        LOG.debug("chain={}", filterChain);
        try {
            try {
                try {
                    try {
                        try {
                            if (servletHolder != null) {
                                ServletRequest request2 = httpServletRequest instanceof ServletRequestHttpWrapper ? ((ServletRequestHttpWrapper) httpServletRequest).getRequest() : httpServletRequest;
                                ServletResponse response = httpServletResponse instanceof ServletResponseHttpWrapper ? ((ServletResponseHttpWrapper) httpServletResponse).getResponse() : httpServletResponse;
                                if (filterChain != null) {
                                    filterChain.doFilter(request2, response);
                                } else {
                                    servletHolder.handle(request, request2, response);
                                }
                            } else if (getHandler() == null) {
                                notFound(httpServletRequest, httpServletResponse);
                            } else {
                                nextHandle(str, request, httpServletRequest, httpServletResponse);
                            }
                        } catch (EofException e) {
                            throw e;
                        }
                    } catch (RuntimeIOException e2) {
                        throw e2;
                    }
                } catch (ContinuationThrowable e3) {
                    throw e3;
                }
            } catch (Error e4) {
                if (!DispatcherType.REQUEST.equals(dispatcherType) && !DispatcherType.ASYNC.equals(dispatcherType)) {
                    throw e4;
                }
                Logger logger = LOG;
                logger.warn("Error for " + httpServletRequest.getRequestURI(), e4);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(httpServletRequest.toString(), new Object[0]);
                }
                if (!httpServletResponse.isCommitted()) {
                    httpServletRequest.setAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE, e4.getClass());
                    httpServletRequest.setAttribute(RequestDispatcher.ERROR_EXCEPTION, e4);
                    httpServletResponse.sendError(500, e4.getMessage());
                } else {
                    LOG.debug("Response already committed for handling ", e4);
                }
                if (servletHolder == null) {
                }
            } catch (Exception e5) {
                e = e5;
                if (!DispatcherType.REQUEST.equals(dispatcherType) && !DispatcherType.ASYNC.equals(dispatcherType)) {
                    if (e instanceof IOException) {
                        throw ((IOException) e);
                    } else if (e instanceof RuntimeException) {
                        throw ((RuntimeException) e);
                    } else if (e instanceof ServletException) {
                        throw ((ServletException) e);
                    }
                }
                if (e instanceof UnavailableException) {
                    LOG.debug(e);
                } else if (e instanceof ServletException) {
                    LOG.debug(e);
                    Throwable rootCause = ((ServletException) e).getRootCause();
                    if (rootCause != null) {
                        e = rootCause;
                    }
                } else if (e instanceof RuntimeIOException) {
                    LOG.debug(e);
                    IOException iOException = (IOException) ((RuntimeIOException) e).getCause();
                    if (iOException != null) {
                        e = iOException;
                    }
                }
                if (e instanceof HttpException) {
                    throw ((HttpException) e);
                } else if (e instanceof RuntimeIOException) {
                    throw ((RuntimeIOException) e);
                } else if (!(e instanceof EofException)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.warn(httpServletRequest.getRequestURI(), e);
                        LOG.debug(httpServletRequest.toString(), new Object[0]);
                    } else {
                        if (!(e instanceof IOException) && !(e instanceof UnavailableException)) {
                            LOG.warn(httpServletRequest.getRequestURI(), e);
                        }
                        LOG.debug(httpServletRequest.getRequestURI(), e);
                    }
                    if (!httpServletResponse.isCommitted()) {
                        httpServletRequest.setAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE, e.getClass());
                        httpServletRequest.setAttribute(RequestDispatcher.ERROR_EXCEPTION, e);
                        if (!(e instanceof UnavailableException)) {
                            httpServletResponse.sendError(500, e.getMessage());
                        } else if (((UnavailableException) e).isPermanent()) {
                            httpServletResponse.sendError(404, e.getMessage());
                        } else {
                            httpServletResponse.sendError(503, e.getMessage());
                        }
                    } else {
                        Logger logger2 = LOG;
                        logger2.debug("Response already committed for handling " + e, new Object[0]);
                    }
                    if (servletHolder == null) {
                    }
                } else {
                    throw ((EofException) e);
                }
            }
        } finally {
            if (servletHolder != null) {
                request.setHandled(true);
            }
        }
    }

    private FilterChain getFilterChain(Request request, String str, ServletHolder servletHolder) {
        Object obj;
        MultiMap<String> multiMap;
        ConcurrentMap<String, FilterChain>[] concurrentMapArr;
        FilterChain filterChain;
        String name = str == null ? servletHolder.getName() : str;
        int dispatch = FilterMapping.dispatch(request.getDispatcherType());
        if (!(!this._filterChainsCached || (concurrentMapArr = this._chainCache) == null || (filterChain = concurrentMapArr[dispatch].get(name)) == null)) {
            return filterChain;
        }
        CachedChain cachedChain = null;
        if (str == null || this._filterPathMappings == null) {
            obj = null;
        } else {
            obj = null;
            for (int i = 0; i < this._filterPathMappings.size(); i++) {
                FilterMapping filterMapping = this._filterPathMappings.get(i);
                if (filterMapping.appliesTo(str, dispatch)) {
                    obj = LazyList.add(obj, filterMapping.getFilterHolder());
                }
            }
        }
        if (servletHolder != null && (multiMap = this._filterNameMappings) != null && multiMap.size() > 0 && this._filterNameMappings.size() > 0) {
            Object obj2 = this._filterNameMappings.get(servletHolder.getName());
            for (int i2 = 0; i2 < LazyList.size(obj2); i2++) {
                FilterMapping filterMapping2 = (FilterMapping) LazyList.get(obj2, i2);
                if (filterMapping2.appliesTo(dispatch)) {
                    obj = LazyList.add(obj, filterMapping2.getFilterHolder());
                }
            }
            Object obj3 = this._filterNameMappings.get("*");
            for (int i3 = 0; i3 < LazyList.size(obj3); i3++) {
                FilterMapping filterMapping3 = (FilterMapping) LazyList.get(obj3, i3);
                if (filterMapping3.appliesTo(dispatch)) {
                    obj = LazyList.add(obj, filterMapping3.getFilterHolder());
                }
            }
        }
        if (obj == null) {
            return null;
        }
        if (this._filterChainsCached) {
            if (LazyList.size(obj) > 0) {
                cachedChain = new CachedChain(obj, servletHolder);
            }
            ConcurrentMap<String, FilterChain> concurrentMap = this._chainCache[dispatch];
            Queue<String> queue = this._chainLRU[dispatch];
            while (true) {
                if (this._maxFilterChainsCacheSize <= 0 || concurrentMap.size() < this._maxFilterChainsCacheSize) {
                    break;
                }
                String poll = queue.poll();
                if (poll == null) {
                    concurrentMap.clear();
                    break;
                }
                concurrentMap.remove(poll);
            }
            concurrentMap.put(name, cachedChain);
            queue.add(name);
            return cachedChain;
        } else if (LazyList.size(obj) > 0) {
            return new Chain(request, obj, servletHolder);
        } else {
            return null;
        }
    }

    private void invalidateChainsCache() {
        Queue<String>[] queueArr = this._chainLRU;
        if (queueArr[1] != null) {
            queueArr[1].clear();
            this._chainLRU[2].clear();
            this._chainLRU[4].clear();
            this._chainLRU[8].clear();
            this._chainLRU[16].clear();
            this._chainCache[1].clear();
            this._chainCache[2].clear();
            this._chainCache[4].clear();
            this._chainCache[8].clear();
            this._chainCache[16].clear();
        }
    }

    public boolean isAvailable() {
        if (!isStarted()) {
            return false;
        }
        ServletHolder[] servlets = getServlets();
        for (ServletHolder servletHolder : servlets) {
            if (!(servletHolder == null || servletHolder.isAvailable())) {
                return false;
            }
        }
        return true;
    }

    public void setStartWithUnavailable(boolean z) {
        this._startWithUnavailable = z;
    }

    public boolean isStartWithUnavailable() {
        return this._startWithUnavailable;
    }

    public void initialize() throws Exception {
        MultiException multiException = new MultiException();
        if (this._filters != null) {
            int i = 0;
            while (true) {
                FilterHolder[] filterHolderArr = this._filters;
                if (i >= filterHolderArr.length) {
                    break;
                }
                filterHolderArr[i].start();
                i++;
            }
        }
        ServletHolder[] servletHolderArr = this._servlets;
        if (servletHolderArr != null) {
            ServletHolder[] servletHolderArr2 = (ServletHolder[]) servletHolderArr.clone();
            Arrays.sort(servletHolderArr2);
            for (int i2 = 0; i2 < servletHolderArr2.length; i2++) {
                try {
                } catch (Throwable th) {
                    LOG.debug(Log.EXCEPTION, th);
                    multiException.add(th);
                }
                if (servletHolderArr2[i2].getClassName() == null && servletHolderArr2[i2].getForcedPath() != null) {
                    ServletHolder servletHolder = (ServletHolder) this._servletPathMap.match(servletHolderArr2[i2].getForcedPath());
                    if (!(servletHolder == null || servletHolder.getClassName() == null)) {
                        servletHolderArr2[i2].setClassName(servletHolder.getClassName());
                    }
                    multiException.add(new IllegalStateException("No forced path servlet for " + servletHolderArr2[i2].getForcedPath()));
                }
                servletHolderArr2[i2].start();
            }
            multiException.ifExceptionThrow();
        }
    }

    public boolean isFilterChainsCached() {
        return this._filterChainsCached;
    }

    public ServletHolder newServletHolder(Holder.Source source) {
        return new ServletHolder(source);
    }

    public ServletHolder addServletWithMapping(String str, String str2) {
        ServletHolder newServletHolder = newServletHolder(null);
        newServletHolder.setName(str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + LazyList.size(this._servlets));
        newServletHolder.setClassName(str);
        addServletWithMapping(newServletHolder, str2);
        return newServletHolder;
    }

    public ServletHolder addServletWithMapping(Class<? extends Servlet> cls, String str) {
        ServletHolder newServletHolder = newServletHolder(Holder.Source.EMBEDDED);
        newServletHolder.setHeldClass(cls);
        setServlets((ServletHolder[]) LazyList.addToArray(getServlets(), newServletHolder, ServletHolder.class));
        addServletWithMapping(newServletHolder, str);
        return newServletHolder;
    }

    public void addServletWithMapping(ServletHolder servletHolder, String str) {
        ServletHolder[] servlets = getServlets();
        if (servlets != null) {
            servlets = (ServletHolder[]) servlets.clone();
        }
        try {
            setServlets((ServletHolder[]) LazyList.addToArray(servlets, servletHolder, ServletHolder.class));
            ServletMapping servletMapping = new ServletMapping();
            servletMapping.setServletName(servletHolder.getName());
            servletMapping.setPathSpec(str);
            setServletMappings((ServletMapping[]) LazyList.addToArray(getServletMappings(), servletMapping, ServletMapping.class));
        } catch (Exception e) {
            setServlets(servlets);
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            }
            throw new RuntimeException(e);
        }
    }

    public void addServlet(ServletHolder servletHolder) {
        setServlets((ServletHolder[]) LazyList.addToArray(getServlets(), servletHolder, ServletHolder.class));
    }

    public void addServletMapping(ServletMapping servletMapping) {
        setServletMappings((ServletMapping[]) LazyList.addToArray(getServletMappings(), servletMapping, ServletMapping.class));
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic dynamic, ServletSecurityElement servletSecurityElement) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            return servletContextHandler.setServletSecurity(dynamic, servletSecurityElement);
        }
        return Collections.emptySet();
    }

    public FilterHolder newFilterHolder(Holder.Source source) {
        return new FilterHolder(source);
    }

    public FilterHolder getFilter(String str) {
        return this._filterNameMap.get(str);
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> cls, String str, EnumSet<DispatcherType> enumSet) {
        FilterHolder newFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        newFilterHolder.setHeldClass(cls);
        addFilterWithMapping(newFilterHolder, str, enumSet);
        return newFilterHolder;
    }

    public FilterHolder addFilterWithMapping(String str, String str2, EnumSet<DispatcherType> enumSet) {
        FilterHolder newFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        newFilterHolder.setName(str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this._filters.length);
        newFilterHolder.setClassName(str);
        addFilterWithMapping(newFilterHolder, str2, enumSet);
        return newFilterHolder;
    }

    public void addFilterWithMapping(FilterHolder filterHolder, String str, EnumSet<DispatcherType> enumSet) {
        FilterHolder[] filters = getFilters();
        if (filters != null) {
            filters = (FilterHolder[]) filters.clone();
        }
        try {
            setFilters((FilterHolder[]) LazyList.addToArray(filters, filterHolder, FilterHolder.class));
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterName(filterHolder.getName());
            filterMapping.setPathSpec(str);
            filterMapping.setDispatcherTypes(enumSet);
            setFilterMappings((FilterMapping[]) LazyList.addToArray(getFilterMappings(), filterMapping, FilterMapping.class));
        } catch (Error e) {
            setFilters(filters);
            throw e;
        } catch (RuntimeException e2) {
            setFilters(filters);
            throw e2;
        }
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> cls, String str, int i) {
        FilterHolder newFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        newFilterHolder.setHeldClass(cls);
        addFilterWithMapping(newFilterHolder, str, i);
        return newFilterHolder;
    }

    public FilterHolder addFilterWithMapping(String str, String str2, int i) {
        FilterHolder newFilterHolder = newFilterHolder(null);
        newFilterHolder.setName(str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this._filters.length);
        newFilterHolder.setClassName(str);
        addFilterWithMapping(newFilterHolder, str2, i);
        return newFilterHolder;
    }

    public void addFilterWithMapping(FilterHolder filterHolder, String str, int i) {
        FilterHolder[] filters = getFilters();
        if (filters != null) {
            filters = (FilterHolder[]) filters.clone();
        }
        try {
            setFilters((FilterHolder[]) LazyList.addToArray(filters, filterHolder, FilterHolder.class));
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterName(filterHolder.getName());
            filterMapping.setPathSpec(str);
            filterMapping.setDispatches(i);
            setFilterMappings((FilterMapping[]) LazyList.addToArray(getFilterMappings(), filterMapping, FilterMapping.class));
        } catch (Error e) {
            setFilters(filters);
            throw e;
        } catch (RuntimeException e2) {
            setFilters(filters);
            throw e2;
        }
    }

    public FilterHolder addFilter(String str, String str2, EnumSet<DispatcherType> enumSet) {
        return addFilterWithMapping(str, str2, enumSet);
    }

    public void addFilter(FilterHolder filterHolder, FilterMapping filterMapping) {
        if (filterHolder != null) {
            setFilters((FilterHolder[]) LazyList.addToArray(getFilters(), filterHolder, FilterHolder.class));
        }
        if (filterMapping != null) {
            setFilterMappings((FilterMapping[]) LazyList.addToArray(getFilterMappings(), filterMapping, FilterMapping.class));
        }
    }

    public void addFilter(FilterHolder filterHolder) {
        if (filterHolder != null) {
            setFilters((FilterHolder[]) LazyList.addToArray(getFilters(), filterHolder, FilterHolder.class));
        }
    }

    public void addFilterMapping(FilterMapping filterMapping) {
        if (filterMapping != null) {
            setFilterMappings((FilterMapping[]) LazyList.addToArray(getFilterMappings(), filterMapping, FilterMapping.class));
        }
    }

    public void prependFilterMapping(FilterMapping filterMapping) {
        if (filterMapping != null) {
            FilterMapping[] filterMappings = getFilterMappings();
            if (filterMappings == null || filterMappings.length == 0) {
                setFilterMappings(new FilterMapping[]{filterMapping});
                return;
            }
            FilterMapping[] filterMappingArr = new FilterMapping[filterMappings.length + 1];
            System.arraycopy(filterMappings, 0, filterMappingArr, 1, filterMappings.length);
            filterMappingArr[0] = filterMapping;
            setFilterMappings(filterMappingArr);
        }
    }

    protected synchronized void updateNameMappings() {
        this._filterNameMap.clear();
        if (this._filters != null) {
            for (int i = 0; i < this._filters.length; i++) {
                this._filterNameMap.put(this._filters[i].getName(), this._filters[i]);
                this._filters[i].setServletHandler(this);
            }
        }
        this._servletNameMap.clear();
        if (this._servlets != null) {
            for (int i2 = 0; i2 < this._servlets.length; i2++) {
                this._servletNameMap.put(this._servlets[i2].getName(), this._servlets[i2]);
                this._servlets[i2].setServletHandler(this);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0110 A[Catch: all -> 0x01d2, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:6:0x000d, B:7:0x001c, B:9:0x0021, B:11:0x0033, B:13:0x0044, B:14:0x004d, B:16:0x0057, B:17:0x0060, B:19:0x0063, B:21:0x0067, B:22:0x0072, B:23:0x0075, B:24:0x0078, B:25:0x0096, B:26:0x0097, B:28:0x009b, B:31:0x00a0, B:32:0x00a6, B:34:0x00ab, B:36:0x00bd, B:38:0x00c3, B:40:0x00cd, B:41:0x00d6, B:43:0x00d9, B:45:0x00dd, B:46:0x00e2, B:47:0x00e5, B:48:0x00e8, B:49:0x0106, B:50:0x0107, B:51:0x010a, B:52:0x010c, B:54:0x0110, B:55:0x0113, B:57:0x0117, B:59:0x011d, B:61:0x0126, B:63:0x012e, B:64:0x01b0, B:66:0x01b4, B:68:0x01bc, B:70:0x01c0, B:72:0x01c6, B:76:0x01cc, B:77:0x01d1), top: B:80:0x0001, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x012e A[Catch: all -> 0x01d2, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:6:0x000d, B:7:0x001c, B:9:0x0021, B:11:0x0033, B:13:0x0044, B:14:0x004d, B:16:0x0057, B:17:0x0060, B:19:0x0063, B:21:0x0067, B:22:0x0072, B:23:0x0075, B:24:0x0078, B:25:0x0096, B:26:0x0097, B:28:0x009b, B:31:0x00a0, B:32:0x00a6, B:34:0x00ab, B:36:0x00bd, B:38:0x00c3, B:40:0x00cd, B:41:0x00d6, B:43:0x00d9, B:45:0x00dd, B:46:0x00e2, B:47:0x00e5, B:48:0x00e8, B:49:0x0106, B:50:0x0107, B:51:0x010a, B:52:0x010c, B:54:0x0110, B:55:0x0113, B:57:0x0117, B:59:0x011d, B:61:0x0126, B:63:0x012e, B:64:0x01b0, B:66:0x01b4, B:68:0x01bc, B:70:0x01c0, B:72:0x01c6, B:76:0x01cc, B:77:0x01d1), top: B:80:0x0001, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected synchronized void updateMappings() {
        /*
            Method dump skipped, instructions count: 469
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHandler.updateMappings():void");
    }

    protected void notFound(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("Not Found " + httpServletRequest.getRequestURI(), new Object[0]);
        }
    }

    public void setFilterChainsCached(boolean z) {
        this._filterChainsCached = z;
    }

    public void setFilterMappings(FilterMapping[] filterMappingArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._filterMappings, (Object[]) filterMappingArr, "filterMapping", true);
        }
        this._filterMappings = filterMappingArr;
        updateMappings();
        invalidateChainsCache();
    }

    public synchronized void setFilters(FilterHolder[] filterHolderArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._filters, (Object[]) filterHolderArr, ChildVideo.ItemsBean.TYPE_FILTER, true);
        }
        this._filters = filterHolderArr;
        updateNameMappings();
        invalidateChainsCache();
    }

    public void setServletMappings(ServletMapping[] servletMappingArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._servletMappings, (Object[]) servletMappingArr, "servletMapping", true);
        }
        this._servletMappings = servletMappingArr;
        updateMappings();
        invalidateChainsCache();
    }

    public synchronized void setServlets(ServletHolder[] servletHolderArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._servlets, (Object[]) servletHolderArr, "servlet", true);
        }
        this._servlets = servletHolderArr;
        updateNameMappings();
        invalidateChainsCache();
    }

    /* loaded from: classes5.dex */
    public class CachedChain implements FilterChain {
        FilterHolder _filterHolder;
        CachedChain _next;
        ServletHolder _servletHolder;

        CachedChain(Object obj, ServletHolder servletHolder) {
            ServletHandler.this = r3;
            if (LazyList.size(obj) > 0) {
                this._filterHolder = (FilterHolder) LazyList.get(obj, 0);
                this._next = new CachedChain(LazyList.remove(obj, 0), servletHolder);
                return;
            }
            this._servletHolder = servletHolder;
        }

        @Override // javax.servlet.FilterChain
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            Request request = servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest();
            if (this._filterHolder != null) {
                if (ServletHandler.LOG.isDebugEnabled()) {
                    Logger logger = ServletHandler.LOG;
                    logger.debug("call filter " + this._filterHolder, new Object[0]);
                }
                Filter filter = this._filterHolder.getFilter();
                if (this._filterHolder.isAsyncSupported()) {
                    filter.doFilter(servletRequest, servletResponse, this._next);
                } else if (request.isAsyncSupported()) {
                    try {
                        request.setAsyncSupported(false);
                        filter.doFilter(servletRequest, servletResponse, this._next);
                    } finally {
                        request.setAsyncSupported(true);
                    }
                } else {
                    filter.doFilter(servletRequest, servletResponse, this._next);
                }
            } else {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                if (this._servletHolder != null) {
                    if (ServletHandler.LOG.isDebugEnabled()) {
                        Logger logger2 = ServletHandler.LOG;
                        logger2.debug("call servlet " + this._servletHolder, new Object[0]);
                    }
                    this._servletHolder.handle(request, servletRequest, servletResponse);
                } else if (ServletHandler.this.getHandler() == null) {
                    ServletHandler.this.notFound(httpServletRequest, (HttpServletResponse) servletResponse);
                } else {
                    ServletHandler.this.nextHandle(URIUtil.addPaths(httpServletRequest.getServletPath(), httpServletRequest.getPathInfo()), request, httpServletRequest, (HttpServletResponse) servletResponse);
                }
            }
        }

        public String toString() {
            if (this._filterHolder != null) {
                return this._filterHolder + "->" + this._next.toString();
            }
            ServletHolder servletHolder = this._servletHolder;
            return servletHolder != null ? servletHolder.toString() : "null";
        }
    }

    /* loaded from: classes5.dex */
    public class Chain implements FilterChain {
        final Request _baseRequest;
        final Object _chain;
        int _filter = 0;
        final ServletHolder _servletHolder;

        Chain(Request request, Object obj, ServletHolder servletHolder) {
            ServletHandler.this = r1;
            this._baseRequest = request;
            this._chain = obj;
            this._servletHolder = servletHolder;
        }

        @Override // javax.servlet.FilterChain
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            if (ServletHandler.LOG.isDebugEnabled()) {
                Logger logger = ServletHandler.LOG;
                logger.debug("doFilter " + this._filter, new Object[0]);
            }
            if (this._filter < LazyList.size(this._chain)) {
                Object obj = this._chain;
                int i = this._filter;
                this._filter = i + 1;
                FilterHolder filterHolder = (FilterHolder) LazyList.get(obj, i);
                if (ServletHandler.LOG.isDebugEnabled()) {
                    Logger logger2 = ServletHandler.LOG;
                    logger2.debug("call filter " + filterHolder, new Object[0]);
                }
                Filter filter = filterHolder.getFilter();
                if (filterHolder.isAsyncSupported() || !this._baseRequest.isAsyncSupported()) {
                    filter.doFilter(servletRequest, servletResponse, this);
                    return;
                }
                try {
                    this._baseRequest.setAsyncSupported(false);
                    filter.doFilter(servletRequest, servletResponse, this);
                } finally {
                    this._baseRequest.setAsyncSupported(true);
                }
            } else {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                if (this._servletHolder != null) {
                    if (ServletHandler.LOG.isDebugEnabled()) {
                        Logger logger3 = ServletHandler.LOG;
                        logger3.debug("call servlet " + this._servletHolder, new Object[0]);
                    }
                    this._servletHolder.handle(this._baseRequest, servletRequest, servletResponse);
                } else if (ServletHandler.this.getHandler() == null) {
                    ServletHandler.this.notFound(httpServletRequest, (HttpServletResponse) servletResponse);
                } else {
                    ServletHandler.this.nextHandle(URIUtil.addPaths(httpServletRequest.getServletPath(), httpServletRequest.getPathInfo()), servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest(), httpServletRequest, (HttpServletResponse) servletResponse);
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < LazyList.size(this._chain); i++) {
                sb.append(LazyList.get(this._chain, i).toString());
                sb.append("->");
            }
            sb.append(this._servletHolder);
            return sb.toString();
        }
    }

    public int getMaxFilterChainsCacheSize() {
        return this._maxFilterChainsCacheSize;
    }

    public void setMaxFilterChainsCacheSize(int i) {
        this._maxFilterChainsCacheSize = i;
    }

    public void destroyServlet(Servlet servlet) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            servletContextHandler.destroyServlet(servlet);
        }
    }

    public void destroyFilter(Filter filter) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            servletContextHandler.destroyFilter(filter);
        }
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        super.dumpThis(appendable);
        dump(appendable, str, TypeUtil.asList(getHandlers()), getBeans(), TypeUtil.asList(getFilterMappings()), TypeUtil.asList(getFilters()), TypeUtil.asList(getServletMappings()), TypeUtil.asList(getServlets()));
    }
}
