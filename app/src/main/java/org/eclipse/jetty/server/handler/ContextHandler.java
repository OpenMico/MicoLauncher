package org.eclipse.jetty.server.handler;

import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes5.dex */
public class ContextHandler extends ScopedHandler implements Server.Graceful, Attributes {
    public static final String MANAGED_ATTRIBUTES = "org.eclipse.jetty.server.context.ManagedAttributes";
    private static final int __AVAILABLE = 1;
    private static final int __SHUTDOWN = 2;
    private static final int __STOPPED = 0;
    private static final int __UNAVAILABLE = 3;
    private boolean _aliases;
    private boolean _allowNullPathInfo;
    private final AttributesMap _attributes;
    private volatile int _availability;
    private boolean _available;
    private Resource _baseResource;
    private ClassLoader _classLoader;
    private boolean _compactPath;
    private Set<String> _connectors;
    private Object _contextAttributeListeners;
    private final AttributesMap _contextAttributes;
    private Object _contextListeners;
    private String _contextPath;
    private String _displayName;
    private ErrorHandler _errorHandler;
    private EventListener[] _eventListeners;
    private final Map<String, String> _initParams;
    private Map<String, String> _localeEncodingMap;
    private Logger _logger;
    private Map<String, Object> _managedAttributes;
    private int _maxFormContentSize;
    private int _maxFormKeys;
    private MimeTypes _mimeTypes;
    private String[] _protectedTargets;
    private Object _requestAttributeListeners;
    private Object _requestListeners;
    protected Context _scontext;
    private boolean _shutdown;
    private String[] _vhosts;
    private String[] _welcomeFiles;
    private static final Logger LOG = Log.getLogger(ContextHandler.class);
    private static final ThreadLocal<Context> __context = new ThreadLocal<>();

    public void restrictEventListener(EventListener eventListener) {
    }

    public static Context getCurrentContext() {
        return __context.get();
    }

    public ContextHandler() {
        this._contextPath = "/";
        this._maxFormKeys = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormKeys", 1000).intValue();
        this._maxFormContentSize = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormContentSize", 200000).intValue();
        this._compactPath = false;
        this._aliases = false;
        this._shutdown = false;
        this._available = true;
        this._scontext = new Context();
        this._attributes = new AttributesMap();
        this._contextAttributes = new AttributesMap();
        this._initParams = new HashMap();
    }

    public ContextHandler(Context context) {
        this._contextPath = "/";
        this._maxFormKeys = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormKeys", 1000).intValue();
        this._maxFormContentSize = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormContentSize", 200000).intValue();
        this._compactPath = false;
        this._aliases = false;
        this._shutdown = false;
        this._available = true;
        this._scontext = context;
        this._attributes = new AttributesMap();
        this._contextAttributes = new AttributesMap();
        this._initParams = new HashMap();
    }

    public ContextHandler(String str) {
        this();
        setContextPath(str);
    }

    public ContextHandler(HandlerContainer handlerContainer, String str) {
        this();
        setContextPath(str);
        if (handlerContainer instanceof HandlerWrapper) {
            ((HandlerWrapper) handlerContainer).setHandler(this);
        } else if (handlerContainer instanceof HandlerCollection) {
            ((HandlerCollection) handlerContainer).addHandler(this);
        }
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        dump(appendable, str, Collections.singletonList(new CLDump(getClassLoader())), TypeUtil.asList(getHandlers()), getBeans(), this._initParams.entrySet(), this._attributes.getAttributeEntrySet(), this._contextAttributes.getAttributeEntrySet());
    }

    public Context getServletContext() {
        return this._scontext;
    }

    public boolean getAllowNullPathInfo() {
        return this._allowNullPathInfo;
    }

    public void setAllowNullPathInfo(boolean z) {
        this._allowNullPathInfo = z;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        if (this._errorHandler != null) {
            Server server2 = getServer();
            if (!(server2 == null || server2 == server)) {
                server2.getContainer().update((Object) this, (Object) this._errorHandler, (Object) null, "error", true);
            }
            super.setServer(server);
            if (!(server == null || server == server2)) {
                server.getContainer().update((Object) this, (Object) null, (Object) this._errorHandler, "error", true);
            }
            this._errorHandler.setServer(server);
            return;
        }
        super.setServer(server);
    }

    public void setVirtualHosts(String[] strArr) {
        if (strArr == null) {
            this._vhosts = strArr;
            return;
        }
        this._vhosts = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            this._vhosts[i] = normalizeHostname(strArr[i]);
        }
    }

    public void addVirtualHosts(String[] strArr) {
        ArrayList arrayList;
        if (strArr != null) {
            String[] strArr2 = this._vhosts;
            if (strArr2 != null) {
                arrayList = new ArrayList(Arrays.asList(strArr2));
            } else {
                arrayList = new ArrayList();
            }
            for (String str : strArr) {
                String normalizeHostname = normalizeHostname(str);
                if (!arrayList.contains(normalizeHostname)) {
                    arrayList.add(normalizeHostname);
                }
            }
            this._vhosts = (String[]) arrayList.toArray(new String[0]);
        }
    }

    public void removeVirtualHosts(String[] strArr) {
        String[] strArr2;
        if (strArr != null && (strArr2 = this._vhosts) != null && strArr2.length != 0) {
            ArrayList arrayList = new ArrayList(Arrays.asList(strArr2));
            for (String str : strArr) {
                String normalizeHostname = normalizeHostname(str);
                if (arrayList.contains(normalizeHostname)) {
                    arrayList.remove(normalizeHostname);
                }
            }
            if (arrayList.isEmpty()) {
                this._vhosts = null;
            } else {
                this._vhosts = (String[]) arrayList.toArray(new String[0]);
            }
        }
    }

    public String[] getVirtualHosts() {
        return this._vhosts;
    }

    public String[] getConnectorNames() {
        Set<String> set = this._connectors;
        if (set == null || set.size() == 0) {
            return null;
        }
        Set<String> set2 = this._connectors;
        return (String[]) set2.toArray(new String[set2.size()]);
    }

    public void setConnectorNames(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            this._connectors = null;
        } else {
            this._connectors = new HashSet(Arrays.asList(strArr));
        }
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Object getAttribute(String str) {
        return this._attributes.getAttribute(str);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Enumeration getAttributeNames() {
        return AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public Attributes getAttributes() {
        return this._attributes;
    }

    public ClassLoader getClassLoader() {
        return this._classLoader;
    }

    public String getClassPath() {
        ClassLoader classLoader = this._classLoader;
        if (classLoader == null || !(classLoader instanceof URLClassLoader)) {
            return null;
        }
        URL[] uRLs = ((URLClassLoader) classLoader).getURLs();
        StringBuilder sb = new StringBuilder();
        for (URL url : uRLs) {
            try {
                File file = newResource(url).getFile();
                if (file != null && file.exists()) {
                    if (sb.length() > 0) {
                        sb.append(File.pathSeparatorChar);
                    }
                    sb.append(file.getAbsolutePath());
                }
            } catch (IOException e) {
                LOG.debug(e);
            }
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public String getContextPath() {
        return this._contextPath;
    }

    public String getInitParameter(String str) {
        return this._initParams.get(str);
    }

    public String setInitParameter(String str, String str2) {
        return this._initParams.put(str, str2);
    }

    public Enumeration getInitParameterNames() {
        return Collections.enumeration(this._initParams.keySet());
    }

    public Map<String, String> getInitParams() {
        return this._initParams;
    }

    public String getDisplayName() {
        return this._displayName;
    }

    public EventListener[] getEventListeners() {
        return this._eventListeners;
    }

    public void setEventListeners(EventListener[] eventListenerArr) {
        this._contextListeners = null;
        this._contextAttributeListeners = null;
        this._requestListeners = null;
        this._requestAttributeListeners = null;
        this._eventListeners = eventListenerArr;
        for (int i = 0; eventListenerArr != null && i < eventListenerArr.length; i++) {
            EventListener eventListener = this._eventListeners[i];
            if (eventListener instanceof ServletContextListener) {
                this._contextListeners = LazyList.add(this._contextListeners, eventListener);
            }
            if (eventListener instanceof ServletContextAttributeListener) {
                this._contextAttributeListeners = LazyList.add(this._contextAttributeListeners, eventListener);
            }
            if (eventListener instanceof ServletRequestListener) {
                this._requestListeners = LazyList.add(this._requestListeners, eventListener);
            }
            if (eventListener instanceof ServletRequestAttributeListener) {
                this._requestAttributeListeners = LazyList.add(this._requestAttributeListeners, eventListener);
            }
        }
    }

    public void addEventListener(EventListener eventListener) {
        setEventListeners((EventListener[]) LazyList.addToArray(getEventListeners(), eventListener, EventListener.class));
    }

    public boolean isShutdown() {
        boolean z;
        synchronized (this) {
            z = !this._shutdown;
        }
        return z;
    }

    @Override // org.eclipse.jetty.server.Server.Graceful
    public void setShutdown(boolean z) {
        synchronized (this) {
            this._shutdown = z;
            this._availability = isRunning() ? this._shutdown ? 2 : this._available ? 1 : 3 : 0;
        }
    }

    public boolean isAvailable() {
        boolean z;
        synchronized (this) {
            z = this._available;
        }
        return z;
    }

    public void setAvailable(boolean z) {
        synchronized (this) {
            this._available = z;
            this._availability = isRunning() ? this._shutdown ? 2 : this._available ? 1 : 3 : 0;
        }
    }

    public Logger getLogger() {
        return this._logger;
    }

    public void setLogger(Logger logger) {
        this._logger = logger;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0085  */
    @Override // org.eclipse.jetty.server.handler.ScopedHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStart() throws java.lang.Exception {
        /*
            r6 = this;
            r0 = 0
            r6._availability = r0
            java.lang.String r0 = r6._contextPath
            if (r0 == 0) goto L_0x0089
            java.lang.String r0 = r6.getDisplayName()
            if (r0 != 0) goto L_0x0012
            java.lang.String r0 = r6.getContextPath()
            goto L_0x0016
        L_0x0012:
            java.lang.String r0 = r6.getDisplayName()
        L_0x0016:
            org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.util.log.Log.getLogger(r0)
            r6._logger = r0
            r0 = 0
            java.lang.ClassLoader r1 = r6._classLoader     // Catch: all -> 0x0079
            if (r1 == 0) goto L_0x0032
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch: all -> 0x0079
            java.lang.ClassLoader r2 = r1.getContextClassLoader()     // Catch: all -> 0x002f
            java.lang.ClassLoader r3 = r6._classLoader     // Catch: all -> 0x0077
            r1.setContextClassLoader(r3)     // Catch: all -> 0x0077
            goto L_0x0034
        L_0x002f:
            r3 = move-exception
            r2 = r0
            goto L_0x007c
        L_0x0032:
            r1 = r0
            r2 = r1
        L_0x0034:
            org.eclipse.jetty.http.MimeTypes r3 = r6._mimeTypes     // Catch: all -> 0x0077
            if (r3 != 0) goto L_0x003f
            org.eclipse.jetty.http.MimeTypes r3 = new org.eclipse.jetty.http.MimeTypes     // Catch: all -> 0x0077
            r3.<init>()     // Catch: all -> 0x0077
            r6._mimeTypes = r3     // Catch: all -> 0x0077
        L_0x003f:
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r3 = org.eclipse.jetty.server.handler.ContextHandler.__context     // Catch: all -> 0x0077
            java.lang.Object r3 = r3.get()     // Catch: all -> 0x0077
            org.eclipse.jetty.server.handler.ContextHandler$Context r3 = (org.eclipse.jetty.server.handler.ContextHandler.Context) r3     // Catch: all -> 0x0077
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = org.eclipse.jetty.server.handler.ContextHandler.__context     // Catch: all -> 0x0072
            org.eclipse.jetty.server.handler.ContextHandler$Context r4 = r6._scontext     // Catch: all -> 0x0072
            r0.set(r4)     // Catch: all -> 0x0072
            r6.startContext()     // Catch: all -> 0x0072
            monitor-enter(r6)     // Catch: all -> 0x0072
            boolean r0 = r6._shutdown     // Catch: all -> 0x006f
            if (r0 == 0) goto L_0x0058
            r0 = 2
            goto L_0x005f
        L_0x0058:
            boolean r0 = r6._available     // Catch: all -> 0x006f
            if (r0 == 0) goto L_0x005e
            r0 = 1
            goto L_0x005f
        L_0x005e:
            r0 = 3
        L_0x005f:
            r6._availability = r0     // Catch: all -> 0x006f
            monitor-exit(r6)     // Catch: all -> 0x006f
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = org.eclipse.jetty.server.handler.ContextHandler.__context
            r0.set(r3)
            java.lang.ClassLoader r0 = r6._classLoader
            if (r0 == 0) goto L_0x006e
            r1.setContextClassLoader(r2)
        L_0x006e:
            return
        L_0x006f:
            r0 = move-exception
            monitor-exit(r6)     // Catch: all -> 0x006f
            throw r0     // Catch: all -> 0x0072
        L_0x0072:
            r0 = move-exception
            r5 = r3
            r3 = r0
            r0 = r5
            goto L_0x007c
        L_0x0077:
            r3 = move-exception
            goto L_0x007c
        L_0x0079:
            r3 = move-exception
            r1 = r0
            r2 = r1
        L_0x007c:
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r4 = org.eclipse.jetty.server.handler.ContextHandler.__context
            r4.set(r0)
            java.lang.ClassLoader r0 = r6._classLoader
            if (r0 == 0) goto L_0x0088
            r1.setContextClassLoader(r2)
        L_0x0088:
            throw r3
        L_0x0089:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Null contextPath"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doStart():void");
    }

    public void startContext() throws Exception {
        String str = this._initParams.get(MANAGED_ATTRIBUTES);
        if (str != null) {
            this._managedAttributes = new HashMap();
            for (String str2 : str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
                this._managedAttributes.put(str2, null);
            }
            Enumeration attributeNames = this._scontext.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String str3 = (String) attributeNames.nextElement();
                checkManagedAttribute(str3, this._scontext.getAttribute(str3));
            }
        }
        super.doStart();
        ErrorHandler errorHandler = this._errorHandler;
        if (errorHandler != null) {
            errorHandler.start();
        }
        if (this._contextListeners != null) {
            ServletContextEvent servletContextEvent = new ServletContextEvent(this._scontext);
            for (int i = 0; i < LazyList.size(this._contextListeners); i++) {
                callContextInitialized((ServletContextListener) LazyList.get(this._contextListeners, i), servletContextEvent);
            }
        }
    }

    public void callContextInitialized(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        servletContextListener.contextInitialized(servletContextEvent);
        LOG.info("started {}", this);
    }

    public void callContextDestroyed(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        servletContextListener.contextDestroyed(servletContextEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a9  */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStop() throws java.lang.Exception {
        /*
            r10 = this;
            r0 = 0
            r10._availability = r0
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r1 = org.eclipse.jetty.server.handler.ContextHandler.__context
            java.lang.Object r1 = r1.get()
            org.eclipse.jetty.server.handler.ContextHandler$Context r1 = (org.eclipse.jetty.server.handler.ContextHandler.Context) r1
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r2 = org.eclipse.jetty.server.handler.ContextHandler.__context
            org.eclipse.jetty.server.handler.ContextHandler$Context r3 = r10._scontext
            r2.set(r3)
            r2 = 1
            r3 = 0
            java.lang.ClassLoader r4 = r10._classLoader     // Catch: all -> 0x0091
            if (r4 == 0) goto L_0x002c
            java.lang.Thread r4 = java.lang.Thread.currentThread()     // Catch: all -> 0x0091
            java.lang.ClassLoader r5 = r4.getContextClassLoader()     // Catch: all -> 0x0026
            java.lang.ClassLoader r6 = r10._classLoader     // Catch: all -> 0x008f
            r4.setContextClassLoader(r6)     // Catch: all -> 0x008f
            goto L_0x002e
        L_0x0026:
            r5 = move-exception
            r9 = r5
            r5 = r3
            r3 = r9
            goto L_0x0095
        L_0x002c:
            r4 = r3
            r5 = r4
        L_0x002e:
            super.doStop()     // Catch: all -> 0x008f
            java.lang.Object r6 = r10._contextListeners     // Catch: all -> 0x008f
            if (r6 == 0) goto L_0x0053
            javax.servlet.ServletContextEvent r6 = new javax.servlet.ServletContextEvent     // Catch: all -> 0x008f
            org.eclipse.jetty.server.handler.ContextHandler$Context r7 = r10._scontext     // Catch: all -> 0x008f
            r6.<init>(r7)     // Catch: all -> 0x008f
            java.lang.Object r7 = r10._contextListeners     // Catch: all -> 0x008f
            int r7 = org.eclipse.jetty.util.LazyList.size(r7)     // Catch: all -> 0x008f
        L_0x0042:
            int r8 = r7 + (-1)
            if (r7 <= 0) goto L_0x0053
            java.lang.Object r7 = r10._contextListeners     // Catch: all -> 0x008f
            java.lang.Object r7 = org.eclipse.jetty.util.LazyList.get(r7, r8)     // Catch: all -> 0x008f
            javax.servlet.ServletContextListener r7 = (javax.servlet.ServletContextListener) r7     // Catch: all -> 0x008f
            r7.contextDestroyed(r6)     // Catch: all -> 0x008f
            r7 = r8
            goto L_0x0042
        L_0x0053:
            org.eclipse.jetty.server.handler.ErrorHandler r6 = r10._errorHandler     // Catch: all -> 0x008f
            if (r6 == 0) goto L_0x005c
            org.eclipse.jetty.server.handler.ErrorHandler r6 = r10._errorHandler     // Catch: all -> 0x008f
            r6.stop()     // Catch: all -> 0x008f
        L_0x005c:
            org.eclipse.jetty.server.handler.ContextHandler$Context r6 = r10._scontext     // Catch: all -> 0x008f
            java.util.Enumeration r6 = r6.getAttributeNames()     // Catch: all -> 0x008f
        L_0x0062:
            boolean r7 = r6.hasMoreElements()     // Catch: all -> 0x008f
            if (r7 == 0) goto L_0x0072
            java.lang.Object r7 = r6.nextElement()     // Catch: all -> 0x008f
            java.lang.String r7 = (java.lang.String) r7     // Catch: all -> 0x008f
            r10.checkManagedAttribute(r7, r3)     // Catch: all -> 0x008f
            goto L_0x0062
        L_0x0072:
            org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.server.handler.ContextHandler.LOG
            java.lang.String r6 = "stopped {}"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r0] = r10
            r3.info(r6, r2)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = org.eclipse.jetty.server.handler.ContextHandler.__context
            r0.set(r1)
            java.lang.ClassLoader r0 = r10._classLoader
            if (r0 == 0) goto L_0x0089
            r4.setContextClassLoader(r5)
        L_0x0089:
            org.eclipse.jetty.util.AttributesMap r0 = r10._contextAttributes
            r0.clearAttributes()
            return
        L_0x008f:
            r3 = move-exception
            goto L_0x0095
        L_0x0091:
            r4 = move-exception
            r5 = r3
            r3 = r4
            r4 = r5
        L_0x0095:
            org.eclipse.jetty.util.log.Logger r6 = org.eclipse.jetty.server.handler.ContextHandler.LOG
            java.lang.String r7 = "stopped {}"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r0] = r10
            r6.info(r7, r2)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = org.eclipse.jetty.server.handler.ContextHandler.__context
            r0.set(r1)
            java.lang.ClassLoader r0 = r10._classLoader
            if (r0 == 0) goto L_0x00ac
            r4.setContextClassLoader(r5)
        L_0x00ac:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doStop():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0060 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkContext(java.lang.String r12, org.eclipse.jetty.server.Request r13, javax.servlet.http.HttpServletResponse r14) throws java.io.IOException, javax.servlet.ServletException {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.checkContext(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletResponse):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x016d  */
    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doScope(java.lang.String r18, org.eclipse.jetty.server.Request r19, javax.servlet.http.HttpServletRequest r20, javax.servlet.http.HttpServletResponse r21) throws java.io.IOException, javax.servlet.ServletException {
        /*
            Method dump skipped, instructions count: 390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doScope(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        DispatcherType dispatcherType = request.getDispatcherType();
        boolean takeNewContext = request.takeNewContext();
        try {
            if (takeNewContext) {
                try {
                    if (this._requestAttributeListeners != null) {
                        int size = LazyList.size(this._requestAttributeListeners);
                        for (int i = 0; i < size; i++) {
                            request.addEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i));
                        }
                    }
                    if (this._requestListeners != null) {
                        int size2 = LazyList.size(this._requestListeners);
                        ServletRequestEvent servletRequestEvent = new ServletRequestEvent(this._scontext, httpServletRequest);
                        for (int i2 = 0; i2 < size2; i2++) {
                            ((ServletRequestListener) LazyList.get(this._requestListeners, i2)).requestInitialized(servletRequestEvent);
                        }
                    }
                } catch (HttpException e) {
                    LOG.debug(e);
                    request.setHandled(true);
                    httpServletResponse.sendError(e.getStatus(), e.getReason());
                    if (takeNewContext) {
                        if (this._requestListeners != null) {
                            ServletRequestEvent servletRequestEvent2 = new ServletRequestEvent(this._scontext, httpServletRequest);
                            int size3 = LazyList.size(this._requestListeners);
                            while (true) {
                                int i3 = size3 - 1;
                                if (size3 <= 0) {
                                    break;
                                }
                                ((ServletRequestListener) LazyList.get(this._requestListeners, i3)).requestDestroyed(servletRequestEvent2);
                                size3 = i3;
                            }
                        }
                        Object obj = this._requestAttributeListeners;
                        if (obj != null) {
                            int size4 = LazyList.size(obj);
                            while (true) {
                                int i4 = size4 - 1;
                                if (size4 > 0) {
                                    request.removeEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i4));
                                    size4 = i4;
                                } else {
                                    return;
                                }
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
            if (DispatcherType.REQUEST.equals(dispatcherType) && isProtectedTarget(str)) {
                throw new HttpException(404);
            }
            if (never()) {
                nextHandle(str, request, httpServletRequest, httpServletResponse);
            } else if (this._nextScope != null && this._nextScope == this._handler) {
                this._nextScope.doHandle(str, request, httpServletRequest, httpServletResponse);
            } else if (this._handler != null) {
                this._handler.handle(str, request, httpServletRequest, httpServletResponse);
            }
            if (takeNewContext) {
                if (this._requestListeners != null) {
                    ServletRequestEvent servletRequestEvent3 = new ServletRequestEvent(this._scontext, httpServletRequest);
                    int size5 = LazyList.size(this._requestListeners);
                    while (true) {
                        int i5 = size5 - 1;
                        if (size5 <= 0) {
                            break;
                        }
                        ((ServletRequestListener) LazyList.get(this._requestListeners, i5)).requestDestroyed(servletRequestEvent3);
                        size5 = i5;
                    }
                }
                Object obj2 = this._requestAttributeListeners;
                if (obj2 != null) {
                    int size6 = LazyList.size(obj2);
                    while (true) {
                        int i6 = size6 - 1;
                        if (size6 > 0) {
                            request.removeEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i6));
                            size6 = i6;
                        } else {
                            return;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            if (takeNewContext) {
                if (this._requestListeners != null) {
                    ServletRequestEvent servletRequestEvent4 = new ServletRequestEvent(this._scontext, httpServletRequest);
                    int size7 = LazyList.size(this._requestListeners);
                    while (true) {
                        int i7 = size7 - 1;
                        if (size7 <= 0) {
                            break;
                        }
                        ((ServletRequestListener) LazyList.get(this._requestListeners, i7)).requestDestroyed(servletRequestEvent4);
                        size7 = i7;
                    }
                }
                Object obj3 = this._requestAttributeListeners;
                if (obj3 != null) {
                    int size8 = LazyList.size(obj3);
                    while (true) {
                        int i8 = size8 - 1;
                        if (size8 <= 0) {
                            break;
                        }
                        request.removeEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i8));
                        size8 = i8;
                    }
                }
            }
            throw th;
        }
    }

    public void handle(Runnable runnable) {
        Throwable th;
        Thread thread;
        Context context;
        ClassLoader classLoader = null;
        try {
            context = __context.get();
            try {
                __context.set(this._scontext);
                if (this._classLoader != null) {
                    thread = Thread.currentThread();
                    try {
                        classLoader = thread.getContextClassLoader();
                        thread.setContextClassLoader(this._classLoader);
                    } catch (Throwable th2) {
                        th = th2;
                        __context.set(context);
                        if (classLoader != null) {
                            thread.setContextClassLoader(classLoader);
                        }
                        throw th;
                    }
                } else {
                    thread = null;
                }
                runnable.run();
                __context.set(context);
                if (classLoader != null) {
                    thread.setContextClassLoader(classLoader);
                }
            } catch (Throwable th3) {
                th = th3;
                thread = null;
            }
        } catch (Throwable th4) {
            th = th4;
            context = null;
            thread = null;
        }
    }

    public boolean isProtectedTarget(String str) {
        boolean z = false;
        if (str == null || this._protectedTargets == null) {
            return false;
        }
        while (str.startsWith("//")) {
            str = URIUtil.compactPath(str);
        }
        int i = 0;
        while (!z) {
            String[] strArr = this._protectedTargets;
            if (i >= strArr.length) {
                break;
            }
            i++;
            z = StringUtil.startsWithIgnoreCase(str, strArr[i]);
        }
        return z;
    }

    public void setProtectedTargets(String[] strArr) {
        if (strArr == null) {
            this._protectedTargets = null;
            return;
        }
        this._protectedTargets = new String[strArr.length];
        System.arraycopy(strArr, 0, this._protectedTargets, 0, strArr.length);
    }

    public String[] getProtectedTargets() {
        String[] strArr = this._protectedTargets;
        if (strArr == null) {
            return null;
        }
        String[] strArr2 = new String[strArr.length];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void removeAttribute(String str) {
        checkManagedAttribute(str, null);
        this._attributes.removeAttribute(str);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void setAttribute(String str, Object obj) {
        checkManagedAttribute(str, obj);
        this._attributes.setAttribute(str, obj);
    }

    public void setAttributes(Attributes attributes) {
        this._attributes.clearAttributes();
        this._attributes.addAll(attributes);
        Enumeration<String> attributeNames = this._attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            checkManagedAttribute(nextElement, attributes.getAttribute(nextElement));
        }
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void clearAttributes() {
        Enumeration<String> attributeNames = this._attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            checkManagedAttribute(attributeNames.nextElement(), null);
        }
        this._attributes.clearAttributes();
    }

    public void checkManagedAttribute(String str, Object obj) {
        Map<String, Object> map = this._managedAttributes;
        if (map != null && map.containsKey(str)) {
            setManagedAttribute(str, obj);
        }
    }

    public void setManagedAttribute(String str, Object obj) {
        getServer().getContainer().update((Object) this, this._managedAttributes.put(str, obj), obj, str, true);
    }

    public void setClassLoader(ClassLoader classLoader) {
        this._classLoader = classLoader;
    }

    public void setContextPath(String str) {
        if (str == null || str.length() <= 1 || !str.endsWith("/")) {
            this._contextPath = str;
            if (getServer() == null) {
                return;
            }
            if (getServer().isStarting() || getServer().isStarted()) {
                Handler[] childHandlersByClass = getServer().getChildHandlersByClass(ContextHandlerCollection.class);
                for (int i = 0; childHandlersByClass != null && i < childHandlersByClass.length; i++) {
                    ((ContextHandlerCollection) childHandlersByClass[i]).mapContexts();
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("ends with /");
    }

    public void setDisplayName(String str) {
        this._displayName = str;
    }

    public Resource getBaseResource() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource;
    }

    public String getResourceBase() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource.toString();
    }

    public void setBaseResource(Resource resource) {
        this._baseResource = resource;
    }

    public void setResourceBase(String str) {
        try {
            setBaseResource(newResource(str));
        } catch (Exception e) {
            LOG.warn(e.toString(), new Object[0]);
            LOG.debug(e);
            throw new IllegalArgumentException(str);
        }
    }

    public boolean isAliases() {
        return this._aliases;
    }

    public void setAliases(boolean z) {
        this._aliases = z;
    }

    public MimeTypes getMimeTypes() {
        if (this._mimeTypes == null) {
            this._mimeTypes = new MimeTypes();
        }
        return this._mimeTypes;
    }

    public void setMimeTypes(MimeTypes mimeTypes) {
        this._mimeTypes = mimeTypes;
    }

    public void setWelcomeFiles(String[] strArr) {
        this._welcomeFiles = strArr;
    }

    public String[] getWelcomeFiles() {
        return this._welcomeFiles;
    }

    public ErrorHandler getErrorHandler() {
        return this._errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler != null) {
            errorHandler.setServer(getServer());
        }
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object) this._errorHandler, (Object) errorHandler, "errorHandler", true);
        }
        this._errorHandler = errorHandler;
    }

    public int getMaxFormContentSize() {
        return this._maxFormContentSize;
    }

    public void setMaxFormContentSize(int i) {
        this._maxFormContentSize = i;
    }

    public int getMaxFormKeys() {
        return this._maxFormKeys;
    }

    public void setMaxFormKeys(int i) {
        this._maxFormKeys = i;
    }

    public boolean isCompactPath() {
        return this._compactPath;
    }

    public void setCompactPath(boolean z) {
        this._compactPath = z;
    }

    public String toString() {
        String name;
        String[] virtualHosts = getVirtualHosts();
        StringBuilder sb = new StringBuilder();
        Package r2 = getClass().getPackage();
        if (!(r2 == null || (name = r2.getName()) == null || name.length() <= 0)) {
            for (String str : name.split("\\.")) {
                sb.append(str.charAt(0));
                sb.append('.');
            }
        }
        sb.append(getClass().getSimpleName());
        sb.append('{');
        sb.append(getContextPath());
        sb.append(io.netty.util.internal.StringUtil.COMMA);
        sb.append(getBaseResource());
        if (virtualHosts != null && virtualHosts.length > 0) {
            sb.append(io.netty.util.internal.StringUtil.COMMA);
            sb.append(virtualHosts[0]);
        }
        sb.append('}');
        return sb.toString();
    }

    public synchronized Class<?> loadClass(String str) throws ClassNotFoundException {
        if (str == null) {
            return null;
        }
        if (this._classLoader == null) {
            return Loader.loadClass(getClass(), str);
        }
        return this._classLoader.loadClass(str);
    }

    public void addLocaleEncoding(String str, String str2) {
        if (this._localeEncodingMap == null) {
            this._localeEncodingMap = new HashMap();
        }
        this._localeEncodingMap.put(str, str2);
    }

    public String getLocaleEncoding(String str) {
        Map<String, String> map = this._localeEncodingMap;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public String getLocaleEncoding(Locale locale) {
        Map<String, String> map = this._localeEncodingMap;
        if (map == null) {
            return null;
        }
        String str = map.get(locale.toString());
        return str == null ? this._localeEncodingMap.get(locale.getLanguage()) : str;
    }

    public Resource getResource(String str) throws MalformedURLException {
        if (str == null || !str.startsWith("/")) {
            throw new MalformedURLException(str);
        } else if (this._baseResource == null) {
            return null;
        } else {
            try {
                String canonicalPath = URIUtil.canonicalPath(str);
                Resource addPath = this._baseResource.addPath(canonicalPath);
                if (this._aliases || addPath.getAlias() == null) {
                    return addPath;
                }
                if (addPath.exists()) {
                    Logger logger = LOG;
                    logger.warn("Aliased resource: " + addPath + "~=" + addPath.getAlias(), new Object[0]);
                } else if (canonicalPath.endsWith("/") && addPath.getAlias().toString().endsWith(canonicalPath)) {
                    return addPath;
                } else {
                    if (LOG.isDebugEnabled()) {
                        Logger logger2 = LOG;
                        logger2.debug("Aliased resource: " + addPath + "~=" + addPath.getAlias(), new Object[0]);
                    }
                }
                return null;
            } catch (Exception e) {
                LOG.ignore(e);
                return null;
            }
        }
    }

    public Resource newResource(URL url) throws IOException {
        return Resource.newResource(url);
    }

    public Resource newResource(String str) throws IOException {
        return Resource.newResource(str);
    }

    public Set<String> getResourcePaths(String str) {
        try {
            String canonicalPath = URIUtil.canonicalPath(str);
            Resource resource = getResource(canonicalPath);
            if (resource != null && resource.exists()) {
                if (!canonicalPath.endsWith("/")) {
                    canonicalPath = canonicalPath + "/";
                }
                String[] list = resource.list();
                if (list != null) {
                    HashSet hashSet = new HashSet();
                    for (int i = 0; i < list.length; i++) {
                        hashSet.add(canonicalPath + list[i]);
                    }
                    return hashSet;
                }
            }
        } catch (Exception e) {
            LOG.ignore(e);
        }
        return Collections.emptySet();
    }

    private String normalizeHostname(String str) {
        if (str == null) {
            return null;
        }
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }

    /* loaded from: classes5.dex */
    public class Context implements ServletContext {
        private static final String __unimplmented = "Unimplemented - use org.eclipse.jetty.servlet.ServletContextHandler";
        protected int _majorVersion = 3;
        protected int _minorVersion = 0;
        protected boolean _enabled = true;

        @Override // javax.servlet.ServletContext
        public int getMajorVersion() {
            return 3;
        }

        @Override // javax.servlet.ServletContext
        public int getMinorVersion() {
            return 0;
        }

        @Override // javax.servlet.ServletContext
        public RequestDispatcher getNamedDispatcher(String str) {
            return null;
        }

        @Override // javax.servlet.ServletContext
        @Deprecated
        public Servlet getServlet(String str) throws ServletException {
            return null;
        }

        public void setJspConfigDescriptor(JspConfigDescriptor jspConfigDescriptor) {
        }

        public Context() {
            ContextHandler.this = r1;
        }

        public ContextHandler getContextHandler() {
            return ContextHandler.this;
        }

        @Override // javax.servlet.ServletContext
        public ServletContext getContext(String str) {
            String str2;
            Context context = this;
            ArrayList arrayList = new ArrayList();
            Handler[] childHandlersByClass = ContextHandler.this.getServer().getChildHandlersByClass(ContextHandler.class);
            int length = childHandlersByClass.length;
            int i = 0;
            String str3 = null;
            while (i < length) {
                Handler handler = childHandlersByClass[i];
                if (handler != null) {
                    ContextHandler contextHandler = (ContextHandler) handler;
                    String contextPath = contextHandler.getContextPath();
                    if (str.equals(contextPath) || ((str.startsWith(contextPath) && str.charAt(contextPath.length()) == '/') || "/".equals(contextPath))) {
                        if (ContextHandler.this.getVirtualHosts() == null || ContextHandler.this.getVirtualHosts().length <= 0) {
                            if (str3 == null || contextPath.length() > str3.length()) {
                                arrayList.clear();
                                str3 = contextPath;
                            }
                            if (str3.equals(contextPath)) {
                                arrayList.add(contextHandler);
                            }
                        } else if (contextHandler.getVirtualHosts() != null && contextHandler.getVirtualHosts().length > 0) {
                            String[] virtualHosts = ContextHandler.this.getVirtualHosts();
                            int length2 = virtualHosts.length;
                            String str4 = str3;
                            int i2 = 0;
                            while (i2 < length2) {
                                String str5 = virtualHosts[i2];
                                String str6 = str4;
                                for (String str7 : contextHandler.getVirtualHosts()) {
                                    if (str5.equals(str7)) {
                                        if (str6 == null || contextPath.length() > str6.length()) {
                                            arrayList.clear();
                                            str2 = contextPath;
                                        } else {
                                            str2 = str6;
                                        }
                                        if (str2.equals(contextPath)) {
                                            arrayList.add(contextHandler);
                                        }
                                        str6 = str2;
                                    }
                                }
                                i2++;
                                str4 = str6;
                            }
                            str3 = str4;
                        }
                    }
                }
                i++;
                context = this;
            }
            if (arrayList.size() > 0) {
                return ((ContextHandler) arrayList.get(0))._scontext;
            }
            String str8 = null;
            for (Handler handler2 : childHandlersByClass) {
                if (handler2 != null) {
                    ContextHandler contextHandler2 = (ContextHandler) handler2;
                    String contextPath2 = contextHandler2.getContextPath();
                    if (str.equals(contextPath2) || ((str.startsWith(contextPath2) && str.charAt(contextPath2.length()) == '/') || "/".equals(contextPath2))) {
                        if (str8 == null || contextPath2.length() > str8.length()) {
                            arrayList.clear();
                            str8 = contextPath2;
                        }
                        if (str8.equals(contextPath2)) {
                            arrayList.add(contextHandler2);
                        }
                    }
                }
            }
            if (arrayList.size() > 0) {
                return ((ContextHandler) arrayList.get(0))._scontext;
            }
            return null;
        }

        @Override // javax.servlet.ServletContext
        public String getMimeType(String str) {
            Buffer mimeByExtension;
            if (ContextHandler.this._mimeTypes == null || (mimeByExtension = ContextHandler.this._mimeTypes.getMimeByExtension(str)) == null) {
                return null;
            }
            return mimeByExtension.toString();
        }

        @Override // javax.servlet.ServletContext
        public RequestDispatcher getRequestDispatcher(String str) {
            String str2;
            if (str == null || !str.startsWith("/")) {
                return null;
            }
            try {
                int indexOf = str.indexOf(63);
                if (indexOf > 0) {
                    str2 = str.substring(indexOf + 1);
                    str = str.substring(0, indexOf);
                } else {
                    str2 = null;
                }
                return new Dispatcher(ContextHandler.this, URIUtil.addPaths(getContextPath(), str), URIUtil.canonicalPath(URIUtil.decodePath(str)), str2);
            } catch (Exception e) {
                ContextHandler.LOG.ignore(e);
                return null;
            }
        }

        @Override // javax.servlet.ServletContext
        public String getRealPath(String str) {
            File file;
            if (str == null) {
                return null;
            }
            if (str.length() == 0) {
                str = "/";
            } else if (str.charAt(0) != '/') {
                str = "/" + str;
            }
            try {
                Resource resource = ContextHandler.this.getResource(str);
                if (!(resource == null || (file = resource.getFile()) == null)) {
                    return file.getCanonicalPath();
                }
            } catch (Exception e) {
                ContextHandler.LOG.ignore(e);
            }
            return null;
        }

        @Override // javax.servlet.ServletContext
        public URL getResource(String str) throws MalformedURLException {
            Resource resource = ContextHandler.this.getResource(str);
            if (resource == null || !resource.exists()) {
                return null;
            }
            return resource.getURL();
        }

        @Override // javax.servlet.ServletContext
        public InputStream getResourceAsStream(String str) {
            try {
                URL resource = getResource(str);
                if (resource == null) {
                    return null;
                }
                return Resource.newResource(resource).getInputStream();
            } catch (Exception e) {
                ContextHandler.LOG.ignore(e);
                return null;
            }
        }

        @Override // javax.servlet.ServletContext
        public Set getResourcePaths(String str) {
            return ContextHandler.this.getResourcePaths(str);
        }

        @Override // javax.servlet.ServletContext
        public String getServerInfo() {
            return "jetty/" + Server.getVersion();
        }

        @Override // javax.servlet.ServletContext
        @Deprecated
        public Enumeration getServletNames() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }

        @Override // javax.servlet.ServletContext
        @Deprecated
        public Enumeration getServlets() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }

        @Override // javax.servlet.ServletContext
        public void log(Exception exc, String str) {
            ContextHandler.this._logger.warn(str, exc);
        }

        @Override // javax.servlet.ServletContext
        public void log(String str) {
            ContextHandler.this._logger.info(str, new Object[0]);
        }

        @Override // javax.servlet.ServletContext
        public void log(String str, Throwable th) {
            ContextHandler.this._logger.warn(str, th);
        }

        @Override // javax.servlet.ServletContext
        public String getInitParameter(String str) {
            return ContextHandler.this.getInitParameter(str);
        }

        @Override // javax.servlet.ServletContext
        public Enumeration getInitParameterNames() {
            return ContextHandler.this.getInitParameterNames();
        }

        @Override // javax.servlet.ServletContext
        public synchronized Object getAttribute(String str) {
            Object attribute;
            attribute = ContextHandler.this.getAttribute(str);
            if (attribute == null && ContextHandler.this._contextAttributes != null) {
                attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            }
            return attribute;
        }

        @Override // javax.servlet.ServletContext
        public synchronized Enumeration getAttributeNames() {
            HashSet hashSet;
            hashSet = new HashSet();
            if (ContextHandler.this._contextAttributes != null) {
                Enumeration<String> attributeNames = ContextHandler.this._contextAttributes.getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    hashSet.add(attributeNames.nextElement());
                }
            }
            Enumeration<String> attributeNames2 = ContextHandler.this._attributes.getAttributeNames();
            while (attributeNames2.hasMoreElements()) {
                hashSet.add(attributeNames2.nextElement());
            }
            return Collections.enumeration(hashSet);
        }

        @Override // javax.servlet.ServletContext
        public synchronized void setAttribute(String str, Object obj) {
            ContextHandler.this.checkManagedAttribute(str, obj);
            Object attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            if (obj == null) {
                ContextHandler.this._contextAttributes.removeAttribute(str);
            } else {
                ContextHandler.this._contextAttributes.setAttribute(str, obj);
            }
            if (ContextHandler.this._contextAttributeListeners != null) {
                ServletContextAttributeEvent servletContextAttributeEvent = new ServletContextAttributeEvent(ContextHandler.this._scontext, str, attribute == null ? obj : attribute);
                for (int i = 0; i < LazyList.size(ContextHandler.this._contextAttributeListeners); i++) {
                    ServletContextAttributeListener servletContextAttributeListener = (ServletContextAttributeListener) LazyList.get(ContextHandler.this._contextAttributeListeners, i);
                    if (attribute == null) {
                        servletContextAttributeListener.attributeAdded(servletContextAttributeEvent);
                    } else if (obj == null) {
                        servletContextAttributeListener.attributeRemoved(servletContextAttributeEvent);
                    } else {
                        servletContextAttributeListener.attributeReplaced(servletContextAttributeEvent);
                    }
                }
            }
        }

        @Override // javax.servlet.ServletContext
        public synchronized void removeAttribute(String str) {
            ContextHandler.this.checkManagedAttribute(str, null);
            if (ContextHandler.this._contextAttributes == null) {
                ContextHandler.this._attributes.removeAttribute(str);
                return;
            }
            Object attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            ContextHandler.this._contextAttributes.removeAttribute(str);
            if (!(attribute == null || ContextHandler.this._contextAttributeListeners == null)) {
                ServletContextAttributeEvent servletContextAttributeEvent = new ServletContextAttributeEvent(ContextHandler.this._scontext, str, attribute);
                for (int i = 0; i < LazyList.size(ContextHandler.this._contextAttributeListeners); i++) {
                    ((ServletContextAttributeListener) LazyList.get(ContextHandler.this._contextAttributeListeners, i)).attributeRemoved(servletContextAttributeEvent);
                }
            }
        }

        @Override // javax.servlet.ServletContext
        public String getServletContextName() {
            String displayName = ContextHandler.this.getDisplayName();
            return displayName == null ? ContextHandler.this.getContextPath() : displayName;
        }

        @Override // javax.servlet.ServletContext
        public String getContextPath() {
            return (ContextHandler.this._contextPath == null || !ContextHandler.this._contextPath.equals("/")) ? ContextHandler.this._contextPath : "";
        }

        public String toString() {
            return "ServletContext@" + ContextHandler.this.toString();
        }

        @Override // javax.servlet.ServletContext
        public boolean setInitParameter(String str, String str2) {
            if (ContextHandler.this.getInitParameter(str) != null) {
                return false;
            }
            ContextHandler.this.getInitParams().put(str, str2);
            return true;
        }

        @Override // javax.servlet.ServletContext
        public FilterRegistration.Dynamic addFilter(String str, Class<? extends Filter> cls) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public FilterRegistration.Dynamic addFilter(String str, Filter filter) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public FilterRegistration.Dynamic addFilter(String str, String str2) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public ServletRegistration.Dynamic addServlet(String str, Class<? extends Servlet> cls) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public ServletRegistration.Dynamic addServlet(String str, Servlet servlet) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public ServletRegistration.Dynamic addServlet(String str, String str2) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public <T extends Filter> T createFilter(Class<T> cls) throws ServletException {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public <T extends Servlet> T createServlet(Class<T> cls) throws ServletException {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public FilterRegistration getFilterRegistration(String str) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public ServletRegistration getServletRegistration(String str) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public Map<String, ? extends ServletRegistration> getServletRegistrations() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public SessionCookieConfig getSessionCookieConfig() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public void setSessionTrackingModes(Set<SessionTrackingMode> set) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // javax.servlet.ServletContext
        public void addListener(String str) {
            if (this._enabled) {
                try {
                    addListener((Class<? extends EventListener>) (ContextHandler.this._classLoader == null ? Loader.loadClass(ContextHandler.class, str) : ContextHandler.this._classLoader.loadClass(str)));
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        @Override // javax.servlet.ServletContext
        public <T extends EventListener> void addListener(T t) {
            if (this._enabled) {
                ContextHandler.this.addEventListener(t);
                return;
            }
            throw new UnsupportedOperationException();
        }

        @Override // javax.servlet.ServletContext
        public void addListener(Class<? extends EventListener> cls) {
            if (this._enabled) {
                try {
                    EventListener createListener = createListener(cls);
                    ContextHandler.this.addEventListener(createListener);
                    ContextHandler.this.restrictEventListener(createListener);
                } catch (ServletException e) {
                    throw new IllegalArgumentException(e);
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        @Override // javax.servlet.ServletContext
        public <T extends EventListener> T createListener(Class<T> cls) throws ServletException {
            try {
                return cls.newInstance();
            } catch (IllegalAccessException e) {
                throw new ServletException(e);
            } catch (InstantiationException e2) {
                throw new ServletException(e2);
            }
        }

        @Override // javax.servlet.ServletContext
        public ClassLoader getClassLoader() {
            AccessController.checkPermission(new RuntimePermission("getClassLoader"));
            return ContextHandler.this._classLoader;
        }

        @Override // javax.servlet.ServletContext
        public int getEffectiveMajorVersion() {
            return this._majorVersion;
        }

        @Override // javax.servlet.ServletContext
        public int getEffectiveMinorVersion() {
            return this._minorVersion;
        }

        public void setEffectiveMajorVersion(int i) {
            this._majorVersion = i;
        }

        public void setEffectiveMinorVersion(int i) {
            this._minorVersion = i;
        }

        @Override // javax.servlet.ServletContext
        public JspConfigDescriptor getJspConfigDescriptor() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Override // javax.servlet.ServletContext
        public void declareRoles(String... strArr) {
            if (!ContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
        }

        public void setEnabled(boolean z) {
            this._enabled = z;
        }

        public boolean isEnabled() {
            return this._enabled;
        }
    }

    /* loaded from: classes5.dex */
    private static class CLDump implements Dumpable {
        final ClassLoader _loader;

        CLDump(ClassLoader classLoader) {
            this._loader = classLoader;
        }

        @Override // org.eclipse.jetty.util.component.Dumpable
        public String dump() {
            return AggregateLifeCycle.dump(this);
        }

        @Override // org.eclipse.jetty.util.component.Dumpable
        public void dump(Appendable appendable, String str) throws IOException {
            Object parent;
            appendable.append(String.valueOf(this._loader)).append("\n");
            ClassLoader classLoader = this._loader;
            if (classLoader != null && (parent = classLoader.getParent()) != null) {
                if (!(parent instanceof Dumpable)) {
                    parent = new CLDump((ClassLoader) parent);
                }
                ClassLoader classLoader2 = this._loader;
                if (classLoader2 instanceof URLClassLoader) {
                    AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(((URLClassLoader) classLoader2).getURLs()), Collections.singleton(parent));
                } else {
                    AggregateLifeCycle.dump(appendable, str, Collections.singleton(parent));
                }
            }
        }
    }
}
