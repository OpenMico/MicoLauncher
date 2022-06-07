package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;

/* loaded from: classes4.dex */
public class ThreadLocalMarshallerProvider implements MarshallerProvider {
    private final FastThreadLocal<Marshaller> a = new FastThreadLocal<>();
    private final MarshallerFactory b;
    private final MarshallingConfiguration c;

    public ThreadLocalMarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.b = marshallerFactory;
        this.c = marshallingConfiguration;
    }

    @Override // io.netty.handler.codec.marshalling.MarshallerProvider
    public Marshaller getMarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        Marshaller marshaller = this.a.get();
        if (marshaller != null) {
            return marshaller;
        }
        Marshaller createMarshaller = this.b.createMarshaller(this.c);
        this.a.set(createMarshaller);
        return createMarshaller;
    }
}
