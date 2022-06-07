package io.netty.handler.codec.haproxy;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.haproxy.HAProxyProxiedProtocol;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class HAProxyMessage {
    private static final HAProxyMessage a = new HAProxyMessage(HAProxyProtocolVersion.V1, HAProxyCommand.PROXY, HAProxyProxiedProtocol.UNKNOWN, (String) null, (String) null, 0, 0);
    private static final HAProxyMessage b = new HAProxyMessage(HAProxyProtocolVersion.V2, HAProxyCommand.PROXY, HAProxyProxiedProtocol.UNKNOWN, (String) null, (String) null, 0, 0);
    private static final HAProxyMessage c = new HAProxyMessage(HAProxyProtocolVersion.V2, HAProxyCommand.LOCAL, HAProxyProxiedProtocol.UNKNOWN, (String) null, (String) null, 0, 0);
    private final HAProxyProtocolVersion d;
    private final HAProxyCommand e;
    private final HAProxyProxiedProtocol f;
    private final String g;
    private final String h;
    private final int i;
    private final int j;

    private HAProxyMessage(HAProxyProtocolVersion hAProxyProtocolVersion, HAProxyCommand hAProxyCommand, HAProxyProxiedProtocol hAProxyProxiedProtocol, String str, String str2, String str3, String str4) {
        this(hAProxyProtocolVersion, hAProxyCommand, hAProxyProxiedProtocol, str, str2, b(str3), b(str4));
    }

    private HAProxyMessage(HAProxyProtocolVersion hAProxyProtocolVersion, HAProxyCommand hAProxyCommand, HAProxyProxiedProtocol hAProxyProxiedProtocol, String str, String str2, int i, int i2) {
        if (hAProxyProxiedProtocol != null) {
            HAProxyProxiedProtocol.AddressFamily addressFamily = hAProxyProxiedProtocol.addressFamily();
            a(str, addressFamily);
            a(str2, addressFamily);
            a(i);
            a(i2);
            this.d = hAProxyProtocolVersion;
            this.e = hAProxyCommand;
            this.f = hAProxyProxiedProtocol;
            this.g = str;
            this.h = str2;
            this.i = i;
            this.j = i2;
            return;
        }
        throw new NullPointerException("proxiedProtocol");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HAProxyMessage a(ByteBuf byteBuf) {
        int i;
        String str;
        String str2;
        if (byteBuf != null) {
            int i2 = 16;
            if (byteBuf.readableBytes() >= 16) {
                byteBuf.skipBytes(12);
                byte readByte = byteBuf.readByte();
                try {
                    HAProxyProtocolVersion valueOf = HAProxyProtocolVersion.valueOf(readByte);
                    if (valueOf == HAProxyProtocolVersion.V2) {
                        try {
                            HAProxyCommand valueOf2 = HAProxyCommand.valueOf(readByte);
                            if (valueOf2 == HAProxyCommand.LOCAL) {
                                return c;
                            }
                            try {
                                HAProxyProxiedProtocol valueOf3 = HAProxyProxiedProtocol.valueOf(byteBuf.readByte());
                                if (valueOf3 == HAProxyProxiedProtocol.UNKNOWN) {
                                    return b;
                                }
                                int readUnsignedShort = byteBuf.readUnsignedShort();
                                HAProxyProxiedProtocol.AddressFamily addressFamily = valueOf3.addressFamily();
                                int i3 = 0;
                                if (addressFamily != HAProxyProxiedProtocol.AddressFamily.AF_UNIX) {
                                    if (addressFamily == HAProxyProxiedProtocol.AddressFamily.AF_IPv4) {
                                        if (readUnsignedShort < 12 || byteBuf.readableBytes() < 12) {
                                            throw new HAProxyProtocolException("incomplete IPv4 address information: " + Math.min(readUnsignedShort, byteBuf.readableBytes()) + " bytes (expected: 12+ bytes)");
                                        }
                                        i2 = 4;
                                    } else if (addressFamily != HAProxyProxiedProtocol.AddressFamily.AF_IPv6) {
                                        throw new HAProxyProtocolException("unable to parse address information (unkown address family: " + addressFamily + ')');
                                    } else if (readUnsignedShort < 36 || byteBuf.readableBytes() < 36) {
                                        throw new HAProxyProtocolException("incomplete IPv6 address information: " + Math.min(readUnsignedShort, byteBuf.readableBytes()) + " bytes (expected: 36+ bytes)");
                                    }
                                    String a2 = a(byteBuf, i2);
                                    String a3 = a(byteBuf, i2);
                                    int readUnsignedShort2 = byteBuf.readUnsignedShort();
                                    i = byteBuf.readUnsignedShort();
                                    str2 = a2;
                                    str = a3;
                                    i3 = readUnsignedShort2;
                                } else if (readUnsignedShort < 216 || byteBuf.readableBytes() < 216) {
                                    throw new HAProxyProtocolException("incomplete UNIX socket address information: " + Math.min(readUnsignedShort, byteBuf.readableBytes()) + " bytes (expected: 216+ bytes)");
                                } else {
                                    int readerIndex = byteBuf.readerIndex();
                                    int i4 = 108;
                                    int forEachByte = byteBuf.forEachByte(readerIndex, 108, ByteProcessor.FIND_NUL);
                                    str2 = byteBuf.toString(readerIndex, forEachByte == -1 ? 108 : forEachByte - readerIndex, CharsetUtil.US_ASCII);
                                    int i5 = readerIndex + 108;
                                    int forEachByte2 = byteBuf.forEachByte(i5, 108, ByteProcessor.FIND_NUL);
                                    if (forEachByte2 != -1) {
                                        i4 = forEachByte2 - i5;
                                    }
                                    str = byteBuf.toString(i5, i4, CharsetUtil.US_ASCII);
                                    i = 0;
                                }
                                return new HAProxyMessage(valueOf, valueOf2, valueOf3, str2, str, i3, i);
                            } catch (IllegalArgumentException e) {
                                throw new HAProxyProtocolException(e);
                            }
                        } catch (IllegalArgumentException e2) {
                            throw new HAProxyProtocolException(e2);
                        }
                    } else {
                        throw new HAProxyProtocolException("version 1 unsupported: 0x" + Integer.toHexString(readByte));
                    }
                } catch (IllegalArgumentException e3) {
                    throw new HAProxyProtocolException(e3);
                }
            } else {
                throw new HAProxyProtocolException("incomplete header: " + byteBuf.readableBytes() + " bytes (expected: 16+ bytes)");
            }
        } else {
            throw new NullPointerException("header");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HAProxyMessage a(String str) {
        if (str != null) {
            String[] split = StringUtil.split(str, ' ');
            int length = split.length;
            if (length < 2) {
                throw new HAProxyProtocolException("invalid header: " + str + " (expected: 'PROXY' and proxied protocol values)");
            } else if ("PROXY".equals(split[0])) {
                try {
                    HAProxyProxiedProtocol valueOf = HAProxyProxiedProtocol.valueOf(split[1]);
                    if (valueOf != HAProxyProxiedProtocol.TCP4 && valueOf != HAProxyProxiedProtocol.TCP6 && valueOf != HAProxyProxiedProtocol.UNKNOWN) {
                        throw new HAProxyProtocolException("unsupported v1 proxied protocol: " + split[1]);
                    } else if (valueOf == HAProxyProxiedProtocol.UNKNOWN) {
                        return a;
                    } else {
                        if (length == 6) {
                            return new HAProxyMessage(HAProxyProtocolVersion.V1, HAProxyCommand.PROXY, valueOf, split[2], split[3], split[4], split[5]);
                        }
                        throw new HAProxyProtocolException("invalid TCP4/6 header: " + str + " (expected: 6 parts)");
                    }
                } catch (IllegalArgumentException e) {
                    throw new HAProxyProtocolException(e);
                }
            } else {
                throw new HAProxyProtocolException("unknown identifier: " + split[0]);
            }
        } else {
            throw new HAProxyProtocolException("header");
        }
    }

    private static String a(ByteBuf byteBuf, int i) {
        StringBuilder sb = new StringBuilder();
        if (i == 4) {
            sb.append(byteBuf.readByte() & 255);
            sb.append('.');
            sb.append(byteBuf.readByte() & 255);
            sb.append('.');
            sb.append(byteBuf.readByte() & 255);
            sb.append('.');
            sb.append(byteBuf.readByte() & 255);
        } else {
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(byteBuf.readUnsignedShort()));
        }
        return sb.toString();
    }

    private static int b(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt > 0 && parseInt <= 65535) {
                return parseInt;
            }
            throw new HAProxyProtocolException("invalid port: " + str + " (expected: 1 ~ 65535)");
        } catch (NumberFormatException e) {
            throw new HAProxyProtocolException("invalid port: " + str, e);
        }
    }

    private static void a(String str, HAProxyProxiedProtocol.AddressFamily addressFamily) {
        if (addressFamily != null) {
            switch (addressFamily) {
                case AF_UNSPEC:
                    if (str != null) {
                        throw new HAProxyProtocolException("unable to validate an AF_UNSPEC address: " + str);
                    }
                    return;
                case AF_UNIX:
                    return;
                default:
                    if (str != null) {
                        switch (addressFamily) {
                            case AF_IPv4:
                                if (!NetUtil.isValidIpV4Address(str)) {
                                    throw new HAProxyProtocolException("invalid IPv4 address: " + str);
                                }
                                return;
                            case AF_IPv6:
                                if (!NetUtil.isValidIpV6Address(str)) {
                                    throw new HAProxyProtocolException("invalid IPv6 address: " + str);
                                }
                                return;
                            default:
                                throw new Error();
                        }
                    } else {
                        throw new NullPointerException("address");
                    }
            }
        } else {
            throw new NullPointerException("addrFamily");
        }
    }

    private static void a(int i) {
        if (i < 0 || i > 65535) {
            throw new HAProxyProtocolException("invalid port: " + i + " (expected: 1 ~ 65535)");
        }
    }

    public HAProxyProtocolVersion protocolVersion() {
        return this.d;
    }

    public HAProxyCommand command() {
        return this.e;
    }

    public HAProxyProxiedProtocol proxiedProtocol() {
        return this.f;
    }

    public String sourceAddress() {
        return this.g;
    }

    public String destinationAddress() {
        return this.h;
    }

    public int sourcePort() {
        return this.i;
    }

    public int destinationPort() {
        return this.j;
    }
}
