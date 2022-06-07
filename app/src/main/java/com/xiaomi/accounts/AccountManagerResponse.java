package com.xiaomi.accounts;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.accounts.IAccountManagerResponse;

/* loaded from: classes2.dex */
public class AccountManagerResponse implements Parcelable {
    public static final Parcelable.Creator<AccountManagerResponse> CREATOR = new Parcelable.Creator<AccountManagerResponse>() { // from class: com.xiaomi.accounts.AccountManagerResponse.1
        /* renamed from: a */
        public AccountManagerResponse createFromParcel(Parcel parcel) {
            return new AccountManagerResponse(parcel);
        }

        /* renamed from: a */
        public AccountManagerResponse[] newArray(int i) {
            return new AccountManagerResponse[i];
        }
    };
    private IAccountManagerResponse a;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccountManagerResponse(IAccountManagerResponse iAccountManagerResponse) {
        this.a = iAccountManagerResponse;
    }

    public AccountManagerResponse(Parcel parcel) {
        this.a = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(Bundle bundle) {
        if (Log.isLoggable("AccountAuthenticator", 2)) {
            bundle.keySet();
            Log.v("AccountAuthenticator", "AccountAuthenticatorResponse.onResult");
        }
        try {
            this.a.onResult(bundle);
        } catch (RemoteException unused) {
        }
    }

    public void onRequestContinued() {
        if (Log.isLoggable("AccountAuthenticator", 2)) {
            Log.v("AccountAuthenticator", "AccountAuthenticatorResponse.onRequestContinued");
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
