package com.xiaomi.accountsdk.futureservice;

import android.os.AsyncTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public abstract class MiAccountManagerFuture<V> extends ClientFuture<V, V> {
    private static Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    protected V convertServerDataToClientData(V v) throws Throwable {
        return v;
    }

    public abstract V doWork();

    /* JADX INFO: Access modifiers changed from: protected */
    public MiAccountManagerFuture() {
        super(null);
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    public void interpretExecutionException(ExecutionException executionException) throws Exception {
        throw new IllegalStateException("not going here");
    }

    public MiAccountManagerFuture<V> start() {
        executor.execute(new Runnable() { // from class: com.xiaomi.accountsdk.futureservice.MiAccountManagerFuture.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    MiAccountManagerFuture.this.setServerData(MiAccountManagerFuture.this.doWork());
                } catch (Throwable th) {
                    MiAccountManagerFuture.this.setServerSideThrowable(th);
                }
            }
        });
        return this;
    }
}
