package io.netty.handler.codec.http.multipart;

import com.xiaomi.onetrack.api.b;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class MemoryAttribute extends AbstractMemoryHttpData implements Attribute {
    public MemoryAttribute(String str) {
        this(str, HttpConstants.DEFAULT_CHARSET);
    }

    public MemoryAttribute(String str, long j) {
        this(str, j, HttpConstants.DEFAULT_CHARSET);
    }

    public MemoryAttribute(String str, Charset charset) {
        super(str, charset, 0L);
    }

    public MemoryAttribute(String str, long j, Charset charset) {
        super(str, charset, j);
    }

    public MemoryAttribute(String str, String str2) throws IOException {
        this(str, str2, HttpConstants.DEFAULT_CHARSET);
    }

    public MemoryAttribute(String str, String str2, Charset charset) throws IOException {
        super(str, charset, 0L);
        setValue(str2);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.Attribute;
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute
    public String getValue() {
        return getByteBuf().toString(getCharset());
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute
    public void setValue(String str) throws IOException {
        if (str != null) {
            byte[] bytes = str.getBytes(getCharset());
            checkSize(bytes.length);
            ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(bytes);
            if (this.definedSize > 0) {
                this.definedSize = wrappedBuffer.readableBytes();
            }
            setContent(wrappedBuffer);
            return;
        }
        throw new NullPointerException(b.p);
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractMemoryHttpData, io.netty.handler.codec.http.multipart.HttpData
    public void addContent(ByteBuf byteBuf, boolean z) throws IOException {
        long readableBytes = byteBuf.readableBytes();
        checkSize(this.size + readableBytes);
        if (this.definedSize > 0 && this.definedSize < this.size + readableBytes) {
            this.definedSize = this.size + readableBytes;
        }
        super.addContent(byteBuf, z);
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Attribute)) {
            return false;
        }
        return getName().equalsIgnoreCase(((Attribute) obj).getName());
    }

    public int compareTo(InterfaceHttpData interfaceHttpData) {
        if (interfaceHttpData instanceof Attribute) {
            return compareTo((Attribute) interfaceHttpData);
        }
        throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + interfaceHttpData.getHttpDataType());
    }

    public int compareTo(Attribute attribute) {
        return getName().compareToIgnoreCase(attribute.getName());
    }

    public String toString() {
        return getName() + '=' + getValue();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute copy() {
        ByteBuf content = content();
        return replace(content != null ? content.copy() : null);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute duplicate() {
        ByteBuf content = content();
        return replace(content != null ? content.duplicate() : null);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute retainedDuplicate() {
        ByteBuf content = content();
        if (content == null) {
            return replace((ByteBuf) null);
        }
        ByteBuf retainedDuplicate = content.retainedDuplicate();
        try {
            return replace(retainedDuplicate);
        } catch (Throwable th) {
            retainedDuplicate.release();
            throw th;
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute replace(ByteBuf byteBuf) {
        MemoryAttribute memoryAttribute = new MemoryAttribute(getName());
        memoryAttribute.setCharset(getCharset());
        if (byteBuf != null) {
            try {
                memoryAttribute.setContent(byteBuf);
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        }
        return memoryAttribute;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public Attribute retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public Attribute retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractMemoryHttpData, io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public Attribute touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractMemoryHttpData, io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.ReferenceCounted
    public Attribute touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
