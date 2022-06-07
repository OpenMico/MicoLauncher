package com.xiaomi.mesh.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mesh.internal.IMeshLogListener;
import com.xiaomi.mesh.internal.IMeshStatusListener;
import com.xiaomi.mesh.internal.IMiotMeshServiceCallBack;
import com.xiaomi.mesh.internal.IOtSendListener;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import java.util.List;

/* loaded from: classes3.dex */
public interface IMiotMeshService extends IInterface {
    int getBindSingleDeviceTimeout() throws RemoteException;

    int getMeshInitDoneResult() throws RemoteException;

    int getProvisionState() throws RemoteException;

    int getScanDeviceTimeout() throws RemoteException;

    void init(boolean z, List<MiotMeshDeviceItem> list) throws RemoteException;

    void quit() throws RemoteException;

    void receiveBrainMessage(String str, String str2) throws RemoteException;

    void receiveOtMessage(String str) throws RemoteException;

    void registerMeshLogListener(IMeshLogListener iMeshLogListener) throws RemoteException;

    void registerMiotMeshServiceCallBack(IMiotMeshServiceCallBack iMiotMeshServiceCallBack) throws RemoteException;

    void registerMiotMeshStatusListener(IMeshStatusListener iMeshStatusListener) throws RemoteException;

    void registerOtSendListener(IOtSendListener iOtSendListener) throws RemoteException;

    void setBleDebugLog(boolean z) throws RemoteException;

    void setProvisionState(int i) throws RemoteException;

    void start() throws RemoteException;

    void stop() throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiotMeshService {
        private static final String DESCRIPTOR = "com.xiaomi.mesh.internal.IMiotMeshService";
        static final int TRANSACTION_getBindSingleDeviceTimeout = 15;
        static final int TRANSACTION_getMeshInitDoneResult = 11;
        static final int TRANSACTION_getProvisionState = 12;
        static final int TRANSACTION_getScanDeviceTimeout = 14;
        static final int TRANSACTION_init = 10;
        static final int TRANSACTION_quit = 8;
        static final int TRANSACTION_receiveBrainMessage = 4;
        static final int TRANSACTION_receiveOtMessage = 2;
        static final int TRANSACTION_registerMeshLogListener = 16;
        static final int TRANSACTION_registerMiotMeshServiceCallBack = 3;
        static final int TRANSACTION_registerMiotMeshStatusListener = 9;
        static final int TRANSACTION_registerOtSendListener = 1;
        static final int TRANSACTION_setBleDebugLog = 5;
        static final int TRANSACTION_setProvisionState = 13;
        static final int TRANSACTION_start = 6;
        static final int TRANSACTION_stop = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMiotMeshService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiotMeshService)) {
                return new Proxy(iBinder);
            }
            return (IMiotMeshService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerOtSendListener(IOtSendListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        receiveOtMessage(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMiotMeshServiceCallBack(IMiotMeshServiceCallBack.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        receiveBrainMessage(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        setBleDebugLog(z);
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        start();
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        stop();
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        quit();
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMiotMeshStatusListener(IMeshStatusListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        init(z, parcel.createTypedArrayList(MiotMeshDeviceItem.CREATOR));
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        int meshInitDoneResult = getMeshInitDoneResult();
                        parcel2.writeNoException();
                        parcel2.writeInt(meshInitDoneResult);
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        int provisionState = getProvisionState();
                        parcel2.writeNoException();
                        parcel2.writeInt(provisionState);
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        setProvisionState(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        int scanDeviceTimeout = getScanDeviceTimeout();
                        parcel2.writeNoException();
                        parcel2.writeInt(scanDeviceTimeout);
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        int bindSingleDeviceTimeout = getBindSingleDeviceTimeout();
                        parcel2.writeNoException();
                        parcel2.writeInt(bindSingleDeviceTimeout);
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMeshLogListener(IMeshLogListener.Stub.asInterface(parcel.readStrongBinder()));
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

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class Proxy implements IMiotMeshService {
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

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void registerOtSendListener(IOtSendListener iOtSendListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iOtSendListener != null ? iOtSendListener.asBinder() : null);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void receiveOtMessage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void registerMiotMeshServiceCallBack(IMiotMeshServiceCallBack iMiotMeshServiceCallBack) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMiotMeshServiceCallBack != null ? iMiotMeshServiceCallBack.asBinder() : null);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void receiveBrainMessage(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void setBleDebugLog(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void start() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void quit() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void registerMiotMeshStatusListener(IMeshStatusListener iMeshStatusListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMeshStatusListener != null ? iMeshStatusListener.asBinder() : null);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void init(boolean z, List<MiotMeshDeviceItem> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeTypedList(list);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public int getMeshInitDoneResult() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public int getProvisionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void setProvisionState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public int getScanDeviceTimeout() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public int getBindSingleDeviceTimeout() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mesh.internal.IMiotMeshService
            public void registerMeshLogListener(IMeshLogListener iMeshLogListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMeshLogListener != null ? iMeshLogListener.asBinder() : null);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
