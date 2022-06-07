package io.netty.handler.codec.memcache;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.AbstractReferenceCounted;

/* loaded from: classes4.dex */
public abstract class AbstractMemcacheObject extends AbstractReferenceCounted implements MemcacheObject {
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
        throw new NullPointerException("DecoderResult should not be null.");
    }
}
