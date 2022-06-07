package io.netty.handler.codec.dns;

import io.netty.util.ReferenceCounted;

/* loaded from: classes4.dex */
public interface DnsMessage extends ReferenceCounted {
    DnsMessage addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord);

    DnsMessage addRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    DnsMessage clear();

    DnsMessage clear(DnsSection dnsSection);

    int count();

    int count(DnsSection dnsSection);

    int id();

    boolean isRecursionDesired();

    DnsOpCode opCode();

    <T extends DnsRecord> T recordAt(DnsSection dnsSection);

    <T extends DnsRecord> T recordAt(DnsSection dnsSection, int i);

    <T extends DnsRecord> T removeRecord(DnsSection dnsSection, int i);

    @Override // io.netty.util.ReferenceCounted
    DnsMessage retain();

    @Override // io.netty.util.ReferenceCounted
    DnsMessage retain(int i);

    DnsMessage setId(int i);

    DnsMessage setOpCode(DnsOpCode dnsOpCode);

    DnsMessage setRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    <T extends DnsRecord> T setRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord);

    DnsMessage setRecursionDesired(boolean z);

    DnsMessage setZ(int i);

    @Override // io.netty.util.ReferenceCounted
    DnsMessage touch();

    @Override // io.netty.util.ReferenceCounted
    DnsMessage touch(Object obj);

    int z();
}
