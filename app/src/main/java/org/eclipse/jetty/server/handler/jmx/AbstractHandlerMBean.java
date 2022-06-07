package org.eclipse.jetty.server.handler.jmx;

import java.io.IOException;
import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.AbstractHandlerContainer;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class AbstractHandlerMBean extends ObjectMBean {
    private static final Logger LOG = Log.getLogger(AbstractHandlerMBean.class);

    public AbstractHandlerMBean(Object obj) {
        super(obj);
    }

    public String getObjectContextBasis() {
        AbstractHandler abstractHandler;
        Server server;
        ContextHandler contextHandler;
        if (this._managed != null) {
            String str = null;
            if (this._managed instanceof ContextHandler) {
                return null;
            }
            if (!(!(this._managed instanceof AbstractHandler) || (server = (abstractHandler = (AbstractHandler) this._managed).getServer()) == null || (contextHandler = (ContextHandler) AbstractHandlerContainer.findContainerOf(server, ContextHandler.class, abstractHandler)) == null)) {
                str = getContextName(contextHandler);
            }
            if (str != null) {
                return str;
            }
        }
        return AbstractHandlerMBean.super.getObjectContextBasis();
    }

    public String getObjectNameBasis() {
        if (this._managed != null) {
            String str = null;
            if (this._managed instanceof ContextHandler) {
                str = getContextName((ContextHandler) this._managed);
            }
            if (str != null) {
                return str;
            }
        }
        return AbstractHandlerMBean.super.getObjectNameBasis();
    }

    protected String getContextName(ContextHandler contextHandler) {
        String str;
        if (contextHandler.getContextPath() == null || contextHandler.getContextPath().length() <= 0) {
            str = null;
        } else {
            int lastIndexOf = contextHandler.getContextPath().lastIndexOf(47);
            str = lastIndexOf < 0 ? contextHandler.getContextPath() : contextHandler.getContextPath().substring(lastIndexOf + 1);
            if (str == null || str.length() == 0) {
                str = org.slf4j.Logger.ROOT_LOGGER_NAME;
            }
        }
        if (str != null || contextHandler.getBaseResource() == null) {
            return str;
        }
        try {
            return contextHandler.getBaseResource().getFile() != null ? contextHandler.getBaseResource().getFile().getName() : str;
        } catch (IOException e) {
            LOG.ignore(e);
            return contextHandler.getBaseResource().getName();
        }
    }
}
