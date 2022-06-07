package org.eclipse.jetty.servlet.jmx;

import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.servlet.FilterMapping;

/* loaded from: classes5.dex */
public class FilterMappingMBean extends ObjectMBean {
    public FilterMappingMBean(Object obj) {
        super(obj);
    }

    public String getObjectNameBasis() {
        String filterName;
        return (this._managed == null || !(this._managed instanceof FilterMapping) || (filterName = ((FilterMapping) this._managed).getFilterName()) == null) ? FilterMappingMBean.super.getObjectNameBasis() : filterName;
    }
}
