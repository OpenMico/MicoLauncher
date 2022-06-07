package org.eclipse.jetty.server.handler;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class ContextHandlerCollection extends HandlerCollection {
    private static final Logger LOG = Log.getLogger(ContextHandlerCollection.class);
    private Class<? extends ContextHandler> _contextClass = ContextHandler.class;
    private volatile PathMap _contextMap;

    public ContextHandlerCollection() {
        super(true);
    }

    public void mapContexts() {
        Handler[] handlerArr;
        Map map;
        PathMap pathMap = new PathMap();
        Handler[] handlers = getHandlers();
        for (int i = 0; handlers != null && i < handlers.length; i++) {
            if (handlers[i] instanceof ContextHandler) {
                handlerArr = new Handler[]{handlers[i]};
            } else if (handlers[i] instanceof HandlerContainer) {
                handlerArr = ((HandlerContainer) handlers[i]).getChildHandlersByClass(ContextHandler.class);
            } else {
                continue;
            }
            for (Handler handler : handlerArr) {
                ContextHandler contextHandler = (ContextHandler) handler;
                String contextPath = contextHandler.getContextPath();
                if (contextPath == null || contextPath.indexOf(44) >= 0 || contextPath.startsWith("*")) {
                    throw new IllegalArgumentException("Illegal context spec:" + contextPath);
                }
                if (!contextPath.startsWith("/")) {
                    contextPath = JsonPointer.SEPARATOR + contextPath;
                }
                if (contextPath.length() > 1) {
                    if (contextPath.endsWith("/")) {
                        contextPath = contextPath + "*";
                    } else if (!contextPath.endsWith("/*")) {
                        contextPath = contextPath + "/*";
                    }
                }
                Object obj = pathMap.get(contextPath);
                String[] virtualHosts = contextHandler.getVirtualHosts();
                if (virtualHosts != null && virtualHosts.length > 0) {
                    if (obj instanceof Map) {
                        map = (Map) obj;
                    } else {
                        HashMap hashMap = new HashMap();
                        hashMap.put("*", obj);
                        pathMap.put(contextPath, hashMap);
                        map = hashMap;
                    }
                    for (String str : virtualHosts) {
                        map.put(str, LazyList.add(map.get(str), handlers[i]));
                    }
                } else if (obj instanceof Map) {
                    Map map2 = (Map) obj;
                    map2.put("*", LazyList.add(map2.get("*"), handlers[i]));
                } else {
                    pathMap.put(contextPath, LazyList.add(obj, handlers[i]));
                }
            }
            continue;
        }
        this._contextMap = pathMap;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerCollection
    public void setHandlers(Handler[] handlerArr) {
        this._contextMap = null;
        super.setHandlers(handlerArr);
        if (isStarted()) {
            mapContexts();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerCollection, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        mapContexts();
        super.doStart();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerCollection, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ContextHandler contextHandler;
        Handler[] handlers = getHandlers();
        if (!(handlers == null || handlers.length == 0)) {
            AsyncContinuation asyncContinuation = request.getAsyncContinuation();
            if (!asyncContinuation.isAsync() || (contextHandler = asyncContinuation.getContextHandler()) == null) {
                PathMap pathMap = this._contextMap;
                if (pathMap == null || str == null || !str.startsWith("/")) {
                    for (Handler handler : handlers) {
                        handler.handle(str, request, httpServletRequest, httpServletResponse);
                        if (request.isHandled()) {
                            return;
                        }
                    }
                    return;
                }
                Object lazyMatches = pathMap.getLazyMatches(str);
                for (int i = 0; i < LazyList.size(lazyMatches); i++) {
                    Object value = ((Map.Entry) LazyList.get(lazyMatches, i)).getValue();
                    if (value instanceof Map) {
                        Map map = (Map) value;
                        String normalizeHostname = normalizeHostname(httpServletRequest.getServerName());
                        Object obj = map.get(normalizeHostname);
                        for (int i2 = 0; i2 < LazyList.size(obj); i2++) {
                            ((Handler) LazyList.get(obj, i2)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (request.isHandled()) {
                                return;
                            }
                        }
                        Object obj2 = map.get("*." + normalizeHostname.substring(normalizeHostname.indexOf(".") + 1));
                        for (int i3 = 0; i3 < LazyList.size(obj2); i3++) {
                            ((Handler) LazyList.get(obj2, i3)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (request.isHandled()) {
                                return;
                            }
                        }
                        Object obj3 = map.get("*");
                        for (int i4 = 0; i4 < LazyList.size(obj3); i4++) {
                            ((Handler) LazyList.get(obj3, i4)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (request.isHandled()) {
                                return;
                            }
                        }
                        continue;
                    } else {
                        for (int i5 = 0; i5 < LazyList.size(value); i5++) {
                            ((Handler) LazyList.get(value, i5)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (request.isHandled()) {
                                return;
                            }
                        }
                        continue;
                    }
                }
                return;
            }
            contextHandler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    public ContextHandler addContext(String str, String str2) {
        try {
            ContextHandler contextHandler = (ContextHandler) this._contextClass.newInstance();
            contextHandler.setContextPath(str);
            contextHandler.setResourceBase(str2);
            addHandler(contextHandler);
            return contextHandler;
        } catch (Exception e) {
            LOG.debug(e);
            throw new Error(e);
        }
    }

    public Class getContextClass() {
        return this._contextClass;
    }

    public void setContextClass(Class cls) {
        if (cls == null || !ContextHandler.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException();
        }
        this._contextClass = cls;
    }

    private String normalizeHostname(String str) {
        if (str == null) {
            return null;
        }
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }
}
