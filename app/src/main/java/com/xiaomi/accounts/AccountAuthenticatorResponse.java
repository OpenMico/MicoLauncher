package com.xiaomi.accounts;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.accounts.IAccountAuthenticatorResponse;
import com.xiaomi.accountsdk.utils.AccountLog;

/* loaded from: classes2.dex */
public class AccountAuthenticatorResponse implements Parcelable {
    public static final Parcelable.Creator<AccountAuthenticatorResponse> CREATOR = new Parcelable.Creator<AccountAuthenticatorResponse>() { // from class: com.xiaomi.accounts.AccountAuthenticatorResponse.1
        /* renamed from: a */
        public AccountAuthenticatorResponse createFromParcel(Parcel parcel) {
            return new AccountAuthenticatorResponse(parcel);
        }

        /* renamed from: a */
        public AccountAuthenticatorResponse[] newArray(int i) {
            return new AccountAuthenticatorResponse[i];
        }
    };
    private IAccountAuthenticatorResponse a;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccountAuthenticatorResponse(IAccountAuthenticatorResponse iAccountAuthenticatorResponse) {
        this.a = iAccountAuthenticatorResponse;
    }

    public AccountAuthenticatorResponse(Parcel parcel) {
        this.a = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(Bundle bundle) {
        if (Log.isLoggable("AccountAuthenticator", 2)) {
            bundle.keySet();
            AccountLog.v("AccountAuthenticator", "AccountAuthenticatorResponse.onResult: " + AccountManager.sanitizeResult(bundle));
        }
        try {
            this.a.onResult(bundle);
        } catch (RemoteException unused) {
        }
    }

    public void onRequestContinued() {
        if (Log.isLoggable("AccountAuthenticator", 2)) {
            AccountLog.v("AccountAuthenticator", "AccountAuthenticatorResponse.onRequestContinued");
        }
        try {
            this.a.onRequestContinued();
        } catch (RemoteException unused) {
        }
    }

    public void onError(int i, String str) {
        if (Log.isLoggable("AccountAuthenticator", 2)) {
            Log.v("AccountAuthenticator", "AccountAuthenticatorResponse.onError: " + i + ", " + str);
        }
        try {
            this.a.onError(i, str);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.a.asBinder());
    }
}
