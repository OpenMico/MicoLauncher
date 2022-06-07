package org.eclipse.jetty.continuation;

import java.lang.reflect.Constructor;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;

/* loaded from: classes5.dex */
public class ContinuationSupport {
    static final boolean __jetty6;
    static final Constructor<? extends Continuation> __newJetty6Continuation;
    static final Constructor<? extends Continuation> __newServlet3Continuation;
    static final boolean __servlet3;
    static final Class<?> __waitingContinuation;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Class<?>, java.lang.reflect.Constructor<? extends org.eclipse.jetty.continuation.Continuation>] */
    static {
        Constructor<? extends Continuation> constructor;
        Constructor<? extends Continuation> constructor2;
        boolean z;
        Constructor<? extends Continuation> constructor3 = 0;
        boolean z2 = true;
        int i = 0;
        try {
            if (ServletRequest.class.getMethod("startAsync", new Class[i]) != null ? true : i) {
                constructor2 = ContinuationSupport.class.getClassLoader().loadClass("org.eclipse.jetty.continuation.Servlet3Continuation").asSubclass(Continuation.class).getConstructor(ServletRequest.class);
                z = true;
            } else {
                constructor2 = constructor3;
                z = i;
            }
        } catch (Exception unused) {
        } finally {
            __servlet3 = i;
            __newServlet3Continuation = constructor3;
        }
        try {
            Class<?> loadClass = ContinuationSupport.class.getClassLoader().loadClass("org.mortbay.util.ajax.Continuation");
            if (loadClass != null ? true : i) {
                constructor = ContinuationSupport.class.getClassLoader().loadClass("org.eclipse.jetty.continuation.Jetty6Continuation").asSubclass(Continuation.class).getConstructor(ServletRequest.class, loadClass);
            } else {
                z2 = i;
                constructor = constructor3;
            }
        } catch (Exception unused2) {
        } finally {
            __jetty6 = i;
            __newJetty6Continuation = constructor3;
        }
        try {
            ContinuationSupport.class.getClassLoader().loadClass("org.mortbay.util.ajax.WaitingContinuation");
        } catch (Exception unused3) {
            __waitingContinuation = constructor3;
        } catch (Throwable th) {
            __waitingContinuation = constructor3;
            throw th;
        }
    }

    public static Continuation getContinuation(ServletRequest servletRequest) {
        Continuation continuation;
        Continuation continuation2 = (Continuation) servletRequest.getAttribute(Continuation.ATTRIBUTE);
        if (continuation2 != null) {
            return continuation2;
        }
        while (servletRequest instanceof ServletRequestWrapper) {
            servletRequest = ((ServletRequestWrapper) servletRequest).getRequest();
        }
        if (__servlet3) {
            try {
                Continuation continuation3 = (Continuation) __newServlet3Continuation.newInstance(servletRequest);
                servletRequest.setAttribute(Continuation.ATTRIBUTE, continuation3);
                return continuation3;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (__jetty6) {
            Object attribute = servletRequest.getAttribute("org.mortbay.jetty.ajax.Continuation");
            if (attribute != null) {
                try {
                    if (__waitingContinuation != null && !__waitingContinuation.isInstance(attribute)) {
                        continuation = (Continuation) __newJetty6Continuation.newInstance(servletRequest, attribute);
                        servletRequest.setAttribute(Continuation.ATTRIBUTE, continuation);
                        return continuation;
                    }
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            continuation = new FauxContinuation(servletRequest);
            servletRequest.setAttribute(Continuation.ATTRIBUTE, continuation);
            return continuation;
        } else {
            throw new IllegalStateException("!(Jetty || Servlet 3.0 || ContinuationFilter)");
        }
    }

    @Deprecated
    public static Continuation getContinuation(ServletRequest servletRequest, ServletResponse servletResponse) {
        return getContinuation(servletRequest);
    }
}
