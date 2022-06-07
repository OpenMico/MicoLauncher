package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;

/* loaded from: classes4.dex */
public class DefaultMarshallerProvider implements MarshallerProvider {
    private final MarshallerFactory a;
    private final MarshallingConfiguration b;

    public DefaultMarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.a = marshallerFactory;
        this.b = marshallingConfiguration;
    }

    @Override // io.netty.handler.codec.marshalling.MarshallerProvider
    public Marshaller getMarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.a.createMarshaller(this.b);
    }
}
