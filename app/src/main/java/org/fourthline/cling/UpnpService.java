package org.fourthline.cling;

import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.transport.Router;

/* loaded from: classes5.dex */
public interface UpnpService {

    /* loaded from: classes5.dex */
    public static class Shutdown {
    }

    /* loaded from: classes5.dex */
    public static class Start {
    }

    UpnpServiceConfiguration getConfiguration();

    ControlPoint getControlPoint();

    ProtocolFactory getProtocolFactory();

    Registry getRegistry();

    Router getRouter();

    void shutdown();
}
