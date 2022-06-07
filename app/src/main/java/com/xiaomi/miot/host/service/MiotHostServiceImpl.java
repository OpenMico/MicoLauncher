package com.xiaomi.miot.host.service;

import android.content.Context;
import android.os.RemoteException;
import com.xiaomi.miot.host.lan.impl.LanHostRuntime;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.IMiotHostService;
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
import com.xiaomi.miot.host.service.tasks.AddBindListenerTask;
import com.xiaomi.miot.host.service.tasks.AddGatewayReceiveListener;
import com.xiaomi.miot.host.service.tasks.AddMiotConnectedListenerTask;
import com.xiaomi.miot.host.service.tasks.AddMiotInternalCommandListener;
import com.xiaomi.miot.host.service.tasks.AddMiotLogListenerTask;
import com.xiaomi.miot.host.service.tasks.AddTransmitListenerTask;
import com.xiaomi.miot.host.service.tasks.CheckBindTask;
import com.xiaomi.miot.host.service.tasks.CreateSessionTask;
import com.xiaomi.miot.host.service.tasks.GetBindKeyTask;
import com.xiaomi.miot.host.service.tasks.GetDevicePropsTask;
import com.xiaomi.miot.host.service.tasks.GetLocalStatusTask;
import com.xiaomi.miot.host.service.tasks.MiotRefreshInfoTask;
import com.xiaomi.miot.host.service.tasks.RegisterMiotTransparentListenerTask;
import com.xiaomi.miot.host.service.tasks.RegisterTask;
import com.xiaomi.miot.host.service.tasks.ResetTask;
import com.xiaomi.miot.host.service.tasks.SendEventTask;
import com.xiaomi.miot.host.service.tasks.SendGatewayMessageTask;
import com.xiaomi.miot.host.service.tasks.SendMessageTask;
import com.xiaomi.miot.host.service.tasks.SendMiotSpecEventNotifyTask;
import com.xiaomi.miot.host.service.tasks.SendMiotSpecEventTask;
import com.xiaomi.miot.host.service.tasks.SendTransparentMessageTask;
import com.xiaomi.miot.host.service.tasks.SetMiioLogLevelTask;
import com.xiaomi.miot.host.service.tasks.SyncDeviceNameTask;
import com.xiaomi.miot.host.service.tasks.SyncTimeTask;
import com.xiaomi.miot.host.service.tasks.UnregisterTask;
import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.listener.GetDeviceInfoHandler;
import com.xiaomi.miot.typedef.property.Property;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class MiotHostServiceImpl extends IMiotHostService.Stub {
    private static final int CORE_POOL_SIZE = 1;
    private static final int KEEPALIVE_TIME = 90;
    private static final int MAX_POOL_SIZE = 5;
    private static final int MAX_QUEUE = 256;
    private ThreadPoolExecutor mExecutor;
    private boolean mInitialized;
    private HostRuntimeManager mRuntimeManager;
    private boolean mStarted;

    public synchronized void initialize(Context context) {
        if (!this.mInitialized) {
            this.mInitialized = true;
            this.mExecutor = new ThreadPoolExecutor(1, 5, 90L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(256));
            this.mRuntimeManager = new HostRuntimeManager();
            this.mRuntimeManager.initialize(LanHostRuntime.newInstance(context));
        }
    }

    public synchronized void destroy() {
        if (this.mInitialized) {
            this.mInitialized = false;
            try {
                this.mRuntimeManager.destroy();
                this.mRuntimeManager = null;
            } catch (MiotException e) {
                e.printStackTrace();
            }
            this.mExecutor.shutdown();
            this.mExecutor = null;
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void setWifiMode(int i) throws RemoteException {
        try {
            if (this.mRuntimeManager != null) {
                this.mRuntimeManager.setWifiMode(i);
            }
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerGetDeviceInfoHandler(final IGetDeviceInfoHandler iGetDeviceInfoHandler) throws RemoteException {
        HostRuntimeManager hostRuntimeManager = this.mRuntimeManager;
        if (hostRuntimeManager != null && iGetDeviceInfoHandler != null) {
            try {
                hostRuntimeManager.registerGetDeviceInfoHandler(new GetDeviceInfoHandler() { // from class: com.xiaomi.miot.host.service.MiotHostServiceImpl.1
                    @Override // com.xiaomi.miot.typedef.listener.GetDeviceInfoHandler
                    public String onRequestRouterInfo() {
                        try {
                            return iGetDeviceInfoHandler.onRequestRouterInfo();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return "";
                        }
                    }
                });
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void start(Device device) throws RemoteException {
        startIfNecessary(device, false, 0, null);
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void startWithRpcType(Device device, int i) {
        startIfNecessary(device, false, i, null);
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void startWithDynamicDid(Device device, boolean z, int i) throws RemoteException {
        startIfNecessary(device, z, i, null);
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void startWithBindParams(Device device, BindParams bindParams) throws RemoteException {
        startIfNecessary(device, false, bindParams != null ? bindParams.getSupportRpcType() : 0, bindParams);
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void stop() throws RemoteException {
        stopIfNecessary();
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void register(Device device, ICompletedListener iCompletedListener, IOperationHandler iOperationHandler) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new RegisterTask(this.mRuntimeManager, device, iCompletedListener, iOperationHandler));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void unregister(Device device, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new UnregisterTask(this.mRuntimeManager, device, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void sendEvent(List<Property> list, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SendEventTask(this.mRuntimeManager, list, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void sendMiotSpecEvent(List<Property> list, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SendMiotSpecEventTask(this.mRuntimeManager, list, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void sendMiotSpecEventNotify(Event event, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SendMiotSpecEventNotifyTask(this.mRuntimeManager, event, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void send(String str, String str2, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SendMessageTask(this.mRuntimeManager, str, str2, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void setMiioClientLogLevel(int i, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SetMiioLogLevelTask(this.mRuntimeManager, i, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void getBindKey(IBindKeyListener iBindKeyListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new GetBindKeyTask(this.mRuntimeManager, iBindKeyListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void reset(ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new ResetTask(this.mRuntimeManager, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerBindListener(IOnBindListener iOnBindListener, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new AddBindListenerTask(this.mRuntimeManager, iOnBindListener, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerTransmitListener(IOnTransmitListener iOnTransmitListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new AddTransmitListenerTask(this.mRuntimeManager, iOnTransmitListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void getDeviceProps(List<String> list, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new GetDevicePropsTask(this.mRuntimeManager, list, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void createSession(String str, ISessionListener iSessionListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new CreateSessionTask(this.mRuntimeManager, str, iSessionListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerMiotConnectedListener(IMiotConnectedListener iMiotConnectedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new AddMiotConnectedListenerTask(this.mRuntimeManager, iMiotConnectedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public boolean isMiotConnected() throws RemoteException {
        HostRuntimeManager hostRuntimeManager = this.mRuntimeManager;
        if (hostRuntimeManager != null) {
            try {
                return hostRuntimeManager.isMiotConnected();
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public boolean hasSetWifiStatus() throws RemoteException {
        HostRuntimeManager hostRuntimeManager = this.mRuntimeManager;
        if (hostRuntimeManager != null) {
            try {
                return hostRuntimeManager.hasSetWifiStatus();
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void checkBind(ICheckBindListener iCheckBindListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new CheckBindTask(this.mRuntimeManager, iCheckBindListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void getToken(final ICompletedListener iCompletedListener) throws RemoteException {
        HostRuntimeManager hostRuntimeManager = this.mRuntimeManager;
        if (hostRuntimeManager != null) {
            try {
                hostRuntimeManager.getToken(new CompletedListener() { // from class: com.xiaomi.miot.host.service.MiotHostServiceImpl.2
                    @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                    public void onSucceed(String str) {
                        ICompletedListener iCompletedListener2 = iCompletedListener;
                        if (iCompletedListener2 != null) {
                            try {
                                iCompletedListener2.onSucceed(str);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                    public void onFailed(MiotError miotError) {
                        ICompletedListener iCompletedListener2 = iCompletedListener;
                        if (iCompletedListener2 != null) {
                            try {
                                iCompletedListener2.onFailed(miotError);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } catch (MiotException e) {
                e.printStackTrace();
            }
        } else if (iCompletedListener != null) {
            try {
                iCompletedListener.onFailed(MiotError.INTERNAL_NOT_INITIALIZED);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void setKeepLiveInterval(int i) throws RemoteException {
        HostRuntimeManager hostRuntimeManager = this.mRuntimeManager;
        if (hostRuntimeManager != null) {
            try {
                hostRuntimeManager.setKeepLiveInterval(i);
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void getSyncTime(ISyncTimeListener iSyncTimeListener) {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SyncTimeTask(this.mRuntimeManager, iSyncTimeListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void getLocalStatus(ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new GetLocalStatusTask(this.mRuntimeManager, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void sendGatewayMessage(String str, IGatewayMessageListener iGatewayMessageListener) {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SendGatewayMessageTask(this.mRuntimeManager, str, iGatewayMessageListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerGatewayReceiveListener(IGatewayMessageListener iGatewayMessageListener) {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new AddGatewayReceiveListener(this.mRuntimeManager, iGatewayMessageListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerMiotInternalCommandListener(IMiotInternalCommandListener iMiotInternalCommandListener) {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new AddMiotInternalCommandListener(this.mRuntimeManager, iMiotInternalCommandListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerMiotTransparentListener(List<String> list, ITransparentListener iTransparentListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new RegisterMiotTransparentListenerTask(this.mRuntimeManager, list, iTransparentListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void sendTransParentMessage(String str, ICompletedListener iCompletedListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SendTransparentMessageTask(this.mRuntimeManager, str, iCompletedListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void registerMiotLogListener(IMiotLogListener iMiotLogListener) throws RemoteException {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new AddMiotLogListenerTask(iMiotLogListener));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void refreshMiotInfo(String str) {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new MiotRefreshInfoTask(this.mRuntimeManager, str));
        }
    }

    @Override // com.xiaomi.miot.host.service.IMiotHostService
    public void syncDeviceName(String str, ICompletedListener iCompletedListener) {
        ThreadPoolExecutor threadPoolExecutor = this.mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.execute(new SyncDeviceNameTask(this.mRuntimeManager, str, iCompletedListener));
        }
    }

    private void startIfNecessary(Device device, boolean z, int i, BindParams bindParams) {
        if (!this.mStarted) {
            this.mStarted = true;
            try {
                if (this.mRuntimeManager != null) {
                    this.mRuntimeManager.start(device, z, i, bindParams);
                }
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopIfNecessary() {
        if (this.mStarted) {
            this.mStarted = false;
            try {
                if (this.mRuntimeManager != null) {
                    this.mRuntimeManager.stop();
                }
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
    }
}
