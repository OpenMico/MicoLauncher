package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.util.AsyncMapping;
import io.netty.util.CharsetUtil;
import io.netty.util.DomainNameMapping;
import io.netty.util.Mapping;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.IDN;
import java.net.SocketAddress;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class SniHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(SniHandler.class);
    private static final b c = new b(null, null);
    private final AsyncMapping<String, SslContext> d;
    private boolean e;
    private boolean f;
    private boolean g;
    private volatile b h;

    public SniHandler(Mapping<? super String, ? extends SslContext> mapping) {
        this(new a(mapping));
    }

    public SniHandler(DomainNameMapping<? extends SslContext> domainNameMapping) {
        this((Mapping<? super String, ? extends SslContext>) domainNameMapping);
    }

    public SniHandler(AsyncMapping<? super String, ? extends SslContext> asyncMapping) {
        this.h = c;
        this.d = (AsyncMapping) ObjectUtil.checkNotNull(asyncMapping, "mapping");
    }

    public String hostname() {
        return this.h.b;
    }

    public SslContext sslContext() {
        return this.h.a;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!this.f && !this.e) {
            int writerIndex = byteBuf.writerIndex();
            int i = 0;
            while (true) {
                if (i < 4) {
                    try {
                        int readerIndex = byteBuf.readerIndex();
                        int i2 = writerIndex - readerIndex;
                        if (i2 >= 5) {
                            switch (byteBuf.getUnsignedByte(readerIndex)) {
                                case 20:
                                case 21:
                                    int a2 = n.a(byteBuf, readerIndex);
                                    if (a2 == -1) {
                                        this.e = true;
                                        NotSslRecordException notSslRecordException = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(byteBuf));
                                        byteBuf.skipBytes(byteBuf.readableBytes());
                                        channelHandlerContext.fireExceptionCaught((Throwable) notSslRecordException);
                                        n.a(channelHandlerContext, notSslRecordException);
                                        return;
                                    } else if (i2 - 5 >= a2) {
                                        byteBuf.skipBytes(a2);
                                        i++;
                                    } else {
                                        return;
                                    }
                                case 22:
                                    if (byteBuf.getUnsignedByte(readerIndex + 1) == 3) {
                                        int unsignedShort = byteBuf.getUnsignedShort(readerIndex + 3) + 5;
                                        if (i2 >= unsignedShort) {
                                            int i3 = unsignedShort + readerIndex;
                                            int i4 = readerIndex + 43;
                                            if (i3 - i4 >= 6) {
                                                int unsignedByte = i4 + byteBuf.getUnsignedByte(i4) + 1;
                                                int unsignedShort2 = unsignedByte + byteBuf.getUnsignedShort(unsignedByte) + 2;
                                                int unsignedByte2 = unsignedShort2 + byteBuf.getUnsignedByte(unsignedShort2) + 1;
                                                int unsignedShort3 = byteBuf.getUnsignedShort(unsignedByte2);
                                                int i5 = unsignedByte2 + 2;
                                                int i6 = unsignedShort3 + i5;
                                                if (i6 <= i3) {
                                                    while (true) {
                                                        if (i6 - i5 >= 4) {
                                                            int unsignedShort4 = byteBuf.getUnsignedShort(i5);
                                                            int i7 = i5 + 2;
                                                            int unsignedShort5 = byteBuf.getUnsignedShort(i7);
                                                            int i8 = i7 + 2;
                                                            if (i6 - i8 < unsignedShort5) {
                                                                break;
                                                            } else if (unsignedShort4 == 0) {
                                                                int i9 = i8 + 2;
                                                                if (i6 - i9 < 3) {
                                                                    break;
                                                                } else {
                                                                    short unsignedByte3 = byteBuf.getUnsignedByte(i9);
                                                                    int i10 = i9 + 1;
                                                                    if (unsignedByte3 == 0) {
                                                                        int unsignedShort6 = byteBuf.getUnsignedShort(i10);
                                                                        int i11 = i10 + 2;
                                                                        if (i6 - i11 < unsignedShort6) {
                                                                            break;
                                                                        } else {
                                                                            a(channelHandlerContext, IDN.toASCII(byteBuf.toString(i11, unsignedShort6, CharsetUtil.UTF_8), 1).toLowerCase(Locale.US));
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                i5 = i8 + unsignedShort5;
                                                            }
                                                        } else {
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        } else {
                                            return;
                                        }
                                    }
                                    break;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        if (a.isDebugEnabled()) {
                            InternalLogger internalLogger = a;
                            internalLogger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(byteBuf), th);
                        }
                    }
                }
            }
            a(channelHandlerContext, (String) null);
        }
    }

    private void a(final ChannelHandlerContext channelHandlerContext, final String str) {
        Future<SslContext> map = this.d.map(str, channelHandlerContext.executor().newPromise());
        if (!map.isDone()) {
            this.f = true;
            map.addListener(new FutureListener<SslContext>() { // from class: io.netty.handler.ssl.SniHandler.1
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<SslContext> future) throws Exception {
                    try {
                        SniHandler.this.f = false;
                        if (future.isSuccess()) {
                            SniHandler.this.a(channelHandlerContext, new b(future.getNow(), str));
                        } else {
                            ChannelHandlerContext channelHandlerContext2 = channelHandlerContext;
                            channelHandlerContext2.fireExceptionCaught((Throwable) new DecoderException("failed to get the SslContext for " + str, future.cause()));
                        }
                    } finally {
                        if (SniHandler.this.g) {
                            SniHandler.this.g = false;
                            channelHandlerContext.read();
                        }
                    }
                }
            });
        } else if (map.isSuccess()) {
            a(channelHandlerContext, new b(map.getNow(), str));
        } else {
            throw new DecoderException("failed to get the SslContext for " + str, map.cause());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelHandlerContext channelHandlerContext, b bVar) {
        this.h = bVar;
        channelHandlerContext.pipeline().replace(this, SslHandler.class.getName(), bVar.a.newHandler(channelHandlerContext.alloc()));
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
        if (this.f) {
            this.g = true;
        } else {
            channelHandlerContext.read();
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.write(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    /* loaded from: classes4.dex */
    private static final class a implements AsyncMapping<String, SslContext> {
        private final Mapping<? super String, ? extends SslContext> a;

        private a(Mapping<? super String, ? extends SslContext> mapping) {
            this.a = (Mapping) ObjectUtil.checkNotNull(mapping, "mapping");
        }

        /* renamed from: a */
        public Future<SslContext> map(String str, Promise<SslContext> promise) {
            try {
                return promise.setSuccess((SslContext) this.a.map(str));
            } catch (Throwable th) {
                return promise.setFailure(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b {
        final SslContext a;
        final String b;

        b(SslContext sslContext, String str) {
            this.a = sslContext;
            this.b = str;
        }
    }
}
