package io.netty.channel.socket.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.util.internal.PlatformDependent;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.util.Enumeration;

/* compiled from: NioDatagramChannelConfig.java */
/* loaded from: classes4.dex */
class a extends DefaultDatagramChannelConfig {
    private static final Object a;
    private static final Object b;
    private static final Object c;
    private static final Method d;
    private static final Method e;
    private final DatagramChannel f;

    static {
        Class<?> cls;
        Class<?> cls2;
        Object obj;
        Object obj2;
        Object obj3;
        Class<?> cls3;
        Method declaredMethod;
        ClassLoader classLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
        Method method = null;
        try {
            cls = Class.forName("java.net.SocketOption", true, classLoader);
        } catch (Exception unused) {
            cls = null;
        }
        try {
            cls2 = Class.forName("java.net.StandardSocketOptions", true, classLoader);
        } catch (Exception unused2) {
            cls2 = null;
        }
        if (cls != null) {
            try {
                obj = cls2.getDeclaredField("IP_MULTICAST_TTL").get(null);
                try {
                    obj2 = cls2.getDeclaredField("IP_MULTICAST_IF").get(null);
                    try {
                        obj3 = cls2.getDeclaredField("IP_MULTICAST_LOOP").get(null);
                        try {
                            cls3 = Class.forName("java.nio.channels.NetworkChannel", true, classLoader);
                        } catch (Throwable unused3) {
                            cls3 = null;
                        }
                        if (cls3 == null) {
                            declaredMethod = null;
                        } else {
                            try {
                                method = cls3.getDeclaredMethod("getOption", cls);
                                try {
                                    declaredMethod = cls3.getDeclaredMethod("setOption", cls, Object.class);
                                } catch (Exception e2) {
                                    throw new Error("cannot locate the setOption() method", e2);
                                }
                            } catch (Exception e3) {
                                throw new Error("cannot locate the getOption() method", e3);
                            }
                        }
                    } catch (Exception e4) {
                        throw new Error("cannot locate the IP_MULTICAST_LOOP field", e4);
                    }
                } catch (Exception e5) {
                    throw new Error("cannot locate the IP_MULTICAST_IF field", e5);
                }
            } catch (Exception e6) {
                throw new Error("cannot locate the IP_MULTICAST_TTL field", e6);
            }
        } else {
            declaredMethod = null;
            obj3 = null;
            obj = null;
            obj2 = null;
        }
        a = obj;
        b = obj2;
        c = obj3;
        d = method;
        e = declaredMethod;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(NioDatagramChannel nioDatagramChannel, DatagramChannel datagramChannel) {
        super(nioDatagramChannel, datagramChannel.socket());
        this.f = datagramChannel;
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public int getTimeToLive() {
        return ((Integer) a(a)).intValue();
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setTimeToLive(int i) {
        a(a, Integer.valueOf(i));
        return this;
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public InetAddress getInterface() {
        NetworkInterface networkInterface = getNetworkInterface();
        if (networkInterface == null) {
            return null;
        }
        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        if (inetAddresses.hasMoreElements()) {
            return inetAddresses.nextElement();
        }
        return null;
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setInterface(InetAddress inetAddress) {
        try {
            setNetworkInterface(NetworkInterface.getByInetAddress(inetAddress));
            return this;
        } catch (SocketException e2) {
            throw new ChannelException(e2);
        }
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public NetworkInterface getNetworkInterface() {
        return (NetworkInterface) a(b);
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        a(b, networkInterface);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public boolean isLoopbackModeDisabled() {
        return ((Boolean) a(c)).booleanValue();
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setLoopbackModeDisabled(boolean z) {
        a(c, Boolean.valueOf(z));
        return this;
    }

    @Override // io.netty.channel.socket.DefaultDatagramChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig
    protected void autoReadCleared() {
        ((NioDatagramChannel) this.channel).b();
    }

    private Object a(Object obj) {
        Method method = d;
        if (method != null) {
            try {
                return method.invoke(this.f, obj);
            } catch (Exception e2) {
                throw new ChannelException(e2);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void a(Object obj, Object obj2) {
        Method method = e;
        if (method != null) {
            try {
                method.invoke(this.f, obj, obj2);
            } catch (Exception e2) {
                throw new ChannelException(e2);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
