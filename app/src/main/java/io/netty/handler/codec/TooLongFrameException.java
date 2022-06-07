package io.netty.handler.codec;

/* loaded from: classes4.dex */
public class TooLongFrameException extends DecoderException {
    private static final long serialVersionUID = -1995801950698951640L;

    public TooLongFrameException() {
    }

    public TooLongFrameException(String str, Throwable th) {
        super(str, th);
    }

    public TooLongFrameException(String str) {
        super(str);
    }

    public TooLongFrameException(Throwable th) {
        super(th);
    }
}
