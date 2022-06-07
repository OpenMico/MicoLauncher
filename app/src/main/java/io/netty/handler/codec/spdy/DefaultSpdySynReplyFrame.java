package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSpdySynReplyFrame extends DefaultSpdyHeadersFrame implements SpdySynReplyFrame {
    public DefaultSpdySynReplyFrame(int i) {
        super(i);
    }

    public DefaultSpdySynReplyFrame(int i, boolean z) {
        super(i, z);
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame, io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdySynReplyFrame setStreamId(int i) {
        super.setStreamId(i);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame, io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdySynReplyFrame setLast(boolean z) {
        super.setLast(z);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame, io.netty.handler.codec.spdy.SpdyHeadersFrame
    public SpdySynReplyFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(last: ");
        sb.append(isLast());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Stream-ID = ");
        sb.append(streamId());
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Headers:");
        sb.append(StringUtil.NEWLINE);
        appendHeaders(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }
}
