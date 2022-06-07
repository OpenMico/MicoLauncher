package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class MixedFileUpload implements FileUpload {
    private FileUpload a;
    private final long b;
    private final long c;
    private long d = -1;

    public MixedFileUpload(String str, String str2, String str3, String str4, Charset charset, long j, long j2) {
        this.b = j2;
        if (j > this.b) {
            this.a = new DiskFileUpload(str, str2, str3, str4, charset, j);
        } else {
            this.a = new MemoryFileUpload(str, str2, str3, str4, charset, j);
        }
        this.c = j;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public long getMaxSize() {
        return this.d;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setMaxSize(long j) {
        this.d = j;
        this.a.setMaxSize(j);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void checkSize(long j) throws IOException {
        long j2 = this.d;
        if (j2 >= 0 && j > j2) {
            throw new IOException("Size exceed allowed maximum capacity");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void addContent(ByteBuf byteBuf, boolean z) throws IOException {
        FileUpload fileUpload = this.a;
        if (fileUpload instanceof MemoryFileUpload) {
            checkSize(fileUpload.length() + byteBuf.readableBytes());
            if (this.a.length() + byteBuf.readableBytes() > this.b) {
                DiskFileUpload diskFileUpload = new DiskFileUpload(this.a.getName(), this.a.getFilename(), this.a.getContentType(), this.a.getContentTransferEncoding(), this.a.getCharset(), this.c);
                diskFileUpload.setMaxSize(this.d);
                ByteBuf byteBuf2 = this.a.getByteBuf();
                if (byteBuf2 != null && byteBuf2.isReadable()) {
                    diskFileUpload.addContent(byteBuf2.retain(), false);
                }
                this.a.release();
                this.a = diskFileUpload;
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

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public String getContentType() {
        return this.a.getContentType();
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public String getContentTransferEncoding() {
        return this.a.getContentTransferEncoding();
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public String getFilename() {
        return this.a.getFilename();
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
            FileUpload fileUpload = this.a;
            if (fileUpload instanceof MemoryFileUpload) {
                this.a = new DiskFileUpload(fileUpload.getName(), fileUpload.getFilename(), fileUpload.getContentType(), fileUpload.getContentTransferEncoding(), fileUpload.getCharset(), this.c);
                this.a.setMaxSize(this.d);
                fileUpload.release();
            }
        }
        this.a.setContent(byteBuf);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(File file) throws IOException {
        checkSize(file.length());
        if (file.length() > this.b) {
            FileUpload fileUpload = this.a;
            if (fileUpload instanceof MemoryFileUpload) {
                this.a = new DiskFileUpload(fileUpload.getName(), fileUpload.getFilename(), fileUpload.getContentType(), fileUpload.getContentTransferEncoding(), fileUpload.getCharset(), this.c);
                this.a.setMaxSize(this.d);
                fileUpload.release();
            }
        }
        this.a.setContent(file);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(InputStream inputStream) throws IOException {
        FileUpload fileUpload = this.a;
        if (fileUpload instanceof MemoryFileUpload) {
            this.a = new DiskFileUpload(fileUpload.getName(), this.a.getFilename(), this.a.getContentType(), this.a.getContentTransferEncoding(), this.a.getCharset(), this.c);
            this.a.setMaxSize(this.d);
            fileUpload.release();
        }
        this.a.setContent(inputStream);
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public void setContentType(String str) {
        this.a.setContentType(str);
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public void setContentTransferEncoding(String str) {
        this.a.setContentTransferEncoding(str);
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public void setFilename(String str) {
        this.a.setFilename(str);
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

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public ByteBuf getChunk(int i) throws IOException {
        return this.a.getChunk(i);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public File getFile() throws IOException {
        return this.a.getFile();
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public FileUpload copy() {
        return this.a.copy();
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public FileUpload duplicate() {
        return this.a.duplicate();
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public FileUpload retainedDuplicate() {
        return this.a.retainedDuplicate();
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public FileUpload replace(ByteBuf byteBuf) {
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

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FileUpload retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FileUpload retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FileUpload touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload, io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FileUpload touch(Object obj) {
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
