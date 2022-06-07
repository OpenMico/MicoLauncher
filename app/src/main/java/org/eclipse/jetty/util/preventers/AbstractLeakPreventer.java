package org.eclipse.jetty.util.preventers;

import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public abstract class AbstractLeakPreventer extends AbstractLifeCycle {
    protected static final Logger LOG = Log.getLogger(AbstractLeakPreventer.class);

    public abstract void prevent(ClassLoader classLoader);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            prevent(getClass().getClassLoader());
            super.doStart();
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }
}
