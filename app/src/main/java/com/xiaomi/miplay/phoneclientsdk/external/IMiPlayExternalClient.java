package com.xiaomi.miplay.phoneclientsdk.external;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback;
import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;
import com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo;

/* loaded from: classes4.dex */
public interface IMiPlayExternalClient extends IInterface {

    /* loaded from: classes4.dex */
    public static class Default implements IMiPlayExternalClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int cancelCirculate(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int getCirculateMode() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int getPosition(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public String getSourceName(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int getVolume(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int initAsync(String str, IMiPlayExternalClientCallback iMiPlayExternalClientCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int onBufferState(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int pause(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int play(String str, MediaMetaData mediaMetaData) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int playState(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int resume(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int seek(String str, long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int setVolume(String str, double d) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int stop(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int unInit(String str) throws RemoteException {
            return 0;
        }
    }

    int cancelCirculate(String str, int i) throws RemoteException;

    int getCirculateMode() throws RemoteException;

    int getPosition(String str) throws RemoteException;

    String getSourceName(String str) throws RemoteException;

    int getVolume(String str) throws RemoteException;

    int initAsync(String str, IMiPlayExternalClientCallback iMiPlayExternalClientCallback) throws RemoteException;

    int onBufferState(String str, int i) throws RemoteException;

    int pause(String str) throws RemoteException;

    int play(String str, MediaMetaData mediaMetaData) throws RemoteException;

    int playState(String str, int i) throws RemoteException;

    int resume(String str) throws RemoteException;

    int seek(String str, long j) throws RemoteException;

    int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo) throws RemoteException;

    int setVolume(String str, double d) throws RemoteException;

    int stop(String str) throws RemoteException;

    int unInit(String str) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IMiPlayExternalClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
        }

        public static IMiPlayExternalClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPlayExternalClient)) {
                return new a(iBinder);
            }
            return (IMiPlayExternalClient) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                MediaMetaData mediaMetaData = null;
                PropertiesInfo propertiesInfo = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int initAsync = initAsync(parcel.readString(), IMiPlayExternalClientCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(initAsync);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        String readString = parcel.readString();
                        if (parcel.readInt() != 0) {
                            mediaMetaData = MediaMetaData.CREATOR.createFromParcel(parcel);
                        }
                        int play = play(readString, mediaMetaData);
                        parcel2.writeNoException();
                        parcel2.writeInt(play);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int unInit = unInit(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(unInit);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int playState = playState(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(playState);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int onBufferState = onBufferState(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(onBufferState);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        String readString2 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            propertiesInfo = PropertiesInfo.CREATOR.createFromParcel(parcel);
                        }
                        int sendPropertiesInfo = sendPropertiesInfo(readString2, propertiesInfo);
                        parcel2.writeNoException();
                        parcel2.writeInt(sendPropertiesInfo);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int stop = stop(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(stop);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int pause = pause(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(pause);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int resume = resume(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(resume);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int seek = seek(parcel.readString(), parcel.readLong());
                        parcel2.writeNoException();
                        parcel2.writeInt(seek);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int volume = setVolume(parcel.readString(), parcel.readDouble());
                        parcel2.writeNoException();
                        parcel2.writeInt(volume);
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int position = getPosition(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(position);
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int volume2 = getVolume(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(volume2);
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        String sourceName = getSourceName(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(sourceName);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int circulateMode = getCirculateMode();
                        parcel2.writeNoException();
                        parcel2.writeInt(circulateMode);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                        int cancelCirculate = cancelCirculate(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(cancelCirculate);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes4.dex */
        public static class a implements IMiPlayExternalClient {
            public static IMiPlayExternalClient a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int initAsync(String str, IMiPlayExternalClientCallback iMiPlayExternalClientCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMiPlayExternalClientCallback != null ? iMiPlayExternalClientCallback.asBinder() : null);
                    if (!this.b.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initAsync(str, iMiPlayExternalClientCallback);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int play(String str, MediaMetaData mediaMetaData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (mediaMetaData != null) {
                        obtain.writeInt(1);
                        mediaMetaData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.b.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().play(str, mediaMetaData);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int unInit(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (!this.b.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unInit(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int playState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().playState(str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int onBufferState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onBufferState(str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (propertiesInfo != null) {
                        obtain.writeInt(1);
                        propertiesInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.b.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendPropertiesInfo(str, propertiesInfo);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int stop(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (!this.b.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stop(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int pause(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (!this.b.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pause(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int resume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (!this.b.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resume(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int seek(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (!this.b.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().seek(str, j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int setVolume(String str, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    obtain.writeDouble(d);
                    if (!this.b.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setVolume(str, d);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int getPosition(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (!this.b.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPosition(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int getVolume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (!this.b.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolume(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public String getSourceName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    if (!this.b.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSourceName(str);
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int getCirculateMode() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    if (!this.b.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCirculateMode();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
            public int cancelCirculate(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelCirculate(str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiPlayExternalClient iMiPlayExternalClient) {
            if (a.a != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iMiPlayExternalClient == null) {
                return false;
            } else {
                a.a = iMiPlayExternalClient;
                return true;
            }
        }

        public static IMiPlayExternalClient getDefaultImpl() {
            return a.a;
        }
    }
}
