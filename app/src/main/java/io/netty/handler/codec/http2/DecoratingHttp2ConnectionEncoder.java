package io.netty.handler.codec.http2;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DecoratingHttp2ConnectionEncoder extends DecoratingHttp2FrameWriter implements Http2ConnectionEncoder {
    private final Http2ConnectionEncoder a;

    public DecoratingHttp2ConnectionEncoder(Http2ConnectionEncoder http2ConnectionEncoder) {
        super(http2ConnectionEncoder);
        this.a = (Http2ConnectionEncoder) ObjectUtil.checkNotNull(http2ConnectionEncoder, "delegate");
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public void lifecycleManager(Http2LifecycleManager http2LifecycleManager) {
        this.a.lifecycleManager(http2LifecycleManager);
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public Http2Connection connection() {
        return this.a.connection();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public Http2RemoteFlowController flowController() {
        return this.a.flowController();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public Http2FrameWriter frameWriter() {
        return this.a.frameWriter();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public Http2Settings pollSentSettings() {
        return this.a.pollSentSettings();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public void remoteSettings(Http2Settings http2Settings) throws Http2Exception {
        this.a.remoteSettings(http2Settings);
    }
}
