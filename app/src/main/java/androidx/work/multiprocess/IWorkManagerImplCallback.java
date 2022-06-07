package androidx.work.multiprocess;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IWorkManagerImplCallback extends IInterface {

    /* loaded from: classes.dex */
    public static class Default implements IWorkManagerImplCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.work.multiprocess.IWorkManagerImplCallback
        public void onFailure(String str) throws RemoteException {
        }

        @Override // androidx.work.multiprocess.IWorkManagerImplCallback
        public void onSuccess(byte[] bArr) throws RemoteException {
        }
    }

    void onFailure(String str) throws RemoteException;

    void onSuccess(byte[] bArr) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IWorkManagerImplCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "androidx.work.multiprocess.IWorkManagerImplCallback");
        }

        public static IWorkManagerImplCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("androidx.work.multiprocess.IWorkManagerImplCallback");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IWorkManagerImplCallback)) {
                return new a(iBinder);
            }
            return (IWorkManagerImplCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("androidx.work.multiprocess.IWorkManagerImplCallback");
                        onSuccess(parcel.createByteArray());
                        return true;
                    case 2:
                        parcel.enforceInterface("androidx.work.multiprocess.IWorkManagerImplCallback");
                        onFailure(parcel.readString());
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("androidx.work.multiprocess.IWorkManagerImplCallback");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class a implements IWorkManagerImplCallback {
            public static IWorkManagerImplCallback a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.b;
            }

            @Override // androidx.work.multiprocess.IWorkManagerImplCallback
            public void onSuccess(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("androidx.work.multiprocess.IWorkManagerImplCallback");
                    obtain.writeByteArray(bArr);
                    if (!this.b.transact(1, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(bArr);
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // androidx.work.multiprocess.IWorkManagerImplCallback
            public void onFailure(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("androidx.work.multiprocess.IWorkManagerImplCallback");
                    obtain.writeString(str);
                    if (!this.b.transact(2, obtain, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFailure(str);
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWorkManagerImplCallback iWorkManagerImplCallback) {
            if (a.a != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (iWorkManagerImplCallback == null) {
                return false;
            } else {
                a.a = iWorkManagerImplCallback;
                return true;
            }
        }

        public static IWorkManagerImplCallback getDefaultImpl() {
            return a.a;
        }
    }
}
