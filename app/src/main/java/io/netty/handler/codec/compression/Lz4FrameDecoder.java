package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import net.jpountz.xxhash.XXHashFactory;

/* loaded from: classes4.dex */
public class Lz4FrameDecoder extends ByteToMessageDecoder {
    private a a;
    private LZ4FastDecompressor c;
    private Checksum d;
    private int e;
    private int f;
    private int g;
    private int h;

    /* loaded from: classes4.dex */
    private enum a {
        INIT_BLOCK,
        DECOMPRESS_DATA,
        FINISHED,
        CORRUPTED
    }

    public Lz4FrameDecoder() {
        this(false);
    }

    public Lz4FrameDecoder(boolean z) {
        this(LZ4Factory.fastestInstance(), z);
    }

    public Lz4FrameDecoder(LZ4Factory lZ4Factory, boolean z) {
        this(lZ4Factory, z ? XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum() : null);
    }

    public Lz4FrameDecoder(LZ4Factory lZ4Factory, Checksum checksum) {
        this.a = a.INIT_BLOCK;
        if (lZ4Factory != null) {
            this.c = lZ4Factory.fastDecompressor();
            this.d = checksum;
            return;
        }
        throw new NullPointerException("factory");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuf byteBuf2;
        Throwable th;
        ByteBuf byteBuf3;
        byte[] bArr;
        int i;
        int i2;
        byte[] bArr2;
        LZ4Exception e;
        try {
            switch (this.a) {
                case INIT_BLOCK:
                    if (byteBuf.readableBytes() >= 21) {
                        if (byteBuf.readLong() == 5501767354678207339L) {
                            byte readByte = byteBuf.readByte();
                            int i3 = (readByte & 15) + 10;
                            int i4 = readByte & 240;
                            int reverseBytes = Integer.reverseBytes(byteBuf.readInt());
                            if (reverseBytes < 0 || reverseBytes > 33554432) {
                                throw new DecompressionException(String.format("invalid compressedLength: %d (expected: 0-%d)", Integer.valueOf(reverseBytes), 33554432));
                            }
                            int reverseBytes2 = Integer.reverseBytes(byteBuf.readInt());
                            int i5 = 1 << i3;
                            if (reverseBytes2 < 0 || reverseBytes2 > i5) {
                                throw new DecompressionException(String.format("invalid decompressedLength: %d (expected: 0-%d)", Integer.valueOf(reverseBytes2), Integer.valueOf(i5)));
                            }
                            if ((reverseBytes2 == 0 && reverseBytes != 0) || ((reverseBytes2 != 0 && reverseBytes == 0) || (i4 == 16 && reverseBytes2 != reverseBytes))) {
                                throw new DecompressionException(String.format("stream corrupted: compressedLength(%d) and decompressedLength(%d) mismatch", Integer.valueOf(reverseBytes), Integer.valueOf(reverseBytes2)));
                            }
                            int reverseBytes3 = Integer.reverseBytes(byteBuf.readInt());
                            if (reverseBytes2 != 0 || reverseBytes != 0) {
                                this.e = i4;
                                this.f = reverseBytes;
                                this.g = reverseBytes2;
                                this.h = reverseBytes3;
                                this.a = a.DECOMPRESS_DATA;
                                break;
                            } else if (reverseBytes3 == 0) {
                                this.a = a.FINISHED;
                                this.c = null;
                                this.d = null;
                                return;
                            } else {
                                throw new DecompressionException("stream corrupted: checksum error");
                            }
                        } else {
                            throw new DecompressionException("unexpected block identifier");
                        }
                    } else {
                        return;
                    }
                case DECOMPRESS_DATA:
                    break;
                case FINISHED:
                case CORRUPTED:
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    return;
                default:
                    throw new IllegalStateException();
            }
            int i6 = this.e;
            int i7 = this.f;
            int i8 = this.g;
            int i9 = this.h;
            if (byteBuf.readableBytes() >= i7) {
                int readerIndex = byteBuf.readerIndex();
                ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(i8, i8);
                byte[] array = heapBuffer.array();
                int arrayOffset = heapBuffer.arrayOffset() + heapBuffer.writerIndex();
                try {
                    if (i6 == 16) {
                        i = arrayOffset;
                        bArr = array;
                        byteBuf3 = heapBuffer;
                        byteBuf.getBytes(readerIndex, bArr, i, i8);
                    } else if (i6 == 32) {
                        try {
                            if (byteBuf.hasArray()) {
                                byte[] array2 = byteBuf.array();
                                i2 = byteBuf.arrayOffset() + readerIndex;
                                bArr2 = array2;
                            } else {
                                byte[] bArr3 = new byte[i7];
                                byteBuf.getBytes(readerIndex, bArr3);
                                bArr2 = bArr3;
                                i2 = 0;
                            }
                            try {
                                i = arrayOffset;
                                bArr = array;
                                byteBuf3 = heapBuffer;
                            } catch (LZ4Exception e2) {
                                e = e2;
                            }
                            try {
                                int decompress = this.c.decompress(bArr2, i2, array, i, i8);
                                if (i7 != decompress) {
                                    throw new DecompressionException(String.format("stream corrupted: compressedLength(%d) and actual length(%d) mismatch", Integer.valueOf(i7), Integer.valueOf(decompress)));
                                }
                            } catch (LZ4Exception e3) {
                                e = e3;
                                throw new DecompressionException((Throwable) e);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            byteBuf2 = heapBuffer;
                            byteBuf2.release();
                            throw th;
                        }
                    } else {
                        throw new DecompressionException(String.format("unexpected blockType: %d (expected: %d or %d)", Integer.valueOf(i6), 16, 32));
                    }
                    Checksum checksum = this.d;
                    if (checksum != null) {
                        checksum.reset();
                        checksum.update(bArr, i, i8);
                        int value = (int) checksum.getValue();
                        if (value != i9) {
                            throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", Integer.valueOf(value), Integer.valueOf(i9)));
                        }
                    }
                    byteBuf2 = byteBuf3;
                    try {
                        byteBuf2.writerIndex(byteBuf3.writerIndex() + i8);
                        list.add(byteBuf2);
                        byteBuf.skipBytes(i7);
                        this.a = a.INIT_BLOCK;
                    } catch (Throwable th3) {
                        th = th3;
                        byteBuf2.release();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    byteBuf2 = channelHandlerContext;
                }
            }
        } catch (Exception e4) {
            this.a = a.CORRUPTED;
            throw e4;
        }
    }

    public boolean isClosed() {
        return this.a == a.FINISHED;
    }
}
