package com.xiaomi.miplay.audioclient;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miplay.audioclient.IMiPlayClientCallback;
import java.util.List;

/* loaded from: classes3.dex */
public interface IMiPlayClient extends IInterface {

    /* loaded from: classes3.dex */
    public static class Default implements IMiPlayClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int closeDevice(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getBtFrequency(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getChannel(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public boolean getCollectAudio() throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public List<MiPlayDevice> getDevices() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public MediaMetaData getLocalMediaInfo() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getMediaInfo(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getPlayState(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getPosition(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public List<MiPlayDeviceControlCenter> getStereoDevices(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getVolume(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int init(String str, IMiPlayClientCallback iMiPlayClientCallback, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public boolean isDiscovering() throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onNext(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onPause(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onPlay(String[] strArr, String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onPrev(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onRefreshDeviceInfo() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onResume(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onSeek(String[] strArr, long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onTimelineChange() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int play(String[] strArr, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setBoxPause(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setBoxResume(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setBtFrequency(String[] strArr, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setChannel(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setMediaInfo(String[] strArr, MediaMetaData mediaMetaData) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setVolume(String[] strArr, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int startDiscovery(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int stop(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int stopDiscovery() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int stopUwbDiscovery() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int unInit(String str) throws RemoteException {
            return 0;
        }
    }

    int closeDevice(String[] strArr) throws RemoteException;

    int getBtFrequency(String[] strArr) throws RemoteException;

    int getChannel(String[] strArr) throws RemoteException;

    boolean getCollectAudio() throws RemoteException;

    List<MiPlayDevice> getDevices() throws RemoteException;

    MediaMetaData getLocalMediaInfo() throws RemoteException;

    int getMediaInfo(String[] strArr) throws RemoteException;

    int getPlayState(String[] strArr) throws RemoteException;

    int getPosition(String[] strArr) throws RemoteException;

    List<MiPlayDeviceControlCenter> getStereoDevices(String str) throws RemoteException;

    int getVolume(String[] strArr) throws RemoteException;

    int init(String str, IMiPlayClientCallback iMiPlayClientCallback, String str2) throws RemoteException;

    boolean isDiscovering() throws RemoteException;

    int onNext(String[] strArr) throws RemoteException;

    int onPause(String[] strArr) throws RemoteException;

    int onPlay(String[] strArr, String str) throws RemoteException;

    int onPrev(String[] strArr) throws RemoteException;

    int onRefreshDeviceInfo() throws RemoteException;

    int onResume(String[] strArr) throws RemoteException;

    int onSeek(String[] strArr, long j) throws RemoteException;

    int onTimelineChange() throws RemoteException;

    int play(String[] strArr, String str, int i) throws RemoteException;

    int setBoxPause(String[] strArr) throws RemoteException;

    int setBoxResume(String[] strArr) throws RemoteException;

    int setBtFrequency(String[] strArr, int i) throws RemoteException;

    int setChannel(String str, int i) throws RemoteException;

    int setMediaInfo(String[] strArr, MediaMetaData mediaMetaData) throws RemoteException;

    int setVolume(String[] strArr, int i) throws RemoteException;

    int startDiscovery(int i) throws RemoteException;

    int stop(String[] strArr) throws RemoteException;

    int stopDiscovery() throws RemoteException;

    int stopUwbDiscovery() throws RemoteException;

    int unInit(String str) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiPlayClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.audioclient.IMiPlayClient");
        }

        public static IMiPlayClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPlayClient)) {
                return new a(iBinder);
            }
            return (IMiPlayClient) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int init = init(parcel.readString(), IMiPlayClientCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(init);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int unInit = unInit(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(unInit);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int startDiscovery = startDiscovery(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(startDiscovery);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int stopDiscovery = stopDiscovery();
                        parcel2.writeNoException();
                        parcel2.writeInt(stopDiscovery);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        boolean isDiscovering = isDiscovering();
                        parcel2.writeNoException();
                        parcel2.writeInt(isDiscovering ? 1 : 0);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onPlay = onPlay(parcel.createStringArray(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(onPlay);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int stop = stop(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(stop);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onPause = onPause(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(onPause);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onResume = onResume(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(onResume);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onSeek = onSeek(parcel.createStringArray(), parcel.readLong());
                        parcel2.writeNoException();
                        parcel2.writeInt(onSeek);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int position = getPosition(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(position);
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int playState = getPlayState(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(playState);
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int mediaInfo = setMediaInfo(parcel.createStringArray(), parcel.readInt() != 0 ? MediaMetaData.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeInt(mediaInfo);
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int mediaInfo2 = getMediaInfo(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(mediaInfo2);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int channel = setChannel(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(channel);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int channel2 = getChannel(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(channel2);
                        return true;
                    case 17:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int volume = setVolume(parcel.createStringArray(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(volume);
                        return true;
                    case 18:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int volume2 = getVolume(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(volume2);
                        return true;
                    case 19:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int closeDevice = closeDevice(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(closeDevice);
                        return true;
                    case 20:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onTimelineChange = onTimelineChange();
                        parcel2.writeNoException();
                        parcel2.writeInt(onTimelineChange);
                        return true;
                    case 21:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        boolean collectAudio = getCollectAudio();
                        parcel2.writeNoException();
                        parcel2.writeInt(collectAudio ? 1 : 0);
                        return true;
                    case 22:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        List<MiPlayDevice> devices = getDevices();
                        parcel2.writeNoException();
                        parcel2.writeTypedList(devices);
                        return true;
                    case 23:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onNext = onNext(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(onNext);
                        return true;
                    case 24:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onPrev = onPrev(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(onPrev);
                        return true;
                    case 25:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int onRefreshDeviceInfo = onRefreshDeviceInfo();
                        parcel2.writeNoException();
                        parcel2.writeInt(onRefreshDeviceInfo);
                        return true;
                    case 26:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int btFrequency = setBtFrequency(parcel.createStringArray(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(btFrequency);
                        return true;
                    case 27:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int btFrequency2 = getBtFrequency(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(btFrequency2);
                        return true;
                    case 28:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int play = play(parcel.createStringArray(), parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(play);
                        return true;
                    case 29:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int boxPause = setBoxPause(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(boxPause);
                        return true;
                    case 30:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int boxResume = setBoxResume(parcel.createStringArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(boxResume);
                        return true;
                    case 31:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        int stopUwbDiscovery = stopUwbDiscovery();
                        parcel2.writeNoException();
                        parcel2.writeInt(stopUwbDiscovery);
                        return true;
                    case 32:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        List<MiPlayDeviceControlCenter> stereoDevices = getStereoDevices(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeTypedList(stereoDevices);
                        return true;
                    case 33:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.IMiPlayClient");
                        MediaMetaData localMediaInfo = getLocalMediaInfo();
                        parcel2.writeNoException();
                        if (localMediaInfo != null) {
                            parcel2.writeInt(1);
                            localMediaInfo.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.audioclient.IMiPlayClient");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class a implements IMiPlayClient {
            public static IMiPlayClient a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int init(String str, IMiPlayClientCallback iMiPlayClientCallback, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMiPlayClientCallback != null ? iMiPlayClientCallback.asBinder() : null);
                    obtain.writeString(str2);
                    if (!this.b.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().init(str, iMiPlayClientCallback, str2);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int unInit(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
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

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int startDiscovery(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
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

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int stopDiscovery() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
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

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public boolean isDiscovering() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    boolean z = false;
                    if (!this.b.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDiscovering();
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

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onPlay(String[] strArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    if (!this.b.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPlay(strArr, str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int stop(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stop(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onPause(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPause(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onResume(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onResume(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onSeek(String[] strArr, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    if (!this.b.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onSeek(strArr, j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int getPosition(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPosition(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int getPlayState(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayState(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int setMediaInfo(String[] strArr, MediaMetaData mediaMetaData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (mediaMetaData != null) {
                        obtain.writeInt(1);
                        mediaMetaData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.b.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMediaInfo(strArr, mediaMetaData);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int getMediaInfo(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMediaInfo(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int setChannel(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setChannel(str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int getChannel(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getChannel(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int setVolume(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    if (!this.b.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setVolume(strArr, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int getVolume(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolume(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int closeDevice(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(19, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().closeDevice(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onTimelineChange() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    if (!this.b.transact(20, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onTimelineChange();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public boolean getCollectAudio() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    boolean z = false;
                    if (!this.b.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public List<MiPlayDevice> getDevices() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    if (!this.b.transact(22, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDevices();
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(MiPlayDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onNext(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(23, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onNext(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onPrev(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(24, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPrev(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int onRefreshDeviceInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    if (!this.b.transact(25, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onRefreshDeviceInfo();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int setBtFrequency(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    if (!this.b.transact(26, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBtFrequency(strArr, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int getBtFrequency(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(27, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtFrequency(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int play(String[] strArr, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.b.transact(28, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().play(strArr, str, i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int setBoxPause(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(29, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBoxPause(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int setBoxResume(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeStringArray(strArr);
                    if (!this.b.transact(30, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBoxResume(strArr);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public int stopUwbDiscovery() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    if (!this.b.transact(31, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopUwbDiscovery();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public List<MiPlayDeviceControlCenter> getStereoDevices(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    obtain.writeString(str);
                    if (!this.b.transact(32, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStereoDevices(str);
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(MiPlayDeviceControlCenter.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
            public MediaMetaData getLocalMediaInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.IMiPlayClient");
                    if (!this.b.transact(33, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLocalMediaInfo();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? MediaMetaData.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiPlayClient iMiPlayClient) {
            if (a.a != null || iMiPlayClient == null) {
                return false;
            }
            a.a = iMiPlayClient;
            return true;
        }

        public static IMiPlayClient getDefaultImpl() {
            return a.a;
        }
    }
}
