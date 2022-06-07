package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIDMClientCallback extends IInterface {
    void onAccountChanged(byte[] bArr) throws RemoteException;

    void onDiscoveryResult(byte[] bArr) throws RemoteException;

    void onEvent(byte[] bArr) throws RemoteException;

    void onInvitationAccepted(byte[] bArr) throws RemoteException;

    void onInviteConnection(byte[] bArr) throws RemoteException;

    void onResponse(byte[] bArr) throws RemoteException;

    void onServiceConnectStatus(byte[] bArr) throws RemoteException;

    void onServiceFound(byte[] bArr) throws RemoteException;

    void onServiceLost(byte[] bArr) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IIDMClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IIDMClientCallback");
        }

        public static IIDMClientCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IIDMClientCallback)) {
                return new a(iBinder);
            }
            return (IIDMClientCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onServiceFound(parcel.createByteArray());
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onResponse(parcel.createByteArray());
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onEvent(parcel.createByteArray());
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onServiceConnectStatus(parcel.createByteArray());
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onServiceLost(parcel.createByteArray());
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onInviteConnection(parcel.createByteArray());
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onInvitationAccepted(parcel.createByteArray());
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onDiscoveryResult(parcel.createByteArray());
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IIDMClientCallback");
                        onAccountChanged(parcel.createByteArray());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IIDMClientCallback");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IIDMClientCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onServiceFound(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onResponse(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onEvent(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onServiceConnectStatus(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onServiceLost(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onInviteConnection(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onInvitationAccepted(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onDiscoveryResult(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onAccountChanged(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IIDMClientCallback");
                    obtain.writeByteArray(bArr);
                    this.a.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
