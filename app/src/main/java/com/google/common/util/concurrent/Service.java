package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public interface Service {

    @Beta
    /* loaded from: classes2.dex */
    public static abstract class Listener {
        public void failed(State state, Throwable th) {
        }

        public void running() {
        }

        public void starting() {
        }

        public void stopping(State state) {
        }

        public void terminated(State state) {
        }
    }

    @Beta
    /* loaded from: classes2.dex */
    public enum State {
        NEW {
            @Override // com.google.common.util.concurrent.Service.State
            boolean a() {
                return false;
            }
        },
        STARTING {
            @Override // com.google.common.util.concurrent.Service.State
            boolean a() {
                return false;
            }
        },
        RUNNING {
            @Override // com.google.common.util.concurrent.Service.State
            boolean a() {
                return false;
            }
        },
        STOPPING {
            @Override // com.google.common.util.concurrent.Service.State
            boolean a() {
                return false;
            }
        },
        TERMINATED {
            @Override // com.google.common.util.concurrent.Service.State
            boolean a() {
                return true;
            }
        },
        FAILED {
            @Override // com.google.common.util.concurrent.Service.State
            boolean a() {
                return true;
            }
        };

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract boolean a();
    }

    void addListener(Listener listener, Executor executor);

    void awaitRunning();

    void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException;

    void awaitTerminated();

    void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException;

    Throwable failureCause();

    boolean isRunning();

    @CanIgnoreReturnValue
    Service startAsync();

    State state();

    @CanIgnoreReturnValue
    Service stopAsync();
}
