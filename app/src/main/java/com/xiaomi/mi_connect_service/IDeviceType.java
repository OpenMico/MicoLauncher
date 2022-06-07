package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDeviceType extends IInterface {
    public static final int TYPE_CAMERA_DEVICE = 1;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDeviceType {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IDeviceType");
        }

        public static IDeviceType asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IDeviceType");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceType)) {
                return new a(iBinder);
            }
            return (IDeviceType) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("com.xiaomi.mi_connect_service.IDeviceType");
            return true;
        }

        /* loaded from: classes3.dex */
        private static class a implements IDeviceType {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }
        }
    }
}
