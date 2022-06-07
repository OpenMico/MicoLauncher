package org.eclipse.jetty.servlet;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.SingleThreadModel;
import javax.servlet.UnavailableException;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.RunAsToken;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class ServletHolder extends Holder<Servlet> implements Comparable, UserIdentity.Scope {
    private static final Logger LOG = Log.getLogger(ServletHolder.class);
    public static final Map<String, String> NO_MAPPED_ROLES = Collections.emptyMap();
    private transient Config _config;
    private String _forcedPath;
    private IdentityService _identityService;
    private int _initOrder;
    private ServletRegistration.Dynamic _registration;
    private Map<String, String> _roleMap;
    private String _runAsRole;
    private RunAsToken _runAsToken;
    private transient Servlet _servlet;
    private transient long _unavailable;
    private transient UnavailableException _unavailableEx;
    private boolean _initOnStartup = false;
    private transient boolean _enabled = true;

    public ServletHolder() {
        super(Holder.Source.EMBEDDED);
    }

    public ServletHolder(Holder.Source source) {
        super(source);
    }

    public ServletHolder(Servlet servlet) {
        super(Holder.Source.EMBEDDED);
        setServlet(servlet);
    }

    public ServletHolder(String str, Class<? extends Servlet> cls) {
        super(Holder.Source.EMBEDDED);
        setName(str);
        setHeldClass(cls);
    }

    public ServletHolder(String str, Servlet servlet) {
        super(Holder.Source.EMBEDDED);
        setName(str);
        setServlet(servlet);
    }

    public ServletHolder(Class<? extends Servlet> cls) {
        super(Holder.Source.EMBEDDED);
        setHeldClass(cls);
    }

    public UnavailableException getUnavailableException() {
        return this._unavailableEx;
    }

    public synchronized void setServlet(Servlet servlet) {
        if (servlet != null) {
            if (!(servlet instanceof SingleThreadModel)) {
                this._extInstance = true;
                this._servlet = servlet;
                setHeldClass(servlet.getClass());
                if (getName() == null) {
                    setName(servlet.getClass().getName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + super.hashCode());
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public int getInitOrder() {
        return this._initOrder;
    }

    public void setInitOrder(int i) {
        this._initOnStartup = true;
        this._initOrder = i;
    }

    public boolean isSetInitOrder() {
        return this._initOnStartup;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (!(obj instanceof ServletHolder)) {
            return 1;
        }
        ServletHolder servletHolder = (ServletHolder) obj;
        int i = 0;
        if (servletHolder == this) {
            return 0;
        }
        int i2 = servletHolder._initOrder;
        int i3 = this._initOrder;
        if (i2 < i3) {
            return 1;
        }
        if (i2 > i3) {
            return -1;
        }
        if (!(this._className == null || servletHolder._className == null)) {
            i = this._className.compareTo(servletHolder._className);
        }
        if (i == 0) {
            i = this._name.compareTo(servletHolder._name);
        }
        return i == 0 ? hashCode() > obj.hashCode() ? 1 : -1 : i;
    }

    public boolean equals(Object obj) {
        return compareTo(obj) == 0;
    }

    public int hashCode() {
        return this._name == null ? System.identityHashCode(this) : this._name.hashCode();
    }

    public synchronized void setUserRoleLink(String str, String str2) {
        if (this._roleMap == null) {
            this._roleMap = new HashMap();
        }
        this._roleMap.put(str, str2);
    }

    public String getUserRoleLink(String str) {
        String str2;
        Map<String, String> map = this._roleMap;
        return (map == null || (str2 = map.get(str)) == null) ? str : str2;
    }

    public Map<String, String> getRoleMap() {
        Map<String, String> map = this._roleMap;
        return map == null ? NO_MAPPED_ROLES : map;
    }

    public String getForcedPath() {
        return this._forcedPath;
    }

    public void setForcedPath(String str) {
        this._forcedPath = str;
    }

    public boolean isEnabled() {
        return this._enabled;
    }

    public void setEnabled(boolean z) {
        this._enabled = z;
    }

    @Override // org.eclipse.jetty.servlet.Holder, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        String str;
        this._unavailable = 0L;
        if (this._enabled) {
            try {
                super.doStart();
                try {
                    checkServletType();
                } catch (UnavailableException e) {
                    makeUnavailable(e);
                    if (!this._servletHandler.isStartWithUnavailable()) {
                        throw e;
                    }
                }
                this._identityService = this._servletHandler.getIdentityService();
                IdentityService identityService = this._identityService;
                if (!(identityService == null || (str = this._runAsRole) == null)) {
                    this._runAsToken = identityService.newRunAsToken(str);
                }
                this._config = new Config();
                if (this._class != null && SingleThreadModel.class.isAssignableFrom(this._class)) {
                    this._servlet = new SingleThreadedWrapper();
                }
                if (this._extInstance || this._initOnStartup) {
                    try {
                        initServlet();
                    } catch (Exception e2) {
                        if (this._servletHandler.isStartWithUnavailable()) {
                            LOG.ignore(e2);
                            return;
                        }
                        throw e2;
                    }
                }
            } catch (UnavailableException e3) {
                makeUnavailable(e3);
                throw e3;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004f  */
    @Override // org.eclipse.jetty.servlet.Holder, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStop() throws java.lang.Exception {
        /*
            r5 = this;
            javax.servlet.Servlet r0 = r5._servlet
            r1 = 0
            if (r0 == 0) goto L_0x004b
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService     // Catch: Exception -> 0x0032, all -> 0x0030
            if (r0 == 0) goto L_0x0018
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService     // Catch: Exception -> 0x0032, all -> 0x0030
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService     // Catch: Exception -> 0x0032, all -> 0x0030
            org.eclipse.jetty.server.UserIdentity r2 = r2.getSystemUserIdentity()     // Catch: Exception -> 0x0032, all -> 0x0030
            org.eclipse.jetty.security.RunAsToken r3 = r5._runAsToken     // Catch: Exception -> 0x0032, all -> 0x0030
            java.lang.Object r0 = r0.setRunAs(r2, r3)     // Catch: Exception -> 0x0032, all -> 0x0030
            goto L_0x0019
        L_0x0018:
            r0 = r1
        L_0x0019:
            javax.servlet.Servlet r2 = r5._servlet     // Catch: Exception -> 0x002b, all -> 0x0026
            r5.destroyInstance(r2)     // Catch: Exception -> 0x002b, all -> 0x0026
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService
            if (r2 == 0) goto L_0x004b
            r2.unsetRunAs(r0)
            goto L_0x004b
        L_0x0026:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0043
        L_0x002b:
            r2 = move-exception
            r4 = r2
            r2 = r0
            r0 = r4
            goto L_0x0034
        L_0x0030:
            r0 = move-exception
            goto L_0x0043
        L_0x0032:
            r0 = move-exception
            r2 = r1
        L_0x0034:
            org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.servlet.ServletHolder.LOG     // Catch: all -> 0x0041
            r3.warn(r0)     // Catch: all -> 0x0041
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService
            if (r0 == 0) goto L_0x004b
            r0.unsetRunAs(r2)
            goto L_0x004b
        L_0x0041:
            r0 = move-exception
            r1 = r2
        L_0x0043:
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService
            if (r2 == 0) goto L_0x004a
            r2.unsetRunAs(r1)
        L_0x004a:
            throw r0
        L_0x004b:
            boolean r0 = r5._extInstance
            if (r0 != 0) goto L_0x0051
            r5._servlet = r1
        L_0x0051:
            r5._config = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHolder.doStop():void");
    }

    @Override // org.eclipse.jetty.servlet.Holder
    public void destroyInstance(Object obj) throws Exception {
        if (obj != null) {
            Servlet servlet = (Servlet) obj;
            servlet.destroy();
            getServletHandler().destroyServlet(servlet);
        }
    }

    public synchronized Servlet getServlet() throws ServletException {
        if (this._unavailable != 0) {
            if (this._unavailable < 0 || (this._unavailable > 0 && System.currentTimeMillis() < this._unavailable)) {
                throw this._unavailableEx;
            }
            this._unavailable = 0L;
            this._unavailableEx = null;
        }
        if (this._servlet == null) {
            initServlet();
        }
        return this._servlet;
    }

    public Servlet getServletInstance() {
        return this._servlet;
    }

    public void checkServletType() throws UnavailableException {
        if (this._class == null || !Servlet.class.isAssignableFrom(this._class)) {
            throw new UnavailableException("Servlet " + this._class + " is not a javax.servlet.Servlet");
        }
    }

    public boolean isAvailable() {
        if (isStarted() && this._unavailable == 0) {
            return true;
        }
        try {
            getServlet();
        } catch (Exception e) {
            LOG.ignore(e);
        }
        return isStarted() && this._unavailable == 0;
    }

    private void makeUnavailable(UnavailableException unavailableException) {
        if (this._unavailableEx != unavailableException || this._unavailable == 0) {
            this._servletHandler.getServletContext().log("unavailable", unavailableException);
            this._unavailableEx = unavailableException;
            this._unavailable = -1L;
            if (unavailableException.isPermanent()) {
                this._unavailable = -1L;
            } else if (this._unavailableEx.getUnavailableSeconds() > 0) {
                this._unavailable = System.currentTimeMillis() + (this._unavailableEx.getUnavailableSeconds() * 1000);
            } else {
                this._unavailable = System.currentTimeMillis() + 5000;
            }
        }
    }

    private void makeUnavailable(final Throwable th) {
        if (th instanceof UnavailableException) {
            makeUnavailable((UnavailableException) th);
            return;
        }
        ServletContext servletContext = this._servletHandler.getServletContext();
        if (servletContext == null) {
            LOG.info("unavailable", th);
        } else {
            servletContext.log("unavailable", th);
        }
        this._unavailableEx = new UnavailableException(String.valueOf(th), -1) { // from class: org.eclipse.jetty.servlet.ServletHolder.1
            {
                initCause(th);
            }
        };
        this._unavailable = -1L;
    }

    private void initServlet() throws ServletException {
        Object obj;
        Throwable th;
        UnavailableException e;
        ServletException e2;
        Exception e3;
        Object obj2;
        try {
            try {
                if (this._servlet == null) {
                    this._servlet = newInstance();
                }
                if (this._config == null) {
                    this._config = new Config();
                }
                if (this._identityService != null) {
                    obj2 = this._identityService.setRunAs(this._identityService.getSystemUserIdentity(), this._runAsToken);
                } else {
                    obj2 = null;
                }
                try {
                    if (isJspServlet()) {
                        initJspServlet();
                    }
                    initMultiPart();
                    this._servlet.init(this._config);
                    IdentityService identityService = this._identityService;
                    if (identityService != null) {
                        identityService.unsetRunAs(obj2);
                    }
                } catch (UnavailableException e4) {
                    e = e4;
                    makeUnavailable(e);
                    this._servlet = null;
                    this._config = null;
                    throw e;
                } catch (ServletException e5) {
                    e2 = e5;
                    makeUnavailable(e2.getCause() == null ? e2 : e2.getCause());
                    this._servlet = null;
                    this._config = null;
                    throw e2;
                } catch (Exception e6) {
                    e3 = e6;
                    makeUnavailable(e3);
                    this._servlet = null;
                    this._config = null;
                    throw new ServletException(toString(), e3);
                } catch (Throwable th2) {
                    th = th2;
                    obj = obj2;
                    IdentityService identityService2 = this._identityService;
                    if (identityService2 != null) {
                        identityService2.unsetRunAs(obj);
                    }
                    throw th;
                }
            } catch (UnavailableException e7) {
                e = e7;
            } catch (ServletException e8) {
                e2 = e8;
            } catch (Exception e9) {
                e3 = e9;
            } catch (Throwable th3) {
                th = th3;
                obj = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    protected void initJspServlet() throws Exception {
        ContextHandler contextHandler = ((ContextHandler.Context) getServletHandler().getServletContext()).getContextHandler();
        contextHandler.setAttribute("org.apache.catalina.jsp_classpath", contextHandler.getClassPath());
        setInitParameter("com.sun.appserv.jsp.classpath", Loader.getClassPath(contextHandler.getClassLoader().getParent()));
        if ("?".equals(getInitParameter("classpath"))) {
            String classPath = contextHandler.getClassPath();
            Logger logger = LOG;
            logger.debug("classpath=" + classPath, new Object[0]);
            if (classPath != null) {
                setInitParameter("classpath", classPath);
            }
        }
    }

    protected void initMultiPart() throws Exception {
        if (((Registration) getRegistration()).getMultipartConfig() != null) {
            ((ContextHandler.Context) getServletHandler().getServletContext()).getContextHandler().addEventListener(new Request.MultiPartCleanerListener());
        }
    }

    @Override // org.eclipse.jetty.server.UserIdentity.Scope
    public String getContextPath() {
        return this._config.getServletContext().getContextPath();
    }

    @Override // org.eclipse.jetty.server.UserIdentity.Scope
    public Map<String, String> getRoleRefMap() {
        return this._roleMap;
    }

    public String getRunAsRole() {
        return this._runAsRole;
    }

    public void setRunAsRole(String str) {
        this._runAsRole = str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0011, code lost:
        if (r1 == 0) goto L_0x0013;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v8, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r7v4, types: [org.eclipse.jetty.security.IdentityService] */
    /* JADX WARN: Type inference failed for: r7v6, types: [org.eclipse.jetty.security.IdentityService] */
    /* JADX WARN: Unknown variable types count: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handle(org.eclipse.jetty.server.Request r7, javax.servlet.ServletRequest r8, javax.servlet.ServletResponse r9) throws javax.servlet.ServletException, javax.servlet.UnavailableException, java.io.IOException {
        /*
            r6 = this;
            java.lang.Class r0 = r6._class
            if (r0 == 0) goto L_0x009c
            javax.servlet.Servlet r0 = r6._servlet
            monitor-enter(r6)
            long r1 = r6._unavailable     // Catch: all -> 0x0099
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x0013
            boolean r1 = r6._initOnStartup     // Catch: all -> 0x0099
            if (r1 != 0) goto L_0x0017
        L_0x0013:
            javax.servlet.Servlet r0 = r6.getServlet()     // Catch: all -> 0x0099
        L_0x0017:
            if (r0 == 0) goto L_0x0080
            monitor-exit(r6)     // Catch: all -> 0x0099
            r1 = 0
            boolean r2 = r7.isAsyncSupported()
            java.lang.String r3 = r6._forcedPath     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            if (r3 == 0) goto L_0x002a
            java.lang.String r3 = "org.apache.catalina.jsp_file"
            java.lang.String r4 = r6._forcedPath     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            r8.setAttribute(r3, r4)     // Catch: UnavailableException -> 0x0065, all -> 0x0063
        L_0x002a:
            org.eclipse.jetty.security.IdentityService r3 = r6._identityService     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            if (r3 == 0) goto L_0x003a
            org.eclipse.jetty.security.IdentityService r3 = r6._identityService     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            org.eclipse.jetty.server.UserIdentity r4 = r7.getResolvedUserIdentity()     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            org.eclipse.jetty.security.RunAsToken r5 = r6._runAsToken     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            java.lang.Object r1 = r3.setRunAs(r4, r5)     // Catch: UnavailableException -> 0x0065, all -> 0x0063
        L_0x003a:
            boolean r3 = r6.isAsyncSupported()     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            if (r3 != 0) goto L_0x0044
            r3 = 0
            r7.setAsyncSupported(r3)     // Catch: UnavailableException -> 0x0065, all -> 0x0063
        L_0x0044:
            javax.servlet.ServletRegistration$Dynamic r3 = r6.getRegistration()     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            org.eclipse.jetty.servlet.ServletHolder$Registration r3 = (org.eclipse.jetty.servlet.ServletHolder.Registration) r3     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            javax.servlet.MultipartConfigElement r3 = r3.getMultipartConfig()     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            if (r3 == 0) goto L_0x0055
            java.lang.String r4 = "org.eclipse.multipartConfig"
            r8.setAttribute(r4, r3)     // Catch: UnavailableException -> 0x0065, all -> 0x0063
        L_0x0055:
            r0.service(r8, r9)     // Catch: UnavailableException -> 0x0065, all -> 0x0063
            r7.setAsyncSupported(r2)
            org.eclipse.jetty.security.IdentityService r7 = r6._identityService
            if (r7 == 0) goto L_0x0062
            r7.unsetRunAs(r1)
        L_0x0062:
            return
        L_0x0063:
            r9 = move-exception
            goto L_0x006c
        L_0x0065:
            r9 = move-exception
            r6.makeUnavailable(r9)     // Catch: all -> 0x0063
            javax.servlet.UnavailableException r9 = r6._unavailableEx     // Catch: all -> 0x0063
            throw r9     // Catch: all -> 0x0063
        L_0x006c:
            r7.setAsyncSupported(r2)
            org.eclipse.jetty.security.IdentityService r7 = r6._identityService
            if (r7 == 0) goto L_0x0076
            r7.unsetRunAs(r1)
        L_0x0076:
            java.lang.String r7 = r6.getName()
            java.lang.String r0 = "javax.servlet.error.servlet_name"
            r8.setAttribute(r0, r7)
            throw r9
        L_0x0080:
            javax.servlet.UnavailableException r7 = new javax.servlet.UnavailableException     // Catch: all -> 0x0099
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: all -> 0x0099
            r8.<init>()     // Catch: all -> 0x0099
            java.lang.String r9 = "Could not instantiate "
            r8.append(r9)     // Catch: all -> 0x0099
            java.lang.Class r9 = r6._class     // Catch: all -> 0x0099
            r8.append(r9)     // Catch: all -> 0x0099
            java.lang.String r8 = r8.toString()     // Catch: all -> 0x0099
            r7.<init>(r8)     // Catch: all -> 0x0099
            throw r7     // Catch: all -> 0x0099
        L_0x0099:
            r7 = move-exception
            monitor-exit(r6)     // Catch: all -> 0x0099
            throw r7
        L_0x009c:
            javax.servlet.UnavailableException r7 = new javax.servlet.UnavailableException
            java.lang.String r8 = "Servlet Not Initialized"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHolder.handle(org.eclipse.jetty.server.Request, javax.servlet.ServletRequest, javax.servlet.ServletResponse):void");
    }

    private boolean isJspServlet() {
        Servlet servlet = this._servlet;
        boolean z = false;
        if (servlet == null) {
            return false;
        }
        for (Class<?> cls = servlet.getClass(); cls != null && !z; cls = cls.getSuperclass()) {
            z = isJspServlet(cls.getName());
        }
        return z;
    }

    private boolean isJspServlet(String str) {
        if (str == null) {
            return false;
        }
        return "org.apache.jasper.servlet.JspServlet".equals(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes5.dex */
    public class Config extends Holder<Servlet>.HolderConfig implements ServletConfig {
        protected Config() {
            super();
        }

        @Override // javax.servlet.ServletConfig
        public String getServletName() {
            return ServletHolder.this.getName();
        }
    }

    /* loaded from: classes5.dex */
    public class Registration extends Holder<Servlet>.HolderRegistration implements ServletRegistration.Dynamic {
        protected MultipartConfigElement _multipartConfig;

        public Registration() {
            super();
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration, javax.servlet.Registration
        public /* bridge */ /* synthetic */ String getClassName() {
            return super.getClassName();
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration, javax.servlet.Registration
        public /* bridge */ /* synthetic */ String getInitParameter(String str) {
            return super.getInitParameter(str);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration, javax.servlet.Registration
        public /* bridge */ /* synthetic */ Map getInitParameters() {
            return super.getInitParameters();
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration, javax.servlet.Registration
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration, javax.servlet.Registration.Dynamic
        public /* bridge */ /* synthetic */ void setAsyncSupported(boolean z) {
            super.setAsyncSupported(z);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ void setDescription(String str) {
            super.setDescription(str);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration, javax.servlet.Registration
        public /* bridge */ /* synthetic */ boolean setInitParameter(String str, String str2) {
            return super.setInitParameter(str, str2);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration, javax.servlet.Registration
        public /* bridge */ /* synthetic */ Set setInitParameters(Map map) {
            return super.setInitParameters(map);
        }

        @Override // javax.servlet.ServletRegistration
        public Set<String> addMapping(String... strArr) {
            ServletHolder.this.illegalStateIfContextStarted();
            HashSet hashSet = null;
            for (String str : strArr) {
                ServletMapping servletMapping = ServletHolder.this._servletHandler.getServletMapping(str);
                if (servletMapping != null && !servletMapping.isDefault()) {
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(str);
                }
            }
            if (hashSet != null) {
                return hashSet;
            }
            ServletMapping servletMapping2 = new ServletMapping();
            servletMapping2.setServletName(ServletHolder.this.getName());
            servletMapping2.setPathSpecs(strArr);
            ServletHolder.this._servletHandler.addServletMapping(servletMapping2);
            return Collections.emptySet();
        }

        @Override // javax.servlet.ServletRegistration
        public Collection<String> getMappings() {
            String[] pathSpecs;
            ServletMapping[] servletMappings = ServletHolder.this._servletHandler.getServletMappings();
            ArrayList arrayList = new ArrayList();
            if (servletMappings != null) {
                for (ServletMapping servletMapping : servletMappings) {
                    if (servletMapping.getServletName().equals(getName()) && (pathSpecs = servletMapping.getPathSpecs()) != null && pathSpecs.length > 0) {
                        arrayList.addAll(Arrays.asList(pathSpecs));
                    }
                }
            }
            return arrayList;
        }

        @Override // javax.servlet.ServletRegistration
        public String getRunAsRole() {
            return ServletHolder.this._runAsRole;
        }

        @Override // javax.servlet.ServletRegistration.Dynamic
        public void setLoadOnStartup(int i) {
            ServletHolder.this.illegalStateIfContextStarted();
            ServletHolder.this.setInitOrder(i);
        }

        public int getInitOrder() {
            return ServletHolder.this.getInitOrder();
        }

        @Override // javax.servlet.ServletRegistration.Dynamic
        public void setMultipartConfig(MultipartConfigElement multipartConfigElement) {
            this._multipartConfig = multipartConfigElement;
        }

        public MultipartConfigElement getMultipartConfig() {
            return this._multipartConfig;
        }

        @Override // javax.servlet.ServletRegistration.Dynamic
        public void setRunAsRole(String str) {
            ServletHolder.this._runAsRole = str;
        }

        @Override // javax.servlet.ServletRegistration.Dynamic
        public Set<String> setServletSecurity(ServletSecurityElement servletSecurityElement) {
            return ServletHolder.this._servletHandler.setServletSecurity(this, servletSecurityElement);
        }
    }

    public ServletRegistration.Dynamic getRegistration() {
        if (this._registration == null) {
            this._registration = new Registration();
        }
        return this._registration;
    }

    /* loaded from: classes5.dex */
    private class SingleThreadedWrapper implements Servlet {
        Stack<Servlet> _stack;

        @Override // javax.servlet.Servlet
        public String getServletInfo() {
            return null;
        }

        private SingleThreadedWrapper() {
            this._stack = new Stack<>();
        }

        @Override // javax.servlet.Servlet
        public void destroy() {
            synchronized (this) {
                while (this._stack.size() > 0) {
                    try {
                        this._stack.pop().destroy();
                    } catch (Exception e) {
                        ServletHolder.LOG.warn(e);
                    }
                }
            }
        }

        @Override // javax.servlet.Servlet
        public ServletConfig getServletConfig() {
            return ServletHolder.this._config;
        }

        @Override // javax.servlet.Servlet
        public void init(ServletConfig servletConfig) throws ServletException {
            synchronized (this) {
                if (this._stack.size() == 0) {
                    try {
                        Servlet newInstance = ServletHolder.this.newInstance();
                        newInstance.init(servletConfig);
                        this._stack.push(newInstance);
                    } catch (ServletException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new ServletException(e2);
                    }
                }
            }
        }

        @Override // javax.servlet.Servlet
        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
            Servlet newInstance;
            synchronized (this) {
                try {
                    if (this._stack.size() > 0) {
                        newInstance = this._stack.pop();
                    } else {
                        try {
                            newInstance = ServletHolder.this.newInstance();
                            newInstance.init(ServletHolder.this._config);
                        } catch (ServletException e) {
                            throw e;
                        } catch (Exception e2) {
                            throw new ServletException(e2);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            try {
                newInstance.service(servletRequest, servletResponse);
                synchronized (this) {
                    try {
                        this._stack.push(newInstance);
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                synchronized (this) {
                    try {
                        this._stack.push(newInstance);
                        throw th3;
                    } catch (Throwable th4) {
                        throw th4;
                    }
                }
            }
        }
    }

    protected Servlet newInstance() throws ServletException, IllegalAccessException, InstantiationException {
        try {
            ServletContext servletContext = getServletHandler().getServletContext();
            if (servletContext == null) {
                return (Servlet) getHeldClass().newInstance();
            }
            return ((ServletContextHandler.Context) servletContext).createServlet(getHeldClass());
        } catch (ServletException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof InstantiationException) {
                throw ((InstantiationException) rootCause);
            } else if (rootCause instanceof IllegalAccessException) {
                throw ((IllegalAccessException) rootCause);
            } else {
                throw e;
            }
        }
    }
}
