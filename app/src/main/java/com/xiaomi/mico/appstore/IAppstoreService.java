package com.xiaomi.mico.appstore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mico.appstore.IAppStoreServiceCallback;

/* loaded from: classes3.dex */
public interface IAppstoreService extends IInterface {
    public static final int RET_FAILED = 1;
    public static final int RET_NOT_ENOUGH_SPACE = 2;
    public static final int RET_OK = 0;

    void clearAppCache(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void initAppStore() throws RemoteException;

    void installApp(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void runAppStoreTimer(boolean z, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void setPreference(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void silentInstallApp(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void silentUninstallApp(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void silentUpgradeApps(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void updateAppCacheSize(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void updateAppSize(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IAppstoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.appstore.IAppstoreService");
        }

        public static IAppstoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.appstore.IAppstoreService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAppstoreService)) {
                return new a(iBinder);
            }
            return (IAppstoreService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        initAppStore();
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        clearAppCache(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        updateAppSize(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        updateAppCacheSize(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        installApp(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        silentInstallApp(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        silentUninstallApp(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        runAppStoreTimer(parcel.readInt() != 0, IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        silentUpgradeApps(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppstoreService");
                        setPreference(parcel.readString(), IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mico.appstore.IAppstoreService");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IAppstoreService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void initAppStore() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void clearAppCache(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void updateAppSize(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void updateAppCacheSize(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void installApp(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void silentInstallApp(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void silentUninstallApp(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void runAppStoreTimer(boolean z, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void silentUpgradeApps(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppstoreService
            public void setPreference(String str, IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppstoreService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
