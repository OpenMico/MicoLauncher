package io.netty.handler.traffic;

import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class GlobalChannelTrafficCounter extends TrafficCounter {
    public GlobalChannelTrafficCounter(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler, ScheduledExecutorService scheduledExecutorService, String str, long j) {
        super(globalChannelTrafficShapingHandler, scheduledExecutorService, str, j);
        if (scheduledExecutorService == null) {
            throw new IllegalArgumentException("Executor must not be null");
        }
    }

    /* loaded from: classes4.dex */
    private static class a implements Runnable {
        private final GlobalChannelTrafficShapingHandler a;
        private final TrafficCounter b;

        a(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler, TrafficCounter trafficCounter) {
            this.a = globalChannelTrafficShapingHandler;
            this.b = trafficCounter;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b.h) {
                long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
                this.b.a(milliSecondFromNano);
                for (GlobalChannelTrafficShapingHandler.a aVar : this.a.f.values()) {
                    aVar.b.a(milliSecondFromNano);
                }
                this.a.doAccounting(this.b);
                TrafficCounter trafficCounter = this.b;
                trafficCounter.g = trafficCounter.e.schedule(this, this.b.b.get(), TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override // io.netty.handler.traffic.TrafficCounter
    public synchronized void start() {
        if (!this.h) {
            this.a.set(milliSecondFromNano());
            long j = this.b.get();
            if (j > 0) {
                this.h = true;
                this.f = new a((GlobalChannelTrafficShapingHandler) this.d, this);
                this.g = this.e.schedule(this.f, j, TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override // io.netty.handler.traffic.TrafficCounter
    public synchronized void stop() {
        if (this.h) {
            this.h = false;
            a(milliSecondFromNano());
            this.d.doAccounting(this);
            if (this.g != null) {
                this.g.cancel(true);
            }
        }
    }

    @Override // io.netty.handler.traffic.TrafficCounter
    public void resetCumulativeTime() {
        for (GlobalChannelTrafficShapingHandler.a aVar : ((GlobalChannelTrafficShapingHandler) this.d).f.values()) {
            aVar.b.resetCumulativeTime();
        }
        super.resetCumulativeTime();
    }
}
