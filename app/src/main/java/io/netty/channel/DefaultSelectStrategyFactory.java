package io.netty.channel;

/* loaded from: classes4.dex */
public final class DefaultSelectStrategyFactory implements SelectStrategyFactory {
    public static final SelectStrategyFactory INSTANCE = new DefaultSelectStrategyFactory();

    private DefaultSelectStrategyFactory() {
    }

    @Override // io.netty.channel.SelectStrategyFactory
    public SelectStrategy newSelectStrategy() {
        return d.a;
    }
}
