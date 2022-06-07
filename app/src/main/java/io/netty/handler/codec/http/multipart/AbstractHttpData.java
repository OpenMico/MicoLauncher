package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.AbstractReferenceCounted;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public abstract class AbstractHttpData extends AbstractReferenceCounted implements HttpData {
    private static final Pattern a = Pattern.compile("(?:^\\s+|\\s+$|\\n)");
    private static final Pattern b = Pattern.compile("[\\r\\t]");
    private final String c;
    protected long definedSize;
    private boolean e;
    protected long size;
    private Charset d = HttpConstants.DEFAULT_CHARSET;
    private long f = -1;

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public abstract HttpData touch();

    @Override // io.netty.util.ReferenceCounted
    public abstract HttpData touch(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractHttpData(String str, Charset charset, long j) {
        if (str != null) {
            String replaceAll = a.matcher(b.matcher(str).replaceAll(StringUtils.SPACE)).replaceAll("");
            if (!replaceAll.isEmpty()) {
                this.c = replaceAll;
                if (charset != null) {
                    setCharset(charset);
                }
                this.definedSize = j;
                return;
            }
            throw new IllegalArgumentException("empty name");
        }
        throw new NullPointerException("name");
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public long getMaxSize() {
        return this.f;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setMaxSize(long j) {
        this.f = j;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void checkSize(long j) throws IOException {
        long j2 = this.f;
        if (j2 >= 0 && j > j2) {
            throw new IOException("Size exceed allowed maximum capacity");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public String getName() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public boolean isCompleted() {
        return this.e;
    }

    protected void setCompleted() {
        this.e = true;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public Charset getCharset() {
        return this.d;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setCharset(Charset charset) {
        if (charset != null) {
            this.d = charset;
            return;
        }
        throw new NullPointerException("charset");
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public long length() {
        return this.size;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public long definedLength() {
        return this.definedSize;
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        try {
            return getByteBuf();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        delete();
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public HttpData retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public HttpData retain(int i) {
        super.retain(i);
        return this;
    }
}
