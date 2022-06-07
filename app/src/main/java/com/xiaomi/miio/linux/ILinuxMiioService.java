package com.xiaomi.miio.linux;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILinuxMiioService extends IInterface {
    int Destroy() throws RemoteException;

    int Initialize() throws RemoteException;

    int Start(String str) throws RemoteException;

    int Stop() throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ILinuxMiioService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miio.linux.ILinuxMiioService");
        }

        public static ILinuxMiioService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miio.linux.ILinuxMiioService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ILinuxMiioService)) {
                return new a(iBinder);
            }
            return (ILinuxMiioService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miio.linux.ILinuxMiioService");
                        int Initialize = Initialize();
                        parcel2.writeNoException();
                        parcel2.writeInt(Initialize);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miio.linux.ILinuxMiioService");
                        int Destroy = Destroy();
                        parcel2.writeNoException();
                        parcel2.writeInt(Destroy);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miio.linux.ILinuxMiioService");
                        int Start = Start(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(Start);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miio.linux.ILinuxMiioService");
                        int Stop = Stop();
                        parcel2.writeNoException();
                        parcel2.writeInt(Stop);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miio.linux.ILinuxMiioService");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements ILinuxMiioService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.miio.linux.ILinuxMiioService
            public int Initialize() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miio.linux.ILinuxMiioService");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miio.linux.ILinuxMiioService
            public int Destroy() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miio.linux.ILinuxMiioService");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miio.linux.ILinuxMiioService
            public int Start(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miio.linux.ILinuxMiioService");
                    obtain.writeString(str);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miio.linux.ILinuxMiioService
            public int Stop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miio.linux.ILinuxMiioService");
                    this.a.transact(4, obtain, obtain2, 0);
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
