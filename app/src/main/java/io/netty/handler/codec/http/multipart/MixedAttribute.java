package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class MixedAttribute implements Attribute {
    private Attribute a;
    private final long b;
    private long c;

    public MixedAttribute(String str, long j) {
        this(str, j, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String str, long j, long j2) {
        this(str, j, j2, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String str, long j, Charset charset) {
        this.c = -1L;
        this.b = j;
        this.a = new MemoryAttribute(str, charset);
    }

    public MixedAttribute(String str, long j, long j2, Charset charset) {
        this.c = -1L;
        this.b = j2;
        this.a = new MemoryAttribute(str, j, charset);
    }

    public MixedAttribute(String str, String str2, long j) {
        this(str, str2, j, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String str, String str2, long j, Charset charset) {
        this.c = -1L;
        this.b = j;
        if (str2.length() > this.b) {
            try {
                this.a = new DiskAttribute(str, str2, charset);
            } catch (IOException e) {
                try {
                    this.a = new MemoryAttribute(str, str2, charset);
                } catch (IOException unused) {
                    throw new IllegalArgumentException(e);
                }
            }
        } else {
            try {
                this.a = new MemoryAttribute(str, str2, charset);
            } catch (IOException e2) {
                throw new IllegalArgumentException(e2);
            }
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public long getMaxSize() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setMaxSize(long j) {
        this.c = j;
        this.a.setMaxSize(j);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void checkSize(long j) throws IOException {
        long j2 = this.c;
        if (j2 >= 0 && j > j2) {
            throw new IOException("Size exceed allowed maximum capacity");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void addContent(ByteBuf byteBuf, boolean z) throws IOException {
        Attribute attribute = this.a;
        if (attribute instanceof MemoryAttribute) {
            checkSize(attribute.length() + byteBuf.readableBytes());
            if (this.a.length() + byteBuf.readableBytes() > this.b) {
                DiskAttribute diskAttribute = new DiskAttribute(this.a.getName(), this.a.definedLength());
                diskAttribute.setMaxSize(this.c);
                if (((MemoryAttribute) this.a).getByteBuf() != null) {
                    diskAttribute.addContent(((MemoryAttribute) this.a).getByteBuf(), false);
                }
                this.a = diskAttribute;
            }
        }
        this.a.addContent(byteBuf, z);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void delete() {
        this.a.delete();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public byte[] get() throws IOException {
        return this.a.get();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public ByteBuf getByteBuf() throws IOException {
        return this.a.getByteBuf();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public Charset getCharset() {
        return this.a.getCharset();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public String getString() throws IOException {
        return this.a.getString();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public String getString(Charset charset) throws IOException {
        return this.a.getString(charset);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public boolean isCompleted() {
        return this.a.isCompleted();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public boolean isInMemory() {
        return this.a.isInMemory();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public long length() {
        return this.a.length();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public long definedLength() {
        return this.a.definedLength();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public boolean renameTo(File file) throws IOException {
        return this.a.renameTo(file);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setCharset(Charset charset) {
        this.a.setCharset(charset);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(ByteBuf byteBuf) throws IOException {
        checkSize(byteBuf.readableBytes());
        if (byteBuf.readableBytes() > this.b) {
            Attribute attribute = this.a;
            if (attribute instanceof MemoryAttribute) {
                this.a = new DiskAttribute(attribute.getName(), this.a.definedLength());
                this.a.setMaxSize(this.c);
            }
        }
        this.a.setContent(byteBuf);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(File file) throws IOException {
        checkSize(file.length());
        if (file.length() > this.b) {
            Attribute attribute = this.a;
            if (attribute instanceof MemoryAttribute) {
                this.a = new DiskAttribute(attribute.getName(), this.a.definedLength());
                this.a.setMaxSize(this.c);
            }
        }
        this.a.setContent(file);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(InputStream inputStream) throws IOException {
        Attribute attribute = this.a;
        if (attribute instanceof MemoryAttribute) {
            this.a = new DiskAttribute(attribute.getName(), this.a.definedLength());
            this.a.setMaxSize(this.c);
        }
        this.a.setContent(inputStream);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return this.a.getHttpDataType();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public String getName() {
        return this.a.getName();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public boolean equals(Object obj) {
        return this.a.equals(obj);
    }

    public int compareTo(InterfaceHttpData interfaceHttpData) {
        return this.a.compareTo(interfaceHttpData);
    }

    public String toString() {
        return "Mixed: " + this.a;
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute
    public String getValue() throws IOException {
        return this.a.getValue();
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute
    public void setValue(String str) throws IOException {
        if (str != null) {
            checkSize(str.getBytes().length);
        }
        this.a.setValue(str);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public ByteBuf getChunk(int i) throws IOException {
        return this.a.getChunk(i);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public File getFile() throws IOException {
        return this.a.getFile();
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute copy() {
        return this.a.copy();
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute duplicate() {
        return this.a.duplicate();
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute retainedDuplicate() {
        return this.a.retainedDuplicate();
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public Attribute replace(ByteBuf byteBuf) {
        return this.a.replace(byteBuf);
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        return this.a.content();
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Attribute retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Attribute retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Attribute touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.Attribute, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Attribute touch(Object obj) {
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return this.a.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.a.release(i);
    }
}
