package com.xiaomi.miplay.phoneclientsdk.external;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IMiPlayExternalClientCallback extends IInterface {

    /* loaded from: classes4.dex */
    public static class Default implements IMiPlayExternalClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onBuffering() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onCirculateEnd() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onCirculateFail(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onCirculateModeChange(int i) throws RemoteException {
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onCirculateStart() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onConnectMirrorSuccess() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onInitError() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onInitSuccess() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onNext() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onNotifyPropertiesInfo(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPaused() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPlayed() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPositionChanged(long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onPrev() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onResumeMirrorFail() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onResumeMirrorSuccess() throws RemoteException {
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onResumed() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onSeekDoneNotify() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onSeekedTo(long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public int onStopped(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
        public void onVolumeChange(double d) throws RemoteException {
        }
    }

    void onBuffering() throws RemoteException;

    int onCirculateEnd() throws RemoteException;

    int onCirculateFail(String str) throws RemoteException;

    void onCirculateModeChange(int i) throws RemoteException;

    int onCirculateStart() throws RemoteException;

    void onConnectMirrorSuccess() throws RemoteException;

    void onInitError() throws RemoteException;

    void onInitSuccess() throws RemoteException;

    int onNext() throws RemoteException;

    int onNotifyPropertiesInfo(String str) throws RemoteException;

    int onPaused() throws RemoteException;

    int onPlayed() throws RemoteException;

    int onPositionChanged(long j) throws RemoteException;

    int onPrev() throws RemoteException;

    void onResumeMirrorFail() throws RemoteException;

    void onResumeMirrorSuccess() throws RemoteException;

    int onResumed() throws RemoteException;

    int onSeekDoneNotify() throws RemoteException;

    int onSeekedTo(long j) throws RemoteException;

    int onStopped(int i) throws RemoteException;

    void onVolumeChange(double d) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IMiPlayExternalClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
        }

        public static IMiPlayExternalClientCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiPlayExternalClientCallback)) {
                return new a(iBinder);
            }
            return (IMiPlayExternalClientCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onInitSuccess();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onInitError();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onPositionChanged = onPositionChanged(parcel.readLong());
                        parcel2.writeNoException();
                        parcel2.writeInt(onPositionChanged);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onPlayed = onPlayed();
                        parcel2.writeNoException();
                        parcel2.writeInt(onPlayed);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onStopped = onStopped(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(onStopped);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onNotifyPropertiesInfo = onNotifyPropertiesInfo(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(onNotifyPropertiesInfo);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onNext = onNext();
                        parcel2.writeNoException();
                        parcel2.writeInt(onNext);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onPrev = onPrev();
                        parcel2.writeNoException();
                        parcel2.writeInt(onPrev);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onCirculateStart = onCirculateStart();
                        parcel2.writeNoException();
                        parcel2.writeInt(onCirculateStart);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onCirculateEnd = onCirculateEnd();
                        parcel2.writeNoException();
                        parcel2.writeInt(onCirculateEnd);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onPaused = onPaused();
                        parcel2.writeNoException();
                        parcel2.writeInt(onPaused);
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onResumed = onResumed();
                        parcel2.writeNoException();
                        parcel2.writeInt(onResumed);
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onSeekedTo = onSeekedTo(parcel.readLong());
                        parcel2.writeNoException();
                        parcel2.writeInt(onSeekedTo);
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onCirculateFail = onCirculateFail(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(onCirculateFail);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        int onSeekDoneNotify = onSeekDoneNotify();
                        parcel2.writeNoException();
                        parcel2.writeInt(onSeekDoneNotify);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onVolumeChange(parcel.readDouble());
                        parcel2.writeNoException();
                        return true;
                    case 17:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onCirculateModeChange(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 18:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onConnectMirrorSuccess();
                        parcel2.writeNoException();
                        return true;
                    case 19:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onBuffering();
                        parcel2.writeNoException();
                        return true;
                    case 20:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onResumeMirrorSuccess();
                        parcel2.writeNoException();
                        return true;
                    case 21:
                        parcel.enforceInterface("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                        onResumeMirrorFail();
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes4.dex */
        public static class a implements IMiPlayExternalClientCallback {
            public static IMiPlayExternalClientCallback a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onInitSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(1, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInitSuccess();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onInitError() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(2, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInitError();
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onPositionChanged(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    obtain.writeLong(j);
                    if (!this.b.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPositionChanged(j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onPlayed() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPlayed();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onStopped(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    obtain.writeInt(i);
                    if (!this.b.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onStopped(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onNotifyPropertiesInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    obtain.writeString(str);
                    if (!this.b.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onNotifyPropertiesInfo(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onNext() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onNext();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onPrev() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPrev();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onCirculateStart() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onCirculateStart();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onCirculateEnd() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onCirculateEnd();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onPaused() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onPaused();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onResumed() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onResumed();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onSeekedTo(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    obtain.writeLong(j);
                    if (!this.b.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onSeekedTo(j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onCirculateFail(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    obtain.writeString(str);
                    if (!this.b.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onCirculateFail(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public int onSeekDoneNotify() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (!this.b.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onSeekDoneNotify();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onVolumeChange(double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    obtain.writeDouble(d);
                    if (this.b.transact(16, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onVolumeChange(d);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onCirculateModeChange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    obtain.writeInt(i);
                    if (this.b.transact(17, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onCirculateModeChange(i);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onConnectMirrorSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (this.b.transact(18, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onConnectMirrorSuccess();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onBuffering() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (this.b.transact(19, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onBuffering();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onResumeMirrorSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (this.b.transact(20, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onResumeMirrorSuccess();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback
            public void onResumeMirrorFail() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback");
                    if (this.b.transact(21, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                    } else {
                        Stub.getDefaultImpl().onResumeMirrorFail();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiPlayExternalClientCallback iMiPlayExternalClientCallback) {
            if (a.a != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iMiPlayExternalClientCallback == null) {
                return false;
            } else {
                a.a = iMiPlayExternalClientCallback;
                return true;
            }
        }

        public static IMiPlayExternalClientCallback getDefaultImpl() {
            return a.a;
        }
    }
}
