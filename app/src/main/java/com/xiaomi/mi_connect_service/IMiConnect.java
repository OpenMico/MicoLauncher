package com.xiaomi.mi_connect_service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.mi_connect_service.IApStateCallback;
import com.xiaomi.mi_connect_service.IConnectionCallback;
import com.xiaomi.mi_connect_service.IDeviceScannerCallback;
import com.xiaomi.mi_connect_service.IDpsMessageListener;
import com.xiaomi.mi_connect_service.IIDMClientCallback;
import com.xiaomi.mi_connect_service.IIDMServiceProcCallback;
import com.xiaomi.mi_connect_service.IIPCDataCallback;
import com.xiaomi.mi_connect_service.IMiConnectCallback;
import com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack;

/* loaded from: classes3.dex */
public interface IMiConnect extends IInterface {
    public static final int ERROR = -1;
    public static final int OK = 0;

    int abortInvitation(String str, byte[] bArr) throws RemoteException;

    void acceptConnection(int i, int i2, int i3, boolean z) throws RemoteException;

    int acceptInvitation(String str, byte[] bArr) throws RemoteException;

    int clientAcceptConnection(String str, byte[] bArr) throws RemoteException;

    int clientRejectConnection(String str, byte[] bArr) throws RemoteException;

    int connectService(String str, byte[] bArr) throws RemoteException;

    int connectServiceStatusResponse(String str, byte[] bArr) throws RemoteException;

    int createConnection(String str, byte[] bArr, IConnectionCallback iConnectionCallback) throws RemoteException;

    void destroy(int i, int i2) throws RemoteException;

    int destroyConnection(String str, byte[] bArr) throws RemoteException;

    byte[] deviceInfoIDM() throws RemoteException;

    void disconnectFromEndPoint(int i, int i2, int i3) throws RemoteException;

    int disconnectService(String str, byte[] bArr) throws RemoteException;

    int event(String str, byte[] bArr) throws RemoteException;

    byte[] getIdHash() throws RemoteException;

    int getServiceApiVersion() throws RemoteException;

    int inviteConnection(String str, byte[] bArr) throws RemoteException;

    int publish(int i, String str, String str2, byte[] bArr) throws RemoteException;

    String registerIDMClient(String str, byte[] bArr, IIDMClientCallback iIDMClientCallback) throws RemoteException;

    int registerProc(String str, byte[] bArr, IIDMServiceProcCallback iIDMServiceProcCallback) throws RemoteException;

    int registerSoundBoxWhiteName(ISoundBoxWhiteNameCallBack iSoundBoxWhiteNameCallBack) throws RemoteException;

    void rejectConnection(int i, int i2, int i3) throws RemoteException;

    byte[] request(String str, byte[] bArr) throws RemoteException;

    void requestConnection(int i, int i2, byte[] bArr) throws RemoteException;

    int response(String str, byte[] bArr) throws RemoteException;

    void sendPayload(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void setCallback(int i, int i2, IMiConnectCallback iMiConnectCallback) throws RemoteException;

    int setEventCallback(String str, byte[] bArr) throws RemoteException;

    int setIPCDataCallback(int i, String str, IIPCDataCallback iIPCDataCallback) throws RemoteException;

    void startAdvertising(int i, byte[] bArr, int i2, int i3, byte[] bArr2) throws RemoteException;

    String startAdvertisingIDM(String str, byte[] bArr) throws RemoteException;

    void startDiscovery(int i, byte[] bArr, int i2, int i3, int i4) throws RemoteException;

    int startDiscoveryIDM(String str, byte[] bArr) throws RemoteException;

    void startDiscoveryV2(int i, byte[] bArr, int i2, int i3, int i4, int[] iArr) throws RemoteException;

    long startSap(String str, String str2, int i, boolean z, IApStateCallback iApStateCallback) throws RemoteException;

    int startScannerList(int i, IDeviceScannerCallback iDeviceScannerCallback, String str) throws RemoteException;

    void stopAdvertising(int i) throws RemoteException;

    int stopAdvertisingIDM(String str, byte[] bArr) throws RemoteException;

    void stopDiscovery(int i) throws RemoteException;

    int stopDiscoveryIDM(String str, byte[] bArr) throws RemoteException;

    void stopSap(long j) throws RemoteException;

    int subscribe(int i, String str, String str2, IDpsMessageListener iDpsMessageListener) throws RemoteException;

    int unregisterIDMClient(String str) throws RemoteException;

    int unregisterProc(String str) throws RemoteException;

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMiConnect {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mi_connect_service.IMiConnect");
        }

        public static IMiConnect asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mi_connect_service.IMiConnect");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiConnect)) {
                return new a(iBinder);
            }
            return (IMiConnect) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        setCallback(parcel.readInt(), parcel.readInt(), IMiConnectCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        startAdvertising(parcel.readInt(), parcel.createByteArray(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        stopAdvertising(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        startDiscovery(parcel.readInt(), parcel.createByteArray(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        stopDiscovery(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int serviceApiVersion = getServiceApiVersion();
                        parcel2.writeNoException();
                        parcel2.writeInt(serviceApiVersion);
                        return true;
                    case 7:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        requestConnection(parcel.readInt(), parcel.readInt(), parcel.createByteArray());
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int readInt = parcel.readInt();
                        int readInt2 = parcel.readInt();
                        int readInt3 = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        acceptConnection(readInt, readInt2, readInt3, z);
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        rejectConnection(parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        disconnectFromEndPoint(parcel.readInt(), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        sendPayload(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        destroy(parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 13:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        byte[] idHash = getIdHash();
                        parcel2.writeNoException();
                        parcel2.writeByteArray(idHash);
                        return true;
                    case 14:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int iPCDataCallback = setIPCDataCallback(parcel.readInt(), parcel.readString(), IIPCDataCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(iPCDataCallback);
                        return true;
                    case 15:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int publish = publish(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(publish);
                        return true;
                    case 16:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int subscribe = subscribe(parcel.readInt(), parcel.readString(), parcel.readString(), IDpsMessageListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(subscribe);
                        return true;
                    case 17:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int createConnection = createConnection(parcel.readString(), parcel.createByteArray(), IConnectionCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(createConnection);
                        return true;
                    case 18:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int destroyConnection = destroyConnection(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(destroyConnection);
                        return true;
                    case 19:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        String registerIDMClient = registerIDMClient(parcel.readString(), parcel.createByteArray(), IIDMClientCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeString(registerIDMClient);
                        return true;
                    case 20:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        byte[] request = request(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeByteArray(request);
                        return true;
                    case 21:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int startDiscoveryIDM = startDiscoveryIDM(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(startDiscoveryIDM);
                        return true;
                    case 22:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int eventCallback = setEventCallback(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(eventCallback);
                        return true;
                    case 23:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int unregisterIDMClient = unregisterIDMClient(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(unregisterIDMClient);
                        return true;
                    case 24:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int connectService = connectService(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(connectService);
                        return true;
                    case 25:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int registerProc = registerProc(parcel.readString(), parcel.createByteArray(), IIDMServiceProcCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(registerProc);
                        return true;
                    case 26:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        String startAdvertisingIDM = startAdvertisingIDM(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeString(startAdvertisingIDM);
                        return true;
                    case 27:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int response = response(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(response);
                        return true;
                    case 28:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int connectServiceStatusResponse = connectServiceStatusResponse(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(connectServiceStatusResponse);
                        return true;
                    case 29:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int event = event(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(event);
                        return true;
                    case 30:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int unregisterProc = unregisterProc(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(unregisterProc);
                        return true;
                    case 31:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        startDiscoveryV2(parcel.readInt(), parcel.createByteArray(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createIntArray());
                        parcel2.writeNoException();
                        return true;
                    case 32:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        byte[] deviceInfoIDM = deviceInfoIDM();
                        parcel2.writeNoException();
                        parcel2.writeByteArray(deviceInfoIDM);
                        return true;
                    case 33:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int stopDiscoveryIDM = stopDiscoveryIDM(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(stopDiscoveryIDM);
                        return true;
                    case 34:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int stopAdvertisingIDM = stopAdvertisingIDM(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(stopAdvertisingIDM);
                        return true;
                    case 35:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int startScannerList = startScannerList(parcel.readInt(), IDeviceScannerCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(startScannerList);
                        return true;
                    case 36:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int registerSoundBoxWhiteName = registerSoundBoxWhiteName(ISoundBoxWhiteNameCallBack.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(registerSoundBoxWhiteName);
                        return true;
                    case 37:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        long startSap = startSap(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt() != 0, IApStateCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeLong(startSap);
                        return true;
                    case 38:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        stopSap(parcel.readLong());
                        parcel2.writeNoException();
                        return true;
                    case 39:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int disconnectService = disconnectService(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(disconnectService);
                        return true;
                    case 40:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int clientAcceptConnection = clientAcceptConnection(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(clientAcceptConnection);
                        return true;
                    case 41:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int clientRejectConnection = clientRejectConnection(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(clientRejectConnection);
                        return true;
                    case 42:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int inviteConnection = inviteConnection(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(inviteConnection);
                        return true;
                    case 43:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int acceptInvitation = acceptInvitation(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(acceptInvitation);
                        return true;
                    case 44:
                        parcel.enforceInterface("com.xiaomi.mi_connect_service.IMiConnect");
                        int abortInvitation = abortInvitation(parcel.readString(), parcel.createByteArray());
                        parcel2.writeNoException();
                        parcel2.writeInt(abortInvitation);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.xiaomi.mi_connect_service.IMiConnect");
                return true;
            }
        }

        /* loaded from: classes3.dex */
        private static class a implements IMiConnect {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void setCallback(int i, int i2, IMiConnectCallback iMiConnectCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongBinder(iMiConnectCallback != null ? iMiConnectCallback.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void startAdvertising(int i, byte[] bArr, int i2, int i3, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr2);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void stopAdvertising(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void startDiscovery(int i, byte[] bArr, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void stopDiscovery(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int getServiceApiVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void requestConnection(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void acceptConnection(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void rejectConnection(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void disconnectFromEndPoint(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void sendPayload(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void destroy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public byte[] getIdHash() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int setIPCDataCallback(int i, String str, IIPCDataCallback iIPCDataCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iIPCDataCallback != null ? iIPCDataCallback.asBinder() : null);
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int publish(int i, String str, String str2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    this.a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int subscribe(int i, String str, String str2, IDpsMessageListener iDpsMessageListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iDpsMessageListener != null ? iDpsMessageListener.asBinder() : null);
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int createConnection(String str, byte[] bArr, IConnectionCallback iConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongBinder(iConnectionCallback != null ? iConnectionCallback.asBinder() : null);
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int destroyConnection(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public String registerIDMClient(String str, byte[] bArr, IIDMClientCallback iIDMClientCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongBinder(iIDMClientCallback != null ? iIDMClientCallback.asBinder() : null);
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public byte[] request(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int startDiscoveryIDM(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int setEventCallback(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int unregisterIDMClient(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    this.a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int connectService(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int registerProc(String str, byte[] bArr, IIDMServiceProcCallback iIDMServiceProcCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongBinder(iIDMServiceProcCallback != null ? iIDMServiceProcCallback.asBinder() : null);
                    this.a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public String startAdvertisingIDM(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int response(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int connectServiceStatusResponse(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int event(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int unregisterProc(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    this.a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void startDiscoveryV2(int i, byte[] bArr, int i2, int i3, int i4, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeIntArray(iArr);
                    this.a.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public byte[] deviceInfoIDM() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    this.a.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int stopDiscoveryIDM(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int stopAdvertisingIDM(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int startScannerList(int i, IDeviceScannerCallback iDeviceScannerCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iDeviceScannerCallback != null ? iDeviceScannerCallback.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int registerSoundBoxWhiteName(ISoundBoxWhiteNameCallBack iSoundBoxWhiteNameCallBack) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeStrongBinder(iSoundBoxWhiteNameCallBack != null ? iSoundBoxWhiteNameCallBack.asBinder() : null);
                    this.a.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public long startSap(String str, String str2, int i, boolean z, IApStateCallback iApStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeStrongBinder(iApStateCallback != null ? iApStateCallback.asBinder() : null);
                    this.a.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public void stopSap(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeLong(j);
                    this.a.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int disconnectService(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int clientAcceptConnection(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int clientRejectConnection(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int inviteConnection(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int acceptInvitation(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.mi_connect_service.IMiConnect
            public int abortInvitation(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.xiaomi.mi_connect_service.IMiConnect");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.a.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
