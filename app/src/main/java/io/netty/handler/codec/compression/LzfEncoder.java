package io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import com.ning.compress.lzf.LZFEncoder;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/* loaded from: classes4.dex */
public class LzfEncoder extends MessageToByteEncoder<ByteBuf> {
    private final ChunkEncoder a;
    private final BufferRecycler b;

    public LzfEncoder() {
        this(false, 65535);
    }

    public LzfEncoder(boolean z) {
        this(z, 65535);
    }

    public LzfEncoder(int i) {
        this(false, i);
    }

    public LzfEncoder(boolean z, int i) {
        super(false);
        if (i < 16 || i > 65535) {
            throw new IllegalArgumentException("totalLength: " + i + " (expected: 16-65535)");
        }
        this.a = z ? ChunkEncoderFactory.safeNonAllocatingInstance(i) : ChunkEncoderFactory.optimalNonAllocatingInstance(i);
        this.b = BufferRecycler.instance();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        byte[] bArr;
        int readableBytes = byteBuf.readableBytes();
        int readerIndex = byteBuf.readerIndex();
        int i = 0;
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            i = byteBuf.arrayOffset() + readerIndex;
            bArr = array;
        } else {
            byte[] allocInputBuffer = this.b.allocInputBuffer(readableBytes);
            byteBuf.getBytes(readerIndex, allocInputBuffer, 0, readableBytes);
            bArr = allocInputBuffer;
        }
        byteBuf2.ensureWritable(LZFEncoder.estimateMaxWorkspaceSize(readableBytes));
        byte[] array2 = byteBuf2.array();
        int arrayOffset = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
        byteBuf2.writerIndex(byteBuf2.writerIndex() + (LZFEncoder.appendEncoded(this.a, bArr, i, readableBytes, array2, arrayOffset) - arrayOffset));
        byteBuf.skipBytes(readableBytes);
        if (!byteBuf.hasArray()) {
            this.b.releaseInputBuffer(bArr);
        }
    }
}
