package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class DiskFileUpload extends AbstractDiskHttpData implements FileUpload {
    public static String baseDirectory = null;
    public static boolean deleteOnExitTemporaryFile = true;
    public static final String postfix = ".tmp";
    public static final String prefix = "FUp_";
    private String a;
    private String b;
    private String c;

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData
    protected String getPostfix() {
        return postfix;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData
    protected String getPrefix() {
        return prefix;
    }

    public DiskFileUpload(String str, String str2, String str3, String str4, Charset charset, long j) {
        super(str, charset, j);
        setFilename(str2);
        setContentType(str3);
        setContentTransferEncoding(str4);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.FileUpload;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public String getFilename() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public void setFilename(String str) {
        if (str != null) {
            this.a = str;
            return;
        }
        throw new NullPointerException("filename");
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
        if (interfaceHttpData instanceof FileUpload) {
            return compareTo((FileUpload) interfaceHttpData);
        }
        throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + interfaceHttpData.getHttpDataType());
    }

    public int compareTo(FileUpload fileUpload) {
        int compareToIgnoreCase = getName().compareToIgnoreCase(fileUpload.getName());
        return compareToIgnoreCase != 0 ? compareToIgnoreCase : compareToIgnoreCase;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public void setContentType(String str) {
        if (str != null) {
            this.b = str;
            return;
        }
        throw new NullPointerException("contentType");
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public String getContentType() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public String getContentTransferEncoding() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http.multipart.FileUpload
    public void setContentTransferEncoding(String str) {
        this.c = str;
    }

    public String toString() {
        File file;
        String str;
        try {
            file = getFile();
        } catch (IOException unused) {
            file = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append((Object) HttpHeaderNames.CONTENT_DISPOSITION);
        sb.append(": ");
        sb.append((Object) HttpHeaderValues.FORM_DATA);
        sb.append("; ");
        sb.append((Object) HttpHeaderValues.NAME);
        sb.append("=\"");
        sb.append(getName());
        sb.append("\"; ");
        sb.append((Object) HttpHeaderValues.FILENAME);
        sb.append("=\"");
        sb.append(this.a);
        sb.append("\"\r\n");
        sb.append((Object) HttpHeaderNames.CONTENT_TYPE);
        sb.append(": ");
        sb.append(this.b);
        if (getCharset() != null) {
            str = "; " + ((Object) HttpHeaderValues.CHARSET) + '=' + getCharset().name() + "\r\n";
        } else {
            str = "\r\n";
        }
        sb.append(str);
        sb.append((Object) HttpHeaderNames.CONTENT_LENGTH);
        sb.append(": ");
        sb.append(length());
        sb.append("\r\n");
        sb.append("Completed: ");
        sb.append(isCompleted());
        sb.append("\r\nIsInMemory: ");
        sb.append(isInMemory());
        sb.append("\r\nRealFile: ");
        sb.append(file != null ? file.getAbsolutePath() : "null");
        sb.append(" DefaultDeleteAfter: ");
        sb.append(deleteOnExitTemporaryFile);
        return sb.toString();
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
        return new File(this.a).getName();
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public FileUpload copy() {
        ByteBuf content = content();
        return replace(content != null ? content.copy() : null);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public FileUpload duplicate() {
        ByteBuf content = content();
        return replace(content != null ? content.duplicate() : null);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    public FileUpload retainedDuplicate() {
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
    public FileUpload replace(ByteBuf byteBuf) {
        DiskFileUpload diskFileUpload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
        if (byteBuf != null) {
            try {
                diskFileUpload.setContent(byteBuf);
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        }
        return diskFileUpload;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FileUpload retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FileUpload retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData, io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FileUpload touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractDiskHttpData, io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.ReferenceCounted
    public FileUpload touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
