package com.xiaomi.mico.mqtt.service.iface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIdentifyDeviceCallback extends IInterface {
    void call(boolean z, String str) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IIdentifyDeviceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.mqtt.service.iface.IIdentifyDeviceCallback");
        }

        public static IIdentifyDeviceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.mqtt.service.iface.IIdentifyDeviceCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IIdentifyDeviceCallback)) {
                return new a(iBinder);
            }
            return (IIdentifyDeviceCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IIdentifyDeviceCallback");
                call(parcel.readInt() != 0, parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.xiaomi.mico.mqtt.service.iface.IIdentifyDeviceCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IIdentifyDeviceCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IIdentifyDeviceCallback
            public void call(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IIdentifyDeviceCallback");
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeString(str);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
