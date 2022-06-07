package io.netty.channel.rxtx;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.rxtx.RxtxChannelConfig;
import java.util.Map;

/* compiled from: DefaultRxtxChannelConfig.java */
/* loaded from: classes4.dex */
final class a extends DefaultChannelConfig implements RxtxChannelConfig {
    private volatile boolean b;
    private volatile boolean c;
    private volatile int g;
    private volatile int a = 115200;
    private volatile RxtxChannelConfig.Stopbits d = RxtxChannelConfig.Stopbits.STOPBITS_1;
    private volatile RxtxChannelConfig.Databits e = RxtxChannelConfig.Databits.DATABITS_8;
    private volatile RxtxChannelConfig.Paritybit f = RxtxChannelConfig.Paritybit.NONE;
    private volatile int h = 1000;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(RxtxChannel rxtxChannel) {
        super(rxtxChannel);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), RxtxChannelOption.BAUD_RATE, RxtxChannelOption.DTR, RxtxChannelOption.RTS, RxtxChannelOption.STOP_BITS, RxtxChannelOption.DATA_BITS, RxtxChannelOption.PARITY_BIT, RxtxChannelOption.WAIT_TIME);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == RxtxChannelOption.BAUD_RATE) {
            return (T) Integer.valueOf(getBaudrate());
        }
        if (channelOption == RxtxChannelOption.DTR) {
            return (T) Boolean.valueOf(isDtr());
        }
        if (channelOption == RxtxChannelOption.RTS) {
            return (T) Boolean.valueOf(isRts());
        }
        if (channelOption == RxtxChannelOption.STOP_BITS) {
            return (T) getStopbits();
        }
        if (channelOption == RxtxChannelOption.DATA_BITS) {
            return (T) getDatabits();
        }
        if (channelOption == RxtxChannelOption.PARITY_BIT) {
            return (T) getParitybit();
        }
        if (channelOption == RxtxChannelOption.WAIT_TIME) {
            return (T) Integer.valueOf(getWaitTimeMillis());
        }
        if (channelOption == RxtxChannelOption.READ_TIMEOUT) {
            return (T) Integer.valueOf(getReadTimeout());
        }
        return (T) super.getOption(channelOption);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == RxtxChannelOption.BAUD_RATE) {
            setBaudrate(((Integer) t).intValue());
            return true;
        } else if (channelOption == RxtxChannelOption.DTR) {
            setDtr(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == RxtxChannelOption.RTS) {
            setRts(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == RxtxChannelOption.STOP_BITS) {
            setStopbits((RxtxChannelConfig.Stopbits) t);
            return true;
        } else if (channelOption == RxtxChannelOption.DATA_BITS) {
            setDatabits((RxtxChannelConfig.Databits) t);
            return true;
        } else if (channelOption == RxtxChannelOption.PARITY_BIT) {
            setParitybit((RxtxChannelConfig.Paritybit) t);
            return true;
        } else if (channelOption == RxtxChannelOption.WAIT_TIME) {
            setWaitTimeMillis(((Integer) t).intValue());
            return true;
        } else if (channelOption != RxtxChannelOption.READ_TIMEOUT) {
            return super.setOption(channelOption, t);
        } else {
            setReadTimeout(((Integer) t).intValue());
            return true;
        }
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setBaudrate(int i) {
        this.a = i;
        return this;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setStopbits(RxtxChannelConfig.Stopbits stopbits) {
        this.d = stopbits;
        return this;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setDatabits(RxtxChannelConfig.Databits databits) {
        this.e = databits;
        return this;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setParitybit(RxtxChannelConfig.Paritybit paritybit) {
        this.f = paritybit;
        return this;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public int getBaudrate() {
        return this.a;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig.Stopbits getStopbits() {
        return this.d;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig.Databits getDatabits() {
        return this.e;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig.Paritybit getParitybit() {
        return this.f;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public boolean isDtr() {
        return this.b;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setDtr(boolean z) {
        this.b = z;
        return this;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public boolean isRts() {
        return this.c;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setRts(boolean z) {
        this.c = z;
        return this;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public int getWaitTimeMillis() {
        return this.g;
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setWaitTimeMillis(int i) {
        if (i >= 0) {
            this.g = i;
            return this;
        }
        throw new IllegalArgumentException("Wait time must be >= 0");
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public RxtxChannelConfig setReadTimeout(int i) {
        if (i >= 0) {
            this.h = i;
            return this;
        }
        throw new IllegalArgumentException("readTime must be >= 0");
    }

    @Override // io.netty.channel.rxtx.RxtxChannelConfig
    public int getReadTimeout() {
        return this.h;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public RxtxChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public RxtxChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public RxtxChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public RxtxChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
