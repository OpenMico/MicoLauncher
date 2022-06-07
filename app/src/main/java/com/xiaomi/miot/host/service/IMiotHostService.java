package com.xiaomi.miot.host.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.miot.host.service.handler.IOperationHandler;
import com.xiaomi.miot.host.service.listener.IBindKeyListener;
import com.xiaomi.miot.host.service.listener.ICheckBindListener;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.host.service.listener.IGatewayMessageListener;
import com.xiaomi.miot.host.service.listener.IGetDeviceInfoHandler;
import com.xiaomi.miot.host.service.listener.IMiotConnectedListener;
import com.xiaomi.miot.host.service.listener.IMiotInternalCommandListener;
import com.xiaomi.miot.host.service.listener.IMiotLogListener;
import com.xiaomi.miot.host.service.listener.IOnBindListener;
import com.xiaomi.miot.host.service.listener.IOnTransmitListener;
import com.xiaomi.miot.host.service.listener.ISessionListener;
import com.xiaomi.miot.host.service.listener.ISyncTimeListener;
import com.xiaomi.miot.host.service.listener.ITransparentListener;
import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.property.Property;
import java.util.List;

/* loaded from: classes2.dex */
public interface IMiotHostService extends IInterface {
    void checkBind(ICheckBindListener iCheckBindListener) throws RemoteException;

    void createSession(String str, ISessionListener iSessionListener) throws RemoteException;

    void getBindKey(IBindKeyListener iBindKeyListener) throws RemoteException;

    void getDeviceProps(List<String> list, ICompletedListener iCompletedListener) throws RemoteException;

    void getLocalStatus(ICompletedListener iCompletedListener) throws RemoteException;

    void getSyncTime(ISyncTimeListener iSyncTimeListener) throws RemoteException;

    void getToken(ICompletedListener iCompletedListener) throws RemoteException;

    boolean hasSetWifiStatus() throws RemoteException;

    boolean isMiotConnected() throws RemoteException;

    void refreshMiotInfo(String str) throws RemoteException;

    void register(Device device, ICompletedListener iCompletedListener, IOperationHandler iOperationHandler) throws RemoteException;

    void registerBindListener(IOnBindListener iOnBindListener, ICompletedListener iCompletedListener) throws RemoteException;

    void registerGatewayReceiveListener(IGatewayMessageListener iGatewayMessageListener) throws RemoteException;

    void registerGetDeviceInfoHandler(IGetDeviceInfoHandler iGetDeviceInfoHandler) throws RemoteException;

    void registerMiotConnectedListener(IMiotConnectedListener iMiotConnectedListener) throws RemoteException;

    void registerMiotInternalCommandListener(IMiotInternalCommandListener iMiotInternalCommandListener) throws RemoteException;

    void registerMiotLogListener(IMiotLogListener iMiotLogListener) throws RemoteException;

    void registerMiotTransparentListener(List<String> list, ITransparentListener iTransparentListener) throws RemoteException;

    void registerTransmitListener(IOnTransmitListener iOnTransmitListener) throws RemoteException;

    void reset(ICompletedListener iCompletedListener) throws RemoteException;

    void send(String str, String str2, ICompletedListener iCompletedListener) throws RemoteException;

    void sendEvent(List<Property> list, ICompletedListener iCompletedListener) throws RemoteException;

    void sendGatewayMessage(String str, IGatewayMessageListener iGatewayMessageListener) throws RemoteException;

    void sendMiotSpecEvent(List<Property> list, ICompletedListener iCompletedListener) throws RemoteException;

    void sendMiotSpecEventNotify(Event event, ICompletedListener iCompletedListener) throws RemoteException;

    void sendTransParentMessage(String str, ICompletedListener iCompletedListener) throws RemoteException;

    void setKeepLiveInterval(int i) throws RemoteException;

    void setMiioClientLogLevel(int i, ICompletedListener iCompletedListener) throws RemoteException;

    void setWifiMode(int i) throws RemoteException;

    void start(Device device) throws RemoteException;

    void startWithBindParams(Device device, BindParams bindParams) throws RemoteException;

    void startWithDynamicDid(Device device, boolean z, int i) throws RemoteException;

    void startWithRpcType(Device device, int i) throws RemoteException;

    void stop() throws RemoteException;

    void syncDeviceName(String str, ICompletedListener iCompletedListener) throws RemoteException;

    void unregister(Device device, ICompletedListener iCompletedListener) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IMiotHostService {
        private static final String DESCRIPTOR = "com.xiaomi.miot.host.service.IMiotHostService";
        static final int TRANSACTION_checkBind = 25;
        static final int TRANSACTION_createSession = 21;
        static final int TRANSACTION_getBindKey = 15;
        static final int TRANSACTION_getDeviceProps = 20;
        static final int TRANSACTION_getLocalStatus = 28;
        static final int TRANSACTION_getSyncTime = 27;
        static final int TRANSACTION_getToken = 16;
        static final int TRANSACTION_hasSetWifiStatus = 24;
        static final int TRANSACTION_isMiotConnected = 23;
        static final int TRANSACTION_refreshMiotInfo = 30;
        static final int TRANSACTION_register = 8;
        static final int TRANSACTION_registerBindListener = 17;
        static final int TRANSACTION_registerGatewayReceiveListener = 33;
        static final int TRANSACTION_registerGetDeviceInfoHandler = 2;
        static final int TRANSACTION_registerMiotConnectedListener = 22;
        static final int TRANSACTION_registerMiotInternalCommandListener = 34;
        static final int TRANSACTION_registerMiotLogListener = 29;
        static final int TRANSACTION_registerMiotTransparentListener = 35;
        static final int TRANSACTION_registerTransmitListener = 18;
        static final int TRANSACTION_reset = 19;
        static final int TRANSACTION_send = 13;
        static final int TRANSACTION_sendEvent = 10;
        static final int TRANSACTION_sendGatewayMessage = 32;
        static final int TRANSACTION_sendMiotSpecEvent = 11;
        static final int TRANSACTION_sendMiotSpecEventNotify = 12;
        static final int TRANSACTION_sendTransParentMessage = 36;
        static final int TRANSACTION_setKeepLiveInterval = 26;
        static final int TRANSACTION_setMiioClientLogLevel = 14;
        static final int TRANSACTION_setWifiMode = 1;
        static final int TRANSACTION_start = 3;
        static final int TRANSACTION_startWithBindParams = 6;
        static final int TRANSACTION_startWithDynamicDid = 5;
        static final int TRANSACTION_startWithRpcType = 4;
        static final int TRANSACTION_stop = 7;
        static final int TRANSACTION_syncDeviceName = 31;
        static final int TRANSACTION_unregister = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMiotHostService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMiotHostService)) {
                return new Proxy(iBinder);
            }
            return (IMiotHostService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                Device device = null;
                Event event = null;
                Device device2 = null;
                Device device3 = null;
                BindParams bindParams = null;
                Device device4 = null;
                Device device5 = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        setWifiMode(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerGetDeviceInfoHandler(IGetDeviceInfoHandler.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            device = Device.CREATOR.createFromParcel(parcel);
                        }
                        start(device);
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            device5 = Device.CREATOR.createFromParcel(parcel);
                        }
                        startWithRpcType(device5, parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            device4 = Device.CREATOR.createFromParcel(parcel);
                        }
                        startWithDynamicDid(device4, parcel.readInt() != 0, parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        Device createFromParcel = parcel.readInt() != 0 ? Device.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            bindParams = BindParams.CREATOR.createFromParcel(parcel);
                        }
                        startWithBindParams(createFromParcel, bindParams);
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        stop();
                        parcel2.writeNoException();
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            device3 = Device.CREATOR.createFromParcel(parcel);
                        }
                        register(device3, ICompletedListener.Stub.asInterface(parcel.readStrongBinder()), IOperationHandler.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            device2 = Device.CREATOR.createFromParcel(parcel);
                        }
                        unregister(device2, ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        sendEvent(parcel.createTypedArrayList(Property.CREATOR), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        sendMiotSpecEvent(parcel.createTypedArrayList(Property.CREATOR), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            event = Event.CREATOR.createFromParcel(parcel);
                        }
                        sendMiotSpecEventNotify(event, ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        send(parcel.readString(), parcel.readString(), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        setMiioClientLogLevel(parcel.readInt(), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        getBindKey(IBindKeyListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        getToken(ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerBindListener(IOnBindListener.Stub.asInterface(parcel.readStrongBinder()), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerTransmitListener(IOnTransmitListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        reset(ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        getDeviceProps(parcel.createStringArrayList(), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        createSession(parcel.readString(), ISessionListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMiotConnectedListener(IMiotConnectedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean isMiotConnected = isMiotConnected();
                        parcel2.writeNoException();
                        parcel2.writeInt(isMiotConnected ? 1 : 0);
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean hasSetWifiStatus = hasSetWifiStatus();
                        parcel2.writeNoException();
                        parcel2.writeInt(hasSetWifiStatus ? 1 : 0);
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        checkBind(ICheckBindListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 26:
                        parcel.enforceInterface(DESCRIPTOR);
                        setKeepLiveInterval(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 27:
                        parcel.enforceInterface(DESCRIPTOR);
                        getSyncTime(ISyncTimeListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 28:
                        parcel.enforceInterface(DESCRIPTOR);
                        getLocalStatus(ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 29:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMiotLogListener(IMiotLogListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 30:
                        parcel.enforceInterface(DESCRIPTOR);
                        refreshMiotInfo(parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 31:
                        parcel.enforceInterface(DESCRIPTOR);
                        syncDeviceName(parcel.readString(), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 32:
                        parcel.enforceInterface(DESCRIPTOR);
                        sendGatewayMessage(parcel.readString(), IGatewayMessageListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 33:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerGatewayReceiveListener(IGatewayMessageListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 34:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMiotInternalCommandListener(IMiotInternalCommandListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 35:
                        parcel.enforceInterface(DESCRIPTOR);
                        registerMiotTransparentListener(parcel.createStringArrayList(), ITransparentListener.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 36:
                        parcel.enforceInterface(DESCRIPTOR);
                        sendTransParentMessage(parcel.readString(), ICompletedListener.Stub.asInterface(parcel.readStrongBinder()));
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

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class Proxy implements IMiotHostService {
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

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void setWifiMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerGetDeviceInfoHandler(IGetDeviceInfoHandler iGetDeviceInfoHandler) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iGetDeviceInfoHandler != null ? iGetDeviceInfoHandler.asBinder() : null);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void start(Device device) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        obtain.writeInt(1);
                        device.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void startWithRpcType(Device device, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        obtain.writeInt(1);
                        device.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void startWithDynamicDid(Device device, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int i2 = 1;
                    if (device != null) {
                        obtain.writeInt(1);
                        device.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!z) {
                        i2 = 0;
                    }
                    obtain.writeInt(i2);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void startWithBindParams(Device device, BindParams bindParams) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        obtain.writeInt(1);
                        device.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bindParams != null) {
                        obtain.writeInt(1);
                        bindParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void register(Device device, ICompletedListener iCompletedListener, IOperationHandler iOperationHandler) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        obtain.writeInt(1);
                        device.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    IBinder iBinder = null;
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    if (iOperationHandler != null) {
                        iBinder = iOperationHandler.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void unregister(Device device, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        obtain.writeInt(1);
                        device.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void sendEvent(List<Property> list, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void sendMiotSpecEvent(List<Property> list, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void sendMiotSpecEventNotify(Event event, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        obtain.writeInt(1);
                        event.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void send(String str, String str2, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void setMiioClientLogLevel(int i, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void getBindKey(IBindKeyListener iBindKeyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBindKeyListener != null ? iBindKeyListener.asBinder() : null);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void getToken(ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerBindListener(IOnBindListener iOnBindListener, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    obtain.writeStrongBinder(iOnBindListener != null ? iOnBindListener.asBinder() : null);
                    if (iCompletedListener != null) {
                        iBinder = iCompletedListener.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerTransmitListener(IOnTransmitListener iOnTransmitListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iOnTransmitListener != null ? iOnTransmitListener.asBinder() : null);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void reset(ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void getDeviceProps(List<String> list, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void createSession(String str, ISessionListener iSessionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iSessionListener != null ? iSessionListener.asBinder() : null);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerMiotConnectedListener(IMiotConnectedListener iMiotConnectedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMiotConnectedListener != null ? iMiotConnectedListener.asBinder() : null);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public boolean isMiotConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public boolean hasSetWifiStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void checkBind(ICheckBindListener iCheckBindListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCheckBindListener != null ? iCheckBindListener.asBinder() : null);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void setKeepLiveInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void getSyncTime(ISyncTimeListener iSyncTimeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iSyncTimeListener != null ? iSyncTimeListener.asBinder() : null);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void getLocalStatus(ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerMiotLogListener(IMiotLogListener iMiotLogListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMiotLogListener != null ? iMiotLogListener.asBinder() : null);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void refreshMiotInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void syncDeviceName(String str, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void sendGatewayMessage(String str, IGatewayMessageListener iGatewayMessageListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iGatewayMessageListener != null ? iGatewayMessageListener.asBinder() : null);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerGatewayReceiveListener(IGatewayMessageListener iGatewayMessageListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iGatewayMessageListener != null ? iGatewayMessageListener.asBinder() : null);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerMiotInternalCommandListener(IMiotInternalCommandListener iMiotInternalCommandListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMiotInternalCommandListener != null ? iMiotInternalCommandListener.asBinder() : null);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void registerMiotTransparentListener(List<String> list, ITransparentListener iTransparentListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongBinder(iTransparentListener != null ? iTransparentListener.asBinder() : null);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaomi.miot.host.service.IMiotHostService
            public void sendTransParentMessage(String str, ICompletedListener iCompletedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iCompletedListener != null ? iCompletedListener.asBinder() : null);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
