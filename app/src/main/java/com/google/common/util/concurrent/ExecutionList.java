package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes2.dex */
public final class ExecutionList {
    private static final Logger a = Logger.getLogger(ExecutionList.class.getName());
    @NullableDecl
    @GuardedBy("this")
    private a b;
    @GuardedBy("this")
    private boolean c;

    public void add(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            if (!this.c) {
                this.b = new a(runnable, executor, this.b);
            } else {
                a(runnable, executor);
            }
        }
    }

    public void execute() {
        synchronized (this) {
            if (!this.c) {
                this.c = true;
                a aVar = this.b;
                this.b = null;
                a aVar2 = aVar;
                a aVar3 = null;
                while (aVar2 != null) {
                    a aVar4 = aVar2.c;
                    aVar2.c = aVar3;
                    aVar3 = aVar2;
                    aVar2 = aVar4;
                }
                while (aVar3 != null) {
                    a(aVar3.a, aVar3.b);
                    aVar3 = aVar3.c;
                }
            }
        }
    }

    private static void a(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = a;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
        }
    }

    /* loaded from: classes2.dex */
    private static final class a {
        final Runnable a;
        final Executor b;
        @NullableDecl
        a c;

        a(Runnable runnable, Executor executor, a aVar) {
            this.a = runnable;
            this.b = executor;
            this.c = aVar;
        }
    }
}
