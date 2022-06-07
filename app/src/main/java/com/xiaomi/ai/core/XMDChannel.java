package com.xiaomi.ai.core;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.b.c;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.transport.LiteCryptInterceptor;
import com.xiaomi.common.Optional;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class XMDChannel extends a {
    private static final int DEFAULT_XMD_PORT = 9200;
    private static final int DNS_TIMEOUT = 5;
    private static final int MAX_BINARY_SEQUENCE = 9000000;
    private static final int MAX_CORE_SEQUENCE = 2000000;
    private static final int MAX_NORMAL_SEQUENCE = 4000000;
    private static final int MAX_OTHER_SEQUENCE = 6000000;
    private static final int MIN_BINARY_SEQUENCE = 7000000;
    private static final int MIN_CORE_SEQUENCE = 1000000;
    private static final int MIN_NORMAL_SEQUENCE = 3000000;
    private static final int MIN_OTHER_SEQUENCE = 5000000;
    private static final String TAG = "XMDChannel";
    private static volatile boolean mLibLoaded;
    private Map<Long, Long> mBinaryDelayMap;
    private volatile boolean mConnected;
    private int mDnsFailCount;
    private int mDnsFailTime;
    private int mErrorCode;
    private int mFailureCode;
    private LiteCryptInterceptor mInterceptor;
    private ObjectNode mTrackProcess;
    private long mXmdInstance;
    private int mCoreSequenceId = 1000000;
    private int mNormalSequenceId = MIN_NORMAL_SEQUENCE;
    private int mOtherSequenceId = MIN_OTHER_SEQUENCE;
    private int mBinarySequenceId = MIN_BINARY_SEQUENCE;
    private long mConnId = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a implements Runnable {
        private long b;

        a(long j) {
            this.b = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b != 0) {
                Logger.b(XMDChannel.TAG, "ReleaseXmdRunnable: release xmdInstance=" + this.b);
                XMDChannel.this.release_xmd_instance(this.b);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum b {
        EMPTY(0),
        AUTHORIZATION(1),
        AIVS_ENCRYPTION_CRC(2),
        AIVS_ENCRYPTION_KEY(3),
        AIVS_ENCRYPTION_TOKEN(4),
        DATE(5),
        USER_AGENT(6),
        HEARTBEAT_CLIENT(7),
        EVENT_RESEND_COUNT(8),
        BINARY_RESEND_COUNT(9),
        RESEND_DELAY(10),
        STREAM_WAIT_TIME(11),
        CONN_RESEND_COUNT(12),
        CONN_RESEND_DELAY(13),
        ENABLE_MTU_DETECT(14),
        SLICE_SIZE(15),
        XMD_CONNECT_PARAMS_NUM(16);
        
        private final int r;

        b(int i) {
            this.r = i;
        }
    }

    public XMDChannel(AivsConfig aivsConfig, Settings.ClientInfo clientInfo, int i, b bVar) {
        super(aivsConfig, clientInfo, i, bVar);
        init();
    }

    public XMDChannel(AivsConfig aivsConfig, Settings.ClientInfo clientInfo, com.xiaomi.ai.a.a aVar, b bVar) {
        super(aivsConfig, clientInfo, aVar, bVar);
        init();
    }

    private void changeWsNext(int i) {
        long currentTimeMillis = (System.currentTimeMillis() / 1000) + i;
        getListener().a(this, "xmd_ws_expire_at", String.valueOf(currentTimeMillis));
        Logger.b(TAG, "switch from xmd to ws next time. Expire at:" + new Date(currentTimeMillis * 1000).toString());
    }

    private long connectXMD(String str, int i, String[] strArr) {
        long j = this.mXmdInstance;
        if (j != 0) {
            return connect_xmd(j, str, i, strArr);
        }
        Logger.c(TAG, "connectXMD: not available");
        return -1L;
    }

    private native long connect_xmd(long j, String str, int i, String[] strArr);

    private native long create_xmd_instance();

    private String[] getXMDHeader(Map<String, String> map) {
        String[] strArr = new String[b.XMD_CONNECT_PARAMS_NUM.r];
        strArr[b.AUTHORIZATION.r] = map.get("Authorization");
        strArr[b.AIVS_ENCRYPTION_CRC.r] = map.get("AIVS-Encryption-CRC");
        strArr[b.AIVS_ENCRYPTION_KEY.r] = map.get("AIVS-Encryption-Key");
        strArr[b.AIVS_ENCRYPTION_TOKEN.r] = map.get("AIVS-Encryption-Token");
        strArr[b.DATE.r] = map.get("Date");
        strArr[b.USER_AGENT.r] = this.mAivsConfig.getString(AivsConfig.Connection.USER_AGENT, "");
        strArr[b.HEARTBEAT_CLIENT.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_PING_INTERVAL));
        strArr[b.EVENT_RESEND_COUNT.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_EVENT_RESEND_COUNT));
        strArr[b.BINARY_RESEND_COUNT.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_BINARY_RESEND_COUNT));
        strArr[b.RESEND_DELAY.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_RESEND_DELAY));
        strArr[b.STREAM_WAIT_TIME.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_STREAM_WAIT_TIME));
        strArr[b.CONN_RESEND_COUNT.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_CONN_RESEND_COUNT));
        strArr[b.CONN_RESEND_DELAY.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_CONN_RESEND_DELAY));
        strArr[b.ENABLE_MTU_DETECT.r] = String.valueOf(this.mAivsConfig.getBoolean(AivsConfig.Connection.XMD_ENABLE_MTU_DETECT) ? 1 : 0);
        strArr[b.SLICE_SIZE.r] = String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_SLICE_SIZE));
        if (Logger.getLogLevel() == 3) {
            Logger.a(TAG, "AUTHORIZATION: " + strArr[b.AUTHORIZATION.r]);
            Logger.a(TAG, "AIVS_ENCRYPTION_CRC: " + strArr[b.AIVS_ENCRYPTION_CRC.r]);
            Logger.a(TAG, "AIVS_ENCRYPTION_KEY: " + strArr[b.AIVS_ENCRYPTION_KEY.r]);
            Logger.a(TAG, "AIVS_ENCRYPTION_TOKEN: " + strArr[b.AIVS_ENCRYPTION_TOKEN.r]);
            Logger.a(TAG, "DATE: " + strArr[b.DATE.r]);
            Logger.a(TAG, "USER_AGENT: " + strArr[b.USER_AGENT.r]);
            Logger.a(TAG, "HEARTBEAT_CLIENT: " + strArr[b.HEARTBEAT_CLIENT.r]);
            Logger.a(TAG, "EVENT_RESEND_COUNT: " + strArr[b.EVENT_RESEND_COUNT.r]);
            Logger.a(TAG, "BINARY_RESEND_COUNT: " + strArr[b.BINARY_RESEND_COUNT.r]);
            Logger.a(TAG, "RESEND_DELAY: " + strArr[b.RESEND_DELAY.r]);
            Logger.a(TAG, "STREAM_WAIT_TIME: " + strArr[b.STREAM_WAIT_TIME.r]);
            Logger.a(TAG, "CONN_RESEND_COUNT: " + strArr[b.CONN_RESEND_COUNT.r]);
            Logger.a(TAG, "CONN_RESEND_DELAY: " + strArr[b.CONN_RESEND_DELAY.r]);
            Logger.a(TAG, "ENABLE_MTU_DETECT: " + strArr[b.ENABLE_MTU_DETECT.r]);
            Logger.a(TAG, "SLICE_SIZE: " + strArr[b.SLICE_SIZE.r]);
        }
        return strArr;
    }

    private void handShake(Instruction instruction) {
        if (AIApiConstants.Settings.ConnectionChallenge.equals(instruction.getHeader().getFullName())) {
            String id = instruction.getId();
            Logger.b(TAG, "handShake: challenge id:" + id);
            updateTrackTimestamp("sdk.connect.ws.recv.challenge", System.currentTimeMillis());
            Settings.ConnectionChallenge connectionChallenge = (Settings.ConnectionChallenge) instruction.getPayload();
            String challenge = connectionChallenge.getChallenge();
            Optional<String> aesToken = connectionChallenge.getAesToken();
            Optional<Integer> tokenExpiresIn = connectionChallenge.getTokenExpiresIn();
            if (aesToken.isPresent() && tokenExpiresIn.isPresent()) {
                this.mInterceptor.updateAesToken(aesToken.get(), (tokenExpiresIn.get().intValue() * 1000) + System.currentTimeMillis());
            }
            Settings.ConnectionChallengeAck connectionChallengeAck = new Settings.ConnectionChallengeAck();
            connectionChallengeAck.setChallengeMd5(com.xiaomi.ai.b.b.a(challenge));
            Event buildEvent = APIUtils.buildEvent(connectionChallengeAck);
            updateTrackTimestamp("sdk.connect.ws.send.challengeack", System.currentTimeMillis());
            postEvent(buildEvent);
            sendInitEvent();
            this.mConnected = true;
            Logger.b(TAG, "handShake:send ackString, ackEvent:" + buildEvent.getId());
            updateTrackTimestamp("sdk.connect.finish", System.currentTimeMillis());
            getListener().e(this);
            synchronized (this) {
                notify();
            }
            return;
        }
        Logger.d(TAG, "handShake: failed at " + instruction);
    }

    private void init() {
        synchronized (XMDChannel.class) {
            if (!mLibLoaded) {
                System.loadLibrary("xmd");
                mLibLoaded = true;
            }
        }
        set_log_level(Logger.getLogLevel());
        this.mInterceptor = new LiteCryptInterceptor(this);
        this.mHttpDns = new com.xiaomi.ai.transport.b(this, new c(this.mAivsConfig).c());
        this.mBinaryDelayMap = new ConcurrentHashMap();
        this.mDnsFailCount = this.mAivsConfig.getInt(AivsConfig.Connection.DNS_FAIL_COUNT, 4);
        this.mDnsFailTime = this.mAivsConfig.getInt(AivsConfig.Connection.DNS_FAIL_TIME, 2000);
    }

    private boolean onBinary(byte[] bArr, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append("onBinary：");
        sb.append(bArr == null ? "null" : Integer.valueOf(bArr.length));
        sb.append(", packetId: ");
        sb.append(j);
        Logger.b(TAG, sb.toString());
        try {
            getListener().a(this, this.mInterceptor.aesCrypt(2, bArr));
            return true;
        } catch (GeneralSecurityException | Exception e) {
            Logger.d(TAG, Logger.throwableToString(e));
            return false;
        }
    }

    private boolean onError(int i, String str) {
        int i2;
        String str2;
        boolean z;
        AivsError aivsError;
        Logger.d(TAG, "onError: code=" + i + ", msg=" + str + ", network=" + this.mListener.d());
        this.mErrorCode = i;
        if (i == 99999) {
            try {
                i2 = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                Logger.d(TAG, Logger.throwableToString(e));
                i2 = 0;
            }
            if ((i2 >= MIN_NORMAL_SEQUENCE && i2 <= MAX_NORMAL_SEQUENCE) || (i2 >= MIN_BINARY_SEQUENCE && i2 <= MAX_BINARY_SEQUENCE)) {
                return true;
            }
            if (i2 >= MIN_OTHER_SEQUENCE && i2 <= MAX_OTHER_SEQUENCE) {
                changeWsNext(259200);
            }
            str2 = "drop sequenceId: " + str;
            z = false;
        } else {
            this.mFailureCode = processErrorMsg(this.mInterceptor, str);
            int i3 = this.mErrorCode;
            if (i3 == 401) {
                aivsError = new AivsError(401, str);
            } else {
                if (i3 == 500) {
                    aivsError = new AivsError(500, str);
                }
                str2 = str;
                z = true;
            }
            this.mLastError = aivsError;
            str2 = str;
            z = true;
        }
        if (i == 401 || i == 900 || i == 904) {
            z = false;
        }
        if (z) {
            changeWsNext(this.mAivsConfig.getInt(AivsConfig.Connection.XMD_WS_EXPIRE_IN));
        }
        if (this.mConnected) {
            getListener().f(this);
        }
        ObjectNode objectNode = this.mTrackProcess;
        if (objectNode != null) {
            objectNode.put("msg", str2 + ", code=" + i + ", connId=" + this.mConnId);
        }
        if (this.mTrackData != null) {
            this.mTrackData.set("sdk.connect.error.msg", str2 + ", code=" + i + ", connId=" + this.mConnId);
        }
        if (!this.mConnected) {
            synchronized (this) {
                notify();
            }
        } else {
            stop();
        }
        return true;
    }

    private boolean onInstruction(String str, long j) {
        try {
            Instruction readInstruction = APIUtils.readInstruction(new String(this.mInterceptor.aesCrypt(2, com.xiaomi.ai.b.a.a(str.getBytes(), 0))));
            String str2 = "";
            if (readInstruction.getDialogId().isPresent()) {
                str2 = readInstruction.getDialogId().get();
            }
            Logger.b(TAG, "onInstruction: " + readInstruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str2 + Constants.ACCEPT_TIME_SEPARATOR_SP + j);
            if (!this.mConnected) {
                handShake(readInstruction);
                return true;
            }
            getListener().a(this, readInstruction);
            return true;
        } catch (IOException | GeneralSecurityException | Exception e) {
            Logger.d(TAG, Logger.throwableToString(e));
            return false;
        }
    }

    public static void onLogger(int i, String str, String str2) {
        switch (i) {
            case 0:
                Logger.d(str, str2);
                return;
            case 1:
                Logger.c(str, str2);
                return;
            case 2:
                Logger.b(str, str2);
                return;
            case 3:
                Logger.a(str, str2);
                return;
            default:
                return;
        }
    }

    private void onSendSuccess(long j) {
        Logger.b(TAG, "onSendSuccess: " + j);
        if (j >= 7000000 && j <= 9000000) {
            Long l = this.mBinaryDelayMap.get(Long.valueOf(j));
            long j2 = 0;
            if (l != null) {
                j2 = System.currentTimeMillis() - l.longValue();
            }
            this.mListener.a(j, j2);
            this.mBinaryDelayMap.remove(Long.valueOf(j));
        }
    }

    private void onTrackEvent(String str, long j) {
        Logger.a(TAG, "onTrackEvent: key =" + str + ", timestamp=" + j);
        updateTrackTimestamp(str, j);
    }

    private native boolean post_data(long j, byte[] bArr, int i, int i2);

    private native boolean post_event(long j, String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean release_xmd_instance(long j);

    private void sendInitEvent() {
        Event<Settings.GlobalConfig> initEvent = getInitEvent();
        Logger.b(TAG, "sendInitEvent:" + initEvent.getId());
        postEvent(initEvent);
    }

    private native void set_log_level(int i);

    public void finishTrack() {
        if (this.mTrackData != null) {
            this.mTrackData.finishTrack();
        }
    }

    @Override // com.xiaomi.ai.core.a
    public String getChannelType() {
        return "xmd";
    }

    @Override // com.xiaomi.ai.core.a
    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Override // com.xiaomi.ai.core.a
    public int getFailureCode() {
        return this.mFailureCode;
    }

    @Override // com.xiaomi.ai.core.a
    public int getType() {
        return 1;
    }

    @Override // com.xiaomi.ai.core.a
    public synchronized boolean isConnected() {
        if (this.mXmdInstance == 0) {
            Logger.b(TAG, "isConnected: not available");
            return false;
        }
        return this.mConnected;
    }

    @Override // com.xiaomi.ai.core.a
    public synchronized boolean postData(byte[] bArr) {
        String str;
        String throwableToString;
        if (this.mXmdInstance == 0) {
            Logger.d(TAG, "postData: not available");
            return false;
        }
        int i = this.mBinarySequenceId;
        if (this.mBinarySequenceId >= MAX_BINARY_SEQUENCE) {
            this.mBinarySequenceId = MIN_BINARY_SEQUENCE;
        } else {
            this.mBinarySequenceId++;
        }
        this.mBinaryDelayMap.put(Long.valueOf(i), Long.valueOf(System.currentTimeMillis()));
        StringBuilder sb = new StringBuilder();
        sb.append("postData: length=");
        sb.append(bArr == null ? 0 : bArr.length);
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(i);
        Logger.b(TAG, sb.toString());
        try {
            try {
                byte[] aesCrypt = this.mInterceptor.aesCrypt(1, bArr);
                return post_data(this.mXmdInstance, aesCrypt, aesCrypt.length, i);
            } catch (Exception e) {
                str = TAG;
                throwableToString = Logger.throwableToString(e);
                Logger.d(str, throwableToString);
                return false;
            }
        } catch (GeneralSecurityException e2) {
            str = TAG;
            throwableToString = Logger.throwableToString(e2);
            Logger.d(str, throwableToString);
            return false;
        }
    }

    @Override // com.xiaomi.ai.core.a
    public synchronized boolean postData(byte[] bArr, int i, int i2) {
        if (this.mXmdInstance == 0) {
            Logger.d(TAG, "postData2: not available");
            return false;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return postData(bArr2);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0085 A[Catch: GeneralSecurityException -> 0x0118, JsonProcessingException -> 0x00f5, Exception -> 0x00ea, all -> 0x0122, TryCatch #1 {Exception -> 0x00ea, blocks: (B:27:0x007a, B:29:0x0085, B:30:0x00a4, B:31:0x00d2), top: B:46:0x007a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a4 A[Catch: GeneralSecurityException -> 0x0118, JsonProcessingException -> 0x00f5, Exception -> 0x00ea, all -> 0x0122, TryCatch #1 {Exception -> 0x00ea, blocks: (B:27:0x007a, B:29:0x0085, B:30:0x00a4, B:31:0x00d2), top: B:46:0x007a, outer: #0 }] */
    @Override // com.xiaomi.ai.core.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean postEvent(com.xiaomi.ai.api.common.Event r8) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.core.XMDChannel.postEvent(com.xiaomi.ai.api.common.Event):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0251  */
    @Override // com.xiaomi.ai.core.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean startConnect(boolean r15) {
        /*
            Method dump skipped, instructions count: 645
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.core.XMDChannel.startConnect(boolean):boolean");
    }

    @Override // com.xiaomi.ai.core.a
    public synchronized void stop() {
        Logger.b(TAG, "stop");
        if (this.mXmdInstance == 0) {
            Logger.c(TAG, "stop：not available");
            return;
        }
        c.a.execute(new a(this.mXmdInstance));
        this.mXmdInstance = 0L;
        this.mBinaryDelayMap.clear();
        if (!this.mConnected) {
            synchronized (this) {
                notify();
            }
        }
        this.mConnected = false;
    }
}
