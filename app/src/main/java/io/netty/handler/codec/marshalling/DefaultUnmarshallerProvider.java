package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/* loaded from: classes4.dex */
public class DefaultUnmarshallerProvider implements UnmarshallerProvider {
    private final MarshallerFactory a;
    private final MarshallingConfiguration b;

    public DefaultUnmarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.a = marshallerFactory;
        this.b = marshallingConfiguration;
    }

    @Override // io.netty.handler.codec.marshalling.UnmarshallerProvider
    public Unmarshaller getUnmarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.a.createUnmarshaller(this.b);
    }
}
