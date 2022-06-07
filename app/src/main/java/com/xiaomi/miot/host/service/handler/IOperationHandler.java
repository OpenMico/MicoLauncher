package com.xiaomi.miot.host.service.handler;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;

/* loaded from: classes2.dex */
public interface IOperationHandler extends IInterface {
    MiotError onAction(ActionInfo actionInfo) throws RemoteException;

    MiotError onGet(Property property) throws RemoteException;

    MiotError onSet(Property property) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IOperationHandler {
        private static final String DESCRIPTOR = "com.xiaomi.miot.host.service.handler.IOperationHandler";
        static final int TRANSACTION_onAction = 1;
        static final int TRANSACTION_onGet = 2;
        static final int TRANSACTION_onSet = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOperationHandler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IOperationHandler)) {
                return new Proxy(iBinder);
            }
            return (IOperationHandler) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                ActionInfo actionInfo = null;
                Property property = null;
                Property property2 = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            actionInfo = ActionInfo.CREATOR.createFromParcel(parcel);
                        }
                        MiotError onAction = onAction(actionInfo);
                        parcel2.writeNoException();
                        if (onAction != null) {
                            parcel2.writeInt(1);
                            onAction.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (actionInfo != null) {
                            parcel2.writeInt(1);
                            actionInfo.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            property2 = Property.CREATOR.createFromParcel(parcel);
                        }
                        MiotError onGet = onGet(property2);
                        parcel2.writeNoException();
                        if (onGet != null) {
                            parcel2.writeInt(1);
                            onGet.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (property2 != null) {
                            parcel2.writeInt(1);
                            property2.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            property = Property.CREATOR.createFromParcel(parcel);
                        }
                        MiotError onSet = onSet(property);
                        parcel2.writeNoException();
                        if (onSet != null) {
                            parcel2.writeInt(1);
                            onSet.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (property != null) {
                            parcel2.writeInt(1);
                            property.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class Proxy implements IOperationHandler {
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

            @Override // com.xiaomi.miot.host.service.handler.IOperationHandler
            public MiotError onAction(ActionInfo actionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (actionInfo != null) {
                        obtain.writeInt(1);
                        actionInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    MiotError createFromParcel = obtain2.readInt() != 0 ? MiotError.CREATOR.createFromParcel(obtain2) : null;
                    if (obtain2.readInt() != 0) {
                        actionInfo.readFromParcel(obtain2);
                    }
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.handler.IOperationHandler
            public MiotError onGet(Property property) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (property != null) {
                        obtain.writeInt(1);
                        property.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    MiotError createFromParcel = obtain2.readInt() != 0 ? MiotError.CREATOR.createFromParcel(obtain2) : null;
                    if (obtain2.readInt() != 0) {
                        property.readFromParcel(obtain2);
                    }
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.handler.IOperationHandler
            public MiotError onSet(Property property) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (property != null) {
                        obtain.writeInt(1);
                        property.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    MiotError createFromParcel = obtain2.readInt() != 0 ? MiotError.CREATOR.createFromParcel(obtain2) : null;
                    if (obtain2.readInt() != 0) {
                        property.readFromParcel(obtain2);
                    }
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
