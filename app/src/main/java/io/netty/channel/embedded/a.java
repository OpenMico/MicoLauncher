package io.netty.channel.embedded;

import io.netty.channel.ChannelId;

/* compiled from: EmbeddedChannelId.java */
/* loaded from: classes4.dex */
final class a implements ChannelId {
    static final ChannelId a = new a();
    private static final long serialVersionUID = -251711922203466130L;

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "embedded";
    }

    private a() {
    }

    @Override // io.netty.channel.ChannelId
    public String asShortText() {
        return toString();
    }

    @Override // io.netty.channel.ChannelId
    public String asLongText() {
        return toString();
    }

    /* renamed from: a */
    public int compareTo(ChannelId channelId) {
        if (channelId instanceof a) {
            return 0;
        }
        return asLongText().compareTo(channelId.asLongText());
    }

    public boolean equals(Object obj) {
        return obj instanceof a;
    }
}
