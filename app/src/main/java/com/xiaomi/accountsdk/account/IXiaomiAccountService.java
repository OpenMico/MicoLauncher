package com.xiaomi.accountsdk.account;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IXiaomiAccountService extends IInterface {
    String getAccessToken(Account account, String str, String str2, boolean z) throws RemoteException;

    ParcelFileDescriptor getAvatarFd(Account account) throws RemoteException;

    ParcelFileDescriptor getAvatarFdByFileName(String str) throws RemoteException;

    String getEmail(Account account) throws RemoteException;

    String getEncryptedUserId(Account account) throws RemoteException;

    String getNickName(Account account) throws RemoteException;

    String getPhone(Account account) throws RemoteException;

    String getSnsAccessToken(String str) throws RemoteException;

    String getUserName(Account account) throws RemoteException;

    XiaomiAccount getXiaomiAccount(boolean z, Account account) throws RemoteException;

    boolean invalidateSnsAccessToken(String str, String str2) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IXiaomiAccountService {
        private static final String DESCRIPTOR = "com.xiaomi.accountsdk.account.IXiaomiAccountService";
        static final int TRANSACTION_getAccessToken = 9;
        static final int TRANSACTION_getAvatarFd = 6;
        static final int TRANSACTION_getAvatarFdByFileName = 8;
        static final int TRANSACTION_getEmail = 3;
        static final int TRANSACTION_getEncryptedUserId = 4;
        static final int TRANSACTION_getNickName = 2;
        static final int TRANSACTION_getPhone = 5;
        static final int TRANSACTION_getSnsAccessToken = 10;
        static final int TRANSACTION_getUserName = 1;
        static final int TRANSACTION_getXiaomiAccount = 7;
        static final int TRANSACTION_invalidateSnsAccessToken = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IXiaomiAccountService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IXiaomiAccountService)) {
                return new Proxy(iBinder);
            }
            return (IXiaomiAccountService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                boolean z = false;
                Account account = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        String userName = getUserName(account);
                        parcel2.writeNoException();
                        parcel2.writeString(userName);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        String nickName = getNickName(account);
                        parcel2.writeNoException();
                        parcel2.writeString(nickName);
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        String email = getEmail(account);
                        parcel2.writeNoException();
                        parcel2.writeString(email);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        String encryptedUserId = getEncryptedUserId(account);
                        parcel2.writeNoException();
                        parcel2.writeString(encryptedUserId);
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        String phone = getPhone(account);
                        parcel2.writeNoException();
                        parcel2.writeString(phone);
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        ParcelFileDescriptor avatarFd = getAvatarFd(account);
                        parcel2.writeNoException();
                        if (avatarFd != null) {
                            parcel2.writeInt(1);
                            avatarFd.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean z2 = parcel.readInt() != 0;
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        XiaomiAccount xiaomiAccount = getXiaomiAccount(z2, account);
                        parcel2.writeNoException();
                        if (xiaomiAccount != null) {
                            parcel2.writeInt(1);
                            xiaomiAccount.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        ParcelFileDescriptor avatarFdByFileName = getAvatarFdByFileName(parcel.readString());
                        parcel2.writeNoException();
                        if (avatarFdByFileName != null) {
                            parcel2.writeInt(1);
                            avatarFdByFileName.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            account = (Account) Account.CREATOR.createFromParcel(parcel);
                        }
                        String readString = parcel.readString();
                        String readString2 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        String accessToken = getAccessToken(account, readString, readString2, z);
                        parcel2.writeNoException();
                        parcel2.writeString(accessToken);
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        String snsAccessToken = getSnsAccessToken(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(snsAccessToken);
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean invalidateSnsAccessToken = invalidateSnsAccessToken(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(invalidateSnsAccessToken ? 1 : 0);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* loaded from: classes2.dex */
        private static class Proxy implements IXiaomiAccountService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getUserName(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getNickName(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getEmail(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getEncryptedUserId(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getPhone(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public ParcelFileDescriptor getAvatarFd(Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public XiaomiAccount getXiaomiAccount(boolean z, Account account) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? XiaomiAccount.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public ParcelFileDescriptor getAvatarFdByFileName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getAccessToken(Account account, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int i = 1;
                    if (account != null) {
                        obtain.writeInt(1);
                        account.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getSnsAccessToken(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public boolean invalidateSnsAccessToken(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    boolean z = false;
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
