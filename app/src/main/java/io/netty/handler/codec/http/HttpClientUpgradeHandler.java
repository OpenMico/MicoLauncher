package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/* loaded from: classes4.dex */
public class HttpClientUpgradeHandler extends HttpObjectAggregator implements ChannelOutboundHandler {
    static final /* synthetic */ boolean a = !HttpClientUpgradeHandler.class.desiredAssertionStatus();
    private final SourceCodec c;
    private final UpgradeCodec d;
    private boolean e;

    /* loaded from: classes4.dex */
    public interface SourceCodec {
        void prepareUpgradeFrom(ChannelHandlerContext channelHandlerContext);

        void upgradeFrom(ChannelHandlerContext channelHandlerContext);
    }

    /* loaded from: classes4.dex */
    public interface UpgradeCodec {
        CharSequence protocol();

        Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest);

        void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception;
    }

    /* loaded from: classes4.dex */
    public enum UpgradeEvent {
        UPGRADE_ISSUED,
        UPGRADE_SUCCESSFUL,
        UPGRADE_REJECTED
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageAggregator, io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        decode(channelHandlerContext, (HttpObject) obj, (List<Object>) list);
    }

    public HttpClientUpgradeHandler(SourceCodec sourceCodec, UpgradeCodec upgradeCodec, int i) {
        super(i);
        if (sourceCodec == null) {
            throw new NullPointerException("sourceCodec");
        } else if (upgradeCodec != null) {
            this.c = sourceCodec;
            this.d = upgradeCodec;
        } else {
            throw new NullPointerException("upgradeCodec");
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (!(obj instanceof HttpRequest)) {
            channelHandlerContext.write(obj, channelPromise);
        } else if (this.e) {
            channelPromise.setFailure((Throwable) new IllegalStateException("Attempting to write HTTP request with upgrade in progress"));
        } else {
            this.e = true;
            a(channelHandlerContext, (HttpRequest) obj);
            channelHandlerContext.write(obj, channelPromise);
            channelHandlerContext.fireUserEventTriggered((Object) UpgradeEvent.UPGRADE_ISSUED);
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        Throwable th;
        FullHttpResponse fullHttpResponse;
        FullHttpResponse fullHttpResponse2 = null;
        try {
            if (this.e) {
                if (httpObject instanceof FullHttpResponse) {
                    FullHttpResponse fullHttpResponse3 = (FullHttpResponse) httpObject;
                    try {
                        fullHttpResponse3.retain();
                        list.add(fullHttpResponse3);
                        fullHttpResponse = fullHttpResponse3;
                    } catch (Throwable th2) {
                        th = th2;
                        fullHttpResponse2 = fullHttpResponse3;
                        ReferenceCountUtil.release(fullHttpResponse2);
                        channelHandlerContext.fireExceptionCaught(th);
                        a(channelHandlerContext);
                        return;
                    }
                } else {
                    super.decode(channelHandlerContext, (ChannelHandlerContext) httpObject, list);
                    if (!list.isEmpty()) {
                        if (!a && list.size() != 1) {
                            throw new AssertionError();
                        }
                        fullHttpResponse = (FullHttpResponse) list.get(0);
                    } else {
                        return;
                    }
                }
                if (!HttpResponseStatus.SWITCHING_PROTOCOLS.equals(fullHttpResponse.status())) {
                    channelHandlerContext.fireUserEventTriggered((Object) UpgradeEvent.UPGRADE_REJECTED);
                    a(channelHandlerContext);
                    return;
                }
                String str = fullHttpResponse.headers().get(HttpHeaderNames.UPGRADE);
                if (str != null && !AsciiString.contentEqualsIgnoreCase(this.d.protocol(), str)) {
                    throw new IllegalStateException("Switching Protocols response with unexpected UPGRADE protocol: " + ((Object) str));
                }
                this.c.prepareUpgradeFrom(channelHandlerContext);
                this.d.upgradeTo(channelHandlerContext, fullHttpResponse);
                channelHandlerContext.fireUserEventTriggered((Object) UpgradeEvent.UPGRADE_SUCCESSFUL);
                this.c.upgradeFrom(channelHandlerContext);
                fullHttpResponse.release();
                list.clear();
                a(channelHandlerContext);
                return;
            }
            throw new IllegalStateException("Read HTTP response without requesting protocol switch");
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static void a(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.pipeline().remove(channelHandlerContext.name());
    }

    private void a(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        httpRequest.headers().set(HttpHeaderNames.UPGRADE, this.d.protocol());
        LinkedHashSet<CharSequence> linkedHashSet = new LinkedHashSet(2);
        linkedHashSet.addAll(this.d.setUpgradeHeaders(channelHandlerContext, httpRequest));
        StringBuilder sb = new StringBuilder();
        for (CharSequence charSequence : linkedHashSet) {
            sb.append(charSequence);
            sb.append(StringUtil.COMMA);
        }
        sb.append((CharSequence) HttpHeaderValues.UPGRADE);
        httpRequest.headers().set(HttpHeaderNames.CONNECTION, sb.toString());
    }
}
