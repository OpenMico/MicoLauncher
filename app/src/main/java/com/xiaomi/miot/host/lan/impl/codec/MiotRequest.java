package com.xiaomi.miot.host.lan.impl.codec;

/* loaded from: classes2.dex */
public abstract class MiotRequest implements MiotMessage {
    private int id;

    public abstract byte[] encodeRequest();

    public abstract byte[] encodeResponse();

    public abstract MiotMethod getMethod();

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotMessage
    public final void setId(int i) {
        this.id = i;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotMessage
    public final int getId() {
        return this.id;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotMessage
    public final MiotMessageType getType() {
        return MiotMessageType.REQUEST;
    }
}
