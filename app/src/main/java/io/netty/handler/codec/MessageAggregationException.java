package io.netty.handler.codec;

/* loaded from: classes4.dex */
public class MessageAggregationException extends IllegalStateException {
    private static final long serialVersionUID = -1995826182950310255L;

    public MessageAggregationException() {
    }

    public MessageAggregationException(String str) {
        super(str);
    }

    public MessageAggregationException(String str, Throwable th) {
        super(str, th);
    }

    public MessageAggregationException(Throwable th) {
        super(th);
    }
}
