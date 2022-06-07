package io.netty.channel.oio;

import com.umeng.analytics.pro.ai;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes4.dex */
public abstract class OioByteStreamChannel extends AbstractOioByteChannel {
    private static final InputStream d = new InputStream() { // from class: io.netty.channel.oio.OioByteStreamChannel.1
        @Override // java.io.InputStream
        public int read() {
            return -1;
        }
    };
    private static final OutputStream e = new OutputStream() { // from class: io.netty.channel.oio.OioByteStreamChannel.2
        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            throw new ClosedChannelException();
        }
    };
    private InputStream f;
    private OutputStream g;
    private WritableByteChannel h;

    /* JADX INFO: Access modifiers changed from: protected */
    public OioByteStreamChannel(Channel channel) {
        super(channel);
    }

    protected final void activate(InputStream inputStream, OutputStream outputStream) {
        if (this.f != null) {
            throw new IllegalStateException("input was set already");
        } else if (this.g != null) {
            throw new IllegalStateException("output was set already");
        } else if (inputStream == null) {
            throw new NullPointerException(ai.ae);
        } else if (outputStream != null) {
            this.f = inputStream;
            this.g = outputStream;
        } else {
            throw new NullPointerException(ai.x);
        }
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        OutputStream outputStream;
        InputStream inputStream = this.f;
        return (inputStream == null || inputStream == d || (outputStream = this.g) == null || outputStream == e) ? false : true;
    }

    @Override // io.netty.channel.oio.AbstractOioByteChannel
    protected int available() {
        try {
            return this.f.available();
        } catch (IOException unused) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.oio.AbstractOioByteChannel
    public int doReadBytes(ByteBuf byteBuf) throws Exception {
        RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        recvBufAllocHandle.attemptedBytesRead(Math.max(1, Math.min(available(), byteBuf.maxWritableBytes())));
        return byteBuf.writeBytes(this.f, recvBufAllocHandle.attemptedBytesRead());
    }

    @Override // io.netty.channel.oio.AbstractOioByteChannel
    protected void doWriteBytes(ByteBuf byteBuf) throws Exception {
        OutputStream outputStream = this.g;
        if (outputStream != null) {
            byteBuf.readBytes(outputStream, byteBuf.readableBytes());
            return;
        }
        throw new NotYetConnectedException();
    }

    @Override // io.netty.channel.oio.AbstractOioByteChannel
    protected void doWriteFileRegion(FileRegion fileRegion) throws Exception {
        OutputStream outputStream = this.g;
        if (outputStream != null) {
            if (this.h == null) {
                this.h = Channels.newChannel(outputStream);
            }
            long j = 0;
            do {
                long transferTo = fileRegion.transferTo(this.h, j);
                if (transferTo == -1) {
                    a(fileRegion);
                    return;
                }
                j += transferTo;
            } while (j < fileRegion.count());
            return;
        }
        throw new NotYetConnectedException();
    }

    private static void a(FileRegion fileRegion) throws IOException {
        if (fileRegion.transferred() < fileRegion.count()) {
            throw new EOFException("Expected to be able to write " + fileRegion.count() + " bytes, but only wrote " + fileRegion.transferred());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public void doClose() throws Exception {
        InputStream inputStream = this.f;
        OutputStream outputStream = this.g;
        this.f = d;
        this.g = e;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }
}
