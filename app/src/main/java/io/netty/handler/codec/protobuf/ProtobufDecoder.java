package io.netty.handler.codec.protobuf;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class ProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final boolean a;
    private final MessageLite b;
    private final ExtensionRegistryLite c;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        decode2(channelHandlerContext, byteBuf, (List<Object>) list);
    }

    static {
        boolean z = false;
        try {
            MessageLite.class.getDeclaredMethod("getParserForType", new Class[0]);
            z = true;
        } catch (Throwable unused) {
        }
        a = z;
    }

    public ProtobufDecoder(MessageLite messageLite) {
        this(messageLite, (ExtensionRegistry) null);
    }

    public ProtobufDecoder(MessageLite messageLite, ExtensionRegistry extensionRegistry) {
        this(messageLite, (ExtensionRegistryLite) extensionRegistry);
    }

    public ProtobufDecoder(MessageLite messageLite, ExtensionRegistryLite extensionRegistryLite) {
        if (messageLite != null) {
            this.b = messageLite.getDefaultInstanceForType();
            this.c = extensionRegistryLite;
            return;
        }
        throw new NullPointerException("prototype");
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bArr;
        int readableBytes = byteBuf.readableBytes();
        int i = 0;
        if (byteBuf.hasArray()) {
            bArr = byteBuf.array();
            i = byteBuf.arrayOffset() + byteBuf.readerIndex();
        } else {
            bArr = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), bArr, 0, readableBytes);
        }
        if (this.c == null) {
            if (a) {
                list.add(this.b.getParserForType().parseFrom(bArr, i, readableBytes));
            } else {
                list.add(this.b.newBuilderForType().mergeFrom(bArr, i, readableBytes).build());
            }
        } else if (a) {
            list.add(this.b.getParserForType().parseFrom(bArr, i, readableBytes, this.c));
        } else {
            list.add(this.b.newBuilderForType().mergeFrom(bArr, i, readableBytes, this.c).build());
        }
    }
}
