package io.netty.channel;

/* loaded from: classes4.dex */
public class ChannelPipelineException extends ChannelException {
    private static final long serialVersionUID = 3379174210419885980L;

    public ChannelPipelineException() {
    }

    public ChannelPipelineException(String str, Throwable th) {
        super(str, th);
    }

    public ChannelPipelineException(String str) {
        super(str);
    }

    public ChannelPipelineException(Throwable th) {
        super(th);
    }
}
