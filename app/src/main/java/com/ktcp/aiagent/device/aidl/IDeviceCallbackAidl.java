package com.ktcp.aiagent.device.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceCallbackAidl extends IInterface {
    String onCallback(String str, String str2) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IDeviceCallbackAidl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl");
        }

        public static IDeviceCallbackAidl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceCallbackAidl)) {
                return new a(iBinder);
            }
            return (IDeviceCallbackAidl) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl");
                String onCallback = onCallback(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(onCallback);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl");
                return true;
            }
        }

        /* loaded from: classes2.dex */
        private static class a implements IDeviceCallbackAidl {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl
            public String onCallback(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
