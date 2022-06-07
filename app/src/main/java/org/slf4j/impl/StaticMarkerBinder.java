package org.slf4j.impl;

import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MarkerFactoryBinder;

/* loaded from: classes4.dex */
public class StaticMarkerBinder implements MarkerFactoryBinder {
    public static final StaticMarkerBinder SINGLETON = new StaticMarkerBinder();
    final IMarkerFactory a = new BasicMarkerFactory();

    private StaticMarkerBinder() {
    }

    @Override // org.slf4j.spi.MarkerFactoryBinder
    public IMarkerFactory getMarkerFactory() {
        return this.a;
    }

    @Override // org.slf4j.spi.MarkerFactoryBinder
    public String getMarkerFactoryClassStr() {
        return BasicMarkerFactory.class.getName();
    }
}
