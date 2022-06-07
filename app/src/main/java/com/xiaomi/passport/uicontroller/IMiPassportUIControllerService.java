package com.xiaomi.passport.uicontroller;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.MiLoginResult;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.account.data.NotificationLoginEndParams;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;

/* loaded from: classes4.dex */
public interface IMiPassportUIControllerService extends IInterface {
    void addOrUpdateAccountManager(AccountInfo accountInfo) throws RemoteException;

    MiLoginResult loginByPassword(PasswordLoginParams passwordLoginParams) throws RemoteException;

    MiLoginResult loginByStep2(Step2LoginParams step2LoginParams) throws RemoteException;

    NotificationAuthResult onNotificationAuthEnd(String str) throws RemoteException;

    MiLoginResult onNotificationLoginEnd(NotificationLoginEndParams notificationLoginEndParams) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IMiPassportUIControllerService {
        private static final String DESCRIPTOR = "com.xiaomi.passport.uicontroller.IMiPassportUIControllerService";
        static final int TRANSACTION_addOrUpdateAccountManager = 4;
        static final int TRANSACTION_loginByPassword = 1;
        static final int TRANSACTION_loginByStep2 = 3;
        static final int TRANSACTION_onNotificationAuthEnd = 5;
        static final int TRANSACTION_onNotificationLoginEnd = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMiPassportUIControllerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPassportUIControllerService)) {
                return new Proxy(iBinder);
            }
            return (IMiPassportUIControllerService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                PasswordLoginParams passwordLoginParams = null;
                AccountInfo accountInfo = null;
                Step2LoginParams step2LoginParams = null;
                NotificationLoginEndParams notificationLoginEndParams = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            passwordLoginParams = PasswordLoginParams.CREATOR.createFromParcel(parcel);
                        }
                        MiLoginResult loginByPassword = loginByPassword(passwordLoginParams);
                        parcel2.writeNoException();
                        if (loginByPassword != null) {
                            parcel2.writeInt(1);
                            loginByPassword.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            notificationLoginEndParams = NotificationLoginEndParams.CREATOR.createFromParcel(parcel);
                        }
                        MiLoginResult onNotificationLoginEnd = onNotificationLoginEnd(notificationLoginEndParams);
                        parcel2.writeNoException();
                        if (onNotificationLoginEnd != null) {
                            parcel2.writeInt(1);
                            onNotificationLoginEnd.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            step2LoginParams = Step2LoginParams.CREATOR.createFromParcel(parcel);
                        }
                        MiLoginResult loginByStep2 = loginByStep2(step2LoginParams);
                        parcel2.writeNoException();
                        if (loginByStep2 != null) {
                            parcel2.writeInt(1);
                            loginByStep2.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            accountInfo = AccountInfo.CREATOR.createFromParcel(parcel);
                        }
                        addOrUpdateAccountManager(accountInfo);
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        NotificationAuthResult onNotificationAuthEnd = onNotificationAuthEnd(parcel.readString());
                        parcel2.writeNoException();
                        if (onNotificationAuthEnd != null) {
                            parcel2.writeInt(1);
                            onNotificationAuthEnd.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IMiPassportUIControllerService {
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

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public MiLoginResult loginByPassword(PasswordLoginParams passwordLoginParams) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (passwordLoginParams != null) {
                        obtain.writeInt(1);
                        passwordLoginParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? MiLoginResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public MiLoginResult onNotificationLoginEnd(NotificationLoginEndParams notificationLoginEndParams) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (notificationLoginEndParams != null) {
                        obtain.writeInt(1);
                        notificationLoginEndParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? MiLoginResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public MiLoginResult loginByStep2(Step2LoginParams step2LoginParams) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (step2LoginParams != null) {
                        obtain.writeInt(1);
                        step2LoginParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? MiLoginResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public void addOrUpdateAccountManager(AccountInfo accountInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accountInfo != null) {
                        obtain.writeInt(1);
                        accountInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public NotificationAuthResult onNotificationAuthEnd(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? NotificationAuthResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
