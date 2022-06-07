package com.xiaomi.miplay.videoclient;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miplay.audioclient.MediaMetaData;
import com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter;

/* loaded from: classes4.dex */
public interface IMiPlayVideoCallback extends IInterface {

    /* loaded from: classes4.dex */
    public static class Default implements IMiPlayVideoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onBeSeized(String str) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onBufferStateChange(String str, int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onDeviceFoundControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onDeviceLost(String str) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onDeviceUpdateControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onInitError() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onInitSuccess() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onMediaInfoAck(String str, MediaMetaData mediaMetaData) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onMediaInfoChange(String str, MediaMetaData mediaMetaData) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPlayStateAck(String str, int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPlayStateChange(String str, int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPositionAck(String str, long j) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onPositionChange(String str, long j) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onVolumeAck(String str, double d) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
        public void onVolumeChange(String str, double d) throws RemoteException {
        }
    }

    void onBeSeized(String str) throws RemoteException;

    void onBufferStateChange(String str, int i) throws RemoteException;

    void onDeviceFoundControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException;

    void onDeviceLost(String str) throws RemoteException;

    void onDeviceUpdateControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException;

    void onInitError() throws RemoteException;

    void onInitSuccess() throws RemoteException;

    void onMediaInfoAck(String str, MediaMetaData mediaMetaData) throws RemoteException;

    void onMediaInfoChange(String str, MediaMetaData mediaMetaData) throws RemoteException;

    void onPlayStateAck(String str, int i) throws RemoteException;

    void onPlayStateChange(String str, int i) throws RemoteException;

    void onPositionAck(String str, long j) throws RemoteException;

    void onPositionChange(String str, long j) throws RemoteException;

    void onVolumeAck(String str, double d) throws RemoteException;

    void onVolumeChange(String str, double d) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IMiPlayVideoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
        }

        public static IMiPlayVideoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPlayVideoCallback)) {
                return new a(iBinder);
            }
            return (IMiPlayVideoCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                MiPlayDeviceControlCenter miPlayDeviceControlCenter = null;
                MediaMetaData mediaMetaData = null;
                MediaMetaData mediaMetaData2 = null;
                MiPlayDeviceControlCenter miPlayDeviceControlCenter2 = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onInitSuccess();
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onInitError();
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        if (parcel.readInt() != 0) {
                            miPlayDeviceControlCenter = MiPlayDeviceControlCenter.CREATOR.createFromParcel(parcel);
                        }
                        onDeviceFoundControlCenter(miPlayDeviceControlCenter);
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        if (parcel.readInt() != 0) {
                            miPlayDeviceControlCenter2 = MiPlayDeviceControlCenter.CREATOR.createFromParcel(parcel);
                        }
                        onDeviceUpdateControlCenter(miPlayDeviceControlCenter2);
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onDeviceLost(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onPositionAck(parcel.readString(), parcel.readLong());
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onPositionChange(parcel.readString(), parcel.readLong());
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onPlayStateChange(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onPlayStateAck(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        String readString = parcel.readString();
                        if (parcel.readInt() != 0) {
                            mediaMetaData2 = MediaMetaData.CREATOR.createFromParcel(parcel);
                        }
                        onMediaInfoAck(readString, mediaMetaData2);
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        String readString2 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            mediaMetaData = MediaMetaData.CREATOR.createFromParcel(parcel);
                        }
                        onMediaInfoChange(readString2, mediaMetaData);
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onVolumeChange(parcel.readString(), parcel.readDouble());
                        parcel2.writeNoException();
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onVolumeAck(parcel.readString(), parcel.readDouble());
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onBufferStateChange(parcel.readString(), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 15:
                        parcel.enforceInterface("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                        onBeSeized(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                return true;
            }
        }

        /* loaded from: classes4.dex */
        public static class a implements IMiPlayVideoCallback {
            public static IMiPlayVideoCallback a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onInitSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    if (this.b.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onInitSuccess();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onInitError() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    if (this.b.transact(2, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onInitError();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onDeviceFoundControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    if (miPlayDeviceControlCenter != null) {
                        obtain.writeInt(1);
                        miPlayDeviceControlCenter.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.b.transact(3, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onDeviceFoundControlCenter(miPlayDeviceControlCenter);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onDeviceUpdateControlCenter(MiPlayDeviceControlCenter miPlayDeviceControlCenter) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    if (miPlayDeviceControlCenter != null) {
                        obtain.writeInt(1);
                        miPlayDeviceControlCenter.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.b.transact(4, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onDeviceUpdateControlCenter(miPlayDeviceControlCenter);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onDeviceLost(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    if (this.b.transact(5, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onDeviceLost(str);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onPositionAck(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (this.b.transact(6, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onPositionAck(str, j);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onPositionChange(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (this.b.transact(7, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onPositionChange(str, j);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onPlayStateChange(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (this.b.transact(8, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onPlayStateChange(str, i);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onPlayStateAck(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (this.b.transact(9, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onPlayStateAck(str, i);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onMediaInfoAck(String str, MediaMetaData mediaMetaData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    if (mediaMetaData != null) {
                        obtain.writeInt(1);
                        mediaMetaData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.b.transact(10, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onMediaInfoAck(str, mediaMetaData);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onMediaInfoChange(String str, MediaMetaData mediaMetaData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    if (mediaMetaData != null) {
                        obtain.writeInt(1);
                        mediaMetaData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.b.transact(11, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onMediaInfoChange(str, mediaMetaData);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onVolumeChange(String str, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    obtain.writeDouble(d);
                    if (this.b.transact(12, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onVolumeChange(str, d);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onVolumeAck(String str, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    obtain.writeDouble(d);
                    if (this.b.transact(13, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onVolumeAck(str, d);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onBufferStateChange(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (this.b.transact(14, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onBufferStateChange(str, i);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoCallback
            public void onBeSeized(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.videoclient.IMiPlayVideoCallback");
                    obtain.writeString(str);
                    if (this.b.transact(15, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onBeSeized(str);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiPlayVideoCallback iMiPlayVideoCallback) {
            if (a.a != null || iMiPlayVideoCallback == null) {
                return false;
            }
            a.a = iMiPlayVideoCallback;
            return true;
        }

        public static IMiPlayVideoCallback getDefaultImpl() {
            return a.a;
        }
    }
}
