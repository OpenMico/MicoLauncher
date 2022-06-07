package org.fourthline.cling.transport.impl;

import org.fourthline.cling.transport.spi.ServletContainerAdapter;
import org.fourthline.cling.transport.spi.StreamServerConfiguration;

/* loaded from: classes5.dex */
public class AsyncServletStreamServerConfigurationImpl implements StreamServerConfiguration {
    protected int asyncTimeoutSeconds;
    protected int listenPort;
    protected ServletContainerAdapter servletContainerAdapter;

    public AsyncServletStreamServerConfigurationImpl(ServletContainerAdapter servletContainerAdapter) {
        this.listenPort = 0;
        this.asyncTimeoutSeconds = 60;
        this.servletContainerAdapter = servletContainerAdapter;
    }

    public AsyncServletStreamServerConfigurationImpl(ServletContainerAdapter servletContainerAdapter, int i) {
        this.listenPort = 0;
        this.asyncTimeoutSeconds = 60;
        this.servletContainerAdapter = servletContainerAdapter;
        this.listenPort = i;
    }

    public AsyncServletStreamServerConfigurationImpl(ServletContainerAdapter servletContainerAdapter, int i, int i2) {
        this.listenPort = 0;
        this.asyncTimeoutSeconds = 60;
        this.servletContainerAdapter = servletContainerAdapter;
        this.listenPort = i;
        this.asyncTimeoutSeconds = i2;
    }

    @Override // org.fourthline.cling.transport.spi.StreamServerConfiguration
    public int getListenPort() {
        return this.listenPort;
    }

    public void setListenPort(int i) {
        this.listenPort = i;
    }

    public int getAsyncTimeoutSeconds() {
        return this.asyncTimeoutSeconds;
    }

    public void setAsyncTimeoutSeconds(int i) {
        this.asyncTimeoutSeconds = i;
    }

    public ServletContainerAdapter getServletContainerAdapter() {
        return this.servletContainerAdapter;
    }

    public void setServletContainerAdapter(ServletContainerAdapter servletContainerAdapter) {
        this.servletContainerAdapter = servletContainerAdapter;
    }
}
