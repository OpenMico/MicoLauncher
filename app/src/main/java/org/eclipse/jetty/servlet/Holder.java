package org.eclipse.jetty.servlet;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.Registration;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class Holder<T> extends AbstractLifeCycle implements Dumpable {
    private static final Logger LOG = Log.getLogger(Holder.class);
    protected transient Class<? extends T> _class;
    protected String _className;
    protected String _displayName;
    protected boolean _extInstance;
    protected String _name;
    protected ServletHandler _servletHandler;
    private final Source _source;
    protected final Map<String, String> _initParams = new HashMap(3);
    protected boolean _asyncSupported = true;

    /* loaded from: classes5.dex */
    public enum Source {
        EMBEDDED,
        JAVAX_API,
        DESCRIPTOR,
        ANNOTATION
    }

    public void destroyInstance(Object obj) throws Exception {
    }

    public Holder(Source source) {
        this._source = source;
    }

    public Source getSource() {
        return this._source;
    }

    public boolean isInstance() {
        return this._extInstance;
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        String str;
        if (this._class == null && ((str = this._className) == null || str.equals(""))) {
            throw new UnavailableException("No class for Servlet or Filter for " + this._name, -1);
        } else if (this._class == null) {
            try {
                this._class = Loader.loadClass(Holder.class, this._className);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Holding {}", this._class);
                }
            } catch (Exception e) {
                LOG.warn(e);
                throw new UnavailableException(e.getMessage(), -1);
            }
        }
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        if (!this._extInstance) {
            this._class = null;
        }
    }

    public String getClassName() {
        return this._className;
    }

    public Class<? extends T> getHeldClass() {
        return this._class;
    }

    public String getDisplayName() {
        return this._displayName;
    }

    public String getInitParameter(String str) {
        Map<String, String> map = this._initParams;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public Enumeration getInitParameterNames() {
        Map<String, String> map = this._initParams;
        if (map == null) {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }
        return Collections.enumeration(map.keySet());
    }

    public Map<String, String> getInitParameters() {
        return this._initParams;
    }

    public String getName() {
        return this._name;
    }

    public ServletHandler getServletHandler() {
        return this._servletHandler;
    }

    public void setClassName(String str) {
        this._className = str;
        this._class = null;
    }

    public void setHeldClass(Class<? extends T> cls) {
        this._class = cls;
        if (cls != null) {
            this._className = cls.getName();
            if (this._name == null) {
                this._name = cls.getName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + hashCode();
            }
        }
    }

    public void setDisplayName(String str) {
        this._displayName = str;
    }

    public void setInitParameter(String str, String str2) {
        this._initParams.put(str, str2);
    }

    public void setInitParameters(Map<String, String> map) {
        this._initParams.clear();
        this._initParams.putAll(map);
    }

    public void setName(String str) {
        this._name = str;
    }

    public void setServletHandler(ServletHandler servletHandler) {
        this._servletHandler = servletHandler;
    }

    public void setAsyncSupported(boolean z) {
        this._asyncSupported = z;
    }

    public boolean isAsyncSupported() {
        return this._asyncSupported;
    }

    public String toString() {
        return this._name;
    }

    protected void illegalStateIfContextStarted() {
        ContextHandler.Context context;
        ServletHandler servletHandler = this._servletHandler;
        if (servletHandler != null && (context = (ContextHandler.Context) servletHandler.getServletContext()) != null && context.getContextHandler().isStarted()) {
            throw new IllegalStateException("Started");
        }
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        appendable.append(this._name).append("==").append(this._className).append(" - ").append(AbstractLifeCycle.getState(this)).append("\n");
        AggregateLifeCycle.dump(appendable, str, this._initParams.entrySet());
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes5.dex */
    public class HolderConfig {
        public HolderConfig() {
            Holder.this = r1;
        }

        public ServletContext getServletContext() {
            return Holder.this._servletHandler.getServletContext();
        }

        public String getInitParameter(String str) {
            return Holder.this.getInitParameter(str);
        }

        public Enumeration getInitParameterNames() {
            return Holder.this.getInitParameterNames();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes5.dex */
    public class HolderRegistration implements Registration.Dynamic {
        public HolderRegistration() {
            Holder.this = r1;
        }

        @Override // javax.servlet.Registration.Dynamic
        public void setAsyncSupported(boolean z) {
            Holder.this.illegalStateIfContextStarted();
            Holder.this.setAsyncSupported(z);
        }

        public void setDescription(String str) {
            if (Holder.LOG.isDebugEnabled()) {
                Logger logger = Holder.LOG;
                logger.debug(this + " is " + str, new Object[0]);
            }
        }

        @Override // javax.servlet.Registration
        public String getClassName() {
            return Holder.this.getClassName();
        }

        @Override // javax.servlet.Registration
        public String getInitParameter(String str) {
            return Holder.this.getInitParameter(str);
        }

        @Override // javax.servlet.Registration
        public Map<String, String> getInitParameters() {
            return Holder.this.getInitParameters();
        }

        @Override // javax.servlet.Registration
        public String getName() {
            return Holder.this.getName();
        }

        @Override // javax.servlet.Registration
        public boolean setInitParameter(String str, String str2) {
            Holder.this.illegalStateIfContextStarted();
            if (str == null) {
                throw new IllegalArgumentException("init parameter name required");
            } else if (str2 == null) {
                throw new IllegalArgumentException("non-null value required for init parameter " + str);
            } else if (Holder.this.getInitParameter(str) != null) {
                return false;
            } else {
                Holder.this.setInitParameter(str, str2);
                return true;
            }
        }

        @Override // javax.servlet.Registration
        public Set<String> setInitParameters(Map<String, String> map) {
            Holder.this.illegalStateIfContextStarted();
            HashSet hashSet = null;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey() == null) {
                    throw new IllegalArgumentException("init parameter name required");
                } else if (entry.getValue() == null) {
                    throw new IllegalArgumentException("non-null value required for init parameter " + entry.getKey());
                } else if (Holder.this.getInitParameter(entry.getKey()) != null) {
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(entry.getKey());
                }
            }
            if (hashSet != null) {
                return hashSet;
            }
            Holder.this.getInitParameters().putAll(map);
            return Collections.emptySet();
        }
    }
}
