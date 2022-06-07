package org.fourthline.cling.support.connectionmanager;

import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;
import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.ServiceReference;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.model.types.csv.CSV;
import org.fourthline.cling.support.connectionmanager.callback.ConnectionComplete;
import org.fourthline.cling.support.connectionmanager.callback.PrepareForConnection;
import org.fourthline.cling.support.model.ConnectionInfo;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.ProtocolInfos;

/* loaded from: classes5.dex */
public abstract class AbstractPeeringConnectionManagerService extends ConnectionManagerService {
    private static final Logger log = Logger.getLogger(AbstractPeeringConnectionManagerService.class.getName());

    protected abstract void closeConnection(ConnectionInfo connectionInfo);

    protected abstract ConnectionInfo createConnection(int i, int i2, ServiceReference serviceReference, ConnectionInfo.Direction direction, ProtocolInfo protocolInfo) throws ActionException;

    protected abstract void peerFailure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String str);

    protected AbstractPeeringConnectionManagerService(ConnectionInfo... connectionInfoArr) {
        super(connectionInfoArr);
    }

    protected AbstractPeeringConnectionManagerService(ProtocolInfos protocolInfos, ProtocolInfos protocolInfos2, ConnectionInfo... connectionInfoArr) {
        super(protocolInfos, protocolInfos2, connectionInfoArr);
    }

    protected AbstractPeeringConnectionManagerService(PropertyChangeSupport propertyChangeSupport, ProtocolInfos protocolInfos, ProtocolInfos protocolInfos2, ConnectionInfo... connectionInfoArr) {
        super(propertyChangeSupport, protocolInfos, protocolInfos2, connectionInfoArr);
    }

    protected synchronized int getNewConnectionId() {
        int i;
        i = -1;
        for (Integer num : this.activeConnections.keySet()) {
            if (num.intValue() > i) {
                i = num.intValue();
            }
        }
        return i + 1;
    }

    protected synchronized void storeConnection(ConnectionInfo connectionInfo) {
        CSV<UnsignedIntegerFourBytes> currentConnectionIDs = getCurrentConnectionIDs();
        this.activeConnections.put(Integer.valueOf(connectionInfo.getConnectionID()), connectionInfo);
        Logger logger = log;
        logger.fine("Connection stored, firing event: " + connectionInfo.getConnectionID());
        getPropertyChangeSupport().firePropertyChange("CurrentConnectionIDs", currentConnectionIDs, getCurrentConnectionIDs());
    }

    protected synchronized void removeConnection(int i) {
        CSV<UnsignedIntegerFourBytes> currentConnectionIDs = getCurrentConnectionIDs();
        this.activeConnections.remove(Integer.valueOf(i));
        Logger logger = log;
        logger.fine("Connection removed, firing event: " + i);
        getPropertyChangeSupport().firePropertyChange("CurrentConnectionIDs", currentConnectionIDs, getCurrentConnectionIDs());
    }

    @UpnpAction(out = {@UpnpOutputArgument(getterName = "getConnectionID", name = "ConnectionID", stateVariable = "A_ARG_TYPE_ConnectionID"), @UpnpOutputArgument(getterName = "getAvTransportID", name = "AVTransportID", stateVariable = "A_ARG_TYPE_AVTransportID"), @UpnpOutputArgument(getterName = "getRcsID", name = "RcsID", stateVariable = "A_ARG_TYPE_RcsID")})
    public synchronized ConnectionInfo prepareForConnection(@UpnpInputArgument(name = "RemoteProtocolInfo", stateVariable = "A_ARG_TYPE_ProtocolInfo") ProtocolInfo protocolInfo, @UpnpInputArgument(name = "PeerConnectionManager", stateVariable = "A_ARG_TYPE_ConnectionManager") ServiceReference serviceReference, @UpnpInputArgument(name = "PeerConnectionID", stateVariable = "A_ARG_TYPE_ConnectionID") int i, @UpnpInputArgument(name = "Direction", stateVariable = "A_ARG_TYPE_Direction") String str) throws ActionException {
        ConnectionInfo createConnection;
        int newConnectionId = getNewConnectionId();
        try {
            ConnectionInfo.Direction valueOf = ConnectionInfo.Direction.valueOf(str);
            Logger logger = log;
            logger.fine("Preparing for connection with local new ID " + newConnectionId + " and peer connection ID: " + i);
            createConnection = createConnection(newConnectionId, i, serviceReference, valueOf, protocolInfo);
            storeConnection(createConnection);
        } catch (Exception unused) {
            ErrorCode errorCode = ErrorCode.ARGUMENT_VALUE_INVALID;
            throw new ConnectionManagerException(errorCode, "Unsupported direction: " + str);
        }
        return createConnection;
    }

    @UpnpAction
    public synchronized void connectionComplete(@UpnpInputArgument(name = "ConnectionID", stateVariable = "A_ARG_TYPE_ConnectionID") int i) throws ActionException {
        ConnectionInfo currentConnectionInfo = getCurrentConnectionInfo(i);
        Logger logger = log;
        logger.fine("Closing connection ID " + i);
        closeConnection(currentConnectionInfo);
        removeConnection(i);
    }

    public synchronized int createConnectionWithPeer(ServiceReference serviceReference, ControlPoint controlPoint, final Service service, final ProtocolInfo protocolInfo, final ConnectionInfo.Direction direction) {
        final int newConnectionId;
        newConnectionId = getNewConnectionId();
        Logger logger = log;
        logger.fine("Creating new connection ID " + newConnectionId + " with peer: " + service);
        final boolean[] zArr = new boolean[1];
        new PrepareForConnection(service, controlPoint, protocolInfo, serviceReference, newConnectionId, direction) { // from class: org.fourthline.cling.support.connectionmanager.AbstractPeeringConnectionManagerService.1
            @Override // org.fourthline.cling.support.connectionmanager.callback.PrepareForConnection
            public void received(ActionInvocation actionInvocation, int i, int i2, int i3) {
                AbstractPeeringConnectionManagerService.this.storeConnection(new ConnectionInfo(newConnectionId, i2, i3, protocolInfo, service.getReference(), i, direction.getOpposite(), ConnectionInfo.Status.OK));
            }

            @Override // org.fourthline.cling.controlpoint.ActionCallback
            public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String str) {
                AbstractPeeringConnectionManagerService.this.peerFailure(actionInvocation, upnpResponse, str);
                zArr[0] = true;
            }
        }.run();
        if (zArr[0]) {
            newConnectionId = -1;
        }
        return newConnectionId;
    }

    public synchronized void closeConnectionWithPeer(ControlPoint controlPoint, Service service, int i) throws ActionException {
        closeConnectionWithPeer(controlPoint, service, getCurrentConnectionInfo(i));
    }

    public synchronized void closeConnectionWithPeer(ControlPoint controlPoint, Service service, final ConnectionInfo connectionInfo) throws ActionException {
        Logger logger = log;
        logger.fine("Closing connection ID " + connectionInfo.getConnectionID() + " with peer: " + service);
        new ConnectionComplete(service, controlPoint, connectionInfo.getPeerConnectionID()) { // from class: org.fourthline.cling.support.connectionmanager.AbstractPeeringConnectionManagerService.2
            @Override // org.fourthline.cling.controlpoint.ActionCallback
            public void success(ActionInvocation actionInvocation) {
                AbstractPeeringConnectionManagerService.this.removeConnection(connectionInfo.getConnectionID());
            }

            @Override // org.fourthline.cling.controlpoint.ActionCallback
            public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String str) {
                AbstractPeeringConnectionManagerService.this.peerFailure(actionInvocation, upnpResponse, str);
            }
        }.run();
    }
}
