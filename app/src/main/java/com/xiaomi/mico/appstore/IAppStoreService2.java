package com.xiaomi.mico.appstore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mico.appstore.IAppStoreServiceCallback;

/* loaded from: classes3.dex */
public interface IAppStoreService2 extends IInterface {
    public static final int RET_FAILED = 1;
    public static final int RET_OK = 0;

    void getAppInfoListData(IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    void getAppInfoListDataFromServer(IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IAppStoreService2 {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.appstore.IAppStoreService2");
        }

        public static IAppStoreService2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.appstore.IAppStoreService2");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAppStoreService2)) {
                return new a(iBinder);
            }
            return (IAppStoreService2) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppStoreService2");
                        getAppInfoListData(IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mico.appstore.IAppStoreService2");
                        getAppInfoListDataFromServer(IAppStoreServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mico.appstore.IAppStoreService2");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IAppStoreService2 {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mico.appstore.IAppStoreService2
            public void getAppInfoListData(IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppStoreService2");
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.appstore.IAppStoreService2
            public void getAppInfoListDataFromServer(IAppStoreServiceCallback iAppStoreServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.appstore.IAppStoreService2");
                    obtain.writeStrongBinder(iAppStoreServiceCallback != null ? iAppStoreServiceCallback.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
