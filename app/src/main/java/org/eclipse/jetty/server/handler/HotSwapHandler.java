package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;

/* loaded from: classes5.dex */
public class HotSwapHandler extends AbstractHandlerContainer {
    private volatile Handler _handler;

    public Handler getHandler() {
        return this._handler;
    }

    @Override // org.eclipse.jetty.server.HandlerContainer
    public Handler[] getHandlers() {
        return new Handler[]{this._handler};
    }

    public void setHandler(Handler handler) {
        if (handler != null) {
            try {
                Handler handler2 = this._handler;
                this._handler = handler;
                Server server = getServer();
                handler.setServer(server);
                addBean(handler);
                if (server != null) {
                    server.getContainer().update(this, handler2, handler, "handler");
                }
                if (handler2 != null) {
                    removeBean(handler2);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Parameter handler is null.");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
    }

    @Override // org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._handler != null && isStarted()) {
            this._handler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        Server server2 = getServer();
        if (server != server2) {
            if (!isRunning()) {
                super.setServer(server);
                Handler handler = getHandler();
                if (handler != null) {
                    handler.setServer(server);
                }
                if (server != null && server != server2) {
                    server.getContainer().update(this, (Object) null, this._handler, "handler");
                    return;
                }
                return;
            }
            throw new IllegalStateException("RUNNING");
        }
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer
    protected Object expandChildren(Object obj, Class cls) {
        return expandHandler(this._handler, obj, cls);
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Destroyable
    public void destroy() {
        if (isStopped()) {
            Handler handler = getHandler();
            if (handler != null) {
                setHandler(null);
                handler.destroy();
            }
            super.destroy();
            return;
        }
        throw new IllegalStateException("!STOPPED");
    }
}
