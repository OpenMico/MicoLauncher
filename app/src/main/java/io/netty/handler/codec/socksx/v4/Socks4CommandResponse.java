package io.netty.handler.codec.socksx.v4;

/* loaded from: classes4.dex */
public interface Socks4CommandResponse extends Socks4Message {
    String dstAddr();

    int dstPort();

    Socks4CommandStatus status();
}
