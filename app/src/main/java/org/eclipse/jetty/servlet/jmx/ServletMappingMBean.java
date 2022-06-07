package org.eclipse.jetty.servlet.jmx;

import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.servlet.ServletMapping;

/* loaded from: classes5.dex */
public class ServletMappingMBean extends ObjectMBean {
    public ServletMappingMBean(Object obj) {
        super(obj);
    }

    public String getObjectNameBasis() {
        String servletName;
        return (this._managed == null || !(this._managed instanceof ServletMapping) || (servletName = ((ServletMapping) this._managed).getServletName()) == null) ? ServletMappingMBean.super.getObjectNameBasis() : servletName;
    }
}
