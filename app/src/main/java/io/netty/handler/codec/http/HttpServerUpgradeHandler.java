package io.netty.handler.codec.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes4.dex */
public class HttpServerUpgradeHandler extends HttpObjectAggregator {
    static final /* synthetic */ boolean a = !HttpServerUpgradeHandler.class.desiredAssertionStatus();
    private final SourceCodec c;
    private final UpgradeCodecFactory d;
    private boolean e;

    /* loaded from: classes4.dex */
    public interface SourceCodec {
        void upgradeFrom(ChannelHandlerContext channelHandlerContext);
    }

    /* loaded from: classes4.dex */
    public interface UpgradeCodec {
        boolean prepareUpgradeResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders);

        Collection<CharSequence> requiredUpgradeHeaders();

        void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest);
    }

    /* loaded from: classes4.dex */
    public interface UpgradeCodecFactory {
        UpgradeCodec newUpgradeCodec(CharSequence charSequence);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageAggregator, io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        decode(channelHandlerContext, (HttpObject) obj, (List<Object>) list);
    }

    /* loaded from: classes4.dex */
    public static final class UpgradeEvent implements ReferenceCounted {
        private final CharSequence a;
        private final FullHttpRequest b;

        UpgradeEvent(CharSequence charSequence, FullHttpRequest fullHttpRequest) {
            this.a = charSequence;
            this.b = fullHttpRequest;
        }

        public CharSequence protocol() {
            return this.a;
        }

        public FullHttpRequest upgradeRequest() {
            return this.b;
        }

        @Override // io.netty.util.ReferenceCounted
        public int refCnt() {
            return this.b.refCnt();
        }

        @Override // io.netty.util.ReferenceCounted
        public UpgradeEvent retain() {
            this.b.retain();
            return this;
        }

        @Override // io.netty.util.ReferenceCounted
        public UpgradeEvent retain(int i) {
            this.b.retain(i);
            return this;
        }

        @Override // io.netty.util.ReferenceCounted
        public UpgradeEvent touch() {
            this.b.touch();
            return this;
        }

        @Override // io.netty.util.ReferenceCounted
        public UpgradeEvent touch(Object obj) {
            this.b.touch(obj);
            return this;
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release() {
            return this.b.release();
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release(int i) {
            return this.b.release(i);
        }

        public String toString() {
            return "UpgradeEvent [protocol=" + ((Object) this.a) + ", upgradeRequest=" + this.b + ']';
        }
    }

    public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory) {
        this(sourceCodec, upgradeCodecFactory, 0);
    }

    public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory, int i) {
        super(i);
        this.c = (SourceCodec) ObjectUtil.checkNotNull(sourceCodec, "sourceCodec");
        this.d = (UpgradeCodecFactory) ObjectUtil.checkNotNull(upgradeCodecFactory, "upgradeCodecFactory");
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        FullHttpRequest fullHttpRequest;
        this.e |= a(httpObject);
        if (!this.e) {
            ReferenceCountUtil.retain(httpObject);
            list.add(httpObject);
            return;
        }
        if (httpObject instanceof FullHttpRequest) {
            fullHttpRequest = (FullHttpRequest) httpObject;
            ReferenceCountUtil.retain(httpObject);
            list.add(httpObject);
        } else {
            super.decode(channelHandlerContext, (ChannelHandlerContext) httpObject, list);
            if (!list.isEmpty()) {
                if (a || list.size() == 1) {
                    this.e = false;
                    fullHttpRequest = (FullHttpRequest) list.get(0);
                } else {
                    throw new AssertionError();
                }
            } else {
                return;
            }
        }
        if (a(channelHandlerContext, fullHttpRequest)) {
            list.clear();
        }
    }

    private static boolean a(HttpObject httpObject) {
        return (httpObject instanceof HttpRequest) && ((HttpRequest) httpObject).headers().get(HttpHeaderNames.UPGRADE) != null;
    }

    private boolean a(final ChannelHandlerContext channelHandlerContext, final FullHttpRequest fullHttpRequest) {
        CharSequence charSequence;
        final UpgradeCodec upgradeCodec;
        String str;
        List<CharSequence> b = b(fullHttpRequest.headers().get(HttpHeaderNames.UPGRADE));
        int size = b.size();
        int i = 0;
        while (true) {
            charSequence = null;
            if (i >= size) {
                upgradeCodec = null;
                break;
            }
            charSequence = b.get(i);
            UpgradeCodec newUpgradeCodec = this.d.newUpgradeCodec(charSequence);
            if (newUpgradeCodec != null) {
                upgradeCodec = newUpgradeCodec;
                break;
            }
            i++;
        }
        if (upgradeCodec == null || (str = fullHttpRequest.headers().get(HttpHeaderNames.CONNECTION)) == null) {
            return false;
        }
        Collection<CharSequence> requiredUpgradeHeaders = upgradeCodec.requiredUpgradeHeaders();
        List<CharSequence> b2 = b(str);
        if (!AsciiString.containsContentEqualsIgnoreCase(b2, HttpHeaderNames.UPGRADE) || !AsciiString.containsAllContentEqualsIgnoreCase(b2, requiredUpgradeHeaders)) {
            return false;
        }
        for (CharSequence charSequence2 : requiredUpgradeHeaders) {
            if (!fullHttpRequest.headers().contains(charSequence2)) {
                return false;
            }
        }
        FullHttpResponse a2 = a(charSequence);
        if (!upgradeCodec.prepareUpgradeResponse(channelHandlerContext, fullHttpRequest, a2.headers())) {
            return false;
        }
        final UpgradeEvent upgradeEvent = new UpgradeEvent(charSequence, fullHttpRequest);
        channelHandlerContext.writeAndFlush(a2).addListener(new ChannelFutureListener() { // from class: io.netty.handler.codec.http.HttpServerUpgradeHandler.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                try {
                    if (channelFuture.isSuccess()) {
                        HttpServerUpgradeHandler.this.c.upgradeFrom(channelHandlerContext);
                        upgradeCodec.upgradeTo(channelHandlerContext, fullHttpRequest);
                        channelHandlerContext.fireUserEventTriggered((Object) upgradeEvent.retain());
                        channelHandlerContext.pipeline().remove(HttpServerUpgradeHandler.this);
                    } else {
                        channelFuture.channel().close();
                    }
                } finally {
                    upgradeEvent.release();
                }
            }
        });
        return true;
    }

    private static FullHttpResponse a(CharSequence charSequence) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, Unpooled.EMPTY_BUFFER, false);
        defaultFullHttpResponse.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
        defaultFullHttpResponse.headers().add(HttpHeaderNames.UPGRADE, charSequence);
        defaultFullHttpResponse.headers().add(HttpHeaderNames.CONTENT_LENGTH, HttpHeaderValues.ZERO);
        return defaultFullHttpResponse;
    }

    private static List<CharSequence> b(CharSequence charSequence) {
        StringBuilder sb = new StringBuilder(charSequence.length());
        ArrayList arrayList = new ArrayList(4);
        for (int i = 0; i < charSequence.length(); i++) {
            char charAt = charSequence.charAt(i);
            if (!Character.isWhitespace(charAt)) {
                if (charAt == ',') {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(charAt);
                }
            }
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString());
        }
        return arrayList;
    }
}
