package com.xiaomi.mico.screensaver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScreenSaverKeepAliveService extends IInterface {

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IScreenSaverKeepAliveService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.mico.screensaver.IScreenSaverKeepAliveService");
        }

        public static IScreenSaverKeepAliveService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.mico.screensaver.IScreenSaverKeepAliveService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IScreenSaverKeepAliveService)) {
                return new a(iBinder);
            }
            return (IScreenSaverKeepAliveService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("com.xiaomi.mico.screensaver.IScreenSaverKeepAliveService");
            return true;
        }

        /* loaded from: classes3.dex */
        private static class a implements IScreenSaverKeepAliveService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }
        }
    }
}
