package io.netty.util;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public interface Timer {
    Timeout newTimeout(TimerTask timerTask, long j, TimeUnit timeUnit);

    Set<Timeout> stop();
}
