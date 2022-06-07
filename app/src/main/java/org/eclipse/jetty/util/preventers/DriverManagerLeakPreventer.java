package org.eclipse.jetty.util.preventers;

import java.sql.DriverManager;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class DriverManagerLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) {
        Logger logger = LOG;
        logger.debug("Pinning DriverManager classloader with " + classLoader, new Object[0]);
        DriverManager.getDrivers();
    }
}
