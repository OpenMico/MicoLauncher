package org.fourthline.cling.transport.spi;

import java.util.concurrent.ExecutorService;
import org.fourthline.cling.model.ServerClientTokens;

/* loaded from: classes5.dex */
public abstract class AbstractStreamClientConfiguration implements StreamClientConfiguration {
    protected int logWarningSeconds;
    protected ExecutorService requestExecutorService;
    protected int timeoutSeconds;

    public AbstractStreamClientConfiguration(ExecutorService executorService) {
        this.timeoutSeconds = 60;
        this.logWarningSeconds = 5;
        this.requestExecutorService = executorService;
    }

    public AbstractStreamClientConfiguration(ExecutorService executorService, int i) {
        this.timeoutSeconds = 60;
        this.logWarningSeconds = 5;
        this.requestExecutorService = executorService;
        this.timeoutSeconds = i;
    }

    protected AbstractStreamClientConfiguration(ExecutorService executorService, int i, int i2) {
        this.timeoutSeconds = 60;
        this.logWarningSeconds = 5;
        this.requestExecutorService = executorService;
        this.timeoutSeconds = i;
        this.logWarningSeconds = i2;
    }

    @Override // org.fourthline.cling.transport.spi.StreamClientConfiguration
    public ExecutorService getRequestExecutorService() {
        return this.requestExecutorService;
    }

    public void setRequestExecutorService(ExecutorService executorService) {
        this.requestExecutorService = executorService;
    }

    @Override // org.fourthline.cling.transport.spi.StreamClientConfiguration
    public int getTimeoutSeconds() {
        return this.timeoutSeconds;
    }

    public void setTimeoutSeconds(int i) {
        this.timeoutSeconds = i;
    }

    @Override // org.fourthline.cling.transport.spi.StreamClientConfiguration
    public int getLogWarningSeconds() {
        return this.logWarningSeconds;
    }

    public void setLogWarningSeconds(int i) {
        this.logWarningSeconds = i;
    }

    @Override // org.fourthline.cling.transport.spi.StreamClientConfiguration
    public String getUserAgentValue(int i, int i2) {
        return new ServerClientTokens(i, i2).toString();
    }
}
