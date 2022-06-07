package com.xiaomi.udevid;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IUDevIdService extends IInterface {
    Bundle getUDevIdFromSystemAccount(Bundle bundle) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IUDevIdService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.udevid.IUDevIdService");
        }

        public static IUDevIdService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.udevid.IUDevIdService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IUDevIdService)) {
                return new a(iBinder);
            }
            return (IUDevIdService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.udevid.IUDevIdService");
                Bundle uDevIdFromSystemAccount = getUDevIdFromSystemAccount(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                if (uDevIdFromSystemAccount != null) {
                    parcel2.writeInt(1);
                    uDevIdFromSystemAccount.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.xiaomi.udevid.IUDevIdService");
                return true;
            }
        }

        /* loaded from: classes4.dex */
        private static class a implements IUDevIdService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.udevid.IUDevIdService
            public Bundle getUDevIdFromSystemAccount(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.udevid.IUDevIdService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
