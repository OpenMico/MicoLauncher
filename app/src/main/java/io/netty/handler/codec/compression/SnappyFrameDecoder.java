package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class SnappyFrameDecoder extends ByteToMessageDecoder {
    private static final byte[] a = {115, 78, 97, 80, 112, 89};
    private final Snappy c;
    private final boolean d;
    private boolean e;
    private boolean f;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        STREAM_IDENTIFIER,
        COMPRESSED_DATA,
        UNCOMPRESSED_DATA,
        RESERVED_UNSKIPPABLE,
        RESERVED_SKIPPABLE
    }

    public SnappyFrameDecoder() {
        this(false);
    }

    public SnappyFrameDecoder(boolean z) {
        this.c = new Snappy();
        this.d = z;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.f) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        try {
            int readerIndex = byteBuf.readerIndex();
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes >= 4) {
                short unsignedByte = byteBuf.getUnsignedByte(readerIndex);
                a a2 = a((byte) unsignedByte);
                int unsignedMediumLE = byteBuf.getUnsignedMediumLE(readerIndex + 1);
                switch (a2) {
                    case STREAM_IDENTIFIER:
                        if (unsignedMediumLE != a.length) {
                            throw new DecompressionException("Unexpected length of stream identifier: " + unsignedMediumLE);
                        } else if (readableBytes >= a.length + 4) {
                            byte[] bArr = new byte[unsignedMediumLE];
                            byteBuf.skipBytes(4).readBytes(bArr);
                            if (Arrays.equals(bArr, a)) {
                                this.e = true;
                                return;
                            }
                            throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
                        } else {
                            return;
                        }
                    case RESERVED_SKIPPABLE:
                        if (this.e) {
                            int i = unsignedMediumLE + 4;
                            if (readableBytes >= i) {
                                byteBuf.skipBytes(i);
                                return;
                            }
                            return;
                        }
                        throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                    case RESERVED_UNSKIPPABLE:
                        throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(unsignedByte));
                    case UNCOMPRESSED_DATA:
                        if (!this.e) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                        } else if (unsignedMediumLE > 65540) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                        } else if (readableBytes >= unsignedMediumLE + 4) {
                            byteBuf.skipBytes(4);
                            if (this.d) {
                                Snappy.a(byteBuf.readIntLE(), byteBuf, byteBuf.readerIndex(), unsignedMediumLE - 4);
                            } else {
                                byteBuf.skipBytes(4);
                            }
                            list.add(byteBuf.readRetainedSlice(unsignedMediumLE - 4));
                            return;
                        } else {
                            return;
                        }
                    case COMPRESSED_DATA:
                        if (!this.e) {
                            throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                        } else if (readableBytes >= unsignedMediumLE + 4) {
                            byteBuf.skipBytes(4);
                            int readIntLE = byteBuf.readIntLE();
                            ByteBuf buffer = channelHandlerContext.alloc().buffer(0);
                            if (this.d) {
                                int writerIndex = byteBuf.writerIndex();
                                byteBuf.writerIndex((byteBuf.readerIndex() + unsignedMediumLE) - 4);
                                this.c.decode(byteBuf, buffer);
                                byteBuf.writerIndex(writerIndex);
                                Snappy.a(readIntLE, buffer, 0, buffer.writerIndex());
                            } else {
                                this.c.decode(byteBuf.readSlice(unsignedMediumLE - 4), buffer);
                            }
                            list.add(buffer);
                            this.c.reset();
                            return;
                        } else {
                            return;
                        }
                    default:
                        return;
                }
            }
        } catch (Exception e) {
            this.f = true;
            throw e;
        }
    }

    private static a a(byte b) {
        if (b == 0) {
            return a.COMPRESSED_DATA;
        }
        if (b == 1) {
            return a.UNCOMPRESSED_DATA;
        }
        if (b == -1) {
            return a.STREAM_IDENTIFIER;
        }
        if ((b & 128) == 128) {
            return a.RESERVED_SKIPPABLE;
        }
        return a.RESERVED_UNSKIPPABLE;
    }
}
