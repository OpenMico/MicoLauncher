package com.xiaomi.mico.visual.algorithm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback;

/* loaded from: classes3.dex */
public interface IMicoCamera2Service extends IInterface {
    String getName() throws RemoteException;

    void registerCallback(IMicoCamera2ServiceCallback iMicoCamera2ServiceCallback) throws RemoteException;

    void requestStartAction(int i) throws RemoteException;

    void requestStopAction(int i) throws RemoteException;

    void unregisterCallback(IMicoCamera2ServiceCallback iMicoCamera2ServiceCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMicoCamera2Service {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
        }

        public static IMicoCamera2Service asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMicoCamera2Service)) {
                return new a(iBinder);
            }
            return (IMicoCamera2Service) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                        registerCallback(IMicoCamera2ServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                        unregisterCallback(IMicoCamera2ServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                        String name = getName();
                        parcel2.writeNoException();
                        parcel2.writeString(name);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                        requestStartAction(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                        requestStopAction(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IMicoCamera2Service {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mico.visual.algorithm.IMicoCamera2Service
            public void registerCallback(IMicoCamera2ServiceCallback iMicoCamera2ServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                    obtain.writeStrongBinder(iMicoCamera2ServiceCallback != null ? iMicoCamera2ServiceCallback.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.visual.algorithm.IMicoCamera2Service
            public void unregisterCallback(IMicoCamera2ServiceCallback iMicoCamera2ServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                    obtain.writeStrongBinder(iMicoCamera2ServiceCallback != null ? iMicoCamera2ServiceCallback.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.visual.algorithm.IMicoCamera2Service
            public String getName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.visual.algorithm.IMicoCamera2Service
            public void requestStartAction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                    obtain.writeInt(i);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.visual.algorithm.IMicoCamera2Service
            public void requestStopAction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.visual.algorithm.IMicoCamera2Service");
                    obtain.writeInt(i);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
