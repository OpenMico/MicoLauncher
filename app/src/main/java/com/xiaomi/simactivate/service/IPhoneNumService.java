package com.xiaomi.simactivate.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IPhoneNumService extends IInterface {
    Bundle blockObtainPhoneNum(int i, String str, int i2, boolean z) throws RemoteException;

    boolean invalidatePhoneNum(int i, String str, int i2) throws RemoteException;

    boolean invalidateVerifiedToken(int i, String str, int i2) throws RemoteException;

    boolean isSupport(int i, String str, String str2) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IPhoneNumService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.simactivate.service.IPhoneNumService");
        }

        public static IPhoneNumService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.simactivate.service.IPhoneNumService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IPhoneNumService)) {
                return new a(iBinder);
            }
            return (IPhoneNumService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.simactivate.service.IPhoneNumService");
                        boolean isSupport = isSupport(parcel.readInt(), parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(isSupport ? 1 : 0);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.simactivate.service.IPhoneNumService");
                        Bundle blockObtainPhoneNum = blockObtainPhoneNum(parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt() != 0);
                        parcel2.writeNoException();
                        if (blockObtainPhoneNum != null) {
                            parcel2.writeInt(1);
                            blockObtainPhoneNum.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.simactivate.service.IPhoneNumService");
                        boolean invalidatePhoneNum = invalidatePhoneNum(parcel.readInt(), parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(invalidatePhoneNum ? 1 : 0);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.simactivate.service.IPhoneNumService");
                        boolean invalidateVerifiedToken = invalidateVerifiedToken(parcel.readInt(), parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(invalidateVerifiedToken ? 1 : 0);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.simactivate.service.IPhoneNumService");
                return true;
            }
        }

        /* loaded from: classes4.dex */
        private static class a implements IPhoneNumService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public boolean isSupport(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.simactivate.service.IPhoneNumService");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    boolean z = false;
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public Bundle blockObtainPhoneNum(int i, String str, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.simactivate.service.IPhoneNumService");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public boolean invalidatePhoneNum(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.simactivate.service.IPhoneNumService");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    boolean z = false;
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public boolean invalidateVerifiedToken(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.simactivate.service.IPhoneNumService");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    boolean z = false;
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
