package io.netty.channel;

import io.netty.util.ReferenceCounted;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public interface AddressedEnvelope<M, A extends SocketAddress> extends ReferenceCounted {
    M content();

    A recipient();

    @Override // io.netty.util.ReferenceCounted
    AddressedEnvelope<M, A> retain();

    @Override // io.netty.util.ReferenceCounted
    AddressedEnvelope<M, A> retain(int i);

    A sender();

    @Override // io.netty.util.ReferenceCounted
    AddressedEnvelope<M, A> touch();

    @Override // io.netty.util.ReferenceCounted
    AddressedEnvelope<M, A> touch(Object obj);
}
