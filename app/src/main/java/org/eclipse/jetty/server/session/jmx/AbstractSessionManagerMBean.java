package org.eclipse.jetty.server.session.jmx;

import org.eclipse.jetty.server.handler.AbstractHandlerContainer;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.jmx.AbstractHandlerMBean;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;

/* loaded from: classes5.dex */
public class AbstractSessionManagerMBean extends AbstractHandlerMBean {
    public AbstractSessionManagerMBean(Object obj) {
        super(obj);
    }

    @Override // org.eclipse.jetty.server.handler.jmx.AbstractHandlerMBean
    public String getObjectContextBasis() {
        ContextHandler contextHandler;
        if (this._managed != null && (this._managed instanceof AbstractSessionManager)) {
            String str = null;
            SessionHandler sessionHandler = ((AbstractSessionManager) this._managed).getSessionHandler();
            if (!(sessionHandler == null || (contextHandler = (ContextHandler) AbstractHandlerContainer.findContainerOf(sessionHandler.getServer(), ContextHandler.class, sessionHandler)) == null)) {
                str = getContextName(contextHandler);
            }
            if (str != null) {
                return str;
            }
        }
        return super.getObjectContextBasis();
    }
}
