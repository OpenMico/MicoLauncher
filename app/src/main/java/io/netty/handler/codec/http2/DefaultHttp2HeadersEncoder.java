package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.handler.codec.http2.internal.hpack.Encoder;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultHttp2HeadersEncoder implements Http2HeadersEncoder, Http2HeadersEncoder.Configuration {
    private final Encoder a;
    private final ByteArrayOutputStream b;
    private final Http2HeadersEncoder.SensitivityDetector c;
    private final Http2HeaderTable d;

    @Override // io.netty.handler.codec.http2.Http2HeadersEncoder
    public Http2HeadersEncoder.Configuration configuration() {
        return this;
    }

    public DefaultHttp2HeadersEncoder() {
        this(4096, NEVER_SENSITIVE);
    }

    public DefaultHttp2HeadersEncoder(int i, Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        this.b = new ByteArrayOutputStream();
        this.c = (Http2HeadersEncoder.SensitivityDetector) ObjectUtil.checkNotNull(sensitivityDetector, "sensitiveDetector");
        this.a = new Encoder(i);
        this.d = new a();
    }

    @Override // io.netty.handler.codec.http2.Http2HeadersEncoder
    public void encodeHeaders(Http2Headers http2Headers, ByteBuf byteBuf) throws Http2Exception {
        ByteBufOutputStream byteBufOutputStream;
        try {
            byteBufOutputStream = new ByteBufOutputStream(byteBuf);
            try {
                if (http2Headers.size() <= this.d.maxHeaderListSize()) {
                    if (this.b.size() > 0) {
                        byteBuf.writeBytes(this.b.toByteArray());
                        this.b.reset();
                    }
                    for (Map.Entry<CharSequence, CharSequence> entry : http2Headers) {
                        a(entry.getKey(), entry.getValue(), byteBufOutputStream);
                    }
                    try {
                        byteBufOutputStream.close();
                    } catch (IOException e) {
                        throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, e, e.getMessage(), new Object[0]);
                    }
                } else {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Number of headers (%d) exceeds maxHeaderListSize (%d)", Integer.valueOf(http2Headers.size()), Integer.valueOf(this.d.maxHeaderListSize()));
                }
            } catch (Http2Exception e2) {
                throw e2;
            } catch (Throwable th) {
                throw Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, th, "Failed encoding headers block: %s", th.getMessage());
            }
        } catch (Throwable th2) {
            try {
                byteBufOutputStream.close();
                throw th2;
            } catch (IOException e3) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, e3, e3.getMessage(), new Object[0]);
            }
        }
    }

    @Override // io.netty.handler.codec.http2.Http2HeadersEncoder.Configuration
    public Http2HeaderTable headerTable() {
        return this.d;
    }

    private byte[] a(CharSequence charSequence) {
        AsciiString of = AsciiString.of(charSequence);
        return of.isEntireArrayUsed() ? of.array() : of.toByteArray();
    }

    private void a(CharSequence charSequence, CharSequence charSequence2, OutputStream outputStream) throws IOException {
        this.a.encodeHeader(outputStream, a(charSequence), a(charSequence2), this.c.isSensitive(charSequence, charSequence2));
    }

    /* loaded from: classes4.dex */
    private final class a extends c implements Http2HeaderTable {
        private a() {
        }

        @Override // io.netty.handler.codec.http2.Http2HeaderTable
        public void maxHeaderTableSize(int i) throws Http2Exception {
            if (i >= 0) {
                try {
                    DefaultHttp2HeadersEncoder.this.a.setMaxHeaderTableSize(DefaultHttp2HeadersEncoder.this.b, i);
                } catch (IOException e) {
                    throw new Http2Exception(Http2Error.COMPRESSION_ERROR, e.getMessage(), e);
                } catch (Throwable th) {
                    throw new Http2Exception(Http2Error.PROTOCOL_ERROR, th.getMessage(), th);
                }
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header Table Size must be non-negative but was %d", Integer.valueOf(i));
            }
        }

        @Override // io.netty.handler.codec.http2.Http2HeaderTable
        public int maxHeaderTableSize() {
            return DefaultHttp2HeadersEncoder.this.a.getMaxHeaderTableSize();
        }
    }
}
