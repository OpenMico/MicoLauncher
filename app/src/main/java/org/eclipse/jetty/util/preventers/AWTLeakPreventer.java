package org.eclipse.jetty.util.preventers;

import java.awt.Toolkit;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class AWTLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) {
        Logger logger = LOG;
        logger.debug("Pinning classloader for java.awt.EventQueue using " + classLoader, new Object[0]);
        Toolkit.getDefaultToolkit();
    }
}
