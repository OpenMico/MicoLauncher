package com.xiaomi.accounts;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.accounts.IAccountAuthenticatorResponse;

/* loaded from: classes2.dex */
public interface IAccountAuthenticator extends IInterface {
    void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException;

    void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException;

    void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException;

    void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException;

    void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IAccountAuthenticator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.accounts.IAccountAuthenticator");
        }

        public static IAccountAuthenticator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.accounts.IAccountAuthenticator");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAccountAuthenticator)) {
                return new a(iBinder);
            }
            return (IAccountAuthenticator) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                Bundle bundle = null;
                Account account = null;
                Account account2 = null;
                Bundle bundle2 = null;
                Bundle bundle3 = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        addAccount(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.createStringArray(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        IAccountAuthenticatorResponse asInterface = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                        Account account3 = parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        confirmCredentials(asInterface, account3, bundle);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        IAccountAuthenticatorResponse asInterface2 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                        Account account4 = parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null;
                        String readString = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle3 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        getAuthToken(asInterface2, account4, readString, bundle3);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        getAuthTokenLabel(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        IAccountAuthenticatorResponse asInterface3 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                        Account account5 = parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null;
                        String readString2 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle2 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        updateCredentials(asInterface3, account5, readString2, bundle2);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        editProperties(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        IAccountAuthenticatorResponse asInterface4 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.readInt() != 0) {
                            account2 = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        hasFeatures(asInterface4, account2, parcel.createStringArray());
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.accounts.IAccountAuthenticator");
                        IAccountAuthenticatorResponse asInterface5 = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        getAccountRemovalAllowed(asInterface5, account);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.accounts.IAccountAuthenticator");
                return true;
            }
        }

        /* loaded from: classes2.dex */
        private static class a implements IAccountAuthenticator {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.a.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.accounts.IAccountAuthenticator");
                    obtain.writeStrongBinder(iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
