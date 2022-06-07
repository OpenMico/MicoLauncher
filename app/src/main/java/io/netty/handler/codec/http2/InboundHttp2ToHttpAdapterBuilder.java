package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public final class InboundHttp2ToHttpAdapterBuilder extends AbstractInboundHttp2ToHttpAdapterBuilder<InboundHttp2ToHttpAdapter, InboundHttp2ToHttpAdapterBuilder> {
    public InboundHttp2ToHttpAdapterBuilder(Http2Connection http2Connection) {
        super(http2Connection);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpAdapterBuilder maxContentLength(int i) {
        return (InboundHttp2ToHttpAdapterBuilder) super.maxContentLength(i);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpAdapterBuilder validateHttpHeaders(boolean z) {
        return (InboundHttp2ToHttpAdapterBuilder) super.validateHttpHeaders(z);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpAdapterBuilder propagateSettings(boolean z) {
        return (InboundHttp2ToHttpAdapterBuilder) super.propagateSettings(z);
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    public InboundHttp2ToHttpAdapter build() {
        return super.build();
    }

    @Override // io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder
    protected InboundHttp2ToHttpAdapter build(Http2Connection http2Connection, int i, boolean z, boolean z2) throws Exception {
        return new InboundHttp2ToHttpAdapter(http2Connection, i, z, z2);
    }
}
