package io.netty.channel.unix;

import io.netty.channel.Channel;

/* loaded from: classes4.dex */
public interface UnixChannel extends Channel {
    FileDescriptor fd();
}
