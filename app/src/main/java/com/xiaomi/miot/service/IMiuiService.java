package com.xiaomi.miot.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miot.service.ICallback;
import com.xiaomi.miot.service.IMsgCallback;

/* loaded from: classes2.dex */
public interface IMiuiService extends IInterface {
    void callSmartHomeApi(String str, String str2, ICallback iCallback) throws RemoteException;

    void callSmartHomeApiWithMethod(String str, String str2, String str3, ICallback iCallback) throws RemoteException;

    void enterDeviceListPage() throws RemoteException;

    void exitDeviceListPage() throws RemoteException;

    int getApiLevel() throws RemoteException;

    void getCommonUsedScene(String str, ICallback iCallback) throws RemoteException;

    void getDeviceList(ICallback iCallback) throws RemoteException;

    void getDeviceListByHomeId(String str, ICallback iCallback) throws RemoteException;

    void getSupportDoorbellPdids(ICallback iCallback) throws RemoteException;

    void isDeviceSetPincode(String str, ICallback iCallback) throws RemoteException;

    void isLogin(ICallback iCallback) throws RemoteException;

    void isLoginFromUid(String str, ICallback iCallback) throws RemoteException;

    void logoutMiHome(ICallback iCallback) throws RemoteException;

    void registerMsgCallback(IMsgCallback iMsgCallback) throws RemoteException;

    void runSpecScene(long j, int i, ICallback iCallback) throws RemoteException;

    String sendMsg(String str) throws RemoteException;

    void setDeviceOn(String str, boolean z, ICallback iCallback) throws RemoteException;

    void unRegisterMsgCallback(String str) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IMiuiService {
        private static final String DESCRIPTOR = "com.xiaomi.miot.service.IMiuiService";
        static final int TRANSACTION_callSmartHomeApi = 5;
        static final int TRANSACTION_callSmartHomeApiWithMethod = 7;
        static final int TRANSACTION_enterDeviceListPage = 9;
        static final int TRANSACTION_exitDeviceListPage = 10;
        static final int TRANSACTION_getApiLevel = 11;
        static final int TRANSACTION_getCommonUsedScene = 15;
        static final int TRANSACTION_getDeviceList = 2;
        static final int TRANSACTION_getDeviceListByHomeId = 3;
        static final int TRANSACTION_getSupportDoorbellPdids = 6;
        static final int TRANSACTION_isDeviceSetPincode = 8;
        static final int TRANSACTION_isLogin = 1;
        static final int TRANSACTION_isLoginFromUid = 12;
        static final int TRANSACTION_logoutMiHome = 13;
        static final int TRANSACTION_registerMsgCallback = 17;
        static final int TRANSACTION_runSpecScene = 16;
        static final int TRANSACTION_sendMsg = 14;
        static final int TRANSACTION_setDeviceOn = 4;
        static final int TRANSACTION_unRegisterMsgCallback = 18;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMiuiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiuiService)) {
                return new Proxy(iBinder);
            }
            return (IMiuiService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        isLogin(ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        getDeviceList(ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        getDeviceListByHomeId(parcel.readString(), ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        setDeviceOn(parcel.readString(), parcel.readInt() != 0, ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        callSmartHomeApi(parcel.readString(), parcel.readString(), ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        getSupportDoorbellPdids(ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        callSmartHomeApiWithMethod(parcel.readString(), parcel.readString(), parcel.readString(), ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        isDeviceSetPincode(parcel.readString(), ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        enterDeviceListPage();
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        exitDeviceListPage();
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        int apiLevel = getApiLevel();
                        parcel2.writeNoException();
                        parcel2.writeInt(apiLevel);
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        isLoginFromUid(parcel.readString(), ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        logoutMiHome(ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        String sendMsg = sendMsg(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(sendMsg);
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        getCommonUsedScene(parcel.readString(), ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        runSpecScene(parcel.readLong(), parcel.readInt(), ICallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMsgCallback(IMsgCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        unRegisterMsgCallback(parcel.readString());
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
        private static class Proxy implements IMiuiService {
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

            @Override // com.xiaomi.miot.service.IMiuiService
            public void isLogin(ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void getDeviceList(ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void getDeviceListByHomeId(String str, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void setDeviceOn(String str, boolean z, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void callSmartHomeApi(String str, String str2, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void getSupportDoorbellPdids(ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void callSmartHomeApiWithMethod(String str, String str2, String str3, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void isDeviceSetPincode(String str, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void enterDeviceListPage() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void exitDeviceListPage() throws RemoteException {
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

            @Override // com.xiaomi.miot.service.IMiuiService
            public int getApiLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void isLoginFromUid(String str, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void logoutMiHome(ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public String sendMsg(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void getCommonUsedScene(String str, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void runSpecScene(long j, int i, ICallback iCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iCallback != null ? iCallback.asBinder() : null);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void registerMsgCallback(IMsgCallback iMsgCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMsgCallback != null ? iMsgCallback.asBinder() : null);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.service.IMiuiService
            public void unRegisterMsgCallback(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
