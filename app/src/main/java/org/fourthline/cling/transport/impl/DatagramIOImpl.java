package org.fourthline.cling.transport.impl;

import com.xiaomi.mipush.sdk.Constants;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.message.OutgoingDatagramMessage;
import org.fourthline.cling.transport.Router;
import org.fourthline.cling.transport.spi.DatagramIO;
import org.fourthline.cling.transport.spi.DatagramProcessor;
import org.fourthline.cling.transport.spi.InitializationException;

/* loaded from: classes5.dex */
public class DatagramIOImpl implements DatagramIO<DatagramIOConfigurationImpl> {
    private static Logger log = Logger.getLogger(DatagramIO.class.getName());
    protected final DatagramIOConfigurationImpl configuration;
    protected DatagramProcessor datagramProcessor;
    protected InetSocketAddress localAddress;
    protected Router router;
    protected MulticastSocket socket;

    public DatagramIOImpl(DatagramIOConfigurationImpl datagramIOConfigurationImpl) {
        this.configuration = datagramIOConfigurationImpl;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.fourthline.cling.transport.spi.DatagramIO
    public DatagramIOConfigurationImpl getConfiguration() {
        return this.configuration;
    }

    @Override // org.fourthline.cling.transport.spi.DatagramIO
    public synchronized void init(InetAddress inetAddress, Router router, DatagramProcessor datagramProcessor) throws InitializationException {
        this.router = router;
        this.datagramProcessor = datagramProcessor;
        try {
            Logger logger = log;
            logger.info("Creating bound socket (for datagram input/output) on: " + inetAddress);
            this.localAddress = new InetSocketAddress(inetAddress, 0);
            this.socket = new MulticastSocket(this.localAddress);
            this.socket.setTimeToLive(this.configuration.getTimeToLive());
            this.socket.setReceiveBufferSize(262144);
        } catch (Exception e) {
            throw new InitializationException("Could not initialize " + getClass().getSimpleName() + ": " + e);
        }
    }

    @Override // org.fourthline.cling.transport.spi.DatagramIO
    public synchronized void stop() {
        if (this.socket != null && !this.socket.isClosed()) {
            this.socket.close();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        Logger logger = log;
        logger.fine("Entering blocking receiving loop, listening for UDP datagrams on: " + this.socket.getLocalAddress());
        while (true) {
            try {
                byte[] bArr = new byte[getConfiguration().getMaxDatagramBytes()];
                DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
                this.socket.receive(datagramPacket);
                Logger logger2 = log;
                logger2.fine("UDP datagram received from: " + datagramPacket.getAddress().getHostAddress() + Constants.COLON_SEPARATOR + datagramPacket.getPort() + " on: " + this.localAddress);
                this.router.received(this.datagramProcessor.read(this.localAddress.getAddress(), datagramPacket));
            } catch (SocketException unused) {
                log.fine("Socket closed");
                try {
                    if (!this.socket.isClosed()) {
                        log.fine("Closing unicast socket");
                        this.socket.close();
                        return;
                    }
                    return;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (UnsupportedDataException e2) {
                Logger logger3 = log;
                logger3.info("Could not read datagram: " + e2.getMessage());
            } catch (Exception e3) {
                throw new RuntimeException(e3);
            }
        }
    }

    @Override // org.fourthline.cling.transport.spi.DatagramIO
    public synchronized void send(OutgoingDatagramMessage outgoingDatagramMessage) {
        if (log.isLoggable(Level.FINE)) {
            Logger logger = log;
            logger.fine("Sending message from address: " + this.localAddress);
        }
        DatagramPacket write = this.datagramProcessor.write(outgoingDatagramMessage);
        if (log.isLoggable(Level.FINE)) {
            Logger logger2 = log;
            logger2.fine("Sending UDP datagram packet to: " + outgoingDatagramMessage.getDestinationAddress() + Constants.COLON_SEPARATOR + outgoingDatagramMessage.getDestinationPort());
        }
        send(write);
    }

    @Override // org.fourthline.cling.transport.spi.DatagramIO
    public synchronized void send(DatagramPacket datagramPacket) {
        if (log.isLoggable(Level.FINE)) {
            Logger logger = log;
            logger.fine("Sending message from address: " + this.localAddress);
        }
        try {
            this.socket.send(datagramPacket);
        } catch (RuntimeException e) {
            throw e;
        } catch (SocketException unused) {
            Logger logger2 = log;
            logger2.fine("Socket closed, aborting datagram send to: " + datagramPacket.getAddress());
        } catch (Exception e2) {
            Logger logger3 = log;
            Level level = Level.SEVERE;
            logger3.log(level, "Exception sending datagram to: " + datagramPacket.getAddress() + ": " + e2, (Throwable) e2);
        }
    }
}
