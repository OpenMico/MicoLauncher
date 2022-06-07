package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public abstract class AbstractMemoryHttpData extends AbstractHttpData {
    private ByteBuf a;
    private int b;

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public boolean isInMemory() {
        return true;
    }

    public AbstractMemoryHttpData(String str, Charset charset, long j) {
        super(str, charset, j);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(ByteBuf byteBuf) throws IOException {
        if (byteBuf != null) {
            long readableBytes = byteBuf.readableBytes();
            checkSize(readableBytes);
            if (this.definedSize <= 0 || this.definedSize >= readableBytes) {
                ByteBuf byteBuf2 = this.a;
                if (byteBuf2 != null) {
                    byteBuf2.release();
                }
                this.a = byteBuf;
                this.size = readableBytes;
                setCompleted();
                return;
            }
            throw new IOException("Out of size: " + readableBytes + " > " + this.definedSize);
        }
        throw new NullPointerException("buffer");
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            ByteBuf buffer = Unpooled.buffer();
            byte[] bArr = new byte[16384];
            int read = inputStream.read(bArr);
            int i = 0;
            while (read > 0) {
                buffer.writeBytes(bArr, 0, read);
                i += read;
                checkSize(i);
                read = inputStream.read(bArr);
            }
            this.size = i;
            if (this.definedSize <= 0 || this.definedSize >= this.size) {
                ByteBuf byteBuf = this.a;
                if (byteBuf != null) {
                    byteBuf.release();
                }
                this.a = buffer;
                setCompleted();
                return;
            }
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        }
        throw new NullPointerException("inputStream");
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void addContent(ByteBuf byteBuf, boolean z) throws IOException {
        if (byteBuf != null) {
            long readableBytes = byteBuf.readableBytes();
            checkSize(this.size + readableBytes);
            if (this.definedSize <= 0 || this.definedSize >= this.size + readableBytes) {
                this.size += readableBytes;
                ByteBuf byteBuf2 = this.a;
                if (byteBuf2 == null) {
                    this.a = byteBuf;
                } else if (byteBuf2 instanceof CompositeByteBuf) {
                    ((CompositeByteBuf) byteBuf2).addComponent(true, byteBuf);
                } else {
                    CompositeByteBuf compositeBuffer = Unpooled.compositeBuffer(Integer.MAX_VALUE);
                    compositeBuffer.addComponents(true, this.a, byteBuf);
                    this.a = compositeBuffer;
                }
            } else {
                throw new IOException("Out of size: " + (this.size + readableBytes) + " > " + this.definedSize);
            }
        }
        if (z) {
            setCompleted();
        } else if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(File file) throws IOException {
        if (file != null) {
            long length = file.length();
            if (length <= 2147483647L) {
                checkSize(length);
                FileInputStream fileInputStream = new FileInputStream(file);
                FileChannel channel = fileInputStream.getChannel();
                ByteBuffer wrap = ByteBuffer.wrap(new byte[(int) length]);
                for (int i = 0; i < length; i += channel.read(wrap)) {
                }
                channel.close();
                fileInputStream.close();
                wrap.flip();
                ByteBuf byteBuf = this.a;
                if (byteBuf != null) {
                    byteBuf.release();
                }
                this.a = Unpooled.wrappedBuffer(Integer.MAX_VALUE, wrap);
                this.size = length;
                setCompleted();
                return;
            }
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
        throw new NullPointerException("file");
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void delete() {
        ByteBuf byteBuf = this.a;
        if (byteBuf != null) {
            byteBuf.release();
            this.a = null;
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public byte[] get() {
        ByteBuf byteBuf = this.a;
        if (byteBuf == null) {
            return Unpooled.EMPTY_BUFFER.array();
        }
        byte[] bArr = new byte[byteBuf.readableBytes()];
        ByteBuf byteBuf2 = this.a;
        byteBuf2.getBytes(byteBuf2.readerIndex(), bArr);
        return bArr;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public String getString() {
        return getString(HttpConstants.DEFAULT_CHARSET);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public String getString(Charset charset) {
        if (this.a == null) {
            return "";
        }
        if (charset == null) {
            charset = HttpConstants.DEFAULT_CHARSET;
        }
        return this.a.toString(charset);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public ByteBuf getByteBuf() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public ByteBuf getChunk(int i) throws IOException {
        ByteBuf byteBuf = this.a;
        if (byteBuf == null || i == 0 || byteBuf.readableBytes() == 0) {
            this.b = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        int readableBytes = this.a.readableBytes() - this.b;
        if (readableBytes == 0) {
            this.b = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        if (readableBytes < i) {
            i = readableBytes;
        }
        ByteBuf retainedSlice = this.a.retainedSlice(this.b, i);
        this.b += i;
        return retainedSlice;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public boolean renameTo(File file) throws IOException {
        int i;
        if (file != null) {
            ByteBuf byteBuf = this.a;
            if (byteBuf != null) {
                int readableBytes = byteBuf.readableBytes();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                FileChannel channel = fileOutputStream.getChannel();
                if (this.a.nioBufferCount() == 1) {
                    ByteBuffer nioBuffer = this.a.nioBuffer();
                    i = 0;
                    while (i < readableBytes) {
                        i += channel.write(nioBuffer);
                    }
                } else {
                    ByteBuffer[] nioBuffers = this.a.nioBuffers();
                    i = 0;
                    while (i < readableBytes) {
                        i = (int) (i + channel.write(nioBuffers));
                    }
                }
                channel.force(false);
                channel.close();
                fileOutputStream.close();
                return i == readableBytes;
            } else if (file.createNewFile()) {
                return true;
            } else {
                throw new IOException("file exists already: " + file);
            }
        } else {
            throw new NullPointerException("dest");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public File getFile() throws IOException {
        throw new IOException("Not represented by a file");
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public HttpData touch() {
        return touch((Object) null);
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.ReferenceCounted
    public HttpData touch(Object obj) {
        ByteBuf byteBuf = this.a;
        if (byteBuf != null) {
            byteBuf.touch(obj);
        }
        return this;
    }
}
