package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.http.HttpClientUpgradeHandler;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public final class HttpClientCodec extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder> implements HttpClientUpgradeHandler.SourceCodec {
    private final Queue<HttpMethod> b;
    private boolean c;
    private final AtomicLong d;
    private final boolean e;

    public HttpClientCodec() {
        this(4096, 8192, 8192, false);
    }

    public HttpClientCodec(int i, int i2, int i3) {
        this(i, i2, i3, false);
    }

    public HttpClientCodec(int i, int i2, int i3, boolean z) {
        this(i, i2, i3, z, true);
    }

    public HttpClientCodec(int i, int i2, int i3, boolean z, boolean z2) {
        this.b = new ArrayDeque();
        this.d = new AtomicLong();
        init(new a(i, i2, i3, z2), new b());
        this.e = z;
    }

    public HttpClientCodec(int i, int i2, int i3, boolean z, boolean z2, int i4) {
        this.b = new ArrayDeque();
        this.d = new AtomicLong();
        init(new a(i, i2, i3, z2, i4), new b());
        this.e = z;
    }

    @Override // io.netty.handler.codec.http.HttpClientUpgradeHandler.SourceCodec
    public void prepareUpgradeFrom(ChannelHandlerContext channelHandlerContext) {
        ((b) outboundHandler()).a = true;
    }

    @Override // io.netty.handler.codec.http.HttpClientUpgradeHandler.SourceCodec
    public void upgradeFrom(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.pipeline().remove(this);
    }

    public void setSingleDecode(boolean z) {
        inboundHandler().setSingleDecode(z);
    }

    public boolean isSingleDecode() {
        return inboundHandler().isSingleDecode();
    }

    /* loaded from: classes4.dex */
    private final class b extends HttpRequestEncoder {
        boolean a;

        private b() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.handler.codec.http.HttpObjectEncoder, io.netty.handler.codec.MessageToMessageEncoder
        public void encode(ChannelHandlerContext channelHandlerContext, Object obj, List<Object> list) throws Exception {
            if (this.a) {
                list.add(ReferenceCountUtil.retain(obj));
                return;
            }
            if ((obj instanceof HttpRequest) && !HttpClientCodec.this.c) {
                HttpClientCodec.this.b.offer(((HttpRequest) obj).method());
            }
            super.encode(channelHandlerContext, obj, list);
            if (HttpClientCodec.this.e && (obj instanceof LastHttpContent)) {
                HttpClientCodec.this.d.incrementAndGet();
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class a extends HttpResponseDecoder {
        a(int i, int i2, int i3, boolean z) {
            super(i, i2, i3, z);
        }

        a(int i, int i2, int i3, boolean z, int i4) {
            super(i, i2, i3, z, i4);
        }

        @Override // io.netty.handler.codec.http.HttpObjectDecoder, io.netty.handler.codec.ByteToMessageDecoder
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            if (HttpClientCodec.this.c) {
                int actualReadableBytes = actualReadableBytes();
                if (actualReadableBytes != 0) {
                    list.add(byteBuf.readBytes(actualReadableBytes));
                    return;
                }
                return;
            }
            super.decode(channelHandlerContext, byteBuf, list);
            if (HttpClientCodec.this.e) {
                int size = list.size();
                for (int size2 = list.size(); size2 < size; size2++) {
                    a(list.get(size2));
                }
            }
        }

        private void a(Object obj) {
            if (obj != null && (obj instanceof LastHttpContent)) {
                HttpClientCodec.this.d.decrementAndGet();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.handler.codec.http.HttpObjectDecoder
        public boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
            int code = ((HttpResponse) httpMessage).status().code();
            if (code == 100) {
                return true;
            }
            HttpMethod httpMethod = (HttpMethod) HttpClientCodec.this.b.poll();
            char charAt = httpMethod.name().charAt(0);
            if (charAt != 'C') {
                if (charAt == 'H' && HttpMethod.HEAD.equals(httpMethod)) {
                    return true;
                }
            } else if (code == 200 && HttpMethod.CONNECT.equals(httpMethod)) {
                HttpClientCodec.this.c = true;
                HttpClientCodec.this.b.clear();
                return true;
            }
            return super.isContentAlwaysEmpty(httpMessage);
        }

        @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            super.channelInactive(channelHandlerContext);
            if (HttpClientCodec.this.e) {
                long j = HttpClientCodec.this.d.get();
                if (j > 0) {
                    channelHandlerContext.fireExceptionCaught((Throwable) new PrematureChannelClosureException("channel gone inactive with " + j + " missing response(s)"));
                }
            }
        }
    }
}
