package io.netty.util.concurrent;

import io.netty.util.concurrent.EventExecutorChooserFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class DefaultEventExecutorChooserFactory implements EventExecutorChooserFactory {
    public static final DefaultEventExecutorChooserFactory INSTANCE = new DefaultEventExecutorChooserFactory();

    private static boolean a(int i) {
        return ((-i) & i) == i;
    }

    private DefaultEventExecutorChooserFactory() {
    }

    @Override // io.netty.util.concurrent.EventExecutorChooserFactory
    public EventExecutorChooserFactory.EventExecutorChooser newChooser(EventExecutor[] eventExecutorArr) {
        if (a(eventExecutorArr.length)) {
            return new b(eventExecutorArr);
        }
        return new a(eventExecutorArr);
    }

    /* loaded from: classes4.dex */
    private static final class b implements EventExecutorChooserFactory.EventExecutorChooser {
        private final AtomicInteger a = new AtomicInteger();
        private final EventExecutor[] b;

        b(EventExecutor[] eventExecutorArr) {
            this.b = eventExecutorArr;
        }

        @Override // io.netty.util.concurrent.EventExecutorChooserFactory.EventExecutorChooser
        public EventExecutor next() {
            return this.b[this.a.getAndIncrement() & (this.b.length - 1)];
        }
    }

    /* loaded from: classes4.dex */
    private static final class a implements EventExecutorChooserFactory.EventExecutorChooser {
        private final AtomicInteger a = new AtomicInteger();
        private final EventExecutor[] b;

        a(EventExecutor[] eventExecutorArr) {
            this.b = eventExecutorArr;
        }

        @Override // io.netty.util.concurrent.EventExecutorChooserFactory.EventExecutorChooser
        public EventExecutor next() {
            return this.b[Math.abs(this.a.getAndIncrement() % this.b.length)];
        }
    }
}
