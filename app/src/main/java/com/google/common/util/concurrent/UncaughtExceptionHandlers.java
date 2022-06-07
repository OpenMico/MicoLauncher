package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.lang.Thread;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtIncompatible
/* loaded from: classes2.dex */
public final class UncaughtExceptionHandlers {
    private UncaughtExceptionHandlers() {
    }

    public static Thread.UncaughtExceptionHandler systemExit() {
        return new a(Runtime.getRuntime());
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    static final class a implements Thread.UncaughtExceptionHandler {
        private static final Logger a = Logger.getLogger(a.class.getName());
        private final Runtime b;

        a(Runtime runtime) {
            this.b = runtime;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            try {
                a.log(Level.SEVERE, String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", thread), th);
            } finally {
                this.b.exit(1);
            }
        }
    }
}
