package com.xiaomi.passport.LocalFeatures;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public interface LocalFeaturesManagerFuture<V> {
    boolean cancel(boolean z);

    V getResult() throws OperationCanceledException, IOException, AuthenticatorException, InvalidCredentialException, InvalidUserNameException, AccessDeniedException, InvalidResponseException, IllegalDeviceException, AuthenticationFailureException;

    V getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException, InvalidCredentialException, InvalidUserNameException, AccessDeniedException, InvalidResponseException, IllegalDeviceException, AuthenticationFailureException;

    boolean isCancelled();

    boolean isDone();
}
