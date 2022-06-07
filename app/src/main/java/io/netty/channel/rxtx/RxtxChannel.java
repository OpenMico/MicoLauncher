package io.netty.channel.rxtx;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.util.internal.OneTimeTask;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class RxtxChannel extends OioByteStreamChannel {
    private static final RxtxDeviceAddress d = new RxtxDeviceAddress("localhost");
    private RxtxDeviceAddress g;
    private SerialPort h;
    private boolean f = true;
    private final RxtxChannelConfig e = new a(this);

    public RxtxChannel() {
        super(null);
    }

    @Override // io.netty.channel.Channel
    public RxtxChannelConfig config() {
        return this.e;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return this.f;
    }

    @Override // io.netty.channel.oio.AbstractOioChannel, io.netty.channel.AbstractChannel
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new a();
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        RxtxDeviceAddress rxtxDeviceAddress = (RxtxDeviceAddress) socketAddress;
        SerialPort open = CommPortIdentifier.getPortIdentifier(rxtxDeviceAddress.value()).open(getClass().getName(), 1000);
        open.enableReceiveTimeout(((Integer) config().getOption(RxtxChannelOption.READ_TIMEOUT)).intValue());
        this.g = rxtxDeviceAddress;
        this.h = open;
    }

    protected void doInit() throws Exception {
        this.h.setSerialPortParams(((Integer) config().getOption(RxtxChannelOption.BAUD_RATE)).intValue(), ((RxtxChannelConfig.Databits) config().getOption(RxtxChannelOption.DATA_BITS)).value(), ((RxtxChannelConfig.Stopbits) config().getOption(RxtxChannelOption.STOP_BITS)).value(), ((RxtxChannelConfig.Paritybit) config().getOption(RxtxChannelOption.PARITY_BIT)).value());
        this.h.setDTR(((Boolean) config().getOption(RxtxChannelOption.DTR)).booleanValue());
        this.h.setRTS(((Boolean) config().getOption(RxtxChannelOption.RTS)).booleanValue());
        activate(this.h.getInputStream(), this.h.getOutputStream());
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public RxtxDeviceAddress localAddress() {
        return (RxtxDeviceAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public RxtxDeviceAddress remoteAddress() {
        return (RxtxDeviceAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    public RxtxDeviceAddress localAddress0() {
        return d;
    }

    @Override // io.netty.channel.AbstractChannel
    public RxtxDeviceAddress remoteAddress0() {
        return this.g;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    /* JADX WARN: Finally extract failed */
    @Override // io.netty.channel.oio.OioByteStreamChannel, io.netty.channel.AbstractChannel
    public void doClose() throws Exception {
        this.f = false;
        try {
            super.doClose();
            SerialPort serialPort = this.h;
            if (serialPort != null) {
                serialPort.removeEventListener();
                this.h.close();
                this.h = null;
            }
        } catch (Throwable th) {
            SerialPort serialPort2 = this.h;
            if (serialPort2 != null) {
                serialPort2.removeEventListener();
                this.h.close();
                this.h = null;
            }
            throw th;
        }
    }

    @Override // io.netty.channel.oio.AbstractOioByteChannel
    protected boolean isInputShutdown() {
        return !this.f;
    }

    @Override // io.netty.channel.oio.AbstractOioByteChannel
    protected ChannelFuture shutdownInput() {
        return newFailedFuture(new UnsupportedOperationException("shutdownInput"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends AbstractChannel.AbstractUnsafe {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a() {
            super();
            RxtxChannel.this = r1;
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                try {
                    final boolean isActive = RxtxChannel.this.isActive();
                    RxtxChannel.this.doConnect(socketAddress, socketAddress2);
                    int intValue = ((Integer) RxtxChannel.this.config().getOption(RxtxChannelOption.WAIT_TIME)).intValue();
                    if (intValue > 0) {
                        RxtxChannel.this.eventLoop().schedule((Runnable) new OneTimeTask() { // from class: io.netty.channel.rxtx.RxtxChannel.a.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    RxtxChannel.this.doInit();
                                    a.this.safeSetSuccess(channelPromise);
                                    if (!isActive && RxtxChannel.this.isActive()) {
                                        RxtxChannel.this.pipeline().fireChannelActive();
                                    }
                                } catch (Throwable th) {
                                    a.this.safeSetFailure(channelPromise, th);
                                    a.this.closeIfClosed();
                                }
                            }
                        }, intValue, TimeUnit.MILLISECONDS);
                    } else {
                        RxtxChannel.this.doInit();
                        safeSetSuccess(channelPromise);
                        if (!isActive && RxtxChannel.this.isActive()) {
                            RxtxChannel.this.pipeline().fireChannelActive();
                        }
                    }
                } catch (Throwable th) {
                    safeSetFailure(channelPromise, th);
                    closeIfClosed();
                }
            }
        }
    }
}
