package io.netty.channel.sctp;

import com.sun.nio.sctp.MessageInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;

/* loaded from: classes4.dex */
public final class SctpMessage extends DefaultByteBufHolder {
    private final int a;
    private final int b;
    private final boolean c;
    private final MessageInfo d;

    public SctpMessage(int i, int i2, ByteBuf byteBuf) {
        this(i, i2, false, byteBuf);
    }

    public SctpMessage(int i, int i2, boolean z, ByteBuf byteBuf) {
        super(byteBuf);
        this.b = i;
        this.a = i2;
        this.c = z;
        this.d = null;
    }

    public SctpMessage(MessageInfo messageInfo, ByteBuf byteBuf) {
        super(byteBuf);
        if (messageInfo != null) {
            this.d = messageInfo;
            this.a = messageInfo.streamNumber();
            this.b = messageInfo.payloadProtocolID();
            this.c = messageInfo.isUnordered();
            return;
        }
        throw new NullPointerException("msgInfo");
    }

    public int streamIdentifier() {
        return this.a;
    }

    public int protocolIdentifier() {
        return this.b;
    }

    public boolean isUnordered() {
        return this.c;
    }

    public MessageInfo messageInfo() {
        return this.d;
    }

    public boolean isComplete() {
        MessageInfo messageInfo = this.d;
        if (messageInfo != null) {
            return messageInfo.isComplete();
        }
        return true;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SctpMessage sctpMessage = (SctpMessage) obj;
        if (this.b == sctpMessage.b && this.a == sctpMessage.a && this.c == sctpMessage.c) {
            return content().equals(sctpMessage.content());
        }
        return false;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder
    public int hashCode() {
        return (((((this.a * 31) + this.b) * 31) + (this.c ? 1231 : 1237)) * 31) + content().hashCode();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public SctpMessage copy() {
        return (SctpMessage) super.copy();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public SctpMessage duplicate() {
        return (SctpMessage) super.duplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public SctpMessage retainedDuplicate() {
        return (SctpMessage) super.retainedDuplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public SctpMessage replace(ByteBuf byteBuf) {
        MessageInfo messageInfo = this.d;
        if (messageInfo == null) {
            return new SctpMessage(this.b, this.a, this.c, byteBuf);
        }
        return new SctpMessage(messageInfo, byteBuf);
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SctpMessage retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SctpMessage retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SctpMessage touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SctpMessage touch(Object obj) {
        super.touch(obj);
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder
    public String toString() {
        return "SctpFrame{streamIdentifier=" + this.a + ", protocolIdentifier=" + this.b + ", unordered=" + this.c + ", data=" + contentToString() + '}';
    }
}
