package com.xiaomi.mesh.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mesh.provision.MiotBindDevicesResult;
import com.xiaomi.mesh.provision.MiotDeviceScanResult;

/* loaded from: classes3.dex */
public interface IMiotMeshServiceCallBack extends IInterface {
    WiFiInfo getWiFiInfo() throws RemoteException;

    void reportAck(int i, String str) throws RemoteException;

    void reportBindDevicesResult(MiotBindDevicesResult miotBindDevicesResult) throws RemoteException;

    void reportScanDevicesResult(MiotDeviceScanResult miotDeviceScanResult) throws RemoteException;

    void saveCurrentWiFi() throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiotMeshServiceCallBack {
        private static final String DESCRIPTOR = "com.xiaomi.mesh.internal.IMiotMeshServiceCallBack";
        static final int TRANSACTION_getWiFiInfo = 4;
        static final int TRANSACTION_reportAck = 2;
        static final int TRANSACTION_reportBindDevicesResult = 3;
        static final int TRANSACTION_reportScanDevicesResult = 1;
        static final int TRANSACTION_saveCurrentWiFi = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMiotMeshServiceCallBack asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiotMeshServiceCallBack)) {
                return new Proxy(iBinder);
            }
            return (IMiotMeshServiceCallBack) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                MiotDeviceScanResult miotDeviceScanResult = null;
                MiotBindDevicesResult miotBindDevicesResult = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            miotDeviceScanResult = MiotDeviceScanResult.CREATOR.createFromParcel(parcel);
                        }
                        reportScanDevicesResult(miotDeviceScanResult);
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        reportAck(parcel.readInt(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            miotBindDevicesResult = MiotBindDevicesResult.CREATOR.createFromParcel(parcel);
                        }
                        reportBindDevicesResult(miotBindDevicesResult);
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        WiFiInfo wiFiInfo = getWiFiInfo();
                        parcel2.writeNoException();
                        if (wiFiInfo != null) {
                            parcel2.writeInt(1);
                            wiFiInfo.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        saveCurrentWiFi();
                        parcel2.writeNoException();
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
        private static class Proxy implements IMiotMeshServiceCallBack {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
            public void reportScanDevicesResult(MiotDeviceScanResult miotDeviceScanResult) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (miotDeviceScanResult != null) {
                        obtain.writeInt(1);
                        miotDeviceScanResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
            public void reportAck(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
            public void reportBindDevicesResult(MiotBindDevicesResult miotBindDevicesResult) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (miotBindDevicesResult != null) {
                        obtain.writeInt(1);
                        miotBindDevicesResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
            public WiFiInfo getWiFiInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? WiFiInfo.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
            public void saveCurrentWiFi() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
