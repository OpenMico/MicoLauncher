package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIDMServiceProcCallback extends IInterface {
    void onAdvertisingResult(byte[] bArr) throws RemoteException;

    void onConnectServiceStatus(byte[] bArr) throws RemoteException;

    void onRequest(byte[] bArr) throws RemoteException;

    void onServiceChanged(byte[] bArr) throws RemoteException;

    int onSetEventCallback(byte[] bArr) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IIDMServiceProcCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
        }

        public static IIDMServiceProcCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IIDMServiceProcCallback)) {
                return new a(iBinder);
            }
            return (IIDMServiceProcCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                        onRequest(parcel.createByteArray());
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                        onConnectServiceStatus(parcel.createByteArray());
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                        onAdvertisingResult(parcel.createByteArray());
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                        int onSetEventCallback = onSetEventCallback(parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(onSetEventCallback);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                        onServiceChanged(parcel.createByteArray());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IIDMServiceProcCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
            public void onRequest(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
            public void onConnectServiceStatus(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
            public void onAdvertisingResult(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
            public int onSetEventCallback(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
            public void onServiceChanged(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMServiceProcCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
