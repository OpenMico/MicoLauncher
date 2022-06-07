package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

/* loaded from: classes4.dex */
public interface Http2Connection {

    /* loaded from: classes4.dex */
    public interface Endpoint<F extends Http2FlowController> {
        void allowPushTo(boolean z);

        boolean allowPushTo();

        boolean canOpenStream();

        Http2Stream createIdleStream(int i) throws Http2Exception;

        Http2Stream createStream(int i, boolean z) throws Http2Exception;

        boolean created(Http2Stream http2Stream);

        F flowController();

        void flowController(F f);

        int incrementAndGetNextStreamId();

        boolean isServer();

        boolean isValidStreamId(int i);

        int lastStreamCreated();

        int lastStreamKnownByPeer();

        int maxActiveStreams();

        void maxActiveStreams(int i);

        boolean mayHaveCreatedStream(int i);

        int numActiveStreams();

        Endpoint<? extends Http2FlowController> opposite();

        Http2Stream reservePushStream(int i, Http2Stream http2Stream) throws Http2Exception;
    }

    /* loaded from: classes4.dex */
    public interface Listener {
        void onGoAwayReceived(int i, long j, ByteBuf byteBuf);

        void onGoAwaySent(int i, long j, ByteBuf byteBuf);

        void onPriorityTreeParentChanged(Http2Stream http2Stream, Http2Stream http2Stream2);

        void onPriorityTreeParentChanging(Http2Stream http2Stream, Http2Stream http2Stream2);

        void onStreamActive(Http2Stream http2Stream);

        void onStreamAdded(Http2Stream http2Stream);

        void onStreamClosed(Http2Stream http2Stream);

        void onStreamHalfClosed(Http2Stream http2Stream);

        void onStreamRemoved(Http2Stream http2Stream);

        void onWeightChanged(Http2Stream http2Stream, short s);
    }

    /* loaded from: classes4.dex */
    public interface PropertyKey {
    }

    void addListener(Listener listener);

    Future<Void> close(Promise<Void> promise);

    Http2Stream connectionStream();

    Http2Stream forEachActiveStream(Http2StreamVisitor http2StreamVisitor) throws Http2Exception;

    void goAwayReceived(int i, long j, ByteBuf byteBuf);

    boolean goAwayReceived();

    void goAwaySent(int i, long j, ByteBuf byteBuf);

    boolean goAwaySent();

    boolean isServer();

    Endpoint<Http2LocalFlowController> local();

    PropertyKey newKey();

    int numActiveStreams();

    Endpoint<Http2RemoteFlowController> remote();

    void removeListener(Listener listener);

    Http2Stream stream(int i);

    boolean streamMayHaveExisted(int i);
}
