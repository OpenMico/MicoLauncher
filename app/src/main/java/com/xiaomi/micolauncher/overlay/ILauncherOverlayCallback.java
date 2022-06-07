package com.xiaomi.micolauncher.overlay;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILauncherOverlayCallback extends IInterface {

    /* loaded from: classes3.dex */
    public static class Default implements ILauncherOverlayCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
        public void overlayStatusChanged(int i) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
        public void overlayTransitionChanged(float f) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
        public void overlayTransitionEnd(float f) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
        public void overlayTransitionStart(float f) throws RemoteException {
        }
    }

    void overlayStatusChanged(int i) throws RemoteException;

    void overlayTransitionChanged(float f) throws RemoteException;

    void overlayTransitionEnd(float f) throws RemoteException;

    void overlayTransitionStart(float f) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ILauncherOverlayCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
        }

        public static ILauncherOverlayCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ILauncherOverlayCallback)) {
                return new a(iBinder);
            }
            return (ILauncherOverlayCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                        overlayStatusChanged(parcel.readInt());
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                        overlayTransitionStart(parcel.readFloat());
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                        overlayTransitionChanged(parcel.readFloat());
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                        overlayTransitionEnd(parcel.readFloat());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class a implements ILauncherOverlayCallback {
            public static ILauncherOverlayCallback a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
            public void overlayStatusChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                    obtain.writeInt(i);
                    if (!this.b.transact(1, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overlayStatusChanged(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
            public void overlayTransitionStart(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                    obtain.writeFloat(f);
                    if (!this.b.transact(2, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overlayTransitionStart(f);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
            public void overlayTransitionChanged(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                    obtain.writeFloat(f);
                    if (!this.b.transact(3, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overlayTransitionChanged(f);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
            public void overlayTransitionEnd(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback");
                    obtain.writeFloat(f);
                    if (!this.b.transact(4, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overlayTransitionEnd(f);
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILauncherOverlayCallback iLauncherOverlayCallback) {
            if (a.a != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iLauncherOverlayCallback == null) {
                return false;
            } else {
                a.a = iLauncherOverlayCallback;
                return true;
            }
        }

        public static ILauncherOverlayCallback getDefaultImpl() {
            return a.a;
        }
    }
}
