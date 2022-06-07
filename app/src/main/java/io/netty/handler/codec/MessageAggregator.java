package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class MessageAggregator<I, S, C extends ByteBufHolder, O extends ByteBufHolder> extends MessageToMessageDecoder<I> {
    private final int a;
    private O b;
    private boolean c;
    private int d = 1024;
    private ChannelHandlerContext e;
    private ChannelFutureListener f;

    protected void aggregate(O o, C c) throws Exception {
    }

    protected abstract O beginAggregation(S s, ByteBuf byteBuf) throws Exception;

    protected abstract boolean closeAfterContinueResponse(Object obj) throws Exception;

    protected void finishAggregation(O o) throws Exception {
    }

    protected abstract boolean ignoreContentAfterContinueResponse(Object obj) throws Exception;

    protected abstract boolean isAggregated(I i) throws Exception;

    protected abstract boolean isContentLengthInvalid(S s, int i) throws Exception;

    protected abstract boolean isContentMessage(I i) throws Exception;

    protected abstract boolean isLastContentMessage(C c) throws Exception;

    protected abstract boolean isStartMessage(I i) throws Exception;

    protected abstract Object newContinueResponse(S s, int i, ChannelPipeline channelPipeline) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public MessageAggregator(int i) {
        a(i);
        this.a = i;
    }

    protected MessageAggregator(int i, Class<? extends I> cls) {
        super(cls);
        a(i);
        this.a = i;
    }

    private static void a(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("maxContentLength: " + i + " (expected: >= 0)");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public boolean acceptInboundMessage(Object obj) throws Exception {
        if (!super.acceptInboundMessage(obj)) {
            return false;
        }
        return (isContentMessage(obj) || isStartMessage(obj)) && !isAggregated(obj);
    }

    public final int maxContentLength() {
        return this.a;
    }

    public final int maxCumulationBufferComponents() {
        return this.d;
    }

    public final void setMaxCumulationBufferComponents(int i) {
        if (i < 2) {
            throw new IllegalArgumentException("maxCumulationBufferComponents: " + i + " (expected: >= 2)");
        } else if (this.e == null) {
            this.d = i;
        } else {
            throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
        }
    }

    public final boolean isHandlingOversizedMessage() {
        return this.c;
    }

    protected final ChannelHandlerContext ctx() {
        ChannelHandlerContext channelHandlerContext = this.e;
        if (channelHandlerContext != null) {
            return channelHandlerContext;
        }
        throw new IllegalStateException("not added to a pipeline yet");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public void decode(final ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception {
        ByteBufHolder byteBufHolder;
        O o = this.b;
        if (isStartMessage(i)) {
            this.c = false;
            if (o == null) {
                Object newContinueResponse = newContinueResponse(i, this.a, channelHandlerContext.pipeline());
                if (newContinueResponse != null) {
                    ChannelFutureListener channelFutureListener = this.f;
                    if (channelFutureListener == null) {
                        channelFutureListener = new ChannelFutureListener() { // from class: io.netty.handler.codec.MessageAggregator.1
                            /* renamed from: a */
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (!channelFuture.isSuccess()) {
                                    channelHandlerContext.fireExceptionCaught(channelFuture.cause());
                                }
                            }
                        };
                        this.f = channelFutureListener;
                    }
                    boolean closeAfterContinueResponse = closeAfterContinueResponse(newContinueResponse);
                    this.c = ignoreContentAfterContinueResponse(newContinueResponse);
                    Future<Void> addListener = channelHandlerContext.writeAndFlush(newContinueResponse).addListener((GenericFutureListener<? extends Future<? super Void>>) channelFutureListener);
                    if (closeAfterContinueResponse) {
                        addListener.addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
                        return;
                    } else if (this.c) {
                        return;
                    }
                } else if (isContentLengthInvalid(i, this.a)) {
                    a(channelHandlerContext, (ChannelHandlerContext) i);
                    return;
                }
                if (!(i instanceof DecoderResultProvider) || ((DecoderResultProvider) i).decoderResult().isSuccess()) {
                    CompositeByteBuf compositeBuffer = channelHandlerContext.alloc().compositeBuffer(this.d);
                    if (i instanceof ByteBufHolder) {
                        a(compositeBuffer, ((ByteBufHolder) i).content());
                    }
                    this.b = (O) beginAggregation(i, compositeBuffer);
                    return;
                }
                if (i instanceof ByteBufHolder) {
                    ByteBufHolder byteBufHolder2 = (ByteBufHolder) i;
                    if (byteBufHolder2.content().isReadable()) {
                        byteBufHolder = beginAggregation(i, byteBufHolder2.content().retain());
                        finishAggregation(byteBufHolder);
                        list.add(byteBufHolder);
                        this.b = null;
                        return;
                    }
                }
                byteBufHolder = beginAggregation(i, Unpooled.EMPTY_BUFFER);
                finishAggregation(byteBufHolder);
                list.add(byteBufHolder);
                this.b = null;
                return;
            }
            throw new MessageAggregationException();
        } else if (isContentMessage(i)) {
            ByteBufHolder byteBufHolder3 = (ByteBufHolder) i;
            ByteBuf content = byteBufHolder3.content();
            boolean isLastContentMessage = isLastContentMessage(byteBufHolder3);
            if (this.c) {
                if (isLastContentMessage) {
                    this.b = null;
                }
            } else if (o != null) {
                CompositeByteBuf compositeByteBuf = (CompositeByteBuf) o.content();
                if (compositeByteBuf.readableBytes() > this.a - content.readableBytes()) {
                    a(channelHandlerContext, (ChannelHandlerContext) o);
                    return;
                }
                a(compositeByteBuf, content);
                aggregate(o, byteBufHolder3);
                if (byteBufHolder3 instanceof DecoderResultProvider) {
                    DecoderResult decoderResult = ((DecoderResultProvider) byteBufHolder3).decoderResult();
                    if (!decoderResult.isSuccess()) {
                        if (o instanceof DecoderResultProvider) {
                            ((DecoderResultProvider) o).setDecoderResult(DecoderResult.failure(decoderResult.cause()));
                        }
                        isLastContentMessage = true;
                    }
                }
                if (isLastContentMessage) {
                    finishAggregation(o);
                    list.add(o);
                    this.b = null;
                }
            } else {
                throw new MessageAggregationException();
            }
        } else {
            throw new MessageAggregationException();
        }
    }

    private static void a(CompositeByteBuf compositeByteBuf, ByteBuf byteBuf) {
        if (byteBuf.isReadable()) {
            byteBuf.retain();
            compositeByteBuf.addComponent(true, byteBuf);
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, S s) throws Exception {
        this.c = true;
        this.b = null;
        try {
            handleOversizedMessage(channelHandlerContext, s);
        } finally {
            ReferenceCountUtil.release(s);
        }
    }

    protected void handleOversizedMessage(ChannelHandlerContext channelHandlerContext, S s) throws Exception {
        channelHandlerContext.fireExceptionCaught((Throwable) new TooLongFrameException("content length exceeded " + maxContentLength() + " bytes."));
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        O o = this.b;
        if (o != null) {
            o.release();
            this.b = null;
        }
        super.channelInactive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.e = channelHandlerContext;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved(channelHandlerContext);
        O o = this.b;
        if (o != null) {
            o.release();
            this.b = null;
        }
    }
}
