package com.xiaomi.miot.host.lan.impl.client;

import android.text.TextUtils;
import com.xiaomi.mico.settingslib.core.MicoSettingsAction;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.host.lan.impl.MiotLogUtil;
import com.xiaomi.miot.host.lan.impl.codec.MiotMessage;
import com.xiaomi.miot.host.lan.impl.codec.MiotMessageFactory;
import com.xiaomi.miot.host.lan.impl.codec.MiotMessageType;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequest;
import com.xiaomi.miot.host.lan.impl.codec.MiotRequestHandler;
import com.xiaomi.miot.host.lan.impl.codec.MiotResponse;
import com.xiaomi.miot.host.lan.impl.codec.MiotResponseHandler;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.listener.MiotInternalCommandListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotClient implements MiotClientListener {
    private static final String MIOT_LOCAL_IP = "127.0.0.1";
    private static final int MIOT_LOCAL_PORT = 54322;
    private static final int REQUEST_TIMEOUT = 6;
    private static final String TAG = "MiotClient";
    private static MiotClient miotClient = new MiotClient();
    private Bootstrap bootstrap;
    private EventLoopGroup clientLoopGroup;
    private boolean connected;
    private ChannelFuture f;
    private ConnectionListener listener;
    private ScheduledExecutorService mExecutor;
    private MiotResponseHandler mGeneralResponseHandler;
    private MiotInternalCommandListener mMiotInternalCommandListener;
    private TransmitListener mTransmitListener;
    private ScheduledFuture mWaitOtcInfoScheduledFuture;
    private MiotRequestHandler requestHandler;
    private int requestId = 10000;
    private Map<Integer, MiotResponseHandler> responseHandlers = new HashMap();
    private Map<Integer, ScheduledFuture> mScheduledFutureMap = new HashMap();
    private boolean mIsLoginSuccess = false;

    /* loaded from: classes2.dex */
    public interface ConnectionListener {
        void onConnected();

        void onDisconnected();

        void onLoginSuccessful();
    }

    /* loaded from: classes2.dex */
    public interface TransmitListener {
        void onTransparentTransmit(String str);
    }

    private MiotClient() {
    }

    public static MiotClient getInstance() {
        return miotClient;
    }

    public void initialize() {
        this.clientLoopGroup = new OioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(this.clientLoopGroup);
        this.bootstrap.channel(OioSocketChannel.class);
        this.bootstrap.handler(new MiotClientInitializer(this));
        this.mExecutor = Executors.newScheduledThreadPool(2);
    }

    public void destroy() {
        MiotLogUtil.d(TAG, "destroy");
        EventLoopGroup eventLoopGroup = this.clientLoopGroup;
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
            this.connected = false;
        }
        ScheduledExecutorService scheduledExecutorService = this.mExecutor;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
            this.mExecutor = null;
        }
        this.responseHandlers.clear();
        this.mScheduledFutureMap.clear();
    }

    public void registerRequestHandler(MiotRequestHandler miotRequestHandler) {
        this.requestHandler = miotRequestHandler;
    }

    public void registerGeneralResponseHandler(MiotResponseHandler miotResponseHandler) {
        this.mGeneralResponseHandler = miotResponseHandler;
    }

    public void registerMiotInternalCommandListener(MiotInternalCommandListener miotInternalCommandListener) {
        this.mMiotInternalCommandListener = miotInternalCommandListener;
    }

    public synchronized void connect(ConnectionListener connectionListener) {
        MiotLogUtil.d(TAG, "connect");
        this.listener = connectionListener;
        this.f = this.bootstrap.connect(MIOT_LOCAL_IP, MIOT_LOCAL_PORT);
    }

    public synchronized void disconnect() {
        MiotLogUtil.d(TAG, MicoSettingsAction.BLUETOOTH_DISCONNECT);
        if (this.f != null) {
            if (this.f.channel().isActive()) {
                this.f.channel().close();
            }
            this.f = null;
        }
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void sendInternalRequest(MiotRequest miotRequest) {
        int i = this.requestId;
        this.requestId = i + 1;
        miotRequest.setId(i);
        sendMessageWithoutEncrypt(miotRequest.encodeRequest());
    }

    public void sendInternalRequest(MiotRequest miotRequest, boolean z, MiotResponseHandler miotResponseHandler) {
        if (z) {
            int i = this.requestId;
            this.requestId = i + 1;
            miotRequest.setId(i);
        }
        if (miotResponseHandler != null) {
            this.responseHandlers.put(Integer.valueOf(miotRequest.getId()), miotResponseHandler);
            ScheduledExecutorService scheduledExecutorService = this.mExecutor;
            if (scheduledExecutorService != null) {
                this.mScheduledFutureMap.put(Integer.valueOf(miotRequest.getId()), scheduledExecutorService.schedule(new RequestTimeoutRunnable(miotRequest.getId()), 6L, TimeUnit.SECONDS));
            }
        }
        sendMessageWithoutEncrypt(miotRequest.encodeRequest());
    }

    public void sendRequest(MiotRequest miotRequest, MiotResponseHandler miotResponseHandler) {
        sendRequest(miotRequest, true, miotResponseHandler);
    }

    public void sendRequest(MiotRequest miotRequest, boolean z, MiotResponseHandler miotResponseHandler) {
        if (this.mIsLoginSuccess) {
            if (z) {
                int i = this.requestId;
                this.requestId = i + 1;
                miotRequest.setId(i);
            }
            if (miotResponseHandler != null) {
                this.responseHandlers.put(Integer.valueOf(miotRequest.getId()), miotResponseHandler);
                ScheduledExecutorService scheduledExecutorService = this.mExecutor;
                if (scheduledExecutorService != null) {
                    this.mScheduledFutureMap.put(Integer.valueOf(miotRequest.getId()), scheduledExecutorService.schedule(new RequestTimeoutRunnable(miotRequest.getId()), 6L, TimeUnit.SECONDS));
                }
            }
            sendMessageWithoutEncrypt(miotRequest.encodeRequest());
        } else if (miotResponseHandler != null) {
            miotResponseHandler.onFailed(MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED);
        }
    }

    public void sendResponse(MiotRequest miotRequest) {
        sendMessageWithoutEncrypt(miotRequest.encodeResponse());
    }

    public synchronized void sendMessageWithoutEncrypt(byte[] bArr) {
        MiotLogUtil.d(TAG, "[start]sendMessageWithoutEncrypt: " + new String(bArr) + "， connected = " + this.connected);
        if (this.connected && this.f != null) {
            ByteBuf buffer = Unpooled.buffer(bArr.length);
            buffer.writeBytes(bArr);
            this.f.channel().writeAndFlush(buffer);
            MiotLogUtil.d(TAG, "[end]data length:" + bArr.length + " full date length:" + bArr.length);
        }
    }

    public void setUploadOtcInfoSuccess() {
        ScheduledFuture scheduledFuture = this.mWaitOtcInfoScheduledFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.mWaitOtcInfoScheduledFuture = null;
        }
        this.mIsLoginSuccess = true;
        ConnectionListener connectionListener = this.listener;
        if (connectionListener != null) {
            connectionListener.onLoginSuccessful();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.client.MiotClientListener
    public void onConnected() {
        MiotLogUtil.d(TAG, "onConnected");
        this.connected = true;
        ConnectionListener connectionListener = this.listener;
        if (connectionListener != null) {
            connectionListener.onConnected();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.client.MiotClientListener
    public void onDisconnected() {
        MiotLogUtil.d(TAG, "onDisconnected");
        this.connected = false;
        ConnectionListener connectionListener = this.listener;
        if (connectionListener != null) {
            connectionListener.onDisconnected();
        }
        clearAllWaitRequest();
        ScheduledFuture scheduledFuture = this.mWaitOtcInfoScheduledFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.mWaitOtcInfoScheduledFuture = null;
        }
        connect(this.listener);
    }

    private void clearAllWaitRequest() {
        Iterator<Map.Entry<Integer, ScheduledFuture>> it = this.mScheduledFutureMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, ScheduledFuture> next = it.next();
            int intValue = next.getKey().intValue();
            ScheduledFuture value = next.getValue();
            if (value != null) {
                value.cancel(true);
            }
            MiotResponseHandler miotResponseHandler = this.responseHandlers.get(Integer.valueOf(intValue));
            if (miotResponseHandler != null) {
                miotResponseHandler.onFailed(MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED);
                this.responseHandlers.remove(Integer.valueOf(intValue));
            }
            it.remove();
        }
    }

    @Override // com.xiaomi.miot.host.lan.impl.client.MiotClientListener
    public void onReceiveMessage(ChannelHandlerContext channelHandlerContext, byte[] bArr) {
        String str = new String(bArr);
        TransmitListener transmitListener = this.mTransmitListener;
        if (transmitListener != null) {
            transmitListener.onTransparentTransmit(str);
        }
        MiotLogUtil.d(TAG, String.format(Locale.US, "onReceiveMessage(%d) : %s", Integer.valueOf(bArr.length), new String(str)));
        if (!needInterruptMessage(str)) {
            MiotMessage parse = MiotMessageFactory.parse(bArr);
            if (parse == null) {
                MiotLogUtil.d(TAG, "Invalid message");
            } else if (parse.getType() == MiotMessageType.REQUEST) {
                this.requestHandler.onRequest((MiotRequest) parse);
            } else {
                MiotResponse miotResponse = (MiotResponse) parse;
                ScheduledFuture scheduledFuture = this.mScheduledFutureMap.get(Integer.valueOf(miotResponse.getId()));
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(true);
                    this.mScheduledFutureMap.remove(Integer.valueOf(miotResponse.getId()));
                }
                MiotResponseHandler miotResponseHandler = this.responseHandlers.get(Integer.valueOf(miotResponse.getId()));
                if (miotResponseHandler == null) {
                    MiotLogUtil.d(TAG, "Response Handler not found: " + miotResponse.getId());
                    return;
                }
                if (miotResponse.hasError()) {
                    int optInt = miotResponse.getError().optInt("code");
                    String optString = miotResponse.getError().optString("message");
                    miotResponseHandler.onFailed(new MiotError(optInt, optString));
                    MiotResponseHandler miotResponseHandler2 = this.mGeneralResponseHandler;
                    if (miotResponseHandler2 != null) {
                        miotResponseHandler2.onFailed(new MiotError(optInt, optString));
                    }
                } else {
                    miotResponseHandler.onSucceed(miotResponse);
                    MiotResponseHandler miotResponseHandler3 = this.mGeneralResponseHandler;
                    if (miotResponseHandler3 != null) {
                        miotResponseHandler3.onSucceed(miotResponse);
                    }
                }
                this.responseHandlers.remove(Integer.valueOf(miotResponse.getId()));
            }
        }
    }

    private boolean needInterruptMessage(String str) {
        try {
            if (!TextUtils.equals(new JSONObject(str).optString(SchemaActivity.KEY_METHOD), "miIO.clear_user_data") || this.mMiotInternalCommandListener == null) {
                return false;
            }
            this.mMiotInternalCommandListener.clearUserData();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public class RequestTimeoutRunnable implements Runnable {
        private int requestId;

        public RequestTimeoutRunnable(int i) {
            MiotClient.this = r1;
            this.requestId = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            MiotResponseHandler miotResponseHandler = (MiotResponseHandler) MiotClient.this.responseHandlers.get(Integer.valueOf(this.requestId));
            if (miotResponseHandler != null) {
                MiotLogUtil.d(MiotClient.TAG, "response timeout: " + this.requestId);
                miotResponseHandler.onFailed(MiotError.INTERNAL_REQUEST_TIMEOUT);
                MiotClient.this.responseHandlers.remove(Integer.valueOf(this.requestId));
                MiotClient.this.mScheduledFutureMap.remove(Integer.valueOf(this.requestId));
                if (MiotClient.this.mGeneralResponseHandler != null) {
                    MiotClient.this.mGeneralResponseHandler.onFailed(MiotError.INTERNAL_REQUEST_TIMEOUT);
                }
            }
        }
    }

    public void setmTransmitListener(TransmitListener transmitListener) {
        this.mTransmitListener = transmitListener;
    }
}
