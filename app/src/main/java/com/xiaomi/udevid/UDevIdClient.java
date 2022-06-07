package com.xiaomi.udevid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.futureservice.ClientFuture;
import com.xiaomi.accountsdk.futureservice.ServerServiceConnector;
import com.xiaomi.accountsdk.futureservice.SimpleClientFuture;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.udevid.IUDevIdService;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* loaded from: classes4.dex */
public class UDevIdClient {
    public static String getUDevIdFromSystemAccount(Context context) throws InterruptedException, ExecutionException, UDevIdNullException, UDevIdServiceNotAvailableException {
        if (a(context)) {
            SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
            new a<Bundle>(context, simpleClientFuture) { // from class: com.xiaomi.udevid.UDevIdClient.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Bundle callServiceWork() throws RemoteException {
                    return getIService().getUDevIdFromSystemAccount(new Bundle());
                }
            }.bind();
            String string = ((Bundle) simpleClientFuture.get()).getString(Constants.UDEVID);
            if (string != null) {
                return string;
            }
            throw new UDevIdNullException("UDevId is null");
        }
        throw new UDevIdServiceNotAvailableException("UDevIdService is not available");
    }

    private static boolean a(Context context) {
        Intent intent = new Intent(AccountIntent.ACTION_UDEVID_SERVICE);
        intent.setPackage(AccountIntent.PACKAGE_XIAOMI_ACCOUNT);
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        if (queryIntentServices != null && !queryIntentServices.isEmpty()) {
            return true;
        }
        AccountLog.w("UDevIdClient", "component not found");
        return false;
    }

    /* loaded from: classes4.dex */
    private static abstract class a<T> extends ServerServiceConnector<IUDevIdService, T, T> {
        private a(Context context, ClientFuture<T, T> clientFuture) {
            super(context, AccountIntent.ACTION_UDEVID_SERVICE, AccountIntent.PACKAGE_XIAOMI_ACCOUNT, clientFuture);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public final IUDevIdService binderToServiceType(IBinder iBinder) {
            return IUDevIdService.Stub.asInterface(iBinder);
        }
    }
}
