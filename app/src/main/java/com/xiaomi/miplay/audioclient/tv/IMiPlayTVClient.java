package com.xiaomi.miplay.audioclient.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback;

/* loaded from: classes3.dex */
public interface IMiPlayTVClient extends IInterface {

    /* loaded from: classes3.dex */
    public static class Default implements IMiPlayTVClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int audioFcControl() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getMediaInfo() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getPlayState() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getPosition() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public String getSourceName() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getVolume() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int init(String str, IMiPlayTVClientCallback iMiPlayTVClientCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int musicRelay(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onNext() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onPause() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onPrev() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onResume() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onSeek(long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setLocalDeviceInfo(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setLocalMediaInfo(TVMediaMetaData tVMediaMetaData) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setLocalMediaState(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setMiplayAudioFcControl(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setMiplayVolumeControl(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setVolume(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int stop() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int unInit(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int volumeControl() throws RemoteException {
            return 0;
        }
    }

    int audioFcControl() throws RemoteException;

    int getMediaInfo() throws RemoteException;

    int getPlayState() throws RemoteException;

    int getPosition() throws RemoteException;

    String getSourceName() throws RemoteException;

    int getVolume() throws RemoteException;

    int init(String str, IMiPlayTVClientCallback iMiPlayTVClientCallback) throws RemoteException;

    int musicRelay(String str, int i) throws RemoteException;

    int onNext() throws RemoteException;

    int onPause() throws RemoteException;

    int onPrev() throws RemoteException;

    int onResume() throws RemoteException;

    int onSeek(long j) throws RemoteException;

    int setLocalDeviceInfo(String str) throws RemoteException;

    int setLocalMediaInfo(TVMediaMetaData tVMediaMetaData) throws RemoteException;

    int setLocalMediaState(int i) throws RemoteException;

    int setMiplayAudioFcControl(int i) throws RemoteException;

    int setMiplayVolumeControl(int i) throws RemoteException;

    int setVolume(int i) throws RemoteException;

    int stop() throws RemoteException;

    int unInit(String str) throws RemoteException;

    int volumeControl() throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiPlayTVClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
        }

        public static IMiPlayTVClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPlayTVClient)) {
                return new a(iBinder);
            }
            return (IMiPlayTVClient) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int init = init(parcel.readString(), IMiPlayTVClientCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(init);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int unInit = unInit(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(unInit);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int stop = stop();
                        parcel2.writeNoException();
                        parcel2.writeInt(stop);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int onPause = onPause();
                        parcel2.writeNoException();
                        parcel2.writeInt(onPause);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int onResume = onResume();
                        parcel2.writeNoException();
                        parcel2.writeInt(onResume);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int onSeek = onSeek(parcel.readLong());
                        parcel2.writeNoException();
                        parcel2.writeInt(onSeek);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int position = getPosition();
                        parcel2.writeNoException();
                        parcel2.writeInt(position);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int playState = getPlayState();
                        parcel2.writeNoException();
                        parcel2.writeInt(playState);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int mediaInfo = getMediaInfo();
                        parcel2.writeNoException();
                        parcel2.writeInt(mediaInfo);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int volume = setVolume(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(volume);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int volume2 = getVolume();
                        parcel2.writeNoException();
                        parcel2.writeInt(volume2);
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int onNext = onNext();
                        parcel2.writeNoException();
                        parcel2.writeInt(onNext);
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int onPrev = onPrev();
                        parcel2.writeNoException();
                        parcel2.writeInt(onPrev);
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        String sourceName = getSourceName();
                        parcel2.writeNoException();
                        parcel2.writeString(sourceName);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int musicRelay = musicRelay(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(musicRelay);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int localDeviceInfo = setLocalDeviceInfo(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(localDeviceInfo);
                        return true;
                    case 17:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int localMediaInfo = setLocalMediaInfo(parcel.readInt() != 0 ? TVMediaMetaData.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeInt(localMediaInfo);
                        return true;
                    case 18:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int localMediaState = setLocalMediaState(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(localMediaState);
                        return true;
                    case 19:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int volumeControl = volumeControl();
                        parcel2.writeNoException();
                        parcel2.writeInt(volumeControl);
                        return true;
                    case 20:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int miplayVolumeControl = setMiplayVolumeControl(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(miplayVolumeControl);
                        return true;
                    case 21:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int audioFcControl = audioFcControl();
                        parcel2.writeNoException();
                        parcel2.writeInt(audioFcControl);
                        return true;
                    case 22:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                        int miplayAudioFcControl = setMiplayAudioFcControl(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(miplayAudioFcControl);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class a implements IMiPlayTVClient {
            public static IMiPlayTVClient a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int init(String str, IMiPlayTVClientCallback iMiPlayTVClientCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMiPlayTVClientCallback != null ? iMiPlayTVClientCallback.asBinder() : null);
                    if (!this.b.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().init(str, iMiPlayTVClientCallback);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int unInit(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
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

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int stop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stop();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int onPause() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPause();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int onResume() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onResume();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int onSeek(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeLong(j);
                    if (!this.b.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onSeek(j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int getPosition() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPosition();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int getPlayState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayState();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int getMediaInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMediaInfo();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int setVolume(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeInt(i);
                    if (!this.b.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setVolume(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int getVolume() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolume();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int onNext() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onNext();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int onPrev() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPrev();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public String getSourceName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSourceName();
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int musicRelay(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().musicRelay(str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int setLocalDeviceInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeString(str);
                    if (!this.b.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLocalDeviceInfo(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int setLocalMediaInfo(TVMediaMetaData tVMediaMetaData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (tVMediaMetaData != null) {
                        obtain.writeInt(1);
                        tVMediaMetaData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.b.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLocalMediaInfo(tVMediaMetaData);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int setLocalMediaState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeInt(i);
                    if (!this.b.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLocalMediaState(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int volumeControl() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(19, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().volumeControl();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int setMiplayVolumeControl(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeInt(i);
                    if (!this.b.transact(20, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMiplayVolumeControl(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int audioFcControl() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    if (!this.b.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().audioFcControl();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
            public int setMiplayAudioFcControl(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient");
                    obtain.writeInt(i);
                    if (!this.b.transact(22, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMiplayAudioFcControl(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiPlayTVClient iMiPlayTVClient) {
            if (a.a != null || iMiPlayTVClient == null) {
                return false;
            }
            a.a = iMiPlayTVClient;
            return true;
        }

        public static IMiPlayTVClient getDefaultImpl() {
            return a.a;
        }
    }
}
