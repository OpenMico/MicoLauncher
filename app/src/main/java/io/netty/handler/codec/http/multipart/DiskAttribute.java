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
public class DiskAttribute extends AbstractDiskHttpData implements Attribute {
    public static String baseDirectory = null;
    public static boolean deleteOnExitTemporaryFile = true;
    public static final String postfix = ".att";
    public static final String prefix = "Attr_";

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData
    protected String getPostfix() {
        return postfix;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData
    protected String getPrefix() {
        return prefix;
    }

    public DiskAttribute(String str) {
        this(str, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String str, long j) {
        this(str, j, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String str, Charset charset) {
        super(str, charset, 0L);
    }

    public DiskAttribute(String str, long j, Charset charset) {
        super(str, charset, j);
    }

    public DiskAttribute(String str, String str2) throws IOException {
        this(str, str2, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String str, String str2, Charset charset) throws IOException {
        super(str, charset, 0L);
        setValue(str2);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.Attribute;
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute
    public String getValue() throws IOException {
        return new String(get(), getCharset());
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

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData, io.netty.handler.codec.http.multipart.HttpData
    public void addContent(ByteBuf byteBuf, boolean z) throws IOException {
        long readableBytes = this.size + byteBuf.readableBytes();
        checkSize(readableBytes);
        if (this.definedSize > 0 && this.definedSize < readableBytes) {
            this.definedSize = readableBytes;
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
        try {
            return getName() + '=' + getValue();
        } catch (IOException e) {
            return getName() + '=' + e;
        }
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData
    protected boolean deleteOnExit() {
        return deleteOnExitTemporaryFile;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData
    protected String getBaseDirectory() {
        return baseDirectory;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData
    protected String getDiskFilename() {
        return getName() + postfix;
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
        DiskAttribute diskAttribute = new DiskAttribute(getName());
        diskAttribute.setCharset(getCharset());
        if (byteBuf != null) {
            try {
                diskAttribute.setContent(byteBuf);
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        }
        return diskAttribute;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public Attribute retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public Attribute retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData, io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public Attribute touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData, io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.ReferenceCounted
    public Attribute touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
