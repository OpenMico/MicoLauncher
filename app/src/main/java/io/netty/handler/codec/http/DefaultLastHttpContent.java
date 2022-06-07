package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultLastHttpContent extends DefaultHttpContent implements LastHttpContent {
    private final HttpHeaders a;
    private final boolean b;

    public DefaultLastHttpContent() {
        this(Unpooled.buffer(0));
    }

    public DefaultLastHttpContent(ByteBuf byteBuf) {
        this(byteBuf, true);
    }

    public DefaultLastHttpContent(ByteBuf byteBuf, boolean z) {
        super(byteBuf);
        this.a = new a(z);
        this.b = z;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public LastHttpContent copy() {
        return replace(content().copy());
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public LastHttpContent duplicate() {
        return replace(content().duplicate());
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public LastHttpContent retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public LastHttpContent replace(ByteBuf byteBuf) {
        DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(byteBuf, this.b);
        defaultLastHttpContent.trailingHeaders().set(trailingHeaders());
        return defaultLastHttpContent;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastHttpContent retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastHttpContent retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastHttpContent touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public LastHttpContent touch(Object obj) {
        super.touch(obj);
        return this;
    }

    @Override // io.netty.handler.codec.http.LastHttpContent
    public HttpHeaders trailingHeaders() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpContent
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(StringUtil.NEWLINE);
        a(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }

    private void a(StringBuilder sb) {
        Iterator<Map.Entry<String, String>> it = trailingHeaders().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            sb.append(next.getKey());
            sb.append(": ");
            sb.append(next.getValue());
            sb.append(StringUtil.NEWLINE);
        }
    }

    /* loaded from: classes4.dex */
    private static final class a extends DefaultHttpHeaders {
        private static final DefaultHeaders.NameValidator<CharSequence> b = new DefaultHeaders.NameValidator<CharSequence>() { // from class: io.netty.handler.codec.http.DefaultLastHttpContent.a.1
            /* renamed from: a */
            public void validateName(CharSequence charSequence) {
                DefaultHttpHeaders.a.validateName(charSequence);
                if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(charSequence) || HttpHeaderNames.TRANSFER_ENCODING.contentEqualsIgnoreCase(charSequence) || HttpHeaderNames.TRAILER.contentEqualsIgnoreCase(charSequence)) {
                    throw new IllegalArgumentException("prohibited trailing header: " + ((Object) charSequence));
                }
            }
        };

        a(boolean z) {
            super(z, z ? b : DefaultHeaders.NameValidator.NOT_NULL);
        }
    }
}
