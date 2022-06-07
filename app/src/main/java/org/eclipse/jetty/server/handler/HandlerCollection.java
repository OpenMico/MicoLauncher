package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

/* loaded from: classes5.dex */
public class HandlerCollection extends AbstractHandlerContainer {
    private volatile Handler[] _handlers;
    private final boolean _mutableWhenRunning;
    private boolean _parallelStart;

    public HandlerCollection() {
        this._parallelStart = false;
        this._mutableWhenRunning = false;
    }

    public HandlerCollection(boolean z) {
        this._parallelStart = false;
        this._mutableWhenRunning = z;
    }

    @Override // org.eclipse.jetty.server.HandlerContainer
    public Handler[] getHandlers() {
        return this._handlers;
    }

    public void setHandlers(Handler[] handlerArr) {
        if (this._mutableWhenRunning || !isStarted()) {
            Handler[] handlerArr2 = this._handlers == null ? null : (Handler[]) this._handlers.clone();
            this._handlers = handlerArr;
            Server server = getServer();
            MultiException multiException = new MultiException();
            for (int i = 0; handlerArr != null && i < handlerArr.length; i++) {
                if (handlerArr[i].getServer() != server) {
                    handlerArr[i].setServer(server);
                }
            }
            if (getServer() != null) {
                getServer().getContainer().update((Object) this, (Object[]) handlerArr2, (Object[]) handlerArr, "handler");
            }
            for (int i2 = 0; handlerArr2 != null && i2 < handlerArr2.length; i2++) {
                if (handlerArr2[i2] != null) {
                    try {
                        if (handlerArr2[i2].isStarted()) {
                            handlerArr2[i2].stop();
                        }
                    } catch (Throwable th) {
                        multiException.add(th);
                    }
                }
            }
            multiException.ifExceptionThrowRuntime();
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    public boolean isParallelStart() {
        return this._parallelStart;
    }

    public void setParallelStart(boolean z) {
        this._parallelStart = z;
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._handlers != null && isStarted()) {
            MultiException multiException = null;
            for (int i = 0; i < this._handlers.length; i++) {
                try {
                    this._handlers[i].handle(str, request, httpServletRequest, httpServletResponse);
                } catch (IOException e) {
                    throw e;
                } catch (RuntimeException e2) {
                    throw e2;
                } catch (Exception e3) {
                    if (multiException == null) {
                        multiException = new MultiException();
                    }
                    multiException.add(e3);
                }
            }
            if (multiException == null) {
                return;
            }
            if (multiException.size() == 1) {
                throw new ServletException(multiException.getThrowable(0));
            }
            throw new ServletException(multiException);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        final MultiException multiException = new MultiException();
        if (this._handlers != null) {
            if (this._parallelStart) {
                final CountDownLatch countDownLatch = new CountDownLatch(this._handlers.length);
                final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                for (final int i = 0; i < this._handlers.length; i++) {
                    getServer().getThreadPool().dispatch(new Runnable() { // from class: org.eclipse.jetty.server.handler.HandlerCollection.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ClassLoader contextClassLoader2 = Thread.currentThread().getContextClassLoader();
                            try {
                                Thread.currentThread().setContextClassLoader(contextClassLoader);
                                HandlerCollection.this._handlers[i].start();
                            } finally {
                                Thread.currentThread().setContextClassLoader(contextClassLoader2);
                                countDownLatch.countDown();
                            }
                        }
                    });
                }
                countDownLatch.await();
            } else {
                for (int i2 = 0; i2 < this._handlers.length; i2++) {
                    try {
                        this._handlers[i2].start();
                    } catch (Throwable th) {
                        multiException.add(th);
                    }
                }
            }
        }
        super.doStart();
        multiException.ifExceptionThrow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        MultiException multiException = new MultiException();
        try {
            super.doStop();
        } catch (Throwable th) {
            multiException.add(th);
        }
        if (this._handlers != null) {
            int length = this._handlers.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                try {
                    this._handlers[i].stop();
                } catch (Throwable th2) {
                    multiException.add(th2);
                }
                length = i;
            }
        }
        multiException.ifExceptionThrow();
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        if (!isStarted()) {
            Server server2 = getServer();
            super.setServer(server);
            Handler[] handlers = getHandlers();
            for (int i = 0; handlers != null && i < handlers.length; i++) {
                handlers[i].setServer(server);
            }
            if (!(server == null || server == server2)) {
                server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._handlers, "handler");
                return;
            }
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    public void addHandler(Handler handler) {
        setHandlers((Handler[]) LazyList.addToArray(getHandlers(), handler, Handler.class));
    }

    public void removeHandler(Handler handler) {
        Handler[] handlers = getHandlers();
        if (handlers != null && handlers.length > 0) {
            setHandlers((Handler[]) LazyList.removeFromArray(handlers, handler));
        }
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer
    protected Object expandChildren(Object obj, Class cls) {
        Handler[] handlers = getHandlers();
        for (int i = 0; handlers != null && i < handlers.length; i++) {
            obj = expandHandler(handlers[i], obj, cls);
        }
        return obj;
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Destroyable
    public void destroy() {
        if (isStopped()) {
            Handler[] childHandlers = getChildHandlers();
            setHandlers(null);
            for (Handler handler : childHandlers) {
                handler.destroy();
            }
            super.destroy();
            return;
        }
        throw new IllegalStateException("!STOPPED");
    }
}
