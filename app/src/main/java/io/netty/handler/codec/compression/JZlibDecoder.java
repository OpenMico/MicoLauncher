package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/* loaded from: classes4.dex */
public class JZlibDecoder extends ZlibDecoder {
    private final Inflater a;
    private byte[] c;
    private volatile boolean d;

    public JZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }

    public JZlibDecoder(ZlibWrapper zlibWrapper) {
        this.a = new Inflater();
        if (zlibWrapper != null) {
            int init = this.a.init(o.a(zlibWrapper));
            if (init != 0) {
                o.a(this.a, "initialization failure", init);
                return;
            }
            return;
        }
        throw new NullPointerException("wrapper");
    }

    public JZlibDecoder(byte[] bArr) {
        this.a = new Inflater();
        if (bArr != null) {
            this.c = bArr;
            int inflateInit = this.a.inflateInit(JZlib.W_ZLIB);
            if (inflateInit != 0) {
                o.a(this.a, "initialization failure", inflateInit);
                return;
            }
            return;
        }
        throw new NullPointerException("dictionary");
    }

    @Override // io.netty.handler.codec.compression.ZlibDecoder
    public boolean isClosed() {
        return this.d;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.d) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            try {
                this.a.avail_in = readableBytes;
                if (byteBuf.hasArray()) {
                    this.a.next_in = byteBuf.array();
                    this.a.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
                } else {
                    byte[] bArr = new byte[readableBytes];
                    byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                    this.a.next_in = bArr;
                    this.a.next_in_index = 0;
                }
                int i = this.a.next_in_index;
                int i2 = readableBytes << 1;
                ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(i2);
                while (true) {
                    this.a.avail_out = i2;
                    heapBuffer.ensureWritable(i2);
                    this.a.next_out = heapBuffer.array();
                    this.a.next_out_index = heapBuffer.arrayOffset() + heapBuffer.writerIndex();
                    int i3 = this.a.next_out_index;
                    int inflate = this.a.inflate(2);
                    int i4 = this.a.next_out_index - i3;
                    if (i4 > 0) {
                        heapBuffer.writerIndex(heapBuffer.writerIndex() + i4);
                    }
                    if (inflate != -5) {
                        switch (inflate) {
                            case 0:
                                break;
                            case 1:
                                this.d = true;
                                this.a.inflateEnd();
                                break;
                            case 2:
                                if (this.c != null) {
                                    int inflateSetDictionary = this.a.inflateSetDictionary(this.c, this.c.length);
                                    if (inflateSetDictionary == 0) {
                                        break;
                                    } else {
                                        o.a(this.a, "failed to set the dictionary", inflateSetDictionary);
                                        break;
                                    }
                                } else {
                                    o.a(this.a, "decompression failure", inflate);
                                    continue;
                                }
                            default:
                                o.a(this.a, "decompression failure", inflate);
                                continue;
                        }
                    } else if (this.a.avail_in <= 0) {
                    }
                }
                byteBuf.skipBytes(this.a.next_in_index - i);
                if (heapBuffer.isReadable()) {
                    list.add(heapBuffer);
                } else {
                    heapBuffer.release();
                }
            } finally {
                Inflater inflater = this.a;
                inflater.next_in = null;
                inflater.next_out = null;
            }
        }
    }
}
