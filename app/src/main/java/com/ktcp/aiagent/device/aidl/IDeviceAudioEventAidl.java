package com.ktcp.aiagent.device.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceAudioEventAidl extends IInterface {
    void onAudioEvent(int i, String str) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IDeviceAudioEventAidl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl");
        }

        public static IDeviceAudioEventAidl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceAudioEventAidl)) {
                return new a(iBinder);
            }
            return (IDeviceAudioEventAidl) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl");
                onAudioEvent(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl");
                return true;
            }
        }

        /* loaded from: classes2.dex */
        private static class a implements IDeviceAudioEventAidl {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl
            public void onAudioEvent(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
