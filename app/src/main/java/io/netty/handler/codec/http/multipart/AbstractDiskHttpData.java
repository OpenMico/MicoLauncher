package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public abstract class AbstractDiskHttpData extends AbstractHttpData {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(AbstractDiskHttpData.class);
    private File b;
    private boolean c;
    private FileChannel d;

    protected abstract boolean deleteOnExit();

    protected abstract String getBaseDirectory();

    protected abstract String getDiskFilename();

    protected abstract String getPostfix();

    protected abstract String getPrefix();

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public boolean isInMemory() {
        return false;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public HttpData touch() {
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.AbstractHttpData, io.netty.util.ReferenceCounted
    public HttpData touch(Object obj) {
        return this;
    }

    public AbstractDiskHttpData(String str, Charset charset, long j) {
        super(str, charset, j);
    }

    private File a() throws IOException {
        String str;
        File file;
        String diskFilename = getDiskFilename();
        if (diskFilename != null) {
            str = '_' + diskFilename;
        } else {
            str = getPostfix();
        }
        if (getBaseDirectory() == null) {
            file = File.createTempFile(getPrefix(), str);
        } else {
            file = File.createTempFile(getPrefix(), str, new File(getBaseDirectory()));
        }
        if (deleteOnExit()) {
            file.deleteOnExit();
        }
        return file;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(ByteBuf byteBuf) throws IOException {
        if (byteBuf != null) {
            try {
                this.size = byteBuf.readableBytes();
                checkSize(this.size);
                if (this.definedSize > 0 && this.definedSize < this.size) {
                    throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
                }
                if (this.b == null) {
                    this.b = a();
                }
                if (byteBuf.readableBytes() != 0) {
                    FileOutputStream fileOutputStream = new FileOutputStream(this.b);
                    FileChannel channel = fileOutputStream.getChannel();
                    ByteBuffer nioBuffer = byteBuf.nioBuffer();
                    int i = 0;
                    while (i < this.size) {
                        i += channel.write(nioBuffer);
                    }
                    byteBuf.readerIndex(byteBuf.readerIndex() + i);
                    channel.force(false);
                    fileOutputStream.close();
                    setCompleted();
                } else if (!this.b.createNewFile()) {
                    throw new IOException("file exists already: " + this.b);
                }
            } finally {
                byteBuf.release();
            }
        } else {
            throw new NullPointerException("buffer");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void addContent(ByteBuf byteBuf, boolean z) throws IOException {
        if (byteBuf != null) {
            try {
                int readableBytes = byteBuf.readableBytes();
                long j = readableBytes;
                checkSize(this.size + j);
                if (this.definedSize > 0 && this.definedSize < this.size + j) {
                    throw new IOException("Out of size: " + (this.size + j) + " > " + this.definedSize);
                }
                ByteBuffer nioBuffer = byteBuf.nioBufferCount() == 1 ? byteBuf.nioBuffer() : byteBuf.copy().nioBuffer();
                if (this.b == null) {
                    this.b = a();
                }
                if (this.d == null) {
                    this.d = new FileOutputStream(this.b).getChannel();
                }
                int i = 0;
                while (i < readableBytes) {
                    i += this.d.write(nioBuffer);
                }
                this.size += j;
                byteBuf.readerIndex(byteBuf.readerIndex() + i);
            } finally {
                byteBuf.release();
            }
        }
        if (z) {
            if (this.b == null) {
                this.b = a();
            }
            if (this.d == null) {
                this.d = new FileOutputStream(this.b).getChannel();
            }
            this.d.force(false);
            this.d.close();
            this.d = null;
            setCompleted();
        } else if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(File file) throws IOException {
        if (this.b != null) {
            delete();
        }
        this.b = file;
        this.size = file.length();
        checkSize(this.size);
        this.c = true;
        setCompleted();
    }

    /* JADX WARN: Finally extract failed */
    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void setContent(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            if (this.b != null) {
                delete();
            }
            this.b = a();
            FileOutputStream fileOutputStream = new FileOutputStream(this.b);
            try {
                FileChannel channel = fileOutputStream.getChannel();
                byte[] bArr = new byte[16384];
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                int read = inputStream.read(bArr);
                int i = 0;
                while (read > 0) {
                    wrap.position(read).flip();
                    i += channel.write(wrap);
                    checkSize(i);
                    read = inputStream.read(bArr);
                }
                channel.force(false);
                fileOutputStream.close();
                this.size = i;
                if (this.definedSize <= 0 || this.definedSize >= this.size) {
                    this.c = true;
                    setCompleted();
                    return;
                }
                if (!this.b.delete()) {
                    a.warn("Failed to delete: {}", this.b);
                }
                this.b = null;
                throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
            } catch (Throwable th) {
                fileOutputStream.close();
                throw th;
            }
        } else {
            throw new NullPointerException("inputStream");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public void delete() {
        FileChannel fileChannel = this.d;
        if (fileChannel != null) {
            try {
                fileChannel.force(false);
                this.d.close();
            } catch (IOException e) {
                a.warn("Failed to close a file.", (Throwable) e);
            }
            this.d = null;
        }
        if (!this.c) {
            File file = this.b;
            if (file != null && file.exists() && !this.b.delete()) {
                a.warn("Failed to delete: {}", this.b);
            }
            this.b = null;
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public byte[] get() throws IOException {
        File file = this.b;
        if (file == null) {
            return EmptyArrays.EMPTY_BYTES;
        }
        return a(file);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public ByteBuf getByteBuf() throws IOException {
        File file = this.b;
        if (file == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.wrappedBuffer(a(file));
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public ByteBuf getChunk(int i) throws IOException {
        File file = this.b;
        if (file == null || i == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (this.d == null) {
            this.d = new FileInputStream(file).getChannel();
        }
        ByteBuffer allocate = ByteBuffer.allocate(i);
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                break;
            }
            int read = this.d.read(allocate);
            if (read == -1) {
                this.d.close();
                this.d = null;
                break;
            }
            i2 += read;
        }
        if (i2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        allocate.flip();
        ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(allocate);
        wrappedBuffer.readerIndex(0);
        wrappedBuffer.writerIndex(i2);
        return wrappedBuffer;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public String getString() throws IOException {
        return getString(HttpConstants.DEFAULT_CHARSET);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public String getString(Charset charset) throws IOException {
        File file = this.b;
        if (file == null) {
            return "";
        }
        if (charset == null) {
            return new String(a(file), HttpConstants.DEFAULT_CHARSET.name());
        }
        return new String(a(file), charset.name());
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00d3  */
    @Override // io.netty.handler.codec.http.multipart.HttpData
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean renameTo(java.io.File r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.multipart.AbstractDiskHttpData.renameTo(java.io.File):boolean");
    }

    private static byte[] a(File file) throws IOException {
        long length = file.length();
        if (length <= 2147483647L) {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[(int) length];
            try {
                FileChannel channel = fileInputStream.getChannel();
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                for (int i = 0; i < length; i += channel.read(wrap)) {
                }
                return bArr;
            } finally {
                fileInputStream.close();
            }
        } else {
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpData
    public File getFile() throws IOException {
        return this.b;
    }
}
