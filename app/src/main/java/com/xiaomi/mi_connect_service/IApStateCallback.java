package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IApStateCallback extends IInterface {
    void onApStartResult(boolean z) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IApStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IApStateCallback");
        }

        public static IApStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IApStateCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IApStateCallback)) {
                return new a(iBinder);
            }
            return (IApStateCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.mi_connect_service.IApStateCallback");
                onApStartResult(parcel.readInt() != 0);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IApStateCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IApStateCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IApStateCallback
            public void onApStartResult(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IApStateCallback");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
