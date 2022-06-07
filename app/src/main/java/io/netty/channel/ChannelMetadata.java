package io.netty.channel;

/* loaded from: classes4.dex */
public final class ChannelMetadata {
    private final boolean a;
    private final int b;

    public ChannelMetadata(boolean z) {
        this(z, 1);
    }

    public ChannelMetadata(boolean z, int i) {
        if (i > 0) {
            this.a = z;
            this.b = i;
            return;
        }
        throw new IllegalArgumentException("defaultMaxMessagesPerRead: " + i + " (expected > 0)");
    }

    public boolean hasDisconnect() {
        return this.a;
    }

    public int defaultMaxMessagesPerRead() {
        return this.b;
    }
}
