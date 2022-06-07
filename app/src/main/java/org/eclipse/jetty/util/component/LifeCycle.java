package org.eclipse.jetty.util.component;

import java.util.EventListener;

/* loaded from: classes5.dex */
public interface LifeCycle {

    /* loaded from: classes5.dex */
    public interface Listener extends EventListener {
        void lifeCycleFailure(LifeCycle lifeCycle, Throwable th);

        void lifeCycleStarted(LifeCycle lifeCycle);

        void lifeCycleStarting(LifeCycle lifeCycle);

        void lifeCycleStopped(LifeCycle lifeCycle);

        void lifeCycleStopping(LifeCycle lifeCycle);
    }

    void addLifeCycleListener(Listener listener);

    boolean isFailed();

    boolean isRunning();

    boolean isStarted();

    boolean isStarting();

    boolean isStopped();

    boolean isStopping();

    void removeLifeCycleListener(Listener listener);

    void start() throws Exception;

    void stop() throws Exception;
}
