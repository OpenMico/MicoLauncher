package com.xiaomi.passport;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;

/* loaded from: classes4.dex */
public interface IPassportServiceTokenService extends IInterface {
    boolean fastCheckSlhPhCompatibility() throws RemoteException;

    XmAccountVisibility getAccountVisible(String str) throws RemoteException;

    ServiceTokenResult getServiceToken(String str) throws RemoteException;

    ServiceTokenResult invalidateServiceToken(ServiceTokenResult serviceTokenResult) throws RemoteException;

    XmAccountVisibility setAccountVisible(String str) throws RemoteException;

    boolean supportAccessAccount() throws RemoteException;

    boolean supportServiceTokenUIResponse() throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IPassportServiceTokenService {
        private static final String DESCRIPTOR = "com.xiaomi.passport.IPassportServiceTokenService";
        static final int TRANSACTION_fastCheckSlhPhCompatibility = 4;
        static final int TRANSACTION_getAccountVisible = 7;
        static final int TRANSACTION_getServiceToken = 1;
        static final int TRANSACTION_invalidateServiceToken = 2;
        static final int TRANSACTION_setAccountVisible = 6;
        static final int TRANSACTION_supportAccessAccount = 5;
        static final int TRANSACTION_supportServiceTokenUIResponse = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPassportServiceTokenService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IPassportServiceTokenService)) {
                return new Proxy(iBinder);
            }
            return (IPassportServiceTokenService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        ServiceTokenResult serviceToken = getServiceToken(parcel.readString());
                        parcel2.writeNoException();
                        if (serviceToken != null) {
                            parcel2.writeInt(1);
                            serviceToken.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        ServiceTokenResult invalidateServiceToken = invalidateServiceToken(parcel.readInt() != 0 ? ServiceTokenResult.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        if (invalidateServiceToken != null) {
                            parcel2.writeInt(1);
                            invalidateServiceToken.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean supportServiceTokenUIResponse = supportServiceTokenUIResponse();
                        parcel2.writeNoException();
                        parcel2.writeInt(supportServiceTokenUIResponse ? 1 : 0);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean fastCheckSlhPhCompatibility = fastCheckSlhPhCompatibility();
                        parcel2.writeNoException();
                        parcel2.writeInt(fastCheckSlhPhCompatibility ? 1 : 0);
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean supportAccessAccount = supportAccessAccount();
                        parcel2.writeNoException();
                        parcel2.writeInt(supportAccessAccount ? 1 : 0);
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        XmAccountVisibility accountVisible = setAccountVisible(parcel.readString());
                        parcel2.writeNoException();
                        if (accountVisible != null) {
                            parcel2.writeInt(1);
                            accountVisible.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        XmAccountVisibility accountVisible2 = getAccountVisible(parcel.readString());
                        parcel2.writeNoException();
                        if (accountVisible2 != null) {
                            parcel2.writeInt(1);
                            accountVisible2.writeToParcel(parcel2, 1);
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
        private static class Proxy implements IPassportServiceTokenService {
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

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public ServiceTokenResult getServiceToken(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? ServiceTokenResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public ServiceTokenResult invalidateServiceToken(ServiceTokenResult serviceTokenResult) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (serviceTokenResult != null) {
                        obtain.writeInt(1);
                        serviceTokenResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? ServiceTokenResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public boolean supportServiceTokenUIResponse() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(3, obtain, obtain2, 0);
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

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public boolean fastCheckSlhPhCompatibility() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(4, obtain, obtain2, 0);
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

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public boolean supportAccessAccount() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(5, obtain, obtain2, 0);
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

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public XmAccountVisibility setAccountVisible(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? XmAccountVisibility.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public XmAccountVisibility getAccountVisible(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? XmAccountVisibility.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
