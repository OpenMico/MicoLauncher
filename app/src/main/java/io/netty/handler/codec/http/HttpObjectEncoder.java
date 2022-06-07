package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class HttpObjectEncoder<H extends HttpMessage> extends MessageToMessageEncoder<Object> {
    private static final byte[] a = {13, 10};
    private static final byte[] b = {48, 13, 10};
    private static final byte[] c = {48, 13, 10, 13, 10};
    private static final ByteBuf d = Unpooled.unreleasableBuffer(Unpooled.directBuffer(a.length).writeBytes(a));
    private static final ByteBuf e = Unpooled.unreleasableBuffer(Unpooled.directBuffer(c.length).writeBytes(c));
    private int f = 0;

    protected abstract void encodeInitialLine(ByteBuf byteBuf, H h) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public void encode(ChannelHandlerContext channelHandlerContext, Object obj, List<Object> list) throws Exception {
        ByteBuf byteBuf;
        if (!(obj instanceof HttpMessage)) {
            byteBuf = null;
        } else if (this.f == 0) {
            HttpMessage httpMessage = (HttpMessage) obj;
            byteBuf = channelHandlerContext.alloc().buffer();
            encodeInitialLine(byteBuf, httpMessage);
            encodeHeaders(httpMessage.headers(), byteBuf);
            byteBuf.writeBytes(a);
            this.f = HttpUtil.isTransferEncodingChunked(httpMessage) ? 2 : 1;
        } else {
            throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(obj));
        }
        boolean z = obj instanceof ByteBuf;
        if (!z || ((ByteBuf) obj).isReadable()) {
            boolean z2 = obj instanceof HttpContent;
            if (z2 || z || (obj instanceof FileRegion)) {
                if (this.f != 0) {
                    long b2 = b(obj);
                    int i = this.f;
                    if (i == 1) {
                        if (b2 > 0) {
                            if (byteBuf == null || byteBuf.writableBytes() < b2 || !z2) {
                                if (byteBuf != null) {
                                    list.add(byteBuf);
                                }
                                list.add(a(obj));
                            } else {
                                byteBuf.writeBytes(((HttpContent) obj).content());
                                list.add(byteBuf);
                            }
                        } else if (byteBuf != null) {
                            list.add(byteBuf);
                        } else {
                            list.add(Unpooled.EMPTY_BUFFER);
                        }
                        if (obj instanceof LastHttpContent) {
                            this.f = 0;
                        }
                    } else if (i == 2) {
                        if (byteBuf != null) {
                            list.add(byteBuf);
                        }
                        a(channelHandlerContext, obj, b2, list);
                    } else {
                        throw new Error();
                    }
                } else {
                    throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(obj));
                }
            } else if (byteBuf != null) {
                list.add(byteBuf);
            }
        } else {
            list.add(Unpooled.EMPTY_BUFFER);
        }
    }

    protected void encodeHeaders(HttpHeaders httpHeaders, ByteBuf byteBuf) throws Exception {
        Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence = httpHeaders.iteratorCharSequence();
        while (iteratorCharSequence.hasNext()) {
            Map.Entry<CharSequence, CharSequence> next = iteratorCharSequence.next();
            c.a(next.getKey(), next.getValue(), byteBuf);
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, Object obj, long j, List<Object> list) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i > 0) {
            byte[] bytes = Long.toHexString(j).getBytes(CharsetUtil.US_ASCII);
            ByteBuf buffer = channelHandlerContext.alloc().buffer(bytes.length + 2);
            buffer.writeBytes(bytes);
            buffer.writeBytes(a);
            list.add(buffer);
            list.add(a(obj));
            list.add(d.duplicate());
        }
        if (obj instanceof LastHttpContent) {
            HttpHeaders trailingHeaders = ((LastHttpContent) obj).trailingHeaders();
            if (trailingHeaders.isEmpty()) {
                list.add(e.duplicate());
            } else {
                ByteBuf buffer2 = channelHandlerContext.alloc().buffer();
                buffer2.writeBytes(b);
                try {
                    encodeHeaders(trailingHeaders, buffer2);
                } catch (Exception e2) {
                    buffer2.release();
                    PlatformDependent.throwException(e2);
                }
                buffer2.writeBytes(a);
                list.add(buffer2);
            }
            this.f = 0;
        } else if (i == 0) {
            list.add(Unpooled.EMPTY_BUFFER);
        }
    }

    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return (obj instanceof HttpObject) || (obj instanceof ByteBuf) || (obj instanceof FileRegion);
    }

    private static Object a(Object obj) {
        if (obj instanceof ByteBuf) {
            return ((ByteBuf) obj).retain();
        }
        if (obj instanceof HttpContent) {
            return ((HttpContent) obj).content().retain();
        }
        if (obj instanceof FileRegion) {
            return ((FileRegion) obj).retain();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(obj));
    }

    private static long b(Object obj) {
        if (obj instanceof HttpContent) {
            return ((HttpContent) obj).content().readableBytes();
        }
        if (obj instanceof ByteBuf) {
            return ((ByteBuf) obj).readableBytes();
        }
        if (obj instanceof FileRegion) {
            return ((FileRegion) obj).count();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(obj));
    }

    @Deprecated
    protected static void encodeAscii(String str, ByteBuf byteBuf) {
        HttpUtil.a(str, byteBuf);
    }
}
