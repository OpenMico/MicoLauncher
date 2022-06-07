package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDeviceScannerCallback extends IInterface {
    void onEndpointFound(int i, int i2, String str) throws RemoteException;

    void onSuccessWrite(int i, int i2, int i3) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDeviceScannerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IDeviceScannerCallback");
        }

        public static IDeviceScannerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IDeviceScannerCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceScannerCallback)) {
                return new a(iBinder);
            }
            return (IDeviceScannerCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IDeviceScannerCallback");
                        onEndpointFound(parcel.readInt(), parcel.readInt(), parcel.readString());
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IDeviceScannerCallback");
                        onSuccessWrite(parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IDeviceScannerCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IDeviceScannerCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IDeviceScannerCallback
            public void onEndpointFound(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IDeviceScannerCallback");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IDeviceScannerCallback
            public void onSuccessWrite(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IDeviceScannerCallback");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
