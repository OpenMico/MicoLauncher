package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDeviceMessageListener extends IInterface {
    public static final int ERROR = -1;
    public static final int OK = 0;

    void onMessage(DpsCallbackData dpsCallbackData) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDeviceMessageListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IDeviceMessageListener");
        }

        public static IDeviceMessageListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IDeviceMessageListener");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceMessageListener)) {
                return new a(iBinder);
            }
            return (IDeviceMessageListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.mi_connect_service.IDeviceMessageListener");
                onMessage(parcel.readInt() != 0 ? DpsCallbackData.CREATOR.createFromParcel(parcel) : null);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IDeviceMessageListener");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IDeviceMessageListener {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IDeviceMessageListener
            public void onMessage(DpsCallbackData dpsCallbackData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IDeviceMessageListener");
                    if (dpsCallbackData != null) {
                        obtain.writeInt(1);
                        dpsCallbackData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
