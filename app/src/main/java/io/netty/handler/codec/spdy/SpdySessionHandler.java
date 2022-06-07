package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.spdy.SpdySession;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.EmptyArrays;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class SpdySessionHandler extends ChannelDuplexHandler {
    private static final SpdyProtocolException a = new SpdyProtocolException();
    private static final SpdyProtocolException b = new SpdyProtocolException("Stream closed");
    private int g;
    private boolean k;
    private boolean l;
    private ChannelFutureListener m;
    private final boolean n;
    private final int o;
    private int c = 65536;
    private int d = 65536;
    private volatile int e = 65536;
    private final SpdySession f = new SpdySession(this.c, this.d);
    private int h = Integer.MAX_VALUE;
    private int i = Integer.MAX_VALUE;
    private final AtomicInteger j = new AtomicInteger();

    static {
        a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        b.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public SpdySessionHandler(SpdyVersion spdyVersion, boolean z) {
        if (spdyVersion != null) {
            this.n = z;
            this.o = spdyVersion.b();
            return;
        }
        throw new NullPointerException("version");
    }

    public void setSessionReceiveWindowSize(int i) {
        if (i >= 0) {
            this.e = i;
            return;
        }
        throw new IllegalArgumentException("sessionReceiveWindowSize");
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame) obj;
            int streamId = spdyDataFrame.streamId();
            int readableBytes = spdyDataFrame.content().readableBytes() * (-1);
            int b2 = this.f.b(0, readableBytes);
            if (b2 < 0) {
                a(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
            if (b2 <= this.e / 2) {
                int i = this.e - b2;
                this.f.b(0, i);
                channelHandlerContext.writeAndFlush(new DefaultSpdyWindowUpdateFrame(0, i));
            }
            if (!this.f.a(streamId)) {
                spdyDataFrame.release();
                if (streamId <= this.g) {
                    a(channelHandlerContext, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                    return;
                } else if (!this.k) {
                    a(channelHandlerContext, streamId, SpdyStreamStatus.INVALID_STREAM);
                    return;
                } else {
                    return;
                }
            } else if (this.f.b(streamId)) {
                spdyDataFrame.release();
                a(channelHandlerContext, streamId, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
                return;
            } else if (a(streamId) || this.f.d(streamId)) {
                int b3 = this.f.b(streamId, readableBytes);
                if (b3 < this.f.g(streamId)) {
                    spdyDataFrame.release();
                    a(channelHandlerContext, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                    return;
                }
                if (b3 < 0) {
                    while (spdyDataFrame.content().readableBytes() > this.d) {
                        channelHandlerContext.writeAndFlush(new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readRetainedSlice(this.d)));
                    }
                }
                if (b3 <= this.d / 2 && !spdyDataFrame.isLast()) {
                    int i2 = this.d - b3;
                    this.f.b(streamId, i2);
                    channelHandlerContext.writeAndFlush(new DefaultSpdyWindowUpdateFrame(streamId, i2));
                }
                if (spdyDataFrame.isLast()) {
                    a(streamId, true, channelHandlerContext.newSucceededFuture());
                }
            } else {
                spdyDataFrame.release();
                a(channelHandlerContext, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
        } else if (obj instanceof SpdySynStreamFrame) {
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame) obj;
            int streamId2 = spdySynStreamFrame.streamId();
            if (spdySynStreamFrame.isInvalid() || !a(streamId2) || this.f.a(streamId2)) {
                a(channelHandlerContext, streamId2, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            } else if (streamId2 <= this.g) {
                a(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            } else if (!a(streamId2, spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional())) {
                a(channelHandlerContext, streamId2, SpdyStreamStatus.REFUSED_STREAM);
                return;
            }
        } else if (obj instanceof SpdySynReplyFrame) {
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame) obj;
            int streamId3 = spdySynReplyFrame.streamId();
            if (spdySynReplyFrame.isInvalid() || a(streamId3) || this.f.b(streamId3)) {
                a(channelHandlerContext, streamId3, SpdyStreamStatus.INVALID_STREAM);
                return;
            } else if (this.f.d(streamId3)) {
                a(channelHandlerContext, streamId3, SpdyStreamStatus.STREAM_IN_USE);
                return;
            } else {
                this.f.e(streamId3);
                if (spdySynReplyFrame.isLast()) {
                    a(streamId3, true, channelHandlerContext.newSucceededFuture());
                }
            }
        } else if (obj instanceof SpdyRstStreamFrame) {
            a(((SpdyRstStreamFrame) obj).streamId(), channelHandlerContext.newSucceededFuture());
        } else if (obj instanceof SpdySettingsFrame) {
            SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame) obj;
            int value = spdySettingsFrame.getValue(0);
            if (value < 0 || value == this.o) {
                int value2 = spdySettingsFrame.getValue(4);
                if (value2 >= 0) {
                    this.h = value2;
                }
                if (spdySettingsFrame.isPersisted(7)) {
                    spdySettingsFrame.removeValue(7);
                }
                spdySettingsFrame.setPersistValue(7, false);
                int value3 = spdySettingsFrame.getValue(7);
                if (value3 >= 0) {
                    b(value3);
                }
            } else {
                a(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
        } else if (obj instanceof SpdyPingFrame) {
            SpdyPingFrame spdyPingFrame = (SpdyPingFrame) obj;
            if (a(spdyPingFrame.id())) {
                channelHandlerContext.writeAndFlush(spdyPingFrame);
                return;
            } else if (this.j.get() != 0) {
                this.j.getAndDecrement();
            } else {
                return;
            }
        } else if (obj instanceof SpdyGoAwayFrame) {
            this.l = true;
        } else if (obj instanceof SpdyHeadersFrame) {
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame) obj;
            int streamId4 = spdyHeadersFrame.streamId();
            if (spdyHeadersFrame.isInvalid()) {
                a(channelHandlerContext, streamId4, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            } else if (this.f.b(streamId4)) {
                a(channelHandlerContext, streamId4, SpdyStreamStatus.INVALID_STREAM);
                return;
            } else if (spdyHeadersFrame.isLast()) {
                a(streamId4, true, channelHandlerContext.newSucceededFuture());
            }
        } else if (obj instanceof SpdyWindowUpdateFrame) {
            SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame) obj;
            int streamId5 = spdyWindowUpdateFrame.streamId();
            int deltaWindowSize = spdyWindowUpdateFrame.deltaWindowSize();
            if (streamId5 != 0 && this.f.c(streamId5)) {
                return;
            }
            if (this.f.f(streamId5) <= Integer.MAX_VALUE - deltaWindowSize) {
                a(channelHandlerContext, streamId5, deltaWindowSize);
            } else if (streamId5 == 0) {
                a(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            } else {
                a(channelHandlerContext, streamId5, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                return;
            }
        }
        channelHandlerContext.fireChannelRead(obj);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        for (Integer num : this.f.b().keySet()) {
            a(num.intValue(), channelHandlerContext.newSucceededFuture());
        }
        channelHandlerContext.fireChannelInactive();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (th instanceof SpdyProtocolException) {
            a(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        a(channelHandlerContext, channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if ((obj instanceof SpdyDataFrame) || (obj instanceof SpdySynStreamFrame) || (obj instanceof SpdySynReplyFrame) || (obj instanceof SpdyRstStreamFrame) || (obj instanceof SpdySettingsFrame) || (obj instanceof SpdyPingFrame) || (obj instanceof SpdyGoAwayFrame) || (obj instanceof SpdyHeadersFrame) || (obj instanceof SpdyWindowUpdateFrame)) {
            a(channelHandlerContext, obj, channelPromise);
        } else {
            channelHandlerContext.write(obj, channelPromise);
        }
    }

    private void a(final ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (obj instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame) obj;
            int streamId = spdyDataFrame.streamId();
            if (this.f.c(streamId)) {
                spdyDataFrame.release();
                channelPromise.setFailure((Throwable) a);
                return;
            }
            int readableBytes = spdyDataFrame.content().readableBytes();
            int min = Math.min(this.f.f(streamId), this.f.f(0));
            if (min <= 0) {
                this.f.a(streamId, new SpdySession.PendingWrite(spdyDataFrame, channelPromise));
                return;
            } else if (min < readableBytes) {
                int i = min * (-1);
                this.f.a(streamId, i);
                this.f.a(0, i);
                DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readRetainedSlice(min));
                this.f.a(streamId, new SpdySession.PendingWrite(spdyDataFrame, channelPromise));
                channelHandlerContext.write(defaultSpdyDataFrame).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.spdy.SpdySessionHandler.1
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            SpdySessionHandler.this.a(channelHandlerContext, SpdySessionStatus.INTERNAL_ERROR);
                        }
                    }
                });
                return;
            } else {
                int i2 = readableBytes * (-1);
                this.f.a(streamId, i2);
                this.f.a(0, i2);
                channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.spdy.SpdySessionHandler.2
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            SpdySessionHandler.this.a(channelHandlerContext, SpdySessionStatus.INTERNAL_ERROR);
                        }
                    }
                });
                if (spdyDataFrame.isLast()) {
                    a(streamId, false, (ChannelFuture) channelPromise);
                }
            }
        } else if (obj instanceof SpdySynStreamFrame) {
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame) obj;
            int streamId2 = spdySynStreamFrame.streamId();
            if (a(streamId2)) {
                channelPromise.setFailure((Throwable) a);
                return;
            } else if (!a(streamId2, spdySynStreamFrame.priority(), spdySynStreamFrame.isUnidirectional(), spdySynStreamFrame.isLast())) {
                channelPromise.setFailure((Throwable) a);
                return;
            }
        } else if (obj instanceof SpdySynReplyFrame) {
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame) obj;
            int streamId3 = spdySynReplyFrame.streamId();
            if (!a(streamId3) || this.f.c(streamId3)) {
                channelPromise.setFailure((Throwable) a);
                return;
            } else if (spdySynReplyFrame.isLast()) {
                a(streamId3, false, (ChannelFuture) channelPromise);
            }
        } else if (obj instanceof SpdyRstStreamFrame) {
            a(((SpdyRstStreamFrame) obj).streamId(), channelPromise);
        } else if (obj instanceof SpdySettingsFrame) {
            SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame) obj;
            int value = spdySettingsFrame.getValue(0);
            if (value < 0 || value == this.o) {
                int value2 = spdySettingsFrame.getValue(4);
                if (value2 >= 0) {
                    this.i = value2;
                }
                if (spdySettingsFrame.isPersisted(7)) {
                    spdySettingsFrame.removeValue(7);
                }
                spdySettingsFrame.setPersistValue(7, false);
                int value3 = spdySettingsFrame.getValue(7);
                if (value3 >= 0) {
                    c(value3);
                }
            } else {
                channelPromise.setFailure((Throwable) a);
                return;
            }
        } else if (obj instanceof SpdyPingFrame) {
            SpdyPingFrame spdyPingFrame = (SpdyPingFrame) obj;
            if (a(spdyPingFrame.id())) {
                channelHandlerContext.fireExceptionCaught((Throwable) new IllegalArgumentException("invalid PING ID: " + spdyPingFrame.id()));
                return;
            }
            this.j.getAndIncrement();
        } else if (obj instanceof SpdyGoAwayFrame) {
            channelPromise.setFailure((Throwable) a);
            return;
        } else if (obj instanceof SpdyHeadersFrame) {
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame) obj;
            int streamId4 = spdyHeadersFrame.streamId();
            if (this.f.c(streamId4)) {
                channelPromise.setFailure((Throwable) a);
                return;
            } else if (spdyHeadersFrame.isLast()) {
                a(streamId4, false, (ChannelFuture) channelPromise);
            }
        } else if (obj instanceof SpdyWindowUpdateFrame) {
            channelPromise.setFailure((Throwable) a);
            return;
        }
        channelHandlerContext.write(obj, channelPromise);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelHandlerContext channelHandlerContext, SpdySessionStatus spdySessionStatus) {
        b(channelHandlerContext, spdySessionStatus).addListener((GenericFutureListener<? extends Future<? super Void>>) new a(channelHandlerContext, channelHandlerContext.newPromise()));
    }

    private void a(ChannelHandlerContext channelHandlerContext, int i, SpdyStreamStatus spdyStreamStatus) {
        boolean z = !this.f.b(i);
        ChannelPromise newPromise = channelHandlerContext.newPromise();
        a(i, newPromise);
        DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(i, spdyStreamStatus);
        channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame, newPromise);
        if (z) {
            channelHandlerContext.fireChannelRead((Object) defaultSpdyRstStreamFrame);
        }
    }

    private boolean a(int i) {
        boolean a2 = a.a(i);
        return (this.n && !a2) || (!this.n && a2);
    }

    private void b(int i) {
        int i2 = i - this.c;
        this.c = i;
        this.f.h(i2);
    }

    private void c(int i) {
        int i2 = i - this.d;
        this.d = i;
        this.f.i(i2);
    }

    private boolean a(int i, byte b2, boolean z, boolean z2) {
        if (this.l || this.k) {
            return false;
        }
        boolean a2 = a(i);
        if (this.f.a(a2) >= (a2 ? this.i : this.h)) {
            return false;
        }
        this.f.a(i, b2, z, z2, this.c, this.d, a2);
        if (!a2) {
            return true;
        }
        this.g = i;
        return true;
    }

    private void a(int i, boolean z, ChannelFuture channelFuture) {
        if (z) {
            this.f.a(i, a(i));
        } else {
            this.f.b(i, a(i));
        }
        if (this.m != null && this.f.a()) {
            channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) this.m);
        }
    }

    private void a(int i, ChannelFuture channelFuture) {
        this.f.a(i, b, a(i));
        if (this.m != null && this.f.a()) {
            channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) this.m);
        }
    }

    private void a(final ChannelHandlerContext channelHandlerContext, int i, int i2) {
        this.f.a(i, i2);
        while (true) {
            SpdySession.PendingWrite j = this.f.j(i);
            if (j != null) {
                SpdyDataFrame spdyDataFrame = j.a;
                int readableBytes = spdyDataFrame.content().readableBytes();
                int streamId = spdyDataFrame.streamId();
                int min = Math.min(this.f.f(streamId), this.f.f(0));
                if (min > 0) {
                    if (min < readableBytes) {
                        int i3 = min * (-1);
                        this.f.a(streamId, i3);
                        this.f.a(0, i3);
                        channelHandlerContext.writeAndFlush(new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readRetainedSlice(min))).addListener(new ChannelFutureListener() { // from class: io.netty.handler.codec.spdy.SpdySessionHandler.3
                            /* renamed from: a */
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (!channelFuture.isSuccess()) {
                                    SpdySessionHandler.this.a(channelHandlerContext, SpdySessionStatus.INTERNAL_ERROR);
                                }
                            }
                        });
                    } else {
                        this.f.k(streamId);
                        int i4 = readableBytes * (-1);
                        this.f.a(streamId, i4);
                        this.f.a(0, i4);
                        if (spdyDataFrame.isLast()) {
                            a(streamId, false, (ChannelFuture) j.b);
                        }
                        channelHandlerContext.writeAndFlush(spdyDataFrame, j.b).addListener(new ChannelFutureListener() { // from class: io.netty.handler.codec.spdy.SpdySessionHandler.4
                            /* renamed from: a */
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (!channelFuture.isSuccess()) {
                                    SpdySessionHandler.this.a(channelHandlerContext, SpdySessionStatus.INTERNAL_ERROR);
                                }
                            }
                        });
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        ChannelFuture b2 = b(channelHandlerContext, SpdySessionStatus.OK);
        if (this.f.a()) {
            b2.addListener((GenericFutureListener<? extends Future<? super Void>>) new a(channelHandlerContext, channelPromise));
        } else {
            this.m = new a(channelHandlerContext, channelPromise);
        }
    }

    private ChannelFuture b(ChannelHandlerContext channelHandlerContext, SpdySessionStatus spdySessionStatus) {
        if (this.k) {
            return channelHandlerContext.newSucceededFuture();
        }
        this.k = true;
        return channelHandlerContext.writeAndFlush(new DefaultSpdyGoAwayFrame(this.g, spdySessionStatus));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a implements ChannelFutureListener {
        private final ChannelHandlerContext a;
        private final ChannelPromise b;

        a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            this.a = channelHandlerContext;
            this.b = channelPromise;
        }

        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            this.a.close(this.b);
        }
    }
}
