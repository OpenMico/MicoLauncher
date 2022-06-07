package com.xiaomi.miplay.videoclient;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miplay.videoclient.IMiPlayVideoCallback;

/* loaded from: classes4.dex */
public interface IMiPlayVideoClient extends IInterface {

    /* loaded from: classes4.dex */
    public static class Default implements IMiPlayVideoClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public boolean getCollectAudio() throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int getMediaInfo(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int getPlayState(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int getPosition(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int getVolume(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int init(String str, IMiPlayVideoCallback iMiPlayVideoCallback, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int next(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int pause(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int play(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int prev(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int resume(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int seek(String str, long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int setVolume(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int startDiscovery(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int stop(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int stopDiscovery() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
        public int unInit(String str) throws RemoteException {
            return 0;
        }
    }

    boolean getCollectAudio() throws RemoteException;

    int getMediaInfo(String str) throws RemoteException;

    int getPlayState(String str) throws RemoteException;

    int getPosition(String str) throws RemoteException;

    int getVolume(String str) throws RemoteException;

    int init(String str, IMiPlayVideoCallback iMiPlayVideoCallback, String str2) throws RemoteException;

    int next(String str) throws RemoteException;

    int pause(String str) throws RemoteException;

    int play(String str, int i) throws RemoteException;

    int prev(String str) throws RemoteException;

    int resume(String str) throws RemoteException;

    int seek(String str, long j) throws RemoteException;

    int setVolume(String str, int i) throws RemoteException;

    int startDiscovery(int i) throws RemoteException;

    int stop(String str) throws RemoteException;

    int stopDiscovery() throws RemoteException;

    int unInit(String str) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IMiPlayVideoClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
        }

        public static IMiPlayVideoClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPlayVideoClient)) {
                return new a(iBinder);
            }
            return (IMiPlayVideoClient) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int init = init(parcel.readString(), IMiPlayVideoCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(init);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int unInit = unInit(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(unInit);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int startDiscovery = startDiscovery(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(startDiscovery);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int stopDiscovery = stopDiscovery();
                        parcel2.writeNoException();
                        parcel2.writeInt(stopDiscovery);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int play = play(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(play);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int stop = stop(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(stop);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int pause = pause(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(pause);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int resume = resume(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(resume);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int seek = seek(parcel.readString(), parcel.readLong());
                        parcel2.writeNoException();
                        parcel2.writeInt(seek);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int position = getPosition(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(position);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int playState = getPlayState(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(playState);
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int mediaInfo = getMediaInfo(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(mediaInfo);
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int volume = setVolume(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(volume);
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int volume2 = getVolume(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(volume2);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        boolean collectAudio = getCollectAudio();
                        parcel2.writeNoException();
                        parcel2.writeInt(collectAudio ? 1 : 0);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int next = next(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(next);
                        return true;
                    case 17:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                        int prev = prev(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(prev);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes4.dex */
        public static class a implements IMiPlayVideoClient {
            public static IMiPlayVideoClient a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int init(String str, IMiPlayVideoCallback iMiPlayVideoCallback, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMiPlayVideoCallback != null ? iMiPlayVideoCallback.asBinder() : null);
                    obtain.writeString(str2);
                    if (!this.b.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().init(str, iMiPlayVideoCallback, str2);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int unInit(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
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

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int startDiscovery(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeInt(i);
                    if (!this.b.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startDiscovery(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int stopDiscovery() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    if (!this.b.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopDiscovery();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int play(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().play(str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int stop(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stop(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int pause(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pause(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int resume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resume(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int seek(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (!this.b.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().seek(str, j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int getPosition(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPosition(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int getPlayState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayState(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int getMediaInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMediaInfo(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int setVolume(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setVolume(str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int getVolume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolume(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public boolean getCollectAudio() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    boolean z = false;
                    if (!this.b.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCollectAudio();
                    }
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

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int next(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().next(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
            public int prev(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoClient");
                    obtain.writeString(str);
                    if (!this.b.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().prev(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiPlayVideoClient iMiPlayVideoClient) {
            if (a.a != null || iMiPlayVideoClient == null) {
                return false;
            }
            a.a = iMiPlayVideoClient;
            return true;
        }

        public static IMiPlayVideoClient getDefaultImpl() {
            return a.a;
        }
    }
}
