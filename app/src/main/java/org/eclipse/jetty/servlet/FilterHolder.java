package org.eclipse.jetty.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletException;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class FilterHolder extends Holder<Filter> {
    private static final Logger LOG = Log.getLogger(FilterHolder.class);
    private transient Config _config;
    private transient Filter _filter;
    private transient FilterRegistration.Dynamic _registration;

    public FilterHolder() {
        super(Holder.Source.EMBEDDED);
    }

    public FilterHolder(Holder.Source source) {
        super(source);
    }

    public FilterHolder(Class<? extends Filter> cls) {
        super(Holder.Source.EMBEDDED);
        setHeldClass(cls);
    }

    public FilterHolder(Filter filter) {
        super(Holder.Source.EMBEDDED);
        setFilter(filter);
    }

    @Override // org.eclipse.jetty.servlet.Holder, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        if (Filter.class.isAssignableFrom(this._class)) {
            if (this._filter == null) {
                try {
                    this._filter = ((ServletContextHandler.Context) this._servletHandler.getServletContext()).createFilter(getHeldClass());
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
            this._config = new Config();
            this._filter.init(this._config);
            return;
        }
        String str = this._class + " is not a javax.servlet.Filter";
        super.stop();
        throw new IllegalStateException(str);
    }

    @Override // org.eclipse.jetty.servlet.Holder, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        Filter filter = this._filter;
        if (filter != null) {
            try {
                destroyInstance(filter);
            } catch (Exception e) {
                LOG.warn(e);
            }
        }
        if (!this._extInstance) {
            this._filter = null;
        }
        this._config = null;
        super.doStop();
    }

    @Override // org.eclipse.jetty.servlet.Holder
    public void destroyInstance(Object obj) throws Exception {
        if (obj != null) {
            Filter filter = (Filter) obj;
            filter.destroy();
            getServletHandler().destroyFilter(filter);
        }
    }

    public synchronized void setFilter(Filter filter) {
        this._filter = filter;
        this._extInstance = true;
        setHeldClass(filter.getClass());
        if (getName() == null) {
            setName(filter.getClass().getName());
        }
    }

    public Filter getFilter() {
        return this._filter;
    }

    @Override // org.eclipse.jetty.servlet.Holder
    public String toString() {
        return getName();
    }

    public FilterRegistration.Dynamic getRegistration() {
        if (this._registration == null) {
            this._registration = new Registration();
        }
        return this._registration;
    }

    /* loaded from: classes5.dex */
    public class Registration extends Holder<Filter>.HolderRegistration implements FilterRegistration.Dynamic {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected Registration() {
            super();
            FilterHolder.this = r1;
        }

        @Override // javax.servlet.FilterRegistration
        public void addMappingForServletNames(EnumSet<DispatcherType> enumSet, boolean z, String... strArr) {
            FilterHolder.this.illegalStateIfContextStarted();
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterHolder(FilterHolder.this);
            filterMapping.setServletNames(strArr);
            filterMapping.setDispatcherTypes(enumSet);
            if (z) {
                FilterHolder.this._servletHandler.addFilterMapping(filterMapping);
            } else {
                FilterHolder.this._servletHandler.prependFilterMapping(filterMapping);
            }
        }

        @Override // javax.servlet.FilterRegistration
        public void addMappingForUrlPatterns(EnumSet<DispatcherType> enumSet, boolean z, String... strArr) {
            FilterHolder.this.illegalStateIfContextStarted();
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterHolder(FilterHolder.this);
            filterMapping.setPathSpecs(strArr);
            filterMapping.setDispatcherTypes(enumSet);
            if (z) {
                FilterHolder.this._servletHandler.addFilterMapping(filterMapping);
            } else {
                FilterHolder.this._servletHandler.prependFilterMapping(filterMapping);
            }
        }

        @Override // javax.servlet.FilterRegistration
        public Collection<String> getServletNameMappings() {
            String[] servletNames;
            FilterMapping[] filterMappings = FilterHolder.this._servletHandler.getFilterMappings();
            ArrayList arrayList = new ArrayList();
            for (FilterMapping filterMapping : filterMappings) {
                if (filterMapping.getFilterHolder() == FilterHolder.this && (servletNames = filterMapping.getServletNames()) != null && servletNames.length > 0) {
                    arrayList.addAll(Arrays.asList(servletNames));
                }
            }
            return arrayList;
        }

        @Override // javax.servlet.FilterRegistration
        public Collection<String> getUrlPatternMappings() {
            FilterMapping[] filterMappings = FilterHolder.this._servletHandler.getFilterMappings();
            ArrayList arrayList = new ArrayList();
            for (FilterMapping filterMapping : filterMappings) {
                if (filterMapping.getFilterHolder() == FilterHolder.this) {
                    arrayList.addAll(TypeUtil.asList(filterMapping.getPathSpecs()));
                }
            }
            return arrayList;
        }
    }

    /* loaded from: classes5.dex */
    class Config extends Holder<Filter>.HolderConfig implements FilterConfig {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        Config() {
            super();
            FilterHolder.this = r1;
        }

        @Override // javax.servlet.FilterConfig
        public String getFilterName() {
            return FilterHolder.this._name;
        }
    }
}
