package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;

/* loaded from: classes5.dex */
public class HandlerList extends HandlerCollection {
    @Override // org.eclipse.jetty.server.handler.HandlerCollection, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        Handler[] handlers = getHandlers();
        if (handlers != null && isStarted()) {
            for (Handler handler : handlers) {
                handler.handle(str, request, httpServletRequest, httpServletResponse);
                if (request.isHandled()) {
                    return;
                }
            }
        }
    }
}
