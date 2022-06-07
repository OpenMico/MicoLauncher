package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2HeadersEncoder;

/* loaded from: classes4.dex */
public final class HttpToHttp2ConnectionHandlerBuilder extends AbstractHttp2ConnectionHandlerBuilder<HttpToHttp2ConnectionHandler, HttpToHttp2ConnectionHandlerBuilder> {
    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder validateHeaders(boolean z) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.validateHeaders(z);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder initialSettings(Http2Settings http2Settings) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.initialSettings(http2Settings);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder frameListener(Http2FrameListener http2FrameListener) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.frameListener(http2FrameListener);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder gracefulShutdownTimeoutMillis(long j) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.gracefulShutdownTimeoutMillis(j);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder server(boolean z) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.server(z);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder connection(Http2Connection http2Connection) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.connection(http2Connection);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder codec(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.codec(http2ConnectionDecoder, http2ConnectionEncoder);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder frameLogger(Http2FrameLogger http2FrameLogger) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.frameLogger(http2FrameLogger);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder encoderEnforceMaxConcurrentStreams(boolean z) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.encoderEnforceMaxConcurrentStreams(z);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandlerBuilder headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        return (HttpToHttp2ConnectionHandlerBuilder) super.headerSensitivityDetector(sensitivityDetector);
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandler build() {
        return (HttpToHttp2ConnectionHandler) super.build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder
    public HttpToHttp2ConnectionHandler build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
        return new HttpToHttp2ConnectionHandler(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings, isValidateHeaders());
    }
}
