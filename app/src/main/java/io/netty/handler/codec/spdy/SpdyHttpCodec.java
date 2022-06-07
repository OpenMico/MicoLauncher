package io.netty.handler.codec.spdy;

import io.netty.channel.CombinedChannelDuplexHandler;

/* loaded from: classes4.dex */
public final class SpdyHttpCodec extends CombinedChannelDuplexHandler<SpdyHttpDecoder, SpdyHttpEncoder> {
    public SpdyHttpCodec(SpdyVersion spdyVersion, int i) {
        super(new SpdyHttpDecoder(spdyVersion, i), new SpdyHttpEncoder(spdyVersion));
    }

    public SpdyHttpCodec(SpdyVersion spdyVersion, int i, boolean z) {
        super(new SpdyHttpDecoder(spdyVersion, i, z), new SpdyHttpEncoder(spdyVersion));
    }
}
