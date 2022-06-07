package io.netty.channel.rxtx;

import com.xiaomi.micolauncher.api.interceptor.ParamsInterceptor;
import io.netty.channel.ChannelOption;
import io.netty.channel.rxtx.RxtxChannelConfig;

/* loaded from: classes4.dex */
public final class RxtxChannelOption<T> extends ChannelOption<T> {
    private static final Class<RxtxChannelOption> a = RxtxChannelOption.class;
    public static final ChannelOption<Integer> BAUD_RATE = valueOf(a, "BAUD_RATE");
    public static final ChannelOption<Boolean> DTR = valueOf(a, "DTR");
    public static final ChannelOption<Boolean> RTS = valueOf(a, "RTS");
    public static final ChannelOption<RxtxChannelConfig.Stopbits> STOP_BITS = valueOf(a, "STOP_BITS");
    public static final ChannelOption<RxtxChannelConfig.Databits> DATA_BITS = valueOf(a, "DATA_BITS");
    public static final ChannelOption<RxtxChannelConfig.Paritybit> PARITY_BIT = valueOf(a, "PARITY_BIT");
    public static final ChannelOption<Integer> WAIT_TIME = valueOf(a, "WAIT_TIME");
    public static final ChannelOption<Integer> READ_TIMEOUT = valueOf(a, ParamsInterceptor.READ_TIMEOUT);

    private RxtxChannelOption() {
        super(null);
    }
}
