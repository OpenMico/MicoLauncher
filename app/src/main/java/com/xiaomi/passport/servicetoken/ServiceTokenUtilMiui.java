package com.xiaomi.passport.servicetoken;

import android.accounts.Account;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import com.elvishew.xlog.internal.util.StackTraceUtil;
import com.xiaomi.accountsdk.futureservice.ClientFuture;
import com.xiaomi.accountsdk.futureservice.ServerServiceConnector;
import com.xiaomi.accountsdk.futureservice.SimpleClientFuture;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.MiuiOsBuildReflection;
import com.xiaomi.accountsdk.utils.MiuiVersionDev;
import com.xiaomi.accountsdk.utils.MiuiVersionStable;
import com.xiaomi.passport.IPassportServiceTokenService;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class ServiceTokenUtilMiui extends ServiceTokenUtilImplBase {
    private static final String TAG = "ServiceTokenUtilMiui";
    private static volatile AtomicBoolean miuiServiceTokenServiceAvailability = new AtomicBoolean(true);
    private static volatile Boolean xiaomiAccountAppSlhPhAvailability = null;

    @Override // com.xiaomi.passport.servicetoken.ServiceTokenUtilImplBase
    public ServiceTokenResult getServiceTokenImpl(final Context context, final String str) {
        if (str != null && str.startsWith("weblogin:") && MiuiCompatUtil.hasWebLoginCompatIssue()) {
            return getAMServiceTokenUtil().getServiceTokenImpl(context, str);
        }
        if (miuiServiceTokenServiceAvailability.get()) {
            ServiceTokenFuture serviceTokenFuture = new ServiceTokenFuture(null);
            new ServiceTokenServiceConnector(context, serviceTokenFuture) { // from class: com.xiaomi.passport.servicetoken.ServiceTokenUtilMiui.1
                @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                public ServiceTokenResult callServiceWork() throws RemoteException {
                    return ServiceTokenUIErrorHandler.blockingHandleIntentError(context, getIService().getServiceToken(str));
                }
            }.bind();
            if (checkBindServiceSuccess(serviceTokenFuture)) {
                return serviceTokenFuture.get();
            }
            miuiServiceTokenServiceAvailability.set(false);
        }
        return getAMServiceTokenUtil().getServiceTokenImpl(context, str);
    }

    @Override // com.xiaomi.passport.servicetoken.ServiceTokenUtilImplBase
    public ServiceTokenResult invalidateServiceTokenImpl(Context context, final ServiceTokenResult serviceTokenResult) {
        if (miuiServiceTokenServiceAvailability.get()) {
            ServiceTokenFuture serviceTokenFuture = new ServiceTokenFuture(null);
            new ServiceTokenServiceConnector(context, serviceTokenFuture) { // from class: com.xiaomi.passport.servicetoken.ServiceTokenUtilMiui.2
                @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                public ServiceTokenResult callServiceWork() throws RemoteException {
                    ServiceTokenResult serviceTokenResult2;
                    if (serviceTokenResult == null || !MiuiCompatUtil.hasServiceTokenResultParcelCompatIssue()) {
                        serviceTokenResult2 = serviceTokenResult;
                    } else {
                        serviceTokenResult2 = ServiceTokenResult.Builder.copyFrom(serviceTokenResult).useV1Parcel(true).build();
                    }
                    return getIService().invalidateServiceToken(serviceTokenResult2);
                }
            }.bind();
            if (checkBindServiceSuccess(serviceTokenFuture)) {
                return serviceTokenFuture.get();
            }
            miuiServiceTokenServiceAvailability.set(false);
        }
        return getAMServiceTokenUtil().invalidateServiceTokenImpl(context, serviceTokenResult);
    }

    public boolean doesXiaomiAccountAppSupportServiceTokenUIResponse(Context context) {
        if (!miuiServiceTokenServiceAvailability.get()) {
            return false;
        }
        SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
        new ServiceTokenServiceConnectorBase<Boolean>(context, simpleClientFuture) { // from class: com.xiaomi.passport.servicetoken.ServiceTokenUtilMiui.3
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public Boolean callServiceWork() throws RemoteException {
                return Boolean.valueOf(getIService().supportServiceTokenUIResponse());
            }
        }.bind();
        try {
            return ((Boolean) simpleClientFuture.get()).booleanValue();
        } catch (InterruptedException e) {
            AccountLog.w(TAG, "", e);
            return false;
        } catch (ExecutionException e2) {
            AccountLog.w(TAG, "", e2);
            return false;
        }
    }

    @Override // com.xiaomi.passport.servicetoken.IServiceTokenUtil
    public boolean fastCheckSlhPhCompatibility(Context context) {
        if (!miuiServiceTokenServiceAvailability.get()) {
            return false;
        }
        synchronized (ServiceTokenUtilMiui.class) {
            if (xiaomiAccountAppSlhPhAvailability != null) {
                return xiaomiAccountAppSlhPhAvailability.booleanValue();
            }
            SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
            new ServiceTokenServiceConnectorBase<Boolean>(context, simpleClientFuture) { // from class: com.xiaomi.passport.servicetoken.ServiceTokenUtilMiui.4
                @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                public Boolean callServiceWork() throws RemoteException {
                    return Boolean.valueOf(getIService().fastCheckSlhPhCompatibility());
                }
            }.bind();
            try {
                Boolean bool = (Boolean) simpleClientFuture.get();
                synchronized (ServiceTokenUtilMiui.class) {
                    xiaomiAccountAppSlhPhAvailability = bool;
                }
                return bool.booleanValue();
            } catch (InterruptedException e) {
                AccountLog.w(TAG, "", e);
                return false;
            } catch (ExecutionException e2) {
                AccountLog.w(TAG, "", e2);
                return false;
            }
        }
    }

    @Override // com.xiaomi.passport.servicetoken.ServiceTokenUtilImplBase
    protected XmAccountVisibility canAccessAccountImpl(final Context context) {
        Account xiaomiAccount = new AMUtilImpl(new AMKeys()).getXiaomiAccount(context);
        if (xiaomiAccount != null) {
            return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_NONE, null).accountVisible(true, xiaomiAccount).build();
        }
        SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
        new ServiceTokenServiceConnectorBase<XmAccountVisibility>(context, simpleClientFuture) { // from class: com.xiaomi.passport.servicetoken.ServiceTokenUtilMiui.5
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v7, types: [android.content.Intent, java.lang.String] */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public XmAccountVisibility callServiceWork() throws RemoteException {
                if (getIService().supportAccessAccount()) {
                    return getIService().setAccountVisible(context.getPackageName());
                }
                if (Build.VERSION.SDK_INT < 26) {
                    return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_PRE_ANDROID_O, null).build();
                }
                String[] strArr = {"com.xiaomi"};
                return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_NOT_SUPPORT, null).newChooseAccountIntent(StackTraceUtil.getStackTraceString(null)).build();
            }
        }.bind();
        try {
            return (XmAccountVisibility) simpleClientFuture.get();
        } catch (InterruptedException e) {
            AccountLog.e(TAG, "setSystemAccountVisible", e);
            return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_CANCELLED, null).build();
        } catch (ExecutionException e2) {
            AccountLog.e(TAG, "setSystemAccountVisible", e2);
            return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_EXECUTION, e2.getMessage()).build();
        }
    }

    private ServiceTokenUtilAM getAMServiceTokenUtil() {
        return new ServiceTokenUtilAM(new AMUtilImpl(new AMKeys()));
    }

    private boolean checkBindServiceSuccess(ServiceTokenFuture serviceTokenFuture) {
        return !serviceTokenFuture.isDone() || serviceTokenFuture.get().errorCode != ServiceTokenResult.ErrorCode.ERROR_REMOTE_EXCEPTION;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static abstract class ServiceTokenServiceConnector extends ServiceTokenServiceConnectorBase<ServiceTokenResult> {
        protected ServiceTokenServiceConnector(Context context, ServiceTokenFuture serviceTokenFuture) {
            super(context, serviceTokenFuture);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static abstract class ServiceTokenServiceConnectorBase<T> extends ServerServiceConnector<IPassportServiceTokenService, T, T> {
        private static final String ACTION_SERVICE_TOKEN_OP = "com.xiaomi.account.action.SERVICE_TOKEN_OP";
        private static final String PACKAGE_NAME_XIAOMI_ACCOUNT = "com.xiaomi.account";

        protected ServiceTokenServiceConnectorBase(Context context, ClientFuture<T, T> clientFuture) {
            super(context, "com.xiaomi.account.action.SERVICE_TOKEN_OP", "com.xiaomi.account", clientFuture);
        }

        @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
        public final IPassportServiceTokenService binderToServiceType(IBinder iBinder) {
            return IPassportServiceTokenService.Stub.asInterface(iBinder);
        }
    }

    /* loaded from: classes4.dex */
    public static class MiuiCompatUtil {
        private static volatile Boolean cachedParcelIssueCheckResult;
        private static volatile Boolean cachedWebLoginIssueCheckResult;

        private MiuiCompatUtil() {
        }

        static boolean hasServiceTokenResultParcelCompatIssue() {
            if (cachedParcelIssueCheckResult != null) {
                return cachedParcelIssueCheckResult.booleanValue();
            }
            boolean z = true;
            if ((!MiuiOsBuildReflection.isStable(false) || !MiuiVersionStable.earlyThan(new MiuiVersionStable(8, 0), false)) && (!MiuiOsBuildReflection.isDevButNotAlpha(false) || !MiuiVersionDev.earlyThan(new MiuiVersionDev(6, 7, 1), false))) {
                z = false;
            }
            if (cachedParcelIssueCheckResult == null) {
                cachedParcelIssueCheckResult = new Boolean(z);
            }
            return cachedParcelIssueCheckResult.booleanValue();
        }

        static boolean hasWebLoginCompatIssue() {
            if (cachedWebLoginIssueCheckResult != null) {
                return cachedWebLoginIssueCheckResult.booleanValue();
            }
            boolean z = true;
            if ((!MiuiOsBuildReflection.isStable(false) || !MiuiVersionStable.earlyThan(new MiuiVersionStable(8, 2), false)) && (!MiuiOsBuildReflection.isDevButNotAlpha(false) || !MiuiVersionDev.earlyThan(new MiuiVersionDev(6, 11, 25), false))) {
                z = false;
            }
            if (cachedWebLoginIssueCheckResult == null) {
                cachedWebLoginIssueCheckResult = new Boolean(z);
            }
            return cachedWebLoginIssueCheckResult.booleanValue();
        }
    }
}
