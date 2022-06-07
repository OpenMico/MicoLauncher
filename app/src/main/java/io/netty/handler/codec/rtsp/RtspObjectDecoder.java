package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;

@Deprecated
/* loaded from: classes4.dex */
public abstract class RtspObjectDecoder extends HttpObjectDecoder {
    protected RtspObjectDecoder() {
        this(4096, 8192, 8192);
    }

    protected RtspObjectDecoder(int i, int i2, int i3) {
        super(i, i2, i3 * 2, false);
    }

    protected RtspObjectDecoder(int i, int i2, int i3, boolean z) {
        super(i, i2, i3 * 2, false, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http.HttpObjectDecoder
    public boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
        boolean isContentAlwaysEmpty = super.isContentAlwaysEmpty(httpMessage);
        if (!isContentAlwaysEmpty && httpMessage.headers().contains(RtspHeaderNames.CONTENT_LENGTH)) {
            return isContentAlwaysEmpty;
        }
        return true;
    }
}
