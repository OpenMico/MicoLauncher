package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2HeadersDecoder;
import io.netty.handler.codec.http2.internal.hpack.Decoder;

/* loaded from: classes4.dex */
public class DefaultHttp2HeadersDecoder implements Http2HeadersDecoder, Http2HeadersDecoder.Configuration {
    private final int a;
    private final Decoder b;
    private final Http2HeaderTable c;
    private final boolean d;
    private float e;

    @Override // io.netty.handler.codec.http2.Http2HeadersDecoder
    public Http2HeadersDecoder.Configuration configuration() {
        return this;
    }

    public DefaultHttp2HeadersDecoder() {
        this(true);
    }

    public DefaultHttp2HeadersDecoder(boolean z) {
        this(8192, 4096, z);
    }

    public DefaultHttp2HeadersDecoder(int i, int i2, boolean z) {
        this.e = 8.0f;
        if (i > 0) {
            this.b = new Decoder(i, i2);
            this.c = new a();
            this.a = i;
            this.d = z;
            return;
        }
        throw new IllegalArgumentException("maxHeaderSize must be positive: " + i);
    }

    @Override // io.netty.handler.codec.http2.Http2HeadersDecoder.Configuration
    public Http2HeaderTable headerTable() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http2.Http2HeadersDecoder.Configuration
    public int maxHeaderSize() {
        return this.a;
    }

    protected void maxHeaderSizeExceeded() throws Http2Exception {
        throw Http2Exception.connectionError(Http2Error.ENHANCE_YOUR_CALM, "Header size exceeded max allowed bytes (%d)", Integer.valueOf(this.a));
    }

    /* JADX WARN: Incorrect type for immutable var: ssa=io.netty.buffer.ByteBuf, code=int, for r7v0, types: [io.netty.buffer.ByteBuf] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v4 */
    @Override // io.netty.handler.codec.http2.Http2HeadersDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public io.netty.handler.codec.http2.Http2Headers decodeHeaders(int r7) throws io.netty.handler.codec.http2.Http2Exception {
        /*
            r6 = this;
            io.netty.buffer.ByteBufInputStream r0 = new io.netty.buffer.ByteBufInputStream
            r0.<init>(r7)
            r7 = 0
            io.netty.handler.codec.http2.DefaultHttp2Headers r1 = new io.netty.handler.codec.http2.DefaultHttp2Headers     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            boolean r2 = r6.d     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            float r3 = r6.e     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            int r3 = (int) r3     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            r1.<init>(r2, r3)     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder$1 r2 = new io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder$1     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            r2.<init>()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            io.netty.handler.codec.http2.internal.hpack.Decoder r3 = r6.b     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            r3.decode(r0, r2)     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            io.netty.handler.codec.http2.internal.hpack.Decoder r2 = r6.b     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            boolean r2 = r2.endHeaderBlock()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            if (r2 == 0) goto L_0x0025
            r6.maxHeaderSizeExceeded()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
        L_0x0025:
            int r2 = r1.size()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            io.netty.handler.codec.http2.Http2HeaderTable r3 = r6.c     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            int r3 = r3.maxHeaderListSize()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            if (r2 > r3) goto L_0x0055
            r2 = 1045220557(0x3e4ccccd, float:0.2)
            int r3 = r1.size()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            float r3 = (float) r3     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            float r3 = r3 * r2
            r2 = 1061997773(0x3f4ccccd, float:0.8)
            float r4 = r6.e     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            float r4 = r4 * r2
            float r3 = r3 + r4
            r6.e = r3     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            r0.close()     // Catch: IOException -> 0x0047
            return r1
        L_0x0047:
            r0 = move-exception
            io.netty.handler.codec.http2.Http2Error r1 = io.netty.handler.codec.http2.Http2Error.INTERNAL_ERROR
            java.lang.String r2 = r0.getMessage()
            java.lang.Object[] r7 = new java.lang.Object[r7]
            io.netty.handler.codec.http2.Http2Exception r7 = io.netty.handler.codec.http2.Http2Exception.connectionError(r1, r0, r2, r7)
            throw r7
        L_0x0055:
            io.netty.handler.codec.http2.Http2Error r2 = io.netty.handler.codec.http2.Http2Error.PROTOCOL_ERROR     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            java.lang.String r3 = "Number of headers (%d) exceeds maxHeaderListSize (%d)"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            int r1 = r1.size()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            r4[r7] = r1     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            r1 = 1
            io.netty.handler.codec.http2.Http2HeaderTable r5 = r6.c     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            int r5 = r5.maxHeaderListSize()     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            r4[r1] = r5     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            io.netty.handler.codec.http2.Http2Exception r1 = io.netty.handler.codec.http2.Http2Exception.connectionError(r2, r3, r4)     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
            throw r1     // Catch: IOException -> 0x008a, Http2Exception -> 0x0088, Throwable -> 0x007a, all -> 0x0078
        L_0x0078:
            r1 = move-exception
            goto L_0x0098
        L_0x007a:
            r1 = move-exception
            io.netty.handler.codec.http2.Http2Error r2 = io.netty.handler.codec.http2.Http2Error.COMPRESSION_ERROR     // Catch: all -> 0x0078
            java.lang.String r3 = r1.getMessage()     // Catch: all -> 0x0078
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch: all -> 0x0078
            io.netty.handler.codec.http2.Http2Exception r1 = io.netty.handler.codec.http2.Http2Exception.connectionError(r2, r1, r3, r4)     // Catch: all -> 0x0078
            throw r1     // Catch: all -> 0x0078
        L_0x0088:
            r1 = move-exception
            throw r1     // Catch: all -> 0x0078
        L_0x008a:
            r1 = move-exception
            io.netty.handler.codec.http2.Http2Error r2 = io.netty.handler.codec.http2.Http2Error.COMPRESSION_ERROR     // Catch: all -> 0x0078
            java.lang.String r3 = r1.getMessage()     // Catch: all -> 0x0078
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch: all -> 0x0078
            io.netty.handler.codec.http2.Http2Exception r1 = io.netty.handler.codec.http2.Http2Exception.connectionError(r2, r1, r3, r4)     // Catch: all -> 0x0078
            throw r1     // Catch: all -> 0x0078
        L_0x0098:
            r0.close()     // Catch: IOException -> 0x009c
            throw r1
        L_0x009c:
            r0 = move-exception
            io.netty.handler.codec.http2.Http2Error r1 = io.netty.handler.codec.http2.Http2Error.INTERNAL_ERROR
            java.lang.String r2 = r0.getMessage()
            java.lang.Object[] r7 = new java.lang.Object[r7]
            io.netty.handler.codec.http2.Http2Exception r7 = io.netty.handler.codec.http2.Http2Exception.connectionError(r1, r0, r2, r7)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder.decodeHeaders(io.netty.buffer.ByteBuf):io.netty.handler.codec.http2.Http2Headers");
    }

    /* loaded from: classes4.dex */
    private final class a extends c implements Http2HeaderTable {
        private a() {
        }

        @Override // io.netty.handler.codec.http2.Http2HeaderTable
        public void maxHeaderTableSize(int i) throws Http2Exception {
            if (i >= 0) {
                try {
                    DefaultHttp2HeadersDecoder.this.b.setMaxHeaderTableSize(i);
                } catch (Throwable th) {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, th.getMessage(), th);
                }
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header Table Size must be non-negative but was %d", Integer.valueOf(i));
            }
        }

        @Override // io.netty.handler.codec.http2.Http2HeaderTable
        public int maxHeaderTableSize() {
            return DefaultHttp2HeadersDecoder.this.b.getMaxHeaderTableSize();
        }
    }
}
