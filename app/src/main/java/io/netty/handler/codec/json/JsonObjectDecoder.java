package io.netty.handler.codec.json;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

/* loaded from: classes4.dex */
public class JsonObjectDecoder extends ByteToMessageDecoder {
    private int a;
    private int c;
    private int d;
    private boolean e;
    private final int f;
    private final boolean g;

    public JsonObjectDecoder() {
        this(1048576);
    }

    public JsonObjectDecoder(int i) {
        this(i, false);
    }

    public JsonObjectDecoder(boolean z) {
        this(1048576, z);
    }

    public JsonObjectDecoder(int i, boolean z) {
        if (i >= 1) {
            this.f = i;
            this.g = z;
            return;
        }
        throw new IllegalArgumentException("maxObjectLength must be a positive int");
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.d == -1) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        int i = this.c;
        int writerIndex = byteBuf.writerIndex();
        if (writerIndex <= this.f) {
            while (i < writerIndex) {
                byte b = byteBuf.getByte(i);
                int i2 = this.d;
                if (i2 == 1) {
                    a(b, byteBuf, i);
                    if (this.a == 0) {
                        int i3 = i + 1;
                        ByteBuf extractObject = extractObject(channelHandlerContext, byteBuf, byteBuf.readerIndex(), i3 - byteBuf.readerIndex());
                        if (extractObject != null) {
                            list.add(extractObject);
                        }
                        byteBuf.readerIndex(i3);
                        a();
                    }
                } else if (i2 == 2) {
                    a(b, byteBuf, i);
                    if (!this.e && ((this.a == 1 && b == 44) || (this.a == 0 && b == 93))) {
                        for (int readerIndex = byteBuf.readerIndex(); Character.isWhitespace(byteBuf.getByte(readerIndex)); readerIndex++) {
                            byteBuf.skipBytes(1);
                        }
                        int i4 = i - 1;
                        while (i4 >= byteBuf.readerIndex() && Character.isWhitespace(byteBuf.getByte(i4))) {
                            i4--;
                        }
                        ByteBuf extractObject2 = extractObject(channelHandlerContext, byteBuf, byteBuf.readerIndex(), (i4 + 1) - byteBuf.readerIndex());
                        if (extractObject2 != null) {
                            list.add(extractObject2);
                        }
                        byteBuf.readerIndex(i + 1);
                        if (b == 93) {
                            a();
                        }
                    }
                } else if (b == 123 || b == 91) {
                    a(b);
                    if (this.d == 2) {
                        byteBuf.skipBytes(1);
                    }
                } else if (Character.isWhitespace(b)) {
                    byteBuf.skipBytes(1);
                } else {
                    this.d = -1;
                    throw new CorruptedFrameException("invalid JSON received at byte position " + i + ": " + ByteBufUtil.hexDump(byteBuf));
                }
                i++;
            }
            if (byteBuf.readableBytes() == 0) {
                this.c = 0;
            } else {
                this.c = i;
            }
        } else {
            byteBuf.skipBytes(byteBuf.readableBytes());
            a();
            throw new TooLongFrameException("object length exceeds " + this.f + ": " + writerIndex + " bytes discarded");
        }
    }

    protected ByteBuf extractObject(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) {
        return byteBuf.retainedSlice(i, i2);
    }

    private void a(byte b, ByteBuf byteBuf, int i) {
        if ((b == 123 || b == 91) && !this.e) {
            this.a++;
        } else if ((b == 125 || b == 93) && !this.e) {
            this.a--;
        } else if (b == 34) {
            if (!this.e) {
                this.e = true;
                return;
            }
            int i2 = 0;
            for (int i3 = i - 1; i3 >= 0 && byteBuf.getByte(i3) == 92; i3--) {
                i2++;
            }
            if (i2 % 2 == 0) {
                this.e = false;
            }
        }
    }

    private void a(byte b) {
        this.a = 1;
        if (b != 91 || !this.g) {
            this.d = 1;
        } else {
            this.d = 2;
        }
    }

    private void a() {
        this.e = false;
        this.d = 0;
        this.a = 0;
    }
}
