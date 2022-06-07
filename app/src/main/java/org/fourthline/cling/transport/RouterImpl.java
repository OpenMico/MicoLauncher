package org.fourthline.cling.transport;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.model.NetworkAddress;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.OutgoingDatagramMessage;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.protocol.ProtocolCreationException;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.protocol.ReceivingAsync;
import org.fourthline.cling.transport.spi.DatagramIO;
import org.fourthline.cling.transport.spi.InitializationException;
import org.fourthline.cling.transport.spi.MulticastReceiver;
import org.fourthline.cling.transport.spi.NetworkAddressFactory;
import org.fourthline.cling.transport.spi.NoNetworkException;
import org.fourthline.cling.transport.spi.StreamClient;
import org.fourthline.cling.transport.spi.StreamServer;
import org.fourthline.cling.transport.spi.UpnpStream;
import org.seamless.util.Exceptions;

@ApplicationScoped
/* loaded from: classes5.dex */
public class RouterImpl implements Router {
    private static Logger log = Logger.getLogger(Router.class.getName());
    protected UpnpServiceConfiguration configuration;
    protected volatile boolean enabled;
    protected NetworkAddressFactory networkAddressFactory;
    protected ProtocolFactory protocolFactory;
    protected StreamClient streamClient;
    protected ReentrantReadWriteLock routerLock = new ReentrantReadWriteLock(true);
    protected Lock readLock = this.routerLock.readLock();
    protected Lock writeLock = this.routerLock.writeLock();
    protected final Map<NetworkInterface, MulticastReceiver> multicastReceivers = new HashMap();
    protected final Map<InetAddress, DatagramIO> datagramIOs = new HashMap();
    protected final Map<InetAddress, StreamServer> streamServers = new HashMap();

    protected int getLockTimeoutMillis() {
        return 6000;
    }

    protected RouterImpl() {
    }

    @Inject
    public RouterImpl(UpnpServiceConfiguration upnpServiceConfiguration, ProtocolFactory protocolFactory) {
        Logger logger = log;
        logger.info("Creating Router: " + getClass().getName());
        this.configuration = upnpServiceConfiguration;
        this.protocolFactory = protocolFactory;
    }

    public boolean enable(@Observes @Default EnableRouter enableRouter) throws RouterException {
        return enable();
    }

    public boolean disable(@Observes @Default DisableRouter disableRouter) throws RouterException {
        return disable();
    }

    @Override // org.fourthline.cling.transport.Router
    public UpnpServiceConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override // org.fourthline.cling.transport.Router
    public ProtocolFactory getProtocolFactory() {
        return this.protocolFactory;
    }

    @Override // org.fourthline.cling.transport.Router
    public boolean enable() throws RouterException {
        lock(this.writeLock);
        try {
            if (!this.enabled) {
                try {
                    log.fine("Starting networking services...");
                    this.networkAddressFactory = getConfiguration().createNetworkAddressFactory();
                    startInterfaceBasedTransports(this.networkAddressFactory.getNetworkInterfaces());
                    startAddressBasedTransports(this.networkAddressFactory.getBindAddresses());
                    if (this.networkAddressFactory.hasUsableNetwork()) {
                        this.streamClient = getConfiguration().createStreamClient();
                        this.enabled = true;
                        return true;
                    }
                    throw new NoNetworkException("No usable network interface and/or addresses available, check the log for errors.");
                } catch (InitializationException e) {
                    handleStartFailure(e);
                }
            }
            return false;
        } finally {
            unlock(this.writeLock);
        }
    }

    @Override // org.fourthline.cling.transport.Router
    public boolean disable() throws RouterException {
        lock(this.writeLock);
        try {
            if (!this.enabled) {
                return false;
            }
            log.fine("Disabling network services...");
            if (this.streamClient != null) {
                log.fine("Stopping stream client connection management/pool");
                this.streamClient.stop();
                this.streamClient = null;
            }
            for (Map.Entry<InetAddress, StreamServer> entry : this.streamServers.entrySet()) {
                Logger logger = log;
                logger.fine("Stopping stream server on address: " + entry.getKey());
                entry.getValue().stop();
            }
            this.streamServers.clear();
            for (Map.Entry<NetworkInterface, MulticastReceiver> entry2 : this.multicastReceivers.entrySet()) {
                Logger logger2 = log;
                logger2.fine("Stopping multicast receiver on interface: " + entry2.getKey().getDisplayName());
                entry2.getValue().stop();
            }
            this.multicastReceivers.clear();
            for (Map.Entry<InetAddress, DatagramIO> entry3 : this.datagramIOs.entrySet()) {
                Logger logger3 = log;
                logger3.fine("Stopping datagram I/O on address: " + entry3.getKey());
                entry3.getValue().stop();
            }
            this.datagramIOs.clear();
            this.networkAddressFactory = null;
            this.enabled = false;
            return true;
        } finally {
            unlock(this.writeLock);
        }
    }

    @Override // org.fourthline.cling.transport.Router
    public void shutdown() throws RouterException {
        disable();
    }

    @Override // org.fourthline.cling.transport.Router
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override // org.fourthline.cling.transport.Router
    public void handleStartFailure(InitializationException initializationException) throws InitializationException {
        if (initializationException instanceof NoNetworkException) {
            log.info("Unable to initialize network router, no network found.");
            return;
        }
        Logger logger = log;
        logger.severe("Unable to initialize network router: " + initializationException);
        Logger logger2 = log;
        logger2.severe("Cause: " + Exceptions.unwrap(initializationException));
    }

    @Override // org.fourthline.cling.transport.Router
    public List<NetworkAddress> getActiveStreamServers(InetAddress inetAddress) throws RouterException {
        StreamServer streamServer;
        lock(this.readLock);
        try {
            if (!this.enabled || this.streamServers.size() <= 0) {
                return Collections.EMPTY_LIST;
            }
            ArrayList arrayList = new ArrayList();
            if (inetAddress == null || (streamServer = this.streamServers.get(inetAddress)) == null) {
                for (Map.Entry<InetAddress, StreamServer> entry : this.streamServers.entrySet()) {
                    arrayList.add(new NetworkAddress(entry.getKey(), entry.getValue().getPort(), this.networkAddressFactory.getHardwareAddress(entry.getKey())));
                }
                return arrayList;
            }
            arrayList.add(new NetworkAddress(inetAddress, streamServer.getPort(), this.networkAddressFactory.getHardwareAddress(inetAddress)));
            return arrayList;
        } finally {
            unlock(this.readLock);
        }
    }

    @Override // org.fourthline.cling.transport.Router
    public void received(IncomingDatagramMessage incomingDatagramMessage) {
        if (!this.enabled) {
            Logger logger = log;
            logger.fine("Router disabled, ignoring incoming message: " + incomingDatagramMessage);
            return;
        }
        try {
            ReceivingAsync createReceivingAsync = getProtocolFactory().createReceivingAsync(incomingDatagramMessage);
            if (createReceivingAsync != null) {
                if (log.isLoggable(Level.FINE)) {
                    Logger logger2 = log;
                    logger2.fine("Received asynchronous message: " + incomingDatagramMessage);
                }
                getConfiguration().getAsyncProtocolExecutor().execute(createReceivingAsync);
            } else if (log.isLoggable(Level.FINEST)) {
                Logger logger3 = log;
                logger3.finest("No protocol, ignoring received message: " + incomingDatagramMessage);
            }
        } catch (ProtocolCreationException e) {
            Logger logger4 = log;
            logger4.warning("Handling received datagram failed - " + Exceptions.unwrap(e).toString());
        }
    }

    @Override // org.fourthline.cling.transport.Router
    public void received(UpnpStream upnpStream) {
        if (!this.enabled) {
            Logger logger = log;
            logger.fine("Router disabled, ignoring incoming: " + upnpStream);
            return;
        }
        Logger logger2 = log;
        logger2.fine("Received synchronous stream: " + upnpStream);
        getConfiguration().getSyncProtocolExecutorService().execute(upnpStream);
    }

    @Override // org.fourthline.cling.transport.Router
    public void send(OutgoingDatagramMessage outgoingDatagramMessage) throws RouterException {
        lock(this.readLock);
        try {
            if (this.enabled) {
                for (DatagramIO datagramIO : this.datagramIOs.values()) {
                    datagramIO.send(outgoingDatagramMessage);
                }
            } else {
                Logger logger = log;
                logger.fine("Router disabled, not sending datagram: " + outgoingDatagramMessage);
            }
        } finally {
            unlock(this.readLock);
        }
    }

    @Override // org.fourthline.cling.transport.Router
    public StreamResponseMessage send(StreamRequestMessage streamRequestMessage) throws RouterException {
        lock(this.readLock);
        try {
            if (!this.enabled) {
                Logger logger = log;
                logger.fine("Router disabled, not sending stream request: " + streamRequestMessage);
                return null;
            } else if (this.streamClient == null) {
                Logger logger2 = log;
                logger2.fine("No StreamClient available, not sending: " + streamRequestMessage);
                return null;
            } else {
                Logger logger3 = log;
                logger3.fine("Sending via TCP unicast stream: " + streamRequestMessage);
                try {
                    return this.streamClient.sendRequest(streamRequestMessage);
                } catch (InterruptedException e) {
                    throw new RouterException("Sending stream request was interrupted", e);
                }
            }
        } finally {
            unlock(this.readLock);
        }
    }

    @Override // org.fourthline.cling.transport.Router
    public void broadcast(byte[] bArr) throws RouterException {
        lock(this.readLock);
        try {
            if (this.enabled) {
                for (Map.Entry<InetAddress, DatagramIO> entry : this.datagramIOs.entrySet()) {
                    InetAddress broadcastAddress = this.networkAddressFactory.getBroadcastAddress(entry.getKey());
                    if (broadcastAddress != null) {
                        Logger logger = log;
                        logger.fine("Sending UDP datagram to broadcast address: " + broadcastAddress.getHostAddress());
                        entry.getValue().send(new DatagramPacket(bArr, bArr.length, broadcastAddress, 9));
                    }
                }
            } else {
                Logger logger2 = log;
                logger2.fine("Router disabled, not broadcasting bytes: " + bArr.length);
            }
        } finally {
            unlock(this.readLock);
        }
    }

    protected void startInterfaceBasedTransports(Iterator<NetworkInterface> it) throws InitializationException {
        while (it.hasNext()) {
            NetworkInterface next = it.next();
            MulticastReceiver createMulticastReceiver = getConfiguration().createMulticastReceiver(this.networkAddressFactory);
            if (createMulticastReceiver == null) {
                Logger logger = log;
                logger.info("Configuration did not create a MulticastReceiver for: " + next);
            } else {
                try {
                    if (log.isLoggable(Level.FINE)) {
                        Logger logger2 = log;
                        logger2.fine("Init multicast receiver on interface: " + next.getDisplayName());
                    }
                    createMulticastReceiver.init(next, this, this.networkAddressFactory, getConfiguration().getDatagramProcessor());
                    this.multicastReceivers.put(next, createMulticastReceiver);
                } catch (InitializationException e) {
                    throw e;
                }
            }
        }
        for (Map.Entry<NetworkInterface, MulticastReceiver> entry : this.multicastReceivers.entrySet()) {
            if (log.isLoggable(Level.FINE)) {
                Logger logger3 = log;
                logger3.fine("Starting multicast receiver on interface: " + entry.getKey().getDisplayName());
            }
            getConfiguration().getMulticastReceiverExecutor().execute(entry.getValue());
        }
    }

    protected void startAddressBasedTransports(Iterator<InetAddress> it) throws InitializationException {
        while (it.hasNext()) {
            InetAddress next = it.next();
            StreamServer createStreamServer = getConfiguration().createStreamServer(this.networkAddressFactory);
            if (createStreamServer == null) {
                Logger logger = log;
                logger.info("Configuration did not create a StreamServer for: " + next);
            } else {
                try {
                    if (log.isLoggable(Level.FINE)) {
                        Logger logger2 = log;
                        logger2.fine("Init stream server on address: " + next);
                    }
                    createStreamServer.init(next, this);
                    this.streamServers.put(next, createStreamServer);
                } catch (InitializationException e) {
                    Throwable unwrap = Exceptions.unwrap(e);
                    if (unwrap instanceof BindException) {
                        Logger logger3 = log;
                        logger3.warning("Failed to init StreamServer: " + unwrap);
                        if (log.isLoggable(Level.FINE)) {
                            log.log(Level.FINE, "Initialization exception root cause", unwrap);
                        }
                        Logger logger4 = log;
                        logger4.warning("Removing unusable address: " + next);
                        it.remove();
                    } else {
                        throw e;
                    }
                }
            }
            DatagramIO createDatagramIO = getConfiguration().createDatagramIO(this.networkAddressFactory);
            if (createDatagramIO == null) {
                Logger logger5 = log;
                logger5.info("Configuration did not create a StreamServer for: " + next);
            } else {
                try {
                    if (log.isLoggable(Level.FINE)) {
                        Logger logger6 = log;
                        logger6.fine("Init datagram I/O on address: " + next);
                    }
                    createDatagramIO.init(next, this, getConfiguration().getDatagramProcessor());
                    this.datagramIOs.put(next, createDatagramIO);
                } catch (InitializationException e2) {
                    throw e2;
                }
            }
        }
        for (Map.Entry<InetAddress, StreamServer> entry : this.streamServers.entrySet()) {
            if (log.isLoggable(Level.FINE)) {
                Logger logger7 = log;
                logger7.fine("Starting stream server on address: " + entry.getKey());
            }
            getConfiguration().getStreamServerExecutorService().execute(entry.getValue());
        }
        for (Map.Entry<InetAddress, DatagramIO> entry2 : this.datagramIOs.entrySet()) {
            if (log.isLoggable(Level.FINE)) {
                Logger logger8 = log;
                logger8.fine("Starting datagram I/O on address: " + entry2.getKey());
            }
            getConfiguration().getDatagramIOExecutor().execute(entry2.getValue());
        }
    }

    protected void lock(Lock lock, int i) throws RouterException {
        try {
            Logger logger = log;
            logger.finest("Trying to obtain lock with timeout milliseconds '" + i + "': " + lock.getClass().getSimpleName());
            if (lock.tryLock(i, TimeUnit.MILLISECONDS)) {
                Logger logger2 = log;
                logger2.finest("Acquired router lock: " + lock.getClass().getSimpleName());
                return;
            }
            throw new RouterException("Router wasn't available exclusively after waiting " + i + "ms, lock failed: " + lock.getClass().getSimpleName());
        } catch (InterruptedException e) {
            throw new RouterException("Interruption while waiting for exclusive access: " + lock.getClass().getSimpleName(), e);
        }
    }

    protected void lock(Lock lock) throws RouterException {
        lock(lock, getLockTimeoutMillis());
    }

    protected void unlock(Lock lock) {
        Logger logger = log;
        logger.finest("Releasing router lock: " + lock.getClass().getSimpleName());
        lock.unlock();
    }
}
