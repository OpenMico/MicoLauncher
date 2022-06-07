package com.android.systemui.qs.mi;

import android.media.MediaMetadata;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISystemUIMediaPlayerCallback extends IInterface {
    void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException;

    void onPlaybackStateChanged(int i) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements ISystemUIMediaPlayerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback");
        }

        public static ISystemUIMediaPlayerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ISystemUIMediaPlayerCallback)) {
                return new a(iBinder);
            }
            return (ISystemUIMediaPlayerCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback");
                        onMetadataChanged(parcel.readInt() != 0 ? (MediaMetadata) MediaMetadata.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback");
                        onPlaybackStateChanged(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback");
                return true;
            }
        }

        /* loaded from: classes.dex */
        private static class a implements ISystemUIMediaPlayerCallback {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback
            public void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback");
                    if (mediaMetadata != null) {
                        obtain.writeInt(1);
                        mediaMetadata.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback
            public void onPlaybackStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback");
                    obtain.writeInt(i);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
