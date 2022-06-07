package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSpdySynStreamFrame extends DefaultSpdyHeadersFrame implements SpdySynStreamFrame {
    private int a;
    private byte b;
    private boolean c;

    public DefaultSpdySynStreamFrame(int i, int i2, byte b) {
        this(i, i2, b, true);
    }

    public DefaultSpdySynStreamFrame(int i, int i2, byte b, boolean z) {
        super(i, z);
        setAssociatedStreamId(i2);
        setPriority(b);
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame, io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdySynStreamFrame setStreamId(int i) {
        super.setStreamId(i);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame, io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdySynStreamFrame setLast(boolean z) {
        super.setLast(z);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame, io.netty.handler.codec.spdy.SpdyHeadersFrame
    public SpdySynStreamFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdySynStreamFrame
    public int associatedStreamId() {
        return this.a;
    }

    @Override // io.netty.handler.codec.spdy.SpdySynStreamFrame
    public SpdySynStreamFrame setAssociatedStreamId(int i) {
        if (i >= 0) {
            this.a = i;
            return this;
        }
        throw new IllegalArgumentException("Associated-To-Stream-ID cannot be negative: " + i);
    }

    @Override // io.netty.handler.codec.spdy.SpdySynStreamFrame
    public byte priority() {
        return this.b;
    }

    @Override // io.netty.handler.codec.spdy.SpdySynStreamFrame
    public SpdySynStreamFrame setPriority(byte b) {
        if (b < 0 || b > 7) {
            throw new IllegalArgumentException("Priority must be between 0 and 7 inclusive: " + ((int) b));
        }
        this.b = b;
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdySynStreamFrame
    public boolean isUnidirectional() {
        return this.c;
    }

    @Override // io.netty.handler.codec.spdy.SpdySynStreamFrame
    public SpdySynStreamFrame setUnidirectional(boolean z) {
        this.c = z;
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(last: ");
        sb.append(isLast());
        sb.append("; unidirectional: ");
        sb.append(isUnidirectional());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Stream-ID = ");
        sb.append(streamId());
        sb.append(StringUtil.NEWLINE);
        if (this.a != 0) {
            sb.append("--> Associated-To-Stream-ID = ");
            sb.append(associatedStreamId());
            sb.append(StringUtil.NEWLINE);
        }
        sb.append("--> Priority = ");
        sb.append((int) priority());
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Headers:");
        sb.append(StringUtil.NEWLINE);
        appendHeaders(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }
}
