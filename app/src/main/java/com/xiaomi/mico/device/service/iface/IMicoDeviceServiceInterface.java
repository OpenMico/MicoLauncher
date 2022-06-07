package com.xiaomi.mico.device.service.iface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mico.device.service.iface.IUbusRelayCallback;

/* loaded from: classes3.dex */
public interface IMicoDeviceServiceInterface extends IInterface {
    void deviceRelay(String str, String str2, String str3, IUbusRelayCallback iUbusRelayCallback) throws RemoteException;

    String getBssid() throws RemoteException;

    String getHardware() throws RemoteException;

    String getLocale() throws RemoteException;

    String getMAC() throws RemoteException;

    String getMIBrainLevel() throws RemoteException;

    String getMiotDID() throws RemoteException;

    String getROMChannel() throws RemoteException;

    String getROMVersion() throws RemoteException;

    String getRegisterJsonExtras() throws RemoteException;

    String getSN() throws RemoteException;

    void setMIBrainLevel(String str) throws RemoteException;

    void ubusRelay(String str, String str2, String str3, IUbusRelayCallback iUbusRelayCallback) throws RemoteException;

    void unbindDevice(String str, IUbusRelayCallback iUbusRelayCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMicoDeviceServiceInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
        }

        public static IMicoDeviceServiceInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMicoDeviceServiceInterface)) {
                return new a(iBinder);
            }
            return (IMicoDeviceServiceInterface) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String sn = getSN();
                        parcel2.writeNoException();
                        parcel2.writeString(sn);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String mac = getMAC();
                        parcel2.writeNoException();
                        parcel2.writeString(mac);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String hardware = getHardware();
                        parcel2.writeNoException();
                        parcel2.writeString(hardware);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String rOMVersion = getROMVersion();
                        parcel2.writeNoException();
                        parcel2.writeString(rOMVersion);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String rOMChannel = getROMChannel();
                        parcel2.writeNoException();
                        parcel2.writeString(rOMChannel);
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String mIBrainLevel = getMIBrainLevel();
                        parcel2.writeNoException();
                        parcel2.writeString(mIBrainLevel);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String miotDID = getMiotDID();
                        parcel2.writeNoException();
                        parcel2.writeString(miotDID);
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String locale = getLocale();
                        parcel2.writeNoException();
                        parcel2.writeString(locale);
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String bssid = getBssid();
                        parcel2.writeNoException();
                        parcel2.writeString(bssid);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        String registerJsonExtras = getRegisterJsonExtras();
                        parcel2.writeNoException();
                        parcel2.writeString(registerJsonExtras);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        setMIBrainLevel(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        ubusRelay(parcel.readString(), parcel.readString(), parcel.readString(), IUbusRelayCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        deviceRelay(parcel.readString(), parcel.readString(), parcel.readString(), IUbusRelayCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                        unbindDevice(parcel.readString(), IUbusRelayCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IMicoDeviceServiceInterface {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getSN() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getMAC() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getHardware() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getROMVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getROMChannel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getMIBrainLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getMiotDID() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getLocale() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getBssid() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public String getRegisterJsonExtras() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public void setMIBrainLevel(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    obtain.writeString(str);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public void ubusRelay(String str, String str2, String str3, IUbusRelayCallback iUbusRelayCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iUbusRelayCallback != null ? iUbusRelayCallback.asBinder() : null);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public void deviceRelay(String str, String str2, String str3, IUbusRelayCallback iUbusRelayCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iUbusRelayCallback != null ? iUbusRelayCallback.asBinder() : null);
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
            public void unbindDevice(String str, IUbusRelayCallback iUbusRelayCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iUbusRelayCallback != null ? iUbusRelayCallback.asBinder() : null);
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
