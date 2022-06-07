package com.qiyi.video.pad.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.qiyi.video.pad.service.IQYPlayerCallback;

/* loaded from: classes2.dex */
public interface IQYPlayerService extends IInterface {
    QYResult action(int i) throws RemoteException;

    QYResult back() throws RemoteException;

    boolean backToCast() throws RemoteException;

    void brightness(int i) throws RemoteException;

    QYResult bringToForeground() throws RemoteException;

    boolean buyVip() throws RemoteException;

    void changeSize(int i) throws RemoteException;

    void endCast() throws RemoteException;

    void exit() throws RemoteException;

    boolean exitCast() throws RemoteException;

    void fastBackward(int i) throws RemoteException;

    void fastForward(int i) throws RemoteException;

    QYPlayerInfo getDetailedPlayerInfo() throws RemoteException;

    String getExtData(int i) throws RemoteException;

    int getLoginStatus() throws RemoteException;

    int getPlayPosition() throws RemoteException;

    QYPlayerInfo getPlayerInfo() throws RemoteException;

    void hideExtBtn() throws RemoteException;

    void hideStatusBar() throws RemoteException;

    boolean isCast() throws RemoteException;

    boolean isForeground() throws RemoteException;

    boolean login() throws RemoteException;

    boolean logout() throws RemoteException;

    void next() throws RemoteException;

    boolean openEpisode() throws RemoteException;

    void pause() throws RemoteException;

    void play() throws RemoteException;

    void playEpisode(int i) throws RemoteException;

    void refreshDlnaDeviceList() throws RemoteException;

    void registerPlayerCallback(IQYPlayerCallback iQYPlayerCallback) throws RemoteException;

    void seek(int i) throws RemoteException;

    void selectDlnaDevice(String str) throws RemoteException;

    void selectDlnaDeviceByIndex(int i) throws RemoteException;

    void selectDlnaDeviceByName(String str) throws RemoteException;

    void setCastVolume(int i) throws RemoteException;

    void shakeExtBtn() throws RemoteException;

    void showControls(boolean z) throws RemoteException;

    void showDlnaDeviceList(boolean z) throws RemoteException;

    void showExtBtnOnControls(String str) throws RemoteException;

    void skip(boolean z) throws RemoteException;

    void speed(int i) throws RemoteException;

    void startCast() throws RemoteException;

    QYResult startScan(int i, boolean z) throws RemoteException;

    void stop() throws RemoteException;

    void stream(int i) throws RemoteException;

    void volume(int i) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IQYPlayerService {
        private static final String DESCRIPTOR = "com.qiyi.video.pad.service.IQYPlayerService";
        static final int TRANSACTION_action = 30;
        static final int TRANSACTION_back = 29;
        static final int TRANSACTION_backToCast = 34;
        static final int TRANSACTION_brightness = 26;
        static final int TRANSACTION_bringToForeground = 32;
        static final int TRANSACTION_buyVip = 37;
        static final int TRANSACTION_changeSize = 23;
        static final int TRANSACTION_endCast = 21;
        static final int TRANSACTION_exit = 33;
        static final int TRANSACTION_exitCast = 41;
        static final int TRANSACTION_fastBackward = 13;
        static final int TRANSACTION_fastForward = 12;
        static final int TRANSACTION_getDetailedPlayerInfo = 2;
        static final int TRANSACTION_getExtData = 3;
        static final int TRANSACTION_getLoginStatus = 42;
        static final int TRANSACTION_getPlayPosition = 43;
        static final int TRANSACTION_getPlayerInfo = 1;
        static final int TRANSACTION_hideExtBtn = 45;
        static final int TRANSACTION_hideStatusBar = 40;
        static final int TRANSACTION_isCast = 39;
        static final int TRANSACTION_isForeground = 31;
        static final int TRANSACTION_login = 35;
        static final int TRANSACTION_logout = 36;
        static final int TRANSACTION_next = 14;
        static final int TRANSACTION_openEpisode = 38;
        static final int TRANSACTION_pause = 11;
        static final int TRANSACTION_play = 10;
        static final int TRANSACTION_playEpisode = 27;
        static final int TRANSACTION_refreshDlnaDeviceList = 5;
        static final int TRANSACTION_registerPlayerCallback = 9;
        static final int TRANSACTION_seek = 16;
        static final int TRANSACTION_selectDlnaDevice = 6;
        static final int TRANSACTION_selectDlnaDeviceByIndex = 7;
        static final int TRANSACTION_selectDlnaDeviceByName = 8;
        static final int TRANSACTION_setCastVolume = 25;
        static final int TRANSACTION_shakeExtBtn = 46;
        static final int TRANSACTION_showControls = 28;
        static final int TRANSACTION_showDlnaDeviceList = 4;
        static final int TRANSACTION_showExtBtnOnControls = 44;
        static final int TRANSACTION_skip = 19;
        static final int TRANSACTION_speed = 17;
        static final int TRANSACTION_startCast = 20;
        static final int TRANSACTION_startScan = 22;
        static final int TRANSACTION_stop = 15;
        static final int TRANSACTION_stream = 18;
        static final int TRANSACTION_volume = 24;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IQYPlayerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IQYPlayerService)) {
                return new Proxy(iBinder);
            }
            return (IQYPlayerService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        QYPlayerInfo playerInfo = getPlayerInfo();
                        parcel2.writeNoException();
                        if (playerInfo != null) {
                            parcel2.writeInt(1);
                            playerInfo.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        QYPlayerInfo detailedPlayerInfo = getDetailedPlayerInfo();
                        parcel2.writeNoException();
                        if (detailedPlayerInfo != null) {
                            parcel2.writeInt(1);
                            detailedPlayerInfo.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        String extData = getExtData(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeString(extData);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        showDlnaDeviceList(z);
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        refreshDlnaDeviceList();
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        selectDlnaDevice(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        selectDlnaDeviceByIndex(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        selectDlnaDeviceByName(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerPlayerCallback(IQYPlayerCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        play();
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        pause();
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        fastForward(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        fastBackward(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        next();
                        parcel2.writeNoException();
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        stop();
                        parcel2.writeNoException();
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        seek(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        speed(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        stream(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        skip(z);
                        parcel2.writeNoException();
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        startCast();
                        parcel2.writeNoException();
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        endCast();
                        parcel2.writeNoException();
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        QYResult startScan = startScan(parcel.readInt(), parcel.readInt() != 0);
                        parcel2.writeNoException();
                        if (startScan != null) {
                            parcel2.writeInt(1);
                            startScan.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        changeSize(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        volume(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        setCastVolume(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 26:
                        parcel.enforceInterface(DESCRIPTOR);
                        brightness(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 27:
                        parcel.enforceInterface(DESCRIPTOR);
                        playEpisode(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 28:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        showControls(z);
                        parcel2.writeNoException();
                        return true;
                    case 29:
                        parcel.enforceInterface(DESCRIPTOR);
                        QYResult back = back();
                        parcel2.writeNoException();
                        if (back != null) {
                            parcel2.writeInt(1);
                            back.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 30:
                        parcel.enforceInterface(DESCRIPTOR);
                        QYResult action = action(parcel.readInt());
                        parcel2.writeNoException();
                        if (action != null) {
                            parcel2.writeInt(1);
                            action.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 31:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean isForeground = isForeground();
                        parcel2.writeNoException();
                        parcel2.writeInt(isForeground ? 1 : 0);
                        return true;
                    case 32:
                        parcel.enforceInterface(DESCRIPTOR);
                        QYResult bringToForeground = bringToForeground();
                        parcel2.writeNoException();
                        if (bringToForeground != null) {
                            parcel2.writeInt(1);
                            bringToForeground.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 33:
                        parcel.enforceInterface(DESCRIPTOR);
                        exit();
                        parcel2.writeNoException();
                        return true;
                    case 34:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean backToCast = backToCast();
                        parcel2.writeNoException();
                        parcel2.writeInt(backToCast ? 1 : 0);
                        return true;
                    case 35:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean login = login();
                        parcel2.writeNoException();
                        parcel2.writeInt(login ? 1 : 0);
                        return true;
                    case 36:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean logout = logout();
                        parcel2.writeNoException();
                        parcel2.writeInt(logout ? 1 : 0);
                        return true;
                    case 37:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean buyVip = buyVip();
                        parcel2.writeNoException();
                        parcel2.writeInt(buyVip ? 1 : 0);
                        return true;
                    case 38:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean openEpisode = openEpisode();
                        parcel2.writeNoException();
                        parcel2.writeInt(openEpisode ? 1 : 0);
                        return true;
                    case 39:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean isCast = isCast();
                        parcel2.writeNoException();
                        parcel2.writeInt(isCast ? 1 : 0);
                        return true;
                    case 40:
                        parcel.enforceInterface(DESCRIPTOR);
                        hideStatusBar();
                        parcel2.writeNoException();
                        return true;
                    case 41:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean exitCast = exitCast();
                        parcel2.writeNoException();
                        parcel2.writeInt(exitCast ? 1 : 0);
                        return true;
                    case 42:
                        parcel.enforceInterface(DESCRIPTOR);
                        int loginStatus = getLoginStatus();
                        parcel2.writeNoException();
                        parcel2.writeInt(loginStatus);
                        return true;
                    case 43:
                        parcel.enforceInterface(DESCRIPTOR);
                        int playPosition = getPlayPosition();
                        parcel2.writeNoException();
                        parcel2.writeInt(playPosition);
                        return true;
                    case 44:
                        parcel.enforceInterface(DESCRIPTOR);
                        showExtBtnOnControls(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 45:
                        parcel.enforceInterface(DESCRIPTOR);
                        hideExtBtn();
                        parcel2.writeNoException();
                        return true;
                    case 46:
                        parcel.enforceInterface(DESCRIPTOR);
                        shakeExtBtn();
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* loaded from: classes2.dex */
        private static class Proxy implements IQYPlayerService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public QYPlayerInfo getPlayerInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? QYPlayerInfo.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public QYPlayerInfo getDetailedPlayerInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? QYPlayerInfo.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public String getExtData(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void showDlnaDeviceList(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void refreshDlnaDeviceList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void selectDlnaDevice(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void selectDlnaDeviceByIndex(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void selectDlnaDeviceByName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void registerPlayerCallback(IQYPlayerCallback iQYPlayerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iQYPlayerCallback != null ? iQYPlayerCallback.asBinder() : null);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void play() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void pause() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void fastForward(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void fastBackward(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void next() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void seek(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void speed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void stream(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void skip(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void startCast() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void endCast() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public QYResult startScan(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? QYResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void changeSize(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void volume(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void setCastVolume(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void brightness(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void playEpisode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void showControls(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public QYResult back() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? QYResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public QYResult action(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? QYResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean isForeground() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(31, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public QYResult bringToForeground() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? QYResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void exit() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean backToCast() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(34, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean login() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(35, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean logout() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(36, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean buyVip() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(37, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean openEpisode() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(38, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean isCast() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(39, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void hideStatusBar() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public boolean exitCast() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(41, obtain, obtain2, 0);
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

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public int getLoginStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public int getPlayPosition() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void showExtBtnOnControls(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void hideExtBtn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.qiyi.video.pad.service.IQYPlayerService
            public void shakeExtBtn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
