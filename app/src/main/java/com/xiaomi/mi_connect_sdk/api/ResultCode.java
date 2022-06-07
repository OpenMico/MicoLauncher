package com.xiaomi.mi_connect_sdk.api;

import com.xiaomi.micolauncher.application.Constants;

/* loaded from: classes3.dex */
public enum ResultCode {
    GENERAL_SUCCESS(0),
    START_DISCOVERY_SUCCESS(1),
    STOP_DISCOVERY_SUCCESS(2),
    START_ADVERTISING_SUCCESS(3),
    STOP_ADVERTISING_SUCCESS(4),
    REQUEST_CONNECTION_SUCCESS(5),
    ACCEPT_CONNECTION_SUCCESS(6),
    REJECT_CONNECTION_SUCCESS(7),
    DISCONNECT_FROM_END_POINT_SUCCESS(8),
    SEND_PAYLOAD_SUCCESS(9),
    DISCOVERY_END(10),
    LINK_CONNECTION_BEGIN(11),
    LINK_CONNECTION_END(12),
    CONNECTION_TO_BE_CONFIRM(13),
    CONNECTION_INVITED(14),
    PHYSICAL_LINK_CONNECTED(15),
    INVITE_CONNECTION_SUCCESS(16),
    ABORT_INVITATION_SUCCESS(17),
    WLAN_SERVER_STARTED(18),
    WLAN_SERVER_STOPPED(19),
    GENERAL_ERROR(-1),
    APP_NOT_SUPPORT_ERROR(-2),
    SERVICE_ERROR(-3),
    ILLEGAL_ARGUMENT_ERROR(-4),
    ALREADY_ADVERTISING(-5),
    ALREADY_DISCOVERY(-6),
    START_ADVERTISING_ERROR(-7),
    STOP_ADVERTISING_ERROR(-8),
    START_DISCOVERY_ERROR(-9),
    STOP_DISCOVERY_ERROR(-10),
    SEND_PAYLOAD_ERROR(-11),
    NOT_IN_ADVERTISING(-12),
    NOT_IN_DISCOVERY(-13),
    ENDPOINT_ID_INVALID(-14),
    LOCALAPP_ID_INVALID(-15),
    SERVICE_INCOMPATIBLE(-16),
    SERVER_OCCUPIED(-17),
    DISCOVERY_NOT_ALLOWED_IN_CONNECTION(-18),
    SERVER_NOT_EXIST(-19),
    REJECTED(-20),
    COMMUNICATION_ERROR(-21),
    REMOTE_CLIENT_NOT_EXIST(-22),
    ERROR_CODE_SET_CALLBACK_PERMS(-23, "permission deny"),
    ERROR_CODE_START_ADVERTISING_PERMS(-24, "permission deny"),
    ERROR_CODE_START_ADVERTISING_DISC_NONE(-25, "disc type none"),
    ERROR_CODE_START_DISC_NOT_SUPPORTED(-26, "disc type not supported"),
    ERROR_CODE_COMMTYPE_NOT_SUPPORTED(-27, "comm type not supported"),
    ERROR_CODE_APPID_ILLEGAL(-28, "app id illegal"),
    ERROR_CODE_START_ADVERTISING_NO_SERVER(-29, "no server"),
    ERROR_CODE_STOP_ADVERTISING_PERMS(-30, "permission deny"),
    ERROR_CODE_START_DISCOVERY_PERMS(-31, "permission deny"),
    ERROR_CODE_START_DISCOVERY_DISC_NONE(-32, "disc type none"),
    ERROR_CODE_STOP_DISCOVERY_PERMS(-33, "permission deny"),
    ERROR_CODE_GET_SERVICE_API_VERSION_PERMS(-34, "permission deny"),
    ERROR_CODE_REQUEST_CONNECT_PERMS(-35, "permission deny"),
    ERROR_CODE_REQUEST_CONNECT_NO_CLIENT(-36, "no client"),
    ERROR_CODE_ACCEPT_CONNECT_PERMS(-37, "permission deny"),
    ERROR_CODE_ACCEPT_CONNECT_NO_CLIENT(-38, "no client"),
    ERROR_CODE_ACCEPT_CONNECT_NO_SERVER(-39, "no server"),
    ERROR_CODE_REJECT_CONNECT_PERMS(-40, "permission deny"),
    ERROR_CODE_REJECT_CONNECT_NO_CLIENT(-41, "no client"),
    ERROR_CODE_REJECT_CONNECT_NO_SERVER(-42, "no server"),
    ERROR_CODE_DISCONNECT_FROM_ENDPOINT_PERMS(-43, "permission deny"),
    ERROR_CODE_DISCONNECT_FROM_ENDPOINT_NO_CLIENT(-44, "no client"),
    ERROR_CODE_DISCONNECT_FROM_ENDPOINT_NO_SERVER(-45, "no server"),
    ERROR_CODE_SEND_PAYLOAD_PERMS(-46, "permission deny"),
    ERROR_CODE_SEND_PAYLOAD_NO_CLIENT(-47, "no client"),
    ERROR_CODE_SEND_PAYLOAD_NO_SERVER(-48, "no server"),
    ERROR_CODE_DESTROY_PERMS(-49, "permission deny"),
    ERROR_CODE_GET_ID_HASH_PERMS(-50, "permission deny"),
    ERROR_CODE_SET_IPC_DATA_CALLBACK_PERMS(-51, "permission deny"),
    ERROR_CODE_SET_IPC_DATA_CALLBACK_GOVERNOR_NULL(-52, "governor null"),
    ERROR_CODE_PUBLISH_PERMS(-53, "permission deny"),
    ERROR_CODE_PUBLISH_INTERNET_PERMS(-54, "permission deny"),
    ERROR_CODE_PUBLISH_CAMERA_NULL(-55, "camera null"),
    ERROR_CODE_PUBLISH_CLIENT_NULL(-56, "client null"),
    ERROR_CODE_SUBSCRIBE_PERMS(-57, "permission deny"),
    ERROR_CODE_SUBSCRIBE_INTERNET_PERMS(-58, "permission deny"),
    ERROR_CODE_START_ADVERTISING_APP_ID_DENY(-59, "appid deny"),
    ERROR_CODE_STOP_ADVERTISING_APP_ID_DENY(-60, "appid deny"),
    ERROR_CODE_START_DISCOVERY_APP_ID_DENY(-61, "appid deny"),
    ERROR_CODE_STOP_DISCOVERY_APP_ID_DENY(-62, "appid deny"),
    ERROR_CODE_REQUEST_CONNECTION_APP_ID_DENY(-63, "appid deny"),
    ERROR_CODE_ACCEPT_CONNECTION_APP_ID_DENY(-64, "appid deny"),
    ERROR_CODE_REJECT_CONNECTION_APP_ID_DENY(-65, "appid deny"),
    ERROR_CODE_DISCONNECT_FROM_ENDPOINT_APP_ID_DENY(-66, "appid deny"),
    ERROR_CODE_SEND_PAYLOAD_APP_ID_DENY(-67, "appid deny"),
    ERROR_CODE_DESTROY_APP_ID_DENY(-68, "appid deny"),
    ERROR_CODE_START_ADVERTING_REGISTER_APP(-69, "register app failed"),
    ERROR_CODE_START_SERVICE_ADVERTISING_REGISTER_APP(-70, "register app failed"),
    ERROR_CODE_START_DISCOVERY_NOT_ALLOWED(-71, "discovery not allowed"),
    ERROR_CODE_START_DISCOVERY_REGISTER_APP(-72, "register app failed"),
    SERVICE_API_VERSION_TOO_LOW(-73),
    PERMISSION_DENNY(-74),
    SERVICE_NOT_INIT_YET(-75),
    BIND_SERVICE_FAILED(-76),
    START_DISCOVERY_NFC_UNAVAILABLE(-79, "start discovery nfc unavailable"),
    START_DISCOVERY_BLE_UNAVAILABLE(-80, "start discovery ble unavailable"),
    START_DISCOVERY_BT_CLASSIC_UNAVAILABLE(-81, "start discovery bt unavailable"),
    START_DISCOVERY_BT_UNAVAILABLE(-82, "start discovery bt unavailable"),
    START_DISCOVERY_BONJOUR_UNAVAILABLE(-83, "start discovery bonjour unavailble"),
    START_DISCOVERY_IDB_UNAVAILBLE(-84, "start discovery idb unavailable"),
    START_ADVERTISING_BLE_UNAVAILABLE(-85, "start advertising ble unavailable"),
    START_ADVERTISING_BT_CLASS_UNAVAILABLE(-86, "start adverting bt unavailable"),
    START_ADVERTISING_BT_UNAVAILABLE(-87, "start advertising bt unavailable"),
    START_ADVERTISING_BONJOUR_UNAVAILABLE(-88, "start advertising bonjour unavailable"),
    START_ADVERTISING_NFC_UNAVAILABLE(-89, "start advertising nfc unavailable"),
    ERROR_CODE_ATTR_GETVALUE_NULL(-90, "attr value null"),
    ERROR_CODE_ATTR_VALUE_LEN_TOO_SHORT(-91, "attr value len less than 2"),
    ERROR_CODE_ATTR_VALUE_LEN_TOO_LONG(-92, "attr value len too long"),
    ERROR_CODE_ENDPOINT_DECRYPT_DATA_NULL(-93, "decrypt data is null in endpoint"),
    ERROR_CODE_ENDPOINT_DECRYPT_ERROR(-94, "failed to decrypt data in endpoint"),
    ERROR_CODE_ATTR_GETVALUE_NULL_2(-95, "attr value null"),
    ERROR_CODE_ATTR_VALUE_LEN_TOO_SHORT_2(-96, "attr value len less than 2"),
    ERROR_CODE_ATTR_VALUE_LEN_TOO_LONG_2(-97, "attr value len too long"),
    ERROR_CODE_ATTR_NOTIFY_ENDPOINT_GOVERNOR_NULL(-98, "endpoint or governor is null when notify attribute"),
    ERROR_CODE_ATTR_NOTIFY_APPID_INVALID(-99, "appid is invalid when notify attribute"),
    ERROR_CODE_ATTR_NOTIFY_ID_INVALID(-100, "attr id is invalid when notify attr"),
    ERROR_CODE_ATTR_NOTIFY_FAILED(Constants.JOB_ID_REFRESH_WEATHER, "notify attr failed"),
    ERROR_CODE_ATTR_CONN_SERVICE_ENDPOINT_NULL(Constants.JOB_CHECK_ALARM_STATE_ID, "endpoint null when connect service"),
    ERROR_CODE_ATTR_CONN_SERVICE_FAILED(-103, "connect service failed"),
    ERROR_CODE_ATTR_DISCONNECT_SERVICE_ENDPOINT_NULL(-104, "endpoint null when disconnect service"),
    ERROR_CODE_ATTR_DISCONNECT_SERVICE_FAILED(-105, "disconnect service failed"),
    ERROR_CODE_ATTR_READ_ENDPOINT_NULL(-106, "endpoint null when read attr"),
    ERROR_CODE_ATTR_READ_APPID_INVALID(-107, "appid invalid when read attr"),
    ERROR_CODE_ATTR_READ_ID_INVALID(-108, "attr id invalid when read attr"),
    ERROR_CODE_ATTR_READ_FAILED(-109, "read attr failed"),
    ERROR_CODE_ATTR_READ_ENDPOINT_NULL_2(-110, "endpoint null when read attr"),
    ERROR_CODE_ATTR_READ_ID_INVALID_2(-111, "attr id invalid when read attr"),
    ERROR_CODE_ATTR_READ_FAILED_2(-112, "read attr failed"),
    ERROR_CODE_ATTR_WRITE_ENDPOINT_NULL(-113, "endpoint null when write attr"),
    ERROR_CODE_ATTR_WRITE_VALUE_NULL(-114, "value null when write attr"),
    ERROR_CODE_ATTR_WRITE_APPID_INVALID(-115, "appid invalid when write attr"),
    ERROR_CODE_ATTR_WRITE_ID_INVALID(-116, "attr id invalid when write attr"),
    ERROR_CODE_ATTR_WRITE_FAILED(-117, "write attr failed"),
    ERROR_CODE_ATTR_SETNOTI_ENDPOINT_NULL(-118, "endpoint null when set attr notification"),
    ERROR_CODE_ATTR_SETNOTI_APPID_INVALID(-119, "appid invalid when set attr notification"),
    ERROR_CODE_ATTR_SETNOTI_ID_INVALID(-120, "attr id invalid when set attr notification"),
    ERROR_CODE_ATTR_SETNOTI_FAILED(-121, "set attr notification failed"),
    ERROR_CODE_ATTR_UNSETNOTI_ENDPOINT_NULL(-122, "endpoint null when unset attr notification"),
    ERROR_CODE_ATTR_UNSETNOTI_APPID_INVALID(-123, "appid invalid when unset attr notification"),
    ERROR_CODE_ATTR_UNSETNOTI_ID_INVALID(-124, "attr id invalid when unset attr notification"),
    ERROR_CODE_ATTR_UNSETNOTI_FAILED(-125, "unset attr notification failed"),
    SA_NOT_VERIFIED(-126, "Same account not verified"),
    SA_VERIFIED_UNKNOWN(-127, "Same account unknown"),
    SA_VERIFIED_FAILED(-128, "Not same account"),
    PHYSICAL_LINK_ERROR(-129, "Physical link error"),
    NOT_SAME_ACCOUNT(-130, "not same account"),
    ENDPOINT_NOT_PAIRED(-131, "Endpoint not paired yet"),
    WLAN_SERVER_CREATE_ERROR(-132, "WLAN Server create error"),
    WLAN_CLIENT_DISCONNECTED(-133, "WLAN client disconnected"),
    INVITE_CONNECTION_ERROR(-134, "General inviting connection error"),
    INVITE_CONNECTION_TIMEOUT(-135, "Inviting connection is timeout"),
    ABORT_INVITITION_ERROR(-136, "Abort invitation error"),
    INVITE_BUSY(-137, "Invite Busy"),
    NOT_IN_INVITING(-138, "not int inviting"),
    DISCOVERY_INTERRUPTED(-139, "Discovery was interrupted"),
    INVITATION_LETTER_FORMAT_ERROR(-140, "Illegal invite information"),
    EXTRA_ERROR_POSITIVE(10000000),
    EXTRA_ERROR_NEGATIVE(-10000000),
    UNKNOWN_ERROR(-999999);
    
    private int code;
    private String message;

    ResultCode(int i) {
        this.code = i;
        this.message = name();
    }

    ResultCode(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public static ResultCode fromInt(int i) {
        ResultCode[] values = values();
        for (ResultCode resultCode : values) {
            if (resultCode.code == i) {
                return resultCode;
            }
        }
        return UNKNOWN_ERROR;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
