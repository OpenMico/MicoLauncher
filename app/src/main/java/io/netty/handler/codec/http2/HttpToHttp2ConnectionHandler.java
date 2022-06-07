package io.netty.handler.codec.http2;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http2.HttpConversionUtil;

/* loaded from: classes4.dex */
public class HttpToHttp2ConnectionHandler extends Http2ConnectionHandler {
    private final boolean a;
    private int c;

    /* JADX INFO: Access modifiers changed from: protected */
    public HttpToHttp2ConnectionHandler(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings, boolean z) {
        super(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings);
        this.a = z;
    }

    private int a(HttpHeaders httpHeaders) throws Exception {
        return httpHeaders.getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), connection().local().incrementAndGetNextStreamId());
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b1, code lost:
        if (r14 != false) goto L_0x00bc;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ba, code lost:
        if (r14 == false) goto L_0x00bf;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bc, code lost:
        io.netty.util.ReferenceCountUtil.release(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bf, code lost:
        r0.b();
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00c2, code lost:
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009b A[Catch: Throwable -> 0x00ae, all -> 0x00ab, TRY_LEAVE, TryCatch #4 {Throwable -> 0x00ae, all -> 0x00ab, blocks: (B:32:0x0089, B:34:0x009b), top: B:55:0x0089 }] */
    @Override // io.netty.handler.codec.http2.Http2ConnectionHandler, io.netty.channel.ChannelOutboundHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(io.netty.channel.ChannelHandlerContext r12, java.lang.Object r13, io.netty.channel.ChannelPromise r14) {
        /*
            Method dump skipped, instructions count: 204
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.HttpToHttp2ConnectionHandler.write(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.channel.ChannelPromise):void");
    }
}
