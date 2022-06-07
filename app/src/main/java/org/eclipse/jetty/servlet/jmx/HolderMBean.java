package org.eclipse.jetty.servlet.jmx;

import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.servlet.Holder;

/* loaded from: classes5.dex */
public class HolderMBean extends ObjectMBean {
    public HolderMBean(Object obj) {
        super(obj);
    }

    public String getObjectNameBasis() {
        String name;
        return (this._managed == null || !(this._managed instanceof Holder) || (name = ((Holder) this._managed).getName()) == null) ? HolderMBean.super.getObjectNameBasis() : name;
    }
}
