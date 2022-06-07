package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIPCDataCallback extends IInterface {
    void onAudioData(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException;

    void onVideoData(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IIPCDataCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IIPCDataCallback");
        }

        public static IIPCDataCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IIPCDataCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IIPCDataCallback)) {
                return new a(iBinder);
            }
            return (IIPCDataCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIPCDataCallback");
                        onVideoData(parcel.readString(), parcel.createByteArray(), parcel.createByteArray(), parcel.readInt());
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIPCDataCallback");
                        onAudioData(parcel.readString(), parcel.createByteArray(), parcel.createByteArray(), parcel.readInt());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IIPCDataCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IIPCDataCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IIPCDataCallback
            public void onVideoData(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIPCDataCallback");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIPCDataCallback
            public void onAudioData(String str, byte[] bArr, byte[] bArr2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIPCDataCallback");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
