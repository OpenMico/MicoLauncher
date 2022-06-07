package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class ShutdownHandler extends AbstractHandler {
    private static final Logger LOG = Log.getLogger(ShutdownHandler.class);
    private boolean _exitJvm = false;
    private final Server _server;
    private final String _shutdownToken;

    public ShutdownHandler(Server server, String str) {
        this._server = server;
        this._shutdownToken = str;
    }

    /* JADX WARN: Type inference failed for: r4v7, types: [org.eclipse.jetty.server.handler.ShutdownHandler$1] */
    @Override // org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (str.equals("/shutdown")) {
            if (!httpServletRequest.getMethod().equals("POST")) {
                httpServletResponse.sendError(400);
            } else if (!hasCorrectSecurityToken(httpServletRequest)) {
                Logger logger = LOG;
                logger.warn("Unauthorized shutdown attempt from " + getRemoteAddr(httpServletRequest), new Object[0]);
                httpServletResponse.sendError(401);
            } else if (!requestFromLocalhost(httpServletRequest)) {
                Logger logger2 = LOG;
                logger2.warn("Unauthorized shutdown attempt from " + getRemoteAddr(httpServletRequest), new Object[0]);
                httpServletResponse.sendError(401);
            } else {
                Logger logger3 = LOG;
                logger3.info("Shutting down by request from " + getRemoteAddr(httpServletRequest), new Object[0]);
                new Thread() { // from class: org.eclipse.jetty.server.handler.ShutdownHandler.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            ShutdownHandler.this.shutdownServer();
                        } catch (InterruptedException e) {
                            ShutdownHandler.LOG.ignore(e);
                        } catch (Exception e2) {
                            throw new RuntimeException("Shutting down server", e2);
                        }
                    }
                }.start();
            }
        }
    }

    private boolean requestFromLocalhost(HttpServletRequest httpServletRequest) {
        return "127.0.0.1".equals(getRemoteAddr(httpServletRequest));
    }

    protected String getRemoteAddr(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRemoteAddr();
    }

    private boolean hasCorrectSecurityToken(HttpServletRequest httpServletRequest) {
        return this._shutdownToken.equals(httpServletRequest.getParameter("token"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdownServer() throws Exception {
        this._server.stop();
        if (this._exitJvm) {
            System.exit(0);
        }
    }

    public void setExitJvm(boolean z) {
        this._exitJvm = z;
    }
}
