package com.xiaomi.micolauncher.overlay;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback;

/* loaded from: classes3.dex */
public interface ILauncherOverlay extends IInterface {

    /* loaded from: classes3.dex */
    public static class Default implements ILauncherOverlay {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
        public void hideOverlay(int i) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
        public void onNewIntent(Intent intent) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
        public void setActivityState(int i) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
        public void showOverlay(int i) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
        public void windowAttached(Bundle bundle, ILauncherOverlayCallback iLauncherOverlayCallback) throws RemoteException {
        }

        @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
        public void windowDetached(boolean z) throws RemoteException {
        }
    }

    void hideOverlay(int i) throws RemoteException;

    void onNewIntent(Intent intent) throws RemoteException;

    void setActivityState(int i) throws RemoteException;

    void showOverlay(int i) throws RemoteException;

    void windowAttached(Bundle bundle, ILauncherOverlayCallback iLauncherOverlayCallback) throws RemoteException;

    void windowDetached(boolean z) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ILauncherOverlay {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.micolauncher.overlay.ILauncherOverlay");
        }

        public static ILauncherOverlay asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ILauncherOverlay)) {
                return new a(iBinder);
            }
            return (ILauncherOverlay) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                Bundle bundle = null;
                Intent intent = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        windowAttached(bundle, ILauncherOverlayCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                        windowDetached(parcel.readInt() != 0);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                        showOverlay(parcel.readInt());
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                        hideOverlay(parcel.readInt());
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                        setActivityState(parcel.readInt());
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                        if (parcel.readInt() != 0) {
                            intent = (Intent) Intent.CREATOR.createFromParcel(parcel);
                        }
                        onNewIntent(intent);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class a implements ILauncherOverlay {
            public static ILauncherOverlay a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
            public void windowAttached(Bundle bundle, ILauncherOverlayCallback iLauncherOverlayCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iLauncherOverlayCallback != null ? iLauncherOverlayCallback.asBinder() : null);
                    if (!this.b.transact(1, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().windowAttached(bundle, iLauncherOverlayCallback);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
            public void windowDetached(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                    obtain.writeInt(z ? 1 : 0);
                    if (!this.b.transact(2, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().windowDetached(z);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
            public void showOverlay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                    obtain.writeInt(i);
                    if (!this.b.transact(3, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showOverlay(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
            public void hideOverlay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                    obtain.writeInt(i);
                    if (!this.b.transact(4, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hideOverlay(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
            public void setActivityState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                    obtain.writeInt(i);
                    if (!this.b.transact(5, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActivityState(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlay
            public void onNewIntent(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.overlay.ILauncherOverlay");
                    if (intent != null) {
                        obtain.writeInt(1);
                        intent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.b.transact(6, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNewIntent(intent);
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILauncherOverlay iLauncherOverlay) {
            if (a.a != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iLauncherOverlay == null) {
                return false;
            } else {
                a.a = iLauncherOverlay;
                return true;
            }
        }

        public static ILauncherOverlay getDefaultImpl() {
            return a.a;
        }
    }
}
