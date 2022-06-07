package org.fourthline.cling;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import org.fourthline.cling.binding.xml.DeviceDescriptorBinder;
import org.fourthline.cling.binding.xml.ServiceDescriptorBinder;
import org.fourthline.cling.model.Namespace;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.model.meta.RemoteService;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.transport.spi.DatagramIO;
import org.fourthline.cling.transport.spi.DatagramProcessor;
import org.fourthline.cling.transport.spi.GENAEventProcessor;
import org.fourthline.cling.transport.spi.MulticastReceiver;
import org.fourthline.cling.transport.spi.NetworkAddressFactory;
import org.fourthline.cling.transport.spi.SOAPActionProcessor;
import org.fourthline.cling.transport.spi.StreamClient;
import org.fourthline.cling.transport.spi.StreamServer;

/* loaded from: classes5.dex */
public interface UpnpServiceConfiguration {
    DatagramIO createDatagramIO(NetworkAddressFactory networkAddressFactory);

    MulticastReceiver createMulticastReceiver(NetworkAddressFactory networkAddressFactory);

    NetworkAddressFactory createNetworkAddressFactory();

    StreamClient createStreamClient();

    StreamServer createStreamServer(NetworkAddressFactory networkAddressFactory);

    int getAliveIntervalMillis();

    Executor getAsyncProtocolExecutor();

    Executor getDatagramIOExecutor();

    DatagramProcessor getDatagramProcessor();

    UpnpHeaders getDescriptorRetrievalHeaders(RemoteDeviceIdentity remoteDeviceIdentity);

    DeviceDescriptorBinder getDeviceDescriptorBinderUDA10();

    UpnpHeaders getEventSubscriptionHeaders(RemoteService remoteService);

    ServiceType[] getExclusiveServiceTypes();

    GENAEventProcessor getGenaEventProcessor();

    Executor getMulticastReceiverExecutor();

    Namespace getNamespace();

    Executor getRegistryListenerExecutor();

    Executor getRegistryMaintainerExecutor();

    int getRegistryMaintenanceIntervalMillis();

    Integer getRemoteDeviceMaxAgeSeconds();

    ServiceDescriptorBinder getServiceDescriptorBinderUDA10();

    SOAPActionProcessor getSoapActionProcessor();

    ExecutorService getStreamServerExecutorService();

    ExecutorService getSyncProtocolExecutorService();

    boolean isReceivedSubscriptionTimeoutIgnored();

    void shutdown();
}
