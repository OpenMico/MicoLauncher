package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoundBoxWhiteNameCallBack extends IInterface {
    int onWriteWhiteName(String str) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISoundBoxWhiteNameCallBack {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack");
        }

        public static ISoundBoxWhiteNameCallBack asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ISoundBoxWhiteNameCallBack)) {
                return new a(iBinder);
            }
            return (ISoundBoxWhiteNameCallBack) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack");
                int onWriteWhiteName = onWriteWhiteName(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(onWriteWhiteName);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements ISoundBoxWhiteNameCallBack {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack
            public int onWriteWhiteName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack");
                    obtain.writeString(str);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
