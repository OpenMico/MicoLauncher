package io.netty.handler.codec.http2;

/* compiled from: DefaultHttp2HeaderTableListSize.java */
/* loaded from: classes4.dex */
class c {
    private int a = Integer.MAX_VALUE;

    public void maxHeaderListSize(int i) throws Http2Exception {
        if (i < 0) {
            this.a = Integer.MAX_VALUE;
        } else {
            this.a = i;
        }
    }

    public int maxHeaderListSize() {
        return this.a;
    }
}
