package io.netty.handler.codec.haproxy;

import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;

/* loaded from: classes4.dex */
public enum HAProxyProxiedProtocol {
    UNKNOWN((byte) 0, AddressFamily.AF_UNSPEC, TransportProtocol.UNSPEC),
    TCP4((byte) 17, AddressFamily.AF_IPv4, TransportProtocol.STREAM),
    TCP6(BinaryMemcacheOpcodes.SASL_AUTH, AddressFamily.AF_IPv6, TransportProtocol.STREAM),
    UDP4((byte) 18, AddressFamily.AF_IPv4, TransportProtocol.DGRAM),
    UDP6((byte) 34, AddressFamily.AF_IPv6, TransportProtocol.DGRAM),
    UNIX_STREAM((byte) 49, AddressFamily.AF_UNIX, TransportProtocol.STREAM),
    UNIX_DGRAM((byte) 50, AddressFamily.AF_UNIX, TransportProtocol.DGRAM);
    
    private final AddressFamily addressFamily;
    private final byte byteValue;
    private final TransportProtocol transportProtocol;

    HAProxyProxiedProtocol(byte b, AddressFamily addressFamily, TransportProtocol transportProtocol) {
        this.byteValue = b;
        this.addressFamily = addressFamily;
        this.transportProtocol = transportProtocol;
    }

    public static HAProxyProxiedProtocol valueOf(byte b) {
        switch (b) {
            case 0:
                return UNKNOWN;
            case 17:
                return TCP4;
            case 18:
                return UDP4;
            case 33:
                return TCP6;
            case 34:
                return UDP6;
            case 49:
                return UNIX_STREAM;
            case 50:
                return UNIX_DGRAM;
            default:
                throw new IllegalArgumentException("unknown transport protocol + address family: " + (b & 255));
        }
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public AddressFamily addressFamily() {
        return this.addressFamily;
    }

    public TransportProtocol transportProtocol() {
        return this.transportProtocol;
    }

    /* loaded from: classes4.dex */
    public enum AddressFamily {
        AF_UNSPEC((byte) 0),
        AF_IPv4((byte) 16),
        AF_IPv6((byte) 32),
        AF_UNIX((byte) 48);
        
        private final byte byteValue;

        AddressFamily(byte b) {
            this.byteValue = b;
        }

        public static AddressFamily valueOf(byte b) {
            int i = b & (-16);
            byte b2 = (byte) i;
            if (b2 == 0) {
                return AF_UNSPEC;
            }
            if (b2 == 16) {
                return AF_IPv4;
            }
            if (b2 == 32) {
                return AF_IPv6;
            }
            if (b2 == 48) {
                return AF_UNIX;
            }
            throw new IllegalArgumentException("unknown address family: " + i);
        }

        public byte byteValue() {
            return this.byteValue;
        }
    }

    /* loaded from: classes4.dex */
    public enum TransportProtocol {
        UNSPEC((byte) 0),
        STREAM((byte) 1),
        DGRAM((byte) 2);
        
        private final byte transportByte;

        TransportProtocol(byte b) {
            this.transportByte = b;
        }

        public static TransportProtocol valueOf(byte b) {
            int i = b & 15;
            switch ((byte) i) {
                case 0:
                    return UNSPEC;
                case 1:
                    return STREAM;
                case 2:
                    return DGRAM;
                default:
                    throw new IllegalArgumentException("unknown transport protocol: " + i);
            }
        }

        public byte byteValue() {
            return this.transportByte;
        }
    }
}
