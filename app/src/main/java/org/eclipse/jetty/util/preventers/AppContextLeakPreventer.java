package org.eclipse.jetty.util.preventers;

import javax.imageio.ImageIO;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class AppContextLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) {
        Logger logger = LOG;
        logger.debug("Pinning classloader for AppContext.getContext() with " + classLoader, new Object[0]);
        ImageIO.getUseCache();
    }
}
