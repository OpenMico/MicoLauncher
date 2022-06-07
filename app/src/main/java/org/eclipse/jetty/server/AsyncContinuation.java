package org.eclipse.jetty.server;

import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationThrowable;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes5.dex */
public class AsyncContinuation implements AsyncContext, Continuation {
    private static final long DEFAULT_TIMEOUT = 30000;
    private static final int __ASYNCSTARTED = 2;
    private static final int __ASYNCWAIT = 4;
    private static final int __COMPLETED = 9;
    private static final int __COMPLETING = 7;
    private static final int __DISPATCHED = 1;
    private static final int __IDLE = 0;
    private static final int __REDISPATCH = 5;
    private static final int __REDISPATCHED = 6;
    private static final int __REDISPATCHING = 3;
    private static final int __UNCOMPLETED = 8;
    private List<AsyncListener> _asyncListeners;
    protected AbstractHttpConnection _connection;
    private volatile boolean _continuation;
    private List<ContinuationListener> _continuationListeners;
    private AsyncEventState _event;
    private volatile long _expireAt;
    private boolean _expired;
    private List<AsyncListener> _lastAsyncListeners;
    private volatile boolean _responseWrapped;
    private boolean _resumed;
    private static final Logger LOG = Log.getLogger(AsyncContinuation.class);
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private long _timeoutMs = 30000;
    private int _state = 0;
    private boolean _initial = true;

    public void setConnection(AbstractHttpConnection abstractHttpConnection) {
        synchronized (this) {
            this._connection = abstractHttpConnection;
        }
    }

    @Override // javax.servlet.AsyncContext
    public void addListener(AsyncListener asyncListener) {
        synchronized (this) {
            if (this._asyncListeners == null) {
                this._asyncListeners = new ArrayList();
            }
            this._asyncListeners.add(asyncListener);
        }
    }

    @Override // javax.servlet.AsyncContext
    public void addListener(AsyncListener asyncListener, ServletRequest servletRequest, ServletResponse servletResponse) {
        synchronized (this) {
            if (this._asyncListeners == null) {
                this._asyncListeners = new ArrayList();
            }
            this._asyncListeners.add(asyncListener);
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void addContinuationListener(ContinuationListener continuationListener) {
        synchronized (this) {
            if (this._continuationListeners == null) {
                this._continuationListeners = new ArrayList();
            }
            this._continuationListeners.add(continuationListener);
        }
    }

    @Override // javax.servlet.AsyncContext, org.eclipse.jetty.continuation.Continuation
    public void setTimeout(long j) {
        synchronized (this) {
            this._timeoutMs = j;
        }
    }

    @Override // javax.servlet.AsyncContext
    public long getTimeout() {
        long j;
        synchronized (this) {
            j = this._timeoutMs;
        }
        return j;
    }

    public AsyncEventState getAsyncEventState() {
        AsyncEventState asyncEventState;
        synchronized (this) {
            asyncEventState = this._event;
        }
        return asyncEventState;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isResponseWrapped() {
        return this._responseWrapped;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isInitial() {
        boolean z;
        synchronized (this) {
            z = this._initial;
        }
        return z;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isSuspended() {
        synchronized (this) {
            int i = this._state;
            if (i != 7) {
                switch (i) {
                    case 2:
                    case 3:
                    case 4:
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }
    }

    public boolean isSuspending() {
        synchronized (this) {
            int i = this._state;
            return i == 2 || i == 4;
        }
    }

    public boolean isDispatchable() {
        synchronized (this) {
            int i = this._state;
            if (i != 3) {
                switch (i) {
                    case 5:
                    case 6:
                    case 7:
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }
    }

    public String toString() {
        String str;
        synchronized (this) {
            str = super.toString() + "@" + getStatusString();
        }
        return str;
    }

    public String getStatusString() {
        String str;
        String sb;
        synchronized (this) {
            StringBuilder sb2 = new StringBuilder();
            if (this._state == 0) {
                str = PlayerEvent.MUSIC_SOURCE_IDLE;
            } else if (this._state == 1) {
                str = "DISPATCHED";
            } else if (this._state == 2) {
                str = "ASYNCSTARTED";
            } else if (this._state == 4) {
                str = "ASYNCWAIT";
            } else if (this._state == 3) {
                str = "REDISPATCHING";
            } else if (this._state == 5) {
                str = "REDISPATCH";
            } else if (this._state == 6) {
                str = "REDISPATCHED";
            } else if (this._state == 7) {
                str = "COMPLETING";
            } else if (this._state == 8) {
                str = "UNCOMPLETED";
            } else if (this._state == 9) {
                str = "COMPLETE";
            } else {
                str = "UNKNOWN?" + this._state;
            }
            sb2.append(str);
            sb2.append(this._initial ? ",initial" : "");
            sb2.append(this._resumed ? ",resumed" : "");
            sb2.append(this._expired ? ",expired" : "");
            sb = sb2.toString();
        }
        return sb;
    }

    public boolean handling() {
        synchronized (this) {
            this._continuation = false;
            this._responseWrapped = false;
            int i = this._state;
            if (i == 0) {
                this._initial = true;
                this._state = 1;
                if (this._lastAsyncListeners != null) {
                    this._lastAsyncListeners.clear();
                }
                if (this._asyncListeners != null) {
                    this._asyncListeners.clear();
                } else {
                    this._asyncListeners = this._lastAsyncListeners;
                    this._lastAsyncListeners = null;
                }
                return true;
            } else if (i != 7) {
                switch (i) {
                    case 4:
                        return false;
                    case 5:
                        this._state = 6;
                        return true;
                    default:
                        throw new IllegalStateException(getStatusString());
                }
            } else {
                this._state = 8;
                return false;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0058 A[Catch: all -> 0x0080, TryCatch #0 {, blocks: (B:3:0x0001, B:8:0x000a, B:9:0x0013, B:10:0x0014, B:12:0x001d, B:14:0x0025, B:16:0x002d, B:19:0x0036, B:20:0x0042, B:21:0x0049, B:23:0x0058, B:24:0x005d), top: B:38:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void doSuspend(javax.servlet.ServletContext r3, javax.servlet.ServletRequest r4, javax.servlet.ServletResponse r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = r2._state     // Catch: all -> 0x0080
            r1 = 1
            if (r0 == r1) goto L_0x0014
            r1 = 6
            if (r0 != r1) goto L_0x000a
            goto L_0x0014
        L_0x000a:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch: all -> 0x0080
            java.lang.String r4 = r2.getStatusString()     // Catch: all -> 0x0080
            r3.<init>(r4)     // Catch: all -> 0x0080
            throw r3     // Catch: all -> 0x0080
        L_0x0014:
            r0 = 0
            r2._resumed = r0     // Catch: all -> 0x0080
            r2._expired = r0     // Catch: all -> 0x0080
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch: all -> 0x0080
            if (r0 == 0) goto L_0x0042
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch: all -> 0x0080
            javax.servlet.ServletRequest r0 = r0.getSuppliedRequest()     // Catch: all -> 0x0080
            if (r4 != r0) goto L_0x0042
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch: all -> 0x0080
            javax.servlet.ServletResponse r0 = r0.getSuppliedResponse()     // Catch: all -> 0x0080
            if (r5 != r0) goto L_0x0042
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch: all -> 0x0080
            javax.servlet.ServletContext r0 = r0.getServletContext()     // Catch: all -> 0x0080
            if (r3 == r0) goto L_0x0036
            goto L_0x0042
        L_0x0036:
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r3 = r2._event     // Catch: all -> 0x0080
            r4 = 0
            org.eclipse.jetty.server.AsyncContinuation.AsyncEventState.access$002(r3, r4)     // Catch: all -> 0x0080
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r3 = r2._event     // Catch: all -> 0x0080
            org.eclipse.jetty.server.AsyncContinuation.AsyncEventState.access$102(r3, r4)     // Catch: all -> 0x0080
            goto L_0x0049
        L_0x0042:
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = new org.eclipse.jetty.server.AsyncContinuation$AsyncEventState     // Catch: all -> 0x0080
            r0.<init>(r3, r4, r5)     // Catch: all -> 0x0080
            r2._event = r0     // Catch: all -> 0x0080
        L_0x0049:
            r3 = 2
            r2._state = r3     // Catch: all -> 0x0080
            java.util.List<javax.servlet.AsyncListener> r3 = r2._lastAsyncListeners     // Catch: all -> 0x0080
            java.util.List<javax.servlet.AsyncListener> r4 = r2._asyncListeners     // Catch: all -> 0x0080
            r2._lastAsyncListeners = r4     // Catch: all -> 0x0080
            r2._asyncListeners = r3     // Catch: all -> 0x0080
            java.util.List<javax.servlet.AsyncListener> r3 = r2._asyncListeners     // Catch: all -> 0x0080
            if (r3 == 0) goto L_0x005d
            java.util.List<javax.servlet.AsyncListener> r3 = r2._asyncListeners     // Catch: all -> 0x0080
            r3.clear()     // Catch: all -> 0x0080
        L_0x005d:
            monitor-exit(r2)     // Catch: all -> 0x0080
            java.util.List<javax.servlet.AsyncListener> r3 = r2._lastAsyncListeners
            if (r3 == 0) goto L_0x007f
            java.util.Iterator r3 = r3.iterator()
        L_0x0066:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x007f
            java.lang.Object r4 = r3.next()
            javax.servlet.AsyncListener r4 = (javax.servlet.AsyncListener) r4
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r5 = r2._event     // Catch: Exception -> 0x0078
            r4.onStartAsync(r5)     // Catch: Exception -> 0x0078
            goto L_0x0066
        L_0x0078:
            r4 = move-exception
            org.eclipse.jetty.util.log.Logger r5 = org.eclipse.jetty.server.AsyncContinuation.LOG
            r5.warn(r4)
            goto L_0x0066
        L_0x007f:
            return
        L_0x0080:
            r3 = move-exception
            monitor-exit(r2)     // Catch: all -> 0x0080
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncContinuation.doSuspend(javax.servlet.ServletContext, javax.servlet.ServletRequest, javax.servlet.ServletResponse):void");
    }

    public boolean unhandle() {
        synchronized (this) {
            switch (this._state) {
                case 0:
                    throw new IllegalStateException(getStatusString());
                case 1:
                case 6:
                    this._state = 8;
                    return true;
                case 2:
                    this._initial = false;
                    this._state = 4;
                    scheduleTimeout();
                    if (this._state == 4) {
                        return true;
                    }
                    if (this._state == 7) {
                        this._state = 8;
                        return true;
                    }
                    this._initial = false;
                    this._state = 6;
                    return false;
                case 3:
                    this._initial = false;
                    this._state = 6;
                    return false;
                case 4:
                case 5:
                default:
                    throw new IllegalStateException(getStatusString());
                case 7:
                    this._initial = false;
                    this._state = 8;
                    return true;
            }
        }
    }

    @Override // javax.servlet.AsyncContext
    public void dispatch() {
        synchronized (this) {
            int i = this._state;
            if (i != 2) {
                switch (i) {
                    case 4:
                        boolean z = !this._expired;
                        this._state = 5;
                        this._resumed = true;
                        if (z) {
                            cancelTimeout();
                            scheduleDispatch();
                            return;
                        }
                        return;
                    case 5:
                        return;
                    default:
                        throw new IllegalStateException(getStatusString());
                }
            } else {
                this._state = 3;
                this._resumed = true;
            }
        }
    }

    protected void expired() {
        synchronized (this) {
            int i = this._state;
            if (i == 2 || i == 4) {
                List<ContinuationListener> list = this._continuationListeners;
                List<AsyncListener> list2 = this._asyncListeners;
                this._expired = true;
                if (list2 != null) {
                    for (AsyncListener asyncListener : list2) {
                        try {
                            asyncListener.onTimeout(this._event);
                        } catch (Exception e) {
                            LOG.warn(e);
                        }
                    }
                }
                if (list != null) {
                    for (ContinuationListener continuationListener : list) {
                        try {
                            continuationListener.onTimeout(this);
                        } catch (Exception e2) {
                            LOG.warn(e2);
                        }
                    }
                }
                synchronized (this) {
                    int i2 = this._state;
                    if (i2 == 2 || i2 == 4) {
                        if (this._continuation) {
                            dispatch();
                        } else {
                            complete();
                        }
                    }
                }
                scheduleDispatch();
            }
        }
    }

    @Override // javax.servlet.AsyncContext, org.eclipse.jetty.continuation.Continuation
    public void complete() {
        synchronized (this) {
            int i = this._state;
            if (i != 4) {
                if (i != 6) {
                    switch (i) {
                        case 1:
                            break;
                        case 2:
                            this._state = 7;
                            return;
                        default:
                            throw new IllegalStateException(getStatusString());
                    }
                }
                throw new IllegalStateException(getStatusString());
            }
            this._state = 7;
            boolean z = !this._expired;
            if (z) {
                cancelTimeout();
                scheduleDispatch();
            }
        }
    }

    @Override // javax.servlet.AsyncContext
    public <T extends AsyncListener> T createListener(Class<T> cls) throws ServletException {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public void doComplete(Throwable th) {
        List<ContinuationListener> list;
        List<AsyncListener> list2;
        synchronized (this) {
            if (this._state == 8) {
                this._state = 9;
                list = this._continuationListeners;
                list2 = this._asyncListeners;
            } else {
                throw new IllegalStateException(getStatusString());
            }
        }
        if (list2 != null) {
            for (AsyncListener asyncListener : list2) {
                if (th != null) {
                    try {
                        this._event.getSuppliedRequest().setAttribute(RequestDispatcher.ERROR_EXCEPTION, th);
                        this._event.getSuppliedRequest().setAttribute(RequestDispatcher.ERROR_MESSAGE, th.getMessage());
                        asyncListener.onError(this._event);
                    } catch (Exception e) {
                        LOG.warn(e);
                    }
                } else {
                    asyncListener.onComplete(this._event);
                }
            }
        }
        if (list != null) {
            for (ContinuationListener continuationListener : list) {
                try {
                    continuationListener.onComplete(this);
                } catch (Exception e2) {
                    LOG.warn(e2);
                }
            }
        }
    }

    public void recycle() {
        synchronized (this) {
            int i = this._state;
            if (i == 1 || i == 6) {
                throw new IllegalStateException(getStatusString());
            }
            this._state = 0;
            this._initial = true;
            this._resumed = false;
            this._expired = false;
            this._responseWrapped = false;
            cancelTimeout();
            this._timeoutMs = 30000L;
            this._continuationListeners = null;
        }
    }

    public void cancel() {
        synchronized (this) {
            cancelTimeout();
            this._continuationListeners = null;
        }
    }

    protected void scheduleDispatch() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (!endPoint.isBlocking()) {
            ((AsyncEndPoint) endPoint).asyncDispatch();
        }
    }

    protected void scheduleTimeout() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (this._timeoutMs <= 0) {
            return;
        }
        if (endPoint.isBlocking()) {
            synchronized (this) {
                this._expireAt = System.currentTimeMillis() + this._timeoutMs;
                long j = this._timeoutMs;
                while (this._expireAt > 0 && j > 0 && this._connection.getServer().isRunning()) {
                    try {
                        wait(j);
                    } catch (InterruptedException e) {
                        LOG.ignore(e);
                    }
                    j = this._expireAt - System.currentTimeMillis();
                }
                if (this._expireAt > 0 && j <= 0 && this._connection.getServer().isRunning()) {
                    expired();
                }
            }
            return;
        }
        ((AsyncEndPoint) endPoint).scheduleTimeout(this._event._timeout, this._timeoutMs);
    }

    protected void cancelTimeout() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (endPoint.isBlocking()) {
            synchronized (this) {
                this._expireAt = 0L;
                notifyAll();
            }
            return;
        }
        AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            ((AsyncEndPoint) endPoint).cancelTimeout(asyncEventState._timeout);
        }
    }

    public boolean isCompleting() {
        boolean z;
        synchronized (this) {
            z = this._state == 7;
        }
        return z;
    }

    public boolean isUncompleted() {
        boolean z;
        synchronized (this) {
            z = this._state == 8;
        }
        return z;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this) {
            z = this._state == 9;
        }
        return z;
    }

    public boolean isAsyncStarted() {
        synchronized (this) {
            switch (this._state) {
                case 2:
                case 3:
                case 4:
                case 5:
                    return true;
                default:
                    return false;
            }
        }
    }

    public boolean isAsync() {
        synchronized (this) {
            switch (this._state) {
                case 0:
                case 1:
                case 8:
                case 9:
                    return false;
                default:
                    return true;
            }
        }
    }

    @Override // javax.servlet.AsyncContext
    public void dispatch(ServletContext servletContext, String str) {
        this._event._dispatchContext = servletContext;
        this._event._pathInContext = str;
        dispatch();
    }

    @Override // javax.servlet.AsyncContext
    public void dispatch(String str) {
        this._event._pathInContext = str;
        dispatch();
    }

    public Request getBaseRequest() {
        return this._connection.getRequest();
    }

    @Override // javax.servlet.AsyncContext
    public ServletRequest getRequest() {
        AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            return asyncEventState.getSuppliedRequest();
        }
        return this._connection.getRequest();
    }

    @Override // javax.servlet.AsyncContext
    public ServletResponse getResponse() {
        AsyncEventState asyncEventState;
        if (!this._responseWrapped || (asyncEventState = this._event) == null || asyncEventState.getSuppliedResponse() == null) {
            return this._connection.getResponse();
        }
        return this._event.getSuppliedResponse();
    }

    @Override // javax.servlet.AsyncContext
    public void start(final Runnable runnable) {
        final AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            this._connection.getServer().getThreadPool().dispatch(new Runnable() { // from class: org.eclipse.jetty.server.AsyncContinuation.1
                @Override // java.lang.Runnable
                public void run() {
                    ((ContextHandler.Context) asyncEventState.getServletContext()).getContextHandler().handle(runnable);
                }
            });
        }
    }

    @Override // javax.servlet.AsyncContext
    public boolean hasOriginalRequestAndResponse() {
        boolean z;
        synchronized (this) {
            z = this._event != null && this._event.getSuppliedRequest() == this._connection._request && this._event.getSuppliedResponse() == this._connection._response;
        }
        return z;
    }

    public ContextHandler getContextHandler() {
        AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            return ((ContextHandler.Context) asyncEventState.getServletContext()).getContextHandler();
        }
        return null;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isResumed() {
        boolean z;
        synchronized (this) {
            z = this._resumed;
        }
        return z;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isExpired() {
        boolean z;
        synchronized (this) {
            z = this._expired;
        }
        return z;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void resume() {
        dispatch();
    }

    public void suspend(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse) {
        synchronized (this) {
            this._responseWrapped = !(servletResponse instanceof Response);
            doSuspend(servletContext, servletRequest, servletResponse);
            if (servletRequest instanceof HttpServletRequest) {
                this._event._pathInContext = URIUtil.addPaths(((HttpServletRequest) servletRequest).getServletPath(), ((HttpServletRequest) servletRequest).getPathInfo());
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend(ServletResponse servletResponse) {
        boolean z = true;
        this._continuation = true;
        if (servletResponse instanceof Response) {
            z = false;
        }
        this._responseWrapped = z;
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), servletResponse);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend() {
        this._responseWrapped = false;
        this._continuation = true;
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), this._connection.getResponse());
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public ServletResponse getServletResponse() {
        AsyncEventState asyncEventState;
        if (!this._responseWrapped || (asyncEventState = this._event) == null || asyncEventState.getSuppliedResponse() == null) {
            return this._connection.getResponse();
        }
        return this._event.getSuppliedResponse();
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public Object getAttribute(String str) {
        return this._connection.getRequest().getAttribute(str);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void removeAttribute(String str) {
        this._connection.getRequest().removeAttribute(str);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void setAttribute(String str, Object obj) {
        this._connection.getRequest().setAttribute(str, obj);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void undispatch() {
        if (!isSuspended()) {
            throw new IllegalStateException("!suspended");
        } else if (LOG.isDebugEnabled()) {
            throw new ContinuationThrowable();
        } else {
            throw __exception;
        }
    }

    /* loaded from: classes5.dex */
    public class AsyncTimeout extends Timeout.Task implements Runnable {
        public AsyncTimeout() {
            AsyncContinuation.this = r1;
        }

        @Override // org.eclipse.jetty.util.thread.Timeout.Task
        public void expired() {
            AsyncContinuation.this.expired();
        }

        @Override // java.lang.Runnable
        public void run() {
            AsyncContinuation.this.expired();
        }
    }

    /* loaded from: classes5.dex */
    public class AsyncEventState extends AsyncEvent {
        private ServletContext _dispatchContext;
        private String _pathInContext;
        private final ServletContext _suspendedContext;
        private Timeout.Task _timeout;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AsyncEventState(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse) {
            super(r1, servletRequest, servletResponse);
            AsyncContinuation.this = r1;
            this._timeout = new AsyncTimeout();
            this._suspendedContext = servletContext;
            Request request = r1._connection.getRequest();
            if (request.getAttribute(AsyncContext.ASYNC_REQUEST_URI) == null) {
                String str = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
                if (str != null) {
                    request.setAttribute(AsyncContext.ASYNC_REQUEST_URI, str);
                    request.setAttribute(AsyncContext.ASYNC_CONTEXT_PATH, request.getAttribute(RequestDispatcher.FORWARD_CONTEXT_PATH));
                    request.setAttribute(AsyncContext.ASYNC_SERVLET_PATH, request.getAttribute(RequestDispatcher.FORWARD_SERVLET_PATH));
                    request.setAttribute(AsyncContext.ASYNC_PATH_INFO, request.getAttribute(RequestDispatcher.FORWARD_PATH_INFO));
                    request.setAttribute(AsyncContext.ASYNC_QUERY_STRING, request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING));
                    return;
                }
                request.setAttribute(AsyncContext.ASYNC_REQUEST_URI, request.getRequestURI());
                request.setAttribute(AsyncContext.ASYNC_CONTEXT_PATH, request.getContextPath());
                request.setAttribute(AsyncContext.ASYNC_SERVLET_PATH, request.getServletPath());
                request.setAttribute(AsyncContext.ASYNC_PATH_INFO, request.getPathInfo());
                request.setAttribute(AsyncContext.ASYNC_QUERY_STRING, request.getQueryString());
            }
        }

        public ServletContext getSuspendedContext() {
            return this._suspendedContext;
        }

        public ServletContext getDispatchContext() {
            return this._dispatchContext;
        }

        public ServletContext getServletContext() {
            ServletContext servletContext = this._dispatchContext;
            return servletContext == null ? this._suspendedContext : servletContext;
        }

        public String getPath() {
            return this._pathInContext;
        }
    }
}
