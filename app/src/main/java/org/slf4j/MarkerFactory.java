package org.slf4j;

import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMarkerBinder;

/* loaded from: classes4.dex */
public class MarkerFactory {
    static IMarkerFactory a;

    private MarkerFactory() {
    }

    static {
        try {
            a = StaticMarkerBinder.SINGLETON.getMarkerFactory();
        } catch (Exception e) {
            Util.report("Unexpected failure while binding MarkerFactory", e);
        } catch (NoClassDefFoundError unused) {
            a = new BasicMarkerFactory();
        }
    }

    public static Marker getMarker(String str) {
        return a.getMarker(str);
    }

    public static Marker getDetachedMarker(String str) {
        return a.getDetachedMarker(str);
    }

    public static IMarkerFactory getIMarkerFactory() {
        return a;
    }
}
