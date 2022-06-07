package io.netty.handler.codec.dns;

/* loaded from: classes4.dex */
public interface DnsRecord {
    public static final int CLASS_ANY = 255;
    public static final int CLASS_CHAOS = 3;
    public static final int CLASS_CSNET = 2;
    public static final int CLASS_HESIOD = 4;
    public static final int CLASS_IN = 1;
    public static final int CLASS_NONE = 254;

    int dnsClass();

    String name();

    long timeToLive();

    DnsRecordType type();
}
