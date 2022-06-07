package com.xiaomi.miplay.videoswitchmirror;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IVideoSwitchMirrorCallback extends IInterface {

    /* loaded from: classes4.dex */
    public static class Default implements IVideoSwitchMirrorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void closeMirror() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void endCirculate() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void onInitError() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void onInitSuccess() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void resumeMirror() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
        public void startCirculate() throws RemoteException {
        }
    }

    void closeMirror() throws RemoteException;

    void endCirculate() throws RemoteException;

    void onInitError() throws RemoteException;

    void onInitSuccess() throws RemoteException;

    void resumeMirror() throws RemoteException;

    void startCirculate() throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IVideoSwitchMirrorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
        }

        public static IVideoSwitchMirrorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IVideoSwitchMirrorCallback)) {
                return new a(iBinder);
            }
            return (IVideoSwitchMirrorCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                        onInitSuccess();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                        onInitError();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                        resumeMirror();
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                        startCirculate();
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                        endCirculate();
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                        closeMirror();
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes4.dex */
        public static class a implements IVideoSwitchMirrorCallback {
            public static IVideoSwitchMirrorCallback a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
            public void onInitSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                    if (!this.b.transact(1, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInitSuccess();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
            public void onInitError() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                    if (!this.b.transact(2, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInitError();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
            public void resumeMirror() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                    if (this.b.transact(3, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().resumeMirror();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
            public void startCirculate() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                    if (this.b.transact(4, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().startCirculate();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
            public void endCirculate() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                    if (this.b.transact(5, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().endCirculate();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback
            public void closeMirror() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback");
                    if (this.b.transact(6, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().closeMirror();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVideoSwitchMirrorCallback iVideoSwitchMirrorCallback) {
            if (a.a != null || iVideoSwitchMirrorCallback == null) {
                return false;
            }
            a.a = iVideoSwitchMirrorCallback;
            return true;
        }

        public static IVideoSwitchMirrorCallback getDefaultImpl() {
            return a.a;
        }
    }
}
