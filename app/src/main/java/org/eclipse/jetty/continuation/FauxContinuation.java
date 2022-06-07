package org.eclipse.jetty.continuation;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;
import org.eclipse.jetty.continuation.ContinuationFilter;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public class FauxContinuation implements ContinuationFilter.FilteredContinuation {
    private static final int __COMPLETE = 7;
    private static final int __COMPLETING = 4;
    private static final int __HANDLING = 1;
    private static final int __RESUMING = 3;
    private static final int __SUSPENDED = 5;
    private static final int __SUSPENDING = 2;
    private static final int __UNSUSPENDING = 6;
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private ArrayList<ContinuationListener> _listeners;
    private final ServletRequest _request;
    private ServletResponse _response;
    private int _state = 1;
    private boolean _initial = true;
    private boolean _resumed = false;
    private boolean _timeout = false;
    private boolean _responseWrapped = false;
    private long _timeoutMs = 30000;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FauxContinuation(ServletRequest servletRequest) {
        this._request = servletRequest;
    }

    public void onComplete() {
        ArrayList<ContinuationListener> arrayList = this._listeners;
        if (arrayList != null) {
            Iterator<ContinuationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onComplete(this);
            }
        }
    }

    public void onTimeout() {
        ArrayList<ContinuationListener> arrayList = this._listeners;
        if (arrayList != null) {
            Iterator<ContinuationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onTimeout(this);
            }
        }
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
    public boolean isResumed() {
        boolean z;
        synchronized (this) {
            z = this._resumed;
        }
        return z;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isSuspended() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    return false;
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

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isExpired() {
        boolean z;
        synchronized (this) {
            z = this._timeout;
        }
        return z;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void setTimeout(long j) {
        this._timeoutMs = j;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend(ServletResponse servletResponse) {
        this._response = servletResponse;
        this._responseWrapped = servletResponse instanceof ServletResponseWrapper;
        suspend();
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    this._timeout = false;
                    this._resumed = false;
                    this._state = 2;
                    return;
                case 2:
                case 3:
                    return;
                case 4:
                case 5:
                case 6:
                    throw new IllegalStateException(getStatusString());
                default:
                    throw new IllegalStateException("" + this._state);
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void resume() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    this._resumed = true;
                    return;
                case 2:
                    this._resumed = true;
                    this._state = 3;
                    return;
                case 3:
                case 4:
                    return;
                case 5:
                    fauxResume();
                    this._resumed = true;
                    this._state = 6;
                    return;
                case 6:
                    this._resumed = true;
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void complete() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    throw new IllegalStateException(getStatusString());
                case 2:
                    this._state = 4;
                    break;
                case 3:
                    break;
                case 4:
                    return;
                case 5:
                    this._state = 4;
                    fauxResume();
                    break;
                case 6:
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation
    public boolean enter(ServletResponse servletResponse) {
        this._response = servletResponse;
        return true;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public ServletResponse getServletResponse() {
        return this._response;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    void handling() {
        synchronized (this) {
            this._responseWrapped = false;
            switch (this._state) {
                case 1:
                    throw new IllegalStateException(getStatusString());
                case 2:
                case 3:
                    throw new IllegalStateException(getStatusString());
                case 4:
                    return;
                case 5:
                    fauxResume();
                    break;
                case 6:
                    break;
                default:
                    throw new IllegalStateException("" + this._state);
            }
            this._state = 1;
        }
    }

    @Override // org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation
    public boolean exit() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    this._state = 7;
                    onComplete();
                    return true;
                case 2:
                    this._initial = false;
                    this._state = 5;
                    fauxSuspend();
                    if (!(this._state == 5 || this._state == 4)) {
                        this._initial = false;
                        this._state = 1;
                        return false;
                    }
                    onComplete();
                    return true;
                case 3:
                    this._initial = false;
                    this._state = 1;
                    return false;
                case 4:
                    this._initial = false;
                    this._state = 7;
                    onComplete();
                    return true;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    protected void expire() {
        synchronized (this) {
            this._timeout = true;
        }
        onTimeout();
        synchronized (this) {
            switch (this._state) {
                case 1:
                    return;
                case 2:
                    this._timeout = true;
                    this._state = 3;
                    fauxResume();
                    return;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    this._timeout = true;
                    this._state = 6;
                    return;
                case 6:
                    this._timeout = true;
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    private void fauxSuspend() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this._timeoutMs;
        long j2 = currentTimeMillis + j;
        while (this._timeoutMs > 0 && j > 0) {
            try {
                wait(j);
                j = j2 - System.currentTimeMillis();
            } catch (InterruptedException unused) {
            }
        }
        if (this._timeoutMs > 0 && j <= 0) {
            expire();
        }
    }

    private void fauxResume() {
        this._timeoutMs = 0L;
        notifyAll();
    }

    public String toString() {
        return getStatusString();
    }

    String getStatusString() {
        String str;
        String sb;
        synchronized (this) {
            StringBuilder sb2 = new StringBuilder();
            if (this._state == 1) {
                str = "HANDLING";
            } else if (this._state == 2) {
                str = "SUSPENDING";
            } else if (this._state == 5) {
                str = DebugCoroutineInfoImplKt.SUSPENDED;
            } else if (this._state == 3) {
                str = "RESUMING";
            } else if (this._state == 6) {
                str = "UNSUSPENDING";
            } else if (this._state == 4) {
                str = "COMPLETING";
            } else {
                str = "???" + this._state;
            }
            sb2.append(str);
            sb2.append(this._initial ? ",initial" : "");
            sb2.append(this._resumed ? ",resumed" : "");
            sb2.append(this._timeout ? ",timeout" : "");
            sb = sb2.toString();
        }
        return sb;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void addContinuationListener(ContinuationListener continuationListener) {
        if (this._listeners == null) {
            this._listeners = new ArrayList<>();
        }
        this._listeners.add(continuationListener);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public Object getAttribute(String str) {
        return this._request.getAttribute(str);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void removeAttribute(String str) {
        this._request.removeAttribute(str);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void setAttribute(String str, Object obj) {
        this._request.setAttribute(str, obj);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void undispatch() {
        if (!isSuspended()) {
            throw new IllegalStateException("!suspended");
        } else if (ContinuationFilter.__debug) {
            throw new ContinuationThrowable();
        } else {
            throw __exception;
        }
    }
}
