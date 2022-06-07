package com.xiaomi.miplay.videoswitchmirror;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback;

/* loaded from: classes4.dex */
public interface IIVideoSwitchMirror extends IInterface {

    /* loaded from: classes4.dex */
    public static class Default implements IIVideoSwitchMirror {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int connectMirrorSuccess(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int disconnectMirror(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int getMirrorMode(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int init(String str, IVideoSwitchMirrorCallback iVideoSwitchMirrorCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int resumeMirrorFail() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int resumeMirrorSuccess() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int startDiscovery(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int unInit(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
        public int userDisconnectMirror(String str) throws RemoteException {
            return 0;
        }
    }

    int connectMirrorSuccess(String str) throws RemoteException;

    int disconnectMirror(String str) throws RemoteException;

    int getMirrorMode(String str) throws RemoteException;

    int init(String str, IVideoSwitchMirrorCallback iVideoSwitchMirrorCallback) throws RemoteException;

    int resumeMirrorFail() throws RemoteException;

    int resumeMirrorSuccess() throws RemoteException;

    int startDiscovery(int i) throws RemoteException;

    int unInit(String str) throws RemoteException;

    int userDisconnectMirror(String str) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IIVideoSwitchMirror {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
        }

        public static IIVideoSwitchMirror asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IIVideoSwitchMirror)) {
                return new a(iBinder);
            }
            return (IIVideoSwitchMirror) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int init = init(parcel.readString(), IVideoSwitchMirrorCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(init);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int unInit = unInit(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(unInit);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int connectMirrorSuccess = connectMirrorSuccess(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(connectMirrorSuccess);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int disconnectMirror = disconnectMirror(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(disconnectMirror);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int resumeMirrorSuccess = resumeMirrorSuccess();
                        parcel2.writeNoException();
                        parcel2.writeInt(resumeMirrorSuccess);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int resumeMirrorFail = resumeMirrorFail();
                        parcel2.writeNoException();
                        parcel2.writeInt(resumeMirrorFail);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int startDiscovery = startDiscovery(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(startDiscovery);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int mirrorMode = getMirrorMode(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(mirrorMode);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                        int userDisconnectMirror = userDisconnectMirror(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(userDisconnectMirror);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes4.dex */
        public static class a implements IIVideoSwitchMirror {
            public static IIVideoSwitchMirror a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int init(String str, IVideoSwitchMirrorCallback iVideoSwitchMirrorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iVideoSwitchMirrorCallback != null ? iVideoSwitchMirrorCallback.asBinder() : null);
                    if (!this.b.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().init(str, iVideoSwitchMirrorCallback);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int unInit(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    obtain.writeString(str);
                    if (!this.b.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unInit(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int connectMirrorSuccess(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    obtain.writeString(str);
                    if (!this.b.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().connectMirrorSuccess(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int disconnectMirror(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    obtain.writeString(str);
                    if (!this.b.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnectMirror(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int resumeMirrorSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    if (!this.b.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resumeMirrorSuccess();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int resumeMirrorFail() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    if (!this.b.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resumeMirrorFail();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int startDiscovery(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    obtain.writeInt(i);
                    if (!this.b.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startDiscovery(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int getMirrorMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    obtain.writeString(str);
                    if (!this.b.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMirrorMode(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
            public int userDisconnectMirror(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror");
                    obtain.writeString(str);
                    if (!this.b.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().userDisconnectMirror(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IIVideoSwitchMirror iIVideoSwitchMirror) {
            if (a.a != null || iIVideoSwitchMirror == null) {
                return false;
            }
            a.a = iIVideoSwitchMirror;
            return true;
        }

        public static IIVideoSwitchMirror getDefaultImpl() {
            return a.a;
        }
    }
}
