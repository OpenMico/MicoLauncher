package com.xiaomi.mi_soundbox_command_sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mi_soundbox_command_sdk.IMiCommandCallback;

/* loaded from: classes3.dex */
public interface IMiCommandInterface extends IInterface {
    void registerCallback(String str, IMiCommandCallback iMiCommandCallback) throws RemoteException;

    void setPlayInfo(String str) throws RemoteException;

    void setPlaying(String str, boolean z) throws RemoteException;

    void unRegisterCallback(String str, IMiCommandCallback iMiCommandCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiCommandInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
        }

        public static IMiCommandInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiCommandInterface)) {
                return new a(iBinder);
            }
            return (IMiCommandInterface) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                        registerCallback(parcel.readString(), IMiCommandCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                        unRegisterCallback(parcel.readString(), IMiCommandCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                        setPlaying(parcel.readString(), parcel.readInt() != 0);
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                        setPlayInfo(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IMiCommandInterface {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
            public void registerCallback(String str, IMiCommandCallback iMiCommandCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMiCommandCallback != null ? iMiCommandCallback.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
            public void unRegisterCallback(String str, IMiCommandCallback iMiCommandCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMiCommandCallback != null ? iMiCommandCallback.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
            public void setPlaying(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface
            public void setPlayInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface");
                    obtain.writeString(str);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
