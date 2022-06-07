package io.netty.handler.ipfilter;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/* loaded from: classes4.dex */
public final class IpSubnetFilterRule implements IpFilterRule {
    private final IpFilterRule a;

    public IpSubnetFilterRule(String str, int i, IpFilterRuleType ipFilterRuleType) {
        try {
            this.a = a(InetAddress.getByName(str), i, ipFilterRuleType);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("ipAddress", e);
        }
    }

    public IpSubnetFilterRule(InetAddress inetAddress, int i, IpFilterRuleType ipFilterRuleType) {
        this.a = a(inetAddress, i, ipFilterRuleType);
    }

    private static IpFilterRule a(InetAddress inetAddress, int i, IpFilterRuleType ipFilterRuleType) {
        if (inetAddress == null) {
            throw new NullPointerException("ipAddress");
        } else if (ipFilterRuleType == null) {
            throw new NullPointerException("ruleType");
        } else if (inetAddress instanceof Inet4Address) {
            return new a((Inet4Address) inetAddress, i, ipFilterRuleType);
        } else {
            if (inetAddress instanceof Inet6Address) {
                return new b((Inet6Address) inetAddress, i, ipFilterRuleType);
            }
            throw new IllegalArgumentException("Only IPv4 and IPv6 addresses are supported");
        }
    }

    @Override // io.netty.handler.ipfilter.IpFilterRule
    public boolean matches(InetSocketAddress inetSocketAddress) {
        return this.a.matches(inetSocketAddress);
    }

    @Override // io.netty.handler.ipfilter.IpFilterRule
    public IpFilterRuleType ruleType() {
        return this.a.ruleType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a implements IpFilterRule {
        static final /* synthetic */ boolean a = !IpSubnetFilterRule.class.desiredAssertionStatus();
        private final int b;
        private final int c;
        private final IpFilterRuleType d;

        private static int a(int i) {
            return (int) ((-1) & ((-1) << (32 - i)));
        }

        private a(Inet4Address inet4Address, int i, IpFilterRuleType ipFilterRuleType) {
            if (i < 0 || i > 32) {
                throw new IllegalArgumentException(String.format("IPv4 requires the subnet prefix to be in range of [0,32]. The prefix was: %d", Integer.valueOf(i)));
            }
            this.c = a(i);
            this.b = a(inet4Address) & this.c;
            this.d = ipFilterRuleType;
        }

        @Override // io.netty.handler.ipfilter.IpFilterRule
        public boolean matches(InetSocketAddress inetSocketAddress) {
            return (a((Inet4Address) inetSocketAddress.getAddress()) & this.c) == this.b;
        }

        @Override // io.netty.handler.ipfilter.IpFilterRule
        public IpFilterRuleType ruleType() {
            return this.d;
        }

        private static int a(Inet4Address inet4Address) {
            byte[] address = inet4Address.getAddress();
            if (a || address.length == 4) {
                return (address[3] & 255) | ((address[0] & 255) << 24) | ((address[1] & 255) << 16) | ((address[2] & 255) << 8);
            }
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b implements IpFilterRule {
        static final /* synthetic */ boolean a = !IpSubnetFilterRule.class.desiredAssertionStatus();
        private static final BigInteger b = BigInteger.valueOf(-1);
        private final BigInteger c;
        private final BigInteger d;
        private final IpFilterRuleType e;

        private b(Inet6Address inet6Address, int i, IpFilterRuleType ipFilterRuleType) {
            if (i < 0 || i > 128) {
                throw new IllegalArgumentException(String.format("IPv6 requires the subnet prefix to be in range of [0,128]. The prefix was: %d", Integer.valueOf(i)));
            }
            this.d = a(i);
            this.c = a(inet6Address).and(this.d);
            this.e = ipFilterRuleType;
        }

        @Override // io.netty.handler.ipfilter.IpFilterRule
        public boolean matches(InetSocketAddress inetSocketAddress) {
            return a((Inet6Address) inetSocketAddress.getAddress()).and(this.d).equals(this.c);
        }

        @Override // io.netty.handler.ipfilter.IpFilterRule
        public IpFilterRuleType ruleType() {
            return this.e;
        }

        private static BigInteger a(Inet6Address inet6Address) {
            byte[] address = inet6Address.getAddress();
            if (a || address.length == 16) {
                return new BigInteger(address);
            }
            throw new AssertionError();
        }

        private static BigInteger a(int i) {
            return b.shiftLeft(128 - i);
        }
    }
}
