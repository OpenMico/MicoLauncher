package org.slf4j.helpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class BasicMarkerFactory implements IMarkerFactory {
    private final ConcurrentMap<String, Marker> a = new ConcurrentHashMap();

    @Override // org.slf4j.IMarkerFactory
    public Marker getMarker(String str) {
        if (str != null) {
            Marker marker = this.a.get(str);
            if (marker != null) {
                return marker;
            }
            BasicMarker basicMarker = new BasicMarker(str);
            Marker putIfAbsent = this.a.putIfAbsent(str, basicMarker);
            return putIfAbsent != null ? putIfAbsent : basicMarker;
        }
        throw new IllegalArgumentException("Marker name cannot be null");
    }

    @Override // org.slf4j.IMarkerFactory
    public boolean exists(String str) {
        if (str == null) {
            return false;
        }
        return this.a.containsKey(str);
    }

    @Override // org.slf4j.IMarkerFactory
    public boolean detachMarker(String str) {
        return (str == null || this.a.remove(str) == null) ? false : true;
    }

    @Override // org.slf4j.IMarkerFactory
    public Marker getDetachedMarker(String str) {
        return new BasicMarker(str);
    }
}
