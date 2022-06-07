package io.netty.channel;

import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public class DefaultAddressedEnvelope<M, A extends SocketAddress> implements AddressedEnvelope<M, A> {
    private final M a;
    private final A b;
    private final A c;

    public DefaultAddressedEnvelope(M m, A a, A a2) {
        if (m == null) {
            throw new NullPointerException("message");
        } else if (a == null && a2 == null) {
            throw new NullPointerException("recipient and sender");
        } else {
            this.a = m;
            this.b = a2;
            this.c = a;
        }
    }

    public DefaultAddressedEnvelope(M m, A a) {
        this(m, a, null);
    }

    @Override // io.netty.channel.AddressedEnvelope
    public M content() {
        return this.a;
    }

    @Override // io.netty.channel.AddressedEnvelope
    public A sender() {
        return this.b;
    }

    @Override // io.netty.channel.AddressedEnvelope
    public A recipient() {
        return this.c;
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        M m = this.a;
        if (m instanceof ReferenceCounted) {
            return ((ReferenceCounted) m).refCnt();
        }
        return 1;
    }

    @Override // io.netty.channel.AddressedEnvelope, io.netty.util.ReferenceCounted
    public AddressedEnvelope<M, A> retain() {
        ReferenceCountUtil.retain(this.a);
        return this;
    }

    @Override // io.netty.channel.AddressedEnvelope, io.netty.util.ReferenceCounted
    public AddressedEnvelope<M, A> retain(int i) {
        ReferenceCountUtil.retain(this.a, i);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return ReferenceCountUtil.release(this.a);
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return ReferenceCountUtil.release(this.a, i);
    }

    @Override // io.netty.channel.AddressedEnvelope, io.netty.util.ReferenceCounted
    public AddressedEnvelope<M, A> touch() {
        ReferenceCountUtil.touch(this.a);
        return this;
    }

    @Override // io.netty.channel.AddressedEnvelope, io.netty.util.ReferenceCounted
    public AddressedEnvelope<M, A> touch(Object obj) {
        ReferenceCountUtil.touch(this.a, obj);
        return this;
    }

    public String toString() {
        if (this.b != null) {
            return StringUtil.simpleClassName(this) + '(' + this.b + " => " + this.c + ", " + this.a + ')';
        }
        return StringUtil.simpleClassName(this) + "(=> " + this.c + ", " + this.a + ')';
    }
}
