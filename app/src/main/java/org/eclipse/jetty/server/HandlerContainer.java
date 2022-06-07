package org.eclipse.jetty.server;

import org.eclipse.jetty.util.component.LifeCycle;

/* loaded from: classes5.dex */
public interface HandlerContainer extends LifeCycle {
    <T extends Handler> T getChildHandlerByClass(Class<T> cls);

    Handler[] getChildHandlers();

    Handler[] getChildHandlersByClass(Class<?> cls);

    Handler[] getHandlers();
}
