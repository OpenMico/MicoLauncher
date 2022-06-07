package com.xiaomi.mico.mqtt.service.iface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mico.mqtt.service.iface.IAiServiceTokenCallback;
import com.xiaomi.mico.mqtt.service.iface.IBindDeviceCallback;
import com.xiaomi.mico.mqtt.service.iface.IIdentifyDeviceCallback;

/* loaded from: classes3.dex */
public interface IMQTTService extends IInterface {
    void allowDeviceOnline() throws RemoteException;

    void bindDevice(long j, String str, IBindDeviceCallback iBindDeviceCallback) throws RemoteException;

    String echo(String str) throws RemoteException;

    void forceDeviceOffline() throws RemoteException;

    void forceRefreshAiServiceToken(IAiServiceTokenCallback iAiServiceTokenCallback) throws RemoteException;

    String getAiServiceToken() throws RemoteException;

    String getDeviceID() throws RemoteException;

    String getDeviceIDAndChannelSecret() throws RemoteException;

    long getSuperAmin() throws RemoteException;

    void identifyDevice(String str, IIdentifyDeviceCallback iIdentifyDeviceCallback) throws RemoteException;

    void registerAndBindDevice(long j, String str, String str2, IBindDeviceCallback iBindDeviceCallback) throws RemoteException;

    void unbindDevice(IBindDeviceCallback iBindDeviceCallback) throws RemoteException;

    void uploadMiotToken(long j, String str, String str2, IAiServiceTokenCallback iAiServiceTokenCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMQTTService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.mqtt.service.iface.IMQTTService");
        }

        public static IMQTTService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMQTTService)) {
                return new a(iBinder);
            }
            return (IMQTTService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        String echo = echo(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(echo);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        bindDevice(parcel.readLong(), parcel.readString(), IBindDeviceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        unbindDevice(IBindDeviceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        String deviceID = getDeviceID();
                        parcel2.writeNoException();
                        parcel2.writeString(deviceID);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        String deviceIDAndChannelSecret = getDeviceIDAndChannelSecret();
                        parcel2.writeNoException();
                        parcel2.writeString(deviceIDAndChannelSecret);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        identifyDevice(parcel.readString(), IIdentifyDeviceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        long superAmin = getSuperAmin();
                        parcel2.writeNoException();
                        parcel2.writeLong(superAmin);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        String aiServiceToken = getAiServiceToken();
                        parcel2.writeNoException();
                        parcel2.writeString(aiServiceToken);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        forceRefreshAiServiceToken(IAiServiceTokenCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        uploadMiotToken(parcel.readLong(), parcel.readString(), parcel.readString(), IAiServiceTokenCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        forceDeviceOffline();
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        allowDeviceOnline();
                        parcel2.writeNoException();
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                        registerAndBindDevice(parcel.readLong(), parcel.readString(), parcel.readString(), IBindDeviceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IMQTTService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public String echo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    obtain.writeString(str);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void bindDevice(long j, String str, IBindDeviceCallback iBindDeviceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBindDeviceCallback != null ? iBindDeviceCallback.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void unbindDevice(IBindDeviceCallback iBindDeviceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    obtain.writeStrongBinder(iBindDeviceCallback != null ? iBindDeviceCallback.asBinder() : null);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public String getDeviceID() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public String getDeviceIDAndChannelSecret() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void identifyDevice(String str, IIdentifyDeviceCallback iIdentifyDeviceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iIdentifyDeviceCallback != null ? iIdentifyDeviceCallback.asBinder() : null);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public long getSuperAmin() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public String getAiServiceToken() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void forceRefreshAiServiceToken(IAiServiceTokenCallback iAiServiceTokenCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    obtain.writeStrongBinder(iAiServiceTokenCallback != null ? iAiServiceTokenCallback.asBinder() : null);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void uploadMiotToken(long j, String str, String str2, IAiServiceTokenCallback iAiServiceTokenCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iAiServiceTokenCallback != null ? iAiServiceTokenCallback.asBinder() : null);
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void forceDeviceOffline() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void allowDeviceOnline() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.mqtt.service.iface.IMQTTService
            public void registerAndBindDevice(long j, String str, String str2, IBindDeviceCallback iBindDeviceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.mqtt.service.iface.IMQTTService");
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBindDeviceCallback != null ? iBindDeviceCallback.asBinder() : null);
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
