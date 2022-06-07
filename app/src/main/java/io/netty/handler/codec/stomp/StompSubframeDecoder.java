package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.AppendableCharSequence;
import io.netty.util.internal.StringUtil;
import java.util.Locale;

/* loaded from: classes4.dex */
public class StompSubframeDecoder extends ReplayingDecoder<a> {
    private final int c;
    private final int d;
    private int e;
    private LastStompContentSubframe f;
    private long g;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        SKIP_CONTROL_CHARACTERS,
        READ_HEADERS,
        READ_CONTENT,
        FINALIZE_FRAME_READ,
        BAD_FRAME,
        INVALID_CHUNK
    }

    public StompSubframeDecoder() {
        this(1024, 8132);
    }

    public StompSubframeDecoder(int i, int i2) {
        super(a.SKIP_CONTROL_CHARACTERS);
        this.g = -1L;
        if (i <= 0) {
            throw new IllegalArgumentException("maxLineLength must be a positive integer: " + i);
        } else if (i2 > 0) {
            this.d = i2;
            this.c = i;
        } else {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + i2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006b A[Catch: Exception -> 0x0117, TryCatch #2 {Exception -> 0x0117, blocks: (B:17:0x0058, B:18:0x0066, B:20:0x006b, B:23:0x0072, B:25:0x0076, B:26:0x0078, B:28:0x0080, B:31:0x008a, B:33:0x009e, B:34:0x00ab, B:36:0x00b4, B:38:0x00c7, B:40:0x00cf, B:41:0x00d6, B:42:0x00df, B:44:0x00ee, B:45:0x00fa, B:47:0x0101, B:48:0x0105, B:49:0x010e), top: B:58:0x0058 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0101 A[Catch: Exception -> 0x0117, TryCatch #2 {Exception -> 0x0117, blocks: (B:17:0x0058, B:18:0x0066, B:20:0x006b, B:23:0x0072, B:25:0x0076, B:26:0x0078, B:28:0x0080, B:31:0x008a, B:33:0x009e, B:34:0x00ab, B:36:0x00b4, B:38:0x00c7, B:40:0x00cf, B:41:0x00d6, B:42:0x00df, B:44:0x00ee, B:45:0x00fa, B:47:0x0101, B:48:0x0105, B:49:0x010e), top: B:58:0x0058 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r6, io.netty.buffer.ByteBuf r7, java.util.List<java.lang.Object> r8) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 322
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.stomp.StompSubframeDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    private StompCommand a(ByteBuf byteBuf) {
        StompCommand stompCommand;
        String a2 = a(byteBuf, this.c);
        try {
            stompCommand = StompCommand.valueOf(a2);
        } catch (IllegalArgumentException unused) {
            stompCommand = null;
        }
        if (stompCommand == null) {
            try {
                stompCommand = StompCommand.valueOf(a2.toUpperCase(Locale.US));
            } catch (IllegalArgumentException unused2) {
            }
        }
        if (stompCommand != null) {
            return stompCommand;
        }
        throw new DecoderException("failed to read command from channel");
    }

    private a a(ByteBuf byteBuf, StompHeaders stompHeaders) {
        while (true) {
            String a2 = a(byteBuf, this.c);
            if (a2.isEmpty()) {
                break;
            }
            String[] split = StringUtil.split(a2, ':');
            if (split.length == 2) {
                stompHeaders.add((StompHeaders) split[0], split[1]);
            }
        }
        if (stompHeaders.contains(StompHeaders.CONTENT_LENGTH)) {
            this.g = a(stompHeaders, 0L);
            if (this.g == 0) {
                return a.FINALIZE_FRAME_READ;
            }
        }
        return a.READ_CONTENT;
    }

    private static long a(StompHeaders stompHeaders, long j) {
        long j2 = stompHeaders.getLong(StompHeaders.CONTENT_LENGTH, j);
        if (j2 >= 0) {
            return j2;
        }
        throw new DecoderException(((Object) StompHeaders.CONTENT_LENGTH) + " must be non-negative");
    }

    private static void b(ByteBuf byteBuf) {
        byte readByte = byteBuf.readByte();
        if (readByte != 0) {
            throw new IllegalStateException("unexpected byte in buffer " + ((int) readByte) + " while expecting NULL byte");
        }
    }

    private static void c(ByteBuf byteBuf) {
        while (true) {
            byte readByte = byteBuf.readByte();
            if (readByte != 13 && readByte != 10) {
                byteBuf.readerIndex(byteBuf.readerIndex() - 1);
                return;
            }
        }
    }

    private static String a(ByteBuf byteBuf, int i) {
        AppendableCharSequence appendableCharSequence = new AppendableCharSequence(128);
        int i2 = 0;
        while (true) {
            byte readByte = byteBuf.readByte();
            if (readByte == 13) {
                if (byteBuf.readByte() == 10) {
                    return appendableCharSequence.toString();
                }
            } else if (readByte == 10) {
                return appendableCharSequence.toString();
            } else {
                if (i2 < i) {
                    i2++;
                    appendableCharSequence.append((char) readByte);
                } else {
                    throw new TooLongFrameException("An STOMP line is larger than " + i + " bytes.");
                }
            }
        }
    }

    private void a() {
        checkpoint(a.SKIP_CONTROL_CHARACTERS);
        this.g = -1L;
        this.e = 0;
        this.f = null;
    }
}
