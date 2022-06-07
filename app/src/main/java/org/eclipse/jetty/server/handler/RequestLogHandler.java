package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class RequestLogHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger(RequestLogHandler.class);
    private RequestLog _requestLog;

    /* JADX WARN: Finally extract failed */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (!request.getAsyncContinuation().isInitial()) {
            request.setDispatchTime(System.currentTimeMillis());
        }
        try {
            super.handle(str, request, httpServletRequest, httpServletResponse);
            if (this._requestLog != null && DispatcherType.REQUEST.equals(request.getDispatcherType())) {
                this._requestLog.log(request, (Response) httpServletResponse);
            }
        } catch (Throwable th) {
            if (this._requestLog != null && DispatcherType.REQUEST.equals(request.getDispatcherType())) {
                this._requestLog.log(request, (Response) httpServletResponse);
            }
            throw th;
        }
    }

    public void setRequestLog(RequestLog requestLog) {
        try {
            if (this._requestLog != null) {
                this._requestLog.stop();
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object) this._requestLog, (Object) requestLog, "logimpl", true);
        }
        this._requestLog = requestLog;
        try {
            if (isStarted() && this._requestLog != null) {
                this._requestLog.start();
            }
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        if (this._requestLog != null) {
            if (!(getServer() == null || getServer() == server)) {
                getServer().getContainer().update((Object) this, (Object) this._requestLog, (Object) null, "logimpl", true);
            }
            super.setServer(server);
            if (server != null && server != getServer()) {
                server.getContainer().update((Object) this, (Object) null, (Object) this._requestLog, "logimpl", true);
                return;
            }
            return;
        }
        super.setServer(server);
    }

    public RequestLog getRequestLog() {
        return this._requestLog;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        RequestLog requestLog = this._requestLog;
        if (requestLog != null) {
            requestLog.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        RequestLog requestLog = this._requestLog;
        if (requestLog != null) {
            requestLog.stop();
        }
    }
}
