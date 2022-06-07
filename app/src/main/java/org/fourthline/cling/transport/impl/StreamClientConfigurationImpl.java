package org.fourthline.cling.transport.impl;

import java.util.concurrent.ExecutorService;
import org.fourthline.cling.transport.spi.AbstractStreamClientConfiguration;

/* loaded from: classes5.dex */
public class StreamClientConfigurationImpl extends AbstractStreamClientConfiguration {
    private boolean usePersistentConnections = false;

    public StreamClientConfigurationImpl(ExecutorService executorService) {
        super(executorService);
    }

    public StreamClientConfigurationImpl(ExecutorService executorService, int i) {
        super(executorService, i);
    }

    public boolean isUsePersistentConnections() {
        return this.usePersistentConnections;
    }

    public void setUsePersistentConnections(boolean z) {
        this.usePersistentConnections = z;
    }
}
