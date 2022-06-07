package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: ImmediateFuture.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class m<V> extends FluentFuture<V> {
    private static final Logger a = Logger.getLogger(m.class.getName());

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // java.util.concurrent.Future
    public abstract V get() throws ExecutionException;

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return true;
    }

    m() {
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (RuntimeException e2) {
            Logger logger = a;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    @Override // java.util.concurrent.Future
    public V get(long j, TimeUnit timeUnit) throws ExecutionException {
        Preconditions.checkNotNull(timeUnit);
        return get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ImmediateFuture.java */
    /* loaded from: classes2.dex */
    public static class e<V> extends m<V> {
        static final e<Object> a = new e<>(null);
        @NullableDecl
        private final V b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public e(@NullableDecl V v) {
            this.b = v;
        }

        @Override // com.google.common.util.concurrent.m, java.util.concurrent.Future
        public V get() {
            return this.b;
        }

        public String toString() {
            return super.toString() + "[status=SUCCESS, result=[" + this.b + "]]";
        }
    }

    /* compiled from: ImmediateFuture.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    static class d<V, X extends Exception> extends m<V> implements CheckedFuture<V, X> {
        @NullableDecl
        private final V a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public d(@NullableDecl V v) {
            this.a = v;
        }

        @Override // com.google.common.util.concurrent.m, java.util.concurrent.Future
        public V get() {
            return this.a;
        }

        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet() {
            return this.a;
        }

        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet(long j, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            return this.a;
        }

        public String toString() {
            return super.toString() + "[status=SUCCESS, result=[" + this.a + "]]";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ImmediateFuture.java */
    /* loaded from: classes2.dex */
    public static final class c<V> extends AbstractFuture.h<V> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public c(Throwable th) {
            setException(th);
        }
    }

    /* compiled from: ImmediateFuture.java */
    /* loaded from: classes2.dex */
    static final class a<V> extends AbstractFuture.h<V> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public a() {
            cancel(false);
        }
    }

    /* compiled from: ImmediateFuture.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    static class b<V, X extends Exception> extends m<V> implements CheckedFuture<V, X> {
        private final X a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(X x) {
            this.a = x;
        }

        @Override // com.google.common.util.concurrent.m, java.util.concurrent.Future
        public V get() throws ExecutionException {
            throw new ExecutionException(this.a);
        }

        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet() throws Exception {
            throw this.a;
        }

        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet(long j, TimeUnit timeUnit) throws Exception {
            Preconditions.checkNotNull(timeUnit);
            throw this.a;
        }

        public String toString() {
            return super.toString() + "[status=FAILURE, cause=[" + this.a + "]]";
        }
    }
}
