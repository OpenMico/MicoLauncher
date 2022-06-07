package org.hapjs.features.channel;

import org.hapjs.features.channel.appinfo.AndroidApplication;
import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.features.channel.listener.ChannelEventListener;
import org.hapjs.features.channel.listener.EventCallBack;

/* loaded from: classes5.dex */
public interface IChannel {
    public static final int CHANNEL_STATUS_CLOSED = 3;
    public static final int CHANNEL_STATUS_CREATED = 0;
    public static final int CHANNEL_STATUS_OPENED = 2;
    public static final int CHANNEL_STATUS_OPENING = 1;
    public static final int CLOSE_CODE_PASSIVE = 1;
    public static final int CLOSE_CODE_POSITIVE = 0;
    public static final int CLOSE_CODE_REMOTE_APP_DEATH = 3;
    public static final int CLOSE_CODE_SERVICE_DISCONNECTED = 2;
    public static final int CLOSE_CODE_SIGN_NOT_MATCH = 4;
    public static final String DEFAULT_CHANNEL_TYPE = "default";
    public static final int ERROR_CODE_APP_NOT_FOUND = 0;
    public static final int ERROR_CODE_INVALID_STATUS = 2;
    public static final int ERROR_CODE_NATIVE_REFUSE = 3;
    public static final int ERROR_CODE_OTHER = 6;
    public static final int ERROR_CODE_REMOTE_APP_DIE = 4;
    public static final int ERROR_CODE_SIGN_NOT_MATCH = 1;
    public static final int ERROR_CODE_TOO_LARGE_MESSAGE = 5;
    public static final String EXTRA_ACK_OPEN_ID_AT_SERVER = "idAtServer";
    public static final String EXTRA_ACK_OPEN_MESSAGE = "message";
    public static final String EXTRA_ACK_OPEN_RESULT = "result";
    public static final String EXTRA_CHANNEL_TYPE = "channelType";
    public static final String EXTRA_CLIENT_PID = "clientPid";
    public static final String EXTRA_CLOSE_REASON = "reason";
    public static final String EXTRA_ERROR_DESC = "desc";
    public static final String EXTRA_ID_AT_RECEIVER = "idAtReceiver";
    public static final String EXTRA_OPEN_ID_AT_CLIENT = "idAtClient";
    public static final String EXTRA_OPEN_PKG_NAME = "pkgName";
    public static final String EXTRA_OPEN_SIGNATURE = "signature";
    public static final String EXTRA_TRANS_DATA_CONTENT = "content";
    public static final int MAX_MESSAGE_DATA_SIZE = 524288;
    public static final int MAX_MESSAGE_FILE_COUNT = 64;
    public static final int MSG_TYPE_CMD_ACK_OPEN = 1;
    public static final int MSG_TYPE_CMD_CLOSE = 3;
    public static final int MSG_TYPE_CMD_OPEN = 0;
    public static final int MSG_TYPE_ERROR = -1;
    public static final int MSG_TYPE_TRANS_DATA = 2;

    boolean addEventListener(ChannelEventListener channelEventListener);

    void close(int i, String str, boolean z);

    AndroidApplication getAndroidApplication();

    HapApplication getHapApplication();

    int getStatus();

    String getType();

    boolean removeEventListener(ChannelEventListener channelEventListener);

    void send(ChannelMessage channelMessage, EventCallBack eventCallBack);
}
