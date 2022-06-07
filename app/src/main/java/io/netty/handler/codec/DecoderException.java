package io.netty.handler.codec;

/* loaded from: classes4.dex */
public class DecoderException extends CodecException {
    private static final long serialVersionUID = 6926716840699621852L;

    public DecoderException() {
    }

    public DecoderException(String str, Throwable th) {
        super(str, th);
    }

    public DecoderException(String str) {
        super(str);
    }

    public DecoderException(Throwable th) {
        super(th);
    }
}
