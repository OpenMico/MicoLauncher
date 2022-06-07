package io.netty.util.concurrent;

/* loaded from: classes4.dex */
public interface EventExecutorChooserFactory {

    /* loaded from: classes4.dex */
    public interface EventExecutorChooser {
        EventExecutor next();
    }

    EventExecutorChooser newChooser(EventExecutor[] eventExecutorArr);
}
