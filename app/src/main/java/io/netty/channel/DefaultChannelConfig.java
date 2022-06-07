package io.netty.channel;

import com.umeng.analytics.pro.ai;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* loaded from: classes4.dex */
public class DefaultChannelConfig implements ChannelConfig {
    private static final MessageSizeEstimator a = DefaultMessageSizeEstimator.DEFAULT;
    private static final AtomicIntegerFieldUpdater<DefaultChannelConfig> b;
    private static final AtomicReferenceFieldUpdater<DefaultChannelConfig, WriteBufferWaterMark> c;
    protected final Channel channel;
    private volatile ByteBufAllocator d;
    private volatile RecvByteBufAllocator e;
    private volatile MessageSizeEstimator f;
    private volatile int g;
    private volatile int h;
    private volatile int i;
    private volatile boolean j;
    private volatile WriteBufferWaterMark k;

    protected void autoReadCleared() {
    }

    static {
        AtomicIntegerFieldUpdater<DefaultChannelConfig> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(DefaultChannelConfig.class, "autoRead");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(DefaultChannelConfig.class, ai.aA);
        }
        b = newAtomicIntegerFieldUpdater;
        AtomicReferenceFieldUpdater<DefaultChannelConfig, WriteBufferWaterMark> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(DefaultChannelConfig.class, "writeBufferWaterMark");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelConfig.class, WriteBufferWaterMark.class, "k");
        }
        c = newAtomicReferenceFieldUpdater;
    }

    public DefaultChannelConfig(Channel channel) {
        this(channel, new AdaptiveRecvByteBufAllocator());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DefaultChannelConfig(Channel channel, RecvByteBufAllocator recvByteBufAllocator) {
        this.d = ByteBufAllocator.DEFAULT;
        this.f = a;
        this.g = 30000;
        this.h = 16;
        this.i = 1;
        this.j = true;
        this.k = WriteBufferWaterMark.DEFAULT;
        a(recvByteBufAllocator, channel.metadata());
        this.channel = channel;
    }

    @Override // io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(null, ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.WRITE_BUFFER_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR);
    }

    protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> map, ChannelOption<?>... channelOptionArr) {
        if (map == null) {
            map = new IdentityHashMap<>();
        }
        for (ChannelOption<?> channelOption : channelOptionArr) {
            map.put(channelOption, getOption(channelOption));
        }
        return map;
    }

    @Override // io.netty.channel.ChannelConfig
    public boolean setOptions(Map<ChannelOption<?>, ?> map) {
        if (map != null) {
            boolean z = true;
            for (Map.Entry<ChannelOption<?>, ?> entry : map.entrySet()) {
                if (!setOption(entry.getKey(), entry.getValue())) {
                    z = false;
                }
            }
            return z;
        }
        throw new NullPointerException("options");
    }

    @Override // io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        } else if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            return (T) Integer.valueOf(getConnectTimeoutMillis());
        } else {
            if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
                return (T) Integer.valueOf(getMaxMessagesPerRead());
            }
            if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
                return (T) Integer.valueOf(getWriteSpinCount());
            }
            if (channelOption == ChannelOption.ALLOCATOR) {
                return (T) getAllocator();
            }
            if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
                return (T) getRecvByteBufAllocator();
            }
            if (channelOption == ChannelOption.AUTO_READ) {
                return (T) Boolean.valueOf(isAutoRead());
            }
            if (channelOption == ChannelOption.AUTO_CLOSE) {
                return (T) Boolean.valueOf(isAutoClose());
            }
            if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
                return (T) Integer.valueOf(getWriteBufferHighWaterMark());
            }
            if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
                return (T) Integer.valueOf(getWriteBufferLowWaterMark());
            }
            if (channelOption == ChannelOption.WRITE_BUFFER_WATER_MARK) {
                return (T) getWriteBufferWaterMark();
            }
            if (channelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
                return (T) getMessageSizeEstimator();
            }
            return null;
        }
    }

    @Override // io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            setConnectTimeoutMillis(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
            setMaxMessagesPerRead(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
            setWriteSpinCount(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.ALLOCATOR) {
            setAllocator((ByteBufAllocator) t);
            return true;
        } else if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
            setRecvByteBufAllocator((RecvByteBufAllocator) t);
            return true;
        } else if (channelOption == ChannelOption.AUTO_READ) {
            setAutoRead(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == ChannelOption.AUTO_CLOSE) {
            setAutoClose(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
            setWriteBufferHighWaterMark(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
            setWriteBufferLowWaterMark(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.WRITE_BUFFER_WATER_MARK) {
            setWriteBufferWaterMark((WriteBufferWaterMark) t);
            return true;
        } else if (channelOption != ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
            return false;
        } else {
            setMessageSizeEstimator((MessageSizeEstimator) t);
            return true;
        }
    }

    protected <T> void validate(ChannelOption<T> channelOption, T t) {
        if (channelOption != null) {
            channelOption.validate(t);
            return;
        }
        throw new NullPointerException("option");
    }

    @Override // io.netty.channel.ChannelConfig
    public int getConnectTimeoutMillis() {
        return this.g;
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setConnectTimeoutMillis(int i) {
        if (i >= 0) {
            this.g = i;
            return this;
        }
        throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", Integer.valueOf(i)));
    }

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    public int getMaxMessagesPerRead() {
        try {
            return ((MaxMessagesRecvByteBufAllocator) getRecvByteBufAllocator()).maxMessagesPerRead();
        } catch (ClassCastException e) {
            throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", e);
        }
    }

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    public ChannelConfig setMaxMessagesPerRead(int i) {
        try {
            ((MaxMessagesRecvByteBufAllocator) getRecvByteBufAllocator()).maxMessagesPerRead(i);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", e);
        }
    }

    @Override // io.netty.channel.ChannelConfig
    public int getWriteSpinCount() {
        return this.h;
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setWriteSpinCount(int i) {
        if (i > 0) {
            this.h = i;
            return this;
        }
        throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
    }

    @Override // io.netty.channel.ChannelConfig
    public ByteBufAllocator getAllocator() {
        return this.d;
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        if (byteBufAllocator != null) {
            this.d = byteBufAllocator;
            return this;
        }
        throw new NullPointerException("allocator");
    }

    @Override // io.netty.channel.ChannelConfig
    public <T extends RecvByteBufAllocator> T getRecvByteBufAllocator() {
        return (T) this.e;
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        this.e = (RecvByteBufAllocator) ObjectUtil.checkNotNull(recvByteBufAllocator, "allocator");
        return this;
    }

    private void a(RecvByteBufAllocator recvByteBufAllocator, ChannelMetadata channelMetadata) {
        if (recvByteBufAllocator instanceof MaxMessagesRecvByteBufAllocator) {
            ((MaxMessagesRecvByteBufAllocator) recvByteBufAllocator).maxMessagesPerRead(channelMetadata.defaultMaxMessagesPerRead());
        } else if (recvByteBufAllocator == null) {
            throw new NullPointerException("allocator");
        }
        this.e = recvByteBufAllocator;
    }

    @Override // io.netty.channel.ChannelConfig
    public boolean isAutoRead() {
        return this.i == 1;
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setAutoRead(boolean z) {
        boolean z2 = true;
        if (b.getAndSet(this, z ? 1 : 0) != 1) {
            z2 = false;
        }
        if (z && !z2) {
            this.channel.read();
        } else if (!z && z2) {
            autoReadCleared();
        }
        return this;
    }

    @Override // io.netty.channel.ChannelConfig
    public boolean isAutoClose() {
        return this.j;
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setAutoClose(boolean z) {
        this.j = z;
        return this;
    }

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    public int getWriteBufferHighWaterMark() {
        return this.k.high();
    }

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    public ChannelConfig setWriteBufferHighWaterMark(int i) {
        WriteBufferWaterMark writeBufferWaterMark;
        if (i >= 0) {
            do {
                writeBufferWaterMark = this.k;
                if (i < writeBufferWaterMark.low()) {
                    throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + writeBufferWaterMark.low() + "): " + i);
                }
            } while (!c.compareAndSet(this, writeBufferWaterMark, new WriteBufferWaterMark(writeBufferWaterMark.low(), i, false)));
            return this;
        }
        throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
    }

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    public int getWriteBufferLowWaterMark() {
        return this.k.low();
    }

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    public ChannelConfig setWriteBufferLowWaterMark(int i) {
        WriteBufferWaterMark writeBufferWaterMark;
        if (i >= 0) {
            do {
                writeBufferWaterMark = this.k;
                if (i > writeBufferWaterMark.high()) {
                    throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + writeBufferWaterMark.high() + "): " + i);
                }
            } while (!c.compareAndSet(this, writeBufferWaterMark, new WriteBufferWaterMark(i, writeBufferWaterMark.high(), false)));
            return this;
        }
        throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        this.k = (WriteBufferWaterMark) ObjectUtil.checkNotNull(writeBufferWaterMark, "writeBufferWaterMark");
        return this;
    }

    @Override // io.netty.channel.ChannelConfig
    public WriteBufferWaterMark getWriteBufferWaterMark() {
        return this.k;
    }

    @Override // io.netty.channel.ChannelConfig
    public MessageSizeEstimator getMessageSizeEstimator() {
        return this.f;
    }

    @Override // io.netty.channel.ChannelConfig
    public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        if (messageSizeEstimator != null) {
            this.f = messageSizeEstimator;
            return this;
        }
        throw new NullPointerException("estimator");
    }
}
