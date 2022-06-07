package com.xiaomi.passport.LocalFeatures;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.xiaomi.accounts.ILocalFeatureManagerResponse;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.ServerErrorCode;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.passport.PassportExternal;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.SSLException;

/* loaded from: classes4.dex */
public class LocalFeaturesImpl implements MiLocalFeaturesManager {
    private static final String TAG = "LocalFeaturesImpl";
    private static final ExecutorService THREAD_POOL_EXECUTOR = Executors.newFixedThreadPool(5);
    private static LocalFeaturesImpl sInstance = null;
    private Context mContext;
    private Handler mMainHandler;

    LocalFeaturesImpl(Context context) {
        this.mContext = context;
        this.mMainHandler = new Handler(this.mContext.getMainLooper());
    }

    public static synchronized LocalFeaturesImpl get(Context context) {
        LocalFeaturesImpl localFeaturesImpl;
        synchronized (LocalFeaturesImpl.class) {
            if (sInstance == null) {
                sInstance = new LocalFeaturesImpl(context);
            }
            localFeaturesImpl = sInstance;
        }
        return localFeaturesImpl;
    }

    @Override // com.xiaomi.passport.LocalFeatures.MiLocalFeaturesManager
    public LocalFeaturesManagerFuture<Bundle> getStsUrl(final String str, final String str2, final String str3, Bundle bundle, Activity activity, LocalFeaturesManagerCallback<Bundle> localFeaturesManagerCallback, Handler handler) {
        if (str != null && str2 != null) {
            return new AmsTask(activity, handler, localFeaturesManagerCallback) { // from class: com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.1
                @Override // com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.AmsTask
                public void doWork() throws RemoteException {
                    LocalFeaturesImpl.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            LocalFeaturesImpl.this.getStsUrl(AnonymousClass1.this.mResponse, str, str2, str3);
                        }
                    });
                }
            }.start();
        }
        throw new IllegalArgumentException("userId or password options is null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getStsUrl(LocalFeaturesManagerResponse localFeaturesManagerResponse, String str, String str2, String str3) {
        if (localFeaturesManagerResponse != null) {
            String str4 = null;
            try {
                AccountInfo stsUrlByPassword = AccountHelper.getStsUrlByPassword(str, str2, str3, null, null);
                Bundle bundle = new Bundle();
                if (stsUrlByPassword != null) {
                    str4 = stsUrlByPassword.getAutoLoginUrl();
                }
                bundle.putString("sts_url", str4);
                localFeaturesManagerResponse.onResult(bundle);
            } catch (IllegalDeviceException e) {
                AccountLog.w(TAG, e);
                localFeaturesManagerResponse.onError(9, e.getMessage());
            } catch (InvalidCredentialException e2) {
                AccountLog.w(TAG, e2);
                if (e2.getHasPwd()) {
                    localFeaturesManagerResponse.onError(4, e2.getMessage());
                } else {
                    onBackIntent(localFeaturesManagerResponse, str, str2, str3, e2);
                }
            } catch (InvalidUserNameException e3) {
                AccountLog.w(TAG, e3);
                localFeaturesManagerResponse.onError(8, e3.getMessage());
            } catch (NeedCaptchaException e4) {
                AccountLog.w(TAG, e4);
                onBackIntent(localFeaturesManagerResponse, str, str2, str3, e4);
            } catch (NeedNotificationException e5) {
                AccountLog.w(TAG, e5);
                onBackIntent(localFeaturesManagerResponse, str, str2, str3, e5);
            } catch (NeedVerificationException e6) {
                AccountLog.w(TAG, e6);
                onBackIntent(localFeaturesManagerResponse, str, str2, str3, e6);
            } catch (AccessDeniedException e7) {
                AccountLog.w(TAG, e7);
                localFeaturesManagerResponse.onError(7, e7.getMessage());
            } catch (AuthenticationFailureException e8) {
                AccountLog.w(TAG, e8);
                localFeaturesManagerResponse.onError(6, e8.getMessage());
            } catch (InvalidResponseException e9) {
                AccountLog.w(TAG, e9);
                localFeaturesManagerResponse.onError(6, e9.getMessage());
            } catch (IOException e10) {
                AccountLog.w(TAG, e10);
                localFeaturesManagerResponse.onError(5, e10.getMessage());
            }
        } else {
            throw new IllegalArgumentException("response is null");
        }
    }

    private void onBackIntent(LocalFeaturesManagerResponse localFeaturesManagerResponse, String str, String str2, String str3, Exception exc) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseConstants.EXTRA_USER_ID, str);
        bundle.putString("service_id", str3);
        bundle.putString("password", str2);
        bundle.putBoolean("need_retry_on_authenticator_response_result", true);
        Intent resultIntent = getResultIntent(localFeaturesManagerResponse, exc, bundle);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("intent", resultIntent);
        localFeaturesManagerResponse.onResult(bundle2);
    }

    private Intent getResultIntent(LocalFeaturesManagerResponse localFeaturesManagerResponse, Exception exc, Bundle bundle) {
        if (exc instanceof NeedCaptchaException) {
            bundle.putString("captcha_url", ((NeedCaptchaException) exc).getCaptchaUrl());
            return AuthenticatorUtil.newQuickLoginIntent(this.mContext, localFeaturesManagerResponse, bundle);
        } else if (exc instanceof NeedNotificationException) {
            return AuthenticatorUtil.newNotificationIntent(this.mContext, localFeaturesManagerResponse, ((NeedNotificationException) exc).getNotificationUrl(), null, true, bundle);
        } else {
            if (!(exc instanceof NeedVerificationException)) {
                return AuthenticatorUtil.newQuickLoginIntent(this.mContext, localFeaturesManagerResponse, bundle);
            }
            NeedVerificationException needVerificationException = (NeedVerificationException) exc;
            MetaLoginData metaLoginData = needVerificationException.getMetaLoginData();
            bundle.putString("extra_step1_token", needVerificationException.getStep1Token());
            bundle.putString("extra_sign", metaLoginData.sign);
            bundle.putString("extra_qs", metaLoginData.qs);
            bundle.putString("extra_callback", metaLoginData.callback);
            return AuthenticatorUtil.newQuickLoginIntent(this.mContext, localFeaturesManagerResponse, bundle);
        }
    }

    @Override // com.xiaomi.passport.LocalFeatures.MiLocalFeaturesManager
    public LocalFeaturesManagerFuture<Bundle> handleLoginQRCodeScanResult(final String str, final Activity activity, final Bundle bundle, LocalFeaturesManagerCallback<Bundle> localFeaturesManagerCallback, Handler handler) {
        return new AmsTask(activity, handler, localFeaturesManagerCallback) { // from class: com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.2
            @Override // com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.AmsTask
            public void doWork() throws RemoteException {
                LocalFeaturesImpl.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!AccountHelper.isMiAccountLoginQRCodeScanResult(str)) {
                            AnonymousClass2.this.mResponse.onError(7, "invalid scan code login url");
                            return;
                        }
                        Intent intent = new Intent("android.intent.action.VIEW");
                        if (MiAccountManager.get(LocalFeaturesImpl.this.mContext).isUseSystem()) {
                            intent.setClassName(AccountIntent.PACKAGE_XIAOMI_ACCOUNT, "com.xiaomi.account.ui.AccountWebActivity");
                        } else {
                            ComponentName processScanLoginQRCodeComponentName = PassportExternal.getAuthenticatorComponentNameInterface(activity).getProcessScanLoginQRCodeComponentName();
                            if (processScanLoginQRCodeComponentName == null) {
                                AnonymousClass2.this.mResponse.onError(8, "custom ui not implements process scan login QR Code");
                                return;
                            }
                            intent.setComponent(processScanLoginQRCodeComponentName);
                        }
                        intent.putExtra("accountAuthenticatorResponse", AnonymousClass2.this.mResponse);
                        intent.setData(Uri.parse(str));
                        if (bundle != null) {
                            intent.putExtras(bundle);
                        }
                        Bundle bundle2 = new Bundle();
                        bundle2.putParcelable("intent", intent);
                        AnonymousClass2.this.mResponse.onResult(bundle2);
                    }
                });
            }
        }.start();
    }

    /* loaded from: classes4.dex */
    private abstract class AmsTask extends FutureTask<Bundle> implements LocalFeaturesManagerFuture<Bundle> {
        final Activity mActivity;
        final LocalFeaturesManagerCallback<Bundle> mCallback;
        final Handler mHandler;
        final LocalFeaturesManagerResponse mResponse = new LocalFeaturesManagerResponse(new Response());

        public abstract void doWork() throws RemoteException;

        public AmsTask(Activity activity, Handler handler, LocalFeaturesManagerCallback<Bundle> localFeaturesManagerCallback) {
            super(new Callable<Bundle>() { // from class: com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.AmsTask.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Bundle call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
            this.mHandler = handler;
            this.mCallback = localFeaturesManagerCallback;
            this.mActivity = activity;
        }

        public final LocalFeaturesManagerFuture<Bundle> start() {
            try {
                doWork();
            } catch (RemoteException e) {
                setException(e);
            }
            return this;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void set(Bundle bundle) {
            if (bundle == null) {
                AccountLog.e(LocalFeaturesImpl.TAG, "the bundle must not be null", new Exception());
            }
            super.set((AmsTask) bundle);
        }

        private Bundle internalGetResult(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException, InvalidCredentialException, InvalidUserNameException, InvalidResponseException, IllegalDeviceException, AccessDeniedException, AuthenticationFailureException {
            if (!isDone()) {
                LocalFeaturesImpl.this.ensureNotOnMainThread();
            }
            try {
                try {
                    try {
                        if (l == null) {
                            return get();
                        }
                        return get(l.longValue(), timeUnit);
                    } finally {
                        cancel(true);
                    }
                } catch (InterruptedException | TimeoutException unused) {
                    cancel(true);
                    throw new OperationCanceledException();
                }
            } catch (CancellationException unused2) {
                throw new OperationCanceledException();
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
                } else if (cause instanceof InvalidCredentialException) {
                    throw new InvalidCredentialException(((InvalidCredentialException) cause).hasPwd);
                } else if (cause instanceof InvalidUserNameException) {
                    throw new InvalidUserNameException();
                } else if (cause instanceof InvalidResponseException) {
                    throw new InvalidResponseException(e.getMessage());
                } else if (cause instanceof IllegalDeviceException) {
                    throw new IllegalDeviceException(e.getMessage());
                } else if (cause instanceof SSLException) {
                    throw new SSLException(e.getMessage());
                } else if (cause instanceof AccessDeniedException) {
                    throw new AccessDeniedException(403, e.getMessage());
                } else if (cause instanceof AuthenticationFailureException) {
                    throw new AuthenticationFailureException(e.getMessage());
                } else {
                    throw new IllegalStateException(cause);
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.passport.LocalFeatures.LocalFeaturesManagerFuture
        public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException, InvalidCredentialException, InvalidUserNameException, AccessDeniedException, InvalidResponseException, IllegalDeviceException, AuthenticationFailureException {
            return internalGetResult(null, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.passport.LocalFeatures.LocalFeaturesManagerFuture
        public Bundle getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException, InvalidCredentialException, InvalidUserNameException, AccessDeniedException, InvalidResponseException, IllegalDeviceException, AuthenticationFailureException {
            return internalGetResult(Long.valueOf(j), timeUnit);
        }

        @Override // java.util.concurrent.FutureTask
        protected void done() {
            LocalFeaturesManagerCallback<Bundle> localFeaturesManagerCallback = this.mCallback;
            if (localFeaturesManagerCallback != null) {
                LocalFeaturesImpl.this.postToHandler(this.mHandler, localFeaturesManagerCallback, this);
            }
        }

        /* loaded from: classes4.dex */
        private class Response extends ILocalFeatureManagerResponse.Stub {
            @Override // com.xiaomi.accounts.ILocalFeatureManagerResponse
            public void onRequestContinued() throws RemoteException {
            }

            private Response() {
            }

            @Override // com.xiaomi.accounts.ILocalFeatureManagerResponse
            public void onResult(Bundle bundle) {
                Intent intent = (Intent) bundle.getParcelable("intent");
                if (intent != null && AmsTask.this.mActivity != null) {
                    AmsTask.this.mActivity.startActivity(intent);
                } else if (bundle.getBoolean(b.M)) {
                    try {
                        AmsTask.this.doWork();
                    } catch (RemoteException unused) {
                    }
                } else {
                    AmsTask.this.set(bundle);
                }
            }

            @Override // com.xiaomi.accounts.ILocalFeatureManagerResponse
            public void onError(int i, String str) {
                if (i == 4) {
                    AmsTask.this.cancel(true);
                    return;
                }
                AmsTask amsTask = AmsTask.this;
                amsTask.setException(LocalFeaturesImpl.this.convertErrorToException(i, str));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Exception convertErrorToException(int i, String str) {
        if (i == 5) {
            return new IOException(str);
        }
        if (i == 7) {
            return new AccessDeniedException(403, str);
        }
        if (i == 4) {
            return new InvalidCredentialException(ServerErrorCode.ERROR_PASSWORD, str, true);
        }
        if (i == 10) {
            return new SSLException(str);
        }
        if (i == 6) {
            return new InvalidResponseException(str);
        }
        if (i == 8) {
            return new InvalidUserNameException();
        }
        if (i == 9) {
            return new IllegalDeviceException(str);
        }
        if (i == 6) {
            return new UnsupportedOperationException(str);
        }
        if (i == 5) {
            return new AuthenticatorException(str);
        }
        if (i == 7) {
            return new IllegalArgumentException(str);
        }
        return new AuthenticatorException(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postToHandler(Handler handler, final LocalFeaturesManagerCallback<Bundle> localFeaturesManagerCallback, final LocalFeaturesManagerFuture<Bundle> localFeaturesManagerFuture) {
        if (handler == null) {
            handler = this.mMainHandler;
        }
        handler.post(new Runnable() { // from class: com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl.3
            @Override // java.lang.Runnable
            public void run() {
                localFeaturesManagerCallback.run(localFeaturesManagerFuture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureNotOnMainThread() {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper == this.mContext.getMainLooper()) {
            IllegalStateException illegalStateException = new IllegalStateException("calling this from your main thread can lead to deadlock");
            AccountLog.e(TAG, "calling this from your main thread can lead to deadlock and/or ANRs", illegalStateException);
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 8) {
                throw illegalStateException;
            }
        }
    }
}
