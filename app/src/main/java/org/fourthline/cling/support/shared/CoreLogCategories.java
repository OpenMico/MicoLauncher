package org.fourthline.cling.support.shared;

import com.xiaomi.ai.api.AIApiConstants;
import java.util.ArrayList;
import java.util.logging.Level;
import org.fourthline.cling.binding.xml.DeviceDescriptorBinder;
import org.fourthline.cling.binding.xml.ServiceDescriptorBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.protocol.RetrieveRemoteDescriptors;
import org.fourthline.cling.protocol.sync.ReceivingAction;
import org.fourthline.cling.protocol.sync.ReceivingEvent;
import org.fourthline.cling.protocol.sync.ReceivingRetrieval;
import org.fourthline.cling.protocol.sync.ReceivingSubscribe;
import org.fourthline.cling.protocol.sync.ReceivingUnsubscribe;
import org.fourthline.cling.protocol.sync.SendingAction;
import org.fourthline.cling.protocol.sync.SendingEvent;
import org.fourthline.cling.protocol.sync.SendingRenewal;
import org.fourthline.cling.protocol.sync.SendingSubscribe;
import org.fourthline.cling.protocol.sync.SendingUnsubscribe;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.transport.Router;
import org.fourthline.cling.transport.spi.DatagramIO;
import org.fourthline.cling.transport.spi.DatagramProcessor;
import org.fourthline.cling.transport.spi.GENAEventProcessor;
import org.fourthline.cling.transport.spi.MulticastReceiver;
import org.fourthline.cling.transport.spi.SOAPActionProcessor;
import org.fourthline.cling.transport.spi.StreamClient;
import org.fourthline.cling.transport.spi.StreamServer;
import org.fourthline.cling.transport.spi.UpnpStream;
import org.seamless.swing.logging.LogCategory;

/* loaded from: classes5.dex */
public class CoreLogCategories extends ArrayList<LogCategory> {
    public CoreLogCategories() {
        super(10);
        add(new LogCategory(AIApiConstants.Network.NAME, new LogCategory.Group[]{new LogCategory.Group("UDP communication", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(DatagramIO.class.getName(), Level.FINE), new LogCategory.LoggerLevel(MulticastReceiver.class.getName(), Level.FINE)}), new LogCategory.Group("UDP datagram processing and content", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(DatagramProcessor.class.getName(), Level.FINER)}), new LogCategory.Group("TCP communication", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(UpnpStream.class.getName(), Level.FINER), new LogCategory.LoggerLevel(StreamServer.class.getName(), Level.FINE), new LogCategory.LoggerLevel(StreamClient.class.getName(), Level.FINE)}), new LogCategory.Group("SOAP action message processing and content", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(SOAPActionProcessor.class.getName(), Level.FINER)}), new LogCategory.Group("GENA event message processing and content", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(GENAEventProcessor.class.getName(), Level.FINER)}), new LogCategory.Group("HTTP header processing", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(UpnpHeaders.class.getName(), Level.FINER)})}));
        add(new LogCategory("UPnP Protocol", new LogCategory.Group[]{new LogCategory.Group("Discovery (Notification & Search)", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(ProtocolFactory.class.getName(), Level.FINER), new LogCategory.LoggerLevel("org.fourthline.cling.protocol.async", Level.FINER)}), new LogCategory.Group("Description", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(ProtocolFactory.class.getName(), Level.FINER), new LogCategory.LoggerLevel(RetrieveRemoteDescriptors.class.getName(), Level.FINE), new LogCategory.LoggerLevel(ReceivingRetrieval.class.getName(), Level.FINE), new LogCategory.LoggerLevel(DeviceDescriptorBinder.class.getName(), Level.FINE), new LogCategory.LoggerLevel(ServiceDescriptorBinder.class.getName(), Level.FINE)}), new LogCategory.Group("Control", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(ProtocolFactory.class.getName(), Level.FINER), new LogCategory.LoggerLevel(ReceivingAction.class.getName(), Level.FINER), new LogCategory.LoggerLevel(SendingAction.class.getName(), Level.FINER)}), new LogCategory.Group("GENA ", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel("org.fourthline.cling.model.gena", Level.FINER), new LogCategory.LoggerLevel(ProtocolFactory.class.getName(), Level.FINER), new LogCategory.LoggerLevel(ReceivingEvent.class.getName(), Level.FINER), new LogCategory.LoggerLevel(ReceivingSubscribe.class.getName(), Level.FINER), new LogCategory.LoggerLevel(ReceivingUnsubscribe.class.getName(), Level.FINER), new LogCategory.LoggerLevel(SendingEvent.class.getName(), Level.FINER), new LogCategory.LoggerLevel(SendingSubscribe.class.getName(), Level.FINER), new LogCategory.LoggerLevel(SendingUnsubscribe.class.getName(), Level.FINER), new LogCategory.LoggerLevel(SendingRenewal.class.getName(), Level.FINER)})}));
        add(new LogCategory("Core", new LogCategory.Group[]{new LogCategory.Group("Router", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(Router.class.getName(), Level.FINER)}), new LogCategory.Group("Registry", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel(Registry.class.getName(), Level.FINER)}), new LogCategory.Group("Local service binding & invocation", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel("org.fourthline.cling.binding.annotations", Level.FINER), new LogCategory.LoggerLevel(LocalService.class.getName(), Level.FINER), new LogCategory.LoggerLevel("org.fourthline.cling.model.action", Level.FINER), new LogCategory.LoggerLevel("org.fourthline.cling.model.state", Level.FINER), new LogCategory.LoggerLevel(DefaultServiceManager.class.getName(), Level.FINER)}), new LogCategory.Group("Control Point interaction", new LogCategory.LoggerLevel[]{new LogCategory.LoggerLevel("org.fourthline.cling.controlpoint", Level.FINER)})}));
    }
}
