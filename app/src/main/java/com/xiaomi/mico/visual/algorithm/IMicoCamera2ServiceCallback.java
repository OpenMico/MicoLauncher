package com.xiaomi.mico.visual.algorithm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMicoCamera2ServiceCallback extends IInterface {
    void onResultCallBack(CallBackResult callBackResult) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMicoCamera2ServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback");
        }

        public static IMicoCamera2ServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMicoCamera2ServiceCallback)) {
                return new a(iBinder);
            }
            return (IMicoCamera2ServiceCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback");
                onResultCallBack(parcel.readInt() != 0 ? CallBackResult.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IMicoCamera2ServiceCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback
            public void onResultCallBack(CallBackResult callBackResult) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback");
                    if (callBackResult != null) {
                        obtain.writeInt(1);
                        callBackResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
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
