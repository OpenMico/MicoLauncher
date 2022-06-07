package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;

/* loaded from: classes4.dex */
public interface Http2RemoteFlowController extends Http2FlowController {

    /* loaded from: classes4.dex */
    public interface FlowControlled {
        void error(ChannelHandlerContext channelHandlerContext, Throwable th);

        boolean merge(ChannelHandlerContext channelHandlerContext, FlowControlled flowControlled);

        int size();

        void write(ChannelHandlerContext channelHandlerContext, int i);

        void writeComplete();
    }

    /* loaded from: classes4.dex */
    public interface Listener {
        void writabilityChanged(Http2Stream http2Stream);
    }

    void addFlowControlled(Http2Stream http2Stream, FlowControlled flowControlled);

    ChannelHandlerContext channelHandlerContext();

    void channelWritabilityChanged() throws Http2Exception;

    boolean hasFlowControlled(Http2Stream http2Stream);

    boolean isWritable(Http2Stream http2Stream);

    void listener(Listener listener);

    void writePendingBytes() throws Http2Exception;
}
