package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ServerChannel;

/* loaded from: classes4.dex */
public final class ChannelMatchers {
    private static final ChannelMatcher a = new ChannelMatcher() { // from class: io.netty.channel.group.ChannelMatchers.1
        @Override // io.netty.channel.group.ChannelMatcher
        public boolean matches(Channel channel) {
            return true;
        }
    };
    private static final ChannelMatcher b = isInstanceOf(ServerChannel.class);
    private static final ChannelMatcher c = isNotInstanceOf(ServerChannel.class);

    private ChannelMatchers() {
    }

    public static ChannelMatcher all() {
        return a;
    }

    public static ChannelMatcher isNot(Channel channel) {
        return invert(is(channel));
    }

    public static ChannelMatcher is(Channel channel) {
        return new c(channel);
    }

    public static ChannelMatcher isInstanceOf(Class<? extends Channel> cls) {
        return new a(cls);
    }

    public static ChannelMatcher isNotInstanceOf(Class<? extends Channel> cls) {
        return invert(isInstanceOf(cls));
    }

    public static ChannelMatcher isServerChannel() {
        return b;
    }

    public static ChannelMatcher isNonServerChannel() {
        return c;
    }

    public static ChannelMatcher invert(ChannelMatcher channelMatcher) {
        return new d(channelMatcher);
    }

    public static ChannelMatcher compose(ChannelMatcher... channelMatcherArr) {
        if (channelMatcherArr.length < 1) {
            throw new IllegalArgumentException("matchers must at least contain one element");
        } else if (channelMatcherArr.length == 1) {
            return channelMatcherArr[0];
        } else {
            return new b(channelMatcherArr);
        }
    }

    /* loaded from: classes4.dex */
    private static final class b implements ChannelMatcher {
        private final ChannelMatcher[] a;

        b(ChannelMatcher... channelMatcherArr) {
            this.a = channelMatcherArr;
        }

        @Override // io.netty.channel.group.ChannelMatcher
        public boolean matches(Channel channel) {
            for (ChannelMatcher channelMatcher : this.a) {
                if (!channelMatcher.matches(channel)) {
                    return false;
                }
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class d implements ChannelMatcher {
        private final ChannelMatcher a;

        d(ChannelMatcher channelMatcher) {
            this.a = channelMatcher;
        }

        @Override // io.netty.channel.group.ChannelMatcher
        public boolean matches(Channel channel) {
            return !this.a.matches(channel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class c implements ChannelMatcher {
        private final Channel a;

        c(Channel channel) {
            this.a = channel;
        }

        @Override // io.netty.channel.group.ChannelMatcher
        public boolean matches(Channel channel) {
            return this.a == channel;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a implements ChannelMatcher {
        private final Class<? extends Channel> a;

        a(Class<? extends Channel> cls) {
            this.a = cls;
        }

        @Override // io.netty.channel.group.ChannelMatcher
        public boolean matches(Channel channel) {
            return this.a.isInstance(channel);
        }
    }
}
