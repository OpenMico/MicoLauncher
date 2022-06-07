package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/* loaded from: classes4.dex */
public class SpdyFrameDecoder {
    private final int a;
    private final int b;
    private final SpdyFrameDecoderDelegate c;
    private a d;
    private byte e;
    private int f;
    private int g;
    private int h;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        READ_COMMON_HEADER,
        READ_DATA_FRAME,
        READ_SYN_STREAM_FRAME,
        READ_SYN_REPLY_FRAME,
        READ_RST_STREAM_FRAME,
        READ_SETTINGS_FRAME,
        READ_SETTING,
        READ_PING_FRAME,
        READ_GOAWAY_FRAME,
        READ_HEADERS_FRAME,
        READ_WINDOW_UPDATE_FRAME,
        READ_HEADER_BLOCK,
        DISCARD_FRAME,
        FRAME_ERROR
    }

    private static boolean a(byte b, byte b2) {
        return (b & b2) != 0;
    }

    private static boolean a(int i, int i2, byte b, int i3) {
        switch (i2) {
            case 0:
                return i != 0;
            case 1:
                return i3 >= 10;
            case 2:
                return i3 >= 4;
            case 3:
                return b == 0 && i3 == 8;
            case 4:
                return i3 >= 4;
            case 5:
            default:
                return true;
            case 6:
                return i3 == 4;
            case 7:
                return i3 == 8;
            case 8:
                return i3 >= 4;
            case 9:
                return i3 == 8;
        }
    }

    public SpdyFrameDecoder(SpdyVersion spdyVersion, SpdyFrameDecoderDelegate spdyFrameDecoderDelegate) {
        this(spdyVersion, spdyFrameDecoderDelegate, 8192);
    }

    public SpdyFrameDecoder(SpdyVersion spdyVersion, SpdyFrameDecoderDelegate spdyFrameDecoderDelegate, int i) {
        if (spdyVersion == null) {
            throw new NullPointerException("spdyVersion");
        } else if (spdyFrameDecoderDelegate == null) {
            throw new NullPointerException("delegate");
        } else if (i > 0) {
            this.a = spdyVersion.a();
            this.c = spdyFrameDecoderDelegate;
            this.b = i;
            this.d = a.READ_COMMON_HEADER;
        } else {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + i);
        }
    }

    public void decode(ByteBuf byteBuf) {
        int i;
        int i2;
        while (true) {
            boolean z = false;
            boolean z2 = true;
            switch (this.d) {
                case READ_COMMON_HEADER:
                    if (byteBuf.readableBytes() >= 8) {
                        int readerIndex = byteBuf.readerIndex();
                        int i3 = readerIndex + 4;
                        int i4 = readerIndex + 5;
                        byteBuf.skipBytes(8);
                        if ((byteBuf.getByte(readerIndex) & 128) == 0) {
                            z2 = false;
                        }
                        if (z2) {
                            i = a.a(byteBuf, readerIndex) & 32767;
                            i2 = a.a(byteBuf, readerIndex + 2);
                            this.g = 0;
                        } else {
                            i = this.a;
                            this.g = a.c(byteBuf, readerIndex);
                            i2 = 0;
                        }
                        this.e = byteBuf.getByte(i3);
                        this.f = a.b(byteBuf, i4);
                        if (i == this.a) {
                            if (a(this.g, i2, this.e, this.f)) {
                                this.d = a(i2, this.f);
                                break;
                            } else {
                                this.d = a.FRAME_ERROR;
                                this.c.readFrameError("Invalid Frame Error");
                                break;
                            }
                        } else {
                            this.d = a.FRAME_ERROR;
                            this.c.readFrameError("Invalid SPDY Version");
                            break;
                        }
                    } else {
                        return;
                    }
                case READ_DATA_FRAME:
                    int i5 = this.f;
                    if (i5 != 0) {
                        int min = Math.min(this.b, i5);
                        if (byteBuf.readableBytes() >= min) {
                            ByteBuf buffer = byteBuf.alloc().buffer(min);
                            buffer.writeBytes(byteBuf, min);
                            this.f -= min;
                            if (this.f == 0) {
                                this.d = a.READ_COMMON_HEADER;
                            }
                            if (this.f == 0 && a(this.e, (byte) 1)) {
                                z = true;
                            }
                            this.c.readDataFrame(this.g, z, buffer);
                            break;
                        } else {
                            return;
                        }
                    } else {
                        this.d = a.READ_COMMON_HEADER;
                        this.c.readDataFrame(this.g, a(this.e, (byte) 1), Unpooled.buffer(0));
                        break;
                    }
                case READ_SYN_STREAM_FRAME:
                    if (byteBuf.readableBytes() >= 10) {
                        int readerIndex2 = byteBuf.readerIndex();
                        this.g = a.c(byteBuf, readerIndex2);
                        int c = a.c(byteBuf, readerIndex2 + 4);
                        byte b = (byte) ((byteBuf.getByte(readerIndex2 + 8) >> 5) & 7);
                        boolean a2 = a(this.e, (byte) 1);
                        boolean a3 = a(this.e, (byte) 2);
                        byteBuf.skipBytes(10);
                        this.f -= 10;
                        if (this.g != 0) {
                            this.d = a.READ_HEADER_BLOCK;
                            this.c.readSynStreamFrame(this.g, c, b, a2, a3);
                            break;
                        } else {
                            this.d = a.FRAME_ERROR;
                            this.c.readFrameError("Invalid SYN_STREAM Frame");
                            break;
                        }
                    } else {
                        return;
                    }
                case READ_SYN_REPLY_FRAME:
                    if (byteBuf.readableBytes() >= 4) {
                        this.g = a.c(byteBuf, byteBuf.readerIndex());
                        boolean a4 = a(this.e, (byte) 1);
                        byteBuf.skipBytes(4);
                        this.f -= 4;
                        if (this.g != 0) {
                            this.d = a.READ_HEADER_BLOCK;
                            this.c.readSynReplyFrame(this.g, a4);
                            break;
                        } else {
                            this.d = a.FRAME_ERROR;
                            this.c.readFrameError("Invalid SYN_REPLY Frame");
                            break;
                        }
                    } else {
                        return;
                    }
                case READ_RST_STREAM_FRAME:
                    if (byteBuf.readableBytes() >= 8) {
                        this.g = a.c(byteBuf, byteBuf.readerIndex());
                        int d = a.d(byteBuf, byteBuf.readerIndex() + 4);
                        byteBuf.skipBytes(8);
                        if (this.g != 0 && d != 0) {
                            this.d = a.READ_COMMON_HEADER;
                            this.c.readRstStreamFrame(this.g, d);
                            break;
                        } else {
                            this.d = a.FRAME_ERROR;
                            this.c.readFrameError("Invalid RST_STREAM Frame");
                            break;
                        }
                    } else {
                        return;
                    }
                case READ_SETTINGS_FRAME:
                    if (byteBuf.readableBytes() >= 4) {
                        boolean a5 = a(this.e, (byte) 1);
                        this.h = a.c(byteBuf, byteBuf.readerIndex());
                        byteBuf.skipBytes(4);
                        this.f -= 4;
                        int i6 = this.f;
                        if ((i6 & 7) != 0 || (i6 >> 3) != this.h) {
                            this.d = a.FRAME_ERROR;
                            this.c.readFrameError("Invalid SETTINGS Frame");
                            break;
                        } else {
                            this.d = a.READ_SETTING;
                            this.c.readSettingsFrame(a5);
                            break;
                        }
                    } else {
                        return;
                    }
                case READ_SETTING:
                    if (this.h != 0) {
                        if (byteBuf.readableBytes() >= 8) {
                            byte b2 = byteBuf.getByte(byteBuf.readerIndex());
                            int b3 = a.b(byteBuf, byteBuf.readerIndex() + 1);
                            int d2 = a.d(byteBuf, byteBuf.readerIndex() + 4);
                            boolean a6 = a(b2, (byte) 1);
                            boolean a7 = a(b2, (byte) 2);
                            byteBuf.skipBytes(8);
                            this.h--;
                            this.c.readSetting(b3, d2, a6, a7);
                            break;
                        } else {
                            return;
                        }
                    } else {
                        this.d = a.READ_COMMON_HEADER;
                        this.c.readSettingsEnd();
                        break;
                    }
                case READ_PING_FRAME:
                    if (byteBuf.readableBytes() >= 4) {
                        int d3 = a.d(byteBuf, byteBuf.readerIndex());
                        byteBuf.skipBytes(4);
                        this.d = a.READ_COMMON_HEADER;
                        this.c.readPingFrame(d3);
                        break;
                    } else {
                        return;
                    }
                case READ_GOAWAY_FRAME:
                    if (byteBuf.readableBytes() >= 8) {
                        int c2 = a.c(byteBuf, byteBuf.readerIndex());
                        int d4 = a.d(byteBuf, byteBuf.readerIndex() + 4);
                        byteBuf.skipBytes(8);
                        this.d = a.READ_COMMON_HEADER;
                        this.c.readGoAwayFrame(c2, d4);
                        break;
                    } else {
                        return;
                    }
                case READ_HEADERS_FRAME:
                    if (byteBuf.readableBytes() >= 4) {
                        this.g = a.c(byteBuf, byteBuf.readerIndex());
                        boolean a8 = a(this.e, (byte) 1);
                        byteBuf.skipBytes(4);
                        this.f -= 4;
                        if (this.g != 0) {
                            this.d = a.READ_HEADER_BLOCK;
                            this.c.readHeadersFrame(this.g, a8);
                            break;
                        } else {
                            this.d = a.FRAME_ERROR;
                            this.c.readFrameError("Invalid HEADERS Frame");
                            break;
                        }
                    } else {
                        return;
                    }
                case READ_WINDOW_UPDATE_FRAME:
                    if (byteBuf.readableBytes() >= 8) {
                        this.g = a.c(byteBuf, byteBuf.readerIndex());
                        int c3 = a.c(byteBuf, byteBuf.readerIndex() + 4);
                        byteBuf.skipBytes(8);
                        if (c3 != 0) {
                            this.d = a.READ_COMMON_HEADER;
                            this.c.readWindowUpdateFrame(this.g, c3);
                            break;
                        } else {
                            this.d = a.FRAME_ERROR;
                            this.c.readFrameError("Invalid WINDOW_UPDATE Frame");
                            break;
                        }
                    } else {
                        return;
                    }
                case READ_HEADER_BLOCK:
                    if (this.f != 0) {
                        if (byteBuf.isReadable()) {
                            int min2 = Math.min(byteBuf.readableBytes(), this.f);
                            ByteBuf buffer2 = byteBuf.alloc().buffer(min2);
                            buffer2.writeBytes(byteBuf, min2);
                            this.f -= min2;
                            this.c.readHeaderBlock(buffer2);
                            break;
                        } else {
                            return;
                        }
                    } else {
                        this.d = a.READ_COMMON_HEADER;
                        this.c.readHeaderBlockEnd();
                        break;
                    }
                case DISCARD_FRAME:
                    int min3 = Math.min(byteBuf.readableBytes(), this.f);
                    byteBuf.skipBytes(min3);
                    this.f -= min3;
                    if (this.f == 0) {
                        this.d = a.READ_COMMON_HEADER;
                        break;
                    } else {
                        return;
                    }
                case FRAME_ERROR:
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    return;
                default:
                    throw new Error("Shouldn't reach here.");
            }
        }
    }

    private static a a(int i, int i2) {
        switch (i) {
            case 0:
                return a.READ_DATA_FRAME;
            case 1:
                return a.READ_SYN_STREAM_FRAME;
            case 2:
                return a.READ_SYN_REPLY_FRAME;
            case 3:
                return a.READ_RST_STREAM_FRAME;
            case 4:
                return a.READ_SETTINGS_FRAME;
            case 5:
            default:
                if (i2 != 0) {
                    return a.DISCARD_FRAME;
                }
                return a.READ_COMMON_HEADER;
            case 6:
                return a.READ_PING_FRAME;
            case 7:
                return a.READ_GOAWAY_FRAME;
            case 8:
                return a.READ_HEADERS_FRAME;
            case 9:
                return a.READ_WINDOW_UPDATE_FRAME;
        }
    }
}
