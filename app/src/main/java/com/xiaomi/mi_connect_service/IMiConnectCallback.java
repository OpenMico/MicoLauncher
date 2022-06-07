package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMiConnectCallback extends IInterface {
    void onAdvertisingResult(int i, int i2) throws RemoteException;

    void onConnectionInitiated(int i, int i2, String str, byte[] bArr, byte[] bArr2) throws RemoteException;

    void onConnectionResult(int i, int i2, String str, int i3) throws RemoteException;

    void onDisconnection(int i, int i2) throws RemoteException;

    void onDiscoveryResult(int i, int i2) throws RemoteException;

    void onEndpointFound(int i, int i2, String str, byte[] bArr) throws RemoteException;

    void onEndpointLost(int i, int i2, String str) throws RemoteException;

    void onPayloadReceived(int i, int i2, byte[] bArr) throws RemoteException;

    void onPayloadSentResult(int i, int i2, int i3) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiConnectCallback {
        private static final String DESCRIPTOR = "com.xiaomi.mi_connect_service.IMiConnectCallback";
        static final int TRANSACTION_onAdvertisingResult = 1;
        static final int TRANSACTION_onConnectionInitiated = 4;
        static final int TRANSACTION_onConnectionResult = 5;
        static final int TRANSACTION_onDisconnection = 6;
        static final int TRANSACTION_onDiscoveryResult = 2;
        static final int TRANSACTION_onEndpointFound = 3;
        static final int TRANSACTION_onEndpointLost = 9;
        static final int TRANSACTION_onPayloadReceived = 8;
        static final int TRANSACTION_onPayloadSentResult = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMiConnectCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiConnectCallback)) {
                return new a(iBinder);
            }
            return (IMiConnectCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        onAdvertisingResult(parcel.readInt(), parcel.readInt());
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        onDiscoveryResult(parcel.readInt(), parcel.readInt());
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        onEndpointFound(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.createByteArray());
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        onConnectionInitiated(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.createByteArray(), parcel.createByteArray());
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        onConnectionResult(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt());
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        onDisconnection(parcel.readInt(), parcel.readInt());
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        onPayloadSentResult(parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        onPayloadReceived(parcel.readInt(), parcel.readInt(), parcel.createByteArray());
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        onEndpointLost(parcel.readInt(), parcel.readInt(), parcel.readString());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IMiConnectCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onAdvertisingResult(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onDiscoveryResult(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onEndpointFound(int i, int i2, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onConnectionInitiated(int i, int i2, String str, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.a.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onConnectionResult(int i, int i2, String str, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.a.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onDisconnection(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.a.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onPayloadSentResult(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.a.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onPayloadReceived(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.a.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
            public void onEndpointLost(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.a.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
