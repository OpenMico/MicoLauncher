package io.netty.handler.codec.socksx;

import io.netty.handler.codec.DecoderResult;

/* loaded from: classes4.dex */
public abstract class AbstractSocksMessage implements SocksMessage {
    private DecoderResult a = DecoderResult.SUCCESS;

    @Override // io.netty.handler.codec.DecoderResultProvider
    public DecoderResult decoderResult() {
        return this.a;
    }

    @Override // io.netty.handler.codec.DecoderResultProvider
    public void setDecoderResult(DecoderResult decoderResult) {
        if (decoderResult != null) {
            this.a = decoderResult;
            return;
        }
        throw new NullPointerException("decoderResult");
    }
}
