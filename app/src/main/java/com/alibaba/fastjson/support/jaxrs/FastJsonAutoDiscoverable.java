package com.alibaba.fastjson.support.jaxrs;

import javax.annotation.Priority;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

@Priority(1999)
/* loaded from: classes.dex */
public class FastJsonAutoDiscoverable implements AutoDiscoverable {
    public static volatile boolean autoDiscover = true;

    public void configure(FeatureContext featureContext) {
        if (!featureContext.getConfiguration().isRegistered(FastJsonFeature.class) && autoDiscover) {
            featureContext.register(FastJsonFeature.class);
        }
    }
}
