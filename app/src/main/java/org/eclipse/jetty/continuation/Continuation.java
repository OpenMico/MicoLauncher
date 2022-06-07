package org.eclipse.jetty.continuation;

import javax.servlet.ServletResponse;

/* loaded from: classes5.dex */
public interface Continuation {
    public static final String ATTRIBUTE = "org.eclipse.jetty.continuation";

    void addContinuationListener(ContinuationListener continuationListener);

    void complete();

    Object getAttribute(String str);

    ServletResponse getServletResponse();

    boolean isExpired();

    boolean isInitial();

    boolean isResponseWrapped();

    boolean isResumed();

    boolean isSuspended();

    void removeAttribute(String str);

    void resume();

    void setAttribute(String str, Object obj);

    void setTimeout(long j);

    void suspend();

    void suspend(ServletResponse servletResponse);

    void undispatch() throws ContinuationThrowable;
}
