package com.ktcp.aiagent.device.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl;
import com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl;

/* loaded from: classes2.dex */
public interface IDeviceVoiceServiceAidl extends IInterface {
    void abandonAudio() throws RemoteException;

    String call(String str, String str2) throws RemoteException;

    void callAsrEvent(String str) throws RemoteException;

    void closeLight() throws RemoteException;

    String getAuthConfig() throws RemoteException;

    void onAsrResult(String str) throws RemoteException;

    void openLight() throws RemoteException;

    void playTTS(String str) throws RemoteException;

    int readAudioData(byte[] bArr) throws RemoteException;

    void requestAudio(int i, int i2, int i3, IDeviceAudioEventAidl iDeviceAudioEventAidl) throws RemoteException;

    void setCallback(IDeviceCallbackAidl iDeviceCallbackAidl) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IDeviceVoiceServiceAidl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
        }

        public static IDeviceVoiceServiceAidl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceVoiceServiceAidl)) {
                return new a(iBinder);
            }
            return (IDeviceVoiceServiceAidl) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        requestAudio(parcel.readInt(), parcel.readInt(), parcel.readInt(), IDeviceAudioEventAidl.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        abandonAudio();
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        byte[] createByteArray = parcel.createByteArray();
                        int readAudioData = readAudioData(createByteArray);
                        parcel2.writeNoException();
                        parcel2.writeInt(readAudioData);
                        parcel2.writeByteArray(createByteArray);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        onAsrResult(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        playTTS(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        openLight();
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        closeLight();
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        setCallback(IDeviceCallbackAidl.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        String call = call(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(call);
                        return true;
                    case 10:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        String authConfig = getAuthConfig();
                        parcel2.writeNoException();
                        parcel2.writeString(authConfig);
                        return true;
                    case 11:
                        parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                        callAsrEvent(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class a implements IDeviceVoiceServiceAidl {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void requestAudio(int i, int i2, int i3, IDeviceAudioEventAidl iDeviceAudioEventAidl) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongBinder(iDeviceAudioEventAidl != null ? iDeviceAudioEventAidl.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void abandonAudio() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public int readAudioData(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    obtain.writeByteArray(bArr);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void onAsrResult(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    obtain.writeString(str);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void playTTS(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    obtain.writeString(str);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void openLight() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void closeLight() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void setCallback(IDeviceCallbackAidl iDeviceCallbackAidl) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    obtain.writeStrongBinder(iDeviceCallbackAidl != null ? iDeviceCallbackAidl.asBinder() : null);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public String call(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public String getAuthConfig() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
            public void callAsrEvent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl");
                    obtain.writeString(str);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
