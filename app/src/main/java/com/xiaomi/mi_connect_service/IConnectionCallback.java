package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IConnectionCallback extends IInterface {
    void onDisconnected() throws RemoteException;

    void onFailure(byte[] bArr) throws RemoteException;

    void onSuccess(byte[] bArr) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IConnectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IConnectionCallback");
        }

        public static IConnectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IConnectionCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IConnectionCallback)) {
                return new a(iBinder);
            }
            return (IConnectionCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IConnectionCallback");
                        onSuccess(parcel.createByteArray());
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IConnectionCallback");
                        onFailure(parcel.createByteArray());
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IConnectionCallback");
                        onDisconnected();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IConnectionCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IConnectionCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IConnectionCallback
            public void onSuccess(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IConnectionCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IConnectionCallback
            public void onFailure(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IConnectionCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IConnectionCallback
            public void onDisconnected() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IConnectionCallback");
                    this.a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
