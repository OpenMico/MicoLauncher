package org.fourthline.cling.android;

import android.os.Build;
import org.fourthline.cling.DefaultUpnpServiceConfiguration;
import org.fourthline.cling.binding.xml.DeviceDescriptorBinder;
import org.fourthline.cling.binding.xml.RecoveringUDA10DeviceDescriptorBinderImpl;
import org.fourthline.cling.binding.xml.ServiceDescriptorBinder;
import org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl;
import org.fourthline.cling.model.Namespace;
import org.fourthline.cling.model.ServerClientTokens;
import org.fourthline.cling.transport.impl.AsyncServletStreamServerConfigurationImpl;
import org.fourthline.cling.transport.impl.AsyncServletStreamServerImpl;
import org.fourthline.cling.transport.impl.RecoveringGENAEventProcessorImpl;
import org.fourthline.cling.transport.impl.RecoveringSOAPActionProcessorImpl;
import org.fourthline.cling.transport.impl.jetty.JettyServletContainer;
import org.fourthline.cling.transport.impl.jetty.StreamClientConfigurationImpl;
import org.fourthline.cling.transport.impl.jetty.StreamClientImpl;
import org.fourthline.cling.transport.spi.GENAEventProcessor;
import org.fourthline.cling.transport.spi.NetworkAddressFactory;
import org.fourthline.cling.transport.spi.SOAPActionProcessor;
import org.fourthline.cling.transport.spi.StreamClient;
import org.fourthline.cling.transport.spi.StreamServer;

/* loaded from: classes5.dex */
public class AndroidUpnpServiceConfiguration extends DefaultUpnpServiceConfiguration {
    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration, org.fourthline.cling.UpnpServiceConfiguration
    public int getRegistryMaintenanceIntervalMillis() {
        return 3000;
    }

    public AndroidUpnpServiceConfiguration() {
        this(0);
    }

    public AndroidUpnpServiceConfiguration(int i) {
        super(i, false);
        System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration
    protected NetworkAddressFactory createNetworkAddressFactory(int i) {
        return new AndroidNetworkAddressFactory(i);
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration
    protected Namespace createNamespace() {
        return new Namespace("/upnp");
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration, org.fourthline.cling.UpnpServiceConfiguration
    public StreamClient createStreamClient() {
        return new StreamClientImpl(new StreamClientConfigurationImpl(getSyncProtocolExecutorService()) { // from class: org.fourthline.cling.android.AndroidUpnpServiceConfiguration.1
            @Override // org.fourthline.cling.transport.spi.AbstractStreamClientConfiguration, org.fourthline.cling.transport.spi.StreamClientConfiguration
            public String getUserAgentValue(int i, int i2) {
                ServerClientTokens serverClientTokens = new ServerClientTokens(i, i2);
                serverClientTokens.setOsName("Android");
                serverClientTokens.setOsVersion(Build.VERSION.RELEASE);
                return serverClientTokens.toString();
            }
        });
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration, org.fourthline.cling.UpnpServiceConfiguration
    public StreamServer createStreamServer(NetworkAddressFactory networkAddressFactory) {
        return new AsyncServletStreamServerImpl(new AsyncServletStreamServerConfigurationImpl(JettyServletContainer.INSTANCE, networkAddressFactory.getStreamListenPort()));
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration
    protected DeviceDescriptorBinder createDeviceDescriptorBinderUDA10() {
        return new RecoveringUDA10DeviceDescriptorBinderImpl();
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration
    protected ServiceDescriptorBinder createServiceDescriptorBinderUDA10() {
        return new UDA10ServiceDescriptorBinderSAXImpl();
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration
    protected SOAPActionProcessor createSOAPActionProcessor() {
        return new RecoveringSOAPActionProcessorImpl();
    }

    @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration
    protected GENAEventProcessor createGENAEventProcessor() {
        return new RecoveringGENAEventProcessorImpl();
    }
}
