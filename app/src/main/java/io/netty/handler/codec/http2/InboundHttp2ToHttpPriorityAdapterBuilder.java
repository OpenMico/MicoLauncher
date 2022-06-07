package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public final class InboundHttp2ToHttpPriorityAdapterBuilder extends AbstractInboundHttp2ToHttpAdapterBuilder<InboundHttp2ToHttpPriorityAdapter, InboundHttp2ToHttpPriorityAdapterBuilder> {
    public InboundHttp2ToHttpPriorityAdapterBuilder(Http2Connection http2Connection) {
        super(http2Connection);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpPriorityAdapterBuilder maxContentLength(int i) {
        return (InboundHttp2ToHttpPriorityAdapterBuilder) super.maxContentLength(i);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpPriorityAdapterBuilder validateHttpHeaders(boolean z) {
        return (InboundHttp2ToHttpPriorityAdapterBuilder) super.validateHttpHeaders(z);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpPriorityAdapterBuilder propagateSettings(boolean z) {
        return (InboundHttp2ToHttpPriorityAdapterBuilder) super.propagateSettings(z);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpPriorityAdapter build() {
        return (InboundHttp2ToHttpPriorityAdapter) super.build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpPriorityAdapter build(Http2Connection http2Connection, int i, boolean z, boolean z2) throws Exception {
        return new InboundHttp2ToHttpPriorityAdapter(http2Connection, i, z, z2);
    }
}
