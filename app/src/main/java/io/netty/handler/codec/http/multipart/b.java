package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.AbstractReferenceCounted;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: InternalAttribute.java */
/* loaded from: classes4.dex */
final class b extends AbstractReferenceCounted implements InterfaceHttpData {
    private final List<ByteBuf> a = new ArrayList();
    private final Charset b;
    private int c;

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public String getName() {
        return "InternalAttribute";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Charset charset) {
        this.b = charset;
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpData
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.InternalAttribute;
    }

    public void a(String str) {
        if (str != null) {
            ByteBuf copiedBuffer = Unpooled.copiedBuffer(str, this.b);
            this.a.add(copiedBuffer);
            this.c += copiedBuffer.readableBytes();
            return;
        }
        throw new NullPointerException(com.xiaomi.onetrack.api.b.p);
    }

    public void a(String str, int i) {
        if (str != null) {
            ByteBuf copiedBuffer = Unpooled.copiedBuffer(str, this.b);
            ByteBuf byteBuf = this.a.set(i, copiedBuffer);
            if (byteBuf != null) {
                this.c -= byteBuf.readableBytes();
                byteBuf.release();
            }
            this.c += copiedBuffer.readableBytes();
            return;
        }
        throw new NullPointerException(com.xiaomi.onetrack.api.b.p);
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        return getName().equalsIgnoreCase(((b) obj).getName());
    }

    /* renamed from: a */
    public int compareTo(InterfaceHttpData interfaceHttpData) {
        if (interfaceHttpData instanceof b) {
            return a((b) interfaceHttpData);
        }
        throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + interfaceHttpData.getHttpDataType());
    }

    public int a(b bVar) {
        return getName().compareToIgnoreCase(bVar.getName());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ByteBuf byteBuf : this.a) {
            sb.append(byteBuf.toString(this.b));
        }
        return sb.toString();
    }

    public int a() {
        return this.c;
    }

    public ByteBuf b() {
        return Unpooled.compositeBuffer().addComponents(this.a).writerIndex(a()).readerIndex(0);
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public InterfaceHttpData retain() {
        for (ByteBuf byteBuf : this.a) {
            byteBuf.retain();
        }
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public InterfaceHttpData retain(int i) {
        for (ByteBuf byteBuf : this.a) {
            byteBuf.retain(i);
        }
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public InterfaceHttpData touch() {
        for (ByteBuf byteBuf : this.a) {
            byteBuf.touch();
        }
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public InterfaceHttpData touch(Object obj) {
        for (ByteBuf byteBuf : this.a) {
            byteBuf.touch(obj);
        }
        return this;
    }
}
