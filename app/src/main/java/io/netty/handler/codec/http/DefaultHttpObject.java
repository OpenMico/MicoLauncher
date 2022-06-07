package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;

/* loaded from: classes4.dex */
public class DefaultHttpObject implements HttpObject {
    private DecoderResult a = DecoderResult.SUCCESS;

    @Override // io.netty.handler.codec.DecoderResultProvider
    public DecoderResult decoderResult() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.HttpObject
    @Deprecated
    public DecoderResult getDecoderResult() {
        return decoderResult();
    }

    @Override // io.netty.handler.codec.DecoderResultProvider
    public void setDecoderResult(DecoderResult decoderResult) {
        if (decoderResult != null) {
            this.a = decoderResult;
            return;
        }
        throw new NullPointerException("decoderResult");
    }

    public int hashCode() {
        return 31 + this.a.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttpObject)) {
            return false;
        }
        return decoderResult().equals(((DefaultHttpObject) obj).decoderResult());
    }
}
