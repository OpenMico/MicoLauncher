package com.xiaomi.miot.host.lan.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miio.MiioClient;
import com.xiaomi.miot.host.lan.impl.client.MiotClient;
import com.xiaomi.miot.host.lan.impl.client.OTKeyExceptionMonitor;
import com.xiaomi.miot.host.lan.impl.codec.MiotMessageFactory;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequestHandler;
import com.xiaomi.miot.host.lan.impl.codec.MiotResponse;
import com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler;
import com.xiaomi.miot.host.lan.impl.codec.MiotSupportRpcType;
import com.xiaomi.miot.host.lan.impl.codec.bindkey.MiotCheckBindRequest;
import com.xiaomi.miot.host.lan.impl.codec.bindkey.MiotGetBindKeyRequest;
import com.xiaomi.miot.host.lan.impl.codec.bindkey.MiotSyncDeviceNameRequest;
import com.xiaomi.miot.host.lan.impl.codec.broadcast.MiotLocalBroadcast;
import com.xiaomi.miot.host.lan.impl.codec.broadcast.MiotWifiStart;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiioSetLogLevel;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotBindStat;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotConfig;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotHello;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotInfo;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotLocalQueryStatus;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotMdns;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotRefreshInfo;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotReportOtcInfoResult;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotRestore;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotSyncTimeRequest;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotToken;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotTransparantRequest;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotUpdateToken;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotUserInfo;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotWifi;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotWifiConnected;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotWifiScan;
import com.xiaomi.miot.host.lan.impl.codec.operation.GatewayRawRequest;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotAction;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotEventNotify;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotPropertyGetter;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotPropertyNotify;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotPropertySetter;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2Action;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2Data;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2EventNotify;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2PropertyGetter;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2PropertyNotify;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2PropertySetter;
import com.xiaomi.miot.host.lan.impl.codec.voice.MiotCreateVoiceSessionRequest;
import com.xiaomi.miot.host.lan.impl.codec.voice.MiotVoiceReplyRequest;
import com.xiaomi.miot.host.lan.impl.util.MiotInfoManager;
import com.xiaomi.miot.host.lan.impl.util.OTInfo;
import com.xiaomi.miot.host.lan.impl.util.SharePrefsManager;
import com.xiaomi.miot.host.lan.impl.wifi.WifiNetworkCallback;
import com.xiaomi.miot.host.lan.impl.wifi.WifiSettingUtils;
import com.xiaomi.miot.typedef.data.DataValue;
import com.xiaomi.miot.typedef.data.KeyType;
import com.xiaomi.miot.typedef.device.Action;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.device.Service;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.handler.OperationHandler;
import com.xiaomi.miot.typedef.listener.BindKeyListener;
import com.xiaomi.miot.typedef.listener.CheckBindListener;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.listener.GatewayMessageListener;
import com.xiaomi.miot.typedef.listener.GetDeviceInfoHandler;
import com.xiaomi.miot.typedef.listener.MiotConnectedListener;
import com.xiaomi.miot.typedef.listener.MiotInternalCommandListener;
import com.xiaomi.miot.typedef.listener.OnBindListener;
import com.xiaomi.miot.typedef.listener.OnTransmitListener;
import com.xiaomi.miot.typedef.listener.SessionListener;
import com.xiaomi.miot.typedef.listener.SyncTimeListener;
import com.xiaomi.miot.typedef.listener.TransparentListener;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.library.common.network.Network;
import io.netty.handler.codec.http.HttpHeaders;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotHost implements MiotClient.ConnectionListener, MiotClient.TransmitListener, MiotRequestHandler {
    private static final String MSG_TAG_RETRY = "retry_left_times";
    private static final String MSG_WIFI_CONNECTED_CONTENT = "wifi_connected_msg_content";
    private static final int MSG_WIFI_CONNECTED_RETRY_ID = 1;
    private static final String TAG = "MiotHost";
    public static volatile int sSupportRpcType;
    private BindParams mBindParams;
    private Context mContext;
    private Device mDevice;
    private ScheduledExecutorService mExecutorService;
    private GatewayMessageListener mGatewayMessageListener;
    private GetDeviceInfoHandler mGetDeviceInfoHandler;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private volatile boolean mInitialized;
    private MiotClient mMiotClient;
    private MiotConnectedListener mMiotConnectedListener;
    private MiotStore mMiotStore;
    private OnBindListener mOnBindListener;
    private OnTransmitListener mOnTransmitListener;
    private OperationHandler mOperationHandler;
    private String mPartnerId;
    private String mToken;
    private TransparentListener mTransParentListener;
    private CompletedListener registerCompletedListener;
    private WifiNetworkCallback wifiNetworkCallback;
    private static List<String> mWhiteListMethods = new ArrayList();
    private static List<String> mJSONObjectMethods = new ArrayList();
    private boolean mLogFlag = false;
    private boolean mIsStarted = false;
    private boolean mHasSetWifiStatus = false;
    private boolean mIsDynamicDid = false;
    private int mWifiMode = 1;
    private long mUid = 0;
    private Map<Integer, JSONArray> mCacheResponseData = new ConcurrentHashMap();
    private MiotResponseHandler mGeneralResponseHandler = new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.1
        @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
        public void onSucceed(MiotResponse miotResponse) {
            OTKeyExceptionMonitor.getInstance(MiotHost.this.mContext).clearException();
        }

        @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
        public void onFailed(MiotError miotError) {
            if (miotError.getCode() != -30011 && miotError.getCode() != MiotError.INTERNAL_REQUEST_TIMEOUT.getCode()) {
                OTKeyExceptionMonitor.getInstance(MiotHost.this.mContext).clearException();
            } else if (OTKeyExceptionMonitor.getInstance(MiotHost.this.mContext).isKeyInvalid()) {
                OTKeyExceptionMonitor.getInstance(MiotHost.this.mContext).clearException();
                if (MiotHost.this.mMiotConnectedListener != null) {
                    MiotHost.this.mMiotConnectedListener.onTokenException();
                }
            } else {
                OTKeyExceptionMonitor.getInstance(MiotHost.this.mContext).abnormalDisconnection();
            }
        }
    };

    public void setKeepLiveInterval(int i) {
    }

    public void stop() {
    }

    public void initialize(Context context) {
        final String str;
        if (!this.mInitialized) {
            MiotLogUtil.d(TAG, "initialize");
            this.mContext = context.getApplicationContext();
            this.mInitialized = true;
            this.mMiotClient = MiotClient.getInstance();
            this.mMiotClient.initialize();
            this.mMiotClient.registerRequestHandler(this);
            this.mMiotClient.registerGeneralResponseHandler(this.mGeneralResponseHandler);
            this.mMiotClient.setmTransmitListener(this);
            this.mMiotStore = new MiotStore(context);
            this.mToken = this.mMiotStore.loadToken();
            initOTTcpHandler();
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (Build.VERSION.SDK_INT >= 24) {
                this.wifiNetworkCallback = new WifiNetworkCallback(context);
                connectivityManager.registerDefaultNetworkCallback(this.wifiNetworkCallback);
            }
            sSupportRpcType = 0;
            this.mIsDynamicDid = false;
            this.mExecutorService = Executors.newSingleThreadScheduledExecutor();
            if (!MiioClient.getInstance().isStarted()) {
                final String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/miio/miothost.txt";
                Log.i(TAG, "logpath = " + str2);
                boolean settingBoolean = SharePrefsManager.getSettingBoolean(context, SharePrefsManager.SP_MIOTHOST, SharePrefsManager.SP_MIOT_SE_KEY, false);
                if (this.mLogFlag) {
                    if (settingBoolean) {
                        str = "NULL&-l&0&-n&126&-s&1024&-o&MSC&-L&" + str2;
                    } else {
                        str = "NULL&-l&0&-n&126&-s&1024&-L&" + str2;
                    }
                } else if (settingBoolean) {
                    str = "NULL&-l&1&-n&126&-s&1024&-o&MSC&-L&" + str2;
                } else {
                    str = "NULL&-l&1&-n&126&-s&1024&-L&" + str2;
                }
                MiioClient.getInstance().Initialize();
                new Thread(new Runnable() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.2
                    @Override // java.lang.Runnable
                    public void run() {
                        MiotHost.createPath(str2);
                        MiioClient.getInstance().start(str);
                    }
                }).start();
            }
        }
    }

    private void initOTTcpHandler() {
        if (this.mHandlerThread == null) {
            this.mHandlerThread = new HandlerThread("OT_TCP_HANDLER");
            this.mHandlerThread.start();
        }
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.3
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    MiotLogUtil.d(MiotHost.TAG, "get msg: " + message.what);
                    if (message.what == 1) {
                        Bundle data = message.getData();
                        int i = data.getInt(MiotHost.MSG_TAG_RETRY);
                        MiotLogUtil.d(MiotHost.TAG, "leftTimes: " + i);
                        if (i > 0 && MiotHost.this.mMiotClient != null) {
                            String string = data.getString(MiotHost.MSG_WIFI_CONNECTED_CONTENT);
                            MiotHost.this.sendDelayWifiConnectedMsg(i - 1, string);
                            MiotLogUtil.d(MiotHost.TAG, "handle sendMessageWithoutEncrypt: " + string);
                            MiotHost.this.mMiotClient.sendMessageWithoutEncrypt(string.getBytes());
                            MiotHost.this.mMiotClient.sendInternalRequest(new MiotHello());
                        }
                    }
                }
            };
        }
    }

    public void sendDelayWifiConnectedMsg(int i, String str) {
        if (this.mHandler == null) {
            MiotLogUtil.e(TAG, "sendDelayWifiConnectedMsg onFailed, mhandler is null");
            return;
        }
        Message message = new Message();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putInt(MSG_TAG_RETRY, i);
        bundle.putString(MSG_WIFI_CONNECTED_CONTENT, str);
        message.setData(bundle);
        MiotLogUtil.i(TAG, "handle sendMessageDelayed: 2000");
        this.mHandler.removeMessages(1);
        this.mHandler.sendMessageDelayed(message, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public static void createPath(String str) {
        File file = new File(str);
        if (!file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() {
        stop();
        if (this.mInitialized) {
            this.mInitialized = false;
            this.mMiotClient.destroy();
            this.mMiotClient = null;
            this.mGatewayMessageListener = null;
            this.mExecutorService.shutdownNow();
            this.mExecutorService = null;
        }
    }

    public void registerGetDeviceInfoHandler(GetDeviceInfoHandler getDeviceInfoHandler) {
        this.mGetDeviceInfoHandler = getDeviceInfoHandler;
    }

    public void setWifiMode(int i) {
        this.mWifiMode = i;
    }

    public void setMiioClientLogLevel(int i, final CompletedListener completedListener) {
        this.mMiotClient.sendRequest(new MiioSetLogLevel(i), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.4
            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onSucceed(MiotResponse miotResponse) {
                MiotLogUtil.d(MiotHost.TAG, "setMiioClientLogLevel onSucceed:" + miotResponse.getOriginResponse());
                completedListener.onSucceed(miotResponse.getOriginResponse());
            }

            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onFailed(MiotError miotError) {
                MiotLogUtil.d(MiotHost.TAG, "setMiioClientLogLevel onFailed: " + miotError.toString());
            }
        });
    }

    public synchronized void start() {
        if (this.mInitialized && this.mMiotClient != null && !this.mIsStarted) {
            this.mIsStarted = true;
            if (this.mMiotClient != null && !this.mMiotClient.isConnected()) {
                this.mMiotClient.connect(this);
            }
        }
    }

    public boolean isStarted() {
        return this.mIsStarted;
    }

    public synchronized void register(Device device, CompletedListener completedListener, OperationHandler operationHandler) {
        MiotLogUtil.d(TAG, MiPushClient.COMMAND_REGISTER);
        MiotLogUtil.d(TAG, "id: " + device.getDeviceId());
        MiotLogUtil.d(TAG, "mac: " + device.getMacAddress());
        MiotLogUtil.d(TAG, "sn: " + device.getSn());
        MiotLogUtil.d(TAG, "manufacturer: " + device.getManufacturer());
        MiotLogUtil.d(TAG, "model: " + device.getModelName());
        MiotLogUtil.d(TAG, "token: " + device.getMiotToken());
        MiotLogUtil.d(TAG, "info: " + device.getMiotInfo());
        this.mDevice = device;
        this.registerCompletedListener = completedListener;
        this.mOperationHandler = operationHandler;
        setPartnerId();
        setUid();
        if (this.mMiotClient != null && this.mMiotClient.isConnected()) {
            this.mMiotClient.sendInternalRequest(new MiotHello());
        }
    }

    public synchronized void unregister(Device device) {
        this.mDevice = null;
        this.mOperationHandler = null;
    }

    private void setPartnerId() {
        Device device = this.mDevice;
        if (device != null && device.getMiotInfo() != null) {
            try {
                this.mPartnerId = new JSONObject(this.mDevice.getMiotInfo()).optString("partner_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUid() {
        Device device = this.mDevice;
        if (device != null && device.getMiotInfo() != null) {
            try {
                String optString = new JSONObject(this.mDevice.getMiotInfo()).optJSONObject("params").optString(OneTrack.Param.UID);
                if (!TextUtils.isEmpty(optString)) {
                    this.mUid = Long.parseLong(optString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEvents(List<Property> list, final CompletedListener completedListener) {
        MiotLogUtil.d(TAG, "sendEvents");
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "send event failed, don't register");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        MiotClient miotClient = this.mMiotClient;
        if (miotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        miotClient.sendRequest(new MiotPropertyNotify(this.mPartnerId, list), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.5
            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onSucceed(MiotResponse miotResponse) {
                MiotLogUtil.d(MiotHost.TAG, "sendEvents onSucceed");
                if (completedListener != null) {
                    completedListener.onSucceed((miotResponse == null || miotResponse.getContent() == null) ? null : String.valueOf(miotResponse.getContent()));
                }
            }

            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onFailed(MiotError miotError) {
                MiotLogUtil.d(MiotHost.TAG, "sendEvents onFailed: " + miotError);
                CompletedListener completedListener2 = completedListener;
                if (completedListener2 != null) {
                    completedListener2.onFailed(miotError);
                }
            }
        });
    }

    public void sendMiotSpecEvent(List<Property> list, final CompletedListener completedListener) {
        String str;
        MiotLogUtil.d(TAG, "sendMiotSpecEvent");
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "send miot spec event failed, don't register");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else {
            if (!TextUtils.isEmpty(this.mPartnerId)) {
                str = this.mPartnerId;
            } else {
                str = this.mDevice.getDeviceId();
            }
            this.mMiotClient.sendRequest(new MiotSpecV2PropertyNotify(str, this.mPartnerId, list), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.6
                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onSucceed(MiotResponse miotResponse) {
                    MiotLogUtil.d(MiotHost.TAG, "sendMiotSpecEvent onSucceed");
                    if (completedListener != null) {
                        completedListener.onSucceed((miotResponse == null || miotResponse.getContent() == null) ? null : String.valueOf(miotResponse.getContent()));
                    }
                }

                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onFailed(MiotError miotError) {
                    MiotLogUtil.d(MiotHost.TAG, "sendMiotSpecEvent onFailed: " + miotError);
                    CompletedListener completedListener2 = completedListener;
                    if (completedListener2 != null) {
                        completedListener2.onFailed(miotError);
                    }
                }
            });
        }
    }

    public void sendMiotSpecEventNotify(Event event, final CompletedListener completedListener) {
        String str;
        MiotLogUtil.d(TAG, "sendMiotSpecEventNotify");
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "send miot spec event notify failed, don't register");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else {
            if (!TextUtils.isEmpty(this.mPartnerId)) {
                str = this.mPartnerId;
            } else {
                str = this.mDevice.getDeviceId();
            }
            this.mMiotClient.sendRequest(new MiotSpecV2EventNotify(str, this.mPartnerId, event), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.7
                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onSucceed(MiotResponse miotResponse) {
                    MiotLogUtil.d(MiotHost.TAG, "sendMiotSpecEvent onSucceed");
                    if (completedListener != null) {
                        completedListener.onSucceed((miotResponse == null || miotResponse.getContent() == null) ? null : String.valueOf(miotResponse.getContent()));
                    }
                }

                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onFailed(MiotError miotError) {
                    MiotLogUtil.d(MiotHost.TAG, "sendMiotSpecEvent onFailed: " + miotError);
                    CompletedListener completedListener2 = completedListener;
                    if (completedListener2 != null) {
                        completedListener2.onFailed(miotError);
                    }
                }
            });
        }
    }

    public void sendMessage(String str, String str2, final CompletedListener completedListener) {
        String str3;
        MiotLogUtil.d(TAG, "sendMessage");
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "send message failed, don't register");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else {
            if (!TextUtils.isEmpty(this.mPartnerId)) {
                str3 = this.mPartnerId;
            } else {
                str3 = this.mDevice.getDeviceId();
            }
            this.mMiotClient.sendRequest(new MiotEventNotify(str, str2, str3, this.mPartnerId), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.8
                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onSucceed(MiotResponse miotResponse) {
                    MiotLogUtil.d(MiotHost.TAG, "sendMessage onSucceed");
                    if (completedListener != null) {
                        completedListener.onSucceed((miotResponse == null || miotResponse.getContent() == null) ? null : String.valueOf(miotResponse.getContent()));
                    }
                }

                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onFailed(MiotError miotError) {
                    MiotLogUtil.d(MiotHost.TAG, "sendMessage onFailed: " + miotError);
                    CompletedListener completedListener2 = completedListener;
                    if (completedListener2 != null) {
                        completedListener2.onFailed(miotError);
                    }
                }
            });
        }
    }

    public void getDeviceProps(List<String> list, CompletedListener completedListener) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (list == null || list.isEmpty()) {
            completedListener.onFailed(MiotError.INTERNAL_INTERRUPTED);
            return;
        }
        for (String str : list) {
            if (!TextUtils.isEmpty(str)) {
                jSONArray.put(str);
            }
        }
        try {
            jSONObject.put("keys", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendMessage("_sync.get_device_props", jSONObject.toString(), completedListener);
    }

    public void createSession(String str, final SessionListener sessionListener) {
        MiotClient miotClient = this.mMiotClient;
        if (miotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (sessionListener != null) {
                sessionListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        miotClient.sendRequest(new MiotCreateVoiceSessionRequest(str, this.mPartnerId), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.9
            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onSucceed(MiotResponse miotResponse) {
                MiotLogUtil.d(MiotHost.TAG, "createSession onSucceed");
                sessionListener.onSucceed(miotResponse.getContent().optString("sessionid"), miotResponse.getContent().optString("token"), miotResponse.getContent().optInt("expire"));
            }

            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onFailed(MiotError miotError) {
                MiotLogUtil.d(MiotHost.TAG, "createSession onFailed: " + miotError.toString());
                sessionListener.onFailed(miotError);
            }
        });
    }

    public void getBindKey(final BindKeyListener bindKeyListener) {
        MiotClient miotClient = this.mMiotClient;
        if (miotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (bindKeyListener != null) {
                bindKeyListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        miotClient.sendRequest(new MiotGetBindKeyRequest(), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.10
            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onSucceed(MiotResponse miotResponse) {
                MiotLogUtil.d(MiotHost.TAG, "getBindKey onSucceed");
                if (miotResponse.hasError()) {
                    bindKeyListener.onFailed(new MiotError(miotResponse.getError().optInt("code", 0), miotResponse.getError().optString("message", "")));
                    return;
                }
                String optString = miotResponse.getContent().optString("bind_key");
                int optInt = miotResponse.getContent().optInt("expire", 0);
                if (optString == null) {
                    bindKeyListener.onFailed(MiotError.INTERNAL);
                } else {
                    bindKeyListener.onSucceed(optString, optInt);
                }
            }

            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onFailed(MiotError miotError) {
                MiotLogUtil.d(MiotHost.TAG, "getBindKey onFailed: " + miotError.toString());
                bindKeyListener.onFailed(miotError);
            }
        });
    }

    public void checkBind(final CheckBindListener checkBindListener) {
        MiotClient miotClient = this.mMiotClient;
        if (miotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (checkBindListener != null) {
                checkBindListener.onFailed(MiotError.INTERNAL);
            }
        } else if (checkBindListener != null) {
            miotClient.sendRequest(new MiotCheckBindRequest(this.mPartnerId), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.11
                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onSucceed(MiotResponse miotResponse) {
                    MiotLogUtil.d(MiotHost.TAG, "checkBind onSucceed");
                    if (miotResponse.hasError()) {
                        checkBindListener.onFailed(new MiotError(miotResponse.getError().optInt("code", 0), miotResponse.getError().optString("message", "")));
                        return;
                    }
                    checkBindListener.onSucceed(miotResponse.getContent().optBoolean("isBind"));
                }

                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onFailed(MiotError miotError) {
                    MiotLogUtil.d(MiotHost.TAG, "checkBind onFailed: " + miotError.toString());
                    checkBindListener.onFailed(miotError);
                }
            });
        }
    }

    public void setSupportRpcType(int i) {
        sSupportRpcType = i;
    }

    public void setBindParams(BindParams bindParams) {
        if (bindParams != null) {
            this.mBindParams = bindParams;
            this.mMiotStore.deleteLocalData();
            this.mToken = null;
        }
    }

    public void getSyncTime(final SyncTimeListener syncTimeListener) {
        MiotClient miotClient = this.mMiotClient;
        if (miotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (syncTimeListener != null) {
                syncTimeListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        miotClient.sendRequest(new MiotSyncTimeRequest(), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.12
            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onSucceed(MiotResponse miotResponse) {
                MiotLogUtil.d(MiotHost.TAG, "getSyncTime onSucceed");
                if (miotResponse.hasError()) {
                    syncTimeListener.onFailed(new MiotError(miotResponse.getError().optInt("code", 0), miotResponse.getError().optString("message", "")));
                    return;
                }
                syncTimeListener.onSucceed(Long.parseLong(miotResponse.getContent().optString("params")) * 1000);
            }

            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onFailed(MiotError miotError) {
                MiotLogUtil.d(MiotHost.TAG, "getSyncTime onFailed: " + miotError.toString());
                syncTimeListener.onFailed(miotError);
            }
        });
    }

    public void getLocalStatus(final CompletedListener completedListener) {
        this.mMiotClient.sendInternalRequest(new MiotLocalQueryStatus(), true, new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.13
            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onSucceed(MiotResponse miotResponse) {
                MiotLogUtil.d(MiotHost.TAG, "getLocalStatus onSucceed:" + miotResponse.getOriginResponse());
                completedListener.onSucceed(miotResponse.getOriginResponse());
            }

            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onFailed(MiotError miotError) {
                MiotLogUtil.d(MiotHost.TAG, "getLocalStatus onFailed: " + miotError.toString());
                completedListener.onFailed(miotError);
            }
        });
    }

    public void setDynamicDid(boolean z) {
        this.mIsDynamicDid = z;
    }

    public void sendGatewayMessage(String str, final GatewayMessageListener gatewayMessageListener) {
        Log.d(TAG, "sendGatewayMessage: " + str);
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (gatewayMessageListener != null) {
                gatewayMessageListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        GatewayRawRequest gatewayRawRequest = new GatewayRawRequest(str);
        if (gatewayRawRequest.isRpcRequest()) {
            this.mMiotClient.sendRequest(gatewayRawRequest, false, new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.14
                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onSucceed(MiotResponse miotResponse) {
                    gatewayMessageListener.onSucceed(miotResponse.getOriginResponse());
                }

                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onFailed(MiotError miotError) {
                    gatewayMessageListener.onFailed(miotError);
                }
            });
            return;
        }
        JSONArray jSONArray = this.mCacheResponseData.get(Integer.valueOf(gatewayRawRequest.getId()));
        this.mCacheResponseData.remove(Integer.valueOf(gatewayRawRequest.getId()));
        if (jSONArray == null || jSONArray.length() == 0) {
            this.mMiotClient.sendResponse(gatewayRawRequest);
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONArray optJSONArray = jSONObject.optJSONArray("result");
            if (optJSONArray == null) {
                optJSONArray = new JSONArray();
                jSONObject.put("result", optJSONArray);
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                optJSONArray.put(jSONArray.get(i));
            }
            this.mMiotClient.sendMessageWithoutEncrypt(jSONObject.toString().getBytes());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void registerGatewayReceiveListener(GatewayMessageListener gatewayMessageListener) {
        MiotLogUtil.d(TAG, "registerGatewayReceiveListener");
        this.mGatewayMessageListener = gatewayMessageListener;
    }

    public void registerMiotInternalCommandListener(MiotInternalCommandListener miotInternalCommandListener) {
        MiotLogUtil.d(TAG, "registerMiotInternalCommandListener");
        this.mMiotClient.registerMiotInternalCommandListener(miotInternalCommandListener);
    }

    public void registerMiotTransparentListener(List<String> list, TransparentListener transparentListener) {
        MiotMessageFactory.addTransparentMethods(list);
        this.mTransParentListener = transparentListener;
    }

    public void sendTransparentMessage(String str, CompletedListener completedListener) {
        MiotLogUtil.d(TAG, "sendTransparentMessage");
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "send message failed, don't register");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
            }
        } else {
            if (str.contains("wifi_connected") && !str.contains("25c829b1922d3123_miwifi")) {
                MiotLogUtil.i(TAG, "sendDelayWifiConnectedMsg: " + str);
                sendDelayWifiConnectedMsg(4, str);
            }
            this.mMiotClient.sendMessageWithoutEncrypt(str.getBytes());
            completedListener.onSucceed("");
        }
    }

    public void reset() {
        MiotLogUtil.d(TAG, "reset");
        this.mToken = null;
        this.mMiotStore.deleteLocalData();
        clearUserId();
        stop();
    }

    public void refreshMiotInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            MiotLogUtil.d(TAG, "refreshMiotInfo miotInfo is empty");
            return;
        }
        Device device = this.mDevice;
        if (device != null) {
            device.setMiotInfo(str);
        }
        MiotClient miotClient = this.mMiotClient;
        if (miotClient != null && miotClient.isConnected()) {
            this.mMiotClient.sendInternalRequest(new MiotRefreshInfo());
        }
    }

    public void syncDeviceName(String str, final CompletedListener completedListener) {
        MiotClient miotClient = this.mMiotClient;
        if (miotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        miotClient.sendRequest(new MiotSyncDeviceNameRequest(this.mDevice.getDeviceId(), str, this.mPartnerId), new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.15
            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onSucceed(MiotResponse miotResponse) {
                MiotLogUtil.d(MiotHost.TAG, "syncDeviceName onSucceed");
                if (completedListener != null) {
                    completedListener.onSucceed((miotResponse == null || miotResponse.getContent() == null) ? null : String.valueOf(miotResponse.getContent()));
                }
            }

            @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
            public void onFailed(MiotError miotError) {
                MiotLogUtil.d(MiotHost.TAG, "syncDeviceName onFailed: " + miotError.toString());
                CompletedListener completedListener2 = completedListener;
                if (completedListener2 != null) {
                    completedListener2.onFailed(miotError);
                }
            }
        });
    }

    private String getAppBindkey() {
        BindParams bindParams = this.mBindParams;
        if (bindParams != null) {
            return bindParams.getBindKey();
        }
        return "";
    }

    private void clearUserId() {
        Device device = this.mDevice;
        if (device != null && device.getMiotInfo() != null) {
            try {
                JSONObject jSONObject = new JSONObject(this.mDevice.getMiotInfo());
                JSONObject optJSONObject = jSONObject.optJSONObject("params");
                if (optJSONObject != null && optJSONObject.has(OneTrack.Param.UID)) {
                    optJSONObject.put(OneTrack.Param.UID, "");
                    this.mDevice.setMiotInfo(jSONObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerMiotConnectedListener(MiotConnectedListener miotConnectedListener) {
        this.mMiotConnectedListener = miotConnectedListener;
    }

    public boolean isMiotConnected() {
        MiotClient miotClient = this.mMiotClient;
        return miotClient != null && miotClient.isConnected();
    }

    @Override // com.xiaomi.miot.host.lan.impl.client.MiotClient.ConnectionListener
    public void onConnected() {
        MiotClient miotClient;
        if (this.mDevice != null && (miotClient = this.mMiotClient) != null) {
            miotClient.sendInternalRequest(new MiotHello());
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.client.MiotClient.ConnectionListener
    public void onLoginSuccessful() {
        MiotConnectedListener miotConnectedListener = this.mMiotConnectedListener;
        if (miotConnectedListener != null) {
            miotConnectedListener.onConnected();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.client.MiotClient.ConnectionListener
    public void onDisconnected() {
        MiotConnectedListener miotConnectedListener = this.mMiotConnectedListener;
        if (miotConnectedListener != null) {
            miotConnectedListener.onDisconnected();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotRequestHandler
    public void onRequest(MiotRequest miotRequest) {
        MiotLogUtil.i(TAG, "onRequest method: " + miotRequest.getMethod().name());
        switch (miotRequest.getMethod()) {
            case CONFIG:
                onConfigurationRequest((MiotConfig) miotRequest);
                MiotClient miotClient = this.mMiotClient;
                if (miotClient != null) {
                    miotClient.sendInternalRequest(new MiotHello());
                    return;
                }
                return;
            case INFO:
                onInfoRequest((MiotInfo) miotRequest);
                MiotClient miotClient2 = this.mMiotClient;
                if (miotClient2 != null) {
                    miotClient2.sendInternalRequest(new MiotHello());
                    return;
                }
                return;
            case MDNS:
                onMdnsRequest((MiotMdns) miotRequest);
                return;
            case BINDSTAT:
                onBindStatRequest((MiotBindStat) miotRequest);
                return;
            case RESTORE:
                onRestore((MiotRestore) miotRequest);
                return;
            case TOKEN:
                onTokenRequest((MiotToken) miotRequest);
                MiotClient miotClient3 = this.mMiotClient;
                if (miotClient3 != null) {
                    miotClient3.sendInternalRequest(new MiotHello());
                    return;
                }
                return;
            case TOKEN_UPDATE:
                onTokenUpdateRequest((MiotUpdateToken) miotRequest);
                MiotClient miotClient4 = this.mMiotClient;
                if (miotClient4 != null) {
                    miotClient4.sendInternalRequest(new MiotHello());
                    return;
                }
                return;
            case WIFI_SCAN:
                onWifiScanRequest((MiotWifiScan) miotRequest);
                return;
            case WIFI:
                onWifiRequest((MiotWifi) miotRequest);
                this.mHasSetWifiStatus = true;
                this.mMiotClient.sendInternalRequest(new MiotHello());
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(getAppBindkey())) {
                    sendMiotUserInfo();
                    return;
                } else if (this.mUid != 0) {
                    this.mBindParams = new BindParams();
                    sendMiotUserInfo();
                    return;
                } else {
                    return;
                }
            case REPORT_OTCINFO_RESULT:
                onReportOtcInfoResult((MiotReportOtcInfoResult) miotRequest);
                return;
            case GET_PROPERTY:
                OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
                onGetProperty((MiotPropertyGetter) miotRequest);
                return;
            case SET_PROPERTY:
                OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
                onSetProperty((MiotPropertySetter) miotRequest);
                return;
            case ACTION:
                OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
                onAction((MiotAction) miotRequest);
                return;
            case VOICE_CTRL_REPLY:
                onVoiceReplyRequest((MiotVoiceReplyRequest) miotRequest);
                return;
            case MIOT_SPEC_V2_GET_PROPERTY:
                OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
                onMiotSpecV2GetProperty((MiotSpecV2PropertyGetter) miotRequest);
                return;
            case MIOT_SPEC_V2_SET_PROPERTY:
                OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
                onMiotSpecV2SetProperty((MiotSpecV2PropertySetter) miotRequest);
                return;
            case MIOT_SPEC_V2_ACTION:
                OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
                onMiotSpecV2Action((MiotSpecV2Action) miotRequest);
                return;
            case BRAODCAST_LOCAL_STATUS:
                onGetLocalBroadcast((MiotLocalBroadcast) miotRequest);
                return;
            case WIFI_START:
                MiotWifiStart miotWifiStart = (MiotWifiStart) miotRequest;
                onWifiStartRequest(miotWifiStart);
                onMiotTransparentRequest(new MiotTransparantRequest("_internal.wifi_start", miotWifiStart.getRequestJsonObject()));
                return;
            case MIOT_TRANSPARENT_REQUEST:
                onMiotTransparentRequest((MiotTransparantRequest) miotRequest);
                return;
            default:
                MiotLogUtil.e(TAG, "unknown request: " + miotRequest.getMethod());
                return;
        }
    }

    public boolean hasSetWifiStatus() {
        return this.mHasSetWifiStatus;
    }

    private synchronized void sendMiotUserInfo() {
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
        } else {
            this.mMiotClient.sendInternalRequest(new MiotUserInfo(this.mDevice.getMiotInfo(), this.mBindParams), true, new MiotResponseHandler() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.16
                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onSucceed(MiotResponse miotResponse) {
                    MiotLogUtil.e(MiotHost.TAG, "MiotUserInfo response: " + miotResponse.getOriginResponse());
                }

                @Override // com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler
                public void onFailed(MiotError miotError) {
                    MiotLogUtil.e(MiotHost.TAG, "MiotUserInfo error: " + miotError.toString());
                }
            });
        }
    }

    public void sendInternetConnectEventToOt(String str) {
        MiotClient miotClient = this.mMiotClient;
        if (miotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        miotClient.sendInternalRequest(new MiotWifiConnected(str));
        this.mMiotClient.sendInternalRequest(new MiotHello());
    }

    private synchronized void onMiotTransparentRequest(MiotTransparantRequest miotTransparantRequest) {
        if (this.mTransParentListener != null) {
            this.mTransParentListener.onMessage(miotTransparantRequest.getOriginRequest());
        } else {
            MiotLogUtil.e(TAG, "mTransParentListener is null, can not transport request: " + miotTransparantRequest.getOriginRequest());
        }
    }

    private synchronized void onVoiceReplyRequest(MiotVoiceReplyRequest miotVoiceReplyRequest) {
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
        } else {
            this.mMiotClient.sendResponse(miotVoiceReplyRequest);
        }
    }

    private synchronized void onConfigurationRequest(MiotConfig miotConfig) {
        MiotLogUtil.e(TAG, "onConfigurationRequest");
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "onConfigurationRequest failed, don't register");
        } else if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
        } else {
            long j = 0;
            try {
                MiotLogUtil.e(TAG, "deviceId: " + this.mDevice.getDeviceId());
                j = Long.valueOf(this.mDevice.getDeviceId()).longValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            miotConfig.setDeviceId(j);
            miotConfig.setMac(this.mDevice.getMacAddress());
            miotConfig.setKey(this.mDevice.getMiotToken());
            miotConfig.setVendor(this.mDevice.getManufacturer());
            miotConfig.setModel(this.mDevice.getModelName());
            miotConfig.setSn(this.mDevice.getSn());
            MiotLogUtil.e(TAG, "SN: " + this.mDevice.getSn());
            if (this.mDevice.getKeyType() == KeyType.BASE64.ordinal()) {
                miotConfig.setKeyType(HttpHeaders.Values.BASE64);
            }
            miotConfig.setSmartConfig(this.mDevice.getSmartConfig().booleanValue());
            miotConfig.setDevCert(this.mDevice.getDevCert());
            miotConfig.setManuCert(this.mDevice.getManuCert());
            this.mMiotClient.sendResponse(miotConfig);
        }
    }

    private synchronized void onTokenRequest(MiotToken miotToken) {
        MiotLogUtil.e(TAG, "onTokenRequest: " + miotToken.getNtoken());
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        if (this.mToken != null) {
            MiotLogUtil.d(TAG, "onTokenRequest token: " + this.mToken);
            miotToken.setNtoken(this.mToken);
        } else {
            MiotLogUtil.e(TAG, "onTokenRequest token is null");
            this.mToken = miotToken.getNtoken();
            this.mMiotStore.saveToken(miotToken.getNtoken());
        }
        this.mMiotClient.sendResponse(miotToken);
    }

    private synchronized void onTokenUpdateRequest(MiotUpdateToken miotUpdateToken) {
        MiotLogUtil.e(TAG, "onTokenUpdateRequest: " + miotUpdateToken.getNtoken());
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        this.mToken = miotUpdateToken.getNtoken();
        this.mMiotStore.saveToken(miotUpdateToken.getNtoken());
        this.mMiotClient.sendResponse(miotUpdateToken);
    }

    private synchronized void onWifiStartRequest(MiotWifiStart miotWifiStart) {
        MiotLogUtil.e(TAG, "onWifiStartRequest: " + miotWifiStart.getMethod());
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
        }
    }

    private synchronized void onWifiScanRequest(final MiotWifiScan miotWifiScan) {
        MiotLogUtil.e(TAG, "onWifiScanRequest: " + miotWifiScan.getMethod());
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        final String ssid = miotWifiScan.getSsid();
        final WifiManager wifiManager = (WifiManager) this.mContext.getSystemService(Network.NETWORK_TYPE_WIFI);
        WifiSettingUtils.addHideNoSecureWifiConfig(wifiManager, ssid);
        wifiManager.startScan();
        new Handler(this.mContext.getMainLooper()).postDelayed(new Runnable() { // from class: com.xiaomi.miot.host.lan.impl.MiotHost.17
            @Override // java.lang.Runnable
            public void run() {
                List<ScanResult> wifiScanResult = WifiSettingUtils.getWifiScanResult(wifiManager, ssid);
                miotWifiScan.setItemNum(wifiScanResult.size());
                ArrayList arrayList = new ArrayList();
                for (ScanResult scanResult : wifiScanResult) {
                    MiotWifiScan.Item item = new MiotWifiScan.Item();
                    item.setBssid(scanResult.BSSID);
                    item.setRssi(scanResult.level);
                    if (Build.VERSION.SDK_INT >= 23) {
                        item.setChannel(scanResult.channelWidth);
                    }
                    arrayList.add(item);
                }
                miotWifiScan.setItem(arrayList);
                MiotHost.this.mMiotClient.sendResponse(miotWifiScan);
            }
        }, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }

    public String getToken() {
        return this.mToken;
    }

    private synchronized void onWifiRequest(MiotWifi miotWifi) {
        MiotLogUtil.e(TAG, "onWifiRequest");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        miotWifi.setWifiMode(this.mWifiMode);
        this.mMiotClient.sendResponse(miotWifi);
    }

    private synchronized void onReportOtcInfoResult(MiotReportOtcInfoResult miotReportOtcInfoResult) {
        MiotLogUtil.d(TAG, "onReportOtcInfoResult");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        if (miotReportOtcInfoResult.isSuccess()) {
            this.mMiotClient.setUploadOtcInfoSuccess();
            OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
        } else if (OTKeyExceptionMonitor.getInstance(this.mContext).isKeyInvalid()) {
            OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
            if (this.mMiotConnectedListener != null) {
                this.mMiotConnectedListener.onTokenException();
            }
        } else {
            OTKeyExceptionMonitor.getInstance(this.mContext).abnormalDisconnection();
        }
    }

    private synchronized void onGetLocalBroadcast(MiotLocalBroadcast miotLocalBroadcast) {
        MiotLogUtil.d(TAG, "onGetLocalBroadcast");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        if (miotLocalBroadcast.isCloudConnected()) {
            this.mMiotClient.setUploadOtcInfoSuccess();
            OTKeyExceptionMonitor.getInstance(this.mContext).clearException();
        }
        if (miotLocalBroadcast.isWifiConnected() && this.mHandler != null) {
            MiotLogUtil.d(TAG, "removeMessage: MSG_WIFI_CONNECTED_RETRY_ID");
            this.mHandler.removeMessages(1);
        }
    }

    private synchronized void onInfoRequest(MiotInfo miotInfo) {
        MiotLogUtil.e(TAG, "onInfoRequest");
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "onInfoRequest failed, don't register");
        } else if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
        } else {
            OTInfo retrieveMiotInfo = MiotInfoManager.retrieveMiotInfo(this.mDevice, this.mContext);
            if (retrieveMiotInfo == null) {
                miotInfo.setResponse(this.mDevice.getMiotInfo());
            } else {
                if (this.mGetDeviceInfoHandler != null) {
                    try {
                        String onRequestRouterInfo = this.mGetDeviceInfoHandler.onRequestRouterInfo();
                        MiotLogUtil.d(TAG, "mGetDeviceInfoHandler info: " + onRequestRouterInfo);
                        if (!TextUtils.isEmpty(onRequestRouterInfo)) {
                            JSONObject jSONObject = new JSONObject(onRequestRouterInfo);
                            retrieveMiotInfo.setSsid(jSONObject.optString("ssid"));
                            retrieveMiotInfo.setPassword(jSONObject.optString("passwd"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String ssid = retrieveMiotInfo.getSsid();
                if (ssid != null && ssid.length() > 1 && ssid.charAt(0) == '\"' && ssid.charAt(ssid.length() - 1) == '\"') {
                    retrieveMiotInfo.setSsid(ssid.substring(1, ssid.length() - 1));
                }
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put(SchemaActivity.KEY_METHOD, MiotInfo.REQUEST_METHOD);
                    if (!TextUtils.isEmpty(this.mPartnerId)) {
                        jSONObject2.put("partner_id", this.mPartnerId);
                    }
                    if (this.mBindParams != null) {
                        if (!TextUtils.isEmpty(this.mBindParams.getPartnerId())) {
                            retrieveMiotInfo.setPartnerId(this.mBindParams.getPartnerId());
                        }
                        if (!TextUtils.isEmpty(this.mBindParams.getPartnerToken())) {
                            retrieveMiotInfo.setPartnerToken(this.mBindParams.getPartnerToken());
                        }
                    }
                    jSONObject2.put("params", retrieveMiotInfo.toJSON());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                MiotLogUtil.d(TAG, jSONObject2.toString());
                miotInfo.setResponse(jSONObject2.toString());
            }
            this.mMiotClient.sendResponse(miotInfo);
            this.registerCompletedListener.onSucceed(null);
        }
    }

    public synchronized void registerOnBindListener(OnBindListener onBindListener) {
        this.mOnBindListener = onBindListener;
    }

    public synchronized void registerOnTransmitListener(OnTransmitListener onTransmitListener) {
        this.mOnTransmitListener = onTransmitListener;
    }

    private synchronized void onMdnsRequest(MiotMdns miotMdns) {
        int params = miotMdns.getParams();
        MiotLogUtil.e(TAG, "onMdnsRequest: " + params);
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        if (this.mOnBindListener != null) {
            switch (params) {
                case 0:
                    MiotLogUtil.d(TAG, "onUnBind");
                    this.mOnBindListener.onUnBind();
                    break;
                case 1:
                    MiotLogUtil.d(TAG, "onBind");
                    this.mOnBindListener.onBind();
                    break;
            }
        }
        miotMdns.setParams(1);
        this.mMiotClient.sendResponse(miotMdns);
    }

    private synchronized void onBindStatRequest(MiotBindStat miotBindStat) {
        int stat = miotBindStat.getStat();
        MiotLogUtil.e(TAG, "onBindStatRequest: " + stat);
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        if (this.mOnBindListener != null) {
            switch (stat) {
                case 0:
                    MiotLogUtil.d(TAG, "onUnBind");
                    this.mOnBindListener.onUnBind();
                    break;
                case 1:
                    MiotLogUtil.d(TAG, "onBind");
                    this.mOnBindListener.onBind();
                    break;
            }
        }
    }

    private synchronized void onRestore(MiotRestore miotRestore) {
        MiotLogUtil.e(TAG, "onRestore");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        if (this.mOnBindListener != null) {
            MiotLogUtil.d(TAG, "onUnBind");
            this.mOnBindListener.onUnBind();
        }
        if (this.mGatewayMessageListener != null) {
            MiotLogUtil.d(TAG, "send message to gateway: " + miotRestore.getOriginRequest());
            this.mGatewayMessageListener.onSucceed(miotRestore.getOriginRequest());
        }
    }

    private synchronized void onGetProperty(MiotPropertyGetter miotPropertyGetter) {
        MiotLogUtil.d(TAG, "onGetProperty");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        for (String str : miotPropertyGetter.getProperties()) {
            Property property = getProperty(str);
            if (property == null) {
                MiotLogUtil.e(TAG, "property not found: " + str);
            } else {
                miotPropertyGetter.getValues().add(getPropertyValue(property));
            }
        }
        this.mMiotClient.sendResponse(miotPropertyGetter);
    }

    private synchronized void onSetProperty(MiotPropertySetter miotPropertySetter) {
        MiotLogUtil.d(TAG, "onSetProperty");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        Property property = getProperty(miotPropertySetter.getName());
        if (property == null) {
            MiotLogUtil.e(TAG, "property not found: " + miotPropertySetter.getName());
            miotPropertySetter.setError(MiotError.IOT_OUT_OF_RESOURCES);
        } else {
            miotPropertySetter.setError(setPropertyValue(property, miotPropertySetter.getValue()));
        }
        this.mMiotClient.sendResponse(miotPropertySetter);
    }

    private synchronized void onAction(MiotAction miotAction) {
        Object obj;
        MiotLogUtil.d(TAG, "onAction: " + miotAction.getAction());
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        ActionInfo actionInfo = getActionInfo(miotAction.getAction());
        if (actionInfo == null) {
            if (this.mGatewayMessageListener != null) {
                MiotLogUtil.d(TAG, "send message to gateway");
                this.mGatewayMessageListener.onSucceed(miotAction.getOriginRequest());
            } else {
                MiotLogUtil.e(TAG, "ActionInfo not found: " + miotAction.getAction());
                miotAction.setError(MiotError.INTERNAL_INVALID_OPERATION);
                this.mMiotClient.sendResponse(miotAction);
            }
            return;
        }
        for (int i = 0; i < actionInfo.getArguments().size(); i++) {
            Object params = miotAction.getParams();
            if (params instanceof JSONArray) {
                obj = ((JSONArray) params).opt(i);
            } else {
                obj = params.toString();
            }
            if (obj == null) {
                MiotLogUtil.e(TAG, "value is null");
                miotAction.setError(MiotError.IOT_INVALID_VALUE);
                this.mMiotClient.sendResponse(miotAction);
                return;
            } else if (!actionInfo.getArguments().get(i).setValue(obj)) {
                MiotLogUtil.e(TAG, "Property.setValue failed");
                miotAction.setError(MiotError.IOT_INVALID_VALUE);
                this.mMiotClient.sendResponse(miotAction);
                return;
            }
        }
        MiotError onAction = this.mOperationHandler.onAction(actionInfo);
        if (!onAction.equals(MiotError.OK)) {
            MiotLogUtil.e(TAG, "handler.onAction() failed");
            miotAction.setError(onAction);
            this.mMiotClient.sendResponse(miotAction);
            return;
        }
        if (mJSONObjectMethods.contains(miotAction.getAction())) {
            miotAction.setReponseParamJSONObject(true);
            for (Property property : actionInfo.getResults()) {
                try {
                    miotAction.getResults_JO().put(property.getDefinition().getType().getSubType(), property.getCurrentValue().getObjectValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (Property property2 : actionInfo.getResults()) {
                miotAction.getResults_JA().put(property2.getCurrentValue().getObjectValue());
            }
        }
        this.mMiotClient.sendResponse(miotAction);
    }

    private boolean isValidDid(String str) {
        Device device = this.mDevice;
        if (device == null) {
            MiotLogUtil.e(TAG, "isValidDid failed, don't registered");
            return false;
        } else if (TextUtils.equals(device.getDeviceId(), str)) {
            return true;
        } else {
            return !TextUtils.isEmpty(this.mPartnerId) && TextUtils.equals(this.mPartnerId, str);
        }
    }

    private void forwardMessageToGateway(MiotRequest miotRequest, List<MiotSpecV2Data> list, List<MiotSpecV2Data> list2) {
        if (list != null && list.size() > 0) {
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("did", list.get(i).did);
                    jSONObject.put("siid", list.get(i).siid);
                    jSONObject.put("piid", list.get(i).piid);
                    MiotError miotError = list.get(i).result;
                    if (miotError == null) {
                        jSONObject.put("code", MiotError.MIOT_SPEC_V2_OPERATE_FAILED.getCode());
                    } else if (miotError == MiotError.OK) {
                        if (miotRequest instanceof MiotSpecV2PropertyGetter) {
                            jSONObject.put(b.p, list.get(i).value);
                        }
                        jSONObject.put("code", 0);
                    } else {
                        jSONObject.put("code", miotError.getCode());
                    }
                    jSONArray.put(jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.mCacheResponseData.put(Integer.valueOf(miotRequest.getId()), jSONArray);
            this.mExecutorService.schedule(new DelaySendAckRunnable(miotRequest.getId()), 2L, TimeUnit.SECONDS);
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("id", miotRequest.getId());
            if (miotRequest instanceof MiotSpecV2PropertyGetter) {
                jSONObject2.put(SchemaActivity.KEY_METHOD, MiotSpecV2PropertyGetter.MIOT_SPEC_V2_REQUEST_METHOD);
            } else {
                jSONObject2.put(SchemaActivity.KEY_METHOD, MiotSpecV2PropertySetter.MIOT_SPEC_V2_REQUEST_METHOD);
            }
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < list2.size(); i2++) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("did", list2.get(i2).did);
                jSONObject3.put("siid", list2.get(i2).siid);
                jSONObject3.put("piid", list2.get(i2).piid);
                if (miotRequest instanceof MiotSpecV2PropertySetter) {
                    jSONObject3.put(b.p, list2.get(i2).value);
                }
                jSONArray2.put(jSONObject3);
            }
            jSONObject2.put("params", jSONArray2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        MiotLogUtil.d(TAG, "send message to gateway");
        this.mGatewayMessageListener.onSucceed(jSONObject2.toString());
    }

    private synchronized void onMiotSpecV2GetProperty(MiotSpecV2PropertyGetter miotSpecV2PropertyGetter) {
        MiotLogUtil.d(TAG, "onMiotSpecV2GetProperty");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (MiotSpecV2Data miotSpecV2Data : miotSpecV2PropertyGetter.getProperties()) {
            if (isValidDid(miotSpecV2Data.did)) {
                Property miotSpecV2Property = getMiotSpecV2Property(miotSpecV2Data.siid, miotSpecV2Data.piid);
                if (miotSpecV2Property == null) {
                    MiotLogUtil.e(TAG, "property not found: siid = " + miotSpecV2Data.siid + ", piid = " + miotSpecV2Data.piid);
                    miotSpecV2Data.result = MiotError.MIOT_SPEC_V2_PROPERTY_NOT_EXIST;
                } else {
                    miotSpecV2Data.value = getPropertyValue(miotSpecV2Property);
                    miotSpecV2Data.result = MiotError.OK;
                }
                arrayList.add(miotSpecV2Data);
            } else if (this.mGatewayMessageListener != null) {
                arrayList2.add(miotSpecV2Data);
            } else {
                MiotLogUtil.e(TAG, "did invalid");
                miotSpecV2Data.result = MiotError.MIOT_SPEC_V2_DID_INVALID;
                arrayList.add(miotSpecV2Data);
            }
        }
        if (!(this.mGatewayMessageListener == null || arrayList2.size() == 0)) {
            forwardMessageToGateway(miotSpecV2PropertyGetter, arrayList, arrayList2);
        }
        this.mMiotClient.sendResponse(miotSpecV2PropertyGetter);
    }

    private synchronized void onMiotSpecV2SetProperty(MiotSpecV2PropertySetter miotSpecV2PropertySetter) {
        MiotLogUtil.d(TAG, "onMiotSpecV2SetProperty");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (MiotSpecV2Data miotSpecV2Data : miotSpecV2PropertySetter.getProperties()) {
            if (isValidDid(miotSpecV2Data.did)) {
                Property miotSpecV2Property = getMiotSpecV2Property(miotSpecV2Data.siid, miotSpecV2Data.piid);
                if (miotSpecV2Property == null) {
                    MiotLogUtil.e(TAG, "property not found: siid = " + miotSpecV2Data.siid + ", piid = " + miotSpecV2Data.piid);
                    miotSpecV2Data.result = MiotError.MIOT_SPEC_V2_PROPERTY_NOT_EXIST;
                } else {
                    miotSpecV2Data.result = setMiotSpecV2PropertyValue(miotSpecV2Property, miotSpecV2Data.value);
                }
                arrayList.add(miotSpecV2Data);
            } else if (this.mGatewayMessageListener != null) {
                arrayList2.add(miotSpecV2Data);
            } else {
                MiotLogUtil.e(TAG, "did invalid");
                miotSpecV2Data.result = MiotError.MIOT_SPEC_V2_DID_INVALID;
                arrayList.add(miotSpecV2Data);
            }
        }
        if (!(this.mGatewayMessageListener == null || arrayList2.size() == 0)) {
            forwardMessageToGateway(miotSpecV2PropertySetter, arrayList, arrayList2);
        }
        this.mMiotClient.sendResponse(miotSpecV2PropertySetter);
    }

    private synchronized void onMiotSpecV2Action(MiotSpecV2Action miotSpecV2Action) {
        MiotLogUtil.d(TAG, "onMiotSpecV2Action");
        if (this.mMiotClient == null) {
            MiotLogUtil.e(TAG, "has destroyed");
        } else if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "don't registered");
            miotSpecV2Action.setError(MiotError.MIOT_SPEC_V2_INTERNAL_ERROR);
            this.mMiotClient.sendResponse(miotSpecV2Action);
        } else if (isValidDid(miotSpecV2Action.getDid())) {
            ActionInfo miotSpecV2ActionInfo = getMiotSpecV2ActionInfo(miotSpecV2Action.getSiid(), miotSpecV2Action.getAiid());
            if (miotSpecV2ActionInfo == null) {
                MiotLogUtil.e(TAG, "ActionInfo not found: " + miotSpecV2Action.getAction());
                miotSpecV2Action.setError(MiotError.MIOT_SPEC_V2_ACTION_IN_PARAMS_INVALID);
                this.mMiotClient.sendResponse(miotSpecV2Action);
                return;
            }
            JSONArray inArray = miotSpecV2Action.getInArray();
            for (int i = 0; i < miotSpecV2ActionInfo.getArguments().size() && i < inArray.length(); i++) {
                Object obj = null;
                try {
                    obj = inArray.optJSONObject(i);
                    if (obj == null) {
                        obj = inArray.get(i);
                    } else {
                        obj = ((JSONObject) obj).get(b.p);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (obj == null) {
                    MiotLogUtil.e(TAG, "value is null");
                    miotSpecV2Action.setError(MiotError.MIOT_SPEC_V2_ACTION_IN_PARAMS_INVALID);
                    this.mMiotClient.sendResponse(miotSpecV2Action);
                    return;
                } else if (!miotSpecV2ActionInfo.getArguments().get(i).setValue(obj)) {
                    MiotLogUtil.e(TAG, "Property.setValue failed");
                    miotSpecV2Action.setError(MiotError.MIOT_SPEC_V2_OPERATE_FAILED);
                    this.mMiotClient.sendResponse(miotSpecV2Action);
                    return;
                }
            }
            MiotError onAction = this.mOperationHandler.onAction(miotSpecV2ActionInfo);
            if (!onAction.equals(MiotError.OK)) {
                MiotLogUtil.e(TAG, "handler.onAction() failed");
                miotSpecV2Action.setError(onAction);
                this.mMiotClient.sendResponse(miotSpecV2Action);
                return;
            }
            for (Property property : miotSpecV2ActionInfo.getResults()) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("piid", property.getInstanceID());
                    jSONObject.put(b.p, property.getCurrentValue().getObjectValue());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                miotSpecV2Action.getOutArray().put(jSONObject);
            }
            this.mMiotClient.sendResponse(miotSpecV2Action);
        } else if (this.mGatewayMessageListener != null) {
            MiotLogUtil.d(TAG, "send message to gateway");
            this.mGatewayMessageListener.onSucceed(new String(miotSpecV2Action.encodeRequest()));
        } else {
            MiotLogUtil.e(TAG, "did invalid");
            miotSpecV2Action.setError(MiotError.MIOT_SPEC_V2_DID_INVALID);
            this.mMiotClient.sendResponse(miotSpecV2Action);
        }
    }

    private Object getPropertyValue(Property property) {
        if (property == null) {
            MiotLogUtil.e(TAG, "property not found");
            return "null";
        }
        MiotError onGet = this.mOperationHandler.onGet(property);
        if (onGet.equals(MiotError.OK)) {
            return property.getCurrentValue().getObjectValue();
        }
        MiotLogUtil.e(TAG, "get property failed: " + onGet.toString());
        DataValue createDefaultValue = property.getDefinition().getDataType().createDefaultValue();
        if (createDefaultValue != null) {
            return createDefaultValue.getObjectValue();
        }
        MiotLogUtil.e(TAG, "property createDefaultValue failed");
        return "null";
    }

    private MiotError setPropertyValue(Property property, Object obj) {
        if (property == null) {
            MiotLogUtil.e(TAG, "property not found");
            return MiotError.IOT_OUT_OF_RESOURCES;
        } else if (property.setValue(obj)) {
            return this.mOperationHandler.onSet(property);
        } else {
            MiotLogUtil.e(TAG, "property value invalid: " + obj);
            return MiotError.IOT_INVALID_VALUE;
        }
    }

    private MiotError setMiotSpecV2PropertyValue(Property property, Object obj) {
        if (property == null) {
            MiotLogUtil.e(TAG, "property not found");
            return MiotError.MIOT_SPEC_V2_PROPERTY_NOT_EXIST;
        } else if (property.setValue(obj)) {
            return this.mOperationHandler.onSet(property);
        } else {
            MiotLogUtil.e(TAG, "property value invalid: " + obj);
            return MiotError.MIOT_SPEC_V2_PROPERY_VALUE_INVALID;
        }
    }

    private Property getProperty(String str) {
        MiotLogUtil.d(TAG, "getProperty: " + str);
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "don't registered");
            return null;
        }
        if (MiotSupportRpcType.isMitvType() || MiotSupportRpcType.isYunmiType()) {
            for (Service service : this.mDevice.getServices()) {
                for (Property property : service.getProperties()) {
                    if (property.getDefinition().getType().getSubType().equalsIgnoreCase(str)) {
                        MiotLogUtil.d(TAG, "getProperty: " + property.getDefinition().getType().getSubType());
                        return property;
                    }
                }
            }
        } else {
            String[] split = str.split("_");
            if (split.length != 2) {
                return null;
            }
            String str2 = split[0];
            String str3 = split[1];
            for (Service service2 : this.mDevice.getServices()) {
                if (service2.getType().getSubType().equalsIgnoreCase(str2)) {
                    for (Property property2 : service2.getProperties()) {
                        if (property2.getDefinition().getType().getSubType().equalsIgnoreCase(str3)) {
                            MiotLogUtil.d(TAG, "getProperty: " + str3);
                            return property2;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    private Property getMiotSpecV2Property(int i, int i2) {
        MiotLogUtil.d(TAG, "getMiotSpecV2Property: siid = " + i + ", piid = " + i2);
        Device device = this.mDevice;
        if (device == null) {
            MiotLogUtil.e(TAG, "don't registered");
            return null;
        }
        for (Service service : device.getServices()) {
            if (i == service.getInstanceID()) {
                for (Property property : service.getProperties()) {
                    if (i2 == property.getInstanceID()) {
                        MiotLogUtil.d(TAG, "getMiotSpecV2Property: " + property.getDefinition().getType().getSubType());
                        return property;
                    }
                }
                continue;
            }
        }
        return null;
    }

    static {
        mWhiteListMethods.add("miIO.ota");
        mWhiteListMethods.add("miIO.get_ota_progress");
        mWhiteListMethods.add("async_device_feedback_log");
        mJSONObjectMethods.add("bt_gateway_status");
        mJSONObjectMethods.add("bt_gateway_enable");
        mJSONObjectMethods.add("bt_gateway_disable");
    }

    private ActionInfo getActionInfo(String str) {
        MiotLogUtil.d(TAG, "method: " + str);
        if (this.mDevice == null) {
            MiotLogUtil.e(TAG, "don't registered");
            return null;
        }
        if (MiotSupportRpcType.isMitvType() || MiotSupportRpcType.isYunmiType() || mWhiteListMethods.contains(str) || mJSONObjectMethods.contains(str)) {
            for (Service service : this.mDevice.getServices()) {
                for (Action action : service.getActions()) {
                    MiotLogUtil.d(TAG, "actionName: " + action.getType().getSubType());
                    if (action.getType().getSubType().equalsIgnoreCase(str)) {
                        return service.getActionInfo(action.getType());
                    }
                }
            }
        } else {
            String[] split = str.split("_");
            if (split.length != 3) {
                return null;
            }
            String str2 = split[1];
            String str3 = split[2];
            for (Service service2 : this.mDevice.getServices()) {
                MiotLogUtil.d(TAG, "serviceName: " + service2.getType().getSubType());
                if (service2.getType().getSubType().equalsIgnoreCase(str2)) {
                    for (Action action2 : service2.getActions()) {
                        MiotLogUtil.d(TAG, "actionName: " + action2.getType().getSubType());
                        if (action2.getType().getSubType().equalsIgnoreCase(str3)) {
                            return service2.getActionInfo(action2.getType());
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    private ActionInfo getMiotSpecV2ActionInfo(int i, int i2) {
        MiotLogUtil.d(TAG, "getMiotSpecV2ActionInfo: siid = " + i + ", aiid = " + i2);
        Device device = this.mDevice;
        if (device == null) {
            MiotLogUtil.e(TAG, "don't registered");
            return null;
        }
        for (Service service : device.getServices()) {
            if (service.getInstanceID() == i) {
                for (Action action : service.getActions()) {
                    if (action.getInstanceID() == i2) {
                        MiotLogUtil.d(TAG, "actionName: " + action.getType().getSubType());
                        return service.getActionInfo(action.getType());
                    }
                }
                continue;
            }
        }
        return null;
    }

    private static String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    @Override // com.xiaomi.miot.host.lan.impl.client.MiotClient.TransmitListener
    public void onTransparentTransmit(String str) {
        MiotLogUtil.d(TAG, "onTransmit data" + str);
        OnTransmitListener onTransmitListener = this.mOnTransmitListener;
        if (onTransmitListener != null) {
            onTransmitListener.onTransmit(str);
        }
    }

    /* loaded from: classes2.dex */
    public class DelaySendAckRunnable implements Runnable {
        private int id;

        private DelaySendAckRunnable(int i) {
            MiotHost.this = r1;
            this.id = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            MiotLogUtil.d(MiotHost.TAG, "DelaySendAckRunnable, id = " + this.id);
            JSONArray jSONArray = (JSONArray) MiotHost.this.mCacheResponseData.get(Integer.valueOf(this.id));
            MiotHost.this.mCacheResponseData.remove(Integer.valueOf(this.id));
            if (MiotHost.this.mMiotClient != null && jSONArray != null && jSONArray.length() > 0) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("id", this.id);
                    jSONObject.put("code", 0);
                    jSONObject.put("message", "");
                    jSONObject.put("result", jSONArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MiotLogUtil.d(MiotHost.TAG, "DelaySendAckRunnable, id = " + this.id + ", wait gateway ack timeout");
                MiotHost.this.mMiotClient.sendMessageWithoutEncrypt(jSONObject.toString().getBytes());
            }
        }
    }
}
