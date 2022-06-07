package com.xiaomi.miplay.audioclient.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMiPlayTVClientCallback extends IInterface {

    /* loaded from: classes3.dex */
    public static class Default implements IMiPlayTVClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onCmdSessionError() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onCmdSessionSuccess() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onDurationUpdated(long j) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onInitError() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onInitSuccess() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onMediaInfoAck(TVMediaMetaData tVMediaMetaData) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onMediaInfoChange(TVMediaMetaData tVMediaMetaData) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onMirrorModeNotify(int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onPlayStateAck(int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onPlayStateChange(int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onPositionAck(long j) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onSourceNameChange(String str) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onVolumeAck(int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
        public void onVolumeChange(int i) throws RemoteException {
        }
    }

    void onCmdSessionError() throws RemoteException;

    void onCmdSessionSuccess() throws RemoteException;

    void onDurationUpdated(long j) throws RemoteException;

    void onInitError() throws RemoteException;

    void onInitSuccess() throws RemoteException;

    void onMediaInfoAck(TVMediaMetaData tVMediaMetaData) throws RemoteException;

    void onMediaInfoChange(TVMediaMetaData tVMediaMetaData) throws RemoteException;

    void onMirrorModeNotify(int i) throws RemoteException;

    void onPlayStateAck(int i) throws RemoteException;

    void onPlayStateChange(int i) throws RemoteException;

    void onPositionAck(long j) throws RemoteException;

    void onSourceNameChange(String str) throws RemoteException;

    void onVolumeAck(int i) throws RemoteException;

    void onVolumeChange(int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiPlayTVClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
        }

        public static IMiPlayTVClientCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPlayTVClientCallback)) {
                return new a(iBinder);
            }
            return (IMiPlayTVClientCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                TVMediaMetaData tVMediaMetaData = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onInitSuccess();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onInitError();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onCmdSessionError();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onCmdSessionSuccess();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onPositionAck(parcel.readLong());
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onPlayStateChange(parcel.readInt());
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onPlayStateAck(parcel.readInt());
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        if (parcel.readInt() != 0) {
                            tVMediaMetaData = TVMediaMetaData.CREATOR.createFromParcel(parcel);
                        }
                        onMediaInfoAck(tVMediaMetaData);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        if (parcel.readInt() != 0) {
                            tVMediaMetaData = TVMediaMetaData.CREATOR.createFromParcel(parcel);
                        }
                        onMediaInfoChange(tVMediaMetaData);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onVolumeChange(parcel.readInt());
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onVolumeAck(parcel.readInt());
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onSourceNameChange(parcel.readString());
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onDurationUpdated(parcel.readLong());
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                        onMirrorModeNotify(parcel.readInt());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public static class a implements IMiPlayTVClientCallback {
            public static IMiPlayTVClientCallback a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onInitSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    if (!this.b.transact(1, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInitSuccess();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onInitError() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    if (!this.b.transact(2, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInitError();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onCmdSessionError() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    if (!this.b.transact(3, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCmdSessionError();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onCmdSessionSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    if (!this.b.transact(4, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCmdSessionSuccess();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onPositionAck(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeLong(j);
                    if (!this.b.transact(5, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPositionAck(j);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onPlayStateChange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeInt(i);
                    if (!this.b.transact(6, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlayStateChange(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onPlayStateAck(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeInt(i);
                    if (!this.b.transact(7, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlayStateAck(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onMediaInfoAck(TVMediaMetaData tVMediaMetaData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    if (tVMediaMetaData != null) {
                        obtain.writeInt(1);
                        tVMediaMetaData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.b.transact(8, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMediaInfoAck(tVMediaMetaData);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onMediaInfoChange(TVMediaMetaData tVMediaMetaData) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    if (tVMediaMetaData != null) {
                        obtain.writeInt(1);
                        tVMediaMetaData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.b.transact(9, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMediaInfoChange(tVMediaMetaData);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onVolumeChange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeInt(i);
                    if (!this.b.transact(10, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeChange(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onVolumeAck(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeInt(i);
                    if (!this.b.transact(11, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeAck(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onSourceNameChange(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeString(str);
                    if (!this.b.transact(12, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSourceNameChange(str);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onDurationUpdated(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeLong(j);
                    if (!this.b.transact(13, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDurationUpdated(j);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
            public void onMirrorModeNotify(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback");
                    obtain.writeInt(i);
                    if (!this.b.transact(14, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMirrorModeNotify(i);
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiPlayTVClientCallback iMiPlayTVClientCallback) {
            if (a.a != null || iMiPlayTVClientCallback == null) {
                return false;
            }
            a.a = iMiPlayTVClientCallback;
            return true;
        }

        public static IMiPlayTVClientCallback getDefaultImpl() {
            return a.a;
        }
    }
}
