package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/* loaded from: classes4.dex */
public class CompatibleObjectEncoder extends MessageToByteEncoder<Serializable> {
    private static final AttributeKey<ObjectOutputStream> a = AttributeKey.valueOf(CompatibleObjectEncoder.class, "OOS");
    private final int b;
    private int c;

    public CompatibleObjectEncoder() {
        this(16);
    }

    public CompatibleObjectEncoder(int i) {
        if (i >= 0) {
            this.b = i;
            return;
        }
        throw new IllegalArgumentException("resetInterval: " + i);
    }

    protected ObjectOutputStream newObjectOutputStream(OutputStream outputStream) throws Exception {
        return new ObjectOutputStream(outputStream);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf) throws Exception {
        Attribute attr = channelHandlerContext.attr(a);
        ObjectOutputStream objectOutputStream = (ObjectOutputStream) attr.get();
        if (objectOutputStream == null) {
            objectOutputStream = newObjectOutputStream(new ByteBufOutputStream(byteBuf));
            ObjectOutputStream objectOutputStream2 = (ObjectOutputStream) attr.setIfAbsent(objectOutputStream);
            if (objectOutputStream2 != null) {
                objectOutputStream = objectOutputStream2;
            }
        }
        synchronized (objectOutputStream) {
            if (this.b != 0) {
                this.c++;
                if (this.c % this.b == 0) {
                    objectOutputStream.reset();
                }
            }
            objectOutputStream.writeObject(serializable);
            objectOutputStream.flush();
        }
    }
}
