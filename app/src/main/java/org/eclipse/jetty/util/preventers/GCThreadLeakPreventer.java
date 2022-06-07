package org.eclipse.jetty.util.preventers;

import com.google.android.exoplayer2.util.TimestampAdjuster;

/* loaded from: classes5.dex */
public class GCThreadLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("sun.misc.GC").getMethod("requestLatency", Long.TYPE).invoke(null, Long.valueOf((long) TimestampAdjuster.MODE_SHARED));
        } catch (ClassNotFoundException e) {
            LOG.ignore(e);
        } catch (Exception e2) {
            LOG.warn(e2);
        }
    }
}
