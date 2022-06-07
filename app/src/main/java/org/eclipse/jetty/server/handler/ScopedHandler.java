package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;

/* loaded from: classes5.dex */
public abstract class ScopedHandler extends HandlerWrapper {
    private static final ThreadLocal<ScopedHandler> __outerScope = new ThreadLocal<>();
    protected ScopedHandler _nextScope;
    protected ScopedHandler _outerScope;

    public abstract void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException;

    public abstract void doScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException;

    protected boolean never() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        try {
            this._outerScope = __outerScope.get();
            if (this._outerScope == null) {
                __outerScope.set(this);
            }
            super.doStart();
            this._nextScope = (ScopedHandler) getChildHandlerByClass(ScopedHandler.class);
        } finally {
            if (this._outerScope == null) {
                __outerScope.set(null);
            }
        }
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    public final void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._outerScope == null) {
            doScope(str, request, httpServletRequest, httpServletResponse);
        } else {
            doHandle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    public final void nextScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ScopedHandler scopedHandler = this._nextScope;
        if (scopedHandler != null) {
            scopedHandler.doScope(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        ScopedHandler scopedHandler2 = this._outerScope;
        if (scopedHandler2 != null) {
            scopedHandler2.doHandle(str, request, httpServletRequest, httpServletResponse);
        } else {
            doHandle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    public final void nextHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ScopedHandler scopedHandler = this._nextScope;
        if (scopedHandler != null && scopedHandler == this._handler) {
            this._nextScope.doHandle(str, request, httpServletRequest, httpServletResponse);
        } else if (this._handler != null) {
            this._handler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }
}
