package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultSpdyHeadersFrame extends DefaultSpdyStreamFrame implements SpdyHeadersFrame {
    private boolean a;
    private boolean b;
    private final SpdyHeaders c;

    public DefaultSpdyHeadersFrame(int i) {
        this(i, true);
    }

    public DefaultSpdyHeadersFrame(int i, boolean z) {
        super(i);
        this.c = new DefaultSpdyHeaders(z);
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyHeadersFrame setStreamId(int i) {
        super.setStreamId(i);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyHeadersFrame setLast(boolean z) {
        super.setLast(z);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame
    public boolean isInvalid() {
        return this.a;
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame
    public SpdyHeadersFrame setInvalid() {
        this.a = true;
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame
    public boolean isTruncated() {
        return this.b;
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame
    public SpdyHeadersFrame setTruncated() {
        this.b = true;
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame
    public SpdyHeaders headers() {
        return this.c;
    }

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

    protected void appendHeaders(StringBuilder sb) {
        for (Map.Entry<CharSequence, CharSequence> entry : headers()) {
            sb.append("    ");
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue());
            sb.append(StringUtil.NEWLINE);
        }
    }
}
