package org.eclipse.jetty.server.handler;

import java.io.IOException;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.TypeUtil;

/* loaded from: classes5.dex */
public abstract class AbstractHandlerContainer extends AbstractHandler implements HandlerContainer {
    protected Object expandChildren(Object obj, Class<?> cls) {
        return obj;
    }

    @Override // org.eclipse.jetty.server.HandlerContainer
    public Handler[] getChildHandlers() {
        return (Handler[]) LazyList.toArray(expandChildren(null, null), Handler.class);
    }

    @Override // org.eclipse.jetty.server.HandlerContainer
    public Handler[] getChildHandlersByClass(Class<?> cls) {
        return (Handler[]) LazyList.toArray(expandChildren(null, cls), cls);
    }

    @Override // org.eclipse.jetty.server.HandlerContainer
    public <T extends Handler> T getChildHandlerByClass(Class<T> cls) {
        Object expandChildren = expandChildren(null, cls);
        if (expandChildren == null) {
            return null;
        }
        return (T) ((Handler) LazyList.get(expandChildren, 0));
    }

    protected Object expandHandler(Handler handler, Object obj, Class<Handler> cls) {
        if (handler == null) {
            return obj;
        }
        if (cls == null || cls.isAssignableFrom(handler.getClass())) {
            obj = LazyList.add(obj, handler);
        }
        if (handler instanceof AbstractHandlerContainer) {
            return ((AbstractHandlerContainer) handler).expandChildren(obj, cls);
        }
        if (!(handler instanceof HandlerContainer)) {
            return obj;
        }
        HandlerContainer handlerContainer = (HandlerContainer) handler;
        return LazyList.addArray(obj, cls == null ? handlerContainer.getChildHandlers() : handlerContainer.getChildHandlersByClass(cls));
    }

    public static <T extends HandlerContainer> T findContainerOf(HandlerContainer handlerContainer, Class<T> cls, Handler handler) {
        Handler[] childHandlersByClass;
        if (!(handlerContainer == null || handler == null || (childHandlersByClass = handlerContainer.getChildHandlersByClass(cls)) == null)) {
            for (Handler handler2 : childHandlersByClass) {
                T t = (T) ((HandlerContainer) handler2);
                Handler[] childHandlersByClass2 = t.getChildHandlersByClass(handler.getClass());
                if (childHandlersByClass2 != null) {
                    for (Handler handler3 : childHandlersByClass2) {
                        if (handler3 == handler) {
                            return t;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        dump(appendable, str, getBeans(), TypeUtil.asList(getHandlers()));
    }
}
