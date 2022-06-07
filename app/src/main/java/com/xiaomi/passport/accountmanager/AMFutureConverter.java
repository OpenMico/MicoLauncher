package com.xiaomi.passport.accountmanager;

import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.AsyncTask;
import android.os.Bundle;
import com.xiaomi.passport.servicetoken.AMAuthTokenConverter;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public final class AMFutureConverter {
    public static AccountManagerFuture<Bundle> fromServiceTokenFuture(final ServiceTokenFuture serviceTokenFuture) {
        final MyAccountManagerFuture myAccountManagerFuture = new MyAccountManagerFuture();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.passport.accountmanager.AMFutureConverter.1
            @Override // java.lang.Runnable
            public void run() {
                ServiceTokenResult serviceTokenResult = ServiceTokenFuture.this.get();
                Exception aMException = AMAuthTokenConverter.toAMException(serviceTokenResult);
                if (aMException != null) {
                    myAccountManagerFuture.setException(aMException);
                    return;
                }
                try {
                    myAccountManagerFuture.setResult(AMAuthTokenConverter.toAMBundle(serviceTokenResult));
                } catch (AMAuthTokenConverter.ConvertException e) {
                    myAccountManagerFuture.setException((Exception) new AuthenticatorException(String.format("errorCode=%s;errorMsg:%s", e.errorCode, e.errorMsg)));
                }
            }
        });
        return myAccountManagerFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class MyAccountManagerFuture extends FutureTask<Bundle> implements AccountManagerFuture<Bundle> {
        public MyAccountManagerFuture() {
            super(new Callable<Bundle>() { // from class: com.xiaomi.passport.accountmanager.AMFutureConverter.MyAccountManagerFuture.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Bundle call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.accounts.AccountManagerFuture
        public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException {
            return internalGetResult(null, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.accounts.AccountManagerFuture
        public Bundle getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            return internalGetResult(Long.valueOf(j), timeUnit);
        }

        private Bundle internalGetResult(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            try {
                try {
                    try {
                        if (l == null) {
                            return get();
                        }
                        return get(l.longValue(), timeUnit);
                    } catch (ExecutionException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof IOException) {
                            throw ((IOException) cause);
                        } else if (cause instanceof UnsupportedOperationException) {
                            throw new AuthenticatorException(cause);
                        } else if (cause instanceof AuthenticatorException) {
                            throw ((AuthenticatorException) cause);
                        } else if (cause instanceof RuntimeException) {
                            throw ((RuntimeException) cause);
                        } else if (cause instanceof Error) {
                            throw ((Error) cause);
                        } else {
                            throw new IllegalStateException(cause);
                        }
                    }
                } catch (InterruptedException | CancellationException | TimeoutException unused) {
                    cancel(true);
                    throw new OperationCanceledException();
                }
            } finally {
                cancel(true);
            }
        }

        public void setResult(Bundle bundle) {
            super.set(bundle);
        }

        public void setException(Exception exc) {
            super.setException((Throwable) exc);
        }
    }
}
