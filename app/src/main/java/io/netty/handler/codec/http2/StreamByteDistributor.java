package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public interface StreamByteDistributor {

    /* loaded from: classes4.dex */
    public interface StreamState {
        boolean hasFrame();

        int pendingBytes();

        Http2Stream stream();

        int windowSize();
    }

    /* loaded from: classes4.dex */
    public interface Writer {
        void write(Http2Stream http2Stream, int i);
    }

    boolean distribute(int i, Writer writer) throws Http2Exception;

    void updateStreamableBytes(StreamState streamState);
}
