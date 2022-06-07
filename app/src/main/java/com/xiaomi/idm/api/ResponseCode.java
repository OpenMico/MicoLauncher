package com.xiaomi.idm.api;

import androidx.annotation.NonNull;
import com.xiaomi.mi_connect_sdk.util.LogUtil;

/* loaded from: classes3.dex */
public interface ResponseCode {
    public static final int ERR_RMI_UNKNOWN = -9999;
    public static final String ERR_RMI_UNKNOWN_MSG = "Unknown response code";
    public static final String TAG = "ResponseCode";

    int getCode();

    String getMsg();

    /* loaded from: classes3.dex */
    public enum ConnectCode implements ResponseCode {
        CONN_STAT_CONNECTED(0, "Service connected"),
        CONN_STAT_TO_BE_CONFIRM(13, "Connect service to be confirmed"),
        CONN_STAT_REMOTE_REJECTED(-20, "Remote reject to connect"),
        CONN_STAT_LOCAL_REJECTED(7, "Local reject to connect"),
        CONN_STAT_DISCONNECT(8, "Service disconnected"),
        CONN_STAT_INVITED(14, "Service invited"),
        CONN_STAT_PHYSICAL_LINK_CONNECTED(15, "Physical link connected"),
        CONN_STAT_WLAN_SERVER_STARTED(18, "WLAN server started"),
        CONN_STAT_WLAN_SERVER_STOPPED(19, "WLAN server stopped"),
        CONN_STAT_ERR_SERVICE_NOT_FOUND(-14, "Error: Service not found"),
        CONN_STAT_ERR_ILLEGAL_PARAMETER(-2, "Error: Illegal parameter"),
        SERVICE_INCOMPATIBLE(-16, "Error: Service incompatible"),
        SERVER_OCCUPIED(-17, "Error: Server occupied"),
        SERVER_NOT_EXIST(-19, "Error: Sever not exist"),
        COMMUNICATION_ERROR(-21, "Error: Communication Error"),
        PHYSICAL_LINK_ERROR(-129, "Error: Physical link error"),
        REMOTE_CLIENT_NOT_EXIST(-22, "Error: Remote client not exist"),
        SA_NOT_VERIFIED(-126, "Error: Same account not verified"),
        SA_VERIFIED_UNKNOWN(-127, "Error: Same account unknown"),
        SA_VERIFIED_FAILED(-128, "Error: Not same account"),
        CONN_STAT_ENDPOINT_NOT_PAIRED(-131, "Error: Endpoint not paired"),
        CONN_STAT_WLAN_SERVER_CREATE_ERROR(-132, "Error: WLAN server create error"),
        CONN_UNKNOWN(ResponseCode.ERR_RMI_UNKNOWN, "Error: Unknown connect error");
        
        private int code;
        private String msg;

        ConnectCode(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public int getCode() {
            return this.code;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public String getMsg() {
            return this.msg;
        }

        public static ConnectCode fromCode(int i) {
            ConnectCode[] values = values();
            for (ConnectCode connectCode : values) {
                if (connectCode.code == i) {
                    return connectCode;
                }
            }
            return CONN_UNKNOWN;
        }

        @NonNull
        public static String getResponseMsg(int i) {
            ConnectCode[] values = values();
            for (ConnectCode connectCode : values) {
                if (connectCode.code == i) {
                    return connectCode.msg;
                }
            }
            LogUtil.e(ResponseCode.TAG, "ConnectCode response code: Unknown response code: [" + i + "] need to define here", new Object[0]);
            return "ConnectCode response code: unKnown response code: [" + i + "]";
        }
    }

    /* loaded from: classes3.dex */
    public enum RequestCode implements ResponseCode {
        REQUEST_SUCCEED(0, "RequestCode succeed"),
        READY_FOR_LOCAL_REQUEST(1, "Ready for local request"),
        READY_FOR_RPC_REQUEST(2, "Ready for RPC request"),
        ERR_REQUEST_NULL(-1, "Error when request bytes is null"),
        ERR_REQUEST_PARSE(-2, "Error when parse request bytes to IDMRequest proto"),
        ERR_RESPONSE_NULL(-3, "Response bytes null when do request"),
        ERR_RESPONSE_PARSE(-4, "Response parse error when do request"),
        ERR_INVALID_SERVICE_ID(-5, "Service id is illegal"),
        ERR_SERVICE_NOT_FOUND(-6, "Service not found"),
        ERR_ACTION_NOT_FOUND(-7, "Action not found"),
        ERR_RESPONSE_PARSE_IN_ACTION(-8, "Response parse error in action"),
        ERR_RMI_TIME_OUT(-9, "RMI call time out"),
        ERR_RMI_THREAD_INTERRUPTED(-10, "Calling thread is interrupted"),
        ERR_RMI_CANCELED(-11, "Call is canceled"),
        ERR_SERVICE_LOST(-12, "Service is lost"),
        ERR_CLIENT_DESTROYED(-13, "Client is destroyed"),
        ERR_REMOTE_UNREACHABLE(-14, "RPC remote unreachable"),
        ERR_SERVICE_NOT_CONNECTED(-15, "Service not connected"),
        ERR_SERVICE_DISCONNECTING(-16, "Service disconnected when requesting"),
        ERR_REQUEST_RPC(-17, "Request failed when call RPC service"),
        REQ_UNKNOWN(ResponseCode.ERR_RMI_UNKNOWN, "Unknown request error");
        
        private int code;
        private String msg;

        RequestCode(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public int getCode() {
            return this.code;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public String getMsg() {
            return this.msg;
        }

        public static RequestCode fromCode(int i) {
            RequestCode[] values = values();
            for (RequestCode requestCode : values) {
                if (requestCode.code == i) {
                    return requestCode;
                }
            }
            return REQ_UNKNOWN;
        }

        public static String getResponseMsg(int i) {
            RequestCode[] values = values();
            for (RequestCode requestCode : values) {
                if (requestCode.code == i) {
                    return requestCode.msg;
                }
            }
            LogUtil.e(ResponseCode.TAG, "RequestCode response code: Unknown response code: [" + i + "] need to define here", new Object[0]);
            return "RequestCode response code: unKnown response code: [" + i + "]";
        }
    }

    /* loaded from: classes3.dex */
    public enum SetEventCode implements ResponseCode {
        EVENT_SUCCEED(0, "Event sub/unSub success"),
        EVENT_ERR_SERVICE_NOT_FOUND(-1, "Event Service not found"),
        EVENT_ERR_NETWORK_UNREACHABLE(-2, "Event net work unreachable"),
        SERVICE_NOT_CONNECTED(-3, "Event service not connected"),
        EVENT_ERR_NOT_FOUND(-4, "Event not found"),
        EVENT_ERR_CLIENT_NOT_SUBSCRIBED(-5, "Event not subscribe"),
        EVENT_LOCAL_SERVICE_NOT_AVAILABLE(-6, "Event local mi_connect_service not available"),
        EVENT_CLIENT_NOT_REGISTERED(-15, "Event Client not registered"),
        EVENT_CONCURRENT_ERROR(-16, "Event concurrent error"),
        EVENT_ERR_OUT_OF_MEMORY(-17, "Malloc failed"),
        EVENT_UNKNOWN(-1999, "Unknown set event error");
        
        private int code;
        private String msg;

        SetEventCode(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public int getCode() {
            return this.code;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public String getMsg() {
            return this.msg;
        }

        public static SetEventCode fromCode(int i) {
            SetEventCode[] values = values();
            for (SetEventCode setEventCode : values) {
                if (setEventCode.code == i) {
                    return setEventCode;
                }
            }
            return EVENT_UNKNOWN;
        }

        public static String getResponseMsg(int i) {
            SetEventCode[] values = values();
            for (SetEventCode setEventCode : values) {
                if (setEventCode.code == i) {
                    return setEventCode.msg;
                }
            }
            LogUtil.e(ResponseCode.TAG, "SetEventCode response code: Unknown response code: [" + i + "] need to define here", new Object[0]);
            return "SetEventCode response code: unKnown response code: [" + i + "]";
        }
    }

    /* loaded from: classes3.dex */
    public enum InvitationCode implements ResponseCode {
        INVITE_CONNECTION_SUCCESS(16, "Invite Connection SUCCESS"),
        ABORT_INVITATION_SUCCESS(17, "Abort InvitationCode SUCCESS"),
        ACCEPT_INVITATION_SUCCESS(20, "Accept Invitation SUCCESS"),
        DENY_INVITATION_SUCCESS(21, "Deny Invitation SUCCESS"),
        RECEIVE_INVITATION_SUCCESS(22, "Receive Invitation SUCCESS"),
        WLAN_SERVER_STARTED(18, "WLAN server started"),
        WLAN_SERVER_STOPPED(19, "WLAN server stopped"),
        INVITE_CONNECTION_ERROR(-134, "General inviting connection error"),
        INVITE_CONNECTION_TIMEOUT(-135, "Inviting connection is timeout and aborted"),
        ABORT_INVITATION_ERROR(-136, "Abort invitation error, not aborted"),
        INVITE_BUSY(-137, "Invite Busy"),
        NOT_IN_INVITING(-138, "Not In Inviting"),
        INV_UNKNOWN(ResponseCode.ERR_RMI_UNKNOWN, "Unknown invitation error");
        
        private int code;
        private String msg;

        InvitationCode(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public int getCode() {
            return this.code;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public String getMsg() {
            return this.msg;
        }

        public static InvitationCode fromCode(int i) {
            InvitationCode[] values = values();
            for (InvitationCode invitationCode : values) {
                if (invitationCode.code == i) {
                    return invitationCode;
                }
            }
            return INV_UNKNOWN;
        }

        public static String getResponseMsg(int i) {
            InvitationCode[] values = values();
            for (InvitationCode invitationCode : values) {
                if (invitationCode.code == i) {
                    return invitationCode.msg;
                }
            }
            LogUtil.e(ResponseCode.TAG, "InvitationCode response code: Unknown response code: [" + i + "] need to define here", new Object[0]);
            return "InvitationCode response code: unKnown response code: [" + i + "]";
        }
    }

    /* loaded from: classes3.dex */
    public enum DiscoveryCode implements ResponseCode {
        START_DISCOVERY_SUCCESS(1, "Start Discovery SUCCESS"),
        STOP_DISCOVERY_SUCCESS(2, "Stop Discovery SUCCESS"),
        DISCOVERY_END(10, "Discovery END"),
        ALREADY_DISCOVERY(-6, "Already In Discovery"),
        START_DISCOVERY_ERROR(-9, "Start Discovery Error"),
        STOP_DISCOVERY_ERROR(-10, "Stop Discovery Error"),
        NOT_IN_DISCOVERY(-13, "Not In Discovery"),
        DISCOVERY_INTERRUPTED(-139, "Discovery is interrupted"),
        DISC_UNKNOWN(ResponseCode.ERR_RMI_UNKNOWN, "Unknown discovery error");
        
        private int code;
        private String msg;

        DiscoveryCode(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        public static DiscoveryCode fromCode(int i) {
            DiscoveryCode[] values = values();
            for (DiscoveryCode discoveryCode : values) {
                if (discoveryCode.code == i) {
                    return discoveryCode;
                }
            }
            return DISC_UNKNOWN;
        }

        public static String getResponseMsg(int i) {
            DiscoveryCode[] values = values();
            for (DiscoveryCode discoveryCode : values) {
                if (discoveryCode.code == i) {
                    return discoveryCode.msg;
                }
            }
            LogUtil.e(ResponseCode.TAG, "DiscoveryCode response code: Unknown response code: [" + i + "] need to define here", new Object[0]);
            return "DiscoveryCode response code: unKnown response code: [" + i + "]";
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public int getCode() {
            return this.code;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public String getMsg() {
            return this.msg;
        }
    }

    /* loaded from: classes3.dex */
    public enum AdvertisingCode implements ResponseCode {
        START_ADVERTISING_SUCCESS(3, "Start Advertising SUCCESS"),
        STOP_ADVERTISING_SUCCESS(4, "Stop Advertising SUCCESS"),
        ALREADY_ADVERTISING(-5, "Already In Advertising"),
        START_ADVERTISING_ERROR(-7, "Start Advertising Error"),
        STOP_ADVERTISING_ERROR(-8, "Stop Advertising Error"),
        NOT_IN_ADVERTISING(-12, "Not In Advertising"),
        ADV_UNKNOWN(ResponseCode.ERR_RMI_UNKNOWN, "Unknown advertising code");
        
        private int code;
        private String msg;

        AdvertisingCode(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        public static AdvertisingCode fromCode(int i) {
            AdvertisingCode[] values = values();
            for (AdvertisingCode advertisingCode : values) {
                if (advertisingCode.code == i) {
                    return advertisingCode;
                }
            }
            return ADV_UNKNOWN;
        }

        public static String getResponseMsg(int i) {
            AdvertisingCode[] values = values();
            for (AdvertisingCode advertisingCode : values) {
                if (advertisingCode.code == i) {
                    return advertisingCode.msg;
                }
            }
            LogUtil.e(ResponseCode.TAG, "AdvertisingCode response code: Unknown response code: [" + i + "] need to define here", new Object[0]);
            return "AdvertisingCode response code: unKnown response code: [" + i + "]";
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public int getCode() {
            return this.code;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public String getMsg() {
            return this.msg;
        }
    }

    /* loaded from: classes3.dex */
    public enum VerifyStatus implements ResponseCode {
        NOT_VERIFIED(-1, "Not Verified"),
        VERIFIED_UNKNOWN(0, "Unknown status"),
        VERIFIED_SUCCEED(1, "Same Account"),
        VERIFIED_FAILED(2, "Not Same Account"),
        VF_UNKNOWN(ResponseCode.ERR_RMI_UNKNOWN, "Unknown verifyStatus code");
        
        private int code;
        private String msg;

        VerifyStatus(int i, String str) {
            this.code = i;
            this.msg = str;
        }

        public static VerifyStatus fromCode(int i) {
            VerifyStatus[] values = values();
            for (VerifyStatus verifyStatus : values) {
                if (verifyStatus.code == i) {
                    return verifyStatus;
                }
            }
            return VF_UNKNOWN;
        }

        public static String getResponseMsg(int i) {
            VerifyStatus[] values = values();
            for (VerifyStatus verifyStatus : values) {
                if (verifyStatus.code == i) {
                    return verifyStatus.msg;
                }
            }
            LogUtil.e(ResponseCode.TAG, "VerifyStatus response code: Unknown response code: [" + i + "] need to define here", new Object[0]);
            return "VerifyStatus response code: unKnown response code: [" + i + "]";
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public int getCode() {
            return this.code;
        }

        @Override // com.xiaomi.idm.api.ResponseCode
        public String getMsg() {
            return this.msg;
        }
    }
}
