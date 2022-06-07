package org.eclipse.jetty.util.preventers;

/* loaded from: classes5.dex */
public class Java2DLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("sun.java2d.Disposer", true, classLoader);
        } catch (ClassNotFoundException e) {
            LOG.ignore(e);
        }
    }
}
