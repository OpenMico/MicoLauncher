package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/* loaded from: classes4.dex */
public class ThreadLocalUnmarshallerProvider implements UnmarshallerProvider {
    private final FastThreadLocal<Unmarshaller> a = new FastThreadLocal<>();
    private final MarshallerFactory b;
    private final MarshallingConfiguration c;

    public ThreadLocalUnmarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.b = marshallerFactory;
        this.c = marshallingConfiguration;
    }

    @Override // io.netty.handler.codec.marshalling.UnmarshallerProvider
    public Unmarshaller getUnmarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        Unmarshaller unmarshaller = this.a.get();
        if (unmarshaller != null) {
            return unmarshaller;
        }
        Unmarshaller createUnmarshaller = this.b.createUnmarshaller(this.c);
        this.a.set(createUnmarshaller);
        return createUnmarshaller;
    }
}
