package com.xiaomi.micolauncher;

import android.media.MediaMetadata;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback;

/* loaded from: classes3.dex */
public interface IMicoRemoteService extends IInterface {
    MediaMetadata getMediaMetadata() throws RemoteException;

    int getPlaybackState() throws RemoteException;

    boolean isNightModeOn() throws RemoteException;

    void notifyMediaMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException;

    void notifyPlaybackStateChanged(int i) throws RemoteException;

    void registerSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback iSystemUIMediaPlayerCallback) throws RemoteException;

    void unregisterSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback iSystemUIMediaPlayerCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMicoRemoteService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.micolauncher.IMicoRemoteService");
        }

        public static IMicoRemoteService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.micolauncher.IMicoRemoteService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMicoRemoteService)) {
                return new a(iBinder);
            }
            return (IMicoRemoteService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.micolauncher.IMicoRemoteService");
                        boolean isNightModeOn = isNightModeOn();
                        parcel2.writeNoException();
                        parcel2.writeInt(isNightModeOn ? 1 : 0);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.micolauncher.IMicoRemoteService");
                        registerSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.micolauncher.IMicoRemoteService");
                        unregisterSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.micolauncher.IMicoRemoteService");
                        MediaMetadata mediaMetadata = getMediaMetadata();
                        parcel2.writeNoException();
                        if (mediaMetadata != null) {
                            parcel2.writeInt(1);
                            mediaMetadata.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.micolauncher.IMicoRemoteService");
                        int playbackState = getPlaybackState();
                        parcel2.writeNoException();
                        parcel2.writeInt(playbackState);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.micolauncher.IMicoRemoteService");
                        notifyMediaMetadataChanged(parcel.readInt() != 0 ? (MediaMetadata) MediaMetadata.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.micolauncher.IMicoRemoteService");
                        notifyPlaybackStateChanged(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.micolauncher.IMicoRemoteService");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class a implements IMicoRemoteService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.micolauncher.IMicoRemoteService
            public boolean isNightModeOn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.IMicoRemoteService");
                    boolean z = false;
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.IMicoRemoteService
            public void registerSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback iSystemUIMediaPlayerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.IMicoRemoteService");
                    obtain.writeStrongBinder(iSystemUIMediaPlayerCallback != null ? iSystemUIMediaPlayerCallback.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.IMicoRemoteService
            public void unregisterSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback iSystemUIMediaPlayerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.IMicoRemoteService");
                    obtain.writeStrongBinder(iSystemUIMediaPlayerCallback != null ? iSystemUIMediaPlayerCallback.asBinder() : null);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.IMicoRemoteService
            public MediaMetadata getMediaMetadata() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.IMicoRemoteService");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (MediaMetadata) MediaMetadata.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.IMicoRemoteService
            public int getPlaybackState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.IMicoRemoteService");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.IMicoRemoteService
            public void notifyMediaMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.IMicoRemoteService");
                    if (mediaMetadata != null) {
                        obtain.writeInt(1);
                        mediaMetadata.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.micolauncher.IMicoRemoteService
            public void notifyPlaybackStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.micolauncher.IMicoRemoteService");
                    obtain.writeInt(i);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
